package appscriptAutomationTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ViewPrescriberInfo {

	public static void getData(String email, String password, String URL) throws Exception {
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		
		WebDriver driver = new ChromeDriver();
		
	     driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(URL);

		sleep();

		//driver.manage().window().maximize();

		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		//driver.get(URL);

		//sleep();

		driver.findElement(By.xpath("//a[text()='Sign In']")).click();
		sleep();

		driver.findElement(By.id("identifierId")).sendKeys(email);
	//	driver.findElement(By.id("identifierId")).sendKeys(email, Keys.ENTER);
		sleep();
		driver.findElement(By.id("identifierNext")).click();
		
		driver.findElement(By.name("password")).sendKeys(password, Keys.ENTER);
		sleep();

		//driver.findElement(By.xpath("//span[contains(text(),'IMS Health AppScript about your health')]")).click();

	List<WebElement> list=driver.findElements(By.xpath(".//*[@class='xW xY ']/span/b"));
	System.out.println("Unread mail size in first page: "+list.size());
	
	String xpath1;String element1;String element2;String element3;
	for (int i=0;i<list.size();i++){
		int j=i+1;
		 xpath1="(.//*[@class='y6'])["+j+"]/span[1]";
		 element1=driver.findElement(By.xpath(xpath1)).getText();System.out.println(element1);
		String xpath2="(.//*[@class='xW xY '])["+j+"]/span";
		element2=driver.findElement(By.xpath(xpath2)).getAttribute("title");System.out.println(element2);
		String xpath3="(.//*[@class='yX xY ']/div[2]/span)["+j+"]";
		element3=driver.findElement(By.xpath(xpath3)).getAttribute("name");System.out.println(element3);
			if (element1.equalsIgnoreCase("A message from IMS Health AppScript about your health") && (element3.equalsIgnoreCase("AppScript"))){
				driver.findElement(By.xpath(xpath1)).click();
			break;
			}
	}
	
	String pin_num=driver.findElement(By.xpath("(.//div[@style='font-size:25px;letter-spacing:3px'])")).getText();
	System.out.println("Pin Number: "+pin_num);
	driver.findElement(By.xpath("(.//td[@valign='middle'])[4]")).click();
	
	
	String oldTab = driver.getWindowHandle();
	ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	newTab.remove(oldTab);
	driver.switchTo().window(newTab.get(0));
	driver.findElement(By.id("pin")).sendKeys(pin_num);
	driver.findElement(By.xpath(".//*[@id='pinForm']/button")).click();
	
	//System.out.println("From: "+driver.findElement(By.xpath("(.//*[@class='zF'])[1]")).getAttribute("name"));
	
	
	/*Iterator<WebElement> i=list.iterator();
	WebElement e;
	
	//loop to get only time of all mails
	while(i.hasNext()){
	
		e=i.next();
		System.out.println(e.getText());
		
		}
	
	//loop to extract whole time stamp of all mails
	while(i.hasNext()){
		
		e=i.next();
		System.out.println("Title: "+e.getAttribute("title"));
		}
	System.out.println("Latest mail subject: "+driver.findElement(By.xpath("(.//*[@class='y6'])[1]/span[1]")).getText()); //extracts latest mail's subject line
	*/
		//driver.quit();
	}

	public static void sleep() throws Exception {
		Thread.sleep(1000);
	}

	public static void main(String[] args) throws Exception {

		final String email = "appscripttest12@gmail.com	"; // change accordingly
		final String password = "Super123$"; // change accordingly
		String URL = "https://www.google.com/gmail/about/";
		getData(email, password, URL);
	}
}
