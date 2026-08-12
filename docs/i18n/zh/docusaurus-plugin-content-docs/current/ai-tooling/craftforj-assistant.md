---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: >-
  A coding agent inside your running webforJ app that writes Java freely,
  compiles it, and applies it with your approval.
_i18n_hash: 2c2a04b29b7b6de57e5689628cd659d0
---
craftforJ助手是一个在您的**运行应用**中工作的编码代理。它自由地编写Java，在您看到之前编译所写的代码，应用更改，并在您的应用重启后继续工作。它与webforJ一起提供，作为[craftforJ](/docs/craftforj)的一部分，这是一个开发环境，让您在应用运行时获得组件树、路由、实时属性和主题。

## 两者的比较 {#how-the-two-compare}

| | [webforJ AI插件](/docs/ai-tooling) | craftforJ助手 |
|---|---|---|
| **生活在** | 您的编辑器 | 运行中的应用 |
| **读取** | 您的源文件 | 您的应用，实时，带有真实值 |
| **执行** | 编写代码 | 编写代码，并检查、修改、导航和主题化运行中的应用 |
| **验证** | 您的下一次构建 | 在您看到之前编译每次编辑，然后向您显示结果运行 |
| **适合** | 从零开始构建新事物 | 理解、修复、构建和原型制作您面前的应用 |

这两者是互补的，可以互相交接工作。一旦工作超出craftforJ的范围，您可以将[craftforJ会话交接](/docs/craftforj/ai#conversations)到您的编辑器。

## 它能做什么 {#what-it-can-do}

您给代理一个目标而不是命令。它会计划、检查所需的一切、行动、检查结果，并在单个回合中多次纠正自己。

它自由地编写Java，因此不限于您手动进行的属性更改。每次编辑都是暂存的，而不是写入磁盘，发送到真实的Java编译器，并由代理根据返回的诊断进行纠正，因此到达您审查的内容已经与您的运行应用相兼容。应用它会重启应用，而代理在其重新启动后会再次开始其计划。

此外，它访问craftforJ所知道的一切：实时组件树和真实属性值、您的Java源、路由表和路由访问规则、主题和样式表、页面本身的CSS和脚本、组件的屏幕截图，以及内置的webforJ知识库和`--dwc-*`令牌工具。有关详细信息，请参见[AI助手](/docs/craftforj/ai)。

## 配置模型 {#configuring-a-model}

craftforJ自身不提供模型，因此您选择运行它的模型。为支持的提供者添加API密钥，或者将craftforJ指向运行在本地的Ollama模型。您的密钥存储在运行应用的机器上，仅在页面打开时保存在浏览器中，助手通过浏览器与您的提供者进行通信，而不是通过您的服务器。有关更多信息，请参见[配置模型](/docs/craftforj/ai#configuring-a-model)。

:::warning AI可能仍然会出错
与运行应用进行互动并编译其自己的输出使得该代理的准确性远高于盲目编写的代理。但它仍然可能出错。在您保留之前，请检查它所做的工作。
:::

## 入门 {#getting-started}

craftforJ在您开启之前是禁用的，并且只能在开发中运行：

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

使用<kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>打开craftforJ，并切换到AI助手选项卡。有关完整设置，请参见[入门](/docs/craftforj/getting-started)。
