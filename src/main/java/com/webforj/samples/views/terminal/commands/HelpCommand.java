package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

import java.util.Map;

public class HelpCommand implements TerminalCommand {
  private final Map<String, TerminalCommand> commands;

  public HelpCommand(Map<String, TerminalCommand> commands) {
    this.commands = commands;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return "help";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return "Show supported commands";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Terminal term, String[] args) {
    term.writeln("Supported commands:");
    for (TerminalCommand command : commands.values()) {
      term.writeln("  \u001b[36m" + command.getName() + "\u001b[0m - " + command.getDescription());
    }
  }
}
