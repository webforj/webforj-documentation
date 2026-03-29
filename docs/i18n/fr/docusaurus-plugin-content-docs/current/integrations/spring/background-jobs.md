---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 4f924436d02caee3bb07967d7055b0bc
---
Lorsque les utilisateurs cliquent sur un bouton pour générer un rapport ou traiter des données, ils s'attendent à ce que l'interface reste réactive. Les barres de progression doivent s'animer, les boutons doivent réagir au survol, et l'application ne doit pas se figer. L'annotation `@Async` de Spring rend cela possible en déplaçant les opérations de longue durée vers des threads d'arrière-plan.

webforJ impose la sécurité des threads pour les composants de l'UI - toutes les mises à jour doivent se faire sur le thread de l'UI. Cela crée un défi : comment les tâches d'arrière-plan mettent-elles à jour les barres de progression ou affichent-elles les résultats ? La réponse est `Environment.runLater()`, qui transfère en toute sécurité les mises à jour de l'UI des threads d'arrière-plan de Spring vers le thread de l'UI de webforJ.

## Activation de l'exécution asynchrone {#enabling-asynchronous-execution}

L'exécution asynchrone des méthodes de Spring nécessite une configuration explicite. Sans cela, les méthodes annotées avec `@Async` s'exécutent de manière synchrone, contrecarrent leur objectif.

Ajoutez `@EnableAsync` à votre classe d'application Spring Boot :

```java {2}
@SpringBootApplication
@EnableAsync
@Routify(packages = { "com.example.views" })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

L'annotation `@EnableAsync` active l'infrastructure de Spring pour détecter les méthodes `@Async` et les exécuter sur des threads d'arrière-plan.

:::tip[Guide asynchrone de Spring]
Pour une introduction rapide à l'annotation `@Async` de Spring et aux modèles d'utilisation de base, consultez [Création de méthodes asynchrones](https://spring.io/guides/gs/async-method).
:::

## Création de services asynchrones {#creating-async-services}

Les services annotés avec `@Service` peuvent avoir des méthodes marquées avec `@Async` pour s'exécuter sur des threads d'arrière-plan. Ces méthodes retournent généralement un `CompletableFuture` pour permettre une gestion correcte de la complétion et de l'annulation :

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Rapport de progression
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simuler un travail
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Tâche terminée avec succès depuis le service d'arrière-plan !");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Ce service accepte un callback de progression (`Consumer<Integer>`) qui est appelé depuis le thread d'arrière-plan. Le modèle de callback permet au service de signaler la progression sans connaître les composants de l'UI.

La méthode simule une tâche de 5 secondes avec 10 mises à jour de progression. En production, cela serait travail réel comme des requêtes de base de données ou le traitement de fichiers. La gestion des exceptions restaure l'état d'interruption pour prendre en charge une annulation correcte de la tâche lorsque `cancel(true)` est appelé.

## Utilisation des tâches d'arrière-plan dans les vues {#using-background-tasks-in-views}

La vue reçoit le service d'arrière-plan par injection de constructeur :

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Démarrer la tâche d'arrière-plan");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Le service est injecté par Spring
    asyncBtn.addClickListener(e -> {
      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring injecte le `BackgroundService` dans le constructeur de la vue, tout comme tout autre bean Spring. La vue utilise ensuite ce service pour démarrer des tâches d'arrière-plan. Le concept clé : les callbacks du service s'exécutent sur des threads d'arrière-plan, donc toutes les mises à jour de l'UI à l'intérieur de ces callbacks doivent utiliser `Environment.runLater()` pour transférer l'exécution au thread de l'UI.

La gestion de la complétion nécessite la même gestion méticuleuse des threads :

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(() -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("Échec de la tâche : " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

Le callback `whenComplete` s'exécute également sur un thread d'arrière-plan. Chaque opération UI - activer le bouton, cacher la barre de progression, afficher des toasts - doit être enfermée dans `Environment.runLater()`. Sans cet enveloppement, webforJ lance des exceptions car les threads d'arrière-plan ne peuvent pas accéder aux composants de l'UI.

:::warning[Sécurité des threads]
Chaque mise à jour de l'UI à partir d'un thread d'arrière-plan doit être enfermée dans `Environment.runLater()`. Cette règle n'a pas d'exceptions. L'accès direct aux composants depuis des méthodes `@Async` échoue toujours.
:::

:::tip[En savoir plus sur la sécurité des threads]
Pour des informations détaillées sur le modèle de threading de webforJ, le comportement d'exécution et les opérations nécessitant `Environment.runLater()`, consultez [Mises à jour asynchrones](../../advanced/asynchronous-updates).
:::

## Annulation de tâches et nettoyage {#task-cancellation-and-cleanup}

Une gestion appropriée du cycle de vie prévient les fuites de mémoire et les mises à jour d'UI non désirées. La vue stocke la référence de `CompletableFuture` :

```java
private CompletableFuture<String> currentTask;
```

Lorsque la vue est détruite, elle annule toute tâche en cours :

```java
@Override
protected void onDestroy() {
  // Annuler la tâche si la vue est détruite
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

Le paramètre `cancel(true)` est crucial. Il interrompt le thread d'arrière-plan, provoquant des opérations bloquantes comme `Thread.sleep()` qui lancent `InterruptedException`. Cela permet une terminaison immédiate de la tâche. Sans le drapeau d'interruption (`cancel(false)`), la tâche continuerait à s'exécuter jusqu'à ce qu'elle vérifie explicitement l'annulation.

Ce nettoyage prévient plusieurs problèmes :
- Les threads d'arrière-plan continuent de consommer des ressources après que la vue ait disparu.
- Les mises à jour de l'UI tentent de modifier des composants détruits.
- Fuites de mémoire des callbacks maintenant des références aux composants de l'UI.
