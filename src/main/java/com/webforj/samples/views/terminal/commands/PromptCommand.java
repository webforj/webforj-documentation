package com.webforj.samples.views.terminal.commands;

import static com.webforj.component.optiondialog.OptionDialog.showInputDialog;

import com.webforj.component.optiondialog.InputDialog;
import com.webforj.component.terminal.Terminal;

public class PromptCommand implements TerminalCommand {
  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "prompt";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return "Prompt the user with custom text";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Terminal term, String[] args) {
    if (args.length < 2) {
      term.writeln("Usage: prompt <text>");
      return;
    }
    String promptText = String.join(" ", args).substring(6);
    String answer = showInputDialog(promptText, "Terminal Prompt", InputDialog.MessageType.QUESTION);
    term.writeln("\u001b[32mUser responded: " + answer + "\u001b[0m");
  }
}
