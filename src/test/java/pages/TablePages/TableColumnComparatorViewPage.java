package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class TableColumnComparatorViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_COLUMN_COMPARATOR;

    private final Locator numberColumnHeader;
    private final Locator numberCells;

    public TableColumnComparatorViewPage(Page page) {
        super(page);

        numberColumnHeader = page.locator("th[data-column='Number']");
        numberCells = page.locator("td[data-cell*='-Number'] div[part='cell-label']");

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNumberColumnHeader() {
        return numberColumnHeader;
    }

    public Locator getNumberCells() {
        return numberCells;
    }
}