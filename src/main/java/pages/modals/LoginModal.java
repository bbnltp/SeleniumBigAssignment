package pages.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

public class LoginModal {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By desktopLoginButton = By.xpath(
            "//a[contains(concat(' ', normalize-space(@class), ' '), ' login ') and contains(concat(' ', normalize-space(@class), ' '), ' d-lg-inline-block ') and @data-bs-target='#loginModal']");

    private final By mobileLoginButton = By.xpath(
            "//a[contains(concat(' ', normalize-space(@class), ' '), ' header-btn ') and contains(concat(' ', normalize-space(@class), ' '), ' login ') and contains(concat(' ', normalize-space(@class), ' '), ' d-lg-none ') and @data-bs-target='#loginModal']");


    private final By loginModal = By.id("loginModal");
    private final By emailInput = By.id("member_email");
    private final By passwordInput = By.id("passwd");
    private final By loginSubmitButton = By.xpath(
            "//*[@id='loginModal']//button[@type='submit']");

    private final By closeModalButton = By.xpath(
            "//*[@id='loginModal']//*[contains(concat(' ', normalize-space(@class), ' '), ' btn-close ')]");


    private final String testEmail = "tesztfiok_sqat@freemail.hu";
    private final String testPassword = "Nincs12Nincs12";

    public LoginModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public boolean isLoginButtonVisible() {
        return isElementDisplayed(desktopLoginButton) || isElementDisplayed(mobileLoginButton);
    }

    public void open() {
        if (isLoginButtonVisible()) {
            By loginButton = getLoginButtonLocator();
            wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
        } else {
            throw new IllegalStateException("Login button is not visible on the page.");
        }
    }

    public boolean isOpen() {
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
            return emailField.isDisplayed() && passwordField.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public void close() {
        if (isOpen()) {
            wait.until(ExpectedConditions.elementToBeClickable(closeModalButton)).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loginModal));
        } else {
            throw new IllegalStateException("Login modal is not open, so it cannot be closed.");
        }
    }

    public void fillLoginDataWithTestData(){
        fillLoginData(testEmail, testPassword);
    }

    public void fillLoginDataWithConfigurationData(){
        try {
            String email = ConfigReader.get("test.email");
            String password = ConfigReader.get("test.password");
            fillLoginData(email, password);
        } catch(Exception e){
            System.out.println("config not found");
        }
    }

    public boolean isLoginDataFilled(){
        String actualEmail = getEmailField().getAttribute("value");;
        String actualPassword = getPasswordField().getAttribute("value");;

        return testEmail.equals(actualEmail) && testPassword.equals(actualPassword);
    }

    public void login(boolean isTestData) {
        open();
        if (isTestData) {
            fillLoginDataWithTestData();
        } else {
            fillLoginDataWithConfigurationData();
        }
        if(isLoginDataFilled()) {
            driver.findElement(loginSubmitButton).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loginModal));
        }
    }

    public boolean isLoggedIn(){
        login(true);
        return !isLoginButtonVisible();
    }


    private void fillLoginData(String email, String password){
        WebElement emailField = getEmailField();
        WebElement passwordField = getPasswordField();

        emailField.clear();
        emailField.sendKeys(testEmail);

        passwordField.clear();
        passwordField.sendKeys(testPassword);

    }

    private WebElement getEmailField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
    }

    private WebElement getPasswordField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
    }

    private boolean isElementDisplayed(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, 3);
            WebElement el = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            // WebElement el = driver.findElement(locator);
            return el.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    private By getLoginButtonLocator() {
        if (!driver.findElements(desktopLoginButton).isEmpty()) {
            return desktopLoginButton;
        } else if (!driver.findElements(mobileLoginButton).isEmpty()) {
            return mobileLoginButton;
        } else {
            throw new NoSuchElementException("Neither desktop nor mobile login button found.");
        }
    }
}
