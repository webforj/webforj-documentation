---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: 5fbe602fa83b6147a07c4e2ad8ac3230
---
# Installation Locale

:::danger Obsolète et sera supprimé
Le plugin webforJ du Gestionnaire de Plugins BBj décrit sur cette page est obsolète et sera supprimé dans une future version. Ne commencez pas de nouveaux projets avec. Déplacez les projets existants vers [BBjShip](/docs/configuration/bbjship/overview), qui installe, déploie et exécute les applications webforJ dans BBjServices. [Les projets existants](/docs/configuration/bbjship/existing-projects) liste les modifications nécessaires à un projet.
:::

Cette section de la documentation couvrira les étapes requises uniquement pour les utilisateurs qui souhaitent utiliser webforJ pour le développement web et/ou d'applications avec une instance locale de BBj sur leur machine. Cette installation ne permettra pas aux utilisateurs de contribuer au code lui-même de la fondation webforJ.
<br/>

:::info
Ce guide couvrira l'installation sur un système Windows - les étapes d'installation peuvent varier pour les appareils Mac/Linux.
:::
<br/>

L'installation sera divisée en étapes suivantes:


1. Téléchargement et installation de BBj
2. Utilisation du Gestionnaire de Plugins BBj pour créer votre application
3. Lancement de votre application


:::tip Prérequis
Avant de commencer, assurez-vous d'avoir examiné les [prérequis](../../introduction/prerequisites) nécessaires pour configurer et utiliser webforJ. Cela garantit que vous disposez de tous les outils et configurations nécessaires avant de démarrer votre projet.
:::


## 1. Téléchargement et installation de BBj {#1-bbj-download-and-installation}

<b>Lors de cette étape, assurez-vous d'installer la version de BBj qui correspond à la même version de webforJ.</b><br/><br/>

[Cette vidéo](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) peut vous aider avec l'installation de BBj si vous avez besoin d'aide pour la configuration. La section d'installation du site web de BASIS peut être trouvée [à ce lien](https://basis.cloud/download-product)

:::tip
Il est recommandé d'utiliser la dernière version stable de BBj, et de sélectionner "BBj" dans la liste des options, sans Barista ni Addon.
:::

<a name='section3'></a>

## 2. Installer et configurer le plugin webforJ

Une fois que BBj a été installé, le Gestionnaire de Plugins peut être accédé pour installer les outils nécessaires à la configuration de webforJ. Pour commencer, tapez "Gestionnaire de Plugins" dans le menu Démarrer ou le Finder.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_1l.png#rounded-border)

Une fois dans cette section, sélectionnez la case à cocher "Afficher les versions en développement"

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_2l.png#rounded-border)

L'entrée DWCJ devrait maintenant être visible dans la liste des plugins disponibles à télécharger. Cliquez sur cette entrée dans la liste pour la sélectionner.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_3l.png#rounded-border)

Avec l'entrée DWCJ sélectionnée, cliquez sur le bouton "Installer"

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_4l.png#rounded-border)

Une fois que le plugin a terminé l'installation, cliquez sur l'onglet "Plugins installés" en haut.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_5l.png#rounded-border)

Cet onglet affiche les plugins installés, qui devraient désormais inclure l'entrée DWCJ. Cliquez sur l'entrée dans la liste.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_6l.png#rounded-border)

Avec l'entrée DWCJ sélectionnée, cliquez sur le bouton "Configurer"

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_7l.png#rounded-border)

Dans la fenêtre qui s'ouvre, cliquez sur le bouton "Activer l'installation distante Maven" en bas à gauche de la fenêtre.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip

Alternativement, naviguez vers le répertoire `bin` dans votre dossier `bbx` et exécutez la commande suivante :

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Une boîte de dialogue devrait s'afficher indiquant que l'installation distante a été activée. Cliquez sur "OK" pour fermer cette boîte de dialogue.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_9l.png#rounded-border)


## 3. Utilisation du projet de démarrage
Une fois que BBj et le plugin webforJ requis sont installés et configurés, vous pouvez créer un nouveau projet, scaffolding depuis la ligne de commande. Ce projet est livré avec les outils nécessaires pour exécuter votre premier programme webforJ.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Lancement de l'application

Une fois cela fait, exécutez un `mvn install` dans votre répertoire de projet. Cela exécutera le plugin d'installation webforJ et vous permettra d'accéder à votre application. Pour voir l'application, vous devrez vous rendre à l'URL suivante :

`http://localhost:YourHostPort/webapp/YourPublishName`

Remplacez `YourHostPort` par le port de l'hôte que vous avez configuré avec Docker, et `YourPublishName` par le texte à l'intérieur de la balise `<publishname>` du POM. Si tout est fait correctement, vous devriez voir votre application s'afficher.
