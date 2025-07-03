package pages.ElementCompositePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class QRCodeBasicPage extends BasePage {

    private static final String ROUTE = RouteConfig.QR_CODE;

    private final Locator qrCodeCanvas;
    private final Locator qrCodeElement;

    public QRCodeBasicPage(Page page) {
        super(page);

        qrCodeCanvas = page.locator("sl-qr-code >> canvas");
        qrCodeElement = page.locator("sl-qr-code");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getQrCodeCanvas() {
        return qrCodeCanvas;
    }

    public Locator getQrCodeElement() {
        return qrCodeElement;
    }
}