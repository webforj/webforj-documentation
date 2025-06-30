package tests.BusyIndicatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.BusyIndicatorPage.BusySpinnersPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class BusySpinnersIT extends BaseTest {

    private BusySpinnersPage busySpinnersPage;

    @BeforeEach
    public void setupBusySpinners() {
        navigateToRoute(BusySpinnersPage.getRoute());
        busySpinnersPage = new BusySpinnersPage(page);
    }

    @BrowserTest
    public void testBusySpinnerVisibility() {
        assertThat(busySpinnersPage.getBusySpinner()).isVisible();

    }
} 