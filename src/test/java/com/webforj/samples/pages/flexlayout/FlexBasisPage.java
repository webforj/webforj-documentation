package com.webforj.samples.pages.flexlayout;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class FlexBasisPage {

    private static final String ROUTE = "flexbasis";

    private final Locator setBasisButton;
    private final Locator numberField;
    private final Locator box1;

    public FlexBasisPage(Page page) {
        this.setBasisButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Set basis"));
        this.numberField = page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Basis •"));
        this.box1 = page.locator("dwc-button").filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Box 1"))));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getSetBasisButton() {
        return setBasisButton;
    }

    public Locator getNumberField() {
        return numberField;
    }

    public Locator getBox1() {
        return box1;
    }

}