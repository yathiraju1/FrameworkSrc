package FooterTestNavigation;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.AppScript.generic.UtilLib;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import appscriptAutomationTest.Utilities;

public class FooterLinks extends Footertest{

	static String oldtab;
	static JavascriptExecutor js = (JavascriptExecutor) driver; //Highlight
	
	public static boolean verifyfooter_TOS(ExtentTest test,ExtentReports report) throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='tos-link']")));
		driver.findElement(By.xpath(".//*[@id='tos-link']")).click(); //Click on Terms Of Service
		Thread.sleep(3000);

		String TOS=driver.findElement(By.xpath(".//*[@id='tos']/div[1]/h4")).getText();
		System.out.println(TOS);
		
		js.executeScript("arguments[0].style.border='2px groove green'", driver.findElement(By.xpath(".//*[@id='tos']/div[1]/h4"))); //Highlight

		test.log(LogStatus.INFO, "TermsOfService present"+test.addScreenCapture(Utilities.screenshot(driver, "TermsOfService")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='tos']/div[1]/div/button")));
		driver.findElement(By.xpath(".//*[@id='tos']/div[1]/div/button")).click(); //Close Terms Of Service
		Thread.sleep(3000);
		return true;
		
		
	}
	
	public static boolean verifyfooter_PrivacyPolicy(ExtentTest test,ExtentReports report) throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='privacy-link']")));
		driver.findElement(By.xpath(".//*[@id='privacy-link']")).click(); //Click on Privacy Policy
		Thread.sleep(3000);

		String PrivacyPolicy=driver.findElement(By.xpath(".//*[@id='privacyPolicay']/div[1]/h4")).getText();
		System.out.println(PrivacyPolicy);

		js.executeScript("arguments[0].style.border='2px groove green'", driver.findElement(By.xpath(".//*[@id='privacyPolicay']/div[1]/h4"))); //Highlight

		test.log(LogStatus.PASS, "PrivacyPolicy present"+test.addScreenCapture(Utilities.screenshot(driver, "PrivacyPolicy")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//*[@id='privacyPolicay']/div[1]/div/button")));
		driver.findElement(By.xpath(".//*[@id='privacyPolicay']/div[1]/div/button")).click(); //Close Privacy Policy
		Thread.sleep(3000);
		return true;

	}

	static String parentHandle = driver.getWindowHandle();

	public static boolean verifyfooter_AppStore(ExtentTest test,ExtentReports report) throws InterruptedException{
		

		System.out.println(parentHandle);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='video']/a/img")));

		oldtab=driver.getWindowHandle();
		driver.findElement(By.xpath(".//*[@id='video']/a/img")).click(); //Click on Download on the App Store link
		Thread.sleep(20000);
		for (String winHandle : driver.getWindowHandles()) {
			screenshot = Utilities.screenshot(driver, "CloseTab1");
			if (!winHandle.equals(oldtab)) {

				driver.switchTo().window(winHandle);// switch focus of WebDriver to the next found window handle (that's your newly opened window)

				Thread.sleep(3000);

				String AppStorePage=Utilities.screenshot(driver, "Navigate to App Store screen successfully");

				test.log(LogStatus.PASS, "Navigate to App Store screen successfully" + test.addScreenCapture(AppStorePage)); 
				System.out.println(driver.getTitle());
				Thread.sleep(3000);
				driver.close(); 
				System.out.println("Closing the Current Window");
				driver.switchTo().window(parentHandle);
			}
		}
		return true;
		

	}
	

	
//	driver.switchTo().window(parentHandle);
		//    test.log(LogStatus.PASS, "Appstore_Download"+test.addScreenCapture(Utilities.screenshot(driver, "Appstore_Download")));


		//    new FooterLinks().closeTab(driver,test);


	public static boolean verifyfooter_IQVIA(ExtentTest test,ExtentReports report) throws InterruptedException{

		/*System.out.println(parentHandle);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='tos']/div/ul/li[3]")));

		oldtab=driver.getWindowHandle();
		driver.findElement(By.xpath(".//*[@id='tos']/div/ul/li[3]")).click(); //Click on Download on the IQVIA link
		Thread.sleep(20000);
		for (String winHandle : driver.getWindowHandles()) {
			screenshot = Utilities.screenshot(driver, "CloseTab1");
			if (!winHandle.equals(oldtab)) {

				driver.switchTo().window(winHandle);// switch focus of WebDriver to the next found window handle (that's your newly opened window)

				Thread.sleep(3000);

				String IQVIAPage=Utilities.screenshot(driver, "Navigate to IQVIA screen successfully");

				test.log(LogStatus.PASS, "Navigate to IQVIA screen successfully" + test.addScreenCapture(IQVIAPage)); 
				System.out.println(driver.getTitle());
				Thread.sleep(3000);
				driver.close(); 
				System.out.println("Closing the Current Window");
			}
		}
		return true;
	}
}*/
		
		



		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='tos']/div/ul/li[3]")));
		driver.findElement(By.xpath(".//*[@id='tos']/div/ul/li[3]")).click(); //Click on IQVIA
		Thread.sleep(20000);
		//    test.log(LogStatus.PASS, "IQVIA"+test.addScreenCapture(Utilities.screenshot(driver, "IQVIA")));
		for (String winHandle : driver.getWindowHandles()) {
			screenshot = Utilities.screenshot(driver, "CloseTab1");
			if (!winHandle.equals(oldtab)) {
				screenshot = Utilities.screenshot(driver, "CloseTab2");
				driver.switchTo().window(winHandle);// switch focus of WebDriver to the next found window handle (that's your newly opened window)

				Thread.sleep(3000);

				String IQVIAPage=Utilities.screenshot(driver, "Navigate to IQVIA screen successfully");

				test.log(LogStatus.PASS, "Navigate to IQVIA screen successfully" + test.addScreenCapture(IQVIAPage)); 
				System.out.println(driver.getTitle());
				Thread.sleep(3000);
				driver.close(); 
				System.out.println("Closing the Current Window");
			}
		}
	


		//	   new FooterLinks().closeTab(driver,test);

		driver.switchTo().window(parentHandle); 	


		return true;
	}


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

public static String screenshot(WebDriver driver, String string) {
	// TODO Auto-generated method stub
	return null;
}

		

}

	/*public static void closeTab(WebDriver driver,ExtentTest test) throws InterruptedException{
		oldtab=driver.getWindowHandle();
		screenshot = Utilities.screenshot(driver, "CloseTab");
		for (String winHandle : driver.getWindowHandles()) {
			screenshot = Utilities.screenshot(driver, "CloseTab1");
		if (!winHandle.equals(oldtab)) {
			screenshot = Utilities.screenshot(driver, "CloseTab2");
		     driver.switchTo().window(winHandle);// switch focus of WebDriver to the next found window handle (that's your newly opened window)
				test.log(LogStatus.PASS, "Navigate to Help screen successfully" + test.addScreenCapture(HelpPage)); 
			 System.out.println(driver.getTitle());
			 Thread.sleep(3000);
		     driver.close(); 
		     System.out.println("Closing the Current Window");
		}
		}
		}


	 */



