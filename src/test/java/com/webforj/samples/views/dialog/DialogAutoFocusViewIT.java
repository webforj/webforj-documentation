package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.pages.dialog.DialogAutoFocusPage;
import com.webforj.samples.views.BaseTest;

public class DialogAutoFocusViewIT extends BaseTest {

    private DialogAutoFocusPage dialogAutoFocusPage;

    @BeforeEach
    public void setupDialogAutoFocus() {
        navigateToRoute(DialogAutoFocusPage.getRoute());
        dialogAutoFocusPage = new DialogAutoFocusPage(page);
    }

    @Test
    public void testAutoFocusIsEnabled() {
        assertThat(dialogAutoFocusPage.getTextField()).isFocused();
    }

}
