package com.orangeHRM.pages;

import java.util.Base64;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class LoginPage {

	private ActionDriver actionDriver;

	//define locators using By class
	private By userNameField = By.name("username");
	private By passwordField = By.cssSelector("input[type='password']");
//	private By loginButton = By.xpath("//button[contains(text(), 'Login')]");
	private By loginButton = By.xpath("//button[@type='submit']");
	private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

	//initialize the actiondriver object by passing webdriver instance
	/*
 	public LoginPage(WebDriver driver) {
 		this.actionDriver = new ActionDriver(driver);
	}
	 */

	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}

	//method to perform login
	public void login(String userName, String password) {
		actionDriver.enterText(userNameField, userName);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginButton);
	}

	//method to check error message is displayed
	public boolean isErrorMessageDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
	}

	//method to get the text from error messsage
	public String getErrorMessageText() {
		return actionDriver.getText(errorMessage);
	}

	//verify if error is correct or not
	public boolean verifyErrorMessage(String expectedError) {
		return actionDriver.compareText(errorMessage, expectedError);
	}

}
