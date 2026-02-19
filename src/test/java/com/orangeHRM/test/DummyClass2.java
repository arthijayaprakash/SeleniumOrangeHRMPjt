package com.orangeHRM.test;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;

public class DummyClass2 extends BaseClass{

	@Test 
	public void dummyTest() {
//		ExtentManager.startTest("Dummy Test2 started"); 	-- THIS HAS BEEN IMPLEMENTED IN TESTLISTENER CLASS
		//		String title = driver.getTitle();		
		String title = getDriver().getTitle(); 		//new change as per THREAD
		System.out.println("title: " + title);
		ExtentManager.logStep("Verifying the title of Home page");
		assert title.equals("OrangeHRM") : "Test Failed - title is matching";
		ExtentManager.logStep("Validation successful");
	}
}
