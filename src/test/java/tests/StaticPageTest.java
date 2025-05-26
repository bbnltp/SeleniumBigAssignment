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

import static org.junit.Assert.assertTrue;

public class StaticPageTest {
    private WebDriver driver;

    private static final String[] PAGE_URLS = {
            "https://www.libri.hu/konyv/",
            "https://www.libri.hu/ekonyv/",
            "https://www.libri.hu/hangoskonyv/"
    };

    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        HomePage homePage = new HomePage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testStaticPagesLoadSuccessfully() {
        for (String url : PAGE_URLS) {
            driver.get(url);
            String title = driver.getTitle();
            assertTrue("A cím nem lehet null vagy üres: " + url, title != null && !title.trim().isEmpty());
        }
    }
}
