package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class CheckboxHorizontalTextPage extends BasePage {

    private static final String ROUTE = "checkboxhorizontaltext";

    private final Locator leftAlignedCheckbox;
    private final Locator monthlyCheckboxLabel;
    private final Locator monthlyCheckboxInput;

    public CheckboxHorizontalTextPage(Page page) {
        super(page);

        leftAlignedCheckbox = page.locator("dwc-checkbox.bbj-reverse-order:has-text('Daily')");
        monthlyCheckboxLabel = page.locator("dwc-checkbox.bbj-reverse-order:has-text('Monthly') >> label[part='label']");
        monthlyCheckboxInput = page.locator("dwc-checkbox.bbj-reverse-order:has-text('Monthly') >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getLeftAlignedCheckbox() {
        return leftAlignedCheckbox;
    }

    public Locator getMonthlyCheckboxLabel() {
        return monthlyCheckboxLabel;
    }

    public Locator getMonthlyCheckboxInput() {
        return monthlyCheckboxInput;
    }
}