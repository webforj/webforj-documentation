package tests.FieldTest.PasswordField;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pages.FieldPages.PasswordFieldPage;
import tests.BaseTest;

public class PasswordFieldIT extends BaseTest {

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