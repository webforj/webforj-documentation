package pages.TablePages;

import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class TableSortOrderViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_SORT_ORDER;

    public TableSortOrderViewPage(Page page) {
        super(page);
    }

    public static String getRoute() {
        return ROUTE;
    }
}