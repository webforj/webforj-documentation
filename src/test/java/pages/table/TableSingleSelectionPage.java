package pages.table;

import com.microsoft.playwright.Page;
import pages.BasePage;

public class TableSingleSelectionPage extends BasePage {
    private static final String ROUTE = "tablesingleselection";

    public TableSingleSelectionPage(Page page) {
        super(page);
        pageTitle = "Table Single Selection";

    }

    public static String getRoute() {
        return ROUTE;
    }
}