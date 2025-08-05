package com.webforj.samples.pages.googlecharts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class ChartGalleryPage extends BasePage {

    private static final String ROUTE = "chartgallery";

    private final Locator ganttChart;
    private final Locator links;

    public ChartGalleryPage(Page page) {
        super(page);
        ganttChart = page.locator("div.chart-div > p:has-text('Gantt Chart')");
        links = page.locator(".chart-gallery a");
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
