---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: 6421e3007af8e795adefa317a13363f0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` 是一个用户界面组件，允许用户输入或选择小时、分钟和可选的秒。它在各种应用程序中提供了一种直观且高效的方式来处理与时间相关的信息。

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

`TimeField` 非常适合在您的应用中选择和显示时间。以下是使用 `TimeField` 的一些示例：

1. **事件调度**：时间字段在涉及设置事件、预约或会议的应用中至关重要。

2. **时间跟踪和记录**：追踪时间的应用程序，如时间表，需要时间字段以准确输入。

3. **提醒和闹钟**：使用时间字段简化用户在您的应用中设置提醒或闹钟的输入过程。

## Min and max value {#min-and-max-value}

通过 `setMin() `和 `setMax()` 方法，您可以指定可接受时间的范围。

- **对于 `setMin()`**：如果输入到组件中的值早于指定的最小时间，组件将无法通过约束验证。当同时设置最小值和最大值时，最小值必须是与最大值相同或更早的时间。

- **对于 `setMax()`**：如果输入到组件中的值晚于指定的最大时间，组件将无法通过约束验证。当同时设置最小值和最大值时，最大值必须是与最小值相同或更晚的时间。

## Value handling and localization {#value-handling-and-localization}

`TimeField` 组件在内部使用 `java.time` 包中的 `LocalTime` 对象来表示其值。这允许开发人员与精确的时间值进行交互，而不管它们如何可视化呈现。

虽然 **客户端组件使用用户的浏览器区域设置显示时间**，但解析和存储的格式始终标准化为 `HH:mm:ss`。

如果设置原始字符串值，请谨慎使用 `setText()` 方法：

```java
timeField.setText("09:15:00"); // 有效
```

:::warning
 使用 `setText()` 方法时，如果组件无法将输入解析为 `HH:mm:ss` 格式，将抛出 `IllegalArgumentException`。
:::


:::info Picker UI 
时间选择器输入用户界面的外观不仅取决于选定的区域设置，还取决于使用的浏览器和操作系统。这确保了与用户已经熟悉的界面之间的自动一致性。
:::

## Static utilities {#static-utilities}

`TimeField` 类还提供以下静态工具方法：

- `fromTime(String timeAsString)`：将 `HH:mm:ss` 格式的时间字符串转换为 `LocalTime` 对象，以便可以在此类中或其他地方使用。

- `toTime(LocalTime time)`：将 `LocalTime` 转换为 `HH:mm:ss` 格式的时间字符串。

- `isValidTime(String timeAsString)`：检查给定字符串是否为有效的 `HH:mm:ss` 时间。如果是，则返回布尔值 true，否则返回 false。

## Best practices {#best-practices}

- **提供清晰的时间格式示例**：在 `TimeField` 附近清楚地向用户展示预期的时间格式。使用示例或占位符帮助他们正确输入时间。如果可能，根据用户的位置显示时间格式。

- **可访问性**：在使用 `TimeField`组件时要考虑可访问性，确保它满足可访问性标准，例如提供适当的标签、足够的色彩对比度以及与辅助技术的兼容性。

- **重置选项**：提供一种方式，让用户轻松地将 `TimeField` 清空或恢复到默认状态。
