---
sidebar_position: 4
_i18n_hash: a41d592f84a4dcd32f5398f3e57621a4
---
# 本地安装

本节文档将涵盖仅适用于希望在其机器上使用本地 BBj 实例进行 web 和/或应用开发的用户所需的步骤。此安装将不允许用户贡献于 webforJ 基础代码本身。
<br/>

:::info
本指南将涵盖在 Windows 系统上的安装 - 安装步骤可能在 Mac/Linux 操作系统设备上有所不同。
:::
<br/>

安装将分为以下步骤：

1. BBj 下载和安装
2. 使用 BBj 插件管理器创建您的应用
3. 启动您的应用


:::tip 前提条件
在开始之前，请确保您已查看设置和使用 webforJ 的必要 [前提条件](../../introduction/prerequisites)。这确保您在开始项目之前拥有所有必需的工具和配置。
:::


## 1. BBj 下载和安装 {#1-bbj-download-and-installation}

<b>在执行此步骤时，请确保您安装与相同 webforJ 版本对应的 BBj 版本。</b><br/><br/>

[此视频](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope)可以帮助您安装 BBj，如果您需要设置方面的帮助，您可以在 BASIS 网站的安装部分找到 [该链接](https://basis.cloud/download-product)

:::tip
建议使用最新的稳定版本构建的 BBj，并从选项列表中选择“BBj”，而不选择 Barista 或 Addon。
:::


<a name='section3'></a>

## 2. 安装和配置 webforJ 插件

一旦安装了 BBj，可以访问插件管理器安装配置 webforJ 所需的工具。首先，在开始菜单或 Finder 中输入“插件管理器”。

![插件管理器配置](/img/bbj-installation/local/Step_1l.png#rounded-border)

打开插件管理器后，导航到顶部的“可用插件”选项卡。

一旦进入此部分，选中“显示开发中的版本”复选框。

![插件管理器配置](/img/bbj-installation/local/Step_2l.png#rounded-border)

DWCJ 条目现在应在可下载的插件列表中可见。点击列表中的该条目进行选择。

![插件管理器配置](/img/bbj-installation/local/Step_3l.png#rounded-border)

选中 DWCJ 条目后，点击“安装”按钮。

![插件管理器配置](/img/bbj-installation/local/Step_4l.png#rounded-border)

插件安装完成后，点击顶部的“已安装插件”选项卡。

![插件管理器配置](/img/bbj-installation/local/Step_5l.png#rounded-border)

此选项卡显示已安装的插件，此时应包括 DWCJ 条目。点击列表中的该条目。

![插件管理器配置](/img/bbj-installation/local/Step_6l.png#rounded-border)

选中 DWCJ 条目后，点击“配置”按钮。

![插件管理器配置](/img/bbj-installation/local/Step_7l.png#rounded-border)

在打开的窗口中，点击窗口左下角的“启用 Maven 远程安装”按钮。

![插件管理器配置](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip 

或者，导航到您的 `bbx` 文件夹中的 `bin` 目录，并运行以下命令：

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

将显示一个对话框，表明远程安装已启用。点击“确定”以关闭此对话框。

![插件管理器配置](/img/bbj-installation/local/Step_9l.png#rounded-border)


## 3. 使用启动项目

安装并配置好 BBj 和所需的 webforJ 插件后，您可以通过命令行创建一个新的、脚手架项目。此项目带有运行您的第一个 webforJ 程序所需的工具。

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. 启动应用

完成后，在您的项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许
您访问您的应用。要查看应用，您需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您使用 Docker 配置的主机端口，将 `YourPublishName` 替换为 POM 中 `<publishname>` 标签内的文本。
如果操作正确，您应该可以看到您的应用被渲染。
