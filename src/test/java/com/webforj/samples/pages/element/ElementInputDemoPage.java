package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ElementInputDemoPage extends BasePage {

    private static final String ROUTE = "elementinputdemo";

    private final Locator inputField;

    public ElementInputDemoPage(Page page) {
        super(page);

        inputField = page.getByPlaceholder("Enter some text");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInputField() {
        return inputField;
    }
}