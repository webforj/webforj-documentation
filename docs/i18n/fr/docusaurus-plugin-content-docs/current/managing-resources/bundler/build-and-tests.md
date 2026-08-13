---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  What the bundler does across the build, the development watch, running
  frontend tests, tuning a compiler, and producing a minified production bundle.
_i18n_hash: 0fe6e8ed747a106be1fedf5a2506f803
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Le bundler fonctionne comme les objectifs du [plugin de build webforJ](/docs/configuration/build-plugin). Ajoutez le plugin une fois, comme montré là-bas, et un `mvn package` ou `gradle build` normal produit une application avec son frontend compilé, tandis que `mvn test` exécute les tests frontend aux côtés des tests Java. Cette page couvre ce que fait le bundler au cours de ces étapes.

## La surveillance de développement {#the-development-watch}

L'étape `watch` est celle que vous exécutez manuellement pendant le développement, aux côtés de l'application. Elle compile le frontend une fois, puis se reconstruit à chaque changement et rafraîchit le navigateur.

```bash
mvn compile webforj:watch spring-boot:run
```

Un projet webforJ définit cela comme son objectif par défaut, donc `mvn` sans arguments démarre la surveillance et l'application ensemble. Le comportement de rechargement qu'il contrôle, un changement de feuille de style appliqué en place contre un changement de script qui recharge la vue, est couvert dans [Surveillance frontend](/docs/configuration/deploy-reload/frontend-watch).

## Tests frontend {#frontend-tests}

L'étape `test` exécute le testeur Bun sur `src/main/frontend` pendant la phase de test, donc `mvn test` exécute les tests frontend avec les tests Java. Lorsque le répertoire source ne contient aucun fichier de test, l'étape est ignorée, et un test frontend échoué échoue la construction, donc un frontend cassé arrête une version de la même manière qu'un test Java cassé. Pour écrire ces tests, voir [Tests frontend](/docs/testing/frontend-testing).

## Ajuster un compilateur {#tuning-a-compiler}

Un compilateur lit ses paramètres depuis `src/main/frontend/bun.config.ts`, indexés par l'identifiant de l'extension, donc un paramètre atteint le bon compilateur sans drapeau sur la construction. Voir [SCSS et Sass](/docs/managing-resources/bundler/extensions/scss) pour un exemple concret qui donne au compilateur SCSS un chemin de chargement.

## Le bundle de production {#the-production-bundle}

L'étape `bundle` s'exécute pendant `prepare-package`, donc emballer une application compile son frontend pour la production. Une construction de production diffère de celle de développement de deux manières qui importent une fois une application déployée.

- **Noms de fichiers hachés.** Chaque fichier de sortie porte un hachage de son contenu dans son nom. Un navigateur peut alors mettre en cache un fichier pendant longtemps, car un changement dans le contenu produit un nouveau nom, et le nouveau nom force un nouvel accès. La mise en cache reste sécurisée sans une mise à jour de version manuelle.
- **Sortie minifiée.** Les espaces vides et le code mort sont supprimés, donc les fichiers téléchargés par un navigateur sont aussi petits que le compilateur peut les rendre.

Une construction de développement saute les deux. Elle conserve des noms stables et une sortie lisible, afin que la surveillance puisse échanger un fichier sur place et que vous puissiez lire ce qui se charge pendant que vous déboguez.

Parce que la minification fait partie de cette étape, un projet qui utilise le bundler n'a besoin de rien d'autre pour expédier du CSS et du JavaScript minifiés. Pour une application qui charge des ressources via les [annotations de ressources](/docs/managing-resources/importing-assets) sans le bundler, le [plugin de minification](/docs/configuration/minifier-plugin) couvre alors cette minification en production.

## Bundle impatient {#eager-bundle}

Par défaut, chaque vue charge seulement le frontend qu'elle utilise, lorsqu'un composant de cette classe est créé, donc une vue ne paie rien qu'elle ne rend pas.

Le mode impatient charge l'ensemble du frontend au démarrage de l'application comme un seul bundle, au lieu de par vue. Activez-le avec l'option `eager` :

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

Le mode impatient est désactivé par défaut, et le modèle par vue convient à la plupart des applications. Utilisez-le lorsque vous souhaitez que l'ensemble du frontend soit en place dès le départ plutôt que chargé au fur et à mesure que les vues sont rendues.
