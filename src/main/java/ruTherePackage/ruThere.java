// This is ruThere. This program is used as a demo to demostrate an update to a fix cell location on a Google sheet
// Todos: Please be sure to update the location of your client_secret.json file & the Googlesheet id before running your program.
// Date: 6/1/18
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.Random;



public class ruThere {

    /** Application name. */
    private static final String APPLICATION_NAME =
            "ruThere";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), "credentials/sheets.googleapis.com-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart.json
     */
    private static final List<String> SCOPES =
        Arrays.asList( SheetsScopes.SPREADSHEETS );

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static Credential authorize() throws IOException {
        // Load client secrets.
        // Todo: Change this text to the location where your client_secret.json resided
        InputStream in = new FileInputStream(System.getProperty("user.home") + "/IdeaProjects/ruThere/src/main/resources/client_secret.json");
            // ruThere.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        //Scanner
        Scanner kb = new Scanner(System.in);

        //Todo: create this demo (For Paul)
        //Json File Demo
        /**
         * Json file shall store
         * -Email of the instructor (use this as identifier)
         * -Password
         * -Class Name
         *  +SheetID
         */


        //Counts for current # of students and current column of date.
        //Column 6 is the column for the counts (update counts from G2 and G3)

        List<List<Object>> cells = null; //data from the classId


        //Start of Program

        //Todo: instructor side
        //Todo: student side
        // Build a new authorized API client service.
        Sheets service = getSheetsService();
        String spreadsheetId = getSpreadsheetId(kb, service);
        cells = getCells(kb, service, spreadsheetId);

        googleSheet mySheet = new googleSheet(spreadsheetId, cells, service);
        generateKey(mySheet);


        while(true) {
            System.out.println("Type your student id ");
            String studentId = kb.nextLine();
            System.out.println("Type today's key ");
            String key = kb.nextLine();
            System.out.println("Type [Here] or answer to today's question");
            String message = kb.nextLine();

            //update the info of the key
            mySheet.updateCells(getCells(kb,mySheet.getService(),mySheet.getSheetId()));

            validateStudent(studentId, key, message, mySheet);
        }
    }

    public static List<List<Object>> getCells(Scanner kb, Sheets service, String spreadsheetId) throws IOException {
        while(true) {
            try {
                String classId;
                System.out.print("Enter the classId--> ");
                classId = kb.nextLine();
                String dateAndStuRange = classId; //locations for dateCol and studentCount
                String valueRenderOption = "UNFORMATTED_VALUE";
                Sheets.Spreadsheets.Values.Get request =
                        service.spreadsheets().values().get(spreadsheetId, dateAndStuRange);
                ValueRange response = request.execute();
                return response.getValues();
            } catch (com.google.api.client.googleapis.json.GoogleJsonResponseException e) {
                System.out.println("invalid classId");
            }
        }
    }

    public static String getSpreadsheetId(Scanner kb, Sheets service) throws IOException{
        String range = "F1:F2";
        String valueRenderOption = "FORMATTED_VALUE";
        String spreadsheetId = "";
        boolean start = true;
        while(start) {
            try {
                System.out.print("Enter your spreadsheet ID--> ");
                spreadsheetId = kb.nextLine();
                Sheets.Spreadsheets.Values.Get request =
                        service.spreadsheets().values().get(spreadsheetId, range);
                ValueRange response = request.execute();
                start = false;
            } catch (com.google.api.client.googleapis.json.GoogleJsonResponseException e) {
                System.out.println("Invalid Sheet ID");
            }
        }
        return spreadsheetId;
    }

    public static String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date currentDay = new Date();
        return dateFormat.format(currentDay);
    }

    public static int generateNewCode() {
        Random rand = new Random();
        int randomnum = rand.nextInt(9998) + 1;
        System.out.println("today's passcode is:");
        System.out.print(randomnum + "\n");
        return randomnum;
    }

    public static void putValue(int row, int col, String value, googleSheet sheet)throws IOException {
        List<Request> requests = new ArrayList<>();

        // Create values object
        List<CellData> values = new ArrayList<>();

        // Add string 5/28/2018 value
        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(value)));

        // Prepare request with proper row and column and its value
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(0)
                                .setRowIndex(row)     // set the row to row 0
                                .setColumnIndex(col)) // set the new column 6 to value 5/28/2018 at row 0
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        sheet.getService().spreadsheets().batchUpdate(sheet.getSheetId(), batchUpdateRequest)
                .execute();
    }

    public static void generateKey(googleSheet sheet) throws IOException {

        int studentCount = sheet.getStudentCount();
        int dateCount = sheet.getDateCount();
        String lastDatePosted = sheet.getCurrentDate();
        String newCode = generateNewCode() + "";

        if (lastDatePosted.equals(getTimeStamp())) {
            putValue(studentCount+1, dateCount, newCode, sheet);
        } else {
            int newDateCount = dateCount+1;
            putValue(0, newDateCount, getTimeStamp(), sheet);
            putValue(studentCount+1, newDateCount, newCode, sheet);
            putValue(1,5, newDateCount+"" , sheet);

        }
    }

    public static boolean keyIsValid(String key, googleSheet sheet) {
        return key.trim().equals(sheet.getCells().get(sheet.getStudentCount()+1).get(sheet.getDateCount()).toString().trim());
    }

    public static int findStudentRow(String studentId, googleSheet sheet) {
        for(int index = 1; index < sheet.getStudentCount()-1; index++) {
            if (studentId.trim().equals(sheet.getCells().get(index).get(2).toString().trim())) {
                System.out.println("Found student row at " + index);
                return index;
            }
        }
        return -1;
    }

    public static void validateStudent(String studentId, String key, String message, googleSheet sheet) throws IOException {
        int studentIndex = findStudentRow(studentId, sheet);
        if(keyIsValid(key, sheet) && studentIndex != -1) {
            putValue(studentIndex, sheet.getDateCount(), message, sheet);
            System.out.println("Your attendance was taken successfully!");
        } else {

            System.out.println("Your key did not match\nor\nyour student id is not in the class");
        }
    }



}

class googleSheet {
    private String sheetId;
    private String currentDate;
    private List<List<Object>> cells;
    private int studentCount;
    private int dateCount;
    private Sheets service;

    public googleSheet(String sheetId, List<List<Object>> cells, Sheets service) {
        this.sheetId = sheetId;
        this.cells   = cells;
        this.studentCount = Integer.parseInt(cells.get(0).get(5).toString());
        this.dateCount    = Integer.parseInt(cells.get(1).get(5).toString());
        this.service = service;
        this.currentDate = cells.get(0).get(dateCount).toString();
    }

    public String getSheetId() {
        return sheetId;
    }
    public List<List<Object>> getCells() {
        return cells;
    }
    public int getStudentCount() {
        return studentCount;
    }
    public int getDateCount() {
        return dateCount;
    }
    public Sheets getService() {
        return service;
    }
    public String getCurrentDate() {
        return currentDate;
    }
    public void updateCells(List<List<Object>> newCells) {
        this.cells = newCells;
    }

}
