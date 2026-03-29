---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 981d5cd2686c83144433a0135b1222dc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` 是一个文本输入组件，让用户可以以数字形式输入日期，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码指定了预期的日期格式，为输入和显示提供指导。该组件支持灵活的解析、验证、本地化和数值恢复，以实现一致的、特定于区域的日期处理。

<!-- INTRO_END -->

## 基础 {#basics}

:::tip 寻找时间输入？
`MaskedDateField` 仅专注于**日期**值。如果您需要类似的组件来输入和格式化**时间**，请查看 [`MaskedTimeField`](./timefield)。
:::

`MaskedDateField` 可以在有或没有参数的情况下实例化。您可以定义初始值、标签、占位符以及一个值变化的事件监听器。

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## 掩码规则 {#mask-rules}

`MaskedDateField` 支持全球多种日期格式，这些格式因日、月和年的顺序而异。常见的模式包括：

- **日/月/年**（在欧洲大部分地区使用）
- **月/日/年**（在美国使用）
- **年/月/日**（在中国、日本和韩国使用；也是ISO标准：`YYYY-MM-DD`）

在这些格式中，本地变体包括分隔符的选择（例如 `-`, `/`, 或 `.`）、年份是否为两位数或四位数，以及单数字月份或日期是否用前导零填充。

为处理这种多样性，`MaskedDateField` 使用格式指示符，每个指示符以 `%` 开头，后跟代表日期特定部分的字母。这些指示符定义输入如何被解析以及日期如何显示。

### 日期格式指示符 {#date-format-indicators}

| 格式 | 描述 |
| ------ | ----------- |
| `%Y`   | 年        |
| `%M`   | 月       |
| `%D`   | 日         |

### 修饰符 {#modifiers}

修饰符允许更好地控制日期组成部分的格式：

| 修饰符 | 描述               |
| -------- | ------------------------- |
| `z`      | 零填                 |
| `s`      | 短文本表示 |
| `l`      | 长文本表示  |
| `p`      | 打包数字             |
| `d`      | 小数（默认格式）  |

这些可以组合以构建多种日期掩码。

## 日期格式本地化 {#date-format-localization}

`MaskedDateField` 通过设置适当的语言环境来适应区域日期格式。这确保日期以符合用户预期的方式显示和解析。

| 区域        | 格式     | 示例      |
| ------------- | ---------- | ------------ |
| 美国 | MM/DD/YYYY | `07/04/2023` |
| 欧洲        | DD/MM/YYYY | `04/07/2023` |
| ISO标准  | YYYY-MM-DD | `2023-07-04` |

要应用本地化，请使用 `setLocale()` 方法。该方法接受一个 [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html)，并自动调整格式和解析：

```java
dateField.setLocale(Locale.FRANCE);
```

## 解析逻辑 {#parsing-logic}

`MaskedDateField` 根据定义的日期掩码解析用户输入。它接受完整和缩略的数字输入，带有或不带分隔符，这允许灵活输入，同时确保日期有效。
解析行为取决于掩码定义的格式顺序（例如 `%Mz/%Dz/%Yz` 表示月/日/年）。此格式确定数字序列如何被解释。

例如，假设今天是 `2012年9月15日`，以下是如何解释各种输入的：

### 示例解析场景 {#example-parsing-scenarios}

| 输入                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | 单个数字始终被解释为当月的日期，因此这将是2012年9月1日。                                                                                 | 同YMD                                                                         | 同YMD                                                                                                                  |
| <div align="center">`12`</div>       | 两个数字总是被解释为当月的日期，因此这将是2012年9月12日。                                                                                   | 同YMD                                                                         | 同YMD                                                                                                                  |
| <div align="center">`112`</div>      | 三个数字被解释为一个数字的月份后跟两个数字的日期，因此这将是2012年1月12日。                                                                        | 同YMD                                                                         | 三个数字被解释为一个数字的日期后跟两个数字的月份，因此这将是2012年12月1日。 |
| <div align="center">`1004`</div>     | 四个数字被解释为MMDD，因此这将是2012年10月4日。                                                                                                                             | 同YMD                                                                         | 四个数字被解释为DDMM，因此这将是2012年4月10日。                                                         |
| <div align="center">`020304`</div>   | 六个数字被解释为YYMMDD，因此这将是2002年3月4日。                                                                                                                              | 六个数字被解释为MMDDYY，因此这将是2004年2月3日。            | 六个数字被解释为DDMMYY，因此这将是2004年3月2日。                                                         |
| <div align="center">`8 digits`</div> | 八个数字被解释为YYYYMMDD。例如，`20040612` 是2004年6月12日。                                                                                                                | 八个数字被解释为MMDDYYYY。例如，`06122004` 是2004年6月12日。 | 八个数字被解释为DDMMYYYY。例如，`06122004` 是2004年12月6日。                                        |
| <div align="center">`12/6`</div>     | 两个用有效分隔符分隔的数字被解释为MM/DD，因此这将是2012年12月6日。 <br />注意：所有字符（字母和数字除外）都被视为有效分隔符。 | 同YMD                                                                         | 两个用分隔符分隔的数字被解释为DD/MM，因此这将是2012年6月12日。                               |
| <div align="center">`3/4/5`</div>    | 2012年4月5日                                                                                                                                                                                      | 2005年3月4日                                                                       | 2005年4月3日                                                                                                                 |


## 文本日期解析 <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

默认情况下，`MaskedDateField` 仅接受数字输入日期。然而，您可以启用**文本日期解析**来允许用户在输入中输入月份和日期名称。这个功能特别适合于创建更自然的日期输入。

要启用文本解析，请使用 `setTextualDateParsing()` 方法：

```java
dateField.setTextualDateParsing(true);
```

### 月份名称替代 {#month-name-substitution}

启用文本解析后，您可以在掩码中使用特殊修饰符来接受月份名称而不是数字值：

- **`%Ms`** - 接受短月份名称（Jan, Feb, Mar, 等等）
- **`%Ml`** - 接受长月份名称（January, February, March, 等等）

月份名称可以出现在掩码的任何位置，且字段仍然可以接受数字输入作为后备。

#### 示例

| 掩码 | 输入 | 结果 |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **有效** - 解析为2025年9月1日 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **有效** - 解析为2025年9月1日 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **有效** - 解析为2025年9月1日 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **有效** - 数字后备仍然有效 |

:::info
所有12个月都是以短（Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec）和长（January, February, 等等）形式支持的。
:::
### 星期名称装饰 {#day-name-decoration}

可以在输入中包含星期名称以提高可读性，但它们是**装饰性的**，在解析时会被去除。它们不会影响实际的日期值。

- **`%Ds`** - 接受短的星期名称（Mon, Tue, Wed, 等等）
- **`%Dl`** - 接受长的星期名称（Monday, Tuesday, Wednesday, 等等）

:::warning 星期名称需要数字日期
使用星期名称（`%Ds` 或 ` %Dl`）时，您的掩码**必须也包含** `%Dz` 或 `%Dd` 来指定实际的日期数字。没有数字日期组件，输入将被视为无效。
:::

#### 示例

| 掩码 | 输入 | 结果 |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mon 09/01/25` | **有效** - 星期名称是装饰性的 |
| `%Dl %Mz/%Dz/%Yz` | `Monday 09/01/25` | **有效** - 星期名称是装饰性的 |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Tue` | **有效** - 星期名称在最后 |
| `%Dl/%Mz/%Yz` | `Monday/09/25` | **无效** - 缺少 `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Monday/25` | **无效** - 缺少 `%Dz` |

所有7个星期几都以短（Mon, Tue, Wed, Thu, Fri, Sat, Sun）和长（Monday, Tuesday, 等等）形式支持。

### 额外解析规则 {#additional-parsing-rules}

文本日期解析包括几个有用的特性：

- **不区分大小写：** 输入如 `MONDAY 09/01/25`、`monday 09/01/25` 或 `Monday 09/01/25` 都能够正常工作。
- **区域感知：** 月份和日期名称必须与字段配置的语言环境匹配。例如，以法语环境为例，使用 `septembre` 而不是 `September`。英语名称在语言环境设置为英语时才会被识别。
  - 法语环境：`septembre/01/25` 被识别为 September
  - 德语环境：`Montag 09/01/25` 被识别为星期一

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedDateField` 中允许的日期范围：

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

这两个方法接受类型为 [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedDateField` 包含一个恢复功能，可将字段的值重置为预定义或原始状态。这对于恢复用户输入或重置为默认日期很有用。

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **通过编程**，调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认恢复键，除非被事件监听器覆盖）

您可以使用 `setRestoreValue()` 方法设置要恢复的值，传入一个 [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 实例。

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## 验证模式 {#validation-patterns}

您可以使用正则表达式和 `setPattern()` 方法应用客户端验证规则：

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

此模式确保仅匹配 `MM/DD/YYYY` 格式（两位数字，斜杠，两位数字，斜杠，四位数字）的值被视为有效。

:::tip 正则表达式格式
模式必须遵循 JavaScript RegExp 语法，如 [这里](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 建议。
:::

:::warning 输入处理说明
该字段尝试基于当前掩码解析和格式化数字日期输入。然而，用户仍然可以手动输入不符合预期格式的值。如果输入在语法上有效，但在语义上不正确或不可解析（例如 `99/99/9999`），它可能通过模式检查，但逻辑验证失败。
您始终应在应用逻辑中验证输入值，即使设置了正则表达式模式，以确保日期不仅格式正确而且有意义。
::::

## 日期选择器 {#date-picker}

`MaskedDateField` 包含一个内置日历选择器，让用户以直观的方式选择日期，而不是键入。这增强了较不技术用户的可用性，或者在需要精确输入时尤其有用。

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### 访问选择器 {#accessing-the-picker}

您可以使用 `getPicker()` 访问日期选择器：

```java
DatePicker picker = dateField.getPicker();
```

### 显示/隐藏选择器图标 {#showhide-the-picker-icon}

使用 `setIconVisible()` 来显示或隐藏字段旁边的日历图标：

```java
picker.setIconVisible(true); // 显示图标
```

### 自动打开行为 {#auto-open-behavior}

您可以配置选择器在用户与字段交互时（例如，单击、按 Enter 或箭头键时）自动打开：

```java
picker.setAutoOpen(true);
```

:::tip 通过选择器强制选择
为了确保用户只能通过日历选择器选择日期（而不是手动输入），请结合以下两个设置：

```java
dateField.getPicker().setAutoOpen(true); // 在用户交互时打开选择器
dateField.setAllowCustomValue(false);    // 禁用手动文本输入
```

此设置确保所有日期输入均通过选择器UI实现，这在您想要严格格式控制并消除输入解析问题时非常有用。
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

### 在日历中显示星期 {#show-weeks-in-the-calendar}

选择器可以选择性地在日历视图中显示星期数：

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` 扩展了 [`MaskedDateField`](#basics)，通过添加旋转控制，允许用户使用箭头键或UI按钮增量或减量日期。它提供了一种更引导的交互风格，尤其在桌面风格应用中非常有用。

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### 关键特性 {#key-features}

- **交互式日期步进：**  
  使用箭头键或旋转按钮增加或减少日期值。

- **可调整步进单位：**  
  选择要修改的日期部分，使用 `setSpinField()`：

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  选项包括 `DAY`，`WEEK`，`MONTH` 和 `YEAR`。

- **最小/最大边界：**  
  继承对最小和最大允许日期的支持，使用 `setMin()` 和 `setMax()`。

- **格式化输出：**  
  完全兼容来自 `MaskedDateField` 的掩码和本地化设置。

### 示例：配置每周步进 {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

这使每次旋转步骤增加或减少一周的日期。

## 样式 {#styling}

<TableBuilder name="MaskedDateField" />
