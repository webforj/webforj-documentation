package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonTextPage;
import com.webforj.samples.views.BaseTest;

public class RadioButtonTextIT extends BaseTest {

    private RadioButtonTextPage radioButton;

    @BeforeEach
    public void setupRadioButtonText() {
        navigateToRoute(RadioButtonTextPage.getRoute());
        radioButton = new RadioButtonTextPage(page);
    }

    @Test
    public void testButtonAlignment() {
        radioButton.getLeftAlignedInput().click();
        assertThat(radioButton.getLeftAlignedInput()).hasAttribute("aria-checked", "true");
    }
}
