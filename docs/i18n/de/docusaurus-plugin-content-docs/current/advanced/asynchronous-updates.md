---
sidebar_position: 55
title: Asynchronous Updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 1f53158dabc9d0270dfe80c1df5bb122
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche von Hintergrundthreads in webforJ-Anwendungen. Dieses experimentelle Feature ermöglicht asynchrone Vorgänge, während die Thread-Sicherheit für UI-Änderungen gewährleistet bleibt.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ zwingt ein strenges Thread-Modell auf, bei dem alle UI-Vorgänge im `Environment`-Thread stattfinden müssen. Diese Einschränkung besteht, weil:

1. **Einschränkungen der webforJ API**: Die zugrunde liegende webforJ API ist an den Thread gebunden, der die Sitzung erstellt hat.
2. **Thread-Affinität der Komponenten**: UI-Komponenten halten einen Zustand, der nicht threadsicher ist.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell auf einem einzelnen Thread verarbeitet.

Dieses einäugige Modell verhindert Rennbedingungen und hält einen konsistenten Zustand für alle UI-Komponenten aufrecht, stellt jedoch eine Herausforderung dar, wenn man mit asynchronen, langanhaltenden Berechnungsaufgaben integriert.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zur Planung von UI-Aktualisierungen:

```java title="Environment.java"
// Planen einer Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Planen einer Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder alle aufgetretenen Ausnahmen bietet.

## Vererbung des Thread-Kontexts {#thread-context-inheritance}

Die automatische Kontextvererbung ist ein kritisches Merkmal von `Environment.runLater()`. Wenn ein Thread, der in einem `Environment`-Thread läuft, Kind-Threads erstellt, erben diese Kinder automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der aus einem `Environment`-Thread erstellt wird, hat automatisch Zugriff auf dieses `Environment`. Diese Vererbung geschieht automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

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

// Threads externer Bibliotheken - kein Environment-Kontext
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Wirft IllegalStateException
  });
```

## Ausführungsverhalten {#execution-behavior}

Das Ausführungsverhalten von `runLater()` hängt davon ab, welcher Thread ihn aufruft:

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

Mit diesem synchronen Verhalten werden UI-Aktualisierungen von Ereignis-Handlern sofort angewendet und verursachen keine unnötigen Warteschlangen-Overheads.

### Von Hintergrund-Threads {#from-background-threads}

Wenn es von einem Hintergrund-Thread aufgerufen wird, werden Aufgaben **zur asynchronen Ausführung in die Warteschlange gestellt**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dies läuft im ForkJoinPool-Thread
    System.out.println("Hintergrund: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(() -> {
      // Dies läuft im Environment-Thread
      System.out.println("UI-Aktualisierung: " + Thread.currentThread().getName());
      statusLabel.setText("Verarbeitung abgeschlossen");
    });

    // result.isDone() wäre hier false
    // Die Aufgabe ist in die Warteschlange gestellt und wird asynchron ausgeführt
  });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrund-Threads eingereicht werden, in **strikter FIFO-Reihenfolge**, wobei die Reihenfolge der Vorgänge beibehalten wird, auch wenn sie gleichzeitig von mehreren Threads eingereicht werden. Mit dieser Garantierung der Reihenfolge werden UI-Aktualisierungen in genau der Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und dann Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Die Verarbeitung von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Stornierung, sodass Sie verhindern können, dass Warteschlangenaufgaben ausgeführt werden. Durch das Stornieren ausstehender Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass langlaufende Vorgänge die UI aktualisieren, nachdem sie nicht mehr benötigt werden.

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

### Verwaltung mehrerer Aktualisierungen {#managing-multiple-updates}

Bei lang laufenden Operationen mit häufigen UI-Aktualisierungen sollten alle ausstehenden Ergebnisse verfolgt werden:

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

        // Für eine mögliche Stornierung verfolgen
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Alle ausstehenden UI-Aktualisierungen stornieren
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

Wenn Komponenten zerstört werden (z. B. während der Navigation), sollten alle ausstehenden Aktualisierungen storniert werden, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Alle ausstehenden Aktualisierungen stornieren, um Speicherlecks zu vermeiden
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

1. **Kontextanforderung**: Threads müssen einen `Environment`-Kontext geerbt haben. Threads externer Bibliotheken, Systemtimer und statische Initialisierer können diese API nicht verwenden.

2. **Vermeidung von Speicherlecks**: Verfolgen Sie immer `PendingResult`-Objekte in Komponenten-Lebenszyklusmethoden. Warteschlangen-Lambdas erfassen Referenzen auf UI-Komponenten, was die Garbage Collection verhindert, wenn sie nicht storniert werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strikter FIFO-Reihenfolge ausgeführt, unabhängig von ihrer Wichtigkeit. Es gibt kein Prioritätssystem.

4. **Limitierungen der Stornierung**: Die Stornierung verhindert nur die Ausführung von Warteschlangenaufgaben. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende vollständige, produktionsbereite Implementierung demonstriert alle Best Practices für asynchrone UI-Aktualisierungen:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen Executor mit einem einzelnen Thread, um Ressourcenerschöpfung zu vermeiden
  // Für die Produktion sollten Sie in Betracht ziehen, einen gemeinsam genutzten, anwendungsweiten Thread-Pool zu verwenden
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Verfolgen Sie die aktuelle Aufgabe und ausstehende UI-Aktualisierungen
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-Komponenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demonstration von Hintergrund-UI-Aktualisierungen");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Aktualisierungen von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Langfristige Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt der UI aktualisiert. " +
          "Die Schaltfläche 'Test UI' beweist, dass die UI während des Hintergrundbetriebs reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Langfristige Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klicken Sie mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was sowohl die Bereinigung des Hintergrund-Threads als auch der wartenden UI-Aktualisierungen demonstriert.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Felder konfigurieren
    statusField.setReadOnly(true);
    statusField.setValue("Bereit zum Starten");
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

    // Schaltflächen konfigurieren
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

    // Jede laufende Aufgabe und ausstehende UI-Aktualisierungen abbrechen
    cancelTask();

    // Aufgabenreferenz löschen
    currentTask = null;

    // Den Executor sanft herunterfahren
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starte Hintergrundaufgabe...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Stornierten Flag zurücksetzen und vorherige ausstehende Aktualisierungen löschen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Hintergrundaufgabe mit explizitem Executor starten
    // Hinweis: cancel(true) unterbricht den Thread, wodurch Thread.sleep() eine
    // InterruptedException auslöst.
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuliere langfristige Aufgabe mit 100 Schritten
      for (int i = 0; i <= 100; i++) {
        // Überprüfen, ob abgebrochen wurde
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
          Thread.currentThread().interrupt(); // Unterbrechungsstatus wiederherstellen
          return;
        }

        // Durchführung einer Berechnung (deterministisch für die Demo)
        // Erzeugt Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Fortschritt aus dem Hintergrund-Thread aktualisieren
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // letzte Aktualisierung mit Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
      // Stornierung beendet wird)
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
      // Abgebrochenes Flag setzen
      isCancelled = true;

      // Die Hauptaufgabe abbrechen (unterbricht den Thread)
      currentTask.cancel(true);

      // Alle ausstehenden UI-Aktualisierungen abbrechen
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
- Verwendung eines **Executor mit einem einzelnen Thread**, um Ressourcenerschöpfung zu vermeiden
- Erstellung von **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Verfolgen ausstehender Aktualisierungen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird verfolgt, um:
- Stornierung zu ermöglichen, wenn der Benutzer auf Abbrechen klickt
- Speicherlecks in `onDestroy()` zu verhindern
- Eine ordnungsgemäße Bereinigung während des Komponentenlebenszyklus sicherzustellen

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrund-Thread überprüft dieses Flag bei jeder Iteration, was ermöglicht:
- Sofortige Reaktion auf die Stornierung
- Sauberes Verlassen der Schleife
- Verhindern weiterer UI-Aktualisierungen

#### 4. Verwaltung des Lebenszyklus {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Verwendet die Logik zur Stornierung wieder
  currentTask = null;
  executor.shutdown();
}
```
Kritisch zur Vermeidung von Speicherlecks durch:
- Stornieren aller ausstehenden UI-Aktualisierungen
- Unterbrechen der laufenden Threads
- Herunterfahren des Executors

#### 5. Testen der UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während Hintergrundoperationen reaktionsfähig bleibt.
