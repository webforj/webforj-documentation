package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.login.LoginCustomFieldsPage;
import com.webforj.samples.utils.WaitUtil;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class LoginCustomFieldsIT extends BaseTest {

    private LoginCustomFieldsPage loginCustomFieldsPage;

    @BeforeEach
    public void setupLoginCustomFields() {
        navigateToRoute(LoginCustomFieldsPage.getRoute());
        loginCustomFieldsPage = new LoginCustomFieldsPage(page);
    }

    @BrowserTest
    public void testSuccessfulLogin() {
        loginCustomFieldsPage.getCustomderID().fill("Tesla");
        loginCustomFieldsPage.getUsername().fill("admin");
        loginCustomFieldsPage.getPassword().fill("admin");
        loginCustomFieldsPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginCustomFieldsPage.getLogoutButton(), 10000);
        loginCustomFieldsPage.getLogoutButton().click();

        assertThat(loginCustomFieldsPage.getHeader()).isVisible();
    }

    @BrowserTest
    public void testInvalidCustomerID() {

        loginCustomFieldsPage.getCustomderID().fill("Toyota");
        loginCustomFieldsPage.getUsername().fill("admin");
        loginCustomFieldsPage.getPassword().fill("admin");
        loginCustomFieldsPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginCustomFieldsPage.getErrorMessage(), 10000);
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testValidCustomerIDAndBlankInput() {
        loginCustomFieldsPage.getCustomderID().fill("Tesla");
        loginCustomFieldsPage.getUsername().fill(" ");
        loginCustomFieldsPage.getPassword().fill(" ");
        loginCustomFieldsPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginCustomFieldsPage.getErrorMessage(), 10000);
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testValidCustomerIDAndInvalidInput() {
        loginCustomFieldsPage.getCustomderID().fill("Tesla");
        loginCustomFieldsPage.getUsername().fill("user1");
        loginCustomFieldsPage.getPassword().fill("wrongpass");
        loginCustomFieldsPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginCustomFieldsPage.getErrorMessage(), 10000);
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }

    @BrowserTest
    public void testBlankCustomerIDAndValidInput() {
        loginCustomFieldsPage.getCustomderID().fill(" ");
        loginCustomFieldsPage.getUsername().fill("admin");
        loginCustomFieldsPage.getPassword().fill("admin");
        loginCustomFieldsPage.getSignInButton().click();

        WaitUtil.waitForVisible(loginCustomFieldsPage.getErrorMessage(), 10000);
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }
}
