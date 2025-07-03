package pages.GoogleChartsPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ChartSettingDataViewPage extends BasePage {

    private static final String ROUTE = RouteConfig.CHART_SETTING_DATA;

    private final Locator regionLocators;
    private final Locator title;

    public ChartSettingDataViewPage(Page page) {
        super(page);
        regionLocators = page.locator("g[column-id]:not([column-id='Sales Distribution by Region'])");
        title = page.locator("svg g").nth(0);

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getRegionLocators() {
        return regionLocators;
    }

    public Locator getTitle() {
        return title;
    }
}
