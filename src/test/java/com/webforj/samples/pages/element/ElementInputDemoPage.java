package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ElementInputDemoPage {

    private static final String ROUTE = "elementinputdemo";

    private final Locator inputField;

    public ElementInputDemoPage(Page page) {

        inputField = page.getByPlaceholder("Enter some text");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInputField() {
        return inputField;
    }
}