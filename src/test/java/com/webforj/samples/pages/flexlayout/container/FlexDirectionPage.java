package com.webforj.samples.pages.flexlayout.container;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class FlexDirectionPage {

    private static final String ROUTE = "flexdirection";

    private final Page page;
    private final Locator flexDirectionDropdown;
    private final Locator flexDirectionContainer;

    public FlexDirectionPage(Page page) {
        this.page = page;

        this.flexDirectionDropdown = page.getByRole(AriaRole.BUTTON).filter().getByLabel("");
        this.flexDirectionContainer = page.locator(".button__container--single-row");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFlexDirectionDropdown() {
        return flexDirectionDropdown;
    }

    public Locator getFlexDirectionContainer() {
        return flexDirectionContainer;
    }

    public Locator getListBox(String text) {
        return page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(text).setExact(true));
    }
}