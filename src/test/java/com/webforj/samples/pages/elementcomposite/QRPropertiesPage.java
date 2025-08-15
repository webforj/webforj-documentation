package com.webforj.samples.pages.elementcomposite;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class QRPropertiesPage extends BasePage {

    private static final String ROUTE = "qrproperties";

    private final Locator qrPropertiesCanvas;

    public QRPropertiesPage(Page page) {
        super(page);

        qrPropertiesCanvas = page.locator("sl-qr-code >>> canvas[part='base'][class='qr-code']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getQrPropertiesCanvas() {
        return qrPropertiesCanvas;
    }
}