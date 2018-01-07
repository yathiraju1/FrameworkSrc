package appscriptBagTest;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.UtilLib;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import sun.applet.AppletListener;

public class BagLimitValidation
{


	public static UtilLib util = new UtilLib();
	public static String TestCase_Name = "VerifyBagLimitValidationMessage";
	String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
	String sheet="Appscript";
	ExtentReports report;


	@BeforeMethod
	public void reportconfig() {
		report=UtilLib.Instance(TestCase_Name);
		report.config().documentTitle("Verify BagLimit-ValidationMessage");
		report.config().reportHeadline("Verify BagLimit-ValidationMessage execution Report");
	}
	@Test
	public void VerifyBagLimitValidationMessage() throws Exception
	{

		ExtentTest logger = report.startTest("Verify the Validation Message for Bag Limit");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
		String CategorySelected= util.getCellData(FilePath, sheet, "Category-Apps", 1, "string");
		ApplicationUtility.AppScript_Login(report, logger);
		ApplicationUtility.BagLimit_ValidationMessage(CategorySelected,AppHeaderSelected,report, logger);
		report.endTest(logger);
	}

	@AfterTest
	public void teardown(){
		report.flush();

	}

}
