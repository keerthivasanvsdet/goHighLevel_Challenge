package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AppointmentPage {

    static HashMap<String,String> bookingData=new HashMap();
    static String bookedDate="";

    public AppointmentPage(){

        if(Common.driver.findElement(By.xpath(Common.locatorsProp.get("AppointmentPageHeader").toString())).isDisplayed()) {
            Common.LOGGER.info("Appointment Page is Displayed");
        }else {
            Common.LOGGER.info("Appointment Page isn't Displayed");
        }

    }

    // Navigate to Appointment URL
    public static void navigateToAppointmentUrl() {
        Common.driver.navigate().to(Common.projectProp.get("AppointmentURL").toString());
        Common.LOGGER.info("Loading the Page: "+Common.projectProp.get("AppointmentURL").toString());
    }

    public void selectTimeZone() {

        String timezone=Common.selectAOptionFromListAtRandom(Common.locatorsProp.get("TimeZoneDropDown").toString(),Common.locatorsProp.get("TimeZoneDropDownArrow").toString(),
                "Time Zone Options Count: ", "Drop Down Option Text: ","Drop Down Selected Randomly: ");

        bookingData.put("Timezone",timezone);
    }

    public void selectAvailableDate() {

        String date=Common.selectAOptionFromListAtRandom(Common.locatorsProp.get("AvailableDates").toString(),
                "Available Dates Count: ", "Available Dates Text: ","Date Selected Randomly: ");

        bookingData.put("Date",date);
    }

    public void selectTimeSlot() {

        String timeSlot=Common.selectAOptionFromListAtRandom(Common.locatorsProp.get("TimeSlots").toString(),
                "Time Slots Count: ", "Time Slot Text: ","Time Slot Selected Randomly: ");

        bookingData.put("TimeSlot",timeSlot);
    }

    public void clickContinue(){

        Common.driver.findElement(By.xpath(Common.locatorsProp.get("ContinueButton").toString())).click();
        Common.LOGGER.info("Clicked Continue Button in Appointment Page");
    }

    public void enterInformation(){

        Common.driver.findElement(By.xpath(Common.locatorsProp.get("FirstNameField").toString())).sendKeys(Common.projectProp.get("FirstName").toString());
        Common.LOGGER.info("First Name: "+Common.projectProp.get("FirstName"));
        Common.driver.findElement(By.xpath(Common.locatorsProp.get("LastNameField").toString())).sendKeys(Common.projectProp.get("LastName").toString());
        Common.LOGGER.info("Last Name: "+Common.projectProp.get("LastName"));
        Common.driver.findElement(By.xpath(Common.locatorsProp.get("PhoneNumberField").toString())).sendKeys(Common.projectProp.get("PhoneNumber").toString());
        Common.LOGGER.info("PhoneNumber: "+Common.projectProp.get("PhoneNumber"));
        Common.driver.findElement(By.xpath(Common.locatorsProp.get("EmailField").toString())).sendKeys(Common.projectProp.get("TestEmail").toString());
        Common.LOGGER.info("Email: "+Common.projectProp.get("TestEmail"));
        Common.driver.findElement(By.xpath(Common.locatorsProp.get("AdditionalInfoField").toString())).sendKeys(Common.projectProp.get("AddInfo").toString());
        Common.LOGGER.info("AdditionalInfo: "+Common.projectProp.get("AddInfo"));
    }

    public void clickScheduleMeeting(){

        Common.driver.findElement(By.xpath(Common.locatorsProp.get("ScheduleMeetingButton").toString())).click();
        Common.LOGGER.info("Clicked ScheduleMeeting Button in Appointment Page");
        Common.driver.findElement(By.xpath(Common.locatorsProp.get("BookingConfirmationText").toString())).isDisplayed();
    }

    public static void validateDataAfterBooking(){

        String bookedTimeZone=Common.driver.findElement(By.xpath(Common.locatorsProp.get("BookedTimeZone").toString())).getText();
        Common.LOGGER.info("BookedTimeZone: "+bookedTimeZone);
        bookedDate=Common.driver.findElement(By.xpath(Common.locatorsProp.get("BookedDate").toString())).getText();
        Common.LOGGER.info("BookedDate: "+bookedDate);
        String bookedTime=Common.driver.findElement(By.xpath(Common.locatorsProp.get("BookedTime").toString())).getText();
        Common.LOGGER.info("BookedTime: "+bookedTime);
        Assert.assertEquals(bookingData.get("Timezone"),bookedTimeZone);
        if(bookedDate.contains(bookingData.get("Date"))){
            Common.LOGGER.info("Date is as per the booking made.");
        }else{
            Assert.assertFalse("Date isn't as per the booking made.",false);
        }
        if(bookedTime.contains(bookingData.get("TimeSlot"))){
            Common.LOGGER.info("TimeSlot is as per the booking made.");
        }else{
            Assert.assertFalse("TimeSlot isn't as per the booking made.",false);
        }
    }

    public void validateBookingInAppointmentsPage() throws ParseException {

        String testName=Common.driver.findElement(By.xpath(Common.locatorsProp.get("TestNameField").toString())).getText();
        Common.LOGGER.info("TestName: "+testName);
        String requestedTime=Common.driver.findElement(By.xpath(Common.locatorsProp.get("RequestedTimeField").toString())).getText();
        Common.LOGGER.info("RequestedTime: "+requestedTime);
        String appointmentNotes=Common.driver.findElement(By.xpath(Common.locatorsProp.get("AppointmentNotesField").toString())).getText();
        Common.LOGGER.info("AppointmentNotes: "+appointmentNotes);
        Assert.assertEquals(Common.projectProp.get("FirstName")+" "+Common.projectProp.get("LastName"),testName);
        Assert.assertEquals(Common.projectProp.get("AddInfo"),appointmentNotes);
        String[] timeZone=bookingData.get("Timezone").split(" ");
        Common.LOGGER.info("Parsed TimeZone: "+timeZone[1]);

        Calendar cal = Calendar.getInstance();
        String[] bookedDateArray=bookedDate.split(" ");
        String month=bookedDateArray[1];
        String dateInString = bookedDateArray[2].replace(",","")+" "+month+" "+bookedDateArray[3]+", "+bookedDateArray[5]+" "+bookedDateArray[6];
        String DATE_FORMAT = "dd MMM yyyy, hh:mm a";
        LocalDateTime ldt = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT));

        ZoneId zoneId = ZoneId.of(timeZone[1]);
        Common.LOGGER.info("Current TimeZone : " + zoneId);

        ZonedDateTime zonedDateTime = ldt.atZone(zoneId);
        Common.LOGGER.info("Date ("+timeZone[1]+") : " + zonedDateTime);

        ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
        Common.LOGGER.info("Conversion TimeZone : " + istZoneId);

        ZonedDateTime istDateTime = zonedDateTime.withZoneSameInstant(istZoneId);
        Common.LOGGER.info("Date (IST) : " + istDateTime);

        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Common.LOGGER.info("---DateTimeFormatter---");
        Common.LOGGER.info("Date ("+timeZone[1]+") : " + format.format(zonedDateTime));
        Common.LOGGER.info("Date (IST) : " + format.format(istDateTime));

        Assert.assertEquals(requestedTime.toUpperCase(),format.format(istDateTime).toUpperCase());
    }

    // Navigate to AppointmentsList URL
    public static void navigateToAppointmentsListUrl() {
        Common.driver.navigate().to(Common.projectProp.get("AppointmentsListURL").toString());
        Common.LOGGER.info("Loading the Page: "+Common.projectProp.get("AppointmentsListURL").toString());
    }
}
