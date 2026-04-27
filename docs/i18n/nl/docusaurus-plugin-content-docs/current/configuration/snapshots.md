---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: 5234e12882e2652d440f8861a6341cef
---
Elke webforJ versie release gaat vergezeld van gedetailleerde [release-opmerkingen](https://github.com/webforj/webforj/releases) en een [release blogartikel](/blog/tags/release). Snapshotversies van webforJ geven je toegang tot de nieuwste functies voor testing terwijl de ontwikkeling doorgaat aan de pre-released versie.

<!-- vale Google.Acronyms = NO -->
Hoewel snapshots niet openbaar worden vermeld op Maven repository sites zoals [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) of [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), is het eenvoudig om de naam van de snapshot te vinden. Om de nieuwste snapshotversie te vinden, navigeer naar het [webforJ-project](https://github.com/webforj/webforj) op GitHub. Zoek daar het [POM-bestand](https://github.com/webforj/webforj/blob/main/pom.xml) van het project en zoek naar de `version` tag:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Om die snapshotversie in je app te gebruiken, gebruik je die waarde als de `webforj.version` eigenschap in het POM-bestand van je app:
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Configureer de snapshotrepository {#configure-the-snapshot-repository}

Omdat snapshots niet naar Maven Central worden gepubliceerd, moet je de Central Portal Snapshots-repository toevoegen aan het `pom.xml` van je app, zodat Maven ze kan oplossen. Je hebt twee vermeldingen nodig: een `<repository>` voor de runtime-artifacten van webforJ, en een `<pluginRepository>` voor de Maven-plugins (zoals de install- en minify-plugins), die ook als snapshots worden uitgebracht. Beide vermeldingen schakelen de release-oplossing uit, zodat Maven alleen deze repository gebruikt voor snapshot-artifacten.

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

Als alternatief, als je een nieuwe webforJ-app aanmaakt, ga naar [startforJ](https://docs.webforj.com/startforj/) en kies de webforJ-versie die eindigt met `(pre)`.

:::warning
Snapshotversies zijn onder actieve ontwikkeling en kunnen veranderen, dus ze worden niet aanbevolen voor gebruik in live productie-apps.
:::
