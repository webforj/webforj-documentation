---
title: 应用更改
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: de9ef40848946a35567c308c132dce0d
---
在 craftforJ 中更改属性会立即反映在正在运行的应用中，但不会更改其他内容。要保留更改，你需要先检查，然后将其写入对应的 Java 文件。本页介绍这一操作。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ 会写入你的项目
请使用版本控制保存你的工作。应用更改前先查看差异，提交前再检查一遍。
:::

## 待处理的更改 {#pending-changes}

你更改的每个属性都会记录为一项待处理更改，craftforJ 会显示等待处理的更改数量。即使重新加载页面或切换路由，待处理的更改也会保留，因为组件重建时 craftforJ 会重新应用这些更改。

## 检查并应用 {#reviewing-and-applying}

按 <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> 打开检查视图。更改会按目标文件分组。每项更改都会显示属性的旧值和新值，并可展开查看文件差异。如果某项更改会用固定值替换计算值，craftforJ 会发出警告，并指出即将被替换的表达式。应用之前，不会写入任何内容。应用前，你可以单独还原或丢弃每项更改。

![按文件分组的更改检查视图，其中一项已展开以显示文件差异](/img/craftforj/source-changes/review.png#rounded-border)

## 选择更改的写入位置 {#choosing-where-a-change-is-written}

更改的写入位置决定了它的影响范围。如果组件直接在视图中构建，更改会写入该视图。如果组件在可复用类中构建，则有两种选择：

- **使用位置** - 使用该组件的位置，只更改当前显示的屏幕。这是默认选项。
- **定义位置** - 构建组件的位置，会更改所有使用该组件的屏幕。

每项待处理的更改都会显示当前选择的位置，并允许你在两者之间切换。有些属性只能写入定义位置，因为组件会自行设置这些属性，而不是由调用方传入。craftforJ 会在应用前标记这些属性。

## 应用之后 {#after-you-apply}

写入 Java 文件会导致应用重新构建并重启。craftforJ 会报告重启状态、等待重启完成，然后重新连接，同时保留当前选择和剩余的待处理更改。更改写入文件后，就会从待处理列表中移除。

只有在这一步，重新加载配置才会起作用。craftforJ 无需实时重新加载也能工作，因为你在检查过程中所做的更改会立即在正在运行的应用中生效，无需重新构建。写入源文件则不同：它会修改构建应用时使用的文件，因此应用必须重新构建，才能让更改来自你的代码，而不是 craftforJ。如果已配置[实时重新加载](/docs/configuration/deploy-reload/overview)，应用会自动重新构建。否则，请手动重启应用。

## 撤销和重做 <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

在 craftforJ 中按 <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> 可撤销上一次更改，按 <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> 可重做。

撤销适用于你在正在运行的应用中所做的更改，包括[属性编辑](/docs/craftforj/inspector#reading-and-changing-properties)和[主题调整](/docs/craftforj/theme#adjusting-a-theme)。它也适用于 craftforJ 写入项目的更改，例如已应用的属性更改、[已保存的主题](/docs/craftforj/theme#saving-a-theme)，或[从源代码中移除的组件](/docs/craftforj/inspector#removing-a-component)。撤销已写入的更改会将文件恢复到之前的状态。如果该文件是 Java 文件，你的应用必须重新构建才能显示更改，与[应用之后](#after-you-apply)的情况相同。

<!-- TODO: add undo/redo recording -->

多次按撤销会逐项撤回更改，按时间从近到远；重做则会按更改发生的顺序重新执行。撤销后进行新更改，会清除所有可重做的操作。

### 撤销受阻时 {#when-an-undo-is-blocked}

如果 craftforJ 写入文件后，你在自己的编辑器中修改并保存了该文件，那么撤销或重做这一步会覆盖你的编辑。craftforJ 会拒绝执行，保留文件当前内容，并指出文件名：

```
Changed outside craftforJ: ContactFormView.java. Undo that edit in your editor, or skip this step.
```

你有两种处理方式：

- 在编辑器中撤销你的修改，使文件恢复为 craftforJ 写入后的状态，然后重试。
- 选择 **跳过**。这会保留文件当前内容，并从历史记录中移除该步骤。跳过不会还原任何内容，因此更早的、涉及同一文件的步骤仍可能受阻。

## 关闭此功能 {#turning-it-off}

你可以在 craftforJ 设置中关闭某个应用的 Java 写入功能，也可以通过 [`source-changes`](/docs/craftforj/configuration#feature-flags) 属性将其彻底移除。关闭后，属性编辑仍然可用，但只会在运行时生效。
