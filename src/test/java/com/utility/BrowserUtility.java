package com.utility;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.constants.Browser;

public abstract class BrowserUtility 
{
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private WebDriverWait wait;
	private Logger logger = LoggerUtility.getLogger(this.getClass());
	
	public WebDriver getDriver() {
		return driver.get();
	}

	public BrowserUtility(WebDriver driver) {
		super();
		this.driver.set(driver);//initialize the instance variable driver
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30L));
	}
	
	public BrowserUtility(String browserName)
	{
		logger.info("Launching browser for"+browserName);

		if(browserName.equalsIgnoreCase("chrome"))
		{
			driver.set(new ChromeDriver());	
			this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

		}
		
		else if(browserName.equalsIgnoreCase("edge"))
		{
			driver.set(new EdgeDriver());
			this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

		}
		
		else
		{
			logger.error("Invalid browser name. Please select Chrome or Edge");
		}
	}
	
	public BrowserUtility(Browser browserName)
	{
		logger.info("Launching browser for"+browserName);

		if(browserName==Browser.CHROME)
		{
			driver.set(new ChromeDriver());	
			this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

		}
		
		else if(browserName==Browser.EDGE)
		{
			driver.set(new EdgeDriver());	
			this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

		}
		
		else if(browserName==Browser.FIREFOX)
		{
			driver.set(new FirefoxDriver());
			this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

		}
	}
	
	public BrowserUtility(Browser browserName, boolean isHeadless)
	{
		logger.info("Launching browser for"+browserName);

		if(browserName==Browser.CHROME)
		{
			if(isHeadless)
			{
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless=old");
				options.addArguments("--window-size=1920,1080");
				driver.set(new ChromeDriver(options));		
				this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			}
			
			else
			{
				driver.set(new ChromeDriver());	
				this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			}
		}
		
		else if(browserName==Browser.EDGE)
		{
			if(isHeadless)
			{
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless=old");
				options.addArguments("disabled-gpu");
				driver.set(new EdgeDriver(options));
				this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			}
			
			else
			{	
				driver.set(new EdgeDriver());	
				this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			}
		}
		
		else if(browserName==Browser.FIREFOX)
		{
			if(isHeadless)
			{
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless=old");
				options.addArguments("disabled-gpu");
				driver.set(new FirefoxDriver(options));	
				this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			}
			
			else
			{	
				driver.set(new FirefoxDriver());
				this.wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30L));

			}	
		}
	}

	public void goToWebsite(String url)
	{
		logger.info("Visiting the website "+url);
		driver.get().get(url);
	}
	
	public void maximizeWindow()
	{
		logger.info("Maximizing the browser window");
		driver.get().manage().window().maximize();//maximise browser window
	}
	
	public void clickOn(By locator)
	{
		logger.info("Finding element with the locator"+locator);
		//WebElement element = driver.get().findElement(locator);//Find the element
		WebElement element=wait.until(ExpectedConditions.elementToBeClickable(locator));
		logger.info("Element found and performing click");
		element.click();
	}

	public void clickOnCheckbox(By locator)
	{
		logger.info("Finding element with the locator"+locator);
		//WebElement element = driver.get().findElement(locator);//Find the element
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		logger.info("Element found and performing click");
		element.click();
	}
	
	public void clickOn(WebElement element)
	{
		logger.info("Element found and performing click");
		element.click();
	}

	public void enterText(By locator, String textToEnter)
	{
		logger.info("Finding element with the locator"+locator);
		//WebElement element = driver.get().findElement(locator);
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		logger.info("Element found and enter text"+textToEnter);
		element.sendKeys(textToEnter);	
	}
	
	public void clearText(By textboxLocator)
	{
		logger.info("Finding element with the locator"+textboxLocator);
		//WebElement element = driver.get().findElement(textboxLocator);
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(textboxLocator));
		logger.info("Element found and clearing the textbox field");
		element.clear();
	}
	
	public void selectFromDropdown(By DropdownLocator, String optionToSelect)
	{
		logger.info("Finding element with the locator"+DropdownLocator);
		WebElement element = driver.get().findElement(DropdownLocator);
		//Select select=new Select(element);
		logger.info("Selecting the option:"+optionToSelect);
		//select.selectByVisibleText(optionToSelect);
		
		try {
			// 1. Try normal Selenium Select first
			Select select = new Select(element);
			select.selectByVisibleText(optionToSelect);
			} catch (Exception e) {
			// 2. If hidden by Uniform plugin (like id_state) - use JavaScript
			logger.info("Normal Select failed, trying JavaScript - " + e.getMessage());
			JavascriptExecutor js = (JavascriptExecutor) driver.get();
			js.executeScript(
			"var sel = arguments[0];" +
			"for(var i=0; i<sel.options.length; i++) {" +
			" if(sel.options[i].text.trim() === arguments[1]) {" +
			" sel.selectedIndex = i;" +
			" sel.dispatchEvent(new Event('change', {bubbles: true}));" +
			" sel.dispatchEvent(new Event('click', {bubbles: true}));" +
			" break;" +
			" }" +
			"}", element, optionToSelect);
			}

	}
	
	public void enterSpecialKey(By locator, Keys keyToEnter)
	{
		logger.info("Finding element with the locator"+locator);
		//WebElement element = driver.get().findElement(locator);
		WebElement element=wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		logger.info("Element found and enter sepcial key"+keyToEnter);
		element.sendKeys(keyToEnter);	
	}
	
	public String getVisibleText(By locator)
	{
		logger.info("Finding element with the locator"+locator);
		WebElement element = driver.get().findElement(locator);
		logger.info("Element found and returning the values"+element.getText());
		return element.getText();
	}
	
	public String getVisibleText(WebElement element)
	{
		logger.info("Returning the visible text"+element.getText());
		return element.getText();
	}
	
	public List<String> getAllVisibleText(By locator)
	{
		logger.info("Finding all element with the locator"+locator);
		List<WebElement> elementList = driver.get().findElements(locator);
		logger.info("Elements found and now printing the list of elements");
		List<String> visibleTextList=new ArrayList<String>();
		
		for(WebElement element:elementList)
		{
			System.out.println(getVisibleText(element));
			visibleTextList.add(getVisibleText(element));
		}
		
		return visibleTextList;
	}
	
	public List<WebElement> getAllElements(By locator)
	{
		logger.info("Finding all element with the locator"+locator);
		List<WebElement> elementList = driver.get().findElements(locator);
		logger.info("Elements found and now printing the list of elements");
		return elementList;
	}
	public WebElement waitForVisibility(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}

	public String takeScreenshot(String name)
	{
		TakesScreenshot screenshot =(TakesScreenshot) driver.get();
		File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
		String timeStamp = format.format(date);
		String path = "./screenshots/"+name+" - "+timeStamp+".png";
		File screenshotFile = new File(path);
		
		try 
		{
			FileUtils.copyFile(screenshotData, screenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return path;
	}
}
