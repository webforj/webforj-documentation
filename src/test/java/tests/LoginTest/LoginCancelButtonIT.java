package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoginCancelButtonIT extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupLoginCancelButton() {
        page.navigate("https://docs.webforj.com/webforj/logincancelbutton?");
        loginPage = new LoginPage(page);
    }

    @BrowserTest
    public void testCancelButton() {
        loginPage.getCancelButton().click();
        assertThat(loginPage.getSignInButton()).hasAttribute("disabled", "");
    }
} 