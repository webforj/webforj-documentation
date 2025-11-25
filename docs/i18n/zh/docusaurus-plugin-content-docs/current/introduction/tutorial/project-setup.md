---
title: 项目设置
sidebar_position: 1
_i18n_hash: f8ad0e22acf56c824b05db580be2203b
---
在本教程中，应用程序将分为**四个步骤**，每个步骤在项目进展中引入新功能。通过跟随学习，您将清楚理解应用程序是如何演变的，以及每个功能是如何实现的。

要开始，您可以下载整个项目或从GitHub克隆：

- 下载ZIP： [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- GitHub仓库： 从[GitHub直接克隆项目](https://github.com/webforj/webforj-demo-application)

```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

无论是ZIP文件还是GitHub仓库，都包含完整的项目结构和所有四个步骤，因此您可以在任意点开始或逐步进行。

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## 项目结构 {#project-structure}

项目分为四个独立的目录，每个目录代表应用程序开发的特定阶段。这些步骤使您可以看到应用程序是如何从基本设置演变为功能完善的客户管理系统。

在项目文件夹中，您会发现四个子目录，每个子目录对应于教程中的一个步骤：

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

1) 导航到所需步骤的目录。这应该是该步骤的顶级目录，包含`pom.xml`

2) 使用Maven Jetty插件通过运行以下命令在本地部署应用程序：

```bash
mvn jetty:run
```

3) 打开您的浏览器并导航到 [http://localhost:8080](http://localhost:8080) 以查看应用程序。

随着您跟随教程的进行，重复此过程以探索添加的应用程序功能。
