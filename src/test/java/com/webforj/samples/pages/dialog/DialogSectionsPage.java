package com.webforj.samples.pages.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DialogSectionsPage {

    private static final String ROUTE = "dialogsections";

    private final Locator header;
    private final Locator content;
    private final Locator footer;

    public DialogSectionsPage(Page page) {
        this.header = page.getByText("Header");
        this.content = page.getByText("Content");
        this.footer = page.getByText("Footer");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getHeader() {
        return header;
    }

    public Locator getContent() {
        return content;
    }

    public Locator getFooter() {
        return footer;
    }
}
