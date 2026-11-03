package com.webforj.samples.unit.views.optiondialog.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.samples.views.optiondialog.message.MessageDialogTypeView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class MessageDialogTypeViewTest {
  @Test
  void shouldWarnThatTheSessionIsExpiringAgainAfterEveryClose(WebforjTestbed context) {
    assertThrows(StackOverflowError.class, () -> context.add(new MessageDialogTypeView()));

    var shown = context.getShownOptionDialogs();

    assertTrue(shown.size() > 1);
    assertEquals(ShownOptionDialog.Kind.CONFIRM, shown.get(0).getKind());
    assertEquals("Session is expiring", shown.get(0).getTitle());
    assertEquals(
        "Your session will expire in 5 minutes due to inactivity."
            + " Save your work now to prevent any data loss.",
        shown.get(0).getMessage());
    assertTrue(shown.stream().allMatch(dialog -> "Session is expiring".equals(dialog.getTitle())));
  }
}
