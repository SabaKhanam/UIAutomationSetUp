package com.careautomate.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.careautomate.baseclasses.ActionKeywords;
import com.careautomate.baseclasses.ManageBrowser;
import com.careautomate.pages.SignUpPage;

public class SignUpTest extends ManageBrowser {

	@Test(groups = { "SignOnTest" })
	public void TC_SignUpWIthValidDetails() {

//		// Setup WebDriverManager
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://care-automate.vercel.app/login");
//		System.out.println("Care automate website launched.");
		startTest(Thread.currentThread().getStackTrace()[1].getMethodName().toString(), "Testcase sign on");
		SignUpPage signUpPage = new SignUpPage(getDriver());
		signUpPage.validateSignUpPage();
		signUpPage.enterEmail("test@example.com");
		signUpPage.enterPassword("Test@1234");
	}

}
//
////package com.kpisolutions.cvsptl.tests;
////
////import org.junit.Assert;
////import org.testng.annotations.Test;
////
////import com.kpisolutions.cvsptl.baseclasses.ActionKeywords;
////import com.kpisolutions.cvsptl.baseclasses.ManageBrowser;
////import com.kpisolutions.cvsptl.data.SqlDbConnection;
////import com.kpisolutions.cvsptl.pages.ChangeZoneDialogBox;
////import com.kpisolutions.cvsptl.pages.HamburgerMenu;
////import com.kpisolutions.cvsptl.pages.HomePage;
////import com.kpisolutions.cvsptl.pages.LoginSuccessPage;
////import com.kpisolutions.cvsptl.pages.ScanPickTicketPage;
////import com.kpisolutions.cvsptl.pages.SignOnPage;
////import com.kpisolutions.cvsptl.pages.TicketDetailsPage;
////import com.kpisolutions.cvsptl.pages.ZoneAssignationPage;
////import com.kpisolutions.cvsptl.pages.ZoneAssignationScanPage;
////import com.kpisolutions.cvsptl.utils.CommonMethods;
////import com.kpisolutions.cvsptl.utils.JiraTestcaseUpdation;
////import com.kpisolutions.cvsptl.utils.TestDataHelper;
////import com.kpisolutions.cvsptl.utils.TestDataprovider;
////
////public class SignOnTest extends ManageBrowser {
////	ActionKeywords performAction;
////	SignOnPage signonPage = new SignOnPage(getDriver());
////	LoginSuccessPage loginSuccess = new LoginSuccessPage(getDriver());
////	HamburgerMenu hamburger = new HamburgerMenu(getDriver());
////	CommonMethods cmnMethods = new CommonMethods();
////
////	SqlDbConnection db = new SqlDbConnection();
////	ZoneAssignationPage zoneAssignationPage = new ZoneAssignationPage(getDriver());
////	TestDataHelper testData = new TestDataHelper();
////
////	String badgeId, name, hamburgerOption, locationId, ticketId, testScenario = "SignOnTest";
////
////	@Test(groups = { "CVSFullSuite", "SignOnTest" })
////	@JiraTestcaseUpdation(TestcaseID = "WES-3652,WES-6183")
////	public void TC_SignOnTest_ValidateErrorMessageOnInvalidBadgeId() {
////
////		startTest(Thread.currentThread().getStackTrace()[1].getMethodName().toString(),
////				"Testcases Covered:WES_3652 Userstories:3153").assignDevice("WebApplication")
////				.assignCategory("CVSFullSuite").assignCategory("SignOnTest");
////		signonPage.validateErrorMessage(cmnMethods.randominteger(99999, 6));
////	}
////
////	@Test(groups = { "CVSFullSuite", "SignOnTest" })
////	@JiraTestcaseUpdation(TestcaseID = "WES-3651,WES-3657,WES-3658,WES-3659,WES-3661")
////	public void TC_SignOnTest_ValidateLoginAndUIElements() {
////		try {
////			startTest(Thread.currentThread().getStackTrace()[1].getMethodName().toString(),
////					"Testcases Covered: WES-3651,WES-3657,WES-3658,WES-3659,WES-3661 Userstories:3153")
////					.assignDevice("WebApplication").assignCategory("CVSFullSuite").assignCategory("SignOnTest");
////
////			// Access test data for test case
////
////			badgeId = testData.getTestData(testScenario, "badgeId");
////			name = testData.getTestData(testScenario, "name");
////			locationId = testData.getTestData(testScenario, "locationId");
////			ticketId = testData.getTestData(testScenario, "ticketId");
////			db.getUnexpiredSession(badgeId);
////			zoneAssignationPage.resetPickPriorityData(locationId);
////			signonPage.enterBadgeId(badgeId);
////			loginSuccess.loginSuccessNextButton(name, badgeId);
////			hamburger.signOut();
////		} finally {
////			db.getUnexpiredSession(badgeId);
////			zoneAssignationPage.resetPickPriorityData(locationId);
////		}
////	}
////}