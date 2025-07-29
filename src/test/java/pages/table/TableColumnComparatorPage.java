package pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TableColumnComparatorPage extends BasePage {
    private static final String ROUTE = "tablecolumncomparator";

    private final Locator numberColumnHeader;
    private final Locator numberCells;

    public TableColumnComparatorPage(Page page) {
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