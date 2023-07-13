package testcases.seo1;

import acko.utilities.JsonUtility;
import acko.utilities.PropertiesFileUtility;
import ackoSEOService.CommonUtils;
import ackoSEOService.web.pages.MyAccountPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import testcases.BaseClass;

import java.util.HashMap;
import acko.utilities.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class TS_03_footerTestcases extends BaseClass {
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
            initilization("https://www.ackodev.com/car-insurance/");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    @Test
    public void TC_01_Verify_Product_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("ProductsFooterLinks"),"Products"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_carInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("CarInsuranceFooterLinks"),"CarInsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_bikeInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("BikeinsuranceFooterLinks"),"Bikeinsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_04_Verify_healthInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("HealthinsuranceFooterLinks"),"Healthinsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_05_Verify_ExploreCarInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("ExplorecarinsuranceFooterLinks"),"Explorecarinsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_06_Verify_ExploreBikeInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("ExplorebikeinsuranceFooterLinks"),"Explorebikeinsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_07_Verify_ExploreHealthInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("ExplorehealthinsuranceFooterLinks"),"Explorehealthinsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_08_Verify_ExploreGroupHealthInsurance_Footer_Pages_Redirections(ITestContext context)
    {

        try{

            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterSection((JSONArray) jsonObject.get("ExploregrouphealthinsuranceFooterLinks"),"Exploregrouphealthinsurance"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_Company_Footer_Pages_Redirections(ITestContext context)
    {

        try{

            Assert.assertTrue(myAccountPageObject.verifyFooterSection2((JSONArray) jsonObject.get("CompanyFooterLinks"),"Company"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_10_Verify_Legal_Footer_Pages_Redirections(ITestContext context)
    {

        try{

            Assert.assertTrue(myAccountPageObject.verifyFooterSection2((JSONArray) jsonObject.get("LegalFooterLinks"),"Legal"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_11_Verify_Support_Footer_Pages_Redirections(ITestContext context)
    {

        try{
            Assert.assertTrue(myAccountPageObject.verifyFooterSection2((JSONArray) jsonObject.get("SupportFooterLinks"),"Support"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_12_Verify_ExploreMore_Footer_Pages_Redirections(ITestContext context)
    {

        try{

            Assert.assertTrue(myAccountPageObject.verifyFooterSection2((JSONArray) jsonObject.get("ExploremoreFooterLinks"),"Exploremore"),"Either link is missing from footer or pages is having wrong redirection links");
        }catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_13_Verify_socialMedia_Footer_Redirections(ITestContext context)
    {
        try
        {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.verifyFooterRedirectionSection2((JSONArray) jsonObject.get("socialsFooterLinks"),"socials"),"Either link is missing from footer or pages is having wrong redirection links");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_14_Verify_appleStoreDownload_Footer_Redirections(ITestContext context)
    {
        try
        {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.DownloadNowAppStore((JSONArray) jsonObject.get("DownloadNowFooterLinks"),"DownloadNow"),"Either link is missing from footer or pages is having wrong redirection links");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_15_verify_playStoreDownload_Footer_Redirection(ITestContext context)
    {
        try
        {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.DownloadNowPlayStore((JSONArray) jsonObject.get("DownloadNowFooterLinks"),"DownloadNow"),"Either link is missing from footer or pages is having wrong redirection links");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }


    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }


}
