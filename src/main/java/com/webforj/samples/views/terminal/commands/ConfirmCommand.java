package com.webforj.samples.views.terminal.commands;

import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.terminal.Terminal;

public class ConfirmCommand implements TerminalCommand {
  private static final String NAME = "confirm";
  private static final String DESCRIPTION = "Show a confirm dialog with YES and NO buttons";

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  @Override
  public void execute(Terminal term, String[] args) {
    if (args.length < 2) {
      term.writeln("Usage: confirm <text>");
      return;
    }
    String confirmText = String.join(" ", args).substring(7);
    ConfirmDialog confirmDialog =
        new ConfirmDialog(
            confirmText,
            "Terminal Confirm",
            ConfirmDialog.OptionType.YES_NO,
            ConfirmDialog.MessageType.QUESTION);

    confirmDialog.setRawText(true);
    ConfirmDialog.Result result = confirmDialog.show();
    term.writeln("\u001b[32mUser picked: " + result + "\u001b[0m");
  }
}
