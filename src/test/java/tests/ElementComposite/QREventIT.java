package tests.elementcomposite;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.elementcomposite.QREventPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class QREventIT extends BaseTest {

    private QREventPage qrCodeEventsPage;

    @BeforeEach
    public void setupQREvents() {
        navigateToRoute(QREventPage.getRoute());
        qrCodeEventsPage = new QREventPage(page);
    }

    @BrowserTest
    public void testQREvents() {
        qrCodeEventsPage.getQrCode().click();

        assertThat(qrCodeEventsPage.getMessageBox()).isVisible();
        assertThat(qrCodeEventsPage.getMessageHeader()).containsText("You clicked the QR code");

    }
}