package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.utilities.ExtentManager;
import com.orangeHRM.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;	//prop value will stay the same for all the tests
	//	protected static WebDriver driver;
	//	private static ActionDriver actionDriver;

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		//load the configuration file
		prop = new Properties();
//		FileInputStream fis = new FileInputStream("src\\main\\resources\\config.properties");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file got loaded");
		
		//start the extent report
//		ExtentManager.getReporter();  -- THIS HAS BEEN IMPLEMENTED IN TESTLISTENER CLASS
	}

	@BeforeMethod
	public synchronized void setup() throws IOException {
		System.out.println("Setting up webdriver for: "+ this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();			
		staticWait(3);
		System.out.println("test1");
		logger.info("WebDriver initialized and browser is maximized");
		System.out.println("test2");
		logger.trace("this is a Trace mesasge");
		System.out.println("test3");
		logger.error("this is an Error message");
		System.out.println("test4");
		logger.debug("this is a Debug message");
		System.out.println("test5");
		logger.fatal("this is a Fatal message");
		System.out.println("test6");
		logger.warn("This is a warning message");
		System.out.println("test7");

		/*
		//initialize the actionDriver only once
		if(actionDriver == null) {
			actionDriver = new ActionDriver(driver);
//			System.out.println("ActionDriver instance is created.");
			logger.info("ActionDriver instance is created");
		}
		 */

		//initialize ActionDriver for the current THread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialization for thread: " + Thread.currentThread().getId());		
	}

	//initialize the webdriver based on driver defined in config properties file
	private void launchBrowser() {
		String browser = prop.getProperty("browser");

		if(browser.equalsIgnoreCase("chrome")) {
			
			//create Chrome options
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");			//run in headless mode
			options.addArguments("--disable-gpu");		//disable GPU for headless mode
			options.addArguments("--window-size=1920, 1080");	//set windows size
			options.addArguments("--disable-notifications");	//disable browser notifications
			options.addArguments("--no-sandbox");	//required for some CI environments 
			options.addArguments("--disable-dev-shm-usage");	//resolve issues in resource
			
			//			driver = new ChromeDriver();
			driver.set(new ChromeDriver());			//new change as per THREAD
			logger.info("ChromeDriver instance is created");
		}
		else if(browser.equalsIgnoreCase("edge")) {
			//			driver = new EdgeDriver();
			driver.set(new EdgeDriver());			//new change as per THREAD
			logger.info("EdgeDriver instance is created");
		}
		else {
			throw new IllegalArgumentException("Invalid browser " + browser);
		}
	}

	//configure browser settings such as implicit wait, maximize the browser and navigate to the URL
	private void configureBrowser() {
		//implicit wait
		int implicitWaitTime = Integer.parseInt(prop.getProperty("implicitwait"));
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		//maximize the browser
//		driver.manage().window().maximize();
		getDriver().manage().window().maximize();

		//navigate to URL
		try {
//			driver.get(prop.getProperty("url"));
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//				e.printStackTrace();
			System.out.println("failed to navigate to the url: " + e.getMessage());
		}
	}

	@AfterMethod
	public synchronized void tearDown() {
		if(driver!=null) {
			try {
//				driver.quit();
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("unable to quit the driver");
			}
		}
		//		System.out.println("Webdriver instance is closed");
		logger.info("WebDriver instance is closed");
		driver.remove();
		actionDriver.remove();
//		driver = null;
//		actionDriver = null;
//		ExtentManager.endTest();	-- THIS HAS BEEN IMPLEMENTED IN TESTLISTENER CLASS
	}

	//getter method for page
	public static Properties getProp() {
		return prop;
	}

	/*
	//driver getter method
	public WebDriver getDriver() {
		return driver;
	}

	 */

	//getter method for webdriver
	public static WebDriver getDriver() {
		if(driver.get() == null) {
			System.out.println("WebDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
	}

	//getter method for webdriver
	public static ActionDriver getActionDriver() {
		if(driver.get() == null) {
			System.out.println("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();
	}

	/*
	//driver setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}*/
	
	//driver setter method
		public void setDriver(ThreadLocal<WebDriver> driver) {
			this.driver = driver;
		}

	//	static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));	
	}



}
