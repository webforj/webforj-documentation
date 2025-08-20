package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonActivationPage;
import com.webforj.samples.views.BaseTest;

public class RadioButtonActivationIT extends BaseTest {

    private RadioButtonActivationPage radioButton;

    @BeforeEach
    public void setupRadioButtonActivation() {
        navigateToRoute(RadioButtonActivationPage.getRoute());
        radioButton = new RadioButtonActivationPage(page);

    }

    @Test
    public void testAutoSelection() {
        assertThat(page.locator("dwc-radio [part~='input']:visible")).hasCount(4);

        assertThat(radioButton.getCheckedAutoInput()).isVisible();
        assertThat(radioButton.getCheckedAutoInput()).hasAttribute("aria-checked", "true");
    }
}
