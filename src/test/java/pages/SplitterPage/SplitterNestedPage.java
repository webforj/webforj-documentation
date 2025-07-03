package pages.SplitterPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SplitterNestedPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPLITTER_NESTED;

    private final Locator splitter;
    private final Locator nestedSplitter;
    private final Locator nestedMasterPanel;
    private final Locator nestedDetailPanel;

    public SplitterNestedPage(Page page) {
        super(page);

        splitter = page.locator("dwc-splitter[dwc-id='11']");
        nestedSplitter = page.locator("dwc-splitter >> dwc-splitter[dwc-id='13']");
        nestedMasterPanel = page.locator("dwc-splitter >> dwc-splitter >> div[dwc-id='14']");
        nestedDetailPanel = page.locator("dwc-splitter >> dwc-splitter >> div[dwc-id='15']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSplitter() {
        return splitter;
    }

    public Locator getNestedSplitter() {
        return nestedSplitter;
    }

    public Locator getNestedMasterPanel() {
        return nestedMasterPanel;
    }

    public Locator getNestedDetailPanel() {
        return nestedDetailPanel;
    }
}