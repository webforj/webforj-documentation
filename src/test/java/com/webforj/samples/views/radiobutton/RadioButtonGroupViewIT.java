package com.webforj.samples.views.radiobutton;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.radiobutton.RadioButtonGroupPage;
import com.webforj.samples.views.BaseTest;

public class RadioButtonGroupViewIT extends BaseTest {

    private RadioButtonGroupPage radioButtonGroupPage;

    @BeforeEach
    public void setupRadioButtonGroup() {
        navigateToRoute(RadioButtonGroupPage.getRoute());
        radioButtonGroupPage = new RadioButtonGroupPage(page);
    }

    @Test
    public void testStronglyDisagreeRadioButtonGroupIsChecked() {
        radioButtonGroupPage.getStronglyDisagreeRB().check();
        assertThat(radioButtonGroupPage.getStronglyDisagreeRB()).isChecked();

        radioButtonGroupPage.getDisagreeRB().check();
        assertThat(radioButtonGroupPage.getDisagreeRB()).isChecked();
        assertThat(radioButtonGroupPage.getStronglyDisagreeRB()).not().isChecked();
    }
}
