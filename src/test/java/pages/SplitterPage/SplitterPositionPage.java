package pages.SplitterPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SplitterPositionPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPLITTER_POSITION;

    private final Locator positionedMasterPanel;
    private final Locator positionedDetailPanel;

    public SplitterPositionPage(Page page) {
        super(page);

        positionedMasterPanel = page.locator("dwc-splitter >> div.splitter-box.splitter-box--info");
        positionedDetailPanel = page.locator("dwc-splitter >> div.splitter-box.splitter-box--success");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPositionedMasterPanel() {
        return positionedMasterPanel;
    }

    public Locator getPositionedDetailPanel() {
        return positionedDetailPanel;
    }
}