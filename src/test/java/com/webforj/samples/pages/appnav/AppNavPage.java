package com.webforj.samples.pages.appnav;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AppNavPage {

    private static final String ROUTE = "appnav";

    private final Locator tablerIcon;
    private final Locator appLayout;
    private final Locator paragraph;

    private final Locator inboxDropdown;
    private final Locator sidebarPrimaryTab;

    private final Locator aboutDropdown;
    private final Locator sidebarWebforJ;
    private final Locator sidebarGitHub;
    private final Locator sidebarDocumentation;

    public AppNavPage(Page page) {

        this.tablerIcon = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("menu 2"));
        this.appLayout = page.locator("dwc-app-layout");
        this.paragraph = page.getByText("Content for", new Page.GetByTextOptions());

        this.inboxDropdown = page.getByText("Inbox", new Page.GetByTextOptions());

        this.sidebarPrimaryTab = page.getByText("Primary", new Page.GetByTextOptions());

        this.aboutDropdown = page.getByText("About", new Page.GetByTextOptions());
        this.sidebarWebforJ = page.getByText("webforJ", new Page.GetByTextOptions());
        this.sidebarGitHub = page.getByText("GitHub", new Page.GetByTextOptions());
        this.sidebarDocumentation = page.getByText("Documentation", new Page.GetByTextOptions());
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