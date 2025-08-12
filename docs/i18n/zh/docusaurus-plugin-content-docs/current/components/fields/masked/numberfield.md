---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 626c7e147632731dfdc761116a8abdc9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个用于结构化数字输入的文本输入。它确保基于定义的掩码一致地格式化数字，对于财务表单、定价字段或任何需要精确度和可读性的输入特别有用。

此组件支持数字格式化、小数/分组字符的本地化，以及诸如最小值或最大值之类的可选值约束。

## 基础 {#basics}

`MaskedNumberField` 可以实例化时带参数或不带参数。它支持设置初始值、标签、占位符和侦听器以响应值的变化。

此演示展示了一个**小费计算器**，使用 `MaskedNumberField` 进行直观的数字输入。一个字段配置为接受格式化的账单金额，而另一个字段则捕获整数小费百分比。两个字段应用数字掩码，以确保格式一致且可预测。

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串来控制数字输入的格式和显示。 
掩码中的每个字符定义了特定的格式化行为，使数字的外观得以精确控制。

### 掩码字符 {#mask-characters}

| 字符   | 描述                                                                 |
|--------|----------------------------------------------------------------------|
| `0`    | 始终被数字（0–9）替换。                                          |
| `#`    | 抑制前导零。用小数点左侧的填充字符替换。对于尾随数字，用空格或零替换。否则，用数字替换。 |
| `,`    | 用作分组分隔符（例如千位）。如果没有数字在前，则用填充字符替换。否则，显示为逗号。 |
| `-`    | 如果数字是负数，则显示负号（`-`）。如果是正数，则用填充字符替换。               |
| `+`    | 对于正数显示`+`，对于负数显示`-`。                                   |
| `$`    | 始终显示美元符号。                                                  |
| `(`    | 对于负值插入左括号`(`。如果是正数，则用填充字符替换。                |
| `)`    | 对于负值插入右括号`)`。如果是正数，则用填充字符替换。                |
| `CR`   | 对于负数显示`CR`。如果数字是正数，则显示两个空格。                     |
| `DR`   | 对于负数显示`CR`。对于正数显示`DR`。                                 |
| `*`    | 插入星号`*`。                                                       |
| `.`    | 标记小数点。如果输出中没有数字，则用填充字符替换。在小数之后，填充字符被视为空间。 |
| `B`    | 始终变为空格。任何其他字面字符原样显示。                            |

上述某些字符可以在掩码中出现多次用于格式化。这些字符包括`-`、`+`、`$`和`(`。如果在掩码中存在任何这些字符，则遇到的第一个字符将移到最后一个`#`或`,`被填充字符替换的位置。如果不存在这样的位，则双字符将保留在原处。

:::info 无自动舍入
字段中的掩码**不**进行舍入。例如，将值`12.34567`放入掩码为`###0.00`的字段中，你将得到`12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持自定义 **分组** 和 **小数** 字符，使数字格式适应不同的地区或商业惯例变得容易。

- **分组分隔符** 用于直观地分隔千位（例如 `1,000,000`）。
- **小数分隔符** 指示一个数字的分数部分（例如 `123.45`）。

在国际应用程序中，这对于不同地区使用不同字符（例如 `.` 与 `,`）非常有用。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(","); // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序当前的区域设置应用分组和小数分隔符。你可以随时使用提供的设置器覆盖它们。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持选项，以控制是否允许负数。

默认情况下，允许负值如 `-123.45`。要阻止这一点，请使用 `setNegateable(false)` 限制输入仅为正值。

在业务场景中，数量、总计或百分比等值必须始终为非负值。

```java
field.setNegateable(false);
```

当 `negatable` 设置为 `false` 时，该字段阻止任何输入负号或其他输入负值的尝试。

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## 最小值和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数值边界。
这些约束帮助确保用户输入保持在有效、预期的范围内。

- **最小值**  
  使用 `setMin()` 定义最低可接受的数字：

  ```java
  field.setMin(10.0); // 最小值: 10
  ```

  如果用户输入的数字低于此阈值，则将被视为无效。

- **最大值**  
  使用 `setMax()` 定义最高可接受的数字：

  ```java
  field.setMax(100.0); // 最大值: 100
  ```

  超过此限制的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持一个恢复功能，将字段的值重置为预定义状态。
当用户需要撤消更改、恢复意外编辑或返回到已知默认值时，这非常有用。

要启用此行为，请使用 `setRestoreValue()` 定义目标值。
在需要时，可以使用 `restoreValue()` 程序性地重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **程序性地** 使用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认恢复键，除非被覆盖）

恢复值必须显式设置。如果未定义，则该功能将不会还原字段。

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 扩展了 [`MaskedNumberField`](#basics)，添加了可以让用户使用步进按钮或箭头键增加或减少值的旋钮控件。
这对于数量、定价调整、评分控件或用户进行增量更改的任何场景非常理想。

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### 主要特性 {#key-features}

- **步进增量**  
  使用 `setStep()` 定义每次旋转值应改变多少：

  ```java
  spinner.setStep(5.0); // 每次旋转增加或减少 5
  ```

- **交互控件**  
  用户可以点击旋钮按钮或使用键盘输入来调整值。

- **来自 MaskedNumberField 的所有功能**  
  完全支持掩码、格式化、分组/小数字符、最小/最大约束及恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
