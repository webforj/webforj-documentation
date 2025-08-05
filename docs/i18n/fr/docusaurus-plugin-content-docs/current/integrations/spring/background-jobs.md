---
title: Background Jobs
sidebar_position: 25
_i18n_hash: 6770951556a0f793ce218daeb686b581
---
Lorsque les utilisateurs cliquent sur un bouton pour générer un rapport ou traiter des données, ils s'attendent à ce que l'interface reste réactive. Les barres de progression doivent s'animer, les boutons doivent réagir au survol, et l'application ne doit pas se bloquer. L'annotation `@Async` de Spring rend cela possible en déplaçant les opérations de longue durée vers des threads en arrière-plan.

webforJ impose une sécurité des threads pour les composants de l'interface utilisateur - toutes les mises à jour doivent se faire sur le thread de l'interface utilisateur. Cela crée un défi : comment les tâches en arrière-plan mettent-elles à jour les barres de progression ou affichent les résultats ? La réponse est `Environment.runLater()`, qui transfère en toute sécurité les mises à jour de l'interface utilisateur des threads en arrière-plan de Spring vers le thread de l'interface utilisateur de webforJ.

## Activer l'exécution asynchrone {#enabling-asynchronous-execution}

L'exécution des méthodes asynchrones de Spring nécessite une configuration explicite. Sans cela, les méthodes annotées avec `@Async` s'exécutent de manière synchrone, ce qui va à l'encontre de leur objectif.

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

L'annotation `@EnableAsync` active l'infrastructure de Spring pour détecter les méthodes annotées avec `@Async` et les exécuter sur des threads en arrière-plan.

:::tip[Guide asynchrone de Spring]
Pour une introduction rapide à l'annotation `@Async` de Spring et aux modèles d'utilisation de base, consultez [Créer des méthodes asynchrones](https://spring.io/guides/gs/async-method).
:::

## Création de services asynchrones {#creating-async-services}

Les services annotés avec `@Service` peuvent avoir des méthodes marquées avec `@Async` pour s'exécuter sur des threads en arrière-plan. Ces méthodes retournent généralement `CompletableFuture` pour permettre une gestion appropriée de la complétion et de l'annulation :

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

          // Simuler un travail
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "Tâche terminée avec succès depuis le service en arrière-plan !");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Ce service accepte un rappel de progression (`Consumer<Integer>`) qui est appelé depuis le thread en arrière-plan. Le modèle de rappel permet au service de signaler la progression sans connaître les composants de l'interface utilisateur.

La méthode simule une tâche de 5 secondes avec 10 mises à jour de progression. En production, il pourrait s'agir de travaux réels tels que des requêtes de base de données ou le traitement de fichiers. La gestion des exceptions restaure l'état d'interruption pour prendre en charge l'annulation correcte de la tâche lorsqu'on appelle `cancel(true)`.

## Utilisation des tâches en arrière-plan dans les vues {#using-background-tasks-in-views}

La vue reçoit le service en arrière-plan par injection de constructeur :

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Démarrer la tâche en arrière-plan");
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

Spring injecte le `BackgroundService` dans le constructeur de la vue, tout comme tout autre bean Spring. La vue utilise ensuite ce service pour démarrer des tâches en arrière-plan. Le concept clé : les rappels du service s'exécutent sur des threads en arrière-plan, donc toutes les mises à jour de l'interface utilisateur à l'intérieur de ces rappels doivent utiliser `Environment.runLater()` pour transférer l'exécution au thread de l'interface utilisateur.

La gestion des complétions nécessite la même gestion minutieuse des threads :

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

Le rappel `whenComplete` s'exécute également sur un thread en arrière-plan. Chaque opération d'interface utilisateur - habiliter le bouton, masquer la barre de progression, afficher des toasts - doit être enveloppée dans `Environment.runLater()`. Sans ce wrapping, webforJ génère des exceptions car les threads en arrière-plan ne peuvent pas accéder aux composants de l'interface utilisateur.

:::warning[Sécurité des threads]
Chaque mise à jour de l'interface utilisateur depuis un thread en arrière-plan doit être enveloppée dans `Environment.runLater()`. Cette règle n'a aucune exception. L'accès direct aux composants depuis des méthodes `@Async` échoue toujours.
:::

:::tip[En savoir plus sur la sécurité des threads]
Pour des informations détaillées sur le modèle de thread de webforJ, le comportement d'exécution et les opérations nécessitant `Environment.runLater()`, consultez [Mises à jour asynchrones](../../advanced/asynchronous-updates).
:::

## Annulation de tâches et nettoyage {#task-cancellation-and-cleanup}

Une gestion correcte du cycle de vie prévient les fuites de mémoire et les mises à jour d'interface utilisateur indésirables. La vue stocke la référence `CompletableFuture` :

```java
private CompletableFuture<String> currentTask;
```

Lorsque la vue est détruite, elle annule toute tâche en cours d'exécution :

```java
@Override
protected void onDestroy() {
    // Annuler la tâche si la vue est détruite
    if (currentTask != null && !currentTask.isDone()) {
        currentTask.cancel(true);
    }
}
```

Le paramètre `cancel(true)` est crucial. Il interrompt le thread en arrière-plan, provoquant des opérations de blocage comme `Thread.sleep()` pour lancer `InterruptedException`. Cela permet une terminaison immédiate de la tâche. Sans le drapeau d'interruption (`cancel(false)`), la tâche continuerait à s'exécuter jusqu'à ce qu'elle vérifie explicitement l'annulation.

Ce nettoyage empêche plusieurs problèmes :
- Les threads en arrière-plan continuent à consommer des ressources après que la vue a disparu
- Les mises à jour de l'interface utilisateur tentent de modifier des composants détruits
- Fuites de mémoire causées par des rappels conservant des références à des composants de l'interface utilisateur
