package pages.DrawerPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DrawerWelcomePage extends BasePage {

    private static final String ROUTE = "drawerwelcome";

    // DrawerWelcome locators
    private final Locator welcomeDrawer;
    private final Locator closeButton;
    private final Locator startButton;
    private final Locator openWelcomePageButton;
    private final Locator drawerTitle;
    private final Locator handshakeImage;
    private final Locator bbjImage;

    public DrawerWelcomePage(Page page) {
        super(page);
        
        // Initialize DrawerWelcome locators
        welcomeDrawer = page.locator("dwc-drawer[dwc-id='27']");
        closeButton = page.locator("dwc-icon-button[part='close-button'] >> button");
        startButton = page.locator("dwc-button[dwc-id='32'] >> button");
        openWelcomePageButton = page.locator("dwc-button[dwc-id='26'] >> button");
        drawerTitle = page.locator("dwc-drawer[dwc-id='27'] >> div[part='title']");
        handshakeImage = page.locator("div[dwc-id='28'] img");
        bbjImage = page.locator("div[dwc-id='16'] img");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DrawerWelcome getters
    public Locator getWelcomeDrawer() {
        return welcomeDrawer;
    }

    public Locator getCloseButton() {
        return closeButton;
    }

    public Locator getStartButton() {
        return startButton;
    }

    public Locator getOpenWelcomePageButton() {
        return openWelcomePageButton;
    }

    public Locator getDrawerTitle() {
        return drawerTitle;
    }

    public Locator getHandshakeImage() {
        return handshakeImage;
    }

    public Locator getBbjImage() {
        return bbjImage;
    }

} 