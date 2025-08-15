package com.webforj.samples.pages.tabbedpane;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class TabbedPaneBorderPage extends BasePage {

    private static final String ROUTE = "tabbedpaneborder";

    private final Locator hideBorderToggle;
    private final Locator hideActiveIndicatorToggle;
    private final Locator borderTabbedPane;
    private final Locator dashboardTab;
    private final Locator ordersTab;

    public TabbedPaneBorderPage(Page page) {
        super(page);

        this.hideBorderToggle = page.locator("dwc-radio").filter(
                new Locator.FilterOptions().setHasText("Hide Border")).locator("input");
        this.hideActiveIndicatorToggle = page.locator("dwc-radio").filter(
                new Locator.FilterOptions().setHasText("Hide Active Indicator")).locator("input");
        this.borderTabbedPane = page.locator("dwc-tabbed-pane");
        this.dashboardTab = borderTabbedPane.locator("dwc-tab").filter(
                new Locator.FilterOptions().setHasText("Dashboard"));
        this.ordersTab = borderTabbedPane.locator("dwc-tab").filter(
                new Locator.FilterOptions().setHasText("Orders"));
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