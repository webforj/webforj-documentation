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

Jede Version von webforJ wird mit detaillierten [Versionshinweisen](https://github.com/webforj/webforj/releases) und einem [Blogartikel zur Veröffentlichung](/blog/tags/release) begleitet.
Snapshot-Versionen von webforJ geben Ihnen Zugang zu den neuesten Funktionen für Tests, während die Entwicklung der voraus veröffentlichten Version fortgesetzt wird.

<!-- vale Google.Acronyms = NO -->
Während Snapshots auf öffentlichen Maven-Repository-Webseiten wie [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) oder [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj) nicht gelistet sind, ist es einfach, den Namen des Snapshots zu finden. Um die neueste Snapshot-Version zu finden, navigieren Sie zum [webforJ-Projekt](https://github.com/webforj/webforj) auf GitHub. Dort finden Sie die [POM-Datei](https://github.com/webforj/webforj/blob/main/pom.xml) des Projekts und suchen nach dem `version`-Tag:
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

Da Snapshots nicht im Maven Central veröffentlicht werden, müssen Sie das Central Portal Snapshots-Repository in das `pom.xml` Ihrer App hinzufügen, damit Maven sie auflösen kann. Sie benötigen zwei Einträge: ein `<repository>` für die Laufzeit-Artefakte von webforJ und ein `<pluginRepository>` für die Maven-Plugins (wie die Installations- und Minify-Plugins), die ebenfalls als Snapshots veröffentlicht werden. Beide Einträge deaktivieren die Release-Auflösung, sodass Maven dieses Repository nur für Snapshot-Artefakte verwendet.

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

Alternativ, wenn Sie eine neue webforJ-App erstellen, gehen Sie zu [startforJ](https://docs.webforj.com/startforj/) und wählen Sie die webforJ-Version aus, die mit `(pre)` endet.

:::warning
Snapshot-Versionen befinden sich in aktiver Entwicklung und können sich ändern, daher werden sie nicht empfohlen für den Einsatz in Live-Produktionsanwendungen.
:::
