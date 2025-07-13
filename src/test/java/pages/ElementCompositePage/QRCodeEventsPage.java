package pages.ElementCompositePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class QRCodeEventsPage extends BasePage {

    private static final String ROUTE = RouteConfig.QR_CODE_EVENTS;

    private final Locator qrCode;
    private final Locator messageBox;
    private final Locator messageHeader;

    public QRCodeEventsPage(Page page) {
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