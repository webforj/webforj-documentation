package tests.ElementComposite;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementCompositePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class QRCodeIT extends BaseTest {

    private ElementCompositePage elementCompositePage;

    @BeforeEach
    public void setupQRCodePage() {
        page.navigate("https://docs.webforj.com/webforj/qrdemo?");
        elementCompositePage = new ElementCompositePage(page);
    }

    @BrowserTest
    public void testQRCode() {
        assertThat(elementCompositePage.getQrCodeCanvas()).isVisible();
    }
} 