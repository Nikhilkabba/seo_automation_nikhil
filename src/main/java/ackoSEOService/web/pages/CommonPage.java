/**

 */
package ackoSEOService.web.pages;

import java.util.HashMap;

import acko.utilities.ObjectRepositoryLoader;
import acko.utilities.PropertiesFileUtility;
import acko.web.actions.Driver;

public class CommonPage {

    public static Driver driver;
	HashMap<String, HashMap<String, String>> commonPageElement = null;
	
	HashMap<String, String> xpathHandler = new HashMap<String, String>  ();
	
	public HashMap<String, String> config = null;

    public CommonPage() {
        try {
			driver = new Driver();
			config = PropertiesFileUtility.getConfigData();
			this.commonPageElement = new ObjectRepositoryLoader().getObjectRepository("CommonPage.xml");
		} catch (Exception e) {	
			e.printStackTrace();
		}
        
    }

    public boolean checkPresenceOfElement(String elementText) throws Exception {
		boolean isPresent = false;
		try {
			    String genericXpath = (commonPageElement.get("genericXpath").get("XPATH")).replace("##value##", elementText);
			    
			    xpathHandler.put("XPATH", genericXpath);
			    commonPageElement.put(elementText, xpathHandler);

			    isPresent=  driver.checkIfElementIsPresent(commonPageElement.get(elementText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isPresent;
		
	}

	public HashMap<String,String> getXpath(String xpath, String oldValue, String newValue ) {

		HashMap<String,String > map=new HashMap<>();
		String updatedXpath=xpath.replace(oldValue,newValue);
		map.put("XPATH",updatedXpath);
		 return map;
	}
}
