package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableSingleSelectionPage extends BasePage {
    private static final String ROUTE = "tablesingleselection";

    private final Locator firstArtist;

    public TableSingleSelectionPage(Page page) {
        super(page);

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