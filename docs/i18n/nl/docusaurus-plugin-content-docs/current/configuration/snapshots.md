---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: a90b2411def220ffa3a2e78af646cf60
---
Elke release van webforJ wordt vergezeld van gedetailleerde [release-opmerkingen](https://github.com/webforj/webforj/releases) en een [release blogartikel](/blog/tags/release). Snapshotversies van webforJ geven je toegang tot de nieuwste functies voor testen terwijl de ontwikkeling van de pre-released versie voortgaat.

<!-- vale Google.Acronyms = NO -->
Hoewel snapshots niet publiekelijk worden vermeld op Maven-reposito sites zoals [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) of [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), is het gemakkelijk om de naam van de snapshot te verkrijgen. Om de nieuwste snapshotversie te vinden, navigeer je naar het [webforJ-project](https://github.com/webforj/webforj) op GitHub. Zoek vanaf daar naar het [POM-bestand](https://github.com/webforj/webforj/blob/main/pom.xml) van het project en kijk naar de `version` tag:
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

Als alternatief, als je een nieuwe webforJ-app aanmaakt, ga dan naar [startforJ](https://docs.webforj.com/startforj/) en kies de webforJ-versie die eindigt op `(pre)`.

:::warning
Snapshotversies zijn onder actieve ontwikkeling en kunnen veranderen, dus worden ze niet aanbevolen voor gebruik in live productie-apps.
:::
