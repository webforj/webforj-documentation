---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 44d86e725d9228ead98794da8f6210ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche von Hintergrundthreads in webforJ-Anwendungen. Diese experimentelle Funktion ermöglicht asynchrone Operationen, während die Thread-Sicherheit für UI-Änderungen gewährleistet bleibt.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ erzwingt ein strenges Thread-Modell, bei dem alle UI-Operationen im `Environment`-Thread stattfinden müssen. Diese Einschränkung existiert, weil:

1. **Einschränkungen der webforJ API**: Die zugrunde liegende webforJ API ist an den Thread gebunden, der die Sitzung erstellt hat.
2. **Thread-Zugehörigkeit von Komponenten**: UI-Komponenten halten Zustände, die nicht threadsicher sind.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzigen Thread verarbeitet.

Dieses ein-Threadmodell verhindert Race-Conditions und sorgt für einen konsistenten Zustand aller UI-Komponenten, schafft jedoch Herausforderungen, wenn es um die Integration mit asynchronen, langlaufenden Berechnungsaufgaben geht.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zur Planung von UI-Aktualisierungen:

```java title="Environment.java"
// Planen Sie eine Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Planen Sie eine Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder aufgetretene Ausnahmen gewährt.

## Erbschaft des Thread-Kontexts {#thread-context-inheritance}

Die automatische Kontextvererbung ist eine kritische Funktion von `Environment.runLater()`. Wenn ein Thread, der in einem `Environment` läuft, Kind-Threads erstellt, erben diese Kinder automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der innerhalb eines `Environment`-Threads erstellt wird, hat automatisch Zugriff auf dieses `Environment`. Diese Vererbung erfolgt automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Dieser Thread hat den Kontext von Environment
    
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
// Statischer Initialisierer - kein Kontext von Environment
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  }).start();
}

// System-Timer-Threads - kein Kontext von Environment  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  }
}, 1000);

// Threads aus externen Bibliotheken - kein Kontext von Environment
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  });
```

## Ausführungsverhalten {#execution-behavior}

Das Ausführungsverhalten von `runLater()` hängt davon ab, welcher Thread es aufruft:

### Vom UI-Thread {#from-the-ui-thread}

Wenn es vom `Environment`-Thread selbst aufgerufen wird, führen Aufgaben **synchron und sofort aus**:

```java
button.onClick(e -> {
  System.out.println("Vorher: " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Innere: " + Thread.currentThread().getName());
    return "vollständig";
  });
  
  System.out.println("Nachher: " + result.isDone());  // true
});
```

Mit diesem synchronen Verhalten werden UI-Updates von Ereignis-Handlern sofort angewendet und es entstehen keine unnötigen Warteschlangenüberhänge.

### Von Hintergrundthreads {#from-background-threads}

Wenn es von einem Hintergrundthread aufgerufen wird, werden Aufgaben **für die asynchrone Ausführung in die Warteschlange gestellt**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dies läuft auf einem ForkJoinPool-Thread
    System.out.println("Hintergrund: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Dies läuft im Environment-Thread
      System.out.println("UI-Aktualisierung: " + Thread.currentThread().getName());
      statusLabel.setText("Verarbeitung abgeschlossen");
    });
    
    // result.isDone() wäre hier falsch
    // Die Aufgabe wird in die Warteschlange gestellt und wird asynchron ausgeführt
  });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrundthreads eingereicht werden, in **strenger FIFO-Reihenfolge** und behält die Reihenfolge der Operationen auch bei gleichzeitiger Einreichung von mehreren Threads bei. Mit dieser Ordnungsgewährleistung werden UI-Updates in der genauen Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Das Verarbeiten von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Stornierung und ermöglicht es Ihnen, die Ausführung von geplanten Aufgaben zu verhindern. Durch das Abbrechen ausstehender Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass lang laufende Operationen die UI aktualisieren, wenn sie nicht mehr benötigt werden.

### Grundlegende Stornierung {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Abbrechen, wenn noch nicht ausgeführt
if (!result.isDone()) {
  result.cancel();
}
```

### Verwaltung mehrerer Updates {#managing-multiple-updates}

Bei lang laufenden Vorgängen mit häufigen UI-Updates verfolgen Sie alle ausstehenden Ergebnisse:

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
        
        // Nachverfolgung für mögliche Stornierung
        pendingUpdates.add(update);
        
        Thread.sleep(100);
      }
    });
  }
  
  public void cancelTask() {
    isCancelled = true;
    
    // Alle ausstehenden UI-Updates stornieren
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Verwaltung des Komponentenlebenszyklus {#component-lifecycle-management}

Wenn Komponenten zerstört werden (z.B. während der Navigation), stornieren Sie alle ausstehenden Updates, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Alle ausstehenden Updates stornieren, um Speicherlecks zu vermeiden
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Entwurfserwägungen {#design-considerations}

1. **Kontextanforderung**: Threads müssen einen `Environment`-Kontext geerbt haben. Threads aus externen Bibliotheken, Systemtimern und statischen Initialisierern können diese API nicht verwenden.

2. **Verhinderung von Speicherlecks**: Verfolgen und stornieren Sie immer `PendingResult`-Objekte in Komponentenlebenszyklusmethoden. In Warteschlangen stehende Lambdas erfassen Referenzen auf UI-Komponenten, was die Garbage Collection verhindert, wenn sie nicht storniert werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strenger FIFO-Reihenfolge ausgeführt, unabhängig von der Wichtigkeit. Es gibt kein Prioritätssystem.

4. **Einschränkungen bei der Stornierung**: Die Stornierung verhindert nur die Ausführung von wartenden Aufgaben. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende Implementierung ist eine komplette, produktionsbereite Umsetzung, die alle Best Practices für asynchrone UI-Aktualisierungen demonstriert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen Executor mit einem einzelnen Thread, um Ressourcenerschöpfung zu verhindern
  // Für die Produktion sollte ein gemeinsamer, anwendungsweiter Thread-Pool in Betracht gezogen werden
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
  private H2 titleLabel = new H2("Demonstration von Hintergrund-UIs-Updates");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Updates von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Langfristige Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt der UI aktualisiert. " +
          "Die Schaltfläche 'Test UI' beweist, dass die UI während der Hintergrundoperationen reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Langfristige Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klicken Sie hier!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was die ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrundthreads als auch der wartenden UI-Updates demonstriert.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Konfigurieren Sie die Felder
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

    // Konfigurieren Sie die Tasten
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

    // Löschen Sie den Aufgabenverweis
    currentTask = null;

    // Fahren Sie den Iterator-Eexecutor ordnungsgemäß herunter
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Hintergrundaufgabe gestartet...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Setzen Sie das Abbruchkennzeichen zurück und löschen Sie vorherige ausstehende Updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Starten Sie die Hintergrundaufgabe mit explizitem Executor
    // Hinweis: cancel(true) unterbricht den Thread, wodurch Thread.sleep() eine
    // InterruptedException wirft
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulieren Sie eine lang laufende Aufgabe mit 100 Schritten
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
          // Der Thread wurde unterbrochen - sofort aussteigen
          Thread.currentThread().interrupt(); // Wiederherstellung des Unterbrechungsstatus
          return;
        }

        // Führen Sie einige Berechnungen durch (deterministisch für die Demo)
        // Erzeugt Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update den Fortschritt aus dem Hintergrundthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Letztes Update mit Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
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
      // Setzen Sie das Abbruchkennzeichen
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
        statusField.setValue("Aufgabe wird abgebrochen...");
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
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Fallstudienanalyse {#case-study-analysis}

Diese Implementierung demonstriert mehrere kritische Muster:

#### 1. Verwaltung des Thread-Pools {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Verwendet einen **Einzel-Thread-Executor**, um Ressourcenerschöpfung zu verhindern
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Verfolgen ausstehender Updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder `Environment.runLater()`-Aufruf wird nachverfolgt, um Folgendes zu ermöglichen:
- Stornierung, wenn der Benutzer auf Abbrechen klickt
- Verhinderung von Speicherlecks in `onDestroy()`
- ordnungsgemäße Bereinigung während des Komponentenlebenszyklus

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrundthread überprüft dieses Kennzeichen bei jeder Iteration, was Folgendes ermöglicht:
- Sofortige Reaktion auf die Stornierung
- Sauberer Ausstieg aus der Schleife
- Vermeidung weiterer UI-Updates

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
- Abbrechen aller ausstehenden UI-Updates
- Unterbrechen laufender Threads
- Herunterfahren des Executors

#### 5. Testen der UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während Hintergrundoperationen reaktionsfähig bleibt.
