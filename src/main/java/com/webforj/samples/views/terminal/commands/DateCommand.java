package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

public class DateCommand implements TerminalCommand {
  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "date";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return "Show current date";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Terminal term, String[] args) {
    String date = new java.util.Date().toString();
    term.writeln("\u001b[32mCurrent date: " + date + "\u001b[0m");
  }
}
