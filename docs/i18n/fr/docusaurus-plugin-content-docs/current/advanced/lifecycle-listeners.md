---
sidebar_position: 10
title: Lifecycle Listeners
sidebar_class_name: new-content
_i18n_hash: 475cc2842226c605bbe7f2ee931955dd
---
<!-- vale off -->
# Écouteurs de cycle de vie <DocChip chip='since' label='25.02' />
<!-- vale on -->

L'interface `AppLifecycleListener` permet à un code externe d'observer et de répondre aux événements du cycle de vie de l'application. En implémentant cette interface, vous pouvez exécuter du code à des moments précis lors du démarrage et de l'arrêt de l'application sans modifier la classe `App` elle-même.

Les écouteurs de cycle de vie sont découverts et chargés automatiquement à l'exécution grâce à des fichiers de configuration du fournisseur de services. Chaque instance d'application reçoit son propre ensemble d'instances d'écouteurs, maintenant l'isolement entre les différentes applications exécutées dans le même environnement.

## Quand utiliser des écouteurs de cycle de vie {#when-to-use-lifecycle-listeners}

Utilisez des écouteurs de cycle de vie lorsque vous devez :
- Initialiser des ressources ou des services avant qu'une application ne s'exécute
- Nettoyer des ressources lors de la terminaison d'une application  
- Ajouter des préoccupations transversales sans modifier la classe `App`
- Construire des architectures de plug-ins

## L'interface `AppLifecycleListener` {#the-applifecyclelistener-interface}

```java title="AppLifecycleListener.java"
public interface AppLifecycleListener {
    default void onWillRun(App app) {}
    default void onDidRun(App app) {}
    default void onWillTerminate(App app) {}
    default void onDidTerminate(App app) {}
}
```

:::info Isolement des applications
Chaque instance d'application reçoit son propre ensemble d'instances d'écouteurs :
- Les écouteurs sont isolés entre différentes applications
- Les champs statiques dans les écouteurs ne seront pas partagés entre les applications
- Les instances d'écouteurs sont créées lorsque l'application démarre et détruites lorsqu'elle se termine

Si vous devez partager des données entre des applications, utilisez des mécanismes de stockage externes comme des bases de données ou des services partagés.
:::

### Événements de cycle de vie {#lifecycle-events}

| Événement | Quand est appelé | Utilisations courantes |
|-----------|-----------------|-----------------------|
| `onWillRun` | Avant l'exécution de `app.run()` | Initialiser des ressources, configurer des services |
| `onDidRun` | Après que `app.run()` ait réussi | Démarrer des tâches en arrière-plan, enregistrer le démarrage réussi |
| `onWillTerminate` | Avant la terminaison de l'application | Enregistrer l'état, préparer l'arrêt |
| `onDidTerminate` | Après la terminaison de l'application | Nettoyer des ressources, journaliser finaliser |

## Création d'un écouteur de cycle de vie {#creating-a-lifecycle-listener}

### Implémentation de base {#basic-implementation}

```java title="StartupListener.java"
import com.webforj.App;
import com.webforj.AppLifecycleListener;

public class StartupListener implements AppLifecycleListener {
    
    @Override
    public void onWillRun(App app) {
        System.out.println("Application en cours de démarrage : " + app.getId());
    }
    
    @Override
    public void onDidRun(App app) {
        System.out.println("Application démarrée : " + app.getId());
    }
}
```

### Enregistrement de l'écouteur {#registering-the-listener}

Créez un fichier de configuration de fournisseur de services :

**Fichier** : `src/main/resources/META-INF/services/com.webforj.AppLifecycleListener`

```
com.example.listeners.StartupListener
```

:::tip Utilisation d'AutoService
Il est facile d'oublier de mettre à jour les descripteurs de service. Utilisez [AutoService](https://github.com/google/auto/blob/main/service/README.md) de Google pour générer automatiquement le fichier de service :

```java title="StartupListener.java"
import com.google.auto.service.AutoService;

@AutoService(AppLifecycleListener.class)
public class StartupListener implements AppLifecycleListener {
    // Implémentation
}
```
:::

## Contrôle de l'ordre d'exécution {#controlling-execution-order}

Lorsque plusieurs écouteurs sont enregistrés, vous pouvez contrôler leur ordre d'exécution à l'aide de l'annotation `@AppListenerPriority`. Cela est particulièrement important lorsque les écouteurs ont des dépendances entre eux ou lorsque certaines initialisations doivent se produire avant d'autres.

Les valeurs de priorité fonctionnent par ordre croissant - **les nombres plus bas s'exécutent en premier**. La priorité par défaut est 10, donc les écouteurs sans annotations de priorité explicites s'exécuteront après ceux avec des valeurs de priorité plus basses.

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

### Flux d'exécution avec les hooks de l'App {#execution-flow-with-app-hooks}

Au-delà du contrôle de l'ordre entre plusieurs écouteurs, il est important de comprendre comment les écouteurs interagissent avec les propres hooks de cycle de vie de la classe `App`. Pour chaque événement de cycle de vie, le framework suit une séquence d'exécution spécifique qui détermine quand vos écouteurs s'exécutent par rapport aux hooks intégrés de l'application.

Le diagramme ci-dessous illustre ce flux d'exécution, montrant le timing précis des méthodes `AppLifecycleListener` appelées par rapport aux hooks `App` correspondants : 

<div align="center">

![Écouteurs AppLifecycleListener VS hooks `App` ](/img/lifecycle-listeners.svg)

</div>


## Gestion des erreurs {#error-handling}

Les exceptions levées par les écouteurs sont enregistrées mais n'empêchent pas l'exécution d'autres écouteurs ou le fonctionnement de l'application. Gérez toujours les exceptions au sein de vos écouteurs :

```java title="Exemple de gestion des erreurs"
@Override
public void onWillRun(App app) {
    try {
        riskyInitialization();
    } catch (Exception e) {
        logger.error("Échec de l'initialisation", e);
    }
}
```
