---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 9ea7ae8b53ce19e2fee19e72929c732e
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

Die `Environment.runLater()` API bietet einen Mechanismus zum sicheren Aktualisieren der Benutzeroberfläche von Hintergrund-Threads in webforJ-Anwendungen. Diese experimentelle Funktion ermöglicht asynchrone Vorgänge und sorgt gleichzeitig für Thread-Sicherheit bei UI-Änderungen.

:::warning Experimentelle API
Diese API ist seit 25.02 als experimentell gekennzeichnet und kann sich in zukünftigen Versionen ändern. Die API-Signatur, das Verhalten und die Leistungsmerkmale können modifiziert werden.
:::

## Verständnis des Thread-Modells {#understanding-the-thread-model}

webforJ zwingt ein strenges Thread-Modell durch, bei dem alle UI-Operationen im `Environment`-Thread erfolgen müssen. Diese Einschränkung besteht, weil:

1. **webforJ API-Beschränkungen**: Die zugrunde liegende webforJ-API ist an den Thread gebunden, der die Sitzung erstellt hat.
2. **Thread-Affinität von Komponenten**: UI-Komponenten halten Zustände, die nicht thread-sicher sind.
3. **Ereignisverarbeitung**: Alle UI-Ereignisse werden nacheinander in einem einzelnen Thread verarbeitet.

Dieses Einzelthread-Modell verhindert Rennbedingungen und erhält einen konsistenten Zustand für alle UI-Komponenten, schafft jedoch Herausforderungen bei der Integration mit asynchronen, lang laufenden Berechnungsaufgaben.

## `RunLater` API {#runlater-api}

Die `Environment.runLater()` API bietet zwei Methoden zur Planung von UI-Aktualisierungen:

```java title="Environment.java"
// Eine Aufgabe ohne Rückgabewert planen
public static PendingResult<Void> runLater(Runnable task)

// Eine Aufgabe planen, die einen Wert zurückgibt
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide Methoden geben ein <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> zurück, das den Abschluss der Aufgabe verfolgt und Zugang zu dem Ergebnis oder aufgetretenen Ausnahmen bietet.

## Vererbung des Thread-Kontexts {#thread-context-inheritance}

Die automatische Kontextvererbung ist ein entscheidendes Merkmal von `Environment.runLater()`. Wenn ein Thread, der in einem `Environment` läuft, Kind-Threads erstellt, erben diese automatisch die Fähigkeit, `runLater()` zu verwenden.

### Wie die Vererbung funktioniert {#how-inheritance-works}

Jeder Thread, der aus einem `Environment`-Thread erstellt wird, hat automatisch Zugang zu diesem `Environment`. Diese Vererbung geschieht automatisch, sodass Sie keinen Kontext übergeben oder etwas konfigurieren müssen.

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

// Externe Bibliotheks-Threads - kein Environment-Kontext
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
    
    System.out.println("Nachher: " + result.isDone()); // true
});
```

Mit diesem synchronen Verhalten werden UI-Aktualisierungen von Ereignisbehandlern sofort angewendet und verursachen keinen unnötigen Warteschlangen-Overhead.

### Von Hintergrund-Threads {#from-background-threads}

Wenn es von einem Hintergrund-Thread aufgerufen wird, werden Aufgaben **für asynchrone Ausführung in die Warteschlange gestellt**:

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
        // Die Aufgabe ist in die Warteschlange eingereiht und wird asynchron ausgeführt
    });
}
```

webforJ verarbeitet Aufgaben, die von Hintergrund-Threads eingereicht werden, in **streng FIFO-Reihenfolge**, wodurch die Reihenfolge der Vorgänge beibehalten wird, selbst wenn sie von mehreren Threads gleichzeitig eingereicht werden. Mit dieser Reihenfolge-Garantie werden UI-Aktualisierungen in der genauen Reihenfolge angewendet, in der sie eingegeben wurden. Wenn also Thread A Aufgabe 1 einreicht und dann Thread B Aufgabe 2 einreicht, wird Aufgabe 1 immer vor Aufgabe 2 im UI-Thread ausgeführt. Das Verarbeiten von Aufgaben in FIFO-Reihenfolge verhindert Inkonsistenzen in der UI.

## Aufgabenstornierung {#task-cancellation}

Das <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, das von `Environment.runLater()` zurückgegeben wird, unterstützt die Stornierung und ermöglicht es Ihnen, das Ausführen von Warteschlangenaufgaben zu verhindern. Durch die Stornierung ausstehender Aufgaben können Sie Speicherlecks vermeiden und verhindern, dass lang laufende Vorgänge die UI aktualisieren, nachdem sie nicht mehr benötigt werden.

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

Bei der Durchführung lang laufender Operationen mit häufigen UI-Aktualisierungen sollten Sie alle ausstehenden Ergebnisse verfolgen:

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
        
        // Stornieren aller ausstehenden UI-Aktualisierungen
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

Wenn Komponenten zerstört werden (z. B. während der Navigation), sollten Sie alle ausstehenden Aktualisierungen stornieren, um Speicherlecks zu vermeiden:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Stornieren aller ausstehenden Aktualisierungen zur Vermeidung von Speicherlecks
        for (PendingResult<?> pending : pendingUpdates) {
            if (!pending.isDone()) {
                pending.cancel();
            }
        }
        pendingUpdates.clear();
    }
}
```

## Gestaltungsüberlegungen {#design-considerations}

1. **Kontextanforderung**: Threads müssen einen `Environment`-Kontext geerbt haben. Threads von externen Bibliotheken, Systemtimern und statischen Initialisierern können diese API nicht verwenden.

2. **Vermeidung von Speicherlecks**: Verfolgen und stornieren Sie immer `PendingResult`-Objekte in den Methoden des Komponentenlebenszyklus. In der Warteschlange stehende Lambdas erfassen Verweise auf UI-Komponenten, was die Garbage Collection verhindert, wenn sie nicht storniert werden.

3. **FIFO-Ausführung**: Alle Aufgaben werden in strenger FIFO-Reihenfolge ausgeführt, unabhängig von ihrer Wichtigkeit. Es gibt kein Prioritätssystem.

4. **Stornierungsbeschränkungen**: Die Stornierung verhindert nur die Ausführung von Warteschlangenaufgaben. Bereits ausführende Aufgaben werden normal beendet.

## Vollständige Fallstudie: `LongTaskView` {#complete-case-study-longtaskview}

Die folgende Implementierung ist eine vollständige, produktionsbereite Implementierung, die alle bewährten Methoden für asynchrone UI-Aktualisierungen demonstriert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Verwenden Sie einen einzelnen Thread-Executor, um Ressourcenerschöpfung zu vermeiden
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
  private H2 titleLabel = new H2("Demo für Hintergrund-UI-Aktualisierungen");
  private Paragraph descriptionPara = new Paragraph(
      "Diese Demo zeigt, wie Environment.runLater() sichere UI-Aktualisierungen von Hintergrund-Threads ermöglicht. " +
          "Klicken Sie auf 'Langsame Aufgabe starten', um eine 10-sekündige Hintergrundberechnung auszuführen, die den Fortschritt aktualisiert. " +
          "Der 'Test UI'-Knopf beweist, dass die UI während der Hintergrundoperation reaktionsfähig bleibt.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Ergebnis");
  private Button startButton = new Button("Langsame Aufgabe starten");
  private Button cancelButton = new Button("Aufgabe abbrechen");
  private Button testButton = new Button("Test UI - Klicken Sie mich!");
  private Paragraph footerPara = new Paragraph(
      "Hinweis: Die Aufgabe kann jederzeit abgebrochen werden, was eine ordnungsgemäße Bereinigung des " +
          "Hintergrund-Threads und ausstehender UI-Aktualisierungen demonstriert.");
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

    // Knöpfe konfigurieren
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

    // Brechen Sie alle laufenden Aufgaben und ausstehende UI-Aktualisierungen ab
    cancelTask();

    // Aufgabe-Referenz zurücksetzen
    currentTask = null;

    // Den Executor sanft herunterfahren
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Hintergrundaufgabe wird gestartet...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Abgebrochenen Flag zurücksetzen und vorherige ausstehende Aktualisierungen löschen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Langsame Hintergrundaufgabe mit explizitem Executor starten
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuliert lange Aufgaben mit 100 Schritten
      for (int i = 0; i <= 100; i++) {
        // Überprüfen, ob abgebrochen
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
          Thread.currentThread().interrupt(); // Unterbrochener Status wiederherstellen
          return;
        }

        // Einige Berechnungen durchführen (deterministisch für die Demo)
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

      // Letzte Aktualisierung mit Ergebnis (dieser Code wird nur erreicht, wenn die Aufgabe ohne
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
      // Abgebrochenes Flag setzen
      isCancelled = true;

      // Den Hauptauftrag abbrechen (unterbricht den Thread)
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
}`}
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
- Verwendet einen **einzelnen Thread-Executor**, um Ressourcenerschöpfung zu vermeiden
- erstellt **Dämonen-Threads**, die den JVM-Shutdown nicht verhindern

#### 2. Verfolgung ausstehender Aktualisierungen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jeder `Environment.runLater()`-Aufruf wird verfolgt, um:
- Stornierung zu ermöglichen, wenn der Benutzer auf Abbrechen klickt
- Speicherlecks in `onDestroy()` zu vermeiden
- Eine ordnungsgemäße Bereinigung während des Komponentenlebenszyklus zu gewährleisten

#### 3. Kooperative Stornierung {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Der Hintergrund-Thread überprüft dieses Flag bei jeder Iteration, was ermöglicht:
- Sofortige Reaktion auf Abbrüche
- Saubere Beendigung der Schleife
- Verhinderung weiterer UI-Aktualisierungen

#### 4. Verwaltung des Lebenszyklus {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // Wiederverwendet Stornierungslogik
    currentTask = null;
    executor.shutdown();
}
```
Kritisch zur Vermeidung von Speicherlecks, indem:
- Alle ausstehenden UI-Aktualisierungen storniert werden
- Laufende Threads unterbrochen werden
- Der Executor heruntergefahren wird

#### 5. Testen der UI-Reaktionsfähigkeit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klick #" + count + " - UI ist reaktionsfähig!", Theme.GRAY);
});
```
Demonstriert, dass der UI-Thread während Hintergrundoperationen reaktionsfähig bleibt.
