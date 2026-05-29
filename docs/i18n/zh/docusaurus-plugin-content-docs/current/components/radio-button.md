---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 491fdadd826e3b34acc02b8833704faf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton` 组件表示可以选择或取消选择的单个选项。单选按钮通常被分组在一起，以便选择一个选项会自动取消选择其他选项，让用户从一组相互排斥的选项中做出单一选择。

<!-- INTRO_END -->

## 用法 {#usages}

`RadioButton` 最适用于用户需要从预定义选项集中进行单一选择的场景。以下是一些使用 `RadioButton` 的示例：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择一个响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，让用户从一组互斥的选择中选择一个选项。

3. **筛选或排序**：`RadioButton` 可用于要求用户选择单一筛选或排序选项的应用程序，例如按不同标准对项目列表进行排序。

:::tip 分组 `RadioButton` 组件
使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 来管理一组单选按钮，当你希望用户选择单一选项时。
:::

## 文本和定位 {#text-and-positioning}

单选按钮可以利用 ```setText(String text)``` 方法，该方法将在单选按钮附近根据内置 `Position` 进行定位。
单选按钮具有内置功能，可以设置文本以在组件的左右两侧显示。默认情况下，文本将在组件的右侧显示。水平文本的定位通过使用 `HorizontalAlignment` 枚举类来支持。以下是两个设置： <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## 激活 {#activation}

单选按钮可以通过两种激活方式进行控制：手动激活和自动激活。这决定了 `RadioButton` 何时会改变其状态。

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，表示在获得焦点时不会自动选中。
手动激活允许用户使用键盘或其他输入方法在单选按钮选项之间导航，而无需立即更改所选选项。

如果单选按钮是组的一部分，选择组内的其他单选按钮将自动取消选中先前选中的单选按钮。
手动激活提供了更细致的选择控制，需要用户明确操作以更改所选选项。

### 自动激活 {#auto-activation}

自动激活是 `RadioButton` 的默认状态，意味着每当按钮由于任何原因获得焦点时，它将被选中。这意味着不仅点击，自动获取焦点或标签导航也会选中按钮。

:::tip 注意
默认激活值为 **`MANUAL`** 激活。
:::


## 切换 {#switches}

`RadioButton` 也可以设置为显示为一个切换开关，为选择选项提供另一种视觉呈现。通常，单选按钮是圆形或圆角形状，表示一组选项中的单个选择。

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

`RadioButton` 可以通过以下两种方法之一转换为切换开关，外观类似于切换开关或滑块：

1. **工厂方法**：可以使用以下工厂方法创建单选按钮：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法镜像 `RadioButton` 构造函数，并将创建已经切换打开的组件。

2. **Setter**：也可以通过使用适当的 setter 将已存在的 `RadioButton` 更改为切换开关：

```java
myRadioButton.setSwitch(true);
```


当 `RadioButton` 显示为开关时，它通常呈现为长方形形状，带有可以切换开或关的指示器。这种视觉呈现为用户提供了更直观和熟悉的界面，类似于电子设备上常见的物理开关。

将 `RadioButton` 设置为显示为切换开关可以通过提供清晰而简单的选择方式来改善用户体验。它可以增强表单、设置面板或任何其他需要多个选择的界面元素的视觉吸引力和可用性。

:::info
当以开关形式呈现时，`RadioButton` 的行为保持不变，这意味着在一组中只能选择一个选项。类似开关的外观是一种视觉转换，保留了 `RadioButton` 的功能。
:::

<br/>

## 样式 {#styling}

### 扩展 {#expanses}
支持五种复选框扩展，允许快速样式设置而无需使用 CSS。
扩展通过使用 `Expanse` 枚举类来支持。以下是复选框组件支持的扩展： <br/>

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为了确保在使用 `RadioButton` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标记选项**：为每个 `RadioButton` 选项提供清晰简洁的文本，以准确描述选择。文本应便于理解，且彼此区分明显。

2. **分组单选按钮**：将相关的单选按钮分组在一起，以指示它们之间的关联。这有助于用户理解在特定组中只能选择一个选项。可以有效使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 组件来完成此操作。

3. **提供默认选择**：如适用，考虑为单选按钮提供默认选择，以指导用户首次遇到选项时的选择。默认选择应与最常见或首选的选择一致。
