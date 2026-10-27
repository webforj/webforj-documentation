---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: bba6de4e793a65cc887af236d206bb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一种文本输入，用于根据定义的掩码一致地格式化数字输入。它对于财务表单、定价字段或任何需要精确和可读性的输入都很有用。

此组件可以无参数或带参数进行实例化。它支持数字格式化、小数/分组字符的本地化，以及可选的值约束，例如最小值或最大值。它还支持设置初始值、标签、占位符以及响应值更改的事件侦听器。

<!-- INTRO_END -->

下面的示例展示了一个 **小费计算器**，该计算器使用 `MaskedNumberField` 进行直观的数字输入。一个字段配置用于接受格式化的账单金额，而另一个则捕获整数的小费百分比。

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串控制如何格式化和显示数字输入。
掩码中的每个字符定义了特定的格式行为，可以精确控制数字的显示方式。

:::tip 以编程方式应用掩码
要在字段外使用相同的掩码语法格式化数字，例如在 [`Table`](/docs/components/table/overview) 中呈现数据时，使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 掩码字符 {#mask-characters}

| 字符      | 描述                                   |
|-----------|--------------------------------------|
| `0`       | 始终被数字（0–9）替换。                  |
| `#`       | 抑制前导零。替换为小数点左侧的填充字符。对于尾随数字，替换为一个空格或零。否则，替换为数字。 |
| `,`       | 用作分组分隔符（例如千位）。如果没有前导数字，则替换为填充字符；否则，显示为逗号。 |
| `-`       | 如果数字为负，则显示负号（`-`）。如果正数，则用填充字符替换。 |
| `+`       | 显示正数的 `+` 或负数的 `-`。            |
| `$`       | 始终显示美元符号。                       |
| `(`       | 对于负值插入左括号 `(`。正数用填充字符替换。 |
| `)`       | 对于负值插入右括号 `)`。正数用填充字符替换。 |
| `CR`      | 对于负数显示 `CR`。如果数字是正数，则显示两个空格。 |
| `DR`      | 对于负数显示 `CR`。对于正数显示 `DR`。   |
| `*`       | 插入星号 `*`。                       |
| `.`       | 标记小数点。如果输出中没有数字，则替换为填充字符。小数点后，填充字符被视为空格。 |
| `B`       | 始终变为空格。其他字面字符按原样显示。 |

上述某些字符可以在掩码中出现多次以进行格式化。这些字符包括 `-`、`+`、`$` 和 `(`。如果掩码中存在任何这些字符，则首先遇到的字符将移动到最后一个用填充字符替换的 `#` 或 `,` 的位置。如果不存在这样的输入位置，则双字符的位置保持不变。

:::info 无自动四舍五入
字段中的掩码 **不** 会进行四舍五入。例如，当将值 `12.34567` 放入用 `###0.00` 掩码的字段时，您将得到 `12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持自定义 **分组** 和 **小数** 字符，使数字格式化易于适应不同的地区或商业惯例。

- **分组分隔符** 用于直观地分隔千位（例如 `1,000,000`）。
- **小数分隔符** 表示数字的分数部分（例如 `123.45`）。

这在国际应用中非常有用，因为不同区域使用不同的字符（例如 `.` 与 `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(","); // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序的当前区域设置应用分组和小数分隔符。您可以随时使用提供的设置器覆盖它们。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持一个选项来控制是否允许负数。

默认情况下，负值如` -123.45` 是被允许的。要防止这种情况，请使用 `setNegateable(false)` 将输入限制为仅正值。

在某些商业场景中，像数量、总额或百分比这样的值必须始终非负，这很有用。

```java
field.setNegateable(false);
```

当 `negatable` 设置为 `false` 时，字段将阻止任何尝试输入负号或以其他方式输入负值的操作。

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## 最小值和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数字边界。
这些约束有助于确保用户输入保持在有效的预期范围内。

- **最小值**
  使用 `setMin()` 定义最低可接受的数字：

  ```java
  field.setMin(10.0); // 最小值: 10
  ```

  如果用户输入的数字低于此阈值，将被视为无效。

- **最大值**
  使用 `setMax()` 定义最高可接受的数字：

  ```java
  field.setMax(100.0); // 最大值: 100
  ```

  超过此限制的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持一个恢复功能，将字段的值重置为预定义状态。
当用户需要撤消更改、恢复意外编辑或返回已知默认值时，这非常有用。

要启用此行为，请使用 `setRestoreValue()` 定义目标值。
在需要时，可以使用 `restoreValue()` 以编程方式重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **通过编程** 使用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd> （这是默认的恢复键，除非被重写）

恢复值必须被显式设置。如果未定义，则该功能将不会使字段恢复。

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 扩展了 `MaskedNumberField`，增加了旋转控制，允许用户通过步骤按钮或箭头键增加或减少值。
这对于数量、定价调整、评分控制或任何用户进行增量更改的场景都很理想。

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### 关键功能 {#key-features}

- **步进增量**
  使用 `setStep()` 定义每次旋转值应增加多少：

  ```java
  spinner.setStep(5.0); // 每次旋转增加或减少5
  ```

- **交互式控件**
  用户可以点击旋转按钮或使用键盘输入来调整值。

- **支持 `MaskedNumberField` 的所有功能**
  完全支持掩码、格式化、分组/小数字符、最小/最大约束和恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
