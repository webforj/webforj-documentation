package tests.RadioButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.RadioButtonPage.RadioButtonActivationPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonActivationIT extends BaseTest {

    private RadioButtonActivationPage radioButton;

    @BeforeEach
    public void setupRadioButtonActivation() {
        navigateToRoute(RadioButtonActivationPage.getRoute());
        radioButton = new RadioButtonActivationPage(page);

    }

    @BrowserTest
    public void testAutoSelection() {
        assertThat(radioButton.getAutoActivatedInput()).hasAttribute("aria-checked", "true");
    }
}