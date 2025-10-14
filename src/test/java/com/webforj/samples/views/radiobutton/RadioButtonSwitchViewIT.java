package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonSwitchPage;
import com.webforj.samples.views.BaseTest;

public class RadioButtonSwitchViewIT extends BaseTest {

    private RadioButtonSwitchPage radioButton;

    @BeforeEach
    public void setupRadioButtonSwitch() {
        navigateToRoute(RadioButtonSwitchPage.getRoute());
        radioButton = new RadioButtonSwitchPage(page);
    }

    @Test
    public void testSwitchRadioButtonStyle() {
        radioButton.getSwitchRadio().check();

        assertThat(radioButton.getSwitchRadio()).isChecked();
    }
}
