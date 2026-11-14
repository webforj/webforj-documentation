---
title: Débogage
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: 6e761f399c4a1f8d562acf85f92af615
---
Le débogage est une étape essentielle du développement Java : il aide les développeurs à identifier et à corriger efficacement les problèmes. Ce guide explique comment configurer le débogage dans webforJ avec Visual Studio Code, IntelliJ IDEA et Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<VideoPlayer video="debugging/vscode" width={1920} height={1080} title="VS Code" />

1. Ouvrez votre projet webforJ dans VS Code.
2. Appuyez sur <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (ou <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> sur Mac) pour ouvrir le panneau Exécuter et déboguer.
3. Cliquez sur « créer un fichier launch.json ».
4. Sélectionnez Java comme environnement.
5. Modifiez `launch.json` comme suit :

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

<VideoPlayer video="debugging/intellij" width={1920} height={1080} title="IntelliJ" />

1. Ouvrez votre projet dans IntelliJ IDEA.
2. Accédez à Exécuter → Modifier les configurations.
3. Cliquez sur le bouton <kbd>+</kbd> et sélectionnez Débogage distant de la JVM.
4. Définissez l’hôte sur `localhost` et le port sur `8000`.
5. Enregistrez la configuration et cliquez sur Déboguer pour vous connecter à l’application en cours d’exécution.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<VideoPlayer video="debugging/eclipse" width={1920} height={1080} title="Eclipse" />

1. Ouvrez votre projet dans Eclipse.
2. Accédez à Exécuter → Modifier les configurations.
3. Sélectionnez Application Java distante.
4. Cliquez sur Nouvelle configuration et définissez :
   - Hôte : `localhost`
   - Port : `8000`
5. Enregistrez et démarrez le débogueur.

</TabItem>
</Tabs>

## Exécuter le débogueur {#running-the-debugger}

Une fois votre IDE configuré :

1. Démarrez votre application webforJ avec `mvnDebug`.
2. Exécutez la configuration de débogage dans votre IDE.
3. Définissez des points d’arrêt et commencez le débogage.

:::tip Conseils de débogage
1. Assurez-vous que le port 8000 est disponible et qu’aucun pare-feu ne le bloque.
2. Si vous utilisez l’un des archétypes webforJ et avez modifié le numéro de port dans le fichier pom.xml, veillez à ce que le port utilisé pour le débogage corresponde à la nouvelle valeur.
:::

## Inspecter l’application en cours d’exécution {#inspecting-the-running-app}

Un débogueur vous montre ce que fait votre code. [craftforJ](/docs/craftforj) vous montre l’application produite par ce code, notamment l’arborescence des composants construite par webforJ, les propriétés de chaque composant, la route active et les personnes autorisées à y accéder. Vous pouvez modifier une propriété, observer le résultat dans l’application en cours d’exécution et répercuter cette modification dans le code Java d’origine.

craftforJ est fourni avec webforJ et utilise le même mode de débogage que celui que vous avez déjà activé, ainsi qu’une propriété supplémentaire :

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Consultez [Premiers pas avec craftforJ](/docs/craftforj/getting-started).
