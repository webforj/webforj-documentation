package pages.TablePages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class DataTablePage extends BasePage {
    private static final String ROUTE = RouteConfig.DATA_TABLE;

    private final Locator searchInput;
    private final Locator tableRows;
    private final Locator entriesDropdown;
    private final Locator entriesTen;
    private final Locator entriesTwentyfive;
    private final Locator entriesFifty;
    private final Locator entriesHundred;
    private final Locator paginator;
    private final Locator paginatorLastPage;
    private final Locator paginatorNextPage;
    private final Locator paginatorFirstPage;
    private final Locator paginatorPreviousPage;
    private final Locator currentPageNavigator;
    private final Locator paginationText;
    private final Locator firstCheckbox;
    private final Locator lastPageNumber;

    public DataTablePage(Page page) {
        super(page);

        searchInput = page.locator("input[type='search']");
        tableRows = page.locator("tbody tr[data-row]");
        entriesDropdown = page.locator("dwc-button[part='button']");
        entriesTen = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("10").setExact(true));
        entriesTwentyfive = page.getByRole(AriaRole.OPTION,
           new Page.GetByRoleOptions().setName("25").setExact(true));
        entriesFifty = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("50").setExact(true));
        entriesHundred = page.getByRole(AriaRole.OPTION,
           new Page.GetByRoleOptions().setName("100").setExact(true));
        paginator = page.locator("dwc-navigator.data-scrollable-right");
        paginatorLastPage = page.locator("button[part='button button-last']");
        paginatorNextPage = page.locator("button[part='button button-next']");
        paginatorFirstPage = page.locator("button[part='button button-first']");
        paginatorPreviousPage = page.locator("button[part='button button-previous']");
        currentPageNavigator = page.locator("dwc-navigator:has-text('Showing')");
        paginationText = page.locator("div[part='layout layout-preview']");
        firstCheckbox = page.locator("input[type='checkbox']").first();
        lastPageNumber = page.locator("div[part='layout layout-numbered']").locator("button").last();
    }

    public void searchAthlete(String athleteName) {
        searchInput.click();
        page.keyboard().type(athleteName);
    }

    public int getRowCount() {
        return tableRows.count();
    }

    public Locator getSearchInput() {
        return searchInput;
    }

    public Locator getTableRows() {
        return tableRows;
    }

    public Locator getEntriesDropdown() {
        return entriesDropdown;
    }

    public Locator getEntriesTen() {
        return entriesTen;
    }

    public Locator getEntriesTwentyfive() {
        return entriesTwentyfive;
    }

    public Locator getEntriesFifty() {
        return entriesFifty;
    }

    public Locator getEntriesHundred() {
        return entriesHundred;
    }

    public Locator getPaginator() {
        return paginator;
    }

    public Locator getPaginatorLastPage() {
        return paginatorLastPage;
    }

    public Locator getPaginatorNextPage() {
        return paginatorNextPage;
    }

    public Locator getPaginatorFirstPage() {
        return paginatorFirstPage;
    }

    public Locator getPaginatorPreviousPage() {
        return paginatorPreviousPage;
    }

    public Locator getCurrentPageNavigator() {
        return currentPageNavigator;
    }

    public Locator getPaginationText() {
        return paginationText;
    }

    public Locator getFirstCheckbox() {
        return firstCheckbox;
    }

    public Locator getLastPageNumber() {
        return lastPageNumber;
    }

    public Locator goToSpecificPage(int pageNumber) {
        Locator pageNavigationButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions()
                        .setName(String.format("Goto page %s", pageNumber))
                        .setExact(true));

        return pageNavigationButton;
    }

    public static String getRoute() {
        return ROUTE;
    }
}