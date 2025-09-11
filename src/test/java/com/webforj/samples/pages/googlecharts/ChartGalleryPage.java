package com.webforj.samples.pages.googlecharts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ChartGalleryPage  {

    private static final String ROUTE = "chartgallery";

    private final Locator ganttChart;
    private final Locator links;

    public ChartGalleryPage(Page page) {

        this.ganttChart = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Gantt Chart"));
        this.links = page.locator(".chart-gallery > a");
   }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getGanttChart() {
        return ganttChart;
    }

    public Locator getLinks() {
        return links;
    }
}
