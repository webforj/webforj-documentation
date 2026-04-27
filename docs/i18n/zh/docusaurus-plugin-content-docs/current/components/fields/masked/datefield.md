---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 6c75156564c20c2d451ebe7046213c37
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` 是一个文本输入，允许用户以数字的形式输入日期，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码指定了预期的日期格式，指导输入和显示。该组件支持灵活的解析、验证、本地化和值恢复，以实现一致的区域特定日期处理。

<!-- INTRO_END -->

## 基础 {#basics}

:::tip 寻找时间输入？
`MaskedDateField` 仅专注于 **日期** 值。如果你需要一个类似的组件用于输入和格式化 **时间**，请参考 [`MaskedTimeField`](./timefield)。
:::

`MaskedDateField` 可以在有或没有参数的情况下实例化。你可以定义初始值、标签、占位符和用于值更改的事件监听器。

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## 掩码规则 {#mask-rules}

`MaskedDateField` 支持全球使用的多种日期格式，这些格式因日期、月份和年份的顺序而异。常见的模式包括：

- **日/月/年**（大多数欧洲国家使用）
- **月/日/年**（美国使用）
- **年/月/日**（中国、日本和韩国使用；也是ISO标准：`YYYY-MM-DD`）

在这些格式中，本地变体包括分隔符的选择（例如，`-`、`/`或 `.`）、年份是两位数还是四位数，以及单数字月份或日期是否填充前导零。

为了处理这种多样性，`MaskedDateField` 使用格式指示符，每个指示符以 `%` 开头，后跟表示日期特定部分的字母。这些指示符定义了输入如何被解析以及日期如何被显示。

:::tip 以编程方式应用掩码
为了在字段外部使用相同的掩码语法格式化或解析日期，请使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 日期格式指示符 {#date-format-indicators}

| 格式 | 描述 |
| ------ | ----------- |
| `%Y`   | 年        |
| `%M`   | 月       |
| `%D`   | 日         |

### 修饰符 {#modifiers}

修饰符允许更好地控制日期组件的格式化：

| 修饰符 | 描述               |
| -------- | ------------------------- |
| `z`      | 前导零填充                 |
| `s`      | 简短文本表示 |
| `l`      | 长文本表示  |
| `p`      | 打包数值             |
| `d`      | 小数（默认格式）  |

可以组合这些修饰符以构建各种日期掩码。

## 日期格式本地化 {#date-format-localization}

`MaskedDateField` 通过设置适当的区域设置以适应区域日期格式。这确保日期以符合用户期望的方式显示和解析。

| 区域        | 格式     | 示例      |
| ------------- | ---------- | ------------ |
| 美国 | MM/DD/YYYY | `07/04/2023` |
| 欧洲        | DD/MM/YYYY | `04/07/2023` |
| ISO标准  | YYYY-MM-DD | `2023-07-04` |

要应用本地化，请使用 `setLocale()` 方法。它接受 [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html)，并自动调整格式和解析：

```java
dateField.setLocale(Locale.FRANCE);
```

## 解析逻辑 {#parsing-logic}

`MaskedDateField` 根据定义的日期掩码解析用户输入。它接受带或不带分隔符的完整和简化数字输入，允许灵活输入，同时确保日期有效。
解析行为取决于掩码定义的格式顺序（例如，`%Mz/%Dz/%Yz` 用于月/日/年）。这种格式决定了数字序列的解释方式。

例如，假设今天是 `2012年9月15日`，不同输入将如何被解析：

### 示例解析场景 {#example-parsing-scenarios}

| 输入                                | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | 单个数字总是被解释为当前月份内的日期，因此这将是2012年9月1日。                                                                                 | 同YMD                                                                         | 同YMD                                                                                                                  |
| <div align="center">`12`</div>       | 两个数字总是被解释为当前月份内的日期，因此这将是2012年9月12日。                                                                                   | 同YMD                                                                         | 同YMD                                                                                                                  |
| <div align="center">`112`</div>      | 三个数字被解释为1位数字的月份编号后跟2位数字的日期编号，因此这将是2012年1月12日。                                                                        | 同YMD                                                                         | 三个数字被解释为1位数字的日期编号后跟2位数字的月份编号，因此这将是2012年12月1日。 |
| <div align="center">`1004`</div>     | 四个数字被解释为MMDD，因此这将是2012年10月4日。                                                                                                                             | 同YMD                                                                         | 四个数字被解释为DDMM，因此这将是2012年4月10日。                                                         |
| <div align="center">`020304`</div>   | 六个数字被解释为YYMMDD，因此这将是2002年3月4日。                                                                                                                              | 六个数字被解释为MMDDYY，因此这将是2004年2月3日。            | 六个数字被解释为DDMMYY，因此这将是2004年3月2日。                                                         |
| <div align="center">`8 digits`</div> | 八个数字被解释为YYYYMMDD。例如，`20040612` 是2004年6月12日。                                                                                                                | 八个数字被解释为MMDDYYYY。例如，`06122004` 是2004年6月12日。 | 八个数字被解释为DDMMYYYY。例如，`06122004` 是2004年12月6日。                                        |
| <div align="center">`12/6`</div>     | 用任何有效的分隔符分隔的两个数字被解释为MM/DD，因此这将是2012年12月6日。 <br />注意：除了字母和数字外的所有字符都被视为有效的分隔符。 | 同YMD                                                                         | 用任何分隔符分隔的两个数字被解释为DD/MM，因此这将是2012年6月12日。                               |
| <div align="center">`3/4/5`</div>    | 2012年4月5日                                                                                                                                                                                      | 2005年3月4日                                                                       | 2005年4月3日                                                                                                                 |


## 文本日期解析 <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

默认情况下，`MaskedDateField` 仅接受数字输入用于日期。但是，你可以启用 **文本日期解析** 以允许用户在输入中输入月份和日期名称。此功能特别适合创建更自然的日期输入。

要启用文本解析，请使用 `setTextualDateParsing()` 方法：

```java
dateField.setTextualDateParsing(true);
```

### 月份名称替换 {#month-name-substitution}

启用文本解析后，您可以在掩码中使用特殊修饰符接受月份名称而不是数字值：

- **`%Ms`** - 接收短月份名称（Jan，Feb，Mar，等）
- **`%Ml`** - 接收长月份名称（January，February，March，等）

月份名称可以出现在掩码中的任何位置，字段仍将接受数字输入作为备选。

#### 示例

| 掩码 | 输入 | 结果 |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **有效** - 解析为2025年9月1日 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **有效** - 解析为2025年9月1日 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **有效** - 解析为2025年9月1日 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **有效** - 数字回退仍然有效 |

:::info
所有12个月在短（Jan，Feb，Mar，Apr，May，Jun，Jul，Aug，Sep，Oct，Nov，Dec）和长（January，February，等）形式下均得到支持。
:::
### 星期几名称装饰 {#day-name-decoration}

星期几名称可以包含在输入中以增强可读性，但它们仅为 **装饰**，在解析时会被剥离。它们不影响实际的日期值。

- **`%Ds`** - 接收短星期几名称（Mon，Tue，Wed，等）
- **`%Dl`** - 接收长星期几名称（Monday，Tuesday，Wednesday，等）

:::warning 星期名称需要数字日期
使用星期几名称（`%Ds` 或 `&Dl`）时，掩码 **必须也包括** `%Dz` 或 `%Dd` 以指定实际的日期编号。如果没有数字日期组件，则输入将无效。
:::

#### 示例

| 掩码 | 输入 | 结果 |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mon 09/01/25` | **有效** - 星期名称仅为装饰 |
| `%Dl %Mz/%Dz/%Yz` | `Monday 09/01/25` | **有效** - 星期名称仅为装饰 |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Tue` | **有效** - 星期名称在末尾 |
| `%Dl/%Mz/%Yz` | `Monday/09/25` | **无效** - 缺少 `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Monday/25` | **无效** - 缺少 `%Dz` |

所有7个星期天都在短（Mon，Tue，Wed，Thu，Fri，Sat，Sun）和长（Monday，Tuesday，等）形式下得到支持。

### 额外解析规则 {#additional-parsing-rules}

文本日期解析包括几个有用的功能：

- **不区分大小写：**输入比如`MONDAY 09/01/25`、`monday 09/01/25`或`Monday 09/01/25`都一样。
- **区域设置感知：**月份和日期名称必须与字段的配置区域匹配。例如，在法语区域下，使用`septembre`而不是`September`。除非将区域设置设为英语，否则将不会识别英语名称。
  - 法语区域：`septembre/01/25` 被识别为九月
  - 德语区域：`Montag 09/01/25` 被识别为星期一

## 设置最小/最大约束 {#setting-minmax-constraints}

你可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedDateField` 中允许的日期范围：

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

这两个方法接受类型为 [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedDateField` 包括一个恢复功能，可以将字段的值重置为预定义或原始状态。这对于撤回用户输入或重置为默认日期非常有用。

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **以编程方式**，通过调用 `restoreValue()`
- **通过键盘**，按下 <kbd>ESC</kbd>（这是默认的恢复键，除非被事件监听器覆盖）

你可以使用 `setRestoreValue()` 设置恢复的值，传入一个 [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 实例。

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## 验证模式 {#validation-patterns}

您可以使用正则表达式通过 `setPattern()` 方法应用客户端验证规则：

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

此模式确保仅值匹配 `MM/DD/YYYY` 格式（两位数字、斜线、两位数字、斜线、四位数字）被视为有效。

:::tip 正则表达式格式
该模式必须遵循JavaScript RegExp语法，如文档 [here](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions)所述。
:::

:::warning 输入处理的注意事项
该字段尝试根据当前掩码解析和格式化数字日期输入。然而，用户仍然可以手动输入不符合预期格式的值。如果输入在语法上有效，但在语义上不正确或者无法解析（例如`99/99/9999`），它可能会通过模式检查，但逻辑验证失败。
即使设置了正则表达式模式，也应始终在应用逻辑中验证输入值，以确保日期格式正确且有意义。
::::

## 日期选择器 {#date-picker}

`MaskedDateField` 包含一个内置的日历选择器，允许用户以可视化方式选择日期，而不是输入。这增强了适用于不太技术化用户的可用性，或在需要精确输入时。

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### 访问选择器 {#accessing-the-picker}

你可以使用 `getPicker()` 访问日期选择器：

```java
DatePicker picker = dateField.getPicker();
```

### 显示/隐藏选择器图标 {#showhide-the-picker-icon}

使用 `setIconVisible()` 来显示或隐藏字段旁边的日历图标：

```java
picker.setIconVisible(true); // 显示图标
```

### 自动打开行为 {#auto-open-behavior}

您可以配置选择器以在用户与字段交互（例如，单击、按 Enter 或箭头键）时自动打开：

```java
picker.setAutoOpen(true);
```

:::tip 通过选择器强制选择
要确保用户只能使用日历选择器选择日期（而不是手动输入），请组合以下两个设置：

```java
dateField.getPicker().setAutoOpen(true); // 用户交互时打开选择器
dateField.setAllowCustomValue(false);    // 禁用手动文本输入
```

此设置可以确保所有日期输入都通过选择器用户界面进行，这在您希望实施严格格式控制并消除键入输入的解析问题时非常有用。
:::

### 手动打开日历 {#manually-open-the-calendar}

要以编程方式打开日历：

```java
picker.open();
```

或者使用别名：

```java
picker.show(); // 同打开
```

### 在日历中显示周 {#show-weeks-in-the-calendar}

选择器可选择在日历视图中显示周数字：

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` 扩展了 [`MaskedDateField`](#basics)，添加了旋转控制，允许用户使用箭头键或用户界面按钮递增或递减日期。它提供了一种更引导的交互风格，尤其适用于桌面风格的应用程序。

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### 关键特性 {#key-features}

- **互动日期步进：**  
  使用箭头键或旋转按钮递增或递减日期值。

- **可自定义步进单位：**  
  选择要修改的日期部分，使用 `setSpinField()`：

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  选项包括 `DAY`、`WEEK`、`MONTH` 和 `YEAR`。

- **最小/最大边界：**  
  继承对使用 `setMin()` 和 `setMax()` 的最小和最大允许日期的支持。

- **格式化输出：**  
  完全兼容 `MaskedDateField` 的掩码和本地化设置。

### 示例：配置每周步进 {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

这使得每个旋转步骤将日期前进或倒退一周。

## 样式 {#styling}

<TableBuilder name="MaskedDateField" />
