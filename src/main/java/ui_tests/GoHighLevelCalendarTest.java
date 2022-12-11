package ui_tests;

import pages.*;

import java.text.ParseException;

public class GoHighLevelCalendarTest extends Common {
	
	public static void main(String[] args) throws ParseException {
		getData();
		LOGGER.info("*******************************************");
		LOGGER.info(" ");
		LOGGER.info("Executing Scenario: " + "GoHighLevelCalendarTest --> Calendar Scenarios");
		LOGGER.info(" ");
		LOGGER.info("*******************************************");
		LOGGER.info("Product: " + projectProp.get("Name"));
		LOGGER.info("Author: " + projectProp.get("Author"));
		connect();
		launchAppUrl();
		LoginPage hp = new LoginPage();
		hp.logIn();
		Common.clickSendSecurityCode();
		LandingPage lp = new LandingPage();
		lp.clickCalendars();
		CalendarsPage cp=new CalendarsPage();
		cp.navigateToCalendarUrl();
		AppointmentPage ap = new AppointmentPage();
		ap.navigateToAppointmentUrl();
		ap.selectTimeZone();
		ap.selectAvailableDate();
		ap.selectTimeSlot();
		ap.clickContinue();
		ap.enterInformation();
		ap.clickScheduleMeeting();
		ap.validateDataAfterBooking();
		ap.navigateToAppointmentsListUrl();
		ap.validateBookingInAppointmentsPage();
		tearDown();
	}

}
