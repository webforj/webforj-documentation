package pages.GoogleChartsPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ChartGalleryViewPage extends BasePage {

    private static final String ROUTE = RouteConfig.CHART_GALLERY;

    private final Locator ganttChart;
    private final Locator links;

    public ChartGalleryViewPage(Page page) {
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
