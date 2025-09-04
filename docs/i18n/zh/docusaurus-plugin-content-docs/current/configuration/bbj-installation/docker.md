---
sidebar_position: 1
title: Docker
_i18n_hash: ec7566378c3ec80f071b7391742ec353
---
# Docker 安装

本节文档将介绍希望使用 Docker 开发的用户所需的步骤。您的代码更改将在开发机器上进行，并将在 Docker 中运行生成的应用程序。

## 1. 下载 Docker {#1-downloading-docker}

Docker 的安装过程在 Windows、Mac 和 Linux 用户之间会略有不同。请参阅下面与您的操作系统相对应的部分。

### Windows {#windows}

:::info
建议下载最新版本的 Windows 子系统 Linux。更多信息可以在[此链接](https://learn.microsoft.com/en-us/windows/wsl/install)中找到。
:::

**1. 下载 Docker Desktop：**
>- 访问 Docker Desktop for Windows 下载页面：[Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- 点击“获取 Docker Desktop for Windows”按钮下载安装程序。

**2. 安装 Docker Desktop：**
>- 运行下载的安装程序。
>- 按照安装向导的指示操作，并确保启用 Hyper-V（如果提示），因为 Windows 上的 Docker 使用 Hyper-V 进行虚拟化。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装：**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已正确安装并正常工作。

### Mac {#mac}

**1. 下载 Docker Desktop：**
>- 访问 Docker Desktop for Mac 下载页面：[Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. 安装 Docker Desktop：**
>- 运行下载的安装程序。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装：**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已正确安装并正常工作。

## 2. 配置 {#2-configuration}

下载 Docker Desktop 后，搜索当前名称为 `webforj/sandbox` 的最新 webforJ 镜像。

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_1l.png#rounded-border)

点击标签列表查看可用选项

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_2l.png#rounded-border)

对于最新的构建，选择“rc”

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_3l.png#rounded-border)

拉取镜像以启动您的容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_4l.png#rounded-border)

下载完成后，点击运行按钮，这将打开配置设置

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_5l.png#rounded-border)

打开“可选设置”菜单

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_6l.png#rounded-border)

选择您希望看到应用程序在 Docker 内运行的主机端口

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_7l.png#rounded-border)

单击“运行”以启动容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success 重要
请务必记下您提供的自定义主机端口号，因为后续使用时将需要该端口号。
:::

## 3. 运行您的应用程序 {#3-running-your-app}

一旦容器创建完成，webforJ 应用程序可以在容器内运行，而非在本地。首先，必须正确配置您项目的 POM 文件。完成此步骤后，访问浏览器中的特定 URL 将显示应用程序。

### 配置您的 POM 文件 {#configuring-your-pom-file}

在 Docker 容器中运行 webforJ 项目将需要使用 webforJ 安装插件，您可以使用 POM 文件进行配置：

在 POM 的 `<plugins>` 部分创建一个新的 `<plugin>` 条目。以下代码展示了一个可以使用并根据项目需求进行调整的起始条目：

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

一旦创建了类似于上面所示的条目，请自定义以下信息：

- 将 `<deployurl>` 条目更改为使用与您在前一步中为容器配置的 **主机端口** 匹配的端口号。

- 确保 `<classname>` 条目与您要运行的应用程序名称匹配。

- 如果您为 BBj 的安装提供的 `<username>` 和 `<password>` 凭据与其他不同，请进行更改。

### 使用启动项目 {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### 启动应用程序 {#launching-the-app}

完成上述步骤后，在您的项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问您的应用程序。要查看应用程序，请转到以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您在 Docker 中配置的主机端口，将 `YourPublishName` 替换为 POM 文件中 `<publishname>` 标签内的文本。如果操作正确，您应该能看到您的应用程序渲染。
