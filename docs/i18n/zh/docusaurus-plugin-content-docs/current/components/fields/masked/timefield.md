---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 2631f01d383c134ba92d8ad03f5a57d3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` 是一个文本输入框，允许用户以 **数字** 形式输入时间，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码指定预期的时间格式，引导输入和显示。该组件支持灵活的解析、验证、本地化和值恢复，以确保一致的时间处理。

<!-- INTRO_END -->

## 基础 {#basics}

:::tip 寻找日期输入？
`MaskedTimeField` 是为 **仅时间** 输入构建的。如果您在寻找一个处理 **日期** 的组件，并具备类似的基于掩码的格式，请查看 [`MaskedDateField`](./datefield.md)。
:::

`MaskedTimeField` 可以在有参数或没有参数的情况下实例化。您可以定义初始值、标签、占位符以及用于值更改的事件监听器。

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## 掩码规则 {#mask-rules}

`MaskedTimeField` 使用格式指示符来定义时间是如何解析和显示的。每个格式指示符以 `%` 开头，后面跟着一个代表时间组件的字母。

:::tip 以编程方式应用掩码
要在字段外使用相同的掩码语法格式或解析时间，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 时间格式指示符 {#time-format-indicators}

| 格式   | 描述                |
|--------|---------------------|
| `%H`   | 小时（24小时制）    |
| `%h`   | 小时（12小时制）    |
| `%m`   | 分钟                |
| `%s`   | 秒钟                |
| `%p`   | 上午/下午           |

### 修饰符 {#modifiers}

修饰符细化时间组件的显示：

| 修饰符 | 描述                     |
|--------|--------------------------|
| `z`    | 补零                     |
| `s`    | 短文本表示               |
| `l`    | 长文本表示               |
| `p`    | 打包数字                 |
| `d`    | 十进制（默认格式）       |

这些允许灵活和适合区域的时间格式化。

## 时间格式本地化 {#time-format-localization}

`MaskedTimeField` 通过设置适当的语言环境支持本地化。这确保时间输入和输出符合地区惯例。

```java
field.setLocale(Locale.GERMANY);
```

这会影响AM/PM指示符的显示、分隔符的处理以及如何解析值。

## 解析逻辑 {#parsing-logic}

`MaskedTimeField` 根据定义的时间掩码解析用户输入。它接受完整和缩写的数字输入，带有或不带有分隔符，允许灵活输入，同时确保有效时间。
解析行为取决于掩码定义的格式顺序（例如 `%Hz:%mz` 用于小时/分钟）。该格式决定了数字序列的解释方式。

### 示例解析场景 {#example-parsing-scenarios}

| 输入   | 掩码         | 解释为      |
|--------|--------------|-------------|
| `900`  | `%Hz:%mz`    | `09:00`     |
| `1345` | `%Hz:%mz`    | `13:45`     |
| `0230` | `%hz:%mz %p` | `02:30 AM`  |
| `1830` | `%hz:%mz %p` | `06:30 PM`  |

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedTimeField` 中允许的时间范围：

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

这两个方法接受 [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) 类型的值。超出定义范围的输入被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedTimeField` 包含一个恢复功能，将字段的值重置为预定义或原始状态。这在撤销更改或返回默认时间时非常有用。

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **以编程方式**，通过调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器重写）

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## 验证模式 {#validation-patterns}

您可以使用正则表达式通过 `setPattern()` 方法应用客户端验证规则：

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

该模式确保仅匹配 `HH:mm` 格式（两个数字、冒号、两个数字）的值被视为有效。

:::tip 正则表达式格式
该模式必须遵循 JavaScript RegExp 语法，如 [此处](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 文档所述。
:::

:::warning 输入处理注意事项
该字段尝试根据当前掩码解析和格式化数字时间输入。然而，用户仍然可以手动输入不匹配预期格式的值。如果输入在语法上有效但在语义上不正确或无法解析（例如 `99:99`），它可能通过模式检查，但未能通过逻辑验证。
即使设置了正则表达式模式，您仍应在应用逻辑中始终验证输入值，以确保时间既格式正确，又有意义。
:::

## 时间选择器 {#time-picker}

`MaskedTimeField` 包含一个内置的时间选择器，允许用户以可视方式选择时间，而不是输入。这增强了对不太专业用户的可用性，或在需要精确输入时。

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### 访问选择器 {#accessing-the-picker}

您可以使用 `getPicker()` 访问时间选择器：

```java
TimePicker picker = field.getPicker();
```

### 显示/隐藏选择器图标 {#showhide-the-picker-icon}

使用 `setIconVisible()` 显示或隐藏字段旁边的时钟图标：

```java
picker.setIconVisible(true); // 显示图标
```

### 自动打开行为 {#auto-open-behavior}

您可以配置选择器在用户与字段交互时自动打开（例如单击、按下 Enter 或方向键）：

```java
picker.setAutoOpen(true);
```

:::tip 强制通过选择器选择
为确保用户只能通过选择器选择时间（而不能手动输入），结合以下两个设置：

```java
field.getPicker().setAutoOpen(true); // 用户与选择器交互时打开选择器
field.setAllowCustomValue(false);    // 禁止手动文本输入
```

此设置保证所有时间输入都来自选择器 UI，这在您希望严格控制格式并消除用户输入的解析问题时非常有用。
:::

### 手动打开选择器 {#manually-open-the-picker}

要以编程方式打开时间选择器：

```java
picker.open();
```

或使用别名：

```java
picker.show(); // 与 open() 相同
```

### 设置选择器步长 {#setting-the-picker-step}

您可以使用 `setStep()` 在选择器中定义可选择时间之间的间隔。这允许您控制时间选项的粒度——适合例如以15分钟为单位的调度场景。

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning 步长约束
步长必须整除一小时或全天。否则，将抛出异常。
:::

这确保下拉列表包含可预测、均匀间隔的值，如 `09:00`、`09:15`、`09:30` 等。

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` 扩展了 [`MaskedTimeField`](#basics)，通过添加旋转控制，让用户使用方向键或 UI 按钮增减时间。它提供了更引导的交互风格，特别在桌面风格应用中非常有用。

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### 主要特性 {#key-features}

- **交互式时间步进：**
  使用方向键或旋转按钮增加或减少时间值。

- **可自定义的旋转单元：**
  选择要修改的时间部分，使用 `setSpinField()`：

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  选项包括 `HOUR`、`MINUTE`、`SECOND` 和 `MILLISECOND`。

- **最小/最大边界：**
  继承通过 `setMin()` 和 `setMax()` 支持最小和最大允许时间。

- **格式化输出：**
  与 `MaskedTimeField` 中的掩码和本地化设置完全兼容。

### 示例：按小时配置步进 {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## 样式 {#styling}

<TableBuilder name="MaskedTimeField" />
