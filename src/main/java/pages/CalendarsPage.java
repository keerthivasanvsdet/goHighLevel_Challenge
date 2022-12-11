package pages;

import org.openqa.selenium.By;

public class CalendarsPage {

    public CalendarsPage(){

        if(Common.driver.findElement(By.xpath(Common.locatorsProp.get("CalendarComponent").toString())).isDisplayed()) {
            Common.LOGGER.info("Calendar's Page is Displayed");
        }else {
            Common.LOGGER.info("Calendar's Page isn't Displayed");
        }

    }

    // Navigate to Calendar URL
    public static void navigateToCalendarUrl() {
        Common.driver.navigate().to(Common.projectProp.get("CalendarURL").toString());
        Common.LOGGER.info("Loading the Page: "+Common.projectProp.get("CalendarURL").toString());
    }
}
