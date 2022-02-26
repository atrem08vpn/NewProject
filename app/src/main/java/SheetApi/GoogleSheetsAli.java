
package SheetApi;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GoogleSheetsAli {
    
    private static Sheets sheets;
    private static final String APPLICATION_NAME = "JavaSheetTest";
    private static final String SHEET_ID = "1ICgfHDtWdWta_v1v08PeSpyayjoxubAHGn5pLRW-bWU"; 
    
    public GoogleSheetsAli(){
        try {
            sheets = getSheetsService();
        } catch (IOException | GeneralSecurityException ex) {
            Logger.getLogger(GoogleSheetsAli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private static Credential authorize() throws IOException, GeneralSecurityException{
        InputStream in = GoogleSheetsAli.class.getResourceAsStream("/creddential.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(), new InputStreamReader(in));
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(),
                clientSecrets, scopes).setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
                .authorize("user");

        return credential;
    }
    
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException{
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME).build();
    }
    
    
    public ValueRange values(String pageName, String rowColumn){
        ValueRange value = null;
        try {
            value = sheets.spreadsheets().values().get(SHEET_ID, pageName + "!" + rowColumn).execute();
        } catch (IOException ex) {
            Logger.getLogger(GoogleSheetsAli.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    public ValueRange values(String pageName){
        ValueRange value = null;
        try {
            value = sheets.spreadsheets().values().get(SHEET_ID, pageName).execute();
        } catch (IOException ex) {
            Logger.getLogger(GoogleSheetsAli.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    
    public void addValue(String pageName, String[] values){
        ValueRange editTable = new ValueRange().setValues(Arrays.asList(Arrays.asList((Object[]) values)));
        try {
            sheets.spreadsheets().values().append(SHEET_ID, pageName, editTable)
                    .setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS")
                    .setIncludeValuesInResponse(true).execute();
        } catch (IOException ex) {
            Logger.getLogger(GoogleSheetsAli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setValue(String pageName, String rowColumn, String value){
        ValueRange editTable = new ValueRange().setValues(Arrays.asList(Arrays.asList(value)));
        
        try {
            sheets.spreadsheets().values().update(SHEET_ID, pageName + "!" + rowColumn, editTable)
                    .setValueInputOption("USER_ENTERED").execute();
        } catch (IOException ex) {
            Logger.getLogger(GoogleSheetsAli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
