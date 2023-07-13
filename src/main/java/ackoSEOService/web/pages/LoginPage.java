package ackoSEOService.web.pages;

import acko.utilities.ObjectRepositoryLoader;
import acko.utilities.PropertiesFileUtility;

import java.util.HashMap;

/**
 * This class extends HomePage class and takes the base class object repository
 * if not defined in the constructor. Methods used for creating test cases can
 * be placed here.
 */
public class LoginPage extends CommonPage {
    HashMap<String, HashMap<String, String>> loginPageElement = null;
    public HashMap<String, String> config = null;

    public LoginPage() {
        try {
            config = PropertiesFileUtility.getConfigData();
            this.loginPageElement = new ObjectRepositoryLoader().getObjectRepository("LoginPage.xml");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    public boolean enterPhoneNumberAndGetOTP(String phoneNumber) throws Exception {
        System.out.println("Inside: <enterPhoneNumberAndGetOTP> [" + phoneNumber + "]");
        boolean isDone = false;
        try {
            driver.waitForTime(5);
            driver.setText(loginPageElement.get("phoneNumberInput"), phoneNumber);
            driver.clickWhenReady(loginPageElement.get("getOTPTextBox"));

            isDone = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDone;
    }

    public boolean enterOTP(String otp) throws Exception {
        System.out.println("Inside: <enterOTP> [" + otp + "]");
        driver.waitForTime(7);
        boolean isDone = false;
        try {
            // input otp
            for (int i = 1; i <= 4; i++) {
                HashMap<String, String> otpBox = new HashMap<>(loginPageElement.get("otpBox"));
                String otpIndex = otpBox.get("XPATH").replace("##index##", Integer.toString(i));
                otpBox.put("XPATH", otpIndex);
                driver.setText(otpBox, String.valueOf(otp.charAt(i - 1)));
            }
            isDone = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDone;
    }
}
