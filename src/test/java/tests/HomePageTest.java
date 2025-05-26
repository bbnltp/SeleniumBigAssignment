package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.HomePage;

import java.net.MalformedURLException;
import java.net.URL;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HomePageTest {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        homePage = new HomePage(driver);


    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHomePageTitle() {
        homePage.open();
        String title = homePage.getPageTitle();
        assertTrue(title.toLowerCase().contains("libri.hu"));
    }


    @Test
    public void testHomePageLoadsCorrectly() {
        testHomePageTitle();
    }

    @Test
    public void testBrowserBackButton() {
        driver.get("https://www.libri.hu/konyv/");
        assertEquals("https://www.libri.hu/konyv/", driver.getCurrentUrl());

        driver.get("https://www.libri.hu/ekonyv/");
        assertEquals("https://www.libri.hu/ekonyv/", driver.getCurrentUrl());

        driver.navigate().back();
        assertEquals("https://www.libri.hu/konyv/", driver.getCurrentUrl());
    }

}
