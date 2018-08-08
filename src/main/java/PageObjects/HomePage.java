package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage extends AbstractPage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final By LOGIN_INPUT_LOCATOR = By.name("login");
    private static final By PASSWORD_INPUT_LOCATOR = By.cssSelector(".input[type=password]");
    private static final By SIGN_IN_BUTTON = By.cssSelector(".o-control[type=submit]");

    public HomePage open() {
        driver.navigate().to("https://mail.ru/");
        return this;
    }

    public HomePage clearLoginInput() {
        driver.findElement(LOGIN_INPUT_LOCATOR).clear();
        return this;
    }

    public HomePage fillLoginInput(String login) {
        WebElement loginInput = driver.findElement(LOGIN_INPUT_LOCATOR);
        new Actions(driver).sendKeys(loginInput, login).build().perform();
        return this;
    }

    public HomePage fillPasswordInput(String password) {
        driver.findElement(PASSWORD_INPUT_LOCATOR).sendKeys(password);
        return this;
    }

    public IncomingMailsPage signIn() {
        driver.findElement(SIGN_IN_BUTTON).click();
        return new IncomingMailsPage(driver);
    }
}
