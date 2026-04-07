package com.webforj.samples.views.terminal.commands;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.component.terminal.Terminal;

public class MsgCommand implements TerminalCommand {
  private static final String NAME = "msg";
  private static final String DESCRIPTION = "Show a message dialog";

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
      term.writeln("Usage: msg <message>");
      return;
    }
    String message = String.join(" ", args).substring(4);
    showMessageDialog(message, "Terminal Message");
  }
}
