package appscriptAutomationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.UtilLib;
//import com.AppScript.generic.ApplicationUtility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestcaseTrigger {
	
	 static WebDriver driver, driver1;
	 static WebDriver Gmaildriver;
	 static WebDriverWait wait;
	static DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
	 static Date date = new Date();
	 static List<WebElement> list,list1;
	 static boolean verify=true;
	 static String categoryname_leftpane;
	 static String categoryname_filter;
	 static String screenshot;
	 static Iterator<WebElement> iterator;
	 static WebElement elements;
	 static JavascriptExecutor  je ;
	 static String Prescriberinfo;
	 
	 public static UtilLib util = new UtilLib();
	 SoftAssert softverification=new SoftAssert();
	 
	 String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
		String sheet="Appscript";
		//ExtentReports report;
	 
	// ExtentReports report  = new ExtentReports(System.getProperty("user.dir")+ "/Reports/Extentreport"+dateFormat.format(date)+".html",true);
	
	static  ExtentTest test;
	 // static String Finalreportpath=System.getProperty("user.dir")+ "/Reports/Extentreport"+dateFormat.format(date)+".html";
	// static String Finalreportpath=System.getProperty("user.dir")+ "/Extentreport"+dateFormat.format(date)+".html";
	 
	 
	 // create new directory..
	 
	 static String folderName="Appscript"+dateFormat.format(date);
	 static String ScreenShotsfoldername="ScreenShots";
	 
	 File Folderinfo = new File(System.getProperty("user.dir") + "/Reports/"+folderName);
	 File ScreenShotinfo=new File(System.getProperty("user.dir") + "/Reports/"+folderName+"/ScreenShots");
	// Folderinfo.
	 boolean reportsuccessful = Folderinfo.mkdir();
	 boolean snapshotsuccessful = ScreenShotinfo.mkdir();
	 
	 
	 
	static  ExtentReports report  = new ExtentReports(System.getProperty("user.dir")+"/Reports/" +folderName + "/Extentreport"+dateFormat.format(date)+".html",true);
	 static String Finalreportpath=System.getProperty("user.dir") + "/Reports/" + folderName + "/Extentreport"+dateFormat.format(date)+".html";
		
	//@BeforeTest 
	//@Parameters("browser")
	 @Parameters({ "browser" })
	 
	 @BeforeClass
	public void ApplicationLogin(String browsername) throws Exception{
		// System.exit(0);
	
		if (browsername.equalsIgnoreCase("firefox")){
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/geckodriver.exe");
		//System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir")+"/geckodriver.exe");
		
		//webdriver.firefox.marionette
		DesiredCapabilities cap=DesiredCapabilities.firefox();
		//cap.se
		cap.setCapability("marionette", true);
		//cap.setCapability(capabilityName, value);
		FirefoxProfile profile = new FirefoxProfile();
		cap.setCapability(FirefoxDriver.PROFILE, profile);
		
		//ProfilesIni profile = new ProfilesIni();
	  	//FirefoxProfile myprofile = profile.getProfile("default");
		
		
		//File pathToBinary = new File("C://Program Files (x86)//Mozilla Firefox//firefox.exe");
		//FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		//FirefoxProfile firefoxProfile = new FirefoxProfile();*/
		
	//	FirefoxDriver driver = new FirefoxDriver(ffBinary,firefoxProfile);
	//	driver=new FirefoxDriver(ffBinary);
		
		//FirefoxOptions options = new FirefoxOptions();
		//options.setBinary("C://Program Files (x86)//Mozilla Firefox//firefox.exe");
		//options.setBinary("C://Program Files (x86)//Mozilla Firefox//firefox.exe");
	  //	FIREFOX = {'browserName': 'firefox', 'marionette': True, 'acceptInsecureCerts': True}¶
				
		 driver=new FirefoxDriver(cap);
	//	 Gmaildriver=new FirefoxDriver(cap);
		 Prescriberinfo="Firefox";
		 
	//	driver.get("http://www.google.com");
		
		//System.exit(0);
		
		// driver=new FirefoxDriver(ffBinary,firefoxProfile);
	    
		}else if (browsername.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
             // options.setBinary("C:/Users/ynanjaiah/AppData/Local/Google/Chrome/Application/chrome.exe");
			options.setBinary("C:/Users/SMelmuri/AppData/Local/Google/Chrome/Application/chrome.exe");
            //  options.setBinary("C:/Users/ynanjaiah/Documents/Appscript/LatestCopy/Appscript_POC");
                 driver = new ChromeDriver(options);
                // Gmaildriver=new ChromeDriver(options);
                 Prescriberinfo="Chrome";
			
			//driver=new ChromeDriver();
		}
		else if(browsername.equalsIgnoreCase("IE")) {
			
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"/IEDriverServer.exe");
			DesiredCapabilities IEcap= DesiredCapabilities.internetExplorer();
			IEcap.setJavascriptEnabled(true);
			IEcap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			IEcap.setCapability("path", "C:\\Program Files\\Internet Explorer\\iexplore.exe");
			driver=new InternetExplorerDriver(IEcap);
			//Gmaildriver=new InternetExplorerDriver(IEcap);
			//Prescriberinfo="IE";
		}
		 
		 
	report.config().documentTitle("AppScript Automation  Report");
	report.config().reportHeadline("AppScript Smoke Testing Report");
		 
  	//System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");
		//driver=new ChromeDriver();
		
	   wait=  new WebDriverWait(driver, 60);
	    driver.manage().window().maximize();
	    System.out.println("Execution started!");
	 //   driver.get("http://www.google.com");
	    System.out.println(Utilities.getpropertiesexcel("applicationurl"));
	   
		driver.get(Utilities.getpropertiesexcel("applicationurl"));
	//	screenshot = Utilities.screenshot(driver, "Wait Login");
		 Thread.sleep(6000);
	
	}
	
	@Test(priority=1)
	public void verifyFooterLinks_BeforeLogin() throws InterruptedException{
		
		System.out.println("Before Login");
		String parentHandle = driver.getWindowHandle();
		
		test=report.startTest("verifyFooterLinks_BeforeLogin");
		screenshot = Utilities.screenshot(driver, "verifyFooterLinks_BeforeLogin");
		
		if(FooterLinks.veriyfooter(test,report))
		test.log(LogStatus.PASS, "Footer links before login verified"+test.addScreenCapture(Utilities.screenshot(driver, "verifyFooterLinks_BeforeLogin")));
		else {
			test.log(LogStatus.FAIL, "Footer links before login Unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "verifyFooterLinks_BeforeLogin")));	
			softverification.fail("Footer Links before login Validation unsuccessful");
		}
			report.endTest(test);
			softverification.assertAll();
	}

	@Test(priority=2) 
	public void VerifyHelpLink_BeforeLogin() {
		System.out.println("Inside VerifyHelpLink");
		String parentHandle = driver.getWindowHandle();
		
		test=report.startTest("VerifyHelpLink_BeforeLogin");
		screenshot = Utilities.screenshot(driver, "VerifyHelpLink_BeforeLogin");
		
try{
	test=report.startTest("Navigation to Help");

	Utilities.VerifyHelpLink(report, test);
	// Utilities.VerifySupportButton(report, test);
	}catch(Exception e){
	test.log(LogStatus.FAIL, "Prescriber functionality is Unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "Prescriber test"))); 
	}
System.out.println("Help link successful");

driver.switchTo().window(parentHandle);

	report.endTest(test);
	}
	
	
	
	@Test(priority=3)
	public void verifyLogin() throws IOException, Exception{
		test = report.startTest("VerifyLogin");
		System.out.println("Inside VerifyLogin");
		Thread.sleep(4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("LoginMainpage")));
		driver.findElement(Utilities.getLocator("LoginMainpage")).click();
		driver.findElement(Utilities.getLocator("username")).sendKeys(Utilities.getpropertiesexcel("username"));
		driver.findElement(Utilities.getLocator("password")).sendKeys(Utilities.passworddecoder(Utilities.getpropertiesexcel("password")));
		driver.findElement(Utilities.getLocator(Utilities.getpropertiesexcel("Region"))).click();
		
	driver.findElement(Utilities.getLocator("Login")).click();
	wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("appscriptlogo")));
	String url=driver.getCurrentUrl();
	 screenshot = Utilities.screenshot(driver, "Verify Login");
		if (url.contains("dashboard"))
		test.log(LogStatus.PASS, "Login Succesful"+test.addScreenCapture(screenshot) );
		else{
			test.log(LogStatus.FAIL, "Login Unsuccesful"+test.addScreenCapture(screenshot) );
			softverification.fail("Footer Links before login Validation unsuccessful");
		}
		report.endTest(test);
		softverification.assertAll();
	}

	
	@Test(priority=4)
	public void verifyFooterLinks_AfterLogin() throws InterruptedException{
		test=report.startTest("verifyFooterLinks_AfterLogin");
		screenshot = Utilities.screenshot(driver, "verifyFooterLinks_AfterLogin");
		if(FooterLinks.veriyfooter(test,report))
			test.log(LogStatus.PASS, "Footer links after login verified"+test.addScreenCapture(Utilities.screenshot(driver, "verifyFooterLinks_AfterLogin")));
			else{ 
				test.log(LogStatus.FAIL, "Footer links after login Unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "verifyFooterLinks_AfterLogin")));	
				softverification.fail("Footer Links before login Validation unsuccessful"); 	
			}
		report.endTest(test);
		softverification.assertAll();
	}
	
	
	@Test(priority=5) 
	public void VerifyHelpLink_AfterLogin() {
		System.out.println("Inside VerifyHelpLink");
		String parentHandle = driver.getWindowHandle();
		
		test=report.startTest("VerifyHelpLink_AfterLogin");
		screenshot = Utilities.screenshot(driver, "VerifyHelpLink_AfterLogin");
		
try{
	test=report.startTest("Navigation to Help");

	Utilities.VerifyHelpLink(report, test);
	// Utilities.VerifySupportButton(report, test);
	}catch(Exception e){
	test.log(LogStatus.FAIL, "Prescriber functionality is Unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "Prescriber test"))); 
	}
System.out.println("Help link successful");

driver.switchTo().window(parentHandle);

	report.endTest(test);
	}
	

	// @Test(priority=6)
	public void verifyDefaulttab() throws Exception{
		test=report.startTest("VerifyDefaultTab");
		screenshot = Utilities.screenshot(driver, "Verify Default Tab");
		if (driver.findElement(Utilities.getLocator("Apps")).getAttribute("class").equals("active"))
			test.log(LogStatus.PASS, "Default tab is Apps"+test.addScreenCapture(screenshot));
			else{ 
				test.log(LogStatus.FAIL, "Default tab is not Apps"+test.addScreenCapture(screenshot));
				softverification.fail("Footer Links before login Validation unsuccessful"); 	
			}
		report.endTest(test);
		softverification.assertAll();
		} 
	
// @Test(priority=7)
	public void verifyAllTabsPresence(){
		test=report.startTest("verifyAllTabsPresence");
		screenshot = Utilities.screenshot(driver, "Verify All tab presence");
			try {
				if (driver.findElement(Utilities.getLocator("Apps")).isDisplayed() && driver.findElement(Utilities.getLocator("Devices")).isDisplayed()
						&& driver.findElement(Utilities.getLocator("DigitalContent")).isDisplayed())
					
				test.log(LogStatus.PASS, "Apps,Devices and Digital Content tabs are present"+test.addScreenCapture(screenshot));System.out.println("element present");
			}catch(NoSuchElementException e){
				test.log(LogStatus.FAIL, "Tabs are missing"+test.addScreenCapture(screenshot));System.out.println("Element not present");
			} 
			
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();test.log(LogStatus.FAIL, "Tabs are missing"+test.addScreenCapture(screenshot));System.out.println("Element not present");
			}
		report.endTest(test);
	}
	
	
	// @Test(priority=8)
	public void verifyPrice_free() throws Exception{
		test = report.startTest("VerifyPrice_Free");
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("Apps")));
		driver.findElement(Utilities.getLocator("Apps")).click();
	
		if (Utilities.verifyprice("free",test)){
			
			test.log(LogStatus.PASS, "PriceValidation-Free:Succesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Price-Free")));
		}
		else 
		{
			test.log(LogStatus.FAIL, "PriceValidation-Free:Un Succesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Price-Free")) );
			softverification.fail("Footer Links before login Validation unsuccessful"); 
		}
			report.endTest(test);
		driver.findElement(Utilities.getLocator("Apps")).click();
		Thread.sleep(6000);
		softverification.assertAll();
		}

	// @Test(priority=9)
	public void verifyPrice_freemium() throws Exception{
		test = report.startTest("VerifyPrice_Freemium");

		if (Utilities.verifyprice("freemium",test)){
			
			test.log(LogStatus.PASS, "PriceValidation-Freemium: Succesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Price-Freemium")) );
		}
		else 
		{
			test.log(LogStatus.FAIL, "PriceValidation-Freemium: UnSuccesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Price-Freemium")) );
			softverification.fail("Footer Links before login Validation unsuccessful"); 
		}
		report.endTest(test);
		driver.findElement(Utilities.getLocator("all")).click();
		Thread.sleep(6000);
	//	wait.until(ExpectedConditions.invisibilityOfElementLocated(Utilities.getLocator("filter")));
		driver.findElement(Utilities.getLocator("Apps")).click();
		Thread.sleep(6000);
		softverification.assertAll();
		}

	// @Test(priority=10)
	public void verifyPrice_paid() throws Exception{
		test = report.startTest("VerifyPrice_paid");
		screenshot=Utilities.screenshot(driver, "Verify Price-Paid");
		if (Utilities.verifyprice("paid",test)){
			
			test.log(LogStatus.PASS, "PriceValidation-paid: Succesful"+test.addScreenCapture(screenshot) );
		}
		else{
			test.log(LogStatus.FAIL, "PriceValidation-paid: UnSuccesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Price-Paid")) );
			softverification.fail("price validation failed");
			System.out.print("failed");
			//softverification1.assertEquals(1, 2);
			softverification.assertEquals("Price tag not found for all paid types Apps", "Price tag exist for all Paid types Apps");
			softverification.assertAll();
		}
		report.endTest(test);
		je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(Utilities.getLocator("appscriptlogo")));
		Thread.sleep(5000);
		driver.findElement(Utilities.getLocator("Apps")).click();
		Thread.sleep(5000);
		
		
		}

	
	// @Test(priority=11)
	public void verifyPlatform_Apple() throws Exception{
	 test = report.startTest("VerifyPlatform_Apple");
	 driver.findElement(Utilities.getLocator("SwitchApple")).click();
	 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Utilities.getLocator("totalapps")));
	 
	 if(Utilities.verifyplatform("SwitchApple")){
		 test.log(LogStatus.PASS, "PlatformValidation-Apple: Succesful" +test.addScreenCapture( Utilities.screenshot(driver, "Verify Platform-Apple"))); System.out.println("Passed");
	 }else {test.log(LogStatus.FAIL, "PlatformValidation-Apple: UnSuccesful" +test.addScreenCapture( Utilities.screenshot(driver, "Verify Platform-Apple")));System.out.println("Failed");
	 softverification.fail("Footer Links before login Validation unsuccessful"); 	
	 softverification.assertAll();
	 }
	 
	 
	  report.endTest(test);
	 
	  
	}
	
	/*@Test(priority=12)
	public void verifyPlatform_Android() throws Exception{
	 test = report.startTest("VerifyPlatform_Android");
	 driver.findElement(Utilities.getLocator("SwitchAndroid")).click();
	 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Utilities.getLocator("totalapps")));

	 if(Utilities.verifyplatform("SwitchAndroid")){
		 
		 test.log(LogStatus.PASS, "PlatformValidation-Android: Succesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Platform-Android"))); System.out.println("Passed");
	 }else {test.log(LogStatus.FAIL, "PlatformValidation-Android: UnSuccesful" +test.addScreenCapture(Utilities.screenshot(driver, "Verify Platform-Android")));System.out.println("Failed");
	 softverification.fail("Footer Links before login Validation unsuccessful");
	 }
	  report.endTest(test);
	  softverification.assertAll();
	}
	
	
	@Test(priority=13)
	public void verifycategory_apps() throws Exception{
		test = report.startTest("verifycategory_apps");
		 categoryname_leftpane=driver.findElement(Utilities.getLocator("category1")).getAttribute("title");
		System.out.println(categoryname_leftpane);
		driver.findElement(Utilities.getLocator("category1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("filtercat1")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("waiticon")));
		 categoryname_filter=driver.findElement(Utilities.getLocator("filtercat1")).getText();
		System.out.println(categoryname_filter);
		if (categoryname_leftpane.equals(categoryname_filter)){
			System.out.println("Apps Category Validation Success!");test.log(LogStatus.PASS, "Apps-Category Validation Succesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Apps-Category")) );}
			else {System.out.println("Apps Category Validation Unsuccessful!"); test.log(LogStatus.FAIL, "Apps-Category Validation UnSuccesful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Apps-Category")) );	
			softverification.fail("Footer Links before login Validation unsuccessful"); 	
			}
		report.endTest(test);
		softverification.assertAll();
	}
	
	@Test(priority=14)
	public void verifyDevices() throws Exception{
		test = report.startTest("verifyDevices");
		try{
	driver.findElement(Utilities.getLocator("Devices")).click();
	wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("waiticon")));
		}catch (NoSuchElementException e){
			test.log(LogStatus.FAIL, "Device page is not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Devices Tab")) );
		}
	
	list=driver.findElements(Utilities.getLocator("deviceicon"));
	System.out.println(list.size());
	if (list.size()>0)
		{System.out.println("Devices present"); test.log(LogStatus.PASS, "Device page is present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Devices Tab")) );}
	else {test.log(LogStatus.FAIL, "Device page is not present"+test.addScreenCapture(screenshot) );
	softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	report.endTest(test);
	softverification.assertAll();
	}
	
	@Test(priority=15)
	public void verifyCategory_devices() {
		test = report.startTest("verifyCategory_devices");
		try {
			categoryname_leftpane="";
			categoryname_leftpane=driver.findElement(Utilities.getLocator("category1")).getAttribute("title");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try{
		driver.findElement(Utilities.getLocator("category1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("filtercat1")));
		}catch(NoSuchElementException e){
			test.log(LogStatus.FAIL, "Device-Category not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Devices-Category")) );
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			categoryname_filter=" ";
			
			categoryname_filter=driver.findElement(Utilities.getLocator("filtercat1")).getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (categoryname_leftpane.equals(categoryname_filter)){
			System.out.println("Devices Category Validation Success!");
		test.log(LogStatus.PASS, "Device-Category present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Devices-Category")) );}
		else {test.log(LogStatus.FAIL, "Device-Category not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Devices-Category")) ); 
		softverification.fail("Footer Links before login Validation unsuccessful"); 
		}
		report.endTest(test);
		softverification.assertAll();
	}
	
	
	@Test(priority=16)
	public void verifyPriceRange() throws Exception{
		test = report.startTest("verify Price Ranges");
	
	
	if (Utilities.verifypricerange(0.0,50.0,"pricerange1")){
		test.log(LogStatus.PASS, "Price validation succesfull for $50 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges50")) );
		System.out.println("Price validation succesfull for $50 range");
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for $50 ranget"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges50_Failed")) );
		System.out.println("Price validation failed for $50 range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	
	if (Utilities.verifypricerange(50.0,100.0,"pricerange2")){
		test.log(LogStatus.PASS, "Price validation succesfull for $50-$100 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges100")) );
		System.out.println("Price validation succesfull for $50-$100 range");
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for $50-$100 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges100_Failed")) );
		System.out.println("Price validation failed for $50-$100 range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	if (Utilities.verifypricerange(100.0,150.0,"pricerange3")){
		test.log(LogStatus.PASS, "Price validation succesfull for $100-$150 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges150")) );
		System.out.println("Price validation succesfull for $100-$150 range");
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for $100-$150 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges150_Failed")) );
		System.out.println("Price validation failed for $100-$150 range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	if (Utilities.verifypricerange(150.0,200.0,"pricerange4")){
		test.log(LogStatus.PASS, "Price validation succesfull for $150-$200 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges200")) );
		System.out.println("Price validation succesfull for $150-$200  range");
		
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for $150-$200  range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges200Failed")) );
		System.out.println("Price validation failed for $150-$200  range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	if (Utilities.verifypricerange(200.0,250.0,"pricerange5")){
		test.log(LogStatus.PASS, "Price validation succesfull for $200-$250 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges250")) );
		System.out.println("Price validation succesfull for $200-$250 range");
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for $200-$250  range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges250_Failed")) );
		System.out.println("Price validation failed for $200-$250  range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	if (Utilities.verifypricerange(250.0,500.0,"pricerange6")){
		test.log(LogStatus.PASS, "Price validation succesfull for $250-$500 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges500")) );
		System.out.println("Price validation succesfull for $250-$500 range");
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for $250-$500  range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Ranges500_Failed")) );
		System.out.println("Price validation failed for $250-$500  range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	if (Utilities.verifypricerange(500.0,0.0,"pricerange7")){
		test.log(LogStatus.PASS, "Price validation succesfull for >$500 range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Rangesgreaterthan500")) );
		System.out.println("Price validation succesfull for >$500 range");
	}
	else {
		test.log(LogStatus.FAIL, "Price validation failed for >$500  range"+test.addScreenCapture(Utilities.screenshot(driver,"verify Price Rangesgreaterthan500_Failed")) );
		System.out.println("Price validation failed for >$500  range");
		softverification.fail("Footer Links before login Validation unsuccessful"); 
	}
	
	report.endTest(test);
	softverification.assertAll();
		}
		
	
	@Test(priority=17)
	public void verifyDigitalContent() throws Exception{
		test = report.startTest("verifyDigitalContent");
		try{
		driver.findElement(Utilities.getLocator("DigitalContent")).click();
		Thread.sleep(10000);
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("totalapps")));
	   wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("contentSymbol1")));
		
		screenshot = Utilities.screenshot(driver, "Verify Digital Content Tab");
		}catch(NoSuchElementException e){
			test.log(LogStatus.FAIL, "DigitalContent page not present" +test.addScreenCapture(screenshot));
		}
		
		list=driver.findElements(Utilities.getLocator("contentSymbol1"));
        list1=driver.findElements(Utilities.getLocator("contentSymbol2"));

		
		
		//list=driver.findElements(Utilities.getLocator("digitalcontenticon"));
		System.out.println(list.size());
		if (list.size()>0 || list1.size()>0)
			{System.out.println("Digital contents present"); test.log(LogStatus.PASS, "DigitalContent page  present"+test.addScreenCapture(screenshot) );}
		else { System.out.println("No digital contents found");test.log(LogStatus.FAIL, "DigitalContent page not present"+test.addScreenCapture(screenshot) );
		softverification.fail("Footer Links before login Validation unsuccessful");
		}
		report.endTest(test);
		softverification.assertAll();
	}
	

	@Test(priority=18)
	public void verifyCategory_DigitalContent() {
		test = report.startTest("verifyCategory_DigitalContent");
		
		try{
			categoryname_leftpane="";	
		categoryname_leftpane=driver.findElement(Utilities.getLocator("category1")).getAttribute("title");
		}catch(NoSuchElementException e){
			test.log(LogStatus.FAIL, "DigitalContent-category not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Digital Content-Categories")) ); 
		}catch(Exception e){
			test.log(LogStatus.FAIL, "DigitalContent-category not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Digital Content-Categories")) );
		}
		try {
			driver.findElement(Utilities.getLocator("category1")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("filtercat1")));
			screenshot = Utilities.screenshot(driver, "Verify Digital Content-Categories");
			categoryname_filter=" "; 
			categoryname_filter=driver.findElement(Utilities.getLocator("filtercat1")).getText();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();test.log(LogStatus.FAIL, "DigitalContent-category not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Digital Content-Categories")));
		}
		
		if (categoryname_leftpane.equals(categoryname_filter)){
			System.out.println("Devices Category Validation Success!");	test.log(LogStatus.PASS, "DigitalContent-category present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Digital Content-Categories")) );}
		else {test.log(LogStatus.FAIL, "DigitalContent-category not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Digital Content-Categories")) );
		softverification.fail("Footer Links before login Validation unsuccessful"); 
		}
		report.endTest(test);
		softverification.assertAll();
	}
	
	@Test(priority=19)
	public void verifymypateintspage() throws Exception{
		test = report.startTest("verifymypateintspage");
		try{
		driver.findElement(Utilities.getLocator("mypatientslink")).click();
		screenshot=Utilities.screenshot(driver, "Verify My Patients Page");
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("heading")));
		
		}catch(NoSuchElementException e){
			test.log(LogStatus.FAIL, "mypateintspage not present"+test.addScreenCapture(screenshot) );
		}
		
		if (driver.findElement(Utilities.getLocator("heading")).getText().equals("My Patients"))
			{System.out.println("My patients page present");test.log(LogStatus.PASS, "mypateintspage present" +test.addScreenCapture(screenshot));}
		else {System.out.println("My patients page not present");test.log(LogStatus.FAIL, "mypateintspage not present"+test.addScreenCapture(screenshot) );
		softverification.fail("Footer Links before login Validation unsuccessful");
		}
		report.endTest(test);
		softverification.assertAll();
	}
	
	@Test(priority=20)
	public void verify_viewall_formulary_DigitalContent() throws Exception{
		test = report.startTest("viewall_formulary_DigitalCoontent");
		try{
		driver.findElement(Utilities.getLocator("formularylink")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("heading")));
		
		}catch(NoSuchElementException e){
			test.log(LogStatus.FAIL, "View all formulary Digital Content page not present"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Formulary Digital Content")) );
		}
		
		if (driver.findElement(Utilities.getLocator("heading")).getText().equals("All Formulary: Content"))
			{System.out.println("View all formulary Digital Content  page present");test.log(LogStatus.PASS, "View all formulary page  present" +test.addScreenCapture(Utilities.screenshot(driver, "Verify Formulary Digital Content")));}
		else {System.out.println("View all formulary Digital Content  page not present");test.log(LogStatus.FAIL, "View all formulary page not present" +test.addScreenCapture(Utilities.screenshot(driver, "Verify Formulary Digital Content")));
		softverification.fail("Footer Links before login Validation unsuccessful");
		}
		report.endTest(test);
		softverification.assertAll();
	}
	
    @Test(priority=21)

	public void verify_viewall_formulary_Devices() throws Exception{
		driver.findElement(Utilities.getLocator("Devices")).click();
		
		Thread.sleep(4000);
			test = report.startTest("viewall_formulary_Devices page");
			try{
		      driver.findElement(Utilities.getLocator("formularylink")).click();
			Thread.sleep(6000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("heading")));
			screenshot = Utilities.screenshot(driver, "Verify All Formulary Devices");
			}catch(NoSuchElementException e){
				test.log(LogStatus.FAIL, "View all formulary Devices not present"+test.addScreenCapture(screenshot) );
			}
			
			if (driver.findElement(Utilities.getLocator("heading")).getText().equals("All Formulary: Devices"))
				{System.out.println("View all formulary Devices present");
				test.log(LogStatus.PASS, "View all formulary Devices present" +test.addScreenCapture(screenshot));}
			else {System.out.println("View all formulary Devices not present");test.log(LogStatus.FAIL, "View all formulary Devices not present" +test.addScreenCapture(screenshot));
			softverification.fail("Footer Links before login Validation unsuccessful"); 
			}
			 report.endTest(test);
			 softverification.assertAll();
		}
	
	@Test(priority=22)
	
	public void verify_viewall_formulary_Apps() throws Exception{
		driver.findElement(Utilities.getLocator("Apps")).click();
		
		Thread.sleep(4000);
			test = report.startTest("viewall_formulary_Apps");
			try{
		      driver.findElement(Utilities.getLocator("formularylink")).click();
			Thread.sleep(6000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("heading")));
			screenshot = Utilities.screenshot(driver, "Verify All Formulary Apps");
			}catch(NoSuchElementException e){
				test.log(LogStatus.FAIL, "View all formulary Apps not present"+test.addScreenCapture(screenshot) );
			}
			
			if (driver.findElement(Utilities.getLocator("heading")).getText().equals("All Formulary: Apps"))
				{System.out.println("View all formulary Apps present");
				test.log(LogStatus.PASS, "View all formulary Apps present" +test.addScreenCapture(screenshot));}
			else {System.out.println("View all formulary Apps not present");test.log(LogStatus.FAIL, "View all formulary Apps not present" +test.addScreenCapture(screenshot));
			softverification.fail("Footer Links before login Validation unsuccessful"); 	
			}
			 report.endTest(test);
			 softverification.assertAll();
	   }
	
	
	@Test(priority=23)
	public void search_devices() throws Exception{
		test = report.startTest("VerifySearch Devices");
	Utilities.searchResources("devices", "me",test)	;	
	report.endTest(test);
	}
	
	@Test(priority=24)
	public void search_digitalcontent() throws Exception{
		
	test = report.startTest("VerifySearch DigitalContent");
	Utilities.searchResources("DigitalContent", "med",test)	;	
	report.endTest(test);
	}
	

	
	@Test(priority=25)
	public void search_Apps() throws Exception{
		
	test = report.startTest("VerifySearch Apps");
	Thread.sleep(5000);
	Utilities.searchResources("Apps", "LI",test)	;	
	report.endTest(test);
	}
	
	
	
	@Test(priority=28)	
	
	public void VerifyLogout() throws Exception {
	 test = report.startTest("veirfy Logout");
	driver.findElement(By.xpath("//*[@id='logout']")).click();	
	Thread.sleep(3000);
	try{
		     wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("Login")));
		     test.log(LogStatus.PASS, "Logout successful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Logout")));
			
	}catch(NoSuchElementException e){
			test.log(LogStatus.FAIL, "Logout unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Logout")));
		}

 	report.endTest(test);
		
	} 

	
	 
	 @Test(priority=22)	
		public void RegisterFreeUser() throws IOException, InterruptedException
		{
			test=report.startTest("Register Free User");
			Thread.sleep(2000);
			Utilities.AppScript_RegisterFreeUser(report, test);
			report.endTest(test);
		}
	 
	 
	
	//@Test(priority=22)	
	public void Prescriber() {
		test=report.startTest("Prescriber Apps.");
	try{
     	Utilities.appscript_mailflow("appscriptpatient1@gmail.com","","https://www.google.com/gmail/about/");
	//test.log(LogStatus.PASS, "Prescriber functionality is successful"+test.addScreenCapture(Utilities.screenshot(driver, "Prescriber test")));
		}catch(Exception e){
			test.log(LogStatus.FAIL, "Prescriber functionality is Unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "Prescriber test")));	
	}
	
	report.endTest(test);
	
	}
	 
	 
	 
	 
	
	@Test(priority=23)	
 * 
 
	
	@Test(priority=26)	
	public void AddRemoveFavoriteApps() throws Exception
	{
		test=report.startTest("AddRemoveFavoriteApps");
		driver.findElement(Utilities.getLocator("Apps")).click();
		
		Thread.sleep(2000);
		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
		
		Utilities.SelectFormularyResource(AppHeaderSelected,report,test);
	//	Utilities.SelectProduct(AppSelected, AppHeaderSelected, report, test);
		Utilities.ClickProductAndVerify(AppHeaderSelected,report,test);
		Utilities.SelectProduct(AppSelected,AppHeaderSelected,report,test);
	  	report.endTest(test);
	}
	
	
	
	@Test(priority=27)	
	public void AddRemoveFavoriteDevices() throws Exception
	{
		test=report.startTest("AddRemoveFavoriteDevices");
		driver.findElement(Utilities.getLocator("Devices")).click();
		Thread.sleep(2000);

		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
		
		Utilities.SelectFormularyResource(AppHeaderSelected,report,test);
	//	Utilities.SelectProduct(AppSelected, AppHeaderSelected, report, test);
		Utilities.ClickProductAndVerify(AppHeaderSelected,report,test);
		Utilities.SelectProduct(AppSelected,AppHeaderSelected,report,test);
		report.endTest(test);
	}
	
	

	@Test(priority=28)	
	public void AddRemoveFavoriteDigitalContent() throws Exception
	{
		test=report.startTest("AddRemoveFavoriteDigital Content");
		driver.findElement(Utilities.getLocator("DigitalContent")).click();
		Thread.sleep(2000);

		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
		Utilities.SelectFormularyResource(AppHeaderSelected,report,test);
	//	Utilities.SelectProduct(AppSelected, AppHeaderSelected, report, test);
		Utilities.ClickProductAndVerify(AppHeaderSelected,report,test);
		Utilities.SelectProduct(AppSelected,AppHeaderSelected,report,test);
		
		report.endTest(test);
	}
	
	
	
	
	@Test(priority=29)	
	public void VerifyAppheaders() throws Exception
	{
		test=report.startTest("Verify App Headers");
		
	driver.findElement(Utilities.getLocator("Apps")).click();
		Thread.sleep(3000);
		
	String AppsMenuCount= util.getCellData(FilePath,sheet,"Appscount",1,"string");
	int Aappscount=Integer.parseInt(AppsMenuCount);	
	String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
	String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
	String DigitalHeaderSelected= util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
   	Utilities.VerifyProductAndMenu(AppHeaderSelected, report, test,"AppsSubMenu",Aappscount);
	report.endTest(test);
	}
	
	
	
	@Test(priority=30)	
	public void VerifyDeviceheaders() throws Exception
	{
		test=report.startTest("Verify Device Headers");
		
		driver.findElement(Utilities.getLocator("Devices")).click();;
		Thread.sleep(3000);
		
	String DevicesMenuCount= util.getCellData(FilePath,sheet,"Devicecount",1,"string");
	int Aappscount=Integer.parseInt(DevicesMenuCount);	
	String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
	String DigitalHeaderSelected= util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
   	Utilities.VerifyProductAndMenu(DeviceHeaderSelected, report, test,"DevicesSubMenu",Aappscount);
	report.endTest(test);
	}
	
	@Test(priority=31)	
	public void VerifyDigitalheaders() throws Exception
	{
		test=report.startTest("Verify Digital content  Headers");
		Thread.sleep(3000);
		driver.findElement(Utilities.getLocator("DigitalContent")).click();
		Thread.sleep(3000);
		
	String AppsMenuCount= util.getCellData(FilePath,sheet,"Dccount",1,"string");
	int Aappscount=Integer.parseInt(AppsMenuCount);	
	String DigitalHeaderSelected= util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
   	Utilities.VerifyProductAndMenu(DigitalHeaderSelected, report, test,"DigitalContentSubMenu",Aappscount);
	report.endTest(test);
	}
	
	 @Test(priority=32) 
		public void SubmitRatings() throws Exception {
		 Thread.sleep(4000);
driver.findElement(Utilities.getLocator("Apps")).click();
Thread.sleep(4000);
		try{
		test=report.startTest("Submit Reviews and Rating");
		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");

		Utilities.VerifyReviews(AppSelected,report,test);
		}catch(Exception e){
		test.log(LogStatus.FAIL, "Submit Reviews and Rating functionality is Unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "Prescriber test"))); 
		}

		report.endTest(test);

		}
	 
	 
	
	
	
	
	@Test(priority=33)
	public void VerifyLogout() throws Exception {
		 test = report.startTest("veirfy Logout");
		driver.findElement(By.xpath("//*[@id='logout']")).click();	
		Thread.sleep(3000);
		try{
			     wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("Login")));
			     test.log(LogStatus.PASS, "Logout successful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Logout")));
				
		}catch(NoSuchElementException e){
				test.log(LogStatus.FAIL, "Logout unsuccessful"+test.addScreenCapture(Utilities.screenshot(driver, "Verify Logout")));
			}

	 	report.endTest(test);
			
		}*/
	

/*
@Test(priority=29)
	public void Registeruser() throws IOException, InterruptedException
	{
		test=report.startTest("Register");
	//	ExtentTest logger = report.startTest("Verifying the Apps selected in ViewFormulary is Added Or Removed to Or from Fevorites");

		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");

        Utilities.AppScript_RegisterFreeUser(report, test);
	//ApplicationUtility.Logout(report,test);
		report.endTest(test);
	}*/
	
	

	
	
	//WebElement SearchMedicine = driver.findElement(By.xpath(element.SearchMedicine));
	//SearchMedicine.sendKeys(AppValueSearch);

/*
	public void SearchApps()
	
	//WebElement SearchMedicine = driver.findElement(By.xpath(element.SearchMedicine));
	//SearchMedicine.sendKeys(AppValueSearch);

	List<WebElement> autoPopulatedList=driver.findElements(By.xpath("//*[@id='search-form']/div/span/span"));  
	
	for(WebElement ele:autoPopulatedList)  
	{  
		System.out.println(ele.getText());
		test.log(LogStatus.INFO, "The autopopulated values in search box of Appscript application is: <b>"+ele.getText()+"</b>");
	}

	//screenshot = util.screenshot(driver, "Search a product in Appscript Application");
        test.log(LogStatus.PASS, "Search a product in Appscript Application and AutoPopulated values" + logger.addScreenCapture(screenshot));
        System.out.println("Searching Product with" + SearchMedicine.getAttribute("value"));
	//System.out.println(SearchMedicine.getAttribute("value"));
	test.log(LogStatus.PASS, "The searched product in dashboard page of Appscript application is: <b>"+SearchMedicine.getAttribute("value")+"</b>");
	driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
	SearchMedicine.sendKeys(Keys.ENTER);


	//Verify the product name in the dashboard page
	
	try{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DisplayingApp)));
		String DisplayingRecords = driver.findElement(By.xpath(element.DisplayingApp)).getText();
		
	//	logger.log(LogStatus.PASS,"<marquee><font color="+"\""+col+"\">Displayed record of product name in dashboard page of Appscript application is: "+DisplayingRecords+"</marquee>");
		screenshot = util.screenshot(driver, "Displayed Search Results");
		System.out.println("displaying record name is :"+DisplayingRecords);
	}
	
	catch(Exception ex){
		ex.printStackTrace();
		ex.getMessage();
		//logger.log(LogStatus.FAIL, "<marquee><font color="+"\""+Failredcol+"\"><b> Product Search Result is Failed, Product Name "+ AppValueSearch  +"</b></marquee>" + logger.addScreenCapture(screenshot));
		report.endTest(logger);
		driver.findElement(By.xpath(element.Logout)).click();
		Thread.sleep(3000);
		driver.close();
		 driver.quit();
		continue;
	}

} */
	

	@AfterClass
	public void ReportFlush() throws InterruptedException{
	//driver.findElement(By.xpath("//*[@id='logout']")).click();	
	
	report.flush();
	//Thread.sleep(4000);
	//	driver.close();
		//driver.quit();
	
// Jenkins related file copy paste.. 
	 String jenkinsfilepath=System.getProperty("user.dir")+ "/Reports/Jenkins/index.html";
		 
		 
		/* FileInputStream instream = null;
			FileOutputStream outstream = null;
		 
		    	try{
		    	    File infile =new File(Finalreportpath);
		    	    File outfile =new File(jenkinsfilepath);
		 
		    	    instream = new FileInputStream(infile);
		    	    outstream = new FileOutputStream(outfile);
		 
		    	    byte[] buffer = new byte[1024];
		 
		    	    int length;
		    	
		    	    while ((length = instream.read(buffer)) > 0){
		    	    	outstream.write(buffer, 0, length);
		    	    }

		    	    //Closing the input/output file streams
		    	    instream.close();
		    	    outstream.close();

		    	    System.out.println("File copied successfully!!");
		 
		    	}catch(IOException ioe){
		    		ioe.printStackTrace();
		    	 }
		 
		 // copy directory..
		    	
		    	String source = System.getProperty("user.dir")+"/Screenshots";
		        File srcDir = new File(source);

		        //
		        // The destination directory to copy to. This directory
		        // doesn't exists and will be created during the copy
		        // directory process.
		        //
		        String jenkinsDirectorypath=System.getProperty("user.dir")+ "/Reports/Jenkins/Screenshots";
		     //   String destination = "C:/Demo/target";
		        File destDir = new File(jenkinsDirectorypath);
		        
		        try {
		            //
		            // Copy source directory into destination directory
		            // including its child directories and files. When
		            // the destination directory is not exists it will
		            // be created. This copy process also preserve the
		            // date information of the file.
		            //
		            FileUtils.copyDirectory(srcDir, destDir);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }*/


	}
}
