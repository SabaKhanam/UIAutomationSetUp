package com.careautomate.utils;

import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
	public static PropertiesFile pro;
	public static String reportpath;

	// Synchronized method to initialize ExtentReports
	public synchronized static ExtentReports createExtentReports() {
		if (extent == null) {
			try {
				pro = new PropertiesFile();

				reportpath = System.getProperty("user.dir") + System.getProperty("file.separator") + pro.getExtentPath()
						+ "_" + (new SimpleDateFormat("ddMMyyyyHHmmss")).format(new Date()) + ".html";
				System.out.println("Report path: " + reportpath);

				ExtentSparkReporter reporter = new ExtentSparkReporter(reportpath);
				reporter.config().setCss(".badge-primary{background-color:#112843}");
				reporter.config().setJs("document.getElementsByClassName('card-title')[1].innerText='Test steps';"
						+ "document.getElementsByClassName('card-header')[3].innerText='Groups';"
						+ "document.getElementById('parent-analysis').style='display: block; width: 500px; height: 180px';"
						+ "document.getElementById('events-analysis').style='display: block; width: 500px; height: 180px';"
						+ "document.getElementsByTagName('h3')[0].style='font-size: 18px';"
						+ "document.getElementsByTagName('h3')[1].style='font-size: 18px';"
						+ "document.getElementsByClassName('card-title')[0].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('card-title')[1].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('card-header')[2].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('card-header')[3].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('card-header')[4].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('m-b-0')[0].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('m-b-0')[1].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('m-b-0')[2].style='font-size: 15px;font-weight: bold';"
						+ "document.getElementsByClassName('m-b-0')[3].style='font-size: 15px;font-weight: bold';");

				reporter.loadXMLConfig(
						new File(System.getProperty("user.dir") + "\\src\\test\\resources\\extent_config.xml"));
				ViewName[] DEFAULT_ORDER = { ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.DEVICE, ViewName.TEST };

				reporter.viewConfigurer().viewOrder().as(DEFAULT_ORDER);
				extent = new ExtentReports();
				extent.attachReporter(reporter);
				extent.setSystemInfo("Environment", "QA");
				extent.setSystemInfo("Browser", "chrome");
				extent.setSystemInfo("OS", System.getProperty("os.name"));
				extent.setSystemInfo("IP Address", InetAddress.getLocalHost().getHostAddress());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return extent;
	}

	// Synchronized method to start a test and add it to ThreadLocal
//	public static synchronized ExtentTest startTest(String name, String description) {
//		if (extent == null) {
//			createExtentReports();
//		}
//		ExtentTest extentTest = extent.createTest(name, description);
//		extentTestMap.put((int) Thread.currentThread().getId(), extentTest);
//		
//		test.set(extentTest);
//		return extentTest;
//	}
//
//	// Method to get the current test for logging, with null checks
//	public static synchronized ExtentTest getTest() {
//		ExtentTest currentTest = extentTestMap.get((int) Thread.currentThread().getId());
//		if (currentTest == null) {
//			throw new IllegalStateException(
//					"ExtentTest is not initialized. Please start the test first using startTest().");
//		}
//		return currentTest;
//	}

	public static synchronized ExtentTest startTest(String testName, String description) {
		ExtentTest test = extent.createTest(testName, description);
		extentTestMap.put((int) Thread.currentThread().getId(), test);
		return test;
	}

	public static synchronized ExtentTest createTest(String testName) {
		ExtentTest extentTest = extent.createTest(testName);
		test.set(extentTest);
		extentTestMap.put((int) Thread.currentThread().getId(), extentTest);
		return extentTest;
	}

	// Retrieves the test associated with the current thread name
	public static synchronized ExtentTest getTest() {
		return extentTestMap.get((int) Thread.currentThread().getId());

	}

	// Helper methods for logging
	public static synchronized void logPass(String message) {
		getTest().pass(message);
	}

	public static synchronized void logFail(String message) {
		getTest().fail(message);
	}

	public static synchronized void logInfo(String message) {
		getTest().info(message);
	}

	// Flush the report
	public static synchronized void flushExtentReport() {
		if (extent != null) {
			extent.flush();
		}
	}
}
