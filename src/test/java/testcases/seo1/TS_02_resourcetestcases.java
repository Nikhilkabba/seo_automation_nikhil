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
import org.testng.annotations.*;
import testcases.BaseClass;

import java.util.Date;
import java.util.HashMap;
import acko.utilities.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class TS_02_resourcetestcases extends BaseClass {

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
    public void TC_01_Verify_explore_more_redirections(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.verifyexploreredirection((JSONArray) jsonObject.get("explorelinks")),"Redirection not working in case of explore more");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_allresources_redirections(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.verifyexploreredirection2((JSONArray) jsonObject.get("explorelinks2")),"Redirection not working in case of explore more 2");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_subscribe_now_butttons(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.subscribenow((JSONArray) jsonObject.get("subscribenowlinks")),"Subscribe now is not functioning correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_04_Verify_subscribe_now_modal_validation(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.subscribenowmodal((JSONArray) jsonObject.get("subscribenowdata")),"Validation in Subscribe Modal is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_05_Verify_subscribe_now_modal_validation3(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.subscribenowmodal4((JSONArray) jsonObject.get("subscribenowdata")),"Subscribe now modal is not functioning correctly");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_06_Verify_subscribe_now_modal_validation2(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.subscribenowmodal3((JSONArray) jsonObject.get("subscribenowdata2")),"Subscribe now modal is not functioning correctly");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_subscribe_now_modal_flow(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.subscribenowmodal2(),"Happy flow in subscribe now modal is not working correctly ");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_08_Verify_resource_pagination(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.blogredirection((JSONArray) jsonObject.get("blogredirectiondata")),"Resource pagination is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_09_Verify_resourceui(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.resourceui((JSONArray) jsonObject.get("resourceheadingsdata")),"Resource ui can not be verified");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_10_Verify_guideui(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.guideui((JSONArray) jsonObject.get("guideheadingsdata")),"Guide ui can not be verified");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_11_Verify_articleui(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.articleui((JSONArray) jsonObject.get("articleheadingsdata")),"Article ui can not be verified");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_12_Verify_ebookui(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.ebookui((JSONArray) jsonObject.get("ebookheadingsdata")),"Ebook ui can not be verified");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_13_Verify_topics(ITestContext context)
    {
        try {

              Assert.assertTrue(myAccountPageObject.verifytopics((JSONArray) jsonObject.get("articletopicsdata")),"Topics table in Articles is not working as per expectations");
              Assert.assertTrue(myAccountPageObject.topicui((JSONArray) jsonObject.get("articletopicsuidata") ),"Topic UI is wrong");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_14_Verify_recent_Articles(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.verifyrecentarticles(),"Redirection in Recent Articles is not working correctly");
            Assert.assertTrue(myAccountPageObject.verifyrecentarticles2(),"Redirection in Recent Articles is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }
}
