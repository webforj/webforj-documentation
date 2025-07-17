package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;
import pages.BasePage;

public class TableFilteringPage extends BasePage {

    private static final String ROUTE = RouteConfig.TABLE_FILTERING;

    private final Locator titleFilterInput;
    private final Locator tableRows;
    private final Locator firstTitleCell;

    public TableFilteringPage(Page page) {
        super(page);

        titleFilterInput = page.locator("#field-1");
        tableRows = page.locator("tbody tr[part*='row']");
        firstTitleCell = page.locator("tr td").first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTitleFilterInput() {
        return titleFilterInput;
    }

    public Locator getTableRows() {
        return tableRows;
    }

    public Locator getFirstTitleCell() {
        return firstTitleCell;
    }

    public void filterByTitle(String title) {
        titleFilterInput.fill(title);
    }

    public int tableRowCount() {
        return tableRows.count();
    }
}
