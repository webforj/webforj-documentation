---
title: Snapshots
sidebar_position: 35
---

Every webforJ version release is accompanied by detailed [release notes](https://github.com/webforj/webforj/releases) and a [release blog article](/blog/tags/release).
Snapshot versions of webforJ give you access to the latest features for testing while development continues on the pre-released version.

<!-- vale Google.Acronyms = NO -->
While snapshots aren't publicly listed on Maven repository sites like [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) or [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), it's easy to access the name of the snapshot. To find the newest snapshot version, navigate to the [webforJ project](https://github.com/webforj/webforj) on GitHub. From there, find the project's [POM file](https://github.com/webforj/webforj/blob/main/pom.xml) and look for the `version` tag:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

To use that snapshot version in your app, use that value as the `webforj.version` property in your app's POM file:
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Configure the snapshot repository {#configure-the-snapshot-repository}

Because snapshots aren't published to Maven Central, you need to add the Central Portal Snapshots repository to your app's `pom.xml` so Maven can resolve them. You need two entries: a `<repository>` for webforJ's runtime artifacts, and a `<pluginRepository>` for its Maven plugins (such as the install and minify plugins), which are also released as snapshots. Both entries turn off release resolution so Maven only uses this repository for snapshot artifacts.

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

Alternatively, if you're creating a new webforJ app, go to [startforJ](https://docs.webforj.com/startforj/) and choose the webforJ version that ends with `(pre)`.

:::warning
Snapshot versions are under active development and are subject to change, so they aren't recommended for use in live production apps.
:::