package ruTherePackage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheets {
    private String sheetId;
    private ArrayList<String> sheetNames;
    private ArrayList<Integer> sheetAddresses;
    private Sheets service;
    private static final double DISTANCE_TOLERANCE = 100; // Feet Range of geolocation from professor to student
    /**
     * The initializer method that takes a sheet id and Service from google
     * to initialize all of the parameters necessary for future calls
     * 
     * @param sheetId
     * @param service API by google
     * @throws IOException
     */
    public  GoogleSheets(String sheetId, Sheets service) throws IOException {
        this.sheetId = sheetId;
        this.service = service;
        this.sheetNames     = new ArrayList<>();
        this.sheetAddresses = new ArrayList<>();
        List<Sheet> info = this.service.spreadsheets().get(this.sheetId).execute().getSheets();
        for(int i = 0;;i++) {
            try {

                this.sheetAddresses.add(
                        Integer.parseInt(info.get(i)
                                .getProperties()
                                .getSheetId()
                                .toString()
                        )
                );
                this.sheetNames.add(info.get(i)
                        .getProperties()
                        .getTitle()
                        .toLowerCase()
                );

            } catch (IndexOutOfBoundsException e ) {
                break;
            }
        }
    }
    /**
     * Function that takes a sheetName and generates 
     * at a current date
     * 
     * @param sheetName
     * @throws IOException
     */
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
    
    /**
     * Writes the Professor's latitude and longitude coordinates at a defined location on their Google Sheet.
     * @param sheetName
     * @param lat Professor's latitude coordinate.
     * @param lng Professor's latitude coordinate.
     * @throws IOException
     */
    public void generateCoordFor(String sheetName, String lat, String lng) throws IOException {
        if(sheetDoesExist(sheetName)) {
            enterValueInto(2, 5, lat, sheetName); // Write the latitude at F3
            enterValueInto(3, 5, lng, sheetName); // Write the longitude at F4
        } else {
            System.out.println("Could not generate key");
        }

    }
    /**
     * Function that validates a student at a given section
     * 
     * @param studentId
     * @param sheetName
     * @param key
     * @param message
     * @return
     * @throws IOException
     */
    public  boolean validateStudent(String studentId, String sheetName, String key, String message) throws IOException{
        if(sheetDoesExist(sheetName)) {
            List<List<Object>> grid = getGridOf(sheetName);
            int studentRowIndex = findStudentRow(studentId, grid);
            if(keyIsValid(key, grid) && studentRowIndex != -1) {
                int dateCount = Integer.parseInt(grid.get(1).get(5).toString());
                enterValueInto(studentRowIndex, dateCount, message, sheetName);
                return true;
            } else {
            	return false;
            }
        } else {
            System.out.println("The section you typed does not exist");
            return false;
        }
    }
    /**
     * Validates the student's geo location in the following fashion:
     * Returns true if the user is within accepted range (DISTANCE_TOLERANCE) of the professor's geolocation.
     * Returns false if not within range of the professor, given a non-existing sheetName, or fails other cases.
     * 
     * @param sheetName the name of the professor's sheet, may be provided to the student.
     * @param lat is the Student's Latitude. Is used to compare the distance from the professor.
     * @param lng is the Student's Longitude. Is used to compare the distance from the professor.
     * @return true or false depending if the user passes the validation.
     * @throws IOException
     */
    public boolean validateStudentGeo(String sheetName, double lat, double lng) throws IOException{
    	 if(sheetDoesExist(sheetName)) {
             List<List<Object>> grid = getGridOf(sheetName);
             double profLat = Double.parseDouble(grid.get(2).get(5).toString());
             double profLng = Double.parseDouble(grid.get(3).get(5).toString());
             double distance = Math.sqrt(Math.pow((profLat - lat), 2) + Math.pow((profLng - lng), 2));
             distance = 364320 * distance;
             if(distance <= DISTANCE_TOLERANCE) {
            	 System.out.println("You are within acceptable range: " + distance);
                 return true;
             } else {
            	 System.out.println("You are beyond the acceptable range from professor: " + distance + " ft");
            	 System.out.println("Acceptable range is: " + DISTANCE_TOLERANCE + " ft");
            	 System.out.println("You are off by: " + (distance - DISTANCE_TOLERANCE) + " ft");
             	return false;
             }
         } else {
             System.out.println("Sheet doesnt exist.");
         }
         return false;
    	
    }
    /**
     * Puts a String at a given coordinate at a given sheet name
     * 
     * @param row X coordinate
     * @param col Y coordinate
     * @param value
     * @param sheetName
     * @throws IOException
     */
    public void enterValueInto(int row, int col, String value, String sheetName) throws IOException {

        int sheetAddress = getSheetAddress(sheetName);

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
    /**
     * Tells if a given sheet exists
     * 
     * @param sheetName
     * @return boolean
     */
    private int getSheetAddress(String sheetName) {
        for(int i = 0; i < sheetNames.size(); i++) {
            if(sheetName.toLowerCase().equals(this.sheetNames.get(i))) {
                return this.sheetAddresses.get(i);
            }
        }
        return -1;
    }
    /**
     * @return a random 4 digit code
     */
    private int generateNewCode() {
        return (new Random().nextInt(9000) + 1000);
    }
    /**
     * Finds a student index in a given sheet
     * 
     * @param studentId
     * @param grid
     * @return
     */
    private int findStudentRow(String studentId, List<List<Object>> grid) {
        int studentCount = Integer.parseInt(grid.get(0).get(5).toString());

        for(int index = 1; index < studentCount+1; index++) {
            if (studentId.trim().equals(grid.get(index).get(2).toString().trim())) {
                System.out.println("Found student row at " + index);
                return index;
            }
        }
        return -1;
    }
    /**
     * Finds if a sheet exists at a given ID
     * 
     * @param sheetName
     * @return boolean
     */
    private boolean sheetDoesExist(String sheetName) {
        for(int i = 0; i < sheetNames.size(); i++) {
            if(sheetName.toLowerCase().equals(this.sheetNames.get(i))) {
                return true;
            }
        }
        return false;
    }
    /**
     * Makes sure the key in the sheet is the same
     * 
     * @param key
     * @param grid
     * @return
     */
    private boolean keyIsValid(String key, List<List<Object>> grid) {
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
    /**
     * @return String a current date in "MM/dd/yy" format
     */
    private String getTimeStamp() {
        return new SimpleDateFormat("MM/dd/yy").format(new Date());
    }
    /**
     * 
     * @param sheetName
     * @return a gird of a given sheet
     * @throws IOException
     */
    public List<List<Object>> getGridOf(String sheetName) throws IOException {
        Sheets.Spreadsheets.Values.Get request =
                service.spreadsheets()
                        .values()
                        .get(this.sheetId, sheetName);
        ValueRange response = request.execute();
        return response.getValues();
    }
    /**
     * @return names of all sheets at a given ID
     */
    public ArrayList<String> getSheetNames() {
        return sheetNames;
    }
}

