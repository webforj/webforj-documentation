---
title: Tâches en arrière-plan
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 2760d6db6a7b7045e7612ca359d04a28
---
Lorsque les utilisateurs cliquent sur un bouton pour générer un rapport ou traiter des données, ils s’attendent à ce que l’interface reste réactive. Les barres de progression doivent s’animer, les boutons réagir au survol et l’application ne doit pas se figer. L’annotation `@Async` de Spring permet cela en déplaçant les opérations de longue durée vers des threads d’arrière-plan.

webforJ garantit la sécurité des threads pour les composants d’interface : toutes les mises à jour doivent s’effectuer sur le thread d’interface. Cela pose un problème : comment les tâches d’arrière-plan peuvent-elles mettre à jour les barres de progression ou afficher les résultats ? La réponse est `Environment.runLater()`, qui transfère en toute sécurité les mises à jour de l’interface depuis les threads d’arrière-plan de Spring vers le thread d’interface de l’utilisateur qui a démarré la tâche.

## Activer l’exécution asynchrone {#enabling-asynchronous-execution}

L’exécution asynchrone des méthodes de Spring nécessite une configuration explicite. Sans celle-ci, les méthodes annotées avec `@Async` s’exécutent de manière synchrone, ce qui va à l’encontre de leur objectif.

Ajoutez `@EnableAsync` à la classe de votre application Spring Boot :

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

L’annotation `@EnableAsync` active le mécanisme de Spring qui détecte les méthodes `@Async` et les exécute sur des threads d’arrière-plan.

:::tip[Guide sur l’asynchronisme avec Spring]
Pour une brève introduction à l’annotation `@Async` de Spring et aux modèles d’utilisation de base, consultez [Créer des méthodes asynchrones](https://spring.io/guides/gs/async-method).
:::

## Créer des services asynchrones {#creating-async-services}

Les services annotés avec `@Service` peuvent comporter des méthodes marquées avec `@Async` pour les exécuter sur des threads d’arrière-plan. Ces méthodes renvoient généralement un `CompletableFuture` afin de permettre une gestion adéquate de leur achèvement et de leur annulation :

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Signaler la progression
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simuler le travail
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "La tâche du service d’arrière-plan s’est terminée avec succès !");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Ce service accepte un rappel de progression (`Consumer<Integer>`) qui est appelé depuis le thread d’arrière-plan. Ce modèle de rappel permet au service de signaler sa progression sans connaître les composants d’interface.

La méthode simule une tâche de 5 secondes avec 10 mises à jour de progression. En production, il s’agirait d’un véritable travail, comme des requêtes de base de données ou le traitement de fichiers. La gestion des exceptions rétablit le statut d’interruption afin de permettre une annulation correcte de la tâche lorsque `cancel(true)` est appelé.

## Utiliser des tâches d’arrière-plan dans les vues {#using-background-tasks-in-views}

La vue reçoit le service d’arrière-plan par injection de constructeur :

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Démarrer la tâche d’arrière-plan");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Le service est injecté par Spring
    asyncBtn.addClickListener(e -> {
      // Le gestionnaire de clic s’exécute sur le thread d’interface de cet utilisateur
      Environment env = Environment.getCurrent();

      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring injecte `BackgroundService` dans le constructeur de la vue, comme n’importe quel autre bean Spring. La vue utilise ensuite ce service pour démarrer des tâches d’arrière-plan. Le concept clé : les rappels du service s’exécutent dans le pool de threads de Spring, partagé par tous les utilisateurs de l’application. Un thread du pool ne peut donc pas savoir quelle interface utilisateur mettre à jour. Le gestionnaire de clic récupère l’`Environment` de l’utilisateur avec `Environment.getCurrent()` tant qu’il s’exécute encore sur le thread d’interface, puis chaque mise à jour de l’interface dans les rappels lui transmet cet `Environment` à `Environment.runLater()`.

La gestion de l’achèvement nécessite la même attention à la gestion des threads :

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(env, () -> {
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

Le rappel `whenComplete` s’exécute lui aussi sur un thread d’arrière-plan. Chaque opération sur l’interface — activer le bouton, masquer la barre de progression, afficher des notifications — doit être encapsulée dans `Environment.runLater()` avec le même `env`. Sans cette encapsulation, webforJ lève des exceptions, car les threads d’arrière-plan ne peuvent pas accéder aux composants d’interface.

:::warning[Sécurité des threads]
Toute mise à jour de l’interface depuis un thread d’arrière-plan doit être encapsulée dans `Environment.runLater()`, avec l’`Environment` récupéré sur le thread d’interface avant le démarrage de la tâche. Cette règle ne souffre aucune exception. L’accès direct aux composants depuis des méthodes `@Async` échoue systématiquement, et `Environment.getCurrent()` renvoie `null` sur un thread du pool.
:::

:::tip[En savoir plus sur la sécurité des threads]
Pour en savoir plus sur le modèle de gestion des threads de webforJ, le comportement d’exécution et les opérations qui nécessitent `Environment.runLater()`, consultez [Mises à jour asynchrones](../../advanced/asynchronous-updates).
:::

## Annuler les tâches et effectuer le nettoyage {#task-cancellation-and-cleanup}

Une gestion adéquate du cycle de vie prévient les fuites de mémoire et les mises à jour indésirables de l’interface. La vue conserve une référence au `CompletableFuture` :

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

Le paramètre `cancel(true)` est crucial. Il interrompt le thread d’arrière-plan, ce qui fait que les opérations bloquantes comme `Thread.sleep()` lèvent une `InterruptedException`. La tâche peut ainsi être interrompue immédiatement. Sans le signal d’interruption (`cancel(false)`), la tâche continuerait jusqu’à ce qu’elle vérifie explicitement si elle doit être annulée.

Ce nettoyage évite plusieurs problèmes :
- Les threads d’arrière-plan continuent de consommer des ressources après la fermeture de la vue.
- Les mises à jour de l’interface tentent de modifier des composants détruits.
- Des fuites de mémoire surviennent lorsque des rappels conservent des références aux composants d’interface.
