package com.webforj.samples.pages.columnslayout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class ColumnsLayoutSpanColumnPage extends BasePage {

    private static final String ROUTE = "columnslayoutspancolumn";

    private final Locator dwcColumnsLayout;
    private final Locator email;

    public ColumnsLayoutSpanColumnPage(Page page) {
        super(page);
        this.dwcColumnsLayout = page.locator("dwc-columns-layout");
        this.email = page.locator("dwc-field:has-text('Email')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcColumnsLayout() {
        return dwcColumnsLayout;
    }

    public Locator getEmail() {
        return email;
    }
}