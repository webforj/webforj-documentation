---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: ead192e1c1a415742cb0446e2d5c314c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

L'API `Environment.runLater()` fournit un mécanisme pour mettre à jour en toute sécurité l'interface utilisateur à partir de threads d'arrière-plan dans des applications webforJ. Cette fonctionnalité expérimentale permet des opérations asynchrones tout en maintenant la sécurité des threads pour les modifications de l'interface utilisateur.

:::warning API expérimentale
Cette API est marquée comme expérimentale depuis 25.02 et peut changer dans des versions futures. La signature de l'API, le comportement et les caractéristiques de performance sont susceptibles d'être modifiés.
:::

## Compréhension du modèle de thread {#understanding-the-thread-model}

webforJ impose un modèle de threading strict où toutes les opérations d'interface utilisateur doivent se dérouler sur le thread `Environment`. Cette restriction existe parce que :

1. **Contraintes de l'API webforJ** : L'API webforJ sous-jacente se lie au thread qui a créé la session.
2. **Affinité des threads des composants** : Les composants de l'interface utilisateur maintiennent un état qui n'est pas sécurisé pour les threads.
3. **Dispatch d'événements** : Tous les événements de l'interface utilisateur sont traités de manière séquentielle sur un seul thread.

Ce modèle mono-thread évite les conditions de concurrence et maintient un état cohérent pour tous les composants de l'interface utilisateur, mais crée des défis lors de l'intégration avec des tâches de calcul asynchrones et de longue durée.

## API `RunLater` {#runlater-api}

L'API `Environment.runLater()` fournit deux méthodes pour planifier les mises à jour de l'interface utilisateur :

```java title="Environment.java"
// Planifier une tâche sans valeur de retour
public static PendingResult<Void> runLater(Runnable task)

// Planifier une tâche qui retourne une valeur
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Les deux méthodes retournent un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui suit l'achèvement de la tâche et fournit un accès au résultat ou à toute exception survenue.

## Héritage du contexte de thread {#thread-context-inheritance}

L'héritage automatique du contexte est une fonctionnalité critique de `Environment.runLater()`. Lorsqu'un thread s'exécute dans un `Environment` crée des threads enfants, ces enfants héritent automatiquement de la capacité d'utiliser `runLater()`.

### Comment fonctionne l'héritage {#how-inheritance-works}

Tout thread créé à partir d'un thread `Environment` a automatiquement accès à cet `Environment`. Cet héritage se produit automatiquement, donc vous n'avez pas besoin de passer de contexte ou de configurer quoi que ce soit.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Ce thread a le contexte d'Environment
    
    // Les threads enfants héritent du contexte automatiquement
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Peut utiliser runLater car le contexte a été hérité
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Threads sans contexte {#threads-without-context}

Les threads créés en dehors du contexte `Environment` ne peuvent pas utiliser `runLater()` et lanceront une `IllegalStateException` :

```java
// Initialiseur statique - pas de contexte Environment
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Lève IllegalStateException
  }).start();
}

// Threads de minuteur système - pas de contexte Environment  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Lève IllegalStateException
  }
}, 1000);

// Threads de bibliothèque externe - pas de contexte Environment
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Lève IllegalStateException
  });
```

## Comportement d'exécution {#execution-behavior}

Le comportement d'exécution de `runLater()` dépend du thread qui l'appelle :

### Depuis le thread de l'interface utilisateur {#from-the-ui-thread}

Lorsqu'il est appelé depuis le thread `Environment` lui-même, les tâches s'exécutent **synchrones et immédiatement** :

```java
button.onClick(e -> {
  System.out.println("Avant : " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("À l'intérieur : " + Thread.currentThread().getName());
    return "terminé";
  });
  
  System.out.println("Après : " + result.isDone());  // true
});
```

Avec ce comportement synchrone, les mises à jour de l'interface utilisateur à partir des gestionnaires d'événements sont appliquées immédiatement et n'entraînent pas de surcharge de mise en file d'attente inutile.

### Depuis les threads d'arrière-plan {#from-background-threads}

Lorsqu'il est appelé depuis un thread d'arrière-plan, les tâches sont **mis en file d'attente pour une exécution asynchrone** :

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Cela s'exécute sur le thread ForkJoinPool
    System.out.println("Arrière-plan : " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Cela s'exécute sur le thread Environment
      System.out.println("Mise à jour de l'UI : " + Thread.currentThread().getName());
      statusLabel.setText("Traitement terminé");
    });
    
    // result.isDone() serait faux ici
    // La tâche est mise en file d'attente et s'exécutera de manière asynchrone
  });
}
```

webforJ traite les tâches soumises à partir de threads d'arrière-plan dans un **ordre FIFO strict**, préservant la séquence des opérations même lorsqu'elles sont soumises par plusieurs threads simultanément. Avec cette garantie de commande, les mises à jour de l'interface utilisateur sont appliquées dans l'ordre exact dans lequel elles ont été soumises. Donc, si le thread A soumet la tâche 1, et ensuite le thread B soumet la tâche 2, la tâche 1 s'exécutera toujours avant la tâche 2 sur le thread de l'interface utilisateur. Le traitement des tâches dans l'ordre FIFO empêche les incohérences dans l'interface utilisateur.

## Annulation de tâche {#task-cancellation}

Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> retourné par `Environment.runLater()` prend en charge l'annulation, vous permettant d'éviter que des tâches en file d'attente ne s'exécutent. En annulant les tâches en attente, vous pouvez éviter les fuites de mémoire et empêcher les opérations longues de mettre à jour l'interface utilisateur après qu'elles ne soient plus nécessaires.

### Annulation de base {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Annuler si pas encore exécuté
if (!result.isDone()) {
  result.cancel();
}
```

### Gestion de plusieurs mises à jour {#managing-multiple-updates}

Lors de l'exécution d'opérations longues avec des mises à jour fréquentes de l'interface utilisateur, suivez tous les résultats en attente :

```java
public class LongRunningTask {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;
  
  public void startTask() {
    CompletableFuture.runAsync(() -> {
      for (int i = 0; i <= 100; i++) {
        if (isCancelled) return;
        
        final int progress = i;
        PendingResult<Void> update = Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
        
        // Suivre pour une éventuelle annulation
        pendingUpdates.add(update);
        
        Thread.sleep(100);
      }
    });
  }
  
  public void cancelTask() {
    isCancelled = true;
    
    // Annuler toutes les mises à jour de l'UI en attente
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Gestion du cycle de vie des composants {#component-lifecycle-management}

Lorsque les composants sont détruits (par exemple, lors de la navigation), annulez toutes les mises à jour en attente pour éviter les fuites de mémoire :

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Annuler toutes les mises à jour en attente pour éviter les fuites de mémoire
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Considérations de conception {#design-considerations}

1. **Exigence de contexte** : Les threads doivent avoir hérité d'un contexte `Environment`. Les threads de bibliothèques externes, les minuteurs système et les initialisateurs statiques ne peuvent pas utiliser cette API.

2. **Prévention des fuites de mémoire** : Suivez toujours et annulez les objets `PendingResult` dans les méthodes du cycle de vie des composants. Les lambdas en file d'attente capturent des références aux composants de l'interface utilisateur, empêchant la collecte des ordures si elles ne sont pas annulées.

3. **Exécution FIFO** : Toutes les tâches s'exécutent dans un ordre FIFO strict, quelle que soit leur importance. Il n'y a pas de système de priorités.

4. **Limitations de l'annulation** : L'annulation ne prévient que l'exécution des tâches en file d'attente. Les tâches déjà en cours d'exécution se termineront normalement.

## Étude de cas complète : `LongTaskView` {#complete-case-study-longtaskview}

Ce qui suit est une implémentation complète, prête pour la production, démontrant toutes les meilleures pratiques pour des mises à jour de l'interface utilisateur asynchrones :

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Utilisez un exécuteur à thread unique pour éviter l'épuisement des ressources
  // Pour la production, envisagez d'utiliser un pool de threads partagé à l'échelle de l'application
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Suivre la tâche actuelle et les mises à jour de l'UI en attente
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Composants de l'UI
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Démonstration des mises à jour de l'UI en arrière-plan");
  private Paragraph descriptionPara = new Paragraph(
      "Cette démonstration montre comment Environment.runLater() permet des mises à jour sécurisées de l'UI à partir de threads d'arrière-plan. " +
          "Cliquez sur 'Démarrer la tâche longue' pour exécuter un calcul d'arrière-plan de 10 secondes qui met à jour la progression de l'interface. " +
          "Le bouton 'Tester l'UI' prouve que l'interface reste réactive pendant l'opération d'arrière-plan.");
  private TextField statusField = new TextField("Statut");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Résultat");
  private Button startButton = new Button("Démarrer la tâche longue");
  private Button cancelButton = new Button("Annuler la tâche");
  private Button testButton = new Button("Tester l'UI - Cliquez ici !");
  private Paragraph footerPara = new Paragraph(
      "Remarque : la tâche peut être annulée à tout moment, démontrant un nettoyage approprié tant du " +
          "thread d'arrière-plan que des mises à jour de l'UI en file d'attente.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configurer les champs
    statusField.setReadOnly(true);
    statusField.setValue("Prêt à démarrer");
    statusField.setLabel("Statut");

    // Configurer la barre de progression
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progression : {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Résultat");

    // Configurer les boutons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Clic #" + count + " - L'UI est réactive !", Theme.GRAY);
    });

    // Ajouter des composants
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuler toute tâche en cours et les mises à jour de l'UI en attente
    cancelTask();

    // Effacer la référence de la tâche
    currentTask = null;

    // Arrêter l'exécuteur d'instance gracieusement
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Démarrage de la tâche d'arrière-plan...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Réinitialiser le drapeau d'annulation et effacer les mises à jour en attente précédentes
    isCancelled = false;
    pendingUIUpdates.clear();

    // Démarrer la tâche d'arrière-plan avec un exécuteur explicite
    // Remarque : cancel(true) interrompra le thread, provoquant Thread.sleep() à lancer
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuler une tâche longue avec 100 étapes
      for (int i = 0; i <= 100; i++) {
        // Vérifier si annulé
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Tâche annulée !");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("La tâche a été annulée", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 10 secondes au total
        } catch (InterruptedException e) {
          // Le thread a été interrompu - sortir immédiatement
          Thread.currentThread().interrupt(); // Rétablir l'état interrompu
          return;
        }

        // Effectuer un calcul (déterministe pour la démonstration)
        // Produit des valeurs entre 0 et 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Mettre à jour la progression à partir du thread d'arrière-plan
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Traitement... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Mise à jour finale avec résultat (ce code est uniquement atteint si la tâche s'est terminée sans
      // annulation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tâche terminée !");
          resultField.setValue("Résultat : " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Tâche d'arrière-plan terminée !", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Définir le drapeau d'annulation
      isCancelled = true;

      // Annuler la tâche principale (interrompt le thread)
      currentTask.cancel(true);

      // Annuler toutes les mises à jour de l'UI en attente
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Annulation de la tâche...");
        cancelButton.setEnabled(false);

        showToast("Annulation demandée", Theme.GRAY);
      }
    }
  }

  private void showToast(String message, Theme theme) {
    if (!globalToast.isDestroyed()) {
      globalToast.setText(message);
      globalToast.setTheme(theme);
      globalToast.open();
    }
  }
}
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Analyse de l'étude de cas {#case-study-analysis}

Cette implémentation démontre plusieurs modèles critiques :

#### 1. Gestion du pool de threads {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Utilise un **exécuteur à thread unique** pour éviter l'épuisement des ressources
- Crée des **threads de démon** qui ne retiennent pas l'arrêt de la JVM

#### 2. Suivi des mises à jour en attente {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Chaque appel à `Environment.runLater()` est suivi pour permettre :
- L'annulation lorsque l'utilisateur clique sur annuler
- La prévention des fuites de mémoire dans `onDestroy()`
- Un nettoyage approprié pendant le cycle de vie du composant

#### 3. Annulation coopérative {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Le thread d'arrière-plan vérifie ce drapeau à chaque itération, permettant :
- Une réponse immédiate à l'annulation
- Une sortie propre de la boucle
- La prévention de mises à jour de l'UI ultérieures

#### 4. Gestion du cycle de vie {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Réutilise la logique d'annulation
  currentTask = null;
  executor.shutdown();
}
```
Critique pour éviter les fuites de mémoire en :
- Annulant toutes les mises à jour de l'UI en attente
- Interrompant les threads en cours d'exécution
- Arrêtant l'exécuteur

#### 5. Test de réactivité de l'UI {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Clic #" + count + " - L'UI est réactive !", Theme.GRAY);
});
```
Démontre que le thread de l'interface utilisateur reste réactif pendant les opérations en arrière-plan.
