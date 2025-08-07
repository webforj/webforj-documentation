---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 00d399f2bcfb22c884608aa8a0975573
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个用于结构化数字输入的文本输入框。它确保数字根据定义的掩码一致格式化，尤其适用于财务表单、定价字段或任何对精度和可读性要求高的输入。

该组件支持数字格式化、小数/分组字符的本地化，以及诸如最小值或最大值的可选值约束。

## 基础 {#basics}

`MaskedNumberField` 可以带参数或不带参数进行实例化。它支持设置初始值、标签、占位符和事件监听器以响应值的变化。

此演示展示了一个 **小费计算器**，使用 `MaskedNumberField` 进行直观的数字输入。一个字段被配置为接受格式化的账单金额，而另一个字段捕获整数小费百分比。两个字段应用数字掩码以确保一致和可预测的格式化。

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串控制数字输入的格式和显示。每个掩码中的字符定义特定的格式化行为，从而精确控制数字的显示方式。

### 掩码字符 {#mask-characters}

| 字符      | 描述                             |
|-----------|----------------------------------|
| `0`       | 始终由数字（0–9）替换。         |
| `#`       | 抑制前导零。用填充字符替换小数点左侧的字符。对于尾随数字，用空格或零替换。否则，用数字替换。 |
| `,`       | 用作分组分隔符（例如千位）。如果没有数字在前面，用填充字符替换。否则，显示为逗号。 |
| `-`       | 如果数字为负，则显示负号（`-`）。如果为正则用填充字符替换。 |
| `+`       | 对于正数显示`+`，负数显示`-`。 |
| `$`       | 总是变为美元符号。               |
| `(`       | 为负值插入左括号`(`。如果为正，则用填充字符替换。 |
| `)`       | 为负值插入右括号`)`。如果为正，则用填充字符替换。 |
| `CR`      | 对于负数显示`CR`。如果数字为正，显示两个空格。 |
| `DR`      | 对于负数显示`CR`。对于正数显示`DR`。 |
| `*`       | 插入星号`*`。                     |
| `.`       | 标记小数点。如果输出中没有数字，用填充字符替换。小数点后的填充字符被视为空格。 |
| `B`       | 始终变为空格。其他字面字符按原样显示。 |

上述某些字符可以在掩码中出现多次以进行格式化。这些字符包括`-`、`+`、`$` 和 `(`。如果掩码中存在任何这些字符，则第一个遇到的字符将移到最后一个被填充字符替换的`#`或`,`的位置。如果不存在这样的位子，则双字符保持原位。

:::info 无自动四舍五入
字段中的掩码**不会**进行四舍五入。例如，当将值`12.34567`放入掩码为`###0.00`的字段时，您将获得`12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持对 **分组** 和 **小数** 字符的自定义，使数字格式化适应不同的区域或业务惯例。

- **分组分隔符** 用于视觉上区分千位（例如 `1,000,000`）。
- **小数分隔符** 表示数字的小数部分（例如 `123.45`）。

这在国际应用中非常有用，因为不同地区使用不同字符（例如 `.` 和 `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(","); // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序当前区域施加分组和小数分隔符。您可以随时使用提供的设置方法覆盖它们。
:::

## 可否为负 {#negateable}

`MaskedNumberField` 支持控制是否允许负数的选项。

默认情况下，像 `-123.45` 这样的负值是允许的。要阻止此行为，请使用 `setNegateable(false)` 将输入限制为仅正值。

在必须始终非负的业务场景中，如数量、总额或百分比等值，这一功能非常有用。

```java
field.setNegateable(false);
```

当 `negatable` 设置为 `false` 时，字段将阻止任何尝试输入负号或以其他方式输入负值。

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## 最小和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数值边界。这些约束有助于确保用户输入保持在有效的预期范围内。

- **最小值**  
  使用 `setMin()` 定义最低可接受的数字：

  ```java
  field.setMin(10.0); // 最小值: 10
  ```

  如果用户输入低于此阈值的数字，它将被视为无效。

- **最大值**  
  使用 `setMax()` 定义最高可接受的数字：

  ```java
  field.setMax(100.0); // 最大值: 100
  ```

  超出此限制的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持恢复功能，将字段的值重置为预定义状态。当用户需要撤销更改、回退意外编辑或返回到已知默认值时，这非常有用。

要启用此行为，请使用 `setRestoreValue()` 定义目标值。当需要时，可以使用 `restoreValue()` 程序性地重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **以编程方式** 使用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（除非被覆盖，否则这是默认的恢复键）

恢复值必须显式设置。如果没有定义，该功能将不会恢复字段。

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 通过添加旋转控件来扩展 [`MaskedNumberField`](#basics)，允许用户使用步骤按钮或箭头键增减值。这对于输入数量、价格调整、评分控件或用户进行增量更改的任何场合非常理想。

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### 关键特性 {#key-features}

- **步进增量**  
  使用 `setStep()` 定义每次旋转值应变化多少：

  ```java
  spinner.setStep(5.0); // 每次旋转增加或减少 5
  ```

- **交互控件**  
  用户可以点击旋转按钮或使用键盘输入来调整值。

- **继承自 MaskedNumberField 的所有特性**  
  完全支持掩码、格式化、分组/小数字符、最小/最大约束和恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
