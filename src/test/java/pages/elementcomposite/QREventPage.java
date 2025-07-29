package pages.elementcomposite;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import pages.BasePage;

public class QREventPage extends BasePage {

    private static final String ROUTE = "qrevent";

    private final Locator qrCode;
    private final Locator messageBox;
    private final Locator messageHeader;

    public QREventPage(Page page) {
        super(page);

        qrCode = page.locator("sl-qr-code >> canvas");
        messageBox = page.locator("dwc-dialog");
        messageHeader = messageBox.locator("header");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getQrCode() {
        return qrCode;
    }

    public Locator getMessageBox() {
        return messageBox;
    }

    public Locator getMessageHeader() {
        return messageHeader;
    }
}