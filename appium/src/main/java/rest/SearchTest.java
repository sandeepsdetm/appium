package rest;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class SearchTest {

	static AppiumDriver<MobileElement> driver;
	Helper helper;
	static volatile HashMap<String, AppiumDriver> map = new HashMap<String, AppiumDriver>();

	String path = System.getProperty("user.dir") + "\\resultls";

	static ExtentHtmlReporter reporter = new ExtentHtmlReporter("./learn_automation1.html");

	ExtentReports extent = new ExtentReports();
	ExtentTest logger;
	static ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();
	static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

	@BeforeClass
	public void setup() {
	
		extent.attachReporter(reporter);

	}

	@BeforeMethod(alwaysRun = true)
	public void start(Method method) {

//			 logger=extent.createTest(method.getName());
		threadLocal.set(extent.createTest(method.getName()));
		driver = createDriver();
		helper = new Helper(driver);

	}

	
	@Test(description = "check whether list price usequal to checkout price")
	public void test1() throws InterruptedException {
		helper.clickWithText("Already a customer? Sign in");
		helper.clickWithText("Login. Already a customer?");

		helper.findElementByID("ap_email_login").sendKeys("username");
		helper.clickWithText("Continue");
		helper.findElementByID("ap_password").sendKeys("password");
		helper.findElementByID("signInSubmit").click();
		helper.tapOnElementUsingID("com.amazon.mShop.android.shopping:id/rs_search_src_text");

		helper.findElementByText("Search").sendKeys("65 INCH TV");
		Thread.sleep(5000);
		((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.ENTER);
		checkForRandomTVAssertion();

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult iTestResult) throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException, IOException {

		synchronized (map) {

			System.out.println("in after" + Thread.currentThread().getId());
			List<String> logs = Reporter.getOutput(iTestResult);
			logs.forEach(log -> threadLocal.get().log(Status.INFO, log));
			if(iTestResult.getStatus()==2)
				threadLocal.get().fail(iTestResult.getThrowable());
		

		}

	}

	@AfterClass(alwaysRun = true)
	public void write() {
		extent.flush();

	}

	public static AppiumDriver createDriver() {

		synchronized (map) {
			AppiumDriver<MobileElement> driver = null;
			try {
				driver = new BaseDriver().capabilities();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				map.put(Thread.currentThread().getName(), driver);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return driver;

		}

	}

	public void checkForRandomTVAssertion() throws InterruptedException {
		helper.swipeUp();
		List<MobileElement> list = driver.findElements(By.id("com.amazon.mShop.android.shopping:id/item_title"));
		list.get(1).click();

		MobileElement productElementTitle = helper
				.findElementByXpath(".//*[@resource-id='title_feature_div']/android.view.View");
		String title = productElementTitle.getText();
		MobileElement productElementPrice = helper
				.findElementByXpath(".//*[@resource-id='atfRedesign_priceblock_priceToPay']/android.widget.EditText");
		String amountInBuyingPage = productElementPrice.getText().split(" ")[1];
		helper.swipeUp();

		helper.findElementByID("buyNowCheckout").click();
		helper.findElementByContainsText("this address").click();
		helper.swipeUp();
		helper.findElementByText("Continue").click();
		helper.findElementByText("Other UPI Apps").click();
		helper.findElementByXpath("//android.widget.EditText[contains(@resource-id,'pp-')]")
				.sendKeys("sandeep.161090@okaxis");
		helper.findElementByText("Verify").click();
		helper.swipeUp();
		helper.findElementByText("Continue");
		MobileElement orderTotalElement = helper
				.findElementByXpath(".//*[contains(@text,'Order Total:')]/following::android.view.View");
		String totalAmount = orderTotalElement.getText().split(" ")[1];

		helper.swipeUp();

		List<MobileElement> checkoutPageList = driver
				.findElementsByXPath("//android.widget.Image[@text='" + title + "']");

		assertTrue(checkoutPageList.size() == 1);
		assertTrue(amountInBuyingPage.equals(totalAmount));
	}

}
