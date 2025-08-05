package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonGroupPage extends BasePage {

    private static final String ROUTE = "radiobuttongroup";

    private final Locator stronglyDisagreeRB;
    private final Locator disagreeRB;
    private final Locator neutralRB;
    private final Locator agreeRB;
    private final Locator stronglyAgreeRB;

    public RadioButtonGroupPage(Page page) {
        super(page);

        stronglyDisagreeRB = page.locator("label:has-text('Strongly disagree')");
        disagreeRB = page.locator("label:text-is('Disagree')");
        neutralRB = page.locator("label:has-text('Neutral')");
        agreeRB = page.locator("label:text-is('Agree')");
        stronglyAgreeRB = page.locator("label:has-text('Strongly agree')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getStronglyDisagreeRB() {
        return stronglyDisagreeRB;
    }

    public Locator getDisagreeRB() {
        return disagreeRB;
    }

    public Locator getNeutralRB() {
        return neutralRB;
    }

    public Locator getAgreeRB() {
        return agreeRB;
    }

    public Locator getStronglyAgreeRB() {
        return stronglyAgreeRB;
    }
}