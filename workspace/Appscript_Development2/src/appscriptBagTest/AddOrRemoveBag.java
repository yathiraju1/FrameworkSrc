package appscriptBagTest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.UtilLib;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class AddOrRemoveBag 
{


	public static UtilLib util = new UtilLib();
	public static String TestCase_Name = "AddProductsToCart";
	String  FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
	String  sheet="Appscript";
	//	ExtentReports report;
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
	public void AddToCart() throws Exception{

		logger = report.startTest("Add Apps - Bag");

		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");


		ApplicationUtility.AppScript_Login(report, logger);
		ApplicationUtility.AddProductsToBag(AppHeaderSelected,report, logger);
		report.endTest(logger);

	}
	@Test
	public void RemoveBag() throws IOException, InterruptedException{
		logger = report.startTest("Remove Apps - Bag");

		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
		ApplicationUtility.RemoveBag(report, logger);
		report.endTest(logger);

	}
	@AfterTest
	public void teardown(){
		report.flush();

	}
	
	/*@DataProvider(name="empLogin")
	public Object[][] loginData() {
		 Object[][] = util.getCellData(FilePath, sheet, "ResourceTypes", 1, "string");
		return arrayObject;
	}*/
}
