package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.DialogPage.DialogBackdropBlurPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class DialogBackdropBlurIT extends BaseTest {

    private DialogBackdropBlurPage dialogBackdropBlurPage;

    @BeforeEach
    public void setupDialogBackdropBlur() {
        page.navigate("https://docs.webforj.com/webforj/dialogbackdropblur?");
        dialogBackdropBlurPage = new DialogBackdropBlurPage(page);
    }

    @BrowserTest
    public void testDialogBackdropBlur() {
        Locator blurButton = dialogBackdropBlurPage.getDialog().locator("dwc-button");

        WaitUtil.waitForVisible(blurButton);

        blurButton.click();
        assertThat(dialogBackdropBlurPage.getDialog()).hasAttribute("blurred", "");

        blurButton.click();
        assertThat(dialogBackdropBlurPage.getDialog()).not().hasAttribute("blurred", "");
    }
} 