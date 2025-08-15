package com.webforj.samples.pages.navigator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigatorTablePage extends BasePage {

    private static final String ROUTE = "navigatortable";

    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;
    private final Locator shadowRootNavigator;
    private final Locator shadowRootTable;

    public NavigatorTablePage(Page page) {
        super(page);

        this.shadowRootTable = page.locator("dwc-table");
        this.shadowRootNavigator = page.locator("dwc-navigator");
        this.firstButton = shadowRootNavigator.locator("button[title='Goto first page']");
        this.prevButton = shadowRootNavigator.locator("button[title='Goto previous page']");
        this.nextButton = shadowRootNavigator.locator("button[title='Goto next page']");
        this.lastButton = shadowRootNavigator.locator("button[title='Goto last page']");
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
        Locator pageButton = shadowRootNavigator.locator("button[title='Goto page " + pageNumber + "']");
        assertThat(pageButton).hasAttribute("aria-current", "true");
    }

    public void assertCurrentPageData(int pageNumber, String expectedData) {
        Locator firstDataCell = shadowRootTable.locator("tbody tr:nth-child(2) td:nth-child(1)");
        String actualData = firstDataCell.innerText().trim();
        assertEquals(expectedData, actualData,
                "First data cell should contain: " + expectedData);
    }

    public void goToPage(int pageNumber) {
        Locator pageButton = shadowRootNavigator.locator("button[title='Goto page " + pageNumber + "']");
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