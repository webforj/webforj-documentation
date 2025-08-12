---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 2fcf0727f1bcfd60a2800bad252733ba
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

您可以使用 `NumberField` 组件来接受用户的数值输入。它确保仅输入有效的数字值，并提供便捷的数字输入界面。

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## 字段值 {#field-value}

`NumberField` 组件将其值存储为 `Double`，允许精确处理整数和小数。

### 获取当前值 {#getting-the-current-value}

您可以使用以下代码来检索用户输入的数值：

```java
Double currentValue = numberField.getValue();
```

### 设置新值 {#setting-a-new-value}

要以编程方式设置字段值：

```java
numberField.setValue(42.5);
```

如果未输入任何值且未设置默认值，则 `getValue()` 将返回 `null`。

:::tip
虽然该字段设计为仅接受有效的数字输入，但请记住，底层值是可以为 null 的。在使用结果之前始终测试是否为 null。
:::

## 用法 {#usages}

`NumberField` 最适用于捕获、显示或处理数字数据对您的应用至关重要的场景。以下是使用 `NumberField` 的一些示例：

1. **数字输入表单**：在设计需要数字输入的表单时，使用 `NumberField` 简化了用户的输入过程。这对于收集用户数据或需要数值的应用程序尤为有用。

2. **数据分析和计算**：在涉及数据分析、计算或数学运算的应用程序中，`NumberField` 尤其有价值。它们允许用户准确输入或操作数值。

3. **财务和预算应用程序**：涉及财务计算、预算或跟踪支出的应用程序通常需要准确的数字输入。`NumberField` 确保财务数字的准确输入。

4. **测量和单位转换**：在处理测量或单位转换的应用程序中，`NumberField` 是输入带单位的数值（如长度、重量或体积）的理想选择。

## 最小值和最大值 {#min-and-max-value}

使用 `setMin()` 方法，您可以指定数字字段中可接受的最小值。如果用户输入的值低于此阈值，组件将失败约束验证并提供适当的反馈。

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // 最小允许值: 0.0
```

另一方面，`setMax()` 方法允许您定义可接受的最大值。如果用户输入的值高于此限制，则输入将被拒绝。当设置最小值和最大值时，最大值必须大于或等于最小值。

```java
numberField.setMax(100.0); // 最大允许值: 100.0
```

在此配置中，输入 -5 或 150 的值将是无效的，而 0 到 100 之间的值是被接受的。

## 精度 {#granularity}

您可以使用 `setStep()` 方法来指定在使用箭头键修改值时，值必须遵循的精度。这将在每次修改时按一定步长递增或递减组件的值。这不适用于用户直接输入的值，而仅在与 `NumberField` 进行交互时适用。

## 占位符文本 {#placeholder-text}

您可以使用 `setPlaceholder()` 方法为 `NumberField` 设置占位符文本。当字段为空时，显示占位符文本，有助于提示用户在 `NumberField` 中输入适当的内容。

:::tip 提供明确的上下文以确保准确性
如果数值输入与特定的测量单位相关或具有特定上下文，请提供明确的标签或附加信息，以指导用户并确保准确输入。
:::

## 最佳实践 {#best-practices}

为了确保顺利集成和最佳用户体验，在使用 `NumberField` 时，请考虑以下最佳实践：

- **可访问性**：在使用 `NumberField` 组件时，要考虑可访问性，遵循适当的标签、键盘导航支持和与辅助技术的兼容性等可访问性标准。确保残障用户能够有效地与 `NumberField` 进行互动。

- **利用递增/递减按钮**：如果适合您的应用，请考虑与 `NumberField` 一起使用递增和递减按钮。这允许用户通过单击来按特定增量或减量调整数值。
