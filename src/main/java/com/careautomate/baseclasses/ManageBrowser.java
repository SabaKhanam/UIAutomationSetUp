package com.careautomate.baseclasses;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterClass;
import org.testng.ITestResult;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.careautomate.utils.ExtentManager;
import com.careautomate.utils.PropertiesFile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ManageBrowser extends ExtentManager {

	public static WebDriver driver;
	public static ExtentReports reports;
	public static PropertiesFile pro = new PropertiesFile();
	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();
	String url = null;

	/**
	 * Browser Initialization
	 * 
	 * @param browser
	 * @return
	 * @throws Exception
	 */
	public WebDriver getdriver(String browser) throws Exception {
		System.out.println("Before method executing");
		if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions optionsC = new ChromeOptions();
			optionsC.setAcceptInsecureCerts(true);
			optionsC.addArguments("--use-fake-ui-for-media-stream");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(optionsC);
			url = pro.getUrl();
		} else if (browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else if (browser.equalsIgnoreCase("headless")) {
			System.out.println("****Headless Browser*****");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setAcceptInsecureCerts(true);
			chromeOptions.addArguments("--use-fake-ui-for-media-stream");
			chromeOptions.addArguments("--headless");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
			url = pro.getUrl();
		}
		dr.set(driver);
		getDriver().manage().window().maximize();
		getDriver().get(url);
		getDriver().manage().timeouts().implicitlyWait(8000, TimeUnit.MILLISECONDS);

		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return dr.get();
	}

	
	@BeforeSuite(alwaysRun = true)
	public void setup() throws Exception {
		reports = ExtentManager.createExtentReports();
		System.out.println("Before Suite Executed");
	}

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	public void setup(String browser) throws Exception {
		driver = getdriver(browser);
	}

	@AfterMethod(alwaysRun = true)
	public void tearup(ITestResult result) throws Exception {
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			System.out.println("***************");
			System.out.println("Exception Message: " + throwable.getMessage());
			System.out.println("Exception Stack Trace: ");
			throwable.printStackTrace();
			System.out.println("***************");
		}

		if (driver != null) {
			driver.quit();
		}
	}

	@AfterClass(alwaysRun = true)
	public void teardown() {
		try {
			reports.flush();
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void tear() {
		if (driver != null) {
			driver.quit();
		}
		reports.flush();
	}
}
