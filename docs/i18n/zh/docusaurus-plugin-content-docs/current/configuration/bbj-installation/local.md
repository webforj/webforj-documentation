---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: 5fbe602fa83b6147a07c4e2ad8ac3230
---
# 本地安装

:::danger 已弃用且将被移除
此页面所述的 BBj 插件管理器的 webforJ 插件已弃用，并将在未来的版本中移除。请勿使用它开始新项目。将现有项目迁移到 [BBjShip](/docs/configuration/bbjship/overview)，该工具在 BBjServices 中安装、部署和运行 webforJ 应用程序。[现有项目](/docs/configuration/bbjship/existing-projects) 列出了项目所需的更改。
:::

本节文档将涵盖仅希望在其机器上使用本地 BBj 实例进行 web 和/或应用开发的用户所需的步骤。此安装将不允许用户对 webforJ 基础代码本身做出贡献。
<br/>

:::info
此指南将涵盖在 Windows 系统上的安装 - 安装步骤可能会因 Mac/Linux 操作系统设备而异。
:::
<br/>

安装将分为以下步骤：

1. BBj 下载和安装
2. 使用 BBj 插件管理器创建您的应用程序
3. 启动您的应用程序

:::tip 先决条件
在开始之前，请确保您已查看了设置和使用 webforJ 所需的 [先决条件](../../introduction/prerequisites)。这确保您在开始项目之前拥有所有必需的工具和配置。
:::


## 1. BBj 下载和安装 {#1-bbj-download-and-installation}

<b>在执行此步骤时，请确保安装与相同 webforJ 版本相对应的 BBj 版本。</b><br/><br/>

[此视频](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) 可以帮助您安装 BBj，如果您在设置方面需要帮助，可以查看 BASIS 网站上的安装部分 [此链接](https://basis.cloud/download-product)。

:::tip
建议使用最新的稳定版本的 BBj，并从选项列表中选择“BBj”，没有 Barista 或 Addon。
:::

<a name='section3'></a>

## 2. 安装和配置 webforJ 插件

安装 BBj 后，可以访问插件管理器来安装配置 webforJ 所需的工具。要开始，您可以在开始菜单或 Finder 中输入“插件管理器”。

打开插件管理器后，导航到顶部的“可用插件”选项卡。

![插件管理器配置](/img/bbj-installation/local/Step_1l.png#rounded-border)

进入此部分后，选择“显示开发中的版本”复选框。

![插件管理器配置](/img/bbj-installation/local/Step_2l.png#rounded-border)

现在，DWCJ 条目应该在可下载插件列表中可见。在列表中单击此条目以选择它。

![插件管理器配置](/img/bbj-installation/local/Step_3l.png#rounded-border)

选择 DWCJ 条目后，单击“安装”按钮。

![插件管理器配置](/img/bbj-installation/local/Step_4l.png#rounded-border)

插件安装完成后，单击顶部的“已安装插件”选项卡。

![插件管理器配置](/img/bbj-installation/local/Step_5l.png#rounded-border)

此选项卡显示已安装的插件，其中现在应该包括 DWCJ 条目。在列表中单击该条目。

![插件管理器配置](/img/bbj-installation/local/Step_6l.png#rounded-border)

选择 DWCJ 条目后，单击“配置”按钮。

![插件管理器配置](/img/bbj-installation/local/Step_7l.png#rounded-border)

在打开的窗口中，单击窗口左下角的“启用 Maven 远程安装”按钮。

![插件管理器配置](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip

或者，导航到您的 `bbx` 文件夹中的 `bin` 目录，并运行以下命令：

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

应会显示一个对话框，表示已启用远程安装。单击“确定”以关闭此对话框。

![插件管理器配置](/img/bbj-installation/local/Step_9l.png#rounded-border)


## 3. 使用启动项目
一旦安装并配置了 BBj 和所需的 webforJ 插件，您可以从命令行创建一个新的、脚手架项目。该项目附带了运行您的第一个 webforJ 程序所需的工具。

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. 启动应用程序

完成此操作后，在您的项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问您的应用程序。要查看应用程序，您需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您在 Docker 中配置的主机端口，将 `YourPublishName` 替换为 POM 中的 `<publishname>` 标签内的文本。
如果一切正确，您应该能看到您的应用程序渲染。
