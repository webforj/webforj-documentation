package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage;
import tests.BaseTest;
import utils.CookiesUtil;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class LoginSubmissionIT extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupLoginSubmission() {
        page.navigate("https://docs.webforj.com/webforj/loginsubmission?");
        loginPage = new LoginPage(page);
    }

    @BrowserTest
    public void testInvalidUsernameAndInvalidPassword() {
        loginPage.getUsername().fill("admin12");
        loginPage.getPassword().fill("wrongpass");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();

    }

    @BrowserTest
    public void testValidUsernmaeAndValidPassword() {
        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginPage.getLogoutButton());
        loginPage.getLogoutButton().click();

        WaitUtil.waitForVisible(loginPage.getHeader());
        assertThat(loginPage.getHeader()).containsText("Authentication");
    }

    @BrowserTest
    public void testCookies() {
        loginPage.getUsername().waitFor();
        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginPage.getLogoutButton(), 10000);

        Map<String, String> localStorageItems = CookiesUtil.getLocalStorage(page);
        assertEquals("on", localStorageItems.get("dwc-login-rememberme"));

        loginPage.getLogoutButton().click();

        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin");
        loginPage.getRememberMe().click();

        assertThat(loginPage.getRememberMe()).hasAttribute("aria-checked", "false");

        loginPage.getSignInButton().click();

        Map<String, String> localStorageAfterRememberMe = CookiesUtil.getLocalStorage(page);

        assertEquals("off", localStorageAfterRememberMe.get("dwc-login-rememberme"));
    }

    @BrowserTest
    public void testValidUsernameInvalidPassword() {
        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin123");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testInvalidUsernameValidPassword() {
        loginPage.getUsername().fill("admin123");
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testBlankUsernameBlankPassword() {
        loginPage.getUsername().fill(" ");
        loginPage.getPassword().fill(" ");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testSpecialCharUsernameAndPassword() {
        loginPage.getUsername().fill("!@#$%^&*");
        loginPage.getPassword().fill("<>?[]{}~");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }
} 