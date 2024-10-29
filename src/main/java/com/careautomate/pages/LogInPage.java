package com.careautomate.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import com.careautomate.baseclasses.ActionKeywords;
import com.careautomate.baseclasses.ManageBrowser;

public class LogInPage extends ManageBrowser {

	public ActionKeywords performAction;

	public LogInPage() {
		// Initialize ActionKeywords using getDriver() from ManageBrowser
		performAction = new ActionKeywords(getDriver());
	}

	By Email = By.xpath("//input[@placeholder='Email']");
	By Password = By.xpath("//input[@placeholder='Password']");
	By LoginButton = By.xpath("//button[text()='Login']");
	By SignUpButton = By.xpath("//a[contains(text(), \"Sign Up\")]");
	By LoginImage = By.xpath("//div[@class='login-image-container']//img[@class='signup-image']");

	public void launchWebsite(String url) {
		try {
			getDriver().get(url);
			getTest().info("Launched website with URL: " + url);
		} catch (NullPointerException e) {
			System.out.println("ExtentTest is not initialized: " + e.getMessage());
			Assert.fail("ExtentTest not initialized. Check `getTest()` method for null handling.");
		} catch (Exception e) {
			getTest().fail("Exception occurred while launching website:\r\n" + e);
			Assert.fail("Failed to launch website.");
		}
	}

	public void enterEmailInLogInPage(String text) {
		try {
			performAction.enterInput(Email, text, "Email entered in input box");
		} catch (AssertionError e) {
			getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;
		} catch (Exception e) {
			getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}
	}

	public void enterPasswordInLogInPage(String text) {
		try {
			performAction.enterInput(Password, text, "Password entered in input box");
		} catch (AssertionError e) {
			getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;
		} catch (Exception e) {
			getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}
	}

	public void clickLoginButton() {
		try {
			performAction.click(LoginButton, "Clicked Login button");
		} catch (AssertionError e) {
			getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;
		} catch (Exception e) {
			getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}
	}

	public void validateLogInPage() {
		try {
			Assert.assertTrue(performAction.findElement(LoginImage).isDisplayed(), "Image is not present");
			Assert.assertTrue(performAction.findElement(Email).isDisplayed(), "Email is not present");
			Assert.assertTrue(performAction.findElement(Password).isDisplayed(), "Password is not present");
			Assert.assertTrue(performAction.findElement(SignUpButton).isDisplayed(), "Sign Up button is not present");
			Assert.assertTrue(performAction.findElement(LoginButton).isDisplayed(), "Log in button is not present");

			getTest().info(
					"UI elements LogIn image, email, password, login button and sign up button are present on LogIn page");
		} catch (AssertionError e) {
			getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;
		} catch (Exception e) {
			getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}
	}
}
