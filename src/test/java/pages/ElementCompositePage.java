package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ElementCompositePage extends BasePage {

    private final Locator qrCodeCanvas;
    private final Locator qrCodeElement;

    private final Locator qrCode;
    private final Locator messageBox;
    private final Locator messageHeader;

    private final Locator qrPropertiesCanvas;

    public ElementCompositePage(Page page) {
        super(page);

        qrCodeCanvas = page.locator("sl-qr-code[dwc-id='12'] >> canvas");
        qrCodeElement = page.locator("sl-qr-code[dwc-id='12']");

        qrCode = page.locator("sl-qr-code[dwc-id='12'] >> canvas");
        messageBox = page.locator("dwc-dialog");
        messageHeader = messageBox.locator("header");

        qrPropertiesCanvas = page.locator("sl-qr-code[dwc-id='11'] >> canvas");
    }

    public Locator getQrCodeCanvas() {
        return qrCodeCanvas;
    }

    public Locator getQrCodeElement() {
        return qrCodeElement;
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

    public Locator getQrPropertiesCanvas() {
        return qrPropertiesCanvas;
    }
} 