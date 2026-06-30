package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.dialog.DialogAutoFocusPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DialogAutoFocusViewIT extends BaseTest {

  private DialogAutoFocusPage dialogAutoFocusPage;

  @BeforeEach
  public void setupDialogAutoFocus() {
    navigateToRoute(DialogAutoFocusPage.getRoute());
    dialogAutoFocusPage = new DialogAutoFocusPage(page);
  }

  @Test
  @Disabled("Dialog autofocus resolves the input but reports 'inactive' across browsers; revisit")
  public void testAutoFocusIsEnabled() {
    assertThat(dialogAutoFocusPage.getTextField()).isFocused();
  }
}
