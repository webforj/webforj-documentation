package pages.AppNavPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;
import com.webforj.samples.config.RouteConfig;

public class AppNavDemoPage extends BasePage {

    private static final String ROUTE = RouteConfig.APP_NAV;

    private final Locator tablerIcon;
    private final Locator sideMenu;
    private final Locator appLayout;
    private final Locator paragraph;

    private final Locator inboxDropdown;
    private final Locator sidebarPrimaryTab;
    private final Locator sidebarSocialTab;

    private final Locator sidebarArchivedTab;
    private final Locator sidebarTrashTab;

    private final Locator aboutDropdown;
    private final Locator sidebarWebforJ;
    private final Locator sidebarGitHub;
    private final Locator sidebarDocumentation;

    public AppNavDemoPage(Page page) {
        super(page);

        tablerIcon = page.locator("dwc-icon-button[class='menu-2 hydrated']");
        sideMenu = page.locator("dwc-app-layout");
        appLayout = page.locator("dwc-app-layout");
        paragraph = page.locator("dwc-app-layout >> p");

        inboxDropdown = page.locator("dwc-app-nav-item:has-text('Inbox')");
        sidebarPrimaryTab = inboxDropdown.locator("dwc-app-nav-item:has-text('Primary')");
        sidebarSocialTab = inboxDropdown.locator("dwc-app-nav-item:has-text('Social')");

        sidebarArchivedTab = page.locator("dwc-app-nav-item:has-text('Archived')");
        sidebarTrashTab = page.locator("dwc-app-nav-item:has-text('Trash')");

        aboutDropdown = page.locator("dwc-app-nav-item:has-text('About')");
        sidebarWebforJ = aboutDropdown.locator("dwc-app-nav-item:has-text('WebforJ')");
        sidebarGitHub = aboutDropdown.locator("dwc-app-nav-item:has-text('GitHub')");
        sidebarDocumentation = aboutDropdown.locator("dwc-app-nav-item:has-text('Documentation')");
    }

    public static String getRoute() {
        return ROUTE;
    }

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

    public Locator getInboxDropdown() {
        return inboxDropdown;
    }

    public Locator getSidebarPrimaryTab() {
        return sidebarPrimaryTab;
    }

    public Locator getSidebarSocialTab() {
        return sidebarSocialTab;
    }

    public Locator getSidebarArchivedTab() {
        return sidebarArchivedTab;
    }

    public Locator getSidebarTrashTab() {
        return sidebarTrashTab;
    }

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