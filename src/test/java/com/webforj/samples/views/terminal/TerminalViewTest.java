package com.webforj.samples.views.terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.terminal.Terminal;
import com.webforj.testbed.ShownOptionDialog;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TerminalViewTest {
  @Test
  void shouldShowTheTextOfTheMsgCommandInMessageDialog(WebforjTestbed context) {
    var view = new TerminalView();
    context.add(view);

    var terminal = ComponentLocator.of(Terminal.class).locate();

    UserInteraction.type(terminal, "msg Hello world");
    UserInteraction.type(terminal, "\r");

    var dialog = context.getShownOptionDialogs().get(0);
    assertEquals("Terminal Message", dialog.getTitle());
    assertEquals("Hello world", dialog.getMessage());
  }

  @Test
  void shouldAskTheQuestionOfTheConfirmCommandInConfirmDialog(WebforjTestbed context) {
    var view = new TerminalView();
    context.add(view);

    var terminal = ComponentLocator.of(Terminal.class).locate();

    context.answerConfirmDialog(ConfirmDialog.Result.YES);
    UserInteraction.type(terminal, "confirm Continue?");
    UserInteraction.type(terminal, "\r");

    var dialog = context.getShownOptionDialogs().get(0);
    assertEquals("Terminal Confirm", dialog.getTitle());
    assertEquals("Continue?", dialog.getMessage());
  }

  @Test
  void shouldEraseTheLastTypedCharacterOnBackspace(WebforjTestbed context) {
    var view = new TerminalView();
    context.add(view);

    var terminal = ComponentLocator.of(Terminal.class).locate();

    UserInteraction.type(terminal, "msg HelloX");
    UserInteraction.type(terminal, "\b");
    UserInteraction.type(terminal, "\r");

    assertEquals("Hello", context.getShownOptionDialogs().get(0).getMessage());
  }

  @Test
  void shouldRunThePreviousCommandAgainFromTheHistoryWithTheUpArrow(WebforjTestbed context) {
    var view = new TerminalView();
    context.add(view);

    var terminal = ComponentLocator.of(Terminal.class).locate();

    UserInteraction.type(terminal, "msg First");
    UserInteraction.type(terminal, "\r");
    UserInteraction.type(terminal, "msg Second");
    UserInteraction.type(terminal, "\r");
    UserInteraction.type(terminal, "\u001b[A");
    UserInteraction.type(terminal, "\u001b[A");
    UserInteraction.type(terminal, "\r");

    assertEquals(
        List.of("First", "Second", "First"),
        context.getShownOptionDialogs().stream().map(ShownOptionDialog::getMessage).toList());
  }

  @Test
  void shouldEmptyTheLineWhenTheDownArrowMovesPastTheNewestCommand(WebforjTestbed context) {
    var view = new TerminalView();
    context.add(view);

    var terminal = ComponentLocator.of(Terminal.class).locate();

    UserInteraction.type(terminal, "msg Hello");
    UserInteraction.type(terminal, "\r");
    UserInteraction.type(terminal, "\u001b[A");
    UserInteraction.type(terminal, "\u001b[B");
    UserInteraction.type(terminal, "\r");

    assertEquals(1, context.getShownOptionDialogs().size());
  }
}
