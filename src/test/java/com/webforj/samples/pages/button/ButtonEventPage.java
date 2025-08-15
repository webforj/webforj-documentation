package com.webforj.samples.pages.button;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ButtonEventPage extends BasePage {

    private static final String ROUTE = "buttonevent";

    private final Locator clickMeButton;
    private final Locator counter;

    public ButtonEventPage(Page page) {
        super(page);

        this.clickMeButton = page.locator("dwc-button:has-text('Click Me!')").locator("button");
        this.counter = page.getByText(Pattern.compile("^\\s*Current Counter:\\s*\\d+\\s*$")).first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getClickMeButton() {
        return clickMeButton;
    }

    public Locator getCounter() {
        return counter;
    }
}
