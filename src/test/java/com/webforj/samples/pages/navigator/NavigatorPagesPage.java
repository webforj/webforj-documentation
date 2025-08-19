package com.webforj.samples.pages.navigator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class NavigatorPagesPage extends BasePage {

    private static final String ROUTE = "navigatorpages";
    private final Locator navigatorHost;

    public NavigatorPagesPage(Page page) {
        super(page);

        this.navigatorHost = page.locator("dwc-navigator");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPageButtons() {
        return navigatorHost.locator("div[part~='layout-numbered'] > button");
    }

    public Locator getCurrentPageButton() {
        return navigatorHost.locator("button[part~='button-numbered'][aria-current='true']");
    }

    public void goToPage(int index) {
        Locator button = navigatorHost.locator("button[part~='button-numbered'][data-index='" + index + "']");
        button.click();
        com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(button)
                .hasAttribute("aria-current", "true");
    }

}