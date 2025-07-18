package tests.BusyIndicatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.BusyIndicatorPage.BusyDemoPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class BusyDemoIT extends BaseTest {

    private BusyDemoPage busyDemoPage;

    @BeforeEach
    public void setupBusyDemo() {
        navigateToRoute(BusyDemoPage.getRoute());
        busyDemoPage = new BusyDemoPage(page);
    }

    @BrowserTest
    public void testUIBlocked() {
        assertThat(busyDemoPage.getBusyIndicator()).isVisible();

        WaitUtil.assertClickTimeout(busyDemoPage.getNameInput(), "Name");
        WaitUtil.assertClickTimeout(busyDemoPage.getPasswordInput(), "Password input");
        WaitUtil.assertClickTimeout(busyDemoPage.getSubmitButton(), "Submit button");

    }
}