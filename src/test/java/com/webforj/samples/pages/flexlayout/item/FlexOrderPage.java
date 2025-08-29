package com.webforj.samples.pages.flexlayout.item;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class FlexOrderPage extends BasePage {

    private static final String ROUTE = "flexorder";

    private final Locator setOrderButton;

    public FlexOrderPage(Page page) {
        super(page);

        this.setOrderButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Set Order"));

    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSetOrderButton() {
        return setOrderButton;
    }

    public Locator buttonValue(int n) {
        Pattern exact = Pattern.compile("^\\s*Order:\\s*" + n + "\\s*$");
        return page.getByText(exact);
    }
}