---
title: Project Setup
sidebar_position: 1
_i18n_hash: b1ac0a58b11558f40824c8caedeb95b3
---
在本教程中，应用程序将分成**四个步骤**，每个步骤都引入新的功能，随着项目的进展。在此过程中，您将清楚地理解应用程序如何演变，以及每个功能是如何实现的。

要开始，您可以下载整个项目或从 GitHub 克隆它：
<!-- vale off -->
- 下载 ZIP： [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub 仓库：直接从 GitHub 克隆项目 [directly from GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

ZIP 文件和 GitHub 仓库都包含完整的项目结构以及所有四个步骤，因此您可以从任何点开始，或逐步跟随。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## 项目结构 {#project-structure}

项目分为四个独立的目录，每个目录代表应用程序开发的特定阶段。这些步骤允许您看到应用程序从基本设置演变为一个功能齐全的客户管理系统的过程。

在项目文件夹中，您会找到四个子目录，每个目录对应于教程中的一个步骤：

```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│   tree.txt
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
└───4-validating-and-binding-data
```

### 运行应用程序 {#running-the-app}

要在任何阶段查看应用程序的运行情况：

1) 导航到所需步骤的目录。这应该是该步骤的顶级目录，包含 `pom.xml`

2) 使用 Maven Jetty 插件通过运行以下命令在本地部署应用程序：

```bash
mvn jetty:run
```

3) 打开浏览器并导航到 http://localhost:8080 来查看应用程序。

按照教程逐步进行此过程，以便您探索随着功能添加而增强的应用程序特性。
