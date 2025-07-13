package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class TableOlympicWinnersViewPage extends BasePage {
    private static final String ROUTE = RouteConfig.TABLE_OLYMPIC_WINNERS;

    private final Locator totalHeader;
    private final Locator athleteHeader;
    private final Locator totalRow;
    private final Locator athleteRow;
    private final Locator rows;
    private final Locator firstRow;
    private final Locator lastRow;

    public TableOlympicWinnersViewPage(Page page) {
        super(page);

        totalHeader = page.locator("th:nth-child(7)").first();
        athleteHeader = page.locator("th:nth-child(1)").first();
        totalRow = page.locator("td:nth-child(7)").first();
        athleteRow = page.locator("td:nth-child(1)").first();

        rows = page.locator("tr[data-row]");
        firstRow = rows.first();
        lastRow = rows.last();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTotalHeader() {
        return totalHeader;
    }

    public Locator getAthleteHeader() {
        return athleteHeader;
    }

    public Locator getTotalRow() {
        return totalRow;
    }

    public Locator getAthleteRow() {
        return athleteRow;
    }

    public Locator getRows() {
        return rows;
    }

    public Locator getFirstRow() {
        return firstRow;
    }

    public Locator getLastRow() {
        return lastRow;
    }
}