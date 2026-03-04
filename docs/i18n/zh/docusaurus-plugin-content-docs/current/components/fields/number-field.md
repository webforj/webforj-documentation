---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: e1cde7099182ddabd898e0c5391fe8b7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

`NumberField` 组件接受数字输入并自动拒绝无效值。它支持最小和最大边界、步进间隔和占位符文本。

<!-- INTRO_END -->

## 使用 `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` 扩展了共享的 `Field` 类，提供了所有字段组件的通用特性。以下示例创建一个带有标签和占位符文本的 `NumberField`。

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## 字段值 {#field-value}

`NumberField` 组件将其值存储为 `Double`，可以准确处理整数和小数。

### 获取当前值 {#getting-the-current-value}

您可以使用以下代码检索用户输入的数字值：

```java
Double currentValue = numberField.getValue();
```

### 设置新值 {#setting-a-new-value}

要以编程方式设置字段：

```java
numberField.setValue(42.5);
```

如果没有输入值并且没有设置默认值，`getValue()` 将返回 `null`。

:::tip
虽然该字段设计为仅接受有效数字输入，但请记住，基础值是可空的。在使用结果之前，始终测试是否为 null。
:::

## 用法 {#usages}

`NumberField` 最适用于捕获、显示或操作数字数据对您的应用至关重要的场景。以下是一些使用 `NumberField` 的示例：

1. **数字输入表单**：当设计需要数字输入的表单时，使用 `NumberField` 可以简化用户的输入过程。这对于收集用户数据或要求输入数字值的应用尤其有用。

2. **数据分析和计算**：在涉及数据分析、计算或数学运算的应用中，`NumberField` 特别有价值。它允许用户准确输入或操作数字值。

3. **财务和预算应用**：涉及财务计算、预算或追踪费用的应用通常需要精确的数字输入。`NumberField` 可以确保金融数字的准确输入。

4. **测量和单位转换**：在处理测量或单位转换的应用中，`NumberField` 非常适合输入具有长度、重量或体积等单位的数字值。

## 最小值和最大值 {#min-and-max-value}

使用 `setMin()` 方法，您可以指定数字字段中可接受的最小值。如果用户输入一个低于这个阈值的值，组件将失败并提供适当的反馈。

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // 允许的最小值：0.0
```

此外，`setMax()` 方法允许您定义可接受的最大值。如果用户输入一个高于这一限制的值，输入将被拒绝。当同时设置最小值和最大值时，最大值必须大于或等于最小值。

```java
numberField.setMax(100.0); // 允许的最大值：100.0
```

在此配置中，输入类似于 -5 或 150 的值将是无效的，而 0 到 100 之间的值是被接受的。

## 粒度 {#granularity}

您可以使用 `setStep()` 方法指定值在使用箭头键修改时必须遵循的粒度。每次修改时将按特定步长增加或减少组件的值。这不适用于用户直接输入的值，而仅在使用箭头键与 `NumberField` 进行交互时适用。

## 占位符文本 {#placeholder-text}

您可以使用 `setPlaceholder()` 方法为 `NumberField` 设置占位符文本。当字段为空时，会显示占位符文本，有助于提示用户输入适当的数字。

:::tip 提供明确的上下文以确保准确性
如果数字输入与特定的计量单位相关或具有特定的上下文，请提供明确的标签或额外信息来引导用户并确保准确的输入。
:::

## 最佳实践 {#best-practices}

为了确保无缝集成和最佳用户体验，在使用 `NumberField` 时请考虑以下最佳实践：

- **无障碍性**：在使用 `NumberField` 组件时考虑无障碍性，遵循无障碍标准，如适当的标签、键盘导航支持和与辅助技术的兼容性。确保残障用户可以有效地与 `NumberField` 进行交互。

- **利用增减按钮**：如果适合您的应用，请考虑与 `NumberField` 一起使用增减按钮。这允许用户通过单击特定的增量或减量来调整数字值。
