package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class CheckboxIndeterminatePage extends BasePage {

    private static final String ROUTE = "checkboxindeterminate";

    private final Locator parentCheckbox;
    private final Locator child1CheckboxInput;
    private final Locator child2CheckboxInput;

    public CheckboxIndeterminatePage(Page page) {
        super(page);

        parentCheckbox = page.locator("dwc-checkbox:has-text('Parent')");
        child1CheckboxInput = page.locator("dwc-checkbox:has-text('Child 1') >> input");
        child2CheckboxInput = page.locator("dwc-checkbox:has-text('Child 2') >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getParentCheckbox() {
        return parentCheckbox;
    }

    public Locator getChild1CheckboxInput() {
        return child1CheckboxInput;
    }

    public Locator getChild2CheckboxInput() {
        return child2CheckboxInput;
    }
}