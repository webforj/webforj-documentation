---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: 32d2e2f74e7f255b901de15622e8e2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton`组件表示一个可以选择或取消选择的单一选项。单选按钮通常组合在一起，以便选择一个时自动取消选择其他选项，让用户从一组选项中做出单一选择。

<!-- INTRO_END -->

## 用法 {#usages}

`RadioButton`最适用于用户需要从预定义选项集中进行单一选择的场景。以下是一些使用`RadioButton`的示例：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择一个单一的响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，以允许用户从一组互斥的选择中选择一个选项。

3. **过滤或排序**：在需要用户选择单个过滤器或排序选项的应用程序中，可以使用`RadioButton`，例如按不同标准对项目列表进行排序。

:::tip 分组 `RadioButton` 组件
使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 来管理一组单选按钮，以便用户选择一个选项。
:::

## 文本和定位 {#text-and-positioning}

单选按钮可以利用```setText(String text)```方法，该方法将根据内置的`Position`定位在单选按钮附近。单选按钮具有内置功能，可以设置文本显示在组件的左侧或右侧。默认情况下，文本将显示在组件的右侧。使用`HorizontalAlignment`枚举类支持水平文本的定位。下面是两个设置：<br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## 激活 {#activation}

单选按钮可以使用两种类型的激活来控制：手动激活和自动激活。这决定了`RadioButton`何时更改其状态。

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，这意味着当它获得焦点时不会自动被选中。手动激活允许用户使用键盘或其他输入方法在单选按钮选项之间导航，而不会立即更改所选选项。

如果单选按钮是组的一部分，在组内选择一个不同的单选按钮将自动取消选中之前选中的单选按钮。手动激活提供更细致的控制选择过程，要求用户明确采取行动以更改所选选项。


### 自动激活 {#auto-activation}

自动激活是`RadioButton`的默认状态，这意味着每当单选按钮获得焦点时，它将被选中。这意味着不仅是点击，自动聚焦或标签导航也会选中该按钮。

:::tip 注意
默认的激活值是 **`MANUAL`** 激活。
:::


## 开关 {#switches}

`RadioButton`还可以被设置为显示为开关，这提供了选择选项的替代视觉表示。通常，单选按钮是圆形或圆角形状，表示从选项组中选择一个选项。

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

`RadioButton`可以通过两种方法转换为类似切换开关或滑块的开关：

1. **工厂方法**：可以使用以下工厂方法创建单选按钮：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法与`RadioButton`构造函数相似，并将组件创建为开关属性已经切换开启。

2. **设置器**：还可以使用适当的设置器将已存在的`RadioButton`更改为开关：

```java
myRadioButton.setSwitch(true);
```


当`RadioButton`以开关的形式显示时，它通常呈现为带有指示器的长方形形状，指示器可以被切换开启或关闭。这种视觉表示使用户获得更加直观和熟悉的界面，类似于在电子设备中常见的物理开关。

将`RadioButton`设置为显示为开关可以通过提供清晰且简单的选项选择方式来改善用户体验。它可以增强表单、设置面板或任何其他需要多个选择的界面的视觉吸引力和可用性。

:::info
当`RadioButton`渲染为开关时，其行为保持不变，这意味着在一组中只能选择一个选项。开关般的外观是视觉转换，保留了`RadioButton`的功能。
:::

<br/>

## 样式 {#styling}

### 扩展 {#expanses}
支持五种复选框扩展，允许快速样式设置而不使用CSS。
扩展通过使用`Expanse`枚举类来支持。以下是复选框组件支持的扩展：<br/>

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为确保使用`RadioButton`组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标记选项**：为每个`RadioButton`选项提供清晰简洁的文本，以准确描述选择。文本应易于理解且可相互区分。

2. **组合单选按钮**：将相关的单选按钮组合在一起以指示它们的关联。这有助于用户理解在特定组中只能选择一个选项。可以有效使用[`RadioButtonGroup`](/docs/components/radiobuttongroup)组件来实现。

3. **提供默认选择**：如果适用，考虑为单选按钮提供默认选择，以指导用户在第一次遇到选项时的选择。默认选择应与最常见或最受欢迎的选择一致。
