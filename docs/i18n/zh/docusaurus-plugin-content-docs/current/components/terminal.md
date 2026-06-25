---
title: Terminal
sidebar_position: 126
description: >-
  Embed an interactive terminal emulator with the Terminal component for shells,
  dashboards, debug consoles, and remote access tools.
_i18n_hash: 8480d045ce597a8a24d6fd9760a72935
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal` 组件是一个交互式终端仿真器，行为类似于传统系统控制台。它处理文本输出、用户输入、控制序列和屏幕缓冲区，非常适合构建远程访问工具、文本仪表板、嵌入式命令外壳或调试控制台。

<!-- INTRO_END -->

## 创建终端 {#creating-a-terminal}

:::info 导入终端
要在您的应用中使用`Terminal` 组件，请确保在您的 pom.xml 中包含以下依赖项。

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

以下示例构建了一个具有输入命令、历史导航和自定义输出的交互式命令外壳。

<ComponentDemo
path='/webforj/terminal'
files={[
  'src/main/java/com/webforj/samples/views/terminal/TerminalView.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ConfirmCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java',
  'src/main/resources/static/css/terminal/terminal-view.css',
]}
height='400px'
/>

## 工作原理 {#how-it-works}

终端管理文本单元的网格，处理传入的字符流，并根据用户的输入（例如输入或选择文本）进行响应。它会自动解释控制字符和转义序列，以进行光标移动、颜色变化和屏幕清除。

核心行为包括：

- **数据输入**：向终端写入数据会更新屏幕，处理文本和控制序列。
- **数据输出**：捕获用户按键并将其作为结构化事件发出。
- **屏幕管理**：维护可滚动的历史缓冲区和当前屏幕状态。
- **光标处理**：跟踪光标位置，以进行文本输入和控制序列响应。

终端是有状态的，这意味着它能够正确重构多字节字符，并在分段输入之间保持一致性。

## 向终端发送数据 {#sending-data-to-the-terminal}

数据使用`write` 和 `writeln` 方法发送到终端：

- `write(Object data)`：将数据发送到终端流中。
- `writeln(Object data)`：发送数据并后跟换行符。

终端将所有传入数据处理为 **UTF-16** 字符串。它会自动处理多字节字符，即使输入以碎片方式到达。

### 示例 {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

您还可以附加一个回调，当数据块处理完毕后运行：

```java
terminal.write("长命令输出", e -> {
  System.out.println("数据已处理。");
});
```

## 接收用户输入 {#receiving-user-input}

终端通过两个事件捕获用户生成的输入：

- **数据事件 (`onData`)**：当文本输入发生时触发，发送 Unicode 字符。
- **按键事件 (`onKey`)**：每次按键时触发，包括关于按键代码和修饰符（如<kbd>Ctrl</kbd>或<kbd>Alt</kbd>）的信息。

这些事件可用于将用户输入中继到后端，更新 UI 元素或触发自定义操作。

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

终端捕获的所有用户输入（例如来自`onData`事件的输入）以 UTF-16 字符串形式发出。  
如果您的后端期望不同的编码（例如 UTF-8 字节），则必须手动对数据进行转码。

:::info 旧编码
终端 **不支持** 旧编码，如 `ISO-8859`。  
如果您需要与非 UTF-8 系统兼容，请使用外部转码器（例如 [`luit`](https://linux.die.net/man/1/luit) 或 [`iconv`](https://en.wikipedia.org/wiki/Iconv)）在将数据写入或从终端读取之前进行转换。
:::

## 处理大数据流 {#handling-large-data-streams}

由于终端无法瞬间呈现无限输入，因此它维持一个内部输入缓冲区。如果该缓冲区变得过大（默认约为 `50MB`），新的传入数据可能会被丢弃以保护系统性能。

要正确管理快速数据源，您应该实现 **流控制**。

### 基本流控制示例 {#basic-flow-control-example}

在终端处理完数据块之前暂停您的后端：

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### 水印流控制示例 {#watermark-flow-control-example}

为了更高效的控制，使用高/低水印：

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
path='/webforj/serverlogs'
files={['src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java']}
height='400px'
/>

## 自定义 {#customization}

### 终端选项 {#terminal-options}

`TerminalOptions` 类允许您配置行为：

- 光标闪烁。
- 字体设置（系列、大小、粗细）。
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

您可以使用 `TerminalTheme` 样式化终端，它定义：

- 背景和前景颜色。
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
path='/webforj/terminalthemepicker'
files={['src/main/java/com/webforj/samples/views/terminal/TerminalThemePickerView.java']}
height='500px'
/>

## 支持的序列 {#supported-sequences}

终端支持广泛的标准控制序列，用于光标移动、屏幕更新和文本格式化。

识别的组别：

- **`C0` 控制码**（单字节 7 位命令，`\x00`，`\x1F`，如退格和换行）
- **`C1` 控制码**（单字节 8 位命令，`\x80`，`\x9F`）
- **`ESC` 序列**（以 `ESC` 开头（`\x1B`），如保存/恢复光标、屏幕对齐）
- **`CSI` 序列**（控制序列引导，`ESC [` 或 `CSI (\x9B)`，用于滚动、擦除和样式等操作）
- **`DCS` 序列**（设备控制字符串，`ESC P` 或 `DCS (\x90)`）
- **`OSC` 序列**（操作系统命令，`ESC ]` 或 `OSC (\x9D)`，用于设置窗口标题、超链接和颜色）

:::info 处理奇特和自定义序列
一些奇特的序列类型，如 `APC`、`PM` 和 `SOS` 被识别但被静默忽略。  
如果需要，可以通过集成来支持自定义序列。
:::

## 样式 {#styling}

<TableBuilder name="Terminal" />
