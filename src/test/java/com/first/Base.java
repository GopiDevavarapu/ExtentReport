package com.first;

import java.awt.Desktop;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	
	static ExtentReports report;
	static ExtentSparkReporter spark;
	static ExtentTest test;
	
	WebDriver driver;
	
	@BeforeSuite
	public static void InitialiseEreport() {
		
		report = new ExtentReports();
		spark = new ExtentSparkReporter("C:\\Users\\Lenovo\\eclipse-workspace\\ExtentReport\\Report.html");
		report.attachReporter(spark);
		
		
	}
	
	@BeforeTest
	public void SetupBrowser() {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
	
	@Test
	public void LoginApplication() {
		
		driver.get("https://opensource-demo.orangehrmlive.com/");
		
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		
//		report.setSystemInfo("Windows", "LocalHost");
//		report.setSystemInfo("Test Engineer", "Gopi");
		
		String actualTitle = "OrangeHRMs";
		String expectedTitle = driver.getTitle();
		
		if(expectedTitle.equals(actualTitle)) {
			
			report.createTest("Pass", "The given URL is opened");
			//test.log(LogStatus.PASS, "Navigated to the specified URL");
		}
		else{
		
			report.createTest("Fail", "The given URL is not opened");
			//test.log(LogStatus.FAIL, "Test Failed");
		}
	}
	
	@AfterTest
	public void CloseBrowser() {
		
		driver.quit();
	}
	
	@AfterSuite
	public void GenerateReport() throws Exception {
		
	
		report.flush();
		Desktop.getDesktop().browse(new File("C:\\Users\\Lenovo\\eclipse-workspace\\ExtentReport\\Report.html").toURI());
	}
	
}
