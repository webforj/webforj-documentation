---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone Aktualisierungen
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 7fedd93e44d699bba0a959504c7bb40c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

Die API `Environment.runLater()` bietet einen Mechanismus, um die UI in webforJ-Anwendungen sicher von Hintergrundthreads aus zu aktualisieren. Dieses Vorschau-Feature ermöglicht asynchrone Vorgänge und gewährleistet zugleich die Thread-Sicherheit bei UI-Änderungen.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Das Thread-Modell verstehen {#understanding-the-thread-model}

webforJ erzwingt ein striktes Thread-Modell, bei dem alle UI-Operationen im `Environment`-Thread ausgeführt werden müssen. Diese Einschränkung besteht aus folgenden Gründen:

1. **Einschränkungen der webforJ-API**: Die zugrunde liegende webforJ-API ist an den Thread gebunden, der die Sitzung erstellt hat
2. **Thread-Affinität von Komponenten**: UI-Komponenten halten einen Status, der nicht thread-sicher ist
3. **Event-Verarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzigen Thread verarbeitet

Dieses Single-Thread-Modell verhindert Race Conditions und sorgt für einen konsistenten Status aller UI-Komponenten. Bei der Integration mit asynchronen, lang laufenden Berechnungsaufgaben entstehen dadurch jedoch Herausforderungen.

Jede Benutzersitzung hat eine eigene `Environment`-Instanz und einen eigenen `Environment`-Thread. Ein Hintergrundthread gehört zu keiner Sitzung. Daher muss ihm mitgeteilt werden, welche `Environment`-Instanz aktualisiert werden soll.

## Die `RunLater`-API {#runlater-api}

Die API `Environment.runLater()` bietet zwei Methoden zum Einplanen von UI-Aktualisierungen. Beide erwarten die Ziel-`Environment`-Instanz als erstes Argument:

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Aufgabenabschluss verfolgt und Zugriff auf das Ergebnis sowie auf eventuell aufgetretene Ausnahmen bietet.

:::warning[Überladungen ohne Ziel]
Die Überladungen `runLater(Runnable)` und `runLater(Supplier)` ermitteln das Ziel anhand des Threads, der den aufrufenden Thread erstellt hat. Bei einem Thread-Pool, der von mehreren Benutzern gemeinsam genutzt wird, wird jede Aufgabe in der `Environment`-Instanz des Benutzers ausgeführt, dessen Aufruf diesen Pool-Thread erstellt hat. Diese Überladungen sind seit Version 26.10 veraltet und sollen mit der nächsten Hauptversion entfernt werden. Übergeben Sie immer das Ziel.
:::

## Das Ziel übergeben {#passing-the-target}

`Environment.getCurrent()` gibt die `Environment`-Instanz des aktuellen Threads zurück. Im `Environment`-Thread, in dem Konstruktoren, Event-Handler und Lebenszyklusmethoden ausgeführt werden, gibt die Methode die `Environment`-Instanz der Sitzung zurück. In einem Hintergrundthread gibt sie `null` zurück.

Lesen Sie das Ziel dort aus, wo es verfügbar ist, also bevor die Hintergrundarbeit beginnt, und übergeben Sie es an die Arbeit:

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

Da das Ziel als Argument übergeben wird, funktioniert der Aufruf von jedem Thread aus: von einem Thread, den die View erstellt hat, von einem Spring-`@Async`-Thread, vom gemeinsamen `ForkJoinPool`, von einem Timer-Thread oder von einem Callback-Thread eines HTTP-Clients.

### Das Ziel im falschen Thread auslesen {#reading-the-target-on-the-wrong-thread}

Der eine Fehler, den Sie vermeiden sollten, ist, `Environment.getCurrent()` innerhalb der Hintergrundarbeit aufzurufen. Dort gibt die Methode `null` zurück, und `runLater()` löst eine `NullPointerException` aus:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Wrong: getCurrent() returns null on a background thread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Dasselbe gilt für statische Initialisierer, `java.util.Timer`-Threads und Callbacks externer Bibliotheken. Keiner davon hat eine `Environment`-Instanz. Das Ziel muss daher aus einem Feld, einem Parameter oder einer erfassten lokalen Variable stammen, die im `Environment`-Thread gesetzt wurde.

## Ausführungsverhalten {#execution-behavior}

Das Ausführungsverhalten von `runLater()` hängt davon ab, welcher Thread die Methode aufruft:

### Aufruf im UI-Thread {#from-the-ui-thread}

Wird die Methode im eigenen `Environment`-Thread des Ziels aufgerufen, werden Aufgaben **synchron und sofort** ausgeführt:

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

Dank dieses synchronen Verhaltens werden UI-Aktualisierungen aus Event-Handlern sofort übernommen und verursachen keinen unnötigen Aufwand durch das Einreihen in eine Warteschlange.

### Aufruf in Hintergrundthreads {#from-background-threads}

Wird die Methode von einem anderen Thread aufgerufen, werden Aufgaben **zur asynchronen Ausführung in eine Warteschlange gestellt**:

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

webforJ verarbeitet Aufgaben, die von Hintergrundthreads eingereicht wurden, in **strikter FIFO-Reihenfolge**. Dadurch bleibt die Reihenfolge der Vorgänge auch dann erhalten, wenn sie gleichzeitig von mehreren Threads eingereicht werden. Dank dieser Garantie werden UI-Aktualisierungen genau in der Reihenfolge übernommen, in der sie eingereicht wurden. Reicht Thread A also Aufgabe 1 und anschließend Thread B Aufgabe 2 ein, wird Aufgabe 1 im UI-Thread immer vor Aufgabe 2 ausgeführt. Die Verarbeitung in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgaben abbrechen {#task-cancellation}

Das von `Environment.runLater()` zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> unterstützt das Abbrechen von Aufgaben. So können Sie verhindern, dass Aufgaben aus der Warteschlange ausgeführt werden. Durch das Abbrechen ausstehender Aufgaben lassen sich Speicherlecks vermeiden und verhindern, dass lang laufende Vorgänge die UI aktualisieren, wenn dies nicht mehr erforderlich ist.

### Einfaches Abbrechen {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Cancel if not yet executed
if (!result.isDone()) {
  result.cancel();
}
```

### Mehrere Aktualisierungen verwalten {#managing-multiple-updates}

Verfolgen Sie bei lang laufenden Vorgängen mit häufigen UI-Aktualisierungen alle ausstehenden Ergebnisse:

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

### Lebenszyklusverwaltung von Komponenten {#component-lifecycle-management}

Wenn Komponenten zerstört werden (z. B. während der Navigation), brechen Sie alle ausstehenden Aktualisierungen ab, um Speicherlecks zu vermeiden:

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

## Designüberlegungen {#design-considerations}

1. **Ziel erforderlich**: Die Ziel-`Environment`-Instanz wird im `Environment`-Thread mit `Environment.getCurrent()` ausgelesen und an die Hintergrundarbeit übergeben. Wird sie in einem Hintergrundthread ausgelesen, gibt die Methode `null` zurück.

2. **Speicherlecks vermeiden**: Verfolgen Sie `PendingResult`-Objekte immer und brechen Sie sie in den Lebenszyklusmethoden der Komponente ab. In die Warteschlange gestellte Lambdas erfassen Referenzen auf UI-Komponenten und verhindern deren Garbage Collection, wenn sie nicht abgebrochen werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden unabhängig von ihrer Wichtigkeit in strikter FIFO-Reihenfolge ausgeführt. Es gibt kein Prioritätensystem.

4. **Einschränkungen beim Abbrechen**: Das Abbrechen verhindert nur die Ausführung von Aufgaben, die noch in der Warteschlange stehen. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Vollständiges Fallbeispiel: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende vollständige, produktionsreife Implementierung zeigt Best Practices für asynchrone UI-Aktualisierungen:

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

### Analyse des Fallbeispiels {#case-study-analysis}

Diese Implementierung zeigt mehrere wichtige Muster:

#### 1. Thread-Pool-Verwaltung {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Verwendet einen **Single-Thread-Executor**, um eine Erschöpfung der Ressourcen zu verhindern
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Das Ziel festlegen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` wird im `Environment`-Thread ausgeführt. Daher wird das Ziel dort ausgelesen und von der Hintergrundaufgabe bei jedem Aufruf von `Environment.runLater()` übergeben. Derselbe Code funktioniert unverändert mit einem anwendungsweit gemeinsam genutzten Thread-Pool.

#### 3. Ausstehende Aktualisierungen verfolgen {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird erfasst, um Folgendes zu ermöglichen:
- Abbrechen, wenn der Benutzer auf „Abbrechen“ klickt
- Speicherlecks in `onDestroy()` vermeiden
- Ordnungsgemäße Bereinigung während des Lebenszyklus der Komponente

#### 4. Kooperatives Abbrechen {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrundthread prüft dieses Flag bei jeder Iteration. Dadurch werden Folgendes ermöglicht:
- Sofortige Reaktion auf das Abbrechen
- Sauberes Beenden der Schleife
- Verhindern weiterer UI-Aktualisierungen

#### 5. Lebenszyklusverwaltung {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Reuses cancellation logic
  currentTask = null;
  executor.shutdown();
}
```
Wichtig, um Speicherlecks zu vermeiden durch:
- Abbrechen aller ausstehenden UI-Aktualisierungen
- Unterbrechen laufender Threads
- Herunterfahren des Executors

#### 6. UI-Reaktionsfähigkeit testen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
Zeigt, dass der UI-Thread während der Hintergrundvorgänge reaktionsfähig bleibt.
