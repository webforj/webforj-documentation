package pages.GoogleChartsPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ChartViewPage extends BasePage {

    private final Locator chartView;

    private static final String ROUTE = RouteConfig.CHART_VIEW;

    public ChartViewPage(Page page) {
        super(page);
        chartView = page.locator("dwc-chart-view");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getChartView() {
        return chartView;
    }
}