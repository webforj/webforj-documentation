package tests.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import pages.login.LoginSubmissionPage;
import tests.BaseTest;
import utils.CookiesUtil;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class LoginSubmissionIT extends BaseTest {

    private LoginSubmissionPage loginSubmissionPage;

    @BeforeEach
    public void setupLoginSubmission() {
        navigateToRoute(LoginSubmissionPage.getRoute());
        loginSubmissionPage = new LoginSubmissionPage(page);
    }

    @BrowserTest
    public void testInvalidUsernameAndInvalidPassword() {
        loginSubmissionPage.getUsername().fill("admin12");
        loginSubmissionPage.getPassword().fill("wrongpass");
        loginSubmissionPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getErrorMessage());
        assertThat(loginSubmissionPage.getErrorMessage()).isVisible();

    }

    @BrowserTest
    public void testValidUsernmaeAndValidPassword() {
        loginSubmissionPage.getUsername().fill("admin");
        loginSubmissionPage.getPassword().fill("admin");
        loginSubmissionPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getLogoutButton());
        loginSubmissionPage.getLogoutButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getHeader());
        assertThat(loginSubmissionPage.getHeader()).containsText("Authentication");
    }

    @BrowserTest
    public void testValidUsernameInvalidPassword() {
        loginSubmissionPage.getUsername().fill("admin");
        loginSubmissionPage.getPassword().fill("admin123");
        loginSubmissionPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getErrorMessage());
        assertThat(loginSubmissionPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testInvalidUsernameValidPassword() {
        loginSubmissionPage.getUsername().fill("admin123");
        loginSubmissionPage.getPassword().fill("admin");
        loginSubmissionPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getErrorMessage());
        assertThat(loginSubmissionPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testBlankUsernameBlankPassword() {
        loginSubmissionPage.getUsername().fill(" ");
        loginSubmissionPage.getPassword().fill(" ");
        loginSubmissionPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getErrorMessage());
        assertThat(loginSubmissionPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testSpecialCharUsernameAndPassword() {
        loginSubmissionPage.getUsername().fill("!@#$%^&*");
        loginSubmissionPage.getPassword().fill("<>?[]{}~");
        loginSubmissionPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginSubmissionPage.getErrorMessage());
        assertThat(loginSubmissionPage.getErrorMessage()).isVisible();
    }
}