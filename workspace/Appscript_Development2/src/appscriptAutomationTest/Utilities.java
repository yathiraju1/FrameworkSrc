package appscriptAutomationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.AppScript.generic.ApplicationUtility;
import com.AppScript.generic.ObjectDefinitionLibrary;
import com.AppScript.generic.UtilLib;
import com.opencsv.CSVReader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;



public class Utilities extends TestcaseTrigger {


	static List<WebElement> appscount;
	public static ObjectDefinitionLibrary element = new ObjectDefinitionLibrary();

	//public static WebDriver driver;
	public static FileInputStream fis = null;
	public static XSSFWorkbook workbook = null;
	public static XSSFSheet sheet = null;
	public static XSSFRow row = null;
	public static XSSFCell cell = null;
	FormulaEvaluator evaluator =null;




	public static By getLocator(String elementname) throws Exception{
		String element="";

		CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"/ObjectRepository_Appscript.csv"));
		String[] nextline;
		while((nextline=reader.readNext())!=null)
		{
			if(nextline[0].equalsIgnoreCase(elementname) ){
				element=nextline[1];
				break;
			}}
		reader.close();

		String locatorType = element.split(":")[0];
		String locatorValue = element.split(":")[1];
		// Return a instance of By class based on type of locator
		if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals("classname"))
				|| (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("tagname"))
				|| (locatorType.toLowerCase().equals("tag")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("linktext"))
				|| (locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);
		else if ((locatorType.toLowerCase().equals("cssselector"))
				|| (locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);
		else if (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);
		else
			throw new Exception("Locator type '" + locatorType+ "' not defined!!");       

	}

	/*	
	public static String getPropertiesValue(String key) throws IOException {

		String workingDir = System.getProperty("user.dir");
		String configpath = workingDir+ "/ConfigFile.csv";

            String element="";

		CSVReader reader = new CSVReader(new FileReader(configpath));
        String[] nextline;
        while((nextline=reader.readNext())!=null)
        {
        	if(nextline[0].equalsIgnoreCase(key) && nextline[2].equalsIgnoreCase("Y")){
          element=nextline[1];
        	break;
         }}
        reader.close();
        return element;
	}*/

	public static String getPropertiesValue_column(String key) throws IOException {
		String workingDir = System.getProperty("user.dir");
		String configpath = workingDir+ "/ConfigFileV1.csv";

		String element="";
		CSVReader reader = new CSVReader(new FileReader(configpath));
		String[] nextline;

		while((nextline=reader.readNext())!=null)
		{
			if( nextline[0].equalsIgnoreCase("Y")){
				if (key.equalsIgnoreCase("applicationUrl"))
					element=nextline[1];
				else if (key.equalsIgnoreCase("username"))
					element=nextline[2];
				else if (key.equalsIgnoreCase("password"))
					element=nextline[3];
				else if (key.equalsIgnoreCase("Region"))
					element=nextline[4];
				else if (key.equalsIgnoreCase("Price")) 
					element=nextline[5];
				else element=nextline[6];
				break;
			}}
		reader.close();
		return element;

	}


	public static String getpropertiesexcel(String key) throws IOException{
		System.out.println("Inside getpropertiesexcel");
		String workingDir = System.getProperty("user.dir");
		String configpath = workingDir+ "/ConfigFileV1.xlsx";
		FileInputStream excellFile = new FileInputStream(new File(configpath));
		XSSFWorkbook workbook = new XSSFWorkbook(excellFile);
		Sheet sheet=workbook.getSheet("ConfigFileV1");
		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		XSSFCell cell1 = null;String keyword=null;
		XSSFCell cell2 = null;
		for (int i=firstRow;i<lastRow;i++){
			XSSFRow row = (XSSFRow) sheet.getRow(i);
			cell1=row.getCell(0);
			keyword=cell1.getStringCellValue();
			System.out.println("keyword: "+keyword);

			if (keyword.equalsIgnoreCase("Y")){
				if (key.equalsIgnoreCase("applicationUrl")){
					cell2=row.getCell(1);keyword=cell2.getStringCellValue();System.out.println("Cell1:"+keyword);break;
				}
				if (key.equalsIgnoreCase("username")){
					cell2=row.getCell(2);keyword=cell2.getStringCellValue();break;
				}
				if (key.equalsIgnoreCase("password")){
					cell2=row.getCell(3);keyword=cell2.getStringCellValue();break;
				}
				if (key.equalsIgnoreCase("Region")){
					cell2=row.getCell(4);keyword=cell2.getStringCellValue();break;
				}
				if (key.equalsIgnoreCase("Price")){
					cell2=row.getCell(5);keyword=cell2.getStringCellValue();break;
				}
				else {
					cell2=row.getCell(5);keyword=cell2.getStringCellValue();break;
				}

			}

		}
		String locator=keyword;
		return locator;

	}

	public static String passworddecoder(String encodedpassword) throws Base64DecodingException {
		String decode=encodedpassword;
		byte[] decodedBytes = Base64.decode(decode);
		decode=new String(decodedBytes);
		return decode;
	}


	public static boolean verifyprice(String pricecat,ExtentTest test) throws Exception{
		verify=true;
		je = (JavascriptExecutor) driver;

		driver.findElement(Utilities.getLocator(pricecat)).click();

		if (pricecat.equalsIgnoreCase("free")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("FreeFilter")));
		}
		else if (pricecat.equalsIgnoreCase("freemium")){
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("FreemiumFilter")));	
		}else
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("PaidFilter")));
		}

		appscount=driver.findElements(Utilities.getLocator("totalapps"));
		list=driver.findElements(Utilities.getLocator("price_div"));

		for(int i=0;i<list.size();i++){
			System.out.println("Inside element trace loop");

			int j=i+1;
			String ele1="(//div[@class='icon-bar info row'])"+"["+j+"]"+"//*[@class='price-inner']";

			System.out.println("Inside try");
			if(driver.findElements(By.xpath(ele1)).size() == 0)

			{
				System.out.println("Inside Catch");
				String ele2="(//div[@class='icon-bar info row'])"+"["+j+"]";
				WebElement w2=driver.findElement(By.xpath(ele2));
				je.executeScript("arguments[0].scrollIntoView(true);", w2);
				String apptextxpath="("+"(.//*[@class='app-name'])"+")"+"["+j+"]";
				System.out.println("apptextxpath: "+apptextxpath);
				String apptext=driver.findElement(By.xpath(apptextxpath)).getText();
				test.log(LogStatus.FAIL, "Failed at app: "+apptext+test.addScreenCapture(Utilities.screenshot(driver,"Failed at element"+j)));
				verify=false;			
			}else {
				System.out.println("Inside If element present loop");
				WebElement w1=driver.findElement(By.xpath(ele1));
				if (pricecat.equalsIgnoreCase("free")){
					if(!(w1.getText().equals("Free"))){
						System.out.println(w1.getText());
						verify=false;
						break;
					}}
				else if (pricecat.equalsIgnoreCase("freemium")){
					if(!(w1.getText().equals("Free+"))){
						System.out.println(w1.getText());
						verify=false;
						break;
					}}
				else{ 	
					if(!(w1.getText().matches(".*[0-9].*"))){
						System.out.println(w1.getText());
						verify=false;
						break;
					}
				}
			}

		}
		return verify;
	}


	public static boolean verifyplatform(String platform) throws Exception{
		verify=true;
		appscount=driver.findElements(Utilities.getLocator("totalapps"));
		list=driver.findElements(Utilities.getLocator("platform"));

		System.out.println("appscount size: "+appscount.size());
		System.out.println("list size: "+list.size());

		if (appscount.size() !=list.size()){
			verify=false;
		}

		iterator=list.iterator();
		while(iterator.hasNext()){
			elements=iterator.next();
			System.out.println( "Element: "+elements.getAttribute("class"));
			if (platform.equalsIgnoreCase("SwitchApple")){

				if(!(elements.getAttribute("class").equals("fa fa-apple"))){

					verify=false;
					break;
				}
			}
			else if(platform.equalsIgnoreCase("SwitchAndroid")){
				if(!(elements.getAttribute("class").equals("fa fa-android"))){

					verify=false;
					break;
				}

			}
		}
		return verify;
	}


	public static String screenshot(WebDriver driver, String screenshotname) {

		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			String dest = System.getProperty("user.dir") + "/Reports/"+folderName+"/Screenshots/" + screenshotname + dateFormat.format(date)+".png";
			String Destinationfile="./Screenshots/" +  screenshotname + dateFormat.format(date)+".png";
			//	String dest =  "./Reports/"+folderName+"/Screenshots/" +  screenshotname + dateFormat.format(date)+".png";
			//	String dest =  "./Reports/"+folderName+"/" +  screenshotname + dateFormat.format(date)+".png";
			//	String dest =  "./Reports/"+folderName+"/Screenshots/" +  screenshotname + dateFormat.format(date)+".png";
			//String Destinationfile="./Screenshots/" +  screenshotname + dateFormat.format(date)+".png";
			File destination = new File(Destinationfile);
			File Destinland=new File(dest);
			org.apache.commons.io.FileUtils.copyFile(source, Destinland);
			//	System.out.println("Screenshot Captured");
			return Destinationfile;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public static boolean verifypricerange(double startrange,double EndRange,String pricerange) throws Exception{
		verify=true;
		if(	driver.findElement(Utilities.getLocator("backtoallresource")).isDisplayed()){
			driver.findElement(Utilities.getLocator("backtoallresource")).click();
		}

		driver.findElement(Utilities.getLocator(pricerange)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("pricerangefilter")));


		list=driver.findElements(Utilities.getLocator("price"));
		iterator=list.iterator();
		while (iterator.hasNext()){
			elements=iterator.next();
			String s=elements.getText().replaceAll("[^\\d.]", "");
			System.out.println(s);
			Double d=Double.parseDouble(s);
			if(pricerange.equalsIgnoreCase("pricerange7")){
				if  (d<startrange){
					verify=false;
					break;
				}
			}
			else{
				if (d>EndRange || d<startrange){
					verify=false;
					break;
				}
			}
		}


		return verify;
	}





	public static void searchResources(String tab,String searchtext,ExtentTest test) throws Exception{
		String concat_string="";
		driver.findElement(Utilities.getLocator(tab)).click();
		wait.until(ExpectedConditions.attributeToBe(Utilities.getLocator(tab), "class", "active"));
		driver.findElement(Utilities.getLocator("searchappscript")).sendKeys(searchtext);
		test.log(LogStatus.PASS, "Searched Value for Resources is Active"+ "-" + tab + "- Search Key --" + searchtext);
		Thread.sleep(9000);
		screenshot=Utilities.screenshot(driver, "AutoSuggestions"+tab);
		// if (driver.findElements(By.xpath(".//*[@class='tt-suggestions']/div/p")).size() >0){
		if (driver.findElements(By.xpath(".//*[@class='tt-dataset-resources']/.//*[@class='tt-suggestions']/div/p")).size() >0){

			list=driver.findElements(By.xpath(".//*[@class='tt-dataset-resources']/.//*[@class='tt-suggestions']/div/p"));
			iterator=list.iterator();
			while(iterator.hasNext()){
				elements=iterator.next();
				//m=p.matcher(elements.getText());
				if(elements.getText().toLowerCase().contains(searchtext.toLowerCase())){
					concat_string=concat_string+" ||| "+elements.getText();
				}else {
					test.log(LogStatus.FAIL, "Auto Suggestions doesnt match -"+ elements.getText() + test.addScreenCapture(screenshot)  );
					//break;
				}

			}

			test.log(LogStatus.PASS, "Auto suggestions: Matched value "+concat_string+test.addScreenCapture(screenshot) );

			driver.findElement(Utilities.getLocator("searchappscript")).sendKeys(Keys.ENTER);
			Thread.sleep(9000);
			concat_string="";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='bread-crumb removable']")));
			if (driver.findElements(By.xpath("(.//*[@class='app-name'])")).size()!=0){
				list=driver.findElements(Utilities.getLocator("apptext"));
				iterator=list.iterator();
				while(iterator.hasNext()){
					elements=iterator.next();
					//m=p.matcher(elements.getText());
					if(elements.getText().toLowerCase().contains(searchtext.toLowerCase())){
						concat_string=concat_string+" | "+elements.getText();
					}else {test.log(LogStatus.FAIL, "Search doesnt match - " + elements.getText() +test.addScreenCapture(Utilities.screenshot(driver, "SearchResult Mismatch"+tab)));
					break;
					}
				}
				System.out.println(concat_string);
				test.log(LogStatus.PASS, " Search result: "+concat_string+test.addScreenCapture(Utilities.screenshot(driver, "SearchResult"+tab)) );
			}else{
				test.log(LogStatus.FAIL, "Not Found: "+test.addScreenCapture(Utilities.screenshot(driver, "No Search Results"+tab)) );
			}

		}
		else {System.out.println("Suggestions not present"); 
		test.log(LogStatus.FAIL, "No suggestions"+test.addScreenCapture(screenshot) );
		}
		driver.findElement(Utilities.getLocator("backtoallresource")).click();

	}	





	// Prescriber methods. 

	public static void appscript_mailflow(String patient_emailid,String patient_password,String gmail_url) throws Exception{
		String xpath;
		String instructions="Recommended for you. Please check!";String appname;String status_before;String instr_pat_page;String appname_pat_page;


		//	Thread.sleep(8000);
		driver.findElement(Utilities.getLocator("Apps")).click();
		Thread.sleep(4000);
		driver.findElement(Utilities.getLocator("appscriptlogo")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Utilities.getLocator("totalapps")));
		Thread.sleep(4000);
		driver.findElement(Utilities.getLocator("recommend1")).click();
		Thread.sleep(6000);
		appname=driver.findElement(Utilities.getLocator("appname_recmend_click")).getText();
		System.out.println(appname);
		driver.findElement(Utilities.getLocator("patient_email")).sendKeys(patient_emailid);
		Thread.sleep(2000);
		driver.findElement(Utilities.getLocator("checkbox")).click();
		//  send instruction...

		driver.findElement(Utilities.getLocator("textarea")).sendKeys(instructions);
		driver.findElement(Utilities.getLocator("sendtopateint")).click();
		Thread.sleep(8000);
		driver.findElement(Utilities.getLocator("mypatients")).click();
		Thread.sleep(8000);
		list=driver.findElements(Utilities.getLocator("patientslist"));
		for (int i=0;i<list.size();i++){
			int j=i+1;
			xpath=".//*[@class='table table-hover no-wrap dataTable no-footer dtr-inline']/tbody/tr["+j+"]/td[1]";
			//if (driver.findElement(By.xpath(xpath)).getText().equals(patient_emailid)){
			if (driver.findElement(By.xpath(xpath)).getText().equals("Auto12 Auto")|| driver.findElement(By.xpath(xpath)).getText().contains("Auto AutoAppscript")){

				test.log(LogStatus.PASS, "Patient selected is Auto AutoAppscript" + test.addScreenCapture(Utilities.screenshot(driver, "Patient Selected")));
				driver.findElement(By.xpath(xpath)).click();
				break;
			}


			if (driver.findElement(By.xpath(xpath)).getText().equals("appscriptpatient1@gmail.com")){

				test.log(LogStatus.PASS, "Patient selected is appscriptpatient1@gmail.com" + test.addScreenCapture(Utilities.screenshot(driver, "Patient Selected")));
				driver.findElement(By.xpath(xpath)).click();
				break;
			}


			// if and else boolean condition...

		}
		Thread.sleep(8000);
		if (driver.findElement(By.xpath(".//*[@id='prescriptions']/div[1]/div[1]/div[5]/i")).getAttribute("class").equals("fa fa-2x collapsed fa-plus-circle")){
			driver.findElement(By.xpath(".//*[@id='prescriptions']/div[1]/div[1]/div[5]/i")).click();	
		}

		appname_pat_page=driver.findElement(Utilities.getLocator("appname_pat_page")).getText();
		System.out.println("appname_pat_page: "+appname_pat_page);
		instr_pat_page=driver.findElement(Utilities.getLocator("instr_pat_page")).getText();
		System.out.println(instr_pat_page);
		status_before=driver.findElement(Utilities.getLocator("status_before")).getText();
		System.out.println(status_before);
		test.log(LogStatus.PASS, "Patient selected is appscriptpatient1@gmail.com  and its status -" + status_before + test.addScreenCapture(Utilities.screenshot(driver, "Patient Status")));
		String winHandleBefore = driver.getCurrentUrl();

		getData("appscriptpatient1@gmail.com", "Super123$", gmail_url);
		//	driver.manage().window().maximize();
		//	driver.switchTo().window(winHandleBefore);
		Thread .sleep(6000);
		//	driver.get(winHandleBefore);

		//	driver.navigate().to(winHandleBefore);
		driver.navigate().refresh();
		Thread.sleep(10000);

		String Statusafter= driver.findElement(By.xpath(".//*[@id='prescriptions']/div[1]/div[1]/div[3]/div")).getText();
		System.out.println("Status after: "+driver.findElement(By.xpath(".//*[@id='prescriptions']/div[1]/div[1]/div[3]/div")).getText());

		if (Statusafter.contains("Accessed") || (Statusafter.contains("Email Accessed"))){

			test.log(LogStatus.PASS, "Latest Patient Status is changed from Sent to Accessed" + test.addScreenCapture(Utilities.screenshot(driver, "Patient Status After Prescribed")));
		}else {
			test.log(LogStatus.FAIL, "Latest Patient Status is not changed from Sent to Accessed" + test.addScreenCapture(Utilities.screenshot(driver, "Patient Status After Prescribed")));
			test.log(LogStatus.FAIL, "Prescriber Functionality is failed" + test.addScreenCapture(Utilities.screenshot(driver, "Patient Status After Prescribed 2")));
		}




		//test.log(LogStatus.PASS, "Prescription Patient Status is -" + status_before + test.addScreenCapture(Utilities.screenshot(driver, "Patient Status")));
	}


	public static void getData(String email, String password, String URL) throws Exception {
		//System.setProperty("webdriver.chrome.driver","C:\\Driver\\chromedriver_win32\\chromedriver.exe");
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		//WebDriver driver = new ChromeDriver();

		if (Prescriberinfo.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/geckodriver.exe");
			DesiredCapabilities cap=DesiredCapabilities.firefox();
			cap.setCapability("marionette", true);
			FirefoxProfile profile = new FirefoxProfile();
			cap.setCapability(FirefoxDriver.PROFILE, profile);
			Gmaildriver=new FirefoxDriver(cap);

		}else if (Prescriberinfo.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");	
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
			Gmaildriver=new ChromeDriver(options);
		}


		//}


		Gmaildriver.manage().window().maximize();

		//	driver.manage().window().maximize();

		Gmaildriver.get(URL);
		//Gmaildriver.
		Gmaildriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		sleep();

		Gmaildriver.findElement(By.xpath("//a[text()='Sign In']")).click();
		sleep();

		Gmaildriver.findElement(By.id("identifierId")).sendKeys(email, Keys.ENTER);
		sleep();

		Gmaildriver.findElement(By.name("password")).sendKeys(password, Keys.ENTER);
		sleep();

		//Gmaildriver.findElement(By.xpath("//span[contains(text(),'IMS Health AppScript about your health')]")).click();
		sleep();

		List<WebElement> list=Gmaildriver.findElements(By.xpath(".//*[@class='xW xY ']/span/b"));
		System.out.println("Unread mail size in first page: "+list.size());
		boolean flag=false;
		String xpath1;String element1;String element2;String element3;
		for (int i=0;i<list.size();i++){
			int j=i+1;

			//list=driver.findElements(By.xpath("(.//*[@class='y6'])["+j+"]/span[1]"));
			xpath1="(.//*[@class='y6']/span[1]/b)["+j+"]";
			element1=Gmaildriver.findElement(By.xpath(xpath1)).getText();System.out.println(element1);
			String xpath2="(.//*[@class='xW xY ']/span/b)["+j+"]";
			element2=Gmaildriver.findElement(By.xpath(xpath2)).getAttribute("title");System.out.println(element2);
			String xpath3="(.//*[@class='yW']/.//*[@class='zF'])["+j+"]";
			element3=Gmaildriver.findElement(By.xpath(xpath3)).getAttribute("name");System.out.println(element3);
			// if (element1.equalsIgnoreCase("A message from IMS Health AppScript about your health") && (element3.equalsIgnoreCase("AppScript"))){
			if (element1.equalsIgnoreCase("A message from IMS Health AppScript about your health") ){		
				Gmaildriver.findElement(By.xpath(xpath1)).click();
				test.log(LogStatus.PASS, "Email Recieved Successful" + test.addScreenCapture(Utilities.screenshot(Gmaildriver, "Email recieved.")));
				Thread.sleep(10000);
				flag=true;
				break;
			}
		}

		if (flag == false){
			System.out.println("EMail not recieved");
		}
		else {
			String pin_num=Gmaildriver.findElement(By.xpath("(.//div[@style='font-size:25px;letter-spacing:3px'])")).getText();
			System.out.println("Pin Number: "+pin_num);
			Gmaildriver.findElement(By.xpath("(.//td[@valign='middle'])[4]")).click(); // view recommendation.
			Thread.sleep(15000);
			sleep();

			String oldTab = Gmaildriver.getWindowHandle();
			ArrayList<String> newTab = new ArrayList<String>(Gmaildriver.getWindowHandles());
			//newTab.remove(oldTab);
			Gmaildriver.switchTo().window(newTab.get(1));
			Gmaildriver.findElement(By.id("pin")).sendKeys(pin_num);
			Gmaildriver.findElement(By.xpath(".//*[@id='pinForm']/button")).click();
			Thread.sleep(10000);
			Gmaildriver.close();
			Gmaildriver.switchTo().window(newTab.get(0));
			Gmaildriver.close();

		}
	}

	public static void sleep() throws Exception {
		Thread.sleep(10000);
	}



	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : ClickMenu
	 * @Description : Function to Verify the Formulary page for Apps/Device/DigitalContent
	 ********************************************************************************************/

	public static void SelectFormularyResource(String HeaderSelection,ExtentReports report,ExtentTest logger) throws InterruptedException, IOException
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,60);
			//WebDriverWait wait = new WebDriverWait()

			//click view all formulary //.//*[@id='utility-nav']
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.NavigationBar_Formulary)));
			driver.findElement(By.xpath(element.ViewAllFormularyLink)).click();

			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ViewAllFormularyContent)));

			String ViewFormularyPage=Utilities.screenshot(driver, "Navigate to View All Formulary page");
			//	ApplicationUtility.LogPassMessage(logger, log ,"Navigate to View All Formulary link page" + logger.addScreenCapture(ViewFormularyPage));
			logger.log(LogStatus.PASS, "Navigate to View All Formulary link page" + logger.addScreenCapture(ViewFormularyPage));



			// Thread.sleep(3000);
			//click Menu Apps or devices or digital content
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FormularyContainer)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]")));
			WebElement DeviceHeader=driver.findElement(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]"));
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", DeviceHeader);
			Actions SelectAction = new Actions(driver);
			SelectAction.doubleClick(DeviceHeader).perform();

			String HeaderSelected=Utilities.screenshot(driver, "Resource Type Selected in View All Formulary page");
			//	ApplicationUtility.LogPassMessage(logger, log ,"Resource Type Selected in View All Formulary page : " +HeaderSelection + logger.addScreenCapture(HeaderSelected));
			logger.log(LogStatus.PASS, "Resource Type Selected in View All Formulary page : " +HeaderSelection + logger.addScreenCapture(HeaderSelected));
			/*WebElement menu= driver.findElement(By.xpath("//a[contains(text(),'"+HeaderSelection+"')]"));
		menu.click();  */ 
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();	
			String HeaderNotSelected=Utilities.screenshot(driver, "Resource Type is not Selected in View All Formulary page");
			//	ApplicationUtility.LogFailMessage(logger, log ,"Resource Type is not Selected in View All Formulary page : "  + logger.addScreenCapture(HeaderNotSelected));
		}
	}


	/******************************************************************************************** 
	 * @throws InterruptedException 
	 * @Function_Name : AppScript_RegisterFreeUser
	 * @Description : Initiate Browser , navigate to the URL and Register the (new)Free User 
	 ********************************************************************************************/
	public static  void AppScript_RegisterFreeUser(ExtentReports report,ExtentTest logger ){
		try{

			driver1 = UtilLib.getDriver();
			WebDriverWait wait = new WebDriverWait(driver1,60);
			String UrlLaunch = util.getPropertiesValue("url");
			String FirstName = util.getPropertiesValue("FirstName");
			String LastName = util.getPropertiesValue("LastName");
			String Password = util.getPropertiesValue("password");

			//Launch the AppSript Application
			driver1.get(UrlLaunch);
			System.out.println(UrlLaunch);

			//byte[] decodedBytes = Base64.decodeBase64(Password);
			//	driver.findElement(By.xpath(element.Password_FreeUser)).sendKeys(new String(decodedBytes));
			Thread.sleep(5000);
			String login = Utilities.screenshot(driver, "Navigate to AppScript SignIn page");
			System.out.println("======="+ login);
			logger.log(LogStatus.PASS, "Navigate to AppScript SignIn page"+UrlLaunch + logger.addScreenCapture(login) );


			//Register Free User
			//	driver.get("https://www.appscript.net");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SignInFreeUser)));

			driver1.findElement(By.xpath(element.FirstName)).sendKeys(FirstName);

			driver1.findElement(By.xpath(element.LastName)).sendKeys(LastName);

			//Entering Email and password for each time execution
			final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH-mm-ss");

			Date date = new Date();
			String UserId=(sdf.format(date))+"Auto@gmail.com";

			driver1.findElement(By.xpath(element.Email)).sendKeys(UserId);

			driver1.findElement(By.xpath(element.Password_FreeUser)).sendKeys(Password);


			driver1.findElement(By.xpath(element.AcceptCheckbox)).click();

			String FieldsEntered = Utilities.screenshot(driver1, "All necessary fields entered Successfully");
			//	logger.log(LogStatus.PASS, "All necessary fields entered Successfully" + logger.addScreenCapture(FieldsEntered) );


			driver1.findElement(By.xpath(element.SubmitButton)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			Thread.sleep(3000);
			String AppScript_Dashborad = Utilities.screenshot(driver1, "Login to AppScript Application");
			logger.log(LogStatus.PASS, "Login to AppScript Application" + logger.addScreenCapture(AppScript_Dashborad) );


		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();

			String FailedToLogin = Utilities.screenshot(driver, "Unable to Navigate to AppScript SignIn page");
			//ApplicationUtility.LogFailMessage(logger, log, "Unable to Navigate to AppScript SignIn page" + logger.addScreenCapture(FailedToLogin));
			report.endTest(logger);
		}

	}


	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : HomePage_APPScript
	 * @Description : Function to Navigate back to selected Apps/Devices/DigitalContent page
	 ********************************************************************************************/
	public static void HomePage_APPScript(String HeaderSelected,ExtentReports report,ExtentTest logger){
		WebDriverWait wait = new WebDriverWait(driver,100);
		String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
		String sheet="Appscript";
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ResourceTypes)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='apps-dashboard']/..//a[contains(text(),'"+HeaderSelected+"')]")));
			WebElement HeaderTab= driver.findElement(By.xpath("//*[@id='apps-dashboard']/..//a[contains(text(),'"+HeaderSelected+"')]"));
			Actions HeaderTabAction = new Actions(driver);
			HeaderTabAction.doubleClick(HeaderTab).perform();

			//		String ResourceTypeSelected = Utilities.screenshot(driver, "Resource Type Selected");
			//ApplicationUtility.LogPassMessage(logger, log , "Resource Type Selected : "+HeaderSelected + logger.addScreenCapture(ResourceTypeSelected));

			//	logger.log(LogStatus.PASS, "Resource Type Selected : "+HeaderSelected + logger.addScreenCapture(ResourceTypeSelected) );


		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();

			String ResourceTypeSelected = Utilities.screenshot(driver, "Unable to Select the Resource Type");
			//ApplicationUtility.LogFailMessage(logger, log , "Unable to Select the Resource Type : " + logger.addScreenCapture(ResourceTypeSelected));

		}


	}


	/******************************************************************************************** 
	 * @return 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Function_Name :SelectProduct
	 * @Description : Add any product to Favorites
	 ********************************************************************************************/
	public static void SelectProduct(String SelectedProduct,String HeaderSelection,ExtentReports report,ExtentTest logger) throws IOException{

		try
		{
			WebDriverWait wait = new WebDriverWait(driver,60);
			String FilePath=System.getProperty("user.dir")+"\\AppData\\TestData.xlsx";
			String sheet="Appscript";
			System.out.println(FilePath);
			int TestdataRowcount = util.getRowCount(FilePath, sheet);
			System.out.println(TestdataRowcount);


			String ProductSelected=driver.findElement(By.xpath(element.SelectedProduct)).getText();
			System.out.println("Product selected from ViewAllFormulary for " +HeaderSelection + " is " +ProductSelected);



			//Add or Remove Selected Product to or from Favorites
			try{

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ProductHeader)));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.DetailButton_Recommend)));
				Thread.sleep(1000);

				//Getting text from Fevorite's link (Add to Favorite or Remove Fevorite)

				WebElement AddFavorites = driver.findElement(By.xpath(element.FavoriteButtonValues));
				Actions AddOrRemoveToFavoriteAction = new Actions(driver);

				String FavoritesValues_Add = driver.findElement(By.xpath(element.FavoriteValue)).getText();
				System.out.println(FavoritesValues_Add);

				if (FavoritesValues_Add.equalsIgnoreCase("Add to Favorites")){
					Utilities.ADDFavorites(ProductSelected, HeaderSelection,report,logger);



					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchField)));
					WebElement search=driver.findElement(By.xpath(element.SearchBox));
					search.sendKeys(ProductSelected);
					String SearchProduct= Utilities.screenshot(driver, "Searching for the product after adding to Favorites");
					logger.log(LogStatus.PASS, "Searching for : " +ProductSelected +logger.addScreenCapture(SearchProduct));
					search.submit();

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchResult)));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ResultHeader)));
					driver.findElement(By.xpath(".//*[@id='search']//div[contains(text(),'"+ProductSelected+"')]")).click();
					Thread.sleep(3000);
					String FavoriteProductDetailPage=Utilities.screenshot(driver, "Navigate to Selected Product Detail Page to Remove from Favorites");
					logger.log(LogStatus.PASS, "Navigate to " +ProductSelected +" Detail Page to Remove from Favorites"  + logger.addScreenCapture(FavoriteProductDetailPage));	
					Utilities.RemoveFavorites(ProductSelected, HeaderSelection,report,logger);




					/*	//Click on Selected Product  --> old code....
				driver.findElement(By.xpath(".//*[@id='dashboard']/div/div[2]//div/img[@title='"+ProductSelected+"']")).click();
				Thread.sleep(7000);
				String FavoriteProductDetailPage=Utilities.screenshot(driver, "Navigate to Selected Product Detail Page to Remove from Favorites");
				logger.log(LogStatus.PASS, "Navigate to " +ProductSelected +" Detail Page to Remove from Favorites"  + logger.addScreenCapture(FavoriteProductDetailPage));				
				//	driver.findElement(By.xpath(".//*[@id='dashboard']/div/div[2]/div//div[contains(text(),'"+ProductSelected+"')]")).click();
				//	driver.findElement(By.xpath("//*[@id='dashboard']/div/div[2]/div//div[2]/div/div/div[contains(text(),'"+ProductSelected+"')]")).click();
				Utilities.RemoveFavorites(ProductSelected, HeaderSelection,report,logger);*/
				}

				else{
					Utilities.RemoveFavorites(ProductSelected, HeaderSelection,report,logger);
					Thread.sleep(5000);
					//Click on Selected Product
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchField)));
					WebElement search=driver.findElement(By.xpath(element.SearchBox));
					search.sendKeys(ProductSelected);
					String SearchProduct= Utilities.screenshot(driver, "Searching for the product after Removing from Favorites");
					logger.log(LogStatus.PASS, "Searching for : " +ProductSelected +logger.addScreenCapture(SearchProduct));
					search.submit();

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchResult)));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ResultHeader)));
					driver.findElement(By.xpath(".//*[@id='search']//div[contains(text(),'"+ProductSelected+"')]")).click();
					Utilities.ADDFavorites(ProductSelected, HeaderSelection,report,logger);

				}


			}
			catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
			}		

		}	

		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
	}


	/******************************************************************************************** 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Function_Name : RemoveFavorites
	 * @Description : Remove any product from Favorites
	 ********************************************************************************************/
	public static void RemoveFavorites(String ProductSelected,String HeaderSelection,ExtentReports report,ExtentTest logger){

		try{
			WebDriverWait wait = new WebDriverWait(driver,60);
			String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
			String sheet="Appscript";
			int TestdataRowcount = util.getRowCount(FilePath, sheet);


			String AddToFevoritesList= util.getCellData(FilePath, sheet, "Add FevoritesValue", 1, "string");
			String RemoveFevoritesList= util.getCellData(FilePath, sheet, "Remove FevoritesValue", 1, "string");

			Actions AddOrRemoveToFavoriteAction = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.DetailButton_Recommend)));
			String FavoritesValues_Remove = driver.findElement(By.xpath(element.FavoriteValueRemove)).getText();
			System.out.println("Favorites value present in " +ProductSelected +" Detail page is " +FavoritesValues_Remove);

			WebElement RemoveToFavorites= driver.findElement(By.xpath("//*[@id='detail-header']//span[contains(text(),'"+RemoveFevoritesList+"')]"));
			AddOrRemoveToFavoriteAction.doubleClick(RemoveToFavorites).perform();
			System.out.println(ProductSelected +" is Removed from Favorites");

			/*	Thread.sleep(2000);
		String RemoveFavorite = Utilities.screenshot(driver, "Selected Product removed from Favorites");
	//	ApplicationUtility.LogPassMessage(logger, log , ProductSelected+" is removed from Favorites" + logger.addScreenCapture(RemoveFavorite));
		logger.log(LogStatus.PASS, ProductSelected+" is removed from Favorites" + logger.addScreenCapture(RemoveFavorite) );
			 */

			Utilities.HomePage_APPScript(HeaderSelection,report,logger);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dashboard']/div/div")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.ProductImage)));
			driver.navigate().refresh();
			Thread.sleep(20000);
			//	Utilities.Favorite_Section(ProductSelected,report,logger);
			String RemovedFromFavorite = Utilities.screenshot(driver, "Product is removed from Favorites Successfully");
			logger.log(LogStatus.PASS, ProductSelected+" is removed from Favorites Successfully" + logger.addScreenCapture(RemovedFromFavorite));

		}

		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String UnableToRemoveFavorite = Utilities.screenshot(driver, "Selected Product is not removed from Favorites");
			logger.log(LogStatus.FAIL,ProductSelected+" is not removed from Favorites" + logger.addScreenCapture(UnableToRemoveFavorite));
		}
	}


	/******************************************************************************************** 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Function_Name : ADDFavorites
	 * @Description : Add any product to Favorites
	 ********************************************************************************************/
	public static void ADDFavorites(String ProductSelected,String HeaderSelection,ExtentReports report,ExtentTest logger){

		try{

			WebDriverWait wait = new WebDriverWait(driver,60);
			String FilePath=System.getProperty("user.dir")+"//AppData//TestData.xlsx";
			String sheet="Appscript";
			int TestdataRowcount = util.getRowCount(FilePath, sheet);

			System.out.println(TestdataRowcount);


			//String AddToFevoritesList= util.getCellData(FilePath, sheet, "Add FavoritesValue", 1, "string");
			//String RemoveFevoritesList= util.getCellData(FilePath, sheet, "Remove FevoritesValue", 1, "string");



			String AddToFevoritesList= util.getCellData(FilePath, sheet, "Add FevoritesValue", 1, "string");
			String RemoveFevoritesList= util.getCellData(FilePath, sheet, "Remove FevoritesValue", 1, "string");

			Actions AddOrRemoveToFavoriteAction = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.DetailButton_Recommend)));
			String FavoritesValues_Add = driver.findElement(By.xpath(element.FavoriteValue)).getText();
			System.out.println("Favorites value present in " +ProductSelected +" Detail page is " +FavoritesValues_Add);


			WebElement AddToFavorites= driver.findElement(By.xpath("//*[@id='detail-header']//span[contains(text(),'"+AddToFevoritesList+"')]"));
			AddOrRemoveToFavoriteAction.doubleClick(AddToFavorites).perform();
			//System.out.println(ProductSelected + " is Added to Favorites");

			//AddOrRemoveToFavoriteAction.click(AddOrRemoveToFavoriteAction).Perform();

			AddOrRemoveToFavoriteAction.click();

			Thread.sleep(4000);


			/*	String AddToFavorite = Utilities.screenshot(driver, "Selected Product is Added to Favorites");
	//	ApplicationUtility.LogPassMessage(logger, log , ProductSelected+" is Added to Favorites" + logger.addScreenCapture(AddToFavorite));
		logger.log(LogStatus.PASS, ProductSelected+" is Added to Favorites" + logger.addScreenCapture(AddToFavorite) );
			 */

			Utilities.HomePage_APPScript(HeaderSelection,report,logger);
			driver.navigate().refresh();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dashboard']/div")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.ProductImage)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.VerifyFavorites)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.FavoriteSection)));

			Thread.sleep(20000);
			Utilities.Favorite_Section(ProductSelected,report,logger);

		}



		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String UnableToAddFavorite = Utilities.screenshot(driver, "Selected Product is not added to Favorites");
			//ApplicationUtility.LogFailMessage(logger, log , ProductSelected+" is not added to Favorites" + logger.addScreenCapture(UnableToAddFavorite));
		}
	}

	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : Favorite_Section
	 * @Description : Function to Verify the FavoriteSection
	 ********************************************************************************************/
	public static void Favorite_Section(String SelectedProduct,ExtentReports report,ExtentTest logger){
		//Verify the Favorites section
		WebDriverWait wait = new WebDriverWait(driver,60);
		try{

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DashboardCategories)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.ProductImage)));
			List<WebElement> FavoriteSection=driver.findElements(By.xpath(element.DashboardCategories));
			System.out.println("link size:" +FavoriteSection.size()); 
			//	boolean FavoriteSectionValue=driver.findElement(By.xpath("//*[@id='dashboard']/div/div/h3[contains(text(),'Favorites')]")).isDisplayed();

			Actions AddToFavoriteAction = new Actions(driver);

			for(WebElement Favorite:FavoriteSection){
				String FavoritesText = Favorite.getText();
				System.out.println(FavoritesText);
				if(FavoritesText.equalsIgnoreCase("Favorites")){
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FavoriteCategory)));
					List<WebElement> FavoriteProduct=driver.findElements(By.xpath(element.FavoritesProduct));
					System.out.println("link size:" +FavoriteProduct.size());
					boolean productcheck=false;
					for(WebElement FavProduct:FavoriteProduct){
						String FavoriteText = FavProduct.getText();
						System.out.println(FavoriteText);
						if(FavoriteText.equalsIgnoreCase(SelectedProduct)){
							System.out.println(SelectedProduct+" has been Added to Favorites Successfully");

							//String ele2="//*[@id='dashboard']/div/div[2]/div/div/div/div/div/div/div/div[1]/div";

							//		String ele2="//*[@id='dashboard']/div/div[2]//div[contains(text(),'"+SelectedProduct+"')]";

							String Partialtext=SelectedProduct.substring(0, 9);

							System.out.println(Partialtext);

							je = (JavascriptExecutor) driver;

							WebElement w2=driver.findElement(By.xpath("//*[@id='dashboard']/div/div[2]//div[contains(text(),'"+SelectedProduct+"')]"));
							je.executeScript("arguments[0].scrollIntoView(true);", w2);
							Thread.sleep(3000);

							String AddedToFavorite = Utilities.screenshot(driver, "Product is added to Favorites Successfully");
							logger.log(LogStatus.PASS, SelectedProduct+" is Added to Favorites" + logger.addScreenCapture(AddedToFavorite));
							break;
						}
						//	else{
						//	System.out.println(FavoriteText+" is not Selected Product");
						//String RemovedFromFavorite = Utilities.screenshot(driver, "Product is removed from Favorites Successfully");
						//logger.log(LogStatus.FAIL, SelectedProduct+" is removed from Favorites Successfully" + logger.addScreenCapture(RemovedFromFavorite));
						//}
					}
					break;
				}
				else{
					System.out.println(FavoritesText+" is not a Favorite Section");
					//logger.error("Favorite Section is not present in Home page");
				}
			}

		}


		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}


	}

	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : ClickProductAndVerify
	 * @Description : Function to Verify the Selected Product in Formulary page for Apps/Device/DigitalContent
	 ********************************************************************************************/

	public static void ClickProductAndVerify(String HeaderSelection,ExtentReports report,ExtentTest logger)
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,100);

			//click the product
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DataTableFirstRowSeleted)));
			WebElement product= driver.findElement(By.xpath(element.Product));

			String prodName=product.getText();
			System.out.println(prodName);
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", product);
			Thread.sleep(3000);
			String ProductSelected=Utilities.screenshot(driver, "Highlighted Product is Selected in View Formulary page");
			//		ApplicationUtility.LogPassMessage(logger, log ,"Product Selected in View Formulary page : " +prodName + logger.addScreenCapture(ProductSelected));
			logger.log(LogStatus.PASS, "Product Selected in View Formulary page : " +prodName + logger.addScreenCapture(ProductSelected));

			product.click();  

			//verify the clicked product name
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SelectedProductDetailHeader)));
			WebElement clickProduct= driver.findElement(By.xpath(element.ClikedProduct));

			String clikedProdName=clickProduct.getText();
			System.out.println(clikedProdName);

			if(prodName.equals(clikedProdName))
			{
				System.out.println("Verifyed for"+"  "+HeaderSelection);
				String ProductDetailPage=Utilities.screenshot(driver, "Navigate to Selected Product Detail Page");
				logger.log(LogStatus.PASS, "Navigate to " +prodName +" Detail Page"  + logger.addScreenCapture(ProductDetailPage));

			}

			//getting sub menus and displaying

			List<WebElement> links=driver.findElements(By.xpath(element.SubMenu));

			System.out.println("Total Menus are "+links.size());

			/*	for(int i=0;i<links.size();i++)
		{
			WebElement ele= links.get(i);
			String Menu=ele.getText();	
			System.out.println(Menu);				
		}*/
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String ProductNotSelected=Utilities.screenshot(driver, "Product is not Selected in View Formulary page");
			//ApplicationUtility.LogFailMessage(logger, log ,"Product is not Selected in View Formulary page : "  + logger.addScreenCapture(ProductNotSelected));

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
	 * @return 
	 * @Function_Name : VerifyProductAndMenu
	 * @Description : Function to Verify the SubMenu for the selected product in all the Resource Types
	 ********************************************************************************************/

	public static void VerifyProductAndMenu(String HeaderSelection,ExtentReports report,ExtentTest logger,String XlHeader,int MenuCount)
	{
		String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
		String sheet="Appscript";

		try
		{
			WebDriverWait wait = new WebDriverWait(driver,100);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FormularyContainer)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]")));
			WebElement MenuHeader=driver.findElement(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]"));
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", MenuHeader);

			Actions SelectAction = new Actions(driver);
			SelectAction.doubleClick(MenuHeader).perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FeaturedProducts)));

			String ClikedMenu=Utilities.screenshot(driver, "Clicking"+ HeaderSelection);
			logger.log(LogStatus.PASS, "Verifying Menu "+HeaderSelection  + logger.addScreenCapture(ClikedMenu));

			String ClickName=driver.findElement(By.xpath(element.FirstProductName)).getText();
			WebElement Product= driver.findElement(By.xpath(element.FirstProduct));
			Actions ProductAction = new Actions(driver);
			ProductAction.doubleClick(Product).perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SelectedProduct)));
			Thread.sleep(1000);
			String ProductCliked=Utilities.screenshot(driver, "Verifying the product  "+ ClickName);
			logger.log(LogStatus.PASS, "Verifying clicking the First Product "+ClickName  + logger.addScreenCapture(ProductCliked));


			String ClickedName=driver.findElement(By.xpath(element.SelectedProduct)).getText();

			//verifying Product name
			if(ClickName.equals(ClickedName))
			{
				System.out.println("Product Name is Verified for "+ ClickedName);

				String ProductDetailsPage=Utilities.screenshot(driver, "Opned product verify "+ ClickedName);
				logger.log(LogStatus.PASS, "Verifying Product name "+ClickedName  + logger.addScreenCapture(ProductDetailsPage));
			}
			else
			{
				System.out.println("Product Name is Not Matching "+ ClickedName);

				String ProductDetailsPage=Utilities.screenshot(driver, "Opned product verify "+ ClickedName);
				logger.log(LogStatus.FAIL, "Product Name is mismatch "  + logger.addScreenCapture(ProductDetailsPage));
			}



			List<WebElement> links=driver.findElements(By.xpath(element.SubMenu));
			//verifying submenu count
			if(MenuCount==links.size())
			{
				System.out.println("Total Submenus for "+HeaderSelection+" is "+links.size());
				// verifying submenu value
				for(int i=0;i<links.size();i++)
				{
					WebElement ele= links.get(i);
					String Menu=ele.getText();	
					String Menu_xl=util.getCellData(FilePath,sheet,XlHeader,i+1,"string");
					String ProductDetailsPage=Utilities.screenshot(driver, " verify submenu for Product name "+ ClickedName);
					if(Menu.equalsIgnoreCase(Menu_xl))
					{
						logger.log(LogStatus.PASS, "Header Available is :" + Menu_xl + logger.addScreenCapture(ProductDetailsPage));

						System.out.println("SubMenu "+Menu+" Verified");
					}
					else
					{
						System.out.println("Displaying incorrect SubMenu "+Menu);
						//String ProductDetailsPage=Utilities.screenshot(driver, " verify submenu for "+ ClickedName);
						logger.log(LogStatus.FAIL, "Displaying incorrect SubMenu "  + logger.addScreenCapture(ProductDetailsPage));	
					}
				}

			}
			else
			{
				System.out.println("Total Submenus for "+HeaderSelection+" is not correct");
				/*String ProductDetailsPage=Utilities.screenshot(driver, " verify submenu for "+ ClickedName);
			logger.log(LogStatus.FAIL, "Displaying incorrect SubMenu "  + logger.addScreenCapture(ProductDetailsPage));	*/
			}

			WebElement HeaderTab= driver.findElement(By.xpath("//*[@id='apps-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]"));
			Actions HeaderTabAction = new Actions(driver);
			Thread.sleep(5000);
			HeaderTabAction.doubleClick(HeaderTab).perform();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
			String ProductNotSelected=Utilities.screenshot(driver, " verifying product name and Submenu");
			//ApplicationUtility.LogFailMessage(logger, log ,"Product Name and Submenu are not mismatching "  + logger.addScreenCapture(ProductNotSelected));
		}

	}

	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : Verify Help Link
	 * @Description : Function to Verify the Navigation to Help screen
	 ********************************************************************************************/ 
	public static void VerifyHelpLink(ExtentReports report,ExtentTest logger){ 

		try{
			WebDriverWait wait = new WebDriverWait(driver,60);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='utility-nav']")));//wait for visibility of navigation bar
			
			//Click on Help Link
			driver.findElement(By.id("help")).click();
			Thread.sleep(6000);
			// Synchronization...
			//Verify the FAQ and General blocks-list in Help screen
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);// switch focus of WebDriver to the next found window handle (that's your newly opened window)
				//driver.close();
			}
			System.out.println("Able to Navigate to Help Screen");
			String HelpPage=Utilities.screenshot(driver, "Navigate to Help screen successfully");
			logger.log(LogStatus.PASS, "Navigate to Help screen successfully" + logger.addScreenCapture(HelpPage)); 

			String FAQBlock=driver.findElement(By.xpath(".//section[1]//li[1]//h4")).getText();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='2px groove green'", driver.findElement(By.xpath(".//section[1]//li[1]//a")));
			System.out.println(FAQBlock + " is present");
			String GeneralBlock=driver.findElement(By.xpath(".//section[1]//li[2]")).getText();
			js.executeScript("arguments[0].style.border='2px groove green'", driver.findElement(By.xpath(".//section[1]//li[2]")));
			Thread.sleep(3000);
			System.out.println(GeneralBlock + "  is present");
			String BlockLists=Utilities.screenshot(driver, "Block lists are present in Home screen");
			logger.log(LogStatus.PASS, "Block lists are present in Home screen" + logger.addScreenCapture(BlockLists)); 


		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String HelpNavigationFailed=UtilLib.screenshot(driver, "Unable to Navigate to Home screen");
			logger.log(LogStatus.FAIL, "Unable to Navigate to Help screen" + logger.addScreenCapture(HelpNavigationFailed)); 

		}
	}

	public static void VerifyReviews(String SelectedProduct,ExtentReports report,ExtentTest logger) {
		// TODO Auto-generated method stub

		try{
			WebDriverWait wait = new WebDriverWait(driver,300);

			//			String UserId = util.getPropertiesValue("User_Id");

			String UserId = Utilities.getpropertiesexcel("User_Id");

			//To Select the Product 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dashboard']/div/div[1]")));	
			WebElement SelectProduct= driver.findElement(By.xpath("//*[@id='dashboard']/div/div[1]/div/div[1]/div/div/div[1]/div/div/img"));


			//Get the name of Selected product

			String ProductSelected= driver.findElement(By.xpath(element.ProductSelected)).getText();
			System.out.println("Selected Product is :"+ProductSelected);
			Actions SelectAction = new Actions(driver);
			SelectAction.doubleClick(SelectProduct).perform();
			String ProductDetailPage=Utilities.screenshot(driver, "Navigate to" +ProductSelected+" Detail page successfully");
			logger.log(LogStatus.PASS, "Navigate to" +ProductSelected+" Detail page successfully" + logger.addScreenCapture(ProductDetailPage));
			//Navigate to Reviews to check whether user has already given reviews or not

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='content']")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='nav']"))); // wait till navigation bar should be visible
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='nav']//a[contains(text(),'reviews')]")));

			//Click on Reviews tab
			driver.findElement(By.xpath(".//*[@id='nav']//a[contains(text(),'reviews')]")).click();
			String ReviewsScreen=Utilities.screenshot(driver, "Navigate to Reviews screen successfully");
			logger.log(LogStatus.PASS, "Navigate to Reviews screen successfully" + logger.addScreenCapture(ReviewsScreen));

			// Verify reviews before submitting it

			WebElement Professionalreviews_Before=driver.findElement(By.xpath(".//*[@id='prescriberReviews']//h3"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();",Professionalreviews_Before);
			String VerifyReviews_Before=Utilities.screenshot(driver, "Verifying Professional Reviews before Submitting the new Reviews");
			logger.log(LogStatus.PASS, "Verifying Professional Reviews before Submitting the new Reviews" + logger.addScreenCapture(VerifyReviews_Before));

			String TotalReviews=driver.findElement(By.xpath(".//*[@id='prescriberReviews']//h3/span[2]")).getText();
			System.out.println("Total Reviews text is: "+TotalReviews);
			String[] TotalReviewsCount=TotalReviews.replace("Reviews"," ").split(" ");
			String Count=TotalReviewsCount[0];
			int ReviewCount = Integer.parseInt(Count);

			for(int j=1;j<=ReviewCount;j++)
			{
				String UserId_BeforeReview=driver.findElement(By.xpath(".//*[@id='prescriberReviewsListing']/div["+j+"]//b")).getText();
				//		System.out.println(ReviewVerified);
				if(UserId_BeforeReview.equalsIgnoreCase(UserId))
				{
					System.out.println(UserId + " has already submitted the ratings");
					String VerifyUserReview_Before=driver.findElement(By.xpath(".//*[@id='prescriberReviewsListing']/div["+j+"]/div/div")).getText();
					String	UserReview =VerifyUserReview_Before.replace(UserId," ");
					System.out.println(UserReview);

					break;
				}
				else
				{
					System.out.println(UserId_BeforeReview+" is not matching with the Searched userId: "+UserId);
				}

			}	

			//	WebElement RatiIt=driver.findElement(By.xpath(".//*[@id='summary']//div[5]/div/button"));
			WebElement RateIt=driver.findElement(By.xpath(".//*[@id='summary']//button/i"));
			js.executeScript("arguments[0].scrollIntoView();",RateIt);
			//	driver.findElement(By.xpath(".//*[@id='summary']//div[5]/div/button")).click();
			RateIt.click();
			Thread.sleep(2000);

			//Provide Ratings
			WebElement RatingReview=driver.findElement(By.xpath(".//*[@id='review-rating']/img[5]"));
			js.executeScript("arguments[0].style.border='2px groove green'", RatingReview);
			RatingReview.click();
			String Rating=Utilities.screenshot(driver, "Rating Provided Successfully");
			logger.log(LogStatus.PASS, "Rating Provided Successfully" + logger.addScreenCapture(Rating));


			//Get the current date and time

			DateFormat dateFormat= new SimpleDateFormat("MM/dd/yyyyHH:mm:ss");
			Date date = new Date();
			String date1=dateFormat.format(date);
			String Review="Review"+date1;

			//Enter Reviews

			driver.findElement(By.xpath(".//*[@id='review-text']")).sendKeys(Review);

			String ReviewEntered=Utilities.screenshot(driver, "New Reviews entered Successfully");
			logger.log(LogStatus.PASS, "New Reviews entered Successfully" + logger.addScreenCapture(ReviewEntered));

			//Clicking on Submit button
			driver.findElement(By.xpath(".//*[@id='reviewForm']//button[contains(text(),'Submit Review')]")).click();
			Thread.sleep(3000);

			//Verify that Review submitted successfully
			String ReviewSubmittedText=driver.findElement(By.xpath(".//*[@id='errorContainer']/p")).getText();
			String SubmitMessage=Utilities.screenshot(driver, "Rating and Reviews submitted Successfully");
			logger.log(LogStatus.PASS, "Rating and Reviews submitted Successfully" + logger.addScreenCapture(SubmitMessage));

			System.out.println("Review submittion confirmation Message is: "+ReviewSubmittedText);

			//refresh the screen
			driver.navigate().refresh();
			Thread.sleep(4000);

			try{

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='nav']/ul")));//wating for navigation bar to be visible
				WebElement Professionalreviews=driver.findElement(By.xpath(".//*[@id='prescriberReviews']//h3"));
				js.executeScript("arguments[0].scrollIntoView();",Professionalreviews);

				//Search for the entered reviews

				for(int j=1;j<=ReviewCount;j++)
				{
					String[] ReviewVerified=driver.findElement(By.xpath(".//*[@id='prescriberReviewsListing']/div["+j+"]/div/div")).getText().split(" ");
					String Reviewcomment=ReviewVerified[0];
					System.out.println(Reviewcomment);
					if(Reviewcomment.equals(Review))
					{
						WebElement PrescriberReview=driver.findElement(By.xpath(".//*[@id='prescriberReviewsListing']/div["+j+"]/div/div"));
						js.executeScript("arguments[0].style.border='2px groove green'", PrescriberReview);
						Thread.sleep(4000);
						String ReviewsValidation=Utilities.screenshot(driver, "Newly entered Rating and Reviews submitted Successfully");
						logger.log(LogStatus.PASS, "Newly entered Rating and Reviews submitted Successfully" + logger.addScreenCapture(ReviewsValidation));
						System.out.println("Newly entered Review is submitted successfully");
						break;
					}
					else
					{
						System.out.println("Newly entered Review is not submitted successfully");

					}
				}

			}catch (Exception e) 
			{
				//Navigate to Review to verify the Professional reviews : After submitting the reviews

				driver.findElement(By.xpath(".//*[@id='nav']//a[contains(text(),'reviews')]")).click();
				Thread.sleep(4000);
				String VerifyReviews_After=Utilities.screenshot(driver, "Verifying Professional Reviews after Submitting the new Reviews");
				logger.log(LogStatus.PASS, "Verifying Professional Reviews after Submitting the new Reviews" + logger.addScreenCapture(VerifyReviews_After));

				WebElement Professionalreviews=driver.findElement(By.xpath(".//*[@id='prescriberReviews']//h3"));
				js.executeScript("arguments[0].scrollIntoView();",Professionalreviews);

				//Search for the entered reviews

				for(int j=1;j<=ReviewCount;j++)
				{
					String[] ReviewVerified=driver.findElement(By.xpath(".//*[@id='prescriberReviewsListing']/div["+j+"]/div/div")).getText().split(" ");
					String Reviewcomment=ReviewVerified[0];
					System.out.println(Reviewcomment);

					List<WebElement> Ratings=driver.findElements(By.xpath(".//*[@id='prescriberReviewsListing']/div[7]//img[@src='https://appscriptcdnstaging.blob.core.windows.net/appscript-stage/css/images/star-on.png']"));
					System.out.println(Ratings.size());

					if(Reviewcomment.equals(Review))
					{
						WebElement PrescriberReview=driver.findElement(By.xpath(".//*[@id='prescriberReviewsListing']/div["+j+"]/div/div"));
						js.executeScript("arguments[0].style.border='2px groove green'", PrescriberReview);
						Thread.sleep(4000);
						String ReviewsValidation=Utilities.screenshot(driver, "Newly entered Rating and Reviews submitted Successfully");
						logger.log(LogStatus.PASS, "Newly entered Rating and Reviews submitted Successfully" + logger.addScreenCapture(ReviewsValidation));
						System.out.println("Newly entered Review is submitted successfully");
						break;
					}
					else
					{
						System.out.println("Newly entered Review is not submitted successfully");

					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
			String SubmitReview_Fail=UtilLib.screenshot(driver, "Unable to submit the New Reviews and Rating");
			logger.log(LogStatus.FAIL, "Unable to submit the New Reviews and Rating" + logger.addScreenCapture(SubmitReview_Fail));
		}

	}




}
