package com.webforj.samples.pages.navigator;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NavigatorBasicPage {

    private static final String ROUTE = "navigatorbasic";

    private final Page page;

    private final Locator firstButton;
    private final Locator prevButton;
    private final Locator nextButton;
    private final Locator lastButton;

    public NavigatorBasicPage(Page page) {
        this.page = page;
        
        firstButton = page.getByLabel("Goto first page");
        prevButton = page.getByLabel("Goto previous page");
        nextButton = page.getByLabel("Goto next page");
        lastButton = page.getByLabel("Goto last page");
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

    public Locator navigatorValue(int n) {
        Pattern exact = Pattern.compile("^\\s*Value:\\s*" + n + "\\s*$");
        return page.getByText(exact);
    }
}