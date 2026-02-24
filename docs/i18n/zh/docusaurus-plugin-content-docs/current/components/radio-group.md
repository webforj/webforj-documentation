---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: da7906128f0d003b9ed8c48c99c3aefc
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` 管理一组 [`RadioButton`](/docs/components/radiobutton) 组件。在 `RadioButtonGroup` 中只能选择一个 `RadioButton`。当用户选中一个新的单选按钮时，组中的之前选中的单选按钮会自动取消选中。

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important `RadioButtonGroup` 渲染
`RadioButtonGroup` 组件不渲染 HTML 元素。它只提供逻辑，使 `RadioButton` 组件作为一个组进行行为，而不是单独的。
:::

## 添加和移除 `RadioButton` 组件 {#adding-and-removing-radiobuttons}

您可以在 `RadioButtonGroup` 构造函数中包含 `RadioButton` 组件，以便创建一个由提供的组件组成的组。
要从现有的 `RadioButtonGroup` 添加或移除 `RadioButton`，请使用 `add()` 或 `remove()` 方法。

:::tip 获取 `RadioButton` 的组
`RadioButton` 组件具有 `getButtonGroup()` 方法，该方法返回其所属的 `RadioButtonGroup`，如果没有组，则返回 `null`。
:::

## 嵌套 <DocChip chip='since' label='25.11' /> {#nesting}

与其他组件一样，您可以将 `RadioButtonGroup` 嵌套在容器中，这样您就不必直接添加每个单独的 `RadioButton`。

```java
RadioButton agree = new RadioButton("同意");
RadioButton neutral = new RadioButton("中立");
RadioButton disagree = new RadioButton("不同意");

RadioButtonGroup group = new RadioButtonGroup("选择", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("选项");
fieldset.add(group);
```

## 使用 `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

每个 `RadioButton` 可以有自己的事件监听器，以检测用户何时切换它。然而，使用 `RadioButtonGroup` 的一个优势是，您可以使用一个单一的事件监听器来响应组中所有单选按钮的 [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html)。

**为每个 `RadioButton` 添加事件监听器**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**为 `RadioButtonGroup` 添加一个单一事件监听器**

```java
RadioButtonGroup group = new RadioButtonGroup("选择", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

以下来自 [Drawer Placement](/docs/components/drawer#placement) 的示例使用 `RadioButtonGroupChangeEvent` 自动更改 `Drawer` 组件的放置：

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 命名 {#naming}

`RadioButtonGroup` 中的 `name` 属性将相关的单选按钮组合在一起，使用户可以从提供的选项中做出单一选择，并强制执行单选按钮之间的排他性。然而，组的名称不会反映在 DOM 中，而是为 Java 开发者提供的便利工具。
