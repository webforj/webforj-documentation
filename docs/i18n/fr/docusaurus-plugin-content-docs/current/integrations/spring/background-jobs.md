---
title: Background Jobs
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 1b265d2e723c0f58c97fd2c4375f15a1
---
Lorsque les utilisateurs cliquent sur un bouton pour générer un rapport ou traiter des données, ils s'attendent à ce que l'interface reste réactive. Les barres de progression doivent s'animer, les boutons doivent réagir au survol et l'application ne doit pas se figer. L'annotation `@Async` de Spring rend cela possible en déplaçant les opérations de longue durée vers des threads d'arrière-plan.

webforJ impose la sécurité des threads pour les composants de l'interface utilisateur - toutes les mises à jour doivent se faire sur le thread de l'interface utilisateur. Cela crée un défi : comment les tâches d'arrière-plan mettent-elles à jour les barres de progression ou affichent-elles des résultats ? La réponse est `Environment.runLater()`, qui transfère en toute sécurité les mises à jour de l'interface utilisateur des threads d'arrière-plan de Spring vers le thread de l'interface utilisateur de webforJ.

## Activer l'exécution asynchrone {#enabling-asynchronous-execution}

L'exécution des méthodes asynchrones de Spring nécessite une configuration explicite. Sans cela, les méthodes annotées avec `@Async` s'exécutent de manière synchrone, annulent leur objectif.

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

:::tip[Guide async de Spring]
Pour une introduction rapide à l'annotation `@Async` de Spring et aux modèles d'utilisation de base, consultez [Créer des méthodes asynchrones](https://spring.io/guides/gs/async-method).
:::

## Création de services asynchrones {#creating-async-services}

Les services annotés avec `@Service` peuvent avoir des méthodes marquées avec `@Async` pour s'exécuter sur des threads d'arrière-plan. Ces méthodes retournent généralement un `CompletableFuture` pour permettre une gestion appropriée des complétions et des annulations :

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

          // Simuler du travail
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

Ce service accepte un rappel de progression (`Consumer<Integer>`) qui est appelé depuis le thread d'arrière-plan. Le modèle de rappel permet au service de signaler la progression sans connaître les composants de l'interface utilisateur.

La méthode simule une tâche de 5 secondes avec 10 mises à jour de progression. En production, il s'agirait de travaux réels comme des requêtes de base de données ou un traitement de fichiers. La gestion des exceptions restaure l'état d'interruption pour soutenir une annulation correcte de la tâche lorsque `cancel(true)` est appelé.

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

Spring injecte le `BackgroundService` dans le constructeur de la vue, tout comme n'importe quel autre bean Spring. La vue utilise alors ce service pour démarrer des tâches d'arrière-plan. Le concept clé : les rappels du service s'exécutent sur des threads d'arrière-plan, donc toutes les mises à jour de l'interface utilisateur à l'intérieur de ces rappels doivent utiliser `Environment.runLater()` pour transférer l'exécution au thread de l'interface utilisateur.

La gestion de la complétion nécessite la même gestion prudente des threads :

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(() -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("La tâche a échoué : " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

Le rappel `whenComplete` s'exécute également sur un thread d'arrière-plan. Chaque opération d'interface utilisateur - activation du bouton, masquage de la barre de progression, affichage des toasts - doit être enveloppée dans `Environment.runLater()`. Sans cet enveloppement, webforJ lance des exceptions car les threads d'arrière-plan ne peuvent pas accéder aux composants de l'interface utilisateur.

:::warning[Sécurité des threads]
Chaque mise à jour de l'interface utilisateur depuis un thread d'arrière-plan doit être enveloppée dans `Environment.runLater()`. Cette règle n'a pas d'exceptions. L'accès direct aux composants depuis des méthodes `@Async` échoue toujours.
:::

:::tip[En savoir plus sur la sécurité des threads]
Pour des informations détaillées sur le modèle de threading de webforJ, le comportement d'exécution, et quelles opérations nécessitent `Environment.runLater()`, consultez [Mises à jour asynchrones](../../advanced/asynchronous-updates).
:::

## Annulation de tâches et nettoyage {#task-cancellation-and-cleanup}

Une gestion appropriée du cycle de vie prévient les fuites de mémoire et les mises à jour indésirables de l'interface utilisateur. La vue stocke la référence `CompletableFuture` :

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

Le paramètre `cancel(true)` est crucial. Il interrompt le thread d'arrière-plan, ce qui provoque des opérations de blocage comme `Thread.sleep()` pour lancer `InterruptedException`. Cela permet une terminaison immédiate de la tâche. Sans le drapeau d'interruption (`cancel(false)`), la tâche continuerait à s'exécuter jusqu'à ce qu'elle vérifie explicitement l'annulation.

Ce nettoyage empêche plusieurs problèmes :
- Les threads d'arrière-plan continuent de consommer des ressources après que la vue ait disparu
- Les mises à jour de l'interface utilisateur tentent de modifier des composants détruits
- Fuites de mémoire dues à des rappels conservant des références aux composants de l'interface utilisateur
