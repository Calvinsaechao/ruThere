package ruTherePackage;
// This is ruThere. This program is used as a demo to demonstrate an update to a fix cell location on a Google sheet
// Todos: Please be sure to update the location of your client_secret.json file & the GoogleSheets id before running your program.
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
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.Random;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ruThere {

	private static final String databaseLocation = 	//"/Users/kistanod/Projects/Java/ruThere/src/resources/database.json";
													"D:/home/site/wwwroot/webapps/ROOT/WEB-INF/classes/resources/database.json";
													//"C:/Users/Dennis/git/ruThere/src/resources/database.json";
													//"C:/Users/Danny/git/ruThere/src/resources/database.json";
													//"/Users/Calvin/IdeaProjects/ruThere/src/resources/database.json";
													//"X:\\Desktop\\Eclipse-Workspace\\ruThere\\src\\resources\\database.json";
	
	
	
	private static final String clientSecretLocation = //"/Users/kistanod/Projects/Java/ruThere/src/resources/client_secret.json";
													   "D:/home/site/wwwroot/webapps/ROOT/WEB-INF/classes/resources/client_secret.json";
													   //"C:/Users/Dennis/git/ruThere/src/resources/client_secret.json";
													   //"C:/Users/Danny/git/ruThere/src/resources/client_secret.json";
													   //"/Users/Calvin/IdeaProjects/ruThere/src/resources/client_secret.json";
													   //"X:\\Desktop\\Eclipse-Workspace\\ruThere\\src\\resources\\client_secret.json";
	
	
	
	
	private static final String credentialsLocation = //"/Users/kistanod/Projects/Java/ruThere/src/resources/credentials/sheets.googleapis.com-java-quickstart.json";
													  "D:/home/site/wwwroot/webapps/ROOT/WEB-INF/classes/resources/credentials";
													  //"C:/Users/Dennis/git/ruThere/src/resources/credentials/sheets.googleapis.com-java-quickstart.json";
													  //"C:/Users/Danny/git/ruThere/src/resources/credentials/sheets.googleapis.com-java-quickstart.json";
													  //"/Users/Calvin/IdeaProjects/ruThere/src/resources/credentials/sheets.googleapis.com-java-quickstart.json";
													  //"X:\\Desktop\\Eclipse-Workspace\\ruThere\\src\\resources\\credentials\\sheets.googleapis.com-java-quickstart.json";

    /** Application name. */
    private static final String APPLICATION_NAME = "ruThere";

    /** Directory to store user credentials for this application. */
    
    private static final java.io.File DATA_STORE_DIR = new java.io.File(credentialsLocation);

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
        InputStream in = new FileInputStream(clientSecretLocation);
        		
        		
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

    /**
     * Generates a new code at a given sheetName
     * @param email provided by professor
     * @param sheetName provided by professor
     * @return
     * @throws IOException
     */
    public static boolean generateCode(String email, String sheetName) throws IOException {	
    	JSONObject professorInfo = (JSONObject) getEmailInfo(email);
        String spreadsheetId = (String) professorInfo.get("sheetId");
        Sheets service = getSheetsService();
        GoogleSheets mySheet = new GoogleSheets(spreadsheetId, service);
        ArrayList<String> sheetNames = mySheet.getSheetNames();
        for(int i = 0; i < sheetNames.size(); i++) {
        	if (sheetName.equalsIgnoreCase(sheetNames.get(i))) {
        		mySheet.generateKeyFor(sheetName);
        		return true;
        	}
        }
        return false;
    }

    /**
     * grades all student at a given sheet name
     * @param email provided by professor
     * @param sheetName provided by professor
     * @return
     * @throws IOException
     */
    public static boolean gradeCourses(String email, String sheetName) throws IOException {
    	JSONObject professorInfo = (JSONObject) getEmailInfo(email);
    	String spreadsheetId = (String)professorInfo.get("sheetId");
    	Sheets service = getSheetsService();
        GoogleSheets mySheet = new GoogleSheets(spreadsheetId, service);
        
        ArrayList<String> sheetNames = mySheet.getSheetNames();
        for(int i = 0; i < sheetNames.size(); i++) {
        	if (sheetName.equalsIgnoreCase(sheetNames.get(i))) {
        		List<List<Object>> cells = mySheet.getGridOf(sheetName);
            	float maxScore = Float.parseFloat((String)cells.get(26).get(4));
            	int dateCount = Integer.parseInt((String)cells.get(1).get(5));
            	int studentCount = Integer.parseInt((String)cells.get(0).get(5));
            	int iAmHere = 0;
            	String score = "";
            	for(int row = 1; row <= studentCount; row++) { //each student
            		iAmHere = 0;
            		for(int col = 6; col <= dateCount; col++) { //counts attendance
            			try{
            				if(!((String)cells.get(row).get(col)).equals(""))
            					iAmHere++;
            				} 
            			catch(java.lang.IndexOutOfBoundsException e) {
            			}
            		}
            		score = Double.toString((Math.round((((iAmHere/(dateCount-5.0))*maxScore) * 100.0)))/100.0);
            		mySheet.enterValueInto(row, 4, score, sheetName);
            	}
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * Writes the Professor's latitude and longitude coordinates 
     * at a defined location on their Google Sheet.
     * 
     * @param email
     * @param sheetName
     * @param lat
     * @param lng
     * @return
     * @throws IOException
     */
    public static boolean generateCoord(String email, String sheetName, String lat, String lng) throws IOException {	
    	JSONObject professorInfo = (JSONObject) getEmailInfo(email);
        String spreadsheetId = (String) professorInfo.get("sheetId");
        Sheets service = getSheetsService();
        GoogleSheets mySheet = new GoogleSheets(spreadsheetId, service);
        ArrayList<String> sheetNames = mySheet.getSheetNames();
        for(int i = 0; i < sheetNames.size(); i++) {
        	if (sheetName.equalsIgnoreCase(sheetNames.get(i))) {
        		mySheet.generateCoordFor(sheetName, lat, lng);
        		return true;
        	}
        }
        return false;
    }

    /**
     * Сhecks if an email is valid or not
     * 
     * @param email
     * @return JSONObject info of a given email
     * @throws IOException
     */
    public static JSONObject validateEmail(String email) throws IOException {
    	JSONObject professorInfo = (JSONObject) getEmailInfo(email);
    	if(professorInfo != null) return professorInfo;
    	else return null;
    }


    /**
     * Makes sure that a given password is valid
     * 
     * @param professorInfo
     * @param password
     * @return
     * @throws IOException
     */
    public static boolean validatePassword(JSONObject professorInfo, String password) throws IOException {
    	System.out.println((String)professorInfo.get("password"));
    	if(((String)professorInfo.get("password")).equalsIgnoreCase(password)) {
    		return true;
    	}
    	else return false;
    }
    /**
     * Given all the credentials, submit student's attendance
     * 
     * @param email
     * @param sheetName
     * @param studentId
     * @param key
     * @param answer
     * @param lat
     * @param lng
     * @return
     * @throws IOException
     */
    public static boolean submitAttendance(String email, String sheetName, String studentId, String key, String answer, double lat, double lng) throws IOException {
    	JSONObject professorInfo = (JSONObject) getEmailInfo(email);
        //String spreadsheetId = "1VZ63I-Wm-pPDM-MHNODscw9treysG-9JLUyZyAC7rj0";
    	if(professorInfo == null) return false;
        String spreadsheetId = (String) professorInfo.get("sheetId");
        Sheets service = getSheetsService();
        GoogleSheets mySheet = new GoogleSheets(spreadsheetId, service);
        ArrayList<String> sheetNames = mySheet.getSheetNames();
        for(int i = 0; i < sheetNames.size(); i++) {
        	if (sheetName.equalsIgnoreCase(sheetNames.get(i)) && mySheet.validateStudentGeo(sheetName, lat, lng)) {
        		return mySheet.validateStudent(studentId, sheetName, key, answer);
        	}
        }
        return false;
    }


    /**
     * returns an info found at a given email
     * 
     * @param email
     * @return
     */
    public static Object getEmailInfo(String email){
        try {
            JSONObject database = (JSONObject) new JSONParser().parse(
                    new FileReader(
                            new File(databaseLocation)));
                            		
            Map emails = ((Map)database.get("emails"));
            Iterator<Map.Entry> iterator = emails.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair = iterator.next();
                if(pair.getKey().equals(email)) {
                    return pair.getValue();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}