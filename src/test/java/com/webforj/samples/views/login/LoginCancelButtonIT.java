package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginCancelButtonPage;
import com.webforj.samples.views.BaseTest;

public class LoginCancelButtonIT extends BaseTest {

    private LoginCancelButtonPage loginCancelButtonPage;

    @BeforeEach
    public void setupLoginCancelButton() {
        navigateToRoute(LoginCancelButtonPage.getRoute());
        loginCancelButtonPage = new LoginCancelButtonPage(page);
    }

    @Test
    public void testCancelButton() {
        loginCancelButtonPage.getCancelButton().click();
        assertThat(loginCancelButtonPage.getSignInButton()).hasAttribute("disabled", "");
    }
}
