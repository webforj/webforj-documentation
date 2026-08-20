---
title: Debugging
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
sidebar_class_name: updated-content
_i18n_hash: c7b0a48745ef8f5793e38a3dd7691176
---
Le débogage est une partie essentielle du développement Java, aidant les développeurs à identifier et à résoudre les problèmes efficacement. Ce guide explique comment configurer le débogage dans webforJ pour Visual Studio Code, IntelliJ IDEA et Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Ouvrez votre projet webforJ dans VS Code.
2. Appuyez sur <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (ou <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> sur Mac) pour ouvrir le panneau Exécuter et déboguer.
3. Cliquez sur "créer un fichier launch.json"
4. Sélectionnez Java comme environnement.
5. Modifiez `launch.json` pour qu'il corresponde à ce qui suit :

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Attach to Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Enregistrez le fichier et cliquez sur Démarrer le débogage.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Ouvrez votre projet dans IntelliJ IDEA.
2. Allez dans Exécuter → Modifier les configurations.
3. Cliquez sur le bouton <kbd>+</kbd> et sélectionnez Débogage JVM à distance.
4. Définissez l'hôte sur `localhost` et le port sur `8000`.
5. Enregistrez la configuration et cliquez sur Déboguer pour vous connecter à l'application en cours d'exécution.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Ouvrez votre projet dans Eclipse.
2. Allez dans Exécuter → Modifier les configurations.
3. Sélectionnez Application Java à distance.
4. Cliquez sur Nouvelle configuration et définissez :
   - Hôte : `localhost`
   - Port : `8000`
5. Enregistrez et démarrez le débogueur.

</TabItem>
</Tabs>

## Exécution du débogueur {#running-the-debugger}

Une fois que vous avez configuré votre IDE :

1. Démarrez votre application webforJ en utilisant la commande correspondante :
    - Pour Jetty, utilisez `mvnDebug jetty:run`
    - Pour Spring Boot, utilisez `mvnDebug spring-boot:run`
2. Exécutez la configuration de débogage dans votre IDE.
3. Définissez des points d'arrêt et commencez le débogage.

:::tip Conseils de débogage
1. Assurez-vous que le port 8000 est disponible et n'est pas bloqué par un pare-feu.
2. Si vous utilisez l'un des archétypes webforJ et que vous avez changé le numéro de port dans le fichier pom.xml, assurez-vous que le port utilisé pour le débogage correspond à la valeur mise à jour.
:::

## Inspection de l'application en cours d'exécution {#inspecting-the-running-app}

Un débogueur vous montre ce que votre code fait. [craftforJ](/docs/craftforj) vous montre l'application que ce code a produite, y compris l'arborescence des composants que webforJ a construite, les propriétés que chaque composant possède, quelle route est active et qui est autorisé à y accéder. Vous pouvez modifier une propriété, voir le résultat dans l'application en cours d'exécution, et écrire ce changement dans le Java d'où il provient.

craftforJ est livré avec webforJ et utilise le même mode débogage que vous avez déjà activé, plus une propriété supplémentaire :

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Voir [Commencer avec craftforJ](/docs/craftforj/getting-started).
