package pages.TabbedPanePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class TabbedPaneBorderPage extends BasePage {

    private static final String ROUTE = RouteConfig.TABBED_PANE_BORDER;

    private final Locator hideBorderToggle;
    private final Locator hideActiveIndicatorToggle;
    private final Locator borderTabbedPane;
    private final Locator borderDashboardTab;
    private final Locator borderOrdersTab;

    public TabbedPaneBorderPage(Page page) {
        super(page);

        hideBorderToggle = page.locator("#radio-1");
        hideActiveIndicatorToggle = page.locator("#radio-2");
        borderTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='13']");
        borderDashboardTab = borderTabbedPane.locator("dwc-tab").nth(0);
        borderOrdersTab = borderTabbedPane.locator("dwc-tab").nth(1);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHideBorderToggle() {
        return hideBorderToggle;
    }

    public Locator getHideActiveIndicatorToggle() {
        return hideActiveIndicatorToggle;
    }

    public Locator getBorderTabbedPane() {
        return borderTabbedPane;
    }

    public Locator getBorderDashboardTab() {
        return borderDashboardTab;
    }

    public Locator getBorderOrdersTab() {
        return borderOrdersTab;
    }
}