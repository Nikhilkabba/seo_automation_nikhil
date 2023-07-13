package testcases.seo1;

import acko.utilities.JsonUtility;
import acko.utilities.PropertiesFileUtility;
import ackoSEOService.CommonUtils;
import ackoSEOService.web.pages.MyAccountPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testcases.BaseClass;

import java.util.Date;
import java.util.HashMap;
import acko.utilities.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class TS_06_electronicsseopage extends BaseClass {
    HashMap<String,String> config=null;
    static JSONObject jsonObject;
    public static String jsonFilePath = System.getProperty("user.dir") + "/dataParam.json";
    String loggedInNumber;


    //pageObjects
    CommonUtils commonUtils=new CommonUtils();
    MyAccountPage myAccountPageObject=new MyAccountPage();

    @BeforeClass
    public void classPrerequisite(ITestContext context)
    {
        try{
            config= PropertiesFileUtility.getConfigData();
            jsonObject = JsonUtility.getJsonPayload(jsonFilePath);
            JSONObject genericData= (JSONObject) jsonObject.get("genericData");
            context.setAttribute("WebDriver",driver);
            initilization(config.get("baseURL"));
            //this.loginUsingCookie();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loginUsingCookie()
    {
        Date dt = new Date();
        Cookie ck = new Cookie("user_id","7LUF0IhcgAF5Zi2HY7LosQ:1670322874263:a7546ce1cc1cf8bf117fe2b771948aa15498651b","www.ackodev.com","/",new Date(dt.getTime() + (1000 * 60 * 60 * 24)),true);
        driver.getWebDriver().manage().addCookie(ck);
        driver.refresh();
    }

    @Test
    public void TC_01_Verify_electronics_nodata(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.electronicsnodata((JSONArray) jsonObject.get("electronicsLinks")),"Search bar functionality is not working in case on no data on electronics page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_electronics_data(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.electronicsdata(),"Search bar functionality is not working in case on no data on electronics page");
            Assert.assertTrue(myAccountPageObject.electronicsdata2(),"Pagination on searched results on electronics page is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_electronics_data_notpresent(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.electronicsdatanotpresent(),"Search bar functionality is not working in case no data is present on electronics page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_04_Verify_electronics_redirections(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.electronicsredirections((JSONArray) jsonObject.get("electronicsLinks")),"Redirections on Electronics page is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_05_Verify_electronics_before_purchase(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.beforepurchase(),"Redirection in Before Purchase is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_06_Verify_electronics_after_purchase(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.afterpurchase(),"Redirection in After Purchase is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_electronics_raise_repair(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.raiserepair(),"Redirection in Raise Repair is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_08_Verify_electronics_modes_of(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.modesof(),"Redirection in Modes of Settlement is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_electronics_track_repair(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.trackrepair(),"Redirection in Tracking Repair & Payout Status is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }


}
