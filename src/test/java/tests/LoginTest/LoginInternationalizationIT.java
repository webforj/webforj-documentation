package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class LoginInternationalizationIT extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupLoginInternationalization() {
        page.navigate("https://docs.webforj.com/webforj/logininternationalization?");
        loginPage = new LoginPage(page);
    }

    @BrowserTest
    public void testLoginInternationalization() {
        assertThat(loginPage.getHeader()).hasText("Authentifizierung");

        WaitUtil.waitForVisible(loginPage.getErrorMessage());

        assertThat(loginPage.getErrorMessage()).hasText(
                "Falscher Benutzername oder falsches PasswortStellen Sie sicher, dass Sie den richtigen Benutzernamen und das richtige Passwort eingegeben haben und versuchen Sie es erneut.");
    }
} 