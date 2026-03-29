package com.webforj.samples.views.terminal.commands;

import com.webforj.component.terminal.Terminal;

public interface TerminalCommand {
  /**
   * Returns the name of the command.
   *
   * @return the name of the command
   */
  String getName();

  /**
   * Returns a description of the command.
   *
   * @return the description of the command
   */
  String getDescription();

  /**
   * Executes the command with the given arguments.
   *
   * @param term the terminal instance
   * @param args the command arguments
   */
  void execute(Terminal term, String[] args);

  /**
   * Prints command information to the terminal.
   * Can be overridden for custom formatting.
   *
   * @param term the terminal instance
   */
  default void printInfo(Terminal term) {
    term.writeln("  \u001B[36m" + getName() + "\u001B[0m - " + getDescription());
  }
}
