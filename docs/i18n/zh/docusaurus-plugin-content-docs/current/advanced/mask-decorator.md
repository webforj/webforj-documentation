---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
_i18n_hash: 30ecd8eeaa79a3e5f963e319373d1378
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` 是一个静态工具类，用于在输入字段外对字符串、数字、日期和时间应用掩码。它使用与 webforJ 的 [masked field components](/docs/components/fields/masked/overview) 相同的掩码语法，使得在展示标签、[`Table`](/docs/components/table/overview) 渲染器或应用程序的任何其他位置一致地格式化和解析值变得简单明了。

当您需要以编程方式格式化值以供展示，而不是进行交互式输入时，请使用 `MaskDecorator`，例如在表格单元渲染器、只读标签、导出报告或任何不适合表单字段的上下文中。对于用户输入时的交互式格式化，请使用掩码字段组件。

## 字符串掩码 {#masking-strings}

使用 `forString()` 对普通字符串值应用字符掩码：

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

掩码控制每个位置接受哪些字符。

### 掩码字符 {#string-mask-characters}

| 字符     | 描述              |
|----------|-------------------|
| `X`      | 任何可打印字符    |
| `a`      | 任何字母字符      |
| `A`      | 任何字母字符；小写字母转换为大写 |
| `0`      | 任何数字 (0–9)    |
| `z`      | 任何数字或字母    |
| `Z`      | 任何数字或字母；小写字母转换为大写 |

掩码中的任何其他字符都被视为字面量并按原样插入输出中。输入中的无效字符将被默默忽略，短输入用空格填充，长输入会被截断以适应掩码。

### 示例 {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (填充)
```

## 数字掩码 {#masking-numbers}

使用 `forNumber()` 使用数字掩码格式化数字值：

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### 掩码字符 {#number-mask-characters}

| 字符     | 描述                        |
|----------|-----------------------------|
| `0`      | 始终由一个数字 (0–9) 替代    |
| `#`      | 抑制前导零；替换为小数点左侧的填充字符；对右侧的数字用空格或零替换；否则替换为一个数字 |
| `,`      | 用作分组分隔符；如果尚未放置数字，则替换为填充字符；否则显示为逗号 |
| `-`      | 如果数字为负，显示 `-`；如果为正，替换为填充字符 |
| `+`      | 如果为正显示 `+`，如果为负显示 `-` |
| `$`      | 始终产生一个美元符号         |
| `(`      | 如果数字为负，插入 `(`；正数替换为填充字符 |
| `)`      | 如果数字为负，插入 `)`；正数替换为填充字符 |
| `CR`     | 对负数插入 `CR`；对正数插入两个空格 |
| `DR`     | 对负数插入 `CR`；对正数插入 `DR` |
| `*`      | 始终插入一个星号             |
| `.`      | 标记小数点；输出中没有数字时替换为填充字符；小数点后，填充字符变为空格 |
| `B`      | 始终变为空格；任何其他字面字符按原样复制 |

字符 `-`、`+`、`$` 和 `(` 可以浮动：第一个出现的位置移动到最后一个用填充字符替代的 `#` 或 `,` 的位置。

:::info 四舍五入行为
`forNumber()` 会根据掩码中的小数精度对值进行四舍五入。例如，`MaskDecorator.forNumber(12.34567, "###0.00")` 产生 `"  12.35"`。
:::

### 示例 {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## 日期掩码 {#masking-dates}

使用 `forDate()` 使用日期掩码格式化 `LocalDate` 值：

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

使用 `parseDate()` 将掩码日期字符串解析回 `LocalDate`：

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

当解析包含周数引用的字符串时，提供一个基于区域的重载：

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### 日期格式指示符 {#date-format-indicators}

| 格式    | 描述        |
|---------|-------------|
| `%Y`    | 年          |
| `%M`    | 月          |
| `%D`    | 日          |

### 修饰符 {#date-modifiers}

可选修饰符紧随格式指示符之后：

| 修饰符 | 描述                |
|--------|---------------------|
| `z`    | 以零填充            |
| `s`    | 短文本表示          |
| `l`    | 长文本表示          |
| `p`    | 打包数字            |
| `d`    | 小数（默认格式）    |

### 示例 {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → "Wednesday, March 05"
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## 时间掩码 {#masking-times}

使用 `forTime()` 使用时间掩码格式化 `LocalTime` 值：

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

使用 `parseTime()` 将掩码时间字符串解析回 `LocalTime`：

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

当解析包含本地 AM/PM 值的字符串时，提供一个基于区域的重载：

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### 时间格式指示符 {#time-format-indicators}

| 格式    | 描述                   |
|---------|------------------------|
| `%H`    | 小时（24小时制）       |
| `%h`    | 小时（12小时制）       |
| `%m`    | 分钟                   |
| `%s`    | 秒                     |
| `%p`    | 上午/下午              |

### 修饰符 {#time-modifiers}

时间掩码使用与日期掩码相同的修饰符。见 [日期修饰符](#date-modifiers)。

### 示例 {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## 日期和时间掩码 {#masking-datetime}

使用 `forDateTime()` 使用组合日期和时间掩码格式化 `LocalDateTime` 值：

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### 格式指示符 {#datetime-format-indicators}

`forDateTime()` 支持所有日期和时间格式指示符的任意组合。请参见 [日期格式指示符](#date-format-indicators) 和 [时间格式指示符](#time-format-indicators) 的完整列表。

### 修饰符 {#datetime-modifiers}

所有在 [日期修饰符](#date-modifiers) 中描述的修饰符适用于组合掩码的日期和时间部分。

### 示例 {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## 处理空结果 {#handling-null-results}

:::warning
所有 `for*()` 和 `parse*()` 方法在输入无效或无法解析时返回 `null`。在将结果用于应用程序逻辑之前，请始终验证结果非空。
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
