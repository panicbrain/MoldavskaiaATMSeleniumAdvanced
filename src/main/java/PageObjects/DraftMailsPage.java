package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DraftMailsPage extends BaseAreasPage {
    public DraftMailsPage(WebDriver driver) {
        super(driver);
    }

    private String toLocator = "[data-subject='" + MAIL_SUBJECT + "']";
    private final By LAST_SAVED_DRAFT_LOCATOR = By.cssSelector(toLocator);

    public NewLetterPage openLastSavedDraft() {
        driver.findElement(LAST_SAVED_DRAFT_LOCATOR).click();
        return new NewLetterPage(driver);
    }
}
