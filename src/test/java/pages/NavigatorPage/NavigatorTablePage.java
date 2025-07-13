package pages.NavigatorPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigatorTablePage extends BasePage {

    private static final String ROUTE = RouteConfig.NAVIGATOR_TABLE;

    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;

    public NavigatorTablePage(Page page) {
        super(page);

        firstButton = page.locator("button[title='Goto first page']");
        prevButton = page.locator("button[title='Goto previous page']");
        nextButton = page.locator("button[title='Goto next page']");
        lastButton = page.locator("button[title='Goto last page']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public void clickFirst() {
        firstButton.click();
    }

    public void clickPrev() {
        prevButton.click();
    }

    public void clickNext() {
        nextButton.click();
    }

    public void clickLast() {
        lastButton.click();
    }

    public void assertCurrentPage(int pageNumber) {
        Locator pageButton = page.locator("button[title='Goto page " + pageNumber + "']");
        assertThat(pageButton).hasAttribute("aria-current", "true");
    }

    public void assertCurrentPageData(int pageNumber, String expectedData) {
        Locator firstDataCell = page.locator("tbody tr:nth-child(2) td:nth-child(1)");
        String actualData = firstDataCell.innerText().trim();
        assertEquals(expectedData, actualData,
                "First data cell should contain: " + expectedData);
    }

    public void goToPage(int pageNumber) {
        Locator pageButton = page.locator("button[title='Goto page " + pageNumber + "']");
        pageButton.click();
    }

    public Locator getFirstButton() {
        return firstButton;
    }

    public Locator getPrevButton() {
        return prevButton;
    }

    public Locator getNextButton() {
        return nextButton;
    }

    public Locator getLastButton() {
        return lastButton;
    }
}