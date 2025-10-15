package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginCustomFieldsPage;
import com.webforj.samples.views.BaseTest;

public class LoginCustomFieldsViewIT extends BaseTest {

    private LoginCustomFieldsPage login;

    @BeforeEach
    public void setupLoginCustomFields() {
        navigateToRoute(LoginCustomFieldsPage.getRoute());
        login = new LoginCustomFieldsPage(page);
    }

    @Test
    public void testSuccessfulLoginWithValidCredentials() {
        login.getCustomderID().fill("Tesla");
        login.getUsername().fill("admin");
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getLogoutButton()).isVisible();
        login.getLogoutButton().click();

        assertThat(login.getUsername()).isVisible();
    }

    @Test
    public void testInvalidCustomerIDIgnored() {

        login.getCustomderID().fill("Toyota");
        login.getUsername().fill("admin");
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getLogoutButton()).not().isVisible();
    }

    @Test
    public void testValidCustomerIDAndBlankInputsIgnored() {
        login.getCustomderID().fill("Tesla");
        login.getUsername().fill(" ");
        login.getPassword().fill(" ");
        login.getSignInButton().click();

        assertThat(login.getLogoutButton()).not().isVisible();
    }

    @Test
    public void testValidCustomerIDAndInvalidInputsIgnored() {
        login.getCustomderID().fill("Tesla");
        login.getUsername().fill("user1");
        login.getPassword().fill("wrongpass");
        login.getSignInButton().click();

        assertThat(login.getLogoutButton()).not().isVisible();
    }

    @Test
    public void testBlankCustomerIDAndValidInputsIgnored() {
        login.getCustomderID().fill(" ");
        login.getUsername().fill("admin");
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getLogoutButton()).not().isVisible();
    }
}
