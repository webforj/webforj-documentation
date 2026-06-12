---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 1214ec1391242fb6b3ff7f60664a6f79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

`DateTimeField` 组件允许用户在单个字段中输入日期和时间，涵盖年、月、日、小时和分钟。它会验证输入的准确性，并可以显示日期时间选择器以简化选择。

<!-- INTRO_END -->

## 使用 `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` 扩展了共享的 `Field` 类，该类为所有字段组件提供了通用功能。以下示例创建了一个带标签的 `DateTimeField`，用于选择出发日期和时间。

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## 用法 {#usages}

`DateTimeField` 最适合在需要捕获或显示日期 **和** 时间的应用场景中使用。以下是一些使用 `DateTimeField` 的示例：

1. **事件安排和日历**：让用户有效地安排事件、预订约会和管理日历，给他们一个可以选择日期和时间的单一组件。
<!-- vale off -->
2. **入住和退房**：便利用户选择入住和退房时间，当期间可以跨越多天。
<!-- vale on -->
3. **数据记录和时间戳**：在涉及记录事件发生日期和时间或用户提交数据的应用中使用 `DateTimeFields`。

4. **任务管理和截止日期**：在涉及任务管理或设置截止日期的应用中，`DateTimeFields` 很有价值，因准确调度时日期和时间都是相关的。

## 字段值 (`LocalDateTime`) {#field-value-localdatetime}

内部 `DateTimeField` 组件使用 `java.time` 包中的 `LocalDateTime` 对象表示其值。这提供了对输入日期和时间组件的精确控制。

虽然 **客户端** 值是根据用户浏览器的区域设置呈现的（例如，符合当地惯例的日期和时间格式），但 **解析后的** 值遵循严格且可预测的结构：**`yyyy-MM-ddTHH:mm:ss`**。

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

如果您更喜欢通过原始字符串设置值，必须遵循 `yyyy-MM-ddTHH:mm:ss` 的确切格式。

```java
dateTimeField.setText("2024-04-27T14:30:00"); // 有效

dateTimeField.setText("24-04-27T14:30:00"); // 无效
```

:::warning
 使用 `setText()` 方法时，如果组件无法以 `yyyy-MM-ddTHH:mm:ss` 格式解析输入，将抛出 `IllegalArgumentException`。
:::

## 静态实用程序 {#static-utilities}

DateTimeField 类还提供以下静态实用方法：

- `fromDateTime(String dateTimeAsString)`：将 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串转换为 `LocalDateTime` 对象，以便在此类或其他地方使用。

- `toDateTime(LocalDateTime dateTime)`：将 `LocalDateTime` 对象转换为 `yyyy-MM-ddTHH:mm:ss` 格式的日期和时间字符串。

- `isValidDateTime(String dateTimeAsString)`：检查给定字符串是否为有效的 `yyyy-MM-ddTHH:mm:ss` 日期和时间。如果是，则返回布尔值 true；否则返回 false。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}

如果输入到组件的值早于指定的最小时间戳，组件将失败约束验证。当同时设置最小值和最大值时，最小值必须是与最大值相同或更早的时间戳。

```java
// 设置允许的最小时间戳：2023年1月1日08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### 最大值 {#the-max-value}

如果输入到组件的值晚于指定的最大时间戳，组件将失败约束验证。当同时设置最小值和最大值时，最大值必须是与最小值相同或更晚的时间戳。

```java
// 设置允许的最大时间戳：2023年12月31日18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## 最佳实践 {#best-practices}

为确保在使用 `DateTimeField` 组件时提供最佳用户体验，请考虑以下最佳实践：

- **本地化日期显示**：本地化日期格式并结合区域偏好，确保日期以用户熟悉的格式呈现。

- **包括时区**：如果您的应用处理跨不同时区的时间敏感信息，考虑在日期字段旁边加入时区选择，以确保准确的日期时间表示。

- **可访问性**：在考虑可访问性时使用 `DateTimeField`。确保它符合可访问性标准，例如提供适当的标签并兼容辅助技术。

- **自动填充当前日期**：考虑在适当情况下，在日期时间字段中提供一个选项，自动填充当前日期和时间作为默认值。
