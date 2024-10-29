package com.careautomate.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.careautomate.baseclasses.*;

import net.minidev.json.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommonMethods extends ManageBrowser {
	static Random r = new Random();

	/**
	 * 
	 * @param driver
	 * @param datepicked
	 */
	public static void calenderdatepicker(WebDriver driver, String datepicked) {
		List<WebElement> dates = driver.findElements(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr/td"));
		int sizeofrow = dates.size();
		for (int i = 0; i < sizeofrow; i++) {
			String name = dates.get(i).getText();
			if (name.equals(datepicked)) {
				dates.get(i).click();
				break;
			}
		}
	}

	/**
	 * 
	 * @param driver
	 * @throws Exception
	 */
	public static void pageload(WebDriver driver) throws Exception {
		Thread.sleep(5000);
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(400));
		wait2.until(pageLoadCondition);
	}

	/**
	 * 
	 * @param driver
	 * @param result
	 * @throws Exception
	 */
	public static String CaptureScreenshot(WebDriver driver) {
		String dest = null;
		try {
			PropertiesFile pro = new PropertiesFile();
			DateFormat dateformat = new SimpleDateFormat("ddMMyyyyHHmmss");
			String date = dateformat.format(new Date());
			File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			dest = System.getProperty("user.dir") + pro.getScreenshotspath() + "screenshot_" + date + ".png";
			FileUtils.copyFile(scr, new File(dest));
			System.out.println("here is the screenshot path" + dest);

		} catch (WebDriverException e) {
			System.out.println("Exception at webDriver" + e);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception at webDriver" + e);
		}
		return dest;
	}

	/**
	 * 
	 * @param duration_in_ms
	 */
	public static void Wait(long duration_in_ms) {
		try {
			Thread.sleep(duration_in_ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} // end Wait

	/**
	 * 
	 * @param size
	 * @param length
	 * @return
	 */
	public static String randominteger(int size, int length) {
		// int x = r.nextInt();
		int a = r.nextInt(size) + length;
		String randnum = Integer.toString(a);
		return randnum;
	}

	/**
	 * 
	 * @param size
	 * @return
	 */
	public static String randaomstring(int size) {
		try {
			String randomstrings = RandomStringUtils.randomAlphabetic(size).toUpperCase();
			return randomstrings;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public static Object[][] getDataFromJson(String jsonFileName) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String projectDirectory = System.getProperty("user.dir");
		String jsonFilePath = projectDirectory + jsonFileName;
		File testDataFile = new File(jsonFilePath);
		List<Map<String, String>> testDataList = objectMapper.readValue(testDataFile,
				new TypeReference<List<Map<String, String>>>() {
				});
		Object[][] testDataArray = new Object[testDataList.size()][7];
		for (int i = 0; i < testDataList.size(); i++) {
			Map<String, String> testDataMap = testDataList.get(i);
			testDataArray[i][0] = testDataMap.get("badgeId");
			testDataArray[i][1] = testDataMap.get("locationId");
			testDataArray[i][2] = testDataMap.get("ticketId");
			testDataArray[i][3] = testDataMap.get("name");
			testDataArray[i][4] = testDataMap.get("zoneId");
			testDataArray[i][5] = testDataMap.get("differentLocationId");
			testDataArray[i][6] = testDataMap.get("ticketIdDiffZone");

		}
		return testDataArray;
	}

	public static Map<String, Map<String, Object>> convertYAMLToHashMap(String yamlFileName) {
		Map<String, Map<String, Object>> testDataMap = new HashMap<>();
		try {
			// Load YAML file
			String projectDirectory = System.getProperty("user.dir");
			String yamlFilePath = projectDirectory + yamlFileName;
			FileInputStream inputStream = new FileInputStream(yamlFilePath);
			Yaml yaml = new Yaml();
			Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

			// Convert YAML data to HashMap
			if (yamlData != null) {
				for (Map.Entry<String, Map<String, Object>> entry : yamlData.entrySet()) {
					String testCaseName = entry.getKey();
					Map<String, Object> originalTestCaseData = entry.getValue();
					// Convert to LinkedHashMap
					Map<String, Object> linkedHashMapData = new LinkedHashMap<>(originalTestCaseData);
					testDataMap.put(testCaseName, linkedHashMapData);
				}
			} else {
				System.err.println("YAML file is empty or invalid.");
			}
		} catch (FileNotFoundException e) {
			System.err.println("YAML file not found: " + yamlFileName);
			e.printStackTrace();
		}
		return testDataMap;
	}

}