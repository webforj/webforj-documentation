---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: f247d28107239e0db4a24823c8a1ef60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个文本输入框，旨在根据定义的掩码一致地格式化数字输入。它对于财务表单、定价字段或任何需要精确度和可读性的输入都很有用。

该组件可以带或不带参数实例化。它支持数字格式化、十进制/分组字符的本地化以及最小值或最大值等可选限制。它还支持设置初始值、标签、占位符，以及响应值变化的事件监听器。

<!-- INTRO_END -->

下面的示例展示了一个使用 `MaskedNumberField` 进行直观数字输入的 **小费计算器**。一个字段配置为接受格式化的账单金额，而另一个字段捕获整数小费百分比。

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串控制数字输入的格式和显示方式。
掩码中的每个字符定义了特定的格式行为，允许精确控制数字的显示方式。

:::tip 以编程方式应用掩码
要在字段外部使用相同的掩码语法格式化数字，例如在 [`Table`](/docs/components/table/overview) 中呈现数据时，使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 实用程序类。
:::

### 掩码字符 {#mask-characters}

| 字符 | 描述 |
|------|------|
| `0`  | 始终由数字 (0–9) 替换。 |
| `#`  | 抑制前导零。由小数点左侧的填充字符替换。对于尾随数字，由空格或零替换。否则，由数字替换。 |
| `,`  | 用作分组分隔符（例如千位）。如果前面没有数字，则由填充字符替换。否则，显示为逗号。 |
| `-`  | 如果数字为负，则显示负号（`-`）。如果为正，则由填充字符替换。 |
| `+`  | 对于正数显示 `+`，对于负数显示 `-`。 |
| `$`  | 始终结果为美元符号。 |
| `(`  | 对于负值插入左括号 `(`。如果为正，则由填充字符替换。 |
| `)`  | 对于负值插入右括号 `)`。如果为正，则由填充字符替换。 |
| `CR` | 对于负数显示 `CR`。如果数字为正，则显示两个空格。 |
| `DR` | 对于负数显示 `CR`。对于正数显示 `DR`。 |
| `*`  | 插入星号 `*`。 |
| `.`  | 标记小数点。如果输出中没有数字，则由填充字符替换。小数点后的填充字符视为空格处理。 |
| `B`  | 始终变为空格。任何其他字面字符按原样显示。 |

上述某些字符可以在掩码中出现多次以进行格式化。这些包括 `-`、`+`、`$` 和 `(`。如果这些字符在掩码中存在，则第一次遇到的字符将被移动到填充字符替换的最后一个 `#` 或 `,` 位置。如果没有这样的替代位置，则双字符将保持在其位置。

:::info 无自动舍入
字段中的掩码**不会**舍入。例如，当将值 `12.34567` 放入使用 `###0.00` 掩码的字段时，你将得到 `12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持自定义 **分组** 和 **小数** 字符，使数字格式化能够轻松适应不同的区域或商业惯例。

- **分组分隔符** 用于可视上分离千位（例如 `1,000,000`）。
- **小数分隔符** 表示数的分数部分（例如 `123.45`）。

这在国际应用中非常有用，因为不同地区使用不同的字符（例如 `.` vs `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(",");  // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序当前的区域设置应用分组和小数分隔符。你可以随时使用提供的设置器覆盖它们。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持一个选项来控制是否允许负数。

默认情况下，像 `-123.45` 的负值是允许的。要防止这种情况，请使用 `setNegateable(false)` 将输入限制为仅正值。

这在业务场景中非常有用，例如数量、总数或百分比等值必须始终为非负数。

```java
field.setNegateable(false);
```

当 `negateable` 设置为 `false` 时，字段将阻止任何尝试输入负号或以其他方式输入负值的操作。

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## 最小值和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数字边界。
这些约束有助于确保用户输入保持在有效的、预期的范围内。

- **最小值**
  使用 `setMin()` 定义最低可接受的数字：

  ```java
  field.setMin(10.0); // 最小值：10
  ```

  如果用户输入的数字低于此阈值，将被视为无效。

- **最大值**
  使用 `setMax()` 定义最高可接受的数字：

  ```java
  field.setMax(100.0); // 最大值：100
  ```

  超过这个限制的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持恢复功能，将字段的值重置为预定义状态。
当用户需要撤销更改、恢复意外编辑或返回到已知默认值时，这非常有用。

要恢复特定值，请使用 `setRestoreValue()` 定义。当需要时，可以使用 `restoreValue()` 以编程方式重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **以编程方式** 使用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非被覆盖）

如果没有设置恢复值，`restoreValue()` 和 <kbd>ESC</kbd> 都会将字段值设置为 `null`。

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 通过添加旋转控制扩展了 `MaskedNumberField`，允许用户使用步进按钮或箭头键增加或减少值。这非常适合数量、定价调整、评分控制或任何用户进行增量更改的场景。

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### 关键特性 {#key-features}

- **步进增量**
  使用 `setStep()` 定义每次旋转值的变化量：

  ```java
  spinner.setStep(5.0); // 每次旋转加或减 5
  ```

- **交互式控制**
  用户可以点击旋转按钮或使用键盘输入来调整值。

- **所有来自 MaskedNumberField 的功能**
  完全支持掩码、格式、分组/小数字符、最小/最大约束和恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
