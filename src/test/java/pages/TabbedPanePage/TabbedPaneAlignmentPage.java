package pages.TabbedPanePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;


public class TabbedPaneAlignmentPage extends BasePage {

    private static final String ROUTE = RouteConfig.TABBED_PANE_ALIGNMENT;

    private final Locator alignmentDropdown;
    private final Locator alignmentListBox;
    private final Locator alignmentTabbedPane;

    public TabbedPaneAlignmentPage(Page page) {
        super(page);

        alignmentDropdown = page.locator("dwc-dropdown.hydrated");
        alignmentListBox = page.locator("dwc-listbox");
        alignmentTabbedPane = page.locator("dwc-tabbed-pane[placement='top']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getAlignmentDropdown() {
        return alignmentDropdown;
    }

    public Locator getAlignmentListBox() {
        return alignmentListBox;
    }

    public Locator getAlignmentTabbedPane() {
        return alignmentTabbedPane;
    }
}