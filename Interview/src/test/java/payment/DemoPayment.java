package payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoPayment {
	
	public static WebDriver driver;
	@Test
	public void paymentTest() throws IOException, InterruptedException {
		//For Providing dynamic file name for screenshot.
		Date date = new Date();
		String filename = date.toString().replace(" ", "_").replace(":", "_");
		//Getting the data from property file
		FileInputStream fis = new FileInputStream(".\\src\\test\\resources\\data\\upiid.property");
		Properties property = new Properties();
		property.load(fis);
		//Get the Url from Property file
		String url = property.getProperty("url");
		//Get the upi id from Property file
		String upiid = property.getProperty("upiid");
		
		String cardnumber = property.getProperty("cnumber");
		String cdate = property.getProperty("date");
		String name = property.getProperty("name");
		String cv = property.getProperty("cv");
		//Setup the chrome driver
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		//Enter the URL
		driver.get(url);
		//For scrolling to the desired web element
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		//Finding the elements for upi
		driver.findElement(By.id("pay_via_hosted")).click();
//		driver.findElement(By.xpath("//ul/div[3]")).click();
//		driver.findElement(By.xpath("//span[@class='upi-label']")).click();
//		driver.findElement(By.xpath("//input[@placeholder='Enter your UPI ID']")).sendKeys(upiid);
//		driver.findElement(By.xpath("//button[@class='pay-btn']")).click();
		
		//Finding elements for card payment
		driver.findElement(By.xpath("//ul/div[1]")).click();
		driver.findElement(By.xpath("//input[@placeholder='Card Number']")).sendKeys(cardnumber);
		driver.findElement(By.xpath("//input[@placeholder='MM/YY']")).sendKeys(cdate);
		driver.findElement(By.xpath("//input[@placeholder='Card Holder Name']")).sendKeys(name);
		driver.findElement(By.xpath("//input[@placeholder='CVV']")).sendKeys(cv);
		driver.findElement(By.xpath("//button[@class='pay-btn']")).click();
		
		Thread.sleep(2000);
		//For Taking the screenshot
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(".//screenshot//"+filename+".png");
		FileUtils.copyFile(src, dest);
		//For closing the browser
		driver.close();

	}

}
