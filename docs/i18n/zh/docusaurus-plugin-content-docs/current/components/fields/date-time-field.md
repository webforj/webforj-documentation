---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: dd6fe3e8a737f5b016f92629d9767dbb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

`DateTimeField` 组件旨在允许用户输入日期和时间。这包括指定年份、月份和日期，以及按小时和分钟输入时间。它为用户提供了一个选项，以验证其输入的准确性，或利用专用的日期时间选择器界面来简化选择过程。

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## 用法 {#usages}

`DateTimeField` 在需要捕获或显示日期 **和** 时间的场景中效果最佳。以下是一些使用 `DateTimeField` 的示例：

1. **事件调度和日历**：让用户高效地安排事件、预订约会，并通过提供一个组件让他们选择日期和时间来管理他们的日历。
<!-- vale off -->
2. **入住和退房**：方便用户选择入住和退房时间，尤其是在期间可能跨越多天时。
<!-- vale on -->
3. **数据记录和时间戳**：在涉及记录事件发生时间和用户提交数据时，使用 `DateTimeFields`。

4. **任务管理和截止日期**：在涉及任务管理或设定截止日期的应用程序中，`DateTimeFields` 很有价值，在这些应用中，日期和时间对于准确的调度至关重要。

## 字段值 (`LocalDateTime`) {#field-value-localdatetime}

`DateTimeField` 组件在内部使用 `java.time` 包中的 `LocalDateTime` 对象表示其值。这提供了对输入的日期和时间部分的精确控制。

虽然 **客户端** 值是根据用户的浏览器区域设置进行呈现的（例如，符合本地惯例的日期和时间格式），**解析** 值则遵循严格和可预测的结构： **`yyyy-MM-ddTHH:mm:ss`**。

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

如果你想通过原始字符串设置值，它必须遵循 `yyyy-MM-ddTHH:mm:ss` 的确切格式。

```java
dateTimeField.setText("2024-04-27T14:30:00"); // 有效

dateTimeField.setText("24-04-27T14:30:00"); // 无效
```

:::warning
 使用 `setText()` 方法时，如果组件无法解析以 `yyyy-MM-ddTHH:mm:ss` 格式输入，将抛出 `IllegalArgumentException`。
:::

## 静态工具 {#static-utilities}

DateTimeField 类还提供以下静态实用方法：

- `fromDateTime(String dateTimeAsString)`：将 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串转换为可以在此类中或其他地方使用的 `LocalDateTime` 对象。

- `toDateTime(LocalDateTime dateTime)`：将 `LocalDateTime` 对象转换为 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串。

- `isValidDateTime(String dateTimeAsString)`：检查给定的字符串是否是有效的 `yyyy-MM-ddTHH:mm:ss` 日期和时间。如果是，则返回布尔值 true，否则返回 false。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}

如果输入到组件中的值早于指定的最小时间戳，组件将无法通过约束验证。当同时设置最小值和最大值时，最小值必须是与最大值相同或更早的时间戳。

```java
// 设置允许的最小时间戳：2023年1月1日08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### 最大值 {#the-max-value}

如果输入到组件中的值晚于指定的最大时间戳，组件将无法通过约束验证。当同时设置最小值和最大值时，最大值必须是与最小值相同或更晚的时间戳。

```java
// 设置允许的最大时间戳：2023年12月31日18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## 最佳实践 {#best-practices}

为了确保在使用 `DateTimeField` 组件时提供最佳用户体验，请考虑以下最佳实践：

- **本地化日期显示**：本地化日期格式并结合区域偏好，确保日期以用户熟悉的格式呈现。

- **包含时区**：如果你的应用涉及不同时区的时间敏感信息，请考虑在日期字段旁边添加时区选择，以确保准确的日期时间表示。

- **可访问性**：在使用 `DateTimeField` 时考虑可访问性。确保其符合可访问性标准，例如提供适当的标签，并与辅助技术兼容。

- **自动填充当前日期**：如果适合你应用的用例，考虑提供一个选项，自动填充当前日期和时间作为日期时间字段的默认值。
