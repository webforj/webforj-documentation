package com.webforj.samples.views.optiondialog.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    context.add(new InputDialogTypeView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals(ShownOptionDialog.Kind.INPUT, shown.getFirst().getKind());
    assertEquals("Restricted access.", shown.getFirst().getTitle());
    assertEquals("Welcome", shown.getLast().getTitle());
    assertEquals("Access granted", shown.getLast().getMessage());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDenyAccessForACancelledOrEmptyPassword(String password, WebforjTestbed context) {
    context.answerInputDialog(password);
    context.add(new InputDialogTypeView());

    var shown = context.getShownOptionDialogs();

    assertEquals(2, shown.size());
    assertEquals("Access denied", shown.getLast().getTitle());
    assertEquals("Access denied", shown.getLast().getMessage());
  }
}
