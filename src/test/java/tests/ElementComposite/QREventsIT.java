package tests.ElementComposite;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementCompositePage.QRCodeEventsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class QREventsIT extends BaseTest {

    private QRCodeEventsPage qrCodeEventsPage;

    @BeforeEach
    public void setupQREvents() {
        navigateToRoute(QRCodeEventsPage.getRoute());
        qrCodeEventsPage = new QRCodeEventsPage(page);
    }

    @BrowserTest
    public void testQREvents() {
        qrCodeEventsPage.getQrCode().click();

        assertThat(qrCodeEventsPage.getMessageBox()).isVisible();
        assertThat(qrCodeEventsPage.getMessageHeader()).containsText("You clicked the QR code");

    }
}