---
title: 检查组件
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 5dd1df77df56d81dd4e54c1998289e71
---
检查器显示了您的 Java 代码构建的组件树。一个 `Composite` 作为您编写的类出现，持有您以 webforJ 所给的顺序提供的子组件，因此 craftforJ 中的结构与您的源代码中的结构匹配。

![运行应用程序中选中并突出显示的组件树](/img/craftforj/inspector/tree-selection.png#rounded-border)

## 选择组件 {#selecting-a-component}

要从页面中选择一个组件，请按 <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> 并单击它。craftforJ 将在树中选择匹配的节点。在树中悬停一个节点会反向操作并高亮显示该组件在页面中，因此您可以在屏幕和树之间任意移动。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

要搜索树，请按 <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>。用斜杠包围一个术语将其视为正则表达式。右键单击一个节点会打开可用的操作。您可以打开其源代码或将其交给 [助手](/docs/craftforj/ai)。

## 阅读和改变属性 {#reading-and-changing-properties}

选择一个组件会填充侧边栏显示其属性，按影响的内容进行分组。组件提供的属性取决于具体组件，其中一些是只读的。对于那些作为纯文本不容易阅读的属性，会提供一个适合其值的编辑器。改变一个值会立即在运行的应用程序中生效。

:::info 实时编辑不会修改您的文件
属性编辑会改变您面前的应用程序，而不会影响其他地方。将它应用到您的源代码中是一个您需要明确执行的单独步骤，详见 [将更改写入源代码](/docs/craftforj/source-changes)。
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## 查看组件的源代码 {#viewing-the-source-of-a-component}

您可以追溯任何组件到构建它的 Java 代码。默认情况下，源代码在 craftforJ 中以只读方式打开，定位在创建该组件的行。您可以配置 craftforJ 让它在您的编辑器中打开相同的行。当组件无法追溯到某一行时，craftforJ 会报告这一点，而不是打开一个空的查看器。

![源代码查看器定位在创建所选组件的行](/img/craftforj/inspector/source-viewer.png#rounded-border)
