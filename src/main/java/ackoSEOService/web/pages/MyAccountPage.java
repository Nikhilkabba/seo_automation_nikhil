package ackoSEOService.web.pages;

import acko.utilities.ObjectRepositoryLoader;
import acko.utilities.PropertiesFileUtility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import java.util.*;

public class MyAccountPage extends CommonPage {


    HashMap<String, HashMap<String, String>> myAccountPageElement = null;
    public HashMap<String, String> config = null;
    PropertiesFileUtility propertiesFileUtility = new PropertiesFileUtility();

    CommonPage commonPageObject = new CommonPage();


    public MyAccountPage() {
        try {
            config = PropertiesFileUtility.getConfigData();
            this.myAccountPageElement = new ObjectRepositoryLoader().getObjectRepository("MyAccountPage.xml");


        } catch (Exception e) {
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

    public boolean clickOnRenewNow() {
        System.out.println("click on Renew Now");
        boolean isDone = false;
        try {
            driver.click(myAccountPageElement.get("renewPolicy"));
            isDone = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDone;
    }

    public boolean clickOnBuyNow() {
        System.out.println(" click on buy now ");
        boolean isDone = false;
        try {
            driver.click(myAccountPageElement.get("buyNowButton"));
            isDone = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDone;
    }

    public boolean waitForPageLoad(Integer time) throws Exception {
        System.out.println("Inside: <waitForPageLoad> [" + time + "]");
        try {
            driver.waitForTime(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean verifyPolicyAndRedirect(HashMap<String, String> data) throws Exception {
        System.out.println("Inside: <verifyPolicyAndRedirect> [" + data + "]");
        try {
            // get values from data hashmap
            String planName = data.get("planName");
            String vehicleName = data.get("vehicleName");
            String registrationNumber = data.get("registrationNumber");
            String policyTag = data.get("policyTag");

            // initialize elements
            HashMap<String, String> planNameElement = new HashMap<>(myAccountPageElement.get("planName"));
            HashMap<String, String> vehicleNameElement = new HashMap<>(myAccountPageElement.get("vehicleName"));
            HashMap<String, String> registrationNumberElement = new HashMap<>(myAccountPageElement.get("registrationNumber"));
            HashMap<String, String> policyTagElement = new HashMap<>(myAccountPageElement.get("policyTag"));
            HashMap<String, String> policyDetailsCTAElement = new HashMap<>(myAccountPageElement.get("policyDetailsCTA"));

            // update elements
            planNameElement.put("XPATH",
                    planNameElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));
            vehicleNameElement.put("XPATH",
                    vehicleNameElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));
            registrationNumberElement.put("XPATH",
                    registrationNumberElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));
            policyTagElement.put("XPATH",
                    policyTagElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));
            policyDetailsCTAElement.put("XPATH",
                    policyDetailsCTAElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));

            // perform actions
            driver.scrollToElement(registrationNumberElement);
            driver.executeJavaScript(registrationNumberElement, "window.scrollBy(0,-100)");
            if (!driver.isElementDisplayed(planNameElement)) {
                return false;
            }

            // get text value from UI
            String vehicleNameUI = driver.getText(vehicleNameElement);
            String registrationNumberUI = driver.getText(registrationNumberElement);
            String policyTagUI = driver.getText(policyTagElement);

            // verify text from UI
            if (!vehicleName.equals(vehicleNameUI) ||
                    !registrationNumber.equals(registrationNumberUI) ||
                    !policyTag.equals(policyTagUI)) {
                return false;
            }

            // redirect to pdp
            driver.clickWhenReady(policyDetailsCTAElement);
            driver.waitForTime(5);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean verifyPolicySubcard(HashMap<String, String> data) throws Exception {
        System.out.println("Inside: <verifyPolicySubcardAndRedirect> [" + data + "]");
        try {
            // get values from data hashmap
            String registrationNumber = data.get("registrationNumber");
            String heading = data.get("heading");
            String ctaText = data.get("ctaText");
            String redirectURL = data.get("redirectURL");

            // initialize elements
            HashMap<String, String> registrationNumberElement = new HashMap<>(myAccountPageElement.get("registrationNumber"));
            HashMap<String, String> headingElement = new HashMap<>(myAccountPageElement.get("subcardHeading"));
            HashMap<String, String> ctaElement = new HashMap<>(myAccountPageElement.get("subcardCTA"));

            // update elements
            registrationNumberElement.put("XPATH",
                    registrationNumberElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));
            headingElement.put("XPATH",
                    headingElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));
            ctaElement.put("XPATH",
                    ctaElement.get("XPATH").replace("{{registrationNumber}}", registrationNumber));

            // perform actions
            driver.scrollToElement(registrationNumberElement);
            driver.executeJavaScript(registrationNumberElement, "window.scrollBy(0,-50)");

            // get text value from UI
            String registrationNumberUI = driver.getText(registrationNumberElement);
            String headingUI = driver.getText(headingElement);
            String ctaTextUI = driver.getText(ctaElement);

            // verify text from UI
            if (!registrationNumber.equals(registrationNumberUI) ||
                    !heading.equals(headingUI) ||
                    !ctaText.equals(ctaTextUI)) {
                return false;
            }

            // redirect to subcard page
            driver.clickWhenReady(ctaElement);
            driver.waitForTime(3);

            // verify url after redirection
            if (!driver.getWebDriver().getCurrentUrl().contains(redirectURL)) {
                return false;
            }
            while (!driver.getWebDriver().getCurrentUrl().contains("/myaccount")) {
                driver.goBack();
            }
            driver.waitForTime(2);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean verifyPolicyCard(JSONObject policyType) {
        boolean isDone = false;
        String policyIdentifier;
        Boolean isHealthPolicy = false;
        int index = 0;

        JSONArray policyData = (JSONArray) policyType.get("expectedValues");
        try {

            System.out.println(policyType);
            if (policyData.get(0).toString().contains("You & your family’s health"))
                isHealthPolicy = true;

            if (isHealthPolicy)
                policyIdentifier = (String) policyData.get(1);
            else
                policyIdentifier = (String) policyData.get(0);

            String isVisible = (String) policyType.get("isVisible");

            //this will find the complete div of a particular policy card
            HashMap<String, String> cardLocatorMap = new HashMap<>(myAccountPageElement.get("policyCard"));
            String updatedPath = cardLocatorMap.get("XPATH").replace("{{vehicleOrPlanName}}", policyIdentifier);
            cardLocatorMap.put("XPATH", updatedPath);

            if (isVisible.contains("no") && driver.checkIfElementIsPresent(cardLocatorMap))
                return isDone;
            else if (isVisible.contains("no") && !driver.checkIfElementIsPresent(cardLocatorMap)) {
                isDone = true;
                return isDone;
            }

            //if card is not visible then break the function and return false;
            if (!driver.checkIfElementIsPresent(cardLocatorMap)) {
                return isDone;
            }

            //if card is visible verify all the Subcards of particular card
            HashMap<String, String> child_locator = new HashMap<>(myAccountPageElement.get("policySubCard"));
            updatedPath = child_locator.get("XPATH").replace("{{vehicleOrPlanName}}", policyIdentifier);
            child_locator.put("XPATH", updatedPath);

            List<WebElement> elements = driver.findAll(child_locator);

            for (int i = 0; i < policyData.size(); i++) {
                System.out.println(policyData.get(i));
                if (!elements.get(index).getText().contains((CharSequence) policyData.get(i)))
                    return isDone;
                index++;
            }
            isDone = true;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
        return isDone;
    }


    public boolean ProductLink() {
        boolean isDone = false;
        try {

            driver.mouseHover(myAccountPageElement.get("productCard"));
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifyLogoAndText(String journey) {
        boolean isDone = false;
        try {
            //driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            driver.waitForElementToAppear(myAccountPageElement.get("productCard"),10);
            driver.mouseHover(myAccountPageElement.get("productCard"));
            driver.waitForTime(4);
            driver.checkIfElementIsPresent(myAccountPageElement.get(journey + "LogoPath"));
            String Text = driver.find(myAccountPageElement.get(journey + "DivHeading")).getText();

            if (Text.toLowerCase(Locale.ROOT).contains(journey))
                isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifyRedirectionLinks(JSONArray links, String journey) {

        HashMap<String, String> currentProductPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            driver.mouseHover(myAccountPageElement.get("productCard"));
            HashMap<String, String> genericInsurancePagesDivLinks = commonPageObject.getXpath(myAccountPageElement.get("genericInsurancePagesDivLinks").get("XPATH").toString(), "{{value}}", journey.substring(0, 1).toUpperCase(Locale.ROOT) + journey.substring(1));
            List<WebElement> pages = driver.findAll(genericInsurancePagesDivLinks);
            if (pages.size() > links.size()) {
                System.out.println("Number of product pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of product pages are less than expected");
                return isDone;
            }

            for (int i = 1; i <= links.size(); i++) {
                currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get(journey + "InsurancePagesLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.mouseHover(myAccountPageElement.get("productCard"));
                driver.waitForTime(2);
                driver.clickWhenReady(currentProductPageXpath);
                driver.waitForTime(2);
                parentWindowHandle = driver.getWindowHandle();
                driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);


                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    return isDone;
                }
                driver.waitForTime(2);
                if (!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier"))) {
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);

            }

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyResourceSection(JSONArray links) {
        boolean isDone = false;
        HashMap<String, String> resourcePageXpath;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        try {
            //driver.mouseHover(myAccountPageElement.get(""));
            //driver.waitForElementToAppear(myAccountPageElement.get("resourceSection"));
            driver.mouseHover(myAccountPageElement.get("resourceSection"));
            List<WebElement> resourcePages = driver.findAll(myAccountPageElement.get("resourceSectionGenericXpath"));
            if (resourcePages.size() > links.size()) {
                System.out.println("Number of product pages are more than expected");
                return isDone;
            } else if (resourcePages.size() < links.size()) {
                System.out.println("Number of product pages are less than expected");
                return isDone;
            }

            for (int i = 1; i <= links.size(); i++) {
                resourcePageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("resourceSectionLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.mouseHover(myAccountPageElement.get("resourceSection"));
                driver.clickWhenReady(resourcePageXpath);
                driver.waitForTime(3);
                if (!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")))
                    return isDone;
                parentWindowHandle = driver.getWindowHandle();
                driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }

                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1)))
                    return isDone;


                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);

            }

            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyProfileSection(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            //driver.clickWhenReady(myAccountPageElement.get("ackoLogoSection"));
            driver.clickWhenReady(myAccountPageElement.get("profileLogo"));
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericTextElement2").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath))
                    return isDone;
                if (testData.containsKey("redirectionLink")) {
                    driver.click(textXpath);
                    driver.waitForTime(5);
                    System.out.println(driver.getWebDriver().getCurrentUrl() + " " + testData.get("redirectionLink").toString());
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;
                    driver.goBack();
                    driver.waitForTime(5);
                    driver.clickWhenReady(myAccountPageElement.get("profileLogo"));

                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyProfileSectionlogout() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("plogoseo"));
            driver.waitForTime(2);
            //driver.deleteAllCookies();
            driver.clickWhenReady(myAccountPageElement.get("logout"));
            driver.deleteAllCookies();

            driver.waitForTime(2);
            driver.SwichToNewWindow();
            System.out.println(driver.getWebDriver());
            driver.waitForTime(2);


            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifylogout() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("ackoLogoSection"));
            driver.waitForTime(2);
            driver.clickWhenReady(myAccountPageElement.get("plogo"));
            driver.waitForTime(2);
            driver.clickWhenReady(myAccountPageElement.get("logout"));
            driver.deleteAllCookies();

            driver.waitForTime(2);
            driver.SwichToNewWindow();
            System.out.println(driver.getWebDriver());
            driver.waitForTime(2);


            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifymypolicies(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("mypolicies"));
            driver.waitForTime(2);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/myaccount"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifysignuplogin(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {

            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("signuplogin").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath))
                    return isDone;
                if (testData.containsKey("redirectionLink")) {
                    driver.click(textXpath);
                    driver.waitForTime(5);
                    if (!driver.isElementDisplayed(myAccountPageElement.get("singuploginidentifier")) || !driver.checkIfElementIsPresent(myAccountPageElement.get("singuploginidentifier")))
                        return isDone;
                    System.out.println(driver.getWebDriver().getCurrentUrl() + " " + testData.get("redirectionLink").toString());
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;
                    driver.goBack();
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyHelpSection(JSONArray links) {

        boolean isDone = false;
        HashMap<String, String> helpPageXpath;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("resourceSection"),10);
            driver.clickWhenReady(myAccountPageElement.get("helpSection"));
            driver.waitForTime(3);
            parentWindowHandle = driver.getWindowHandle();
            driver.waitForTime(8);
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> it = handles.iterator();
            while (it.hasNext()) {
                if (it.next() != parentWindowHandle)
                    childWindowHandle = it.next();
            }

            driver.switchToWindow(childWindowHandle);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("helpheading")))
                return isDone;
            if (!driver.getWebDriver().getCurrentUrl().equals(links.get(0)))
                return isDone;
            driver.closeWindow();
            driver.switchToWindow(parentWindowHandle);


            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }


    }

    public boolean verifyAckoLogoSection(JSONArray links) {

        boolean isDone = false;
        try {
            driver.waitForTime(3);
            driver.scrollToElement(myAccountPageElement.get("ackoLogoSection"));
            driver.clickWhenReady(myAccountPageElement.get("ackoLogoSection"));
            driver.waitForTime(5);
            if (!driver.getWebDriver().getCurrentUrl().equals(links.get(0)))
                return isDone;
            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyAckoLogoSectionout(JSONArray links) {

        boolean isDone = false;
        try {
            driver.clickWhenReady(myAccountPageElement.get("ackoLogoSection"));
            driver.waitForTime(5);
            if (!driver.getWebDriver().getCurrentUrl().equals(links.get(0)))
                return isDone;
            isDone = true;
            return isDone;
        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyexploreredirection(JSONArray links) {
        boolean isDone = false;
        HashMap<String, String> resourcePageXpath;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        try {
            //OPEN ALL RESOURCES URL
            driver.navigateToURL("https://www.ackodev.com/resources/");
            List<WebElement> resourcePages = driver.findAll(myAccountPageElement.get("genericexplore"));

            if (resourcePages.size() > links.size()) {
                System.out.println("Number of product pages are more than expected");
                return isDone;
            } else if (resourcePages.size() < links.size()) {
                System.out.println("Number of product pages are less than expected");
                return isDone;
            }

            for (int i = 1; i <= links.size(); i++) {
                resourcePageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("explore2").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.scrollToElement(resourcePageXpath);
                driver.clickWhenReady(resourcePageXpath);
                if(!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                    return isDone;
                parentWindowHandle = driver.getWindowHandle();
                driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }

                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1)))
                    return isDone;


                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);

            }
            driver.scrollToElement(myAccountPageElement.get("verifier"));
            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyexploreredirection2(JSONArray links) {
        boolean isDone = false;
        HashMap<String, String> resourcePageXpath;
        try {
            //OPEN ALL RESOURCES URL
            driver.navigateToURL("https://www.ackodev.com/resources/");
            List<WebElement> resourcePages = driver.findAll(myAccountPageElement.get("genericexplore2"));

            if ((resourcePages.size()) > links.size()) {
                System.out.println("Number of product pages are more than expected");
                return isDone;
            } else if (resourcePages.size() < links.size()) {
                System.out.println("Number of product pages are less than expected");
                return isDone;
            }

            for (int i = 1; i <= links.size() - 1; i++) {
                resourcePageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("explore3").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.clickWhenReady(resourcePageXpath);
                driver.waitForTime(8);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i)))
                    return isDone;


                //driver.goBack();

            }

            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean subscribenow(JSONArray links) {
        boolean isDone = false;
        HashMap<String, String> resourcePageXpath;
        try {
            //OPEN ALL RESOURCES URL
            driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.waitForTime(3);
            List<WebElement> resourcePages = driver.findAll(myAccountPageElement.get("subscribenow"));

            if ((resourcePages.size()) > links.size()) {
                System.out.println("Number of product pages are more than expected");
                return isDone;
            } else if (resourcePages.size() < links.size()) {
                System.out.println("Number of product pages are less than expected");
                return isDone;
            }

            for (int i = 1; i <= links.size(); i++) {
                resourcePageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("genericsubscribenow").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.clickWhenReady(resourcePageXpath);
                driver.waitForTime(8);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1)))
                    return isDone;


                driver.goBack();

            }

            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean subscribenowmodal(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.clickWhenReady(myAccountPageElement.get("subscribenow"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("subscribenowph1"));
            driver.clickWhenReady(myAccountPageElement.get("subscribenowph2"));
            driver.clickWhenReady(myAccountPageElement.get("subscribenowph1"));
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("subscribenowph").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Validation is not shown properly");
                    return isDone;
                }
            }
            driver.clickWhenReady(myAccountPageElement.get("subscribenowph3"));
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean subscribenowmodal4(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("subscribenow"));
            driver.waitForTime(3);
            driver.click(myAccountPageElement.get("subbutton"));
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("subscribenowph").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Validation is not shown properly");
                    return isDone;
                }
            }
            driver.clickWhenReady(myAccountPageElement.get("subscribenowph3"));
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean subscribenowmodal3(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("subscribenow"));
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("subscribenowph1"), "Nikhil");
            driver.setText(myAccountPageElement.get("subscribenowph2"), "Nikhil");
            driver.click(myAccountPageElement.get("subbutton"));
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("subscribenowph").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Validation is not shown properly");
                    return isDone;
                }
            }
            driver.click(myAccountPageElement.get("subscribenowph3"));
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean subscribenowmodal2() {
        boolean isDone = false;
        try {
//           driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.clickWhenReady(myAccountPageElement.get("subscribenow"));
            driver.setText(myAccountPageElement.get("subscribenowph1"), "Nikhil");
            driver.setText(myAccountPageElement.get("subscribenowph2"), "Nikhil@gmail.com");
            driver.click(myAccountPageElement.get("subbutton"));
            driver.waitForTime(2);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("explorebutton"))) {
                System.out.println("not able to subscribe");
                return isDone;
            }
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("subscribenowph3"))) {
                System.out.println("not able to subscribe");
                return isDone;
            }
            driver.clickWhenReady(myAccountPageElement.get("explorebutton"));
            driver.waitForTime(1);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")))
                return isDone;
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean blogredirection(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/articles/");
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("blogredirections").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath))
                    return isDone;
                if (testData.containsKey("redirectionLink")) {
                    int j = 0;
                    boolean hit = false;
                    driver.click(textXpath);
                    if (i == 7 || i == 8) {
                        if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                            return isDone;
                        driver.goBack();
                        driver.waitForTime(1);
                        hit = true;
                    }
                    driver.waitForTime(2);
                    while (j < i) {
                        driver.click(myAccountPageElement.get("sidescroll"));
                        driver.waitForTime(2);
                        j++;
                    }
                    if (hit) {
                        hit = false;
                        continue;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;

                    driver.waitForTime(2);


                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean resourceui(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.checkIfElementIsPresent(myAccountPageElement.get("resourcesideimg"));
            driver.checkIfElementIsPresent(myAccountPageElement.get("guidesideimg"));
            driver.checkIfElementIsPresent(myAccountPageElement.get("articlesideimg"));
            driver.checkIfElementIsPresent(myAccountPageElement.get("ebooksideimg"));
            driver.waitForTime(2);
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("resourcesheadings").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Heading is not shown properly");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean guideui(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            //driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.clickWhenReady(myAccountPageElement.get("guideside"));
            driver.waitForTime(2);
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("resourcesheadings").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Heading is not shown properly");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean articleui(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            //driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.clickWhenReady(myAccountPageElement.get("articleside"));

            driver.waitForTime(2);
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("resourcesheadings").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Heading is not shown properly");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean ebookui(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            // driver.navigateToURL("https://www.ackodev.com/resources/");
            driver.clickWhenReady(myAccountPageElement.get("ebookside"));
            driver.waitForTime(2);
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("resourcesheadings").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Heading is not shown properly");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifytopics(JSONArray links) {
        boolean isDone = false;
        HashMap<String, String> resourcePageXpath;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        try {

            driver.navigateToURL("https://www.ackodev.com/car-guide/cars-for-women-in-india/");
            driver.scrollToElement(myAccountPageElement.get("topicsdivtable"));
            List<WebElement> resourcePages = driver.findAll(myAccountPageElement.get("topicsdivtable"));

            if (resourcePages.size() > links.size()) {
                System.out.println("Number of Topics are more than expected");
                return isDone;
            } else if (resourcePages.size() < links.size()) {
                System.out.println("Number of Topics are less than expected");
                return isDone;
            }

            for (int i = 1; i <= links.size(); i++) {
                resourcePageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("topicssingle").get("XPATH"), "{{index}}", String.valueOf(i)));
                //driver.mouseHover(myAccountPageElement.get("resourceSection"));
                driver.scrollToElement(resourcePageXpath);
                driver.clickWhenReady(resourcePageXpath);
                parentWindowHandle = driver.getWindowHandle();
                driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }

                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1)))
                    return isDone;


                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);

            }

            isDone = true;
            return isDone;


        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }

    public boolean topicui(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.scrollToElement(myAccountPageElement.get("topics"));
            driver.waitForTime(2);
            for (int i = 0; i < data.size(); i++) {
                driver.waitForTime(2);
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("topicstextgeneric").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath)) {
                    System.out.println(textXpath);
                    System.out.println("Topics are not shown properly");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifyrecentarticles() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/cars-for-women-in-india/");
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            driver.waitForTime(4);
            driver.scrollToElement(myAccountPageElement.get("recentarticles"));
            driver.waitForTime(2);
            List<WebElement> resourcePages = driver.findAll(myAccountPageElement.get("recentdivtableelements"));
            for (int i = 1; i < resourcePages.size() + 1; i++) {
                textXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("recenthrefs").get("XPATH"), "{{index}}", String.valueOf(i)));
                //System.out.println(textXpath);
                if (!driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println(i);
                    System.out.println("Recent Article at above index is not working correctly");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean verifyrecentarticles2() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/car-guide/cars-for-women-in-india/");
            driver.scrollToElement(myAccountPageElement.get("recentdivtableelements"));
            driver.clickWhenReady(myAccountPageElement.get("recentallarticles"));
            driver.waitForTime(2);
            driver.SwichToNewWindow();
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/articles/"))
                return isDone;
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }


    //    Footer Redirections functions
    public boolean verifyFooterSection(JSONArray links, String journey) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            HashMap<String, String> genericFooterLinks = commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH").toString(), "[{{index}}]", "");
            List<WebElement> pages = driver.findAll(genericFooterLinks);
            System.out.println(pages.size());
            if (pages.size() > links.size()) {
                System.out.println("Number of pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of pages are less than expected");
                return isDone;
            }


            for (int i = 1; i <= links.size(); i++) {
                driver.waitForTime(3);
                currentPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.waitForElementToAppear(currentPageXpath,10);
                parentWindowHandle = driver.getWindowHandle();
                driver.clickWhenReady(currentPageXpath);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);
                driver.waitForElementToAppear(myAccountPageElement.get("verifier"),10);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    System.out.println("The link on the above index didnt match" +driver.getWebDriver().getCurrentUrl());
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                if (!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier"))  ) {
                    System.out.println("verifier element not found" +driver.getWebDriver().getCurrentUrl());
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                driver.waitForElementToAppear(myAccountPageElement.get("verifier"),5);
                driver.scrollToElement(myAccountPageElement.get("verifier"));
            }
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }
    public boolean verifyFooterSection2(JSONArray links, String journey) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            HashMap<String, String> genericFooterLinks = commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH").toString(), "[{{index}}]", "");
            List<WebElement> pages = driver.findAll(genericFooterLinks);
            System.out.println(pages.size());
            if (pages.size() > links.size()) {
                System.out.println("Number of pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of pages are less than expected");
                return isDone;
            }


            for (int i = 1; i <= links.size(); i++) {
                driver.waitForTime(3);
                currentPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.waitForElementToAppear(currentPageXpath,10);
                parentWindowHandle = driver.getWindowHandle();
                driver.clickWhenReady(currentPageXpath);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);
                driver.waitForElementToAppear(myAccountPageElement.get("verifier"),10);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    System.out.println("The link on the above index didnt match" +driver.getWebDriver().getCurrentUrl());
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                driver.waitForElementToAppear(myAccountPageElement.get("verifier"),5);
                driver.scrollToElement(myAccountPageElement.get("verifier"));
            }
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }

    public boolean verifyFooterSection3(JSONArray links, String journey, int index) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {

            HashMap<String, String> genericFooterLinks = commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH").toString(), "[{{index}}]", "");
            List<WebElement> pages = driver.findAll(genericFooterLinks);

            if (pages.size() > links.size()) {
                System.out.println("Number of pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of pages are less than expected");
                return isDone;
            }


            for (int i = 1; i <= links.size(); i++) {

                currentPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.waitForElementToAppear(currentPageXpath,10);
                driver.clickWhenReady(currentPageXpath);
                parentWindowHandle = driver.getWindowHandle();
                driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.waitForTime(3);
                if(i!=index) {
                    if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        return isDone;
                    }
                    if (!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier"))) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        return isDone;
                    }

                }

                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                driver.waitForTime(5);

            }

            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }

    public boolean verifyFooterRedirectionSection2(JSONArray links, String journey) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            HashMap<String, String> genericFooterLinks = commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH").toString(), "[{{index}}]", "");
            List<WebElement> pages = driver.findAll(genericFooterLinks);
            if (pages.size() > links.size()) {
                System.out.println("Number of pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of pages are less than expected");
                return isDone;
            }
            for (int i = 1; i <= links.size(); i++) {
                currentPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get(journey + "FooterLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.waitForElementToAppear(currentPageXpath,10);
                driver.clickWhenReady(currentPageXpath);
                parentWindowHandle = driver.getWindowHandle();
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    driver.waitForTime(2);
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                driver.waitForElementToAppear(myAccountPageElement.get("verifier"),5);
                driver.scrollToElement(myAccountPageElement.get("verifier"));
            }

            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }


    public boolean DownloadNowAppStore(JSONArray links, String journey) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {

            driver.waitForTime(5);
            driver.clickWhenReady(myAccountPageElement.get("appStoreDownload"));
            parentWindowHandle = driver.getWindowHandle();
            driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);
                String url = driver.getWebDriver().getCurrentUrl();
                url = url.substring(0,23);
                if (!url.equals(links.get(1))) {
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                if(!driver.checkIfElementIsPresent(myAccountPageElement.get("appstoreverifier")))
                {
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }



    public boolean DownloadNowPlayStore(JSONArray links, String journey) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {

            driver.waitForTime(5);
            driver.clickWhenReady(myAccountPageElement.get("GooglePlayDownload"));
            parentWindowHandle = driver.getWindowHandle();
            driver.waitForTime(8);
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> it = handles.iterator();
            while (it.hasNext()) {
                if (it.next() != parentWindowHandle)
                    childWindowHandle = it.next();
            }
            driver.switchToWindow(childWindowHandle);
            String url = driver.getWebDriver().getCurrentUrl();
            System.out.println(url);

            url = url.substring(0,23);
            System.out.println(url);
            System.out.println(links.get(0));

            if (!url.equals(links.get(0))) {
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("playstoreverifier")))
            {
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                return isDone;
            }
            driver.closeWindow();
            driver.switchToWindow(parentWindowHandle);
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }

    /////////////////////                          /////////////////////
    ////////////////////          BIKE START       /////////////////////
    /////////////////////                          /////////////////////
    public boolean bikewidget() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("nodatavalidation")) || !driver.isElementDisplayed(myAccountPageElement.get("nodatavalidation")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean bikewidget2() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholder"));
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("bikeplaceholder"), "Nikhil");
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("wrongdatavalidation2")) || !driver.isElementDisplayed(myAccountPageElement.get("wrongdatavalidation2")))
                return isDone;
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean bikewidget3() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholder"));
            driver.setText(myAccountPageElement.get("bikeplaceholder"), "HR26CK2404");
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("welcomebacklogin"));
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/login?next=%2F"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("loginbutton")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean bikewidget4() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholder"));
            driver.setText(myAccountPageElement.get("bikeplaceholder"), "HR26CK1234");
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            driver.waitForTime(3);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 57);
            System.out.println(link);
            if (!link.equals("https://www.ackodev.com/lp/new-bike/bike/mmv?proposal_id="))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Select your bike") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget5() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholder"));
            driver.setText(myAccountPageElement.get("bikeplaceholder"), "HR26CK2404");
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("loginbuttonwelcomeback"));
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/login?next=%2F"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("loginbutton")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget6() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Get a quote") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/lp/new-bike/bike/mmv?flow=new"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Enter bike details") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget7() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),10);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholder"));
            driver.setText(myAccountPageElement.get("bikeplaceholder"), "HR26CK2404");
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("accessbuttonwelcomeback"));
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/myaccount"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget8() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            driver.waitForElementToAppear(myAccountPageElement.get("bikeplaceholder"),20);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholder"));
            driver.setText(myAccountPageElement.get("bikeplaceholder"), "UP85BD0681");
            driver.clickWhenReady(myAccountPageElement.get("insurenow"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("loginbuttonwelcomeback"));
            driver.waitForTime(10);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/myaccount"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            //driver.closeBrowser();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidgetgq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            bikewidgetgetquote();
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("insurenowgq"));
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("nodatavalidation")) && !driver.isElementDisplayed(myAccountPageElement.get("enterdata")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean bikewidget2gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            bikewidgetgetquote();
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholdergq"));
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("bikeplaceholdergq"), "Nikhil");
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("insurenowgq"));
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("wrongdatavalidation2")) && !driver.isElementDisplayed(myAccountPageElement.get("wrongdatavalidation2")))
                return isDone;
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget4gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Rate Us") ;
            driver.scrollToElement(textXpath);
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("BikeInsurancegetquote"));
            driver.waitForTime(2);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholdergq"));
            driver.setText(myAccountPageElement.get("bikeplaceholdergq"), "HR26CK1234");
            driver.clickWhenReady(myAccountPageElement.get("insurenowgq"));
            driver.waitForTime(20);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 57);
            System.out.println(link);
            if (!link.equals("https://www.ackodev.com/lp/new-bike/bike/mmv?proposal_id="))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Select your bike") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget5gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Rate Us") ;
            driver.scrollToElement(textXpath);
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("BikeInsurancegetquote"));
            driver.waitForTime(2);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholdergq"));
            driver.setText(myAccountPageElement.get("bikeplaceholdergq"), "HR26CK2404");
            driver.clickWhenReady(myAccountPageElement.get("insurenowgq"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("loginbuttonwelcomeback"));
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/login?next=%2F"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("loginbutton")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget6gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            bikewidgetgetquote();
            driver.waitForTime(2);
            driver.clickWhenReady(myAccountPageElement.get("getquotegq"));
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/lp/new-bike/bike/mmv?flow=new"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Enter bike details") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget7gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/two-wheeler-insurance/");
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Rate Us") ;
            driver.waitForElementToAppear(textXpath,20);
            driver.scrollToElement(textXpath);
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("BikeInsurancegetquote"));
            driver.waitForTime(2);
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholdergq"));
            driver.setText(myAccountPageElement.get("bikeplaceholdergq"), "HR26CK2404");
            driver.clickWhenReady(myAccountPageElement.get("insurenowgq"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("accessbuttonwelcomeback"));
            driver.waitForTime(3);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/myaccount"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            isDone = true;
            return isDone;



        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidget8gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            bikewidgetgetquote();
            driver.clickWhenReady(myAccountPageElement.get("bikeplaceholdergq"));
            driver.setText(myAccountPageElement.get("bikeplaceholdergq"), "UP85BD0681");
            driver.clickWhenReady(myAccountPageElement.get("insurenowgq"));
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("loginbuttonwelcomeback"));
            driver.waitForTime(10);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/myaccount"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")))
                return isDone;
            //driver.closeBrowser();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean bikewidgetgetquote() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Rate Us") ;
            driver.scrollToElement(textXpath);
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("BikeInsurancegetquote"));
            driver.waitForTime(2);
            //driver.closeBrowser();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
/////////////////////                          /////////////////////
/////////////////////          BIKE END        /////////////////////
/////////////////////                          /////////////////////
    public boolean electronicsnodata(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath))
                    return isDone;
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(myAccountPageElement.get("electronicssearch"));
                    driver.click(textXpath);
                    driver.scrollToElement(myAccountPageElement.get("electronicssearch"));
                    if (!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))) {
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    driver.waitForTime(2);
                    System.out.println(driver.getWebDriver().getCurrentUrl() + " " + testData.get("redirectionLink").toString());
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;
                    driver.goBack();
                    driver.waitForTime(2);
                }
            }
            //driver.closeBrowser();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean electronicsdata() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("electronicsplaceholder"), "Bike");
            driver.clickWhenReady(myAccountPageElement.get("electronicssearch"));
            driver.waitForTime(3);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("searchresult")) || !driver.isElementDisplayed(myAccountPageElement.get("searchresult"))) {
                System.out.println("The New Page did not load properly");
                return isDone;
            }
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/search-result?lob=electronics&query=Bike"))
                return isDone;
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean electronicsdata2() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.scrollToElement(myAccountPageElement.get("pagination2"));
            driver.clickWhenReady(myAccountPageElement.get("pagination2"));
            driver.waitForTime(3);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("searchresult")) || !driver.isElementDisplayed(myAccountPageElement.get("searchresult"))) {
                System.out.println("The New Page did not load properly");
                return isDone;
            }
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/search-result?lob=electronics&query=Bike&page=1"))
                return isDone;
            driver.waitForTime(3);
            driver.scrollToElement(myAccountPageElement.get("pagination1"));
            driver.clickWhenReady(myAccountPageElement.get("pagination1"));
            driver.waitForTime(3);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("searchresult")) || !driver.isElementDisplayed(myAccountPageElement.get("searchresult"))) {
                System.out.println("The New Page did not load properly");
                return isDone;
            }
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/search-result?lob=electronics&query=Bike&page=0"))
                return isDone;

            //driver.closeBrowser();
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean electronicsdatanotpresent() {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("electronicsplaceholder"),"Nik");
            driver.clickWhenReady(myAccountPageElement.get("electronicssearch"));
            driver.waitForTime(3);
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))){
                System.out.println("The New Page did not load properly");
                return isDone;}
            if(!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/no-result-found?lob=electronics&query=Nik"))
                return isDone;

            //driver.closeBrowser();
            isDone=true;
            return isDone;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean electronicsredirections(JSONArray data) {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            for(int i=0;i<data.size();i++)
            {
                JSONObject testData= (JSONObject) data.get(i);
                textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}",testData.get("visibleText").toString()) ;
                if(!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath))
                    return isDone;
                if(testData.containsKey("redirectionLink"))
                {
                    driver.click(textXpath);
                    driver.waitForTime(2);
                    driver.scrollToElement(myAccountPageElement.get("verifier"));
                    if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))){
                        System.out.println("The New Page did not load properly");
                        return isDone;}
                    System.out.println(driver.getWebDriver().getCurrentUrl() + " "+ testData.get("redirectionLink").toString());
                    if(!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;
                    driver.waitForTime(2);
                }
            }
           // driver.closeBrowser();
            isDone=true;
            return isDone;

        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean beforepurchase() {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Before Purchasing") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(3);
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))){
                System.out.println("The New Page did not load properly");
                return isDone;}
            if(!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/electronics/before-purchasing-a-plan"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Appliances") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","What are ACKO Protection Plans for Applaince?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Mobile Phones") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","What are ACKO Protection Plans for Mobile phone?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}

           // driver.closeBrowser();
            isDone=true;
            return isDone;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean raiserepair() {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Raise a Repair") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(3);
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))){
                System.out.println("The New Page did not load properly");
                return isDone;}
            if(!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/electronics/raise-a-repair-request"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Appliances") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","How do I raise a repair request for an appliance?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Mobile Phones") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}", "How do I raise a repair request for my mobile phone?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}

            // driver.closeBrowser();
            isDone=true;
            return isDone;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean modesof() {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Modes of Settlement") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(3);
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))){
                System.out.println("The New Page did not load properly");
                return isDone;}
            if(!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/electronics/modes-of-settlement?tab=mobile-phone&option=cashless-repair"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Appliances") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","What is At Home Repair mode for appliance and how does it work?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Mobile Phones") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}", "What is Cashless mode of repair for mobile and how does it work?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}

            // driver.closeBrowser();
            isDone=true;
            return isDone;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean trackrepair() {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Tracking Repair & Payout Status") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(3);
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))){
                System.out.println("The New Page did not load properly");
                return isDone;}
            if(!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/electronics/tracking-repair-and-payout-status"))
                return isDone;
            // driver.closeBrowser();
            isDone=true;
            return isDone;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean afterpurchase() {
        boolean isDone=false;
        HashMap<String,String> textXpath=null;
        try{
            driver.navigateToURL("https://www.ackodev.com/help/electronics");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","After Plan Purchase") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(3);
            if(!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/help/electronics/after-plan-purchase"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Update Plan") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","How do I check for active plans for myself?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Cancel Plan") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","How do I cancel my plan?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Activate Plan") ;
            driver.clickWhenReady(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","What is plan activation?") ;
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("The New Page did not load properly");
                return isDone;}

            //driver.closeBrowser();
            isDone=true;
            return isDone;
        }catch(Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }




    public boolean electronicshelpus() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {

            driver.closeBrowser();
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }


    public boolean carWidgetNoData(String journey) {
        boolean isDone = false;

        try {


            driver.waitForTime(5);
            driver.clickWhenReady(myAccountPageElement.get(journey + "InsureNowButton"));
            driver.waitForTime(3);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("noDataCarValidation")) && !driver.isElementDisplayed(myAccountPageElement.get("noDataCarValidation")))
                return isDone;

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean carWidgetWrongData(String journey) {
        boolean isDone = false;

        try {
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get(journey + "carInputPlaceholder"));
            driver.setText(myAccountPageElement.get(journey + "carInputPlaceholder"), "TEST");
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get(journey + "InsureNowButton"));
            driver.waitForTime(3);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("wrongDataCarValidation")) && !driver.isElementDisplayed(myAccountPageElement.get("wrongDataCarValidation")))
                return isDone;

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }

    public boolean bikeNumberInCarJourney(String journey, String bikeNumber) {
        boolean isDone = false;

        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20);
            if(bikeNumber != "")
            {
                driver.clickWhenReady(myAccountPageElement.get(journey + "carInputPlaceholder"));
                driver.setText(myAccountPageElement.get(journey + "carInputPlaceholder"), bikeNumber);
                driver.waitForTime(3);

            }
            driver.clickWhenReady(myAccountPageElement.get(journey + "InsureNowButton"));
            driver.waitForTime(5);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("bikeInCarJourney")) || !driver.isElementDisplayed(myAccountPageElement.get("bikeInCarJourney")))
                return isDone;

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean carGetQuoteButton(String journey) {
        boolean isDone = false;

        try {

            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get(journey + "carGetQuote"));
            driver.waitForTime(8);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 31);
            System.out.println(link);
            if (!link.equals("https://www.ackodev.com/new-car")) {
                return isDone;
            }

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }


    public boolean getQuoteWidget() {
        boolean isDone = false;

        try {
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            driver.waitForTime(4);
            driver.scrollToElement(myAccountPageElement.get("Widget1"));
            driver.waitForTime(3);

            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("getQuoteWidget")) && !driver.isElementDisplayed(myAccountPageElement.get("getQuoteWidget")))
                return isDone;

            driver.clickWhenReady(myAccountPageElement.get("getQuoteWidget"));
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean alreadyInsured(String journey, String carNumber) {
        boolean isDone = false;

        try {
            if(carNumber != "")
            {
                driver.waitForTime(3);
                driver.clickWhenReady(myAccountPageElement.get(journey + "carInputPlaceholder"));
                driver.setText(myAccountPageElement.get(journey + "carInputPlaceholder"), carNumber);
            }
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get(journey + "InsureNowButton"));
            driver.waitForTime(3);

            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("alreadyInsuredWithAcko")) && !driver.isElementDisplayed(myAccountPageElement.get("alreadyInsuredWithAcko")))
                return isDone;


            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean widgetAssertion_registrationButton(String journey) {
        boolean isDone = false;

        try {
            driver.clickWhenReady(myAccountPageElement.get("registrationButton"));
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get(journey + "InsureNowButton")) && !driver.isElementDisplayed(myAccountPageElement.get(journey + "InsureNowButton")))
                return isDone;

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }


    public boolean widgetAssertion_LoginToKnowMore() {
        boolean isDone = false;

        try {
            driver.waitForTime(4);
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get("LoginToKnowMore")) && !driver.isElementDisplayed(myAccountPageElement.get("LoginToKnowMore")))
                return isDone;

            driver.clickWhenReady(myAccountPageElement.get("LoginToKnowMore"));

            driver.waitForTime(5);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 23);
            System.out.println(link);
            if (!link.equals("https://www.ackodev.com")) {
                return isDone;
            }


            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean widgetAssertion_viewPolicy(String buttonName) {
        boolean isDone = false;

        try {
            if (!driver.checkIfElementIsPresent(myAccountPageElement.get(buttonName)) && !driver.isElementDisplayed(myAccountPageElement.get(buttonName)))
                return isDone;

            driver.clickWhenReady(myAccountPageElement.get(buttonName));

            driver.waitForTime(10);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 23);
            System.out.println(link);
            if (!link.equals("https://www.ackodev.com")) {
                return isDone;
            }


            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean freshCarRedirection(String journey ,String carNumber) {
        boolean isDone = false;


        try{

            driver.clickWhenReady(myAccountPageElement.get(journey + "carInputPlaceholder"));
            driver.setText(myAccountPageElement.get(journey + "carInputPlaceholder"), carNumber);
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get(journey + "InsureNowButton"));
            driver.waitForTime(8);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 52);
            System.out.println(link);
            if (!link.equals("https://www.ackodev.com/car-journey/vehicle-prequote")) {
                return isDone;
            }
            isDone = true;
            return isDone;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean getyourfamily() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericAelement").get("XPATH"),"{{value}}","Find the right plan") ;
            driver.clickWhenReady(textXpath);
           if (!driver.getWebDriver().getCurrentUrl().equals("https://www.acko.com/p/health/segment?ref=slp-top-health"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","What kind of insurance do you need?") ;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.goBack();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean insureyourfamily() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/health-insurance/frostbite/");
            driver.waitForTime(4);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericAelement").get("XPATH"),"{{value}}","Insure your family") ;
            driver.waitForElementExplicitly(textXpath);
            parentWindowHandle=driver.getWebDriver().getWindowHandle();
            driver.clickWhenReady(textXpath);
//            Set<String> handles = driver.getWindowHandles();
//            Iterator<String> it = handles.iterator();
//            while (it.hasNext()) {
//                if (it.next() != parentWindowHandle){
//                    childWindowHandle = it.next();}
//            }
            driver.SwichToNewWindow();
           // driver.switchToWindow(childWindowHandle);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","What kind of insurance do you need?") ;
            driver.waitForElementExplicitly(textXpath);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.acko.com/p/health/segment?ref=slp-side-health"))
                return isDone;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.closeWindow();
            driver.switchToWindow(parentWindowHandle);
            driver.waitForTime(5);
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean getyourfamilygq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Rate Us") ;
            driver.scrollToElement(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Get Quote") ;
            driver.clickWhenReady(textXpath);
            driver.clickWhenReady(myAccountPageElement.get("getyourfamily"));
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.acko.com/p/health/segment?ref=slp-top-health"))
                return isDone;
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","What kind of insurance do you need?") ;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.goBack();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean scheduledemo() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/group-health-insurance/");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericAelement").get("XPATH"),"{{value}}","Schedule a Demo") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(8);
            System.out.println(driver.getWebDriver().getCurrentUrl());
            if (!driver.getWebDriver().getCurrentUrl().equals("https://form.typeform.com/to/V5CKuaqZ?typeform-source=www.ackodev.com"))
                return isDone;
          textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericSpanElement").get("XPATH"),"{{value}}","Your Name") ;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.goBack();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean scheduledemogq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/group-health-insurance/");
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Two Different Claim Approaches") ;
            driver.scrollToElement(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Get Quote") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            driver.click(myAccountPageElement.get("scheduleyourdemo"));
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericSpanElement").get("XPATH"),"{{value}}","Your Name") ;
            driver.waitForElementExplicitly(textXpath);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://form.typeform.com/to/V5CKuaqZ?typeform-source=www.ackodev.com"))
                return isDone;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.goBack();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean scheduledemoarticles() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/health-insurance/importance-of-group-health-insurance-for-employees-and-employers/");
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericAelement").get("XPATH"),"{{value}}","Schedule a Demo") ;
            driver.waitForElementToAppear(textXpath,100);
            parentWindowHandle = driver.getWebDriver().getWindowHandle();
            driver.clickWhenReady(textXpath);
            driver.SwichToNewWindow();
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericSpanElement").get("XPATH"),"{{value}}","Your Name") ;
            driver.waitForElementToAppear(textXpath,10);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://form.typeform.com/to/V5CKuaqZ?typeform-source=www.ackodev.com"))
                return isDone;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.closeWindow();
            driver.switchToWindow(parentWindowHandle);
            driver.goBack();
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean ratings() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        try {
            driver.waitForTime(2);
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Write to us at hello@acko.com") ;
            driver.waitForElementToAppear(textXpath,10);
            parentWindowHandle = driver.getWebDriver().getWindowHandle();
            driver.scrollToElement(textXpath);
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","Rate Us") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            driver.SwichToNewWindow();
            childWindowHandle= driver.getWebDriver().getWindowHandle();
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.google.com/search?hl=en-IN&gl=in&q=ACKO+General+Insurance+Limited,+%2336/5,+Hustlehub+One+East,+Somasandrapalya,+27th+Main+Rd,+Sector+2,+HSR+Layout,+Bengaluru,+Karnataka+560102&ludocid=1641951772424297989&lsig=AB86z5XTfbqiCeKddjq1bQb0cU6X#lrd=0x3bae145b3b40270d:0x16c9627005ac5e05,3"))
                return isDone;
           textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Sign in") ;
            driver.waitForElementToAppear(textXpath,10);
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.SwichToNewWindow();
            driver.waitForTime(2);
            driver.closeWindow();
            driver.waitForTime(2);
            driver.switchToWindow(childWindowHandle);
            driver.waitForTime(2);
            driver.closeWindow();
            driver.waitForTime(2);
            driver.switchToWindow(parentWindowHandle);
            driver.waitForTime(2);
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean ratings2() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        try {
            driver.waitForTime(3);
            driver.navigateToURL("https://www.ackodev.com/car-insurance/");
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Write to us at hello@acko.com") ;
            driver.waitForElementToAppear(textXpath,10);
            parentWindowHandle = driver.getWebDriver().getWindowHandle();
            driver.scrollToElement(textXpath);
            driver.waitForTime(3);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericRelement").get("XPATH"),"{{value}}","See all reviews") ;
            driver.waitForElementToAppear(textXpath,10);
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            driver.SwichToNewWindow();
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericAelement").get("XPATH"),"{{value}}","Sign in") ;
            driver.waitForTime(8);
            String link = driver.getWebDriver().getCurrentUrl();
            link = link.substring(0, 66);
            System.out.println(link);
            if (!link.equals("https://www.google.co.in/maps/place/ACKO+General+Insurance+Limited"))
                return isDone;
            if (!driver.isElementDisplayed(textXpath))
                return isDone;
            driver.closeWindow();
            driver.waitForTime(2);
            driver.switchToWindow(parentWindowHandle);
            driver.waitForTime(2);
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean arogya1() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/arogya-sanjeevani-health-insurance/");
            driver.waitForTime(4);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Enter your phone number") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Insure now") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","We require your Phone number!") ;
                        if (!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                            return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean arogya1gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/arogya-sanjeevani-health-insurance/");
            driver.waitForTime(4);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Why should you buy ACKO Arogya Sanjeevani Health Insurance Policy?") ;
            driver.scrollToElement(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Get Quote") ;
            driver.click(textXpath);
            driver.waitForTime(3);
            driver.clickWhenReady(myAccountPageElement.get("arogyagqinsure"));
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericgqarogya").get("XPATH"),"{{value}}","We require your Phone number!") ;
            if (!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean arogya2gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/arogya-sanjeevani-health-insurance/");
            driver.waitForTime(4);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Why should you buy ACKO Arogya Sanjeevani Health Insurance Policy?") ;
            driver.scrollToElement(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Get Quote") ;
            driver.click(textXpath);
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("arogyaInputElement2"), "UP85BD0681");
            driver.clickWhenReady(myAccountPageElement.get("arogyagqinsure"));
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericgqarogya").get("XPATH"),"{{value}}","You typed an invalid Phone number!") ;
            if (!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean arogya3gq() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/arogya-sanjeevani-health-insurance/");
            driver.waitForTime(4);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Why should you buy ACKO Arogya Sanjeevani Health Insurance Policy?") ;
            driver.scrollToElement(textXpath);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"),"{{value}}","Get Quote") ;
            driver.click(textXpath);
            driver.waitForTime(3);
            driver.setText(myAccountPageElement.get("arogyaInputElement2"), "9818277494");
            driver.clickWhenReady(myAccountPageElement.get("arogyagqinsure"));
            driver.waitForTime(2);
            driver.waitForElementToAppear(myAccountPageElement.get("arogyah6"),10);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/health-product/addfamilymember"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("arogyah6")))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean arogya2() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/arogya-sanjeevani-health-insurance/");
            driver.waitForTime(4);
            //textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Enter your phone number") ;
            //driver.clickWhenReady(textXpath);
            driver.setText(myAccountPageElement.get("arogyaInputElement"), "UP85BD0681");
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Insure now") ;
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","You typed an invalid Phone number!") ;
            if (!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean arogya3() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.navigateToURL("https://www.ackodev.com/arogya-sanjeevani-health-insurance/");
            driver.waitForTime(4);
            //textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Enter your phone number") ;
            //driver.clickWhenReady(textXpath);
            driver.setText(myAccountPageElement.get("arogyaInputElement"), "9818277494");
            driver.waitForTime(2);
            textXpath=commonPageObject.getXpath(myAccountPageElement.get("genericTextElement").get("XPATH"),"{{value}}","Insure now") ;
            driver.clickWhenReady(textXpath);
            //textXpath=commonPageObject.getXpath(myAccountPageElement.get("bikebuttons").get("XPATH"),"{{value}}","") ;
            driver.waitForElementToAppear(myAccountPageElement.get("arogyah6"),10);
            if (!driver.getWebDriver().getCurrentUrl().equals("https://www.ackodev.com/health-product/addfamilymember"))
                return isDone;
            if (!driver.isElementDisplayed(myAccountPageElement.get("arogyah6")))
                return isDone;

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    ////NEW header
    public boolean verifylogoandheading(String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericcategoryheading").get("XPATH"), "{{value}}",journey);
            if(!driver.isElementDisplayed(textXpath)){
                System.out.println("Element not found");
                return isDone;
            }
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean categorylinks(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20);
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericcatergorytexts").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get(category+"genericcatergorylinks").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(textXpath);
                    parentWindowHandle = driver.getWindowHandle();
                    driver.waitForTime(8);
                    Set<String> handles = driver.getWindowHandles();
                    Iterator<String> it = handles.iterator();
                    while (it.hasNext()) {
                        if (it.next() != parentWindowHandle)
                            childWindowHandle = it.next();
                    }
                    driver.switchToWindow(childWindowHandle);
                    driver.waitForTime(3);
                    if(!driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20))
                    {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")) || !driver.isElementDisplayed(myAccountPageElement.get("verifier"))) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean categorylinkselectronics(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            if(!driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20)){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericcatergorytexts").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get(category+"genericcatergorylinks").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(textXpath);
                    parentWindowHandle = driver.getWindowHandle();
                    driver.waitForTime(8);
                    Set<String> handles = driver.getWindowHandles();
                    Iterator<String> it = handles.iterator();
                    while (it.hasNext()) {
                        if (it.next() != parentWindowHandle)
                            childWindowHandle = it.next();
                    }
                    driver.switchToWindow(childWindowHandle);
                    driver.waitForTime(3);
                    textXpath = commonPageObject.getXpath(myAccountPageElement.get("generic*xpath").get("XPATH"), "{{value}}", "Help");
                    if(!driver.waitForElementToAppear(textXpath,20)){
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")) || !driver.isElementDisplayed(textXpath)) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly 2");
                        return isDone;
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean categorylinksrenewals(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),25);
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericcatergorytextsrenewals").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get(category+"genericcatergorylinksrenewals").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)){
                    System.out.println("The textxpath not found");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    if(i==1){
                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }

                        driver.switchToWindow(childWindowHandle);
                        driver.waitForElementToAppear(myAccountPageElement.get("verifier"),15);
                        if(!driver.isElementDisplayed(myAccountPageElement.get("verifier")) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))){
                            System.out.println("How claims works page did not load properly");
                        }
                    }else {

                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }

                        driver.switchToWindow(childWindowHandle);
                        textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"), "{{value}}", "Log in with your phone number");
                        driver.waitForElementToAppear(textXpath,10);
                        if (!driver.isElementDisplayed(textXpath) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))) {
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean categorylinksclaims(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20);
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericcatergorytextsclaims").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get(category+"genericcatergorylinksclaims").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath)){
                    System.out.println("The textxpath not found");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    if(i==2){
                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }
                        driver.switchToWindow(childWindowHandle);
                        if(!driver.waitForElementToAppear(myAccountPageElement.get("verifier"),10)){
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                        if(!driver.isElementDisplayed(myAccountPageElement.get("verifier")) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))){
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                    }else {
                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }
                        driver.switchToWindow(childWindowHandle);
                        textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericTextElement3").get("XPATH"), "{{value}}", "Log in with your phone number");
                        if(!driver.waitForElementToAppear(textXpath,10)){
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                        if (!driver.isElementDisplayed(textXpath) || !driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))) {
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean resources(JSONArray data) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        HashMap<String, String> currentProductPageXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("genericcatergorytextsresources").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath)){
                    System.out.println("The textxpath not found");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(textXpath);
                    parentWindowHandle = driver.getWindowHandle();
                    driver.waitForTime(8);
                    Set<String> handles = driver.getWindowHandles();
                    Iterator<String> it = handles.iterator();
                    while (it.hasNext()) {
                        if (it.next() != parentWindowHandle)
                            childWindowHandle = it.next();
                    }
                    driver.switchToWindow(childWindowHandle);
                    if(!driver.waitForElementToAppear(myAccountPageElement.get("verifier"),15)){
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    if (!driver.isElementDisplayed(myAccountPageElement.get("verifier")) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("headercategory").get("XPATH"), "{{value}}", "Resources")) ;
                driver.clickWhenReady(currentProductPageXpath);

            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean verifysignuplogin(JSONArray data,String category) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("signuplogin").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath))
                    return isDone;
                if (testData.containsKey("redirectionLink")) {
                    driver.click(textXpath);
                    driver.waitForTime(5);
                    if (!driver.isElementDisplayed(myAccountPageElement.get("singuploginidentifier")) || !driver.checkIfElementIsPresent(myAccountPageElement.get("singuploginidentifier")))
                        return isDone;
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;
                    driver.goBack();
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean verifyAckoLogo(JSONArray links) {

        boolean isDone = false;
        try {
            driver.clickWhenReady(myAccountPageElement.get("ackoLogoSection"));
            driver.waitForTime(5);
            if (!driver.getWebDriver().getCurrentUrl().equals(links.get(0)))
                return isDone;
            isDone = true;
            return isDone;
        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }
    public boolean entercategory(String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),15);
            if(!driver.checkIfElementIsPresent(myAccountPageElement.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("headercategory").get("XPATH"), "{{value}}", journey)) ;
            driver.clickWhenReady(currentProductPageXpath);
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean logintheuser() {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        try {
            driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20);
            loginUsingCookie();
            driver.waitForTime(2);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("spanxpath").get("XPATH"), "{{value}}", "My account")) ;
            if(!driver.waitForElementToAppear(currentProductPageXpath,20)){
                System.out.println("Not able to login the user");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(currentProductPageXpath)){
                return isDone;
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifyProfileSection(JSONArray link,JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        HashMap<String, String> currentProductPageXpath = null;

        try {
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("spanxpath").get("XPATH"), "{{value}}", "My account")) ;
            driver.clickWhenReady(currentProductPageXpath);
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("generic*xpath").get("XPATH"), "{{value}}", "nikhil Kabba");
            if(!driver.checkIfElementIsPresent(textXpath)){
                System.out.println("User name is not visible properly");
                return isDone;
            }
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("generic*xpath").get("XPATH"), "{{value}}", "View Profile");
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(myAccountPageElement.get("generic*xpath").get("XPATH"), "{{value}}", "Contact info");
            if(!driver.waitForElementToAppear(textXpath,20)){
                System.out.println("The New Page did not load properly");
                return isDone;
            }
            if(!driver.getWebDriver().getCurrentUrl().equals(link.get(0))){
                System.out.println("Redirected link is not correct");
                return isDone;
            }
            driver.goBack();
            if(!driver.waitForElementToAppear(myAccountPageElement.get("verifier"),20)){
                System.out.println("The New Page did not load properly");
                return isDone;
            }
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(myAccountPageElement.get("spanxpath").get("XPATH"), "{{value}}", "My account")) ;
                driver.clickWhenReady(currentProductPageXpath);
                textXpath = commonPageObject.getXpath(myAccountPageElement.get("generic*xpath").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)){
                    System.out.println("The visible text element is not correct");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    driver.click(textXpath);
                    driver.waitForTime(8);
                    textXpath = commonPageObject.getXpath(myAccountPageElement.get("generic*xpath").get("XPATH"), "{{value}}", "Covered by ACKO");
                    if(!driver.waitForElementToAppear(textXpath,15)){
                        System.out.println("my account page did not load as expected");
                        return isDone;
                    }
                    if (!driver.checkIfElementIsPresent(textXpath)){
                        System.out.println("my account page did not load as expected");
                        return isDone;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))){
                        System.out.println("The redirected link is not correct");
                        return isDone;}
                    driver.goBack();
                }
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }


}
