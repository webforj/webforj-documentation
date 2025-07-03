package tests.RadioButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.RadioButtonPage.RadioButtonTextPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonTextIT extends BaseTest {

    private RadioButtonTextPage radioButton;

    @BeforeEach
    public void setupRadioButtonText() {
        page.navigate(RadioButtonTextPage.getRoute());
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