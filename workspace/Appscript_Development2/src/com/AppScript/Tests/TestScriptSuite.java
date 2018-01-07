package com.AppScript.Tests;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.UtilLib;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestScriptSuite 
{


	public static UtilLib util = new UtilLib();
	public static String TestCase_Name = "ViewFormulary and AddOrRemove Favorites-Apps";
	String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
	String sheet="Appscript";
	ExtentReports report;


	@BeforeMethod
	public void reportconfig() {
		report=UtilLib.Instance(TestCase_Name);
		report.config().documentTitle("ViewFormulary and AddOrRemove Favorites-Apps");
		report.config().reportHeadline("ViewFormulary and AddOrRemove Favorites-Apps execution Report");
	}


	@Test
	public void ViewAllFormulary() throws IOException, InterruptedException
	{

		ExtentTest logger = report.startTest("Verifying the Apps selected in ViewFormulary is Added Or Removed to Or from Fevorites");

		String AppSelected= util.getCellData(FilePath, sheet, "ProductSelected", 1, "string");
		String AppHeaderSelected= util.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");


		ApplicationUtility.AppScript_RegisterFreeUser(report, logger);
		//View Formulary
		ApplicationUtility.ClickMenu(AppHeaderSelected,report,logger);
		ApplicationUtility.ClickProductAndVerify(AppHeaderSelected,report,logger);
		// Favorites. 
		ApplicationUtility.SelectProduct(AppSelected,AppHeaderSelected,report,logger);

		ApplicationUtility.Logout(report,logger);
		report.endTest(logger);
	}

	@AfterTest
	public void teardown(){
		report.flush();

	}
}
