package com.careautomate.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.careautomate.baseclasses.ActionKeywords;
import com.careautomate.baseclasses.ManageBrowser;

import org.openqa.*;

/**
 * Hello world!
 *
 */
public class SignUpPage extends ManageBrowser {
//	private WebDriver driver;
	public ActionKeywords performAction;

	public SignUpPage(WebDriver driver) {
		this.driver = driver;
	}

	By SignUpImage = By.xpath("//div[@class='left-section']//img[@class='signup-image']");
	By HeaderTextInSignUpPage = By
			.xpath("//h3[contains(., 'A Small Step To Begin Automation Of') and contains(., 'Your Housing Services')]");
	By FirstName = By.xpath("//input[@name='firstName']");
	By LastName = By.xpath("//input[@name='lastName']");
	By Email = By.xpath("//input[@name='email']");
	By CompanyName = By.xpath("//input[@name='companyName']");
	By State = By.xpath("//select[@name='state']");
	By AlaskaState = By.xpath("//option[@value='Alaska']");
	By MobileNumber = By.xpath("//input[@name='mobileNumber']");
	By Password = By.xpath("//input[@name='password']");
	By EyeIconForPassword = By
			.xpath("//input[@name='password']/following-sibling::button[contains(@class, 'toggle-password')]");
	By ConfirmPassword = By.xpath("//input[@name='confirmPassword']");
	By EyeIconForConfirmPassword = By
			.xpath("//input[@name='confirmPassword']/following-sibling::button[contains(@class, 'toggle-password')]");
	By SignUpButton = By.xpath("//button[text()='Sign Up']");
	By SignInButton = By.xpath("//a[text()='Sign In']");

	By PasswordErrorMessage = By.xpath(
			"//p[contains(text(),'Password must be at least 8 characters long and include 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.')]");
	By StateErrorMessage = By.xpath("//p[contains(text(),'State is required.')]");
	By ConfirmPasswordErrorMessage = By.xpath("//p[contains(text(),'Passwords do not match.')]");

	public void validateSignUpPage() {
		try {
			performAction = new ActionKeywords(getDriver());
			Assert.assertTrue(performAction.findElement(SignUpImage).isDisplayed(), "Image is not present");
			Assert.assertTrue(performAction.findElement(HeaderTextInSignUpPage).isDisplayed(),
					"Header text element is not present");
			Assert.assertTrue(performAction.findElement(FirstName).isDisplayed(), "First name is not present");
			Assert.assertTrue(performAction.findElement(LastName).isDisplayed(), "Last name is not present");
			Assert.assertTrue(performAction.findElement(Email).isDisplayed(), "Email is not present");
			Assert.assertTrue(performAction.findElement(CompanyName).isDisplayed(), "Company name is not present");
			Assert.assertTrue(performAction.findElement(State).isDisplayed(), "State is not present");
			Assert.assertTrue(performAction.findElement(MobileNumber).isDisplayed(), "Mobile number is not present");
			Assert.assertTrue(performAction.findElement(Password).isDisplayed(), "Password is not present");
			Assert.assertTrue(performAction.findElement(ConfirmPassword).isDisplayed(),
					"Confirm Password is not present");
			Assert.assertTrue(performAction.findElement(SignUpButton).isDisplayed(), "Sign Up button is not present");
			Assert.assertTrue(performAction.findElement(SignInButton).isDisplayed(), "Sign in button is not present");

//			getTest().info(
//					" UI elements signup image, header text, firts name, last name, email, state, phone number, company name, password, confirm password box, sign up button are present in signup page");

		} catch (AssertionError e) {
			System.err.println("Assertion failed:\n" + e.getMessage());
			// getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;

		} catch (Exception e) {
			System.err.println("Exception details:\n" + e);
			// getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}

	}

	public void enterEmail(String text) {
		try {
			performAction = new ActionKeywords(getDriver());
			performAction.enterInput(Email, text, "email entered in input box");

		} catch (AssertionError e) {
			// getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;

		} catch (Exception e) {
			// getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}

	}

	public void enterPassword(String text) {
		try {
			performAction = new ActionKeywords(getDriver());
			performAction.enterInput(Password, text, "Password entered in input box");

		} catch (AssertionError e) {
			// getTest().fail("Assertion failed:\r\n" + e.getMessage());
			throw e;

		} catch (Exception e) {
			// getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}

	}

}
