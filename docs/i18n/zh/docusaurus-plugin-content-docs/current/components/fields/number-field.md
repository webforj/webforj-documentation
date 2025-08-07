---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 0d5052fd2f20b391e0eaadbf7c771e5e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

您可以使用 `NumberField` 组件来接受用户的数字输入。它确保只输入有效的数字值，并提供便于输入数字的界面。

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## 字段值 {#field-value}

`NumberField` 组件将其值存储为 `Double`，允许准确处理整数和小数。

### 获取当前值 {#getting-the-current-value}

您可以使用以下方法检索用户输入的数字值：

```java
Double currentValue = numberField.getValue();
```

### 设置新值 {#setting-a-new-value}

要以编程方式设置字段：

```java
numberField.setValue(42.5);
```

如果没有输入值并且未设置默认值，则 `getValue()` 将返回 `null`。

:::tip
虽然该字段旨在仅接受有效的数字输入，但请注意底层值是可为空的。在使用结果之前，请始终测试是否为 null。
:::

## 用途 {#usages}

`NumberField` 最适用于需要捕获、显示或处理数字数据的场景。以下是使用 `NumberField` 的一些示例：

1. **数字输入表单**：在设计需要数字输入的表单时，使用 `NumberField` 简化了用户的输入过程。这对于收集用户数据或要求提供数字值的应用特别有用。

2. **数据分析和计算**：在涉及数据分析、计算或数学运算的应用中，`NumberField` 特别有价值。它允许用户准确输入或处理数字值。

3. **财务和预算应用**：涉及财务计算、预算或跟踪支出的应用通常需要精确的数字输入。`NumberField` 确保财务数字的准确输入。

4. **测量和单位转换**：在处理测量或单位转换的应用中，`NumberField` 是输入长度、重量或体积等单位数值的理想选择。

## 最小值和最大值 {#min-and-max-value}

使用 `setMin()` 方法，您可以指定数字字段中接受的最小值。如果用户输入的值低于此阈值，该组件将无法通过约束验证并提供适当的反馈。

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // 最小允许值: 0.0
```

此外，`setMax()` 方法允许您定义最大可接受值。如果用户输入的值高于此限制，输入将被拒绝。当设置了最小值和最大值时，最大值必须大于或等于最小值。

```java
numberField.setMax(100.0); // 最大允许值: 100.0
```

在这种配置下，输入值如 -5 或 150 将是无效的，而 0 到 100 之间的值是被接受的。

## 精度 {#granularity}

您可以使用 `setStep()` 方法指定用户通过箭头键修改值时必须遵循的精度。这将在每次修改时以一定步长增加或减少组件的值。此设置不适用于用户直接输入值，只适用于使用箭头键与 `NumberField` 交互时。

## 占位符文本 {#placeholder-text}

您可以使用 `setPlaceholder()` 方法为 `NumberField` 设置占位符文本。当字段为空时，会显示占位符文本，以帮助提示用户在 `NumberField` 中输入适当的内容。

:::tip 提供清晰的上下文以确保准确性
如果数字输入与特定的单位或上下文相关，请提供清晰的标签或额外的信息，以引导用户并确保准确输入。
:::

## 最佳实践 {#best-practices}

为了确保无缝集成和最佳用户体验，在使用 `NumberField` 时，请考虑以下最佳实践：

- **可访问性**：考虑到可访问性标准，使用 `NumberField` 组件时，要遵循适当的标签、键盘导航支持和与辅助技术的兼容性。确保残疾用户能够有效地与 `NumberField` 互动。

- **利用增加/减少按钮**：如果适合您的应用，请考虑与 `NumberField` 一起使用增加和减少按钮。这使用户能够通过单击特定的增量或减量来调整数字值。
