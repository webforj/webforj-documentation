package com.webforj.samples.pages.fields.numberfield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class NumberFieldPage extends BasePage {

    private static final String ROUTE = "numberfield";

    private final Locator numberField;

    public NumberFieldPage(Page page) {
        super(page);

        Locator shadowRootNumberField = page.locator("dwc-field[type='number']");
        numberField = shadowRootNumberField.locator("input#field-1");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getNumberField() {
        return numberField;
    }
}