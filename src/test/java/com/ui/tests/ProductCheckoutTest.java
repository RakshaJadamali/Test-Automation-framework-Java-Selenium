package com.ui.tests;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.constants.Size.*;
import com.ui.pages.SearchResultPage;
import com.utility.FakeAddressUtility;
import com.utility.LoggerUtility;

@Listeners(com.ui.listeners.TestListener.class)

public class ProductCheckoutTest extends TestBase
{
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private static final String SEARCH_TERM="Printed summer dress";
	private SearchResultPage searchResultPage;
	
	@BeforeMethod(description="Valid first time user logs into the application")
	public void setup()
	{
		searchResultPage=homePage.goToLoginPage().doLoginWith("yelape8867@hiringview.com", "password").searchForAProduct(SEARCH_TERM);
	}
	
	@Test(description="Verify if the logged in user is able to buy a dress", groups={"e2e","sanity","smoke"})
	public void checkoutTest()
	{
		searchResultPage.clickOnTheProductAtIndex(1).changeSize(L).addProductToCart().proceedToCheckout().goToConfirmAddressPage().
		goToShipmentPage().goToPaymentPage();
	}
}
