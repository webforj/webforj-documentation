---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: aa5037e2faa2968328081b1811dcabb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

`NumberField`组件接受数字输入，并自动拒绝无效值。它支持最小和最大范围、步长间隔以及占位符文本。

<!-- INTRO_END -->

## 使用`NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField`扩展了共享的`Field`类，提供了所有字段组件的共同特性。以下示例创建了一个带有标签和占位符文本的`NumberField`。

<ComponentDemo
path='/webforj/numberfield'
files={['src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java']}
/>

## 字段值 {#field-value}

`NumberField`组件将其值存储为`Double`，可以准确处理整数和小数。

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

如果未输入值且未设置默认值，`getValue()`将返回`null`。

:::tip
虽然该字段设计用于仅接受有效数字输入，但请记住，底层值是可空的。在使用结果之前，请始终检查是否为null。
:::

## 用法 {#usages}

`NumberField`最适合用于捕获、显示或操作数字数据对您的应用至关重要的场景。以下是使用`NumberField`的一些示例：

1. **数字输入表单**：在设计需要数字输入的表单时，使用`NumberField`简化了用户的输入过程。这对于收集用户数据或需要数值的应用程序特别有用。

2. **数据分析和计算**：在涉及数据分析、计算或数学操作的应用中，`NumberField`尤其有价值。它允许用户准确输入或操控数字值。

3. **财务和预算应用**：涉及财务计算、预算或跟踪开支的应用程序通常需要精确的数字输入。`NumberField`确保财务数字的准确输入。

4. **度量和单位转换**：在处理测量或单位转换的应用中，`NumberField`非常适合输入带有单位的数字值，例如长度、重量或体积。

## 最小和最大值 {#min-and-max-value}

使用`setMin()`方法，您可以指定数字字段中可接受的最小值。如果用户输入的值低于此阈值，组件将无法通过约束验证，并提供适当的反馈。

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // 最小允许值: 0.0
```

单独使用`setMax()`方法可以定义最大可接受值。如果用户输入的值高于此限制，输入将被拒绝。当设置了最小值和最大值时，最大值必须大于或等于最小值。

```java
numberField.setMax(100.0); // 最大允许值: 100.0
```

在此配置中，输入 -5 或 150 等值将无效，而接受在 0 到 100 之间的值。

## 粗细 {#granularity}

您可以使用`setStep()`方法指定在使用箭头键修改值时值必须遵循的粗细程度。每次按下箭头键时，组件的值将增减一定的步长。这不适用于用户直接输入的值，而仅适用于与`NumberField`进行交互时使用箭头键。

## 占位符文本 {#placeholder-text}

您可以使用`setPlaceholder()`方法为`NumberField`设置占位符文本。当字段为空时，会显示占位符文本，帮助提示用户输入适当的内容。

:::tip 提供清晰的上下文以确保准确性
如果数值输入与特定的测量单位相关或具有特定的上下文，请提供清晰的标签或额外信息以引导用户，确保准确输入。
:::

## 最佳实践 {#best-practices}

为确保无缝集成和最佳用户体验，在使用`NumberField`时请考虑以下最佳实践：

- **可访问性**：在设计时考虑可访问性，遵循可访问性标准，如适当的标签、键盘导航支持和与辅助技术的兼容性。确保残疾人士能够有效与`NumberField`进行交互。

- **利用增减按钮**：如果适合您的应用，考虑为`NumberField`增设增减按钮。这使用户能够通过单击特定的增量或减量来调整数字值。
