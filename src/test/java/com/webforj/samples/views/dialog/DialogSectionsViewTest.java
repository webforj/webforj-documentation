package com.webforj.samples.views.dialog;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.dialog.Dialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DialogSectionsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new DialogSectionsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldCloseTheDialogFromEitherFooterAction(WebforjTestbed context) {
    context.add(new DialogSectionsView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var editProfile = ComponentLocator.of(Button.class).byText("Edit profile").locate();

    UserInteraction.click(
        ComponentLocator.of(Button.class).within(dialog).byText("Cancel").locate());
    assertFalse(dialog.isOpened());

    UserInteraction.click(editProfile);
    assertTrue(dialog.isOpened());

    UserInteraction.click(
        ComponentLocator.of(Button.class).within(dialog).byText("Save changes").locate());
    assertFalse(dialog.isOpened());
  }
}
