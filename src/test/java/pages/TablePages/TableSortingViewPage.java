package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class TableSortingViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_SORTING;

    private final Locator titleSorting;
    private final Locator firstTitleCell;

    public TableSortingViewPage(Page page) {
        super(page);

        titleSorting = page.locator("dwc-table >> text=Title");
        firstTitleCell = page.locator("tr[part*='row'] td[part*='cell'] div[part='cell-label']")
                .first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTitleSorting() {
        return titleSorting;
    }

    public Locator getFirstTitleCell() {
        return firstTitleCell;
    }
}