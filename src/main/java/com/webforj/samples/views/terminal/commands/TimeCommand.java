package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

public class TimeCommand implements TerminalCommand {
  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "time";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return "Show current time";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Terminal term, String[] args) {
    String time = new java.util.Date().toString();
    term.writeln("\u001b[32mCurrent time: " + time + "\u001b[0m");
  }
}
