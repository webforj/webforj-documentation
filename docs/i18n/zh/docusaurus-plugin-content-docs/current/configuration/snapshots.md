---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: a90b2411def220ffa3a2e78af646cf60
---
每个 webforJ 版本发布都会伴随详细的 [发行说明](https://github.com/webforj/webforj/releases) 和一篇 [发布博客文章](/blog/tags/release)。webforJ 的快照版本让您可以访问最新功能进行测试，同时在预发布版本上继续开发。

虽然快照在 Maven 仓库网站如 [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) 或 [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj) 上不公开列出，但访问快照的名称是很容易的。要查找最新的快照版本，请导航至 GitHub 上的 [webforJ 项目](https://github.com/webforj/webforj)。从那里找到项目的 [POM 文件](https://github.com/webforj/webforj/blob/main/pom.xml)，并查找 `version` 标签：

```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
  ```

在您的应用程序中使用该快照版本，请在应用程序的 POM 文件中将该值用作 `webforj.version` 属性：

```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

或者，如果您正在创建一个新的 webforJ 应用程序，请访问 [startforJ](https://docs.webforj.com/startforj/) 并选择以 `(pre)` 结尾的 webforJ 版本。

:::warning
快照版本正在积极开发中，可能会发生变化，因此不建议在实时生产应用程序中使用。
:::
