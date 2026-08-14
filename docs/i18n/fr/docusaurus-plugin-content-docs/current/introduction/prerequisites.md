---
title: Prerequisites
description: >-
  What a webforJ development environment needs, a Java 21 or higher JDK, Maven
  or Gradle, and an editor with Java support.
sidebar_position: 1
_i18n_hash: 038e0cf692852d650329b263c25aaf55
---
Commencer avec webforJ est simple, car il n'y a que quelques prérequis. Utilisez ce guide pour configurer votre environnement de développement avec les outils essentiels dont vous aurez besoin pour démarrer avec webforJ.

## Java Development Kit (JDK) {#java-development-kit-jdk-21}

webforJ nécessite Java **21** ou supérieur. Toute distribution à cette version fonctionne, donc choisissez celle que votre équipe utilise déjà.

:::tip Recommandé pour le développement
Développez sur une version [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Elle accepte l'option `-XX:+AllowEnhancedClassRedefinition`, ce qui permet à un [outil de hotswap](/docs/configuration/deploy-reload/hotswap) d'apporter un changement à la structure d'une classe, un nouveau champ ou une nouvelle méthode, dans l'application en cours d'exécution.

Sur toute autre version, les modifications à l'intérieur d'un corps de méthode s'appliquent toujours sur place, et un changement à la structure d'une classe attend un redémarrage. Le choix ne concerne que la machine sur laquelle vous développez, et cela n'affecte pas ce que vous empaquetez ou où vous le déployez.
:::

Un gestionnaire de version est le moyen le plus simple d'installer un JDK, et le moyen le plus facile de passer entre les versions par la suite. [SDKMAN!](https://sdkman.io/) couvre les systèmes UNIX, et [Jabba](https://github.com/Jabba-Team/jabba) couvre les systèmes UNIX et Windows. Sous SDKMAN!, `sdk install java 21.0.11-jbr` vous procure un JetBrains Runtime.

Pour télécharger une version vous-même :

- **Oracle JDK** : la page des [Téléchargements Java](https://www.oracle.com/java/technologies/downloads/), avec le [guide d'installation d'Oracle](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html).
- **Eclipse Temurin** : la page des [dernières versions](https://adoptium.net/temurin/releases/), avec le [guide d'installation d'Adoptium](https://adoptium.net/installation/).
- **JetBrains Runtime** : la page des [versions](https://github.com/JetBrains/JetBrainsRuntime/releases).

Exécutez `java -version` pour confirmer la version qui est sur votre chemin.

## Build tool {#build-tool}

webforJ se construit avec Maven ou Gradle. Des [Archetypes](/docs/introduction/getting-started) génèrent des projets Maven, donc Maven est le moyen le plus rapide d'obtenir une nouvelle application, et un build Gradle existant fonctionne de la même manière.

<Tabs>
<TabItem value="maven" label="Maven">

Installez Maven depuis la [page de téléchargement d'Apache Maven](https://maven.apache.org/download.cgi), en suivant les [instructions d'installation de Maven](https://maven.apache.org/install.html) ou le [guide de Baeldung pour chaque système d'exploitation](https://www.baeldung.com/install-maven-on-windows-linux-mac).

Exécutez `mvn -v` pour confirmer l'installation.

</TabItem>
<TabItem value="gradle" label="Gradle">

Installez Gradle en suivant le [guide d'installation de Gradle](https://gradle.org/install/).

Exécutez `gradle -v` pour confirmer l'installation. Un projet qui expédie un wrapper Gradle n'a besoin d'aucune installation, car `./gradlew` récupère la version que le projet désigne.

</TabItem>
</Tabs>

Les deux builds exécutent les travaux de construction de webforJ via le [plugin de construction webforJ](/docs/configuration/build-plugin), que possède déjà un projet créé à partir d'un archétype.

## Editor {#java-ide}

Tout éditeur avec support Java fonctionne, donc utilisez celui qui correspond à votre flux de travail. Choix courants :

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)** : Support Java et un écosystème de plugins dès le départ.
- **[Visual Studio Code](https://code.visualstudio.com/Download)** : Un éditeur léger qui obtient son support Java à partir d'extensions.
- **[Zed](https://zed.dev/download)** : Un éditeur de code qui capte Java via une extension, qui télécharge et gère le serveur de langage Java d'Eclipse pour vous.
