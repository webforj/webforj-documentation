package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage.LoginBasicPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoginBasicsIT extends BaseTest {

    private LoginBasicPage loginBasicPage;

    @BeforeEach
    public void setupLoginBasics() {
        navigateToRoute(LoginBasicPage.getRoute());
        loginBasicPage = new LoginBasicPage(page);
    }

    @BrowserTest
    public void testUsernameAndPassword() {
        loginBasicPage.getUsername().fill("admin");
        loginBasicPage.getPasswordReveal().click();
        loginBasicPage.getPassword().fill("admin");
        loginBasicPage.getSignInButton().click();

        assertThat(loginBasicPage.getHeader()).hasText("Authentication");
        assertThat(loginBasicPage.getUsername()).hasValue("admin");
        assertThat(loginBasicPage.getPassword()).hasValue("admin");
        assertThat(loginBasicPage.getSignInButton()).hasAttribute("disabled", "");
    }

    @BrowserTest
    public void testEmptyInput() {
        loginBasicPage.getSignInButton().click();

        assertThat(loginBasicPage.getDwcUsernameField()).hasAttribute("invalid", "");
        assertThat(loginBasicPage.getDwcPasswordField()).hasAttribute("invalid", "");

    }

    @BrowserTest
    public void testRememberMe() {
        loginBasicPage.getRememberMe().click();
        assertThat(loginBasicPage.getRememberMe()).hasAttribute("aria-checked", "false");

        loginBasicPage.getRememberMe().click();
        assertThat(loginBasicPage.getRememberMe()).hasAttribute("aria-checked", "true");

    }
}