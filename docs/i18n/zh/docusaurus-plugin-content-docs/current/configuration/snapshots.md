---
title: Snapshots
sidebar_position: 30
hide_table_of_contents: true
description: >-
  Locate the latest webforJ snapshot version and add the Central Portal
  Snapshots repository to consume pre-release builds.
_i18n_hash: 646ace835d5ba39ed182935e8d7f33fb
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

每个 webforJ 版本发布都伴随着详细的 [发布说明](https://github.com/webforj/webforj/releases) 和一篇 [发布博客文章](/blog/tags/release)。
webforJ 的快照版本让您可以访问最新功能以进行测试，同时在预发布版本上继续开发。

<!-- vale Google.Acronyms = NO -->
虽然快照并未在 Maven 仓库网站上公开列出，如 [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) 或 [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj)，但轻松查看快照名称。要找到最新的快照版本，请导航到 GitHub 上的 [webforJ 项目](https://github.com/webforj/webforj)。从那里，找到项目的 [POM 文件](https://github.com/webforj/webforj/blob/main/pom.xml)，并查找 `version` 标签：
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

要在您的应用中使用该快照版本，请在您的应用的 POM 文件中将该值作为 `webforj.version` 属性使用：
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## 配置快照仓库 {#configure-the-snapshot-repository}

由于快照未发布到 Maven Central，您需要在应用的 `pom.xml` 中添加 Central Portal Snapshots 仓库，以便 Maven 能够解析它们。您需要两个条目：一个 `<repository>` 用于 webforJ 的运行时工件，另一个 `<pluginRepository>` 用于其 Maven 插件（例如安装和压缩插件），这些插件也以快照形式发布。这两个条目会关闭发布解析，以便 Maven 仅使用此仓库中的快照工件。

```xml title="pom.xml"
<repositories>
  <repository>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
    <id>central-portal-snapshots</id>
    <name>Central Portal Snapshots</name>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
  </repository>
</repositories>
<pluginRepositories>
  <pluginRepository>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
    <id>central-portal-snapshots</id>
    <name>Central Portal Snapshots</name>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
  </pluginRepository>
</pluginRepositories>
```

或者，如果您正在创建一个新的 webforJ 应用，请访问 [startforJ](https://docs.webforj.com/startforj/) 并选择以 `(pre)` 结尾的 webforJ 版本。

:::warning
快照版本正在积极开发中，可能会发生变化，因此不建议在生产环境的实时应用中使用。
:::
