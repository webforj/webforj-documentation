package pages.AppNavPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class AppNavDemoPage extends BasePage {

    private static final String ROUTE = "appnav";

    // Main layout and menu elements
    private final Locator tablerIcon;
    private final Locator sideMenu;
    private final Locator appLayout;
    private final Locator paragraph;
    private final Locator mainTitle;

    // Navigation items - Inbox dropdown
    private final Locator inboxDropdown;
    private final Locator sidebarPrimaryTab;
    private final Locator sidebarSocialTab;

    // Navigation items - Main tabs
    private final Locator sidebarArchivedTab;
    private final Locator sidebarTrashTab;

    // About dropdown items
    private final Locator aboutDropdown;
    private final Locator sidebarWebforJ;
    private final Locator sidebarGitHub;
    private final Locator sidebarDocumentation;

    public AppNavDemoPage(Page page) {
        super(page);

        // Main layout and menu elements
        tablerIcon = page.locator("dwc-icon-button[dwc-id='12']");
        sideMenu = page.locator("dwc-app-layout[dwc-id='10']");
        appLayout = page.locator("dwc-app-layout[dwc-id='10']");
        paragraph = page.locator("dwc-app-layout[dwc-id='10'] >> p");
        mainTitle = page.locator("dwc-app-layout[dwc-id='10'] >> h1[dwc-id='45']");

        // Navigation items - Inbox dropdown
        inboxDropdown = page.locator("dwc-app-nav-item[dwc-id='15']");
        sidebarPrimaryTab = inboxDropdown.locator("dwc-app-nav-item[dwc-id='18']");
        sidebarSocialTab = page.locator("dwc-app-nav-item[dwc-id='22']");

        // Navigation items - Main tabs
        sidebarArchivedTab = page.locator("dwc-app-nav-item[dwc-id='30']");
        sidebarTrashTab = page.locator("dwc-app-nav-item[dwc-id='32']");

        // About dropdown items
        aboutDropdown = page.locator("dwc-app-nav-item[dwc-id='36']");
        sidebarWebforJ = aboutDropdown.locator("dwc-app-nav-item[dwc-id='38']");
        sidebarGitHub = aboutDropdown.locator("dwc-app-nav-item[dwc-id='40']");
        sidebarDocumentation = aboutDropdown.locator("dwc-app-nav-item[dwc-id='42']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // Main layout and menu elements getters
    public Locator getTablerIcon() {
        return tablerIcon;
    }

    public Locator getSideMenu() {
        return sideMenu;
    }

    public Locator getAppLayout() {
        return appLayout;
    }

    public Locator getParagraph() {
        return paragraph;
    }

    public Locator getMainTitle() {
        return mainTitle;
    }

    // Navigation items - Inbox dropdown getters
    public Locator getInboxDropdown() {
        return inboxDropdown;
    }

    public Locator getSidebarPrimaryTab() {
        return sidebarPrimaryTab;
    }

    public Locator getSidebarSocialTab() {
        return sidebarSocialTab;
    }

    // Navigation items - Main tabs getters
    public Locator getSidebarArchivedTab() {
        return sidebarArchivedTab;
    }

    public Locator getSidebarTrashTab() {
        return sidebarTrashTab;
    }

    // About dropdown items getters
    public Locator getAboutDropdown() {
        return aboutDropdown;
    }

    public Locator getSidebarWebforJ() {
        return sidebarWebforJ;
    }

    public Locator getSidebarGitHub() {
        return sidebarGitHub;
    }

    public Locator getSidebarDocumentation() {
        return sidebarDocumentation;
    }
} 