---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: e90e93f7db172a33b2ce205bfd6a6b3c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

`DateTimeField` 组件允许用户在单个字段中输入日期和时间，包括年份、月份、日期、小时和分钟。它对输入进行准确性验证，并可以呈现一个日期时间选择器，以便于选择。

<!-- INTRO_END -->

## 使用 `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` 扩展了共享的 `Field` 类，该类提供所有字段组件的共通功能。以下示例创建了一个带标签的 `DateTimeField` 以选择出发日期和时间。

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## 用法 {#usages}

`DateTimeField` 最适合用于捕获或显示日期 **和** 时间对于您的应用至关重要的场景。以下是一些使用 `DateTimeField` 的示例：

1. **事件调度和日历**：通过提供一个单一的组件，让用户高效地调度事件、预约和管理日历，以便选择日期和时间。
<!-- vale off -->
2. **入住和退房**：方便用户选择入住和退房时间，当期间可以跨越多天时。
<!-- vale on -->
3. **数据记录和时间戳**：将 `DateTimeFields` 用于涉及记录事件发生的日期和时间或用户提交数据的应用程序。

4. **任务管理和截止日期**：`DateTimeFields` 在涉及任务管理或设定截止日期的应用程序中非常有价值，其中日期和时间对于准确调度是相关的。

## 字段值 (`LocalDateTime`) {#field-value-localdatetime}

内部，`DateTimeField` 组件使用 `java.time` 包中的 `LocalDateTime` 对象表示其值。这提供了对输入的日期和时间组件的精确控制。

虽然 **客户端** 值是基于用户的浏览器语言环境（例如，符合当地习惯的日期和时间格式）进行渲染的，但 **解析** 值遵循严格且可预测的结构：**`yyyy-MM-ddTHH:mm:ss`**。

### 获取和设置值 {#getting-and-setting-the-value}

要检索当前值，请使用 `getValue()` 方法：

```java
LocalDateTime value = dateTimeField.getValue();
```

要以编程方式设置值，请使用 `setValue()` 方法：

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### 使用 `setText()` {#using-settext}

如果您更喜欢通过原始字符串设置值，则必须遵循 `yyyy-MM-ddTHH:mm:ss` 的确切格式。

```java
dateTimeField.setText("2024-04-27T14:30:00"); // 有效

dateTimeField.setText("24-04-27T14:30:00"); // 无效
```

:::warning
 当使用 `setText()` 方法时，如果组件无法解析 `yyyy-MM-ddTHH:mm:ss` 格式的输入，将抛出 `IllegalArgumentException`。
:::

## 静态工具 {#static-utilities}

DateTimeField 类还提供以下静态工具方法：

- `fromDateTime(String dateTimeAsString)`：将 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串转换为 `LocalDateTime` 对象，然后可以与此类或其他地方一起使用。

- `toDateTime(LocalDateTime dateTime)`：将 `LocalDateTime` 对象转换为 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串。

- `isValidDateTime(String dateTimeAsString)`：检查给定字符串是否是有效的 `yyyy-MM-ddTHH:mm:ss` 日期和时间。如果是，则返回布尔值 true ，否则返回 false。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}

如果输入的值早于指定的最小时间戳，组件将无法通过约束验证。当同时设置最小值和最大值时，最小值必须是相同或早于最大值的时间戳。

```java
// 设置最小允许时间戳：2023年1月1日08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### 最大值 {#the-max-value}

如果输入的值晚于指定的最大时间戳，组件将无法通过约束验证。当同时设置最小值和最大值时，最大值必须是相同或晚于最小值的时间戳。

```java
// 设置最大允许时间戳：2023年12月31日18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## 最佳实践 {#best-practices}

为确保在使用 `DateTimeField` 组件时提供最佳用户体验，请考虑以下最佳实践：

- **本地化日期显示**：本地化日期格式并结合区域偏好，确保日期以用户熟悉的格式呈现。

- **包含时区**：如果您的应用程序涉及跨不同时区的时间敏感信息，请考虑在日期字段旁添加时区选择，以确保准确的日期时间表示。

- **可访问性**：在使用 `DateTimeField` 时考虑可访问性。确保符合可访问性标准，例如提供适当的标签，并与辅助技术兼容。

- **自动填充当前日期**：考虑在日期时间字段中提供选项，以自动填充当前日期和时间作为默认值，前提是适合您应用的用例。
