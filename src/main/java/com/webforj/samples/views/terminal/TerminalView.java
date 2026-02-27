package com.webforj.samples.views.terminal;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.terminal.Terminal;
import com.webforj.component.terminal.event.TerminalDataEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.terminal.commands.ClearCommand;
import com.webforj.samples.views.terminal.commands.DateCommand;
import com.webforj.samples.views.terminal.commands.HelpCommand;
import com.webforj.samples.views.terminal.commands.MsgCommand;
import com.webforj.samples.views.terminal.commands.PromptCommand;
import com.webforj.samples.views.terminal.commands.TerminalCommand;
import com.webforj.samples.views.terminal.commands.TimeCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route
@FrameTitle("Custom Terminal")
@InlineStyleSheet("""
    dwc-window-center {
      background-color: #1e1e1e;
    }
  """)
public class TerminalView extends Composite<Terminal> {
  // self field enables fluent method chaining from the bound component
  private final Terminal self = getBoundComponent();
  private final StringBuilder commandBuffer = new StringBuilder();
  private final List<String> commandHistory = new ArrayList<>();
  private int historyIndex = -1;
  private final Map<String, TerminalCommand> commands = new HashMap<>();

  public TerminalView() {
    self.setAutoFit(true)
      .setSize("95%", "95%")
      .setStyle("margin", "var(--dwc-space-m)")
      .writeln("\u001B[1;32mWelcome 👋  to the webforJ terminal!\u001B[0m")
      .writeln("Type \u001B[1;33m`help`\u001B[0m to see a list of supported commands.")
      .write("$ ")
      .addDataListener(this::onData);


    registerCommands();
    self.focus();
  }

  private void registerCommands() {
    List<TerminalCommand> commandList = List.of(
      new TimeCommand(),
      new DateCommand(),
      new PromptCommand(),
      new MsgCommand(),
      new ClearCommand(),
      new HelpCommand(commands));

    for (TerminalCommand command : commandList) {
      commands.put(command.getName(), command);
    }
  }

  private void processCommand() {
    String commandLine = commandBuffer.toString();
    commandBuffer.setLength(0);

    if (commandLine.isBlank()) {
      self.write("$ ");
      return;
    }

    String[] args = commandLine.trim().split("\\s+");
    TerminalCommand command = commands.get(args[0]);
    if (command == null) {
      self.writeln("Unknown command: " + args[0]);
    } else {
      command.execute(self, args);
    }

    self.write("$ ");
  }

  private void onData(TerminalDataEvent e) {
    String input = e.getValue();
    boolean isPrintable = input.chars().allMatch(c -> (c >= 0x20 && c <= 0x7E) || c >= 0xA0);

    // Use enhanced switch with pattern matching for cleaner input handling
    switch (input) {
      case "\r" -> {
        self.write("\r\n");
        if (!commandBuffer.isEmpty()) {
          commandHistory.add(commandBuffer.toString());
          historyIndex = commandHistory.size();
        }
        processCommand();
      }
      case "\u007F", "\b" -> {
        if (!commandBuffer.isEmpty()) {
          commandBuffer.deleteCharAt(commandBuffer.length() - 1);
          self.write("\b \b");
        }
      }
      case "\u001b[A" -> {
        if (historyIndex > 0) {
          historyIndex--;
          replaceCommandBuffer(commandHistory.get(historyIndex));
        }
      }
      case "\u001b[B" -> {
        if (historyIndex < commandHistory.size() - 1) {
          historyIndex++;
          replaceCommandBuffer(commandHistory.get(historyIndex));
        } else if (historyIndex == commandHistory.size() - 1) {
          historyIndex++;
          replaceCommandBuffer("");
        }
      }
      default -> {
        if (isPrintable) {
          commandBuffer.append(input);
          self.write(input);
        }
      }
    }
  }

  private void replaceCommandBuffer(String newBuffer) {
    while (!commandBuffer.isEmpty()) {
      self.write("\b \b");
      commandBuffer.setLength(commandBuffer.length() - 1);
    }
    commandBuffer.append(newBuffer);
    self.write(newBuffer);
  }
}
