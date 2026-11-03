package com.webforj.samples.unit.views.dialog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.dialog.DialogAutoWidthView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class DialogAutoWidthViewTest {
  @Test
  void shouldSizeTheDialogToItsContentOnlyWhileTheSwitchIsOn(WebforjTestbed context) {
    context.add(new DialogAutoWidthView());

    var dialog = ComponentLocator.of(Dialog.class).locate();
    var fitContent = ComponentLocator.of(RadioButton.class).byText("Fit content width").locate();

    assertTrue(dialog.isAutoWidth());

    UserInteraction.uncheck(fitContent);
    assertFalse(dialog.isAutoWidth());

    UserInteraction.check(fitContent);
    assertTrue(dialog.isAutoWidth());
  }
}
