package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ElementInputDemoPage extends BasePage {

    private static final String ROUTE = "elementinputdemo";

    private final Locator input;

    public ElementInputDemoPage(Page page) {
        super(page);

        input = page.locator("input.element--input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInput() {
        return input;
    }
}