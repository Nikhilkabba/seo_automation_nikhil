package ackoSEOService;

import acko.utilities.ConnectionFactory;
import acko.utilities.PropertiesFileUtility;
import ackoSEOService.web.pages.HomePage;
import ackoSEOService.web.pages.LoginPage;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import testcases.BaseClass;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class CommonUtils extends BaseClass {

    public static HashMap<String,String> config=PropertiesFileUtility.getConfigData();

    //create a Connection Object for all used dbs

    Connection masterdbConnection=null;
    Connection proposaldbConnection=null;
    Connection autoClaimsdbConnection=null;



    public static String getValueForKey(String jsonResponse, String key) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonResponse);
        String value = "";
        try {
            if (!key.contains(".")) {
                value = String.valueOf(json.get(key));
            } else {
                String outerkey = key.split("\\.")[0];
                String innerkey = key.split("\\.")[1];
                JSONObject j2 = (JSONObject) json.get(outerkey);
                value = j2.get(innerkey).toString();
            }
        } catch (Exception e) {
            return value = "";
        }

        if (!Objects.equals(value, "null")) {
            return value;
        } else {
            return value = "";
        }
    }


    public static String modifyPayloadWithKey(String payload, String key, String value) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);
        json.put(key, value);
        return json.toString();
    }

    public static String getUniqueString() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("ddMMyyhhmmss");
        String datetime = ft.format(date);
        return datetime;
    }

    public static String getFromDateForProcessFile() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        String datetime = ft.format(date);
        return datetime;

    }

    public static String getFromDateForPaymentResponseFile() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String datetime = ft.format(date);
        return datetime;

    }

    public static String getToDateForProcessFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        String datetime = ft.format(date);
        return datetime;
    }

    public static String getValueOfSeconds() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("s");
        String datetime = ft.format(date);
        return datetime;
    }

    public static JSONObject convertStringToJson(String payload) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);
        return json;
    }

    public static JSONObject modifyJsonWithKey(JSONObject payload, String key, String value) throws ParseException {
        payload.put(key, value);
        return payload;
    }

    public static JSONObject modifyJsonWithKey(JSONObject payload, String key, Object value) throws ParseException {
        payload.put(key, value);
        return payload;
    }

    public static String encodeBase64Encoding(String string) {
        byte[] encoded = Base64.encodeBase64(string.getBytes());
        return new String(encoded);
    }

    public static String decodeBase64Encoding(String string) {
        //decoding byte array into base64
        byte[] decoded = Base64.decodeBase64(string);
        return new String(decoded);
    }

    public boolean saveResponseToHTMLFIle(String fileName, String dataString) {

        boolean status = false;
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/" + fileName);
            myWriter.write(dataString);
            myWriter.close();
            status = true;
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return status;
    }

    // type: [ackomunication, analytics]
    public void updatePropertyFile(String type) {

        try {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("commonConfig.properties"));
            Configuration config = builder.getConfiguration();

            config.setProperty("dbIP", config.getProperty(type + "DbIP"));
            config.setProperty("dbPort", Integer.valueOf((String) config.getProperty(type + "DbPort")));
            config.setProperty("dbName", config.getProperty(type + "DbName"));
            config.setProperty("dbUserName", config.getProperty(type + "DbUserName"));
            config.setProperty("dbPassword", config.getProperty(type + "DbPassword"));

            builder.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String fetchOTP(String mobileNumber, String type) throws Exception {

        String sql = null;
        String otpValue = "";
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
           //updatePropertyFile(type);

            ConnectionFactory connectionFactory = new ConnectionFactory();

            if (type.equals("ackomunication")) {
                sql = "SELECT template_context_data->>'otp' FROM sms_report " +
                        "WHERE template_name ='send_otp_default' AND recipient='" + mobileNumber +
                        "' AND created_on > NOW()- INTERVAL '300 second' ORDER BY id DESC LIMIT 1";
            } else if (type.equals("analytics")) {
                sql = "SELECT edata->>'otp' FROM r2d2_event WHERE edata->>'gupshup_phone'='" + mobileNumber + "' " +
                        "AND ekind='otp_generated' AND created_on > NOW()- INTERVAL '300 second' "
                        + "ORDER BY id DESC LIMIT 1";
            }

            con = connectionFactory.getConnection();
            preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                otpValue = resultSet.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception ignored) {
            }
        }

        return otpValue;
    }


    public boolean loginIntoMyAccount(String phoneNumber) {
        HomePage homePageObject = new HomePage();
        LoginPage loginPageObject = new LoginPage();
        boolean isDone = false;
        driver.waitForTime(5);
        try {
            //check whether acko home page is loaded or not
            if (!driver.getWebDriver().getTitle().contains("ACKO | Insurance made easy"))
                return isDone;
            //click on login button
            driver.waitForTime(5);
            if (!homePageObject.clickOnLogin())
            return isDone;

            driver.waitForTime(5);
            //Enter phone number
            if (!loginPageObject.enterPhoneNumberAndGetOTP(phoneNumber))
            return isDone;
            //Enter OTP
            driver.waitForTime(5);
            if (!loginPageObject.enterOTP(this.fetchOTP(phoneNumber, "ackomunication")))
                return isDone;
            isDone = true;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

        return isDone;

    }

    public boolean logOutFromMyAccount() {

        Boolean isDone=false;

        try{
            driver.deleteAllCookies();
            driver.refresh();
            driver.waitForTime(10);
            isDone=true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    return isDone;

    }

    public void bikeRenewalFlow(String policyNumber)
    {
        try{
            URL url=new URL("https://proposal-builder-qa.internal.ackodev.com/auto/proposalbuilder/api/v1/renewals/proposal/create/?policy_number="+policyNumber);
            HttpsURLConnection conn= (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(conn.getResponseCode());

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }




    public Connection getConnection(String dbName) {

        String url = null;
        String user=null;
        String password=null;
        Connection connection=null;

        if(dbName.equalsIgnoreCase("MASTERDB"))
        {
            url = "jdbc:" + (String)this.config.get("masterdbType") + "://" + (String)this.config.get("masterdbIP") + ":" + (String)this.config.get("masterdbPort") + "/" + (String)this.config.get("masterdbName");
            user = (String)this.config.get("masterdbUserName");
            password = (String)this.config.get("masterdbPassword");
        }
        else if(dbName.equalsIgnoreCase("PROPOSALSDB"))
        {
            url = "jdbc:" + (String)this.config.get("proposalsdbType") + "://" + (String)this.config.get("proposalsdbIP") + ":" + (String)this.config.get("proposalsdbPort") + "/" + (String)this.config.get("proposalsdbName");
            user = (String)this.config.get("proposalsdbUserName");
            password = (String)this.config.get("proposalsdbPassword");
        }
        else if(dbName.equalsIgnoreCase("AUTOCLAIMSDB"))
        {
            url = "jdbc:" + (String)this.config.get("autoClaimsdbType") + "://" + (String)this.config.get("autoClaimsdbIP") + ":" + (String)this.config.get("autoClaimsdbPort") + "/" + (String)this.config.get("autoClaimsdbName");
            user = (String)this.config.get("autoClaimsdbUserName");
            password = (String)this.config.get("autoClaimsdbPassword");
        }

        try {
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException var6) {
            var6.printStackTrace();
            return connection;
        }
    }



    public String getTime(int changeInDays,int changeInYears) {

        //Find the current date to update it according to change in date
        Date currentDate =new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, changeInDays);
        c.add(Calendar.YEAR,changeInYears);
        return c.getTime().toInstant().toString();

    }


    public void executeQuery(JSONObject updationData)  {

        try{
            Statement stmt = null;
            String dbName=updationData.get("dbName").toString();
            String journey=updationData.get("journey").toString();
            Integer changeInDays=Integer.parseInt(updationData.get("changeInDays").toString());
            String q1=null,q2=null,q3=null,q4=null;


            if(dbName.equalsIgnoreCase("MASTERDB") && journey.equalsIgnoreCase("CAR") )
            {
                    String policyNumber=updationData.get("policyNumber").toString();
                    q1="UPDATE ackore_policy SET data = data || '{\"car_base_cover__policy_start_date\":\"" + this.getTime(changeInDays,-1)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    q2="UPDATE ackore_policy SET data = data || '{\"car_base_cover__policy_end_date\":\"" + this.getTime(changeInDays,0)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    q3="UPDATE ackore_policy SET data = data || '{\"policy_start_date\":\"" + this.getTime(changeInDays,-1)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    q4="UPDATE ackore_policy SET data = data || '{\"policy_end_date\":\"" + this.getTime(changeInDays,0)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    if(masterdbConnection==null)
                        masterdbConnection=this.getConnection("masterdb");
                    stmt=masterdbConnection.createStatement();
                    stmt.executeUpdate(q1);
                    stmt.executeUpdate(q2);
                    stmt.executeUpdate(q3);
                    stmt.executeUpdate(q4);

            }
            else if(dbName.equalsIgnoreCase("MASTERDB") && journey.equalsIgnoreCase("BIKE"))
            {



                    String policyNumber=updationData.get("policyNumber").toString();
                    q1="UPDATE ackore_policy SET data = data || '{\"two_wheeler_base_cover__policy_start_date\":\"" + this.getTime(changeInDays,-1)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    q2="UPDATE ackore_policy SET data = data || '{\"two_wheeler_base_cover__policy_end_date\":\"" + this.getTime(changeInDays,0)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    q3="UPDATE ackore_policy SET data = data || '{\"policy_start_date\":\"" + this.getTime(changeInDays,-1)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    q4="UPDATE ackore_policy SET data = data || '{\"policy_end_date\":\"" + this.getTime(changeInDays,0)  + "\"}' WHERE policy_number ='" + policyNumber +"'";
                    if(masterdbConnection==null)
                        masterdbConnection=this.getConnection("masterdb");
                    stmt=masterdbConnection.createStatement();
                    stmt.executeUpdate(q1);
                    stmt.executeUpdate(q2);
                    stmt.executeUpdate(q3);
                    stmt.executeUpdate(q4);

                    if(updationData.containsKey("isRenewalJourney"))
                        this.bikeRenewalFlow(policyNumber);



            }

           else if(dbName.equalsIgnoreCase("PROPOSALSDB") )
            {

                    String proposalParentId=updationData.get("proposalParentId").toString();
                    q1="update auto_proposal set created_on='"+this.getTime(changeInDays,0) +"' , updated_on='"+this.getTime(changeInDays,0)+"' where id='"+proposalParentId+"'";
                    if(proposaldbConnection==null)
                        proposaldbConnection=this.getConnection("PROPOSALSDB");

                    stmt=proposaldbConnection.createStatement();
                    stmt.executeUpdate(q1);
            }
           else if(dbName.equalsIgnoreCase("AUTOCLAIMSDB"))
            {
                String policyNumber=updationData.get("policyNumber").toString();
                q1="update fnol_form_data set created_on='"+this.getTime(changeInDays,0) +"' where policy_number='"+policyNumber+"'";
                if(autoClaimsdbConnection==null)
                    autoClaimsdbConnection=this.getConnection("AUTOCLAIMSDB");

                stmt=autoClaimsdbConnection.createStatement();
                stmt.executeUpdate(q1);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }




    }

    public void preProceesing(JSONArray policiesTestData)  {
        JSONObject data;

        try{
            for(int i=0;i<policiesTestData.size();i++) {
                data = (JSONObject) policiesTestData.get(i);
                if (data.get("dbUpdationRequired").equals("true")) {
                    this.executeQuery((JSONObject) data.get("updationDetails"));
                }
            }
            //this.closeConnection();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void closeConnection() {

        try{
            masterdbConnection.close();
            proposaldbConnection.close();
            autoClaimsdbConnection.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }



    }
}
