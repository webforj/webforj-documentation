---
title: Configuration du projet
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 95bc825bd041018765a732bf464d77b7
---
Pour commencer ce tutoriel, vous avez besoin d’un emplacement pour votre projet, où vous pourrez gérer vos classes et vos ressources. Les sections suivantes décrivent les différentes façons de créer votre projet webforJ pour ce tutoriel.

## Utiliser le code source {#using-source-code}

Le moyen le plus simple de suivre ce tutoriel est de consulter son code source. Vous pouvez télécharger l’ensemble du projet ou le cloner depuis GitHub :

<!-- vale off -->
- Télécharger le fichier ZIP : [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- Dépôt GitHub : cloner le projet [directement depuis GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <VideoPlayer video="tutorial/project-setup" width={1920} height={1080} title="Project setup" /> -->

### Structure du projet {#project-structure}

Le projet comporte six sous-répertoires, un pour chaque étape du tutoriel, et chacun contient une application exécutable. En suivant les étapes, vous pourrez voir l’application évoluer, d’une configuration de base à un système de gestion des clients entièrement fonctionnel.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

<!-- vale off -->
## Utiliser startforJ {#using-startforj}
<!-- vale on -->

Si vous préférez créer un projet, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj) pour générer un projet de démarrage minimal. Consultez [Bien démarrer](/docs/introduction/getting-started) pour en savoir plus sur l’utilisation de startforJ.

:::note Paramètres requis
- Dans la liste déroulante **version de webforJ**, choisissez la version **26.01 ou ultérieure** de webforJ.
- Sous **S’exécute sur**, choisissez **Spring Boot**.

## Utiliser la ligne de commande {#using-command-line}

Vous pouvez également générer un nouveau projet à l’aide de la commande suivante :

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Configurations {#configurations}

Les deux méthodes mentionnées pour créer un nouveau projet utilisent les [archétypes](/docs/building-ui/archetypes/overview) webforJ, qui ajoutent automatiquement à votre projet les configurations nécessaires. Cela comprend les [dépendances](/docs/integrations/spring/spring-boot) Spring, le plugin Maven webforJ qui compile et surveille les sources frontend, ainsi que les propriétés suivantes dans `src/main/resources/application.properties` :

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Exécuter l’application {#running-the-app}

Pour voir l’application en action au fil des étapes du tutoriel :

1. Accédez au répertoire correspondant à l’étape souhaitée. Il s’agit du répertoire de premier niveau de cette étape, qui contient le fichier `pom.xml`.

2. Utilisez la commande Maven suivante pour exécuter l’application Spring Boot localement :
    ```bash
    mvn
    ```

   Le POM généré configure cette commande par défaut pour compiler l’application, démarrer le processus de surveillance du frontend webforJ et exécuter Spring Boot.

<!-- vale Google.WordList = NO -->
L’exécution de l’application ouvre automatiquement une nouvelle fenêtre de navigateur à l’adresse `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
