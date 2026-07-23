---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: c6cfdc6b07db675d741ea4a096f286ca
---
# 本地安装

本节文档将涵盖仅针对希望在其机器上使用本地 BBj 实例进行 web 和/或应用开发的用户所需的步骤。此安装将不允许用户对 webforJ 基础代码本身进行贡献。
<br/>

:::info
此指引将涵盖在 Windows 系统上的安装 - 安装步骤可能会因 Mac/Linux 操作系统设备而异。
:::
<br/>

安装将分为以下步骤：

1. 下载和安装 BBj
2. 使用 BBj 插件管理器创建您的应用
3. 启动您的应用

:::tip 先决条件
在您开始之前，请确保您已查看设置和使用 webforJ 所需的 [先决条件](../../introduction/prerequisites)。这确保您在开始项目之前具备所有必需的工具和配置。
:::

## 1. 下载和安装 BBj {#1-bbj-download-and-installation}

<b>在执行此步骤时，请确保安装与所使用的 webforJ 版本相对应的 BBj 版本。</b><br/><br/>

[此视频](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) 可帮助您进行 BBj 安装，如果您需要设置帮助。BASIS 网站上的安装部分可以在 [此链接](https://basis.cloud/download-product) 找到。

:::tip
建议使用最新的稳定版本 BBj，并从选项列表中选择“BBj”，而不选择 Barista 或 Addon。
:::

<a name='section3'></a>

## 2. 安装并配置 webforJ 插件

安装 BBj 后，可以访问插件管理器以安装配置 webforJ 所需的工具。首先，在开始菜单或 Finder 中输入“插件管理器”。

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

打开插件管理器后，导航到顶部的“可用插件”标签。

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

进入此部分后，选择“显示正在开发的版本”复选框。

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

DWCJ 项目现在应在可下载插件列表中可见。单击此列表中的条目以选择它。

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

选择 DWCJ 条目后，单击“安装”按钮。

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

插件安装完成后，单击顶部的“已安装插件”标签。

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

此标签显示已安装的插件，现在应包括 DWCJ 条目。单击列表中的条目。

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

选择 DWCJ 条目后，单击“配置”按钮。

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

在打开的窗口中，单击窗口左下角的“启用 Maven 远程安装”按钮。

:::tip
另外，导航到 `bbx` 文件夹内的 `bin` 目录并运行以下命令：

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

应会显示一个对话框，指示已启用远程安装。单击“确定”关闭此对话框。

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

## 3. 使用启动项目
一旦安装并配置了 BBj 及所需的 webforJ 插件，您可以从命令行创建一个新的搭建项目。该项目包含运行第一个 webforJ 程序所需的工具。

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. 启动应用

完成此操作后，在项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问您的应用。要查看应用，您需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您与 Docker 配置的主机端口，将 `YourPublishName` 替换为 POM 的 `<publishname>` 标签中的文本。
如果操作正确，您应能看到您的应用呈现。
