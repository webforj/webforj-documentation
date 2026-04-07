---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: 5234e12882e2652d440f8861a6341cef
---
Jede Version von webforJ wird von detaillierten [Versionshinweisen](https://github.com/webforj/webforj/releases) und einem [Blogartikel zur Veröffentlichung](/blog/tags/release) begleitet. Snapshot-Versionen von webforJ ermöglichen Ihnen den Zugriff auf die neuesten Funktionen zum Testen, während die Entwicklung an der vorab veröffentlichten Version fortgesetzt wird.

<!-- vale Google.Acronyms = NO -->
Während Snapshots auf öffentlichen Maven-Repository-Websites wie [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) oder [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj) nicht aufgeführt sind, ist es einfach, den Namen des Snapshots zu finden. Um die neueste Snapshot-Version zu finden, navigieren Sie zum [webforJ-Projekt](https://github.com/webforj/webforj) auf GitHub. Finden Sie von dort aus die [POM-Datei](https://github.com/webforj/webforj/blob/main/pom.xml) des Projekts und suchen Sie nach dem `version`-Tag:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Um diese Snapshot-Version in Ihrer App zu verwenden, verwenden Sie diesen Wert als `webforj.version`-Eigenschaft in der POM-Datei Ihrer App:
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Konfigurieren Sie das Snapshot-Repository {#configure-the-snapshot-repository}

Da Snapshots nicht auf Maven Central veröffentlicht werden, müssen Sie das Central Portal Snapshots-Repository zu Ihrer POM-Datei hinzufügen, damit Maven sie auflösen kann. Sie benötigen zwei Einträge: ein `<repository>` für die Laufzeitartefakte von webforJ und ein `<pluginRepository>` für die Maven-Plugins (wie die Install- und Minify-Plugins), die ebenfalls als Snapshots veröffentlicht werden. Beide Einträge deaktivieren die Veröffentlichungslösung, sodass Maven nur dieses Repository für Snapshot-Artefakte verwendet.

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

Alternativ, wenn Sie eine neue webforJ-App erstellen, gehen Sie zu [startforJ](https://docs.webforj.com/startforj/) und wählen Sie die webforJ-Version, die mit `(pre)` endet.

:::warning
Snapshot-Versionen befinden sich in aktiver Entwicklung und können Änderungen unterliegen, daher werden sie nicht für den Einsatz in produktiven Anwendungen empfohlen.
:::
