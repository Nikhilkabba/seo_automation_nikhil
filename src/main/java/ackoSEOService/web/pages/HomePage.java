package ackoSEOService.web.pages;

import java.util.HashMap;

import acko.utilities.ObjectRepositoryLoader;
import acko.utilities.PropertiesFileUtility;

/**
 * This class extends HomePage class and takes the base class object repository
 * if not defined in the constructor. Methods used for creating test cases can
 * be placed here.
 *
 */
public class HomePage extends CommonPage {
	HashMap<String, HashMap<String, String>> homePageElement = null;
	public HashMap<String, String> config = null;

	public HomePage() {
		try {
			config = PropertiesFileUtility.getConfigData();
			this.homePageElement = new ObjectRepositoryLoader().getObjectRepository("HomePage.xml");
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public boolean clickOnLogin() throws Exception {
		boolean isDone = false;
		try {
			driver.click(homePageElement.get("loginButton"));
			isDone = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDone;

	}

}
