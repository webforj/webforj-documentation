package tests.ColumnsLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import pages.ColumnsLayoutPage.ColumnsLayoutBreakpointsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ColumnsLayoutBreakpointsIT extends BaseTest {

    private ColumnsLayoutBreakpointsPage columnsLayout;

    private void testBreakpoint(String name, int width, String expectedColumns) {
        navigateToRoute(ColumnsLayoutBreakpointsPage.getRoute());
        columnsLayout = new ColumnsLayoutBreakpointsPage(page);

        page.setViewportSize(width, 844);
        assertThat(columnsLayout.getDwcColumnsLayout()).hasAttribute("data-columns", expectedColumns);
    }

    @BrowserTest
    public void testSmallLayout() {
        testBreakpoint("Small Layout", 390, "1");
    }

    @BrowserTest
    public void testMediumLayout() {
        testBreakpoint("Medium Layout", 768, "2");
    }

    @BrowserTest
    public void testLargeLayout() {
        testBreakpoint("Large Layout", 1024, "3");
    }
}