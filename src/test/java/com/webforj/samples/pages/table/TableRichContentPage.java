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

    public TableRichContentPage(Page page) {
        super(page);

        masterCheckBox = page.locator("thead input[type='checkbox']");
        firstCheckbox = page.locator("dwc-checkbox").first();
        checkboxInput = firstCheckbox.locator("input[type='checkbox']");
        images = page.locator("img[part='avatar-img']");
    }

    public static String getRoute() {
        return ROUTE;
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