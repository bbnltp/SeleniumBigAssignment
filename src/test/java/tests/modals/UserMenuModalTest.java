package tests.modals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import pages.HomePage;
import pages.modals.LoginModal;
import pages.modals.UserMenuModal;

import org.junit.After;
import org.junit.Before;

public class UserMenuModalTest {
    private WebDriver driver;
    private LoginModal loginModal;
    private UserMenuModal userMenu;
    private HomePage homePage;

    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"),
                options);
        homePage = new HomePage(driver);
        homePage.open();
        userMenu = new UserMenuModal(driver);
        loginModal = new LoginModal(driver);
        loginModal.login(true);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testOpenUserMenuModal() {
        assertFalse(userMenu.isOpen());
        userMenu.toggle();
        assertTrue(userMenu.isOpen());
    }

    @Test
    public void testCloseUserMenuModal() {
        testOpenUserMenuModal();
        assertTrue(userMenu.isOpen());
        userMenu.toggle();
        assertFalse(userMenu.isOpen());
    }

    @Test
    public void testNavigateToPersonalDataPage(){
        userMenu.navigateToPersonalData();
        assertTrue(userMenu.isNavigationToPersonalDataSuccessful());
    }

    @Test
    public void testGetLoggedInUserName() {
        assertTrue(userMenu.getLoggedInUserName().contains("teszt2"));
    }

    @Test
    public void testLogout() {
        userMenu.logOut();
        assertFalse(userMenu.isOpen());
        assertFalse(userMenu.isUserMenuVisible());
    }
}
