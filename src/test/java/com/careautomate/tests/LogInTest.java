package com.careautomate.tests;

import org.testng.annotations.Test;
import com.careautomate.pages.LogInPage;

public class LogInTest {

	@Test
	public void TC_LogInWithValidDetails() {
		LogInPage loginPage = new LogInPage();
		loginPage.launchWebsite("https://care-automate.vercel.app/login");
		loginPage.validateLogInPage();
		loginPage.enterEmailInLogInPage("test@gmail.com");
		loginPage.enterPasswordInLogInPage("123#Sabakhan");
		loginPage.clickLoginButton();
		//loginPage.getDriver().quit();
	}
}
