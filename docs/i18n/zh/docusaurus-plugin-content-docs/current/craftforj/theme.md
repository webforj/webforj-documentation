---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: 98545075c2ac2777380812af08d71345
---
主题选项卡允许您在应用运行时更改其外观。它与您的应用已经使用的[DWC设计令牌](/docs/styling/css-variables)协同工作，因此一次更改会影响读取该令牌的每个组件，而不是一次影响一个规则。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4" type="video/mp4" />
  </video>
</div>

## 调整主题 {#adjusting-a-theme}

控件根据它们影响的内容进行分组，覆盖应用构建所基于的调色板、其背后的表面、边缘和角的形状、排版以及间距。每个控件都会解释其作用，因为其中一些会影响应用的可读性，而不仅仅是外观。

主题有明亮的一面和黑暗的一面。您可以同时或单独对两个进行编辑，并在它们之间切换，以查看您正在处理的那一面。预览显示了调色板、表面、字体样本和状态颜色，您可以在保存之前发现一个在一个屏幕上起效而在另一个屏幕上不起效的组合。

![主题控件旁边的预览](/img/craftforj/theme/knob-rail.png#rounded-border)

## 保存主题 {#saving-a-theme}

您正在处理的主题已应用于应用程序，但尚未成为您项目的一部分，重新加载页面会丢弃它。保存将其写入应用程序的样式表，使其在重启后依然存在，显示在您的差异中，并与您的应用一起发布。

craftforJ写入单个样式表，系统会自行检测或您在craftforJ设置中命名。如果该文件已经包含一个主题，则保存将整体替换它，而不是在其上层叠加第二个主题，并且craftforJ会先要求您确认。如果在craftforJ读取后该文件发生了变化，则不会写入任何内容，craftforJ会要求您再次保存。

您可以将主题恢复到上次保存的状态，或从样式表中完全删除它，而不影响文件中的其他内容。

## 预设主题 {#preset-themes}

除了默认的外观和感觉外，craftforJ还有多个主题预设供选择。以下是主题App Default与Portico之间的比较。

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![应用程序使用App Default主题](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![应用程序使用Portico主题](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## 关闭功能 {#turning-it-off}

您可以在craftforJ设置中关闭应用的样式保存，或通过[`stylesheet-changes`](/docs/craftforj/configuration#feature-flags)属性将其完全删除。无论哪种关闭，选项卡仍然可以工作，并仍然重绘正在运行的应用，但您无法保存结果。
