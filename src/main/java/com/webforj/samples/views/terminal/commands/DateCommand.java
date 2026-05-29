package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;
import java.util.Date;

public class DateCommand implements TerminalCommand {
  private static final String NAME = "date";
  private static final String DESCRIPTION = "Show current date";

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
    term.writeln("\u001b[32mCurrent date: " + new Date() + "\u001b[0m");
  }
}
