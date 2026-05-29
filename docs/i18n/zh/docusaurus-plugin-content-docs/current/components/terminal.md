---
title: Terminal
sidebar_position: 126
_i18n_hash: 513f4970da96e2e9f36a80739e60cd9c
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal` 组件是一个交互式终端仿真器，行为类似于传统的系统控制台。它处理文本输出、用户输入、控制序列和屏幕缓冲，使其适用于构建远程访问工具、文本仪表板、嵌入式命令行壳或调试控制台。

<!-- INTRO_END -->

## 创建终端 {#creating-a-terminal}

:::info 导入终端
要在您的应用中使用 `Terminal` 组件，请确保在您的 pom.xml 中包含以下依赖项。

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-terminal</artifactId>
</dependency>
```
:::

以下示例构建了具有输入命令、历史导航和自定义输出的交互式命令行。

<ComponentDemo
path='/webforj/terminal'
files={[
  'src/main/java/com/webforj/samples/views/terminal/TerminalView.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TerminalCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/ClearCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/DateCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/HelpCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/MsgCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/PromptCommand.java',
  'src/main/java/com/webforj/samples/views/terminal/commands/TimeCommand.java',
  'src/main/resources/static/css/terminal/terminal-view.css',
]}
height='400px'
/>

## 工作原理 {#how-it-works}

终端管理着一个文本单元格的网格，处理传入的字符流，并对用户输入（如输入或选择文本）做出反应。它自动解释控制字符和转义序列以进行光标移动、颜色变化和屏幕清理。

核心行为包括：

- **数据输入**：向终端写入数据会更新屏幕，处理文本和控制序列。
- **数据输出**：捕获用户按键并将其作为结构化事件输出。
- **屏幕管理**：维护可滚动的历史缓冲区和当前屏幕状态。
- **光标处理**：跟踪文本输入和控制序列响应的光标位置。

终端是有状态的，这意味着它能够正确重构多字节字符，并在分段输入之间保持连续性。

## 向终端发送数据 {#sending-data-to-the-terminal}

数据通过 `write` 和 `writeln` 方法发送到终端：

- `write(Object data)`：将数据发送到终端流中。
- `writeln(Object data)`：发送数据后跟一个换行。

终端将所有传入数据作为 **UTF-16** 字符串处理。它会自动处理多字节字符，即使输入是以分段的形式到达。

### 示例 {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

您还可以附加一个回调，该回调在数据块被处理后运行：

```java
terminal.write("Long command output", e -> {
  System.out.println("Data processed.");
});
```

## 接收用户输入 {#receiving-user-input}

终端通过两个事件捕获用户生成的输入：

- **数据事件 (`onData`)**：在文本输入发生时触发，发送 Unicode 字符。
- **键事件 (`onKey`)**：每次按键时触发，包括键码和修饰符的信息，如 <kbd>Ctrl</kbd> 或 <kbd>Alt</kbd>。

这些事件可以用于将用户输入转发到后端、更新用户界面元素或触发自定义操作。

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

终端捕获的所有用户输入（如来自 `onData` 事件的输入）都作为 UTF-16 字符串发出。  
如果您的后端期望不同的编码（如 UTF-8 字节），您必须手动将数据转码。

:::info 传统编码
终端 **不支持传统编码**，如 `ISO-8859`。  
如果您需要兼容非 UTF-8 系统，请使用外部转码器（例如，[`luit`](https://linux.die.net/man/1/luit) 或 [`iconv`](https://en.wikipedia.org/wiki/Iconv)）在写入到终端或从终端读取之前转换数据。
:::

## 处理大数据流 {#handling-large-data-streams}

因为终端无法立即渲染无限输入，所以它维护一个内部输入缓冲区。如果该缓冲区过大（默认大约 `50MB`），新的传入数据可能会被丢弃，以保护系统性能。

为了妥善管理快速数据源，您应实现 **流控制**。

### 基本流控制示例 {#basic-flow-control-example}

在终端完成处理一个数据块之前暂停您的后端：

```java
pty.onData(chunk -> {
  pty.pause();
  terminal.write(chunk, result -> {
    pty.resume();
  });
});
```

### 水印流控制示例 {#watermark-flow-control-example}

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

您可以使用 `TerminalTheme` 来样式化终端，定义：

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

终端支持一系列用于光标移动、屏幕更新和文本格式化的标准控制序列。

识别的组：

- **`C0` 控制码**（单字节 7 位命令，`\x00`，`\x1F`，如退格和换行）
- **`C1` 控制码**（单字节 8 位命令，`\x80`，`\x9F`）
- **`ESC` 序列**（以 `ESC`（`\x1B`）开头，如保存/恢复光标、屏幕对齐）
- **`CSI` 序列**（控制序列引入器，`ESC [` 或 `CSI (\x9B)`，用于滚动、擦除和样式等操作）
- **`DCS` 序列**（设备控制字符串，`ESC P` 或 `DCS (\x90)`）
- **`OSC` 序列**（操作系统命令，`ESC ]` 或 `OSC (\x9D)`，用于设置窗口标题、超链接和颜色）

:::info 处理特殊和自定义序列
一些特殊序列类型，如 `APC`、`PM` 和 `SOS` 被识别但会被静默忽略。  
如果需要，可以通过集成支持自定义序列。
:::

## 样式 {#styling}

<TableBuilder name="Terminal" />
