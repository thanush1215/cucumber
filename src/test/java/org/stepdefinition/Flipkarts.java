package org.stepdefinition;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Flipkarts {

	WebDriver driver;
	Robot r;
	JavascriptExecutor js;
	Actions a;

	@Given("I open the Flipkart website")
	public void i_open_the_Flipkart_website() {

		ChromeOptions c = new ChromeOptions();
		c.addArguments("--start-maximized"); // Maximize window
		driver = new ChromeDriver(c);
		driver.get("https://www.flipkart.com/");

	}

	@When("I search for a product")
	public void i_search_for_a_product() throws AWTException {

		r = new Robot();

		WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));
		searchBox.sendKeys("mobile");
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

	}

	@When("I select a product")
	public void i_select_a_product() {

		WebElement product = driver.findElement(By.xpath("(//div[text()='vivo T3x 5G (Crimson Bliss, 128 GB)'])[1]")); // Replace
																														// 'product_id'
																														// with
																														// actual
																														// value
		product.click();
	}

	@When("I add the product to the cart")
	public void i_add_the_product_to_the_cart() {

		String parentWindowId = driver.getWindowHandle();
		System.out.println(parentWindowId);

		Set<String> allWindowId = driver.getWindowHandles();
		System.out.println(allWindowId);

		for (String id : allWindowId) {

			if (!allWindowId.equals(id)) {
				driver.switchTo().window(id);
			}
		}

		js = (JavascriptExecutor) driver;
		WebElement addToCartButton = driver.findElement(By.xpath("//button[text()='Add to cart']"));
		js.executeScript("arguments[0].scrollIntoView(true)", addToCartButton);
		addToCartButton.click();
	}

	@When("I proceed to checkout")
	public void i_proceed_to_checkout() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		WebElement placeOrder = driver.findElement(By.xpath("//span[text()='Place Order']"));
		placeOrder.click();
	}

	@When("I login to the application and enter otp")
	public void i_login_to_the_application_and_enter_otp() throws InterruptedException, AWTException {

		WebElement login = driver.findElement(By.xpath("//input[@type='text']"));
		login.sendKeys("8056220250");

		WebElement continueBtn = driver.findElement(By.xpath("//span[text()='CONTINUE']"));
		continueBtn.click();

		WebElement otp = driver.findElement(By.xpath("(//input[@type='text'])[2]"));
		Thread.sleep(10000l);

		WebElement otpLogin = driver.findElement(By.xpath("//span[text()='Login']"));
		otpLogin.click();
	}

	@When("I should be able to place the order successfully")
	public void i_should_be_able_to_place_the_order_successfully() {

	}

	@When("I select the delivery address")
	public void i_select_the_delivery_address() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		js = (JavascriptExecutor) driver;

		WebElement address = driver.findElement(By.xpath("//div[text()='Add a new address']"));
		js.executeScript("arguments[0].click()", address);

		WebElement name = driver.findElement(By.xpath("//input[@type='text']"));
		js.executeScript("arguments[0].setAttribute('value','Thanush')", address);

		WebElement mobileNo = driver.findElement(By.name("phone"));
		js.executeScript("arguments[0].setAttribute('value','8056220250')", mobileNo);

		WebElement pinCode = driver.findElement(By.xpath("//input[@tabindex='3']"));
		js.executeScript("arguments[0].setAttribute('value','8056220250')", pinCode);

		WebElement locality = driver.findElement(By.name("addressLine2"));
		locality.sendKeys("Indian");

		WebElement addressArea = driver.findElement(By.xpath("textarea[@autocomplete='street-address']"));
		addressArea.sendKeys("");

		WebElement city = driver.findElement(By.name("city"));
		city.sendKeys("");

		WebElement state = driver.findElement(By.name("state"));
		Select s = new Select(state);
		s.selectByVisibleText("Tamil Nadu");

		WebElement landMart = driver.findElement(By.xpath("//input[@autocomplete='off']"));
		landMart.sendKeys("");

		WebElement alterMobileNo = driver.findElement(By.xpath(" (//input[@type='text'])[7]"));
		alterMobileNo.sendKeys("");

		WebElement addressType = driver.findElement(By.xpath("//span[text()='Home (All day delivery)']"));
		js.executeScript("arguments[0].click()", addressType);

		WebElement deleveryHere = driver.findElement(By.xpath("//button[text()='Save and Deliver Here']"));
		js.executeScript("arguments[0].click()", deleveryHere);

		WebElement orderSummary = driver.findElement(By.xpath("//span[text()='Order Summary']"));
		js.executeScript("arguments[0].click()", orderSummary);

	}

	@When("I select the payment option")
	public void i_select_the_payment_option() {

		WebElement continueBtn = driver.findElement(By.xpath("//button[text()='CONTINUE']"));
		js.executeScript("arguments[0].scrollIntoView(true)", continueBtn);
		continueBtn.click();

		WebElement paymentOption = driver.findElement(By.xpath("//span[text()='Credit / Debit / ATM Card']"));
		paymentOption.click();
	}

	@When("I have products in the cart")
	public void i_have_products_in_the_cart() {

		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		a = new Actions(driver);
		WebElement flipkartMainPage = driver.findElement(By.xpath("//img[@title='Flipkart']"));
		a.moveToElement(flipkartMainPage).perform();
		flipkartMainPage.click();

	}

	@When("I remove a product from the cart")
	public void i_remove_a_product_from_the_cart() {

		WebElement cart = driver.findElement(By.xpath("(//div[@class='_38VF5e'])[2]"));
		cart.click();

		WebElement removeProduct = driver.findElement(By.xpath("//div[text()='Remove']"));
		js.executeScript("arguments[0].scrollIntoView(true)", removeProduct);
		removeProduct.click();

	}

	@When("The cart should be empty")
	public void the_cart_should_be_empty() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		a = new Actions(driver);
		WebElement flipkartMainPage = driver.findElement(By.xpath("//img[@title='Flipkart']"));
		a.moveToElement(flipkartMainPage).perform();
		flipkartMainPage.click();

		WebElement cart = driver.findElement(By.xpath("(//div[@class='_38VF5e'])[2]"));
		cart.click();

		WebElement emptyCart = driver.findElement(By.xpath("//div[text()='Your cart is empty!']"));
		String text = emptyCart.getText();
		System.out.println(text);
	}

	@Then("I quit the browser")
	public void i_quit_the_browser() {

		driver.quit();
	}

}
