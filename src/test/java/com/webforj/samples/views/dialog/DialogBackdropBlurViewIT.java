package com.webforj.samples.views.dialog;

import com.webforj.samples.pages.dialog.DialogBackdropBlurPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DialogBackdropBlurViewIT extends BaseTest {

    private DialogBackdropBlurPage dialogBackdropBlurPage;

    @BeforeEach
    public void setupDialogBackdropBlur() {
        navigateToRoute(DialogBackdropBlurPage.getRoute());
        dialogBackdropBlurPage = new DialogBackdropBlurPage(page);
    }

    @Test
    public void testDialogBackdropBlur() {
        dialogBackdropBlurPage.getBackgroundBlur().click();
        assertThat(dialogBackdropBlurPage.getDwcDialog()).hasAttribute("blurred", "");
    }

}
