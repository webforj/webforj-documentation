---
title: "Java web terminal: embed a terminal in webforJ"
description: "Embed a Java web terminal in a webforJ app. Write output, handle typed input with onData, and style the Terminal component from server-side Java code."
slug: java-web-terminal
date: 2026-06-16
authors: Matthew Hawkins
tags: [components, front end, events]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-16-java-web-terminal/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-16-java-web-terminal/cover.jpg)

Some tools are easier to use with a prompt than with forms: an admin console, a log viewer, a way to run a quick command against a live server. Building one in the browser usually means a JavaScript terminal library and the code to wire it to your backend.

A Java web terminal in webforJ skips that wiring. The `Terminal` component is a full console you place in a view like any other component, and every keystroke reaches your Java code as an event.

<!-- truncate -->

:::tip Try it live
The [Terminal documentation](/docs/components/terminal) has live, interactive samples you can type into in the browser, with the full Java source next to each one.
:::

This guide embeds a terminal, handles typed input, and styles it, using the sample from the docs.

## Embed a Java web terminal {#embed-a-java-web-terminal}

The `Terminal` class ships in the `webforj-terminal` artifact. Add that artifact to your pom.xml. Its version comes from the webforJ bill of materials (BOM), so you leave the version out.

Inside the terminal, a grid of text cells holds the screen. The terminal processes incoming character streams, and it interprets control characters and escape sequences for cursor movement, color changes, and screen clearing. Your code does not handle those sequences one by one. It sends text and sequences in, and the component draws the result.

The view below is the start of the terminal sample from the docs. It binds a `Composite` straight to the `Terminal`, lets the terminal fit its container with `setAutoFit(true)`, prints a colored welcome with ANSI escape codes, and registers a listener for typed input.

```java
@Route
public class TerminalView extends Composite<Terminal> {
  private final Terminal self = getBoundComponent();
  private final StringBuilder commandBuffer = new StringBuilder();

  public TerminalView() {
    self.setAutoFit(true)
        .setSize("95%", "95%")
        .writeln("\u001B[1;32mWelcome 👋  to the webforJ terminal!\u001B[0m")
        .writeln("Type \u001B[1;33m`help`\u001B[0m to see a list of supported commands.")
        .write("$ ")
        .addDataListener(this::onData);
  }
}
```

The terminal keeps the screen state between calls, so a sequence you send in one call affects the output of the next one.

`writeln` adds a line break after the text, and `write` sends the text as it is. Both accept an `Object`. The terminal processes the data as UTF-16 strings, which matches how Java stores strings.

The terminal understands several groups of control sequences, including control codes, escape sequences, CSI, DCS, and OSC. CSI covers scrolling, erasing, and styling, and OSC covers the window title, hyperlinks, and colors. That means a server can send colored output or clear the screen with standard escape sequences, and the component applies them. Some rare sequence types, such as APC, PM, and SOS, are recognized but ignored.

## Handle typed input {#handle-typed-input}

The terminal reports typed text through `onData`. The event's `getValue()` returns the characters as a string. The terminal only reports input. It does not run anything for you, so the handler owns the state. The handler below comes from the same docs sample, trimmed to the basics. It keeps a command buffer, writes each printable character back with `write`, runs the command on Enter, and removes the last character on backspace. The full sample also adds command history on the arrow keys and a small set of commands.

```java
private void onData(TerminalDataEvent e) {
  String input = e.getValue();
  boolean isPrintable = input.chars().allMatch(c -> (c >= 0x20 && c <= 0x7E) || c >= 0xA0);

  switch (input) {
    case "\r" -> {
      self.write("\r\n");
      processCommand();
    }
    case "\u007F", "\b" -> {
      if (!commandBuffer.isEmpty()) {
        commandBuffer.deleteCharAt(commandBuffer.length() - 1);
        self.write("\b \b");
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
```

The write call `\b \b` is a common trick. The backspace control character moves the cursor left, the space overwrites the old character, and the second backspace moves the cursor left again.

The handler accepts both `\b` and DEL (the delete control character) as backspace, and it only echoes printable characters, so stray control codes never reach the screen.

`onKey` works at a different level. Its event exposes `getKey()`, `getCode()`, and the modifier checks `isControlKey()`, `isAltKey()`, and `isShiftKey()`. Use `onKey` for shortcuts and `onData` for text.

The pattern is the same as any input handler in a Java UI. The event fires, your code updates its state, and the output follows from that state. The terminal adds the cursor and the scrolling, so the handler only needs to decide what to write.

## Style the terminal {#style-the-terminal}

`TerminalOptions` covers behavior and fonts: cursor blink, font family, font size, scrollback, and more. `TerminalTheme` covers color: the background, the foreground, the 16 ANSI colors, the cursor, and the selection.

```java
TerminalOptions options = new TerminalOptions()
  .setCursorBlink(true)
  .setFontFamily("Courier New, monospace")
  .setFontSize(13)
  .setScrollback(5000);

terminal.setOptions(options);
```

```java
TerminalTheme theme = new TerminalTheme();
theme.setBackground("#1e1e1e");
theme.setForeground("#cccccc");
terminal.setTheme(theme);
```

Both snippets come from the Terminal docs. `setScrollback` sets how many rows of history the terminal keeps, so raise it when users need to scroll further back.

## Large output and flow control {#large-output-and-flow-control}

The terminal keeps an input buffer, and the docs put its default size around 50 MB. Data beyond that can be dropped, so a fast source needs flow control. Pause the source, call `write` with a callback, and resume the source inside that callback. The callback fires after the terminal has processed the data, which is the signal to send the next chunk.

The terminal works with UTF-16 strings and does not support legacy encodings such as ISO-8859. If your backend sends bytes in another encoding, convert them to a Java string in the correct encoding before you call `write`.

Next, create a project with [startforJ](https://docs.webforj.com/startforj) and add the terminal view to it. The [Terminal reference](/docs/components/terminal) lists every option and event, and [getting started](/docs/introduction/getting-started) covers the project setup.

## Frequently asked questions {#faq}

### Does the webforJ terminal run a real shell? {#does-the-terminal-run-a-real-shell}

No. The `Terminal` shows output and sends input to your code. To run a real shell, your server starts the process, writes its output with `write`, and forwards the `onData` input to it. The Terminal docs show this pattern with a pty source that is paused and resumed around each `write` call.

### Which encodings does the terminal support? {#which-encodings-does-the-terminal-support}

The terminal works with UTF-16 strings and does not support legacy encodings such as ISO-8859. Convert other encodings to a Java string before you call `write`. Input from `onData` arrives as UTF-16 strings too.

### Since which webforJ version is the Terminal available? {#since-which-version}

The `Terminal` component is available since webforJ 24.10. Its docs page and API reference both carry that version. The component needs the `webforj-terminal` artifact on the classpath.

### Can I change the terminal colors and font? {#change-colors-and-font}

Yes. `TerminalTheme` sets the background, foreground, ANSI palette, cursor, and selection colors. `TerminalOptions` sets the font family, font size, font weight, and cursor style.
