---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: ead192e1c1a415742cb0446e2d5c314c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche von Hintergrund-Threads in webforJ-Anwendungen. Diese experimentelle Funktion ermöglicht asynchrone Operationen, während die Threadsicherheit für UI-Modifikationen gewährleistet bleibt.

:::warning Experimentelle API
Diese API ist seit 25.02 als experimentell gekennzeichnet und kann in zukünftigen Versionen geändert werden. Die API-Signatur, das Verhalten und die Leistungsmerkmale können Änderungen unterliegen.
:::

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ erzwingt ein striktes Thread-Modell, bei dem alle UI-Operationen im `Environment`-Thread erfolgen müssen. Diese Einschränkung existiert, weil:

1. **Einschränkungen der webforJ-API**: Die zugrunde liegende webforJ-API bindet an den Thread, der die Sitzung erstellt hat.
2. **Thread-Affinität von Komponenten**: UI-Komponenten halten Zustände, die nicht threadsicher sind.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzelnen Thread verarbeitet.

Dieses Single-Thread-Modell verhindert Wettlaufbedingungen und erhält einen konsistenten Zustand für alle UI-Komponenten, schafft jedoch Herausforderungen bei der Integration mit asynchronen, lang laufenden Berechnungsaufgaben.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zum Planen von UI-Updates:

```java title="Environment.java"
// Planen Sie eine Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Planen Sie eine Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder aufgetretene Ausnahmen bietet.

## Kontextvererbung von Threads {#thread-context-inheritance}

Die automatische Kontextvererbung ist ein wichtiges Merkmal von `Environment.runLater()`. Wenn ein Thread, der in einem `Environment` läuft, Kind-Threads erstellt, erben diese Kinder automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der aus einem `Environment`-Thread erstellt wird, hat automatisch Zugriff auf dieses `Environment`. Diese Vererbung erfolgt automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

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

// System-Timer-Threads - kein Environment-Kontext  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  }
}, 1000);

// Threads aus externen Bibliotheken - kein Environment-Kontext
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  });
```

## Ausführungsverhalten {#execution-behavior}

Das Ausführungsverhalten von `runLater()` hängt davon ab, welcher Thread es aufruft:

### Vom UI-Thread {#from-the-ui-thread}

Wenn es vom `Environment`-Thread selbst aufgerufen wird, werden Aufgaben **synchron und sofort** ausgeführt:

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

Durch dieses synchrone Verhalten werden UI-Updates aus Ereignishandlern sofort angewendet und verursachen keine unnötige Warteschlangenüberhead.

### Von Hintergrund-Threads {#from-background-threads}

Wenn es von einem Hintergrund-Thread aufgerufen wird, werden Aufgaben für die **asynchrone Ausführung in die Warteschlange gestellt**:

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
    
    // result.isDone() wäre hier false
    // Die Aufgabe wird eingereiht und wird asynchron ausgeführt
  });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrund-Threads eingereicht werden, in **strikter FIFO-Reihenfolge** und bewahrt die Reihenfolge der Operationen, selbst wenn sie gleichzeitig von mehreren Threads eingereicht werden. Mit dieser Reihenfolge-Garantie werden UI-Updates genau in der Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und dann Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Das Verarbeiten von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenkündigung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Kündigung, sodass Sie eingereihte Aufgaben daran hindern können, ausgeführt zu werden. Durch die Kündigung ausstehender Aufgaben können Sie Speicherlecks vermeiden und lange laufende Operationen daran hindern, die UI zu aktualisieren, nachdem sie nicht mehr benötigt werden.

### Grundlegende Kündigung {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Kündigen, wenn noch nicht ausgeführt
if (!result.isDone()) {
  result.cancel();
}
```

### Verwaltung mehrerer Updates {#managing-multiple-updates}

Bei lang laufenden Operationen mit häufigen UI-Updates verfolgen Sie alle ausstehenden Ergebnisse:

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
        
        // Nachverfolgung für eventuelle Kündigung
        pendingUpdates.add(update);
        
        Thread.sleep(100);
      }
    });
  }
  
  public void cancelTask() {
    isCancelled = true;
    
    // Kündigen Sie alle ausstehenden UI-Updates
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

Wenn Komponenten zerstört werden (z. B. während der Navigation), kündigen Sie alle ausstehenden Updates, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Kündigen Sie alle ausstehenden Updates, um Speicherlecks zu vermeiden
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

2. **Vermeidung von Speicherlecks**: Verfolgen und kündigen Sie immer `PendingResult`-Objekte in den Lebenszyklusmethoden von Komponenten. Eingereihte Lambdas erfassen Verweise auf UI-Komponenten, wodurch die Müllsammlung verhindert wird, wenn sie nicht gekündigt werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strikter FIFO-Reihenfolge ausgeführt, unabhängig von der Wichtigkeit. Es gibt kein Prioritätssystem.

4. **Kündigungsbeschränkungen**: Die Kündigung verhindert nur die Ausführung eingereihter Aufgaben. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Komplettfallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende Implementierung ist ein vollständiges, produktionsbereites Beispiel, das alle Best Practices für asynchrone UI-Updates demonstriert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen Executor mit einem einzelnen Thread, um Ressourcenerschöpfung zu verhindern
  // Für die Produktion sollten Sie in Betracht ziehen, einen gemeinsam genutzten, anwendungsweiten Thread-Pool zu verwenden
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
  private H2 titleLabel = new H2("Demonstration asynchroner UI-Updates");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Updates von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Langlaufende Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt der UI aktualisiert. " +
          "Die Schaltfläche 'Test UI' beweist, dass die UI während des Hintergrundvorgangs reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Langlaufende Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klicke mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was die ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrund-Threads als auch der eingereihte UI-Updates demonstriert.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Konfigurieren Sie die Felder
    statusField.setReadOnly(true);
    statusField.setValue("Bereit zum Start");
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

    // Konfigurieren Sie die Schaltflächen
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

    // Kündigen Sie jede laufende Aufgabe und ausstehende UI-Updates
    cancelTask();

    // Löschen Sie die Aufgabenreferenz
    currentTask = null;

    // Herunterfahren des Executor instanzengerecht
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starte Hintergrundaufgabe...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Setzen Sie die Abbruchflagge zurück und löschen Sie frühere ausstehende Updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Starten Sie die Hintergrundaufgabe mit explizitem Executor
    // Hinweis: cancel(true) unterbricht den Thread, wodurch Thread.sleep() eine
    // InterruptedException auslösen kann
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulieren Sie lange Aufgaben mit 100 Schritten
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
          Thread.sleep(100); // Insgesamt 10 Sekunden
        } catch (InterruptedException e) {
          // Thread wurde unterbrochen - sofort beenden
          Thread.currentThread().interrupt(); // Unterbrochenen Status wiederherstellen
          return;
        }

        // Durchführung einiger Berechnungen (deterministisch für die Demo)
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

      // Letzte Aktualisierung mit dem Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
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
      // Setzen Sie das Abbruchflag
      isCancelled = true;

      // Kündigen Sie die Hauptaufgabe (unterbricht den Thread)
      currentTask.cancel(true);

      // Kündigen Sie alle ausstehenden UI-Updates
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
`}
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
- Verwendet einen **Executor mit einem einzelnen Thread**, um Ressourcenerschöpfung zu verhindern
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Verfolgung ausstehender Updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird verfolgt, um zu ermöglichen:
- Kündigung, wenn der Benutzer auf abbrechen klickt
- Verhinderung von Speicherlecks in `onDestroy()`
- Ordentliche Bereinigung während des Komponentenlebenszyklus

#### 3. Kooperative Kündigung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrund-Thread prüft dieses Flag in jeder Iteration, wodurch ermöglicht wird:
- Sofortige Reaktion auf Abbrechen
- Sauberer Ausgang aus der Schleife
- Verhinderung weiterer UI-Updates

#### 4. Lebenszyklusverwaltung {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Wiederverwendet die Kündigungslogik
  currentTask = null;
  executor.shutdown();
}
```
Kritisch zur Verhinderung von Speicherlecks, indem:
- Alle ausstehenden UI-Updates gekündigt werden
- Ausgeführte Threads unterbrochen werden
- Der Executor heruntergefahren wird

#### 5. Testen der UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während Hintergrundoperationen reaktionsfähig bleibt.
