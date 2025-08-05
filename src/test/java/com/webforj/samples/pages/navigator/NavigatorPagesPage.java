package com.webforj.samples.pages.navigator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigatorPagesPage extends BasePage {

    private static final String ROUTE = "navigatorpages";

    public NavigatorPagesPage(Page page) {
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