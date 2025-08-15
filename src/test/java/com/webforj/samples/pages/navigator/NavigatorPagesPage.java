package com.webforj.samples.pages.navigator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigatorPagesPage extends BasePage {

    private static final String ROUTE = "navigatorpages";
    private final Locator shadowRoot;

    public NavigatorPagesPage(Page page) {
        super(page);

        this.shadowRoot = page.locator("dwc-navigator");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public void assertCurrentPage(int pageNumber) {
        Locator pageButton = shadowRoot.locator("button[title='Goto page " + pageNumber + "']");
        assertThat(pageButton).hasAttribute("aria-current", "true");
    }

    public void goToPage(int pageNumber) {
        Locator pageButton = shadowRoot.locator("button[title='Goto page " + pageNumber + "']");
        pageButton.click();
    }

    public Locator getEllipsisButtons() {
        return shadowRoot.locator("div[part='layout layout-numbered'] > button");
    }

}