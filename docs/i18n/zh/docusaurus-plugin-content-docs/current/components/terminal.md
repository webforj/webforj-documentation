---
title: Terminal
sidebar_position: 126
_i18n_hash: a4c442c62c748f82d75133db075054af
---
<DocChip chip="shadow" />  
<DocChip chip="name" label="dwc-terminal" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="terminal" location="com/webforj/component/terminal/Terminal" top='true'/>

`Terminal` 组件是一个交互式终端仿真器，表现得像传统的系统控制台。它处理文本输出、用户输入、控制序列和屏幕缓冲，使其适合构建远程访问工具、文本仪表板、嵌入式命令行或调试控制台。

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

以下示例构建了一个具有输入命令、历史导航和自定义输出的交互式命令行。

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

终端管理文本单元格的网格，处理传入的字符流，并响应用户操作（如输入或选择文本）。它自动解释控制字符和转义序列，用于光标移动、颜色变化和屏幕清除。

核心行为包括：

- **数据输入**：向终端写入数据更新屏幕，处理文本和控制序列。
- **数据输出**：捕获用户按键，将其作为结构化事件发出。
- **屏幕管理**：维护可滚动的历史缓冲区和当前屏幕状态。
- **光标处理**：跟踪光标在文本输入和控制序列响应中的位置。

终端是有状态的，这意味着它正确重构多字节字符，并在分段输入之间保持连续性。

## 向终端发送数据 {#sending-data-to-the-terminal}

数据通过 `write` 和 `writeln` 方法发送到终端：

- `write(Object data)`：将数据发送到终端流中。
- `writeln(Object data)`：发送数据后跟一个换行符。

终端将所有传入数据处理为 **UTF-16** 字符串。即使输入分段到达，它也会自动处理多字节字符。

### 示例 {#example}
```java
terminal.write("echo Hello World\n");
terminal.writeln("Ready.");
```

您还可以附加一个回调，在数据块处理完毕后运行：

```java
terminal.write("Long command output", e -> {
  System.out.println("Data processed.");
});
```

## 接收用户输入 {#receiving-user-input}

终端通过两个事件捕获用户生成的输入：

- **数据事件 (`onData`)**：在发生文本输入时触发，发送 Unicode 字符。
- **按键事件 (`onKey`)**：在每次按键时触发，包括按键代码和修饰符的信息，如 <kbd>Ctrl</kbd> 或 <kbd>Alt</kbd>。

这些事件可用于将用户输入转发到后端、更新用户界面元素或触发自定义操作。

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

终端捕获的所有用户输入（例如来自 `onData` 事件的输入）都会作为 UTF-16 字符串发出。  
如果您的后端期望不同的编码（例如 UTF-8 字节），您必须手动转码数据。

:::info 传统编码
终端 **不支持传统编码** 如 `ISO-8859`。  
如果您需要与非 UTF-8 系统兼容，使用外部转码器（例如 [`luit`](https://linux.die.net/man/1/luit) 或 [`iconv`](https://en.wikipedia.org/wiki/Iconv)）在将数据写入或从终端读取之前进行转换。
:::

## 处理大数据流 {#handling-large-data-streams}

由于终端无法立即呈现无限输入，因此它维护一个内部输入缓冲区。如果这个缓冲区变得过大（默认约为 `50MB`），新的传入数据可能会被丢弃以保护系统性能。

要妥善管理快速数据源，您应实现 **流控制**。

### 基本流控制示例 {#basic-flow-control-example}

暂停您的后端，直到终端完成处理一个数据块：

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
path='/webforj/serverlogs?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/terminal/ServerLogsView.java'
height='400px'
/>

## 自定义 {#customization}

### 终端选项 {#terminal-options}

`TerminalOptions` 类允许您配置行为：

- 光标闪烁。
- 字体设置（系列、大小、粗细）。
- 倒滚缓冲区大小。
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

您可以使用 `TerminalTheme` 来为终端设置样式，它定义：

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

终端支持一系列用于光标移动、屏幕更新和文本格式化的标准控制序列。

识别的组：

- **`C0` 控制代码**（单字节 7 位命令，`\x00`, `\x1F`，如退格和换行）
- **`C1` 控制代码**（单字节 8 位命令，`\x80`, `\x9F`）
- **`ESC` 序列**（以 `ESC`（`\x1B`）开头，如保存/恢复光标，屏幕对齐）
- **`CSI` 序列**（控制序列引导器，`ESC [` 或 `CSI (\x9B)`，用于滚动、清除和样式等操作）
- **`DCS` 序列**（设备控制字符串，`ESC P` 或 `DCS (\x90)`）
- **`OSC` 序列**（操作系统命令，`ESC ]` 或 `OSC (\x9D)`，用于设置窗口标题、超链接和颜色）

:::info 处理异国情调和自定义序列
一些异国情调的序列类型如 `APC`、`PM` 和 `SOS` 被识别但会静默忽略。  
如果需要，可以通过集成支持自定义序列。
:::

## 样式 {#styling}

<TableBuilder name="Terminal" />
