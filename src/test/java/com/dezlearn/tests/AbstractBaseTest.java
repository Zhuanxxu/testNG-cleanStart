package com.dezlearn.tests;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.dezlearn.lib.AppLib;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AbstractBaseTest{
	
	WebDriver driver;
	private AppLib app;
	
	@Parameters({"browser","driverexe"})
	@BeforeMethod(alwaysRun=true)
	public void setUp(@Optional("Chrome") String browser, 
			@Optional("D:\\Documents\\Programacion Codigo\\WebScraping\\chromedriver.exe") String exe) throws Exception {
		if (browser.equals("Chrome")) {
			/*System.setProperty("webdriver.chrome.driver", exe);
			driver = new ChromeDriver();*/
			Map<String,Object> preferences= new HashMap<>();
			preferences.put("profile.default_content_settings.popups", 0);

			preferences.put("download.default_directory",System.getProperty("user.dir")+"\\output");



			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs",preferences);
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//			options.addArguments("--headless");
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
			driver.manage().window().setSize(new Dimension(1920, 1080));
			driver.manage().window().maximize();
		} else if(browser.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", exe);
			driver = new FirefoxDriver();
		} 
		driver.manage().timeouts().implicitlyWait(3, SECONDS);
		app = new AppLib(driver);
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
	public AppLib App() {
		return app;
	}

}
