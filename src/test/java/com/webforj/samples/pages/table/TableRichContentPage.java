package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class TableRichContentPage extends BasePage {
    private static final String ROUTE = "tablerichcontent";

    private final Locator masterCheckBox;
    private final Locator firstCheckbox;
    private final Locator images;
    private final Locator checkboxes;

    public TableRichContentPage(Page page) {
        super(page);

        this.checkboxes = page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.CHECKBOX)))
                .getByRole(AriaRole.CHECKBOX);

        this.masterCheckBox = page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.CHECKBOX)))
                .getByRole(AriaRole.CHECKBOX).nth(0);

        this.firstCheckbox = page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.CHECKBOX)))
                .getByRole(AriaRole.CHECKBOX).nth(1);

        this.images = page.getByRole(AriaRole.IMG);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCheckboxes() {
        return checkboxes;
    }

    public Locator getMasterCheckBox() {
        return masterCheckBox;
    }

    public Locator getFirstCheckbox() {
        return firstCheckbox;
    }

    public Locator getImages() {
        return images;
    }

}