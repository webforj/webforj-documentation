---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 97e5bc068e72cfd770c26fed4ceca434
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` 是一个文本输入框，可以让用户以 **数字** 的形式输入时间，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码指定了期望的时间格式，指导输入和显示。该组件支持灵活的解析、验证、本地化和数值恢复，以实现一致的时间处理。

<!-- INTRO_END -->

## 基础 {#basics}

:::tip 寻找日期输入?
`MaskedTimeField` 是专为 **仅时间** 输入构建的。如果您正在寻找一个处理 **日期** 的组件，具有类似的基于掩码的格式化，请查看 [`MaskedDateField`](./datefield.md)。
:::

`MaskedTimeField` 可以在有无参数的情况下实例化。您可以定义初始值、标签、占位符和用于值更改的事件监听器。

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## 掩码规则 {#mask-rules}

`MaskedTimeField` 使用格式指示符定义时间的解析和显示方式。每个格式指示符以 `%` 开头，后跟一个表示时间组件的字母。

:::tip 以编程方式应用掩码
要使用相同的掩码语法在字段外格式化或解析时间，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 实用程序类。
:::

### 时间格式指示符 {#time-format-indicators}

| 格式   | 描述               |
|--------|---------------------|
| `%H`   | 小时（24 小时制）   |
| `%h`   | 小时（12 小时制）   |
| `%m`   | 分钟               |
| `%s`   | 秒                 |
| `%p`   | AM/PM              |

### 修饰符 {#modifiers}

修饰符细化时间组件的显示：

| 修饰符 | 描述                     |
|--------|---------------------------|
| `z`    | 补零                     |
| `s`    | 短文本表示               |
| `l`    | 长文本表示               |
| `p`    | 紧凑数字                 |
| `d`    | 十进制（默认格式）       |

这些允许灵活且适合地域的时间格式。

## 时间格式本地化 {#time-format-localization}

`MaskedTimeField` 通过设置适当的区域设置来支持本地化。这确保时间输入和输出符合地区惯例。

```java
field.setLocale(Locale.GERMANY);
```

这会影响 AM/PM 指示符的显示、分隔符的处理以及数值的解析。

## 解析逻辑 {#parsing-logic}

`MaskedTimeField` 根据定义的时间掩码解析用户输入。它接受完整和简写的数字输入，可以带有或不带有分隔符，允许灵活输入，同时确保时间有效。
解析行为取决于掩码定义的格式顺序（例如，`%Hz:%mz` 表示小时/分钟）。该格式决定了数字序列如何被解释。

### 示例解析场景 {#example-parsing-scenarios}

| 输入   | 掩码          | 解释为 |
|--------|---------------|--------|
| `900`  | `%Hz:%mz`     | `09:00` |
| `1345` | `%Hz:%mz`     | `13:45` |
| `0230` | `%hz:%mz %p`  | `02:30 AM` |
| `1830` | `%hz:%mz %p`  | `06:30 PM` |

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedTimeField` 中允许的时间范围：

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

这两个方法接受 [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) 类型的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedTimeField` 包括一个恢复功能，可以将字段的值重置为预定义的或原始的状态。这在撤销更改或返回到默认时间时非常有用。

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **通过编程**，调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认恢复键，除非被事件监听器覆盖）

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

此模式确保仅匹配 `HH:mm` 格式（两位数字、冒号、两位数字）的值被视为有效。

:::tip 正则表达式格式
该模式必须遵循 JavaScript RegExp 语法，如 [此处](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 所述。
:::

:::warning 输入处理说明
该字段尝试根据当前掩码解析和格式化数字时间输入。然而，用户仍然可以手动输入不符合预期格式的值。如果输入在语法上有效但在语义上不正确或不可解析（例如 `99:99`），则可能会通过模式检查，但逻辑验证失败。
即使设置了正则表达式模式，您也应始终在应用逻辑中验证输入值，以确保时间既格式正确又有意义。
:::

## 时间选择器 {#time-picker}

`MaskedTimeField` 包括一个内置的时间选择器，让用户可以通过视觉选择时间，而不是输入。这增强了以较少技术用户的可用性或在需要精确输入时。

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

您可以配置选择器在用户与字段交互时（例如单击、按 Enter 或箭头键）自动打开：

```java
picker.setAutoOpen(true);
```

:::tip 通过选择器强制选择
为了确保用户只能通过选择器选择时间（而不是手动输入），请结合以下两个设置：

```java
field.getPicker().setAutoOpen(true); // 在用户交互时打开选择器
field.setAllowCustomValue(false);    // 禁用手动文本输入
```

此设置确保所有时间输入都通过选择器 UI 输入，这在您希望严格控制格式并消除从输入的解析问题时特别有用。
:::

### 手动打开选择器 {#manually-open-the-picker}

要以编程方式打开时间选择器：

```java
picker.open();
```

或者使用别名：

```java
picker.show(); // 与 open() 相同
```

### 设置选择器步长 {#setting-the-picker-step}

您可以使用 `setStep()` 定义选择器中可选时间之间的间隔。这使您能够控制时间选项的粒度——对于如每 15 分钟调度等场景理想。

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning 步长约束
步长必须整除一个小时或整个一天。否则，将抛出异常。
:::

这确保下拉列表包含预测性和均匀分布的值，如 `09:00`、`09:15`、`09:30` 等。

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` 扩展了 [`MaskedTimeField`](#basics)，增加了让用户使用箭头键或 UI 按钮增减时间的旋转控制。它提供了一种更指导式的交互方式，尤其适合桌面风格的应用程序。

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### 主要特点 {#key-features}

- **交互式时间步进：**  
  使用箭头键或旋转按钮增减时间值。

- **可自定义旋转单位：**  
  选择要修改的时间部分，使用 `setSpinField()`：

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  选项包括 `HOUR`、`MINUTE`、`SECOND` 和 `MILLISECOND`。

- **最小/最大边界：**  
  继承了使用 `setMin()` 和 `setMax()` 限制最小和最大允许时间的支持。

- **格式化输出：**  
  完全兼容 `MaskedTimeField` 的掩码和本地化设置。

### 示例：按小时配置步进 {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## 样式 {#styling}

<TableBuilder name="MaskedTimeField" />
