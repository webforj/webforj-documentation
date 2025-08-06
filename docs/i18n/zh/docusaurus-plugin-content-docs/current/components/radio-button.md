---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: bf7e30274560f1e29fc307b5894c533a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

`RadioButton` 类创建一个可以被选择或取消选择的对象，并向用户显示其状态。根据惯例，在一组中只能选择一个单选按钮。当可用选项互斥时，通常使用单选按钮，允许用户从一组选择中选择单个选项。

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## 用法 {#usages}

`RadioButton` 最适合用于用户需要从预定义选项集中进行单一选择的场景。以下是使用 `RadioButton` 的一些示例：

1. **调查或问卷**：单选按钮通常用于调查或问卷中，用户需要从选项列表中选择一个响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用单选按钮，让用户从一组互斥的选择中选择一个选项。

3. **过滤或排序**：`RadioButton` 可用于需要用户选择单个过滤或排序选项的应用程序，例如按不同标准对项目列表进行排序。

## 文本和定位 {#text-and-positioning}

单选按钮可以使用 ```setText(String text)``` 方法，文本将根据内置的 `Position` 定位在单选按钮附近。
单选按钮具有内置功能，可以设置文本显示在组件的左侧或右侧。默认情况下，文本将在组件的右侧显示。水平文本的定位通过使用 `HorizontalAlignment` 枚举类来支持。下面是两个设置： <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## 激活 {#activation}

单选按钮可以使用两种类型的激活进行控制：手动激活和自动激活。这决定了 `RadioButton` 何时会更改其状态。

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### 手动激活 {#manual-activation}

当单选按钮设置为手动激活时，意味着在获得焦点时不会自动被选中。
手动激活使用户能够使用键盘或其他输入方法浏览单选按钮选项，而不会立即更改所选选项。

如果单选按钮是组的一部分，选择组内的另一单选按钮将自动取消选中先前选择的单选按钮。
手动激活提供了对选择过程的更精细控制，需要用户明确采取行动来更改所选选项。

### 自动激活 {#auto-activation}

自动激活是 `RadioButton` 的默认状态，意味着当其因任何原因获得焦点时，按钮会被选中。这意味着不仅点击，自动聚焦或选项卡导航也会选中按钮。

:::tip 注意
默认激活值为 **`MANUAL`** 激活。
:::

## 交换开关 {#switches}

`RadioButton` 也可以设置为显示为一个开关，提供选择选项的替代视觉表现。通常，单选按钮是圆形或圆润的外形，表示从一组选项中选择一个。

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

`RadioButton` 可以通过以下两种方法之一转变为一个类似于切换开关或滑块的开关：

1. **工厂方法**：可以使用以下工厂方法创建 `RadioButton`：

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

这些方法镜像 `RadioButton` 构造函数，并将创建组件，其开关属性已经切换开启。

2. **Setter**：也可以使用适当的 setter 将已经存在的 `RadioButton` 更改为开关：

```java
myRadioButton.setSwitch(true);
```

当 `RadioButton` 作为开关显示时，通常呈现为一个带有可切换的指示器的长方形。此视觉表现为用户提供了更直观和熟悉的界面，类似于电子设备中常见的物理开关。

将 `RadioButton` 设置为显示为开关可以通过提供清晰直接的选项选择方式来改善用户体验。它可以增强表单、设置面板或任何其他需要多重选择的界面元素的视觉吸引力和可用性。

:::info
当以开关形式呈现时，`RadioButton` 的行为保持不变，这意味着在一组中只能选择一个选项。开关式外观是一种视觉转换，保留了 `RadioButton` 的功能。
:::

<br/>

## 样式 {#styling}

### 扩展 {#expanses}
支持五个复选框扩展，允许快速样式设置而无需使用 CSS。
扩展通过使用 `Expanse` 枚举类来支持。下面是复选框组件支持的扩展： <br/>

<TableBuilder name="RadioButton" />

## 最佳实践 {#best-practices}

为确保在使用 RadioButton 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标识选项**：为每个 `RadioButton` 选项提供清晰简洁的文本，以准确描述选择。文本应易于理解且具有可区分性。

2. **分组单选按钮**：将相关的单选按钮分组在一起，以指示它们的关联。这可以帮助用户理解在特定组中只能选择一个选项。可以有效地使用 [`RadioButtonGroup`](/docs/components/radiobuttongroup) 组件来实现这一点。

3. **提供默认选择**：如果适用，考虑为单选按钮提供默认选择，以在用户首次遇到选项时进行引导。默认选择应与最常见或首选的选择保持一致。
