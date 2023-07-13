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
public class TS_04_bikeseopage extends BaseClass {
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
    public void TC_01_Verify_Bike_Insurance_Page_nodata(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget(),"Bike widget validation is not working correctly in case of No data is given");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_Bike_Insurance_Page_wrongdata(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget2(),"Bike widget validation is not working correctly in case of wrong data is given");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_Bike_Insurance_Page_fresh_bike(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget4(),"Fresh bike flow is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_04_Verify_Bike_Insurance_Page_old_bike_lout(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget5(),"Bike Widget pop-up message in case of already insured bike is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_05_Verify_Bike_Insurance_Page_new_bike(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget6(),"New bike flow is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_06_Verify_Bike_Insurance_get_quote(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidgetgq(),"Bike widget validation is not working correctly in case of No data is given in pop-up bike widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_Bike_Insurance_Page_wrongdatagq(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget2gq(),"Bike widget validation is not working correctly in case of wrong data is given in pop-up bike widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_08_Verify_Bike_Insurance_Page_fresh_bikegq(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget4gq(),"Fresh bike flow is not working correctly in case of pop-up widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_Bike_Insurance_Page_old_bike_loutgq(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget5gq(),"Bike Widget pop-up message in case of already insured bike is not working correctly in pop-up widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_10_Verify_Bike_Insurance_Page_new_bikegq(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            //Assert.assertTrue(myAccountPageObject.bikewidgetgetquote(),"Redirection in Recent Articles is not working correctly");
            Assert.assertTrue(myAccountPageObject.bikewidget6gq(),"New bike flow is not working correctly in case of pop-up widget");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_11_Verify_Bike_Insurance_Page_nodata_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget(),"Bike widget validation is not working correctly in case of No data is given in bike article page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_12_Verify_Bike_Insurance_Page_wrongdata_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget2(),"Bike widget validation is not working correctly in case of wrong data is given in bike article page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_13_Verify_Bike_Insurance_Page_fresh_bike_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget4(),"Fresh bike flow is not working correctly in case of article page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_14_Verify_Bike_Insurance_Page_old_bike_lout_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget5(),"Bike Widget pop-up message in case of already insured bike is not working correctly on article page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_15_Verify_Bike_Insurance_Page_new_bike_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget6(),"New bike flow is not working correctly in case of bike article page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_16_Verify_Bike_Insurance_Page_old_bike_same_account(ITestContext context)
    {
        try {
            loginUsingCookie();
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget7(),"Bike widget pop-up message is not working correctly for already insured bike on same account in logged in state");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_17_Verify_Bike_Insurance_Page_old_bike_diff_account(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.bikewidget8(),"Bike widget pop-up message is not working correctly for already insured bike on diff account in logged in state");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_18_Verify_Bike_Insurance_Page_old_bike_same_accountgq(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.bikewidget7gq(),"Bike widget pop-up message is not working correctly for already insured bike on same account in logged in state get quote");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_19_Verify_Bike_Insurance_Page_old_bike_diff_accountgq(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            Assert.assertTrue(myAccountPageObject.bikewidget8gq(),"Bike widget pop-up message is not working correctly for already insured bike on diff account in logged in state get quote");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_20_Verify_Bike_Insurance_Page_old_bike_same_account_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget7(),"Bike widget pop-up message is not working correctly for already insured bike on same account in logged in state on article page");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_21_Verify_Bike_Insurance_Page_old_bike_diff_account_articles(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/tips-to-file-a-successful-bike-insurance-claim/");
            Assert.assertTrue(myAccountPageObject.bikewidget8(),"Bike widget pop-up message is not working correctly for already insured bike on diff account in logged in state on article page");
            Assert.assertTrue(myAccountPageObject.verifylogout(),"Not able to logout in bike flow");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @AfterClass
    public void cleanUp()
    {
        driver.closeBrowser();
    }

}
