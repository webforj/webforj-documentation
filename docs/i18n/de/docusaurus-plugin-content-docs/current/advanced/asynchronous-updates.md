---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone Aktualisierungen
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 8dc68aced15dfd90481cd9ea98e4b2f2
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

Die API `Environment.runLater()` bietet einen Mechanismus, um die UI in webforJ-Anwendungen sicher von Hintergrundthreads aus zu aktualisieren. Dieses experimentelle Feature ermöglicht asynchrone Vorgänge und gewährleistet gleichzeitig die Threadsicherheit bei UI-Änderungen.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Das Thread-Modell verstehen {#understanding-the-thread-model}

webforJ erzwingt ein striktes Thread-Modell, bei dem alle UI-Vorgänge im `Environment`-Thread stattfinden müssen. Diese Einschränkung besteht aus folgenden Gründen:

1. **Einschränkungen der webforJ-API**: Die zugrunde liegende webforJ-API ist an den Thread gebunden, der die Sitzung erstellt hat.
2. **Thread-Affinität von Komponenten**: UI-Komponenten verwalten einen Zustand, der nicht threadsicher ist.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzelnen Thread verarbeitet.

Dieses Single-Thread-Modell verhindert Race Conditions und gewährleistet einen konsistenten Zustand für alle UI-Komponenten. Bei der Integration mit asynchronen, lang laufenden Berechnungsaufgaben stellt es jedoch eine Herausforderung dar.

Jede Benutzersitzung hat ihr eigenes `Environment` und ihren eigenen `Environment`-Thread. Ein Hintergrundthread gehört zu keiner Sitzung. Daher muss ihm mitgeteilt werden, welches `Environment` aktualisiert werden soll.

## Die `RunLater`-API {#runlater-api}

Die API `Environment.runLater()` bietet zwei Methoden zum Planen von UI-Aktualisierungen. Beide erwarten das Ziel-`Environment` als erstes Argument:

```java title="Environment.java"
// Eine Aufgabe ohne Rückgabewert planen
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Eine Aufgabe planen, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder auf eventuell aufgetretene Ausnahmen bietet.

:::warning[Überladungen ohne Ziel]
Die Überladungen `runLater(Runnable)` und `runLater(Supplier)` ermitteln das Ziel anhand des Threads, der den aufrufenden Thread erstellt hat. In einem Thread-Pool, der von mehreren Benutzern gemeinsam verwendet wird, wird jede Aufgabe im `Environment` des Benutzers ausgeführt, dessen Aufruf diesen Pool-Thread erstellt hat. Diese Überladungen sind seit Version 26.10 veraltet und sollen in der nächsten Hauptversion entfernt werden. Übergeben Sie immer das Ziel.
:::

## Das Ziel übergeben {#passing-the-target}

`Environment.getCurrent()` gibt das `Environment` des aktuellen Threads zurück. Im `Environment`-Thread, in dem Konstruktoren, Ereignisbehandler und Lifecycle-Methoden ausgeführt werden, gibt die Methode das `Environment` der Sitzung zurück. In einem Hintergrundthread gibt sie `null` zurück.

Lesen Sie das Ziel an der Stelle aus, an der es verfügbar ist, also bevor die Hintergrundarbeit beginnt, und übergeben Sie es an die Arbeit:

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

Vermeiden Sie unbedingt, `Environment.getCurrent()` innerhalb der Hintergrundarbeit auszulesen. Dort gibt die Methode `null` zurück, und `runLater()` wirft eine `NullPointerException`:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Falsch: getCurrent() gibt in einem Hintergrundthread null zurück
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Dasselbe gilt für statische Initialisierer, `java.util.Timer`-Threads und Callbacks externer Bibliotheken. Keiner davon hat ein `Environment`. Das Ziel muss daher aus einem Feld, einem Parameter oder einer lokalen Variable stammen, die im `Environment`-Thread befüllt wurde.

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

  System.out.println("Danach: " + result.isDone());  // true
});
```

Dank dieses synchronen Verhaltens werden UI-Aktualisierungen aus Ereignisbehandlern sofort angewendet, ohne unnötigen Aufwand durch das Einreihen in eine Warteschlange.

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

webforJ verarbeitet Aufgaben, die von Hintergrundthreads eingereicht wurden, in **strikter FIFO-Reihenfolge**. So bleibt die Reihenfolge der Vorgänge erhalten, selbst wenn mehrere Threads gleichzeitig Aufgaben einreichen. Dank dieser garantierten Reihenfolge werden UI-Aktualisierungen genau in der Reihenfolge angewendet, in der sie eingereicht wurden. Reicht Thread A also Aufgabe 1 und anschließend Thread B Aufgabe 2 ein, wird Aufgabe 1 im UI-Thread immer vor Aufgabe 2 ausgeführt. Die FIFO-Verarbeitung verhindert Inkonsistenzen in der UI.

## Aufgaben abbrechen {#task-cancellation}

Das von `Environment.runLater()` zurückgegebene <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> unterstützt das Abbrechen von Aufgaben. Dadurch können Sie verhindern, dass Aufgaben aus der Warteschlange ausgeführt werden. Wenn Sie ausstehende Aufgaben abbrechen, vermeiden Sie Speicherlecks und verhindern, dass lang laufende Vorgänge die UI aktualisieren, obwohl sie nicht mehr benötigt werden.

### Einfaches Abbrechen {#basic-cancellation}

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

Wenn Sie lang laufende Vorgänge mit häufigen UI-Aktualisierungen durchführen, verfolgen Sie alle ausstehenden Ergebnisse:

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

        // Für ein mögliches Abbrechen erfassen
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

### Verwaltung des Komponenten-Lifecycles {#component-lifecycle-management}

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

1. **Ziel erforderlich**: Das Ziel-`Environment` wird mit `Environment.getCurrent()` im `Environment`-Thread ausgelesen und an die Hintergrundarbeit übergeben. Wird es in einem Hintergrundthread ausgelesen, gibt die Methode `null` zurück.

2. **Speicherlecks verhindern**: Verfolgen Sie `PendingResult`-Objekte immer und brechen Sie sie in den Lifecycle-Methoden von Komponenten ab. Eingereihte Lambdas erfassen Referenzen auf UI-Komponenten und verhindern deren Garbage Collection, wenn sie nicht abgebrochen werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden unabhängig von ihrer Wichtigkeit in strikter FIFO-Reihenfolge ausgeführt. Es gibt kein Prioritätensystem.

4. **Einschränkungen beim Abbrechen**: Das Abbrechen verhindert nur die Ausführung von Aufgaben, die sich noch in der Warteschlange befinden. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende vollständige, produktionsreife Implementierung veranschaulicht bewährte Vorgehensweisen für asynchrone UI-Aktualisierungen:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Einen Executor mit einem einzelnen Thread verwenden, um eine Ressourcenerschöpfung zu verhindern
  // Für den Produktiveinsatz empfiehlt sich ein anwendungsweiter, gemeinsam genutzter Thread-Pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Die aktuelle Aufgabe und ausstehende UI-Aktualisierungen nachverfolgen
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-Komponenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demo: UI-Aktualisierungen im Hintergrund");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Aktualisierungen aus Hintergrundthreads ermöglicht. " +
          "Klicken Sie auf „Lang laufende Aufgabe starten“, um eine 10 Sekunden dauernde Hintergrundberechnung zu starten, die den UI-Fortschritt aktualisiert. " +
          "Die Schaltfläche „UI testen“ zeigt, dass die UI während des Hintergrundvorgangs reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Lang laufende Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("UI testen – hier klicken!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden. Das veranschaulicht die ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrundthreads als auch der eingereihten UI-Aktualisierungen.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Felder konfigurieren
    statusField.setReadOnly(true);
    statusField.setValue("Bereit");
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
      showToast("Klick Nr. " + count + " – die UI reagiert!", Theme.GRAY);
    });

    // Komponenten hinzufügen
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Alle laufenden Aufgaben und ausstehenden UI-Aktualisierungen abbrechen
    cancelTask();

    // Aufgabenreferenz löschen
    currentTask = null;

    // Den Executor der Instanz ordnungsgemäß herunterfahren
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Hintergrundaufgabe wird gestartet …");
    progressBar.setValue(0);
    resultField.setValue("");

    // Abbruch-Flag zurücksetzen und vorherige ausstehende Aktualisierungen löschen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Das Ziel-Environment im UI-Thread auslesen, bevor die Hintergrundaufgabe beginnt
    Environment env = Environment.getCurrent();

    // Hintergrundaufgabe mit einem expliziten Executor starten
    // Hinweis: cancel(true) unterbricht den Thread, wodurch Thread.sleep() eine
    // InterruptedException auslöst
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Eine lang laufende Aufgabe mit 100 Schritten simulieren
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
          // Der Thread wurde unterbrochen – sofort beenden
          Thread.currentThread().interrupt(); // Unterbrechungsstatus wiederherstellen
          return;
        }

        // Eine Berechnung durchführen (für die Demo deterministisch)
        // Erzeugt Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Fortschritt aus dem Hintergrundthread aktualisieren
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung … " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Abschließende Aktualisierung mit dem Ergebnis (dieser Code wird nur erreicht, wenn die
      // Aufgabe ohne Abbruch abgeschlossen wurde)
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
        statusField.setValue("Aufgabe wird abgebrochen …");
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

Diese Implementierung veranschaulicht mehrere wichtige Vorgehensweisen:

#### 1. Thread-Pool-Verwaltung {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Verwendet einen **Executor mit einem einzelnen Thread**, um eine Ressourcenerschöpfung zu verhindern
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Das Ziel bestimmen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` wird im `Environment`-Thread ausgeführt. Daher wird das Ziel dort ausgelesen und von der Hintergrundaufgabe an jeden Aufruf von `Environment.runLater()` übergeben. Derselbe Code funktioniert unverändert mit einem gemeinsam genutzten, anwendungsweiten Thread-Pool.

#### 3. Ausstehende Aktualisierungen nachverfolgen {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird erfasst, um Folgendes zu ermöglichen:
- Abbrechen, wenn der Benutzer auf „Abbrechen“ klickt
- Speicherlecks in `onDestroy()` verhindern
- Ordnungsgemäße Bereinigung während des Komponenten-Lifecycles

#### 4. Kooperatives Abbrechen {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrundthread prüft dieses Flag bei jeder Iteration. Dadurch werden Folgendes ermöglicht:
- Sofortige Reaktion auf einen Abbruch
- Sauberes Beenden der Schleife
- Verhindern weiterer UI-Aktualisierungen

#### 5. Lifecycle-Verwaltung {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Verwendet die Abbruchlogik erneut
  currentTask = null;
  executor.shutdown();
}
```
Wichtig, um Speicherlecks zu verhindern durch:
- Abbrechen aller ausstehenden UI-Aktualisierungen
- Unterbrechen laufender Threads
- Herunterfahren des Executors

#### 6. Reaktionsfähigkeit der UI testen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klick Nr. " + count + " – die UI reagiert!", Theme.GRAY);
});
```
Zeigt, dass der UI-Thread während Hintergrundvorgängen reaktionsfähig bleibt.
