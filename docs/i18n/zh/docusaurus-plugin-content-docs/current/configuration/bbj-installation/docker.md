---
sidebar_position: 1
title: Docker
_i18n_hash: 49f4e9eb5470926c186e323e4d67377f
---
# Docker 安装

本节文档将介绍希望使用 Docker 开发的用户所需的步骤。您将在开发机器上对代码进行更改，生成的应用将运行在 Docker 中。

## 1. 下载 Docker {#1-downloading-docker}

Docker 的安装过程在 Windows、Mac 和 Linux 用户之间略有不同。请参见下面与您的操作系统相对应的部分。

### Windows {#windows}

:::info
建议下载最新版本的 Windows 子系统 Linux。更多信息请访问 [此链接](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. 下载 Docker Desktop:**
>- 访问 Windows Docker Desktop 下载页面：[Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- 单击“获取 Docker Desktop for Windows”按钮下载安装程序。

**2. 安装 Docker Desktop:**
>- 运行您下载的安装程序。
>- 按照安装向导的指示进行操作，并确保在提示时启用 Hyper-V，因为 Windows 版 Docker 使用 Hyper-V 进行虚拟化。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装:**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已正确安装和运行。

### Mac {#mac}

**1. 下载 Docker Desktop:**
>- 访问 Mac Docker Desktop 下载页面：[Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. 安装 Docker Desktop:**
>- 运行您下载的安装程序。
>- 安装完成后，Docker Desktop 将自动启动。

**3. 验证安装:**
>- 打开终端并运行命令 `docker --version` 以验证 Docker 是否已正确安装和运行。

## 2. 配置 {#2-configuration}

下载 Docker Desktop 后，搜索当前名为 `webforj/sandbox` 的最新 webforJ 镜像。

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_1l.png#rounded-border)

单击标签列表以查看可用选项

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_2l.png#rounded-border)

对于最新的构建，选择“rc”

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_3l.png#rounded-border)

拉取镜像以启动您的容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_4l.png#rounded-border)

下载完成后，单击运行按钮，将打开配置设置

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_5l.png#rounded-border)

打开“可选设置”菜单

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_6l.png#rounded-border)

选择一个希望在 Docker 中运行您的应用的主机端口

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_7l.png#rounded-border)

单击“运行”以启动容器

![DWCJ 图像搜索](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success 重要
请务必记下您提供的自定义主机端口号，因为稍后将需要该信息。
:::

## 3. 运行您的应用 {#3-running-your-app}

容器创建后，可以在容器内运行 webforJ 应用，而不是在本地。首先，需要正确配置项目的 POM 文件。完成此操作后，在浏览器中访问特定 URL 将显示应用。

### 配置您的 POM 文件 {#configuring-your-pom-file}

在 Docker 容器中运行 webforJ 项目将需要使用 webforJ 安装插件，可以通过您的 POM 文件进行配置：

在 POM 的 `<plugins>` 部分创建一个新的 `<plugin>` 条目。以下代码显示了一个可以使用并根据项目需要进行调整的初始条目：

:::important
如果您的 POM 文件中没有 `<plugins>` 部分，请创建一个。
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

创建类似于上述条目后，自定义以下信息：

- 将 `<deployurl>` 条目更改为使用您在上一步中为容器配置的 **主机端口** 的端口号。

- 确保 `<classname>` 条目与您要运行的应用名称匹配。

- 如果您安装 BBj 使用的 `<username>` 和 `<password>` 凭据不同，请进行更改。

### 使用启动项目 {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### 启动应用 {#launching-the-app}

完成后，在您的项目目录中运行 `mvn install`。这将运行 webforJ 安装插件，并允许您访问您的应用。要查看应用，您需要访问以下 URL：

`http://localhost:YourHostPort/webapp/YourPublishName`

将 `YourHostPort` 替换为您在 Docker 中配置的主机端口，将 `YourPublishName` 替换为 POM 的 `<publishname>` 标签中的文本。如果操作正确，您应该会看到您的应用渲染。
