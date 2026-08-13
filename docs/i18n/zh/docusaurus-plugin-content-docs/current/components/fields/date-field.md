---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: 173c4a1d080dc6e0c01828131af61c08
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

`DateField` 组件允许用户按年、月和日输入或选择日期。它自动处理验证，因此错误格式的日期会在表单提交之前被捕获。

<!-- INTRO_END -->

## 使用 `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` 扩展了共享的 `Field` 类，该类提供所有字段组件的通用特性。以下示例创建了出发和返回的 DateFields，并保持同步，同时设置最小和最大约束以限制可选择范围。

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## 字段值 (`LocalDate`) {#field-value-localdate}

`DateField` 组件将其值内部存储为 `LocalDate` 对象，表示没有时间或时区信息的日期。这允许在不同系统中准确处理基于日历的输入。

:::info 显示值与解析值
虽然 **显示值** 会根据用户的浏览器区域设置进行调整，确保符合区域习惯的格式（例如，美国的 `MM/DD/YYYY` 或欧洲的 `DD.MM.YYYY`），**解析值** 始终依赖于固定格式 `yyyy-MM-dd`。
:::

### 获取和设置 `LocalDate` 值 {#getting-and-setting-the-localdate-value}

要检索当前值，请使用 `getValue()` 方法：

```java
LocalDate value = dateField.getValue();
```

要以编程方式设置值，请使用 `setValue()` 方法：

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### 使用 `setText()` {#using-settext}

您还可以使用原始字符串分配值，但必须遵循精确的 `yyyy-MM-dd` 格式：

```java
dateField.setText("2024-04-27"); // 有效

dateField.setText("04/27/2024"); // 无效
```

:::warning
当使用 `setText()` 方法时，如果组件无法以 `yyyy-MM-dd` 格式解析输入，则会抛出 `IllegalArgumentException`。
:::

## 用途 {#usages}

`DateField` 非常适合选择和显示应用程序中的日期。以下是一些使用 `DateField` 的示例：

1. **事件调度和日历**：日期字段在涉及调度事件、预订约会或跟踪重要日期的应用程序中至关重要。

2. **表单输入**：简化用户填写需要日期的表单（例如，生日）的日期选择过程。

3. **预订和预约系统**：涉及预订和预约系统的应用程序通常要求用户输入特定日期。日期字段简化了过程并确保准确的日期选择。

4. **任务管理和截止日期**：日期字段在涉及任务管理或设置截止日期的应用程序中非常有价值。用户可以轻松指定截止日期、开始日期或其他时间敏感信息。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}
`setMin()` 方法定义用户可以在组件中输入的最早日期。如果输入早于指定的最小值，约束验证将失败。当与 `setMax()` 一起使用时，最小值必须是与最大值相同或更早的日期。

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // 允许的最小值：2023年1月1日
```

### 最大值 {#the-max-value}
`setMax()` 方法定义组件接受的最晚日期。如果输入的日期晚于指定的最大值，输入无效。当同时定义两个值时，最大值必须是与最小值相同或更晚的日期。

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // 允许的最大值：2023年12月31日
```

## 静态工具 {#static-utilities}

`DateField` 类还提供以下静态工具方法：

- `fromDate(String dateAsString)`：将 `yyyy-MM-dd` 格式的日期字符串转换为 `LocalDate` 对象，随后可以在此字段或其他地方使用。

- `toDate(LocalDate date)`：将 `LocalDate` 对象转换为 `yyyy-MM-dd` 格式的日期字符串。

- `isValidDate(String dateAsString)`：检查给定字符串是否为有效的 `yyyy-MM-dd` 日期。

## 最佳实践 {#best-practices}

为确保使用 `DateField` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **无障碍性**：利用适当的标签确保使用辅助技术的用户能够轻松导航并使用应用程序中的日期字段。

- **自动填写当前日期**：如果适合应用程序的使用场景，可以自动填写当前日期。
