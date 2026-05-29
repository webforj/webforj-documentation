---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 5716356b99e40dc53cfdf82a87fd9b3c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` 管理一组 [`RadioButton`](/docs/components/radiobutton) 组件。在 `RadioButtonGroup` 中只能选择一个 `RadioButton`。当用户选中一个新的单选按钮时，该组中以前选中的按钮会自动取消选中。

<!-- INTRO_END -->

## 创建 `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` 渲染
`RadioButtonGroup` 组件不会渲染 HTML 元素。它仅提供逻辑，使 `RadioButton` 组件作为一个组而不是单独工作。
:::

创建单个 `RadioButton` 组件并将其传递给 `RadioButtonGroup` 构造函数。组中只能选择一个按钮。

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com.webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## 添加和移除 `RadioButton` 组件 {#adding-and-removing-radiobuttons}

您可以在 `RadioButtonGroup` 构造函数中包含 `RadioButton` 组件，以便从提供的组件中创建一个组。
要从现有的 `RadioButtonGroup` 中添加或移除 `RadioButton`，请使用 `add()` 或 `remove()` 方法。

:::tip 获取 `RadioButton` 的组
`RadioButton` 组件具有 `getButtonGroup()` 方法，该方法返回其所属的 `RadioButtonGroup`，如果没有组则返回 `null`。
:::

## 嵌套 <DocChip chip='since' label='25.11' /> {#nesting}

与其他组件一样，您可以将 `RadioButtonGroup` 嵌套在容器中，因此您不必直接添加每个单独的 `RadioButton`。

```java
RadioButton agree = new RadioButton("同意");
RadioButton neutral = new RadioButton("中立");
RadioButton disagree = new RadioButton("不同意");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("选项");
fieldset.add(group);
```

## 使用 `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

每个 `RadioButton` 都可以拥有自己的事件监听器，以检测用户何时切换它。然而，使用 `RadioButtonGroup` 的一个优点是您可以使用一个单一的事件监听器来响应组中所有单选按钮的切换，使用 [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html)。

**为每个 `RadioButton` 添加事件监听器**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**为 `RadioButtonGroup` 添加单个事件监听器**

```java
RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

以下示例来自 [Drawer Placement](/docs/components/drawer#placement)，使用 `RadioButtonGroupChangeEvent` 自动改变 `Drawer` 组件的位置：

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## 命名 {#naming}

`RadioButtonGroup` 中的 `name` 属性将相关的单选按钮组合在一起，使用户可以从提供的选项中进行单一选择，并在单选按钮之间强制排他性。然而，组的名称不会在 DOM 中反映出来，而是 Java 开发人员的一个便利工具。
