package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

import java.util.Map;

public class HelpCommand implements TerminalCommand {
  private final Map<String, TerminalCommand> commands;

  public HelpCommand(Map<String, TerminalCommand> commands) {
    this.commands = commands;
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescription() {
    return "Show supported commands";
  }

  @Override
  public void execute(Terminal term, String[] args) {
    term.writeln("Supported commands:");
    // Use forEach with lambda for cleaner iteration
    commands.values().forEach(command -> command.printInfo(term));
  }
}
