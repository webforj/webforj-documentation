---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: 564d1d0c46ef2395fb2ad2785ba18d53
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` 管理一组 [`RadioButton`](/docs/components/radiobutton) 组件。在 `RadioButtonGroup` 中只能选择一个 `RadioButton`。当用户选中一个新的单选按钮时，组中之前选中的单选按钮会自动取消选中。

<!-- INTRO_END -->

## 创建 `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` 渲染
`RadioButtonGroup` 组件不渲染 HTML 元素。它只提供逻辑，使 `RadioButton` 组件作为一个组而不是单独行为。
:::

创建单独的 `RadioButton` 组件并将它们传递给 `RadioButtonGroup` 构造函数。组中的按钮一次只能选中一个。

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## 添加和删除 `RadioButton` 组件 {#adding-and-removing-radiobuttons}

您可以在 `RadioButtonGroup` 构造函数中包含 `RadioButton` 组件，以创建一个由提供组件组成的组。
要从现有的 `RadioButtonGroup` 中添加或删除 `RadioButton`，请使用 `add()` 或 `remove()` 方法。

:::tip 获取 `RadioButton` 的组
`RadioButton` 组件具有 `getButtonGroup()` 方法，该方法返回它所属的 `RadioButtonGroup`，如果没有组，则返回 `null`。
:::

## 嵌套 <DocChip chip='since' label='25.11' /> {#nesting}

与其他组件一样，您可以在容器中嵌套 `RadioButtonGroup`，这样您就不必直接添加每个单独的 `RadioButton`。

```java
RadioButton agree = new RadioButton("同意");
RadioButton neutral = new RadioButton("中立");
RadioButton disagree = new RadioButton("不同意");

RadioButtonGroup group = new RadioButtonGroup("选择", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("选项");
fieldset.add(group);
```

## 使用 `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

每个 `RadioButton` 都可以拥有自己的事件监听器，以检测用户何时切换它。然而，使用 `RadioButtonGroup` 的一个优势是您可以使用一个单一的事件监听器，该监听器响应组中所有单选按钮的 [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html)。

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

以下示例来自 [抽屉位置](/docs/components/drawer#placement)，它使用 `RadioButtonGroupChangeEvent` 自动更改 `Drawer` 组件的放置：

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 命名 {#naming}

`RadioButtonGroup` 中的 `name` 属性将相关的单选钮组合在一起，允许用户从提供的选项中做出单一选择，并在单选按钮之间强制排他性。组的名称在 DOM 中没有反映，但对于 Java 开发人员来说是一个便捷的工具。
