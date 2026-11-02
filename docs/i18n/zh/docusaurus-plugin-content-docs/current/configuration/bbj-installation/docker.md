---
sidebar_position: 1
title: Docker
description: >-
  Install Docker Desktop on Windows or Mac, pull the webforj/sandbox image, and
  run BBjServices in a container for webforJ development.
_i18n_hash: 6c644b1d4f54cfa22e3b978714a5849e
---
# Docker 安装

:::danger 已弃用且将被移除
使用 `webforj/sandbox` 镜像的 Docker 安装已被弃用，并将在未来的版本中移除。请勿使用它启动新项目。将现有项目迁移到 [BBjShip](/docs/configuration/bbjship/overview)，它可以在 BBjServices 中安装、部署和运行 webforJ 应用。 [现有项目](/docs/configuration/bbjship/existing-projects) 列出了项目需要的更改。
:::

本部分文档将覆盖希望使用 Docker 进行开发的用户所需的步骤。您的代码将会在开发机器上进行更改，最终生成的应用将会在 Docker 中运行。

## 1. 下载 Docker {#1-downloading-docker}

Docker 的安装过程在 Windows、Mac 和 Linux 用户之间略有不同。请查看与您操作系统对应的部分。

### Windows {#windows}

:::info
建议下载最新版本的 Windows Subsystem for Linux。更多信息请访问 [此链接](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. 下载 Docker Desktop：**
>- 访问 Docker Desktop for Windows 的下载页面：[Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- 点击 “Get Docker Desktop for Windows” 按钮下载安装程序。

**2. 安装 Docker Desktop：**
>- 运行下载的安装程序。
>- 按照安装向导的指示操作，并确保启用 Hyper-V（如果提示）因为 Windows 上的 Docker 使用 Hyper-V 进行虚拟化。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装：**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已安装并正常工作。

### Mac {#mac}

**1. 下载 Docker Desktop：**
>- 访问 Docker Desktop for Mac 的下载页面：[Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. 安装 Docker Desktop：**
>- 运行下载的安装程序。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装：**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已安装并正常工作。

## 2. 配置 {#2-configuration}

下载 Docker Desktop 后，搜索最新的 webforJ 镜像，目前名称为 `webforj/sandbox`。

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_1l.png#rounded-border)

点击标签列表以查看可用选项

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_2l.png#rounded-border)

选择“rc”以获取最新构建

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_3l.png#rounded-border)

拉取镜像以启动容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_4l.png#rounded-border)

下载完成后，点击运行按钮，打开配置设置

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_5l.png#rounded-border)

打开“可选设置”菜单

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_6l.png#rounded-border)

选择所需的主机端口，以便您可以在 Docker 中查看运行的应用

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_7l.png#rounded-border)

点击“运行”以启动容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success 注意
请务必记下您提供的自定义主机端口号，因为这将在后续操作中使用。
:::

## 3. 运行您的应用 {#3-running-your-app}

创建容器后，可以在容器内运行 webforJ 应用，而不是在本地运行。首先，您需要正确配置项目的 POM 文件。完成后，访问浏览器中的特定 URL 即可查看应用。

### 配置您的 POM 文件 {#configuring-your-pom-file}

在 Docker 容器中运行 webforJ 项目需要使用 webforJ 安装插件，可以通过您的 POM 文件进行配置：

在 POM 的 `<plugins>` 部分创建一个新的 `<plugin>` 条目。以下代码展示了可以作为起始条目使用并根据需要调整的内容：

:::important
如果您的 POM 文件没有 `<plugins>` 部分，请创建一个。
:::

```xml
<plugin>
<groupId>com.webforj</groupId>
<artifactId>webforj-install-maven-plugin</artifactId>
<version>${webforj.version}</version>
<executions>
  <execution>
  <goals>
    <goal>install</goal>
  </goals>
  </execution>
</executions>
<configuration>
  <deployurl>http://localhost:8888/webforj-install</deployurl>
  <classname>samples.HelloWorldApp</classname>
  <publishname>hello-world</publishname>
  <debug>true</debug>
</configuration>
</plugin>
```

创建类似于上述的条目后，自定义以下信息：

- 将 `<deployurl>` 条目更改为使用与您在上一步中为容器配置的 **主机端口** 相匹配的端口号。

- 确保 `<classname>` 条目与您要运行的应用的名称匹配。

- 如果您的 BBj 安装的 `<username>` 和 `<password>` 凭据不同，请相应更改。

### 使用启动项目 {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### 启动应用 {#launching-the-app}

完成以上步骤后，在项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问应用。要查看应用，您需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您在 Docker 中配置的主机端口，`YourPublishName` 替换为 POM 中 `<publishname>` 标签内的文本。
如果处理正确，您应该能看到应用渲染。
