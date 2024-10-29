package com.careautomate.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.careautomate.baseclasses.*;

public class Retry extends ManageBrowser implements IRetryAnalyzer {
	private int count = 0;
	private static int maxTry = pro.RetryMaxLimit(); // Run the failed test 2 times

	@Override
	public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) { // Check if test not succeed
			if (count < maxTry) { // Check if maxTry count is reached
				count++; // Increase the maxTry count by 1
				iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed and take base64Screenshot
				getTest().getExtent().keepLastRetryOnly(true);
				extendReportsFailOperations(iTestResult); // ExtentReports fail operations
				return true; // Tells TestNG to re-run the test
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
			getTest().getExtent().keepLastRetryOnly(true);
		}
		return false;
	}

	public void extendReportsFailOperations(ITestResult iTestResult) {

		Object testClass = iTestResult.getInstance();
		WebDriver webDriver = getDriver();
		String base64Screenshot = "data:image/png;base64,"
				+ ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
		getTest().log(Status.FAIL, "Test Failed",
				getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));

	}
}