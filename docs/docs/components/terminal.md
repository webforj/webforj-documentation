---
title: Terminal
sidebar_position: 126
---

<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />  
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

The `Terminal` component provides an interactive terminal emulator that behaves much like a traditional system console. It allows applications to display and manipulate a text-based interface, handling text output, receiving user input, interpreting control sequences, and maintaining screen buffers.

This terminal is designed to deliver reliable behavior across a range of use cases, such as building remote access tools, text dashboards, embedded command shells, or interactive debug consoles.

<ComponentDemo 
path='/webforj/terminal?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalView.java'
urls={[
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
height='400px'
/>

## How it works

The terminal manages a grid of text cells, processes incoming character streams, and reacts to user actions like typing or selecting text. It automatically interprets control characters and escape sequences for cursor movement, color changes, and screen clearing.

The core behaviors include:

- **Data Input**: Writing data to the terminal updates the screen, handling both text and control sequences.
- **Data Output**: Captures user keystrokes and emits them as structured events.
- **Screen Management**: Maintains a scrollable history buffer and current screen state.
- **Cursor Handling**: Tracks cursor position for text input and control sequence responses.

The terminal is stateful, meaning it properly reconstructs multibyte characters and maintains continuity across fragmented inputs.

## Sending data to the terminal

Data is sent to the terminal using the `write` and `writeln` methods:

- `write(Object data)`: Sends data into the terminal stream.
- `writeln(Object data)`: Sends data followed by a newline.

The terminal processes all incoming data as **UTF-16** strings. It automatically handles multibyte characters, even when input arrives in fragmented chunks.

### Example
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

You can also attach a callback that runs once the chunk of data has been processed:

```java
terminal.write("Long command output", e -> {
    System.out.println("Data processed.");
});
```

## Receiving user input

The terminal captures user-generated input through two events:

- **Data Event (`onData`)**: Fires when text input occurs, sending Unicode characters.
- **Key Event (`onKey`)**: Fires for every keypress, including information about key codes and modifiers like <kbd>Ctrl</kbd> or <kbd>Alt</kbd>.

These events can be used to relay user input to a backend, update UI elements, or trigger custom actions.

### Example
```java
terminal.onData(event -> {
  String userInput = event.getValue();
  backend.send(userInput);
});

terminal.onKey(event -> {
  if (event.isControlKey() && "C".equals(event.getKey())) {
      backend.send("SIGINT");
  }
});
```

All user input captured by the terminal (such as from `onData` events) is emitted as UTF-16 strings.  
If your backend expects a different encoding (such as UTF-8 bytes), you must manually transcode the data.

:::info Legacy Encodings
The terminal **doesn't support legacy encodings** like `ISO-8859`.  
If you need compatibility with non-UTF-8 systems, use an external transcoder (for example, [`luit`](https://linux.die.net/man/1/luit) or [`iconv`](https://en.wikipedia.org/wiki/Iconv)) to convert the data before writing it to or reading it from the terminal.
:::

## Handling large data streams

Because the terminal can't instantly render unlimited input, it maintains an internal input buffer. If this buffer grows too large (default around `50MB`), new incoming data may be dropped to protect system performance.

To properly manage fast data sources, you should implement **flow control**.

### Basic flow control example

Pause your backend until the terminal finishes processing a chunk:

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### Watermark flow control example

For more efficient control, use high/low watermarks:

```java
int HIGH_WATERMARK = 100_000;
int LOW_WATERMARK = 10_000;

int bufferedBytes = 0;

pty.onData(chunk -> {
  bufferedBytes += chunk.length;

  terminal.write(chunk, e -> {
    bufferedBytes -= chunk.length;
    if (bufferedBytes < LOW_WATERMARK) {
        pty.resume();
    }
  });

  if (bufferedBytes > HIGH_WATERMARK) {
    pty.pause();
  }
});
```

<ComponentDemo 
path='/webforj/serverlogs?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java'
height='400px'
/>

## Customization

### Terminal options

The `TerminalOptions` class allows you to configure behavior:

- Cursor blinking.
- Font settings (family, size, weight).
- Scrollback buffer size.
- Line height and letter spacing.
- Accessibility settings (screen reader mode).

Example:
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### Terminal theme

You can style the terminal using `TerminalTheme`, which defines:

- Background and foreground colors.
- Standard ANSI color palette.
- Cursor and selection background colors.

Example:
```java
TerminalTheme theme = new TerminalTheme();
theme.setBackground("#1e1e1e");
theme.setForeground("#cccccc");
terminal.setTheme(theme);
```
<ComponentDemo 
path='/webforj/terminalthemepicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java'
height='500px'
/>

## Supported sequences

The terminal supports a wide range of standard control sequences used for cursor movement, screen updates, and text formatting.

Recognized groups:

- **`C0` control codes** (single-byte 7-bit commands, `\x00`, `\x1F`, like backspace and line feed)
- **`C1` control codes** (single-byte 8-bit commands, `\x80`, `\x9F`)
- **`ESC` sequences** (starting with `ESC` (`\x1B`), like save/restore cursor, screen alignment)
- **`CSI` sequences** (Control Sequence Introducer, `ESC [` or `CSI (\x9B)`, for operations like scrolling, erasing, and styling)
- **`DCS` sequences** (Device Control Strings, `ESC P` or `DCS (\x90)`)
- **`OSC` sequences** (Operating System Commands, `ESC ]` or `OSC (\x9D)`, for setting window title, hyperlinks, and colors)

:::info Handling Exotic and Custom Sequences
Some exotic sequence types like `APC`,`PM`, and `SOS` are recognized but silently ignored.  
Custom sequences can be supported through integrations if needed.
:::


## Styling

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Terminal} table='parts' />
