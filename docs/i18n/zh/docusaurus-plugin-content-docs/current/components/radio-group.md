---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
description: >-
  Coordinate mutually exclusive RadioButton selections with RadioButtonGroup,
  including nested containers and dynamic membership.
_i18n_hash: a616c60faaf0d58f9d9a1e778318880a
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` 管理一组 [`RadioButton`](/docs/components/radiobutton) 组件。在一个 `RadioButtonGroup` 中只能选择一个 `RadioButton`。当用户选择一个新的单选按钮时，之前在组中选中的单选按钮会自动取消选中。

<!-- INTRO_END -->

## 创建一个 `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` 渲染
`RadioButtonGroup` 组件不会渲染 HTML 元素。它只提供逻辑，使 `RadioButton` 组件作为一个组而不是单独行为。
:::

创建单独的 `RadioButton` 组件，并将它们传递给 `RadioButtonGroup` 构造函数。组中的一次只能选择一个按钮。

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com.webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## 添加和移除 `RadioButton` 组件 {#adding-and-removing-radiobuttons}

您可以在 `RadioButtonGroup` 构造函数中包含 `RadioButton` 组件，以从提供的组件中创建一个组。
要将 `RadioButton` 添加到现有的 `RadioButtonGroup` 或从中移除，请使用 `add()` 或 `remove()` 方法。

:::tip 获取 `RadioButton` 的组
`RadioButton` 组件具有 `getButtonGroup()` 方法，该方法返回它所属的 `RadioButtonGroup`，如果没有分组，则返回 `null`。
:::

## 嵌套 <DocChip chip='since' label='25.11' /> {#nesting}

与其他组件一样，您可以在容器中嵌套一个 `RadioButtonGroup`，这样您就不必直接添加每个单独的 `RadioButton`。

```java
RadioButton agree = new RadioButton("同意");
RadioButton neutral = new RadioButton("中立");
RadioButton disagree = new RadioButton("不同意");

RadioButtonGroup group = new RadioButtonGroup("选择", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("选项");
fieldset.add(group);
```

## 使用 `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

每个 `RadioButton` 都可以有自己的事件监听器，以检测用户何时切换它。但是，使用 `RadioButtonGroup` 的一个优点是您可以使用一个事件监听器来响应组中所有单选按钮的操作，使用 [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html)。

**为每个 `RadioButton` 添加事件监听器**

```java
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**为 `RadioButtonGroup` 添加单一事件监听器**

```java
RadioButtonGroup group = new RadioButtonGroup("选择", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

来自 [抽屉位置](/docs/components/drawer#placement) 的以下示例使用 `RadioButtonGroupChangeEvent` 自动更改 `Drawer` 组件的位置：

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## 命名 {#naming}

`RadioButtonGroup` 中的 `name` 属性将相关的 RadioButtons 分组在一起，使用户能够从提供的选项中做出单一选择，并强制执行 RadioButtons 之间的排他性。然而，组的名称不会在 DOM 中反映，而是 Java 开发人员的一个便利工具。
