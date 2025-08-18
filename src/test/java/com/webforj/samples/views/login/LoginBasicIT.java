package com.webforj.samples.views.login;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.login.LoginBasicPage;
import com.webforj.samples.views.BaseTest;

public class LoginBasicIT extends BaseTest {

    private LoginBasicPage login;

    @BeforeEach
    public void setupLoginBasics() {
        navigateToRoute(LoginBasicPage.getRoute());
        login = new LoginBasicPage(page);
    }

    @Test
    public void testUsernameAndPassword() {
        login.getUsername().fill("admin");
        login.getPasswordReveal().click();
        login.getPassword().fill("admin");
        login.getSignInButton().click();

        assertThat(login.getHeader()).hasText("Authentication");
        assertThat(login.getUsername()).hasValue("admin");
        assertThat(login.getPassword()).hasValue("admin");
        assertThat(login.getPassword()).hasAttribute("type", "text");
    }

    @Test
    public void testEmptyInput() {
        login.getSignInButton().click();

        assertThat(login.getDwcUsernameField()).hasAttribute("invalid", "");
        assertThat(login.getDwcPasswordField()).hasAttribute("invalid", "");

    }
}
