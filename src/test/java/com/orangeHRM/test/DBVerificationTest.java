package com.orangeHRM.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;
import com.orangeHRM.utilities.DBConnection;
import com.orangeHRM.utilities.DataProviders;
import com.orangeHRM.utilities.ExtentManager;

import io.opentelemetry.semconv.SemanticAttributes.DbCosmosdbConnectionModeValues;

public class DBVerificationTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test (dataProvider="empVerification", dataProviderClass = DataProviders.class)
	public void verifyEmployeeNameVerificationFromDB() {
		ExtentManager.logStep("Logging with Admin creedentials");
		loginPage.login(System.getProperty("username"), prop.getProperty("password"));
		
		
		
		ExtentManager.logStep("click on PIM tab");
		homePage.clickOnPIMTab();
		
		ExtentManager.logStep("Search for Employee");
		homePage.employeeSearch("Robinson");
		
		ExtentManager.logStep("Get the EMployee Name from DB");
		String employee_id="3";
		
		//fetch the date into a map
		Map<String, String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);
		String empFirstName = employeeDetails.get("firstName");
		String empMiddleName = employeeDetails.get("middleName");
		String empLastName = employeeDetails.get("lastName");
		
		String empFirstAndMiddleName = (empFirstName + " " + empMiddleName).trim();
		
		ExtentManager.logStep("Verify the employee first and middle names");
		Assert.assertTrue(homePage.verifyEmpFirstAndMiddleName(empFirstAndMiddleName), "First name and middle name are not matching");
		
		ExtentManager.logStep("Verify the employee last name");
		Assert.assertTrue(homePage.verifyEmpLastName(empLastName));
		
		ExtentManager.logStep("DB Validation Completed");
	}

}
