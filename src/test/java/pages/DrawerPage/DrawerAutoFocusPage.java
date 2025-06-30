package pages.DrawerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DrawerAutoFocusPage extends BasePage {

    private static final String ROUTE = "drawerautofocus";

    // DrawerAutoFocus locators
    private final Locator drawer;
    private final Locator closeButton;
    private final Locator checkbox;

    public DrawerAutoFocusPage(Page page) {
        super(page);
        
        // Initialize DrawerAutoFocus locators
        drawer = page.locator("dwc-drawer[dwc-id='11']");
        closeButton = page.locator("dwc-icon-button[part='close-button'] >> button");
        checkbox = page.locator("dwc-checkbox[dwc-id='12'] >> input[type='checkbox']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DrawerAutoFocus getters
    public Locator getDrawer() {
        return drawer;
    }

    public Locator getCloseButton() {
        return closeButton;
    }

    public Locator getCheckbox() {
        return checkbox;
    }
} 