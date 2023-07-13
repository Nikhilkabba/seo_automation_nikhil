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
public class TS_05_carseopage extends BaseClass {
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
    public void TC_01_Verify_car_insurance_noData(ITestContext context)
    {
        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.carWidgetNoData("main"),"Car number field should not be empty");

        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_02_Verify_car_insurance_wrongData(ITestContext context)
    {
        try{
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.carWidgetWrongData("main"),"Data Validation Failed");
        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_03_verify_car_insurance_bikeInCarJourney(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.bikeNumberInCarJourney("main","TN73K1920"),"Widget not displayed");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.bikeNumberInCarJourney("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("buyBikeInsurance"),"Login redirection failed");


        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_04_verify_car_insurance_getQuote(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.carGetQuoteButton("main"),"get a Quote redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_05_verify_car_insurance_AlreadyInsuredWidget_loggedOut(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","HR26CP7005"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_06_verify_car_insurance_freshCarJourney(ITestContext context)
    {
        try
        {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.freshCarRedirection("main","HR26CP1234"),"New car journey failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }


    @Test
    public void TC_07_verify_car_insurance_bikeNumberInCarJourney_loggedOut(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","UP85BD0681"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }




//    floater widgets testcases


    @Test
    public void TC_08_verify_car_insurance_isFloaterWidgetPresent(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_09_Verify_widget_car_insurance_noData(ITestContext context)
    {
        try{

            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.carWidgetNoData("widget"),"Car number field should not be empty");

        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_10_verify_widget_car_insurance_wrongData(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(), "Floater widget not found");
            Assert.assertTrue(myAccountPageObject.carWidgetWrongData("widget"), "Wrong data error not displayed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_11_verify_widget_car_insurance_bikeInCarJourney(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.bikeNumberInCarJourney("widget","TN73K1920"),"Widget not displayed");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.bikeNumberInCarJourney("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("buyBikeInsurance"),"Login redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_12_verify_widget_car_insurance_getaQuote(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.carGetQuoteButton("widget"),"Redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_13_verify_widget_car_insurance_AlreadyInsuredWidget_loggedOut(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget","HR26CP7005"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }



    @Test
    public void TC_14_verify_widget_car_insurance_freshCarJourney(ITestContext context)
    {
        try
        {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.freshCarRedirection("widget","HR26CP1234"),"New car journey failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }


    @Test
    public void TC_15_verify_widget_car_insurance_bikeNumberInCarJourney_loggedOut(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget","UP85BD0681"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }


////    Article page car widget

    @Test
    public void TC_16_Verify_articlePage_car_insurance_noData(ITestContext context)
    {
        try{
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.carWidgetNoData("main"),"Car number field should not be empty");

        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }



    @Test
    public void TC_17_Verify_articlePage_car_insurance_wrongData(ITestContext context)
    {
        try{
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.carWidgetWrongData("main"),"Data Validation Failed");
        }
        catch(Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_18_verify_articlePage_car_insurance_bikeInCarJourney(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.bikeNumberInCarJourney("main","TN73K1920"),"Widget not displayed");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.bikeNumberInCarJourney("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("buyBikeInsurance"),"Login redirection failed");


        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_19_verify_articlePage_car_insurance_getQuote(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.carGetQuoteButton("main"),"get a Quote redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_20_verify_articlePage_car_insurance_AlreadyInsuredWidget_loggedOut(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","HR26CP7005"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }




    @Test
    public void TC_21_verify_articlePage_car_insurance_freshCarJourney(ITestContext context)
    {
        try
        {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.freshCarRedirection("main","HR26CP1234"),"New car journey failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

//    logged in testcases
    @Test
    public void TC_22_verify_articlePage_car_insurance_bikeNumberInCarJourney_sameAccount(ITestContext context)
    {
        try {
            loginUsingCookie();
            driver.waitForTime(5);
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","HR26CK2404"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("ViewPolicy"),"view policy redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_23_verify_articlePage_car_insurance_bikeNumberInCarJourney_differentAccount(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","UP85BD0681"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_24_verify_articlePage_car_insurance_bikeNumberInCarJourney_loggedOut(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","UP85BD0681"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_25_verify_car_insurance_bikeNumberInCarJourney_sameAccount(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","HR26CK2404"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("ViewPolicy"),"view policy redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }


    @Test
    public void TC_26_verify_car_insurance_AlreadyInsuredWidget_differentAccount(ITestContext context)
    {
        try {

            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","MH05CV7557"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");


        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_27_verify_car_insurance_AlreadyInsuredWidget_sameAccount(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","HR26CP7005"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("ViewPolicy"),"view policy redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_28_verify_car_insurance_bikeNumberInCarJourney_differentAccount(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","UP85BD0681"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_29_verify_widget_car_insurance_AlreadyInsuredWidget_differentAccount(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget","MH05CV7557"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_30_verify_widget_car_insurance_AlreadyInsuredWidget_sameAccount(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget","HR26CP7005"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("ViewPolicy"),"view policy redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_31_verify_widget_car_insurance_bikeNumberInCarJourney_sameAccount(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget","HR26CK2404"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("ViewPolicy"),"view policy redirection failed");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }

    @Test
    public void TC_32_verify_widget_car_insurance_bikeNumberInCarJourney_differentAccount(ITestContext context)
    {
        try {
            Assert.assertTrue(myAccountPageObject.getQuoteWidget(),"Floater widget not found");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget","UP85BD0681"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("widget"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("widget",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }



    @Test
    public void TC_33_verify_articlePage_car_insurance_AlreadyInsuredWidget_differentAccount(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","MH05CV7557"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_LoginToKnowMore(),"Login redirection failed");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }





    @Test
    public void TC_34_verify_articlePage_car_insurance_AlreadyInsuredWidget_sameAccount(ITestContext context)
    {
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/how-to-check-cars-engine-oil/");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main","HR26CP7005"),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_registrationButton("main"),"widget registration number button not working");
            Assert.assertTrue(myAccountPageObject.alreadyInsured("main",""),"widget not found");
            Assert.assertTrue(myAccountPageObject.widgetAssertion_viewPolicy("ViewPolicy"),"view policy redirection failed");
            Assert.assertTrue(myAccountPageObject.verifylogout(),"log out failed");


        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }


    @AfterClass
    public void cleanUp()
    {
        driver.closeBrowser();
    }

}
