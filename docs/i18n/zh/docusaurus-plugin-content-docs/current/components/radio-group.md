---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 91d753e882e3d6d59deef5044ee7bc4c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

`RadioButtonGroup` 类用于将相关的单选按钮组在一起，这有助于建立该组内选项之间的互斥性。用户只能在给定的单选组中选择一个单选按钮。当用户在一个组中选择一个单选按钮时，同一组中任何先前选中的单选按钮会自动取消选中。这确保了一次只能选择一个选项。

:::tip
`RadioButton` 组件存储其所属的组，可以通过 `getButtonGroup()` 方法访问。
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
`RadioButtonGroup` 组件不会在页面上渲染 HTML 元素。它只是确保一组 `RadioButton` 按钮作为一个组而不是单独行为的逻辑。
:::

## Usages {#usages}

`RadioButtonGroup` 最适用于用户需要从一组预定义的选项中进行单项选择的场景，以下是一些使用 `RadioButtonGroup` 的示例：

1. **调查或问卷**：`RadioButtonGroup` 组件通常用于调查或问卷中，用户需要从选项列表中选择单个响应。

2. **偏好设置**：涉及偏好或设置面板的应用程序经常使用 `RadioButtonGroup` 组件，以允许用户从一组互斥选择中选择单个选项。

3. **过滤或排序**：在需要用户选择单个过滤或排序选项的应用程序中，可以使用 `RadioButton`，例如根据不同标准对项目列表进行排序。

<!-- vale off -->
## Adding and removing RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

可以向组中添加和删除单个或多个 `RadioButton` 对象，确保它们表现出互斥的选中行为，并与可能属于该组的任何名称相关联。

## Naming {#naming}

`RadioButtonGroup` 中的名称属性将相关的 `RadioButton` 组合在一起，允许用户从提供的选项中做出单个选择，同时强制 `RadioButton` 之间的独占性。组的名称不会在 DOM 中反映，但对于 Java 开发者来说，这是一个方便的工具。

## Best practices {#best-practices}

为确保在使用 `RadioButton` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **清晰标注选项**：为每个 `RadioButton` 选项提供清晰简洁的标签，以准确描述选择。标签应易于理解并且彼此区别明显。

2. **提供默认选择**：如适用，考虑为单选按钮提供默认选择，以引导用户在首次遇到选项时。默认选择应与最常见或首选的选择一致。
