package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.radiobutton.RadioButtonActivationPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

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
