---
title: Premiers pas
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 913c105077586054f3af87216fe848e0
---
Ce guide crée une application webforJ qui s’exécute avec BBjServices, la lance sur votre BBj local pendant le développement et la déploie avec BBjShip.

:::tip Prérequis
Vous avez besoin d’un projet Maven et d’une installation de BBj de la même version que webforJ, par exemple BBj 26.03 pour webforJ 26.03, avec BBjServices en cours d’exécution. BBjShip ne prend pas en charge Gradle. Consultez les [prérequis](/docs/introduction/prerequisites) pour connaître les autres outils nécessaires.
:::

## Créer une application BBj {#create-a-bbj-app}

Vous avez deux options pour créer une nouvelle application webforJ qui s’exécute avec BBjServices : utiliser l’outil graphique startforJ ou la ligne de commande Maven.

<!-- vale off -->
### Option 1 : utiliser startforJ {#option-1-using-startforj}
<!-- vale on -->

Lorsque vous créez une application avec [startforJ](https://docs.webforj.com/startforj), vous pouvez la personnaliser en fournissant les informations suivantes :

- Métadonnées de base du projet (nom de l’application, ID de groupe, ID d’artefact)
- Versions de webforJ et de Java
- Couleur du thème et icône
- Archétype
- **S’exécute sur** - Sélectionnez **BBjServices** pour créer un projet qui s’exécute avec BBjServices

### Option 2 : utiliser la ligne de commande {#option-2-using-the-command-line}

Si vous préférez utiliser la ligne de commande, générez le projet avec les [archétypes officiels de webforJ](/docs/building-ui/archetypes/overview) :

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
    ```bash {8}
    mvn -B archetype:generate \
    -DarchetypeGroupId=com.webforj \
    -DarchetypeArtifactId=webforj-archetype-hello-world \
    -DarchetypeVersion=LATEST \
    -DgroupId=org.example \
    -DartifactId=my-app \
    -Dversion=1.0-SNAPSHOT \
    -Dflavor=webforj-bbj
    ```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
    ```powershell {8}
    mvn -B archetype:generate `
    -DarchetypeGroupId="com.webforj" `
    -DarchetypeArtifactId="webforj-archetype-blank" `
    -DarchetypeVersion="LATEST" `
    -DgroupId="org.example" `
    -DartifactId="my-app" `
    -Dversion="1.0-SNAPSHOT" `
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
    ```console {8}
    mvn -B archetype:generate ^
    -DarchetypeGroupId="com.webforj" ^
    -DarchetypeArtifactId="webforj-archetype-blank" ^
    -DarchetypeVersion="LATEST" ^
    -DgroupId="org.example" ^
    -DartifactId="my-app" ^
    -Dversion="1.0-SNAPSHOT" ^
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

Avec le paramètre `flavor`, l’archétype génère un projet qui s’exécute avec BBjServices.

Cela crée un projet avec :
- La dépendance `webforj` sans le moteur webforJ, fourni par BBjServices
- Le plugin Maven webforJ, configuré pour déployer sur BBjShip dans votre BBj local, en utilisant l’ID d’artefact comme nom d’application
- HotswapAgent pour les exécutions en développement
- Des vues d’exemple
- Des fichiers de configuration webforJ pour le développement et la production

## Exécuter l’application {#run-the-app}

Un projet créé à partir d’un archétype définit son objectif Maven par défaut : ainsi, `mvn` sans argument compile l’application et lance une [exécution de développement](/docs/configuration/deploy-reload/bbjservices) dans votre BBjServices local :

```bash
mvn
```

La première exécution prépare votre BBj local. Elle installe BBjShip et ajoute HotswapAgent à BBjServices, qui redémarre une fois. La compilation affiche ensuite l’adresse de votre application :

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Les classes que vous compilez sont intégrées à l’application en cours d’exécution sans redémarrage. Arrêtez la compilation avec <kbd>Ctrl</kbd>+<kbd>C</kbd> pour arrêter l’application.

## Déployer l’application {#deploy-the-app}

Pour déployer l’application empaquetée, exécutez :

```bash
mvn install
```

L’application est alors publiée à l’adresse `http://localhost:8888/webapp/my-app` jusqu’à ce que vous la redéployiez ou la supprimiez. Consultez la section [Déploiement](/docs/configuration/bbjship/deploying) pour la déployer sur un autre serveur BBj, par exemple depuis un pipeline CI.
