---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone Aktualisierungen
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 96865999eae550e8c20172458695ee01
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

Die API `Environment.runLater()` bietet einen Mechanismus, um die UI in webforJ-Anwendungen sicher von Hintergrundthreads aus zu aktualisieren. Dieses Vorschaufeature ermöglicht asynchrone Vorgänge und gewährleistet gleichzeitig die Thread-Sicherheit bei UI-Änderungen.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Das Thread-Modell verstehen {#understanding-the-thread-model}

webforJ erzwingt ein striktes Thread-Modell, bei dem alle UI-Vorgänge im `Environment`-Thread ausgeführt werden müssen. Diese Einschränkung hat folgende Gründe:

1. **Einschränkungen der webforJ-API**: Die zugrunde liegende webforJ-API ist an den Thread gebunden, der die Sitzung erstellt hat
2. **Thread-Affinität von Komponenten**: UI-Komponenten verwalten einen Zustand, der nicht Thread-sicher ist
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzelnen Thread verarbeitet

Dieses Single-Thread-Modell verhindert Race Conditions und gewährleistet einen konsistenten Zustand aller UI-Komponenten. Bei der Integration mit asynchronen, lang laufenden Berechnungsaufgaben entstehen dadurch jedoch Herausforderungen.

Jede Benutzersitzung hat ihre eigene `Environment`-Instanz und ihren eigenen `Environment`-Thread. Ein Hintergrundthread gehört zu keiner Sitzung. Daher muss ihm mitgeteilt werden, welche `Environment`-Instanz aktualisiert werden soll.

## Die `RunLater`-API {#runlater-api}

Die API `Environment.runLater()` bietet zwei Methoden zum Planen von UI-Aktualisierungen. Beide erwarten die Ziel-`Environment` als erstes Argument:

```java title="Environment.java"
// Eine Aufgabe ohne Rückgabewert planen
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Eine Aufgabe planen, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder auf aufgetretene Ausnahmen bietet.

:::warning[Überladungen ohne Ziel]
Die Überladungen `runLater(Runnable)` und `runLater(Supplier)` ermitteln das Ziel anhand des Threads, der den aufrufenden Thread erstellt hat. In einem Threadpool, der von mehreren Benutzern gemeinsam genutzt wird, wird jede Aufgabe in der `Environment` des Benutzers ausgeführt, dessen Aufruf den Pool-Thread erstellt hat. Diese Überladungen sind seit 26.10 veraltet und sollen mit der nächsten Hauptversion entfernt werden. Übergeben Sie immer das Ziel.
:::

## Das Ziel übergeben {#passing-the-target}

`Environment.getCurrent()` gibt die `Environment` des aktuellen Threads zurück. Im `Environment`-Thread, in dem Konstruktoren, Ereignis-Handler und Lifecycle-Methoden ausgeführt werden, gibt die Methode die `Environment` der Sitzung zurück. In einem Hintergrundthread gibt sie `null` zurück.

Lesen Sie das Ziel dort aus, wo es verfügbar ist, also bevor die Hintergrundarbeit beginnt, und übergeben Sie es an diese Arbeit:

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Der Konstruktor wird im Environment-Thread ausgeführt
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

Da das Ziel als Argument übergeben wird, funktioniert der Aufruf von jedem Thread aus: von einem Thread, den die View erstellt hat, einem Spring-`@Async`-Thread, dem gemeinsamen `ForkJoinPool`, einem Timer-Thread oder einem Callback-Thread eines HTTP-Clients.

### Das Ziel im falschen Thread auslesen {#reading-the-target-on-the-wrong-thread}

Der eine Fehler, den Sie vermeiden sollten, ist, `Environment.getCurrent()` innerhalb der Hintergrundarbeit auszulesen. Dort gibt die Methode `null` zurück, und `runLater()` wirft eine `NullPointerException`:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Falsch: getCurrent() gibt in einem Hintergrundthread null zurück
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Dasselbe gilt für statische Initialisierer, Threads von `java.util.Timer` und Callbacks externer Bibliotheken. Keiner davon verfügt über eine `Environment`. Das Ziel muss daher aus einem Feld, einem Parameter oder einer lokalen Variable stammen, die im `Environment`-Thread belegt wurde.

## Ausführungsverhalten {#execution-behavior}

Das Ausführungsverhalten von `runLater()` hängt davon ab, welcher Thread die Methode aufruft:

### Aufruf im UI-Thread {#from-the-ui-thread}

Wird die Methode im eigenen `Environment`-Thread des Ziels aufgerufen, werden Aufgaben **synchron und sofort** ausgeführt:

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Vorher: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Innerhalb: " + Thread.currentThread().getName());
    return "abgeschlossen";
  });

  System.out.println("Nachher: " + result.isDone());  // true
});
```

Dank dieses synchronen Verhaltens werden UI-Aktualisierungen aus Ereignis-Handlern sofort angewendet und verursachen keinen unnötigen Aufwand durch Warteschlangen.

### Aufruf in Hintergrundthreads {#from-background-threads}

Wird die Methode von einem anderen Thread aus aufgerufen, werden Aufgaben **zur asynchronen Ausführung in eine Warteschlange gestellt**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // Wird in einem ForkJoinPool-Thread ausgeführt
    System.out.println("Hintergrund: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // Wird im Environment-Thread ausgeführt
      System.out.println("UI-Aktualisierung: " + Thread.currentThread().getName());
      statusLabel.setText("Verarbeitung abgeschlossen");
    });

    // result.isDone() wäre hier false
    // Die Aufgabe wird in die Warteschlange gestellt und asynchron ausgeführt
  });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrundthreads übermittelt werden, in **strikter FIFO-Reihenfolge**. Dadurch bleibt die Reihenfolge der Vorgänge auch dann erhalten, wenn mehrere Threads gleichzeitig Aufgaben übermitteln. Dank dieser Reihenfolgegarantie werden UI-Aktualisierungen exakt in der Reihenfolge angewendet, in der sie übermittelt wurden. Übermittelt Thread A also Aufgabe 1 und anschließend Thread B Aufgabe 2, wird Aufgabe 1 im UI-Thread immer vor Aufgabe 2 ausgeführt. Die FIFO-Verarbeitung verhindert Inkonsistenzen in der UI.

## Aufgaben abbrechen {#task-cancellation}

Das von `Environment.runLater()` zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> unterstützt den Abbruch. Damit können Sie verhindern, dass Aufgaben aus der Warteschlange ausgeführt werden. Durch das Abbrechen ausstehender Aufgaben lassen sich Speicherlecks vermeiden und verhindern, dass lang laufende Vorgänge die UI aktualisieren, nachdem sie nicht mehr benötigt werden.

### Einfacher Abbruch {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Abbrechen, falls die Aufgabe noch nicht ausgeführt wurde
if (!result.isDone()) {
  result.cancel();
}
```

### Mehrere Aktualisierungen verwalten {#managing-multiple-updates}

Wenn Sie lang laufende Vorgänge mit häufigen UI-Aktualisierungen ausführen, verfolgen Sie alle ausstehenden Ergebnisse:

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

        // Für einen möglichen Abbruch speichern
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Alle ausstehenden UI-Aktualisierungen abbrechen
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Lifecycle-Verwaltung von Komponenten {#component-lifecycle-management}

Wenn Komponenten zerstört werden (z. B. bei der Navigation), brechen Sie alle ausstehenden Aktualisierungen ab, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Alle ausstehenden Aktualisierungen abbrechen, um Speicherlecks zu vermeiden
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Überlegungen zum Design {#design-considerations}

1. **Ziel erforderlich**: Die Ziel-`Environment` wird mit `Environment.getCurrent()` im `Environment`-Thread ausgelesen und an die Hintergrundarbeit übergeben. Wird sie in einem Hintergrundthread ausgelesen, gibt die Methode `null` zurück.

2. **Speicherlecks vermeiden**: Verfolgen Sie `PendingResult`-Objekte immer und brechen Sie sie in Lifecycle-Methoden von Komponenten ab. Lambdas in der Warteschlange halten Referenzen auf UI-Komponenten fest und verhindern deren Garbage Collection, wenn sie nicht abgebrochen werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden unabhängig von ihrer Wichtigkeit in strikter FIFO-Reihenfolge ausgeführt. Es gibt kein Prioritätssystem.

4. **Einschränkungen beim Abbruch**: Durch den Abbruch wird nur die Ausführung von Aufgaben verhindert, die sich noch in der Warteschlange befinden. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende vollständige, produktionsreife Implementierung zeigt alle Best Practices für asynchrone UI-Aktualisierungen:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Einen einzelnen Thread-Executor verwenden, um eine Ressourcenerschöpfung zu verhindern
  // Für den Produktivbetrieb sollte ein anwendungsweit gemeinsam genutzter Threadpool in Betracht gezogen werden
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Die aktuelle Aufgabe und ausstehende UI-Aktualisierungen verfolgen
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-Komponenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demo: UI-Aktualisierungen im Hintergrund");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Aktualisierungen aus Hintergrundthreads ermöglicht. " +
          "Klicken Sie auf 'Lange Aufgabe starten', um eine zehn Sekunden dauernde Berechnung im Hintergrund auszuführen, die den Fortschritt in der UI aktualisiert. " +
          "Die Schaltfläche 'UI testen' zeigt, dass die UI während des Vorgangs im Hintergrund reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Lange Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("UI testen – hier klicken!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden. So wird die ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrundthreads als auch der eingereihten UI-Aktualisierungen veranschaulicht.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Felder konfigurieren
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

    // Schaltflächen konfigurieren
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Klick #" + count + " – die UI reagiert!", Theme.GRAY);
    });

    // Komponenten hinzufügen
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Laufende Aufgaben und ausstehende UI-Aktualisierungen abbrechen
    cancelTask();

    // Aufgabenreferenz löschen
    currentTask = null;

    // Den Executor der Instanz ordnungsgemäß herunterfahren
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Hintergrundaufgabe wird gestartet...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Abbruch-Flag zurücksetzen und zuvor ausstehende Aktualisierungen löschen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Die Ziel-Environment im UI-Thread auslesen, bevor die Hintergrundaufgabe beginnt
    Environment env = Environment.getCurrent();

    // Hintergrundaufgabe mit explizitem Executor starten
    // Hinweis: cancel(true) unterbricht den Thread, wodurch Thread.sleep() eine
    // InterruptedException auslöst
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Lang laufende Aufgabe mit 100 Schritten simulieren
      for (int i = 0; i <= 100; i++) {
        // Prüfen, ob die Aufgabe abgebrochen wurde
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
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
          // Thread wurde unterbrochen – sofort beenden
          Thread.currentThread().interrupt(); // Unterbrechungsstatus wiederherstellen
          return;
        }

        // Berechnung durchführen (für die Demo deterministisch)
        // Ergibt Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Fortschritt vom Hintergrundthread aus aktualisieren
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Abschließende Aktualisierung mit Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe
      // ohne Abbruch abgeschlossen wurde)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
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
      // Abbruch-Flag setzen
      isCancelled = true;

      // Hauptaufgabe abbrechen (unterbricht den Thread)
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
```

</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Analyse der Fallstudie {#case-study-analysis}

Diese Implementierung veranschaulicht mehrere wichtige Muster:

#### 1. Threadpool-Verwaltung {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Verwendet einen **Executor mit einem einzelnen Thread**, um eine Ressourcenerschöpfung zu verhindern
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Ziel festlegen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` wird im `Environment`-Thread ausgeführt. Daher wird das Ziel dort ausgelesen und von der Hintergrundaufgabe an jeden Aufruf von `Environment.runLater()` übergeben. Derselbe Code funktioniert unverändert auch mit einem anwendungsweit gemeinsam genutzten Threadpool.

#### 3. Ausstehende Aktualisierungen verfolgen {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird verfolgt, um Folgendes zu ermöglichen:
- Abbruch, wenn der Benutzer auf „Abbrechen“ klickt
- Vermeidung von Speicherlecks in `onDestroy()`
- Ordnungsgemäße Bereinigung während des Komponenten-Lifecycles

#### 4. Kooperativer Abbruch {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrundthread prüft dieses Flag bei jeder Iteration. Dadurch wird Folgendes ermöglicht:
- Sofortige Reaktion auf einen Abbruch
- Sauberes Beenden der Schleife
- Verhindern weiterer UI-Aktualisierungen

#### 5. Lifecycle-Verwaltung {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Wiederverwendet die Abbruchlogik
  currentTask = null;
  executor.shutdown();
}
```
Entscheidend, um Speicherlecks zu verhindern, und zwar durch:
- Abbrechen aller ausstehenden UI-Aktualisierungen
- Unterbrechen laufender Threads
- Herunterfahren des Executors

#### 6. Reaktionsfähigkeit der UI testen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick #" + count + " – die UI reagiert!", Theme.GRAY);
});
```
Zeigt, dass der UI-Thread während Hintergrundvorgängen reaktionsfähig bleibt.
