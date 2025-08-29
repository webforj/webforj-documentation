package com.webforj.samples.pages.appnav;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class AppNavPage extends BasePage {

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
        super(page);

        this.tablerIcon = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("menu 2"));
        this.appLayout = page.locator("dwc-app-layout");
        this.paragraph = page.getByText("Content for", new Page.GetByTextOptions().setExact(false));

        this.inboxDropdown = page.getByText("Inbox", new Page.GetByTextOptions().setExact(false));

        this.sidebarPrimaryTab = page.getByText("Primary", new Page.GetByTextOptions().setExact(false));

        this.aboutDropdown = page.getByText("About", new Page.GetByTextOptions().setExact(false));
        this.sidebarWebforJ = page.getByText("webforJ", new Page.GetByTextOptions().setExact(false));
        this.sidebarGitHub = page.getByText("GitHub", new Page.GetByTextOptions().setExact(false));
        this.sidebarDocumentation = page.getByText("Documentation", new Page.GetByTextOptions().setExact(false));
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