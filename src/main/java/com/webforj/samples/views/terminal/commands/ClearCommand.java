package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

public class ClearCommand implements TerminalCommand {
  private static final String NAME = "clear";
  private static final String DESCRIPTION = "Clear the terminal";

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
    term.clear();
  }
}
