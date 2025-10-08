package com.webforj.samples.pages.spinner;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SpinnerDirectionDemoPage {

    private static final String ROUTE = "spinnerdirectiondemo";

    private final Locator spinner;
    private final Locator clockwiseButton;
    private final Locator counterClockwiseButton;

    public SpinnerDirectionDemoPage(Page page) {

        this.spinner = page.locator("dwc-spinner");
        this.clockwiseButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName(Pattern.compile("^Clockwise$", Pattern.CASE_INSENSITIVE)));
        this.counterClockwiseButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName(Pattern.compile("^Counter\\s*-?\\s*Clockwise$", Pattern.CASE_INSENSITIVE)));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSpinner() {
        return spinner;
    }

    public Locator getClockwiseButton() {
        return clockwiseButton;
    }

    public Locator getCounterClockwiseButton() {
        return counterClockwiseButton;
    }
}