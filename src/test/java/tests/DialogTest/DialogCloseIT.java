package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DialogPage.DialogClosePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DialogCloseIT extends BaseTest {

    private DialogClosePage dialogClosePage;

    @BeforeEach
    public void setDialogClose() {
        navigateToRoute(DialogClosePage.getRoute());
        dialogClosePage = new DialogClosePage(page);
    }

    @BrowserTest
    public void testDialogClose() {
        assertThat(dialogClosePage.getDialogHeader()).hasText("Closing the Dialog");

        dialogClosePage.getCloseDialog().click();
        assertThat(dialogClosePage.getDialogPart()).hasAttribute("aria-hidden", "true");

        dialogClosePage.getShowDialog().click();
        assertThat(dialogClosePage.getDialogPart()).hasAttribute("aria-hidden", "false");
    }
} 