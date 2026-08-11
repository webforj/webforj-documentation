---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9688647e85d453578ccd59934e52e26b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` 是一个用户界面组件，允许用户输入或选择小时、分钟，以及可选的秒。它提供了一种直观且高效的方式来处理各种应用中的时间相关信息。

<!-- INTRO_END -->

## 使用 `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` 扩展了共享的 `Field` 类，该类为所有字段组件提供了共同的特性。以下示例创建了一个初始化为当前时间的提醒 `TimeField`。

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## 使用场景 {#usages}

`TimeField` 适合在您的应用中选择和显示时间。以下是一些使用 `TimeField` 的示例场景：

1. **事件调度**：时间字段在涉及设置事件、约会或会议时间的应用中至关重要。

2. **时间追踪和记录**：需要时间字段以确保准确条目的应用，例如时间表应用。

3. **提醒和闹钟**：使用时间字段可以简化用户在您的应用中设置提醒或闹钟的输入过程。

## 最小值和最大值 {#min-and-max-value}

通过 `setMin()` 和 `setMax()` 方法，您可以指定可接受时间的范围。

- **对于 `setMin()`**：如果输入到组件的值早于指定的最小时间，该组件将无法通过约束验证。当同时设置最小值和最大值时，最小值必须与最大值相同或早于最大值。

- **对于 `setMax()`**：如果输入到组件的值晚于指定的最大时间，该组件将无法通过约束验证。当同时设置最小值和最大值时，最大值必须与最小值相同或晚于最小值。

## 值处理和本地化 {#value-handling-and-localization}

在内部，`TimeField` 组件使用来自 `java.time` 包的 `LocalTime` 对象表示其值。这使得开发人员可以与精确的时间值进行交互，而不论这些值的视觉呈现如何。

虽然 **客户端组件使用用户的浏览器区域设置显示时间**，但解析和存储的格式始终标准化为 `HH:mm:ss`。

如果设置原始字符串值，请谨慎使用 `setText()` 方法：

```java
timeField.setText("09:15:00"); // 有效
```

:::warning
 使用 `setText()` 方法时，如果组件无法解析 `HH:mm:ss` 格式的输入，将抛出 `IllegalArgumentException`。
:::


:::info 选择器 UI
时间选择器输入 UI 的外观不仅取决于选定的区域设置，还取决于所使用的浏览器和操作系统。这确保了与用户已经熟悉的界面的自动一致性。
:::

## 静态工具 {#static-utilities}

`TimeField` 类还提供以下静态工具方法：

- `fromTime(String timeAsString)`：将 HH:mm:ss 格式的时间字符串转换为可以在此类中或其他地方使用的 `LocalTime` 对象。

- `toTime(LocalTime time)`：将 `LocalTime` 转换为 HH:mm:ss 格式的时间字符串。

- `isValidTime(String timeAsString)`：检查给定字符串是否为有效的 HH:mm:ss 时间。如果是，则返回布尔值 true，否则返回 false。

## 最佳实践 {#best-practices}

- **提供清晰的时间格式示例**：在 `TimeField` 附近清晰显示用户期望的时间格式。使用示例或占位符帮助他们正确输入时间。如果可能，根据用户的位置显示时间格式。

- **可访问性**：考虑可访问性因素使用 `TimeField` 组件，确保其符合可访问性标准，例如提供适当的标签、足够的颜色对比度和与辅助技术的兼容性。

- **重置选项**：提供一种方法，让用户能够轻松将 `TimeField` 清空或重置为默认状态。
