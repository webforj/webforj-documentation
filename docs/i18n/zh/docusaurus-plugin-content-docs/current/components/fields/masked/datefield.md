---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 5bd41c7d02fb7ae0c934db0a4e2ffb60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` 是一个文本输入，允许用户以数字形式输入日期，并在字段失去焦点时根据定义的掩码自动格式化输入。掩码指定了预期的日期格式，指导输入和显示。该组件支持灵活的解析、验证、本地化和值恢复，以确保一致的区域特定日期处理。

<!-- INTRO_END -->

## 基础 {#basics}

:::tip 寻找时间输入？
`MaskedDateField` 专注于 **日期** 值。如果您需要一个类似的组件来输入和格式化 **时间**，可以查看 [`MaskedTimeField`](./timefield)。
:::

`MaskedDateField` 可以在有或没有参数的情况下实例化。您可以定义初始值、标签、占位符和用于值变化的事件监听器。

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## 掩码规则 {#mask-rules}

`MaskedDateField` 支持多种在全球使用的日期格式，它们的日、月和年顺序各不相同。常见的模式包括：

- **日/月/年**（在大部分欧洲使用）
- **月/日/年**（在美国使用）
- **年/月/日**（在中国、日本和韩国使用；也是 ISO 标准：`YYYY-MM-DD`）

在这些格式中，本地变体包括分隔符的选择（例如，`-`、`/`或 `.`）、年份是两位数还是四位数，以及单月或日是否用前导零填充。

为了处理这种多样性，`MaskedDateField` 使用格式指示符，每个指示符以 `%` 开头，后面跟着一个表示日期特定部分的字母。这些指示符定义了如何解析输入以及如何显示日期。

:::tip 程序化应用掩码
要使用相同的掩码语法格式化或解析日期，可以使用 [`MaskDecorator`](/docs/advanced/mask-decorator) 工具类。
:::

### 日期格式指示符 {#date-format-indicators}

| 格式  | 描述     |
|-------|----------|
| `%Y`  | 年       |
| `%M`  | 月       |
| `%D`  | 日       |

### 修饰符 {#modifiers}

修饰符允许更好地控制日期组件的格式：

| 修饰符 | 描述                     |
|--------|-------------------------|
| `z`    | 零填充                   |
| `s`    | 简短文本表示             |
| `l`    | 长文本表示               |
| `p`    | 打包数字                 |
| `d`    | 十进制（默认格式）       |

这些可以组合在一起构建多种日期掩码。

## 日期格式本地化 {#date-format-localization}

`MaskedDateField` 通过设置适当的区域设置来适应区域日期格式。这确保日期以与用户期望相匹配的方式显示和解析。

| 区域          | 格式       | 示例         |
|--------------|------------|-------------|
| 美国         | MM/DD/YYYY | `07/04/2023` |
| 欧洲         | DD/MM/YYYY | `04/07/2023` |
| ISO标准      | YYYY-MM-DD | `2023-07-04` |

要应用本地化，请使用 `setLocale()` 方法。它接受一个 [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html)，并自动调整格式和解析：

```java
dateField.setLocale(Locale.FRANCE);
```

## 解析逻辑 {#parsing-logic}

`MaskedDateField` 根据定义的日期掩码解析用户输入。它接受完整和简化的数字输入，带或不带分隔符，允许灵活输入，同时确保有效日期。
解析行为依赖于掩码定义的格式顺序（例如，`%Mz/%Dz/%Yz` 表示月/日/年）。该格式决定了数字序列的解释方式。

例如，假设今天是 `2012年9月15日`，以下是各种输入的解释方式：

### 示例解析场景 {#example-parsing-scenarios}

| 输入                                   | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| <div align="center">`1`</div>         | 单个数字始终被解释为当前月份的日期，因此这将是2012年9月1日。                                                                                                                                      | 与 YMD 相同                                                                         | 与 YMD 相同                                                                                                               |
| <div align="center">`12`</div>        | 两个数字始终被解释为当前月份的日期，因此这将是2012年9月12日。                                                                                                                                       | 与 YMD 相同                                                                         | 与 YMD 相同                                                                                                               |
| <div align="center">`112`</div>       | 三个数字被解释为一个数字月份和一个两位数日期，因此这将是2012年1月12日。                                                                                                                             | 与 YMD 相同                                                                         | 三个数字被解释为一个两位数日期和一个数字月份，因此这将是2012年12月1日。                                                        |
| <div align="center">`1004`</div>      | 四个数字被解释为 MMDD，因此这将是2012年10月4日。                                                                                                                                                    | 与 YMD 相同                                                                         | 四个数字被解释为 DDMM，因此这将是2012年4月10日。                                                                          |
| <div align="center">`020304`</div>    | 六个数字被解释为 YYMMDD，因此这将是2002年3月4日。                                                                                                                                                   | 六个数字被解释为 MMDDYY，因此这将是2004年2月3日。                                   | 六个数字被解释为 DDMMYY，因此这将是2004年3月2日。                                                                         |
| <div align="center">`8 digits`</div>  | 八个数字被解释为 YYYYMMDD。例如，`20040612` 是2004年6月12日。                                                                                                                                     | 八个数字被解释为 MMDDYYYY。例如，`06122004` 是2004年6月12日。                        | 八个数字被解释为 DDMMYYYY。例如，`06122004` 是2004年12月6日。                                                             |
| <div align="center">`12/6`</div>      | 由任何有效分隔符分隔的两个数字被解释为 MM/DD，因此这将是2012年12月6日。 <br />注意：所有除字母和数字以外的字符都被视为有效分隔符。                                                               | 与 YMD 相同                                                                         | 由任何分隔符分隔的两个数字被解释为 DD/MM，因此这将是2012年6月12日。                                                        |
| <div align="center">`3/4/5`</div>     | 2012年4月5日                                                                                                                                                                                      | 2005年3月4日                                                                        | 2005年4月3日                                                                                                               |


## 文本日期解析 <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

默认情况下，`MaskedDateField` 仅接受数字输入用于日期。然而，您可以启用 **文本日期解析**，以允许用户在输入中输入月份和日期名称。此功能对于创建更自然的日期输入尤其有用。

要启用文本解析，请使用 `setTextualDateParsing()` 方法：

```java
dateField.setTextualDateParsing(true);
```

### 月份名称替代 {#month-name-substitution}

当启用文本解析时，您可以在掩码中使用特殊修饰符以接受月份名称而不是数字值：

- **`%Ms`** - 接受短月份名称（如一月、二月、三月等）
- **`%Ml`** - 接受长月份名称（如一月、二月、三月等）

月份名称可以在掩码中的任何位置出现，字段仍将接受数字输入作为后备。

#### 示例

| 掩码              | 输入              | 结果                           |
|-------------------|-------------------|--------------------------------|
| `%Ms/%Dz/%Yz`     | `Sep/01/25`       | **有效** - 被解析为2025年9月1日 |
| `%Ml/%Dz/%Yz`     | `September/01/25` | **有效** - 被解析为2025年9月1日 |
| `%Dz/%Ml/%Yz`     | `01/September/25` | **有效** - 被解析为2025年9月1日 |
| `%Mz/%Dz/%Yz`     | `09/01/25`        | **有效** - 数字回退仍然有效   |

:::info
所有12个月都支持短形式（一月、二月、三月、四月、五月、六月、七月、八月、九月、十月、十一月、十二月）和长形式（如一月、二月等）。
:::
### 星期名称装饰 {#day-name-decoration}

可以在输入中包含星期名称以提高可读性，但它们仅为 **装饰性**，在解析过程中会被去掉。它们不会影响实际日期值。

- **`%Ds`** - 接受短星期名称（如周一、周二、周三等）
- **`%Dl`** - 接受长星期名称（如星期一、星期二、星期三等）

:::warning 星期名称需要数字日期
使用星期名称（`%Ds` 或 `%Dl`）时，您的掩码 **必须还包括** `%Dz` 或 `%Dd` 来指定实际的日期数字。没有数字日期组件，输入将无效。
:::

#### 示例

| 掩码                      | 输入                   | 结果                           |
|---------------------------|-----------------------|--------------------------------|
| `%Ds %Mz/%Dz/%Yz`        | `Mon 09/01/25`        | **有效** - 星期名称是装饰性的   |
| `%Dl %Mz/%Dz/%Yz`        | `Monday 09/01/25`     | **有效** - 星期名称是装饰性的   |
| `%Mz/%Dz/%Yz %Ds`        | `09/01/25 Tue`        | **有效** - 星期名称在最后       |
| `%Dl/%Mz/%Yz`            | `Monday/09/25`        | **无效** - 缺少 `%Dz`         |
| `%Mz/%Dl/%Yz`            | `09/Monday/25`        | **无效** - 缺少 `%Dz`         |

所有7个星期都支持短形式（周一、周二、周三、周四、周五、周六、周日）和长形式（星期一、星期二等）。

### 其他解析规则 {#additional-parsing-rules}

文本日期解析包含几个有用的功能：

- **不区分大小写：** 输如 `MONDAY 09/01/25`、`monday 09/01/25` 或 `Monday 09/01/25` 均会以相同方式处理。
- **区域感知：** 月份和日期名称必须与字段的配置区域相匹配。例如，使用法语区域时，使用 `septembre` 而不是 `September`。英语名称不会被识别，除非将区域设置为英语。
  - 法语区域： `septembre/01/25` 被识别为九月
  - 德语区域： `Montag 09/01/25` 被识别为星期一

## 设置最小/最大约束 {#setting-minmax-constraints}

您可以使用 `setMin()` 和 `setMax()` 方法限制 `MaskedDateField` 中允许的日期范围：

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

这两个方法接受 [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 类型的值。超出定义范围的输入将被视为无效。

## 恢复值 {#restoring-the-value}

`MaskedDateField` 包含恢复功能，将字段的值重置为预定义或原始状态。这对于撤回用户输入或重置为默认日期非常有用。

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### 恢复值的方式 {#ways-to-restore-the-value}

- **以编程方式，**通过调用 `restoreValue()`
- **通过键盘，**按下 <kbd>ESC</kbd>（这是真正的恢复键，除非被事件监听器覆盖）

您可以使用 `setRestoreValue()` 将要恢复的值设置为一个 [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) 实例。

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## 验证模式 {#validation-patterns}

您可以使用 `setPattern()` 方法应用客户端验证规则，利用正则表达式：

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

该模式确保只有与 `MM/DD/YYYY` 格式（两位数字、斜杠、两位数字、斜杠、四位数字）匹配的值被视为有效。

:::tip 正则表达式格式
模式必须遵循 JavaScript RegExp 语法，如 [此处](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) 所述。
:::

:::warning 输入处理注意事项
该字段尝试基于当前掩码解析和格式化数字日期输入。然而，用户仍然可以手动输入与预期格式不匹配的值。如果输入在语法上有效但语义上不正确或无法解析（例如 `99/99/9999`），则可能通过模式检查，但逻辑验证会失败。
即使设置了正则表达式模式，您也应始终在应用逻辑中验证输入值，以确保日期格式正确且有意义。
::::

## 日期选择器 {#date-picker}

`MaskedDateField` 包含一个内置的日历选择器，允许用户以可视化的方式选择日期，而不是输入。这样可以增强使用体验，尤其对不太懂技术的用户或在需要精确输入时。

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

使用 `setIconVisible()` 来显示或隐藏字段旁边的日历图标：

```java
picker.setIconVisible(true); // 显示图标
```

### 自动打开行为 {#auto-open-behavior}

您可以配置选择器在用户与字段交互时自动打开（例如，点击、按下 Enter 或箭头键）：

```java
picker.setAutoOpen(true);
```

:::tip 强制通过选择器选择
为了确保用户只能通过日历选择器选择日期（而不能手动输入），请结合以下两个设置：

```java
dateField.getPicker().setAutoOpen(true); // 用户交互时自动打开选择器
dateField.setAllowCustomValue(false);    // 禁用手动文本输入
```

这种设置确保所有日期输入都通过选择器 UI 输入，这在您想要严格的格式控制并消除手动输入带来的解析问题时非常有用。
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

`MaskedDateFieldSpinner` 通过添加让用户使用箭头键或 UI 按钮增量或减量日期的选择器控件，扩展了 [`MaskedDateField`](#basics)。它提供了一种更引导的交互风格，特别适用于桌面风格的应用。

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### 主要特性 {#key-features}

- **交互式日期步进：**  
  使用箭头键或旋转按钮增量或减量日期值。

- **可定制步进单位：**  
  使用 `setSpinField()` 选择要修改的日期部分：

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  选项包括 `DAY`、`WEEK`、`MONTH` 和 `YEAR`。

- **最小/最大边界：**  
  继承支持使用 `setMin()` 和 `setMax()` 限制最小和最大允许日期。

- **格式化输出：**  
  完全与 `MaskedDateField` 的掩码和本地化设置兼容。

### 示例：配置每周步进 {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

这使每次旋转步骤前进或后退一周的日期。

## 样式 {#styling}

<TableBuilder name="MaskedDateField" />
