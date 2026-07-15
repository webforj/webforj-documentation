---
sidebar_position: 2
title: Github Codespaces
description: >-
  Run the webforj-hello-world starter in a GitHub Codespace to develop and
  preview webforJ apps directly from the browser.
_i18n_hash: ffbe6dd8d2c6c81e95e7e97dbb8ff32e
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) a été configuré pour fonctionner dans Github Codespaces. Codespaces est un environnement de développement basé sur le cloud, et vous permet de développer et d’exécuter des applications webforJ directement dans votre navigateur. Pour commencer à développer avec cet outil, suivez les étapes ci-dessous :

## 1. Navigate to the HelloWorldJava repository {#1-navigate-to-the-helloworldjava-repository}

Pour commencer, vous devez aller dans le référentiel HelloWorldJava, que vous pouvez trouver [à ce lien](https://github.com/webforj/webforj-hello-world). Une fois là-bas, cliquez sur le bouton vert **"Utiliser ce modèle"**, puis sur l'option **"Ouvrir dans un codespace"**.

![Codespace buttons](/img/bbj-installation/github/1.png#rounded-border)

## 2. Running your program {#2-running-your-program}

Après avoir attendu que le codespace se charge, vous devriez voir une version navigateur de VS Studio Code s'ouvrir avec le programme d'exemple "HelloWorldJava" chargé. À partir de là, vous pouvez exécuter le programme d'exemple ou commencer à développer.

Pour compiler un programme, ouvrez le terminal dans VS Code et exécutez la commande `mvn install`.

![Maven Install](/img/bbj-installation/github/2.png#rounded-border)

Si tout se termine avec succès, vous devriez voir le message `BUILD SUCCESS`.

:::warning WARNING
Assurez-vous d'utiliser la commande `mvn install` au lieu de l'interface Maven intégrée de VS Code pour installer votre programme.
:::

Une fois cela fait, vous devrez naviguer vers une adresse web spécifique pour voir votre programme. Pour ce faire, cliquez d'abord sur l'onglet **"Ports"** en bas de VS Code. Là, vous verrez deux ports, 8888 et un autre, listés.

![Forwarded Ports](/img/bbj-installation/github/3.png#rounded-border)

Cliquez sur le petit bouton **"Ouvrir dans le navigateur"**, ressemblant à un globe, dans la section **"Adresse locale"** de l'onglet **Ports**, ce qui ouvrira un nouvel onglet dans votre navigateur.

![Browser Button](/img/bbj-installation/github/4.png#rounded-border)

Lorsque l'onglet du navigateur est ouvert, vous devez ajouter à la fin de l'URL pour vous assurer que votre application est exécutée. D'abord, ajoutez un `/webapp` à la fin de l'adresse web, qui se terminera par `github.dev`. Ensuite, ajoutez le nom de l'application et de la classe corrects (le cas échéant) pour afficher l'application souhaitée. Pour voir comment configurer correctement l'URL, [suivez ce guide](./configuration).

:::success Tip
Si vous souhaitez exécuter le programme par défaut "Hello World", ajoutez simplement `/hworld` après le `/webapp` dans l'URL :
<br />

![Modified URL](/img/bbj-installation/github/5.png#rounded-border)
:::


Une fois cela fait, vous devriez voir votre application s'exécuter dans le navigateur, et vous pouvez la modifier dans l'instance de VS Code exécutée dans les codespaces.
