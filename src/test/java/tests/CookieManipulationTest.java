package tests;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CookieManipulationTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.libri.hu");

            driver.manage().deleteAllCookies();
            Cookie consentCookie = new Cookie.Builder("cookie-consent", "accepted")
                    .domain("libri.hu")
                    .path("/")
                    .isHttpOnly(false)
                    .isSecure(false)
                    .build();

            driver.manage().addCookie(consentCookie);
            driver.navigate().refresh();

            System.out.println("Cookie added. Check if the popup is gone.");

        } finally {
            driver.quit();
        }
    }
}
