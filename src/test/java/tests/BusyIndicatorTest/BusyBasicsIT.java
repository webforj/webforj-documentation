package tests.BusyIndicatorTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.BusyIndicatorPage.BusyDemoPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class BusyBasicsIT extends BaseTest {

    private BusyDemoPage busyBasicsPage;

    @BeforeEach
    public void setupBusyBasics() {
        navigateToRoute(BusyDemoPage.getRoute());
        busyBasicsPage = new BusyDemoPage(page);
    }

    @BrowserTest
    public void testBusyIndicatorVisibility() {
        assertThat(busyBasicsPage.getBusyIndicator()).isVisible();
    }

    @BrowserTest
    public void testUIBlocked() {
        WaitUtil.assertClickTimeout(busyBasicsPage.getNameInput(), "Name input");
        WaitUtil.assertClickTimeout(busyBasicsPage.getPasswordInput(), "Password input");
        WaitUtil.assertClickTimeout(busyBasicsPage.getSubmitButton(), "Submit button");

    }
} 