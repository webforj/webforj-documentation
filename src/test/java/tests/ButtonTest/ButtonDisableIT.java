package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ButtonPage.ButtonDisablePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonDisableIT extends BaseTest {

    private ButtonDisablePage buttonPage;

    @BeforeEach
    public void setupButtonDisable() {
        navigateToRoute(ButtonDisablePage.getRoute());
        buttonPage = new ButtonDisablePage(page);

    }

    @BrowserTest
    public void testValidEmailEnablesSubmitButton() {
        assertThat(buttonPage.getDisabledButton()).isDisabled();

        buttonPage.getEmailField().fill("example@email.com");

        assertThat(buttonPage.getDisabledButton()).isEnabled();
    }
} 