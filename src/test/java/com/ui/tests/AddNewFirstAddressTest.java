package com.ui.tests;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.ui.pages.AddressPage;
import com.ui.pages.MyAccountPage;
import com.ui.pojo.AddressPOJO;
import com.utility.FakeAddressUtility;
import com.utility.LoggerUtility;

@Listeners(com.ui.listeners.TestListener.class)


public class AddNewFirstAddressTest extends TestBase
{
	Logger logger = LoggerUtility.getLogger(this.getClass());
	private MyAccountPage myAccountPage;
	private AddressPage addressPage;
	private AddressPOJO address;

	@BeforeMethod(description="Valid first time user logs into the application")
	public void setup()
	{
		myAccountPage=homePage.goToLoginPage().doLoginWith("yelape8867@hiringview.com", "password");
		address=FakeAddressUtility.getFakeAddress();
	}
	
	@Test
	public void addNewAddress()
	{
		String newAddress = myAccountPage.goToAddressPage().saveAddress(address);
		Assert.assertEquals(newAddress, address.getAddressAlias().toUpperCase());
	}
}
