

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.List;
	import java.util.concurrent.TimeUnit;

	import org.apache.poi.hssf.usermodel.HSSFSheet;
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.Select;
	import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
	import com.relevantcodes.extentreports.ExtentTest;
	import com.relevantcodes.extentreports.LogStatus;

	public class SalesforceTest {
		public static ExtentReports reports;
		public static ExtentTest logger;
		public static WebDriver driver;

//		public static void main(String args[]) throws Exception {
//			setupChromeDriver();
//			Reporting();
//			// TC_01_loginErrorMessage();
//			 TC_02_loginToSFDC();
//			// TC_03_rememberMeCheckBox();
//			// TC_4A_forgotPassword();
//			//TC_4B_validateLoginErrorMessage();
//			//TC_05_SelectUserMenu();
//			//TC_06_MyProfile();
//			Report_Close();
//			TearDown();
//
//		}
       @BeforeTest
		public static void Reporting() {
	
			String fileName = new SimpleDateFormat("'SampleTestExtentDemo_'yyyyMMddHHmm'.html'").format(new Date());
			String reportPath = "C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_ExtendReports\\" + fileName;
			reports = new ExtentReports(reportPath);

		}

		public static String[][] getDataInput(String filePath, String sheetName) throws IOException {
			// Get the Xl path
			File xlFile = new File(filePath);
			// Access to xl path
			FileInputStream xlDoc = new FileInputStream(xlFile);
			// Access the workbook
			HSSFWorkbook wb = new HSSFWorkbook(xlDoc);
			// Access the sheet
			HSSFSheet sheet = wb.getSheet(sheetName);

			int rowCount = sheet.getLastRowNum()+1;
			int columnCount = sheet.getRow(0).getLastCellNum();

			System.out.println("Row Count: " + rowCount);
			System.out.println("Column Count: " + columnCount);

			String[][] readData = new String[rowCount][columnCount];
			for (int i = 0; i <rowCount; i++) {
				for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
					readData[i][j] = sheet.getRow(i).getCell(j).getStringCellValue();
					//System.out.println(readData[i][j]);
				}
			}
			return readData;

		}

	@BeforeMethod
		public static void setupChromeDriver() throws InterruptedException {

			System.setProperty("webdriver.chrome.driver", "C:\\Java_Anitha\\chromedriver.exe\\");
			driver = new ChromeDriver();
			System.out.println("Chrome browser launched");
			driver.manage().window().maximize();

			System.out.println("The URL is paded to the browser");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			driver.get("https://login.salesforce.com/");
			Thread.sleep(2000);
		}

		 @AfterTest
		public static void Report_Close() {
			reports.endTest(logger);
			reports.flush();
		}
		 
        @AfterMethod
		public static void TearDown() throws InterruptedException {
			Thread.sleep(3000);
			driver.quit();

		}

		public static void enter_Data_Textbox(WebElement textbox, String inputData, String textbox_name) {
			if (textbox.isDisplayed() == true) {
				if (textbox.isEnabled() == true) {
					textbox.sendKeys(inputData);
					//System.out.println(textbox.getAttribute("value"));
					//System.out.println(inputData);
					if (textbox.getAttribute("value").equals(inputData)) {
						logger.log(LogStatus.PASS, "" + inputData + " was entered in " + textbox_name + " textbox");
						System.out.println();
					} else {
						logger.log(LogStatus.FAIL, " " + inputData + " was not entered in " + textbox_name + " textbox");
						System.out.println("" + inputData + " was entered in " + textbox_name + " textbox ");
					}
				} else {
					logger.log(LogStatus.FAIL, textbox_name + " button was not enabled");
					System.out.println(textbox_name + " textbox was not enabled");
				}
			} else {
				logger.log(LogStatus.FAIL, textbox_name + " button was not displayed");
				System.out.println(textbox_name + " textbox was not displayed");
			}
		}
		//Reusuable Method for Login
		public static void valid_Login(String User_Name,String Password) {
			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
			
			String username_Data = User_Name;
			
			enter_Data_Textbox(username, username_Data, "User Name");
			logger.log(LogStatus.PASS, "User Name is Entered");
			
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			String password_Data = Password;
			
			enter_Data_Textbox(password, password_Data, "Password");
			logger.log(LogStatus.PASS, "Password is Entered");
			
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			button_Click(loginButton, "Log In");
			logger.log(LogStatus.PASS, "LogIn Button Successful");
			
		}
		public static void enter_Message(WebElement textbox, String inputData, String textbox_name) {
			if (textbox.isDisplayed() == true) {
				if (textbox.isEnabled() == true) {
					textbox.sendKeys(inputData);}
					else {
						logger.log(LogStatus.FAIL, textbox_name + " button was not enabled");
						System.out.println(textbox_name + " textbox was not enabled");
					}
				} else {
					logger.log(LogStatus.FAIL, textbox_name + " button was not displayed");
					System.out.println(textbox_name + " textbox was not displayed");
				}
			
		}

		public static void button_Click(WebElement button, String button_Name) {
			if (button.isDisplayed() == true) {
				if (button.isEnabled() == true) {
					button.click();
					logger.log(LogStatus.PASS, "" + button_Name + " is clicked");
					System.out.println(button_Name + " is clicked");
				}

				else {
					logger.log(LogStatus.FAIL, "" + button_Name + " button was not enabled");
					// System.out.println(textbox_name + " textbox was not enabled");
				}
			}

			else {
				logger.log(LogStatus.FAIL, "" + button_Name + " button was not displayed");
				// System.out.println(textbox_name + " textbox was not displayed");
			}

		}

		public static void validate_Message(String errorMessage, String actualErrorMessage) {
			if (errorMessage.equals(actualErrorMessage)) {
				logger.log(LogStatus.PASS, "Correct Message displayed on Screen");
				
			} else {
				logger.log(LogStatus.FAIL, "Correct Message is not displayed ");
				}
		}
		public static void menuDropdown(List<WebElement> dropdown, String[] items) {
			System.out.println("length:"+items.length);
			System.out.println("Drop Length:"+dropdown.size());
				for (WebElement we : dropdown) {
				System.out.println(we);
						for (int i = 0; i < items.length; i++) {
							System.out.println(we.toString()+" compared with "+items[i]);
							if (we.toString().equals(items[i])) {
								System.out.println(items[i]+" is present");
							} 
							else {
								System.out.println(items[i] + " is not present");
							}
						}
				}
				}
//@Test(priority=1)
		public static void TC_01_loginErrorMessage() throws Exception {
			Reporting();
			logger = reports.startTest("TC_01_loginErrorMessage");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_01_loginErrorMessage.xls", "Sheet1");
			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
			String username_Data = data[1][2];
			enter_Data_Textbox(username, username_Data, "User Name");
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			password.clear();
			logger.log(LogStatus.PASS, "The Password field was cleared");

			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			// Call a method to click a button
			button_Click(loginButton, "Log In");

			WebElement errorMessage = driver.findElement(By.xpath("//div[@id='error']"));
//			System.out.println(errorMessage);
			String actualErrorMessage = errorMessage.getText();
			Thread.sleep(2000);
			String expectedErrorMessage = "Please enter your password.";
			validate_Message(expectedErrorMessage, actualErrorMessage);
			Report_Close();
			TearDown();
		}
//============================================================================================================		
       // @Test(priority=2)
		public static void TC_02_loginToSFDC() throws Exception {
			Reporting();
			logger = reports.startTest("TC_02_loginToSFDC");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_02_loginToSFDC.xls", "Sheet1");
			
					valid_Login(data[1][2],data[1][3]);
			
		}
  //===============================================================================================================      
//@Test(priority=3)
		public static void TC_03_rememberMeCheckBox() throws Exception {
			
			logger = reports.startTest("TC_03_rememberMeCheckBox");
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_03_rememberMeCheckBox.xls", "Sheet1");
			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
			String username_Data = data[1][2];
			enter_Data_Textbox(username, username_Data, "User Name");
			logger.log(LogStatus.PASS, "User Name is Entered");
			System.out.println("Username Entered in username field");
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			String password_Data = data[1][3];
			enter_Data_Textbox(password, password_Data, "Password");
			logger.log(LogStatus.PASS, "Password is Entered");
			System.out.println("Password passed");
			WebElement rememberMe = driver.findElement(By.xpath("//input[@type='checkbox']"));
			rememberMe.click();
			System.out.println("Check the Remeber Me check box");
			Thread.sleep(2000);
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			button_Click(loginButton, "Log In");
			logger.log(LogStatus.PASS, "LogIn Button is clicked");
			System.out.println("Login sucessful");

			WebElement userMenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
			button_Click(userMenu, "User Menu");
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "User Profile is clicked");
			System.out.println("Clicked on User Profile");
			WebElement logoutButton = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
			button_Click(logoutButton, "Log Out");
			logger.log(LogStatus.PASS, "User Logged Out");
			System.out.println("The User is logged out");
			WebElement verifyUserName = driver.findElement(By.xpath("//span[@id='idcard-identity']"));

			String actualUserName = verifyUserName.getText();
			String expectedUserName = "anitham2u@gmail.com";
			validate_Message(expectedUserName, actualUserName);

		}
		//====================================================================================================
//@Test(priority=4)
		public static void TC_4A_forgotPassword() throws Exception {
			
			logger = reports.startTest("TC_4A_forgotPassword");
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_4A_forgotPassword.xls", "Sheet1");
			WebElement forgotPwd = driver.findElement(By.xpath("//a[@id='forgot_password_link']"));
			button_Click(forgotPwd, "Forgot Password");
			System.out.println("Clicked on the forgot password link");
			logger.log(LogStatus.PASS, "Clicked on the forgot password link");
			WebElement provideUsername = driver.findElement(By.xpath("//input[@id='un']"));
			// provideUsername.sendKeys("anitham2u@gmail.com");
			String provideUsername_Data = data[1][2];
			enter_Data_Textbox(provideUsername, provideUsername_Data, "User Name");
			System.out.println("Provided Username");
			logger.log(LogStatus.PASS, "Provided Username for forgot Password");
			WebElement clickContinue = driver.findElement(By.xpath("//input[@id='continue']"));
			button_Click(clickContinue, "Continue Button");
			logger.log(LogStatus.PASS, "Clicked on Continue");
			System.out.println("Clicked on Continue");
			// *[@id="forgotPassForm"]/div/p[1]
			WebElement forgotPwdConfm = driver.findElement(By.xpath("//*[@id=\"forgotPassForm\"]/div/p[1]"));
			String forgotpwdText = forgotPwdConfm.getText();
			String actualText = "We�ve sent you an email with a link to finish resetting your password.";
			validate_Message(forgotpwdText, actualText);
		}
		//***********************************************************************************************************
//@Test(priority=5)
		public static void TC_4B_validateLoginErrorMessage() throws Exception {
			
			logger = reports.startTest("TC_4B_validateLoginErrorMessage");
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_4B_ForgotPassword.xls", "Sheet1");
			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
			String username_Data = data[1][2];
			
			enter_Data_Textbox(username, username_Data, "User Name");
			logger.log(LogStatus.PASS, "User Name is Entered");
			
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			String password_Data = data[1][3];
			
			enter_Data_Textbox(password, password_Data, "Password");
			logger.log(LogStatus.PASS, "Password is Entered");
			
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			button_Click(loginButton, "Log In");
			logger.log(LogStatus.PASS, "LogIn Button is clicked");
								
			WebElement errorMsg = driver.findElement(By.xpath("//div[@id='error']"));
			String printErrorMsg = errorMsg.getText();
			System.out.println(printErrorMsg);
			String actualMsg = "Your login attempt has failed. The username or password may be incorrect,"
					+ " or your location or login time may be restricted. Please contact the administrator at your company for help'";
			validate_Message(printErrorMsg, actualMsg);
		}
		//*********************************************************************************************************
//@Test(priority=5)
		public static void TC_05_SelectUserMenu() throws Exception {
			
			logger = reports.startTest("TC_05_SelectUserMenu");
	       
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_05_SelectUserMenu.xls", "Sheet1");
			valid_Login(data[1][2],data[1][3]);
						
			WebElement userMenu = driver.findElement(By.id("userNavButton"));
			button_Click(userMenu, "User Menu");
			
			//String[] list=data[1][4];
			
			
			String[] list = { "My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience",
					"Logout" };

			List<WebElement> menu = driver.findElements(By.xpath("div[@id='userNav-menuItems']/a"));
//			for(int i=0;i<menu.size();i++) {
//				String dropdown[]=
//			}
			menuDropdown(menu,list);
		}

	//*****************************************************************************************************************	
	
		//@Test(priority=6)
		public static void TC_06_MyProfile() throws Exception {
			
			logger = reports.startTest("TC_06_MyProfile");
			
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_06_MyProfile.xls", "Sheet1");
			
			valid_Login(data[1][2],data[1][3]);
			
						
			WebElement userMenu = driver.findElement(By.id("userNavButton"));
			button_Click(userMenu, "User Menu");
			
			
		WebElement myProfile = driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
		button_Click(myProfile,"My Profile");
		Thread.sleep(3000);
		//TC_O6_Step 4:
		//==========================================
		WebElement editProfile = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//img"));
		
		button_Click(editProfile,"Edit Profile");
		logger.log(LogStatus.INFO, "Edit Profile is clicked");
		
		//Edit Profile
		
		WebElement popUpWindow = driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
		driver.switchTo().frame(popUpWindow);
		logger.log(LogStatus.INFO,"About tab is clicked");
		WebElement aboutTab = driver.findElement(By.xpath("//li[@id='aboutTab']"));
			System.out.println("About Tab is Displayed: "+aboutTab.isDisplayed());
		aboutTab.click();
		 WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));
		 lastName.clear();
		String lname_data=data[1][5];
		enter_Data_Textbox(lastName, lname_data, "lastName");

		String actualLastName=lastName.getText();
		WebElement saveAll = driver.findElement(By.xpath("//input[@type='button']"));
		button_Click(saveAll, "Save All");
		
		logger.log(LogStatus.PASS,"The Last Name is Updated and saved");
//		//================================================================
//		//Havn't done validation for last name
//		//=====================================================================
	WebElement postLink = driver.findElement(By.xpath("//*[@id=\'publisherAttachTextPost\']/span[1]"));
	button_Click(postLink, "Post Link");

	logger.log(LogStatus.INFO,"Clicked on the postLink tab");
	Thread.sleep(5000);
	WebElement postFrame = driver.findElement(By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']"));
	driver.switchTo().frame(postFrame);
	WebElement inputPost = driver.findElement(By.xpath("/html/body"));
	button_Click(inputPost, "Input Post ");
	WebElement input = driver.findElement(By.xpath("/html[1]/body[1]"));
	logger.log(LogStatus.INFO,"Sending message to post");

	String inputPost_data=data[2][5];
	enter_Message(input, inputPost_data, "input Post");

	logger.log(LogStatus.INFO,"Input is entered in Post tab entered");
	Thread.sleep(3000);
	driver.switchTo().parentFrame();
	WebElement shareButton = driver.findElement(By.xpath("//*[@id='publishersharebutton']"));
	//shareButton.click();
	button_Click(shareButton, "Share Button ");
	logger.log(LogStatus.PASS,"The Post is shared..");
	//String actualpostText=postLink.getText();
	//WebElement diplayedPost = driver.findElement(By.xpath("//p[contains(text(),'My First Post')]"));
	//String diplayedPostText=diplayedPost.getText();
	//if(actualpostText.equals(diplayedPostText)) {
//		System.out.println("The Entered Text is diplayed");}
//		else { System.out.println("The Entered Text is not Displayed");}
//	//===========================================================================
	WebElement fileLink = driver.findElement(By.xpath("//span[contains(@class,'publisherattachtext')][contains(text(),'File')]"));
	button_Click(fileLink, "File Link ");
	////div[@id='chatterFileStageOne']
	logger.log(LogStatus.INFO,"Clicked on file Link");

	Thread.sleep(3000); 
	WebElement uploadFile = driver.findElement(By.cssSelector("#chatterUploadFileAction"));
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	button_Click(uploadFile, "Upload File ");

	logger.log(LogStatus.INFO,"Upload file tab is clicked");
	WebElement chooseFile = driver.findElement(By.xpath("//input[@id='chatterFile']"));
	Thread.sleep(6000); 

	enter_Message(chooseFile, "C:\\Users\\mapkhome\\Desktop\\xpath.txt", "Choose File");
	//chooseFile.click();
	//chooseFile.sendKeys("C:\\Users\\mapkhome\\Desktop\\links.txt");
	Thread.sleep(6000);

	WebElement share = driver.findElement(By.xpath("//input[@id='publishersharebutton']"));
	button_Click(share, "Share Button ");
	Actions action=new Actions(driver);
	action.moveToElement(driver.findElement(By.id("displayBadge"))).build().perform();
	WebElement updatePhoto = driver.findElement(By.xpath("//a[@id='uploadLink']"));
	button_Click(updatePhoto, "Update Photo ");

	//updatePhoto.click();
	System.out.println("The update photo is clicked");
	WebElement uploadFrame = driver.findElement(By.xpath("//iframe[@id='uploadPhotoContentId']"));
	driver.switchTo().frame(uploadFrame);
	System.out.println("The Update pic Frame is displayed");

	WebElement uploadProfilePhoto = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadInputFile']"));
	//uploadProfilePhoto.click();
	//button_Click(uploadProfilePhoto, "Update Profile Photo ");
	Thread.sleep(3000);
	enter_Message(uploadProfilePhoto, "C:\\Users\\mapkhome\\Desktop\\SaleForce\\pup.jpg", "Update Profile Photo");
	//uploadProfilePhoto.click();
	System.out.println("Upload pic link is clicked");
	WebElement savePhoto = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:save']"));
	button_Click(savePhoto, "Save Photo");
		}
//============================================================================================
		//@Test(priority=7)
		public static void TC_07_MySettings() throws Exception {
		
			logger = reports.startTest("TC_07_MySettings");
			
			String data[][] = getDataInput("C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_07_MySettings.xls","Sheet1");
			valid_Login(data[1][2],data[1][3]);
			
			WebElement userMenu = driver.findElement(By.id("userNavButton"));
			button_Click(userMenu, "User Menu");
			
			
			WebElement mySettings = driver.findElement(By.xpath("//a[@class='menuButtonMenuLink']"));
			button_Click(mySettings, "My Settings");
			logger.log(LogStatus.INFO,"Clicked on My Settings");
			
			WebElement personal = driver.findElement(By.xpath("//span[@id='PersonalInfo_font']"));
			button_Click(personal, "Personal");
			logger.log(LogStatus.INFO,"The Personal was clicked");

			WebElement loginHistory = driver.findElement(By.xpath("//a[@id='LoginHistory_font']"));
			button_Click(loginHistory, "Login History");
		    logger.log(LogStatus.INFO,"Login History is clicked");
		
		 WebElement downloadFile = driver.findElement(By.xpath("//*[@id=\"RelatedUserLoginHistoryList_body\"]/div/a"));
		 button_Click(downloadFile, "File Download");
		
//		//downloadValidate=
		//========================================================================
//		Display and Layout Link
		driver.switchTo().defaultContent(); 
		Thread.sleep(6000);
		Actions mousehoover=new Actions(driver);
		
		WebElement displayAndLayout = driver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']"));
		mousehoover.moveToElement(displayAndLayout).build().perform();
		button_Click(displayAndLayout, "Display and Layout");
		logger.log(LogStatus.INFO,"Clicked on Display and Layout Link");
		Thread.sleep(6000);
		WebElement customizeMyTab = driver.findElement(By.xpath("//span[@id='CustomizeTabs_font']"));
		button_Click(customizeMyTab,"Customize My Tab");
		logger.log(LogStatus.INFO,"Clicked on CustomizeMy tab");
		
		WebElement customApp = driver.findElement(By.xpath("//select[@id='p4']"));
		Select dropdown=new Select(customApp);
		dropdown.selectByVisibleText("Salesforce Chatter");
		logger.log(LogStatus.PASS,"SalesForce Chatter is selected");
		
		WebElement selectedTabs = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		if(selectedTabs.getText().contains("Reports")==true) {
			Select selectedTabDropdown=new Select(selectedTabs);
			selectedTabDropdown.selectByVisibleText("Reports");
			WebElement moveToLeftTab = driver.findElement(By.xpath("//img[@class='leftArrowIcon']"));
			button_Click(moveToLeftTab,"Move to Left");
			logger.log(LogStatus.INFO,"Report tab is moved Left");
		}
		WebElement availableTabs = driver.findElement(By.xpath("//select[@id='duel_select_0']"));
		if(availableTabs.getText().contains("Reports")==true) {
			Select availableTabDropdown=new Select(availableTabs);
			availableTabDropdown.selectByVisibleText("Reports");
			WebElement moveToRight = driver.findElement(By.xpath("//img[@class='rightArrowIcon']"));
			button_Click(moveToRight,"Move To Right");
			logger.log(LogStatus.INFO,"Report tab is moved Right");
			
			WebElement saveReportTab = driver.findElement(By.xpath("//input[@name='save']"));
			button_Click(saveReportTab,"Reports Tab Saved");
			logger.log(LogStatus.PASS,"The tab is saved");
		
		}
		Thread.sleep(3000);
//		if(selectedTabs.getText().contains("Reports")==true) {
//			System.out.println("Reports tab is added to the Selected Tab : Passed");
//		}else System.out.println("Report tab is not added :Failed");
		
		 WebElement salesForcePageTab = driver.findElement(By.cssSelector("#tabBar"));
		 if(salesForcePageTab.getText().contains("Reports")==true) {
				System.out.println("Reports tab is added to the HomePage Tab : Passed");
			}else System.out.println("Report tab is not HomePage Tab :Failed");
//========================================================================
			
		//Email Link 
		WebElement email = driver.findElement(By.xpath("//*[@id=\"EmailSetup\"]/a"));
		mousehoover.moveToElement(email).build().perform();
		button_Click(email,"Email");
		WebElement emailSettings = driver.findElement(By.xpath("//span[@id='EmailSettings_font']"));
		button_Click(emailSettings,"Email Settings");
		
		WebElement emailName = driver.findElement(By.cssSelector("#sender_name"));
		emailName.clear();
		String emailName_Data = data[1][4];
		
		enter_Data_Textbox(emailName, emailName_Data, "Email Name");
		logger.log(LogStatus.PASS, "User Name is Entered");
//		emailName.sendKeys("Anitha");
//		System.out.println("The Name for email is send");
		WebElement emailAddress = driver.findElement(By.cssSelector("#sender_email"));
		emailAddress.clear();
		String emailAddress_Data = data[2][4];
		enter_Data_Textbox(emailAddress, emailAddress_Data, "Email Address");
//		emailAddress.sendKeys("anitham2u@gmail.com");
//		System.out.println("The email address  is set ");
		WebElement autoBcc = driver.findElement(By.xpath("//input[@id='auto_bcc1']"));
		button_Click(autoBcc,"Auto BCC");
		
		WebElement saveEmailSettings = driver.findElement(By.xpath("//input[@name='save']"));
		button_Click(saveEmailSettings,"Email Settings Saved");
		
		WebElement savedEmailSettings = driver.findElement(By.cssSelector("#meSaveCompleteMessage"));
		String VerifyEmailSettings=savedEmailSettings.getText().toString();
		String expectedEmailSettings="Your settings have been successfully saved.";
		if(expectedEmailSettings.equals(expectedEmailSettings)) {
			System.out.println("The Email has been updated:Pass");
		}else System.out.println("The Email Settings is not updated: Fail");

		//Have to check the MY Settings Page Display
				

//==============================================================================================
		//Calenders and Remainders
		WebElement calender = driver.findElement(By.xpath("//span[@id='CalendarAndReminders_font']"));
		mousehoover.moveToElement(calender).build().perform();
		button_Click(calender,"Calender");
		logger.log(LogStatus.INFO,"The Calender And Reminder tab is clicked");
		WebElement activityRemainder = driver.findElement(By.xpath("//span[@id='Reminders_font']"));
		button_Click(activityRemainder,"Activity Remainder");
		logger.log(LogStatus.INFO,"Activity Remainder is clicked");
		WebElement testRemainder = driver.findElement(By.xpath("//*[@id=\"testbtn\"]"));
		button_Click(testRemainder,"Test Remainder");
		logger.log(LogStatus.PASS,"Test Remainder is clicked");
		//Alert alert = driver.switchTo().alert();
		//alert.dismiss();
		String mainWindow=driver.getWindowHandle();
		for(String handle:driver.getWindowHandles()) {
			System.out.println("No of handles: "+handle);
			
				driver.switchTo().window(handle);
			}
		Thread.sleep(3000);
		//driver.close();
		//driver.switchTo().window(handle).close();

		driver.switchTo().window(mainWindow);
		driver.close();

		logger.log(LogStatus.PASS,"The PopUp window is closed");
	}
//========================================================================
		//@Test(priority=8)
		public static void TC_08_DeveloperConsole() throws InterruptedException, Exception {
			
			logger = reports.startTest("TC_08_DeveloperConsole");
			
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_08_DeveloperConsole.xls", "Sheet1");
			
			valid_Login(data[1][2],data[1][3]);
			
			WebElement userMenu = driver.findElement(By.id("userNavButton"));
			button_Click(userMenu, "User Menu");
			
			
			
			WebElement developerConsole = driver.findElement(By.xpath("//a[@class='debugLogLink menuButtonMenuLink']"));
			button_Click(developerConsole,"Developer Console");
			logger.log(LogStatus.PASS, "Developer Console is clicked");
			String primaryWindow=driver.getWindowHandle();
			String newWindow=driver.getWindowHandle();
			driver.switchTo().window(newWindow);
			Thread.sleep(3000);
			driver.switchTo().window(primaryWindow);

			driver.close();
			logger.log(LogStatus.PASS, "The primary Window is displayed ");
		}
//======================================================================================
		//@Test(priority=9)
		public static void TC_09_LogoutFromUsermenu() throws Exception {
			
			logger = reports.startTest("TC_09_LogoutFromUsermenu");
			
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_09_LogoutFromUsermenu.xls", "Sheet1");
			
			valid_Login(data[1][2],data[1][3]);
			
			WebElement userMenu = driver.findElement(By.id("userNavButton"));
			button_Click(userMenu, "User Menu");
			

			String[] list = { "My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience",
					"Logout" };

			List<WebElement> menu = driver.findElements(By.xpath("div[@id='userNav-menuItems']/a"));
			
			//menuDropdown(menu,list);
			
	
			for(int i=0;i<menu.size();i++) {
				String option=menu.get(i).toString();
				if(option.equals(list[i])) {
					System.out.println(list[i]+" is present");
				} else {
					System.out.println(list[i] +" is not present");
					}
				WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
				button_Click(logout,"Logout");
				logger.log(LogStatus.PASS,"The Login of sales Force Page is displayed");
		}
	}
	//===========================================================================================================
		//@Test(priority=10)
		public static void TC_10_CreateAccount() throws Exception {
			
			logger = reports.startTest("TC_10_CreateAccount");
			
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_10_CreateAccount.xls", "Sheet1");
			
			valid_Login(data[1][2],data[1][3]);
			
			WebElement accountClick = driver
					.findElement(By.xpath("//li[@id='Account_Tab']//a[contains(text(),'Accounts')]"));
			button_Click(accountClick,"Account Click");
			logger.log(LogStatus.PASS,"The Accounts Page is displayed");

			WebElement accountWithUserName = driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2"));
			String displayName = accountWithUserName.getText().toString();
			String expecteduserName = "Anitha Maru";
			validate_Message(expecteduserName, displayName);
//			if (displayName.equals(userName)) {
//				System.out.println("Account Page is displayed with correct Username:  PASS");
//			} else
//				System.out.println("Account Page is displayed with incorrect username: FAIL");
			WebElement closePopup = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			button_Click(closePopup,"Close Popup");
			WebElement New = driver.findElement(By.xpath("//input[@name='new']"));
			button_Click(New,"New");

			logger.log(LogStatus.PASS,"New Account page is displayed");
			WebElement accountName = driver.findElement(By.xpath("//input[@id='acc2']"));
			
			String accountName_Data = data[1][4];
			enter_Data_Textbox(accountName, accountName_Data, "Account Name");
			WebElement accountSave = driver.findElement(By.xpath("//td[@id='bottomButtonRow']//input[@name='save']"));
			button_Click(accountSave,"Account Save");
			WebElement displayAcctName=driver.findElement(By.className("topName"));
			String expectName="Hackathon";
			String displayAccontName=displayAcctName.getText().toString();
			validate_Message(expectName, displayAccontName);
			logger.log(LogStatus.PASS,"New Account Page is diaplayed with account details");
		}
	
//==================================================================================================================
		//@Test(priority=11)
		public static void TC_11_Create_New_View() throws Exception {
			
			logger = reports.startTest("TC_11_Create_New_View");
			
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_11_Create_New_View.xls", "Sheet1");
			
			valid_Login(data[1][2],data[1][3]);
			
			WebElement accountClick = driver
					.findElement(By.xpath("//li[@id='Account_Tab']//a[contains(text(),'Accounts')]"));
			button_Click(accountClick,"Account Click");
			logger.log(LogStatus.PASS,"The Accounts Page is displayed");

			WebElement accountWithUserName = driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[1]/div[1]/div[1]/h2"));
			String displayName = accountWithUserName.getText().toString();
			String userName1 = "Anitha Maru";

			validate_Message(userName1, displayName);
		WebElement closePopup = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			button_Click(closePopup,"Close Popup");
			logger.log(LogStatus.INFO,"The Popup window is closed");
			
			WebElement createNewView = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
			button_Click(createNewView,"Create New View");
			logger.log(LogStatus.INFO,"Clicked on create New view");
			
			WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
			String viewName_data=data[2][4];
			enter_Data_Textbox(viewName, viewName_data, "View Name");
			
			WebElement uniqName = driver.findElement(By.xpath("//input[@id='devname']"));
			String uniqName_data=data[3][4];
			enter_Data_Textbox(uniqName, uniqName_data, "Account Name");
			
			logger.log(LogStatus.PASS,"View Name and Unque name is created");
			WebElement saveNewView = driver.findElement(By.xpath("//div[@class='pbBottomButtons']//input[@name='save']"));
			button_Click(saveNewView,"Save Newview");
			logger.log(LogStatus.INFO,"The newly added view is saved");
			// ****************Getting Error in Validation Part************************
			//String verifyViewName="Peoples";
//			WebElement newlyAddedView = driver.findElement(By.xpath("//select[@id='00B3k00000AZ0Ec_listSelect']"));
//			String newDisplay=newlyAddedView.getText().toString();
//			validate_Message(viewName_data, newDisplay);

	}
		//****************************************************************************************************
		@Test(priority=12)
		public static void TC_12_EditView() throws InterruptedException, IOException {
logger = reports.startTest("TC_12_EditView");
			
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_12_EditView.xls", "Sheet1");
			
			valid_Login(data[1][2],data[1][3]);
			WebElement accountClick = driver
					.findElement(By.xpath("//li[@id='Account_Tab']//a[contains(text(),'Accounts')]"));
			accountClick.click();
			System.out.println("The Accounts Page is displayed");

			WebElement closePopup = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			closePopup.click();

			WebElement viewTab = driver.findElement(By.xpath("//select[@id='fcf']"));
			Select dropdown = new Select(viewTab);
			dropdown.selectByVisibleText("CrazyArt");

			System.out.println("The Account to edit is selected");
			Thread.sleep(3000);
			WebElement editViewAccount = driver.findElement(By.linkText("Edit"));
			editViewAccount.click();
			System.out.println("The edit page is dispalyed");
			WebElement editViewName = driver.findElement(By.xpath("//input[@id='fname']"));
			editViewName.clear();
			editViewName.sendKeys("CrazyArt");
			WebElement field = driver.findElement(By.xpath("//select[@id='fcol1']"));
			Select dropdown1 = new Select(field);
			dropdown1.selectByVisibleText("Account Name");
			System.out.println("Account Name is selected");
			WebElement operator = driver.findElement(By.xpath("//*[@id=\"fop1\"]"));
			Select dropdown2 = new Select(operator);
			dropdown2.selectByVisibleText("contains");
			System.out.println("Operator is selected");
			WebElement value = driver.findElement(By.xpath("//input[@id='fval1']"));
			value.clear();
			value.sendKeys("a");
			value.submit();
			System.out.println("The value is given a");
Thread.sleep(4000);
			WebElement availableField = driver.findElement(By.xpath("//select[@id='colselector_select_0']//option[contains(text(),'Last Activity')]"));
			availableField.click();
			WebElement addButton = driver.findElement(By.xpath("//a[@id='colselector_select_0_right']//img[@class='rightArrowIcon']"));
			addButton.click();
			WebElement saveButton = driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[3]/table/tbody/tr/td[2]/input[1]"));
			saveButton.click();
			System.out.println("View Page is displayed");
			System.out.println("The View page is updated and Account name with'a' is displayed");
		
	}
		
		// ****************************************************************************************************
		//@Test(priority = 13)

		public static void TC_13_MergeAccounts() throws InterruptedException, Exception {
			logger = reports.startTest("TC_13_MergeAccounts");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_13_MergeAccounts.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement accountClick = driver
					.findElement(By.xpath("//li[@id='Account_Tab']//a[contains(text(),'Accounts')]"));
			accountClick.click();
			System.out.println("The Accounts Page is displayed");

			WebElement closePopup = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			closePopup.click();
			Thread.sleep(4000);
			WebElement merge = driver.findElement(By.linkText("Merge Accounts"));
			merge.click();
			WebElement acctsName = driver.findElement(By.xpath("//input[@id='srch']"));
			acctsName.sendKeys("Ha");
			WebElement findAccounts = driver.findElement(By.xpath("//input[@name='srchbutton']"));
			findAccounts.click();
			WebElement table = driver.findElement(By.xpath("//div[@class='pbBody']"));
			// Reading Table..
			/**
			 * List<WebElement>
			 * rows=driver.findElements(By.xpath("//table[contains(@class,'list')]//tr"));
			 * //"//tr[contains(@class,'dataRow even first')]" for(WebElement row:rows) {
			 * List<WebElement> columns=row.findElements(By.tagName("th"));
			 * if(columns.isEmpty()) { columns=row.findElements(By.tagName("td")); }
			 * for(WebElement column:columns) { System.out.print(row.getText());
			 * System.out.print("|"); } System.out.println(); for(int
			 * rnum=0;rnum<rows.size();rnum++){ List<WebElement>
			 * header=rows.get(rnum).findElements(By.tagName("th"));}
			 **/
			WebElement checkBox1 = driver.findElement(By.xpath("//input[@id='cid0']"));
			checkBox1.click();
			WebElement checkBox2 = driver.findElement(By.xpath("//input[@id='cid1']"));
			checkBox2.click();
			WebElement next = driver.findElement(By.xpath("//div[@class='pbBottomButtons']//input[@name='goNext']"));
			next.click();
			WebElement mergeButton = driver.findElement(By.xpath("//div[@class='pbBottomButtons']//input[@name='save']"));
			mergeButton.click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			String verify = "Hackathon";
			WebElement verifyDisplay = driver
					.findElement(By.xpath("//div[@class='mruItem']//a[contains9@class,'accountMaru']"));
			String display = verifyDisplay.getText();
			System.out.println(display);
		}

		// ****************************************************************************************************
		//@Test(priority = 14)

		public static void TC_14_CreateAccountReport() throws InterruptedException, Exception {
			logger = reports.startTest("TC_14_CreateAccountReport");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_14_CreateAccountReport.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement accountClick = driver
					.findElement(By.xpath("//li[@id='Account_Tab']//a[contains(text(),'Accounts')]"));
			accountClick.click();
			System.out.println("The Accounts Page is displayed");

			WebElement closePopup = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			closePopup.click();
			Thread.sleep(4000);
			WebElement account = driver.findElement(By.xpath("//*[@id='Account_Tab']"));
			account.click();
			logger.log(LogStatus.PASS, "Account button clicked");
			WebElement accountLastActivity = driver
					.findElement(By.xpath("//*[@id='toolsContent']/tbody/tr/td[1]/div/div/div[1]/ul/li[2]/a"));
			accountLastActivity.click();
			logger.log(LogStatus.PASS, "Account last Activity button clicked");
			WebElement fromDate = driver.findElement(By.xpath("//*[@id='ext-comp-1042']"));
			fromDate.clear();
			fromDate.sendKeys("12/5/2019");
			WebElement toDate = driver.findElement(By.xpath("//*[@id='ext-comp-1045']"));
			toDate.clear();
			toDate.sendKeys("12/5/2019");
			logger.log(LogStatus.INFO, "dates selected");
			// WebElement toToday =
			// driver.findElement(By.xpath(prop.getProperty("toToday")));
			// toToday.click();
			WebElement saveReport = driver.findElement(By.xpath("//*[@id='ext-gen49']"));
			saveReport.click();
			logger.log(LogStatus.INFO, "saving the report");
			WebElement reportName = driver.findElement(By.xpath("//*[@id='saveReportDlg_reportNameField']"));
			reportName.sendKeys(data[1][4]);
			WebElement reportUniqueName = driver.findElement(By.xpath("//*[@id='saveReportDlg_DeveloperName']"));
			reportUniqueName.click();
			Thread.sleep(5000);
			WebElement saveRep = driver.findElement(By.xpath("//*[@id=\"dlgSaveAndRun\"]/tbody/tr[2]/td[2]/em"));
			saveRep.click();
			logger.log(LogStatus.PASS, "report run and saved");

		}

		// ***************************************************************************************************
		//@Test(priority = 15)
		public static void TC_15_UserMenuDropDown() throws Exception {
			logger = reports.startTest("TC_15_UserMenuDropDown");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_15_UserMenuDropDown.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement oppurtunities = driver
					.findElement(By.xpath("//li[@id='Opportunity_Tab']//a[contains(text(),'Opportunities')]"));
			oppurtunities.click();
			System.out.println("Oppurtunity tab is clicked");

			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement viewOppurtunity = driver.findElement(By.xpath("//select[@id='fcf']"));
			Select oppDropdown = new Select(viewOppurtunity);
			String[] oppurtunityList = { "All Oppotunities", "Closing Next Month", "Closing This Month", "My Opportunities",
					"New This Week", "Recently Viewed Opportunities", "Won" };
			int len = oppurtunityList.length;
			List<WebElement> menu = oppDropdown.getOptions();
			for (WebElement item : menu) {
				System.out.println("The Dropdown valus are: " + item.getText());
			}
			for (int i = 0; i < len; i++) {
				String option = menu.get(i).toString();
				if ((oppurtunityList[i]).equals(option)) {
					System.out.println(oppurtunityList[i] + " is present");
				} else {
					System.out.println(oppurtunityList[i] + " is not present");
				}

			}
		}
		// ****************************************************8
		//@Test(priority = 16)
		public static void TC_16_CreateNewOpty() throws InterruptedException, Exception {
			logger = reports.startTest("TC_16_CreateNewOpt");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_16_CreateNewOpt.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement oppurtunities = driver
					.findElement(By.xpath("//li[@id='Opportunity_Tab']//a[contains(text(),'Opportunities')]"));
			oppurtunities.click();
			System.out.println("Oppurtunity tab is clicked");

			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement createNew = driver.findElement(By.xpath("//*[@id='createNewButton']"));
			createNew.click();
			WebElement oppurtunity = driver.findElement(By.linkText("Opportunity"));
			oppurtunity.click();
			System.out.println("New Oppurtunity is selected");
			WebElement oppName = driver.findElement(By.xpath("//input[@id='opp3']"));
			oppName.sendKeys("Tester");
			WebElement acctName = driver.findElement(By.xpath("//input[@id='opp4']"));
			acctName.sendKeys("Sele");
			WebElement closeDate = driver.findElement(By.xpath("//input[@id='opp9']"));
			closeDate.click();
			WebElement todaydate = driver.findElement(By.xpath("//a[@class='calToday']"));
			todaydate.click();
			WebElement stage = driver.findElement(By.xpath("//select[@id='opp11']"));
			Select stgDropdown = new Select(stage);
			stgDropdown.selectByIndex(3);
			Thread.sleep(2000);
			WebElement probablity = driver.findElement(By.xpath("//input[@id='opp12']"));
			probablity.sendKeys("");
			WebElement leadSource = driver.findElement(By.xpath("//select[@id='opp6']"));
			Select leadDropdown = new Select(leadSource);
			Thread.sleep(3000);
			leadDropdown.selectByIndex(3);
			Thread.sleep(2000);
			WebElement primarySource = driver.findElement(By.xpath("//input[@id='opp17']"));
			primarySource.click();
			WebElement save = driver.findElement(By.xpath("//input[@name='save']"));
			save.click();
			System.out.println("New Oppurtunity page is displayed with details");
		}

		// ==========================================================================================================
		//@Test(priority = 17)
		public static void TC_17_OppurtunityPipelineReport() throws Exception {
			logger = reports.startTest("TC_17_OppurtunityPipelineReport");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_17_OppurtunityPipelineReport.xls",
					"Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement oppurtunities = driver
					.findElement(By.xpath("//li[@id='Opportunity_Tab']//a[contains(text(),'Opportunities')]"));
			oppurtunities.click();
			System.out.println("Oppurtunity tab is clicked");

			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement oppPipeline = driver.findElement(By.xpath("//a[contains(text(),'Opportunity Pipeline')]"));
			oppPipeline.click();
			System.out.println("Report Page with the Opportunities that are pipelined will be displayed.");
		}

		// =====================================================================================================================
		//@Test(priority = 18)
		public static void TC_18_StuckOppurtunityReport() throws Exception {
			logger = reports.startTest("TC_18_StuckOppurtunityReport");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_18_StuckOppurtunityReport.xls",
					"Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement oppurtunities = driver
					.findElement(By.xpath("//li[@id='Opportunity_Tab']//a[contains(text(),'Opportunities')]"));
			oppurtunities.click();
			System.out.println("Oppurtunity tab is clicked");

			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement stuckOpp = driver.findElement(By.xpath("//a[contains(text(),'Stuck Opportunities')]"));
			stuckOpp.click();
			System.out.println("Report Page with the Opportunities that are Stuck will be displayed.");
		}

		// =======================================================================================================================
		//@Test(priority = 19)
		public static void TC_19_QuaterlySummerReport() throws Exception {
			logger = reports.startTest("TC_19_QuaterlySummerReport");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_19_QuaterlySummerReport.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);

			WebElement oppurtunities = driver
					.findElement(By.xpath("//li[@id='Opportunity_Tab']//a[contains(text(),'Opportunities')]"));
			oppurtunities.click();
			System.out.println("Oppurtunity tab is clicked");

			// Closing the popup
			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement interval = driver.findElement(By.xpath("//select[@id='quarter_q']"));
			Select intervalDropdown = new Select(interval);
			intervalDropdown.selectByVisibleText("Current FQ");

			WebElement include = driver.findElement(By.xpath("//select[@id='open']"));
			Select includeDropdown = new Select(include);
			includeDropdown.selectByIndex(2);

			WebElement runReport = driver.findElement(By.xpath("//table[@class='opportunitySummary']//input[@name='go']"));
			runReport.click();
			System.out.println("Report Page with Oppurtunities will be dispalyed");

		}

		// ==================================================================================================================================
		//@Test(priority = 20)
		public static void TC_20_LeadsTab() throws Exception {
			logger = reports.startTest("TC_20_LeadsTab");

			String data[][] = getDataInput("C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_20_LeadsTab.xls",
					"Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement leads = driver.findElement(By.xpath("//li[@id='Lead_Tab']//a[contains(text(),'Leads')]"));
			leads.click();
			System.out.println("The leads home Page is displayed");

			// Closing the popup
			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement verifyLeadPage = driver.findElement(By.xpath("//h2[contains(text(),'Recent Items')]"));
			if (verifyLeadPage.isDisplayed()) {
				System.out.println("Lead Home Page is loaded");
			}
		}

		// ===========================================================================
		@Test(priority = 21)
		public static void TC_21_LeadsSelectView() throws Exception {
			logger = reports.startTest("TC_21_LeadsSelectView");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_21_LeadsSelectView.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);
			WebElement leads = driver.findElement(By.xpath("//li[@id='Lead_Tab']//a[contains(text(),'Leads')]"));
			leads.click();
			System.out.println("The leads home Page is displayed");

			// Closing the popup
			WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
			popupClose.click();
			System.out.println("Popup closed");

			WebElement verifyLeadPage = driver.findElement(By.xpath("//h2[contains(text(),'Recent Items')]"));
			if (verifyLeadPage.isDisplayed()) {
				System.out.println("Lead Home Page is loaded");
			}
			String[] list = { "All Open Leads", "My Unread Leads", "Recently Viewed", "Leads","Today's Leads"};

	List<WebElement> leadMenu = driver.findElements(By.xpath("//select[@id='fcf']"));

	menuDropdown(leadMenu, list);

		}
		// ===========================================================================
			
		@Test(priority = 22)
		public static void TC_22_defaultView() throws Exception {
			logger = reports.startTest("TC_22_defaultView");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_22_defaultView.xls", "Sheet1");

			valid_Login(data[1][2], data[1][3]);
			
			// Closing the popup
					WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
					popupClose.click();
					System.out.println("Popup closed");
			WebElement leaddd =driver.findElement(By.xpath("//select[@id='fcf']"));		
			Select dd = new Select(leaddd);
			dd.selectByVisibleText("Today's Leads");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			valid_Login(data[1][2], data[1][3]);
			driver.findElement(By.xpath("//a[contains(text(),'Leads')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.name("go")).click();
			Select select = new Select(driver.findElement(By.xpath("//select[@id='is:islv:inlineSchedulerListView:enhancedList_listSelect']")));
			WebElement option = select.getFirstSelectedOption();
			String defaultItem = option.getText();
			System.out.println(defaultItem );
			String expected ="Today's Leads";
			Assert.assertEquals(defaultItem, expected,"Not as expected");
	}

	// ===========================================================================
		
			@Test(priority = 23)
			public static void TC_23_TodaysDeals() throws Exception {
				logger = reports.startTest("TC_23_TodaysDeals");

				String data[][] = getDataInput(
						"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_23_TodaysDeals", "Sheet1");

				valid_Login(data[1][2], data[1][3]);
				
				// Closing the popup
						WebElement popupClose = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
						popupClose.click();
						System.out.println("Popup closed");
						driver.findElement(By.xpath("//a[contains(text(),'Leads')]")).click();
						Thread.sleep(2000);
						WebElement leaddd =driver.findElement(By.xpath("//select[@id='fcf']"));
						Select dd = new Select(leaddd);
						dd.selectByVisibleText("Today's Leads");
						Thread.sleep(2000);
						driver.findElement(By.name("go")).click();
						Select select = new Select(driver.findElement(By.xpath("//select[@id='is:islv:inlineSchedulerListView:enhancedList_listSelect']")));
						WebElement option = select.getFirstSelectedOption();
						String defaultItem = option.getText();
						System.out.println(defaultItem );
						String expected ="Today's Leads";
						Assert.assertEquals(defaultItem, expected,"Not as expected");
						
					}
			// ===========================================================================
			
				@Test(priority = 24)
				public static void TC_24_CheckNewButton() throws Exception {
					logger = reports.startTest("TC_24_CheckNewButton");

					String data[][] = getDataInput(
							"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_24_CheckNewButton", "Sheet1");

					valid_Login(data[1][2], data[1][3]);
					driver.findElement(By.xpath("//a[contains(text(),'Leads')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//input[@name='new']")).click();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					driver.findElement(By.xpath("//input[@id='name_lastlea2']")).sendKeys("ABCD");
					driver.findElement(By.xpath("//input[@id='lea3']")).sendKeys("ABCD");
					driver.findElement(By.name("save")).click();
					String header =driver.findElement(By.xpath("//h2[@class='topName']")) .getText();
					String expected ="ABCD";
					Assert.assertEquals(header, expected,"Not as expected");
				}
				// ===========================================================================
				
					@Test(priority = 25)
					public static void TC_25_CreateNewContact() throws Exception {
						logger = reports.startTest("TC_25_CreateNewContact");

						String data[][] = getDataInput(
								"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_25_CreateNewContact", "Sheet1");

						valid_Login(data[1][2], data[1][3]);
						driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();
						driver.findElement(By.xpath("//input[@name='new']")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//input[@id='name_lastcon2']")).sendKeys("Maru");
						driver.findElement(By.xpath("//input[@id='con4']")).sendKeys("Acme(Sample)");
						driver.findElement(By.name("save")).click();
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						String header =driver.findElement(By.xpath("//h2[@class='topName']")) .getText();
						String expected ="Maru";
						Assert.assertEquals(header, expected,"Not as expected");
					}
					// ===========================================================================
					
					@Test(priority = 26)
					public static void TC_26_CreateNewContactPage() throws Exception {
						logger = reports.startTest("TC_26_CreateNewContactPage");

						String data[][] = getDataInput(
								"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_26_CreateNewContactPage", "Sheet1");

						valid_Login(data[1][2], data[1][3]);
						driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();
						driver.findElement(By.xpath("//a[contains(text(),'Create New View')]")).click();
						driver.findElement(By.xpath("//input[@id='fname']")).sendKeys("QA Arch");
						driver.findElement(By.xpath("//input[@id='devname']")).click();
						driver.findElement(By.name("save")).click();
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						Select select = new Select(driver.findElement(By.xpath("//select[@id='is:islv:inlineSchedulerListView:enhancedList_listSelect']")));
						WebElement option = select.getFirstSelectedOption();
						String defaultItem = option.getText();
						System.out.println(defaultItem );
						String expected ="QA Arch";
						Assert.assertEquals(defaultItem, expected,"Not as expected");
						
	}
					@Test(priority = 27)
					public static void TC_27_CheckRecentContact() throws Exception {
						logger = reports.startTest("TC_27_CheckRecentContact");

						String data[][] = getDataInput(
								"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_27_CheckRecentContact", "Sheet1");

						valid_Login(data[1][2], data[1][3]);
						driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();
						Thread.sleep(2000);
						WebElement dd=driver.findElement(By.xpath("//select[@id='hotlist_mode']"));
						Select select = new Select(dd);
						select.selectByVisibleText("Recently Created");
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						String contact =driver.findElement(By.xpath("//a[contains(text(),'Maru')]")) .getText();
						String expected ="Maru";
						Assert.assertEquals(contact, expected,"Not as expected");	
						
	}
					@Test(priority = 28)
					public static void TC_28_CheckMyContact() throws Exception {
						logger = reports.startTest("TC_28_CheckMyContact");

						String data[][] = getDataInput(
								"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_28_CheckMyContact", "Sheet1");
						valid_Login(data[1][2], data[1][3]);
						driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();
						Thread.sleep(2000);
						WebElement dd=driver.findElement(By.xpath("//select[@id='fcf']"));
						Select select = new Select(dd);
						select.selectByVisibleText("My Contacts");
								

					
	}
					@Test(priority = 29)
					public static void TC_29_viewContact() throws Exception {
						logger = reports.startTest("TC_29_viewContact");

						String data[][] = getDataInput(
								"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_29_viewContact", "Sheet1");

						valid_Login(data[1][2], data[1][3]);
						driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();
						Thread.sleep(2000);
						driver.findElement(By.xpath("//a[contains(text(),'Maru')]")).click();
						String contact =driver.findElement(By.xpath("//h2[@class='topName']")) .getText();
						String expected ="Maru";
						Assert.assertEquals(contact, expected,"Not as expected");	
	}
					@Test(priority = 30)
					public static void TC_30_checkErrorMessage() throws Exception {
						logger = reports.startTest("TC_30_checkErrorMessage");

						String data[][] = getDataInput(
								"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_30_checkErrorMessage", "Sheet1");

						valid_Login(data[1][2], data[1][3]);
						
						driver.findElement(By.xpath("//a[contains(text(),'Contacts')]")).click();
						driver.findElement(By.xpath("//a[contains(text(),'Create New View')]")).click();
						
						driver.findElement(By.xpath("//input[@id='devname']")).sendKeys("EFGH");
						driver.findElement(By.name("save")).click();
						String error =driver.findElement(By.xpath("//div[contains(text(),'You must enter a value')]")) .getText();
						String expected ="Error: You must enter a value";
						Assert.assertEquals(error, expected,"Not as expected");	
					}
					
						
		
	}