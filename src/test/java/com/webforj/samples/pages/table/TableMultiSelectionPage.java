package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class TableMultiSelectionPage {

    private static final String ROUTE = "tablemultiselection";

    private final Locator masterCheckbox;
    private final Locator rowCheckboxes;
    private final Locator recordItems;
    private final Locator okButton;
    private final Locator noRecordsMessage;

    public TableMultiSelectionPage(Page page) {

        this.masterCheckbox = page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.CHECKBOX)))
                .getByRole(AriaRole.CHECKBOX).nth(0);

        this.rowCheckboxes = page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHas(page.getByRole(AriaRole.CHECKBOX)))
                .getByRole(AriaRole.CHECKBOX);

        this.recordItems = page.getByRole(AriaRole.LIST).getByRole(AriaRole.LISTITEM);

        this.okButton = page.getByRole(AriaRole.CONTENTINFO)
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("OK"));

        this.noRecordsMessage = page.getByText("There are no records selected");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMasterCheckbox() {
        return masterCheckbox;
    }

    public Locator getRowCheckboxes() {
        return rowCheckboxes;
    }

    public Locator getRecordItems() {
        return recordItems;
    }

    public Locator getOkButton() {
        return okButton;
    }

    public Locator getNoRecordsMessage() {
        return noRecordsMessage;
    }
}