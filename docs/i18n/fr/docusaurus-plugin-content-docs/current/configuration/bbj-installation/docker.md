---
sidebar_position: 1
title: Docker
_i18n_hash: ec7566378c3ec80f071b7391742ec353
---
# Installation de Docker

Cette section de la documentation couvre les étapes nécessaires pour les utilisateurs qui souhaitent développer en utilisant Docker. Les modifications de votre code seront effectuées sur votre machine de développement, et l'application résultante sera exécutée dans Docker.

## 1. Téléchargement de Docker {#1-downloading-docker}

Le processus d'installation de Docker diffère légèrement entre les utilisateurs de Windows, Mac et Linux. Consultez la section ci-dessous qui correspond à votre système d'exploitation.

### Windows {#windows}

:::info
Il est recommandé de télécharger la version la plus récente de Windows Subsystem for Linux. Plus d'informations peuvent être trouvées [à ce lien](https://learn.microsoft.com/en-us/windows/wsl/install).
:::

**1. Télécharger Docker Desktop :**
>- Visitez la page de téléchargement de Docker Desktop pour Windows : [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
>- Cliquez sur le bouton "Get Docker Desktop for Windows" pour télécharger l'installateur.

**2. Installer Docker Desktop :**
>- Exécutez l'installateur que vous avez téléchargé.
>- Suivez l'assistant d'installation, et assurez-vous d'activer Hyper-V (si demandé) car Docker pour Windows utilise Hyper-V pour la virtualisation.
>- Une fois l'installation terminée, Docker Desktop se lancera automatiquement.

**3. Vérifier l'installation :**
>- Ouvrez un terminal et exécutez la commande `docker --version` pour vérifier que Docker est installé et fonctionne correctement.

### Mac {#mac}

**1. Télécharger Docker Desktop :**
>- Visitez la page de téléchargement de Docker Desktop pour Mac : [Docker Desktop for Mac](https://www.docker.com/products/docker-desktop/)

**2. Installer Docker Desktop :**
>- Exécutez l'installateur que vous avez téléchargé.
>- Une fois l'installation terminée, Docker Desktop se lancera automatiquement.

**3. Vérifier l'installation :**
>- Ouvrez un terminal et exécutez la commande `docker --version` pour vérifier que Docker est installé et fonctionne correctement.

## 2. Configuration {#2-configuration}

Une fois Docker Desktop téléchargé, recherchez la dernière image webforJ, qui est actuellement sous le nom `webforj/sandbox`.

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Cliquez sur la liste des balises pour voir les options disponibles.

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Pour la version la plus récente, sélectionnez "rc".

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Tirez l'image pour démarrer votre conteneur.

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Une fois le téléchargement terminé, cliquez sur le bouton exécuter, qui ouvrira les paramètres de configuration.

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Ouvrez le menu "Paramètres optionnels".

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Sélectionnez un port hôte souhaité où vous pourrez voir votre application s'exécuter dans Docker.

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Cliquez sur "Exécuter" pour démarrer le conteneur.

![Recherche d'image DWCJ](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Important
Assurez-vous de noter le numéro de port hôte personnalisé que vous fournissez, car cela sera nécessaire plus tard.
:::

## 3. Exécution de votre application {#3-running-your-app}

Une fois le conteneur créé, les applications webforJ peuvent être exécutées dans le conteneur au lieu de localement. Tout d'abord, il est nécessaire de configurer correctement le fichier POM de votre projet. Une fois cela fait, accéder à une URL spécifique dans le navigateur affichera l'application.

### Configuration de votre fichier POM {#configuring-your-pom-file}

Exécuter un projet webforJ dans le conteneur Docker nécessitera l'utilisation du WebforJ Install Plugin, qui peut être configuré en utilisant votre fichier POM :

Créez une nouvelle entrée `<plugin>` dans la section `<plugins>` du POM. Le code suivant montre une entrée de départ qui peut être utilisée et ajustée selon les besoins de votre projet :

:::important
Si votre fichier POM n'a pas de section `<plugins>`, créez-en une.
:::

```xml
<plugin>
<groupId>com.webforj</groupId>
<artifactId>webforj-install-maven-plugin</artifactId>
<version>${webforj.version}</version>
<executions>
    <execution>
    <goals>
        <goal>install</goal>
    </goals>
    </execution>
</executions>
<configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
</configuration>
</plugin>
```

Une fois une entrée similaire à celle ci-dessus créée, personnalisez les informations suivantes :

- Changez l'entrée `<deployurl>` pour utiliser le numéro de port qui correspond au **Port hôte** que vous avez configuré pour votre conteneur à l'étape précédente.

- Assurez-vous que l'entrée `<classname>` correspond au nom de l'application que vous souhaitez exécuter.

- Si vos identifiants `<username>` et `<password>` sont différents pour votre installation de BBj, modifiez-les.

### Utilisation du projet de démarrage {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Lancement de l'application {#launching-the-app}

Une fois cela fait, exécutez un `mvn install` dans le répertoire de votre projet. Cela exécutera le plugin d'installation webforJ et vous permettra d'accéder à votre application. Pour voir l'application, vous voudrez vous rendre à l'URL suivante :

`http://localhost:YourHostPort/webapp/YourPublishName`

Remplacez `YourHostPort` par le port hôte que vous avez configuré avec Docker, et `YourPublishName` est remplacé par le texte à l'intérieur de la balise `<publishname>` du POM. Si tout est fait correctement, vous devriez voir votre application s'afficher.
