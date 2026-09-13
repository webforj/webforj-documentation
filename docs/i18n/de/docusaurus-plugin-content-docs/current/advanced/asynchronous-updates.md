---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone Aktualisierungen
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: ee0d9acbd0ac4b9b04510636531eb49d
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche von Hintergrund-Threads in webforJ-Anwendungen. Dieses experimentelle Feature ermöglicht asynchrone Operationen, während die Thread-Sicherheit für UI-Änderungen aufrechterhalten wird.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ erzwingt ein strenges Thread-Modell, bei dem alle UI-Operationen im `Environment`-Thread ausgeführt werden müssen. Diese Einschränkung besteht aus folgenden Gründen:

1. **Einschränkungen der webforJ API**: Die zugrunde liegende webforJ API bindet an den Thread, der die Sitzung erstellt hat.
2. **Thread-Affinität von Komponenten**: UI-Komponenten behalten einen Zustand, der nicht threadsicher ist.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzelnen Thread verarbeitet.

Dieses einheitliche Thread-Modell verhindert Wettlaufbedingungen und erhält einen konsistenten Zustand für alle UI-Komponenten, schafft jedoch Herausforderungen bei der Integration mit asynchronen, lang laufenden Rechenaufgaben.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zur Planung von UI-Aktualisierungen:

```java title="Environment.java"
// Planen Sie eine Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Planen Sie eine Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder aufgetretene Ausnahmen bietet.

## Vererbung des Thread-Kontexts {#thread-context-inheritance}

Die automatische Vererbung des Kontexts ist ein grundlegendes Merkmal von `Environment.runLater()`. Wenn ein Thread, der in einem `Environment` läuft, Kind-Threads erstellt, erben diese automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der innerhalb eines `Environment`-Threads erstellt wird, hat automatisch Zugriff auf dieses `Environment`. Diese Vererbung erfolgt automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Dieser Thread hat den Environment-Kontext

    // Kind-Threads erben den Kontext automatisch
    executor.submit(() -> {
      String data = fetchRemoteData();

      // Kann runLater verwenden, da der Kontext vererbt wurde
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Threads ohne Kontext {#threads-without-context}

Threads, die außerhalb des `Environment`-Kontexts erstellt werden, können `runLater()` nicht verwenden und werfen eine `IllegalStateException`:

```java
// Statischer Initialisierer - kein Environment-Kontext
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  }).start();
}

// Systemtimer-Threads - kein Environment-Kontext
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  }
}, 1000);

// Threads von externen Bibliotheken - kein Environment-Kontext
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  });
```

## Ausführungsverhalten {#execution-behavior}

Das Ausführungsverhalten von `runLater()` hängt davon ab, von welchem Thread es aufgerufen wird:

### Vom UI-Thread {#from-the-ui-thread}

Wenn es vom `Environment`-Thread selbst aufgerufen wird, werden die Aufgaben **synchron und sofort** ausgeführt:

```java
button.onClick(e -> {
  System.out.println("Vorher: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Innerhalb: " + Thread.currentThread().getName());
    return "abgeschlossen";
  });

  System.out.println("Nachher: " + result.isDone());  // true
});
```

Mit diesem synchronen Verhalten werden UI-Updates von Ereignishandlern sofort angewendet und verursachen keine unnötigen Warteschlangenüberhead.

### Von Hintergrund-Threads {#from-background-threads}

Wenn es von einem Hintergrund-Thread aufgerufen wird, werden die Aufgaben **für die asynchrone Ausführung in der Warteschlange platziert**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dies wird im ForkJoinPool-Thread ausgeführt
    System.out.println("Hintergrund: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(() -> {
      // Dies wird im Environment-Thread ausgeführt
      System.out.println("UI-Aktualisierung: " + Thread.currentThread().getName());
      statusLabel.setText("Verarbeitung abgeschlossen");
    });

    // result.isDone() wäre hier false
    // Die Aufgabe ist in der Warteschlange und wird asynchron ausgeführt
  });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrund-Threads eingereicht werden, in **strikter FIFO-Reihenfolge**, um die Reihenfolge der Operationen beizubehalten, selbst wenn sie gleichzeitig von mehreren Threads eingereicht werden. Mit dieser Reihenfolge-Garantie werden UI-Updates in der genauen Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und Thread B dann Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Das Verarbeiten von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Stornierung und ermöglicht es Ihnen, das Ausführen von Warteschlangenaufgaben zu verhindern. Durch die Stornierung ausstehender Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass lang laufende Operationen die UI aktualisieren, nachdem sie nicht mehr benötigt werden.

### Grundlegende Stornierung {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Stornieren, wenn noch nicht ausgeführt
if (!result.isDone()) {
  result.cancel();
}
```

### Verwaltung mehrerer Updates {#managing-multiple-updates}

Bei der Ausführung lang laufender Operationen mit häufigen UI-Aktualisierungen sollten Sie alle ausstehenden Ergebnisse verfolgen:

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

        // Nachverfolgung für eine mögliche Stornierung
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Stornieren Sie alle ausstehenden UI-Updates
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Verwaltung des Lebenszyklus von Komponenten {#component-lifecycle-management}

Wenn Komponenten zerstört werden (z. B. während der Navigation), stornieren Sie alle ausstehenden Updates, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Stornieren Sie alle ausstehenden Updates, um Speicherlecks zu vermeiden
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

1. **Kontextanforderung**: Threads müssen einen `Environment`-Kontext vererbt haben. Threads von externen Bibliotheken, Systemtimern und statischen Initialisierern können diese API nicht verwenden.

2. **Vermeidung von Speicherlecks**: Nachverfolgen und stornieren Sie immer `PendingResult`-Objekte in den Methoden des Lebenszyklus von Komponenten. Warteschlangen-Lambdas erfassen Verweise auf UI-Komponenten und verhindern die Garbage Collection, wenn sie nicht storniert werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strikter FIFO-Reihenfolge ausgeführt, unabhängig von der Wichtigkeit. Es gibt kein Prioritätssystem.

4. **Einschränkungen bei der Stornierung**: Die Stornierung verhindert nur die Ausführung von Warteschlangenaufgaben. Aufgaben, die bereits ausgeführt werden, werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Das Folgende ist eine vollständige, produktionsbereite Implementierung, die alle Best Practices für asynchrone UI-Updates demonstriert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen einzelnen Thread-Executor, um Ressourcenermüdung zu verhindern
  // Für die Produktion sollten Sie einen gemeinsam genutzten, anwendungsweiten Thread-Pool in Betracht ziehen
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Verfolgen Sie die aktuelle Aufgabe und ausstehende UI-Updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-Komponenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demo für Hintergrund-UI-Updates");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sicheres UI-Updates von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Lange Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt der UI aktualisiert. " +
          "Der Button 'Test UI' beweist, dass die UI während der Hintergrundoperation reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Lange Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klicken Sie mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was eine ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrundthreads als auch der Warteschlangen-UPDATES demonstriert.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Konfigurieren Sie Felder
    statusField.setReadOnly(true);
    statusField.setValue("Bereit zum Starten");
    statusField.setLabel("Status");

    // Konfigurieren Sie die Fortschrittsanzeige
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Fortschritt: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Ergebnis");

    // Konfigurieren Sie die Buttons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
    });

    // Komponenten hinzufügen
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Brechen Sie alle laufenden Aufgaben und ausstehenden UI-Updates ab
    cancelTask();

    // Setzen Sie die Aufgabenreferenz zurück
    currentTask = null;

    // Fahren Sie den Instanz-Executor ordnungsgemäß herunter
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starte Hintergrundaufgabe...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Setzen Sie das Abbruchflag zurück und leeren Sie die vorherigen ausstehenden Updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Starten Sie die Hintergrundaufgabe mit explizitem Executor
    // Hinweis: cancel(true) unterbricht den Thread, was Thread.sleep() eine InterruptedException auslösen kann
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulieren Sie eine lange Aufgabe mit 100 Schritten
      for (int i = 0; i <= 100; i++) {
        // Überprüfen Sie, ob abgebrochen wurde
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Aufgabe abgebrochen!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Aufgabe wurde abgebrochen", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // insgesamt 10 Sekunden
        } catch (InterruptedException e) {
          // Der Thread wurde unterbrochen - sofort verlassen
          Thread.currentThread().interrupt(); // Wiederherstellung des Unterbrechungsstatus
          return;
        }

        // Führen Sie einige Berechnungen durch (deterministisch für die Demo)
        // Produziert Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Aktualisierung des Fortschritts aus dem Hintergrundthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Letzte Aktualisierung mit Ergebnis (Dieser Code wird nur erreicht, wenn die Aufgabe ohne
      // Stornierung abgeschlossen wurde)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Aufgabe abgeschlossen!");
          resultField.setValue("Ergebnis: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Hintergrundaufgabe abgeschlossen!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Setzen Sie das Abbruch-Flag
      isCancelled = true;

      // Brechen Sie die Hauptaufgabe ab (unterbricht den Thread)
      currentTask.cancel(true);

      // Brechen Sie alle ausstehenden UI-Updates ab
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Abbrechen der Aufgabe...");
        cancelButton.setEnabled(false);

        showToast("Abbruch angefordert", Theme.GRAY);
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

### Analyse der Fallstudie {#case-study-analysis}

Diese Implementierung demonstriert mehrere kritische Muster:

#### 1. Verwaltung des Thread-Pools {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Verwendet einen **einzelnen Thread-Executor**, um Ressourcenermüdung zu verhindern
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Nachverfolgung ausstehender Updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird nachverfolgt, um Folgendes zu ermöglichen:
- Stornierung, wenn der Benutzer auf Stornieren klickt
- Vermeidung von Speicherlecks in `onDestroy()`
- Ordnungsgemäße Bereinigung während des Lebenszyklus der Komponente

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrund-Thread überprüft dieses Flag in jeder Iteration, was Folgendes ermöglicht:
- Sofortige Reaktion auf die Stornierung
- Sauberes Verlassen der Schleife
- Verhinderung weiterer UI-Aktualisierungen

#### 4. Verwaltung des Lebenszyklus {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Wiederverwendet die Stornierungslogik
  currentTask = null;
  executor.shutdown();
}
```
Kritisch zur Vermeidung von Speicherlecks durch:
- Stornierung aller ausstehenden UI-Updates
- Unterbrechung laufender Threads
- Herunterfahren des Executors

#### 5. Testen der UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während der Hintergrundoperationen reaktionsfähig bleibt.
