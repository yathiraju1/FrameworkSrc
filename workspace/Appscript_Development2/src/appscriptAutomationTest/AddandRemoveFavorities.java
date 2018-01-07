/*package appscriptAutomationTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddandRemoveFavorities {
	

public static void SelectProduct(String ProductSelected,String HeaderSelection) throws IOException{
		
	try
		{
		WebDriver driver=new ChromeDriver();
			WebDriverWait wait = new WebDriverWait(driver,300);
			String FilePath=System.getProperty("user.dir")+"/AppData/App_TestData.xlsx";
			String sheet="Appscript";
	
			String AppHeaderSelected= UtilLib.getCellData(FilePath, sheet, "APPHeaderName", 1, "string");
			String DeviceHeaderSelected=UtilLib.getCellData(FilePath, sheet, "DeviceHeaderName", 1, "string");
			String DigitalHeaderContentSelected=UtilLib.getCellData(FilePath, sheet, "DigitalHeaderName", 1, "string");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/[@id='devices-dashboard']/..a[contains(text(),'"+HeaderSelection+"')]")));  Apps.devices, content..
			WebElement DeviceHeader=driver.findElement(By.xpath("/[@id='devices-dashboard']/..a[contains(text(),'"+HeaderSelection+"')]"));
			Actions FavoriteAction = new Actions(driver);
			FavoriteAction.doubleClick(DeviceHeader).perform(); 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='dashboard']/div/div[1]")));	
			WebElement SelectProduct= driver.findElement(By.xpath("/[@id='dashboard']div[contains(text(),'"+ProductSelected+"')]"));  selecting Product...
			Actions SelectProductAction = new Actions(driver);
			SelectProductAction.doubleClick(SelectProduct).perform();  selecting the product..
			
			Add Selected Product to Favorites
			try{
				
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='detail-header']")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/[@id='detail-header']/div/div/div[1]/div")));  add or remove..
			Thread.sleep(1000);
			WebElement AddFavorites = driver.findElement(By.xpath("/[@id='detail-header']/div/div/div[1]/div/a")); 
			String FavoritesValues_Add = driver.findElement(By.xpath("/[@id='detail-header']/div/div/div[1]/div/a")).getText();  add or remove text..
			System.out.println(FavoritesValues_Add);
			List<WebElement> AddFavorites = driver.findElements(By.xpath("/[@id='detail-header']/div/div/div[1]/div/a"));
			System.out.println("link size:" +AddFavorites.size()); 
			Actions AddOrRemoveToFavoriteAction = new Actions(driver);
			if(FavoritesValues_Add.equalsIgnoreCase("Add to Favorites")){
				WebElement AddToFavorites= driver.findElement(By.xpath("/[@id='detail-header']span[contains(text(),'Add to Favorites')]"));
				AddOrRemoveToFavoriteAction.doubleClick(AddToFavorites).perform();
				System.out.println("Added to Favorites");
				UtilLib.HomePage_APPScript(HeaderSelection);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='dashboard']/div")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='dashboard']/div/div/h3[contains(text(),'Favorites')]")));
				UtilLib.Favorite_Section(ProductSelected);
				}
				
			 driver.findElement(By.xpath("/[@id='dashboard']div[contains(text(),'"+ProductSelected+"')]")).click();
			 wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/[@id='detail-header']/div/div/div[1]/div")));
			 String FavoritesValues_Remove = driver.findElement(By.xpath("./[@id='detail-header']/div/div/div[1]/div/a[2]/span")).getText();
			 System.out.println(FavoritesValues_Remove);
			if(FavoritesValues_Remove.equalsIgnoreCase("Remove Favorite")){
				WebElement RemoveToFavorites= driver.findElement(By.xpath("/[@id='detail-header']span[contains(text(),'Remove Favorite')]"));
				AddOrRemoveToFavoriteAction.doubleClick(RemoveToFavorites).perform();
				System.out.println("Removed from Favorites");
				UtilLib.HomePage_APPScript(HeaderSelection);
				Thread.sleep(5000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='dashboard']/div")));
				UtilLib.Favorite_Section(ProductSelected);
			}
			else{
				System.out.println("Not Removed From Fevorite");
			}
			

			
			for(WebElement AddFav:AddFavorites){
				Thread.sleep(1000);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/[@id='detail-header']/div/div/div[1]/div")));
				String AddFavoritesText = AddFav.getText();
				System.out.println(AddFavoritesText);
				if(AddFavoritesText.equalsIgnoreCase("Add to Favorites")){
					WebElement AddToFavorites= driver.findElement(By.xpath("/[@id='detail-header']span[contains(text(),'Add to Favorites')]"));
					AddOrRemoveToFavoriteAction.doubleClick(AddToFavorites).perform();
					System.out.println("Added to Favorites");
					UtilLib.HomePage_APPScript(HeaderSelection);
					UtilLib.Favorite_Section(ProductSelected);
					
					
				}
			for (int i = 0; i < AddFavorites.size(); i++) {
				System.out.println(AddFavorites.get(i).getText());
				System.out.println("--");
				if(AddFavorites.equalsIgnoreCase("Add to Favorites")){
					WebElement AddToFavorites= driver.findElement(By.xpath("/[@id='detail-header']span[contains(text(),'Add to Favorites')]"));
					AddToFavoriteAction.doubleClick(AddToFavorites).perform();
					System.out.println("Added to Favorites");

				}
				else if (AddFavoritesText.equalsIgnoreCase("Remove Favorite")){
					WebElement RemoveToFavorites= driver.findElement(By.xpath("/[@id='detail-header']span[contains(text(),'Remove Favorite')]"));
					AddOrRemoveToFavoriteAction.doubleClick(RemoveToFavorites).perform();
					System.out.println("Removed from Favorites");
					UtilLib.HomePage_APPScript(HeaderSelection);
					UtilLib.Favorite_Section(ProductSelected);

				}
				
				else{
					System.out.println("Not Found");
				}
				
			} 
			
			driver.findElement(By.xpath("/[@id='logout']")).click();
			driver.close();
			driver.quit();

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



public static void HomePage_APPScript(String HeaderSelected){
	WebDriverWait wait = new WebDriverWait(driver,100);
	String FilePath=System.getProperty("user.dir")+"/AppData/App_TestData.xlsx";
	String sheet="Appscript";
	try{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='header']/div/div[1]/div[2]/div/div[2]")));   complete header section..
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/[@id='apps-dashboard']/..a[contains(text(),'"+HeaderSelected+"')]"))); configured. 
		WebElement HeaderTab= driver.findElement(By.xpath("/[@id='apps-dashboard']/..a[contains(text(),'"+HeaderSelected+"')]"));
		Actions HeaderTabAction = new Actions(driver);
		HeaderTabAction.doubleClick(HeaderTab).perform();
	}
	catch (Exception e) {
		e.printStackTrace();		}
	
	
}

public static void Favorite_Section(String SelectedProduct){
	Verify the Favorites section
	try{
	WebDriverWait wait = new WebDriverWait(driver,100);
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/[@id='dashboard']/div")));
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/[@id='dashboard']/div/div[2]div[contains(text(),'"+SelectedProduct+"')]")));
	List<WebElement> FavoriteSection=driver.findElements(By.xpath("/[@id='dashboard']/div/div/h3"));
	System.out.println("link size:" +FavoriteSection.size()); 
	boolean FavoriteSectionValue=driver.findElement(By.xpath("/[@id='dashboard']/div/div/h3[contains(text(),'Favorites')]")).isDisplayed();
	 
	Actions AddToFavoriteAction = new Actions(driver);
	
	for(WebElement Favorite:FavoriteSection){
		String FavoritesText = Favorite.getText();
		System.out.println(FavoritesText);
		if(FavoritesText.equalsIgnoreCase("Favorites")){
			WebElement VerifyFavoriteSection= driver.findElement(By.xpath("/[@id='dashboard']/div/div[2]div[contains(text(),'"+SelectedProduct+"')]"));
			String FavoriteProduct=driver.findElement(By.xpath("/[@id='dashboard']/div/div[2]div[contains(text(),'"+SelectedProduct+"')]")).getText();
			if(SelectedProduct.equalsIgnoreCase(FavoriteProduct)){
				System.out.println(SelectedProduct+" has been Added to Favorites Successfully");
				break;
			}
			else{
				System.out.println(SelectedProduct+"not added to Favorites");
			}
			
		}
		else{
			System.out.println(FavoritesText+" is not a Favorite Section");
		}
}
	
	}
	catch (Exception e) {
		e.printStackTrace();
		e.getMessage();
	}
}
} */
