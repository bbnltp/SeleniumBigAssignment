package pages;

import org.openqa.selenium.WebDriver;
import pages.modals.LoginModal;
import pages.modals.UserMenuModal;

public class HomePage extends BasePage {
    private final LoginModal loginModal;
    private final UserMenuModal userMenuModal;

    public HomePage(WebDriver driver) {
        super(driver);
        loginModal = new LoginModal(driver);
        userMenuModal = new UserMenuModal(driver);
    }

    public void open() {
        driver.get("https://www.libri.hu");
        driver.manage().window().maximize();

    }


}
