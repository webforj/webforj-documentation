---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: a426166aa63471b0d9d84e6c4786c6db
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche von Hintergrundthreads in webforJ-Anwendungen. Diese experimentelle Funktion ermöglicht asynchrone Vorgänge, während die Thread-Sicherheit für UI-Modifikationen aufrechterhalten wird.

:::warning Experimentelle API
Diese API ist seit 25.02 als experimentell gekennzeichnet und kann sich in zukünftigen Versionen ändern. Die API-Signatur, das Verhalten und die Leistungsmerkmale können modifiziert werden.
:::

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ erzwingt ein strenges Thread-Modell, bei dem alle UI-Operationen im `Environment`-Thread durchgeführt werden müssen. Diese Einschränkung besteht, weil:

1. **Einschränkungen der webforJ API**: Die zugrunde liegende webforJ API ist an den Thread gebunden, der die Sitzung erstellt hat.
2. **Thread-Affinität von Komponenten**: UI-Komponenten halten einen Zustand, der nicht threadsicher ist.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzigen Thread verarbeitet.

Dieses eindrähtige Modell verhindert Wettlaufbedingungen und gewährleistet einen konsistenten Zustand für alle UI-Komponenten, schafft jedoch Herausforderungen bei der Integration mit asynchronen, langlaufenden Berechnungsaufgaben.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zur Planung von UI-Aktualisierungen:

```java title="Environment.java"
// Plane eine Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Plane eine Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugang zum Ergebnis oder zu etwaigen aufgetretenen Ausnahmen bietet.

## Thread-Kontextvererbung {#thread-context-inheritance}

Die automatische Kontextvererbung ist eine wichtige Funktion von `Environment.runLater()`. Wenn ein in einem `Environment` laufender Thread Kind-Threads erstellt, erben diese Kinder automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der innerhalb eines `Environment`-Threads erstellt wird, hat automatisch Zugang zu diesem `Environment`. Diese Vererbung geschieht automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Dieser Thread hat Kontext des Environment
        
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

Threads, die außerhalb des `Environment`-Kontexts erstellt wurden, können `runLater()` nicht verwenden und werfen eine `IllegalStateException`:

```java
// Statische Initialisierung - kein Environment-Kontext
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

Mit diesem synchronen Verhalten werden UI-Aktualisierungen von Ereignis-Handlern sofort angewendet und verursachen keinen unnötigen Warteschlangenaufwand.

### Von Hintergrundthreads {#from-background-threads}

Wenn es von einem Hintergrundthread aufgerufen wird, werden Aufgaben **für die asynchrone Ausführung in die Warteschlange gestellt**:

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

webforJ verarbeitet Aufgaben, die von Hintergrundthreads eingereicht werden, in **strikter FIFO-Reihenfolge**, wodurch die Reihenfolge der Vorgänge selbst bei gleichzeitiger Einreichung von mehreren Threads erhalten bleibt. Mit dieser Reihenfolgegewährleistung werden UI-Aktualisierungen in der genauen Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und dann Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Die Verarbeitung von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der Benutzeroberfläche.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Stornierung, sodass Sie vermeiden können, dass Warteschlangenaufgaben ausgeführt werden. Durch das Stornieren von ausstehenden Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass lang laufende Vorgänge die Benutzeroberfläche aktualisieren, nachdem sie nicht mehr benötigt werden.

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

Bei der Durchführung lang laufender Vorgänge mit häufigen UI-Aktualisierungen sollten alle ausstehenden Ergebnisse verfolgt werden:

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
                
                // Für potenzielle Stornierungen verfolgen
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

Wenn Komponenten zerstört werden (z.B. während der Navigation), storniere alle ausstehenden Aktualisierungen, um Speicherlecks zu verhindern:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Alle ausstehenden Aktualisierungen stornieren, um Speicherlecks zu verhindern
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

1. **Kontextanforderung**: Threads müssen einen `Environment`-Kontext geerbt haben. Threads von externen Bibliotheken, Systemtimern und statischen Initialisierern können diese API nicht verwenden.

2. **Vermeidung von Speicherlecks**: Verfolgen und stornieren Sie immer `PendingResult`-Objekte in Komponentenlebenszyklusmethoden. Warteschlangen-Lambdas fangen Referenzen auf UI-Komponenten ein, wodurch die Garbage Collection verhindert wird, wenn sie nicht storniert werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strikter FIFO-Reihenfolge ausgeführt, unabhängig von der Wichtigkeit. Es gibt kein Prioritätssystem.

4. **Einschränkungen der Stornierung**: Die Stornierung verhindert nur die Ausführung von Warteschlangenaufgaben. Bereits ausgeführte Aufgaben werden normal abgeschlossen.

## vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende Implementierung ist eine vollständige, produktionsbereite Demonstration aller Best Practices für asynchrone UI-Aktualisierungen:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwende einen einzelnen Thread-Executor, um Ressourcenermüdung zu vermeiden
  // Für die Produktion sollten Sie einen gemeinsamen, anwendungsweiten Thread-Pool in Betracht ziehen
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
  private FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demo für Hintergrund-UI-Aktualisierungen");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Aktualisierungen von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Lange Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt der UI aktualisiert. " +
          "Der 'Test UI'-Knopf beweist, dass die UI während des Hintergrundvorgangs reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Lange Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klicke mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was eine ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrundthreads als auch der Warteschlangen-UIS-Aktualisierungen demonstriert.");
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

    // Konfigurieren Sie die Knöpfe
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

    // Brechen Sie eine laufende Aufgabe und ausstehende UI-Aktualisierungen ab
    cancelTask();

    // Löschen Sie die Aufgabenreferenz
    currentTask = null;

    // Fahren Sie den Instanz-Executor ordnungsgemäß herunter
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Hintergrundaufgabe wird gestartet...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Stornierungsflag zurücksetzen und vorherige ausstehende Aktualisierungen löschen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Starten Sie die Hintergrundaufgabe mit einem expliziten Executor
    // Hinweis: cancel(true) unterbricht den Thread, was Thread.sleep() veranlasst, eine
    // InterruptedException zu werfen
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulieren Sie die lange Aufgabe mit 100 Schritten
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
          // Der Thread wurde unterbrochen - sofort beenden
          Thread.currentThread().interrupt(); // Unterbrechungsstatus wiederherstellen
          return;
        }

        // Führen Sie eine Berechnung durch (deterministisch für die Demo)
        // Produziert Werte zwischen 0 und 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Aktualisieren Sie den Fortschritt von einem Hintergrundthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Endgültige Aktualisierung mit dem Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
      // Abbruch abgeschlossen wurde)
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
      // Setzen Sie das Abbruchflag
      isCancelled = true;

      // Brechen Sie die Hauptaufgabe ab (unterbricht den Thread)
      currentTask.cancel(true);

      // Brechen Sie alle ausstehenden UI-Aktualisierungen ab
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
`}</ExpandableCode>

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
- Verwendet einen **einzelnen Thread-Executor**, um Ressourcenermüdung zu vermeiden
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Verfolgung ausstehender Aktualisierungen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder Aufruf von `Environment.runLater()` wird verfolgt, um zu ermöglichen:
- Stornierung, wenn der Benutzer auf Abbrechen klickt
- Vermeidung von Speicherlecks in `onDestroy()`
- Ordnungsgemäße Bereinigung während des Komponentenlebenszyklus

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrundthread überprüft dieses Flag bei jeder Iteration, um zu ermöglichen:
- Sofortige Reaktion auf den Abbruch
- Sauberer Ausstieg aus der Schleife
- Vermeidung weiterer UI-Aktualisierungen

#### 4. Lebenszyklusverwaltung {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // Wiederverwendung der Abbruchlogik
    currentTask = null;
    executor.shutdown();
}
```
Kritisch zur Vermeidung von Speicherlecks durch:
- Abbrechen aller ausstehenden UI-Aktualisierungen
- Unterbrechen laufender Threads
- Herunterfahren des Executors

#### 5. Test von UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während der Hintergrundoperationen reaktionsfähig bleibt.
