package com.webforj.samples.pages.appnav;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class AppNavPage extends BasePage {

    private static final String ROUTE = "appnav";

    private final Locator tablerIcon;
    private final Locator appLayout;
    private final Locator paragraph;

    private final Locator inboxDropdown;
    private final Locator sidebarPrimaryTab;

    private final Locator sidebarArchivedTab;
    private final Locator sidebarTrashTab;

    private final Locator aboutDropdown;
    private final Locator sidebarWebforJ;
    private final Locator sidebarGitHub;
    private final Locator sidebarDocumentation;
    private final Locator appLayoutHost;
    private final Locator toolBarHost;
    private final Locator AppBarHost;


    public AppNavPage(Page page) {
        super(page);

        appLayoutHost = page.locator("dwc-app-layout");
        toolBarHost = appLayoutHost.locator("dwc-toolbar");
        AppBarHost = appLayoutHost.locator("dwc-app-nav");

        this.tablerIcon = toolBarHost.locator("dwc-icon-button.menu-2.hydrated");
        this.appLayout = page.locator("dwc-app-layout");
        this.paragraph = page.locator("dwc-app-layout").locator("p");

        this.inboxDropdown = AppBarHost.locator("dwc-app-nav-item:has-text('Inbox')");
        this.sidebarPrimaryTab = inboxDropdown.locator("dwc-app-nav-item:has-text('Primary')");

        this.sidebarArchivedTab = AppBarHost.locator("dwc-app-nav-item:has-text('Archived')");
        this.sidebarTrashTab = AppBarHost.locator("dwc-app-nav-item:has-text('Trash')");

        this.aboutDropdown = AppBarHost.locator("dwc-app-nav-item:has-text('About')");
        this.sidebarWebforJ = aboutDropdown.locator("dwc-app-nav-item:has-text('WebforJ')");
        this.sidebarGitHub = aboutDropdown.locator("dwc-app-nav-item:has-text('GitHub')");
        this.sidebarDocumentation = aboutDropdown.locator("dwc-app-nav-item:has-text('Documentation')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTablerIcon() {
        return tablerIcon;
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