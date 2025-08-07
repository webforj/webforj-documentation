---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: 70f471320621b40dc1bb4170e4cbf752
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

`DateTimeField` 组件旨在允许用户输入日期和时间。这包括指定年份、月份和日期，以及以小时和分钟表示时间。它为用户提供了验证输入准确性或利用专门的日期时间选择器界面的选项，以简化选择过程。

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usages {#usages}

`DateTimeField` 最适合用于需要捕获或显示日期 **和** 时间的场景。以下是一些使用 `DateTimeField` 的示例：

1. **事件调度和日历**：让用户高效地安排事件、预订约会和管理日历，提供一个单一组件，允许他们选择日期和时间。
<!-- vale off -->
2. **入住和退房**：促进用户选择入住和退房时间，当期间可以跨越多天时。
<!-- vale on -->
3. **数据记录和时间戳**：在涉及记录事件发生日期和时间或用户提交数据的应用程序中，利用 `DateTimeFields`。

4. **任务管理和截止日期**：在涉及任务管理或设定截止日期的应用中，`DateTimeFields` 在准确调度中非常重要，其中日期和时间都是相关的。

## Field value (`LocalDateTime`) {#field-value-localdatetime}

内部，`DateTimeField` 组件使用 `java.time` 包中的 `LocalDateTime` 对象表示其值。这为输入的日期和时间组件提供了精确控制。

虽然 **客户端** 值是基于用户的浏览器语言环境（例如，匹配本地惯例的日期和时间格式）呈现的，但 **解析** 值遵循严格和可预测的结构：**`yyyy-MM-ddTHH:mm:ss`**。

### 获取和设置值 {#getting-and-setting-the-value}

要检索当前值，请使用 `getValue()` 方法：

```java
LocalDateTime value = dateTimeField.getValue();
```

要程序性地设置值，请使用 `setValue()` 方法：

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### 使用 `setText()` {#using-settext}

如果您更喜欢通过原始字符串设置值，它必须遵循 `yyyy-MM-ddTHH:mm:ss` 的确切格式。

```java
dateTimeField.setText("2024-04-27T14:30:00"); // 有效

dateTimeField.setText("24-04-27T14:30:00"); // 无效
```

:::warning
 使用 `setText()` 方法时，如果组件无法以 `yyyy-MM-ddTHH:mm:ss` 格式解析输入，将抛出 `IllegalArgumentException`。
:::

## 静态工具 {#static-utilities}

DateTimeField 类还提供以下静态工具方法：

- `fromDateTime(String dateTimeAsString)`：将 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串转换为 `LocalDateTime` 对象，然后可以在此类或其他地方使用。

- `toDateTime(LocalDateTime dateTime)`：将 `LocalDateTime` 对象转换为 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串。

- `isValidDateTime(String dateTimeAsString)`：检查给定字符串是否是有效的 `yyyy-MM-ddTHH:mm:ss` 日期和时间。如果是，则返回布尔值 true，否则返回 false。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}

如果输入到组件中的值早于指定的最小时间戳，组件将失败约束验证。当设置了最小值和最大值时，最小值必须是与最大值相同或更早的时间戳。

```java
// 设置允许的最小时间戳：2023年1月1日08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### 最大值 {#the-max-value}

如果输入到组件中的值晚于指定的最大时间戳，组件将失败约束验证。当设置了最小值和最大值时，最大值必须是与最小值相同或更晚的时间戳。

```java
// 设置允许的最大时间戳：2023年12月31日18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## 最佳实践 {#best-practices}

为确保在使用 `DateTimeField` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **本地化日期显示**：本地化日期格式并结合地区偏好，确保日期以用户熟悉的格式呈现。

- **包含时区**：如果您的应用处理不同时区的时间敏感信息，请考虑在日期字段旁边加入时区选择，以确保准确的日期时间表示。

- **可访问性**：使用 `DateTimeField` 时考虑可访问性。确保满足可访问性标准，例如提供适当的标签并与辅助技术兼容。

- **自动填充当前日期**：考虑在日期时间字段中提供选项，自动填充当前日期和时间作为默认值，如果适用于您的应用用例。
