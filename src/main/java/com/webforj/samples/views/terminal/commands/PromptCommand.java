package com.webforj.samples.views.terminal.commands;

import static com.webforj.component.optiondialog.OptionDialog.showInputDialog;

import com.webforj.component.optiondialog.InputDialog;
import com.webforj.component.terminal.Terminal;

public class PromptCommand implements TerminalCommand {
  private static final String NAME = "prompt";
  private static final String DESCRIPTION = "Prompt the user with custom text";

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
      term.writeln("Usage: prompt <text>");
      return;
    }
    String promptText = String.join(" ", args).substring(6);
    String answer = showInputDialog(promptText, "Terminal Prompt", InputDialog.MessageType.QUESTION);
    term.writeln("\u001b[32mUser responded: " + answer + "\u001b[0m");
  }
}
