---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: e2073fda6d7853bbacc6431c615e8cff
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` 是一个用于结构化日期输入的文本输入控件。它允许用户以 **数字** 的形式输入日期，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码是一个字符串，它指定了期望的日期格式，引导输入和显示。

此组件支持灵活的解析、验证、本地化和值恢复。它在注册、预订和调度等表单中尤其有用，这些地方需要一致且区域特定的日期格式。

:::tip 寻找时间输入？
`MaskedDateField` 仅专注于 **日期** 值。如果您需要一个类似的组件来输入和格式化 **时间**，请查看 [`MaskedTimeField`](./timefield)。
:::

## 基础 {#basics}

`MaskedDateField` 可以在有或没有参数的情况下实例化。您可以定义初始值、标签、占位符，以及用于值更改的事件监听器。

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## 掩码规则 {#mask-rules}

`MaskedDateField` 支持多种在全球范围内使用的日期格式，这些格式的日、月、年顺序各不相同。常见的模式包括：

- **日/月/年**（在大部分欧洲使用）
- **月/日/年**（在美国使用）
- **年/月/日**（在中国、日本和韩国使用；也是ISO标准：`YYYY-MM-DD`）

在这些格式中，本地变化包括分隔符的选择（例如，`-`、`/` 或 `.`）、年份是两位还是四位数字，以及单数字月份或日期是否使用前导零填充。

为了处理这种多样性，`MaskedDateField` 使用格式指示符，每个指示符以 `%` 开头，后跟一个代表日期特定部分的字母。这些指示符定义了如何解析输入以及如何显示日期。

### 日期格式指示符 {#date-format-indicators}

| 格式   | 描述        |
| ------ | ----------- |
| `%Y`   | 年          |
| `%M`   | 月          |
| `%D`   | 日          |

### 修饰符 {#modifiers}

修饰符提供了更精确地控制日期组件格式化的方法：

| 修饰符 | 描述                     |
| ------- | ------------------------ |
| `z`     | 零填充                   |
| `s`     | 短文本表示               |
| `l`     | 长文本表示               |
| `p`     | 打包数字                 |
| `d`     | 十进制（默认格式）       |

这些可以组合起来构建多种不同的日期掩码。

## 日期格式本地化 {#date-format-localization}

`MaskedDateField` 通过设置适当的区域设置来适应区域日期格式。这确保日期的显示和解析方式与用户的期望相匹配。

| 区域            | 格式       | 示例          |
| --------------- | ---------- | ------------ |
| 美国            | MM/DD/YYYY | `07/04/2023` |
| 欧洲            | DD/MM/YYYY | `04/07/2023` |
| ISO标准        | YYYY-MM-DD | `2023-07-04` |

要应用本地化，请使用 `setLocale()` 方法。它接受一个 [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) 实例，并自动调整格式化和解析：

```java
dateField.setLocale(Locale.FRANCE);
```

## 解析逻辑 {#parsing-logic}

`MaskedDateField` 根据定义的日期掩码解析用户输入。它接受完整和简写的数字输入，无论是否带有分隔符，允许灵活输入，同时确保日期有效。
解析行为取决于掩码定义的格式顺序（例如，`%Mz/%Dz/%Yz` 对于月/日/年）。这种格式决定了数字序列的解释方式。

例如，假设今天是 `2012 年 9 月 15 日`，那么以下各种输入将被解释为：

### 示例解析场景 {#example-parsing-scenarios}

| 条目                               | YMD (ISO)                                                                                                                                                                                           | MDY (美国)                                                                          | DMY (欧洲)                                                                                                                    |
| ---------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>       | 单个数字总是被解释为当前月份内的日期，所以这将是 2012 年 9 月 1 日。                                                                                                                       | 同 YMD                                                                              | 同 YMD                                                                                                                    |
| <div align="center">`12`</div>      | 两个数字总是被解释为当前月份内的日期，所以这将是 2012 年 9 月 12 日。                                                                                                                      | 同 YMD                                                                              | 同 YMD                                                                                                                    |
| <div align="center">`112`</div>     | 三个数字被解释为一个数字月份后跟两个数字日期，所以这将是 2012 年 1 月 12 日。                                                                                                                | 同 YMD                                                                              | 三个数字被解释为一个数字日期后跟两个数字月份，所以这将是 2012 年 12 月 1 日。                                          |
| <div align="center">`1004`</div>    | 四个数字被解释为 MMDD，所以这将是 2012 年 10 月 4 日。                                                                                                                                   | 同 YMD                                                                              | 四个数字被解释为 DDMM，所以这将是 2012 年 4 月 10 日。                                                                  |
| <div align="center">`020304`</div>  | 六个数字被解释为 YYMMDD，所以这将是 2002 年 3 月 4 日。                                                                                                                                  | 六个数字被解释为 MMDDYY，所以这将是 2004 年 2 月 3 日。                                                                     | 六个数字被解释为 DDMMYY，所以这将是 2004 年 3 月 2 日。                                                                 |
| <div align="center">`8 digits`</div> | 八个数字被解释为 YYYYMMDD。例如，`20040612` 是 2004 年 6 月 12 日。                                                                                                                        | 八个数字被解释为 MMDDYYYY。例如，`06122004` 是 2004 年 6 月 12 日。                                                        | 八个数字被解释为 DDMMYYYY。例如，`06122004` 是 2004 年 12 月 6 日。                                                         |
| <div align="center">`12/6`</div>     | 用任何有效分隔符分隔的两个数字被解释为 MM/DD，所以这将是 2012 年 12 月 6 日。<br />注意：除字母和数字之外的所有字符都被视为有效分隔符。                                                     | 同 YMD                                                                              | 用任何分隔符分隔的两个数字被解释为 DD/MM，所以这将是 2012 年 6 月 12 日。                                                |
| <div align="center">`3/4/5`</div>    | 2012 年 4 月 5 日                                                                                                                                                                          | 2005 年 3 月 4 日                                                                      | 2005 年 4 月 3 日                                                                                                          |

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法在 `MaskedDateField` 中限制允许的日期范围：

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

这两个方法都接受 [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 类型的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedDateField` 包含一个恢复功能，可以将字段的值重置为预定义或原始状态。这对于恢复用户输入或重置为默认日期非常有用。

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### 恢复值的方法 {#ways-to-restore-the-value}

- **编程方式**，通过调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器重写）

您可以使用 `setRestoreValue()` 设置要恢复的值，传递一个 [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 实例。

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## 验证模式 {#validation-patterns}

您可以使用 `setPattern()` 方法应用客户端验证规则，使用正则表达式：

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

此模式确保只有符合 `MM/DD/YYYY` 格式（两个数字，斜杠，两个数字，斜杠，四个数字）的值被视为有效。

:::tip 正则表达式格式
该模式必须遵循 JavaScript RegExp 语法，如此处所述 [here](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)。
:::

:::warning 输入处理注意事项
该字段试图根据当前掩码解析和格式化数值日期输入。然而，用户仍然可以手动输入不符合预期格式的值。如果输入在语法上是有效的，但在语义上不正确或无法解析（例如 `99/99/9999`），它可能会通过模式检查，但会失败于逻辑验证。
即便设置了正则表达式模式，您仍然应在应用逻辑中始终验证输入值，以确保日期格式正确且有意义。
::::

## 日期选择器 {#date-picker}

`MaskedDateField` 包含一个内置的日历选择器，让用户可以直观选择日期，而不是输入。这增强了不太技术用户的可用性，或者在需要精确输入时。

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

使用 `setIconVisible()` 显示或隐藏字段旁边的日历图标：

```java
picker.setIconVisible(true); // 显示图标
```

### 自动打开行为 {#auto-open-behavior}

您可以配置选择器在用户与字段交互时自动打开（例如，点击、按回车或箭头键）：

```java
picker.setAutoOpen(true);
```

:::tip 强制通过选择器进行选择
为了确保用户只能通过日历选择器选择日期（而不能手动输入），请结合以下两个设置：

```java
dateField.getPicker().setAutoOpen(true); // 用户交互时打开选择器
dateField.setAllowCustomValue(false);    // 禁用手动文本输入
```

此设置保证所有日期输入都是通过选择器 UI 实现的，这在您想要严格的格式控制并消除手动输入的解析问题时非常有用。
:::

### 手动打开日历 {#manually-open-the-calendar}

要以编程方式打开日历：

```java
picker.open();
```

或者使用别名：

```java
picker.show(); // 与 open() 相同
```

### 在日历中显示周数 {#show-weeks-in-the-calendar}

选择器可以选择性地在日历视图中显示周数：

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` 通过添加旋转控件扩展了 [`MaskedDateField`](#basics)，使用户能够使用箭头键或 UI 按钮增减日期。它提供了更指导性的交互风格，特别在桌面应用程序中非常有用。

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### 主要特点 {#key-features}

- **交互式日期步进：**  
  使用箭头键或旋转按钮增减日期值。

- **可定制的步进单位：**  
  使用 `setSpinField()` 选择要修改的日期部分：

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  选项包括 `DAY`、`WEEK`、`MONTH` 和 `YEAR`。

- **最小/最大边界：**  
  继承支持通过 `setMin()` 和 `setMax()` 设置的最小和最大允许日期。

- **格式化输出：**  
  与 `MaskedDateField` 的掩码和本地化设置完全兼容。

### 示例：配置每周步进 {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

这使得每个旋转步骤将日期提前或推后一个星期。

## 样式 {#styling}

<TableBuilder name="MaskedDateField" />
