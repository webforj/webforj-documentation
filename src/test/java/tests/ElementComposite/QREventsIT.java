package tests.ElementComposite;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementCompositePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class QREventsIT extends BaseTest {

    private ElementCompositePage elementCompositePage;

    @BeforeEach
    public void setupQREvents() {
        page.navigate("https://docs.webforj.com/webforj/qrevent?");
        elementCompositePage = new ElementCompositePage(page);
    }

    @BrowserTest
    public void testQREvents() {
        elementCompositePage.getQrCode().click();

        assertThat(elementCompositePage.getMessageBox()).isVisible();
        assertThat(elementCompositePage.getMessageHeader()).containsText("You clicked the QR code");

    }
} 