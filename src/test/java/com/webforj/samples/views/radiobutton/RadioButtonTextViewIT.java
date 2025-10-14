package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonTextPage;
import com.webforj.samples.views.BaseTest;

public class RadioButtonTextViewIT extends BaseTest {

    private RadioButtonTextPage radioButton;

    @BeforeEach
    public void setupRadioButtonText() {
        navigateToRoute(RadioButtonTextPage.getRoute());
        radioButton = new RadioButtonTextPage(page);
    }

    @Test
    public void testLeftAlignedButtonStyle() {
        radioButton.getLeftAlignedInput().check();
        assertThat(radioButton.getLeftAlignedInput()).hasAttribute("aria-checked", "true");
    }
}
