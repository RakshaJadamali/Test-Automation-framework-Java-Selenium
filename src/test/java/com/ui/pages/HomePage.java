package com.ui.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.constants.Browser;
import static com.constants.Env.*;
import com.utility.BrowserUtility;
import com.utility.JSONUtility;
import com.utility.LoggerUtility;

import static com.utility.PropertiesUtil.*;

public final class HomePage extends BrowserUtility 
{
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private static final By SIGN_IN_LINK_LOCATOR = By.xpath("//a[contains(text(),'Sign')]");
	
	public HomePage(Browser browserName, boolean isHeadless)
	{
		super(browserName,isHeadless);
		goToWebsite(JSONUtility.readJSON(QA).getUrl());
	}

	
	public HomePage(WebDriver driver)
	{
		super(driver);//To call parent class constructor from the child class constructor
		goToWebsite(JSONUtility.readJSON(QA).getUrl());
	}
	
	public LoginPage goToLoginPage()
	{
		logger.info("Trying to perform click to go to Sign in Page");
		clickOn(SIGN_IN_LINK_LOCATOR);
		LoginPage loginPage=new LoginPage(getDriver());
		return loginPage;
	}
}
