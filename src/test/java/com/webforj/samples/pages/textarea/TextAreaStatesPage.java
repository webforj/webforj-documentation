package com.webforj.samples.pages.textarea;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TextAreaStatesPage {

    private static final String ROUTE = "textareastates";

    private final Locator readonlyArea;
    private final Locator disabledArea;

    public TextAreaStatesPage(Page page) {
        this.readonlyArea = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Read-Only"));
        this.disabledArea = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Disabled"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getReadonlyArea() {
        return readonlyArea;
    }

    public Locator getDisabledArea() {
        return disabledArea;
    }
}
