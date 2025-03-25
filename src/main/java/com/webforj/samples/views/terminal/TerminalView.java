package com.webforj.samples.views.terminal;

import static com.webforj.component.optiondialog.OptionDialog.showInputDialog;
import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.InputDialog;
import com.webforj.component.terminal.Terminal;
import com.webforj.component.terminal.event.TerminalDataEvent;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

@Route
@FrameTitle("Terminal Basics")
public class TerminalView extends Composite<Div> {

  private String commandBuffer = "";
  private List<String> commandHistory = new ArrayList<>();
  private int historyIndex = -1;

  public TerminalView() {
    Terminal terminal = new Terminal();
    terminal.addDataListener(this::handleDataEvent);
    terminal.setAutoFit(true);
    terminal.setSize("100vw", "100vh");

    terminal.writeln("Welcome to the webforj terminal!");
    terminal.writeln("Type `help` for a list of supported commands.");
    terminal.write("$ ");

    getBoundComponent().setSize("100vw", "100vh");
    getBoundComponent().add(terminal);

    terminal.focus();
  }

  private void replaceCommandBuffer(Terminal term, String newBuffer) {
    while (commandBuffer.length() > 0) {
      term.write("\b \b");
      commandBuffer = commandBuffer.substring(0, commandBuffer.length() - 1);
    }

    commandBuffer = newBuffer;
    term.write(newBuffer);
  }

  private void processCommand(Terminal term, String command) {
    String[] args = command.trim().split(" ");
    String cmd = args[0];
    for (int i = 0; i < args.length; i++) {
      args[i] = args[i].trim();
    }

    switch (cmd) {
      case "time":
        String time = new java.util.Date().toString();
        term.writeln("\u001b[32mCurrent time: " + time + "\u001b[0m");
        break;
      case "date":
        String date = new java.util.Date().toString();
        term.writeln("\u001b[32mCurrent date: " + date + "\u001b[0m");
        break;
      case "prompt":
        if (args.length < 2) {
          term.writeln("Usage: prompt <text>");
          break;
        }

        String promptText = String.join(" ", args).substring(6);
        String answer = showInputDialog(promptText, "Terminal Prompt", InputDialog.MessageType.QUESTION);
        term.writeln("\u001b[32mUser responded: " + answer + "\u001b[0m");
        break;
      case "msg":
        if (args.length < 2) {
          term.writeln("Usage: msg <message>");
          break;
        }

        String message = String.join(" ", args).substring(4);
        showMessageDialog(message, "Terminal Message");
        break;
      case "clear":
        term.clear();
        break;
      case "help":
        term.writeln("Supported commands:");
        term.writeln("  time - show current time");
        term.writeln("  date - show current date");
        term.writeln("  prompt <text> - prompt the user with custom text");
        term.writeln("  msg <message> - show a message dialog");
        term.writeln("  clear - clear the terminal");
        break;
      default:
        term.writeln("Unknown command: " + cmd);
        break;
    }

    term.write("$ ");
  }

  private void handleDataEvent(TerminalDataEvent e) {
    String input = e.getValue();

    boolean isPrintable = input.chars().allMatch(c -> (c >= 0x20 && c <= 0x7E) || c >= 0xA0);
    Terminal term = e.getComponent();

    switch (input) {
      case "\r": // Enter Key
        term.write("\r\n");
        if (commandBuffer.trim().length() > 0) {
          commandHistory.add(commandBuffer);
          historyIndex = commandHistory.size();
        }
        processCommand(term, this.commandBuffer);
        this.commandBuffer = "";
        break;

      case "\u007F": // Backspace
        if (commandBuffer.length() > 0) {
          commandBuffer = commandBuffer.substring(0, commandBuffer.length() - 1);
          term.write("\b \b");
        }
        break;

      case "\u001b[A": // Up arrow key
        if (historyIndex > 0) {
          historyIndex--;
          replaceCommandBuffer(term, commandHistory.get(historyIndex));
        }
        break;

      case "\u001b[B": // Down arrow key
        if (historyIndex < commandHistory.size() - 1) {
          historyIndex++;
          replaceCommandBuffer(term, commandHistory.get(historyIndex));
        } else if (historyIndex == commandHistory.size() - 1) {
          historyIndex++;
          replaceCommandBuffer(term, "");
        }
        break;

      default:
        if (isPrintable) {
          commandBuffer += input;
          term.write(input);
        }
        break;
    }
  }

}
