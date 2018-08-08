import PageObjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static PageObjects.BaseAreasPage.MAIL_SUBJECT;
import static org.testng.Assert.*;

public class MailRuTestPageObject {

    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }

    @Test(description = "mail.ru page should open and contain appropriate title")
    public void loginTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Open mail.ru page
        HomePage homepage = new HomePage(driver);
        homepage.open();
        assertEquals(homepage.driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");

        // clear the email address field
        homepage.clearLoginInput();

        //login to the mail box
        homepage.fillLoginInput("MoldavskaiaATM");
        homepage.fillPasswordInput("ghbvtcm46");
        IncomingMailsPage incomingMailsPage = homepage.signIn();

        // assert that the login was successful
        wait.until(ExpectedConditions.titleContains("Входящие - Почта Mail.Ru"));
        assertTrue(incomingMailsPage.driver.getTitle().contains("Входящие - Почта Mail.Ru"));

        //Create a new mail
        NewLetterPage newLetterPage = incomingMailsPage.createNewLetter();
        newLetterPage.fillEmailInput("ekaterinamoldavskaia18@gmail.com");
        newLetterPage.fillSubjectInput();
        newLetterPage.fillMailBodyInput("Text");

        // save the mail as draft
        newLetterPage.saveAsDraft();

        //open drafts folder
        DraftMailsPage draftMailsPage = newLetterPage.openDraftFolder();
        assertEquals(draftMailsPage.driver.getTitle(), "Новое письмо - Почта Mail.Ru");

        // assert that draft presents in the Draft folder
        assertTrue(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft of test email is absent in the folder");

        //Open saved draft
        newLetterPage = draftMailsPage.openLastSavedDraft();
        assertEquals(newLetterPage.driver.getTitle(), "Новое письмо - Почта Mail.Ru");

        // assert that all field contain the same information that before saving as draft
        assertEquals(newLetterPage.getMailAddress(), "ekaterinamoldavskaia18@gmail.com");
        assertEquals(newLetterPage.getMailSubject(), MAIL_SUBJECT);
        assertTrue(newLetterPage.getBodyText().contains("Text"), "Mail text is absent");

        // send email
        newLetterPage.sendMail();

        // assert that the draft disappeared from draft folder
        draftMailsPage = newLetterPage.openDraftFolder();
        assertFalse(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft stays in the folder");

        //assert the sent mail presents in Sent folder
        SentMailsPage sentMailsPage = draftMailsPage.openSentFolder();
        assertTrue(sentMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The sent email is absent in the folder");

        //Log off
        homepage = sentMailsPage.logOff();
        assertEquals(homepage.driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");
    }
}
