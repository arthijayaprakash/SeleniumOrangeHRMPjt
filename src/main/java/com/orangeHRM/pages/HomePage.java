package com.orangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class HomePage {

	private ActionDriver actionDriver;

	//define locators using by class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIDButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

	private By pimTab = By.xpath("//span[text()='PIM']");
	private By employeeSearch = By.xpath("//div/label[text()='Employee Name']/parent::div/following-sibling::div//input");
	private By searchButton = By.xpath("//button[@type='submit']");
	private By empFirstAndMiddleName = By.xpath("//div[@class='oxd-table-card']/div/div[3]");	
	private By empLastName = By.xpath("//div[@class='oxd-table-card']/div/div[4]");

	//initialize the actiondriver object by passing webdriver instance
	/*
	public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}
	 */

	public HomePage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	//method to verify if admin tab is visible
	public boolean isAdminTabVisible() {
		return actionDriver.isDisplayed(adminTab);
	}

	//method to verify if logo is displayed
	public boolean verifyOrangeHRMLogo() {
		return actionDriver.isDisplayed(orangeHRMLogo);
	}

	//method to navigate to PIM tab
	public void clickOnPIMTab() {
		actionDriver.click(pimTab);
	}

	//employee search
	public void employeeSearch(String value) {
		actionDriver.enterText(employeeSearch, value);
		actionDriver.click(searchButton);
		actionDriver.scrollToElement(empFirstAndMiddleName);
	}

	//verify employee first and middle name
	public boolean verifyEmpFirstAndMiddleName(String empFirstAndMiddleNameFromDB) {
		return actionDriver.compareText(empFirstAndMiddleName, empFirstAndMiddleNameFromDB);
	}

	//verify employee last name
	public boolean verifyEmpLastName(String empLastNameFromDB) {
		return actionDriver.compareText(empLastName, empLastNameFromDB);
	}

	//method to perform logout operation
	public void logout() {
		actionDriver.click(userIDButton);
		actionDriver.click(logoutButton);
	}

}
