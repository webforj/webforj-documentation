package pages.NavigatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import config.RouteConfig;

import pages.BasePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigatorPagesViewPage extends BasePage {

    private static final String ROUTE = RouteConfig.NAVIGATOR_PAGES;

    public NavigatorPagesViewPage(Page page) {
        super(page);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public void assertCurrentPage(int pageNumber) {
        Locator pageButton = page.locator("button[title='Goto page " + pageNumber + "']");
        assertThat(pageButton).hasAttribute("aria-current", "true");
    }

    public void goToPage(int pageNumber) {
        Locator pageButton = page.locator("button[title='Goto page " + pageNumber + "']");
        pageButton.click();
    }

    public Locator getEllipsisButtons() {
        return page.locator("div[part='layout layout-numbered'] > button");
    }

}