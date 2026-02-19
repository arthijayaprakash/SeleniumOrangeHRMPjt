package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}

	@Test
	public void verifyOrangeHRMLogo() {
//		ExtentManager.startTest("Home page verify Logo test");	-- THIS HAS BEEN IMPLEMENTED IN TESTLISTENER CLASS
		ExtentManager.logStep("Navigated to Home page");
		loginPage.login("admin", "admin123");
		ExtentManager.logStep("Verify logo is visible or not");
		Assert.assertTrue(homePage.verifyOrangeHRMLogo(), "Logo is not visible");
		ExtentManager.logStep("Validation is Successful");
	}


}