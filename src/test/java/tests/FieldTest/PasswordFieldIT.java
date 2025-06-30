package tests.FieldTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.PasswordFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class PasswordFieldIT extends BaseTest {

    private PasswordFieldPage passwordFieldPage;

    @BeforeEach
    public void setupPasswordField() {
        page.navigate("https://docs.webforj.com/passwordfield?");
        passwordFieldPage = new PasswordFieldPage(page);
    }

    @BrowserTest
    public void testInputAndMasking() {
        passwordFieldPage.getPasswordField().fill("Password123!");
        assertThat(passwordFieldPage.getPasswordField()).hasAttribute("type", "password");
    }

    @BrowserTest
    public void testVisibilityToggle() {
        passwordFieldPage.getPasswordField().fill("Password123!");
        assertThat(passwordFieldPage.getPasswordField()).hasAttribute("type", "password");

        passwordFieldPage.getEyeOffIcon().click();
        assertThat(passwordFieldPage.getPasswordField()).hasAttribute("type", "text");
    }
} 