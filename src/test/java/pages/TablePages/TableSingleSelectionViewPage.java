package pages.TablePages;

import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class TableSingleSelectionViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_SINGLE_SELECTION;

    public TableSingleSelectionViewPage(Page page) {
        super(page);
        pageTitle = "Table Single Selection";

    }

    public static String getRoute() {
        return ROUTE;
    }
}