package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginCustomFieldsPage;
import com.webforj.samples.views.BaseTest;

public class LoginCustomFieldsIT extends BaseTest {

    private LoginCustomFieldsPage loginCustomFieldsPage;

    @BeforeEach
    public void setupLoginCustomFields() {
        navigateToRoute(LoginCustomFieldsPage.getRoute());
        loginCustomFieldsPage = new LoginCustomFieldsPage(page);
    }

    @Test
    public void testSuccessfulLogin() {
        loginCustomFieldsPage.getCustomderID().fill("Tesla");
        loginCustomFieldsPage.getUsername().fill("admin");
        loginCustomFieldsPage.getPassword().fill("admin");
        loginCustomFieldsPage.getSignInButton().click();

        assertThat(loginCustomFieldsPage.getLogoutButton()).isVisible();
        loginCustomFieldsPage.getLogoutButton().click();

        assertThat(loginCustomFieldsPage.getUsername()).isVisible();
    }

    @Test
    public void testInvalidCustomerID() {

        loginCustomFieldsPage.getCustomderID().fill("Toyota");
        loginCustomFieldsPage.getUsername().fill("admin");
        loginCustomFieldsPage.getPassword().fill("admin");
        loginCustomFieldsPage.getSignInButton().click();

        assertThat(loginCustomFieldsPage.getErrorHost()).hasAttribute("opened", "");
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }

    @Test
    public void testValidCustomerIDAndBlankInput() {
        loginCustomFieldsPage.getCustomderID().fill("Tesla");
        loginCustomFieldsPage.getUsername().fill(" ");
        loginCustomFieldsPage.getPassword().fill(" ");
        loginCustomFieldsPage.getSignInButton().click();

        assertThat(loginCustomFieldsPage.getErrorHost()).hasAttribute("opened", "");
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }

    @Test
    public void testValidCustomerIDAndInvalidInput() {
        loginCustomFieldsPage.getCustomderID().fill("Tesla");
        loginCustomFieldsPage.getUsername().fill("user1");
        loginCustomFieldsPage.getPassword().fill("wrongpass");
        loginCustomFieldsPage.getSignInButton().click();

        assertThat(loginCustomFieldsPage.getErrorHost()).hasAttribute("opened", "");
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }

    @Test
    public void testBlankCustomerIDAndValidInput() {
        loginCustomFieldsPage.getCustomderID().fill(" ");
        loginCustomFieldsPage.getUsername().fill("admin");
        loginCustomFieldsPage.getPassword().fill("admin");
        loginCustomFieldsPage.getSignInButton().click();

        assertThat(loginCustomFieldsPage.getErrorHost()).hasAttribute("opened", "");
        assertThat(loginCustomFieldsPage.getErrorMessage()).isVisible();
    }
}
