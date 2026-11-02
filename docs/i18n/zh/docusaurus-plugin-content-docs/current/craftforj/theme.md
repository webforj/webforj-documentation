---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: c7dd349ebf7570b2e34953fce990173d
---
主题选项卡允许您在应用运行时更改其外观。它与您的应用已经使用的[DWC设计令牌](/docs/styling/css-variables)协同工作，因此单个更改会影响每个读取该令牌的组件，而不是一次只影响一个规则。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## 调整主题 {#adjusting-a-theme}

控制项按其影响的内容分组，涵盖构建应用的调色板、背景表面、边缘和角落的形状、排版以及间距。每个控制项都解释其作用，因为其中一些会改变应用的可读性，而不仅仅是外观。

主题有明亮的一面和黑暗的一面。您可以同时对两者进行编辑，或者只对其中一个进行编辑，并在它们之间切换，以查看您正在处理的那一面。预览显示调色板、表面、字体样本和状态颜色，这样您可以在保存之前发现某个组合在一个屏幕上有效而在另一个屏幕上无效。

![主题控制与预览在一起](/img/craftforj/theme/knob-rail.png#rounded-border)

## 保存主题 {#saving-a-theme}

您正在处理的主题已应用于应用程序，但尚未成为您项目的一部分，重新加载页面将丢弃它。保存会将其写入应用的样式表，使其在重启后还保留、显示在您的差异中，并随应用一起发布。

craftforJ写入单个样式表，该样式表由其自行检测或您在craftforJ设置中命名。如果该文件已保存主题，保存操作将完全替换它，而不是在其上叠加第二个主题，并且craftforJ会首先要求您确认。如果在craftforJ读取后文件已更改，则不会写入任何内容，并且craftforJ会让您再次保存。

您可以将主题恢复到上一次保存的状态，或者完全从样式表中删除它，而不会影响文件中的其他内容。

## 预设主题 {#preset-themes}

除了默认的外观和感觉，craftforJ还有几个可供选择的主题预设。以下显示了应用默认主题与Portico主题之间的比较。

<Tabs>
  <TabItem value="app-default" label="应用默认">
    ![使用应用默认主题的应用](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![使用Portico主题的应用](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## 关闭它 {#turning-it-off}

您可以在craftforJ设置中关闭样式保存，或通过[`stylesheet-changes`](/docs/craftforj/configuration#feature-flags)属性完全删除它。无论哪种情况，选项卡仍然有效并重新绘制正在运行的应用，但您无法保存结果。
