---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: a90b2411def220ffa3a2e78af646cf60
---
Jokainen webforJ-version julkaisu on varustettu yksityiskohtaisilla [julkaisumuistiinpanoilla](https://github.com/webforj/webforj/releases) ja [julkaisublogikirjoituksella](/blog/tags/release). Snapshot-version webforJ:stä saat käyttöösi uusimmat ominaisuudet testaamista varten, kun kehitys jatkuu esijulkaistussa versiossa.

Vaikka snapshot-versioita ei ole julkisesti listattu Maven-rekisterisivustoilla, kuten [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) tai [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), on snapshotin nimen löytäminen helppoa. Löytääksesi uusimman snapshot-version, siirry [webforJ-projektiin](https://github.com/webforj/webforj) GitHubissa. Sieltä löydät projektin [POM-tiedoston](https://github.com/webforj/webforj/blob/main/pom.xml) ja etsi `version`-tagia:

```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Käyttääksesi tätä snapshot-versiota sovelluksessasi, käytä tätä arvoa `webforj.version` -ominaisuutena sovelluksesi POM-tiedostossa:

```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

Alternatiivisesti, jos luot uutta webforJ-sovellusta, siirry [startforJ](https://docs.webforj.com/startforj/) ja valitse webforJ-versio, joka päättyy `(pre)`.

:::warning
Snapshot-versiot ovat aktiivisen kehityksen alla ja voivat muuttua, joten niitä ei suositella käytettäväksi tuotantoympäristössä.
:::
