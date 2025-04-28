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
}
