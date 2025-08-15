package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginBasicPage;
import com.webforj.samples.views.BaseTest;

public class LoginBasicIT extends BaseTest {

    private LoginBasicPage loginBasicPage;

    @BeforeEach
    public void setupLoginBasics() {
        navigateToRoute(LoginBasicPage.getRoute());
        loginBasicPage = new LoginBasicPage(page);
    }

    @Test
    public void testUsernameAndPassword() {
        loginBasicPage.getUsername().fill("admin");
        loginBasicPage.getPasswordReveal().click();
        loginBasicPage.getPassword().fill("admin");
        loginBasicPage.getSignInButton().click();

        assertThat(loginBasicPage.getHeader()).hasText("Authentication");
        assertThat(loginBasicPage.getUsername()).hasValue("admin");
        assertThat(loginBasicPage.getPassword()).hasValue("admin");
        assertThat(loginBasicPage.getPassword()).hasAttribute("type", "text");
    }

    @Test
    public void testEmptyInput() {
        loginBasicPage.getSignInButton().click();

        assertThat(loginBasicPage.getDwcUsernameField()).hasAttribute("invalid", "");
        assertThat(loginBasicPage.getDwcPasswordField()).hasAttribute("invalid", "");

    }
}
