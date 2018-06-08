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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
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

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        //Scanner
        Scanner kb = new Scanner(System.in);
        //Counts for current # of students and current column of date.
        //Column 6 is the column for the counts (update counts from G2 and G3)
        int dateCol = 0; //count for date column
        int stuCount = 0; //count for amount of students

        //Todo: create this demo (For Paul)
        //Json File Demo
        /**Json file shall store
         * -Email of the instructor (use this as identifier)
         * -Password
         * -Class Name
         *  +SheetID
         */

        //Start of Program
        while (true) {
            Timestamp date = getTimeStamp();
            System.out.println("Date: " + date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
            // Build a new authorized API client service.
            Sheets service = getSheetsService();

            System.out.print("Enter your spreadsheet ID--> ");
            String spreadsheetId = kb.nextLine();
            //String spreadsheetId = "1VZ63I-Wm-pPDM-MHNODscw9treysG-9JLUyZyAC7rj0";
            //gets the spreadsheet data

            String range = "F1:F2";
            String valueRenderOption = "FORMATTED_VALUE";
            Sheets.Spreadsheets.Values.Get request =
                    service.spreadsheets().values().get(spreadsheetId, range);
            ValueRange response = request.execute();
            System.out.println(response.getValues().get(1)); // still needs work Calvin is working on it


            // Create requests object
            List<Request> requests = new ArrayList<>();

            // Create values object
            List<CellData> values = new ArrayList<>();

            // Add string 5/28/2018 value
            values.add(new CellData()
                    .setUserEnteredValue(new ExtendedValue()
                            .setStringValue((date.getDay() + "/" + date.getMonth() + "/" + date.getYear()))));

            // Prepare request with proper row and column and its value
            requests.add(new Request()
                    .setUpdateCells(new UpdateCellsRequest()
                            .setStart(new GridCoordinate()
                                    .setSheetId(0)
                                    .setRowIndex(0)     // set the row to row 0
                                    .setColumnIndex(dateCol)) // set the new column 6 to value 5/28/2018 at row 0
                            .setRows(Arrays.asList(
                                    new RowData().setValues(values)))
                            .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                    .execute();

            List<CellData> valuesNew = new ArrayList<>();
            // Add string 5/28/2018 value
            valuesNew.add(new CellData()
                    .setUserEnteredValue(new ExtendedValue()
                            .setStringValue(("Yes"))));

            // Prepare request with proper row and column and its value
            requests.add(new Request()
                    .setUpdateCells(new UpdateCellsRequest()
                            .setStart(new GridCoordinate()
                                    .setSheetId(0)
                                    .setRowIndex(1)     // set the row to row 1
                                    .setColumnIndex(dateCol)) // set the new column 6 to value "yes" at row 1
                            .setRows(Arrays.asList(
                                    new RowData().setValues(valuesNew)))
                            .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
            BatchUpdateSpreadsheetRequest batchUpdateRequestNew = new BatchUpdateSpreadsheetRequest()
                    .setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequestNew)
                    .execute();
            getCode();
        }
    }

    public static Timestamp getTimeStamp(){
        Timestamp date = new Timestamp(System.currentTimeMillis());
        date.setYear(date.getYear() + 1900);
        date.setMonth(date.getMonth() + 1);
        System.out.println(date.getYear());
        return date;
        /**
         * @return Timestamp object of current date and formats the year to current year
         */
    }

    public static int retrieveIntData(String spreadsheetId, String range){
        return 1;
    }
    /**
     * @return int data from spreadsheet
     */

    public static int getStuCount(){
        return 1;
    }
    /**
     * @return current student count
     */

    public static int getDateCol(){
        return 1;
    }
    /**
     * @return current column in use for the date
     */

    public static int getCode() {
        int randomnum = (int)(Math.random()*9000)+1000;
        System.out.println("today's passcode is:");
        System.out.print(randomnum);
        return randomnum;

    }
    /**
     * @return the passcode for the day
     */
}
