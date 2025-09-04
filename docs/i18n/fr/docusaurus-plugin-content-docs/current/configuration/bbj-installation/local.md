---
sidebar_position: 4
_i18n_hash: a41d592f84a4dcd32f5398f3e57621a4
---
# Installation Locale

Cette section de la documentation couvre les étapes requises uniquement pour les utilisateurs qui souhaitent utiliser webforJ pour le développement web et/ou d'application avec une instance BBj locale sur leur machine. Cette installation ne permettra pas aux utilisateurs de contribuer au code de base de webforJ.
<br/>

:::info
Ce guide traitera de l'installation sur un système Windows - les étapes d'installation peuvent varier pour les dispositifs Mac/Linux.
:::
<br/>

L'installation sera décomposée en les étapes suivantes :

1. Téléchargement et installation de BBj
2. Utilisation du Plugin Manager BBj pour créer votre application
3. Lancement de votre application

:::tip Prérequis
Avant de commencer, assurez-vous d'avoir consulté les [prérequis](../../introduction/prerequisites) nécessaires pour configurer et utiliser webforJ. Cela garantit que vous disposez de tous les outils et configurations requis avant de commencer votre projet.
:::


## 1. Télécharger et installer BBj {#1-bbj-download-and-installation}

<b>Lors de cette étape, assurez-vous d'installer la version de BBj qui correspond à la même version de webforJ.</b><br/><br/>

[Cette vidéo](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) peut vous aider avec l'installation de BBj si vous avez besoin d'assistance pour la configuration. La section d'installation du site Web de BASIS se trouve [à ce lien](https://basis.cloud/download-product)

:::tip
Il est recommandé d'utiliser la dernière version stable de BBj, et de sélectionner "BBj" parmi les options, sans Barista ni Addon.
:::

<a name='section3'></a>

## 2. Installer et configurer le plugin webforJ

Une fois BBj installé, le Plugin Manager peut être accessible pour installer les outils nécessaires à la configuration de webforJ. Pour commencer, tapez "Plugin Manager" dans le menu de démarrage ou Finder. 

Après avoir ouvert le plugin manager, naviguez vers l'onglet "Plugins disponibles" en haut.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Une fois dans cette section, cochez la case "Afficher les versions en développement"

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

L'entrée DWCJ devrait maintenant être visible dans la liste des plugins disponibles au téléchargement. Cliquez sur cette entrée dans la liste pour la sélectionner.

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

Avec l'entrée DWCJ sélectionnée, cliquez sur le bouton "Installer"

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Une fois le plugin installé, cliquez sur l'onglet "Plugins installés" en haut.

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Cet onglet affiche les plugins installés, qui devraient maintenant inclure l'entrée DWCJ. Cliquez sur l'entrée dans la liste.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Avec l'entrée DWCJ sélectionnée, cliquez sur le bouton "Configurer"

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Dans la fenêtre qui s'ouvre, cliquez sur le bouton "Activer l'installation distante Maven" en bas à gauche de la fenêtre.

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip 

Alternativement, naviguez vers le répertoire `bin` dans votre dossier `bbx` et exécutez la commande suivante :

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Une boîte de dialogue devrait afficher que l'installation distante a été activée. Cliquez sur "OK" pour fermer cette boîte de dialogue.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

## 3. Utiliser le projet de démarrage
Une fois BBj et le plugin webforJ requis installés et configurés, vous pouvez créer un nouveau projet structurel depuis la ligne de commande. Ce projet est doté des outils nécessaires pour exécuter votre premier programme webforJ.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Lancer l'application

Une fois cela fait, exécutez `mvn install` dans votre répertoire de projet. Cela exécutera le plugin d'installation de webforJ et vous permettra d'accéder à votre application. Pour voir l'application, vous devez vous rendre à l'URL suivante :

`http://localhost:YourHostPort/webapp/YourPublishName`

Remplacez `YourHostPort` par le port d'hôte que vous avez configuré avec Docker, et `YourPublishName` est remplacé par le texte à l'intérieur de la balise `<publishname>` du POM. 
Si cela est fait correctement, vous devriez voir votre application s'afficher.
