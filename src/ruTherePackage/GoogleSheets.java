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

    public  void validateStudent(String studentId, String sheetName, String key, String message) throws IOException{
        if(sheetDoesExist(sheetName)) {
            List<List<Object>> grid = getGridOf(sheetName);
            int studentRowIndex = findStudentRow(studentId, grid);
            if(keyIsValid(key, grid) && studentRowIndex != -1) {
                int dateCount = Integer.parseInt(grid.get(1).get(5).toString());
                enterValueInto(studentRowIndex, dateCount, message, sheetName);
            } else {
            	throw new IOException("Opps");
            }
        } else {
            System.out.println("The section you typed does not exist");
        }

    }
    private void enterValueInto(int row, int col, String value, String sheetName) throws IOException {

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

    private int getSheetAddress(String sheetName) {
        for(int i = 0; i < sheetNames.size(); i++) {
            if(sheetName.toLowerCase().equals(this.sheetNames.get(i))) {
                return this.sheetAddresses.get(i);
            }
        }
        return -1;
    }

    private int generateNewCode() {
        return (new Random().nextInt(9000) + 1000);
    }

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

    private boolean sheetDoesExist(String sheetName) {
        for(int i = 0; i < sheetNames.size(); i++) {
            if(sheetName.toLowerCase().equals(this.sheetNames.get(i))) {
                return true;
            }
        }
        return false;
    }

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

    private String getTimeStamp() {
        return new SimpleDateFormat("MM/dd/yy").format(new Date());
    }

    private List<List<Object>> getGridOf(String sheetName) throws IOException {
        Sheets.Spreadsheets.Values.Get request =
                service.spreadsheets()
                        .values()
                        .get(this.sheetId, sheetName);
        ValueRange response = request.execute();
        return response.getValues();
    }
    
    public ArrayList<String> getSheetNames() {
        return sheetNames;
    }
}

