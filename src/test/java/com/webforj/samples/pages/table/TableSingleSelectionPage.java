package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TableSingleSelectionPage {
    private static final String ROUTE = "tablesingleselection";

    private final Page page;
    private final Locator firstArtist;

    public TableSingleSelectionPage(Page page) {
        this.page = page;
        this.firstArtist = page.getByText("Mississippi Blues");

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFirstArtist() {
        return firstArtist;
    }

    public Locator getDialogMessage(String title, String artist) {
        return page.getByText("You have selected " + title + " by " + artist);
    }
}