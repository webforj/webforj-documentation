package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.login.LoginCancelButtonPage;
import com.webforj.samples.views.BaseTest;

public class LoginCancelButtonViewIT extends BaseTest {

    private LoginCancelButtonPage login;

    @BeforeEach
    public void setupLoginCancelButton() {
        navigateToRoute(LoginCancelButtonPage.getRoute());
        login = new LoginCancelButtonPage(page);
    }

    @Test
    public void testCancelButton() {
        login.getCancelButton().click();
        assertThat(login.getSignInButton()).hasAttribute("disabled", "");
    }
}
