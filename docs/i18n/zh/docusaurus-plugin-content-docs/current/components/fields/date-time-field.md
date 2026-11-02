---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 9ccb17716ffd5fa2d32781fc603d5042
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

`DateTimeField` 组件允许用户在一个字段中输入日期和时间，包括年、月、日、小时和分钟。它验证输入的准确性，并可以呈现日期时间选择器以便于选择。

<!-- INTRO_END -->

## 使用 `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` 扩展了共享的 `Field` 类，该类提供了所有字段组件的公共功能。以下示例创建了一个带标签的 `DateTimeField`，用于选择出发日期和时间。

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## 用途 {#usages}

`DateTimeField` 最适合用于需要捕获或显示日期 **和** 时间的场景。以下是一些使用 `DateTimeField` 的示例：

1. **事件调度和日历**：让用户高效地安排事件、预定预约并管理日历，提供一个单一的组件，允许他们选择日期和时间。
<!-- vale off -->
2. **入住和退房**：便利用户选择入住和退房时间，当期间可以跨越多天时。
<!-- vale on -->
3. **数据记录和时间戳**：在涉及记录事件发生的日期和时间或用户提交数据的时间的应用中利用 `DateTimeFields`。

4. **任务管理和截止日期**：在涉及任务管理或设置截止日期的应用中，`DateTimeFields` 非常有价值，其中日期和时间对于准确调度是相关的。

## 字段值 (`LocalDateTime`) {#field-value-localdatetime}

`DateTimeField` 组件在内部使用来自 `java.time` 包的 `LocalDateTime` 对象表示其值。值被截断为分钟，因此秒和小数秒被丢弃。

浏览器根据用户的区域设置显示日期和时间，而字段的文本值使用格式 `yyyy-MM-ddTHH:mm`。`LocalDateTime` 不包括时区，并且不接受带有时区后缀的输入，如 `Z` 或 `+02:00`。

### 获取和设置值 {#getting-and-setting-the-value}

要检索当前值，使用 `getValue()` 方法：

```java
LocalDateTime value = dateTimeField.getValue();
```

要以编程方式设置值，使用 `setValue()` 方法：

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### 使用 `setText()` {#using-settext}

要从原始字符串中设置值，请使用 `setText()` 方法，格式为本地日期时间，例如 `yyyy-MM-ddTHH:mm`。输入还可以包括秒或小数秒，但会被丢弃。

```java
dateTimeField.setText("2024-04-27T14:30");       // 有效
dateTimeField.setText("2024-04-27T14:30:45");    // 有效；秒被丢弃

dateTimeField.setText("24-04-27T14:30");        // 无效；使用四位数年份
dateTimeField.setText("2024-04-27T14:30Z");      // 无效；不接受时区后缀
```

:::warning
使用 `setText()` 时，如果输入无法解析为有效的本地日期时间，则会抛出 `IllegalArgumentException`。接受具有分钟精度的输入和具有秒的输入，但不接受时区后缀。
:::

## 静态工具 {#static-utilities}

DateTimeField 类还提供以下静态工具方法：

- `fromDateTime(String dateTimeAsString)`：将具有或不具有秒的本地日期时间字符串解析为截断到分钟的 `LocalDateTime`。

- `toDateTime(LocalDateTime dateTime)`：将 `LocalDateTime` 转换为 `yyyy-MM-ddTHH:mm` 格式的字符串。秒和小数秒被丢弃。

- `isValidDateTime(String dateTimeAsString)`：检查字符串是否为有效的本地日期时间。接受带有秒或小数秒的输入。对于无效输入或时区后缀，返回 `false`。

## 最小和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}

如果输入到组件中的值早于指定的最小时间戳，组件将会失败约束验证。当设置了最小值和最大值时，最小值必须是与最大值相同或更早的时间戳。

```java
// 设置允许的最小时间戳：2023年1月1日08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### 最大值 {#the-max-value}

如果输入到组件中的值晚于指定的最大时间戳，组件将会失败约束验证。当设置了最小值和最大值时，最大值必须是与最小值相同或更晚的时间戳。

```java
// 设置允许的最大时间戳：2023年12月31日18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## 最佳实践 {#best-practices}

为了确保在使用 `DateTimeField` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **本地化日期显示**：本地化日期格式并结合区域偏好，确保日期以用户熟悉的格式呈现。

- **单独处理时区**：如果你的应用跨越多个时区，提供单独的时区选择器并在你的应用中处理转换。`DateTimeField` 仅存储本地日期和时间。

- **可访问性**：利用 `DateTimeField` 考虑可访问性。确保它符合可访问性标准，例如提供适当的标签并与辅助技术兼容。

- **自动填充当前日期**：考虑在日期时间字段中提供自动填充当前日期和时间作为默认值的选项，如果适合你的应用用例。
