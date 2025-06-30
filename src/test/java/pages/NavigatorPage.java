package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import utils.WaitUtil;

public class NavigatorPage extends BasePage {
    private final Locator navigator;
    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;
    private final Locator navigatorValue;

    public NavigatorPage(Page page) {
        super(page);

        this.navigator = page.locator("dwc-navigator[dwc-id='13']");
        this.firstButton = page.locator("button[title='Goto first page']");
        this.prevButton = page.locator("button[title='Goto previous page']");
        this.nextButton = page.locator("button[title='Goto next page']");
        this.lastButton = page.locator("button[title='Goto last page']");
        this.navigatorValue = page.locator("div[part='area area-middle']");

    }

    public void waitForVisiblePaginator() {
        WaitUtil.waitForVisible(navigator);
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

    public Locator getEllipsisButtons() {
        return page.locator("div[part='layout layout-numbered'] > button");
    }

    public Locator getPageRangeMessage() {
        return page.locator("div[dwc-id='10'] > p");
    }

    public Locator getFirstButton() {
        return this.firstButton;
    }

    public Locator getPrevButton() {
        return this.prevButton;
    }

    public Locator getNextButton() {
        return this.nextButton;
    }

    public Locator getLastButton() {
        return this.lastButton;
    }

    public Locator getNavigatorValue() {
        return this.navigatorValue;
    }
}