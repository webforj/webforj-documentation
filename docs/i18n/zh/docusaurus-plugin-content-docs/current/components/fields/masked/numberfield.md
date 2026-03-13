---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 6eae8d772ec386aff55df31b674a1e84
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个用于结构化数字输入的文本输入框。它确保数字根据定义的掩码进行一致的格式化，特别适用于财务表单、定价字段或任何对精确度和可读性要求较高的输入。

此组件支持数字格式化、小数点/分组字符的本地化，以及可选的值约束，如最小值或最大值。

<!-- INTRO_END -->

## 基础 {#basics}

`MaskedNumberField` 可以通过或不通过参数进行实例化。它支持设置初始值、标签、占位符和事件监听器以响应值变化。

此演示展示了一个 **小费计算器**，它使用 `MaskedNumberField` 进行直观的数字输入。一个字段配置为接受格式化的账单金额，另一个字段捕获整数形式的小费百分比。这两个字段应用数字掩码，以确保格式的一致性和可预测性。

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串来控制数字输入的格式和显示。每个掩码中的字符定义了特定的格式化行为，以便精确控制数字的显示方式。

### 掩码字符 {#mask-characters}

| 字符    | 描述                          |
|---------|-------------------------------|
| `0`     | 始终用数字（0–9）替换。      |
| `#`     | 抑制前导零。如果在小数点左侧替换，则用填充字符替换；如果在尾随数字，则用空格或零替换。否则，替换为数字。 |
| `,`     | 用作分组分隔符（例如，千位数）。如果前面没有数字，则用填充字符替换；否则，显示为逗号。 |
| `-`     | 如果数字为负，则显示负号(`-`)。如果为正，则用填充字符替换。 |
| `+`     | 对于正数显示 `+`，负数显示 `-`。 |
| `$`     | 始终显示美元符号。            |
| `(`     | 对于负值插入左括号 `(`。如果为正，则用填充字符替换。 |
| `)`     | 对于负值插入右括号 `)`。如果为正，则用填充字符替换。 |
| `CR`    | 对于负数显示 `CR`。如果数字为正，则显示两个空格。 |
| `DR`    | 对于负数显示 `CR`。对于正数显示 `DR`。 |
| `*`     | 插入星号 `*`。                |
| `.`     | 标记小数点。如果输出中没有数字，则用填充字符替换；小数点后的填充字符视为空格。 |
| `B`     | 始终变为空格。任何其他字面字符按原样显示。 |

以上字符中的一些可以在掩码中出现多次以进行格式化。这些字符包括 `-`、`+`、`$` 和 `(`。如果这些字符中的任何一个存在于掩码中，第一个遇到的字符将移动到最后一个被填充字符替换的 `#` 或 `,` 之后的位置。如果没有这样的替换位置，则该字符保持原位。

:::info 无自动舍入
字段中的掩码并**不**进行舍入。例如，将值如 `12.34567` 放入用 `###0.00` 掩码的字段中，你将得到 `12.34`。
:::

## 分组和小数点分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持 **分组** 和 **小数** 字符的自定义，使数字格式化适应不同的地区或商业惯例。

- **分组分隔符** 用于视觉上分隔千位数（例如 `1,000,000`）。
- **小数分隔符** 表示数字的小数部分（例如 `123.45`）。

这在国际应用中很有用，因为不同地区使用不同的字符（例如 `.` 与 `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(","); // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序的当前区域设置应用分组和小数分隔符。您随时可以使用提供的设置器覆盖它们。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持控制是否允许负数的选项。

默认情况下，负值如 `-123.45` 是允许的。要防止这种情况，请使用 `setNegateable(false)` 将输入限制为仅正值。

这在必须确保数量、总额或百分比等值始终为非负的商业场景中非常有用。

```java
field.setNegateable(false);
```

当 `negatable` 设置为 `false` 时，字段将阻止输入负号或以其他方式输入负值的尝试。

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## 最小值和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数字边界。这些约束有助于确保用户输入保持在有效、预期的范围内。

- **最小值**  
  使用 `setMin()` 定义最低可接受数字：

  ```java
  field.setMin(10.0); // 最小值：10
  ```

  如果用户输入的数字低于此阈值，将被视为无效。

- **最大值**  
  使用 `setMax()` 定义最高可接受数字：

  ```java
  field.setMax(100.0); // 最大值：100
  ```

  超出此限制的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持恢复功能，将字段的值重置为预定义状态。当用户需要撤销更改、恢复意外编辑或返回已知默认值时，这非常有用。

要启用此行为，请使用 `setRestoreValue()` 定义目标值。当需要时，可以使用 `restoreValue()` 程序化地重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **程序化** 使用 `restoreValue()`
- **通过键盘**，按下 <kbd>ESC</kbd> （这是默认的恢复键，除非被覆盖）

恢复值必须明确设置。如果未定义，则不会恢复字段。

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 扩展了 [`MaskedNumberField`](#basics)，添加了旋转器控件，允许用户使用步进按钮或箭头键增加或减少值。这非常适合需要增量更改的输入，如数量、价格调整、评级控件或任何场景。

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### 关键特性 {#key-features}

- **步进增量**  
  使用 `setStep()` 定义每次旋转时数值应变化的幅度：

  ```java
  spinner.setStep(5.0); // 每次旋转加或减 5
  ```

- **交互式控件**  
  用户可以点击旋转按钮或使用键盘输入来调整数值。

- **支持所有 `MaskedNumberField` 的功能**  
  完全支持掩码、格式化、分组/小数字符、最小/最大约束和恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
