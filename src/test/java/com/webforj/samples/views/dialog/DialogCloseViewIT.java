package com.webforj.samples.views.dialog;

import com.webforj.samples.views.BaseTest;
import com.webforj.samples.pages.dialog.DialogClosePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DialogCloseViewIT extends BaseTest {

    private DialogClosePage dialogClosePage;

    @BeforeEach
    public void setupDialogClose() {
        navigateToRoute(DialogClosePage.getRoute());
        dialogClosePage = new DialogClosePage(page);
    }

    @Test
    public void testDialogClose() {
        assertThat(dialogClosePage.getDialog()).isVisible();

        dialogClosePage.getCloseDialogButton().click();
        assertThat(dialogClosePage.getDialog()).not().isVisible();

        dialogClosePage.getShowDialogButton().click();
        assertThat(dialogClosePage.getDialog()).isVisible();
    }

}
