---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: b261deb2944ba67d2e9ae8cbbb8b26eb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton` 组件表示一个可以选择或取消选择的单一选项。单选按钮通常组合在一起，以便选择一个时自动取消选择其他选项，让用户从一组相互排斥的选项中进行单一选择。

<!-- INTRO_END -->

## 用法 {#usages}

`RadioButton` 最适合用于用户需要从预定义选项集中进行单一选择的场景。以下是一些使用 `RadioButton` 的示例：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择单一答案。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，允许用户从一组相互排斥的选项中选择一个。

3. **过滤或排序**：`RadioButton` 可用于需要用户选择单一过滤器或排序选项的应用程序，例如根据不同标准对项目列表进行排序。

:::tip 分组 `RadioButton` 组件
使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 来管理一组单选按钮，当您希望用户选择一个选项时。
:::

## 文本和位置 {#text-and-positioning}

使用 `setText(String text)` 设置单选按钮的标签。默认情况下，文本出现在组件的右侧。要更改其位置，请使用 `setTextPosition(Position.LEFT)` 或 `setTextPosition(Position.RIGHT)` 与 `HasTextPosition.Position` 枚举一起使用。

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>

## 激活 {#activation}

单选按钮可以通过两种激活方式控制：手动激活和自动激活。这些决定了 `RadioButton` 何时会更改其状态。默认值为 `Activation.MANUAL`，来自 `RadioButton.Activation` 枚举。

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，这意味着它在获得焦点时不会自动选中。手动激活允许用户使用键盘或其他输入方式在单选按钮选项之间导航，而不会立即更改所选选项。

如果单选按钮是组的一部分，在组内选择不同的单选按钮将自动取消之前选择的单选按钮。手动激活提供了对选择过程的更细致控制，需要用户明确的操作才能更改所选选项。

### 自动激活 {#auto-activation}

通过 `setActivation(Activation.AUTO)` 启用自动激活。在此模式下，单选按钮在获得焦点时会被选中，包括通过点击、自动聚焦或标签导航。

## 开关 {#switches}

单选按钮也可以设置为显示为开关，提供选择选项的替代视觉表示。通常，单选按钮呈圆形或圆边形，并表示从一组选项中选择一个。

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

`RadioButton` 可以通过以下两种方法之一转换为类似于切换开关或滑动条的开关：

1. **工厂方法**：单选按钮可以使用以下工厂方法创建：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法与 `RadioButton` 构造函数相似，并将组件创建为已经开启开关属性。

2. **Setter**：使用适当的 setter 也可以将一个已存在的 `RadioButton` 转换为开关：

```java
myRadioButton.setSwitch(true);
```

当 `RadioButton` 显示为开关时，它通常呈长形，并带有可以切换开或关的指示器。这种视觉表示为用户提供了更直观和熟悉的界面，类似于在电子设备中常见的物理开关。

将 `RadioButton` 设置为显示为开关可以通过提供一个清晰明了的选择选项方式来提升用户体验。它可以增强表单、设置面板或其他需要多个选择的界面元素的视觉吸引力和可用性。

:::info
`RadioButton` 的行为在作为开关渲染时保持不变，这意味着在一组中只能选择一个选项。开关状外观是视觉转化，保留了 `RadioButton` 的功能。
:::

<br/>

## 样式 {#styling}

### 扩展 {#expanses}

使用 `setExpanse()` 配合 `Expanse` 枚举来调整 `RadioButton` 的大小，而无需编写 CSS。可用值为 `XSMALL`、`SMALL`、`MEDIUM`、`LARGE`、`XLARGE` 和 `NONE`。

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为了确保使用 `RadioButton` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标记选项**：为每个 `RadioButton` 选项提供清晰简洁的文本，以准确描述选择。文本应易于理解并彼此区分。

2. **分组单选按钮**：将相关的单选按钮分组在一起以表明它们的关联性。这有助于用户理解在特定组内只能选择一个选项。可以使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 组件有效实现。

3. **提供默认选择**：如适用，考虑为单选按钮提供默认选择，以指导用户在第一次遇到选项时。默认选择应与最常见或首选的选择一致。
