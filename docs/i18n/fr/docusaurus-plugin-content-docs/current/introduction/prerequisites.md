---
title: Prerequisites
description: >-
  Set up a webforJ development environment with JDK 21, Apache Maven, and a
  supported Java IDE such as IntelliJ, VS Code, or NetBeans.
sidebar_position: 1
_i18n_hash: 03fdfcdc58e52eabd51a8f9dbda568e6
---
Commencer avec webforJ est simple, car il n'y a que quelques prérequis. Utilisez ce guide pour configurer votre environnement de développement avec les outils essentiels dont vous aurez besoin pour commencer à travailler avec webforJ.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Un Java Development Kit (JDK) est le prérequis le plus important pour développer avec webforJ, fournissant les outils nécessaires pour compiler, exécuter et gérer des applications Java.  
Java **21** est requis pour garantir la compatibilité avec webforJ et l'accès aux dernières fonctionnalités et mises à jour de sécurité de l'écosystème Java. Le framework webforJ est compatible avec les JDK officiels d'Oracle et les JDK open source d'Eclipse Temurin.
<!-- vale off -->
### JDK installation links: {#jdk-installation-links}
<!-- vale on -->
:::tip
Si vous utilisez un système d'exploitation basé sur UNIX, il est recommandé d'utiliser [SDKMAN!](https://sdkman.io/) pour gérer votre environnement Java. Cela vous permet de changer facilement entre différents fournisseurs Java sans tracas supplémentaires.

Alternativement, vous pouvez utiliser [Jabba](https://github.com/Jabba-Team/jabba), qui fonctionne à la fois sur les systèmes UNIX et Windows. C'est une solution solide multiplateforme pour gérer les versions de Java.
:::

- Les JDK officiels d'Oracle peuvent être trouvés sur la page [Java Downloads](https://www.oracle.com/java/technologies/downloads/) d'Oracle.
  - Sélectionnez la version Java **21**.
  - Cliquez sur l'onglet pour Linux, macOS ou Windows.
  - Cliquez sur le lien correspondant à l'architecture de votre ordinateur.
  - Consultez le [JDK Installation Guide](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) d'Oracle pour des informations complètes sur l'installation d'un JDK Oracle.
- Les JDK open source peuvent être trouvés sur la page [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) d'Adoptium.
  - Utilisez les menus déroulants pour sélectionner le système d'exploitation, l'architecture, le type de package et la version JDK **21**.
  - Cliquez sur le lien dans le tableau pour le type d'archive que vous souhaitez télécharger.
  - Consultez le [Installation Guide](https://adoptium.net/installation/) d'Adoptium pour des informations complètes sur l'installation d'un JDK Eclipse Temurin.

<!-- vale off -->
### Verify your JDK installation {#verify-your-jdk-installation}
<!-- vale on -->
Après avoir installé le JDK, vérifiez l'installation en exécutant la commande suivante dans votre terminal ou invite de commande :

```bash
java -version
```

Si votre JDK est correctement installé, vous verrez une sortie avec les détails de la version de votre JDK, indiquant la version **21**.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) est un outil d'automatisation de construction et de gestion des dépendances qui simplifie le processus d'inclusion de bibliothèques externes telles que webforJ dans votre projet.  
En plus d'aider à la gestion des dépendances, Maven peut automatiser des tâches comme la compilation de code, l'exécution de tests et l'emballage d'applications.

### Maven installation links {#maven-installation-links}
- Pour installer la dernière version de Maven, rendez-vous sur la [Apache Maven Download Page](https://maven.apache.org/download.cgi).
  - La page [Installing Apache Maven](https://maven.apache.org/install.html) de Maven fournit un aperçu du processus d'installation.
  - Le guide plus détaillé de Baeldung sur [Comment installer Maven sur Windows, Linux et Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) est un guide d'installation approfondi pour chaque système d'exploitation.

<!-- vale off -->
### Verify your Maven installation {#verify-your-maven-installation}

<!-- vale on -->

Après avoir installé Maven, vérifiez l'installation en exécutant la commande suivante dans votre terminal ou invite de commande :

```bash
mvn -v
```

Si Maven est correctement installé, la sortie affichera la version de Maven, la version de Java et des informations sur le système d'exploitation.

## Java IDE {#java-ide}

Un IDE Java fournit un environnement complet pour écrire, tester et déboguer votre code. Il existe de nombreux IDE parmi lesquels choisir, vous pouvez donc choisir celui qui convient le mieux à votre flux de travail. Quelques choix populaires pour le développement Java incluent :

- **[Visual Studio Code](https://code.visualstudio.com/Download)** : Un éditeur de code léger et extensible avec un support Java via des plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)** : Connu pour son puissant support Java et son riche écosystème de plugins.
- **[NetBeans](https://netbeans.apache.org/download/index.html)** : Un IDE gratuit et open source pour Java et d'autres langages, connu pour sa facilité d'utilisation et ses templates de projet intégrés.
