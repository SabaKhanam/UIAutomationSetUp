package com.careautomate.utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.careautomate.baseclasses.ManageBrowser;

public class TestDataProvider extends ManageBrowser {

	@DataProvider(name = "testData")
	public Object[][] getTestData() throws IOException {
		return CommonMethods.getDataFromJson(pro.TestData());
	}
}