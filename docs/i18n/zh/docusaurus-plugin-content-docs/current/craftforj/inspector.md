---
title: 检查组件
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 93bf0d91de1f942373082ccebd0bab6d
---
检查器显示了您 Java 代码构建的组件树。一个 `Composite` 将显示您编写的类，包含您按 webforJ 提供的顺序给它的子组件，因此 craftforJ 中的结构与您的源结构相匹配。

![运行应用程序中选中并高亮显示的组件树](/img/craftforj/inspector/tree-selection.png#rounded-border)

## 选择组件 {#selecting-a-component}

要从页面中选择一个组件，请按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> 并单击它。craftforJ 将在树中选择匹配的节点。在树中悬停一个节点会反向高亮显示该页面上的组件，因此您可以在屏幕和树之间双向移动。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

要搜索树，请按 <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>。用斜杠包围一个术语会将其视为正则表达式。右键单击节点会打开可用于该节点的操作。您可以打开其源代码或将其交给 [助手](/docs/craftforj/ai)。

## 读取和更改属性 {#reading-and-changing-properties}

选择一个组件将填充侧边栏以显示其属性，按影响类别分组。组件提供的属性取决于组件本身，其中一些是只读的。那些以普通文本无法很好读取的属性会配备适合其值的编辑器。更改一个值将在正在运行的应用程序中立即生效。

:::info 实时编辑不会修改您的文件
属性编辑会改变眼前的应用程序，而不会影响其他内容。将其应用到源代码中是一个单独的步骤，您需要有意执行，详见 [将更改写入源](/docs/craftforj/source-changes)。
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## 查看组件的源代码 {#viewing-the-source-of-a-component}

您可以追溯任何组件到构建它的 Java 代码。默认情况下，源代码以只读方式在 craftforJ 中打开，位于创建该组件的行。您可以配置 craftforJ 使其在您的编辑器中打开，仍然处于相同的行。当一个组件无法追溯到某一行时，craftforJ 会报告此情况，而不是打开一个空查看器。

![源代码查看器定位于创建所选组件的行](/img/craftforj/inspector/source-viewer.png#rounded-border)
