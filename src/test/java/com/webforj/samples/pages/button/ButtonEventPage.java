package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class ButtonEventPage {

    private static final String ROUTE = "buttonevent";

    private final Locator button;
    private final Page page;

    public ButtonEventPage(Page page) {
        this.button = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Click Me!"));
        this.page = page;
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getButton() {
        return button;
    }

    public Locator getCounterText(String text) {
        return page.getByText("Current Counter: " + text);
    }
}
