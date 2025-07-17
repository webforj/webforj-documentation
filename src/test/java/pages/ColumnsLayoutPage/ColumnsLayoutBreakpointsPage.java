package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class ColumnsLayoutBreakpointsPage extends BasePage {

    private static final String ROUTE = RouteConfig.COLUMNS_LAYOUT_BREAKPOINTS;

    private final Locator dwcColumnsLayout;

    public ColumnsLayoutBreakpointsPage(Page page) {
        super(page);
        this.dwcColumnsLayout = page.locator("dwc-columns-layout");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcColumnsLayout() {
        return dwcColumnsLayout;
    }
}