package tests.ElementComposite;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import pages.ElementCompositePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class QRPropertiesIT extends BaseTest {

    private ElementCompositePage elementCompositePage;

    private static String decodeQRCode(byte[] imageData) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();

    }

    @BeforeEach
    public void setupQRProperties() {
        page.navigate("https://docs.webforj.com/webforj/qrproperties?");
        elementCompositePage = new ElementCompositePage(page);
    }

    @BrowserTest
    public void testQRProperties() throws IOException, NotFoundException {
        byte[] screenshot = elementCompositePage.getQrPropertiesCanvas().screenshot();

        String decodedText = decodeQRCode(screenshot);

        assertEquals("https://www.webforj.com", decodedText);
        assertThat(elementCompositePage.getQrPropertiesCanvas()).hasAttribute("style", "width:200px;height:200px;");
    }
} 