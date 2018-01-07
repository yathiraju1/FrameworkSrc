package FooterTestNavigation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TestLogin {
	@Test(priority=1)
	public void verifyFooterLinks_TOS_BeforeLogin(){
		WebDriver driver = new ChromeDriver();
//		driver.get("https://stg.appscript.net");
		driver.get("https://appscript.net");
	
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
	}

}
