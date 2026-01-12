package TestNGSession2;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParaBankDataDriven {
	
	static WebDriver driver;
	
	@BeforeMethod
	void pageLoad() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/parabank/register.htm");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scrollBy(0,200)");
	}
	
	@DataProvider (name = "regdata")
	Object[][] fetchdata() throws IOException {
		FileInputStream file = new FileInputStream("E:/BESENT TECHNOLOGIES/Selenium/ParabankDatadriven.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(file);
		XSSFSheet sheet = book.getSheetAt(0);
//		XSSFSheet sheet = book.getSheet("testdata");
		int rows = sheet.getLastRowNum();
		short cell = sheet.getRow(0).getLastCellNum();
		
		Object[][] arr = new Object[rows] [cell]; //3 //2

		for (int i = 1; i <= rows; i++) {
			for (int j = 0; j < cell; j++) {
				arr[i-1][j] = sheet.getRow(i).getCell(j).toString();
			}			
		}
		return arr;
	}
	
	@Test (dataProvider = "regdata")
	void register(String fname, String lname, String add, String city, String state, String zip, String phone, String ssn, String uname, String pwd, String cnfpwd) {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.id("customer.firstName")).sendKeys(fname);
		driver.findElement(By.id("customer.lastName")).sendKeys(lname);
		driver.findElement(By.id("customer.address.street")).sendKeys(add);
		driver.findElement(By.id("customer.address.city")).sendKeys(city);
		driver.findElement(By.id("customer.address.state")).sendKeys(state);
		driver.findElement(By.id("customer.address.zipCode")).sendKeys(zip);
		driver.findElement(By.id("customer.phoneNumber")).sendKeys(phone);
		driver.findElement(By.id("customer.ssn")).sendKeys(ssn);
		driver.findElement(By.id("customer.username")).sendKeys(uname);
		driver.findElement(By.id("customer.password")).sendKeys(pwd);
		driver.findElement(By.id("repeatedPassword")).sendKeys(cnfpwd);
		driver.findElement(By.xpath("//input[@value='Register']")).click();			
		
//		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(uname);
//		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(pwd);
//		driver.findElement(By.xpath("//input[@value='Log In']")).click();
				
//		WebElement validationText = driver.findElement(By.xpath("//h2[.='Account Services']"));
//		Assert.assertEquals(validationText, "Account Services");
		
	}
	
	@AfterMethod 
	void tearDown() {
		driver.quit();
	}
	
	
	

}
