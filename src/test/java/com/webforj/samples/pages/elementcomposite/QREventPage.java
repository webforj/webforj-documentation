package com.webforj.samples.pages.elementcomposite;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class QREventPage extends BasePage {

    private static final String ROUTE = "qrevent";

    private final Locator qrCode;
    private final Locator messageHeader;
    private final Locator dialogBox;

    public QREventPage(Page page) {
        super(page);

        Locator shadowRootQRCode = page.locator("sl-qr-code");
        qrCode = shadowRootQRCode.locator("canvas");
        dialogBox = page.locator("dwc-dialog[type='msgbox']");
        messageHeader = dialogBox.locator("header");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getQrCode() {
        return qrCode;
    }

    public Locator getDialogBox() {
        return dialogBox;
    }

    public Locator getMessageHeader() {
        return messageHeader;
    }
}