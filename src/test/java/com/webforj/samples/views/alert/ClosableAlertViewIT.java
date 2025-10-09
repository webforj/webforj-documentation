package com.webforj.samples.views.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class ClosableAlertViewIT extends BaseTest {

    @BeforeEach
    public void setupClosableAlert() {
        navigateToRoute("closablealert");
    }

    @Test
    public void testAlertClosable() {
        Locator alert = page.getByRole(AriaRole.ALERT);

        assertThat(alert).isVisible();

        Locator closeButton = page.getByRole(AriaRole.BUTTON).filter().getByLabel("icon x");
        closeButton.click();
        assertThat(alert).not().isVisible();
    }
}
