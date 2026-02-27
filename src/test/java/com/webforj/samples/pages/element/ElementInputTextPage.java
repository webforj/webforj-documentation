package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

    public class ElementInputTextPage {

    private static final String ROUTE = "elementinputtext";

    private final Locator inputField;

    public ElementInputTextPage(Page page) {
        this.inputField = page.getByRole(AriaRole.TEXTBOX);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInputField() {
        return inputField;
    }
}
