---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: 5234e12882e2652d440f8861a6341cef
---
Jokainen webforJ-version julkaisu tulee yksityiskohtaisten [julkaisumuistiinpanojen](https://github.com/webforj/webforj/releases) ja [julkaisublogikirjoituksen](/blog/tags/release) kanssa. webforJ:n snapshot-versiot antavat sinulle mahdollisuuden testata viimeisimpiä ominaisuuksia samalla, kun kehitys jatkuu ennakkoversiolla.

<!-- vale Google.Acronyms = NO -->
Vaikka snapshotit eivät ole julkisesti listattuina Maven-repositorioissa kuten [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) tai [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), on snapshotin nimen löytäminen helppoa. Löydäksesi uusin snapshot-versio, siirry [webforJ-projektiin](https://github.com/webforj/webforj) GitHubissa. Sieltä etsi projektin [POM-tiedosto](https://github.com/webforj/webforj/blob/main/pom.xml) ja etsi `version`-tagia:
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Käyttääksesi tätä snapshot-versiota sovelluksessasi, käytä tätä arvoa `webforj.version`-ominaisuutena sovelluksesi POM-tiedostossa:
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

Koska snapshotit eivät julkaista Maven Centraliin, sinun on lisättävä Central Portal Snapshots -repositorio sovelluksesi `pom.xml`-tiedostoon, jotta Maven voi ratkaista ne. Tarvitset kaksi merkintää: `<repository>` webforJ:n suoritustaidetta varten ja `<pluginRepository>` sen Maven-laajennuksia (kuten asennus- ja minimointi-laajennuksia) varten, jotka myös julkaistaan snapshotteina. Molemmat merkinnät poistavat julkaisujen ratkaisemisen käytöstä, joten Maven käyttää tätä repositoriat vain snapshot-taiteille.

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

Vaihtoehtoisesti, jos luot uuden webforJ-sovelluksen, siirry [startforJ](https://docs.webforj.com/startforj/) ja valitse webforJ-versio, joka päättyy `(pre)`.

:::warning
Snapshot-versiot ovat aktiivisen kehityksen alla ja voivat muuttua, joten niitä ei suositella käytettäväksi live-tuotantosovelluksissa.
:::
