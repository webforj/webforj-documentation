---
sidebar_position: 2
title: Github Codespaces
description: >-
  Run the webforj-hello-world starter in a GitHub Codespace to develop and
  preview webforJ apps directly from the browser.
_i18n_hash: ffbe6dd8d2c6c81e95e7e97dbb8ff32e
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) 已配置为在 Github Codespaces 上运行。Codespaces 是一个基于云的开发环境，允许您直接在浏览器中开发和运行 webforJ 应用程序。要开始使用此工具，请按照以下步骤操作：

## 1. 导航至 HelloWorldJava 仓库 {#1-navigate-to-the-helloworldjava-repository}

首先，您需要访问 HelloWorldJava 仓库，可以在 [此链接](https://github.com/webforj/webforj-hello-world) 找到。进入后，点击绿色的 **"Use this template"** 按钮，然后选择 **"Open in a codespace"** 选项。

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. 运行您的程序 {#2-running-your-program}

在等待 codespace 加载后，您应该会看到浏览器版本的 VS Studio Code 打开，并加载 "HelloWorldJava" 示例程序。在这里，您可以运行示例程序，或开始开发。

要编译程序，请在 VS Code 中打开终端并运行 `mvn install` 命令。

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

如果一切顺利完成，您应该会看到 `BUILD SUCCESS` 消息。

:::warning WARNING
请确保使用 `mvn install` 命令，而不是 VS Code 内置的 Maven 界面来安装您的程序。
:::

完成此操作后，您需要导航到特定的网址以查看您的程序。为此，首先点击 VS Code 底部的 **"Ports"** 选项卡。在这里，您将看到列出的两个端口，8888 和另一个端口。

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

在 **Ports** 选项卡的 **"Local Address"** 部分，点击小型 **"Open in Browser"** 按钮，形状像一个地球仪，这将会在您的浏览器中打开一个新标签。

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

当浏览器标签打开后，您需要在网址末尾添加内容以确保运行您的应用程序。首先，添加 `/webapp` 到网址的末尾，该网址将以 `github.dev` 结束。之后，添加正确的应用程序和类名（如果适用），以显示所需的应用程序。要了解如何正确配置 URL，请 [查看本指南](./configuration)。

:::success Tip
如果您想运行默认的 "Hello World" 程序，只需在 URL 的 `/webapp` 后添加 `/hworld`：
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::

完成后，您应该会看到您的应用程序在浏览器中运行，并可以在运行于 codespaces 中的 VS Code 实例中对其进行修改。
