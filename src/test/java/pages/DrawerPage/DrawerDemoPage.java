package pages.DrawerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DrawerDemoPage extends BasePage {

    private static final String ROUTE = "drawerdemo";

    // DrawerDemo locators
    private final Locator drawer;
    private final Locator closeButton;

    public DrawerDemoPage(Page page) {
        super(page);
        
        // Initialize DrawerDemo locators
        drawer = page.locator("dwc-drawer[dwc-id='11']");
        closeButton = page.locator("dwc-icon-button[part='close-button'] >> button");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DrawerDemo getters
    public Locator getDrawer() {
        return drawer;
    }

    public Locator getCloseButton() {
        return closeButton;
    }
} 