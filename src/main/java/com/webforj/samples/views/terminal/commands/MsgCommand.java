package com.webforj.samples.views.terminal.commands;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.component.terminal.Terminal;

public class MsgCommand implements TerminalCommand {
  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "msg";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return "Show a message dialog";
  }

  /**
   * {@inheritDoc}
   */
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
