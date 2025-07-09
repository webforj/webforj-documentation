package pages.SplitterPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class SplitterOrientationPage extends BasePage {

    private static final String ROUTE = RouteConfig.SPLITTER_ORIENTATION;

    private final Locator splitter;
    private final Locator splitterIcon;
    private final Locator masterPanel;
    private final Locator detailPanel;
    private final Locator horizontalSplitter;
    private final Locator verticalSplitter;

    public SplitterOrientationPage(Page page) {
        super(page);

        splitter = page.locator("dwc-splitter[orientation='vertical']");
        splitterIcon = page.locator("dwc-splitter[orientation='vertical'] >> div > dwc-icon");
        masterPanel = page.locator("dwc-splitter >> div.splitter-box.splitter-box--info");
        detailPanel = page.locator("dwc-splitter >> div.splitter-box.splitter-box--success");
        horizontalSplitter = page.locator("dwc-splitter[orientation='horizontal']");
        verticalSplitter = page.locator("dwc-splitter[orientation='vertical']");
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

    public Locator getHorizontalSplitter() {
        return horizontalSplitter;
    }

    public Locator getVerticalSplitter() {
        return verticalSplitter;
    }
}