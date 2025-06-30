package tests.LoginTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoginPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoginCustomFieldsIT extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setupLoginCustomFields() {
        page.navigate("https://docs.webforj.com/webforj/logincustomfields?");
        loginPage = new LoginPage(page);
    }

    @BrowserTest
    public void testSuccessfulLogin() {
        loginPage.getCustomderID().fill("Tesla");
        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getLogoutButton()).isVisible();
        loginPage.getLogoutButton().click();

        assertThat(loginPage.getHeader()).isVisible();
    }

    @BrowserTest
    public void testInvalidCustomerID() {

        loginPage.getCustomderID().fill("Toyota");
        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testValidCustomerIDAndBlankInput() {
        loginPage.getCustomderID().fill("Tesla");
        loginPage.getUsername().fill(" ");
        loginPage.getPassword().fill(" ");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testValidCustomerIDAndInvalidInput() {
        loginPage.getCustomderID().fill("Tesla");
        loginPage.getUsername().fill("user1");
        loginPage.getPassword().fill("wrongpass");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testBlankCustomerIDAndValidInput() {
        loginPage.getCustomderID().fill(" ");
        loginPage.getUsername().fill("admin");
        loginPage.getPassword().fill("admin");
        loginPage.getSignInButton().click();

        assertThat(loginPage.getErrorMessage()).isVisible();
    }
} 