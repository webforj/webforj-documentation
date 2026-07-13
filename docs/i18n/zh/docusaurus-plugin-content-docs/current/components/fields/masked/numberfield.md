---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: 1ce8783919180d45f2f7321c559fc26a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个用于结构化数字输入的文本输入框。它确保数字根据定义的掩码一致格式化，尤其适用于财务表单、定价字段或任何对精确度和可读性要求较高的输入。

该组件支持数字格式化、十进制/分组字符的本地化以及最低值或最高值等可选值限制。

<!-- INTRO_END -->

## 基础 {#basics}

`MaskedNumberField` 可以通过参数或不通过参数实例化。支持设置初始值、标签、占位符以及事件监听器以响应值的变化。

此示例展示了一个**小费计算器**，它使用 `MaskedNumberField` 进行直观的数字输入。一个字段配置为接受格式化的账单金额，而另一个字段捕获一个整数小费百分比。两个字段都应用数字掩码，以确保一致和可预测的格式化。

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串来控制数字输入的格式化和显示。
掩码中的每个字符定义了特定的格式化行为，允许精确控制数字的显示方式。

:::tip 以编程方式应用掩码
要使用相同的掩码语法格式化数字，例如在 [`Table`](/docs/components/table/overview) 中呈现数据，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 掩码字符 {#mask-characters}

| 字符     | 描述                                         |
|----------|--------------------------------------------|
| `0`      | 始终被数字（0–9）替换。                       |
| `#`      | 抑制前导零。在小数点左侧由填充字符替换。对于后续数字，由空格或零替换。否则，由数字替换。 |
| `,`      | 用作分组分隔符（例如千位）。如果没有数字在前面，则由填充字符替换。否则，显示为逗号。 |
| `-`      | 如果数字为负，则显示负号（`-`）。如果为正，则由填充字符替换。        |
| `+`      | 显示`+`用于正数或`-`用于负数。               |
| `$`      | 始终生成美元符号。                          |
| `(`      | 对于负值插入左括号`(`。对于正值由填充字符替换。  |
| `)`      | 对于负值插入右括号`)`。对于正值由填充字符替换。  |
| `CR`     | 对于负数显示`CR`。如果数字为正，则显示两个空格。 |
| `DR`     | 对于负数显示`CR`。对于正数显示`DR`。        |
| `*`      | 插入星号`*`。                              |
| `.`      | 标记小数点。如果输出中没有数字，则由填充字符替换。在小数后，填充字符被视为空格。 |
| `B`      | 始终变为空格。任何其他文字字符按原样显示。    |

上述一些字符可以在掩码中多次出现以进行格式化。这些字符包括`-`、`+`、`$`和`(`。如果掩码中存在任何这些字符，遇到的第一个字符将移动到最后一个位置，其中`#`或`,`被填充字符替换。如果没有这样的位点，则双字符的位置保持不变。

:::info 无自动四舍五入
字段中的掩码**不**进行四舍五入。例如，当在使用`###0.00`掩码的字段中放入一个值如`12.34567`时，您将得到`12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持定制**分组**和**小数**字符，使数字格式化更易于适应不同的地区或商业惯例。

- **分组分隔符**用于视觉上分隔千位（例如`1,000,000`）。
- **小数分隔符**表示数字的小数部分（例如`123.45`）。

这对于国际应用非常有用，因为不同地区使用不同的字符（例如`.` 与 `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(","); // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用的当前语言环境应用分组和小数分隔符。您可以随时使用提供的设置器覆盖它们。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持控制是否允许负数的选项。

默认情况下，负值如`-123.45`是允许的。要防止这种情况，请使用`setNegateable(false)`限制输入为正值。

在业务场景中，这在数量、总和或百分比等值必须始终非负时非常有用。

```java
field.setNegateable(false);
```

当`negatable`设置为`false`时，字段将阻止任何进入负号或其他输入负值的尝试。

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## 最小值和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数值边界。
这些限制有助于确保用户输入保持在有效的预期范围内。

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

  超过此限制的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持恢复功能，将字段的值重置为预定义状态。
当用户需要撤销更改、恢复意外编辑或返回到已知默认值时，这非常有用。

要启用此行为，请使用 `setRestoreValue()` 定义目标值。
必要时，可以使用 `restoreValue()` 程序matically地重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **通过编程** 使用 `restoreValue()`
- **通过键盘**，按下 <kbd>ESC</kbd>（这是默认的恢复键，除非被覆盖）

必须显式设置恢复值。如果未定义，此功能将不会恢复该字段。

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 扩展了 [`MaskedNumberField`](#basics)，添加了让用户使用步进按钮或箭头键来增加或减少值的旋转器控制。
这对于数量、价格调整、评分控制或用户进行增量更改的任何场景非常理想。

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### 主要特性 {#key-features}

- **步进增量**
  使用 `setStep()` 定义每次旋转值应该更改的量：

  ```java
  spinner.setStep(5.0); // 每次旋转增加或减少5
  ```

- **交互式控制**
  用户可以单击旋转器按钮或使用键盘输入来调整值。

- **支持 `MaskedNumberField` 的所有功能**
  完全支持掩码、格式化、分组/小数字符、最小/最大限制和恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
