---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Mises à jour asynchrones
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 96865999eae550e8c20172458695ee01
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

L’API `Environment.runLater()` fournit un mécanisme permettant de mettre à jour l’interface utilisateur en toute sécurité depuis des threads d’arrière-plan dans les applications webforJ. Cette fonctionnalité en préversion permet d’effectuer des opérations asynchrones tout en garantissant la sécurité des modifications de l’interface utilisateur du point de vue des threads.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Comprendre le modèle de threads {#understanding-the-thread-model}

webforJ impose un modèle de threads strict, dans lequel toutes les opérations de l’interface utilisateur doivent s’exécuter sur le thread `Environment`. Cette restriction s’explique par les raisons suivantes :

1. **Contraintes de l’API webforJ** : l’API webforJ sous-jacente est liée au thread qui a créé la session
2. **Affinité des composants avec un thread** : les composants de l’interface utilisateur conservent un état qui n’est pas sûr pour une utilisation concurrente
3. **Distribution des événements** : tous les événements de l’interface utilisateur sont traités séquentiellement sur un seul thread

Ce modèle à thread unique évite les conditions de concurrence et garantit un état cohérent pour tous les composants de l’interface utilisateur, mais il complique l’intégration avec des tâches de calcul asynchrones et de longue durée.

Chaque session utilisateur possède son propre `Environment` et son propre thread `Environment`. Un thread d’arrière-plan n’appartient à aucune session : il faut donc lui indiquer quel `Environment` mettre à jour.

## API `RunLater` {#runlater-api}

L’API `Environment.runLater()` fournit deux méthodes pour planifier des mises à jour de l’interface utilisateur. Les deux prennent l’`Environment` cible comme premier argument :

```java title="Environment.java"
// Planifier une tâche sans valeur de retour
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Planifier une tâche qui renvoie une valeur
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Les deux méthodes renvoient un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui suit l’achèvement de la tâche et donne accès au résultat ou aux éventuelles exceptions qui se sont produites.

:::warning[Surcharges sans cible]
Les surcharges `runLater(Runnable)` et `runLater(Supplier)` déduisent la cible à partir du thread qui a créé le thread appelant. Dans un pool de threads partagé par plusieurs utilisateurs, chaque tâche s’exécute dans l’`Environment` de l’utilisateur dont l’appel a créé ce thread du pool. Ces surcharges sont obsolètes depuis la version 26.10 et leur suppression est prévue dans la prochaine version majeure. Passez toujours la cible.
:::

## Passer la cible {#passing-the-target}

`Environment.getCurrent()` renvoie l’`Environment` du thread courant. Sur le thread `Environment`, où s’exécutent les constructeurs, les gestionnaires d’événements et les méthodes du cycle de vie, cette méthode renvoie l’`Environment` de la session. Sur un thread d’arrière-plan, elle renvoie `null`.

Récupérez la cible lorsqu’elle est disponible, avant le démarrage du travail en arrière-plan, puis transmettez-la à ce travail :

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Le constructeur s’exécute sur le thread Environment
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

La seule erreur à éviter consiste à appeler `Environment.getCurrent()` dans le travail en arrière-plan. Dans ce contexte, la méthode renvoie `null`, et `runLater()` lève une `NullPointerException` :

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Incorrect : getCurrent() renvoie null sur un thread d’arrière-plan
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Il en va de même pour les initialisateurs statiques, les threads `java.util.Timer` et les rappels de bibliothèques externes. Aucun d’eux ne possède d’`Environment` : la cible doit donc provenir d’un champ, d’un paramètre ou d’une variable locale capturée, renseignée sur le thread `Environment`.

## Comportement de l’exécution {#execution-behavior}

Le comportement d’exécution de `runLater()` dépend du thread qui l’appelle :

### Depuis le thread de l’interface utilisateur {#from-the-ui-thread}

Lorsqu’elles sont appelées depuis le thread `Environment` propre à la cible, les tâches s’exécutent **de manière synchrone et immédiate** :

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Avant : " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Dans la tâche : " + Thread.currentThread().getName());
    return "terminé";
  });

  System.out.println("Après : " + result.isDone());  // true
});
```

Grâce à ce comportement synchrone, les mises à jour de l’interface utilisateur effectuées depuis les gestionnaires d’événements sont appliquées immédiatement, sans surcoût inutile de mise en file d’attente.

### Depuis des threads d’arrière-plan {#from-background-threads}

Lorsqu’elles sont appelées depuis tout autre thread, les tâches sont **mises en file d’attente pour une exécution asynchrone** :

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // Cette tâche s’exécute sur un thread ForkJoinPool
    System.out.println("Arrière-plan : " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // Cette tâche s’exécute sur le thread Environment
      System.out.println("Mise à jour de l’interface : " + Thread.currentThread().getName());
      statusLabel.setText("Traitement terminé");
    });

    // result.isDone() renverrait false ici
    // La tâche est mise en file d’attente et s’exécutera de manière asynchrone
  });
}
```

webforJ traite les tâches soumises depuis des threads d’arrière-plan dans un **ordre FIFO strict**, préservant ainsi la séquence des opérations même lorsqu’elles sont soumises simultanément depuis plusieurs threads. Grâce à cette garantie d’ordre, les mises à jour de l’interface utilisateur sont appliquées exactement dans l’ordre où elles ont été soumises. Ainsi, si le thread A soumet la tâche 1, puis le thread B soumet la tâche 2, la tâche 1 s’exécutera toujours avant la tâche 2 sur le thread de l’interface utilisateur. Le traitement des tâches dans l’ordre FIFO évite les incohérences dans l’interface utilisateur.

## Annulation des tâches {#task-cancellation}

Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> renvoyé par `Environment.runLater()` permet l’annulation, ce qui vous permet d’empêcher l’exécution des tâches en attente. L’annulation des tâches en attente permet d’éviter les fuites de mémoire et d’empêcher les opérations de longue durée de mettre à jour l’interface utilisateur lorsqu’elles ne sont plus nécessaires.

### Annulation de base {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Annuler si l’exécution n’a pas encore eu lieu
if (!result.isDone()) {
  result.cancel();
}
```

### Gérer plusieurs mises à jour {#managing-multiple-updates}

Lors d’opérations de longue durée qui effectuent fréquemment des mises à jour de l’interface utilisateur, suivez tous les résultats en attente :

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

        // Conserver une référence pour une éventuelle annulation
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Annuler toutes les mises à jour de l’interface en attente
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

Lorsque des composants sont détruits (par exemple, lors d’une navigation), annulez toutes les mises à jour en attente afin d’éviter les fuites de mémoire :

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuler toutes les mises à jour en attente afin d’éviter les fuites de mémoire
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

1. **Cible obligatoire** : la cible `Environment` est récupérée avec `Environment.getCurrent()` sur le thread `Environment`, puis transmise au travail en arrière-plan. L’appeler depuis un thread d’arrière-plan renvoie `null`.

2. **Prévention des fuites de mémoire** : suivez et annulez toujours les objets `PendingResult` dans les méthodes du cycle de vie des composants. Les lambdas mises en file d’attente capturent des références aux composants de l’interface utilisateur, empêchant leur récupération par le ramasse-miettes si elles ne sont pas annulées.

3. **Exécution FIFO** : toutes les tâches s’exécutent dans un ordre FIFO strict, quelle que soit leur importance. Il n’existe aucun système de priorité.

4. **Limites de l’annulation** : l’annulation empêche uniquement l’exécution des tâches en attente. Les tâches déjà en cours d’exécution se terminent normalement.

## Étude de cas complète : `LongTaskView` {#complete-case-study-longtaskview}

Voici une implémentation complète, prête pour la production, qui illustre les bonnes pratiques pour les mises à jour asynchrones de l’interface utilisateur :

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Utiliser un exécuteur à thread unique pour éviter l’épuisement des ressources
  // En production, envisager d’utiliser un pool de threads partagé à l’échelle de l’application
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Suivre la tâche en cours et les mises à jour de l’interface utilisateur en attente
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Composants de l’interface utilisateur
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Démonstration des mises à jour de l’interface en arrière-plan");
  private Paragraph descriptionPara = new Paragraph(
      "Cette démonstration montre comment Environment.runLater() permet de mettre à jour l’interface utilisateur en toute sécurité depuis des threads d’arrière-plan. " +
          "Cliquez sur « Démarrer la tâche longue » pour lancer un calcul de 10 secondes en arrière-plan qui met à jour la progression dans l’interface. " +
          "Le bouton « Tester l’interface » prouve que l’interface reste réactive pendant l’opération en arrière-plan.");
  private TextField statusField = new TextField("État");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Résultat");
  private Button startButton = new Button("Démarrer la tâche longue");
  private Button cancelButton = new Button("Annuler la tâche");
  private Button testButton = new Button("Tester l’interface - Cliquez ici !");
  private Paragraph footerPara = new Paragraph(
      "Remarque : la tâche peut être annulée à tout moment, ce qui montre le nettoyage approprié du thread " +
          "d’arrière-plan et des mises à jour de l’interface utilisateur en attente.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configurer les champs
    statusField.setReadOnly(true);
    statusField.setValue("Prêt à démarrer");
    statusField.setLabel("État");

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
      showToast("Clic n°" + count + " - L’interface est réactive !", Theme.GRAY);
    });

    // Ajouter les composants
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuler toute tâche en cours et toutes les mises à jour de l’interface en attente
    cancelTask();

    // Effacer la référence à la tâche
    currentTask = null;

    // Arrêter proprement l’exécuteur de l’instance
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Démarrage de la tâche en arrière-plan...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Réinitialiser l’indicateur d’annulation et effacer les mises à jour précédentes en attente
    isCancelled = false;
    pendingUIUpdates.clear();

    // Récupérer l’Environment cible sur le thread de l’interface utilisateur, avant le démarrage de la tâche en arrière-plan
    Environment env = Environment.getCurrent();

    // Démarrer la tâche en arrière-plan avec un exécuteur explicite
    // Remarque : cancel(true) interrompt le thread, ce qui provoque une
    // InterruptedException lors de Thread.sleep()
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuler une tâche longue composée de 100 étapes
      for (int i = 0; i <= 100; i++) {
        // Vérifier si la tâche a été annulée
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
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
          // Le thread a été interrompu : quitter immédiatement
          Thread.currentThread().interrupt(); // Rétablir l’état d’interruption
          return;
        }

        // Effectuer un calcul (déterministe pour la démonstration)
        // Produit des valeurs comprises entre 0 et 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Mettre à jour la progression depuis le thread d’arrière-plan
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Traitement en cours... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Mise à jour finale avec le résultat (ce code n’est atteint que si la tâche s’est terminée sans
      // être annulée)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
          statusField.setValue("Tâche terminée !");
          resultField.setValue("Résultat : " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("La tâche en arrière-plan est terminée !", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Définir l’indicateur d’annulation
      isCancelled = true;

      // Annuler la tâche principale (interrompt le thread)
      currentTask.cancel(true);

      // Annuler toutes les mises à jour de l’interface en attente
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Annulation de la tâche...");
        cancelButton.setEnabled(false);

        showToast("Demande d’annulation envoyée", Theme.GRAY);
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

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

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
- Utilise un **exécuteur à thread unique** pour éviter l’épuisement des ressources
- Crée des **threads démons** qui n’empêcheront pas l’arrêt de la JVM

#### 2. Nommer la cible {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` s’exécute sur le thread `Environment`. Il y récupère donc la cible, que la tâche en arrière-plan transmet à chaque appel de `Environment.runLater()`. Le même code fonctionne sans modification avec un pool de threads partagé à l’échelle de l’application.

#### 3. Suivre les mises à jour en attente {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Chaque appel à `Environment.runLater()` est suivi afin de permettre :
- L’annulation lorsque l’utilisateur clique sur le bouton d’annulation
- La prévention des fuites de mémoire dans `onDestroy()`
- Un nettoyage approprié pendant le cycle de vie du composant

#### 4. Annulation coopérative {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Le thread d’arrière-plan vérifie cet indicateur à chaque itération, ce qui permet :
- De réagir immédiatement à l’annulation
- De quitter proprement la boucle
- D’empêcher toute nouvelle mise à jour de l’interface utilisateur

#### 5. Gestion du cycle de vie {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Réutilise la logique d’annulation
  currentTask = null;
  executor.shutdown();
}
```
Cette étape est essentielle pour éviter les fuites de mémoire en :
- Annulant toutes les mises à jour de l’interface en attente
- Interrompant les threads en cours d’exécution
- Arrêtant l’exécuteur

#### 6. Tester la réactivité de l’interface utilisateur {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Clic n°" + count + " - L’interface est réactive !", Theme.GRAY);
});
```
Cela montre que le thread de l’interface utilisateur reste réactif pendant les opérations en arrière-plan.
