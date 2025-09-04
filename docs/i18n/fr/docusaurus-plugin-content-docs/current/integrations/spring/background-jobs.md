---
title: Background Jobs
sidebar_position: 25
_i18n_hash: d419b53d933af4ef48890b8be2eab4dc
---
Lorsque les utilisateurs cliquent sur un bouton pour générer un rapport ou traiter des données, ils s'attendent à ce que l'interface reste réactive. Les barres de progression doivent s'animer, les boutons doivent réagir au survol et l'application ne doit pas se bloquer. L'annotation `@Async` de Spring rend cela possible en déplaçant les opérations longues vers des threads en arrière-plan.

webforJ impose la sécurité des threads pour les composants UI - toutes les mises à jour doivent se faire sur le thread d'interface utilisateur. Cela crée un défi : comment les tâches d'arrière-plan peuvent-elles mettre à jour les barres de progression ou afficher des résultats ? La réponse est `Environment.runLater()`, qui transfère en toute sécurité les mises à jour de l'interface utilisateur des threads d'arrière-plan de Spring au thread d'interface utilisateur de webforJ.

## Activation de l'exécution asynchrone {#enabling-asynchronous-execution}

L'exécution des méthodes asynchrones de Spring nécessite une configuration explicite. Sans cela, les méthodes annotées avec `@Async` s'exécutent de manière synchrone, ce qui contredit leur objectif.

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

L'annotation `@EnableAsync` active l'infrastructure de Spring pour détecter les méthodes `@Async` et les exécuter sur des threads en arrière-plan.

:::tip[Guide asynchrone de Spring]
Pour une introduction rapide à l'annotation `@Async` de Spring et aux motifs d'utilisation de base, consultez [Créer des méthodes asynchrones](https://spring.io/guides/gs/async-method).
:::

## Création de services asynchrones {#creating-async-services}

Les services annotés avec `@Service` peuvent avoir des méthodes marquées avec `@Async` pour s'exécuter sur des threads en arrière-plan. Ces méthodes retournent généralement `CompletableFuture` pour permettre un traitement approprié des complétions et des annulations :

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

Ce service accepte un rappel de progression (`Consumer<Integer>`) qui est appelé depuis le thread d'arrière-plan. Le modèle de rappel permet au service de rapporter la progression sans avoir connaissance des composants UI.

La méthode simule une tâche de 5 secondes avec 10 mises à jour de progression. En production, il s'agirait d'un travail réel comme des requêtes de base de données ou des traitements de fichiers. La gestion des exceptions restaure l'état d'interruption pour permettre une annulation appropriée de la tâche lorsque `cancel(true)` est appelé.

## Utilisation des tâches en arrière-plan dans les vues {#using-background-tasks-in-views}

La vue reçoit le service d'arrière-plan par injection par le constructeur :

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Démarrer la tâche d'arrière-plan");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Service injecté par Spring
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

Spring injecte le `BackgroundService` dans le constructeur de la vue, tout comme n'importe quel autre bean Spring. La vue utilise ensuite ce service pour démarrer des tâches d'arrière-plan. Le concept clé : les rappels du service s'exécutent sur des threads d'arrière-plan, donc toutes les mises à jour de l'UI à l'intérieur de ces rappels doivent utiliser `Environment.runLater()` pour transférer l'exécution au thread de l'UI.

La gestion des complétions nécessite la même gestion minutieuse des threads :

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

Le rappel `whenComplete` s'exécute également sur un thread d'arrière-plan. Chaque opération UI - activation du bouton, masquage de la barre de progression, affichage des toasts - doit être encapsulée dans `Environment.runLater()`. Sans cette encapsulation, webforJ génère des exceptions car les threads d'arrière-plan ne peuvent pas accéder aux composants UI.

:::warning[Sécurité des threads]
Chaque mise à jour de l'UI depuis un thread d'arrière-plan doit être encapsulée dans `Environment.runLater()`. Cette règle n'a aucune exception. L'accès direct aux composants depuis des méthodes `@Async` échoue toujours.
:::

:::tip[En savoir plus sur la sécurité des threads]
Pour des informations détaillées sur le modèle de threading de webforJ, le comportement d'exécution et les opérations nécessitant `Environment.runLater()`, consultez [Mises à jour asynchrones](../../advanced/asynchronous-updates).
:::

## Annulation des tâches et nettoyage {#task-cancellation-and-cleanup}

Une gestion appropriée du cycle de vie prévient les fuites de mémoire et les mises à jour d'interface indésirables. La vue stocke la référence `CompletableFuture` :

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

Le paramètre `cancel(true)` est crucial. Il interrompt le thread d'arrière-plan, causant des opérations de blocage comme `Thread.sleep()` à lancer `InterruptedException`. Cela permet une terminaison immédiate de la tâche. Sans le drapeau d'interruption (`cancel(false)`), la tâche continuerait à s'exécuter jusqu'à ce qu'elle vérifie explicitement l'annulation.

Ce nettoyage prévient plusieurs problèmes :
- Les threads d'arrière-plan continuent à consommer des ressources après que la vue soit disparue
- Les mises à jour de l'UI tentent de modifier des composants détruits
- Les fuites de mémoire des rappels tenant des références à des composants UI
