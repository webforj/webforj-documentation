package com.webforj.samples.views.toast;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class ToastViewIT extends BaseTest {

    @BeforeEach
    public void setupToastView() {
        navigateToRoute("toast");
    }

    @Test
    public void testToastStopButtonClosesToast() {
        Locator toast = page.getByText("System update failed. Restoring to the previous state.");
        assertThat(toast).isVisible();

        Locator stopButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Stop"));
        stopButton.click();

        assertThat(stopButton).not().isVisible();
    }
}
