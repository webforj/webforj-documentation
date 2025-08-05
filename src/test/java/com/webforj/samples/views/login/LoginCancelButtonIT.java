package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.login.LoginCancelButtonPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class LoginCancelButtonIT extends BaseTest {

    private LoginCancelButtonPage loginCancelButtonPage;

    @BeforeEach
    public void setupLoginCancelButton() {
        navigateToRoute(LoginCancelButtonPage.getRoute());
        loginCancelButtonPage = new LoginCancelButtonPage(page);
    }

    @BrowserTest
    public void testCancelButton() {
        loginCancelButtonPage.getCancelButton().click();
        assertThat(loginCancelButtonPage.getSignInButton()).hasAttribute("disabled", "");
    }
}
