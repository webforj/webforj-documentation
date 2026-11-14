---
title: 应用更改
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c421d594499c29fdae8dd88ca6e35e3d
---
在 craftforJ 中更改属性会立即影响正在运行的应用，除此之外不会做任何更改。若要保留更改，请先检查，再将其写入对应的 Java 文件。本页将介绍这一步骤。

<VideoPlayer video="craftforj/apply-changes" width={1920} height={992} title="Apply changes" />

:::warning craftforJ 会写入你的项目
请将工作纳入版本控制。应用更改前先查看差异，提交前再查看一次。
:::

## 待处理的更改 {#pending-changes}

你更改的每个属性都会记录为待处理的更改，craftforJ 会显示当前有多少项更改在等待处理。即使重新加载页面或切换路由，待处理的更改也会保留，因为 craftforJ 会在组件重建时重新应用这些更改。

## 检查并应用 {#reviewing-and-applying}

按 <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> 打开检查视图。更改会按将要写入的文件分组。每项更改都会显示属性的旧值和新值，展开后可查看文件差异。如果某项更改会将计算所得的值替换为固定值，craftforJ 会发出警告，并指出即将被替换的表达式。在你应用更改之前，不会写入任何内容。应用之前，你可以单独还原或丢弃每项更改。

![按文件分组的更改检查视图，其中一项已展开以显示差异](/img/craftforj/source-changes/review.png#rounded-border)

## 选择更改的写入位置 {#choosing-where-a-change-is-written}

更改的写入位置决定了它会影响的范围。如果组件直接在视图中构建，更改就会写入该视图。如果组件在可复用类中构建，则有两个选项：

- **使用位置** - 使用该组件的位置，只会更改当前屏幕。这是默认选项。
- **定义位置** - 构建该组件的位置，会更改所有使用该组件的屏幕。

每项待处理的更改都会显示适用的选项，并允许你在两者之间切换。某些属性只能写入定义位置，因为它们由组件自行设置，而不是由调用方传入。craftforJ 会在应用前标记这些属性。

## 应用之后 {#after-you-apply}

写入 Java 文件会导致应用重新构建并重启。craftforJ 会报告重启状态、等待重启完成，然后重新连接，同时保留你的选中项和剩余的待处理更改。应用的更改写入文件后，就会从待处理列表中移除。

只有在这一步，重新加载设置才会起作用。craftforJ 不需要实时重新加载也能工作，因为你在检查期间所做的所有更改都会立即在运行中的应用里生效，无需重新构建。写入源代码则不同：它会修改应用构建所依据的文件，因此应用必须重新构建，才能让更改来自你的代码，而不是 craftforJ。如果配置了[实时重新加载](/docs/configuration/deploy-reload/overview)，重建会自动进行。否则，请自行重启应用。

## 撤销和重做 <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

在 craftforJ 中按 <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> 撤销上一次更改，按 <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd> 重做。

撤销适用于你在运行中的应用里所做的更改，从[属性编辑](/docs/craftforj/inspector#reading-and-changing-properties)到[主题调整](/docs/craftforj/theme#adjusting-a-theme)都可以撤销。它也适用于 craftforJ 写入项目的更改，例如已应用的属性更改、[已保存的主题](/docs/craftforj/theme#saving-a-theme)，或[从源代码中移除的组件](/docs/craftforj/inspector#removing-a-component)。撤销已写入的更改会将文件恢复到之前的状态。如果该文件是 Java 文件，应用必须重新构建后才能显示更改，与[应用更改之后](#after-you-apply)的情况相同。

<!-- TODO: add undo/redo recording -->

多次按撤销会逐项还原更改，顺序从最近到最早；重做则按更改的原始顺序逐项重放。在撤销后进行新更改，会清除所有可重做的更改。

### 撤销被阻止时 {#when-an-undo-is-blocked}

如果 craftforJ 写入文件后，你又在自己的编辑器中修改并保存了该文件，那么撤销或重做这一步会覆盖你的修改。craftforJ 会拒绝执行操作，保留文件原样，并指出文件名：

```
文件已在 craftforJ 外部更改：ContactFormView.java。请在编辑器中撤销该修改，或跳过此步骤。
```

你有两种处理方式：

- 在编辑器中撤销你的修改，使文件与 craftforJ 写入的内容一致，然后重试。
- 选择 **跳过**。文件会保持原样，并从历史记录中移除该步骤。跳过不会还原任何内容，因此更早的、涉及同一文件的步骤仍可能被阻止。

## 关闭此功能 {#turning-it-off}

你可以在 craftforJ 设置中关闭某个应用的 Java 写入功能，也可以使用 [`source-changes`](/docs/craftforj/configuration#feature-flags) 属性将其彻底移除。无论采用哪种方式，属性编辑仍可使用，但更改只会即时生效。
