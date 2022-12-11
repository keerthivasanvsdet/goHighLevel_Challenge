package pages;

import java.io.FileReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.By;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Common {

	public static WebDriver driver;
	public static JSONObject projectProp;
	public static JSONObject locatorsProp;
	public static Logger LOGGER = LogManager.getLogger(Common.class);

	// Get JSON data
	public static JSONObject getData() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("config.json"));
			projectProp = (JSONObject) obj;

			Object object = parser.parse(new FileReader("locators.json"));
			locatorsProp = (JSONObject) object;
			LOGGER.info("Read all data from config.json & locators.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectProp;
	}

	// Get JSON listdata as a Hash
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap getListData(String listName) {
		JSONParser parser = new JSONParser();		
		HashMap map = new HashMap();
		try {
			Object obj = parser.parse(new FileReader("config.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonList = (JSONArray) jsonObject.get(listName);
			Iterator<JSONObject> iterator = jsonList.iterator();
			while (iterator.hasNext()) {
				String data=iterator.next().toString();
				LOGGER.info(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// Connect Chrome Browser
	public static void connect() {
		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(chromeOptions);
		LOGGER.info("Launched Chrome Driver");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		LOGGER.info("Have set implicit timeout of 20 Seconds");
	}

	// Launch App URL
	public static void launchAppUrl() {
		driver.manage().window().maximize();
		driver.get(projectProp.get("URL").toString());
		LOGGER.info("Loading the Page: "+projectProp.get("URL").toString());
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		LOGGER.info("Have set implicit timeout of 30 Seconds");
	}

	// Click Send Security Code
	public static void clickSendSecurityCode() {
		driver.findElement(By.xpath(Common.locatorsProp.get("SendSecurityCodeButton").toString())).click();
		LOGGER.info("Clicked Send Security Code");
	}

	// closes the App
	public static void tearDown() {		
		driver.quit();
		LOGGER.info("Closed the Chrome Browser");
	}

	// Applies Sleep
	public static void waitSleep(long millisecs) {
		try {
			Thread.sleep(millisecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Scroll down based on Pixel
	public static void scrollDown(int start, int end) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy("+start+","+end+")", "");
		LOGGER.info("Page Scrolled Down By (0,850)");
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	// Select a random option from a list
	public static String selectAOptionFromListAtRandom(String selector1, String selector2, String text1, String text2, String text3){

		List<WebElement> allOptions=Common.driver.findElements(By.xpath(selector1));
		Common.LOGGER.info(text1 +allOptions.size());

		int randomNumber = getRandomNumber(0,allOptions.size()-1);
		for (int i=0; i<allOptions.size();i++){
			Common.LOGGER.info(text2 +i+" "+ allOptions.get(i).getAttribute("innerText"));
		}

		Common.driver.findElement(By.xpath(selector2)).click();

		Common.LOGGER.info(text3 + allOptions.get(randomNumber).getAttribute("innerText"));
		allOptions.get(randomNumber).click();

		return allOptions.get(randomNumber).getAttribute("innerText");
	}

	// Select a random option from a list -Overloaded Function
	public static String selectAOptionFromListAtRandom(String selector1,String text1,String text2,String text3){

		List<WebElement> allOptions=Common.driver.findElements(By.xpath(selector1));
		Common.LOGGER.info(text1 +allOptions.size());

		int randomNumber = getRandomNumber(0,allOptions.size()-1);
		for (int i=0; i<allOptions.size();i++){
			Common.LOGGER.info(text2 +i+" "+ allOptions.get(i).getAttribute("innerText"));
		}

		Common.LOGGER.info(text3 + allOptions.get(randomNumber).getAttribute("innerText"));
		allOptions.get(randomNumber).click();

		return allOptions.get(randomNumber).getAttribute("innerText");
	}



}
