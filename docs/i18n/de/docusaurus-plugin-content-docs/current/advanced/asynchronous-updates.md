---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: 0db4be3f7e785c967b2e7efa442ca3ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche aus Hintergrundthreads in webforJ-Anwendungen. Diese experimentelle Funktion ermöglicht asynchrone Operationen, während die Threadsicherheit für UI-Modifikationen gewährleistet bleibt.

:::warning Experimentelle API
Diese API wird seit 25.02 als experimentell eingestuft und kann sich in zukünftigen Versionen ändern. Die API-Signatur, das Verhalten und die Leistungsmerkmale können modifiziert werden.
:::

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ erzwingt ein strenges Thread-Modell, bei dem alle UI-Operationen im `Environment`-Thread stattfinden müssen. Diese Einschränkung existiert, weil:

1. **webforJ API-Beschränkungen**: Die zugrunde liegende webforJ-API ist an den Thread gebunden, der die Sitzung erstellt hat.
2. **Thread-Affinität der Komponenten**: UI-Komponenten behalten einen Zustand, der nicht thread-sicher ist.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden sequenziell in einem einzigen Thread verarbeitet.

Dieses eindrähtige Modell verhindert Wettlaufbedingungen und sorgt für einen konsistenten Zustand aller UI-Komponenten, schafft jedoch Herausforderungen bei der Integration von asynchronen, langlaufenden Berechnungsaufgaben.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zur Planung von UI-Aktualisierungen:

```java title="Environment.java"
// Planen Sie eine Aufgabe ohne Rückgabewert
public static PendingResult<Void> runLater(Runnable task)

// Planen Sie eine Aufgabe, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugriff auf das Ergebnis oder auf aufgetretene Ausnahmen bietet.

## Vererbung des Thread-Kontexts {#thread-context-inheritance}

Die automatische Kontextvererbung ist ein wichtiges Merkmal von `Environment.runLater()`. Wenn ein Thread, der im `Environment` ausgeführt wird, untergeordnete Threads erstellt, erben diese Kinder automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der innerhalb eines `Environment`-Threads erstellt wird, hat automatisch Zugriff auf dieses `Environment`. Diese Vererbung geschieht automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Dieser Thread hat den Kontext des Environment
        
        // Kindthreads erben den Kontext automatisch
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
// Statische Initialisierung - kein Environment-Kontext
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Wirft IllegalStateException
    }).start();
}

// Systemzeituhr-Threads - kein Environment-Kontext  
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

Wenn aus dem `Environment`-Thread selbst aufgerufen, werden Aufgaben **synchron und sofort** ausgeführt:

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

Mit diesem synchronen Verhalten werden UI-Aktualisierungen von Ereignishandlern sofort angewendet und verursachen keine unnötigen Warteschlangenüberlastungen.

### Aus Hintergrund-Threads {#from-background-threads}

Wenn aus einem Hintergrundthread aufgerufen, werden Aufgaben **für asynchrone Ausführung in die Warteschlange gestellt**:

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
        // Die Aufgabe wird in die Warteschlange gestellt und wird asynchron ausgeführt
    });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrund-Threads eingereicht werden, in **strikter FIFO-Reihenfolge**, wobei die Reihenfolge der Operationen beibehalten wird, auch wenn sie gleichzeitig von mehreren Threads eingereicht werden. Mit dieser Reihenfolgegarantie werden UI-Aktualisierungen in genau der Reihenfolge angewendet, in der sie eingereicht wurden. Wenn also Thread A Aufgabe 1 einreicht und dann Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Die Verarbeitung von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt eine Stornierung, die es Ihnen ermöglicht, zu verhindern, dass wartende Aufgaben ausgeführt werden. Durch das Stornieren ausstehender Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass langlaufende Operationen die Benutzeroberfläche aktualisieren, nachdem sie nicht mehr benötigt werden.

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

Bei der Durchführung langlaufender Operationen mit häufigen UI-Aktualisierungen sollten alle ausstehenden Ergebnisse verfolgt werden:

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
                
                // Verfolgen für mögliche Stornierung
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

### Verwaltung des Komponentenlebenszyklus {#component-lifecycle-management}

Wenn Komponenten zerstört werden (z. B. während der Navigation), stornieren Sie alle ausstehenden Aktualisierungen, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Stornieren Sie alle ausstehenden Aktualisierungen, um Speicherlecks zu vermeiden
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

1. **Anforderung des Kontexts**: Threads müssen einen `Environment`-Kontext geerbt haben. Threads von externen Bibliotheken, Systemtimern und statischen Initialisierern können diese API nicht verwenden.

2. **Vermeidung von Speicherlecks**: Verfolgen und stornieren Sie immer `PendingResult`-Objekte in den Methoden des Komponentenlebenszyklus. Wartende Lambdas erfassen Verweise auf UI-Komponenten, was die Garbage Collection verhindert, wenn sie nicht storniert werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden unabhängig von der Wichtigkeit in strikter FIFO-Reihenfolge ausgeführt. Es gibt kein Prioritätssystem.

4. **Einschränkungen der Stornierung**: Die Stornierung verhindert nur die Ausführung wartender Aufgaben. Bereits ausführende Aufgaben werden normal abgeschlossen.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende Implementierung ist vollständig und produktionsbereit und demonstriert alle Best Practices für asynchrone UI-Aktualisierungen:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen einzelnen Thread-Executor, um Ressourcenauslastung zu vermeiden
  // Für die Produktion sollten Sie einen gemeinschaftlichen anwendungsweiten Thread-Pool in Betracht ziehen
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
  private H2 titleLabel = new H2("Demo für Hintergrund-U-I-Aktualisierungen");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Aktualisierungen aus Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Lange Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den UI-Fortschritt aktualisiert. " +
          "Der 'Test UI'-Button beweist, dass die UI während des Hintergrundvorgangs reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Lange Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe stornieren");
  private Button testButton = new Button("Test UI - Klicke mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit storniert werden, was eine ordnungsgemäße Bereinigung sowohl des " +
          "Hintergrundthreads als auch der wartenden UI-Aktualisierungen demonstriert.");
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

    // Stornieren Sie alle laufenden Aufgaben und ausstehenden UI-Aktualisierungen
    cancelTask();

    // Löschen Sie die Aufgabenreferenz
    currentTask = null;

    // Beenden Sie den Instanz-Executor ordnungsgemäß
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starte Hintergrundaufgabe...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Rücksetzen des stornierten Flags und Löschen vorheriger ausstehender Aktualisierungen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Starten Sie die Hintergrundaufgabe mit explizitem Executor
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuliert lange Aufgabe mit 100 Schritten
      for (int i = 0; i <= 100; i++) {
        // Überprüfen, ob storniert
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Aufgabe storniert!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Aufgabe wurde storniert", Theme.GRAY);
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

        // Führen Sie einige Berechnungen durch (deterministisch für Demo)
        result += Math.sin(i) * 0.5 + 0.5;

        // Fortschrittsaktualisierung aus Hintergrundthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verarbeitung... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Letzte Aktualisierung mit dem Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
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
      // Setzen Sie das stornierte Flag
      isCancelled = true;

      // Stornieren Sie die Hauptaufgabe (unterbricht den Thread)
      currentTask.cancel(true);

      // Stornieren Sie alle ausstehenden UI-Aktualisierungen
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Stornieren der Aufgabe...");
        cancelButton.setEnabled(false);

        showToast("Stornierung angefordert", Theme.GRAY);
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
- Verwendet einen **einzelnen Thread-Executor**, um Ressourcenauslastung zu vermeiden
- Erstellt **Daemon-Threads**, die das Herunterfahren der JVM nicht verhindern

#### 2. Verfolgung ausstehender Aktualisierungen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder `Environment.runLater()`-Aufruf wird verfolgt, um zu ermöglichen:
- Stornierung, wenn der Benutzer auf Stornieren klickt
- Vermeidung von Speicherlecks in `onDestroy()`
- Ordnungsgemäße Bereinigung während des Komponentenlebenszyklus

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrundthread überprüft dieses Flag in jeder Iteration, was ermöglicht:
- Sofortige Reaktion auf Stornierung
- Sauberes Verlassen der Schleife
- Verhinderung weiterer UI-Aktualisierungen

#### 4. Verwaltung des Lebenszyklus {#4-lifecycle-management}
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
- Stornieren aller ausstehenden UI-Aktualisierungen
- Unterbrechen von laufenden Threads
- Herunterfahren des Executors

#### 5. Test der Reaktionsfähigkeit der UI {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während der Hintergrundoperationen reaktionsfähig bleibt.
