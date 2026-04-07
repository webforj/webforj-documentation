package com.webforj.samples.pages.debouncer;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DebouncerPage {

    private static final String ROUTE = "debouncer/";

    private final Locator input;
    private final Locator output;
    private final Page page;

    public DebouncerPage(Page page) {
        this.page = page;
        this.input = page.getByLabel("Type something");
        this.output = page.getByLabel("Debounced output");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInput() {
        return input;
    }

    public Locator getOutput() {
        return output;
    }

    public Locator getInputHelperText() {
        return page.getByText(Pattern.compile("Key events: \\d+"));
    }

    public void waitForTimeout(int timeout) {
        page.waitForTimeout(timeout);
    }
}
