---
sidebar_position: 1
title: Docker
_i18n_hash: 642936b8f7fd836ca4510eab19087a8c
---
# Docker 安装

本节文档将介绍希望使用 Docker 进行开发的用户所需的步骤。您将在开发机上进行代码更改，并在 Docker 中运行生成的应用程序。

## 1. 下载 Docker {#1-downloading-docker}

Docker 的安装过程在 Windows、Mac 和 Linux 用户之间略有不同。请参阅与您的操作系统相对应的部分。

### Windows {#windows}

:::info
建议下载最新版本的 Windows 子系统 Linux。更多信息可以在 [此链接](https://learn.microsoft.com/en-us/windows/wsl/install) 中找到。
:::

**1. 下载 Docker Desktop：**
>- 访问 Docker Desktop for Windows 下载页面：[Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- 单击“获取 Docker Desktop for Windows”按钮以下载安装程序。

**2. 安装 Docker Desktop：**
>- 运行您下载的安装程序。
>- 按照安装向导的指示进行操作，并确保启用 Hyper-V（如果提示）因为 Docker for Windows 使用 Hyper-V 进行虚拟化。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装：**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已正确安装和工作。

### Mac {#mac}

**1. 下载 Docker Desktop：**
>- 访问 Docker Desktop for Mac 下载页面：[Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. 安装 Docker Desktop：**
>- 运行您下载的安装程序。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装：**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已正确安装和工作。

## 2. 配置 {#2-configuration}

下载 Docker Desktop 后，搜索最新的 webforJ 镜像，目前名为 `webforj/sandbox`。

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_1l.png#rounded-border)

单击标签列表以查看可用选项

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_2l.png#rounded-border)

对于最新的构建，选择 "rc"

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_3l.png#rounded-border)

拉取镜像以启动您的容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_4l.png#rounded-border)

下载完成后，单击运行按钮，这将打开配置设置

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_5l.png#rounded-border)

打开“可选设置”菜单

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_6l.png#rounded-border)

选择一个所需的主机端口，在其中可以看到您的应用程序在 Docker 中运行

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_7l.png#rounded-border)

单击“运行”以启动容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success 重要
确保记录下您提供的自定义主机端口号，因为稍后将需要该端口号。
:::

## 3. 运行您的应用程序 {#3-running-your-app}

容器创建后，webforJ 应用程序可以在容器内运行，而不是在本地。首先，需要正确配置项目的 POM 文件。一旦完成，在浏览器中访问特定 URL 将显示该应用程序。

### 配置您的 POM 文件 {#configuring-your-pom-file}

在 Docker 容器中运行 webforJ 项目需要使用 webforJ 安装插件，该插件可以通过您的 POM 文件进行配置：

在 POM 的 `<plugins>` 部分创建一个新的 `<plugin>` 条目。以下代码显示了可以使用的起始条目，可以根据您的项目进行调整：

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

一旦创建了类似于以上的条目，请自定义以下信息：

- 将 `<deployurl>` 条目更改为您与容器中配置的 **主机端口** 相匹配的端口号。

- 确保 `<classname>` 条目与您要运行的应用程序的名称匹配。

- 如果您的 `<username>` 和 `<password>` 凭据与您安装的 BBj 不同，请进行更改。

### 使用启动项目 {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### 启动应用程序 {#launching-the-app}

完成后，在您的项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问您的应用程序。要查看该应用程序，您需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您在 Docker 中配置的主机端口，将 `YourPublishName` 替换为 POM 中 `<publishname>` 标签内的文本。 如果操作正确，您应该看到您的应用程序呈现。
