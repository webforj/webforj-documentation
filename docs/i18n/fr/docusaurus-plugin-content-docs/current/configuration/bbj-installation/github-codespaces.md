---
sidebar_position: 2
title: Github Codespaces
_i18n_hash: 05f50845efd34767431faacb2e5dc97e
---
import UnderConstruction from '@site/src/components/PageTools/UnderConstruction';

[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) a été configuré pour fonctionner dans Github Codespaces. Codespaces est un environnement de développement basé sur le cloud, et vous permet de développer et d'exécuter des applications webforJ directement dans votre navigateur. Pour commencer à développer avec cet outil, suivez les étapes ci-dessous :

## 1. Accédez au dépôt HelloWorldJava {#1-navigate-to-the-helloworldjava-repository}

Pour commencer, vous devez vous rendre sur le dépôt HelloWorldJava, qui peut être trouvé [à ce lien](https://github.com/webforj/webforj-hello-world). Une fois là, cliquez sur le bouton vert **"Utiliser ce modèle"**, puis sur l'option **"Ouvrir dans un codespace"**.

![Boutons Codespace](/img/bbj-installation/github/1.png#rounded-border)

## 2. Exécuter votre programme {#2-running-your-program}

Après avoir attendu le chargement du codespace, vous devriez voir une version navigateur de VS Studio Code s'ouvrir avec le programme d'exemple "HelloWorldJava" chargé. À partir de là, vous pouvez exécuter le programme d'exemple ou commencer à développer.

Pour compiler un programme, ouvrez le terminal dans VS Code et exécutez la commande `mvn install`.

![Installation Maven](/img/bbj-installation/github/2.png#rounded-border)

Si tout se passe bien, vous devriez voir le message `BUILD SUCCESS`.

:::warning AVERTISSEMENT 
Assurez-vous d'utiliser la commande `mvn install` au lieu de l'interface Maven intégrée de VS Code pour installer votre programme.
:::

Une fois cela fait, vous devrez naviguer vers une adresse web spécifique pour voir votre programme. Pour ce faire, cliquez d'abord sur l'onglet **"Ports"** en bas de VS Code. Ici, vous verrez deux ports, 8888 et un autre, listés.

![Ports Redirigés](/img/bbj-installation/github/3.png#rounded-border)

Cliquez sur le petit bouton **"Ouvrir dans le navigateur"**, ressemblant à un globe, dans la section **"Adresse locale"** de l'onglet **Ports**, ce qui ouvrira un nouvel onglet dans votre navigateur.

![Bouton du Navigateur](/img/bbj-installation/github/4.png#rounded-border)

Lorsque l'onglet du navigateur s'ouvre, vous devrez ajouter à la fin de l'URL pour vous assurer que votre application est exécutée. D'abord, ajoutez un `/webapp` à la fin de l'adresse web, qui se terminera par `github.dev`. Ensuite, ajoutez le nom d'application et de classe correct (le cas échéant) pour afficher l'application désirée. Pour voir comment configurer correctement l'URL, [suivez ce guide](./configuration).

:::success Astuce
Si vous souhaitez exécuter le programme par défaut "Hello World", ajoutez simplement `/hworld` après le `/webapp` dans l'URL :
<br />

![URL Modifiée](/img/bbj-installation/github/5.png#rounded-border)
:::

Une fois cela fait, vous devriez voir votre application fonctionnant dans le navigateur et pouvoir la modifier dans l'instance de VS Code s'exécutant dans les codespaces.
