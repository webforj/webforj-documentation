---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: b528c4f1b76e02e7bd6fe132df47198c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个用于结构化数字输入的文本输入框。它确保数字根据定义的掩码一致地格式化，这使其在财务表单、定价字段或任何对精度和可读性要求较高的输入中特别有用。

该组件支持数字格式化、小数/分组字符的本地化以及最小值或最大值等可选值约束。

<!-- INTRO_END -->

## 基础 {#basics}

`MaskedNumberField` 可以在有参数或没有参数的情况下实例化。它支持设置初始值、标签、占位符和事件监听器，以响应值的变化。

此演示展示了一个**小费计算器**，它使用 `MaskedNumberField` 进行直观的数字输入。一个字段配置为接受格式化后的账单金额，而另一个字段则捕捉一个整数的小费百分比。两个字段应用数字掩码以确保一致和可预测的格式。

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串来控制数字输入的格式和显示。
掩码中的每个字符定义了特定的格式化行为，允许精确控制数字的显示方式。

:::tip 以编程方式应用掩码
要在字段外使用相同的掩码语法格式化数字，例如在以 [`Table`](/docs/components/table/overview) 渲染数据时，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 掩码字符 {#mask-characters}

| 字符       | 描述 |
|------------|------|
| `0`        | 始终被数字（0-9）替换。 |
| `#`        | 抑制前导零。从小数点左侧用填充字符替换。对于尾随数字，则被空格或零替换。否则，被数字替换。 |
| `,`        | 用作分组分隔符（例如千位）。如果前面没有数字，则用填充字符替换。否则，显示为逗号。 |
| `-`        | 如果数字为负，显示负号（`-`）。如果为正数，则用填充字符替换。 |
| `+`        | 显示 `+` 表示正数，或 `-` 表示负数。 |
| `$`        | 始终产生美元符号。 |
| `(`        | 对于负值插入左括号 `(`。如果为正，则用填充字符替换。 |
| `)`        | 对于负值插入右括号 `)`。如果为正，则用填充字符替换。 |
| `CR`       | 对于负数显示 `CR`。如果数字为正，则显示两个空格。 |
| `DR`       | 对于负数显示 `CR`。对于正数显示 `DR`。 |
| `*`        | 插入星号 `*`。 |
| `.`        | 标记小数点。如果输出中没有数字，则用填充字符替换。在小数点之后，填充字符被视为空格。 |
| `B`        | 始终变为空格。任何其他文字字符按原样显示。 |

上述某些字符可以在掩码中出现多次以进行格式化。这些字符包括 `-`、`+`、`$` 和 `(`。如果掩码中存在任何这些字符，第一次遇到的字符将被移动到最后一个 `#` 或 `,` 被填充字符替换的位置。如果没有这样的职位，双字符将保留在其位置。

:::info 无自动四舍五入
字段中的掩码**不**进行四舍五入。例如，当将值 `12.34567` 放入一个使用 `###0.00` 掩码的字段时，您会得到 `12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持自定义**分组**和**小数**字符，使数字格式化易于适应不同区域或商业惯例。

- **分组分隔符** 用于视觉上分隔千位（例如 `1,000,000`）。
- **小数分隔符** 表示数字的分数部分（例如 `123.45`）。

这在国际应用中很有用，因为不同地区使用不同的字符（例如 `.` 与 `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(",");  // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序的当前区域设置应用分组和小数分隔符。您可以随时使用提供的设置方法进行覆盖。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持控制是否允许负数的选项。

默认情况下，允许像 `-123.45` 这样的负值。要防止这种情况，请使用 `setNegateable(false)` 将输入限制为仅正值。

这在商业场景中很有用，比如数量、总额或百分比的值必须始终是非负的。

```java
field.setNegateable(false);
```

当 `negatable` 设置为 `false` 时，字段将阻止任何尝试输入负号或其他输入负值的操作。

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## 最小和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数值边界。
这些约束帮助确保用户输入保持在有效的预期范围内。

- **最小值**  
  使用 `setMin()` 定义最低可接受的数字：

  ```java
  field.setMin(10.0); // 最小值: 10
  ```

  如果用户输入的数字低于该阈值，它将被视为无效。

- **最大值**  
  使用 `setMax()` 定义最高可接受的数字：

  ```java
  field.setMax(100.0); // 最大值: 100
  ```

  超过此限值的值将被标记为无效。

## 恢复值 {#restoring-the-value}

`MaskedNumberField` 支持一个恢复功能，可以将字段的值重置为预定义状态。
当用户需要撤销更改、恢复意外编辑或返回已知默认值时，这非常有用。

要启用此行为，请使用 `setRestoreValue()` 定义目标值。
需要时，可以使用 `restoreValue()` 以编程方式重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **以编程方式** 使用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非被覆盖）

恢复值必须显式设置。如果未定义，则该功能不会恢复字段。

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 扩展了 [`MaskedNumberField`](#basics)，通过添加旋转控制，让用户可以使用步进按钮或箭头键增加或减少值。
这对于数量、价格调整、评分控制或用户进行增量更改的任何场景都很理想。

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### 关键特性 {#key-features}

- **步进增量**  
  使用 `setStep()` 定义每次旋转时值应更改多少：

  ```java
  spinner.setStep(5.0); // 每次旋转增加或减少 5
  ```

- **交互式控制**  
  用户可以点击旋转按钮或使用键盘输入来调整值。

- **继承自 `MaskedNumberField` 的所有功能**  
  完全支持掩码、格式化、分组/小数字符、最小/最大约束和恢复逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
