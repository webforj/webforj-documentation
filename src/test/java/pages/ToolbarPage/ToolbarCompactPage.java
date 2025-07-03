package pages.ToolbarPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ToolbarCompactPage extends BasePage {

    private static final String ROUTE = RouteConfig.TOOLBAR_COMPACT;

    // Toolbar Compact Elements
    private final Locator compactToolbar;
    private final Locator tablerIcon;
    private final Locator applicationTitle;
    private final Locator salesTab;
    private final Locator enterpriseTab;
    private final Locator paymentTab;
    private final Locator historyTab;
    private final Locator mainTitle;
    private final Locator paragraph;

    public ToolbarCompactPage(Page page) {
        super(page);

        // Toolbar Compact (dwc-id from ToolbarCompactIT)
        compactToolbar = page.locator("dwc-toolbar[dwc-id='13']");
        tablerIcon = compactToolbar.locator("dwc-icon-button[dwc-id='15']");
        applicationTitle = compactToolbar.locator("h3[dwc-id='14']");
        salesTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(0);
        enterpriseTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(1);
        paymentTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(2);
        historyTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(3);
        mainTitle = page.locator("dwc-app-layout[dwc-id='10'] >> h1[dwc-id='11']");
        paragraph = page.locator("dwc-app-layout[dwc-id='10'] >>p[dwc-id='12']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Toolbar Compact Getters
    public Locator getCompactToolbar() {
        return compactToolbar;
    }

    public Locator getTablerIcon() {
        return tablerIcon;
    }

    public Locator getApplicationTitle() {
        return applicationTitle;
    }

    public Locator getSalesTab() {
        return salesTab;
    }

    public Locator getEnterpriseTab() {
        return enterpriseTab;
    }

    public Locator getPaymentTab() {
        return paymentTab;
    }

    public Locator getHistoryTab() {
        return historyTab;
    }

    public Locator getMainTitle() {
        return mainTitle;
    }

    public Locator getParagraph() {
        return paragraph;
    }
}