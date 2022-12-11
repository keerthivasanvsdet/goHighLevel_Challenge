package pages;

import org.openqa.selenium.By;

public class LoginPage {
	
	public LoginPage() {
		
		if(Common.driver.findElement(By.xpath(Common.locatorsProp.get("LogInPageText").toString())).getText().
				equals(Common.projectProp.get("LogInPageText").toString())) {
			Common.LOGGER.info("Login Page is Displayed");
		}else {
			Common.LOGGER.info("Login Page isn't Displayed");
		}

	}
	
	public void logIn() {

		Common.driver.findElement(By.xpath(Common.locatorsProp.get("EmailInput").toString())).sendKeys(Common.projectProp.get("Email").toString());
		Common.driver.findElement(By.xpath(Common.locatorsProp.get("PasswordInput").toString())).sendKeys(Common.projectProp.get("Password").toString());
		Common.driver.findElement(By.xpath(Common.locatorsProp.get("SignInButton").toString())).click();
		Common.LOGGER.info("Clicked Sign in button in Login Page");
	}

}
