---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: aa5cbd6fb54c91be419380eeaf26e65b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` 是一个用户界面组件，允许用户输入或选择小时、分钟和可选的秒。它提供了一种直观高效的方式来处理各种应用中的时间相关信息。

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

`TimeField` 非常适合在你的应用中选择和显示时间。以下是一些使用 `TimeField` 的示例：

1. **事件调度**：在涉及设置事件、约会或会议时间的应用中，时间字段至关重要。

2. **时间跟踪和记录**：跟踪时间的应用（如时间表）需要时间字段以确保准确的记录。

3. **提醒和闹钟**：使用时间字段简化了用户在你的应用中设置提醒或闹钟的输入过程。

## Min and max value {#min-and-max-value}

使用 `setMin()` 和 `setMax()` 方法，您可以指定可接受时间的范围。

- **对于 `setMin()`**：如果输入的值早于指定的最小时间，组件将无法通过约束验证。当同时设置最小值和最大值时，最小值必须等于或早于最大值。

- **对于 `setMax()`**：如果输入的值晚于指定的最大时间，组件将无法通过约束验证。当同时设置最小值和最大值时，最大值必须等于或晚于最小值。

## Value handling and localization {#value-handling-and-localization}

内部，`TimeField` 组件使用 `java.time` 包中的 `LocalTime` 对象表示其值。这样，开发者可以与精确的时间值进行交互，而不管它们的视觉呈现方式。

虽然**客户端组件使用用户的浏览器区域设置显示时间**，但解析和存储的格式始终标准化为 `HH:mm:ss`。

如果设置原始字符串值，请谨慎使用 `setText()` 方法：

```java
timeField.setText("09:15:00"); // 有效
```

:::warning
 使用 `setText()` 方法时，如果组件无法解析输入为 `HH:mm:ss` 格式，将抛出 `IllegalArgumentException`。
:::

:::info 选择器用户界面
时间选择器输入 UI 的外观不仅依赖于所选的区域设置，还取决于所使用的浏览器和操作系统。这确保了与用户已熟悉的界面的自动一致性。
:::

## Static utilities {#static-utilities}

`TimeField` 类还提供以下静态工具方法：

- `fromTime(String timeAsString)`：将 `HH:mm:ss` 格式的时间字符串转换为 `LocalTime` 对象，然后可以在此类或其他地方使用。

- `toTime(LocalTime time)`：将 `LocalTime` 转换为 `HH:mm:ss` 格式的时间字符串。

- `isValidTime(String timeAsString)`：检查给定字符串是否是有效的 `HH:mm:ss` 时间。如果是，将返回布尔值 true，否则返回 false。

## Best practices {#best-practices}

- **提供清晰的时间格式示例**：在 `TimeField` 附近清晰显示用户期望的时间格式。使用示例或占位符来帮助他们正确输入时间。如果可能，根据用户的位置显示时间格式。

- **可访问性**：考虑可访问性使用 `TimeField` 组件，确保符合可访问性标准，例如提供适当的标签、足够的颜色对比度以及与辅助技术的兼容性。

- **重置选项**：提供一种方式，让用户能够轻松清除 `TimeField` 到空或默认状态。
