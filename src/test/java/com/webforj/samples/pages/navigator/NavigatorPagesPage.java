package com.webforj.samples.pages.navigator;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NavigatorPagesPage {

    private static final String ROUTE = "navigatorpages";

    private final Page page;
    public NavigatorPagesPage(Page page) {
        this.page = page;
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator navigatorValue(int n) {
        String regex = String.format("^\\s*Goto page %d\\s*$", n);
        return page.getByLabel(Pattern.compile(regex));
    }

    public Locator showingRange(int from, int to) {
        String regex = String.format("^\\s*Showing %d to %d of 100\\s*$", from, to);
        return page.getByText(Pattern.compile(regex));
    }
}