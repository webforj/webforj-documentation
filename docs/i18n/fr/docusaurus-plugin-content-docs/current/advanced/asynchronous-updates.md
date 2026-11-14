---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Mises à jour asynchrones
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 7fedd93e44d699bba0a959504c7bb40c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

L’API `Environment.runLater()` permet de mettre à jour l’interface utilisateur en toute sécurité depuis des threads d’arrière-plan dans les applications webforJ. Cette fonctionnalité en aperçu permet les opérations asynchrones tout en garantissant la sécurité des modifications de l’interface utilisateur en matière de concurrence entre threads.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Comprendre le modèle de threads {#understanding-the-thread-model}

webforJ impose un modèle strict de gestion des threads : toutes les opérations sur l’interface utilisateur doivent s’effectuer sur le thread `Environment`. Cette restriction s’explique par les raisons suivantes :

1. **Contraintes de l’API webforJ** : l’API webforJ sous-jacente est liée au thread qui a créé la session.
2. **Affinité des composants avec les threads** : les composants d’interface utilisateur conservent un état qui n’est pas sécurisé en cas d’accès concurrent.
3. **Distribution des événements** : tous les événements d’interface utilisateur sont traités séquentiellement sur un seul thread.

Ce modèle à thread unique évite les conditions de concurrence et garantit un état cohérent pour tous les composants d’interface utilisateur, mais complique l’intégration avec des tâches de calcul asynchrones et de longue durée.

Chaque session utilisateur possède son propre `Environment` et son propre thread `Environment`. Un thread d’arrière-plan n’appartient à aucune session ; il faut donc lui indiquer quel `Environment` mettre à jour.

## API `RunLater` {#runlater-api}

L’API `Environment.runLater()` propose deux méthodes pour planifier des mises à jour de l’interface utilisateur. Elles prennent toutes deux l’`Environment` cible en premier argument :

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Les deux méthodes renvoient un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui suit l’achèvement de la tâche et permet d’accéder à son résultat ou aux exceptions qui se sont produites.

:::warning[Surcharges sans cible]
Les surcharges `runLater(Runnable)` et `runLater(Supplier)` déduisent la cible à partir du thread qui a créé le thread appelant. Dans un pool de threads partagé entre plusieurs utilisateurs, chaque tâche s’exécute dans l’`Environment` de l’utilisateur dont l’appel a créé ce thread du pool. Ces surcharges sont obsolètes depuis la version 26.10 et leur suppression est prévue dans la prochaine version majeure. Passez toujours la cible en argument.
:::

## Passer la cible {#passing-the-target}

`Environment.getCurrent()` renvoie l’`Environment` du thread courant. Sur le thread `Environment` — là où s’exécutent les constructeurs, les gestionnaires d’événements et les méthodes du cycle de vie —, cette méthode renvoie l’`Environment` de la session. Sur un thread d’arrière-plan, elle renvoie `null`.

Récupérez la cible là où elle est disponible, avant le démarrage du travail en arrière-plan, puis transmettez-la à ce travail :

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // The constructor runs on the Environment thread
    Environment env = Environment.getCurrent();

    executor.submit(() -> {
      String data = fetchRemoteData();

      Environment.runLater(env, () -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

Comme la cible est passée en argument, l’appel fonctionne depuis n’importe quel thread : un thread créé par la vue, un thread Spring `@Async`, le `ForkJoinPool` commun, un thread de minuterie ou un thread de rappel d’un client HTTP.

### Récupérer la cible depuis le mauvais thread {#reading-the-target-on-the-wrong-thread}

La seule erreur à éviter consiste à appeler `Environment.getCurrent()` depuis le travail en arrière-plan. Dans ce cas, la méthode renvoie `null` et `runLater()` lève une `NullPointerException` :

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Wrong: getCurrent() returns null on a background thread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Il en va de même pour les initialiseurs statiques, les threads `java.util.Timer` et les rappels de bibliothèques externes. Aucun d’eux ne dispose d’un `Environment` ; la cible doit donc provenir d’un champ, d’un paramètre ou d’une variable locale capturée, renseignée sur le thread `Environment`.

## Comportement à l’exécution {#execution-behavior}

Le comportement de `runLater()` dépend du thread qui l’appelle :

### Depuis le thread d’interface utilisateur {#from-the-ui-thread}

Lorsque la méthode est appelée depuis le thread `Environment` de la cible, les tâches s’exécutent **de façon synchrone et immédiate** :

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Before: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Inside: " + Thread.currentThread().getName());
    return "completed";
  });

  System.out.println("After: " + result.isDone());  // true
});
```

Grâce à ce comportement synchrone, les mises à jour de l’interface utilisateur depuis les gestionnaires d’événements sont appliquées immédiatement et n’entraînent pas de surcharge inutile liée à la mise en file d’attente.

### Depuis des threads d’arrière-plan {#from-background-threads}

Lorsqu’elle est appelée depuis tout autre thread, la tâche est **mise en file d’attente pour une exécution asynchrone** :

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // This runs on a ForkJoinPool thread
    System.out.println("Background: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // This runs on the Environment thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Processing complete");
    });

    // result.isDone() would be false here
    // The task is queued and will execute asynchronously
  });
}
```

webforJ traite les tâches soumises depuis des threads d’arrière-plan dans un **ordre FIFO strict**, en préservant la séquence des opérations même lorsque plusieurs threads les soumettent simultanément. Cette garantie d’ordre assure que les mises à jour de l’interface utilisateur sont appliquées exactement dans l’ordre de leur soumission. Ainsi, si le thread A soumet la tâche 1, puis le thread B la tâche 2, la tâche 1 s’exécutera toujours avant la tâche 2 sur le thread d’interface utilisateur. Le traitement des tâches dans l’ordre FIFO évite les incohérences dans l’interface utilisateur.

## Annulation des tâches {#task-cancellation}

Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> renvoyé par `Environment.runLater()` prend en charge l’annulation, ce qui permet d’empêcher l’exécution des tâches en file d’attente. L’annulation des tâches en attente permet d’éviter les fuites de mémoire et d’empêcher les opérations de longue durée de mettre à jour l’interface utilisateur lorsqu’elles ne sont plus nécessaires.

### Annulation de base {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Cancel if not yet executed
if (!result.isDone()) {
  result.cancel();
}
```

### Gestion de plusieurs mises à jour {#managing-multiple-updates}

Lorsque vous effectuez des opérations de longue durée avec des mises à jour fréquentes de l’interface utilisateur, suivez tous les résultats en attente :

```java
public class LongRunningTask {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  public void startTask() {
    Environment env = Environment.getCurrent();

    CompletableFuture.runAsync(() -> {
      for (int i = 0; i <= 100; i++) {
        if (isCancelled) return;

        final int progress = i;
        PendingResult<Void> update = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });

        // Track for potential cancellation
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Cancel all pending UI updates
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

Lorsque les composants sont détruits (par exemple, lors d’une navigation), annulez toutes les mises à jour en attente afin d’éviter les fuites de mémoire :

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel all pending updates to prevent memory leaks
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

1. **Cible obligatoire** : l’`Environment` cible est récupéré avec `Environment.getCurrent()` sur le thread `Environment`, puis transmis au travail en arrière-plan. La méthode renvoie `null` si elle est appelée depuis un thread d’arrière-plan.

2. **Prévention des fuites de mémoire** : suivez et annulez toujours les objets `PendingResult` dans les méthodes du cycle de vie des composants. Les lambdas en file d’attente capturent des références aux composants d’interface utilisateur, ce qui empêche leur récupération par le ramasse-miettes si elles ne sont pas annulées.

3. **Exécution FIFO** : toutes les tâches s’exécutent dans un ordre FIFO strict, quelle que soit leur importance. Il n’existe pas de système de priorité.

4. **Limites de l’annulation** : l’annulation empêche uniquement l’exécution des tâches en file d’attente. Les tâches déjà en cours d’exécution se terminent normalement.

## Étude de cas complète : `LongTaskView` {#complete-case-study-longtaskview}

Voici une implémentation complète, prête pour la production, qui illustre les bonnes pratiques pour les mises à jour asynchrones de l’interface utilisateur :

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Use a single thread executor to prevent resource exhaustion
  // For production, consider using a shared application-wide thread pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Track the current task and pending UI updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI components
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Background UI Updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "This demo shows how Environment.runLater() enables safe UI updates from background threads. " +
          "Click 'Start Long Task' to run a 10-second background computation that updates the UI progress. " +
          "The 'Test UI' button proves the UI remains responsive during the background operation.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Result");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Cancel Task");
  private Button testButton = new Button("Test UI - Click Me!");
  private Paragraph footerPara = new Paragraph(
      "Note: The task can be cancelled at any time, demonstrating proper cleanup of both the " +
          "background thread and queued UI updates.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configure fields
    statusField.setReadOnly(true);
    statusField.setValue("Ready to start");
    statusField.setLabel("Status");

    // Configure progress bar
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progress: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Result");

    // Configure buttons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
    });

    // Add components
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel any running task and pending UI updates
    cancelTask();

    // Clear task reference
    currentTask = null;

    // Shutdown the instance executor gracefully
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starting background task...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset cancelled flag and clear previous pending updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Read the target Environment on the UI thread, before the background task starts
    Environment env = Environment.getCurrent();

    // Start background task with explicit executor
    // Note: cancel(true) will interrupt the thread, causing Thread.sleep() to throw
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulate long task with 100 steps
      for (int i = 0; i <= 100; i++) {
        // Check if cancelled
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
            statusField.setValue("Task cancelled!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Task was cancelled", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 10 seconds total
        } catch (InterruptedException e) {
          // Thread was interrupted - exit immediately
          Thread.currentThread().interrupt(); // Restore interrupted status
          return;
        }

        // Do some calculation (deterministic for demo)
        // Produces values between 0 and 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update progress from background thread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Processing... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Final update with result (this code is only reached if the task completed without
      // cancellation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
          statusField.setValue("Task completed!");
          resultField.setValue("Result: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Background task finished!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Set the cancelled flag
      isCancelled = true;

      // Cancel the main task (interrupts the thread)
      currentTask.cancel(true);

      // Cancel all pending UI updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Cancelling task...");
        cancelButton.setEnabled(false);

        showToast("Cancellation requested", Theme.GRAY);
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
```

</ExpandableCode>

<VideoPlayer video="async/long-tasks" width={1080} height={1288} title="Long tasks" />

<!-- vale on -->

### Analyse de l’étude de cas {#case-study-analysis}

Cette implémentation illustre plusieurs modèles essentiels :

#### 1. Gestion du pool de threads {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Utilise un **exécuteur à thread unique** pour éviter l’épuisement des ressources.
- Crée des **threads démons** qui n’empêcheront pas l’arrêt de la JVM.

#### 2. Nommer la cible {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` s’exécute sur le thread `Environment` ; la cible y est donc récupérée et la tâche d’arrière-plan la transmet à chaque appel de `Environment.runLater()`. Le même code fonctionne sans modification avec un pool de threads partagé à l’échelle de l’application.

#### 3. Suivre les mises à jour en attente {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Chaque appel à `Environment.runLater()` est suivi afin de permettre :
- l’annulation lorsque l’utilisateur clique sur le bouton d’annulation ;
- la prévention des fuites de mémoire dans `onDestroy()` ;
- un nettoyage correct pendant le cycle de vie du composant.

#### 4. Annulation coopérative {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Le thread d’arrière-plan vérifie cet indicateur à chaque itération, ce qui permet :
- de réagir immédiatement à l’annulation ;
- de quitter proprement la boucle ;
- d’empêcher toute nouvelle mise à jour de l’interface utilisateur.

#### 5. Gestion du cycle de vie {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Reuses cancellation logic
  currentTask = null;
  executor.shutdown();
}
```
Cette étape est essentielle pour éviter les fuites de mémoire en :
- annulant toutes les mises à jour de l’interface utilisateur en attente ;
- interrompant les threads en cours d’exécution ;
- arrêtant l’exécuteur.

#### 6. Vérifier la réactivité de l’interface utilisateur {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
Cela montre que le thread d’interface utilisateur reste réactif pendant les opérations en arrière-plan.
