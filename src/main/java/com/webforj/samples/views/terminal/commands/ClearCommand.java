package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

public class ClearCommand implements TerminalCommand {
  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "clear";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return "Clear the terminal";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Terminal term, String[] args) {
    term.clear();
  }
}
