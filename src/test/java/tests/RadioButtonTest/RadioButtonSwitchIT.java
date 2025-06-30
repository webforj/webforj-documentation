package tests.RadioButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.RadioButtonPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonSwitchIT extends BaseTest {

    private RadioButtonPage radioButton;

    @BeforeEach
    public void setupRadioButtonSwitch() {
        page.navigate("https://docs.webforj.com/webforj/radiobuttonswitch?");
        radioButton = new RadioButtonPage(page);
    }

    @BrowserTest
    public void testSwitchButtonStyle() {
        assertThat(radioButton.getSwitchRadio()).hasAttribute("switch", "");

        radioButton.getSwitchRadio().click();
        assertThat(radioButton.getSwitchInput()).hasAttribute("aria-checked", "true");
    }
} 