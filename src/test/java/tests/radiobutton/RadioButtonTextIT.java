package tests.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.radiobutton.RadioButtonTextPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonTextIT extends BaseTest {

    private RadioButtonTextPage radioButton;

    @BeforeEach
    public void setupRadioButtonText() {
        navigateToRoute(RadioButtonTextPage.getRoute());
        radioButton = new RadioButtonTextPage(page);
    }

    @BrowserTest
    public void testButtonAlignment() {
        radioButton.getRightAlignedInput().click();
        assertThat(radioButton.getRightAlignedInput()).hasAttribute("aria-checked", "true");

        radioButton.getLeftAlignedInput().click();
        assertThat(radioButton.getLeftAlignedInput()).hasAttribute("aria-checked", "true");

    }
}