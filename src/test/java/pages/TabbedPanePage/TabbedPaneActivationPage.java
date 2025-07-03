package pages.TabbedPanePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class TabbedPaneActivationPage extends BasePage {

    private static final String ROUTE = RouteConfig.TABBED_PANE_ACTIVATION;

    private final Locator activationToggle;
    private final Locator activationTabbedPane;
    private final Locator activationDashboardTab;
    private final Locator activationOrdersTab;

    public TabbedPaneActivationPage(Page page) {
        super(page);

        activationToggle = page.locator("#radio-1");
        activationTabbedPane = page.locator("dwc-tabbed-pane[dwc-id='12']");
        activationDashboardTab = activationTabbedPane.locator("dwc-tab").nth(0);
        activationOrdersTab = activationTabbedPane.locator("dwc-tab").nth(1);
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Activation getters
    public Locator getActivationToggle() {
        return activationToggle;
    }

    public Locator getActivationTabbedPane() {
        return activationTabbedPane;
    }

    public Locator getActivationDashboardTab() {
        return activationDashboardTab;
    }

    public Locator getActivationOrdersTab() {
        return activationOrdersTab;
    }
}