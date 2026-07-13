---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: 433c612e16b0476f720deb896cb73f4a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` 是一个文本输入，让用户以数字形式输入日期，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码指定所需的日期格式，引导输入和显示。该组件支持灵活的解析、验证、本地化和值复原，以实现一致且符合地区特定要求的日期处理。

<!-- INTRO_END -->

## 基础 {#basics}

:::tip 寻找时间输入？
`MaskedDateField` 仅专注于 **日期** 值。如果您需要一个类似的组件用于输入和格式化 **时间**，请查看 [`MaskedTimeField`](./timefield)。
:::

`MaskedDateField` 可以在有或没有参数的情况下实例化。您可以定义初始值、标签、占位符，以及值更改的事件监听器。

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## 掩码规则 {#mask-rules}

`MaskedDateField` 支持多种全球使用的日期格式，这些格式按日、月和年顺序变化。常见模式包括：

- **日/月/年**（在大多数欧洲国家使用）
- **月/日/年**（在美国使用）
- **年/月/日**（在中国、日本和韩国使用；也是 ISO 标准：`YYYY-MM-DD`）

在这些格式中，当地变体包括分隔符的选择（例如 `-`、`/` 或 `.`），年份是两位数还是四位数，以及单数字的月份或日期是否用前导零填充。

为了处理这些多样性，`MaskedDateField` 使用格式指示符，每个指示符以 `%` 开头，后跟表示日期特定部分的字母。这些指示符定义了输入的解析方式以及日期的显示方式。

:::tip 以编程方式应用掩码
要以相同的掩码语法格式化或解析字段外的日期，使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 日期格式指示符 {#date-format-indicators}

| 格式   | 描述     |
| ------ | -------- |
| `%Y`   | 年      |
| `%M`   | 月      |
| `%D`   | 日      |

### 修饰符 {#modifiers}

修饰符允许更好地控制日期组件的格式：

| 修饰符 | 描述                     |
| ------ | ------------------------ |
| `z`    | 零填充                   |
| `s`    | 简短文本表示             |
| `l`    | 长文本表示               |
| `p`    | 打包数字                 |
| `d`    | 十进制（默认格式）       |

这些可以组合以构建多种日期掩码。

## 日期格式本地化 {#date-format-localization}

`MaskedDateField` 通过设置适当的区域来适应区域日期格式。这确保日期以符合用户期望的方式显示和解析。

| 区域        | 格式     | 示例          |
| ----------- | -------- | ------------ |
| 美国        | MM/DD/YYYY | `07/04/2023` |
| 欧洲        | DD/MM/YYYY | `04/07/2023` |
| ISO 标准    | YYYY-MM-DD | `2023-07-04` |

要应用本地化，请使用 `setLocale()` 方法。它接受一个 [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html)，并自动调整格式和解析：

```java
dateField.setLocale(Locale.FRANCE);
```

## 解析逻辑 {#parsing-logic}

`MaskedDateField` 根据定义的日期掩码解析用户输入。它接受完整和简写的数字输入，可以带或不带分隔符，允许灵活输入，同时确保有效日期。
解析行为取决于掩码定义的格式顺序（例如 `%Mz/%Dz/%Yz` 对于月/日/年）。此格式决定了数字序列的解释方式。

例如，假设今天是 `2012年9月15日`，以下是不同输入的解释方式：

### 示例解析场景 {#example-parsing-scenarios}

| 输入                                   | YMD (ISO)                                                                                                                                                                                             | MDY (US)                                                                            | DMY (EU)                                                                                                                      |
| ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | 单数字始终被解释为当前月份内的日期，因此将解读为 2012 年 9 月 1 日。                                                                                                                          | 同 YMD                                                                             | 同 YMD                                                                                                                     |
| <div align="center">`12`</div>        | 两位数字始终被解释为当前月份内的日期，因此将解读为 2012 年 9 月 12 日。                                                                                                                         | 同 YMD                                                                             | 同 YMD                                                                                                                     |
| <div align="center">`112`</div>       | 三位数字被解释为 1 位数字的月份和 2 位数字的日期，因此将解读为 2012 年 1 月 12 日。                                                                                                               | 同 YMD                                                                             | 三位数字被解释为 1 位数字的日期和 2 位数字的月份，因此将解读为 2012 年 12 月 1 日。                                                                                  |
| <div align="center">`1004`</div>      | 四位数字被解释为 MMDD，因此将解读为 2012 年 10 月 4 日。                                                                                                                                     | 同 YMD                                                                             | 四位数字被解释为 DDMM，因此将解读为 2012 年 4 月 10 日。                                                                                                                     |
| <div align="center">`020304`</div>    | 六位数字被解释为 YYMMDD，因此将解读为 2002 年 3 月 4 日。                                                                                                                                     | 六位数字被解释为 MMDDYY，因此将解读为 2004 年 2 月 3 日。                                                                                  | 六位数字被解释为 DDMMYY，因此将解读为 2004 年 3 月 2 日。                                                                                                                    |
| <div align="center">`8 digits`</div>  | 八位数字被解释为 YYYYMMDD。例如，`20040612` 是 2004 年 6 月 12 日。                                                                                                                            | 八位数字被解释为 MMDDYYYY。例如，`06122004` 是 2004 年 6 月 12 日。                                                               | 八位数字被解释为 DDMMYYYY。例如，`06122004` 是 2004 年 12 月 6 日。                                                                                                           |
| <div align="center">`12/6`</div>      | 用任何有效分隔符分隔的两个数字被解释为 MM/DD，因此将解读为 2012 年 12 月 6 日。 <br />注意：所有的字符（除了字母和数字）都被视为有效的分隔符。                                                        | 同 YMD                                                                             | 用任何分隔符分隔的两个数字被解释为 DD/MM，因此将解读为 2012 年 6 月 12 日。                                                                                        |
| <div align="center">`3/4/5`</div>     | 2012 年 4 月 5 日                                                                                                                                                                                  | 2005 年 3 月 4 日                                                                    | 2005 年 4 月 3 日                                                                                                           |


## 文本日期解析 <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

默认情况下，`MaskedDateField` 仅接受关于日期的数字输入。但是，您可以启用 **文本日期解析**，允许用户在输入中输入月份和日期名称。此功能对于创建更自然的日期输入非常有用。

要启用文本解析，请使用 `setTextualDateParsing()` 方法：

```java
dateField.setTextualDateParsing(true);
```

### 月份名称替代 {#month-name-substitution}

启用文本解析后，您可以在掩码中使用特殊修饰符，以接受月份名称而不是数字值：

- **`%Ms`** - 接受简短月份名称（如 Jan、Feb、Mar 等）
- **`%Ml`** - 接受长月份名称（如 January、February、March 等）

月份名称可以在掩码中的任何位置出现，且该字段仍然会接受数字输入作为回退。

#### 示例 {#examples}

| 掩码               | 输入             | 结果                               |
| ------------------ | ---------------- | ---------------------------------- |
| `%Ms/%Dz/%Yz`     | `Sep/01/25`      | **有效** - 解析为 2025 年 9 月 1 日 |
| `%Ml/%Dz/%Yz`     | `September/01/25` | **有效** - 解析为 2025 年 9 月 1 日 |
| `%Dz/%Ml/%Yz`     | `01/September/25`  | **有效** - 解析为 2025 年 9 月 1 日 |
| `%Mz/%Dz/%Yz`     | `09/01/25`      | **有效** - 数字回退仍然有效         |

:::info
所有 12 个月在简短（Jan、Feb、Mar、Apr、May、Jun、Jul、Aug、Sep、Oct、Nov、Dec）和长（January、February 等）形式中均受支持。
:::
### 星期名称装饰 {#day-name-decoration}

可以在输入中包含星期几名称以增强可读性，但它们仅仅是 **装饰性的**，在解析过程中会被删除。它们不会影响实际的日期值。

- **`%Ds`** - 接受简短的星期名称（如 Mon、Tue、Wed 等）
- **`%Dl`** - 接受长的星期名称（如 Monday、Tuesday、Wednesday 等）

:::warning 星期名称需要数字日期
使用星期几名称（`%Ds` 或 `%Dl`）时，您的掩码 **还必须包含** `%Dz` 或 `%Dd` 以指定实际的日期数字。如果没有数字日期组件，则输入将无效。
:::

#### 示例 {#examples-1}

| 掩码                  | 输入                          | 结果                                      |
| --------------------- | ----------------------------- | ----------------------------------------- |
| `%Ds %Mz/%Dz/%Yz`    | `Mon 09/01/25`               | **有效** - 星期名称是装饰性的           |
| `%Dl %Mz/%Dz/%Yz`    | `Monday 09/01/25`            | **有效** - 星期名称是装饰性的           |
| `%Mz/%Dz/%Yz %Ds`    | `09/01/25 Tue`               | **有效** - 星期名称在最后                |
| `%Dl/%Mz/%Yz`        | `Monday/09/25`               | **无效** - 缺少 `%Dz`                   |
| `%Mz/%Dl/%Yz`        | `09/Monday/25`               | **无效** - 缺少 `%Dz`                   |

所有 7 个星期也以简短（Mon、Tue、Wed、Thu、Fri、Sat、Sun）和长（Monday、Tuesday 等）形式受支持。

### 额外解析规则 {#additional-parsing-rules}

文本日期解析包括几个有用的功能：

- **不区分大小写：** 输入类似 `MONDAY 09/01/25`、`monday 09/01/25` 或 `Monday 09/01/25` 的方式均相同。
- **地区感知：** 月份和日期名称必须与字段的配置区域匹配。例如，使用法语区域时，使用 `septembre` 而不是 `September`。除非区域设置为英语，否则不会识别英文名称。
  - 法语区域：`septembre/01/25` 识别为 September
  - 德语区域：`Montag 09/01/25` 识别为星期一

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedDateField` 中允许的日期范围：

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

这两个方法接受 [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 类型的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedDateField` 包含一个恢复功能，将字段的值重置为预定义或原始状态。这对于恢复用户输入或重置为默认日期非常有用。

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **以编程方式，** 通过调用 `restoreValue()`
- **通过键盘，** 按下 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器覆盖）

您可以通过 `setRestoreValue()` 设置要恢复的值，传入一个 [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 实例。

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## 验证模式 {#validation-patterns}

您可以使用 `setPattern()` 方法通过正则表达式应用客户端验证规则：

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

此模式确保仅能将匹配 `MM/DD/YYYY` 格式（两位数字，斜杠，两位数字，斜杠，四位数字）的值视为有效。

:::tip 正则表达式格式
模式必须遵循 JavaScript RegExp 语法，如 [此处](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 所述。
:::

:::warning 输入处理注意事项
该字段尝试根据当前掩码解析和格式化数字日期输入。但是，用户仍然可以手动输入不匹配预期格式的值。如果输入在语法上有效，但语义不正确或不可解析（例如 `99/99/9999`），则可能通过模式检查，但未通过逻辑验证。
您应始终在应用程序逻辑中验证输入值，即使已设置正则表达式模式，以确保日期格式正确且有意义。
::::

## 日期选择器 {#date-picker}

`MaskedDateField` 包含一个内置的日历选择器，让用户可以在视觉上选择日期，而不是输入。这提升了对不太技术性的用户的可用性，或在需要精确输入时。

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### 访问选择器 {#accessing-the-picker}

您可以使用 `getPicker()` 访问日期选择器：

```java
DatePicker picker = dateField.getPicker();
```

### 显示/隐藏选择器图标 {#showhide-the-picker-icon}

使用 `setIconVisible()` 显示或隐藏字段旁边的日历图标：

```java
picker.setIconVisible(true); // 显示图标
```

### 自动打开行为 {#auto-open-behavior}

您可以配置选择器在用户与字段交互时自动打开（例如，单击、按 Enter 或箭头键）：

```java
picker.setAutoOpen(true);
```

:::tip 强制通过选择器选择
要确保用户只能通过日历选择器选择日期（而不能手动输入），请结合以下两个设置：

```java
dateField.getPicker().setAutoOpen(true); // 在用户交互时打开选择器
dateField.setAllowCustomValue(false);    // 禁用手动文本输入
```

此设置确保所有日期输入都通过选择器 UI 提供，这在您希望严格控制格式并消除输入中解析问题时非常有用。
:::

### 手动打开日历 {#manually-open-the-calendar}

要以编程方式打开日历：

```java
picker.open();
```

或使用别名：

```java
picker.show(); // 与 open() 相同
```

### 在日历中显示周数 {#show-weeks-in-the-calendar}

选择器可以选择性地在日历视图中显示周数：

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` 通过添加旋钮控件扩展了 [`MaskedDateField`](#basics)，让用户可以通过箭头键或 UI 按钮增加或减少日期值。它提供了更具指导性的交互方式，特别适用于桌面风格的应用程序。

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### 主要特性 {#key-features}

- **互动日期步进：**
  使用箭头键或旋钮按钮增加或减少日期值。

- **可定制的步进单位：**
  使用 `setSpinField()` 选择要修改的日期部分：

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  选项包括 `DAY`、`WEEK`、`MONTH` 和 `YEAR`。

- **最小/最大边界：**
  继承对最小和最大允许日期的支持，使用 `setMin()` 和 `setMax()`。

- **格式化输出：**
  与 `MaskedDateField` 的掩码和本地化设置完全兼容。

### 示例：配置按周步进 {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

这样每次步进都会使日期前进或后退一周。

## 样式 {#styling}

<TableBuilder name="MaskedDateField" />
