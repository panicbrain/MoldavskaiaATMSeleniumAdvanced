package PageObjects;

import Utils.Screenshoter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class NewLetterPage extends BaseAreasPage {
    public NewLetterPage(WebDriver driver) {
        super(driver);
    }

    private final static By EMAIL_ADDRESS_INPUT_LOCATOR = By.cssSelector(".js-input[data-original-name=To]");
    private final static By SUBJECT_INPUT_LOCATOR = By.name("Subject");
    private final static By FRAME_MAIL_BODY_LOCATOR = By.cssSelector("iframe");
    private final static By MAIL_BODY_INPUT_LOCATOR = By.cssSelector("#tinymce");
    private final static By SAVE_AS_DRAFT_BUTTON_LOCATOR = By.cssSelector("#b-toolbar__right [data-name='saveDraft']");
    private final static By SAVE_STATUS_MESSAGE_LOCATOR = By.cssSelector("[data-mnemo=\"saveStatus\"]");
    private final static By FILLED_EMAIL_ADDRESS_LOCATOR = By.cssSelector("[data-text='ekaterinamoldavskaia18@gmail.com']");
    private final static By SEND_MAIL_BUTTON = By.xpath("//div[@data-name='send']");
    private final static By SENT_MAIL_MESSAGE = By.cssSelector(".message-sent__title");

    public NewLetterPage fillEmailInput(String email) {
        waitForElementsVisible(EMAIL_ADDRESS_INPUT_LOCATOR);
        driver.findElement(EMAIL_ADDRESS_INPUT_LOCATOR).sendKeys(email);
        return this;
    }

    public NewLetterPage fillSubjectInput() {
        waitForElementsVisible(SUBJECT_INPUT_LOCATOR);
        driver.findElement(SUBJECT_INPUT_LOCATOR).sendKeys(MAIL_SUBJECT);
        return this;
    }

    public NewLetterPage fillMailBodyInput(String mailBodyText) {
        driver.switchTo().frame(driver.findElement(FRAME_MAIL_BODY_LOCATOR));
        waitForElementEnabled(MAIL_BODY_INPUT_LOCATOR);
        driver.findElement(MAIL_BODY_INPUT_LOCATOR).sendKeys(mailBodyText);
        driver.switchTo().defaultContent();
        return this;
    }

    public NewLetterPage saveAsDraft() {
        waitForElementEnabled(SAVE_AS_DRAFT_BUTTON_LOCATOR);
        highlightElement(SAVE_AS_DRAFT_BUTTON_LOCATOR);
        Screenshoter.takeScreenshot();
        unHighlightElement(SAVE_AS_DRAFT_BUTTON_LOCATOR);
        driver.findElement(SAVE_AS_DRAFT_BUTTON_LOCATOR).click();
        waitForElementVisible(SAVE_STATUS_MESSAGE_LOCATOR);
        return this;
    }

    public String getMailAddress() {
        waitForElementVisible(FILLED_EMAIL_ADDRESS_LOCATOR);
        String mailAddress = driver.findElement(FILLED_EMAIL_ADDRESS_LOCATOR).getText();
        return mailAddress;
    }

    public Object getMailSubject() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object subToMail = js.executeScript("return document.getElementsByName('Subject')[0].value");
        return subToMail;
    }

    public String getBodyText() {
        driver.switchTo().frame(driver.findElement(FRAME_MAIL_BODY_LOCATOR));
        waitForElementEnabled(MAIL_BODY_INPUT_LOCATOR);
        String bodyText = driver.findElement(MAIL_BODY_INPUT_LOCATOR).getText();
        driver.switchTo().defaultContent();
        return bodyText;
    }

    public void sendMail() {
        waitForElementEnabled(SEND_MAIL_BUTTON);
        highlightElement(SEND_MAIL_BUTTON);
        Screenshoter.takeScreenshot();
        unHighlightElement(SEND_MAIL_BUTTON);
        driver.findElement(SEND_MAIL_BUTTON).click();
        waitForElementVisible(SENT_MAIL_MESSAGE);
    }
}
