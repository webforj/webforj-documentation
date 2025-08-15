package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonGroupPage extends BasePage {

    private static final String ROUTE = "radiobuttongroup";

    private final Locator stronglyDisagreeRadioButton;
    private final Locator disagreeRadioButton;
    private final Locator shadowRootDisagreeRadio;
    private final Locator shadowRootStronglyDisagreeRadio;

    public RadioButtonGroupPage(Page page) {
        super(page);

        this.shadowRootStronglyDisagreeRadio = page.locator("dwc-radio").nth(0);
        this.stronglyDisagreeRadioButton = shadowRootStronglyDisagreeRadio
                .locator("label:has-text('Strongly disagree')");
        this.shadowRootDisagreeRadio = page.locator("dwc-radio").nth(1);
        this.disagreeRadioButton = shadowRootDisagreeRadio.locator("label:text-is('Disagree')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getStronglyDisagreeRadioButton() {
        return stronglyDisagreeRadioButton;
    }

    public Locator getDisagreeRadioButton() {
        return disagreeRadioButton;
    }
}