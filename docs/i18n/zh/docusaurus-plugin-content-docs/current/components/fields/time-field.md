---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 994cad91e2870d59f3c0eec7c2b47141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` 是一个用户界面组件，允许用户输入或选择小时、分钟以及可选的秒数。它提供了一种直观且高效的方式来处理各种应用中的时间相关信息。

<!-- INTRO_END -->

## 使用 `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` 扩展了共享的 `Field` 类，提供了所有字段组件的共同特性。以下示例创建了一个初始化为当前时间的提醒 `TimeField`。

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## 用法 {#usages}

`TimeField` 非常适合在您的应用中选择和显示时间。以下是一些使用 `TimeField` 的场景示例：

1. **事件调度**：在涉及为事件、约会或会议设置时间的应用中，时间字段是不可或缺的。

2. **时间跟踪和记录**：需要准确条目的时间字段，如时间表应用，可以使用时间字段。

3. **提醒和闹钟**：使用时间字段简化用户在您的应用中设置提醒或闹钟的输入过程。

## 最小值和最大值 {#min-and-max-value}

通过 `setMin()` 和 `setMax()` 方法，您可以指定可接受时间的范围。

- **关于 `setMin()`**：如果输入组件的值早于指定的最小时间，则组件将无法通过约束验证。当同时设置最小和最大值时，最小值必须是与最大值相同或更早的时间。

- **关于 `setMax()`**：如果输入组件的值晚于指定的最大时间，则组件将无法通过约束验证。当同时设置最小和最大值时，最大值必须是与最小值相同或更晚的时间。

## 值处理和本地化 {#value-handling-and-localization}

`TimeField` 组件在内部使用 `java.time` 包中的 `LocalTime` 对象来表示其值。这允许开发人员与精确的时间值进行交互，而不管它们是如何可视化渲染的。

虽然 **客户端组件使用用户的浏览器语言环境显示时间**，但解析和存储的格式始终标准化为 `HH:mm:ss`。

如果设置原始字符串值，请小心使用 `setText()` 方法：

```java
timeField.setText("09:15:00"); // valid
```

:::warning
 使用 `setText()` 方法时，如果组件无法按照 `HH:mm:ss` 格式解析输入，将抛出 `IllegalArgumentException`。
:::


:::info 选择器 UI 
时间选择器输入 UI 的外观不仅取决于所选的语言环境，还取决于所使用的浏览器和操作系统。这样可以确保与用户已经熟悉的界面自动保持一致。
:::

## 静态工具 {#static-utilities}

`TimeField` 类还提供以下静态工具方法：

- `fromTime(String timeAsString)`：将 HH:mm:ss 格式的时间字符串转换为 `LocalTime` 对象，然后可以在此类中或其他地方使用。

- `toTime(LocalTime time)`：将 `LocalTime` 转换为 HH:mm:ss 格式的时间字符串。

- `isValidTime(String timeAsString)`：检查给定字符串是否是有效的 HH:mm:ss 时间。如果是，则返回布尔值 true，否则返回 false。

## 最佳实践 {#best-practices}

- **提供清晰的时间格式示例**：在 `TimeField` 附近清楚显示用户预期的时间格式。使用示例或占位符帮助他们正确输入时间。如果可能，基于用户的位置显示时间格式。

- **可访问性**：在使用 `TimeField` 组件时考虑可访问性，确保符合可访问性标准，例如提供适当的标签、足够的颜色对比和与辅助技术的兼容性。

- **重置选项**：提供一种方法，让用户可以轻松将 `TimeField` 清空或重置为默认状态。
