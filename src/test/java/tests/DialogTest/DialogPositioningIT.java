package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;

import pages.DialogPage.DialogPositioningPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DialogPositioningIT extends BaseTest {

    private DialogPositioningPage dialogPositioningPage;

    @BeforeEach
    public void setupDialogPositioning() {
        page.navigate("https://docs.webforj.com/webforj/dialogpositioning?");
        dialogPositioningPage = new DialogPositioningPage(page);
    }

    @BrowserTest
    public void testValidValues() {
        Locator xPixels = page.locator("dwc-field[dwc-id='13'] >> input");
        Locator yPixels = page.locator("dwc-field[dwc-id='14'] >> input");
        Locator setPositionButton = page.locator("dwc-button[dwc-id='15'] >> button");

        xPixels.fill("155");
        yPixels.fill("255");
        setPositionButton.click();

        assertThat(dialogPositioningPage.getDialog()).hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posx: 155.0px;*"));
        assertThat(dialogPositioningPage.getDialog()).hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posy: 255.0px;*"));
    }

    @BrowserTest
    public void testInvalidValues() {
        Locator xPixels = page.locator("dwc-field[dwc-id='13'] >> input");
        Locator yPixels = page.locator("dwc-field[dwc-id='14'] >> input");

        xPixels.fill("0");
        yPixels.fill("-150");

        Locator yParentField = page.locator("dwc-field[dwc-id='14']");
        assertThat(yParentField).hasAttribute("invalid", "");
    }

    @BrowserTest
    public void testAlphabeticCharacters() {
        Locator xPixels = page.locator("dwc-field[dwc-id='13'] >> input");
        Locator yPixels = page.locator("dwc-field[dwc-id='14'] >> input");
        Locator setPositionButton = page.locator("dwc-button[dwc-id='15'] >> button");

        try {
            xPixels.fill("abcd");
            yPixels.fill("abcd");
            setPositionButton.click();

        } catch (PlaywrightException e) {
        }

        assertThat(dialogPositioningPage.getDialog()).not().hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posx*"));
        assertThat(dialogPositioningPage.getDialog()).not().hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posy*"));

        assertThat(xPixels).not().hasValue("abcd");
        assertThat(yPixels).not().hasValue("abcd");
    }

    @BrowserTest
    public void testSpecialCharacters() {
        Locator xPixels = page.locator("dwc-field[dwc-id='13'] >> input");
        Locator yPixels = page.locator("dwc-field[dwc-id='14'] >> input");
        Locator setPositionButton = page.locator("dwc-button[dwc-id='15'] >> button");

        try {
            xPixels.fill("#$&!");
            yPixels.fill("#$&!");
            setPositionButton.click();

        } catch (PlaywrightException e) {
        }

        assertThat(dialogPositioningPage.getDialog()).not().hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posx*"));
        assertThat(dialogPositioningPage.getDialog()).not().hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posy*"));

        assertThat(xPixels).not().hasValue("#$&!");
        assertThat(yPixels).not().hasValue("#$&!");
    }
}