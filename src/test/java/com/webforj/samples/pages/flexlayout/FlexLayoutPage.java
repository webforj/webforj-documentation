package com.webforj.samples.pages.flexlayout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class FlexLayoutPage {

    private static final String ROUTE = "flexlayout";

    private final Locator cityField;
    private final Locator zipField;

    public FlexLayoutPage(Page page) {

        this.cityField = page.locator("dwc-field").filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("City"))));
        this.zipField = page.locator("dwc-numberfield").filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Zip"))));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCityField() {
        return cityField;
    }

    public Locator getZipField() {
        return zipField;
    }
}
