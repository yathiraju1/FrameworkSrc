package com.AppScript.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import appscriptAutomationTest.Utilities;


public class UtilLib {
	public static WebDriver driver;
	public static ObjectDefinitionLibrary element = new ObjectDefinitionLibrary();
	public static FileInputStream fis = null;
	public static XSSFWorkbook workbook = null;
	public static XSSFSheet sheet = null;
	public static XSSFRow row = null;
	public static XSSFCell cell = null;
	FormulaEvaluator evaluator =null;
	static String Reportgenenrationfolder = null;
	
	/******************************************************************************************** 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Function_Name : getDriver
	 * @Description : Creates Driver object to launch scripts in different browser
	 ********************************************************************************************/
	public static  WebDriver getDriver () throws IOException, InterruptedException{
		String browserType=getPropertiesValue("Browser");
		switch (browserType) {
		case "Chrome":
			System.out.println("=========="+browserType);

			String downloadpath = System.getProperty("user.dir")+"\\"+"Downloaded reports";
			File file = new File(downloadpath);
			file.mkdir();
			String downloadFilepath = file.getAbsolutePath();
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("profile.default_content_setting_values.notifications",1);
			chromePrefs.put("download.prompt_for_download", "false");
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);

			// add parameter which will disable the extension
		//	options.addArguments("user-data-dir=C:/Users/KSharif/AppData/Local/Google/Chrome/User Data");

			//options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			cap.setCapability(ChromeOptions.CAPABILITY, options);

			
			
		/*	cap.setBrowserName("chrome");
			cap.setPlatform(Platform.ANY);*/
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");
		//	Thread.sleep(10000);
			driver = new ChromeDriver(options);
	//		driver= new RemoteWebDriver(new URL("http://10.205.6.28:5555/wd/hub"),cap);


			driver.manage().window().maximize();


			break;
		case "Firefox":
			/*System.out.println("^^^^^^^^^^^^^^^^^^"+browserType);
			File pathToBinary = new File("C:\\Users\\Madhura.G\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
			FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			driver = new FirefoxDriver(ffBinary,firefoxProfile);*/

			//			ProfilesIni ini = new ProfilesIni();
			//			FirefoxProfile fp = ini.getProfile("default");
			//			driver = new FirefoxDriver(fp);
			
			
			
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/geckodriver.exe");
			//System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir")+"/geckodriver.exe");
			
			//webdriver.firefox.marionette
			DesiredCapabilities cap1=DesiredCapabilities.firefox();
			//cap.se
			cap1.setCapability("marionette", true);
			//cap.setCapability(capabilityName, value);
			FirefoxProfile profile = new FirefoxProfile();
			cap1.setCapability(FirefoxDriver.PROFILE, profile);
			
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
					
			 driver=new FirefoxDriver(cap1);
			
			
			/*
			
			
			
			System.setProperty("webdriver.gecko.driver","C:\\JARS\\geckodriver.exe");
			driver = new FirefoxDriver();*/
			driver.manage().window().maximize();
			break;
		case "IE":
			System.out.println("*********"+browserType);
			System.setProperty("webdriver.IEDriverServer.driver",System.getProperty("user.dir")+"/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			break;
		default:
			System.out.println("browser : " + browserType+ " is invalid..");
		}
		return driver;
	}
	/******************************************************************************************** 
	 * @Function_Name : getPropertiesValue
	 * @Description : Read values from property file for constant values
	 ********************************************************************************************/
	public static String getPropertiesValue(String key) throws IOException {
		String workingDir = System.getProperty("user.dir");
		
		String configpath = workingDir + "/AppData/ConfigFile.Properties";
		Properties props = new Properties();
		FileInputStream URLPath = new FileInputStream(configpath);
		props.load(URLPath);
		String url = props.getProperty(key);
		//System.out.println("url= "+url);
		return url;
	}
	/******************************************************************************************** 
	 * @Function_Name : systemTimeStamp
	 * @Description : Function to fetch the current system date 
	 ********************************************************************************************/
	public static String systemTimeStamp() {
		DateFormat df = new SimpleDateFormat("dd.MMM.yyyy-HH.mm.ss");
		Date today = Calendar.getInstance().getTime();
		String runTime = df.format(today);
		//System.out.println(df.format(today));
		return runTime;
	}
	/******************************************************************************************** 
	 * @throws InterruptedException 
	 * @Function_Name : isElementPresent
	 * @Description : Checks if Element is present
	 ********************************************************************************************/
	public static boolean isElementPresent(String element , By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchFrameException e) {
			return false;
		}
	}
	/********************************************************************************************
	 * @Function_Name :  verifyElementPresent
	 * @Description : Verifies the Element is present in the page
	 *@param ElementName - Name of the Element
	 *@param Xpath - XPATH of the Element 
	 ********************************************************************************************/
	public static boolean verifyElementPresent(String ElementName,String Xpath){
		int RerunFlag = 0;
		try {
			boolean Verify = UtilLib.isElementPresent(ElementName, By.xpath(Xpath));
			if (Verify  == true){
				boolean Verify_Displayed  = driver.findElement(By.xpath(Xpath)).isDisplayed();
				if (Verify_Displayed  == true){
					System.out.println(ElementName+" is present in the page");
				} else {
					RerunFlag = RerunFlag+1;
				}
			} else {
				RerunFlag = RerunFlag+1;
			}

			if(RerunFlag>0){
				return false;
			} else{
				return true;
			}
		} catch (Error e) {
			e.printStackTrace();
			return false;
		}
	}
	/********************************************************************************************
	 * @Function_Name :  screenshot
	 * @Description : Function to capture the screenshot
	 ********************************************************************************************/
	public static String screenshot(WebDriver driver, String screenshotname) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			DateFormat screenshotName1 = new SimpleDateFormat("dd-MMMM-yyyy_HH-mm-ss");
			Date screenshotDate = new Date();
			String picName = screenshotName1.format(screenshotDate);

			String dest = System.getProperty("user.dir") + "/Screenshots/" + picName +"_"+ screenshotname+ ".png";
			File destination = new File(dest);
			FileUtils.copyFile(source, destination);
			System.out.println("Screenshot Captured");

			return dest;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	/********************************************************************************************
	 * @Function_Name : getCellData
	 * @Description :  Reusable function for retrieving the test data from excel sheet
	 ********************************************************************************************/

	public  String getCellData(String xlFilePath,String sheetName, String colName, int rowNum,String cellvaltype) throws IOException
	{
		String cellvalue="";
		fis = new FileInputStream(xlFilePath);
		workbook = new XSSFWorkbook(fis);
		try
		{
			int col_Num = -1;
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			for(int i = 0; i < row.getLastCellNum(); i++)
			{
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}

			row = sheet.getRow(rowNum);
			cell = row.getCell(col_Num);
			//			System.out.println(cell.getCellType());
			switch (cellvaltype)

			{
			case "date":
			{
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				//Date date = new Date();
				String time = sdf.format(cell.getDateCellValue());
				cellvalue=String.valueOf(time);
				//				System.out.println(time);
				break;
			}
			case "string":
			{
				cellvalue=cell.getStringCellValue();
				break;
			}
			case "int":
			{
				cellvalue=String.valueOf(cell.getNumericCellValue());
				break;
			}

			} 
			System.out.println(cellvalue);
			return cellvalue;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist  in Excel";
		}
	}
	/********************************************************************************************
	 * @Function_Name :  getRowCount
	 * @Description : Function to retrieve the Row count from the excel sheet
	 ********************************************************************************************/
	public  int getRowCount(String xlpath,String sheetName)
	{
		int rc=0;
		try
		{
			FileInputStream fis=new FileInputStream(xlpath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			rc=sheet.getLastRowNum();
		}
		catch(Exception e)
		{
		}
		return rc;
	}
	/********************************************************************************************
	 * @Function_Name :  deleteFolder
	 * @Description : Function to delete the files from a folder
	 ********************************************************************************************/
	public static void deleteFolder(File rfolder) 
	{
		File[] files = rfolder.listFiles();
		if(files!=null) 
		{ 
			for(File f: files) 
			{
				if(f.isDirectory())
				{
					deleteFolder(f);
					f.delete();
				} 
				else 
				{
					f.delete();
				}
			}
		}
	}
	
	
	/******************************************************************************************** 
	 * @Function_Name : Instance
	 * @Description : Function to generate Extent Reports
	 ********************************************************************************************/
	
	public static ExtentReports Instance(String TestcaseName){
		ExtentReports extent ;
		String currentTime = UtilLib.systemTimeStamp();
		String foldername=TestcaseName+currentTime;
		
		String folderName_New = foldername;
		
   // create directory which has test case name with date and time
		File folderdir=new File("./Report/" + foldername);
		folderdir.mkdirs(); 

	    Reportgenenrationfolder="./Report/" + foldername;
		//Reportgenenrationfolder="C:\\Users\\sandhya.kr\\Documents\\Appscript-Development\\Report" + foldername;
		 String ReportPath = Reportgenenrationfolder  + "/Automation_" + TestcaseName+"_" + currentTime + "Extentreport.html";
		extent= new ExtentReports(ReportPath,true);
		return extent;
	}
	
	
	
	

}

