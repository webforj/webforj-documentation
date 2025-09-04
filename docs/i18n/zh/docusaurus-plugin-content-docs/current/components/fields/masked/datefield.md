---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: f76242de3a742ad3a930e1581f688592
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` 是一个用于结构化日期输入的文本输入控件。它允许用户作为**数字**输入日期，并在字段失去焦点时基于定义的掩码自动格式化输入。掩码是一个字符串，指定预期的日期格式，指导输入和显示。

此组件支持灵活的解析、验证、本地化和值恢复。它特别适用于像注册、预订和调度这样的表单，要求一致且地域特定的日期格式。

:::tip 寻找时间输入？
`MaskedDateField`专注于**日期**值。如果您需要一个类似的组件来输入和格式化**时间**，请查看[`MaskedTimeField`](./timefield)。
:::

## 基础 {#basics}

`MaskedDateField` 可以在有或没有参数的情况下实例化。您可以定义初始值、标签、占位符和用于值更改的事件监听器。

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## 掩码规则 {#mask-rules}

`MaskedDateField` 支持全球多种日期格式，这些格式因日、月、年的顺序而异。常见模式包括：

- **日/月/年**（在大多数欧洲地区使用）
- **月/日/年**（在美国使用）
- **年/月/日**（在中国、日本和韩国使用；也是ISO标准：`YYYY-MM-DD`）

在这些格式中，地方变体包括分隔符的选择（例如，`-`、`/`或`.`）、年份是两位数还是四位数以及单个数字月份或日期是否用前导零填充。

为了处理这种多样性，`MaskedDateField` 使用格式指示符，每个指示符以`%`开头，后跟一个表示日期特定部分的字母。这些指示符定义了如何解析输入以及如何显示日期。

### 日期格式指示符 {#date-format-indicators}

| 格式   | 描述        |
| ------ | ----------- |
| `%Y`   | 年          |
| `%M`   | 月          |
| `%D`   | 日          |

### 修饰符 {#modifiers}

修饰符允许更好地控制日期组成部分的格式：

| 修饰符 | 描述                     |
| ------ | ------------------------ |
| `z`    | 零填充                   |
| `s`    | 短文本表示               |
| `l`    | 长文本表示               |
| `p`    | 紧凑数字                 |
| `d`    | 小数（默认格式）         |

这些可以组合起来构建各种类型的日期掩码。

## 日期格式本地化 {#date-format-localization}

`MaskedDateField` 通过设置适当的区域设置来适应区域日期格式。这确保日期以匹配用户期望的方式显示和解析。

| 区域         | 格式       | 示例           |
| ------------ | ---------- | ------------- |
| 美国         | MM/DD/YYYY | `07/04/2023`  |
| 欧洲         | DD/MM/YYYY | `04/07/2023`  |
| ISO标准      | YYYY-MM-DD | `2023-07-04`  |

要应用本地化，请使用`setLocale()`方法。此方法接受一个[`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html)，并自动调整格式和解析：

```java
dateField.setLocale(Locale.FRANCE);
```

## 解析逻辑 {#parsing-logic}

`MaskedDateField` 根据定义的日期掩码解析用户输入。它接受完整和简写的数字输入，带或不带分隔符，允许灵活输入，同时确保有效日期。
解析行为取决于掩码定义的格式顺序（例如，`%Mz/%Dz/%Yz` 为月份/日期/年份）。这个格式决定了数字序列的解释方式。

例如，假设今天是`2012年9月15日`，以下是不同输入的解析示例：

### 示例解析场景 {#example-parsing-scenarios}

| 输入                                 | YMD（ISO）                                                                                                                                                                                          | MDY（美国）                                                                          | DMY（欧洲）                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | 单个数字总是被解析为当前月份的日期，所以这将是2012年9月1日。                                                                                                                                         | 同YMD                                                                              | 同YMD                                                                                                                  |
| <div align="center">`12`</div>       | 两个数字总是被解析为当前月份的日期，所以这将是2012年9月12日。                                                                                                                                       | 同YMD                                                                              | 同YMD                                                                                                                  |
| <div align="center">`112`</div>      | 三个数字被解析为1位数字的月份和2位数字的日期，所以这将是2012年1月12日。                                                                                                                              | 同YMD                                                                              | 三个数字被解析为1位数字的日期和2位数字的月份，所以这将是2012年12月1日。                                                                         |
| <div align="center">`1004`</div>     | 四个数字被解析为MMDD，所以这将是2012年10月4日。                                                                                                                                                         | 同YMD                                                                              | 四个数字被解析为DDMM，所以这将是2012年4月10日。                                                                         |
| <div align="center">`020304`</div>   | 六个数字被解析为YYMMDD，所以这将是2002年3月4日。                                                                                                                                                          | 六个数字被解析为MMDDYY，所以这将是2004年2月3日。                                                     | 六个数字被解析为DDMMYY，所以这将是2004年3月2日。                                                                         |
| <div align="center">`8 digits`</div> | 八个数字被解析为YYYYMMDD。例如，`20040612` 是 2004年6月12日。                                                                                                                                             | 八个数字被解析为MMDDYYYY。例如，`06122004` 是 2004年6月12日。                                 | 八个数字被解析为DDMMYYYY。例如，`06122004` 是 2004年12月6日。                                                       |
| <div align="center">`12/6`</div>     | 用任何有效分隔符分隔的两个数字被解析为MM/DD，所以这将是2012年12月6日。<br />注意：除了字母和数字外，所有字符都被视为有效分隔符。          | 同YMD                                                                              | 用任何分隔符分隔的两个数字被解析为DD/MM，所以这将是2012年6月12日。                               |
| <div align="center">`3/4/5`</div>    | 2012年4月5日                                                                                                                                                                                        | 2005年3月4日                                                                         | 2005年4月3日                                                                                                                 |

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用`setMin()`和`setMax()`方法限制`MaskedDateField`中允许的日期范围：

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

这两个方法接受类型为[`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html)的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedDateField` 包含一个恢复功能，用于将字段的值重置为预定义或原始状态。这对于还原用户输入或重置为默认日期非常有用。

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **以编程方式**，通过调用 `restoreValue()`
- **通过键盘**，按 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器覆盖）

您可以使用 `setRestoreValue()` 设置要恢复的值，传递一个 [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 实例。

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## 验证模式 {#validation-patterns}

您可以使用正则表达式通过 `setPattern()` 方法应用客户端验证规则：

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

此模式确保只有与 `MM/DD/YYYY` 格式（两位数，斜杠，两位数，斜杠，四位数）匹配的值被视为有效。

:::tip 正则表达式格式
模式必须遵循 JavaScript RegExp 语法，如文档中所述 [here](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)。
:::

:::warning 输入处理的注意事项
该字段尝试根据当前掩码解析和格式化数字日期输入。然而，用户仍然可以手动输入不符合预期格式的值。如果输入在语法上有效但在语义上不正确或无法解析（例如 `99/99/9999`），则可能会通过模式检查但未能通过逻辑验证。
即使设置了正则表达式模式，您也应该始终在应用逻辑中验证输入值，以确保日期格式正确且有意义。
::::

## 日期选择器 {#date-picker}

`MaskedDateField` 包含一个内置的日历选择器，允许用户以视觉方式选择日期，而不是输入。这增强了不太技术化用户的可用性，或在需要精确输入时。

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

您可以配置选择器在用户与字段交互时自动打开（例如，单击、按回车或箭头键）：

```java
picker.setAutoOpen(true);
```

:::tip 仅通过选择器强制选择
为确保用户只能通过日历选择器选择日期（而不能手动输入），请结合以下两个设置：

```java
dateField.getPicker().setAutoOpen(true); // 在用户交互时打开选择器
dateField.setAllowCustomValue(false);    // 禁用手动文本输入
```

这种设置保证所有日期输入都来自选择器用户界面，当您希望严格格式控制并消除由输入引起的解析问题时非常有用。
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

`MaskedDateFieldSpinner` 通过添加旋转控制扩展了[`MaskedDateField`](#basics)，允许用户使用箭头键或UI按钮增量或减少日期。它提供了一种更有指导性的交互风格，特别适用于桌面风格的应用程序。

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### 主要特点 {#key-features}

- **交互式日期步进：**  
  使用箭头键或旋转按钮增量或减少日期值。

- **可自定义步进单位：**  
  选择要修改的日期部分，使用 `setSpinField()`：

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  选项包括 `DAY`、`WEEK`、`MONTH` 和 `YEAR`。

- **最小/最大边界：**  
  继承对最小和最大允许日期的支持，使用 `setMin()` 和 `setMax()`。

- **格式化输出：**  
  完全兼容`MaskedDateField`的掩码和本地化设置。

### 示例：配置每周步进 {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

这使得每个旋转步骤将日期前进或后退一周。

## 样式 {#styling}

<TableBuilder name="MaskedDateField" />
