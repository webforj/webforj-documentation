---
title: 将变更写回源
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c79e8574cbf260fd784a2cffc00a0ab5
---
在craftforJ中更改属性仅会影响正在运行的应用程序，而不会影响其他内容。要保存更改，您需要审核它并将其写入来源Java文件。此页面描述了该步骤。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4" type="video/mp4" />
  </video>
</div>

:::warning craftforJ 会写入您的项目
请在版本控制中保留您的工作。在应用更改之前阅读差异，并在提交之前再次阅读。
:::

## 待处理的更改 {#pending-changes}

您更改的每个属性都会记录为待处理更改，craftforJ会显示有多少待处理更改。待处理更改在页面重新加载和路由更改时依然有效，因为craftforJ在重建组件时会重新应用它们。

## 审核和应用 {#reviewing-and-applying}

按 <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> 打开审核。更改按将落入的文件进行分组。每个更改显示属性及其旧值和新值，并展开为文件的差异。如果某个更改将计算值替换为固定值，craftforJ会发出警告并命名即将被替换的表达式。在您被应用之前，没有任何内容被写入。在此之前，您可以单独撤销或丢弃每个更改。

![审核内容，按文件分组的更改以及一个展开到其差异](/img/craftforj/source-changes/review.png#rounded-border)

## 选择写入更改的位置 {#choosing-where-a-change-is-written}

更改写入的位置决定了它的影响范围。当组件直接在视图中构建时，更改将进入该视图。当它在可重用类内部构建时，您有两个选择：

- **使用** - 组件被使用的地方，只更改您面前的屏幕。这是默认选项。
- **定义** - 组件被构建的地方，改变所有使用它的屏幕。

每个待处理更改会显示适用的两者之一，并允许您在它们之间切换。有些属性只能在定义处写入，因为组件自行设置它们，而不是从调用者那里接受。craftforJ在您应用之前会标记这些属性。

## 应用之后 {#after-you-apply}

写入Java会导致您的应用重建和重启。craftforJ会报告重启，等待它，并保持您的选择和剩余的待处理更改完好无损。已应用的更改在它们写入文件后将离开待处理列表。

这是唯一一个重新加载设置重要的点。craftforJ不需要实时重载才能工作，因为您在检查时所做的所有更改会立即在运行的应用程序中生效，而无需重建。写入源文件则不同：它更改了构建您应用的文件，因此应用必须在更改来自您的代码而不是来自craftforJ之前重新构建。配置了[实时重载](/docs/configuration/deploy-reload/overview)时，这会自动发生。如果没有，请自行重启应用。

## 关闭它 {#turning-it-off}

您可以在craftforJ设置中关闭对Java的写入，或者通过[`source-changes`](/docs/craftforj/configuration#feature-flags)属性完全移除它。无论哪种关闭，属性编辑仍然有效，但保持实时状态。
