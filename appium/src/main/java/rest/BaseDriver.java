package rest;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseDriver {
	public static    AppiumDriver<MobileElement> capabilities() throws MalformedURLException
	{
		

		  AppiumDriver<MobileElement>  driver;

		// TODO Auto-generated method stub
	
     DesiredCapabilities capabilities = new DesiredCapabilities();
     
     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 900);
    driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	    
	    return driver;
	}
	
	

}
