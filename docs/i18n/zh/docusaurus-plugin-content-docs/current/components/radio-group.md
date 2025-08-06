---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 8e58efd7b052a00eaf8cfce276cda92e
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` 类用于将相关的单选按钮组合在一起，这有助于在该组内建立选项之间的互斥性。用户只能在给定的单选组中选择一个单选按钮。当用户在组内选择一个单选按钮时，任何先前在同一组中选择的单选按钮将自动取消选择。这确保一次只能选择一个选项。

:::tip
`RadioButton` 组件存储其所属的组，可以通过 `getButtonGroup()` 方法访问。
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
`RadioButtonGroup` 组件不会在页面上渲染一个 HTML 元素。相反，它仅是一种逻辑，确保一组单选按钮作为一个组而不是单独行为。
:::

## 用法 {#usages}

`RadioButtonGroup` 最适合用于用户需要从一组预定义选项中选择一个单一选项的场景。以下是一些使用 `RadioButtonGroup` 的示例：

1. **调查或问卷**：`RadioButtonGroup` 组件常用于调查或问卷中，用户需要从一系列选项中选择一个单一的响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序通常使用 `RadioButtonGroup` 组件，以允许用户从一组互斥选择中选择一个选项。

3. **过滤或排序**：在需要用户选择单一过滤或排序选项的应用程序中，可以使用 `RadioButton`，例如按不同标准对项目列表进行排序。

<!-- vale off -->
## 添加和删除单选按钮 {#adding-and-removing-radiobuttons}
<!-- vale on -->

可以向一组中添加和删除单个或多个 `RadioButton` 对象，确保它们表现出互斥的检查行为，并与任何可能属于该组的名称相关联。

## 命名 {#naming}

`RadioButtonGroup` 中的名称属性将相关的单选按钮分组在一起，允许用户从提供的选项中做出单一选择，并强制执行单选按钮之间的排他性。组的名称不会反映在 DOM 中，而是 Java 开发者的便捷工具。

## 最佳实践 {#best-practices}

为了确保使用单选按钮组件时的最佳用户体验，请考虑以下最佳实践：

1. **明确标记选项**：为每个 `RadioButton` 选项提供清晰简洁的标签，以准确描述选择。标签应易于理解，并且彼此之间要有明显的区分。

2. **提供默认选择**：如适用，可以考虑为单选按钮提供默认选择，以引导用户在第一次遇到选项时。默认选择应与最常见或首选的选项一致。
