package appiumTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import utility.ScreenShots;
import utility.ScrollElement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContactApplicationUIScrollable {

	private AppiumDriver<MobileElement> driver;

	@BeforeClass
	public void contactSetUp() throws InterruptedException, MalformedURLException {
		
		System.out.println("*************************************************************");
		System.out.println("Setting up Contact App capabilities");
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "0715f7c98061163a");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.android.contacts");
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.android.contacts.activities.PeopleActivity");
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
		cap.setCapability(MobileCapabilityType.VERSION, "7.0");
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(url, cap);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterClass
	public void tearDown() throws Exception {
		if(driver != null){
			System.out.println("*****************************************************");
			System.out.println("Destroying the Test Environment");
			driver.quit();
		}
	}
	
	@BeforeMethod
	public void beforeMethod(Method method){
		System.out.println("Executing Method: " + method.getName());
		System.out.println("-----------------------------------------------");
	}

	@AfterMethod
	public void afterMethod(Method method){
		System.out.println("Closing Method: " + method.getName());
		System.out.println("-------------------------------------------");
	}
	
	@Test(description="Navigate to contact and call")
	public void navigateContact() throws InterruptedException, IOException {
		ScreenShots ss = new ScreenShots((AndroidDriver<MobileElement>) driver);
		ScrollElement se = new ScrollElement((AndroidDriver<MobileElement>) driver);
		
		try{
			MobileElement title = driver.findElement(By.xpath("//android.widget.TextView[@text='CONTACTS']"));
			System.out.println(title.getText() + " page opened");
			ss.takeScreenShots("Contact List");
		} catch(NoSuchElementException | IOException e){
			System.out.println("Contact page failed to open");
			e.printStackTrace();
		}
		
		MobileElement contactList = driver.findElement(By.id("android:id/content"));
		String callContact = JOptionPane.showInputDialog("Provide the name that you want to call");
		
		MobileElement trgtcontact = se.scrollToElement(contactList, callContact);
		System.out.println("Navigating to the target contact: " + callContact);
		ss.takeScreenshots("Target Contact");
		trgtcontact.click();
		Thread.sleep(2000);
		
		MobileElement callButton = driver.findElementById("com.samsung.android.contacts:id/expand_call_icon");
		System.out.println("Call initiated to target contact");
		ss.takeScreenshots("Contact details");
		callButton.click();
		Thread.sleep(2000);
		
		MobileElement endCallButton = driver.findElementById("com.android.incallui:id/endButon");
		System.out.println("Disconnecting call");
		ss.takeScreenshots("Calling page");
		endCallButton.click();
		Thread.sleep(2000);
		
		
		
		
		
	}


}
