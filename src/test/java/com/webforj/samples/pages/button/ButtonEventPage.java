package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class ButtonEventPage extends BasePage {

    private static final String ROUTE = "buttonevent";

    private final Locator clickMeButton;
    private final Locator firstClickCounter;

    public ButtonEventPage(Page page) {
        super(page);

        this.clickMeButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Click Me"));
        this.firstClickCounter = page.getByText("Current Counter: 1");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getClickMeButton() {
        return clickMeButton;
    }

    public Locator getFirstClickCounter() {
        return firstClickCounter;
    }
}
