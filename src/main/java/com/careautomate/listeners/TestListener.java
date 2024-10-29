package com.careautomate.listeners;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import com.careautomate.baseclasses.*;
import com.careautomate.logs.*;
import com.careautomate.utils.*;

public class TestListener extends ManageBrowser implements ITestListener {
	//JiraServiceProvider js = new JiraServiceProvider();
	PropertiesFile prop = new PropertiesFile();

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		Log.info("I am in onStart method " + iTestContext.getName());
		iTestContext.setAttribute("WebDriver", getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		Log.info("I am in onFinish method " + iTestContext.getName());

	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		Log.info(getTestMethodName(iTestResult) + " test is starting.");
	}

//	@Override
//	public void onTestSuccess(ITestResult iTestResult) {
//
//		Log.info(getTestMethodName(iTestResult) + " test is succeed.");
//		// ExtentReports log operation for passed tests.
//		getTest().log(Status.PASS, "Test passed successfully");
//		if (prop.getJiraUpdation().equals("True")) {
//			updateJira(iTestResult, "PASS");
//		}
//	}
//
//	@Override
//	public void onTestFailure(ITestResult iTestResult) {
//
//		Log.info(getTestMethodName(iTestResult) + " test is failed.");
//		// Get driver from BaseTest and assign to local webdriver variable.
//		Object testClass = iTestResult.getInstance();
//		getTest().fail("Test Failed , please find screenshot attached above "
//				+ getTest().addScreenCaptureFromPath(CommonMethods.CaptureScreenshot(getDriver())));
//		if (prop.getJiraUpdation().equals("True")) {
//			updateJira(iTestResult, "FAIL");
//		}
//	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		Log.info(getTestMethodName(iTestResult) + " test is failed.");
		// ExtentReports log operation for skipped tests.
		getTest().log(Status.FAIL, "Test Failed");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

//	public void updateJira(ITestResult iTestResult, String testResult) {
//		String TestcaseId = iTestResult.getMethod().getConstructorOrMethod().getMethod()
//				.getAnnotation(JiraTestcaseUpdation.class).TestcaseID();
//		try {
//			if (driver != null) {
//				driver.quit();
//			}
//			String[] testcases = TestcaseId.split(",");
//			for (String testcase : testcases) {
//				System.out.println(testcase);
//				js.updateTestCaseExecution(testcase, testResult);
//			}
//
//		} catch (Exception e) {
//			getTest().fail("Exception details: " + TestcaseId + " updation failed in jira");
//			Assert.fail();
//		}
//	}
}