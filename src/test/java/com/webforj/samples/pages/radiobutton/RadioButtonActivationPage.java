package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonActivationPage extends BasePage {

    private static final String ROUTE = "radiobuttonactivation";

    private final Locator checkedAutoInput;
    private final Locator checkedAutoHost;

    public RadioButtonActivationPage(Page page) {
        super(page);

        this.checkedAutoHost = page.locator("dwc-radio[checked]")
                .filter(new Locator.FilterOptions()
                        .setHas(page.locator("label:has-text('Auto Activated')")));

        this.checkedAutoInput = checkedAutoHost.locator("input[part~='input']:visible");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCheckedAutoInput() {
        return checkedAutoInput;
    }
}