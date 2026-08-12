---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 1704f647af5396bd4efd4fdbcc4da978
---
Pour commencer ce tutoriel, vous avez besoin d'un emplacement pour votre projet où vous pouvez gérer vos classes et ressources. Les sections suivantes décrivent les différentes manières de créer votre projet webforJ pour ce tutoriel.

## Using the source code {#using-source-code}

La façon la plus simple de suivre ce tutoriel est de se référer à son code source. Vous pouvez télécharger l'intégralité du projet ou le cloner depuis GitHub :

<!-- vale off -->
- Télécharger ZIP : [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- Répertoire GitHub : Clonez le projet [directement depuis GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Project structure {#project-structure}

Le projet a six sous-répertoires, un pour chaque étape du tutoriel, et chacun contient une application exécutable. Suivre les étapes vous permet de voir comment l'application évolue d'une configuration de base à un système de gestion de clients entièrement fonctionnel.

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

## Using startforJ {#using-startforj}

Si vous préférez créer un nouveau projet, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj) pour générer un projet de démarrage minimal. Consultez [Getting Started](/docs/introduction/getting-started) pour plus d'informations détaillées sur l'utilisation de startforJ.

:::note Paramètres requis
- Dans le menu déroulant **version de webforJ**, choisissez la version de webforJ **26.01 ou supérieure**.
- Dans le menu déroulant **Flavor**, choisissez **webforJ + Spring Boot**.

## Using the command line {#using-command-line}

Vous pouvez également générer un nouveau projet avec la commande suivante :

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

Les deux façons mentionnées de créer un nouveau projet utilisent les [archétypes](/docs/building-ui/archetypes/overview) de webforJ, qui ajoutent automatiquement les configurations nécessaires à votre projet. Cela inclut les [dépendances](/docs/integrations/spring/spring-boot) Spring, le plugin Maven webforJ qui construit et surveille les sources frontend, et les propriétés suivantes dans `src/main/resources/application.properties` :

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Running the app {#running-the-app}

Pour voir l'application en action au fur et à mesure que vous progressez dans le tutoriel :

1. Naviguez vers le répertoire de l'étape souhaitée. Cela devrait être le répertoire de premier niveau pour cette étape, contenant le `pom.xml`.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

   Le POM généré configure cette commande par défaut pour compiler l'application, démarrer le surveillant frontend de webforJ et exécuter Spring Boot.

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.
