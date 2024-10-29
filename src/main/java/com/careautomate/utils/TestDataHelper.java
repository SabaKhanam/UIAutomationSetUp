package com.careautomate.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.careautomate.baseclasses.ManageBrowser;
import com.careautomate.data.SqlDbConnection;

public class TestDataHelper extends ManageBrowser {

	Map<String, Map<String, Object>> testDataMap;
	String value = null, operatorsId = null;
	SqlDbConnection db = new SqlDbConnection();

	public String getTestData(String testCaseName, String parameter) {

		try {
			testDataMap = CommonMethods.convertYAMLToHashMap(pro.YamlTestData());

			// Access test data for test case

			Map<String, Object> testData = testDataMap.get(testCaseName);
			/*
			 * if(parameter.equals("badgeId")) {
			 * operatorsId=testData.get(parameter).toString(); String[] li=
			 * operatorsId.split(","); for (String a : li) { if(!db.getOperatorID( a)){
			 * value =a; System.out.println("operatorid used "+value); break; } } } else {
			 */
			value = testData.get(parameter).toString();

			// Use test data for test case
			System.out.println("Test data for test case: " + testData);

			if (testDataMap.isEmpty()) {
				throw new RuntimeException("Test data conversion failed. Check YAML file path or format.");
			}

		}

		catch (RuntimeException e) {
			getTest().fail("Exception details:\r\n" + e);
			Assert.fail();
		}

		return value;
	}
}