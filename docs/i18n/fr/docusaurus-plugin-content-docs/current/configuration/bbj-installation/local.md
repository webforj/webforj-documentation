---
sidebar_position: 4
_i18n_hash: 9cf48c2124910b9a10a8ec4c5cd82205
---
# Installation locale

Cette section de la documentation couvrira les étapes nécessaires uniquement pour les utilisateurs qui souhaitent utiliser webforJ pour le développement web et/ou d'applications avec une instance BBj locale sur leur machine. Cette installation ne permettra pas aux utilisateurs de contribuer au code de la fondation webforJ.
<br/>

:::info
Ce guide traitera de l'installation sur un système Windows - les étapes d'installation peuvent varier pour les appareils Mac/Linux.
:::
<br/>

L'installation sera divisée en les étapes suivantes :


1. Téléchargement et installation de BBj
2. Utilisation du Gestionnaire de plugins BBj pour créer votre application
3. Lancement de votre application


:::tip Prérequis
Avant de commencer, assurez-vous d'avoir passé en revue les [prérequis](../../introduction/prerequisites) nécessaires pour configurer et utiliser webforJ. Cela garantit que vous disposez de tous les outils et configurations requis avant de commencer votre projet.
:::


## 1. Téléchargement et installation de BBj {#1-bbj-download-and-installation}

<b>Tout en suivant cette étape, assurez-vous d'installer la version de BBj qui correspond à la même version de webforJ. </b><br/><br/>

[Cette vidéo](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) peut vous aider avec l'installation de BBj si vous avez besoin d'aide pour la configuration. La section d'installation du site web de BASIS peut être trouvée [à ce lien](https://basis.cloud/download-product)

:::tip
Il est recommandé d'utiliser la dernière version stable de BBj et de sélectionner "BBj" dans la liste des options, sans Barista ni Addon.
:::

<a name='section3'></a>

## 2. Installer et configurer le plugin webforJ

Une fois BBj installé, le Gestionnaire de plugins peut être accessible pour installer les outils nécessaires à la configuration de webforJ. Pour commencer, tapez "Gestionnaire de plugins" dans le menu de démarrage ou le Finder.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_1l.png#rounded-border)

Une fois dans cette section, sélectionnez la case à cocher "Afficher les versions en cours de développement"

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_2l.png#rounded-border)

L'entrée DWCJ devrait maintenant être visible dans la liste des plugins disponibles à télécharger. Cliquez sur cette entrée dans la liste pour la sélectionner.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_3l.png#rounded-border)

Avec l'entrée DWCJ sélectionnée, cliquez sur le bouton "Installer"

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_4l.png#rounded-border)

Une fois le plugin installé, cliquez sur l'onglet "Plugins installés" en haut.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_5l.png#rounded-border)

Cet onglet affiche les plugins installés, qui devraient maintenant inclure l'entrée DWCJ. Cliquez sur l'entrée dans la liste.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_6l.png#rounded-border)

Avec l'entrée DWCJ sélectionnée, cliquez sur le bouton "Configurer"

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_7l.png#rounded-border)

Dans la fenêtre qui s'ouvre, cliquez sur le bouton "Activer l'installation distante Maven" en bas à gauche de la fenêtre.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip 

Alternativement, naviguez vers le répertoire `bin` de votre dossier `bbx` et exécutez la commande suivante :

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Une boîte de dialogue devrait s'afficher indiquant que l'installation distante a été activée. Cliquez sur "OK" pour fermer cette boîte de dialogue.

![Configuration du gestionnaire de plugins](/img/bbj-installation/local/Step_9l.png#rounded-border)

## 3. Utiliser le projet de démarrage
Une fois BBj et le plugin webforJ requis installés et configurés, vous pouvez créer un nouveau projet structuré depuis la ligne de commande. Ce projet est livré avec les outils nécessaires pour exécuter votre premier programme webforJ.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Lancer l'application

Une fois cela fait, exécutez un `mvn install` dans votre répertoire de projet. Cela exécutera le plugin d'installation webforJ et vous permettra d'accéder à votre application. Pour voir l'application, vous devrez accéder à l'URL suivante :

`http://localhost:YourHostPort/webapp/YourPublishName`

Remplacez `YourHostPort` par le port d'hôte que vous avez configuré avec Docker, et `YourPublishName` est remplacé par le texte à l'intérieur de la balise `<publishname>` du POM. 
Si tout est fait correctement, vous devriez voir votre application se rendre.
