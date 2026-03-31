---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: a90b2411def220ffa3a2e78af646cf60
---
Jede Version von webforJ wird von ausführlichen [Versionshinweisen](https://github.com/webforj/webforj/releases) und einem [Blogartikel zur Version](https://blog/tags/release) begleitet. Snapshot-Versionen von webforJ geben Ihnen Zugang zu den neuesten Funktionen für Tests, während die Entwicklung der vorab veröffentlichten Version fortgesetzt wird.

<!-- vale Google.Acronyms = NO -->
Während Snapshots auf öffentlichen Maven-Repository-Seiten wie [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) oder [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj) nicht aufgelistet sind, ist es einfach, den Namen des Snapshots zu finden. Um die neueste Snapshot-Version zu finden, navigieren Sie zum [webforJ-Projekt](https://github.com/webforj/webforj) auf GitHub. Von dort aus finden Sie die [POM-Datei](https://github.com/webforj/webforj/blob/main/pom.xml) des Projekts und suchen nach dem `version`-Tag:
<!-- vale Google.Acronyms = YES -->

```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Um diese Snapshot-Version in Ihrer App zu verwenden, nutzen Sie diesen Wert als die `webforj.version`-Eigenschaft in der POM-Datei Ihrer App:

```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

Alternativ, wenn Sie eine neue webforJ-App erstellen, gehen Sie zu [startforJ](https://docs.webforj.com/startforj/) und wählen Sie die webforJ-Version, die mit `(pre)` endet.

:::warning
Snapshot-Versionen befinden sich in aktiver Entwicklung und können sich ändern, daher werden sie nicht für den Einsatz in Live-Produktionsanwendungen empfohlen.
:::
