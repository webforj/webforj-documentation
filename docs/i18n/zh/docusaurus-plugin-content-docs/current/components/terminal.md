---
title: Terminal
sidebar_position: 126
_i18n_hash: 4b7abaef79b0511bbbb796171ddd8c07
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal` 组件提供了一个交互式终端仿真器，其行为类似于传统的系统控制台。它允许应用程序显示和操作基于文本的界面，处理文本输出，接收用户输入，解释控制序列，并维护屏幕缓冲区。

该终端旨在在多种用例中提供可靠的行为，例如构建远程访问工具、文本仪表板、嵌入式命令行或交互式调试控制台。

:::info 引入终端
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
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java' 
]} 
height='400px' 
/>

## 工作原理 {#how-it-works}

终端管理文本单元格的网格，处理传入的字符流，并对用户操作（如输入或选择文本）作出反应。它会自动解释控制字符和转义序列，以实现光标移动、颜色变化和屏幕清除。

核心行为包括：

- **数据输入**：向终端写入数据会更新屏幕，处理文本和控制序列。
- **数据输出**：捕获用户按键并将其作为结构化事件发出。
- **屏幕管理**：维护可滚动的历史缓冲区和当前屏幕状态。
- **光标处理**：跟踪光标位置，以进行文本输入和控制序列响应。

终端是有状态的，这意味着它能够正确重建多字节字符，并在分段输入之间保持连贯性。

## 向终端发送数据 {#sending-data-to-the-terminal}

数据通过 `write` 和 `writeln` 方法发送到终端：

- `write(Object data)`：将数据发送到终端流中。
- `writeln(Object data)`：发送数据并在后面添加换行符。

终端将所有传入的数据处理为 **UTF-16** 字符串。它会自动处理多字节字符，即使输入以分段形式到达。

### 示例 {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

您还可以附加一个回调，以便在处理完数据块后运行：

```java
terminal.write("Long command output", e -> {
    System.out.println("Data processed.");
});
```

## 接收用户输入 {#receiving-user-input}

终端通过两个事件捕获用户生成的输入：

- **数据事件 (`onData`)**：当输入文本时触发，发送 Unicode 字符。
- **按键事件 (`onKey`)**：每次按键时触发，包括键码和修饰符（如 <kbd>Ctrl</kbd> 或 <kbd>Alt</kbd>）的信息。

这些事件可以用于将用户输入转发到后端、更新 UI 元素或触发自定义操作。

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

终端捕获的所有用户输入（例如来自 `onData` 事件）被作为 UTF-16 字符串发出。  
如果您的后端期望不同的编码（例如 UTF-8 字节），您必须手动转换数据。

:::info 旧版编码
终端 **不支持旧版编码**，如 `ISO-8859`。  
如果您需要与非 UTF-8 系统兼容，请使用外部转码器（例如，[`luit`](https://linux.die.net/man/1/luit) 或 [`iconv`](https://en.wikipedia.org/wiki/Iconv)）在写入或从终端读取数据之前转换数据。
:::

## 处理大数据流 {#handling-large-data-streams}

由于终端无法瞬时渲染无限输入，它维护一个内部输入缓冲区。如果此缓冲区过大（默认约 `50MB`），新传入的数据可能会被丢弃以保护系统性能。

为了正确管理快速数据源，您应该实现 **流量控制**。

### 基本流量控制示例 {#basic-flow-control-example}

在终端完成处理一个数据块之前，暂停后端：

```java
pty.onData(chunk -> {
    pty.pause();
    terminal.write(chunk, result -> {
        pty.resume();
    });
});
```

### 水位线流量控制示例 {#watermark-flow-control-example}

为了更有效地控制，使用高水位线/低水位线：

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
- 字体设置（字体、大小、粗细）。
- 回滚缓冲区大小。
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

您可以使用 `TerminalTheme` 来样式终端，定义：

- 背景和前景颜色。
- 标准的 `ANSI` 颜色调色板。
- 光标和选择的背景颜色。

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
- **`ESC` 序列**（以 `ESC`（`\x1B`）开头，如保存/恢复光标、屏幕对齐）
- **`CSI` 序列**（控制序列引入器，`ESC [` 或 `CSI (\x9B)`，用于滚动、擦除和样式等操作）
- **`DCS` 序列**（设备控制字符串，`ESC P` 或 `DCS (\x90)`）
- **`OSC` 序列**（操作系统命令，`ESC ]` 或 `OSC (\x9D)`，用于设置窗口标题、超链接和颜色）

:::info 处理特殊和自定义序列
一些特殊序列类型，如 `APC`，`PM` 和 `SOS`，被识别但会被静默忽略。  
如果需要，可以通过集成支持自定义序列。
:::

## 样式 {#styling}

<TableBuilder name="Terminal" />
