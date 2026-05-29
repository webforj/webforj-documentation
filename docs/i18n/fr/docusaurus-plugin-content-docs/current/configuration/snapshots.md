---
title: Snapshots
sidebar_position: 35
sidebar_class_name: new-content
_i18n_hash: 5234e12882e2652d440f8861a6341cef
---
Chaque version de webforJ est accompagnée de [notes de version détaillées](https://github.com/webforj/webforj/releases) et d'un [article de blog sur la version](/blog/tags/release). Les versions instantanées de webforJ vous donnent accès aux dernières fonctionnalités pour des tests pendant que le développement se poursuit sur la version pré-publiée.

Bien que les instantanés ne soient pas publiés publiquement sur des sites de référentiel Maven comme [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj/overview) ou [MVN Repository](https://mvnrepository.com/artifact/com.webforj/webforj), il est facile d'accéder au nom de l'instantané. Pour trouver la version instantanée la plus récente, naviguez vers le [projet webforJ](https://github.com/webforj/webforj) sur GitHub. De là, trouvez le [fichier POM](https://github.com/webforj/webforj/blob/main/pom.xml) du projet et recherchez la balise `version` :

Pour utiliser cette version instantanée dans votre application, utilisez cette valeur comme propriété `webforj.version` dans le fichier POM de votre application :

## Configurer le référentiel instantané {#configure-the-snapshot-repository}

Parce que les instantanés ne sont pas publiés sur Maven Central, vous devez ajouter le référentiel Central Portal Snapshots au fichier `pom.xml` de votre application afin que Maven puisse les résoudre. Vous avez besoin de deux entrées : un `<repository>` pour les artefacts d'exécution de webforJ et un `<pluginRepository>` pour ses plugins Maven (comme les plugins d'installation et de minification), qui sont également publiés en tant qu'instantanés. Les deux entrées désactivent la résolution des versions de libération afin que Maven n'utilise ce référentiel que pour les artefacts instantanés.

Alternativement, si vous créez une nouvelle application webforJ, allez sur [startforJ](https://docs.webforj.com/startforj/) et choisissez la version de webforJ qui se termine par `(pre)`.

:::warning
Les versions instantanées sont en cours de développement actif et sont sujettes à changement, il n'est donc pas recommandé de les utiliser dans des applications de production en direct.
:::
