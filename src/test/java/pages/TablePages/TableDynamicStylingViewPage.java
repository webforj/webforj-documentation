package pages.TablePages;

import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class TableDynamicStylingViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_DYNAMIC_STYLING;

    public TableDynamicStylingViewPage(Page page) {
        super(page);
    }

    public static String getRoute() {
        return ROUTE;
    }
}