---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: ca6e544259fc218b59cebd14d34e4530
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` 是一个用户界面组件，允许用户以小时、分钟和可选的秒数输入或选择时间。它提供了一种直观且高效的方式来处理各种应用程序中的时间相关信息。

<!-- INTRO_END -->

## 使用 `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` 扩展了共享的 `Field` 类，该类提供了所有字段组件的共同特性。以下示例创建了一个提醒 `TimeField`，其初始化为当前时间。

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## 用法 {#usages}

`TimeField` 非常适合在您的应用中选择和显示时间。以下是使用 `TimeField` 的一些示例：

1. **事件调度**：时间字段在涉及设定事件、约会或会议时间的应用中至关重要。

2. **时间跟踪和记录**：跟踪时间的应用，例如时间表，需要时间字段以确保准确记录。

3. **提醒和闹钟**：使用时间字段简化用户在应用中设置提醒或闹钟的输入过程。

## 最小值和最大值 {#min-and-max-value}

通过 `setMin()` 和 `setMax()` 方法，您可以指定可接受时间的范围。

- **对于 `setMin()`**：如果输入到组件中的值早于指定的最小时间，则组件将无法通过约束验证。当设置了最小和最大值时，最小值必须与最大值相同或更早。

- **对于 `setMax()`**：如果输入到组件中的值晚于指定的最大时间，则组件将无法通过约束验证。当设置了最小和最大值时，最大值必须与最小值相同或更晚。

## 值处理和本地化 {#value-handling-and-localization}

在内部，`TimeField` 组件使用 `java.time` 包中的 `LocalTime` 对象来表示其值。这使得开发者可以以精确的时间值进行交互，而不管它们的可视化呈现如何。

虽然 **客户端组件使用用户浏览器的语言环境显示时间**，但解析和存储的格式始终标准化为 `HH:mm:ss`。

如果设置原始字符串值，请小心使用 `setText()` 方法：

```java
timeField.setText("09:15:00"); // 有效
```

:::warning
 使用 `setText()` 方法时，如果组件无法以 `HH:mm:ss` 格式解析输入，将抛出 `IllegalArgumentException`。
:::

:::info 选择器 UI 
时间选择器输入 UI 的外观不仅取决于所选的语言环境，还依赖于所使用的浏览器和操作系统。这确保了与用户已经熟悉的界面的自动一致性。
:::

## 静态工具 {#static-utilities}

`TimeField` 类还提供以下静态实用方法：

- `fromTime(String timeAsString)`：将以 HH:mm:ss 格式的时间字符串转换为 `LocalTime` 对象，以供该类或其他地方使用。

- `toTime(LocalTime time)`：将 `LocalTime` 转换为以 HH:mm:ss 格式的时间字符串。

- `isValidTime(String timeAsString)`：检查给定字符串是否为有效的 HH:mm:ss 时间。如果是，则返回布尔值 true，否则返回 false。

## 最佳实践 {#best-practices}

- **提供清晰的时间格式示例**：在 `TimeField` 附近清晰地展示期望的时间格式。使用示例或占位符帮助用户正确输入时间。如果可能，根据用户的地理位置显示时间格式。

- **无障碍性**：考虑到无障碍性，使用 `TimeField` 组件，确保符合无障碍标准，例如提供适当的标签、足够的色彩对比度，并与辅助技术兼容。

- **重置选项**：提供一种方法，方便用户将 `TimeField` 清空或恢复为默认状态。
