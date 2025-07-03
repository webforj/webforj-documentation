package tests.BusyIndicatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.BusyIndicatorPage.BusySpinnerDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class BusySpinnerDemoIT extends BaseTest {

    private BusySpinnerDemoPage busySpinnerDemoPage;

    @BeforeEach
    public void setupBusySpinnerDemo() {
        navigateToRoute(BusySpinnerDemoPage.getRoute());
        busySpinnerDemoPage = new BusySpinnerDemoPage(page);
    }

    @BrowserTest
    public void testBusySpinnerVisibility() {
        assertThat(busySpinnerDemoPage.getBusySpinner()).isVisible();

    }
}