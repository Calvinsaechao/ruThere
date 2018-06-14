package ruTherePackage;
// This is ruThere. This program is used as a demo to demostrate an update to a fix cell location on a Google sheet
// Todos: Please be sure to update the location of your client_secret.json file & the Googlesheet id before running your program.
// Date: 6/1/18
import Exceptions.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.EmptyContent;
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

    public static void main(String[] args) throws IOException {
        //Todo: create this demo (For Paul)
        //Json File Demo
        //
        //* Json file shall store
        //* -Email of the instructor (use this as identifier)
        // * -Password
        //* -Class Name
        //*  +SheetID
        //

        //Counts for current # of students and current column of date.
        //Column 6 is the column for the counts (update counts from G2 and G3)
        //Start of Program

        //Todo: instructor side
        //Todo: student side
        // Build a new authorized API client service.


        Scanner kb = new Scanner(System.in);
        Sheets service = getSheetsService();

        //for testing purposes
        System.out.println("\nspreadsheetId: 1VZ63I-Wm-pPDM-MHNODscw9treysG-9JLUyZyAC7rj0");

        String spreadsheetId = getSpreadsheetId(kb, service);
        googleSheet mySheet = new googleSheet(spreadsheetId, service);
        //1VZ63I-Wm-pPDM-MHNODscw9treysG-9JLUyZyAC7rj0
        mySheet.generateKeyFor("sheet1");


        //Constraints
        int maxAnswerLength = 140;
        int minimumAnswerLength = 1;

        while(true) {
            System.out.print("Type your student id--> ");
            String studentId = kb.nextLine();
            System.out.print("Type today's key--> ");
            String key = kb.nextLine();
            String message = validateMessage(kb, minimumAnswerLength, maxAnswerLength);
            mySheet.validateStudent(studentId,"sheet1",key,message);
        }

    }
    public static String validateMessage(Scanner kb, int minimumAnswerLength, int maxAnswerLength) {
        String message = "Here";
        for(;;) {
            try {
                System.out.print("Type [Here] or answer today's question--> ");
                message = kb.nextLine();
                if (message.length() < minimumAnswerLength){
                    throw new EmptyAnswerException();
                }
                if (message.length() > maxAnswerLength){
                    throw new ExceededAnswerLengthException();
                }
                return message;
            } catch (EmptyAnswerException e) {
                System.out.println("Invalid Answer! Minimum character length is: " + minimumAnswerLength);
            } catch (ExceededAnswerLengthException e){
                System.out.println("Invalid Answer! Max character length exceeded: " + message.length() + "/" + maxAnswerLength);
            }
        }
    }
}


class googleSheet {
    private String sheetId;
    private ArrayList<String> sheetNames;
    private ArrayList<Integer> sheetAddresses;
    private Sheets service;

    public googleSheet(String sheetId, Sheets service) throws IOException {
        this.sheetId = sheetId;
        this.service = service;
        this.sheetNames     = new ArrayList<String>();
        this.sheetAddresses = new ArrayList<Integer>();
        for(int i = 0;;i++) {
            try {

                this.sheetAddresses.add(
                        Integer.parseInt(
                                service.spreadsheets()
                                        .get(this.sheetId)
                                        .execute()
                                        .getSheets()
                                        .get(i)
                                        .getProperties()
                                        .getSheetId()
                                        .toString()
                        )
                );
                this.sheetNames.add(
                        service.spreadsheets()
                                .get(this.sheetId)
                                .execute()
                                .getSheets()
                                .get(i)
                                .getProperties()
                                .getTitle()
                                .toLowerCase()
                );

            } catch (IndexOutOfBoundsException e ) {
                break;
            }
        }
        //this.studentCount = Integer.parseInt(cells.get(0).get(5).toString());
        //this.dateCount    = Integer.parseInt(cells.get(1).get(5).toString());
        //this.currentDate  = cells.get(0).get(dateCount).toString();

    }

    public void generateKeyFor(String sheetName) throws IOException {
        if(sheetDoesExist(sheetName)) {
            //get the grid of a given sheet
            List<List<Object>> grid = getGridOf(sheetName);
            //find student count
            int studentCount = Integer.parseInt(grid.get(0).get(5).toString());
            //find dateCount
            int dateCount = Integer.parseInt(grid.get(1).get(5).toString());
            //find lastDate
            String lastDatePosted = grid.get(0).get(dateCount).toString();
            //generate a new code
            String newCode = generateNewCode() + "";

            if (lastDatePosted.equals(getTimeStamp())) {
                enterValueInto(studentCount+1, dateCount, newCode, sheetName);
            } else {
                int newDateCount = dateCount+1;
                enterValueInto(0, newDateCount, getTimeStamp(), sheetName);

                enterValueInto(studentCount+1, newDateCount, newCode, sheetName);

                enterValueInto(1,5, newDateCount+"" , sheetName);
            }
        } else {
            System.out.println("Could not generate key");
        }

    }

    public void validateStudent(String studentId, String sheetName, String key, String message) throws IOException{
        if(sheetDoesExist(sheetName)) {
            List<List<Object>> grid = getGridOf(sheetName);
            int studentRowIndex = findStudentRow(studentId, grid);
            if(keyIsValid(key, grid) && studentRowIndex != -1) {
                int dateCount = Integer.parseInt(grid.get(1).get(5).toString());
                enterValueInto(studentRowIndex, dateCount, message, sheetName);
            } else {
                System.out.println("Either your key was invalid");
                System.out.println("\nor your id not in the section");
            }
        } else {
            System.out.println("The section you typed does not exist");
        }

    }

    public ArrayList<Integer> getsheetAddresses() {
        return sheetAddresses;
    }

    public ArrayList<String> getsheetNames() {
        return sheetNames;
    }

    private void enterValueInto(int row, int col, String value, String sheetName) throws IOException {

        int sheetAddress = this.sheetAddresses.get(findIndexOf(sheetName));

        List<Request> requests = new ArrayList<>();
        List<CellData> values = new ArrayList<>();

        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(value)));

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(sheetAddress)
                                .setRowIndex(row)     // set the row to row 0
                                .setColumnIndex(col)) // set the new column 6 to value 5/28/2018 at row 0
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        this.service.spreadsheets().batchUpdate(this.sheetId, batchUpdateRequest).execute();

    }

    private int findIndexOf(String sheetName) {
        for(int i = 0; i < sheetNames.size(); i++) {
            if(sheetName.toLowerCase().equals(this.sheetNames.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private int generateNewCode() {
        Random rand = new Random();
        int randomnum = rand.nextInt(9998) + 1;
        System.out.println("today's passcode is:");
        System.out.print(randomnum + "\n");
        return randomnum;
    }

    private int findStudentRow(String studentId, List<List<Object>> grid) throws IOException {
        int studentCount = Integer.parseInt(grid.get(0).get(5).toString());

        for(int index = 1; index < studentCount+1; index++) {
            if (studentId.trim().equals(grid.get(index).get(2).toString().trim())) {
                System.out.println("Found student row at " + index);
                return index;
            }
        }
        return -1;

    }

    private boolean sheetDoesExist(String sheetName) {
        for(int i = 0; i < sheetNames.size(); i++) {
            if(sheetName.toLowerCase().equals(this.sheetNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean keyIsValid(String key, List<List<Object>> grid) throws IOException {
        int studentCount = Integer.parseInt(grid.get(0).get(5).toString());
        int dateCount = Integer.parseInt(grid.get(1).get(5).toString());
        if(key.equals(grid.get(studentCount+1).get(dateCount))) {
            System.out.println("The key was validated successfully");
            return true;
        } else {
            System.out.println("The key was not validated successfully");
            return false;
        }

    }

    private  String getTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date currentDay = new Date();
        return dateFormat.format(currentDay);
    }

    private List<List<Object>> getGridOf(String sheetName) throws IOException {
        if(sheetDoesExist(sheetName)) {
            Sheets.Spreadsheets.Values.Get request =
                    service.spreadsheets()
                            .values()
                            .get(this.sheetId, sheetName);
            ValueRange response = request.execute();
            return response.getValues();
        } else {
            return null;
        }
    }





}
