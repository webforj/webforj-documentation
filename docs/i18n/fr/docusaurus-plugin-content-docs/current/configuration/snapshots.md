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

Chaque version de webforJ est accompagnée de [notes de version](https://github.com/webforj/webforj/releases) détaillées et d'un [article de blog sur la version](/blog/tags/release).
Les versions instantanées de webforJ vous donnent accès aux dernières fonctionnalités pour des tests pendant que le développement se poursuit sur la version pré-publiée.

<!-- vale Google.Acronyms = NO -->
Bien que les instantanés ne soient pas publiés publiquement sur des sites de dépôt Maven comme [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) ou [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), il est facile d'accéder au nom de l'instantané. Pour trouver la dernière version instantanée, naviguez vers le [projet webforJ](https://github.com/webforj/webforj) sur GitHub. De là, trouvez le [fichier POM du projet](https://github.com/webforj/webforj/blob/main/pom.xml) et recherchez la balise `version` :
<!-- vale Google.Acronyms = YES -->
```xml {3} title="pom.xml"
<groupId>com.webforj</groupId>
<artifactId>webforj-parent</artifactId>
<version>26.00-SNAPSHOT</version>
<packaging>pom</packaging>
<name>webforj</name>
```

Pour utiliser cette version instantanée dans votre application, utilisez cette valeur comme propriété `webforj.version` dans le fichier POM de votre application :
```xml title="pom.xml" {2}
<properties>
  <webforj.version>26.00-SNAPSHOT</webforj.version>
  <maven.compiler.target>21</maven.compiler.target>
  <maven.compiler.source>21</maven.compiler.source>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <tomcat.version>11.0.2</tomcat.version>
</properties>
```

## Configurez le dépôt instantané {#configure-the-snapshot-repository}

Parce que les instantanés ne sont pas publiés sur Maven Central, vous devez ajouter le dépôt des instantanés du Central Portal au fichier `pom.xml` de votre application afin que Maven puisse les résoudre. Vous avez besoin de deux entrées : un `<repository>` pour les artefacts d'exécution de webforJ, et un `<pluginRepository>` pour ses plugins Maven (tels que les plugins d'installation et de minification), qui sont également publiés en tant qu'instantanés. Les deux entrées désactivent la résolution des versions stables afin que Maven n'utilise ce dépôt que pour les artefacts instantanés.

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

Alternativement, si vous créez une nouvelle application webforJ, allez sur [startforJ](https://docs.webforj.com/startforj/) et choisissez la version webforJ qui se termine par `(pre)`.

:::warning
Les versions instantanées sont en cours de développement actif et sont susceptibles de changer, elles ne sont donc pas recommandées pour une utilisation dans des applications de production en direct.
:::
