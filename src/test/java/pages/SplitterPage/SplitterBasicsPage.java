package pages.SplitterPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

public class SplitterBasicsPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPLITTER_BASIC;

    private final Locator splitter;
    private final Locator splitterIcon;
    private final Locator masterPanel;
    private final Locator detailPanel;

    public SplitterBasicsPage(Page page) {
        super(page);

        splitter = page.locator("dwc-splitter[orientation='horizontal']");
        splitterIcon = page.locator("dwc-splitter[orientation='horizontal'] >> div > dwc-icon");
        masterPanel = page.locator("dwc-splitter >> div.splitter-box.splitter-box--info");
        detailPanel = page.locator("dwc-splitter >> div.splitter-box.splitter-box--success");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSplitter() {
        return splitter;
    }

    public Locator getSplitterIcon() {
        return splitterIcon;
    }

    public Locator getMasterPanel() {
        return masterPanel;
    }

    public Locator getDetailPanel() {
        return detailPanel;
    }
}