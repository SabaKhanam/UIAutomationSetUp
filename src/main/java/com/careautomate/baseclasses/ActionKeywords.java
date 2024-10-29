package com.careautomate.baseclasses;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.careautomate.utils.*;

public class ActionKeywords extends ManageBrowser {

	WebDriver driver;

	public ActionKeywords(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * finds element using text
	 * 
	 * @param elementType
	 * @param elementText
	 * @return
	 */
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}

	public WebElement findElementByText(String elementType, String elementText) {
		WebElement targetElement = null;

		try {
			By element = By.xpath(".//" + elementType + "[contains(text(),'" + elementText + "')]");
			targetElement = getDriver().findElement(element);
			getTest().log(Status.INFO, "Found " + elementText);
		} catch (Exception e) {
			getTest().fail(elementText + " element not found ,\r\nException details:\r\n" + e);

			Assert.fail(elementText + " element not found");
		}
		return targetElement;
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void click(By locator, String tests) {
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(pro.LongWait()));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			getDriver().findElement(locator).click();
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " click failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " click failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void clickandwait(By locator, String tests) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(pro.LongWait()));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			getDriver().findElement(locator).click();
			getTest().log(Status.INFO, tests);
			getDriver().manage().timeouts().pageLoadTimeout(pro.ShortWait(), TimeUnit.SECONDS);
		} catch (Exception e) {
			getTest().fail(tests + " click  and wait failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " click and wait failed");
		}
	}

	public void enterInput(By locator, String value, String tests) {
		try {
			wait(locator, pro.ShortWait());
			getDriver().findElement(locator).sendKeys(value);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " enter text failed,\r\nException details:\r\n" + e);
			// Assert.fail(tests + " enter text failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 * @param tests
	 * @throws Exception
	 */
	public void enter(By locator, String value, String tests) {
		try {
			wait(locator, pro.LongWait());
			getDriver().findElement(locator).clear();
			getDriver().findElement(locator).sendKeys(value);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " enter text failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " enter text failed");
		}
	}

	public void jsEnterValue(By locator, String value, String tests) {
		try {
			wait(locator, pro.ShortWait());
			WebElement element = getDriver().findElement(locator);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			jse.executeScript(mouseOverScript, element);
			jse.executeScript("arguments[0].value='" + value + "';", element);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " enter text failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " enter text failed");
		}
	}

	public void enterandwait(By locator, String value, String tests) {
		try {
			wait(locator, pro.LongWait());
			getDriver().findElement(locator).clear();
			getDriver().findElement(locator).sendKeys(value);
			getTest().log(Status.INFO, tests);
			getDriver().manage().timeouts().pageLoadTimeout(pro.ShortWait(), TimeUnit.SECONDS);
		} catch (Exception e) {
			getTest().fail(tests + " enter text and wait failed,\r\nException details:\r\n" + e);
			// Assert.fail(tests + " enter text and wait failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @return
	 * @throws Exception
	 */
	public boolean verify(By locator, String tests) {
		boolean bool = false;
		try {
			wait(locator, pro.ShortWait());
			bool = getDriver().findElement(locator).isDisplayed();
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + "locator is not displayed ,\r\nException details:\r\n" + e);
			Assert.fail(tests + " locator is not displayed");
		}
		return bool;
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void jsclick(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			WebElement element = getDriver().findElement(locator);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", element);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + "  Js click failed ,\r\nException details:\r\n" + e);
			Assert.fail(tests + " Js click failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void browserScroll(int ScrollTo, String tests) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0, " + ScrollTo + ")");
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + "scroll failed ,\r\nException details:\r\n" + e);
			Assert.fail(tests + " scroll failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void mouseOver(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			Actions MouseOver = new Actions(getDriver());
			MouseOver.moveToElement(getDriver().findElement(locator)).build().perform();
		} catch (Exception e) {
			getTest().fail(tests + " mouseOver failed,\r\nException details:\r\n" + e);
			// Assert.fail(tests + " mouseOver failed");
		}
	}

	public void scollTillView(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			WebElement element = getDriver().findElement(locator);
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " mouseOver failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " mouseOver failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void mouseClick(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			Actions MouseOver = new Actions(getDriver());
			MouseOver.moveToElement(getDriver().findElement(locator)).click().build().perform();
		} catch (Exception e) {
			getTest().fail(tests + "mouse click failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + "mouse click failed");
		}
	}

	/**
	 * 
	 * @param locators
	 * @param Value
	 * @param tests
	 * @throws Exception
	 */

	public void select(By locator, String Value, String tests) {
		try {
			wait(locator, pro.ShortWait());
			new Select(getDriver().findElement(locator)).selectByVisibleText(Value);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + "\r\nException details:\r\n" + e);
			Assert.fail(tests + " selection of element failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 * @param tests
	 * @throws Exception
	 */

	public void select(By locator, int value, String tests) {
		try {
			wait(locator, pro.ShortWait());
			new Select(getDriver().findElement(locator)).selectByIndex(value);
			getTest().log(Status.INFO, tests);

		} catch (Exception e) {
			getTest().fail(tests + " selection of element failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " selection of element failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 * @param tests
	 * @throws Exception
	 */
	public void selectandwait(By locator, int value, String tests) {
		try {
			wait(locator, pro.ShortWait());
			new Select(getDriver().findElement(locator)).selectByIndex(value);
			getTest().log(Status.INFO, tests);
			wait(locator, pro.LongWait());
		} catch (Exception e) {
			getTest().fail(tests + " selection of element failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " selection of element failed");
		}
	}

	/**
	 * wait for visibility of element
	 */
	public void wait(By locator, int driverWait) {
		try {
			System.out.println("waiting for " + locator);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(driverWait));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			getTest().info(locator + "is visible");
		} catch (Exception e) {
			getTest().fail(locator + " is not found,\r\nException details:\r\n" + e);
			Assert.fail("Unable to find element,wait failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param value
	 * @param tests
	 * @throws Exception
	 */
	public void selectandwait(By locator, String value, String tests) {
		try {
			wait(locator, pro.ShortWait());
			new Select(getDriver().findElement(locator)).selectByVisibleText(value);
			getTest().log(Status.INFO, tests);
			wait(locator, pro.LongWait());
		} catch (Exception e) {
			getTest().fail(tests + "selection of element failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " selection of element failed");
		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void clear(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			getDriver().findElement(locator).clear();
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + "\r\nException details:\r\n" + e);
			Assert.fail(tests + " unable to clear");

		}
	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void arrow_Down(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			getDriver().findElement(locator).sendKeys(Keys.ARROW_DOWN);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " arrown down failed,\r\nException details:\r\n" + e);
			Assert.fail(tests + " arrow down failed");
		}

	}

	/**
	 * 
	 * @param locator
	 * @param tests
	 * @throws Exception
	 */
	public void key_Enter(By locator, String tests) {
		try {
			wait(locator, pro.ShortWait());
			getDriver().findElement(locator).sendKeys(Keys.ENTER);
			getTest().log(Status.INFO, tests);
		} catch (Exception e) {
			getTest().fail(tests + " key enter failed,Exception details:\\r\\n" + e);
			Assert.fail(tests + " key enter failed");
		}

	}

}