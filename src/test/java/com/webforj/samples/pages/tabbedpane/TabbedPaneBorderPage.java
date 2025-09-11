package com.webforj.samples.pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TabbedPaneBorderPage {

    private static final String ROUTE = "tabbedpaneborder";

    private final Locator hideBorderToggle;
    private final Locator hideActiveIndicatorToggle;
    private final Locator borderTabbedPane;
    private final Locator dashboardTab;
    private final Locator ordersTab;

    public TabbedPaneBorderPage(Page page) {

        this.hideBorderToggle = page.getByRole(AriaRole.RADIO, 
                new Page.GetByRoleOptions().setName("Hide Border"));
        this.hideActiveIndicatorToggle = page.getByRole(AriaRole.RADIO, 
                new Page.GetByRoleOptions().setName("Hide Active Indicator"));
        this.borderTabbedPane = page.locator("dwc-tabbed-pane");
        this.dashboardTab = page.getByRole(AriaRole.TAB, 
                new Page.GetByRoleOptions().setName("Dashboard"));
        this.ordersTab = page.getByRole(AriaRole.TAB, 
                new Page.GetByRoleOptions().setName("Orders"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHideBorderToggle() {
        return hideBorderToggle;
    }

    public Locator getHideActiveIndicatorToggle() {
        return hideActiveIndicatorToggle;
    }

    public Locator getBorderTabbedPane() {
        return borderTabbedPane;
    }

    public Locator getDashboardTab() {
        return dashboardTab;
    }

    public Locator getOrdersTab() {
        return ordersTab;
    }
}