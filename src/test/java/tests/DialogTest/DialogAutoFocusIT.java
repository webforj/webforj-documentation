package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DialogPage.DialogAutoFocusPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DialogAutoFocusIT extends BaseTest {

    private DialogAutoFocusPage dialogAutoFocusPage;

    @BeforeEach
    public void setupDialogAutoFocus() {
        navigateToRoute(DialogAutoFocusPage.getRoute());
        dialogAutoFocusPage = new DialogAutoFocusPage(page);
    }

    @BrowserTest
    public void testAutoFocus() {
        assertThat(dialogAutoFocusPage.getDialogBox()).hasAttribute("has-focus", "");
    }
} 