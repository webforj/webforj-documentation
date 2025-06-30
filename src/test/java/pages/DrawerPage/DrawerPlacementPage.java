package pages.DrawerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DrawerPlacementPage extends BasePage {

    private static final String ROUTE = "drawerplacement";

    // DrawerPlacement locators
    private final Locator drawer;
    private final Locator drawerDropdown;
    private final Locator listBox;

    public DrawerPlacementPage(Page page) {
        super(page);
        
        // Initialize DrawerPlacement locators
        drawer = page.locator("dwc-drawer[dwc-id='11']");
        drawerDropdown = page.locator("dwc-dropdown >> dwc-icon[part='suffix-icon']");
        listBox = page.locator("dwc-listbox >> li");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DrawerPlacement getters
    public Locator getDrawer() {
        return drawer;
    }

    public Locator getDrawerDropdown() {
        return drawerDropdown;
    }

    public Locator getListBox(String text) {
        return listBox.locator("text=\"" + text + "\"");
    }
} 