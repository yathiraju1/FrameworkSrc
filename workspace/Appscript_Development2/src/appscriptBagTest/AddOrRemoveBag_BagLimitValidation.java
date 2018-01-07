package appscriptBagTest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.UtilLib;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import appscriptAutomationTest.TestcaseTrigger;
import appscriptAutomationTest.Utilities;

public class AddOrRemoveBag_BagLimitValidation 
{

   public static UtilLib util = new UtilLib();
	public static String TestCase_Name = "AddOrRemove Bag and Bag Limitation Validation Message";
	String  FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
	String  sheet="Appscript";
	ExtentTest logger;
		
	

	static DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
	static Date date = new Date();
	String workingDir = System.getProperty("user.dir");
	ExtentReports report  = new ExtentReports(workingDir+ "/ExtentReport/AutomationBag"+dateFormat.format(date)+".html",true);

	@BeforeClass
	public void config(){
		report.config().documentTitle("Appscript");
		report.config().reportHeadline("Automation Bag");
		report.config().reportName("Appscript ");
}
	
@Test(priority=1)
// Login to Appscript  Application..
 public void LogintoBag() throws Exception{
  logger = report.startTest("Login to Appscript Application");
    ApplicationUtility.AppScript_Login(report, logger);
      report.endTest(logger);
// Need to add report statement. [Soft Assertion].  
}
// Add to Bag Under Apps. 
@Test(priority=2)
public void AddAppsToBag() throws IOException, InterruptedException{
  	logger = report.startTest("Bag Apps-Add to Bags");
     String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
   ApplicationUtility.AddProductsToBag(AppHeaderSelected,report, logger);
   report.endTest(logger);
}
// Remove from Bag Under Apps.......
 @Test (priority=3)
public void RemoveAppsfromBag() throws IOException {
 logger = report.startTest("Bag Apps - Remove from Bag");
 String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
 ApplicationUtility.RemoveProductsFromBag(AppHeaderSelected,report, logger);
 report.endTest(logger);
}
 // Add Devices to Bag....
 @Test(priority=4)
 public void AddDevicestoBag() throws IOException {
	 logger = report.startTest("Bag Devices - Add to Bag [+] ");
	 String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
	 ApplicationUtility.AddProductsToBag(DeviceHeaderSelected,report, logger);
	 report.endTest(logger);
}
 
// Remove Devices from Bag....
 @Test(priority=5)
 public void RemoveDevicesFromBag() throws IOException {
	 logger = report.startTest("Bag Devices - Remove from Bag [-] ");
	 String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
	 ApplicationUtility.RemoveProductsFromBag(DeviceHeaderSelected,report, logger);
	 report.endTest(logger);
}
 
 // Add Contents to Bag.. [Digital content]
 @Test(priority=6)
 public void AddContentstoBag() throws IOException {
	 logger = report.startTest("Bag Content - Add to Bag [+] ");
	 String DigitalHeaderContentSelected=util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
	 ApplicationUtility.AddProductsToBag(DigitalHeaderContentSelected,report, logger);
	 report.endTest(logger);
}
 
 // Remove Contents from Bag.. [Remove Content]. 
 @Test(priority=7)
 public void RemoveContentfromBag() throws IOException {
	 logger = report.startTest("Bag Content - Remove from Bag [-] ");
	 String DigitalHeaderContentSelected=util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
	 ApplicationUtility.RemoveProductsFromBag(DigitalHeaderContentSelected,report, logger);
	 report.endTest(logger);
}
 
// Remove from Bag Button Functionality from Resource Type - Apps. 
@Test (priority=8)
public void RemoveButtonBagApps() throws IOException, InterruptedException{
  	logger = report.startTest("Apps :  Remove Bag - Button");
     String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
    ApplicationUtility.RemoveBagResourceButton(AppHeaderSelected,report, logger);
	report.endTest(logger);
}

//Remove from Bag  Button Functionality from Resource Type - Devices. 
@Test (priority=9)
public void RemoveButtonBagDevices() throws IOException, InterruptedException{
  	logger = report.startTest("Devices :  Remove Bag - Button");
   	String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
   ApplicationUtility.RemoveBagResourceButton(DeviceHeaderSelected,report, logger);
   report.endTest(logger);
}

//Remove from Bag  Button Functionality from Resource Type - Content. 

@Test (priority=10)
public void RemoveButtonBagContent() throws IOException, InterruptedException{
  	logger = report.startTest("Content :  Remove Bag - Button");
  String DigitalHeaderContentSelected=util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
   ApplicationUtility.RemoveBagResourceButton(DigitalHeaderContentSelected,report, logger);
	report.endTest(logger);
}


@Test (priority=11)
public void VerifyBagLimitValidationMessage() throws IOException, InterruptedException
{

	logger = report.startTest("Verify the Validation Message for Bag Limit");
	String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
	String CategorySelected_APPS= util.getCellData(FilePath, sheet, "Category-Apps", 1, "string");
	
	ApplicationUtility.BagLimit_ValidationMessage(CategorySelected_APPS,AppHeaderSelected,report, logger);

	report.endTest(logger);
}


@Test(priority=12)
public void VerifyLogout() throws Exception {
	logger = report.startTest("veirfy Logout");
	ApplicationUtility.Logout(report, logger);
 	report.endTest(logger);
} 
	
	@AfterClass
	public void teardown(){
		report.flush();

	}

}