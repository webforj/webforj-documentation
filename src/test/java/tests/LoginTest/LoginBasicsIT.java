package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoginBasicsIT extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupLoginBasics() {
        page.navigate("https://docs.webforj.com/webforj/loginbasic?");
        loginPage = new LoginPage(page);
    }

    @BrowserTest
    public void testUsernameAndPassword() {
        loginPage.getUsername().fill("admin");
        loginPage.getPasswordReveal().click();
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getHeader()).hasText("Authentication");
        assertThat(loginPage.getUsername()).hasValue("admin");
        assertThat(loginPage.getPassword()).hasValue("admin");
        assertThat(loginPage.getSignInButton()).hasAttribute("disabled", "");
    }

    @BrowserTest
    public void testEmptyInput() {
        loginPage.getSignInButton().click();

        assertThat(loginPage.getDwcUsernameField()).hasAttribute("invalid", "");
        assertThat(loginPage.getDwcPasswordField()).hasAttribute("invalid", "");

    }

    @BrowserTest
    public void testRememberMe() {
        loginPage.getRememberMe().click();
        assertThat(loginPage.getRememberMe()).hasAttribute("aria-checked", "false");

        loginPage.getRememberMe().click();
        assertThat(loginPage.getRememberMe()).hasAttribute("aria-checked", "true");

    }
} 