package tests.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.button.ButtonDisablePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonDisableIT extends BaseTest {

    private ButtonDisablePage buttonDisablePage;

    @BeforeEach
    public void setupButtonDisable() {
        navigateToRoute(ButtonDisablePage.getRoute());
        buttonDisablePage = new ButtonDisablePage(page);

    }

    @BrowserTest
    public void testValidEmailEnablesSubmitButton() {
        assertThat(buttonDisablePage.getDisabledButton()).isDisabled();

        buttonDisablePage.getEmailField().fill("example@email.com");

        assertThat(buttonDisablePage.getDisabledButton()).isEnabled();
    }
}