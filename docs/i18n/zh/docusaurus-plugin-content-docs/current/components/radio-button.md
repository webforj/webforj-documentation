---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: efd1171b68ca07b593064abe0366ded7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton` 类创建一个可以选择或取消选择的对象，并向用户显示其状态。根据惯例，在一组中只能选择一个单选按钮。当存在互斥选项时，单选按钮通常用于允许用户从一组选项中选择一个选项。

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## 用法 {#usages}

`RadioButton` 最适合用于用户需要从预定义选项集中进行单一选择的场景。以下是使用 `RadioButton` 的一些示例：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择一个响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，允许用户从一组互斥选择中选择一个选项。

3. **过滤或排序**：`RadioButton` 可以用于需要用户选择单一过滤或排序选项的应用程序，例如根据不同标准对项目列表进行排序。

## 文本和定位 {#text-and-positioning}

单选按钮可以利用 ```setText(String text)``` 方法，该方法将根据内置 `Position` 在单选按钮附近定位。
单选按钮具有内置功能，可以设置文本显示在组件的右侧或左侧。默认情况下，文本将显示在组件的右侧。水平文本的定位通过使用 `HorizontalAlignment` 枚举类来支持。下面显示了两种设置： <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## 激活 {#activation}

单选按钮可以使用两种类型的激活来控制：手动激活和自动激活。这些决定了 `RadioButton` 何时改变其状态。

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，这意味着它在获得焦点时不会自动被选中。
手动激活允许用户使用键盘或其他输入方法在单选按钮选项之间导航，而不会立即更改所选选项。

如果单选按钮是组的一部分，选择组内的其他单选按钮将自动取消选中之前选择的单选按钮。
手动激活提供了对选择过程的更细致控制，要求用户采取明确的操作来更改所选选项。

### 自动激活 {#auto-activation}

自动激活是 `RadioButton` 的默认状态，这意味着无论因何原因获得焦点，按钮都将被选中。这意味着
不仅点击，而且自动聚焦或选项卡导航也将选中按钮。

:::tip 注意
默认激活值为 **`MANUAL`** 激活。
:::

## 切换 {#switches}

`RadioButton` 还可以设置为以切换的形式显示，提供选择选项的替代视觉表示。通常，单选按钮呈圆形或圆角形状，表示从一组选项中选择一个选项。 

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` 可以通过两种方法转换为类似于切换开关或滑块的切换：

1. **工厂方法**：单选按钮可以使用以下工厂方法创建：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法与 `RadioButton` 构造函数相似，将创建已切换属性打开的组件。

2. **Setter**：也可以通过使用适当的 setter 将现有的 `RadioButton` 更改为切换：

```java
myRadioButton.setSwitch(true);
```

当 `RadioButton` 显示为切换时，它通常呈现为带有可切换指示器的椭圆形状。此视觉表示为用户提供了更直观和熟悉的界面，类似于电子设备中常见的物理切换。

将 `RadioButton` 设置为显示为切换可以通过提供清晰而简单的选项选择方式来改善用户体验。它可以增强表单、设置面板或任何需要多个选择的界面元素的视觉吸引力和可用性。

:::info
当以切换呈现时，`RadioButton` 的行为保持不变，这意味着在一组中一次只能选择一个选项。切换外观是视觉转换，保留了 `RadioButton` 的功能。
:::

<br/>

## 样式 {#styling}

### 扩展 {#expanses}
支持五种复选框扩展，允许快速样式设置而无需使用 CSS。
通过使用 `Expanse` 枚举类来支持扩展。下面是复选框组件支持的扩展： <br/>

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为了确保在使用 RadioButton 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标注选项**：为每个 `RadioButton` 选项提供清晰简洁的文本，以准确描述选择。文本应易于理解并彼此区分。

2. **分组单选按钮**：将相关单选按钮分组在一起，以指示其关联性。这帮助用户理解在特定组内只能选择一个选项。可以有效使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 组件完成这一点。

3. **提供默认选择**：如果适用，请考虑为单选按钮提供默认选择，以指导用户在首次遇到选项时。默认选择应与最常见或最受欢迎的选择保持一致。
