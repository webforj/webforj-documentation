---
sidebar_position: 10
title: Lifecycle Listeners
description: >-
  Hook into webforJ app startup and shutdown phases with AppLifecycleListener to
  initialize services, modify config, or clean up resources.
_i18n_hash: 3ef33ca5104ef421c38d3db16c9fa453
---
<!-- vale off -->
# Écouteurs de Cycle de Vie <DocChip chip='since' label='25.02' />
<!-- vale on -->

L'interface `AppLifecycleListener` permet au code externe d'observer et de réagir aux événements du cycle de vie de l'application. En implémentant cette interface, vous pouvez exécuter du code à des moments spécifiques lors du démarrage et de l'arrêt de l'application sans modifier la classe `App` elle-même.

Les écouteurs de cycle de vie sont automatiquement découverts et chargés à l'exécution par le biais de fichiers de configuration de fournisseur de services. Chaque instance d'application reçoit son propre ensemble d'instances d'écoutéurs, maintenant ainsi l'isolement entre différentes applications fonctionnant dans le même environnement.

## Quand utiliser des écouteurs de cycle de vie {#when-to-use-lifecycle-listeners}

Utilisez des écouteurs de cycle de vie lorsque vous devez :

- Initialiser des ressources ou des services avant l'exécution d'une application
- Nettoyer des ressources lorsque l'application se termine
- Ajouter des préoccupations transversales sans modifier la classe `App`
- Construire des architectures de plugins

## L'interface `AppLifecycleListener` {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
  default void onWillCreate(Environment env) {}     // Depuis 25.03
  default void onDidCreate(App app) {}              // Depuis 25.03
  default void onWillRun(App app) {}
  default void onDidRun(App app) {}
  default void onWillTerminate(App app) {}
  default void onDidTerminate(App app) {}
}
```

:::info Isolation des applications
Chaque instance d'application reçoit son propre ensemble d'instances d'écouteurs :

- Les écouteurs sont isolés entre différentes applications
- Les champs statiques dans les écouteurs ne seront pas partagés entre les applications
- Les instances d'écouteurs sont créées lorsque l'application démarre et détruites lorsqu'elle se termine

Si vous devez partager des données entre des applications, utilisez des mécanismes de stockage externes comme des bases de données ou des services partagés.
:::

### Événements du cycle de vie {#lifecycle-events}

| Événement           | Quand il est appelé                                    | Utilisations courantes                                  |
| ------------------- | ------------------------------------------------------ | ------------------------------------------------------- |
| `onWillCreate`&nbsp;<DocChip chip='since' label='25.03' /> | Après l'initialisation de l'environnement, avant la création de l'application  | Modifier la configuration, fusionner des sources de configuration externes |
| `onDidCreate`&nbsp;<DocChip chip='since' label='25.03' />  | Après l'instanciation de l'application, avant l'initialisation        | Configuration précoce au niveau de l'application, enregistrer des services            |
| `onWillRun`       | Avant l'exécution de `app.run()`                           | Initialiser des ressources, configurer des services            |
| `onDidRun`        | Après que `app.run()` se soit terminé avec succès        | Démarrer des tâches de fond, enregistrer le démarrage réussi      |
| `onWillTerminate` | Avant la terminaison de l'application                                | Sauvegarder l'état, préparer l'arrêt                    |
| `onDidTerminate`  | Après la terminaison de l'application                                 | Nettoyer les ressources, journaliser la dernière fois                   |

## Création d'un écouteur de cycle de vie {#creating-a-lifecycle-listener}

### Implémentation de base {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;
import com.webforj.Environment;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class StartupListener implements AppLifecycleListener {

  @Override
  public void onWillCreate(Environment env) {
    // Modifier la configuration avant la création de l'application
    Config additionalConfig = ConfigFactory.parseString(
      "myapp.feature.enabled = true"
    );
    env.setConfig(additionalConfig);
  }

  @Override
  public void onDidCreate(App app) {
    System.out.println("Application créée : " + app.getId());
  }

  @Override
  public void onWillRun(App app) {
    System.out.println("Application démarrant : " + app.getId());
  }

  @Override
  public void onDidRun(App app) {
    System.out.println("Application démarrée : " + app.getId());
  }
}
```

### Enregistrement de l'écouteur {#registering-the-listener}

Créez un fichier de configuration du fournisseur de services :

**Fichier** : `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Utiliser AutoService
Il est facile d'oublier de mettre à jour les descripteurs de services. Utilisez le [AutoService](https://github.com/google/auto/blob/main/service/README.md) de Google pour générer automatiquement le fichier de service :

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
  // Implémentation
}
```
:::

## Contrôle de l'ordre d'exécution {#controlling-execution-order}

Lorsque plusieurs écouteurs sont enregistrés, vous pouvez contrôler leur ordre d'exécution en utilisant l'annotation `@AppListenerPriority`. Cela est particulièrement important lorsque les écouteurs ont des dépendances entre eux ou lorsque certaines initialisations doivent se produire avant d'autres.

Les valeurs de priorité fonctionnent dans l'ordre croissant - **les nombres plus bas s'exécutent en premier**. La priorité par défaut est 10, donc les écouteurs sans annotations de priorité explicites s'exécuteront après ceux avec des valeurs de priorité plus faibles.

```java title="SecurityListener.java"
@AutoService(AppLifecycleListener.class)
@AppListenerPriority(1)  // S'exécute en premier - configuration de sécurité critique
public class SecurityListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeSecurity();
  }
}

@AutoService(AppLifecycleListener.class)
@AppListenerPriority(10) // Priorité par défaut - journalisation générale
public class LoggingListener implements AppLifecycleListener {
  @Override
  public void onWillRun(App app) {
    initializeLogging();
  }
}
```

### Flux d'exécution avec les crochets de l'application {#execution-flow-with-app-hooks}

Au-delà du contrôle de l'ordre entre plusieurs écouteurs, il est important de comprendre comment les écouteurs interagissent avec les propres crochets de cycle de vie de la classe `App`. Pour chaque événement du cycle de vie, le cadre suit une séquence d'exécution spécifique qui détermine quand vos écouteurs s'exécutent par rapport aux crochets intégrés de l'application.

Le diagramme ci-dessous illustre ce flux d'exécution, montrant le moment précis où les méthodes `AppLifecycleListener` sont appelées par rapport aux crochets correspondants de `App` :

<div align="center">

![Écouteurs AppLifecycleListener VS crochets `App` ](/img/lifecycle-listeners.svg)

</div>


## Gestion des erreurs {#error-handling}

Les exceptions lancées par les écouteurs sont enregistrées mais n'empêchent pas les autres écouteurs de s'exécuter ni l'application de fonctionner. Gérez toujours les exceptions dans vos écouteurs :

```java title="Exemple de gestion des erreurs"
@Override
public void onWillRun(App app) {
  try {
    riskyInitialization();
  } catch (Exception e) {
    logger.error("L'initialisation a échoué", e);
  }
}
```
