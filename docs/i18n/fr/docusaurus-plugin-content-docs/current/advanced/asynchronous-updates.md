---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: a426166aa63471b0d9d84e6c4786c6db
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

L'API `Environment.runLater()` fournit un mécanisme pour mettre à jour en toute sécurité l'interface utilisateur à partir de threads d'arrière-plan dans les applications webforJ. Cette fonctionnalité expérimentale permet des opérations asynchrones tout en maintenant la sécurité des threads pour les modifications de l'interface utilisateur.

:::warning API expérimentale
Cette API est marquée comme expérimentale depuis 25.02 et pourrait changer dans de futures versions. La signature de l'API, son comportement et ses caractéristiques de performance sont susceptibles de modification.
:::

## Comprendre le modèle de thread {#understanding-the-thread-model}

webforJ impose un modèle de threading strict où toutes les opérations de l'interface utilisateur doivent se produire sur le thread `Environment`. Cette restriction existe pour les raisons suivantes :

1. **Contraintes de l'API webforJ** : L'API webforJ sous-jacente est liée au thread qui a créé la session.
2. **Affinité du thread des composants** : Les composants de l'interface utilisateur maintiennent un état qui n'est pas sûr pour les threads.
3. **Dispatch d'événements** : Tous les événements de l'interface utilisateur sont traités séquentiellement sur un seul thread.

Ce modèle à un seul thread empêche les conditions de concurrence et maintient un état cohérent pour tous les composants de l'interface utilisateur, mais crée des défis lors de l'intégration avec des tâches de calcul asynchrones et de longue durée.

## API `RunLater` {#runlater-api}

L'API `Environment.runLater()` fournit deux méthodes pour planifier des mises à jour d'interface utilisateur :

```java title="Environment.java"
// Planifiez une tâche sans valeur de retour
public static PendingResult<Void> runLater(Runnable task)

// Planifiez une tâche qui retourne une valeur
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Les deux méthodes retournent un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> qui suit l'achèvement de la tâche et fournit un accès au résultat ou à toute exception survenue.

## Héritage du contexte du thread {#thread-context-inheritance}

L'héritage de contexte automatique est une fonctionnalité critique de `Environment.runLater()`. Lorsqu'un thread s'exécutant dans un `Environment` crée des threads enfants, ces derniers héritent automatiquement de la capacité d'utiliser `runLater()`.

### Comment fonctionne l'héritage {#how-inheritance-works}

Tout thread créé à partir d'un thread `Environment` a automatiquement accès à cet `Environment`. Cet héritage se produit automatiquement, vous n'avez donc pas besoin de transmettre de contexte ou de configurer quoi que ce soit.

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

// Threads de minuterie système - pas de contexte Environment  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Lance IllegalStateException
    }
}, 1000);

// Threads de bibliothèques externes - pas de contexte Environment
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Lance IllegalStateException
    });
```

## Comportement d'exécution {#execution-behavior}

Le comportement d'exécution de `runLater()` dépend de quel thread l'appelle :

### Depuis le thread de l'interface utilisateur {#from-the-ui-thread}

Lorsqu'il est appelé depuis le thread `Environment` lui-même, les tâches s'exécutent **synchroniquement et immédiatement** :

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

Avec ce comportement synchrone, les mises à jour de l'interface utilisateur des gestionnaires d'événements sont appliquées immédiatement et n'encourent aucun coût de mise en file d'attente inutile.

### Depuis des threads d'arrière-plan {#from-background-threads}

Lorsqu'il est appelé depuis un thread d'arrière-plan, les tâches sont **placées en file d'attente pour une exécution asynchrone** :

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Cela s'exécute sur le thread ForkJoinPool
        System.out.println("Arrière-plan : " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Cela s'exécute sur le thread Environment
            System.out.println("Mise à jour de l'interface utilisateur : " + Thread.currentThread().getName());
            statusLabel.setText("Traitement terminé");
        });
        
        // result.isDone() serait faux ici
        // La tâche est mise en file d'attente et s'exécutera de manière asynchrone
    });
}
```

webforJ traite les tâches soumises depuis des threads d'arrière-plan dans un **ordre FIFO strict**, préservant la séquence des opérations même lorsqu'elles sont soumises simultanément depuis plusieurs threads. Avec cette garantie d'ordre, les mises à jour de l'interface utilisateur sont appliquées dans l'ordre exact dans lequel elles ont été soumises. Ainsi, si le thread A soumet la tâche 1, puis le thread B soumet la tâche 2, la tâche 1 s'exécutera toujours avant la tâche 2 sur le thread de l'interface utilisateur. Le traitement des tâches dans l'ordre FIFO prévient les incohérences dans l'interface utilisateur.

## Annulation de tâches {#task-cancellation}

Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> retourné par `Environment.runLater()` prend en charge l'annulation, vous permettant d'empêcher l'exécution des tâches en file d'attente. En annulant les tâches en attente, vous pouvez éviter les fuites de mémoire et empêcher les opérations de longue durée de mettre à jour l'interface utilisateur après qu'elles ne sont plus nécessaires.

### Annulation basique {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Annuler si pas encore exécuté
if (!result.isDone()) {
    result.cancel();
}
```

### Gestion des mises à jour multiples {#managing-multiple-updates}

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
                
                // Suivez pour une annulation potentielle
                pendingUpdates.add(update);
                
                Thread.sleep(100);
            }
        });
    }
    
    public void cancelTask() {
        isCancelled = true;
        
        // Annulez toutes les mises à jour de l'interface en attente
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

Lorsque les composants sont détruits (par exemple, lors de la navigation), annulez toutes les mises à jour en attente pour prévenir les fuites de mémoire :

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Annulez toutes les mises à jour en attente pour éviter les fuites de mémoire
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

1. **Exigence de contexte** : Les threads doivent avoir hérité d'un contexte `Environment`. Les threads de bibliothèques externes, les timers système et les initializeurs statiques ne peuvent pas utiliser cette API.

2. **Prévention des fuites de mémoire** : Toujours suivre et annuler les objets `PendingResult` dans les méthodes de cycle de vie des composants. Les lambdas mises en file d'attente capturent des références aux composants de l'interface utilisateur, empêchant leur collecte par le garbage collector si elles ne sont pas annulées.

3. **Exécution FIFO** : Toutes les tâches s'exécutent dans un ordre FIFO strict, peu importe leur importance. Il n'y a pas de système de priorité.

4. **Limitations de l'annulation** : L'annulation empêche uniquement l'exécution des tâches en file d'attente. Les tâches déjà en cours d'exécution se termineront normalement.

## Étude de cas complète : `LongTaskView` {#complete-case-study-longtaskview}

Ce qui suit est une implémentation complète, prête pour la production, démontrant toutes les meilleures pratiques pour des mises à jour asynchrones de l'interface utilisateur :

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
// La classe et son contenu restent identiques
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Analyse de l'étude de cas {#case-study-analysis}

Cette implémentation démontre plusieurs schémas critiques :

#### 1. Gestion du pool de threads {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Utilise un **exécuteur à thread unique** pour éviter l'épuisement des ressources.
- Crée des **threads de démon** qui ne bloqueront pas l'arrêt de la JVM.

#### 2. Suivi des mises à jour en attente {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Chaque appel à `Environment.runLater()` est suivi pour permettre :
- L'annulation lors de l'appui sur le bouton annuler.
- La prévention des fuites de mémoire dans `onDestroy()`.
- Un nettoyage approprié lors du cycle de vie des composants.

#### 3. Annulation coopérative {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Le thread d'arrière-plan vérifie ce drapeau à chaque itération, permettant :
- Une réponse immédiate à l'annulation.
- Un arrêt propre de la boucle.
- La prévention de futures mises à jour de l'interface utilisateur.

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
- Annulant toutes les mises à jour de l'interface en attente.
- Interrompant les threads en cours d'exécution.
- Arrêtant l'exécuteur.

#### 5. Test de réactivité de l'interface utilisateur {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Clic #" + count + " - L'interface utilisateur est réactive !", Theme.GRAY);
});
```
Démontre que le thread de l'interface utilisateur reste réactif durant les opérations d'arrière-plan.
