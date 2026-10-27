---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9b4c187f1c86207e13b38812f0eb7e6c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` 是一个用户界面组件，允许用户以小时和分钟的精确度输入或选择时间。如果提供了秒数，组件将丢弃它们。

<!-- INTRO_END -->

## 使用 `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` 扩展了共享的 `Field` 类，提供了所有字段组件的共同特性。以下示例创建了一个提醒 `TimeField`，其初始化为当前时间。

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## 用途 {#usages}

`TimeField` 非常适合在您的应用中选择和显示时间。以下是一些使用 `TimeField` 的示例：

1. **事件安排**：在涉及设置事件、约会或会议时间的应用中，时间字段是必不可少的。

2. **时间追踪和记录**：跟踪时间的应用（如工时表）需要时间字段以确保准确的记录。

3. **提醒和闹钟**：使用时间字段可以简化用户在您的应用中设置提醒或闹钟的输入过程。

## 最小和最大值 {#min-and-max-value}

通过 `setMin()` 和 `setMax()` 方法，您可以指定可接受时间的范围。

- **对于 `setMin()`**：如果输入到组件的值早于指定的最小时间，组件将无法通过约束验证。当同时设置最小和最大值时，最小值必须等于或早于最大值。

- **对于 `setMax()`**：如果输入到组件的值晚于指定的最大时间，组件将无法通过约束验证。当同时设置最小和最大值时，最大值必须等于或晚于最小值。

## 值处理和本地化 {#value-handling-and-localization}

在内部，`TimeField` 组件使用 `java.time` 包中的 `LocalTime` 对象表示其值。这使得开发者可以与精确的时间值进行交互，而不论它们的视觉呈现方式如何。

浏览器决定为用户的区域设置如何显示时间选择器。字段的文本值使用 24 小时的 `HH:mm` 格式，其 `LocalTime` 值被截断到分钟。

如果设置原始字符串值，请小心使用 `setText()` 方法：

```java
timeField.setText("09:15");    // 有效
timeField.setText("09:15:30"); // 也有效；秒数被丢弃，留下 09:15
```

:::warning
使用 `setText()` 时，如果输入无法解析为有效时间，将抛出 `IllegalArgumentException`。接受 `HH:mm` 和 `HH:mm:ss` 的输入，但秒数会被丢弃。
:::

:::info 选择器 UI
时间选择器输入 UI 的外观取决于所选的语言环境、浏览器和操作系统。这与用户已经熟悉的界面自动保持一致。
:::

## 静态工具 {#static-utilities}

`TimeField` 类还提供以下静态工具方法：

- `fromTime(String timeAsString)`：解析包含或不包含秒数的时间字符串，转换为截断到分钟的 `LocalTime`。

- `toTime(LocalTime time)`：将 `LocalTime` 转换为 `HH:mm` 格式的字符串，丢弃秒数。

- `isValidTime(String timeAsString)`：检查时间字符串是否有效，包括 `HH:mm` 和 `HH:mm:ss` 输入。如果有效则返回 `true`，否则返回 `false`。

## 最佳实践 {#best-practices}

- **提供清晰的时间格式示例**：在 `TimeField` 附近清晰地向用户展示预期的时间格式。使用示例或占位符帮助他们正确输入时间。如果可能，根据用户的位置显示时间格式。

- **无障碍性**：在设计 `TimeField` 组件时考虑无障碍性标准，确保满足可访问性标准，如适当的标签、足够的色彩对比度和与辅助技术的兼容性。

- **重置选项**：提供方式让用户能够轻松地将 `TimeField` 清空或恢复到默认状态。
