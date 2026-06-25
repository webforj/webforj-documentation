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

Jokainen webforJ-version julkaisu on mukana yksityiskohtaiset [julkaisumuistiinpanot](https://github.com/webforj/webforj/releases) ja [julkaisublogikirje](/blog/tags/release).
Snapshot-versiot webforJ:stä antavat sinulle pääsyn uusimpiin ominaisuuksiin testattavaksi, kun kehitys jatkuu julkaisemattomalla versiolla.

<!-- vale Google.Acronyms = NO -->
Vaikka snapshot-julkaisuja ei ole julkisesti listattu Maven-repositorioissa, kuten [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) tai [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), on helppo löytää snapshotin nimi. Löytääksesi uusin snapshot-versio, siirry [webforJ-projektiin](https://github.com/webforj/webforj) GitHubissa. Sieltä löydät projektin [POM-tiedoston](https://github.com/webforj/webforj/blob/main/pom.xml) ja etsi `version`-tagia:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Käyttääksesi tuota snapshot-versiota sovelluksessasi, käytä sitä arvoa `webforj.version`-ominaisuutena sovelluksesi POM-tiedostossa:
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Määritä snapshot-repositorio {#configure-the-snapshot-repository}

Koska snapshot-julkaisuja ei julkaista Maven Centralissa, sinun on lisättävä Central Portal Snapshots -repositorio sovelluksesi `pom.xml`-tiedostoon, jotta Maven voi ratkaista ne. Tarvitset kaksi merkintää: `<repository>` webforJ:n ajonaikaisille artefakteille ja `<pluginRepository>` sen Maven-laajennuksille (kuten asennus- ja minifiointilaajennuksille), jotka myös julkaistaan snapshotina. Molemmat merkinnät poistavat julkaisun ratkaisun käytöstä, jotta Maven käyttää vain tätä repositorion snapshot-artefakteille.

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

Vaihtoehtoisesti, jos olet luomassa uutta webforJ-sovellusta, mene [startforJ](https://docs.webforj.com/startforj/) ja valitse webforJ-versio, joka päättyy `(pre)`.

:::warning
Snapshot-versiot ovat aktiivisessa kehityksessä ja voivat muuttua, joten niiden käyttöä live-tuotantosovelluksissa ei suositella.
:::
