package pages.ToolbarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class ToolbarCompactPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOOLBAR_COMPACT;

    private final Locator salesTab;
    private final Locator enterpriseTab;

    public ToolbarCompactPage(Page page) {
        super(page);

        salesTab = page.locator("dwc-tabbed-pane:has-text('Sales') >> dwc-tab").nth(0);
        enterpriseTab = page.locator("dwc-tabbed-pane:has-text('Enterprise') >> dwc-tab").nth(1);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSalesTab() {
        return salesTab;
    }

    public Locator getEnterpriseTab() {
        return enterpriseTab;
    }

}