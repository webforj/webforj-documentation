---
title: 编写源代码的更改
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: dd96407889e9e9fa1de4af299809dcad
---
在 craftforJ 中更改属性仅会影响运行中的应用程序，而不会影响其他任何内容。要保留更改，您需要审查并将其写入原始 Java 文件。此页面描述了该步骤。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ 会写入您的项目
请确保您的工作在版本控制中。应用更改之前，请先阅读差异，并在提交之前再次阅读。
:::

## 待处理更改 {#pending-changes}

您更改的每个属性都会被记录为待处理更改，并且 craftforJ 会显示有多少更改在等待。待处理更改会在页面重新加载和路由更改后保存，因为 craftforJ 会在您的组件重建时重新应用它们。

## 审查和应用 {#reviewing-and-applying}

按 <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> 以打开审查界面。更改按文件分组，每个更改显示属性及其旧值和新值，并扩展为文件的差异。如果更改将用固定值替换计算值，craftforJ 会警告您并命名即将被替换的表达式。在您应用之前，什么都不会被写入。您可以单独恢复或放弃每个更改。

![更改按文件分组的审查界面，其中一个文件展开显示其差异](/img/craftforj/source-changes/review.png#rounded-border)

## 选择更改的写入位置 {#choosing-where-a-change-is-written}

更改写入的位置决定了其影响范围。当组件直接在视图中构建时，变更将进入该视图。当它在可重用类内部构建时，您有两个选项：

- **使用** - 组件被使用的地方，仅更改您面前的屏幕。这是默认选项。
- **定义** - 构建组件的地方，更改所有使用它的屏幕。

每个待处理更改都会显示适用的选项，并允许您在两者之间切换。某些属性只能在定义中写入，因为组件自己设置这些属性，而不是从调用方那里接受它们。craftforJ 在您应用之前会标记这些属性。

## 应用之后 {#after-you-apply}

写入 Java 会导致您的应用重建并重新启动。craftforJ 反馈重启状态，等待重启完成，并保持您的选择和剩余未处理更改不变。已应用的更改一旦写入文件就会从待处理列表中移除。

这是唯一一个重载设置重要的时刻。craftforJ 不需要实时重载来工作，因为您在检查期间所做的所有更改都会立即在运行的应用中生效，无需重建。写入源文件则不同：这会更改构建应用的文件，因此在您的代码更改生效之前，应用必须重建，并且该变化不是来自 craftforJ。如果配置了[实时重载](/docs/configuration/deploy-reload/overview)，这会自动发生。如果没有，需要您手动重启应用。

## 关闭它 {#turning-it-off}

您可以在 craftforJ 设置中关闭写入 Java 的功能，或者通过 [`source-changes`](/docs/craftforj/configuration#feature-flags) 属性完全移除它。关闭这两者后，属性编辑仍然有效，但保持实时状态。
