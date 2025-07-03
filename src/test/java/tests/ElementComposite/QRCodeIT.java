package tests.ElementComposite;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementCompositePage.QRCodeBasicPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class QRCodeIT extends BaseTest {

    private QRCodeBasicPage qrCodeBasicPage;

    @BeforeEach
    public void setupQRCodePage() {
        navigateToRoute(QRCodeBasicPage.getRoute());
        qrCodeBasicPage = new QRCodeBasicPage(page);
    }

    @BrowserTest
    public void testQRCode() {
        assertThat(qrCodeBasicPage.getQrCodeCanvas()).isVisible();
    }
}