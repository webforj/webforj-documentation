---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 02dbd05d1fdaba50c25155904013471b
---
Pour commencer ce tutoriel, vous avez besoin d'un emplacement pour votre projet où vous pouvez gérer vos classes et ressources. Les sections suivantes décrivent les différentes manières de créer votre projet webforJ pour ce tutoriel.

## Utilisation du code source {#using-source-code}

La manière la plus simple de suivre ce tutoriel est de se référer à son code source. Vous pouvez télécharger l'ensemble du projet ou le cloner depuis GitHub :

<!-- vale off -->
- Télécharger ZIP : [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- Dépôt GitHub : Clonez le projet [directement depuis GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Structure du projet {#project-structure}

Le projet a six sous-répertoires, un pour chaque étape du tutoriel, et chacun contient une application exécutable. Suivre le tutoriel vous permet de voir comment l'application progresse d'une configuration de base à un système de gestion de clients entièrement fonctionnel.

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

## Utilisation de startforJ {#using-startforj}

Si vous préférez créer un nouveau projet, vous pouvez utiliser [startforJ](https://docs.webforj.com/startforj) pour générer un projet de démarrage minimal. Voir [Commencer](/docs/introduction/getting-started) pour plus d'informations détaillées sur l'utilisation de startforJ.

:::note Paramètres requis
- Dans le menu déroulant **version de webforJ**, choisissez la version de webforJ **26.00 ou supérieure**.
- Dans le menu déroulant **Flavor**, choisissez **webforJ + Spring Boot**. 
:::

## Utilisation de la ligne de commande {#using-command-line}

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
  <TabItem value="cmd" label="Invite de commandes">
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

## Configurations

Les deux façons mentionnées de créer un nouveau projet utilisent les [archetypes webforJ](/docs/building-ui/archetypes/overview), qui ajoutent automatiquement les configurations nécessaires à votre projet, comme les [dépendances Spring](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) à votre POM et les propriétés suivantes dans `src/main/resources/application.properties` :

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Exécution de l'application {#running-the-app}

Pour voir l'application en action au fur et à mesure de votre progression dans le tutoriel :

1. Accédez au répertoire de l'étape souhaitée. Cela devrait être le répertoire de niveau supérieur pour cette étape, contenant le `pom.xml`.

2. Utilisez la commande Maven suivante pour exécuter l'application Spring Boot localement :
    ```bash
    mvn
    ```

L'exécution de l'application ouvre automatiquement un nouveau navigateur à `http://localhost:8080`.
