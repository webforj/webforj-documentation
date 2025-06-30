package tests.RadioButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.RadioButtonPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonActivationIT extends BaseTest {

    private RadioButtonPage radioButton;

    @BeforeEach
    public void setupRadioButtonActivation() {
        page.navigate("https://docs.webforj.com/webforj/radiobuttonactivation?");
        radioButton = new RadioButtonPage(page);

    }

    @BrowserTest
    public void testAutoSelection() {
        assertThat(radioButton.getAutoActivatedInput()).hasAttribute("aria-checked", "true");
    }
} 