package tests.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.login.LoginCancelButtonPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoginCancelButtonIT extends BaseTest {

    private LoginCancelButtonPage loginCancelButtonPage;

    @BeforeEach
    public void setupLoginCancelButton() {
        navigateToRoute(LoginCancelButtonPage.getRoute());
        loginCancelButtonPage = new LoginCancelButtonPage(page);
    }

    @BrowserTest
    public void testCancelButton() {
        loginCancelButtonPage.getCancelButton().click();
        assertThat(loginCancelButtonPage.getSignInButton()).hasAttribute("disabled", "");
    }
}