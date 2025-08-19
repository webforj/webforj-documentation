---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: 05f50845efd34767431faacb2e5dc97e
---
import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) 已配置为在 Github Codespaces 中运行。Codespaces 是一个基于云的开发环境，允许您直接在浏览器中开发和运行 webforJ 应用程序。要开始使用此工具进行开发，请按照以下步骤操作：

## 1. 导航到 HelloWorldJava 仓库 {#1-navigate-to-the-helloworldjava-repository}

首先，您需要访问 HelloWorldJava 仓库，可以在 [此链接](https://github.com/webforj/webforj-hello-world) 中找到。进入后，点击绿色的 **“使用此模板”** 按钮，然后选择 **“在 codespace 中打开”** 选项。

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. 运行您的程序 {#2-running-your-program}

等待 codespace 加载后，您应该会看到浏览器版本的 VS Studio Code 打开，加载了 "HelloWorldJava" 示例程序。在这里，您可以运行示例程序或开始开发。

要编译程序，请在 VS Code 中打开终端并运行 `mvn install` 命令。

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

如果一切成功完成，您应该会看到 `BUILD SUCCESS` 消息。

:::warning 警告 
确保使用 `mvn install` 命令，而不是 VS Code 内置的 Maven 界面来安装您的程序。
:::

完成后，您需要导航到一个特定的网页地址以查看您的程序。为此，首先在 VS Code 底部点击 **“端口”** 选项卡。在这里，您将看到两个端口 8888 和另一个端口的列表。

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

在 **端口** 选项卡的 **“本地地址”** 部分，点击小的 **“在浏览器中打开”** 按钮（类似地球的图标），这将会在您的浏览器中打开一个新标签。

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

当浏览器标签打开时，您需要在 URL 的末尾添加内容以确保您的应用程序被运行。首先，在网页地址末尾添加 `/webapp`，其将以 `github.dev` 结尾。然后，添加正确的应用程序和类名（如果适用），以显示所需的应用程序。要查看如何正确配置 URL，请 [遵循本指南](./configuration)。

:::success 提示
如果您想运行默认的 "Hello World" 程序，只需在 URL 的 `/webapp` 后添加 `/hworld`：
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::


一旦完成，您应该会看到您的应用程序在浏览器中运行，并可以在运行于 codespaces 中的 VS Code 实例中对其进行修改。
