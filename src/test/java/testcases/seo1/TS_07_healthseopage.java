package testcases.seo1;

import acko.utilities.JsonUtility;
import acko.utilities.PropertiesFileUtility;
import ackoSEOService.CommonUtils;
import ackoSEOService.web.pages.MyAccountPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import testcases.BaseClass;

import java.util.Date;
import java.util.HashMap;
import acko.utilities.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class TS_07_healthseopage extends BaseClass {

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
            //initilization(config.get("baseURL"));
            initilization("https://www.ackodev.com/health-insurance/");

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
    public void TC_01_Verify_health_insurance(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getyourfamily(),"Find the right plan in health-insurance is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_health_insurancegq(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getyourfamilygq(),"Find the right plan pop-up widget in health-insurance is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_health_insurance_article(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.insureyourfamily(),"Find the right plan in health-insurance is not working correctly on article pages");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_04_Verify_gmc(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.scheduledemo(),"Schedule a demo button in gmc is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_05_Verify_gmcgq(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.scheduledemogq(),"Schedule a demo button in pop-up widget in gmc is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_06_Verify_gmc_articles(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.scheduledemoarticles(),"Schedule a demo button in gmc is not working correctly on article pages");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_arogya_1(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.arogya1(),"Validation is not working on arogya-sanjeevani-health-insurance");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_08_Verify_arogya_2(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.arogya2(),"Validation is not working on arogya-sanjeevani-health-insurance");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_arogya_3(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.arogya3(),"Insure now button is not working correctly on arogya-sanjeevani-health-insurance");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_10_Verify_arogya_4(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.arogya1gq(),"Validation is not working on arogya-sanjeevani-health-insurance in pop-up widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_11_Verify_arogya5(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.arogya2gq(),"Validation is not working on arogya-sanjeevani-health-insurance in pop-up widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    //check
    @Test
    public void TC_12_Verify_arogya6(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.arogya3gq(),"Insure now button is not working correctly on arogya-sanjeevani-health-insurance in pop-up widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }


    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }
}
