---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 19e51a9c57a6524781ac008abcebc790
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton` 组件表示一个可以被选中或取消选中的单一选项。单选按钮通常被组合在一起，以便选中一个时自动取消选中其他选项，让用户从一组互斥的选项中做出单一选择。

<!-- INTRO_END -->

## 用法 {#usages}

`RadioButton` 最适合用于用户需要从预定义的选项集合中进行单一选择的场景。以下是使用 `RadioButton` 的一些例子：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择一个响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，允许用户从一组互斥的选项中选择一个。

3. **筛选或排序**：`RadioButton` 可用于需要用户选择单一筛选或排序选项的应用程序，例如按不同标准对项目列表进行排序。

:::tip 组合 `RadioButton` 组件
使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 来管理一组单选按钮，以便让用户选择一个选项。
:::

## 文本和定位 {#text-and-positioning}

单选按钮可以利用 ```setText(String text)``` 方法，该方法将根据内置的 `Position` 在单选按钮附近定位。
单选按钮具有内置功能，可以设置文本显示在组件的右侧或左侧。默认情况下，文本将显示在组件的右侧。水平文本的定位支持使用 `HorizontalAlignment` 枚举类。下面是两种设置的示例： <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## 激活 {#activation}

单选按钮可以通过两种激活方式进行控制：手动激活和自动激活。这些方式决定了 `RadioButton` 何时会改变其状态。

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，这意味着当它获得焦点时不会自动选中。
手动激活允许用户使用键盘或其他输入方式在单选按钮选项之间导航，而不立即更改所选选项。

如果单选按钮属于一个组，选择组内的其他单选按钮将自动取消选中之前选择的单选按钮。
手动激活为选择过程提供了更精细的控制，要求用户采取明确操作来更改所选选项。


### 自动激活 {#auto-activation}

自动激活是 `RadioButton` 的默认状态，意味着当它出于任何原因获得焦点时，该按钮将被选中。这意味着不仅点击，而且自动聚焦或标签导航也会选中该按钮。

:::tip 注意
默认激活值为 **`MANUAL`** 激活。
:::


## 开关 {#switches}

`RadioButton` 还可以设置为以开关显示，提供选择选项的另一种视觉表现。通常，单选按钮呈圆形或圆润形状，表示从选项组中选择单一选择。

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` 可以通过以下两种方法转换为看起来像切换开关或滑块的开关：

1. **工厂方法**：可以使用以下工厂方法创建单选按钮：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法镜像 `RadioButton` 构造函数，并将创建的组件的开关属性已经切换为开启。

2. **设置器**：也可以通过使用适当的设置器将已经存在的 `RadioButton` 转换为开关：

```java
myRadioButton.setSwitch(true);
```


当 `RadioButton` 以开关显示时，它通常呈现为一个椭圆形状，带有可以切换开启或关闭的指示器。这种视觉表现为用户提供了更直观和熟悉的界面，类似于电子设备中常见的物理开关。 

将 `RadioButton` 设置为以开关显示可以通过提供清晰直接的方式来选择选项，从而改善用户体验。它可以增强表单、设置面板或任何需要多选的界面元素的视觉吸引力和可用性。

:::info
当 `RadioButton` 以开关的形式呈现时，其行为保持不变，这意味着在同一组中只能选中一个选项。开关样式外观是一种视觉转换，保留了 `RadioButton` 的功能。
:::

<br/>

## 样式 {#styling}

### 范围 {#expanses}
支持五种复选框范围，可以快速样式化而无需使用 CSS。
范围通过使用 `Expanse` 枚举类进行支持。以下是复选框组件支持的范围： <br/>

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为确保在使用 RadioButton 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标记选项**：为每个 `RadioButton` 选项提供清晰简洁的文本，以准确描述选择。文本应该易于理解，并且能相互区分。

2. **组合单选按钮**：将相关的单选按钮组合在一起，以指示它们的关联性。这帮助用户理解在特定组内只能选择一个选项。这可以通过使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 组件有效实现。

3. **提供默认选择**：如果适用，可以考虑为单选按钮提供默认选择，以在用户首次遇到选项时提供指导。默认选择应与最常见或首选的选择一致。
