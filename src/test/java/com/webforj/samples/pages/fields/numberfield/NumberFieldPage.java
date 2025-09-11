package com.webforj.samples.pages.fields.numberfield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NumberFieldPage {

    private static final String ROUTE = "numberfield";

    private final Locator numberField;

    public NumberFieldPage(Page page) {

        numberField = page.getByLabel("Quantity");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNumberField() {
        return numberField;
    }
}