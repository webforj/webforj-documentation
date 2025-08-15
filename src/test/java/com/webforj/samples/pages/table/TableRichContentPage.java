package com.webforj.samples.pages.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class TableRichContentPage extends BasePage {
    private static final String ROUTE = "tablerichcontent";

    private final Locator masterCheckBox;
    private final Locator firstCheckbox;
    private final Locator checkboxInput;
    private final Locator images;
    private final Locator checkboxes;
    private final Locator tableHost;

    public TableRichContentPage(Page page) {
        super(page);

        this.tableHost = page.locator("dwc-table");

        this.checkboxes = tableHost.locator("dwc-checkbox").locator("input[type='checkbox']");
        this.masterCheckBox = tableHost.locator("dwc-checkbox").locator("#checkbox-1");
        this.firstCheckbox = tableHost.locator("dwc-checkbox").first();
        this.checkboxInput = firstCheckbox.locator("input[type='checkbox']");
        this.images = tableHost.locator("img[part='avatar-img']");
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

    public Locator getCheckboxInput() {
        return checkboxInput;
    }

    public Locator getImages() {
        return images;
    }

}