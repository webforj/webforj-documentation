package com.webforj.samples.views.busyindicator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.TimeoutError;
import com.webforj.samples.pages.busyindicator.BusyDemoPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusyDemoViewIT extends BaseTest {

    private BusyDemoPage busyDemoPage;

    @BeforeEach
    public void setupBusyDemo() {
        navigateToRoute(BusyDemoPage.getRoute());
        busyDemoPage = new BusyDemoPage(page);
    }

    @Test
    public void testNameInputIsNotInteractableWhenBusyIndicatorIsVisible() {
        assertThat(busyDemoPage.getBusyIndicator()).isVisible();

        try {
            busyDemoPage.getNameInput().click(new Locator.ClickOptions().setTimeout(1000));
            throw new AssertionError("Expected field to not be clickable, but it was");
        }
        catch (TimeoutError e) {
        }
    }
}
