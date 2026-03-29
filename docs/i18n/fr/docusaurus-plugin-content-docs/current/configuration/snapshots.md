---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: a90b2411def220ffa3a2e78af646cf60
---
Chaque version de webforJ est accompagnée de notes de [version détaillées](https://github.com/webforj/webforj/releases) et d'un [article de blog sur la version](/blog/tags/release). Les versions instantanées de webforJ vous donnent accès aux dernières fonctionnalités pour les tests pendant que le développement se poursuit sur la version pré-publiée.

<!-- vale Google.Acronyms = NO -->
Bien que les instantanés ne soient pas publiquement répertoriés sur les sites de dépôt Maven comme [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) ou [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), il est facile d'accéder au nom de l'instantané. Pour trouver la version d'instantané la plus récente, naviguez vers le [projet webforJ](https://github.com/webforj/webforj) sur GitHub. À partir de là, trouvez le [fichier POM du projet](https://github.com/webforj/webforj/blob/main/pom.xml) et recherchez la balise `version` :
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

Alternativement, si vous créez une nouvelle application webforJ, allez sur [startforJ](https://docs.webforj.com/startforj/) et choisissez la version de webforJ qui se termine par `(pre)`.

:::warning
Les versions instantanées sont en développement actif et sont susceptibles de changer, elles ne sont donc pas recommandées pour une utilisation dans des applications de production en direct.
:::
