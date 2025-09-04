package com.webforj.samples.views.fields.passwordfield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.fields.passwordfield.PasswordFieldPage;
import com.webforj.samples.views.BaseTest;

public class PasswordFieldViewIT extends BaseTest {

    private PasswordFieldPage passwordFieldPage;

    @BeforeEach
    public void setupPasswordField() {
        navigateToRoute(PasswordFieldPage.getRoute());
        passwordFieldPage = new PasswordFieldPage(page);
    }

    @Test
    public void testVisibilityToggle() {
        passwordFieldPage.getPasswordField().fill("Password123!");
        assertThat(passwordFieldPage.getPasswordField()).hasAttribute("type", "password");

        passwordFieldPage.getEyeOffIcon().click();
        assertThat(passwordFieldPage.getPasswordField()).hasAttribute("type", "text");
    }
}
