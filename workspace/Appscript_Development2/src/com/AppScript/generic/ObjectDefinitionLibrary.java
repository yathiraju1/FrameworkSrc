package com.AppScript.generic;

public class ObjectDefinitionLibrary {
	
	/**************************Login***************************/
       public String UserName = "//*[@id='username']";
       public String Password = "//*[@id='password']";
       public String Login_Button = "//*[@id='submit']";
       
    
       /**************************Free User***************************/
       public String SignInFreeUser = ".//*[@id='section-1']/div/div[2]/div[2]";
       public String FirstName = ".//*[@id='first']";
       public String LastName = ".//*[@id='last']";
       public String Email = ".//*[@id='email']";
       public String Password_FreeUser = ".//*[@id='password']";
       public String AcceptCheckbox = "//div[@class='checkbox']";
       public String SubmitButton = "//button[text()='SUBMIT']";

       
       /**************************Dashboard Header - Search***************************/
       public String Loading = "//*[@id='login']/div[1]/div";
       public String Dashboard = "//*[@id='login']/div[1]/div";
       public String SearchField = ".//*[@id='search-form']";
       public String SearchBox = ".//*[@id='query']";
       public String SearchResult = ".//*[@id='search']/div";
       public String ResultHeader = ".//*[@id='search']/div/div/div[1]/h3";
       public String FirstProductSelected="//*[@id='dashboard']/div/div[1]/div/div[1]//div[1]/img";
       public String ResourceTypes="//*[@id='header']/div/div[1]/div[2]/div/div[2]";
     
       
       /**************************Dashboard-Categories,FavoriteSection - Apps,Devices,DigitalContent***************************/
       public String VerifyFavorites= "//*[@id='dashboard']//h3[contains(text(),'Favorites')]";
       public String FavoriteSection= "//*[@id='dashboard']/div/div[2]/div/div/div/div/div[1]";
       public String Dashbord="//*[@id='dashboard']/div";
       public String DashboardCategories="//*[@id='dashboard']/div/div/h3";
       public String ProductImage="//*[@id='dashboard']//img";
       public String FavoriteCategory="//*[@id='dashboard']/div/div[2]/div/div";
       public String FavoritesProduct="//*[@id='dashboard']/div/div[2]/div/div/div/div/div/div/div/div[1]/div";
       
       /**************************Bag Limit Validation***************************/
       public String NavigationHeader=".//*[@id='nav-header']";
       public String Content=".//*[@id='content']";
       public String SideBar=".//*[@id='sidebar']";
       public String SearchContent=".//*[@id='search']/div/div";
       public String ProductRows=".//*[@id='search']//div[@class='row app-wrapper']";
       public String DetailHeader=".//*[@id='detail-header']/div/div";
       public String BagitButton=".//*[@id='bagit']";



       
       /**************************Product Detail Page-Add/Remove***************************/
       public String ProductHeader="//*[@id='detail-header']";
       public String SelectedProduct=".//*[@id='detail-header']//h1";
       public String DetailButton_Recommend="//*[@id='detail-header']/div/div/div[1]/div";
       public String FavoriteButtonValues="//*[@id='detail-header']/div/div/div[1]/div/a";
       public String FavoriteValue="//*[@id='detail-header']/div/div/div[1]/div/a/span";
       public String FavoriteValueRemove=".//*[@id='detail-header']/div/div/div[1]/div/a[2]/span";
       
       
       /*******Add to Bag from Product detail page **************/
       public String AddtoBagbutton="//button[@id='bagit']";
       public String RemoveFromBagbutton="//button[contains(text(),'Remove Bag')]";
       public String ProductDetailsHeader="//*[@id='detail-header']";
       public String ProductNumberInBag=".//*[@id='add-bundle']";
       public String NumberofProductsInBag=".//*[@id='bag-list']/li";
       
       public String AppsProductsListInBag="//*[@id='bag-list']//child::div[@class='apps-bagHeader bagheader'] /../li[@class='apps-bagList']/div/span";
       public String DevicesProductsListInBag="//*[@id='bag-list']//child::div[@class='devices-bagHeader bagheader'] /../li[@class='devices-bagList']/div/span";
       public String DCProductsListInBag="//*[@id='bag-list']//child::div[@class='content-bagHeader bagheader'] /../li[@class='content-bagList']/div/span";
       public String ProductsListInBag="//*[@id='bag-list']/li";
       
       
       public String BagSymbole=".//*[@id='bag-collapse']/i";
       
       public String AppScriptLogo=".//*[@id='logo']/img";
       public String HeadersInBag=".//*[@id='bag-list']/div";
       public String AppsProductsInBag=".//*[@id='bag-list']/li[@class='apps-bagList']/div/span";
       public String DevicesProductsInBag=".//*[@id='bag-list']/li[@class='devices-bagList']/div/span";
       public String ContentProductsInBag=".//*[@id='bag-list']/li[@class='content-bagList']/div/span";
       
       public String FirstProductNameInBag=".//*[@id='bag-list']/li[1]/div/span";
       public String FirstProductDelete=".//*[@id='bag-list']/li[1]/div/i";
       public String FirstHeaderInBag=".//*[@id='bag-list']/div[1]";
       public String PdouctToAddBag=".//*[@id='dashboard']/div[@class='dashboard-content']/div/h3[contains(text(),'Recommended')]/../div/div[1]";
       public String WaitForRecomandProducts= ".//*[@id='dashboard']/div/div[2]";
       public String BagLimitErrormessage=".//*[@id='toast-container']/div";
       public String secondProductToAdd=".//*[@id='dashboard']/div/div[3]/div/div[2]/div/div/div[1]";
       //.//*[@id='toast-container']/div/div[1]
      /* public String DevicesFirstProductNameInBag=".//*[@id='bag-list']/li[2]/div/span";
       public String DevicesFirstProductDelete=".//*[@id='bag-list']/li[2]/div/i";
       public String DigitalFirstProductNameInBag=".//*[@id='bag-list']/li[3]/div/span";
       public String DigitalFirstProductDelete=".//*[@id='bag-list']/li[3]/div/i";
       */
       
       public String SearchedProduct=".//*[@id='search']/div/div/div[2]/div/div/div/div/div[1]";
       public String SingleProductNameInBag=".//*[@id='bag-list']/li/div/span";
       public String SingleProductDelete=".//*[@id='bag-list']/li/div/i";
       
      
       /**************************ViewAllFormulary***************************/   
       public String Product="//tbody/tr[1]/td[2]";
       public String ClikedProduct=".//*[@id='detail-header']/div/div/div[2]/div/h1";
       public String SubMenu="//div[@id='nav']/ul/li/a";
       public String NavigationBar_Formulary=".//*[@id='utility-nav']";
       public String ViewAllFormularyLink=".//*[@id='formulary-link']";
       public String FormularyContainer=".//*[@id='content']/div";
       public String DataTableFirstRowSeleted="//tbody/tr[1]";
       public String SelectedProductDetailHeader=".//*[@id='detail-header']/div/div";
       public String ViewAllFormularyContent=".//*[@id='content']";
       
    //   public String ProductSelected="//*[@id='dashboard']/div/div[1]/div/div[1]/div/div/div[1]/div[2]/div/div[1]/div";
   
       public String ProductSelected=".//*[@id='dashboard']/div/div[1]/div/div[1]/div/div/div[1]/div[2]/div/div[1]/div";
       /**************************Logout***************************/  
        public String Logout_Button="//*[@id='logout']";
       
 // DashBoard Description page.    
        public String FirstProductName=".//*[@id='dashboard']/div/div[1]/div/div[1]/div/div/div[1]/div[2]/div/div[1]/div";
        public String FirstProduct=".//*[@id='dashboard']/div/div[1]/div/div[1]/div/div/div[1]";
     
        public String FeaturedProducts="//*[@id='dashboard']/div/div[1]";
        
        
        
}


