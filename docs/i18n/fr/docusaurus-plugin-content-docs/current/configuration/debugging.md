---
title: Débogage
sidebar_position: 1
_i18n_hash: 057e00d21a3392bb3bf8d1fba1dea15f
---
Le débogage est une partie essentielle du développement Java, aidant les développeurs à identifier et à corriger les problèmes de manière efficace. Ce guide explique comment configurer le débogage dans webforJ pour Visual Studio Code, IntelliJ IDEA et Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Ouvrez votre projet webforJ dans VS Code.
2. Appuyez sur <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (ou <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> sur Mac) pour ouvrir le panneau Exécuter et Déboguer.
3. Cliquez sur "créer un fichier launch.json"
4. Sélectionnez Java comme environnement.
5. Modifiez `launch.json` pour correspondre à ce qui suit :

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
2. Accédez à Exécuter → Modifier les configurations.
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
2. Allez à Exécuter → Modifier les configurations.
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
