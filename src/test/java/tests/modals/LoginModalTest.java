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
import org.junit.After;
import org.junit.Before;

public class LoginModalTest {
    private WebDriver driver;
    private LoginModal loginModal;

    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"),
                options);
        HomePage homePage = new HomePage(driver);
        homePage.open();
        loginModal = new LoginModal(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginButtonVisible() {
        assertTrue(loginModal.isLoginButtonVisible());
    }

    @Test
    public void testOpenLoginModal() {
        loginModal.open();
        assertTrue(loginModal.isOpen());
    }

    @Test
    public void testCloseLoginModal() {
        testOpenLoginModal();
        loginModal.close();
        assertFalse(loginModal.isOpen());
    }

    @Test
    public void testLoginWithCorrectData() {
        assertTrue(loginModal.isLoggedIn());
    }

    @Test
    public void testFillLoginFormFieldsWithTestData() {
        testOpenLoginModal();
        loginModal.fillLoginDataWithTestData();
        assertTrue(loginModal.isLoginDataFilled());

    }

    @Test
    public void testFillLoginFormFieldsWithConfigurationData() {
        testOpenLoginModal();
        loginModal.fillLoginDataWithConfigurationData();
        assertTrue(loginModal.isLoginDataFilled());

    }

}
