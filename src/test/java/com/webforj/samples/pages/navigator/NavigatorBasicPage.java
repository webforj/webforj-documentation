package com.webforj.samples.pages.navigator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class NavigatorBasicPage extends BasePage {

    private static final String ROUTE = "navigatorbasic";

    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;
    private final Locator navigatorValue;

    public NavigatorBasicPage(Page page) {
        super(page);

        Locator shadowRoot = page.locator("dwc-navigator");
        firstButton = shadowRoot.locator("button[title='Goto first page']");
        prevButton = shadowRoot.locator("button[title='Goto previous page']");
        nextButton = shadowRoot.locator("button[title='Goto next page']");
        lastButton = shadowRoot.locator("button[title='Goto last page']");
        navigatorValue = shadowRoot.locator("div[part='area area-middle']");
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

    public Locator getNavigatorValue() {
        return navigatorValue;
    }
}