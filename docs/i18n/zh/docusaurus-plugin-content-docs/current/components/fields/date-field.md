---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: ee9981c57d9964a3f759b116dbd75af2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

`DateField`是一个字段组件，允许用户通过年、月和日输入或选择日期。它提供了一种直观且高效的方法来处理各种应用程序中的与日期相关的信息，并提供了验证用户输入的灵活性。

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## 字段值 (`LocalDate`) {#field-value-localdate}

`DateField`组件将其值内部存储为`LocalDate`对象，表示没有时间或时区信息的日期。这允许在不同系统中准确处理基于日历的输入。

:::info 显示值与解析值
虽然**显示值**根据用户的浏览器区域设置进行调整，确保采用区域熟悉的格式（例如，美国的`MM/DD/YYYY`或欧洲的`DD.MM.YYYY`），但**解析值**始终依赖于固定的`yyyy-MM-dd`格式。
:::

### 获取和设置`LocalDate`值 {#getting-and-setting-the-localdate-value}

要检索当前值，请使用`getValue()`方法：

```java
LocalDate value = dateField.getValue();
```

要以编程方式设置值，请使用`setValue()`方法：

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### 使用`setText()` {#using-settext}

您还可以使用原始字符串分配值，但必须遵循确切的`yyyy-MM-dd`格式：

```java
dateField.setText("2024-04-27"); // 有效

dateField.setText("04/27/2024"); // 无效
```

:::warning
 使用`setText()`方法时，如果组件无法解析以`yyyy-MM-dd`格式输入，将抛出`IllegalArgumentException`。
:::

## 用法 {#usages}

`DateField`非常适合在应用中选择和显示日期。以下是一些使用`DateField`的示例：

1. **事件调度和日历**：日期字段在涉及调度事件、预订约会或跟踪重要日期的应用程序中是必不可少的。

2. **表单输入**：简化用户填写需要日期，如生日的表单中的日期选择过程。

3. **预订和预约系统**：涉及预订和预约系统的应用程序通常要求用户输入特定日期。日期字段简化了该过程，并确保准确的日期选择。

4. **任务管理和截止日期**：日期字段在涉及任务管理或设置截止日期的应用中非常有价值。用户可以轻松指定到期日期、开始日期或其他时间敏感信息。

## 最小值和最大值 {#min-and-max-value}

### 最小值 {#the-min-value}
`setMin()`方法定义用户可以输入的最早日期。如果输入早于指定的最小值，则会失败约束验证。当与`setMax()`一起使用时，最小值必须是与最大值相同或更早的日期。

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // 允许的最小日期: 2023年1月1日
```

### 最大值 {#the-max-value}
`setMax()`方法定义组件接受的最新日期。如果输入日期晚于指定的最大值，则输入无效。当同时定义这两个值时，最大值必须是与最小值相同或更晚的日期。

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // 允许的最大日期: 2023年12月31日
```

## 静态实用程序 {#static-utilities}

`DateField`类还提供以下静态实用方法：

- `fromDate(String dateAsString)`：将`yyyy-MM-dd`格式的日期字符串转换为`LocalDate`对象，然后可以在此字段或其他地方使用。

- `toDate(LocalDate date)`：将`LocalDate`对象转换为`yyyy-MM-dd`格式的日期字符串。

- `isValidDate(String dateAsString)`：检查给定字符串是否是有效的`yyyy-MM-dd`日期。

## 最佳实践 {#best-practices}

为了确保在使用`DateField`组件时获得最佳用户体验，请考虑以下最佳实践：

- **可访问性**：利用合适的标签，确保使用辅助技术的用户能够轻松导航并使用您应用中的日期字段。

- **自动填充当前日期**：如果适合您应用的用例，可以自动填充当前日期。
