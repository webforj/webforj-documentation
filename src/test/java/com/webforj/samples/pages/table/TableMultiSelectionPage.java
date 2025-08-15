package com.webforj.samples.pages.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import com.webforj.samples.pages.BasePage;

public class TableMultiSelectionPage extends BasePage {

    private static final String ROUTE = "tablemultiselection";

    private final Locator masterCheckbox;
    private final Locator checkboxes;
    private final Locator recordItems;
    private final Locator okButton;
    private final Locator noRecordsMessage;
    private final Locator tableHost;
    private final Locator dialogHost;

    public TableMultiSelectionPage(Page page) {
        super(page);
        pageTitle = "Table Multiple Selection";

        this.tableHost = page.locator("dwc-table");
        this.dialogHost = page.locator("dwc-dialog");

        this.masterCheckbox = tableHost.locator("dwc-checkbox").locator("#checkbox-1");
        this.checkboxes = tableHost.locator("dwc-checkbox").locator("input[type='checkbox']");
        this.recordItems = dialogHost.locator("ul li");
        this.okButton = dialogHost.getByRole(AriaRole.CONTENTINFO).getByText("OK");
        this.noRecordsMessage = dialogHost.locator("text='There are no records selected'");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public void verifyRecordItems(String text, boolean shouldBeVisible) {
        Locator recordItem = page.locator("dwc-dialog ul li:has-text('" + text + "')");

        if (shouldBeVisible) {
            assertThat(recordItem).isVisible();
        } else {
            assertThat(recordItem).not().isVisible();
        }
    }

    public void verifyRecordItems(String text) {
        verifyRecordItems(text, true);
    }

    public Locator getMasterCheckbox() {
        return masterCheckbox;
    }

    public Locator getCheckboxes() {
        return checkboxes;
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