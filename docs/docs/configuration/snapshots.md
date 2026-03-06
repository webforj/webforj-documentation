---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
---

Every webforJ version release is accompanied by detailed [release notes](https://github.com/webforj/webforj/releases) and a [release blog article](/blog/tags/release).
Snapshot versions of webforJ give you access to the latest features for testing while development continues for the pre-released version.

<!-- vale Google.Acronyms = NO -->
While snapshots aren't publicly listed on Maven repository sites like [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) or [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), it's easy to access the name of the snapshot. To find the newest snapshot version, navigate to the [webforJ project](https://github.com/webforj/webforj) on GitHub. From there, find the project's [POM file](https://github.com/webforj/webforj/blob/main/pom.xml) and look for the `version` tag:
<!-- vale Google.Acronyms = YES -->

```xml {3}
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
  ```

Then, to use that snapshot version in your app, use the found value as your `webforj.version` property in the POM file of your app:

```xml {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

Alternatively, if you’re creating a new webforJ app, go to [startforJ](https://docs.webforj.com/startforj/) and from the **webforJ version** dropdown, select the option with `(pre)`.

:::warning
Snapshot versions are under active development and are subject to change, so they aren’t recommended for use in live production apps.
:::