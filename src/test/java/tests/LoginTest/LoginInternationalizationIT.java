package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage.LoginInternationalizationPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class LoginInternationalizationIT extends BaseTest {

    private LoginInternationalizationPage loginInternationalizationPage;

    @BeforeEach
    public void setupLoginInternationalization() {
        navigateToRoute(LoginInternationalizationPage.getRoute());
        loginInternationalizationPage = new LoginInternationalizationPage(page);
    }

    @BrowserTest
    public void testLoginInternationalization() {
        assertThat(loginInternationalizationPage.getHeader()).hasText("Authentifizierung");

        WaitUtil.waitForVisible(loginInternationalizationPage.getErrorMessage());

        assertThat(loginInternationalizationPage.getErrorMessage()).hasText(
                "Falscher Benutzername oder falsches PasswortStellen Sie sicher, dass Sie den richtigen Benutzernamen und das richtige Passwort eingegeben haben und versuchen Sie es erneut.");
    }
}