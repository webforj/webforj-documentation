---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: bf6829e0fafbd0c69a49a5563e8a298b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

`DateField` 组件允许用户通过年、月和日输入或选择日期。它会自动处理验证，因此格式不正确的日期会在表单提交之前被捕获。

<!-- INTRO_END -->

## 使用 `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` 扩展了共享的 `Field` 类，该类提供所有字段组件的公共特性。以下示例创建了出发和返回的 DateFields，使之同步，并设置最小和最大限制来限制可选择的范围。

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## 字段值 (`LocalDate`) {#field-value-localdate}

`DateField` 组件将其值内部存储为 `LocalDate` 对象，表示没有时间或时区信息的日期。这使得在不同系统中能够准确处理基于日历的输入。

:::info 显示的值与解析的值 
虽然 **显示的值** 会适应用户的浏览器区域，以确保符合地区习惯的格式（例如，在美国为 `MM/DD/YYYY` 或在欧洲为 `DD.MM.YYYY`），但 **解析的值** 始终依赖于固定格式 `yyyy-MM-dd`。
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

您也可以使用原始字符串分配一个值，但它必须遵循精确的 `yyyy-MM-dd` 格式：

```java
dateField.setText("2024-04-27"); //有效

dateField.setText("04/27/2024"); //无效
```

:::warning
 使用 `setText()` 方法时，如果组件无法以 `yyyy-MM-dd` 格式解析输入，则会抛出 `IllegalArgumentException`。
:::

## 用法 {#usages}

`DateField` 非常适合在您的应用中选择和显示日期。以下是一些使用 `DateField` 的示例：

1. **事件调度和日历**：日期字段在涉及调度事件、预约或跟踪重要日期的应用中至关重要。

2. **表单输入**：简化用户填写需要日期的表单（如生日）时的日期选择过程。

3. **预订和预约系统**：涉及预订和预约系统的应用通常需要用户输入特定日期。日期字段简化了这一过程，确保准确的日期选择。

4. **任务管理和截止日期**：在涉及任务管理或设置截止日期的应用中，日期字段非常有价值。用户可以轻松指定截止日期、开始日期或其他时间敏感的信息。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}
`setMin()` 方法定义了用户可以输入到组件中的最早日期。如果输入早于指定的最小值，则会失败约束验证。与 `setMax()` 一起使用时，最小值必须是与最大值相同或早于最大值的日期。

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // 允许的最小值：2023年1月1日
```

### 最大值 {#the-max-value}
`setMax()` 方法定义了组件接受的最迟日期。如果输入的日期晚于指定的最大值，则输入无效。当同时定义两个值时，最大值必须是与最小值相同或晚于最小值的日期。

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // 允许的最大值：2023年12月31日
```

## 静态工具 {#static-utilities}

`DateField` 类还提供以下静态实用方法：

- `fromDate(String dateAsString)`：将 `yyyy-MM-dd` 格式的日期字符串转换为 `LocalDate` 对象，此对象可以在此字段或其他地方使用。

- `toDate(LocalDate date)`：将 `LocalDate` 对象转换为 `yyyy-MM-dd` 格式的日期字符串。

- `isValidDate(String dateAsString)`：检查给定字符串是否是有效的 `yyyy-MM-dd` 日期。

## 最佳实践 {#best-practices}

为了确保在使用 `DateField` 组件时获得最佳用户体验，请考虑以下最佳实践：

- **可访问性**：使用合适的标签以确保使用辅助技术的用户可以轻松导航并使用应用中的日期字段。

- **自动填充当前日期**：如果适合您应用的用例，可以自动填充当前日期到日期字段中。
