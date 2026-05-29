---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: a2b0a5275077beea1c053993d47aa861
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` 是一个用于结构化数字输入的文本输入框。它确保数字根据定义的掩码一致格式化，尤其适用于财务表单、定价字段或任何需要精度和可读性的输入。

该组件支持数字格式化、小数/分组字符的本地化以及可选的值约束，如最小值或最大值。

<!-- INTRO_END -->

## 基础 {#basics}

`MaskedNumberField` 可以在有参数或无参数的情况下实例化。它支持设置初始值、标签、占位符和事件监听器以响应值的变化。

此演示展示了一个 **小费计算器**，使用 `MaskedNumberField` 进行直观的数字输入。一个字段被配置为接受格式化的账单金额，另一个字段捕获整数的小费百分比。两个字段应用数字掩码，以确保一致和可预测的格式化。

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## 掩码规则 {#mask-rules}

`MaskedNumberField` 使用掩码字符串来控制数字输入的格式和显示。
掩码中的每个字符定义了一种特定的格式行为，允许精确控制数字的显示方式。

:::tip 以编程方式应用掩码
要使用相同的掩码语法格式化数字，例如在渲染 [`Table`](/docs/components/table/overview) 中的数据时，可以使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 实用类。
:::

### 掩码字符 {#mask-characters}

| 字符 | 描述 |
|------|------|
| `0`  | 总是被数字（0–9）替换。 |
| `#`  | 抑制前导零。用小数点左侧的填充字符替换。如果后面的数字被替换为空格或零。否则，用数字替换。 |
| `,`  | 用作分组分隔符（例如千位）。如果没有数字在它之前，则用填充字符替换。否则，显示为逗号。 |
| `-`  | 如果数字为负，则显示减号（`-`）。如果是正数，则用填充字符替换。 |
| `+`  | 对于正数显示 `+`，对于负数显示 `-`。 |
| `$`  | 总是显示美元符号。 |
| `(`  | 为负值插入左括号 `(`。如果是正数，则用填充字符替换。 |
| `)`  | 为负值插入右括号 `)`。如果是正数，则用填充字符替换。 |
| `CR` | 对于负数显示 `CR`。如果数字为正，则显示两个空格。 |
| `DR` | 对于负数显示 `CR`。对于正数显示 `DR`。 |
| `*`  | 插入星号 `*`。 |
| `.`  | 标记小数点。如果输出中没有数字，则用填充字符替换。小数点后的填充字符视为空格。 |
| `B`  | 总是变成空格。任何其他字面字符都按原样显示。 |

上述某些字符可以在掩码中出现多次以进行格式化。这些字符包括 `-`、`+`、`$` 和 `(`。如果掩码中存在这些字符，将移动第一个遇到的字符到最后一个用填充字符替换的 `#` 或 `,` 位置。如果不存在这样的位点，双字符将保留在其位置。

:::info 不自动四舍五入
字段中的掩码**不**会进行四舍五入。例如，当将值 `12.34567` 放入掩码为 `###0.00` 的字段时，您将得到 `12.34`。
:::

## 分组和小数分隔符 {#group-and-decimal-separators}

`MaskedNumberField` 支持**分组**和**小数**字符的自定义，使得适应不同地区或商业规范的数字格式化变得容易。

- **分组分隔符** 用于在视觉上分隔千位（例如 `1,000,000`）。
- **小数分隔符** 表示数字的分数部分（例如 `123.45`）。

这在国际应用中非常有用，因为不同地区使用不同的字符（例如 `.` 与 `,`）。

```java
field.setGroupCharacter(".");   // 例如 1.000.000
field.setDecimalCharacter(","); // 例如 123,45
```

:::tip 默认行为
默认情况下，`MaskedNumberField` 根据应用程序的当前语言环境应用分组和小数分隔符。您可以随时使用提供的设置函数来覆盖它们。
:::

## 可否为负数 {#negateable}

`MaskedNumberField` 支持控制是否允许负数的选项。

默认情况下，像 `-123.45` 的负值是被允许的。要防止这一点，可以使用 `setNegateable(false)` 将输入限制为正值。

这在需要始终为非负值的商业场景中非常有用，例如数量、总额或百分比。

```java
field.setNegateable(false);
```

当 `negatable` 设置为 `false` 时，字段会阻止用户输入负号或以其他方式输入负数。

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## 最小值和最大值 {#min-and-max-values}

`MaskedNumberField` 支持使用 `setMin()` 和 `setMax()` 设置数值边界。 
这些约束有助于确保用户输入保持在有效且预期的范围内。

- **最小值**  
  使用 `setMin()` 定义最低可接受的数字：

  ```java
  field.setMin(10.0); // 最小值: 10
  ```

  如果用户输入的数字低于此阈值，则被视为无效。

- **最大值**  
  使用 `setMax()` 定义最高可接受的数字：

  ```java
  field.setMax(100.0); // 最大值: 100
  ```

  超过此限制的值将被标记为无效。

## 还原值 {#restoring-the-value}

`MaskedNumberField` 支持还原功能，将字段的值重置为预定义状态。 
这在用户需要撤销更改、恢复意外编辑或返回到已知默认值时非常有用。

要启用此行为，使用 `setRestoreValue()` 定义目标值。 
在需要时，可以使用 `restoreValue()` 以编程方式重置字段。

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### 还原值的方法 {#ways-to-restore-the-value}

- **以编程方式** 使用 `restoreValue()`
- **通过键盘** 按下 <kbd>ESC</kbd>（这是默认的还原键，除非被覆盖）

还原值必须显式设置。如果未定义，则该功能不会还原字段。

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` 扩展了 [`MaskedNumberField`](#basics)，通过添加旋转器控件，让用户可以使用步伐按钮或方向键增加或减少值。 
这对于数量、定价调整、评分控制或用户进行增量更改的任何情况都是理想的。

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### 主要特征 {#key-features}

- **步进增量**  
  使用 `setStep()` 定义每次旋转值应该改变多少：

  ```java
  spinner.setStep(5.0); // 每次旋转增加或减少 5
  ```

- **交互控件**  
  用户可以点击旋转按钮或使用键盘输入调整值。

- **支持 `MaskedNumberField` 的所有功能**  
  完全支持掩码、格式化、分组/小数字符、最小/最大约束和还原逻辑。

## 样式 {#styling}

<TableBuilder name="MaskedNumberField" />
