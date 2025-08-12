---
title: Terminal
sidebar_position: 126
_i18n_hash: 8bc6b488ddba7e4660319f00c497321c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal` 组件提供了一种交互式终端模拟器，行为类似于传统系统控制台。它允许应用程序显示和操作基于文本的界面，处理文本输出，接收用户输入，解释控制序列，并维护屏幕缓冲区。

该终端旨在为多种用例提供可靠的行为，例如构建远程访问工具、文本仪表板、嵌入式命令外壳或交互式调试控制台。

:::info 导入 Terminal
要在您的应用中使用 `Terminal` 组件，请确保在您的 pom.xml 中包含以下依赖项。

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

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
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java'
]}
height='400px'
/>

## 工作原理 {#how-it-works}

终端管理文本单元格的网格，处理传入的字符流，并响应用户的动作，例如输入或选择文本。它自动解释控制字符和转义序列，用于光标移动、颜色更改和屏幕清除。

核心行为包括：

- **数据输入**：将数据写入终端会更新屏幕，处理文本和控制序列。
- **数据输出**：捕获用户击键并将它们作为结构化事件发出。
- **屏幕管理**：维护可滚动的历史缓冲区和当前屏幕状态。
- **光标处理**：跟踪用于文本输入和控制序列响应的光标位置。

终端是有状态的，意味着它适当地重构多字节字符，并在分片输入之间保持连续性。

## 向终端发送数据 {#sending-data-to-the-terminal}

数据是通过 `write` 和 `writeln` 方法发送到终端的：

- `write(Object data)`：将数据发送到终端流中。
- `writeln(Object data)`：发送数据并换行。

终端将所有传入数据视为 **UTF-16** 字符串。它自动处理多字节字符，即使输入以分片传送。

### 示例 {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

您还可以附加一个回调，当数据块处理完成后运行：

```java
terminal.write("Long command output", e -> {
    System.out.println("Data processed.");
});
```

## 接收用户输入 {#receiving-user-input}

终端通过两个事件捕获用户生成的输入：

- **数据事件 (`onData`)**：当发生文本输入时触发，发送 Unicode 字符。
- **按键事件 (`onKey`)**：每次按键时触发，包括按键代码和修饰符的信息，如 <kbd>Ctrl</kbd> 或 <kbd>Alt</kbd>。

这些事件可用于将用户输入转发到后端，更新 UI 元素或触发自定义操作。

### 示例 {#example-1}
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

终端捕获的所有用户输入（如来自 `onData` 事件）都作为 UTF-16 字符串发出。  
如果您的后端期望不同的编码（例如 UTF-8 字节），则必须手动转码数据。

:::info 经典编码
终端 **不支持经典编码**，如 `ISO-8859`。  
如果您需要与非 UTF-8 系统兼容，请使用外部转码器（例如，[`luit`](https://linux.die.net/man/1/luit) 或 [`iconv`](https://en.wikipedia.org/wiki/Iconv)）在写入或读取终端之前转换数据。
:::

## 处理大数据流 {#handling-large-data-streams}

由于终端无法即时呈现无限输入，因此它维护一个内部输入缓冲区。如果此缓冲区增大到过大（默认约为 `50MB`），则可能会丢弃新输入数据以保护系统性能。

为了正确管理快速数据源，您应该实现 **流量控制**。

### 基本流量控制示例 {#basic-flow-control-example}

暂停您的后端直到终端处理完一个数据块：

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### 水位流量控制示例 {#watermark-flow-control-example}

为了更有效的控制，使用高/低水位线：

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

## 自定义 {#customization}

### 终端选项 {#terminal-options}

`TerminalOptions` 类允许您配置行为：

- 光标闪烁。
- 字体设置（家族、大小、粗细）。
- 滚动缓冲区大小。
- 行高和字母间距。
- 可访问性设置（屏幕阅读器模式）。

示例：
```java
TerminalOptions options = new TerminalOptions()
    .setCursorBlink(true)
    .setFontFamily("Courier New, monospace")
    .setFontSize(13)
    .setScrollback(5000);

terminal.setOptions(options);
```

### 终端主题 {#terminal-theme}

您可以使用 `TerminalTheme` 对终端进行样式设置，其中定义：

- 背景色和前景色。
- 标准 `ANSI` 颜色调色板。
- 光标和选择背景颜色。

示例：
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

## 支持的序列 {#supported-sequences}

终端支持广泛的标准控制序列，用于光标移动、屏幕更新和文本格式化。

识别的组：

- **`C0` 控制代码**（单字节 7 位命令，`\x00`，`\x1F`，如退格和换行）
- **`C1` 控制代码**（单字节 8 位命令，`\x80`，`\x9F`）
- **`ESC` 序列**（以 `ESC` (`\x1B`) 开头，如保存/恢复光标、屏幕对齐）
- **`CSI` 序列**（控制序列引入器，`ESC [` 或 `CSI (\x9B)`，用于滚动、擦除和样式等操作）
- **`DCS` 序列**（设备控制字符串，`ESC P` 或 `DCS (\x90)`）
- **`OSC` 序列**（操作系统命令，`ESC ]` 或 `OSC (\x9D)`，用于设置窗口标题、超链接和颜色）

:::info 处理特殊和自定义序列
某些特殊序列类型，如 `APC`、`PM` 和 `SOS` 被识别但默默忽略。  
如果需要，可以通过集成支持自定义序列。
:::

## 样式 {#styling}

<TableBuilder name="Terminal" />
