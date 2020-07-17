package rest;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;
public class Helper {
	AppiumDriver driver;
	
	
	public Helper(AppiumDriver driver )
	{
		this.driver=driver;
	}
	public void clickWithText(String text)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='"+text+ "']")));	
		element.click();
		Reporter.log("Clicked on element with text"+text);
	}
	
	public MobileElement findElementByID(String id)
	{

		WebDriverWait wait = new WebDriverWait(driver, 30);
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@resource-id='"+id+ "']")));	
		
		Reporter.log("Element Found with ID"+id);return element;
		
	}
	
	public MobileElement findElementByXpath(String xpath)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	
		Reporter.log("Element Found with xpath"+xpath);return element;
		
	}
	public MobileElement findElementByText(String text)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='"+text+ "']")));
//		MobileElement element=(MobileElement)driver.findElement(By.xpath("//*[@text='"+text+ "']"));
		Reporter.log("Element Found with text"+text);return element;
		
	}
	public MobileElement findElementByContainsText(String text)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'"+text+ "')]")));
	
		Reporter.log("Element Found with text"+text);return element;
		
	}
	
	public void tapOnElementUsingID(String id)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@resource-id='"+id+ "']")));
		
		tapOnElement(element);
	
		
		Reporter.log("tapped on element with id"+id);
		
	}

	public void tapOnElement(MobileElement element)
	{
		TouchAction touchAction=new TouchAction(driver);
		touchAction.tap(tapOptions().withElement(element(element))).perform();
		
	}
	
	public void swipeUp()
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  Dimension size = driver.manage ()
				    .window ()
				    .getSize ();
		  int width = size.getWidth () ;
		  int height = size.getHeight () ;
		 int startX=(int)(width/2);
		 int startY=(int)(height * 0.8);
		 int endY=(int)(height * 0.2);
		 TouchAction swipe = new TouchAction(driver)
	              .press(PointOption.point(startX,startY))
	              .waitAction(waitOptions(ofMillis(800)))
	              .moveTo(PointOption.point(startX,endY))
	              .release()
	              .perform();
	}
}