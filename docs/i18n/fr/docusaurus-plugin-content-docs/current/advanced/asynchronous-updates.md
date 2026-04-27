---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: cbdf51a80355d73a6c7f5ec85cfa198a
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

L'API `Environment.runLater()` fournit un mécanisme pour mettre à jour en toute sécurité l'interface utilisateur depuis des threads en arrière-plan dans les applications webforJ. Cette fonction expérimentale permet des opérations asynchrones tout en maintenant la sécurité des threads pour les modifications de l'interface utilisateur.

<ExperimentalWarning />

## Comprendre le modèle de threads {#understanding-the-thread-model}

webforJ impose un modèle de threading strict où toutes les opérations de l'interface utilisateur doivent se produire dans le thread `Environment`. Cette restriction existe parce que :

1. **Contraintes de l'API webforJ** : L'API webforJ sous-jacente est liée au thread qui a créé la session
2. **Affinité des threads des composants** : Les composants de l'interface utilisateur conservent un état qui n'est pas thread-safe
3. **Dispatch des événements** : Tous les événements de l'interface utilisateur sont traités séquentiellement sur un seul thread

Ce modèle à thread unique empêche les conditions de concurrence et maintient un état cohérent pour tous les composants de l'interface utilisateur, mais crée des défis lorsqu'il s'agit d'intégrer des tâches de calcul asynchrones et de longue durée.

## API `RunLater` {#runlater-api}

L'API `Environment.runLater()` fournit deux méthodes pour planifier des mises à jour de l'interface utilisateur :

```java title="Environment.java"
// Planifiez une tâche sans valeur de retour
public static PendingResult<Void> runLater(Runnable task)

// Planifiez une tâche qui retourne une valeur
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Les deux méthodes retournent un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui suit l'achèvement de la tâche et permet d'accéder au résultat ou à toute exception qui s'est produite.

## Héritage du contexte de thread {#thread-context-inheritance}

L'héritage automatique de contexte est une fonctionnalité critique de `Environment.runLater()`. Lorsqu'un thread s'exécutant dans un `Environment` crée des threads enfants, ceux-ci héritent automatiquement de la capacité d'utiliser `runLater()`.

### Comment fonctionne l'héritage {#how-inheritance-works}

Tout thread créé au sein d'un thread `Environment` a automatiquement accès à cet `Environment`. Cet héritage se produit automatiquement, donc vous n'avez pas besoin de passer de contexte ou de configurer quoi que ce soit.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Ce thread a le contexte de l'Environment
    
    // Les threads enfants héritent automatiquement du contexte
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

Les threads créés en dehors du contexte d'`Environment` ne peuvent pas utiliser `runLater()` et entraîneront une `IllegalStateException` :

```java
// Initialisateur statique - pas de contexte d'Environment
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Lance IllegalStateException
  }).start();
}

// Threads de minuteur système - pas de contexte d'Environment  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Lance IllegalStateException
  }
}, 1000);

// Threads de bibliothèques externes - pas de contexte d'Environment
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Lance IllegalStateException
  });
```

## Comportement d'exécution {#execution-behavior}

Le comportement d'exécution de `runLater()` dépend de quel thread l'appelle :

### Depuis le thread d'interface utilisateur {#from-the-ui-thread}

Lorsqu'il est appelé depuis le thread `Environment` lui-même, les tâches s'exécutent **synchroniquement et immédiatement** :

```java
button.onClick(e -> {
  System.out.println("Avant : " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Intérieur : " + Thread.currentThread().getName());
    return "terminé";
  });
  
  System.out.println("Après : " + result.isDone());  // true
});
```

Avec ce comportement synchronique, les mises à jour de l'interface utilisateur depuis les gestionnaires d'événements sont appliquées immédiatement et n'entraînent pas de surcharge de mise en file d'attente inutile.

### Depuis des threads en arrière-plan {#from-background-threads}

Lorsqu'il est appelé depuis un thread en arrière-plan, les tâches sont **mise en file d'attente pour une exécution asynchrone** :

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Cela s'exécute sur un thread ForkJoinPool
    System.out.println("Arrière-plan : " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Cela s'exécute sur le thread Environment
      System.out.println("Mise à jour de l'UI : " + Thread.currentThread().getName());
      statusLabel.setText("Traitement terminé");
    });
    
    // result.isDone() serait false ici
    // La tâche est mise en file d'attente et s'exécutera de manière asynchrone
  });
}
```

webforJ traite les tâches soumises depuis des threads en arrière-plan dans un **ordre FIFO strict**, préservant la séquence des opérations même lorsqu'elles sont soumises simultanément depuis plusieurs threads. Avec cette garantie d'ordre, les mises à jour de l'interface utilisateur sont appliquées dans l'ordre exact où elles ont été soumises. Donc, si le thread A soumet la tâche 1, puis le thread B soumet la tâche 2, la tâche 1 s'exécutera toujours avant la tâche 2 sur le thread d'interface utilisateur. Le traitement des tâches dans l'ordre FIFO empêche les incohérences dans l'interface utilisateur.

## Annulation de tâche {#task-cancellation}

Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> retourné par `Environment.runLater()` prend en charge l'annulation, vous permettant d'empêcher l'exécution des tâches mises en file d'attente. En annulant les tâches en attente, vous pouvez éviter les fuites de mémoire et empêcher les opérations de longue durée de mettre à jour l'interface utilisateur après qu'elles ne soient plus nécessaires.

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

Lors de l'exécution d'opérations de longue durée avec des mises à jour fréquentes de l'interface utilisateur, suivez tous les résultats en attente :

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
    
    // Annuler toutes les mises à jour UI en attente
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

2. **Prévention des fuites de mémoire** : Suivez et annulez toujours les objets `PendingResult` dans les méthodes de cycle de vie des composants. Les lambdas mises en file d'attente capturent des références à des composants de l'interface utilisateur, empêchant la collecte des ordures si elles ne sont pas annulées.

3. **Exécution FIFO** : Toutes les tâches s'exécutent dans un ordre FIFO strict, quel que soit leur importance. Il n'y a pas de système de priorité.

4. **Limitations d'annulation** : L'annulation empêche uniquement l'exécution des tâches mises en file d'attente. Les tâches déjà en cours d'exécution se termineront normalement.

## Étude de cas complète : `LongTaskView` {#complete-case-study-longtaskview}

Ce qui suit est une mise en œuvre complète, prête pour la production, démontrant toutes les meilleures pratiques pour les mises à jour de l'interface utilisateur asynchrones :

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Utilisez un exécuteur à thread unique pour éviter l'épuisement des ressources
  // Pour la production, envisagez d'utiliser un pool de threads partagé dans toute l'application
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Suivre la tâche actuelle et les mises à jour UI pendantes
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Composants d'interface utilisateur
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Démo des mises à jour UI en arrière-plan");
  private Paragraph descriptionPara = new Paragraph(
      "Cette démonstration montre comment Environment.runLater() permet des mises à jour UI sécurisées depuis des threads en arrière-plan. " +
          "Cliquez sur 'Démarrer une tâche longue' pour exécuter un calcul en arrière-plan de 10 secondes qui met à jour la progression de l'UI. " +
          "Le bouton 'Tester l'UI' prouve que l'UI reste réactive pendant l'opération en arrière-plan.");
  private TextField statusField = new TextField("État");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Résultat");
  private Button startButton = new Button("Démarrer une tâche longue");
  private Button cancelButton = new Button("Annuler la tâche");
  private Button testButton = new Button("Tester l'UI - Cliquez sur moi !");
  private Paragraph footerPara = new Paragraph(
      "Remarque : la tâche peut être annulée à tout moment, démontrant un nettoyage approprié à la fois du " +
          "thread en arrière-plan et des mises à jour UI en queue.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configurer les champs
    statusField.setReadOnly(true);
    statusField.setValue("Prêt à commencer");
    statusField.setLabel("État");

    // Configurer la barre de progression
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progès : {{x}}%");
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

    // Annuler toute tâche en cours et mises à jour UI en attente
    cancelTask();

    // Effacer la référence à la tâche
    currentTask = null;

    // Arrêter l'exécuteur d'instance gracieusement
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Démarrage de la tâche en arrière-plan...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Réinitialiser le drapeau d'annulation et effacer les mises à jour en attente précédentes
    isCancelled = false;
    pendingUIUpdates.clear();

    // Démarrer la tâche en arrière-plan avec exécuteur explicite
    // Remarque : cancel(true) interrompt le thread, ce qui fait que Thread.sleep() lève
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
          Thread.currentThread().interrupt(); // Restaurer l'état interrompu
          return;
        }

        // Faire quelques calculs (déterministe pour la démo)
        // Produit des valeurs entre 0 et 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Mettre à jour la progression depuis le thread en arrière-plan
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Traitement... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Mise à jour finale avec résultat (ce code n'est atteint que si la tâche est terminée sans
      // annulation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tâche terminée !");
          resultField.setValue("Résultat : " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Tâche en arrière-plan terminée !", Theme.SUCCESS);
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

      // Annuler toutes les mises à jour UI en attente
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

Cette mise en œuvre démontre plusieurs modèles critiques :

#### 1. Gestion du pool de threads {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Utilise un **exécuteur à thread unique** pour éviter l'épuisement des ressources
- Crée des **threads de démon** qui ne vont pas empêcher l'arrêt de la JVM

#### 2. Suivi des mises à jour pendantes {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Chaque appel à `Environment.runLater()` est suivi pour permettre :
- L'annulation lorsque l'utilisateur clique sur annuler
- La prévention des fuites de mémoire lors de `onDestroy()`
- Un nettoyage approprié pendant le cycle de vie du composant

#### 3. Annulation coopérative {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Le thread en arrière-plan vérifie ce drapeau à chaque itération, permettant :
- Une réponse immédiate à l'annulation
- Une sortie propre de la boucle
- La prévention de futures mises à jour de l'interface utilisateur

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
Critique pour prévenir les fuites de mémoire en :
- Annulant toutes les mises à jour UI en attente
- Interrompant les threads en cours d'exécution
- Arrêtant l'exécuteur

#### 5. Test de la réactivité de l'UI {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Clic #" + count + " - L'UI est réactive !", Theme.GRAY);
});
```
Démontre que le thread d'interface utilisateur reste réactif pendant les opérations de fond.
