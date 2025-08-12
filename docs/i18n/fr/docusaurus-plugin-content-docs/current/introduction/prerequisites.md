---
title: Prerequisites
sidebar_position: 1
_i18n_hash: 079539f07a72647e2faa9a9a5eda5634
---
Commencer avec webforJ est simple, car il n'y a que quelques prérequis. Utilisez ce guide pour configurer votre environnement de développement avec les outils essentiels dont vous aurez besoin pour démarrer avec webforJ.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Un Java Development Kit (JDK) est le prérequis le plus important pour le développement avec webforJ, fournissant les outils nécessaires pour compiler, exécuter et gérer des applications Java. 
Java **21** est requis pour garantir la compatibilité avec webforJ et accéder aux dernières fonctionnalités et mises à jour de sécurité de l'écosystème Java. Le framework webforJ est compatible avec les JDKs Oracle officiels et les JDKs open source Eclipse Temurin.
<!-- vale off -->
### Liens d'installation du JDK : {#jdk-installation-links}
<!-- vale on -->
:::tip  
Si vous utilisez un système d'exploitation basé sur UNIX, il est recommandé d'utiliser [SDKMAN!](https://sdkman.io/) pour gérer votre environnement Java. Cela vous permet de passer facilement d'un fournisseur Java à un autre sans tracas supplémentaires.  

Alternativement, vous pouvez utiliser [Jabba](https://github.com/shyiko/jabba), qui fonctionne à la fois sur les systèmes basés sur UNIX et Windows. C'est une solution multiplateforme solide pour gérer les versions de Java.  
:::

- Les JDKs Oracle officiels peuvent être trouvés sur la page [Java Downloads](https://www.oracle.com/java/technologies/downloads/) d'Oracle. 
  - Sélectionnez la version Java **21**.
  - Cliquez sur l'onglet pour Linux, macOS ou Windows.
  - Cliquez sur le lien correspondant à l'architecture de votre ordinateur. 
  - Consultez le [Guide d'installation JDK](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) d'Oracle pour des informations complètes sur l'installation d'un JDK Oracle.
- Les JDKs open source peuvent être trouvés sur la page [Eclipse Temurin™ Latest Releases](https://adoptium.net/temurin/releases/) d'Adoptium. 
  - Utilisez les menus déroulants pour sélectionner le système d'exploitation, l'architecture, le type de paquet et la version JDK **21**. 
  - Cliquez sur le lien dans le tableau pour le type d'archive que vous souhaitez télécharger.
  - Consultez le [Guide d'installation](https://adoptium.net/installation/) d'Adoptium pour des informations complètes sur l'installation d'un JDK Eclipse Temurin.

<!-- vale off -->
### Vérifiez votre installation JDK {#verify-your-jdk-installation}
<!-- vale on -->
Après avoir installé le JDK, vérifiez l'installation en exécutant la commande suivante dans votre terminal ou votre invite de commande :

```bash
java -version
```

Si votre JDK est installé correctement, vous verrez une sortie avec les détails de votre version JDK, indiquant la version **21**.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) est un outil d'automatisation de construction et de gestion des dépendances qui simplifie le processus d'inclusion de bibliothèques externes telles que webforJ dans votre projet. 
En plus d'aider à la gestion des dépendances, Maven peut automatiser des tâches comme la compilation de code, l'exécution de tests et le conditionnement d'applications.

### Liens d'installation de Maven {#maven-installation-links}
- Pour installer la dernière version de Maven, allez sur la [Page de téléchargement d'Apache Maven](https://maven.apache.org/download.cgi). 
  - La page [Installer Apache Maven](https://maven.apache.org/install.html) de Maven comporte un aperçu du processus d'installation. 
  - Le guide d'installation plus approfondi de Baeldung [Comment installer Maven sur Windows, Linux et Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) est une ressource utile pour chaque système d'exploitation.

<!-- vale off -->
### Vérifiez votre installation Maven {#verify-your-maven-installation}

<!-- vale on -->

Après avoir installé Maven, vérifiez l'installation en exécutant la commande suivante dans votre terminal ou votre invite de commande :

```bash
mvn -v
```

Si Maven est installé correctement, la sortie devrait afficher la version de Maven, la version de Java et des informations sur le système d'exploitation.

## IDE Java {#java-ide}

Un IDE Java fournit un environnement complet pour écrire, tester et déboguer votre code. Il existe de nombreux IDE parmi lesquels choisir, vous pouvez donc choisir celui qui correspond le mieux à votre flux de travail. Parmi les choix populaires pour le développement Java, on trouve :

- **[Visual Studio Code](https://code.visualstudio.com/Download)** : Un éditeur de code léger et extensible avec un support Java via des plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)** : Connu pour son puissant support Java et son riche écosystème de plugins.
- **[NetBeans](https://netbeans.apache.org/download/index.html)** : Un IDE gratuit et open source pour Java et d'autres langages, connu pour sa facilité d'utilisation et ses modèles de projet intégrés.
