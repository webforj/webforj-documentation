package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginCustomFieldsPage;
import com.webforj.samples.views.BaseTest;

public class LoginCustomFieldsIT extends BaseTest {

    private LoginCustomFieldsPage login;

    @BeforeEach
    public void setupLoginCustomFields() {
        navigateToRoute(LoginCustomFieldsPage.getRoute());
        login = new LoginCustomFieldsPage(page);
    }

    @Test
    public void testSuccessfulLogin() {
        login.getCustomderID().fill("Tesla");
        login.getUsername().fill("admin");
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getLogoutButton()).isVisible();
        login.getLogoutButton().click();

        assertThat(login.getUsername()).isVisible();
    }

    @Test
    public void testInvalidCustomerID() {

        login.getCustomderID().fill("Toyota");
        login.getUsername().fill("admin");
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getErrorHost()).hasAttribute("opened", "");
        assertThat(login.getErrorMessage()).isVisible();
    }

    @Test
    public void testValidCustomerIDAndBlankInput() {
        login.getCustomderID().fill("Tesla");
        login.getUsername().fill(" ");
        login.getPassword().fill(" ");
        login.getSignInButton().click();

        assertThat(login.getErrorHost()).hasAttribute("opened", "");
        assertThat(login.getErrorMessage()).isVisible();
    }

    @Test
    public void testValidCustomerIDAndInvalidInput() {
        login.getCustomderID().fill("Tesla");
        login.getUsername().fill("user1");
        login.getPassword().fill("wrongpass");
        login.getSignInButton().click();

        assertThat(login.getErrorHost()).hasAttribute("opened", "");
        assertThat(login.getErrorMessage()).isVisible();
    }

    @Test
    public void testBlankCustomerIDAndValidInput() {
        login.getCustomderID().fill(" ");
        login.getUsername().fill("admin");
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getErrorHost()).hasAttribute("opened", "");
        assertThat(login.getErrorMessage()).isVisible();
    }
}
