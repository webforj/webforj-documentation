package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonGroupPage;
import com.webforj.samples.views.BaseTest;

public class RadioButtonGroupIT extends BaseTest {

    private RadioButtonGroupPage radioButtonGroupPage;

    @BeforeEach
    public void setupRadioButtonGroup() {
        navigateToRoute(RadioButtonGroupPage.getRoute());
        radioButtonGroupPage = new RadioButtonGroupPage(page);
    }

    @Test
    public void testRadioButtonGroup() {
        radioButtonGroupPage.getStronglyDisagreeRadioButton().click();
        assertThat(radioButtonGroupPage.getStronglyDisagreeRadioButton()).isChecked();

        radioButtonGroupPage.getDisagreeRadioButton().click();
        assertThat(radioButtonGroupPage.getDisagreeRadioButton()).isChecked();
        assertThat(radioButtonGroupPage.getStronglyDisagreeRadioButton()).not().isChecked();
    }
}
