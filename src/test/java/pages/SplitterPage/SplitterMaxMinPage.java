package pages.SplitterPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class SplitterMaxMinPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPLITTER_MIN_MAX;

    private final Locator masterPanelWithConstraints;
    private final Locator detailPanelWithConstraints;

    public SplitterMaxMinPage(Page page) {
        super(page);

        masterPanelWithConstraints = page.locator("dwc-splitter >> div.splitter-box.splitter-box--info");
        detailPanelWithConstraints = page.locator("dwc-splitter >> div.splitter-box.splitter-box--success");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMasterPanelWithConstraints() {
        return masterPanelWithConstraints;
    }

    public Locator getDetailPanelWithConstraints() {
        return detailPanelWithConstraints;
    }
}