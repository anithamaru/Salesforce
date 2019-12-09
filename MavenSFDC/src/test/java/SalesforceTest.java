

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

			System.setProperty("webdriver.chrome.driver", "C:\\Java_Anitha\\chromedriver_win32\\chromedriver.exe\\");
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
						logger.log(LogStatus.PASS, "" + inputData + "was entered in" + textbox_name + "textbox");
						System.out.println();
					} else {
						logger.log(LogStatus.FAIL, " " + inputData + "was not entered in" + textbox_name + "textbox");
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
				System.out.println("PASS:Correct Message displayed on Screen");
			} else {
				logger.log(LogStatus.FAIL, "Correct Message is not displayed ");
				System.out.println("FAIL: Correct Message is not displayed ");
			}
		}
@Test(priority=1)
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
        @Test(priority=2)
		public static void TC_02_loginToSFDC() throws Exception {
			Reporting();
			logger = reports.startTest("TC_02_loginToSFDC");

			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_02_loginToSFDC.xls", "Sheet1");
			
			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
			String username_Data = data[1][2];
			System.out.println(username_Data);
			enter_Data_Textbox(username, username_Data, "User Name");
			logger.log(LogStatus.PASS, "User Name is Entered");
			System.out.println("Username Entered in username field");
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			String password_Data = data[1][3];
			System.out.println(password_Data);
			enter_Data_Textbox(password, password_Data, "Password");
			logger.log(LogStatus.PASS, "Password is Entered");
			System.out.println("Password passed");
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			button_Click(loginButton, "Log In");
			logger.log(LogStatus.PASS, "LogIn Button is clicked");
			System.out.println("Login successful");
			Report_Close();
//			TearDown();
		}
@Test(priority=3)
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
@Test(priority=4)
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
@Test(priority=5)
		public static void TC_4B_validateLoginErrorMessage() throws Exception {
			logger = reports.startTest("TC_4B_validateLoginErrorMessage");
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_4B_ForgotPassword.xls", "Sheet1");
			WebElement userName = driver.findElement(By.xpath("//input[@type='email']"));
			String username_Data = data[1][2];
			enter_Data_Textbox(userName, username_Data, "User Name");
			logger.log(LogStatus.PASS, "Wrong User Name is Entered");
			System.out.println("Wrong Username is Entered ");
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			String password_Data = data[1][3];
			enter_Data_Textbox(password, password_Data, "Password");
			logger.log(LogStatus.PASS, "Wrong Password is Entered");
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			button_Click(loginButton, "Log In");
			logger.log(LogStatus.PASS, "LogIn Button is clicked");
			System.out.println("Clicked on login button");
			WebElement errorMsg = driver.findElement(By.xpath("//div[@id='error']"));
			String printErrorMsg = errorMsg.getText();
			System.out.println(printErrorMsg);
			String actualMsg = "Your login attempt has failed. The username or password may be incorrect,"
					+ " or your location or login time may be restricted. Please contact the administrator at your company for help'";
			validate_Message(printErrorMsg, actualMsg);
		}
@Test(priority=6)
		public static void TC_05_SelectUserMenu() throws Exception {
			logger = reports.startTest("TC_05_SelectUserMenu");
	       
			String data[][] = getDataInput(
					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_05_SelectUserMenu.xls", "Sheet1");
			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
			String username_Data = data[1][2];
			System.out.println(username_Data);
			enter_Data_Textbox(username, username_Data, "User Name");
			logger.log(LogStatus.PASS, "User Name is Entered");
			System.out.println("Username Entered in username field");
			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
			String password_Data = data[1][3];
			System.out.println(password_Data);
			enter_Data_Textbox(password, password_Data, "Password");
			logger.log(LogStatus.PASS, "Password is Entered");
			System.out.println("Password passed");
			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
			button_Click(loginButton, "Log In");
			logger.log(LogStatus.PASS, "LogIn Button is clicked");
			System.out.println("Login successful");
			WebElement userMenu = driver.findElement(By.id("userNavButton"));
			button_Click(userMenu, "User Menu");
			
			//String[] list=data[1][4];
			
			
			String[] list = { "My Profile", "My Settings", "Developer Console", "Switch to Lightning Experience",
					"Logout" };

			List<WebElement> menu = driver.findElements(By.xpath("div[@id='userNav-menuItems']"));
			
			menuDropdown(menu,list);
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
	}
		
//		public static void TC_06_MyProfile() throws Exception {
//			logger = reports.startTest("TC_06_MyProfile");
//			//TC_02_loginToSFDC();
//			String data[][] = getDataInput(
//					"C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_06_MyProfile.xls", "Sheet1");
//			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
//			String username_Data = data[1][2];
//			System.out.println(username_Data);
//			enter_Data_Textbox(username, username_Data, "User Name");
//			logger.log(LogStatus.PASS, "User Name is Entered");
//			System.out.println("Username Entered in username field");
//			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
//			String password_Data = data[1][3];
//			System.out.println(password_Data);
//			enter_Data_Textbox(password, password_Data, "Password");
//			logger.log(LogStatus.PASS, "Password is Entered");
//			System.out.println("Password passed");
//			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
//			button_Click(loginButton, "Log In");
//			logger.log(LogStatus.PASS, "LogIn Button is clicked");
//			System.out.println("Login successful");
//			
//			
//			WebElement userMenu = driver.findElement(By.id("userNavButton"));
//			button_Click(userMenu, "User Menu");
//			System.out.println("User Menu is clicked");
//			
//		WebElement myProfile = driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
//		button_Click(myProfile,"My Profile");
//		Thread.sleep(3000);
//		//TC_O6_Step 4:
//		//==========================================
//		WebElement editProfile = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//img"));
//		editProfile.click();
//		logger.log(LogStatus.INFO, "Edit Profile is clicked");
//		
//		//Edit Profile
//		System.out.println("Edit Profile is clicked");
//		WebElement popUpWindow = driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
//		driver.switchTo().frame(popUpWindow);
//		logger.log(LogStatus.INFO,"About tab is clicked");
//		WebElement aboutTab = driver.findElement(By.xpath("//li[@id='aboutTab']"));
//			System.out.println("About Tab is Displayed: "+aboutTab.isDisplayed());
//		aboutTab.click();
//		 WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));
//		 lastName.clear();
//		String lname_data=data[1][5];
//		enter_Data_Textbox(lastName, lname_data, "lastName");
//
//		String actualLastName=lastName.getText();
//		WebElement saveAll = driver.findElement(By.xpath("//input[@type='button']"));
//		button_Click(saveAll, "Save All");
//		
//		System.out.println("The Last Name is Updated and saved");
//		//================================================================
//		//Havn't done validation for last name
//		//=====================================================================
//	WebElement postLink = driver.findElement(By.xpath("//*[@id=\'publisherAttachTextPost\']/span[1]"));
//	button_Click(postLink, "Post Link");
//
//	System.out.println("Clicked on the postLink tab");
//	Thread.sleep(5000);
//	WebElement postFrame = driver.findElement(By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']"));
//	driver.switchTo().frame(postFrame);
//	WebElement inputPost = driver.findElement(By.xpath("/html/body"));
//	button_Click(inputPost, "Input Post ");
//	WebElement input = driver.findElement(By.xpath("/html[1]/body[1]"));
//	System.out.println("Sending message to post");
//
//	String inputPost_data=data[2][5];
//	enter_Message(input, inputPost_data, "input Post");
//
//	System.out.println("Input is entered in Post tab entered");
//	Thread.sleep(3000);
//	driver.switchTo().parentFrame();
//	WebElement shareButton = driver.findElement(By.xpath("//*[@id='publishersharebutton']"));
//	//shareButton.click();
//	button_Click(shareButton, "Share Button ");
//	System.out.println("The Post is shared..");
//	//String actualpostText=postLink.getText();
//	//WebElement diplayedPost = driver.findElement(By.xpath("//p[contains(text(),'My First Post')]"));
//	//String diplayedPostText=diplayedPost.getText();
//	//if(actualpostText.equals(diplayedPostText)) {
////		System.out.println("The Entered Text is diplayed");}
////		else { System.out.println("The Entered Text is not Displayed");}
//	//===========================================================================
//	WebElement fileLink = driver.findElement(By.xpath("//span[contains(@class,'publisherattachtext')][contains(text(),'File')]"));
//	button_Click(fileLink, "File Link ");
//	////div[@id='chatterFileStageOne']
//	System.out.println("Clicked on file Link");
//
//	Thread.sleep(3000); 
//	WebElement uploadFile = driver.findElement(By.cssSelector("#chatterUploadFileAction"));
//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//	button_Click(uploadFile, "Upload File ");
//
//	System.out.println("Upload file tab is clicked");
//	WebElement chooseFile = driver.findElement(By.xpath("//input[@id='chatterFile']"));
//	Thread.sleep(6000); 
//
//	enter_Message(chooseFile, "C:\\Users\\mapkhome\\Desktop\\links.txt", "Choose File");
//	//chooseFile.click();
//	//chooseFile.sendKeys("C:\\Users\\mapkhome\\Desktop\\links.txt");
//	Thread.sleep(6000);
//
//	WebElement share = driver.findElement(By.xpath("//input[@id='publishersharebutton']"));
//	button_Click(share, "Share Button ");
//	Actions action=new Actions(driver);
//	action.moveToElement(driver.findElement(By.id("displayBadge"))).build().perform();
//	WebElement updatePhoto = driver.findElement(By.xpath("//a[@id='uploadLink']"));
//	button_Click(updatePhoto, "Update Photo ");
//
//	//updatePhoto.click();
//	System.out.println("The update photo is clicked");
//	WebElement uploadFrame = driver.findElement(By.xpath("//iframe[@id='uploadPhotoContentId']"));
//	driver.switchTo().frame(uploadFrame);
//	System.out.println("The Update pic Frame is displayed");
//
//	WebElement uploadProfilePhoto = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadInputFile']"));
//	uploadProfilePhoto.click();
//	//button_Click(uploadProfilePhoto, "Update Profile Photo ");
//	Thread.sleep(3000);
//	enter_Message(uploadProfilePhoto, "C:\\Users\\mapkhome\\Desktop\\SaleForce\\pup.jpg", "Update Profile Photo");
//	//uploadProfilePhoto.click();
//	System.out.println("Upload pic link is clicked");
//	WebElement savePhoto = driver.findElement(By.xpath("//input[@type='submit']"));
//	button_Click(savePhoto, "Save Photo");
//		}
//		//============================================================================================
//		public static void TC_07_MySettings() throws Exception {
//			logger = reports.startTest("TC_06_MyProfile");
//			//TC_02_loginToSFDC();
//			String data[][] = getDataInput("C:\\Users\\mapkhome\\Desktop\\SFDC_Project\\SFDC_TestData\\TC_07_MySettings.xls","Sheet1");
//			WebElement username = driver.findElement(By.xpath("//input[@type='email']"));
//			String username_Data = data[1][2];
//			System.out.println(username_Data);
//			enter_Data_Textbox(username, username_Data, "User Name");
//			logger.log(LogStatus.PASS, "User Name is Entered");
//			System.out.println("Username Entered in username field");
//			WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
//			String password_Data = data[1][3];
//			System.out.println(password_Data);
//			enter_Data_Textbox(password, password_Data, "Password");
//			logger.log(LogStatus.PASS, "Password is Entered");
//			System.out.println("Password passed");
//			WebElement loginButton = driver.findElement(By.xpath("//input[@type='submit']"));
//			button_Click(loginButton, "Log In");
//			logger.log(LogStatus.PASS, "LogIn Button is clicked");
//			System.out.println("Login successful");
//			
//			WebElement userMenu = driver.findElement(By.id("userNavButton"));
//			button_Click(userMenu, "User Menu");
//			
//			
//			WebElement mySettings = driver.findElement(By.xpath("//a[@class='menuButtonMenuLink']"));
//			button_Click(mySettings, "My Settings");
//			logger.log(LogStatus.INFO,"Clicked on My Settings");
//			
//			WebElement personal = driver.findElement(By.xpath("//span[@id='PersonalInfo_font']"));
//			button_Click(personal, "Personal");
//			logger.log(LogStatus.INFO,"The Personal was clicked");
//
//			WebElement loginHistory = driver.findElement(By.xpath("//a[@id='LoginHistory_font']"));
//			button_Click(loginHistory, "Login History");
//		    logger.log(LogStatus.INFO,"Login History is clicked");
//		
//		 WebElement downloadFile = driver.findElement(By.xpath("//*[@id=\"RelatedUserLoginHistoryList_body\"]/div/a"));
//		 button_Click(downloadFile, "File Download");
//		System.out.println("The file is downloaded");
//		//downloadValidate=
		//========================================================================
		//Display and Layout Link
		//driver.switchTo().defaultContent(); 
		//Thread.sleep(6000);
		//Actions mousehoover=new Actions(driver);
		//
		//WebElement displayAndLayout = driver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']"));
		//mousehoover.moveToElement(displayAndLayout).build().perform();
		//displayAndLayout.click();
		//System.out.println("Clicked on Display and Layout Link");
		//Thread.sleep(6000);
		//WebElement customizeMyTab = driver.findElement(By.xpath("//span[@id='CustomizeTabs_font']"));
		//customizeMyTab.click();
		//System.out.println("Clicked on CustomizeMy tab");
		//WebElement customApp = driver.findElement(By.xpath("//select[@id='p4']"));
		//Select dropdown=new Select(customApp);
		//dropdown.selectByVisibleText("Salesforce Chatter");
		//System.out.println("SalesForce Chatter is selected");
		//
		//WebElement selectedTabs = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		//if(selectedTabs.getText().contains("Reports")==true) {
//			Select selectedTabDropdown=new Select(selectedTabs);
//			selectedTabDropdown.selectByVisibleText("Reports");
//			WebElement moveToLeftTab = driver.findElement(By.xpath("//img[@class='leftArrowIcon']"));
//			moveToLeftTab.click();
//			System.out.println("Report tab is moved Left");
		//}
		//WebElement availableTabs = driver.findElement(By.xpath("//select[@id='duel_select_0']"));
		//if(availableTabs.getText().contains("Reports")==true) {
//			Select availableTabDropdown=new Select(availableTabs);
//			availableTabDropdown.selectByVisibleText("Reports");
//			WebElement moveToRight = driver.findElement(By.xpath("//img[@class='rightArrowIcon']"));
//			moveToRight.click();
//			System.out.println("Report tab is moved Right");
//			WebElement saveReportTab = driver.findElement(By.xpath("//input[@name='save']"));
//			saveReportTab.click();
//			System.out.println("The tab is saved");
		//
		//}
		//Thread.sleep(3000);
		////if(selectedTabs.getText().contains("Reports")==true) {
////			System.out.println("Reports tab is added to the Selected Tab : Passed");
		////}else System.out.println("Report tab is not added :Failed");
		//
		// WebElement salesForcePageTab = driver.findElement(By.cssSelector("#tabBar"));
		// if(salesForcePageTab.getText().contains("Reports")==true) {
//				System.out.println("Reports tab is added to the HomePage Tab : Passed");
//			}else System.out.println("Report tab is not HomePage Tab :Failed");
		//========================================================================
			
		//Email Link 
		//WebElement email = driver.findElement(By.xpath("//*[@id=\"EmailSetup\"]/a"));
		//mousehoover.moveToElement(email).build().perform();
		//email.click();
		//WebElement emailSettings = driver.findElement(By.xpath("//span[@id='EmailSettings_font']"));
		//emailSettings.click();
		//System.out.println("Email Settings is clicked");
		//WebElement emailName = driver.findElement(By.cssSelector("#sender_name"));
		//emailName.clear();
		//emailName.sendKeys("Anitha");
		//System.out.println("The Name for email is send");
		//WebElement emailAddress = driver.findElement(By.cssSelector("#sender_email"));
		//emailAddress.clear();
		//emailAddress.sendKeys("anitham2u@gmail.com");
		//System.out.println("The email address  is set ");
		//WebElement autoBcc = driver.findElement(By.xpath("//input[@id='auto_bcc1']"));
		//autoBcc.click();
		//System.out.println("The Bcc radio button is selected");
		//WebElement saveEmailSettings = driver.findElement(By.xpath("//input[@name='save']"));
		//saveEmailSettings.click();
		//System.out.println("The email settings is saved");
		//WebElement savedEmailSettings = driver.findElement(By.cssSelector("#meSaveCompleteMessage"));
		//String VerifyEmailSettings=savedEmailSettings.getText().toString();
		//String expectedEmailSettings="Your settings have been successfully saved.";
		//if(expectedEmailSettings.equals(expectedEmailSettings)) {
//			System.out.println("The Email has been updated:Pass");
		//}else System.out.println("The Email Settings is not updated: Fail");

		//Have to check the MY Settings Page Display
				

		//==============================================================================================
		//Calenders and Remainders
//		WebElement calender = driver.findElement(By.xpath("//span[@id='CalendarAndReminders_font']"));
//		mousehoover.moveToElement(calender).build().perform();
//		calender.click();
//		System.out.println("The Calender And Reminder tab is clicked");
//		WebElement activityRemainder = driver.findElement(By.xpath("//span[@id='Reminders_font']"));
//		activityRemainder.click();
//		System.out.println("Activity Remainder is clicked");
//		WebElement testRemainder = driver.findElement(By.xpath("//*[@id=\"testbtn\"]"));
//		testRemainder.click();
//		System.out.println("Test Remainder is clicked");
//		//Alert alert = driver.switchTo().alert();
//		//alert.dismiss();
//		String mainWindow=driver.getWindowHandle();
//		for(String handle:driver.getWindowHandles()) {
//			System.out.println("No of handles: "+handle);
//			
//				driver.switchTo().window(handle);
//			}
//		Thread.sleep(3000);
//		//driver.close();
//		//driver.switchTo().window(handle).close();
//
//		driver.switchTo().window(mainWindow);
//		driver.close();
//
//		System.out.println("The PopUp window is closed");
//		}

	


