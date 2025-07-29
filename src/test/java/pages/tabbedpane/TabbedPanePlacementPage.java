package pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class TabbedPanePlacementPage extends BasePage {

    private static final String ROUTE = "tabbedpaneplacement";

    private final Locator placementDropdown;
    private final Locator placementListBox;
    private final Locator placementTabbedPane;

    public TabbedPanePlacementPage(Page page) {
        super(page);

        placementDropdown = page.locator("dwc-dropdown.hydrated");
        placementListBox = page.locator("dwc-listbox");
        placementTabbedPane = page.locator("dwc-tabbed-pane[theme='default']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPlacementDropdown() {
        return placementDropdown;
    }

    public Locator getPlacementListBox() {
        return placementListBox;
    }

    public Locator getPlacementTabbedPane() {
        return placementTabbedPane;
    }
}