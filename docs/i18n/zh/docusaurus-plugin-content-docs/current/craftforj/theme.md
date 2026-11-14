---
title: 主题
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: fe089afe4f44e457a98622707bf995e5
---
“主题”选项卡可让你在应用运行时更改其外观。它与应用已经使用的 [DWC 设计令牌](/docs/styling/css-variables)配合使用，因此，只需更改一次，就能影响所有读取该令牌的组件，而不必逐条修改规则。

<VideoPlayer video="craftforj/theme-knobs" width={1920} height={992} title="Theme knobs" />

## 调整主题 {#adjusting-a-theme}

这些控件按影响范围分组，涵盖构成应用的调色板、背景表面、边缘和圆角形状、字体排版以及间距。每个控件都会说明其作用，因为有些控件改变的不只是应用的外观，还会影响其可读性。

主题分为浅色和深色两种模式。你可以将更改应用到两种模式，也可以只应用到其中一种，还可以在两种模式间切换，查看当前正在调整的模式。预览会同时展示调色板、背景表面、字体样例和状态颜色，让你在保存前就能发现某种搭配可能适用于一个屏幕，却不适用于另一个屏幕。

![主题控件与预览并排显示](/img/craftforj/theme/knob-rail.png#rounded-border)

## 保存主题 {#saving-a-theme}

你正在编辑的主题会应用到应用中，但尚未写入项目，重新加载页面后便会丢失。保存后，主题会写入应用的样式表，在应用重启后仍然保留、显示在差异中，并随应用一起发布。

craftforJ 会写入单个样式表：它可以自动检测该样式表，也可以由你在 craftforJ 设置中指定。如果该文件中已包含主题，保存时会整体替换原有主题，而不会叠加第二个主题；craftforJ 会先征求你的确认。如果该文件在 craftforJ 读取后发生了更改，则不会写入任何内容，并会提示你重新保存。

你可以将主题还原到上次保存的状态，也可以将其从样式表中完全移除，而不影响文件中的其他内容。

## 预设主题 {#preset-themes}

除了默认外观，craftforJ 还提供了多个主题预设供你选择。下方对比了 App Default 和 Portico 两种主题。

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![使用 App Default 主题的应用](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![使用 Portico 主题的应用](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## 关闭主题功能 {#turning-it-off}

你可以在 craftforJ 设置中关闭应用的样式保存功能，也可以通过 [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) 属性将其完全移除。无论采用哪种方式，选项卡仍然可以使用，也仍会重新渲染正在运行的应用，但你无法保存更改结果。
