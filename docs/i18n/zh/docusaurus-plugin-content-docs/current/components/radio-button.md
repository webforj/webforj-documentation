---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 0445bb7e995db7e0d725964c66690d19
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton` 组件是一个可以被选中或取消选中的对象，并向用户显示其状态。单选按钮通常用于可互斥的选项，使用户能够从一组选项中选择一个。

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::tip 对 `RadioButton` 组件进行分组
使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 来管理一组单选按钮，当您希望用户选出一个选项时。
:::

## 用法 {#usages}

`RadioButton` 最适用于用户需要从预定义选项集中进行单一选择的场景。以下是使用 `RadioButton` 的一些示例：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择一个响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，允许用户从一组互斥的选择中选择一个选项。

3. **过滤或排序**：在要求用户选择单个过滤器或排序选项的应用程序中，可以使用 `RadioButton`，例如按不同标准对项目列表进行排序。

## 文本和定位 {#text-and-positioning}

单选按钮可以使用 ```setText(String text)``` 方法，该方法将根据内置的 `Position` 在单选按钮附近定位。单选按钮内置功能可以设置文本显示在组件的左侧或右侧。默认情况下，文本将在组件的右侧显示。水平文本的定位通过使用 `HorizontalAlignment` 枚举类来支持。以下是两个设置：<br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## 激活 {#activation}

单选按钮可以通过两种激活类型进行控制：手动激活和自动激活。这些决定了 `RadioButton` 何时改变其状态。

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，这意味着它在获得焦点时不会自动被选中。手动激活允许用户使用键盘或其他输入方法浏览单选按钮选项，而不会立即更改所选选项。

如果单选按钮是组的一部分，选择组内的另一个单选按钮将自动取消选中先前选择的单选按钮。手动激活提供了更细致的选择控制，要求用户采取明确的操作来更改所选选项。


### 自动激活 {#auto-activation}

自动激活是 `RadioButton` 的默认状态，意味着按钮将在获得焦点时被检查。这意味着不仅单击，而且自动聚焦或选项卡导航也将检查该按钮。

:::tip 注意
默认激活值为 **`MANUAL`** 激活。
:::


## 开关 {#switches}

`RadioButton` 也可以设置为作为开关显示，提供选择选项的替代可视化表示。通常，单选按钮是圆形或圆润的形状，并表示从一组选项中选择一个选项。

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` 可以通过以下两种方法之一转换为看起来像切换开关或滑块的开关：

1. **工厂方法**：单选按钮可以使用以下工厂方法创建：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法与 `RadioButton` 构造函数镜像，将创建已切换开关属性的组件。

2. **设置器**：也可以通过使用适当的设置器将已存在的 `RadioButton` 更改为开关：

```java
myRadioButton.setSwitch(true);
```


当 `RadioButton` 作为开关显示时，通常呈现为带有指示器的椭圆形状，指示器可以切换开或关。这种可视化表示为用户提供了更直观和熟悉的界面，类似于电子设备中常见的物理开关。

将 `RadioButton` 设置为作为开关显示可以通过提供清晰简便的选择方式来改善用户体验。它可以增强表单、设置面板或任何需要多个选择的界面元素的视觉吸引力和可用性。

:::info
当单选按钮以开关形式呈现时，其行为保持不变，意味着在同一个组内只能选择一个选项。开关样式的外观是可视化转换，保留了 `RadioButton` 的功能。
:::

<br/>

## 样式 {#styling}

### 扩展 {#expanses}
支持五种复选框扩展，允许快速样式设置而无需使用 CSS。扩展通过使用 `Expanse` 枚举类来支持。以下是复选框组件支持的扩展：<br/>

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为了确保使用 RadioButton 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标记选项**：为每个 `RadioButton` 选项提供清晰简洁的文本，以准确描述选择。文本应该易于理解，且彼此区分。

2. **分组单选按钮**：将相关的单选按钮分组在一起，以指示它们的关系。这帮助用户理解在特定组内只能选择一个选项。这可以通过使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 组件有效实现。

3. **提供默认选择**：如适用，考虑为单选按钮提供默认选择，以指导用户在首次遇到选项时。默认选择应与最常见或最受欢迎的选择一致。
