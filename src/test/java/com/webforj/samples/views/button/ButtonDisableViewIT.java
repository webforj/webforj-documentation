package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.button.ButtonDisablePage;
import com.webforj.samples.views.BaseTest;

public class ButtonDisableViewIT extends BaseTest {

    private ButtonDisablePage buttonDisable;

    @BeforeEach
    public void setupButtonDisableDemo() {
        navigateToRoute(ButtonDisablePage.getRoute());
        buttonDisable = new ButtonDisablePage(page);
    }

    @Test
    public void testSubmitButtonIsDisabledWhenEmailInputIsNotValid() {
        buttonDisable.getEmailInput().fill("invalid-email");
        assertThat(buttonDisable.getSubmitButton()).isDisabled();
    }

    @Test
    public void testSubmitButtonIsEnabledWhenEmailInputIsValid() {
        buttonDisable.getEmailInput().fill("valid-email@example.com");
        assertThat(buttonDisable.getSubmitButton()).isEnabled();
    }

}
