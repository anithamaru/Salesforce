import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Demo1 {

//	static WebDriver driver;
//	static AppiumDriver <WebElement> driver1;
	static  AndroidDriver<MobileElement> driver;

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
//driver =new ChromeDriver();

DesiredCapabilities capability=new DesiredCapabilities();
//capability.setCapability("platformName", "Android");
//
//capability.setCapability("deviceName", "ZY2222SVX9");
//capability.setCapability("browserName", "chrome");
//capability.setCapability("platformVersion", "6.0");
capability.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
capability.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
capability.setCapability(MobileCapabilityType.DEVICE_NAME, "ZY2222SVX9");
capability.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");

capability.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.goole.android.contacts");
capability.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.goole.android.contacts.activities.PeopleActivity");

driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capability);
System.out.println("Amazon browser Launched");
driver.close();
	}

}
