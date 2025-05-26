package pages.modals;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserMenuModal {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By userDropdownToggle = By.id("userDropdown");
    private final By userMenu = By.xpath("//div[contains(@class, 'dropdown-menu') and @aria-labelledby='userDropdown']");

    private final By loggedInUserName = By.xpath("//span[@rel='loggedInMemberName']");
    private final By personalDataLink = By.xpath("//a[@href='/adataim/' and contains(text(), 'adat') or contains(., 'Adataim')]");
    private final By basicInfoLegend = By.xpath("//form//fieldset//legend[text()='Alapadatok']");
    private final By mobileProfileMenuButton = By.xpath("//button[contains(@class, 'profile-menu-btn') and contains(text(), '')]");
    private final By mobileUserMenu = By.xpath("//div[@id='menu-loggedIn-name']");
    private final By logoutLink = By.xpath("//a[contains(@onclick,'LibriMember.logout')]");

    public UserMenuModal(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public boolean isUserMenuVisible() {
        try {
            return driver.findElement(userMenu).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e2) {
            return false;
        }
    }

    public void toggle() {
        WebElement toggleButton = wait.until(ExpectedConditions.elementToBeClickable(userDropdownToggle));
        boolean isCurrentlyOpen = isOpen();

        toggleButton.click();

        if (isCurrentlyOpen) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(userMenu));
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userMenu));
        }
    }

    public boolean isOpen() {
        try {
            return driver.findElement(userMenu).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public String getLoggedInUserName() {
        openUserMenuModal();
        return driver.findElement(loggedInUserName).getText();
    }

    public void navigateToPersonalData(){
        openUserMenuModal();
        WebElement personalData = wait.until(ExpectedConditions.elementToBeClickable(personalDataLink));
        personalData.click();
    }

    public boolean isNavigationToPersonalDataSuccessful(){
        try {
            WebElement basicInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(basicInfoLegend));
            return basicInfo.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void logOut() {
        openUserMenuModal();
        List<WebElement> mobileMenuButtons = driver.findElements(mobileProfileMenuButton);
        if (!mobileMenuButtons.isEmpty() && mobileMenuButtons.get(0).isDisplayed()) {
            logOutMobile();
        } else {
            logOutDesktop();
        }
    }

    private void logOutMobile() {
        WebElement mobileMenuButton = wait.until(ExpectedConditions.elementToBeClickable(mobileProfileMenuButton));
        mobileMenuButton.click();

        WebElement mobileUser = wait.until(ExpectedConditions.elementToBeClickable(mobileUserMenu));
        mobileUser.click();

        WebElement logoutMobile = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutMobile.click();
    }

    private void logOutDesktop() {
        openUserMenuModal();
        WebElement logoutDesktop = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutDesktop.click();
    }

    private void openUserMenuModal(){
        if(!isOpen()){
            toggle();
        }
    }
}