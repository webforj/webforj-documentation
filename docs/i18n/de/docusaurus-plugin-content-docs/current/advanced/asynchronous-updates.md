---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: cbdf51a80355d73a6c7f5ec85cfa198a
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus, um sicher das UI von Hintergrund-Threads in webforJ-Anwendungen zu aktualisieren. Diese experimentelle Funktion ermöglicht asynchrone Operationen und gewährleistet gleichzeitig die Thread-Sicherheit bei UI-Änderungen.

<ExperimentalWarning />

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ erzwingt ein striktes Thread-Modell, in dem alle UI-Operationen im `Environment`-Thread stattfinden müssen. Diese Einschränkung existiert, weil:

1. **webforJ API-Beschränkungen**: Die zugrunde liegende webforJ-API bindet sich an den Thread, der die Sitzung erstellt hat.
2. **Thread-Zugehörigkeit von Komponenten**: UI-Komponenten behalten einen Zustand, der nicht thread-sicher ist.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzigen Thread verarbeitet.

Dieses einthreadige Modell verhindert Race Conditions und erhält einen konsistenten Zustand für alle UI-Komponenten, stellt jedoch eine Herausforderung dar, wenn man mit asynchronen, lang laufenden Berechnungsaufgaben integriert ist.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zum Planen von UI-Updates:

```java title="Environment.java"
// Planen Sie eine Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Planen Sie eine Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss von Aufgaben verfolgt und Zugriff auf das Ergebnis oder etwaige aufgetretene Ausnahmen bietet.

## Kontextvererbung {#thread-context-inheritance}

Die automatische Kontextvererbung ist ein kritisches Merkmal von `Environment.runLater()`. Wenn ein Thread, der in einem `Environment` läuft, Kind-Threads erstellt, erben diese Kinder automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der von einem `Environment`-Thread erstellt wird, hat automatisch Zugriff auf dieses `Environment`. Diese Vererbung erfolgt automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Dieser Thread hat den Environment-Kontext
    
    // Kind-Threads erben den Kontext automatisch
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Kann runLater verwenden, weil der Kontext vererbt wurde
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
// Statische Initialisierung - kein Environment-Kontext
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  }).start();
}

// System-Timer-Threads - kein Environment-Kontext  
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

Das Ausführungsverhalten von `runLater()` hängt von dem Thread ab, der es aufruft:

### Vom UI-Thread {#from-the-ui-thread}

Wenn es vom `Environment`-Thread selbst aufgerufen wird, werden Aufgaben **synchron und sofort** ausgeführt:

```java
button.onClick(e -> {
  System.out.println("Before: " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Inside: " + Thread.currentThread().getName());
    return "completed";
  });
  
  System.out.println("After: " + result.isDone());  // true
});
```

Mit diesem synchronen Verhalten werden UI-Updates aus Ereignishandlern sofort angewendet und verursachen keine unnötigen Warteschlangenüberhänge.

### Von Hintergrund-Threads {#from-background-threads}

Wenn es von einem Hintergrund-Thread aufgerufen wird, werden Aufgaben **zum asynchronen Ausführen in die Warteschlange gestellt**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dies läuft auf ForkJoinPool-Thread
    System.out.println("Background: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Dies läuft im Environment-Thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Verarbeitung abgeschlossen");
    });
    
    // result.isDone() wäre hier false
    // Die Aufgabe wird in die Warteschlange gestellt und wird asynchron ausgeführt
  });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrund-Threads eingereicht werden, in **strikter FIFO-Reihenfolge**, wobei die Reihenfolge der Operationen auch dann beibehalten wird, wenn sie gleichzeitig von mehreren Threads eingereicht werden. Mit dieser Reihenfolge-Garantie werden UI-Updates in genau der Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und später Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Die Verarbeitung von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Stornierung, sodass Sie die Ausführung von in der Warteschlange befindlichen Aufgaben verhindern können. Durch die Stornierung ausstehender Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass lang laufende Operationen die UI aktualisieren, nachdem sie nicht mehr benötigt werden.

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

Bei lang laufenden Operationen mit häufigen UI-Updates sollten alle ausstehenden Ergebnisse verfolgt werden:

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
        
        // Verfolgen für potenzielle Stornierung
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

Wenn Komponenten zerstört werden (z. B. während der Navigation), sollten alle ausstehenden Updates storniert werden, um Speicherlecks zu vermeiden:

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

## Designüberlegungen {#design-considerations}

1. **Kontextanforderung**: Threads müssen einen `Environment`-Kontext geerbt haben. Threads von externen Bibliotheken, Systemtimern und statischen Initialisierungen können diese API nicht verwenden.

2. **Vermeidung von Speicherlecks**: Verfolgen und stornieren Sie immer `PendingResult`-Objekte in den Lifecycle-Methoden der Komponenten. In der Warteschlange befindliche Lambdas erfassen Verweise auf UI-Komponenten und verhindern die Garbage Collection, wenn sie nicht abgebrochen werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strikter FIFO-Reihenfolge ausgeführt, unabhängig von der Bedeutung. Es gibt kein Prioritätssystem.

4. **Stornierungsbeschränkungen**: Stornierung verhindert nur die Ausführung von in der Warteschlange befindlichen Aufgaben. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende Implementierung ist ein vollständiges, produktionsbereites Beispiel, das alle Best Practices für asynchrone UI-Updates demonstriert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen einzelnen Thread-Executor, um eine Ressourcenerschöpfung zu verhindern
  // Für die Produktion sollten Sie einen freigegebenen Thread-Pool für die gesamte Anwendung in Betracht ziehen
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
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Updates von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Lange Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt der UI aktualisiert. " +
          "Der Button 'Test UI' beweist, dass die UI während der Hintergrundoperation reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Lange Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klick mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was eine ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrund-Threads als auch der in der Warteschlange befindlichen UI-Updates demonstriert.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Konfigurieren der Felder
    statusField.setReadOnly(true);
    statusField.setValue("Bereit zum Start");
    statusField.setLabel("Status");

    // Fortschrittsbalken konfigurieren
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

    // Buttons konfigurieren
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

    // Beenden Sie alle aktiven Aufgaben und ausstehenden UI-Updates
    cancelTask();

    // Löschen Sie die Aufgabenreferenz
    currentTask = null;

    // Fahren Sie den Executor instanzschonend herunter
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starte Hintergrundaufgabe...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Setzen Sie das Abgebrochen-Flag zurück und löschen Sie die vorherigen ausstehenden Updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Starten Sie die Hintergrundaufgabe mit explizitem Executor
    // Hinweis: cancel(true) unterbricht den Thread, was dazu führt, dass Thread.sleep() eine
    // InterruptedException auslöst
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulieren Sie eine lange Aufgabe mit 100 Schritten
      for (int i = 0; i <= 100; i++) {
        // Prüfen, ob abgebrochen
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Aufgabe abgebrochen!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Die Aufgabe wurde abgebrochen", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Insgesamt 10 Sekunden
        } catch (InterruptedException e) {
          // Thread wurde unterbrochen - sofort verlassen
          Thread.currentThread().interrupt(); // Unterbrechungsstatus wiederherstellen
          return;
        }

        // Führen Sie eine Berechnung durch (deterministisch für die Demo)
        // Produziert Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Aktualisieren Sie den Fortschritt vom Hintergrund-Thread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Finale Aktualisierung mit Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
      // Abbruch abgeschlossen wurde)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Aufgabe abgeschlossen!");
          resultField.setValue("Ergebnis: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Hintergrundaufgabe beendet!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Setzen Sie das Abgebrochen-Flag
      isCancelled = true;

      // Stornieren Sie die Hauptaufgabe (unterbricht den Thread)
      currentTask.cancel(true);

      // Stornieren Sie alle ausstehenden UI-Updates
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

Diese Implementierung zeigt mehrere kritische Muster:

#### 1. Verwaltung des Thread-Pools {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Verwendet einen **einzelnen Thread-Executor**, um eine Ressourcenerschöpfung zu verhindern.
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern.

#### 2. Verfolgen ausstehender Updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder `Environment.runLater()`-Aufruf wird verfolgt, um zu ermöglichen:
- Stornierung, wenn der Benutzer auf Abbrechen klickt.
- Vermeidung von Speicherlecks in `onDestroy()`.
- Ordentliche Bereinigung während des Komponenten-Lebenszyklus.

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrund-Thread überprüft dieses Flag bei jeder Iteration, was ermöglicht:
- Sofortige Reaktion auf Stornierung.
- Sauberes Verlassen der Schleife.
- Verhinderung weiterer UI-Updates.

#### 4. Lebenszyklusverwaltung {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Wiederverwendung der Stornierungslogik
  currentTask = null;
  executor.shutdown();
}
```
Kritisch zur Vermeidung von Speicherlecks durch:
- Stornierung aller ausstehenden UI-Updates.
- Unterbrechung laufender Threads.
- Herunterfahren des Executors.

#### 5. Test der UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während der Hintergrundoperationen reaktionsfähig bleibt.
