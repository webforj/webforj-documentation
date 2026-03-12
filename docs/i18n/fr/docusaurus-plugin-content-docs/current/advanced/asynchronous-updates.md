---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 9ea7ae8b53ce19e2fee19e72929c732e
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

L'API `Environment.runLater()` fournit un mécanisme pour mettre à jour l'interface utilisateur en toute sécurité à partir de threads d'arrière-plan dans les applications webforJ. Cette fonctionnalité expérimentale permet des opérations asynchrones tout en maintenant la sécurité des threads pour les modifications de l'interface utilisateur.

:::warning API expérimentale
Cette API est marquée comme expérimentale depuis le 25.02 et peut changer dans les futures versions. La signature de l'API, son comportement et ses caractéristiques de performance sont susceptibles de modifications.
:::

## Comprendre le modèle de threads {#understanding-the-thread-model}

webforJ impose un modèle de threads strict où toutes les opérations de l'interface utilisateur doivent se produire sur le thread `Environment`. Cette restriction existe pour les raisons suivantes :

1. **Contraintes de l'API webforJ** : L'API webforJ sous-jacente est liée au thread qui a créé la session.
2. **Affinité des threads des composants** : Les composants de l'interface utilisateur maintiennent un état qui n'est pas sûr pour les threads.
3. **Gestion des événements** : Tous les événements de l'interface utilisateur sont traités séquentiellement sur un seul thread.

Ce modèle à thread unique empêche les conditions de concurrence et maintient un état cohérent pour tous les composants de l'interface utilisateur, mais crée des défis lors de l'intégration avec des tâches de calcul asynchrones et de longue durée.

## API `RunLater` {#runlater-api}

L'API `Environment.runLater()` fournit deux méthodes pour planifier des mises à jour de l'interface utilisateur :

```java title="Environment.java"
// Planifier une tâche sans valeur de retour
public static PendingResult<Void> runLater(Runnable task)

// Planifier une tâche qui retourne une valeur
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Les deux méthodes renvoient un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui suit l'achèvement de la tâche et fournit un accès au résultat ou à toute exception qui s'est produite.

## Héritage du contexte de thread {#thread-context-inheritance}

L'héritage automatique du contexte est une fonctionnalité cruciale de `Environment.runLater()`. Lorsqu'un thread s'exécutant dans un `Environment` crée des threads enfants, ces enfants héritent automatiquement de la possibilité d'utiliser `runLater()`.

### Comment fonctionne l'héritage {#how-inheritance-works}

Tout thread créé à partir d'un thread `Environment` a automatiquement accès à cet `Environment`. Cet héritage se produit automatiquement, donc vous n'avez pas besoin de transmettre un contexte ou de configurer quoi que ce soit.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Ce thread a le contexte Environment
        
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

Les threads créés en dehors du contexte `Environment` ne peuvent pas utiliser `runLater()` et lanceront une `IllegalStateException` :

```java
// Initialiseur statique - pas de contexte Environment
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Lance IllegalStateException
    }).start();
}

// Threads de timer système - pas de contexte Environment  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Lance IllegalStateException
    }
}, 1000);

// Threads de bibliothèque externe - pas de contexte Environment
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Lance IllegalStateException
    });
```

## Comportement d'exécution {#execution-behavior}

Le comportement d'exécution de `runLater()` dépend de quel thread l'appelle :

### Depuis le thread UI {#from-the-ui-thread}

Lorsqu'il est appelé depuis le thread `Environment` lui-même, les tâches s'exécutent **synchrones et immédiatement** :

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

Avec ce comportement synchrone, les mises à jour de l'interface utilisateur à partir des gestionnaires d'événements sont appliquées immédiatement et n'encourent pas de surcharge de mise en file d'attente inutile.

### Depuis les threads d'arrière-plan {#from-background-threads}

Lorsque appelé depuis un thread d'arrière-plan, les tâches sont **enregistrées pour exécution asynchrone** :

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Ceci s'exécute sur le thread ForkJoinPool
        System.out.println("Arrière-plan : " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Ceci s'exécute sur le thread Environment
            System.out.println("Mise à jour UI : " + Thread.currentThread().getName());
            statusLabel.setText("Traitement terminé");
        });
        
        // result.isDone() serait faux ici
        // La tâche est mise en file d'attente et s'exécutera de manière asynchrone
    });
}
```

webforJ traite les tâches soumises à partir de threads d'arrière-plan dans un **ordre FIFO strict**, préservant la séquence des opérations même lorsqu'elles sont soumises simultanément à partir de plusieurs threads. Avec cette garantie d'ordre, les mises à jour de l'interface utilisateur sont appliquées dans l'ordre exact dans lequel elles ont été soumises. Ainsi, si le thread A soumet la tâche 1, puis le thread B soumet la tâche 2, la tâche 1 s'exécutera toujours avant la tâche 2 sur le thread de l'interface utilisateur. Le traitement des tâches dans l'ordre FIFO empêche les incohérences dans l'interface utilisateur.

## Annulation des tâches {#task-cancellation}

Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> retourné par `Environment.runLater()` prend en charge l'annulation, vous permettant d'empêcher l'exécution des tâches en file d'attente. En annulant des tâches en attente, vous pouvez éviter les fuites de mémoire et empêcher les opérations longues de mettre à jour l'interface utilisateur après qu'elles ne sont plus nécessaires.

### Annulation de base {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Annuler si non encore exécuté
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

1. **Exigence de contexte** : Les threads doivent avoir hérité d'un contexte `Environment`. Les threads de bibliothèque externes, les timers système et les initialiseurs statiques ne peuvent pas utiliser cette API.

2. **Prévention des fuites de mémoire** : Suivez toujours et annulez les objets `PendingResult` dans les méthodes de cycle de vie des composants. Les lambdas mises en file d'attente capturent des références à des composants UI, empêchant la collecte des ordures si elles ne sont pas annulées.

3. **Exécution FIFO** : Toutes les tâches s'exécutent dans un ordre strict FIFO, quel que soit leur importance. Il n'existe pas de système de priorité.

4. **Limitations de l'annulation** : L'annulation empêche uniquement l'exécution des tâches en file d'attente. Les tâches déjà en cours d'exécution termineront normalement.

## Étude de cas complète : `LongTaskView` {#complete-case-study-longtaskview}

Ce qui suit est une implémentation complète et prête pour la production démontrant toutes les meilleures pratiques pour des mises à jour UI asynchrones :

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Utilisez un seul exécuteur de thread pour prévenir l'épuisement des ressources
  // Pour la production, envisagez d'utiliser un pool de threads partagé à l'échelle de l'application
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Suivez la tâche actuelle et les mises à jour UI en attente
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Composants UI
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Démonstration des mises à jour d'UI en arrière-plan");
  private Paragraph descriptionPara = new Paragraph(
      "Cette démonstration montre comment Environment.runLater() permet des mises à jour de l'UI en toute sécurité à partir de threads d'arrière-plan. " +
          "Cliquez sur 'Démarrer une tâche longue' pour exécuter un calcul d'arrière-plan de 10 secondes qui met à jour l'UI sur la progression. " +
          "Le bouton 'Tester l'UI' prouve que l'UI reste réactive pendant l'opération d'arrière-plan.");
  private TextField statusField = new TextField("Statut");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Résultat");
  private Button startButton = new Button("Démarrer une tâche longue");
  private Button cancelButton = new Button("Annuler la tâche");
  private Button testButton = new Button("Tester l'UI - Cliquez moi !");
  private Paragraph footerPara = new Paragraph(
      "Remarque : La tâche peut être annulée à tout moment, démontrant un nettoyage approprié à la fois du " +
          "thread d'arrière-plan et des mises à jour d'UI mises en file d'attente.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configurez les champs
    statusField.setReadOnly(true);
    statusField.setValue("Prêt à commencer");
    statusField.setLabel("Statut");

    // Configurez la barre de progression
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

    // Configurez les boutons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Clic #" + count + " - L'UI est réactive !", Theme.GRAY);
    });

    // Ajoutez des composants
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuler toute tâche en cours et mises à jour UI en attente
    cancelTask();

    // Effacer la référence de tâche
    currentTask = null;

    // Arrêter l'exécuteur d'instance en douceur
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Démarrage de la tâche d'arrière-plan...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Réinitialiser le flag annulé et effacer les mises à jour en attente précédentes
    isCancelled = false;
    pendingUIUpdates.clear();

    // Démarrer la tâche d'arrière-plan avec un exécuteur explicite
    // Remarque : cancel(true) interrompt le thread, ce qui provoque Thread.sleep() pour lancer
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
          // Le thread a été interrompu - sortie immédiate
          Thread.currentThread().interrupt(); // Restaurer le statut interrompu
          return;
        }

        // Effectuer un calcul (déterministe pour la démo)
        // Produit des valeurs entre 0 et 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Mise à jour de la progression depuis le thread d'arrière-plan
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Traitement... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Mise à jour finale avec le résultat (ce code n'est atteint que si la tâche est terminée sans
      // annulation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tâche terminée !");
          resultField.setValue("Résultat : " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("La tâche d'arrière-plan est terminée !", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Définir le flag annulé
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

Cette implémentation démontre plusieurs motifs critiques :

#### 1. Gestion du pool de threads {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Utilise un **exécuteur à un seul thread** pour prévenir l'épuisement des ressources
- Crée des **threads de démon** qui ne battront pas l'arrêt de la JVM

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
- La prévention de mises à jour d'UI supplémentaires

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
Crucial pour prévenir les fuites de mémoire en :
- Annulant toutes les mises à jour UI en attente
- Interrompant les threads en cours d'exécution
- Fermant l'exécuteur

#### 5. Test de réactivité de l'UI {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Clic #" + count + " - L'UI est réactive !", Theme.GRAY);
});
```
Démontre que le thread de l'UI reste réactif pendant les opérations d'arrière-plan.
