package com.orangeHRM.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.RetryAnalyzer;

public class TestListener implements ITestListener, IAnnotationTransformer{

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		//start logging in Extent Reports
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: " + testName);
	}
	
	//triggered when a Test Succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully", "Test End: " + testName + " - Test Passed");
		}
		else {
			ExtentManager.logStepValidationForAPI("TEst End:" + testName + " - Test Passed");
		}
		
	}

	//triggered when a Test Fails
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String failureMsg = result.getThrowable().getMessage();
		ExtentManager.logStep(failureMsg);
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed!" , "Test End: " + testName + " - Test Failed");
		}
		else
		{
			ExtentManager.logFailureAPI("Test End:" + testName + " - Test Failed");
		}
	}

	//triggered when a Test Skips
	@Override
	public void onTestSkipped(ITestResult result) {
	String testName = result.getMethod().getMethodName();
	ExtentManager.logSkip("Test Skipped: " + testName);		
	}

	//triggered when a suite starts
	@Override
	public void onStart(ITestContext context) {
		//intialize the extent reports
		ExtentManager.getReporter();
	}

	//triggered when a suite ends
	@Override
	public void onFinish(ITestContext context) {
		//flush the extent report
		ExtentManager.endTest();
	}
	
	

}
