---
title: Local BLS License
sidebar_class_name: new-content
sidebar_position: 25
description: >-
  Configure a webforJ project to use a locally installed BLS certificate and
  client configuration.
_i18n_hash: 981d7333984cb25b65a90d27775eab9f
---
本地 BASIS 许可证服务 (BLS) 允许 webforJ 应用从运行在开发机器或内部网络上的服务请求许可证。当您拥有序列号和授权号码，并希望项目使用生成的本地许可证文件而不是默认许可证配置时，此设置非常有用。

使用 [startforJ](https://docs.webforj.com/startforj) 创建的 webforJ 项目在 `src/main/resources` 下包含两个许可证文件：

```
src/main/resources/blsclient.conf
src/main/resources/certificate.bls
```

以下是如何用本地 BLS 安装生成的许可证文件替换默认许可证文件的方法：

## 前提条件 {#prerequisites}

在开始之前，确保您具备：

- 可用于运行 BLS 26 安装程序的 Java 21 或 Java 25。
- 一个序列号和授权号码。
- 包含 `src/main/resources` 目录的 webforJ 项目。
- 访问将运行 BLS 的机器。

## 1. 下载 BLS 安装程序 {#1-download-bls}

要获取 BLS 安装程序，请访问 [BASIS 产品套件下载](https://basis.cloud/bbj-downloads/) 页面。一旦您选择了所需的语言，转到 **选择产品** 部分。在 **产品** 下拉菜单中，选择 `BLS`，在 **修订** 下拉菜单中选择最新版本。运行 BLS 所需的 Java 版本在 **修订** 下拉菜单下。

然后，填写 **联系信息** 中的表格，并在 **下载条款** 中选中复选框。
填写完成后，选择 `下载` 按钮以下载 BLS 安装程序 JAR。

   ![选择 BLS 作为产品的下载表单](/img/configuration/local-bls-license/download-bls.png#rounded-border)

   *选择 `BLS` 作为产品的下载表单。*

## 2. 安装和配置 BLS {#2-install-andc-onfig-bls}

下载的可执行 JAR 具有以下命名约定：`BLS<revision><date>_<time>.jar`。找到 JAR 并双击它以启动安装程序，或从命令控制台运行它：

```bash
java -jar <downloaded-bls-installer>.jar
```

按照安装程序的提示完成所需信息的填写。

默认情况下，BLS 将根据操作系统安装到特定目录，但可以在 **目录选择** 窗口中更改。往后，`<blshome>` 指代 BLS 的安装位置。

- **Windows**: `C:\bls`
- **macOS**: `/Applications/bls`
- **其他操作系统**: `/usr/local/bls`

安装完成后，BLS 将打开 **许可证注册向导**。

### 许可证注册 {#license-registration}

1. 在许可证注册向导中，选择 `检索许可证` 选项：

   ![选择检索许可证的许可证注册向导](/img/configuration/local-bls-license/retrieve-license.png#rounded-border)

   *选择 `检索许可证` 的许可证注册向导。*

2. 在接下来的窗口中，输入您的联系信息、序列号和授权号码。

3. 当您到达 **许可证注册和交付方式** 窗口时，选择 `自动注册和安装许可证`。

注册许可证后，根据需要完成本地 BLS 的配置。如果以后需要更改 BLS 设置或检索另一许可证，请使用位于 `<blshome>/bin/BLSAdmin` 的 BLS 管理工具。

## 3. 复制生成的许可证文件 {#3-copy-the-generated-license-files}

现在，进入 `<blshome>/cfg` 目录，找到生成的许可证文件 `blsclient.conf` 和 `certificate.bls`：

![包含生成的客户端配置和证书的 BLS cfg 文件夹](/img/configuration/local-bls-license/bls-cfg-folder.png#rounded-border)

*包含生成的客户端配置和证书的 BLS 安装 `cfg` 文件夹。*

将 `blsclient.conf` 和 `certificate.bls` 复制到您的 webforJ 项目中，并替换资源目录中任何同名的现有文件。现在，当您的本地 BLS 正在运行时，您的 webforJ 应用将从该服务请求许可证。

```
src
├───main
│   ├───java/
│   └───resources
│       ├───icons/
│       ├───static/
│       ├───application.properties
│       ├───banner.txt
// highlight-next-line
│       ├───blsclient.conf
// highlight-next-line
│       └───certificate.bls
```

:::tip
如果您的许可证文件位于默认 webforJ 配置目录之外，您可以通过 [`webforj.license.cfg`](./properties#configuration-options) 配置许可证目录。
:::
