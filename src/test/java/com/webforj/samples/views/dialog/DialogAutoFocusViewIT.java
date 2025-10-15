package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class DialogAutoFocusViewIT extends BaseTest {

    @BeforeEach
    public void setupDialogAutoFocus() {
        navigateToRoute("dialogautofocus");
    }

    @Test
    public void testAutoFocusIsEnabled() {
        Locator textField = page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("This Box is Auto Focused"));

        assertThat(textField).isFocused();
    }

}
