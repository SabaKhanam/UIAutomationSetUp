package com.careautomate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

	Properties prop;

	/**
	 * @Function to read properties file
	 * @throws Exception
	 */
	public PropertiesFile() {

		try {
			String path = System.getProperty("user.dir") + System.getProperty("file.separator")
					+ "\\src\\test\\resources\\Config\\" + "qa.properties";
			File config = new File(path);
			FileInputStream fis = new FileInputStream(config);
			prop = new Properties();
			prop.load(fis);
		} catch (IOException e) {
			System.out.println("Properties file not found please check in Propertiesfile.class");
			e.printStackTrace();
		}
	}

	public String getUrl() {
		String uname = prop.getProperty("CareAutomateApplicationurl");
		return uname;
	}

	public String getApiUrl() {
		String uname = prop.getProperty("APIApplicationurl");
		return uname;
	}

	public String getKPIUsername() {
		String un = prop.getProperty("kpiusername");
		return un;
	}

	public String getDB() {
		String un = prop.getProperty("DB");
		return un;
	}

	public String getStorageDB() {
		String un = prop.getProperty("StorageDB");
		return un;
	}

	public String getWorkDB() {
		String un = prop.getProperty("WorkDB");
		return un;
	}

	public String getPtlDB() {
		String un = prop.getProperty("OtherDB");
		return un;
	}

	public String getKPIPassword() {
		String un = prop.getProperty("kpipassword");
		return un;
	}

	public String getExtentPath() {
		String extentpath = prop.getProperty("Extentreportspath");
		return extentpath;
	}

	public String getScreenshotspath() {
		String scrnshotpath = prop.getProperty("screenshotspath");
		return scrnshotpath;
	}

	public int RetryMaxLimit() {
		int un = Integer.parseInt(prop.getProperty("Retry_limit"));
		return un;
	}

//	public int ShortWait() {
//		int un = Integer.parseInt(prop.getProperty("ShortWait"));
//		return un;
//	}
//
//	public Duration LongWait() {
//		int seconds = Integer.parseInt(prop.getProperty("LongWait"));
//		return Duration.ofSeconds(seconds);
//	}

	public int ShortWait() {
		return Integer.parseInt(prop.getProperty("ShortWait")); // assuming "ShortWait" is defined in your properties
	}

	public int LongWait() {
		return Integer.parseInt(prop.getProperty("LongWait")); // assuming "LongWait" is defined in your properties
	}

//	public Duration LongWait() {
//		Duration un = Integer.parseInt(prop.getProperty("LongWait"));
//		return un;
//	}

	public String TestData() {
		String un = prop.getProperty("TestDataPath");
		return un;
	}

	public String YamlTestData() {
		String un = prop.getProperty("YamlTestDataPath");
		return un;
	}

	public String getDBServer() {
		String un = prop.getProperty("DBServer");
		return un;
	}

	public String getDBUsername() {
		String un = prop.getProperty("DBUsername");
		return un;
	}

	public String getDBPassword() {
		String un = prop.getProperty("DBPassword");
		return un;
	}

}