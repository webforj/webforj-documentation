package com.webforj.samples.unit.views.optiondialog.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.samples.views.optiondialog.input.InputDialogTypeView;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@WebforjTest
class InputDialogTypeViewTest {
  @Test
  void shouldGrantAccessForAPassword(WebforjTestbed context) {
    context.answerInputDialog("secret");
    var reopened =
        assertThrows(IllegalStateException.class, () -> context.add(new InputDialogTypeView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(3, shown.size());
    assertEquals(shown.get(0).getTitle(), shown.get(2).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
    assertEquals(ShownOptionDialog.Kind.INPUT, shown.get(0).getKind());
    assertEquals("Restricted access.", shown.get(0).getTitle());
    assertEquals("Welcome", shown.get(1).getTitle());
    assertEquals("Access granted", shown.get(1).getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDenyAccessForACancelledOrEmptyPassword(String password, WebforjTestbed context) {
    context.answerInputDialog(password);
    var reopened =
        assertThrows(IllegalStateException.class, () -> context.add(new InputDialogTypeView()));

    var shown = context.getShownOptionDialogs();

    assertEquals(3, shown.size());
    assertEquals(shown.get(0).getTitle(), shown.get(2).getTitle());
    assertTrue(reopened.getMessage().contains("no answer was scripted"));
    assertEquals("Access denied", shown.get(1).getTitle());
    assertEquals("Access denied", shown.get(1).getMessage());
  }
}
