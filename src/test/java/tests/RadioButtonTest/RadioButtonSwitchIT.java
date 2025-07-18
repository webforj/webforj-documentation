package tests.RadioButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.RadioButtonPage.RadioButtonSwitchPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class RadioButtonSwitchIT extends BaseTest {

    private RadioButtonSwitchPage radioButton;

    @BeforeEach
    public void setupRadioButtonSwitch() {
        navigateToRoute(RadioButtonSwitchPage.getRoute());
        radioButton = new RadioButtonSwitchPage(page);
    }

    @BrowserTest
    public void testSwitchButtonStyle() {
        assertThat(radioButton.getSwitchRadio()).hasAttribute("switch", "");

        radioButton.getSwitchRadio().click();
        assertThat(radioButton.getSwitchInput()).hasAttribute("aria-checked", "true");
    }
}