package appscriptBagTest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.UtilLib;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class AddOrRemoveBag_BagLimitValidation2Oct16 
{


	public static UtilLib util = new UtilLib();
	public static String TestCase_Name = "AddOrRemove Bag and Bag Limitation Validation Message";
	String  FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
	String  sheet="Appscript";
	ExtentTest logger;

	static DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
	static Date date = new Date();
	String workingDir = System.getProperty("user.dir");
	ExtentReports report  = new ExtentReports(workingDir+ "/ExtentReport/Extentreport"+dateFormat.format(date)+".html",true);

	@BeforeClass
	public void config(){
		report.config().documentTitle("Appscript");
		report.config().reportHeadline("Automation Bag");
		report.config().reportName("Appscript ");
	}
	
	@Test
	public void AddToBag() throws Exception{


		logger = report.startTest("Add to Bag-Apps,Devices,DigitalContent");

		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
		String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
		String DigitalHeaderContentSelected=util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");

		ApplicationUtility.AppScript_Login(report, logger);
		ApplicationUtility.AddProductsToBag(AppHeaderSelected,report, logger);
		ApplicationUtility.AddProductsToBag(DeviceHeaderSelected,report, logger);
		ApplicationUtility.AddProductsToBag(DigitalHeaderContentSelected,report, logger);

		report.endTest(logger);
	}

	@Test
	public void RemoveBag() throws IOException, InterruptedException{


		logger = report.startTest("Remove from Bag-Apps,Devices,DigitalContent");

		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
		String DeviceHeaderSelected= util.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
		String DigitalHeaderContentSelected=util.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");

		ApplicationUtility.RemoveProductsFromBag(AppHeaderSelected,report, logger);
		ApplicationUtility.RemoveProductsFromBag(DeviceHeaderSelected,report, logger);
		ApplicationUtility.RemoveProductsFromBag(DigitalHeaderContentSelected,report, logger);
		report.endTest(logger);
	}

	@Test
	public void VerifyBagLimitValidationMessage() throws IOException, InterruptedException
	{

		logger = report.startTest("Verify the Validation Message for Bag Limit");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
		String CategorySelected_APPS= util.getCellData(FilePath, sheet, "Category-Apps", 1, "string");
		
		ApplicationUtility.BagLimit_ValidationMessage(CategorySelected_APPS,AppHeaderSelected,report, logger);

		report.endTest(logger);
	}
	
	@AfterTest
	public void teardown(){
		report.flush();

	}


}