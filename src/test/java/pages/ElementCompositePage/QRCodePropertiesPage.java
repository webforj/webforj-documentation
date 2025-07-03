package pages.ElementCompositePage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;

import pages.BasePage;

public class QRCodePropertiesPage extends BasePage {

    private static final String ROUTE = RouteConfig.QR_CODE_PROPERTIES;

    private final Locator qrPropertiesCanvas;

    public QRCodePropertiesPage(Page page) {
        super(page);

        qrPropertiesCanvas = page.locator("sl-qr-code >> canvas");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getQrPropertiesCanvas() {
        return qrPropertiesCanvas;
    }
}