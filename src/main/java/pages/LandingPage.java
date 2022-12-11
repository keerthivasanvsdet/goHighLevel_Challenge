package pages;

import org.openqa.selenium.By;

public class LandingPage {

    public LandingPage() {

        if(Common.driver.findElement(By.xpath(Common.locatorsProp.get("UserProfile").toString())).isDisplayed()) {
            Common.LOGGER.info("Dashboard Landing Page is Displayed");
        }else {
            Common.LOGGER.info("Dashboard Landing Page isn't Displayed");
        }

    }

    // Click Calendar Option
    public void clickCalendars() {
        Common.driver.findElement(By.xpath(Common.locatorsProp.get("CalendarsMenu").toString())).click();
        Common.LOGGER.info("Clicked Calendars Menu Option in Dashboard Landing Page");
    }
}
