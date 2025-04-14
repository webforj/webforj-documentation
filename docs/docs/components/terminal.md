---
title: Terminal
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-terminal" />

<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

The `Terminal` component in webforJ provides a command-line interface within a web app, allowing users to interact with the app through typed commands. It’s ideal for apps that need command execution, debugging tools, or interactive text-based environments. With support for features like custom commands, command history, and user prompts, the `Terminal` component makes it easy to build a functional and interactive terminal experience. 

## Basics

To initialize a `Terminal` component in webforJ, simply create an instance of the Terminal class and configure any desired properties. At its most basic, the `Terminal` provides an interactive text area where users can enter commands and receive output. 

The following example demonstrates a `Terminal` component with enhanced functionality, including command history navigation with the arrow keys, command processing, and interactive prompts. These features make it easier to build a realistic command-line experience within your app.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/terminal?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/TerminalView.java'
height='300px'
/>

## Writing text to the `Terminal`
The `Terminal` component provides methods to display text in different ways:

- `write()`: Outputs text without a newline, which is useful for prompts or ongoing lines of text that should stay on the same line.
- `writeln()`: Outputs text followed by a newline, ideal for messages or commands that should appear on separate lines.

## Reading user input
To capture user input, use the `addDataListener()` method. This method listens for user keypresses and processes the entered text when the user presses <kbd>Enter</kbd>.

In this example, each time the user presses <kbd>Enter</kbd>, the `Terminal` captures the input, displays it back to the user, and shows a new prompt. This setup is essential for creating an interactive command interface.

```java
terminal.addDataListener(event -> {
    String input = event.getValue();
    terminal.writeln("You entered: " + input);
    terminal.write("$ "); // Displays a new prompt
});
```

## Command processing
The `Terminal` component allows you to define custom commands, enabling the creation of an interactive command-line environment. 

Here's an example of capturing user commands and processing each command:

```java
private void processCommand(Terminal terminal, String command) {
    String[] args = command.trim().split(" ");
    String cmd = args[0];

    switch (cmd) {
        case "time":
            terminal.writeln("Current time: " + new java.util.Date());
            break;
        case "clear":
            terminal.clear();
            break;
        case "help":
            terminal.writeln("Supported commands:");
            terminal.writeln("  time - display current time");
            terminal.writeln("  clear - clear the terminal");
            terminal.writeln("  help - list available commands");
            break;
        default:
            terminal.writeln("Unknown command: " + cmd);
            break;
    }

    terminal.write("$ ");
}
```
This setup defines the commands `time`, `clear`, and `help`, and handles unrecognized commands with an error message.

## Styling

### Auto-fit and custom sizing
For responsive design, you can use `setAutoFit(true)` to make the `Terminal` automatically fit its container, or `setSize(width, height)` to define custom dimensions.

```java
terminal.setAutoFit(true);  // Auto-fits the container
terminal.setSize("100vw", "100vh"); // Sets width and height
```

These options give you control over the `Terminal`’s layout and appearance, making it adaptable to different screen sizes or embedded contexts.

### Shadow parts

These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Terminal} table="parts"/>


