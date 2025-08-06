---
sidebar_position: 4
_i18n_hash: 9cf48c2124910b9a10a8ec4c5cd82205
---
# 本地安装

本文档的这一部分将介绍仅对希望在其机器上使用本地 BBj 实例进行 web 和/或应用开发的用户所需的步骤。此安装将不允许用户对 webforJ 基础代码本身进行贡献。
<br/>

:::info
本指南将涵盖 Windows 系统上的安装 - 安装步骤可能会因 Mac/Linux 操作系统设备而有所不同。
:::
<br/>

安装将分为以下步骤：


1. BBj 下载和安装
2. 使用 BBj 插件管理器创建您的应用
3. 启动您的应用


:::tip 先决条件
在开始之前，请确保您已查看设置和使用 webforJ 所需的 [先决条件](../../introduction/prerequisites)。这样可以确保您在开始项目之前拥有所有必要的工具和配置。
:::


## 1. BBj 下载和安装 {#1-bbj-download-and-installation}

<b>在执行此步骤时，请确保您安装与相同 webforJ 版本对应的 BBj 版本。</b><br/><br/>

[此视频](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) 可以为您提供安装 BBj 时的帮助。如果您需要设置协助，可以在 BASIS 网站的安装部分找到[此链接](https://basis.cloud/download-product)。

:::tip
建议使用最新的稳定版本的 BBj，并从选项列表中选择 "BBj"，而不选择 Barista 或 Addon。
:::


<a name='section3'></a>

## 2. 安装和配置 webforJ 插件

安装 BBj 后，可以访问插件管理器以安装配置 webforJ 所需的工具。首先，在开始菜单或 Finder 中输入 "Plugin Manager"。

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

打开插件管理器后，导航到顶部的 "可用插件" 选项卡。

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

在此部分，选中 "显示正在开发的版本" 复选框。

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

DWCJ 条目现在应在可下载的插件列表中可见。单击该列表中的此条目以选择它。

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

选中 DWCJ 条目后，单击 "安装" 按钮。

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

插件安装完成后，单击顶部的 "已安装插件" 选项卡。

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

此选项卡显示已安装的插件，现在应包括 DWCJ 条目。单击列表中的该条目。

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

选择 DWCJ 条目后，单击 "配置" 按钮。

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

在打开的窗口中，单击窗口左下角的 "启用 Maven 远程安装" 按钮。

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

或者，导航到 `bbx` 文件夹中的 `bin` 目录并运行以下命令：

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

应该显示一个对话框，指示已启用远程安装。单击 "确定" 关闭该对话框。

## 3. 使用起始项目
一旦安装并配置了 BBj 和所需的 webforJ 插件，您可以从命令行创建一个新的骨架项目。此项目配备了运行您的第一个 webforJ 程序所需的工具。

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. 启动应用

完成上述步骤后，在您的项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问您的应用。要查看应用，您将需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您用 Docker 配置的主机端口，而 `YourPublishName` 则由 POM 中 `<publishname>` 标签内的文本替换。
如果操作正确，您应该看到您的应用呈现。
