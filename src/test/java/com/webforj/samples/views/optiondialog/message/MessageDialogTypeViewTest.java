package com.webforj.samples.views.optiondialog.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MessageDialogTypeViewTest {
  @Test
  void shouldWarnThatTheSessionIsExpiring(WebforjTestbed context) {
    context.add(new MessageDialogTypeView());

    var shown = context.getShownOptionDialogs();

    assertEquals(1, shown.size());
    assertEquals(ShownOptionDialog.Kind.CONFIRM, shown.getFirst().getKind());
    assertEquals("Session is expiring", shown.getFirst().getTitle());
    assertEquals(
        "Your session will expire in 5 minutes due to inactivity."
            + " Save your work now to prevent any data loss.",
        shown.getFirst().getMessage());
  }
}
