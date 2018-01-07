package com.AppScript.generic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import appscriptAutomationTest.Utilities;



public class ApplicationUtility {
	public static UtilLib util = new UtilLib();
	public static ObjectDefinitionLibrary element = new ObjectDefinitionLibrary();
	private static WebDriver driver;
	static String screenshot;
	static String TotalRebateValue;
	static long roundOff_rebate_followupdisputvalue;
	static long summation_RebatePerClaim_F;
	static long summation_RebatePerClaim_I;
	static long summation_RebatePerClaim_D;
	static Logger log;

	public ApplicationUtility(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public static void LoggerMethod(){
		log=Logger.getLogger("ApplicationUtility");
		PropertyConfigurator.configure("Log4j.properties");
		//log.info("DNA UAT Application");
	}

	private static void LogPassMessage(Object logger, Object log, String LogMessage) {
		((ExtentTest) logger).log(LogStatus.PASS,LogMessage);
		((Category) log).info(LogMessage);

	}

	private static void LogFailMessage(Object logger, Object log, String LogMessage) {
		((ExtentTest) logger).log(LogStatus.FAIL,LogMessage);
		((Category) log).error(LogMessage);

	}

	/******************************************************************************************** 
	 * @throws Exception 
	 * @Function_Name : AppScript_Login
	 * @Description : Initiate Browser and navigate to the URL with valid credentials
	 ********************************************************************************************/
	public static  void AppScript_Login(ExtentReports report,ExtentTest logger ) throws Exception{
		try {
			driver = UtilLib.getDriver();
			//                                        driver.manage().deleteAllCookies();
			WebDriverWait wait = new WebDriverWait(driver,300);
			String UrlLaunch = util.getPropertiesValue("url");
			String UN = util.getPropertiesValue("User_Name");
			String Password_property = util.getPropertiesValue("Password");
			
			// Yathi - Reading from configuration file.... try catch . [Paramterize may be problem]. 
			driver.get(UrlLaunch);
			System.out.println(UrlLaunch);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Loading)));
			driver.findElement(By.xpath(element.UserName)).clear();
			driver.findElement(By.xpath(element.UserName)).sendKeys(util.getPropertiesValue("User_Name"));

			byte[] decodedBytes = Base64.decodeBase64(Password_property);
			driver.findElement(By.xpath(element.Password)).sendKeys(new String(decodedBytes)); // encrypted password is decoded. 

			
		//	System.out.println("======="+ login);

			//	ApplicationUtility.LogPassMessage(logger, log , "Login to UAT AppScript Application: "+UrlLaunch + logger.addScreenCapture(login));

			

			//	driver.findElement(By.xpath("//*[@id='loginForm']/div[4]/span/label/span")).click();
			driver.findElement(By.xpath(element.Login_Button)).click();
			
			//WebDriverWait wait = new WebDriverWait(driver,50);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("Apps")));
			
			
			if (driver.findElement(Utilities.getLocator("Apps")).getAttribute("class").equals("active")){
				String login = UtilLib.screenshot(driver, "Login to  AppScript Application");
				logger.log(LogStatus.PASS, "Login to  AppScript Application: "+UrlLaunch + logger.addScreenCapture(login) );
			}
			
			
			
			
			
			
			// No  failure message added. here...... [in case login failed]. 
			
		} 

		catch (Error e) {
			e.printStackTrace();
			e.getMessage();
			String FailedToLogin = util.screenshot(driver, "Unable to Login to AppScript application");
			ApplicationUtility.LogFailMessage(logger, log, "Unable to Login to AppScript application" + logger.addScreenCapture(FailedToLogin));
			report.endTest(logger);
		}
	}

	/******************************************************************************************** 
	 * @throws InterruptedException 
	 * @Function_Name : AppScript_RegisterFreeUser
	 * @Description : Initiate Browser , navigate to the URL and Register the (new)Free User 
	 ********************************************************************************************/
	public static  void AppScript_RegisterFreeUser(ExtentReports report,ExtentTest logger ){
		try{
			//	driver = UtilLib.getDriver();
			WebDriverWait wait = new WebDriverWait(driver,50);
			String UrlLaunch = util.getPropertiesValue("url");
			String FirstName = util.getPropertiesValue("FirstName");
			String LastName = util.getPropertiesValue("LastName");
			String Password = util.getPropertiesValue("password");

			//Launch the AppSript Application
			driver.get(UrlLaunch);
			System.out.println(UrlLaunch);

			byte[] decodedBytes = Base64.decodeBase64(Password);
			//	driver.findElement(By.xpath(element.Password_FreeUser)).sendKeys(new String(decodedBytes));
			Thread.sleep(5000);
			String login = UtilLib.screenshot(driver, "Navigate to AppScript SignIn page");
			System.out.println("======="+ login);
			logger.log(LogStatus.PASS, "Navigate to AppScript SignIn page"+UrlLaunch + logger.addScreenCapture(login) );


			//Register Free User
			//	driver.get("https://www.appscript.net");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SignInFreeUser)));

			driver.findElement(By.xpath(element.FirstName)).sendKeys(FirstName);

			driver.findElement(By.xpath(element.LastName)).sendKeys(LastName);

			//Entering Email and password for each time execution
			final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH-mm-ss");

			Date date = new Date();
			String UserId=(sdf.format(date)+"gouri@gmail.com");

			driver.findElement(By.xpath(element.Email)).sendKeys(UserId);

			driver.findElement(By.xpath(element.Password_FreeUser)).sendKeys(new String(decodedBytes));


			driver.findElement(By.xpath(element.AcceptCheckbox)).click();

			String FieldsEntered = UtilLib.screenshot(driver, "All necessary fields entered Successfully");
			logger.log(LogStatus.PASS, "All necessary fields entered Successfully" + logger.addScreenCapture(FieldsEntered) );


			driver.findElement(By.xpath(element.SubmitButton)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			Thread.sleep(3000);
			String AppScript_Dashborad = UtilLib.screenshot(driver, "Login to AppScript Application");
			logger.log(LogStatus.PASS, "Login to AppScript Application" + logger.addScreenCapture(AppScript_Dashborad) );


		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();

			String FailedToLogin = util.screenshot(driver, "Unable to Navigate to AppScript SignIn page");
			ApplicationUtility.LogFailMessage(logger, log, "Unable to Navigate to AppScript SignIn page" + logger.addScreenCapture(FailedToLogin));
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

			//		String ResourceTypeSelected = UtilLib.screenshot(driver, "Resource Type Selected");
			//ApplicationUtility.LogPassMessage(logger, log , "Resource Type Selected : "+HeaderSelected + logger.addScreenCapture(ResourceTypeSelected));

			//	logger.log(LogStatus.PASS, "Resource Type Selected : "+HeaderSelected + logger.addScreenCapture(ResourceTypeSelected) );


		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();

			String ResourceTypeSelected = UtilLib.screenshot(driver, "Unable to Select the Resource Type");
			ApplicationUtility.LogFailMessage(logger, log , "Unable to Select the Resource Type : " + logger.addScreenCapture(ResourceTypeSelected));

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
			WebDriverWait wait = new WebDriverWait(driver,300);
			String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
			String sheet="Appscript";
			int TestdataRowcount = util.getRowCount(FilePath, sheet);


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
					ApplicationUtility.ADDFavorites(ProductSelected, HeaderSelection,report,logger);
					//Click on Selected Product
					driver.findElement(By.xpath(".//*[@id='dashboard']/div/div[2]//div/img[@title='"+ProductSelected+"']")).click();
					Thread.sleep(2000);
					String FavoriteProductDetailPage=UtilLib.screenshot(driver, "Navigate to Selected Product Detail Page to Remove from Favorites");
					logger.log(LogStatus.PASS, "Navigate to " +ProductSelected +" Detail Page to Remove from Favorites"  + logger.addScreenCapture(FavoriteProductDetailPage));				
					//	driver.findElement(By.xpath(".//*[@id='dashboard']/div/div[2]/div//div[contains(text(),'"+ProductSelected+"')]")).click();
					//	driver.findElement(By.xpath("//*[@id='dashboard']/div/div[2]/div//div[2]/div/div/div[contains(text(),'"+ProductSelected+"')]")).click();
					ApplicationUtility.RemoveFavorites(ProductSelected, HeaderSelection,report,logger);
				}

				else{
					ApplicationUtility.RemoveFavorites(ProductSelected, HeaderSelection,report,logger);
					Thread.sleep(5000);
					//Click on Selected Product
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchField)));
					WebElement search=driver.findElement(By.xpath(element.SearchBox));
					search.sendKeys(ProductSelected);
					String SearchProduct= UtilLib.screenshot(driver, "Searching for the product after Removing from Favorites");
					logger.log(LogStatus.PASS, "Searching for : " +ProductSelected +logger.addScreenCapture(SearchProduct));
					search.submit();

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchResult)));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ResultHeader)));
					driver.findElement(By.xpath(".//*[@id='search']//div[contains(text(),'"+ProductSelected+"')]")).click();
					ApplicationUtility.ADDFavorites(ProductSelected, HeaderSelection,report,logger);

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
			WebDriverWait wait = new WebDriverWait(driver,300);
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
			String RemoveFavorite = UtilLib.screenshot(driver, "Selected Product removed from Favorites");
		//	ApplicationUtility.LogPassMessage(logger, log , ProductSelected+" is removed from Favorites" + logger.addScreenCapture(RemoveFavorite));
			logger.log(LogStatus.PASS, ProductSelected+" is removed from Favorites" + logger.addScreenCapture(RemoveFavorite) );
			 */

			ApplicationUtility.HomePage_APPScript(HeaderSelection,report,logger);
			driver.navigate().refresh();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dashboard']/div/div")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.ProductImage)));

			Thread.sleep(20000);
			ApplicationUtility.Favorite_Section(ProductSelected,report,logger);
			String RemovedFromFavorite = UtilLib.screenshot(driver, "Product is removed from Favorites Successfully");
			logger.log(LogStatus.PASS, ProductSelected+" is removed from Favorites Successfully" + logger.addScreenCapture(RemovedFromFavorite));

		}

		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String UnableToRemoveFavorite = UtilLib.screenshot(driver, "Selected Product is not removed from Favorites");
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

			WebDriverWait wait = new WebDriverWait(driver,300);
			String FilePath=System.getProperty("user.dir")+"/AppData/TestData.xlsx";
			String sheet="Appscript";
			int TestdataRowcount = util.getRowCount(FilePath, sheet);


			String AddToFevoritesList= util.getCellData(FilePath, sheet, "Add FevoritesValue", 1, "string");
			String RemoveFevoritesList= util.getCellData(FilePath, sheet, "Remove FevoritesValue", 1, "string");

			Actions AddOrRemoveToFavoriteAction = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.DetailButton_Recommend)));
			String FavoritesValues_Add = driver.findElement(By.xpath(element.FavoriteValue)).getText();
			System.out.println("Favorites value present in " +ProductSelected +" Detail page is " +FavoritesValues_Add);


			WebElement AddToFavorites= driver.findElement(By.xpath("//*[@id='detail-header']//span[contains(text(),'"+AddToFevoritesList+"')]"));
			AddOrRemoveToFavoriteAction.doubleClick(AddToFavorites).perform();
			System.out.println(ProductSelected + " is Added to Favorites");


			/*	String AddToFavorite = UtilLib.screenshot(driver, "Selected Product is Added to Favorites");
		//	ApplicationUtility.LogPassMessage(logger, log , ProductSelected+" is Added to Favorites" + logger.addScreenCapture(AddToFavorite));
			logger.log(LogStatus.PASS, ProductSelected+" is Added to Favorites" + logger.addScreenCapture(AddToFavorite) );
			 */

			ApplicationUtility.HomePage_APPScript(HeaderSelection,report,logger);
			driver.navigate().refresh();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dashboard']/div")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.ProductImage)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.VerifyFavorites)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.FavoriteSection)));

			Thread.sleep(20000);
			ApplicationUtility.Favorite_Section(ProductSelected,report,logger);

		}



		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String UnableToAddFavorite = UtilLib.screenshot(driver, "Selected Product is not added to Favorites");
			ApplicationUtility.LogFailMessage(logger, log , ProductSelected+" is not added to Favorites" + logger.addScreenCapture(UnableToAddFavorite));
		}
	}

	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : Favorite_Section
	 * @Description : Function to Verify the FevoriteSection
	 ********************************************************************************************/
	public static void Favorite_Section(String SelectedProduct,ExtentReports report,ExtentTest logger){
		//Verify the Favorites section
		WebDriverWait wait = new WebDriverWait(driver,100);
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

					for(WebElement FavProduct:FavoriteProduct){
						String FavoriteText = FavProduct.getText();
						System.out.println(FavoriteText);

						if(FavoriteText.equalsIgnoreCase(SelectedProduct)){
							System.out.println(SelectedProduct+" has been Added to Favorites Successfully");
							String AddedToFavorite = UtilLib.screenshot(driver, "Product is added to Favorites Successfully");
							logger.log(LogStatus.PASS, SelectedProduct+" is Added to Favorites" + logger.addScreenCapture(AddedToFavorite));
							break;
						}
						else{
							System.out.println(FavoriteText+" is not Selected Product");
							String RemovedFromFavorite = UtilLib.screenshot(driver, "Product is removed from Favorites Successfully");
							logger .log(LogStatus.FAIL, SelectedProduct+" is removed from Favorites Successfully" + logger.addScreenCapture(RemovedFromFavorite));
						}
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
	 * @Function_Name : ClickMenu
	 * @Description : Function to Verify the Formulary page for Apps/Device/DigitalContent
	 ********************************************************************************************/

	public static void ClickMenu(String HeaderSelection,ExtentReports report,ExtentTest logger) throws InterruptedException, IOException
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,800);
			//WebDriverWait wait = new WebDriverWait()

			//click view all formulary //.//*[@id='utility-nav']
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Dashbord)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.NavigationBar_Formulary)));
			driver.findElement(By.xpath(element.ViewAllFormularyLink)).click();

			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ViewAllFormularyContent)));

			String ViewFormularyPage=UtilLib.screenshot(driver, "Navigate to View All Formulary page");
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

			String HeaderSelected=UtilLib.screenshot(driver, "Resource Type Selected in View All Formulary page");
			//	ApplicationUtility.LogPassMessage(logger, log ,"Resource Type Selected in View All Formulary page : " +HeaderSelection + logger.addScreenCapture(HeaderSelected));
			logger.log(LogStatus.PASS, "Resource Type Selected in View All Formulary page : " +HeaderSelection + logger.addScreenCapture(HeaderSelected));
			/*WebElement menu= driver.findElement(By.xpath("//a[contains(text(),'"+HeaderSelection+"')]"));
			menu.click();  */ 
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();	
			String HeaderNotSelected=UtilLib.screenshot(driver, "Resource Type is not Selected in View All Formulary page");
			ApplicationUtility.LogFailMessage(logger, log ,"Resource Type is not Selected in View All Formulary page : "  + logger.addScreenCapture(HeaderNotSelected));
		}
	}
	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : ClickHeader
	 * @Description : Function to click Apps/Device/DigitalContent
	 ********************************************************************************************/
	public static void ClickHeader(String HeaderSelection,ExtentReports report,ExtentTest logger) throws InterruptedException, IOException
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			//clicking Appscript logo
			WebElement AppScriptLogo= driver.findElement(By.xpath(element.AppScriptLogo));
			AppScriptLogo.click();
			Thread.sleep(5000);

			//clicking header
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FormularyContainer)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]")));
			WebElement DeviceHeader=driver.findElement(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]"));
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", DeviceHeader);
			Actions SelectAction = new Actions(driver);
			SelectAction.doubleClick(DeviceHeader).perform();

		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();	
			String HeaderNotSelected=UtilLib.screenshot(driver, "Resource Type is not clicked");
			ApplicationUtility.LogFailMessage(logger, log ,"Resource Type is not clicked : "  + logger.addScreenCapture(HeaderNotSelected));
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
			WebDriverWait wait = new WebDriverWait(driver,60);

			//click the product
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DataTableFirstRowSeleted)));
			WebElement product= driver.findElement(By.xpath(element.Product));

			String prodName=product.getText();
			System.out.println(prodName);
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", product);
			Thread.sleep(3000);
			String ProductSelected=UtilLib.screenshot(driver, "Highlighted Product is Selected in View Formulary page");
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
				String ProductDetailPage=UtilLib.screenshot(driver, "Navigate to Selected Product Detail Page");
				logger.log(LogStatus.PASS, "Navigate to " +prodName +" Detail Page"  + logger.addScreenCapture(ProductDetailPage));

			}

			//getting sub menus and displaying

			List<WebElement> links=driver.findElements(By.xpath(element.SubMenu));

			System.out.println("Total Menus are "+links.size());

			for(int i=0;i<links.size();i++)
			{
				WebElement ele= links.get(i);
				String Menu=ele.getText();	
				System.out.println(Menu);				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			String ProductNotSelected=UtilLib.screenshot(driver, "Product is not Selected in View Formulary page");
			ApplicationUtility.LogFailMessage(logger, log ,"Product is not Selected in View Formulary page : "  + logger.addScreenCapture(ProductNotSelected));

		}
	}


	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : ClickProductAndAddToBag
	 * @Description : Function to Verify the Selected Product to add in bag for Apps/Device/DigitalContent
	 ********************************************************************************************/
	public static void AddProductsToBag(String HeaderSelection,ExtentReports report,ExtentTest logger)
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,120); // 2 minutes to wait. 
			//click Menu Apps or devices or digital content
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FormularyContainer)));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]")));
			
			// click the Header [Apps, Devices, Content]. 
			WebElement DeviceHeader=driver.findElement(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]"));
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", DeviceHeader);
			Actions SelectAction = new Actions(driver);
			SelectAction.doubleClick(DeviceHeader).perform();
		
			/*try{
				SelectAction.doubleClick(DeviceHeader).perform();
				// once we perform doubleclick need to check Whether Resource Type is active or not. 
			}
		catch(Exception e){
			
		}*/
			
			

			//click the first product
			Thread.sleep(3000); // wait for 3 seconds. 
			

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FeaturedProducts)));
			WebElement FirstProduct= driver.findElement(By.xpath(element.FirstProduct));
			WebElement AddingProductName= driver.findElement(By.xpath(element.FirstProductName));
			
			String ProductName= AddingProductName.getText(); // Get the Product Name..
		
		
			js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", FirstProduct);
			String ClikcingFirstProduct=UtilLib.screenshot(driver, "Selecting the product in "+HeaderSelection);
			logger.log(LogStatus.PASS, "Resource Type -" + HeaderSelection + "- Resource Name -" + ProductName+ logger.addScreenCapture(ClikcingFirstProduct));

			FirstProduct.click(); // click on first product. 
		
			
			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ProductDetailsHeader)));

//Verify bag number and products in bag before adding into Bag....	
			int bagNumberbeforeadding=ProductCountOnBagIcon(js,logger);
			String NumberofProductinBagBeforeAdd=UtilLib.screenshot(driver, "Count in Bag - "  + bagNumberbeforeadding);
		//	logger.log(LogStatus.INFO, "Number of Products in Bag : " + bagNumberbeforeadding + logger.addScreenCapture(NumberofProductinBagBeforeAdd));
//add product to bag and verify the product name under particular resource
			//Thread.sleep(30000);				
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.AddtoBagbutton)));	
			WebElement AddProductToBagButton= driver.findElement(By.xpath(element.AddtoBagbutton));
			js.executeScript("arguments[0].style.border='2px groove green'", AddProductToBagButton);
	
	     String ClikcingTheAddToBagButton=UtilLib.screenshot(driver, "clicking the Add To Bag button");
	     AddProductToBagButton.click();
		  logger.log(LogStatus.PASS, "clicking the Add to Bag Button"  +logger.addScreenCapture(ClikcingTheAddToBagButton));
			
// Need to verify Remove From Bag Button  - After Click on Add to Bag..  [Yathi]. 
		Thread.sleep(2000);

			//click BagSymbole
			WebElement Bag= driver.findElement(By.xpath(element.BagSymbole));
			Bag.click();
			Thread.sleep(2000);
			js.executeScript("arguments[0].style.border='2px groove green'", Bag);
			String Bagicon=UtilLib.screenshot(driver, "click the Bag icon to verify the product is added sucessfully into bag and also to verify the Bag count");
			logger.log(LogStatus.PASS, "Click Bag icon Successful and able to view Added Resource"  + logger.addScreenCapture(Bagicon));

			List<WebElement> links1=driver.findElements(By.xpath(element.ProductsListInBag)); // Click on Bag Icon. 
			Thread.sleep(2000);
	//verify the product present under the particular resource. [input is Product Name, header Section under Bag, Product Name]. 
				switch (HeaderSelection) {
		        case "Apps":
		        	String ProductsUnderApps=element.AppsProductsListInBag;  
		        	VerifytheProductNameInBag(ProductName,ProductsUnderApps,HeaderSelection, logger);
	             	break;
		        case "Devices":
		        	String ProductsUnderDevices=element.DevicesProductsListInBag;
		        	 VerifytheProductNameInBag(ProductName,ProductsUnderDevices,HeaderSelection, logger);
		        	break;
		        case "Digital Content":

		        	String ProductsUnderDC=element.DCProductsListInBag;
		        	 VerifytheProductNameInBag(ProductName,ProductsUnderDC,HeaderSelection, logger);
		        	break;
		        default: 
		               System.out.println("Switch default No Products added into the Bag");
		               break;

				}

	//verify button name, after product added into the bag. [Verification of Text] changed from "Add to Bag"  to Remove Bag. 
			String buttonname=AddProductToBagButton.getText();
				if(buttonname.equals("Remove Bag"))
				{
					System.out.println("Remove Bag button name verified sucessfully");
					String ButtonName=UtilLib.screenshot(driver, "Remove Bag button Name is Verifyed, after adding the product into Bag");
					logger.log(LogStatus.PASS, "Remove Bag button Name is Verifyed, after adding the product into Bag"  + logger.addScreenCapture(ButtonName));
				}
				else
				{
					System.out.println("button name not chnaged");
					String ButtonName=UtilLib.screenshot(driver, "Remove Bag button Name button Name is not changed after adding the product into the Bag");
					ApplicationUtility.LogFailMessage(logger, log ,"button Name is not changed after adding the product into the Bag "  + logger.addScreenCapture(ButtonName));

				}

			//verifying product count which will display on Bag
		int BagNumberAfterAdding =ProductCountOnBagIcon(js,logger);
			System.out.println("Product number in Bag is "+BagNumberAfterAdding);

			if(BagNumberAfterAdding==bagNumberbeforeadding+1 && BagNumberAfterAdding==links1.size())
			{
			  String ButtonNameCount=UtilLib.screenshot(driver, "Remove Bag button Name is Verifyed, after adding the product into Bag");
			  logger.log(LogStatus.PASS, "Count-Verification in Bag Successful " + "Before Add to Bag : " + bagNumberbeforeadding +":" + "After Add to Bag: " + BagNumberAfterAdding + logger.addScreenCapture(ButtonNameCount));
				System.out.println("verifying Product count displaying on Bag "+BagNumberAfterAdding);
			}
			else
			{
				System.out.println("verifying Product count displaying in Bag -----count is mismatching");
				String ProductNameInBag=UtilLib.screenshot(driver, "verifying Product count displaying in Bag-----count is mismatching");
				ApplicationUtility.LogFailMessage(logger, log ,"verifying Product count displaying in Bag-----count is mismatching "  + logger.addScreenCapture(ProductNameInBag));

			}

			WebElement AppScriptLogo= driver.findElement(By.xpath(element.AppScriptLogo));
			AppScriptLogo.click();
			Thread.sleep(2000);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
			String ProductNotSelected=UtilLib.screenshot(driver, "Product not added sucessfully into the Bag");
			ApplicationUtility.LogFailMessage(logger, log ,"Product not added sucessfully into the Bag "  + logger.addScreenCapture(ProductNotSelected));

		}
	}

	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : Remove Product from Bag and verify
	 * @Description : Function to Verify the product deleted in bag under particular resource 
	 ********************************************************************************************/
	public static void RemoveProductsFromBag(String HeaderSelection,ExtentReports report,ExtentTest logger)
	{
		try{
			//WebDriverWait wait = new WebDriverWait(driver,120);
			//Actions SelectAction = new Actions(driver);
			JavascriptExecutor js=(JavascriptExecutor)driver;
			int BagCountBeforDeleting =ProductCountOnBagIcon( js,logger);

			if(BagCountBeforDeleting>=1)
			{
				WebElement Bag= driver.findElement(By.xpath(element.BagSymbole));
				js.executeScript("arguments[0].style.border='2px groove green'", Bag);
				String Bagicon=UtilLib.screenshot(driver, "click the Bag icon to delete the product");
				logger.log(LogStatus.PASS, "click the Bag icon to delete the product"  + logger.addScreenCapture(Bagicon));
				Bag.click();

				Thread.sleep(1000);
				List<WebElement> HeadersList=driver.findElements(By.xpath(element.HeadersInBag));
				for(int k=0;k<HeadersList.size();k++)
				{
					WebElement Headers=HeadersList.get(k) ;
					String HeaderName=Headers.getText();

					if(HeaderName.equals("Apps"))
					{
					String BagAfterClickCount=UtilLib.screenshot(driver, "Count of Bag Before Deletion");
					logger.log(LogStatus.PASS, "Count in Bag Before Deletion :" + BagCountBeforDeleting  + logger.addScreenCapture(BagAfterClickCount));
						//delete products under apps
						String DeletedProductNameInApps =
								DeleteProductInBag(js,logger,Bag,element.AppsProductsListInBag);
						Bag.click();    
						//Verify for the productName deleted from the Bag
						VerifyProductNameRemovedFromBag( DeletedProductNameInApps,element.AppsProductsListInBag,logger,js);
						ClickHeader( HeaderSelection, report,logger);
						SearchProductAndVerifyButtonName ( js,logger,DeletedProductNameInApps, Bag) ;
						break;
					}
					else if(HeaderName.equals("Devices"))
					{
						String BagAfterClickCount=UtilLib.screenshot(driver, "Count of Bag Before Deletion");
						logger.log(LogStatus.PASS, "Count in Bag Before Deletion :" + BagCountBeforDeleting  + logger.addScreenCapture(BagAfterClickCount));
						
						//delete Products under Devices
						String DeletedProductNameInDevices =
								DeleteProductInBag(js,logger,Bag,element.DevicesProductsListInBag);
						Bag.click();  	        
						//Verify for the productName deleted from the Bag
						VerifyProductNameRemovedFromBag( DeletedProductNameInDevices,element.DevicesProductsListInBag,logger,js);
						ClickHeader( HeaderSelection, report,logger);
						SearchProductAndVerifyButtonName( js,logger,DeletedProductNameInDevices, Bag) ;
						break;
					}
					else if(HeaderName.equals("Content"))
					{
						String BagAfterClickCount=UtilLib.screenshot(driver, "Count of Bag Before Deletion");
						logger.log(LogStatus.PASS, "Count in Bag Before Deletion :" + BagCountBeforDeleting  + logger.addScreenCapture(BagAfterClickCount));
						//delete Products under Content
						String DeletedProductNameInDC =
								DeleteProductInBag(js,logger,Bag,element.DCProductsListInBag);
						Bag.click();          
						//Verify for the productName deleted from the Bag
						VerifyProductNameRemovedFromBag( DeletedProductNameInDC,element.DCProductsListInBag,logger,js);
						ClickHeader( HeaderSelection, report,logger);
						SearchProductAndVerifyButtonName ( js,logger,DeletedProductNameInDC, Bag) ;
						break;
					}
				}

			}
			else
			{
				String ResourceType=UtilLib.screenshot(driver, "No Products to delete in the Bag under "+HeaderSelection);
				logger.log(LogStatus.PASS, "No Products to delete in the Bag under "+HeaderSelection  + logger.addScreenCapture(ResourceType));

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			e.getMessage();
			String ProductNotSelected=UtilLib.screenshot(driver, "Product not Removed from the Bag");
			ApplicationUtility.LogFailMessage(logger, log ,"Product not Removed from the Bag "  + logger.addScreenCapture(ProductNotSelected));
			report.endTest(logger);
		}
	}


	/******************************************************************************************** 
	 * @return 
	 * @throws InterruptedException 
	 * @Function_Name : Find the Bag Count
	 * @Description : Function to find the number of products in Bag
	 ********************************************************************************************/	
	public static String DeleteProductInBag(JavascriptExecutor js,ExtentTest logger,WebElement Bag,String Products) throws InterruptedException
	{
		//find the products count in Bag
		int BagCountBeforeDeleting =ProductCountOnBagIcon( js,logger);

		//find the first product under particular resource type
		List<WebElement> ProductList=driver.findElements(By.xpath(Products));

		//delete the first product under Apps or devices or digital content
		String DeletedProductName=null;
		for(int i=0;i<=ProductList.size();i++)
		{
			WebElement Product=ProductList.get(i) ;		
			js.executeScript("arguments[0].style.border='2px groove green'", Product);
			DeletedProductName=Product.getText();
			WebElement DeleteFirstProductInBag= driver.findElement(By.xpath(".//*[@id='bag-list']/li/div/i["+i+1+"]"));
			js.executeScript("arguments[0].style.border='2px groove green'", DeleteFirstProductInBag);
			DeleteFirstProductInBag.click();
			Thread.sleep(1000);
			break;
		}

		//find the number of products in Bag
		int BagCountAfterDeleting =ProductCountOnBagIcon( js,logger);

		//verify Bag count
		if(BagCountAfterDeleting==BagCountBeforeDeleting-1)
		{
			System.out.println("Product "+DeletedProductName+" deleted sucessfully from the Bag");
			Bag.click();
			String deletedProduct=UtilLib.screenshot(driver, "Verifying Bag Count After deleting a product from the Bag");
			logger.log(LogStatus.PASS, "Resource Removed from Bag Successfully"  + logger.addScreenCapture(deletedProduct));
			logger.log(LogStatus.PASS, "Remove From Bag Count : " + "Before Remove " + BagCountBeforeDeleting + " Bag Count After Deletion " + BagCountAfterDeleting  + logger.addScreenCapture(deletedProduct));

		}
		else
		{
			String ProductNotSelected=UtilLib.screenshot(driver, "Verifying Bag count after deletig the Product from the Bag");
			ApplicationUtility.LogFailMessage(logger, log ,"Verifying Bag count after deletig the Product from the Bag UnSuccessful"  + logger.addScreenCapture(ProductNotSelected));

		}



		return DeletedProductName;

	}


	/******************************************************************************************** 
	 * @return 
	 * @throws InterruptedException 
	 * @Function_Name : SearchProductAndVerifyButtonName
	 * @Description : Search the product and verify the button name
	 ********************************************************************************************/	
	public static void SearchProductAndVerifyButtonName (JavascriptExecutor js,ExtentTest logger,String DeletedProductName,WebElement Bag) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver,120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchField)));
		WebElement search=driver.findElement(By.xpath(element.SearchBox));
		search.sendKeys(DeletedProductName);
		String SearchProduct= UtilLib.screenshot(driver, "Searching for the product "+DeletedProductName+" after Removing from Bag");
		logger.log(LogStatus.PASS, "Searching for : " +DeletedProductName +logger.addScreenCapture(SearchProduct));
		search.submit();
		Thread.sleep(2000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchResult)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ResultHeader)));

		WebElement ProductSearched= driver.findElement(By.xpath(element.SearchedProduct));
		js.executeScript("arguments[0].style.border='2px groove green'", ProductSearched);
		String Searchresult= UtilLib.screenshot(driver, "product "+DeletedProductName+" searched sucessfully");
		logger.log(LogStatus.PASS, "product "+DeletedProductName+" searched sucessfully"  +logger.addScreenCapture(Searchresult));
		ProductSearched.click();
		Thread.sleep(8000);
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ProductDetailsHeader)));
		WebElement AddProductToBagButton= driver.findElement(By.xpath(element.AddtoBagbutton));
		String buttonname=AddProductToBagButton.getText();
		if(buttonname.equals("Add to Bag"))
		{
			System.out.println("button name verified sucessfully");
			js.executeScript("arguments[0].style.border='2px groove green'", AddProductToBagButton);
			String ButtonName=UtilLib.screenshot(driver, "button Name is Verifyed, after deleting the product from Bag");
			logger.log(LogStatus.PASS, "button Name is Verifyed, From Remove Bag to - 'Add to Bag'"   + logger.addScreenCapture(ButtonName));

			
		}
		else
		{
			System.out.println("button name not chnaged");
			js.executeScript("arguments[0].style.border='2px groove green'", AddProductToBagButton);
			String ButtonName=UtilLib.screenshot(driver, "button Name is not changed after deleting the product from Bag");
			ApplicationUtility.LogFailMessage(logger, log ,"Expected Button Name:Add to Bag" + "Actual :" + buttonname   + logger.addScreenCapture(ButtonName));

		}
		WebElement AppScriptLogo= driver.findElement(By.xpath(element.AppScriptLogo));
		js.executeScript("arguments[0].style.border='2px groove green'", AppScriptLogo);
		AppScriptLogo.click();
		Thread.sleep(3000);

	}
	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : Find the Bag Count
	 * @Description : Function to find the number of products in Bag
	 ********************************************************************************************/	
	public static int ProductCountOnBagIcon(JavascriptExecutor js,ExtentTest logger)
	{

		WebElement ProductsNumberOnBagIcon= driver.findElement(By.xpath(element.ProductNumberInBag));
		js.executeScript("arguments[0].style.border='2px groove green'", ProductsNumberOnBagIcon);
		String BagNumber=ProductsNumberOnBagIcon.getText();
		int BagCount;


		if(BagNumber != null && !BagNumber.isEmpty())
		{
			BagCount= Integer.parseInt(BagNumber);
			System.out.println("Product count in Bag "+BagCount);
			/*String BagnumberBeforeAdding=UtilLib.screenshot(driver, "Product count in Bag is:  "+BagCount);
			logger.log(LogStatus.PASS, "Product count in Bag is:  "+BagCount + logger.addScreenCapture(BagnumberBeforeAdding));*/

		}
		else
		{
			BagCount=0;
			System.out.println("No Products in the Bag");
			String BagnumberBeforeAdding=UtilLib.screenshot(driver, "No Products in the Bag");
			logger.log(LogStatus.PASS, "Number of Products in the Bag " + BagCount + logger.addScreenCapture(BagnumberBeforeAdding));

		}
		return BagCount;
	}
	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : VerifyProductNameRemovedFromBag
	 * @Description : verify for the product name deleted from from the Bag after performing delete operation
	 ********************************************************************************************/	
	public static void VerifyProductNameRemovedFromBag(String DeletedProductName,String ProductList,ExtentTest logger,JavascriptExecutor js)
	{
		int BagCount =ProductCountOnBagIcon( js,logger);

		if(BagCount>=1) // condition is only when Bag count is more than 1 - Iterate to Loop. 
		{
			List<WebElement> ProductsList=driver.findElements(By.xpath(ProductList));
			boolean flag=false;
			for(int j=0;j<ProductsList.size();j++)
			{

				WebElement ProductNameinBag= ProductsList.get(j) ;
				String Product=ProductNameinBag.getText();	
				if(Product.equals(DeletedProductName))	
				{
					flag=true;
					System.out.println("verifying product name in the Bag after deleting the product---Product not deleted from the Bag");
					String ProductNameInBag=UtilLib.screenshot(driver, "verifying product name in the Bag after deleting the product---Prduct not deleted from the Bag");
					ApplicationUtility.LogFailMessage(logger, log ,"verifying product name in the Bag after deleting the product---Prduct not deleted from the Bag "  + logger.addScreenCapture(ProductNameInBag));

				}	

			}
			if(!flag){
				System.out.println("Product "+DeletedProductName+"verifying product name in the Bag after deleting the product--- deleted sucessfully from the Bag");
				String ProductDeletedFromBag=UtilLib.screenshot(driver, "verifying product name in the Bag after deleting the product---product deleted sucessfully from the Bag");
				logger.log(LogStatus.PASS, "verifying product name in the Bag after deleting the product---product deleted sucessfully from the Bag"  + logger.addScreenCapture(ProductDeletedFromBag));

			}

		}
		else
		{
			System.out.println("Product "+DeletedProductName+"verifying product name in the Bag after deleting the product--- deleted sucessfully from the Bag");
			String ProductDeletedFromBag=UtilLib.screenshot(driver, "verifying product name in the Bag after deleting the product---product deleted sucessfully from the Bag");
			logger.log(LogStatus.PASS, "verifying product name in the Bag after deleting the product---product deleted sucessfully from the Bag"  + logger.addScreenCapture(ProductDeletedFromBag));

		}

	}

	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : VerifytheProductNameInBag
	 * @Description : Verify for the product Name in Bag After adding
	 ********************************************************************************************/	
	public static void VerifytheProductNameInBag(String ProductName,String ProductsListInBag,String Header,ExtentTest logger)
	{
		//verify added product in bag
		List<WebElement> links1=driver.findElements(By.xpath(ProductsListInBag));
		System.out.println("Total Products in bag under "+ Header +" is  "+links1.size());
		boolean flag=false;
// Loop in Bag and Verify the right Product added under Right Resource Type. 
		for(int i=0;i<links1.size();i++)
		{
			WebElement ProductinBag= links1.get(i);
			String ProductAddedToBag=ProductinBag.getText();
			System.out.println(ProductAddedToBag);
			//System.out.println("Product "+i+" in bag is"+ProductAddedToBag);
			if(ProductAddedToBag.equals(ProductName))	
			{
				System.out.println("Product "+ProductName+" is added sucessfully into the Bag under "+Header);
				String ProductNameInBag=UtilLib.screenshot(driver, "product added sucessfully in the Bag under "+Header);
				logger.log(LogStatus.PASS, "Selected Resource from Resource Type is-- "+Header+ "  added Successfully in Bag -" + Header + "-Resource Name-" +  ProductName  + logger.addScreenCapture(ProductNameInBag));
				flag=true;
				break;
			}
		}
		if(!flag)
		{
			System.out.println("Product not added sucessfully into the Bag under "+ Header );
			String ProductNameInBag=UtilLib.screenshot(driver, "verified product in the Bag---Product not added into the Bag under "+Header );
			ApplicationUtility.LogFailMessage(logger, log ,"verified product name in the Bag---Product not added into the Bag under "+Header+ logger.addScreenCapture(ProductNameInBag));
		}      

	}


	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : BagLimit_ValidationMessage
	 * @Description : Verify Bag Limit-Validation Message
	 ********************************************************************************************/	
	public static void BagLimit_ValidationMessage(String SelectedCategory,String HeaderSelection,ExtentReports report,ExtentTest logger)
	{	
		SoftAssert Bagsoftverification=new SoftAssert();
		WebDriverWait wait = new WebDriverWait(driver,100);
		try{
			// 	WebDriverWait wait= new WebDriverWait(driver, 100);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.NavigationHeader)));

			Thread.sleep(2000);
			String ResourceTypeSelected=UtilLib.screenshot(driver, "Resource Type Selected is "+HeaderSelection );
			logger.log(LogStatus.PASS, "Resource Type Selected is " +HeaderSelection+ logger.addScreenCapture(ResourceTypeSelected));

			//Click on Resouce Type
			driver.findElement(By.xpath("//*[@id='apps-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]")).click();


			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.Content)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SideBar)));

			//Click on Category	
			WebElement Category=driver.findElement(By.xpath(".//*[@id='sidebar']//a[contains(text(),'"+SelectedCategory+"')]"));
			JavascriptExecutor js=(JavascriptExecutor)driver; 
			js.executeScript("arguments[0].style.border='2px groove green'", Category);
			driver.findElement(By.xpath(".//*[@id='sidebar']//a[contains(text(),'"+SelectedCategory+"')]")).click();
			Thread.sleep(3000);
			String CategorySelected=UtilLib.screenshot(driver, SelectedCategory+" is the selected category from "+HeaderSelection+" resource type" );
			logger.log(LogStatus.PASS, SelectedCategory+" is the selected category from "+HeaderSelection+"resource type"+ logger.addScreenCapture(CategorySelected));


			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchContent)));

			  Thread.sleep(3000);
			
			//Get the all products present in Lifestyle & Stress Category
			List<WebElement> ProductList=driver.findElements(By.xpath(".//*[@id='search']/div/div/div[2]/div//div[@class='app-name']"));
			System.out.println("Total Products in Lifestyle & Stress: " + ProductList.size());
			
			
			WebElement ProductsNumberOnBagIcon= driver.findElement(By.xpath(element.ProductNumberInBag));
			js.executeScript("arguments[0].style.border='2px groove green'", ProductsNumberOnBagIcon);
			String BagNumber=ProductsNumberOnBagIcon.getText();
			int BagCount;

			for(WebElement AppsList:ProductList)
			{
				String ProductName=AppsList.getText();
				
				
			//	ApplicationUtility.SearchProductAndAddtoBAg(js, logger, ProductName, ProductsNumberOnBagIcon);
				
				
				
				
				
				
				System.out.println(ProductName);
			}

			//Get all Product Rows in screen
			List<WebElement> ProductRows=driver.findElements(By.xpath(element.ProductRows));
			System.out.println("Total Rows " +ProductRows.size());

			for(int RowSize=1;RowSize<=ProductRows.size();RowSize++)
			{
				for(int Product=1;Product<=4;Product++) //product in each row
				{

					Thread.sleep(4000);

					//Click on Product
					String ProductName=driver.findElement(By.xpath(".//*[@id='search']/div/div/div[2]/div["+RowSize+"]/div["+Product+"]/div/div/div[1]/div[2]/div/div[1]/div")).getText();						
					ProductName=ProductName.replaceAll(":", "");
					
				String currentURL=driver.getCurrentUrl();

					driver.findElement(By.xpath(".//*[@id='search']/div/div/div[2]/div["+RowSize+"]/div["+Product+"]//img")).click();
					Thread.sleep(4000);
					String ProductSelected=UtilLib.screenshot(driver, ProductName+" is Selected to add it in Bag " );
					logger.log(LogStatus.PASS, ProductName+" is Selected to add it in Bag "+ logger.addScreenCapture(ProductSelected));


					System.out.println("Product selected");


					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DetailHeader))); //wait for visibility of header
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.BagitButton)));

					//Add to Bag
					WebElement AddBag=driver.findElement(By.xpath(element.BagitButton));
					AddBag.click();
					Thread.sleep(2000);
					// driver.findElement(By.xpath(".//*[@id='bagit']")).click();
					js.executeScript("arguments[0].style.border='2px groove green'", AddBag);
					Thread.sleep(8000);
					String ProductAddedToBag=UtilLib.screenshot(driver, ProductName+" is added to Bag successfully" );
					logger.log(LogStatus.PASS, ProductName+" is added to Bag successfully" + logger.addScreenCapture(ProductAddedToBag));

					System.out.println("Product added to Bag");

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[contains(text(),'Remove Bag')]")));//wait for visibility of Remove Bag Button

					driver.navigate().back();
				//	driver.navigate().to(currentURL);
					Thread.sleep(2000);
					String CategoryScreen=UtilLib.screenshot(driver, "Navigated back to "+SelectedCategory+ " screen" );
					logger.log(LogStatus.PASS, "Navigated back to "+SelectedCategory+ " screen"+ logger.addScreenCapture(CategoryScreen));

					//Get the Bag count and Verify the Validation Message on Bag Limit
					String ProdCount_Bag=driver.findElement(By.xpath(".//*[@id='add-bundle']")).getText();
					System.out.println(ProdCount_Bag);
					Thread.sleep(5000);
					int Count=Integer.parseInt(ProdCount_Bag);
					if(Count==9)
					{

						int nextProduct = Product+1;
						Thread.sleep(5000);
						String NextProductName=driver.findElement(By.xpath(".//*[@id='search']/div/div/div[2]/div["+RowSize+"]/div["+nextProduct+"]/div/div/div[1]/div[2]/div/div[1]/div")).getText();
						driver.findElement(By.xpath(".//*[@id='search']/div/div/div[2]/div["+RowSize+"]/div["+nextProduct+"]//img")).click();  // Resource Detail information. 
						Thread.sleep(8000);
						String LastProductSelected=UtilLib.screenshot(driver, NextProductName+" is Selected to add it in Bag" );
						logger.log(LogStatus.PASS, NextProductName+" is Selected to add it in Bag "+ logger.addScreenCapture(LastProductSelected));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.DetailHeader))); //wait for visibility of header
						wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.BagitButton)));


						driver.findElement(By.xpath(element.BagitButton)).click(); // Add to Bag button.
					//	Thread.sleep(6000);
						
						if (driver.findElement(By.xpath(".//*[@id='toast-container']/div/div[2]")).isDisplayed()){
							String BagFullMessage=driver.findElement(By.xpath(".//*[@id='toast-container']/div/div[2]")).getText();
							String FailedToAddToBag=UtilLib.screenshot(driver, "Validation Message for Bag Limit is displaying" );
							logger.log(LogStatus.PASS, "Validation Message for Bag Limit is displayed " + BagFullMessage  + logger.addScreenCapture(FailedToAddToBag));
							System.out.println(BagFullMessage);
							Thread.sleep(6000);
						//	logger.log(LogStatus.PASS, "Validation Message for Bag Limit is displaying" + logger.addScreenCapture(FailedToAddToBag));
						}
						
						else {
							
							String FailedToAddToBag=UtilLib.screenshot(driver, "Validation Message for Bag Limit is Not  displayed" );
							logger.log(LogStatus.FAIL, "Validation Message for Bag Limit is Not displayed" + logger.addScreenCapture(FailedToAddToBag));
							report.endTest(logger);
							Bagsoftverification.assertEquals("No Bag  Validation Error message" , "Bag Validation Limit Error Message");
							//Bagsoftverification.assertAll();
							
						}
						
													
						driver.navigate().back();
						break;
					}
					else 
					{
						
						//	String BagFullMessage=driver.findElement(By.xpath(".//*[@id='toast-container']/div/div[2]")).getText();
						System.out.println("Bag is not full");
					}
				}

				Thread.sleep(5000);
				int NewRow = RowSize+1;
				if(NewRow<=ProductRows.size()){
					WebElement ProductSelected=driver.findElement(By.xpath(".//*[@id='search']/div/div/div[2]/div["+NewRow+"]"));
					JavascriptExecutor Prgmdrpdwn = (JavascriptExecutor) driver;
					Prgmdrpdwn.executeScript("arguments[0].scrollIntoView();",ProductSelected);
				}
				else{
					break;
				}

			}	
		}
		catch (Exception e) 
		{
			String FailedToAddToBag=UtilLib.screenshot(driver, "Validation Message for Bag Limit is Not  displayed" );
			logger.log(LogStatus.FAIL, "Validation Message for Bag Limit is Not displayed" + logger.addScreenCapture(FailedToAddToBag));
			e.printStackTrace();
			e.getMessage();
			//Bagsoftverification.assertEquals("No Bag  Validation Error message" , "Bag Validation Limit Error Message");
			Bagsoftverification.assertAll();
		//	Assertion.
			
		//	String BagLimitValidationMessageFailed=UtilLib.screenshot(driver, "Unable to get the Bag Limit Validation Message");
			//ApplicationUtility.LogFailMessage(logger, log ,"Unable to Logout from AppScript Application"  + logger.addScreenCapture(BagLimitValidationMessageFailed));
		}
	}


	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : AddToBag
	 * @Description : Function to Verify the Logout
	 ********************************************************************************************/	

	public static void AddToBag(ExtentReports report,ExtentTest logger){
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);

			//Select First Product To add to Bag
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dashboard']/div/div[1]")));	
			driver.findElement(By.xpath("//*[@id='dashboard']/div/div[1]/div/div[1]//img")).click();
			String AddToBag=UtilLib.screenshot(driver, "First Product is selected to add to Bag" );
			logger.log(LogStatus.PASS, "First Product is selected to add to Bag" + logger.addScreenCapture(AddToBag));

			//Add to Bag
			driver.findElement(By.xpath(".//*[@id='bagit']")).click();

		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();	
			String ProductNotSelected=UtilLib.screenshot(driver, "Unable to Remove product from Bag");
			ApplicationUtility.LogFailMessage(logger, log ,"Unable to Remove product from Bag"  + logger.addScreenCapture(ProductNotSelected));
			report.endTest(logger);
		}

	}



	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : RemoveBag
	 * @Description : Function to Verify the Logout
	 ********************************************************************************************/	

	public static void RemoveBag(ExtentReports report,ExtentTest logger){
		try{
			WebDriverWait wait = new WebDriverWait(driver,120);
			Thread.sleep(20000);
			driver.findElement(By.xpath(".//*[@id='dashboard']/div/div[1]/div/div[1]/div/div/div[1]/div[1]/div/img")).click();
			Thread.sleep(3000);
			String Bag_Count=UtilLib.screenshot(driver, "Bag Count before removing the product from Bag");
			logger.log(LogStatus.PASS, "Bag Count before removing the product from Bag"  + logger.addScreenCapture(Bag_Count));

			//	wait.until(ExpectedConditions.invisibilityOfElementLocated(".//*[contains(text(),'Remove Bag')]"));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='detail-header']/div/div"))); //wait for visibility of header
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='bagit']")));

			//click Menu Apps or devices or digital content
			driver.findElement(By.xpath(".//*[@id='bagit']")).click();
			String Removed=UtilLib.screenshot(driver, "Product is removed from Bag successfully");
			logger.log(LogStatus.PASS, "Product is removed from Bag successfully"  + logger.addScreenCapture(Removed));

			String Count=driver.findElement(By.xpath(".//*[@id='add-bundle']")).getText();
			System.out.println("Bag Count is: "+Count);
			String RemovedFromBag=driver.findElement(By.xpath(".//*[@id='bagit']")).getText();
			if(RemovedFromBag.equalsIgnoreCase("Add To Bag")){
				System.out.println("Selected Product is Removed from Bag Successfully");
			}
			else{
				System.out.println("Unable to remove product from Bag");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();	
			String ProductNotSelected=UtilLib.screenshot(driver, "Unable to Remove product from Bag");
			ApplicationUtility.LogFailMessage(logger, log ,"Unable to Remove product from Bag"  + logger.addScreenCapture(ProductNotSelected));
			report.endTest(logger);
		}

	}




	/******************************************************************************************** 
	 * @return 
	 * @Function_Name : Logout
	 * @Description : Function to Verify the Logout
	 ********************************************************************************************/	

public static void Logout(ExtentReports report,ExtentTest logger){	

		try{
		//	driver.close();
			//driver.quit();
			
			WebDriverWait wait = new WebDriverWait(driver,60);
			String currentURL=driver.getCurrentUrl();
			driver.navigate().to(currentURL);
			Thread.sleep(6000);
		//	driver.findElement(Utilities.getLocator("Apps")).click();
			Thread.sleep(6000);
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.Logout_Button)));
			//Logout from the Application
			driver.findElement(By.xpath(element.Logout_Button)).click();
			
			
		/*	try{
			     wait.until(ExpectedConditions.visibilityOfElementLocated(Utilities.getLocator("Login")));
			     String Loginscreen=UtilLib.screenshot(driver, "Logout Application");
			     logger.log(LogStatus.PASS, "Logout successful" + logger.addScreenCapture(Loginscreen));
				
		}catch(NoSuchElementException e){
			String Loginscreen1=UtilLib.screenshot(driver, "Logout Application");
			logger.log(LogStatus.FAIL, "Logout unsuccessful" + logger.addScreenCapture(Loginscreen1));
			}*/
			
			

			Thread.sleep(6000);
			String Logout=UtilLib.screenshot(driver, "Logout from AppScript Application");
			String Loginscreen=UtilLib.screenshot(driver, "Logout Application");
		     logger.log(LogStatus.PASS, "Logout successful" + logger.addScreenCapture(Loginscreen));
			//	ApplicationUtility.LogFailMessage(logger, log ,"Logout from AppScript Application"  + logger.addScreenCapture(Logout));
			//	logger.log(LogStatus.PASS, "Logout from AppScript Application"  + logger.addScreenCapture(Logout));

		//	driver.close();
		//	driver.quit();

		}
		catch (Exception e) {
			e.printStackTrace();
			e.getMessage();

			String FailedToLogout=UtilLib.screenshot(driver, "Unable to Logout from AppScript Application");
			//ApplicationUtility.LogFailMessage(logger, log ,"Unable to Logout from AppScript Application"  + logger.addScreenCapture(FailedToLogout));
			//report.endTest(logger);
		}

	}	
	

	
//}

public static void RemoveBagResourceButton(String HeaderSelection,ExtentReports report,ExtentTest logger)
{
	try{
		WebDriverWait wait = new WebDriverWait(driver,120); // 2 minutes to wait. 
		//click Menu Apps or devices or digital content
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FormularyContainer)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]")));
		
		// click the Header [Apps, Devices, Content]. 
		WebElement DeviceHeader=driver.findElement(By.xpath("//*[@id='devices-dashboard']/..//a[contains(text(),'"+HeaderSelection+"')]"));
		JavascriptExecutor js=(JavascriptExecutor)driver; 
		js.executeScript("arguments[0].style.border='2px groove green'", DeviceHeader);
		Actions SelectAction = new Actions(driver);
		SelectAction.doubleClick(DeviceHeader).perform();
	
		/*try{
			SelectAction.doubleClick(DeviceHeader).perform();
			// once we perform doubleclick need to check Whether Resource Type is active or not. 
		}
	catch(Exception e){
		
	}*/
		
		

		//click the first product
		Thread.sleep(10000); // wait for 10 seconds. 
		

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.FeaturedProducts)));
		WebElement FirstProduct= driver.findElement(By.xpath(element.FirstProduct));
		WebElement AddingProductName= driver.findElement(By.xpath(element.FirstProductName));
		
		String ProductName= AddingProductName.getText(); // Get the Product Name..
	
	
		js=(JavascriptExecutor)driver; 
		js.executeScript("arguments[0].style.border='2px groove green'", FirstProduct);
		String ClikcingFirstProduct=UtilLib.screenshot(driver, "Selecting the product in "+HeaderSelection);
		logger.log(LogStatus.PASS, "Resource Type -" + HeaderSelection + "- Resource Name -" + ProductName+ logger.addScreenCapture(ClikcingFirstProduct));

		FirstProduct.click(); // click on first product. 
	
		
		Thread.sleep(10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ProductDetailsHeader)));

//Verify bag number and products in bag before adding into Bag....	
		int bagNumberbeforeadding=ProductCountOnBagIcon(js,logger);
		String NumberofProductinBagBeforeAdd=UtilLib.screenshot(driver, "Count in Bag - "  + bagNumberbeforeadding);
	//	logger.log(LogStatus.INFO, "Number of Products in Bag : " + bagNumberbeforeadding + logger.addScreenCapture(NumberofProductinBagBeforeAdd));
//add product to bag and verify the product name under particular resource
		//Thread.sleep(30000);				
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element.AddtoBagbutton)));	
		WebElement AddProductToBagButton= driver.findElement(By.xpath(element.AddtoBagbutton));
		js.executeScript("arguments[0].style.border='2px groove green'", AddProductToBagButton);

     String ClikcingTheAddToBagButton=UtilLib.screenshot(driver, "clicking the Add To Bag button");
     AddProductToBagButton.click();
	  logger.log(LogStatus.PASS, "clicking the Add to Bag Button"  +logger.addScreenCapture(ClikcingTheAddToBagButton));
		
//Need to verify Remove From Bag Button  - After Click on Add to Bag..  [Yathi]. 
	Thread.sleep(2000);
//click BagSymbole

	WebElement Bag= driver.findElement(By.xpath(element.BagSymbole));
		Bag.click();
		Thread.sleep(2000);
		js.executeScript("arguments[0].style.border='2px groove green'", Bag);
		String Bagicon=UtilLib.screenshot(driver, "click the Bag icon to verify the product is added sucessfully into bag and also to verify the Bag count");
		logger.log(LogStatus.PASS, "Click Bag icon Successful and able to view Added Resource"  + logger.addScreenCapture(Bagicon));

		List<WebElement> links1=driver.findElements(By.xpath(element.ProductsListInBag)); // Click on Bag Icon. 
		Thread.sleep(2000);
//verify the product present under the particular resource. [input is Product Name, header Section under Bag, Product Name]. 
			switch (HeaderSelection) {
	        case "Apps":
	        	String ProductsUnderApps=element.AppsProductsListInBag;  
	        	VerifytheProductNameInBag(ProductName,ProductsUnderApps,HeaderSelection, logger);
             	break;
	        case "Devices":
	        	String ProductsUnderDevices=element.DevicesProductsListInBag;
	        	 VerifytheProductNameInBag(ProductName,ProductsUnderDevices,HeaderSelection, logger);
	        	break;
	        case "Digital Content":

	        	String ProductsUnderDC=element.DCProductsListInBag;
	        	 VerifytheProductNameInBag(ProductName,ProductsUnderDC,HeaderSelection, logger);
	        	break;
	        default: 
	               System.out.println("Switch default No Products added into the Bag");
	               break;

			}
			
			Bag.click();
		Thread.sleep(3000);
		

//verify button name, after product added into the bag. [Verification of Text] changed from "Add to Bag"  to Remove Bag. 
		String buttonname=AddProductToBagButton.getText();
			if(buttonname.equals("Remove Bag"))
			{
				System.out.println("Remove Bag button name verified sucessfully");
				String ButtonName=UtilLib.screenshot(driver, "Remove Bag button Name is Verifyed, after adding the product into Bag");
				logger.log(LogStatus.PASS, "Remove Bag button Name is Verifyed, after adding the product into Bag"  + logger.addScreenCapture(ButtonName));
			//	int CountBeforeDeletion =ProductCountOnBagIcon(js,logger);
				AddProductToBagButton.click(); // Remove button Click. 
				Thread.sleep(5000);
				
				String buttonname2=AddProductToBagButton.getText();
				String ButtonNamescreenshot=UtilLib.screenshot(driver, "Add to Bag is Verifyed, after adding the product into Bag");
				 if (buttonname2.equals("Add to Bag"))
						 {
					    logger.log(LogStatus.PASS, "Remove Bag button Click is Successful : Text of Button changed Add to Bag"  + logger.addScreenCapture(ButtonNamescreenshot));
					 }
				 else {
					 logger.log(LogStatus.FAIL, "Remove Bag button Click is UnSuccessful : Text of Button changed Add to Bag"  + logger.addScreenCapture(ButtonNamescreenshot));
				 }
				
		}
			else
			{
				System.out.println("button name not chnaged");
				String ButtonName=UtilLib.screenshot(driver, "Remove Bag button Name button Name is not changed after adding the product into the Bag");
				ApplicationUtility.LogFailMessage(logger, log ,"button Name is not changed after adding the product into the Bag "  + logger.addScreenCapture(ButtonName));

			}

		//verifying product count which will display on Bag
	int CountAfterDeletion =ProductCountOnBagIcon(js,logger);
		System.out.println("Product number in Bag is "+CountAfterDeletion);

		if(CountAfterDeletion==links1.size()-1)
		{
		  String ButtonNameCount=UtilLib.screenshot(driver, "Remove Bag button functionality is Successfull");
		  logger.log(LogStatus.PASS, "Count-Verification in Bag Successful" +  logger.addScreenCapture(ButtonNameCount));
		}
		else
		{
			System.out.println("verifying Product count displaying in Bag -----count is mismatching");
			String ProductNameInBag=UtilLib.screenshot(driver, "verifying Product count displaying in Bag-----count is mismatching");
			ApplicationUtility.LogFailMessage(logger, log ,"verifying Product count displaying in Bag-----count is mismatching "  + logger.addScreenCapture(ProductNameInBag));

	}
		
		
	WebElement AppScriptLogo= driver.findElement(By.xpath(element.AppScriptLogo));
		AppScriptLogo.click();
		Thread.sleep(2000);

	}
	catch(Exception e)
	{
		e.printStackTrace();
		e.getMessage();
		String ProductNotSelected=UtilLib.screenshot(driver, "Product not added sucessfully into the Bag");
		ApplicationUtility.LogFailMessage(logger, log ,"Product not added sucessfully into the Bag "  + logger.addScreenCapture(ProductNotSelected));

	}
}


/******************************************************************************************** 
 * @return 
 * @throws InterruptedException 
 * @Function_Name : SearchProductAndVerifyButtonName
 * @Description : Search the product and Add to BAg
 ********************************************************************************************/	
public static void SearchProductAndAddtoBAg(JavascriptExecutor js,ExtentTest logger,String DeletedProductName,WebElement Bag) throws InterruptedException
{
	WebDriverWait wait = new WebDriverWait(driver,120);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchField)));
	WebElement search=driver.findElement(By.xpath(element.SearchBox));
	search.sendKeys(DeletedProductName);
	String SearchProduct= UtilLib.screenshot(driver, "Searching for the product "+DeletedProductName+" after Removing from Bag");
	logger.log(LogStatus.PASS, "Searching for : " +DeletedProductName +logger.addScreenCapture(SearchProduct));
	search.submit();

	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.SearchResult)));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ResultHeader)));

	WebElement ProductSearched= driver.findElement(By.xpath(element.SearchedProduct));
	js.executeScript("arguments[0].style.border='2px groove green'", ProductSearched);
	String Searchresult= UtilLib.screenshot(driver, "product "+DeletedProductName+" searched sucessfully");
	logger.log(LogStatus.PASS, "product "+DeletedProductName+" searched sucessfully"  +logger.addScreenCapture(Searchresult));
	ProductSearched.click();
	Thread.sleep(5000);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element.ProductDetailsHeader)));
	WebElement AddProductToBagButton= driver.findElement(By.xpath(element.AddtoBagbutton));
	String buttonname=AddProductToBagButton.getText();
	if(buttonname.equals("Add to Bag"))
	{
		AddProductToBagButton.click();
		
		System.out.println("button name verified sucessfully");
		js.executeScript("arguments[0].style.border='2px groove green'", AddProductToBagButton);
		//buttonname.cl
		String ButtonName=UtilLib.screenshot(driver, "button Name is Verifyed, after deleting the product from Bag");
		logger.log(LogStatus.PASS, "button Name is Verifyed, From Remove Bag to - 'Add to Bag'"   + logger.addScreenCapture(ButtonName));

		
	}
	else
	{
		System.out.println("button name not chnaged");
		js.executeScript("arguments[0].style.border='2px groove green'", AddProductToBagButton);
		String ButtonName=UtilLib.screenshot(driver, "button Name is not changed after deleting the product from Bag");
		ApplicationUtility.LogFailMessage(logger, log ,"Expected Button Name:Add to Bag" + "Actual :" + buttonname   + logger.addScreenCapture(ButtonName));

	}
	WebElement AppScriptLogo= driver.findElement(By.xpath(element.AppScriptLogo));
	js.executeScript("arguments[0].style.border='2px groove green'", AppScriptLogo);
	AppScriptLogo.click();
	Thread.sleep(3000);

}







}



