---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: e50a52f19876f98eec1bd825ca82cd6a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` 是一个文本输入控件，旨在精确和结构化的时间输入。它允许用户以**数字**的形式输入时间，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码是一个字符串，指定预期的时间格式，引导输入和显示。

此组件支持灵活的解析、验证、本地化和值恢复。它在时间敏感的表单中尤为有用，如日程安排、考勤表和预订。

:::tip 寻找日期输入？
`MaskedTimeField` 是专为**时间**输入而构建的。如果您正在寻找一个组件来处理具有类似掩码格式的**日期**，请查看 [`MaskedDateField`](./datefield.md)。
:::

## 基础 {#basics}

`MaskedTimeField` 可以在有或没有参数的情况下实例化。您可以定义初始值、标签、占位符和用于值更改的事件监听器。

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## 掩码规则 {#mask-rules}

`MaskedTimeField` 使用格式指示符来定义时间的解析和显示方式。每个格式指示符以 `%` 开头，后跟一个代表时间组件的字母。

### 时间格式指示符 {#time-format-indicators}

| 格式 | 描述            |
|------|-----------------|
| `%H` | 小时（24小时）  |
| `%h` | 小时（12小时）  |
| `%m` | 分钟            |
| `%s` | 秒              |
| `%p` | 上午/下午       |

### 修饰符 {#modifiers}

修饰符细化时间组件的显示：

| 修饰符 | 描述                   |
|--------|----------------------|
| `z`    | 零填充                 |
| `s`    | 短文本表示             |
| `l`    | 长文本表示             |
| `p`    | 压缩数字               |
| `d`    | 小数（默认格式）      |

这些允许灵活和适合本地化的时间格式。

## 时间格式本地化 {#time-format-localization}

`MaskedTimeField` 通过设置适当的区域设置支持本地化。这确保了时间输入和输出符合地区惯例。

```java
field.setLocale(Locale.GERMANY);
```

这会影响 AM/PM 指示符的显示方式、分隔符的处理方式以及值的解析方式。

## 解析逻辑 {#parsing-logic}

`MaskedTimeField` 根据定义的时间掩码解析用户输入。它接受完整和缩写的数字输入，可以有或没有分隔符，允许灵活输入，同时确保时间有效。
解析行为取决于掩码定义的格式顺序（例如，`%Hz:%mz` 表示小时/分钟）。此格式决定了数字序列的解释方式。

### 示例解析场景 {#example-parsing-scenarios}

| 输入  | 掩码        | 被解释为    |
|-------|-------------|-------------|
| `900` | `%Hz:%mz`   | `09:00`     |
| `1345`| `%Hz:%mz`   | `13:45`     |
| `0230`| `%hz:%mz %p`| `02:30 AM`  |
| `1830`| `%hz:%mz %p`| `06:30 PM`  |

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedTimeField` 中允许的时间范围：

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

这两个方法接受 [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) 类型的值。超出定义范围的输入被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedTimeField` 包含一个恢复功能，可以将字段的值重置为预定义或原始状态。这对于撤销更改或返回到默认时间非常有用。

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **通过编程**，调用 `restoreValue()`
- **通过键盘**，按下 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器覆盖）

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## 验证模式 {#validation-patterns}

您可以使用正则表达式通过 `setPattern()` 方法应用客户端验证规则：

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

此模式确保仅将符合 `HH:mm` 格式（两个数字、冒号、两个数字）的值视为有效。

:::tip 正则表达式格式
模式必须遵循 JavaScript RegExp 语法，如 [这里](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 所述。
:::

:::warning 输入处理注意事项
该字段会根据当前掩码尝试解析和格式化数字时间输入。但是，用户仍然可以手动输入不符合预期格式的值。如果输入在语法上是有效的但语义上不正确或无法解析（例如 `99:99`），它可能会通过模式检查但未能通过逻辑验证。
即使设置了正则表达式模式，您仍应在应用逻辑中验证输入值，以确保时间既格式正确又有意义。
:::

## 时间选择器 {#time-picker}

`MaskedTimeField` 包含一个内置的时间选择器，允许用户通过视觉方式选择时间，而不是键入。这提高了对技术水平较低的用户的可用性，或者当需要精确输入时。

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

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

您可以配置选择器在用户与字段交互时自动打开（例如点击、按回车或箭头键）：

```java
picker.setAutoOpen(true);
```

:::tip 强制通过选择器选择
为了确保用户只能使用选择器选择时间（而不是手动输入），结合以下两个设置：

```java
field.getPicker().setAutoOpen(true); // 用户交互时打开选择器
field.setAllowCustomValue(false);    // 禁用手动文本输入
```

该设置确保所有时间输入均通过选择器 UI 进行，这在您想要严格控制格式并消除来自手动输入的解析问题时非常有用。
:::

### 手动打开选择器 {#manually-open-the-picker}

要通过程序打开时间选择器：

```java
picker.open();
```

或使用别名：

```java
picker.show(); // 与 open() 相同
```

### 设置选择器步长 {#setting-the-picker-step}

您可以使用 `setStep()` 定义选择器中可选择时间的间隔。这允许您控制时间选项的精细程度—非常适合像每 15 分钟调度块这样的场景。

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning 步长约束
步长必须整除一个小时或一天，否则将抛出异常。
:::

这确保下拉列表包含可预测、均匀间隔的值，如 `09:00`、`09:15`、`09:30` 等。

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` 扩展了 [`MaskedTimeField`](#basics)，添加了旋转控制，让用户可以使用箭头键或 UI 按钮增减时间。它提供了一种更指导性的交互风格，尤其适用于桌面风格的应用程序。

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### 主要特点 {#key-features}

- **互动时间步进：**  
  使用箭头键或旋转按钮增减时间值。

- **可定制的旋转单位：**  
  使用 `setSpinField()` 选择要修改的时间部分：

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  选项包括 `HOUR`、`MINUTE`、`SECOND` 和 `MILLISECOND`。

- **最小/最大边界：**  
  继承了使用 `setMin()` 和 `setMax()` 的最小和最大允许时间支持。

- **格式化输出：**  
  完全兼容 `MaskedTimeField` 的掩码和本地化设置。

### 示例：按小时配置步进 {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## 样式 {#styling}

<TableBuilder name="MaskedTimeField" />
