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
    public void testBusyIndicatorVisibility() {
        assertThat(busyDemoPage.getBusyIndicator()).isVisible();
    }

    @BrowserTest
    public void testUIBlocked() {
        WaitUtil.assertClickTimeout(busyDemoPage.getNameInput(), "Name input");
        WaitUtil.assertClickTimeout(busyDemoPage.getPasswordInput(), "Password input");
        WaitUtil.assertClickTimeout(busyDemoPage.getSubmitButton(), "Submit button");

    }
}