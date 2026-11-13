---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 96865999eae550e8c20172458695ee01
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

De API `Environment.runLater()` biedt een mechanisme om de UI veilig bij te werken vanuit achtergrondthreads in webforJ-applicaties. Deze previewfunctie maakt asynchrone bewerkingen mogelijk en zorgt tegelijk voor threadveiligheid bij UI-aanpassingen.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Het threadmodel begrijpen {#understanding-the-thread-model}

webforJ hanteert een strikt threadmodel waarbij alle UI-bewerkingen moeten plaatsvinden op de `Environment`-thread. Deze beperking bestaat omdat:

1. **Beperkingen van de webforJ-API**: De onderliggende webforJ-API is gebonden aan de thread waarop de sessie is aangemaakt
2. **Threadaffiniteit van componenten**: UI-componenten behouden een status die niet threadveilig is
3. **Gebeurtenisafhandeling**: Alle UI-gebeurtenissen worden sequentieel verwerkt op één thread

Dit single-threadmodel voorkomt racecondities en zorgt voor een consistente status van alle UI-componenten, maar maakt de integratie met asynchrone, langlopende rekentaken lastig.

Elke gebruikerssessie heeft een eigen `Environment` en een eigen `Environment`-thread. Een achtergrondthread hoort bij geen enkele sessie, dus moet deze verteld worden welke `Environment` moet worden bijgewerkt.

## De `RunLater`-API {#runlater-api}

De API `Environment.runLater()` biedt twee methoden om UI-updates in te plannen. Beide nemen de doel-`Environment` als eerste argument:

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de taakvoltooiing bijhoudt en toegang biedt tot het resultaat of eventuele opgetreden uitzonderingen.

:::warning[Overloads zonder doel]
De overloads `runLater(Runnable)` en `runLater(Supplier)` leiden het doel af van de thread waarop de aanroepende thread is aangemaakt. In een threadpool die door meerdere gebruikers wordt gedeeld, wordt elke taak uitgevoerd in de `Environment` van de gebruiker wiens aanroep die poolthread heeft aangemaakt. Deze overloads zijn sinds 26.10 verouderd verklaard en worden in de volgende major release verwijderd. Geef altijd het doel mee.
:::

## Het doel meegeven {#passing-the-target}

`Environment.getCurrent()` retourneert de `Environment` van de huidige thread. Op de `Environment`-thread, waarop constructors, event handlers en lifecycle-methoden worden uitgevoerd, retourneert deze de `Environment` van de sessie. Op een achtergrondthread retourneert deze `null`.

Lees het doel waar het beschikbaar is, voordat het achtergrondwerk begint, en geef het mee aan het werk:

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // The constructor runs on the Environment thread
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

Omdat het doel als argument wordt meegegeven, werkt de aanroep vanuit elke thread: een thread die door de view is aangemaakt, een Spring-`@Async`-thread, de algemene `ForkJoinPool`, een timerthread of een callbackthread van een HTTP-client.

### Het doel op de verkeerde thread uitlezen {#reading-the-target-on-the-wrong-thread}

De enige fout die je moet vermijden, is `Environment.getCurrent()` uitlezen binnen het achtergrondwerk. Daar retourneert deze `null`, waardoor `runLater()` een `NullPointerException` veroorzaakt:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Wrong: getCurrent() returns null on a background thread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Hetzelfde geldt voor statische initializers, threads van `java.util.Timer` en callbacks van externe bibliotheken. Geen daarvan heeft een `Environment`, dus moet het doel afkomstig zijn uit een veld, een parameter of een lokale variabele die op de `Environment`-thread is gevuld.

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van de thread die de methode aanroept:

### Vanaf de UI-thread {#from-the-ui-thread}

Wanneer de methode wordt aangeroepen vanaf de eigen `Environment`-thread van het doel, worden taken **synchroon en onmiddellijk** uitgevoerd:

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Before: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Inside: " + Thread.currentThread().getName());
    return "completed";
  });

  System.out.println("After: " + result.isDone());  // true
});
```

Dankzij dit synchrone gedrag worden UI-updates vanuit event handlers onmiddellijk toegepast en ontstaat er geen onnodige overhead door wachtrijen.

### Vanaf achtergrondthreads {#from-background-threads}

Wanneer de methode wordt aangeroepen vanaf een andere thread, worden taken **in de wachtrij gezet voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // This runs on a ForkJoinPool thread
    System.out.println("Background: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // This runs on the Environment thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Processing complete");
    });

    // result.isDone() would be false here
    // The task is queued and will execute asynchronously
  });
}
```

webforJ verwerkt taken die vanuit achtergrondthreads worden ingediend in **strikte FIFO-volgorde**, waarbij de volgorde van bewerkingen behouden blijft, ook wanneer meerdere threads tegelijk taken indienen. Dankzij deze volgorde worden UI-updates precies toegepast in de volgorde waarin ze zijn ingediend. Als thread A dus taak 1 indient en thread B daarna taak 2, wordt taak 1 altijd vóór taak 2 uitgevoerd op de UI-thread. FIFO-verwerking voorkomt inconsistenties in de UI.

## Taken annuleren {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die `Environment.runLater()` retourneert, ondersteunt annulering. Zo kun je voorkomen dat taken in de wachtrij worden uitgevoerd. Door openstaande taken te annuleren, kun je geheugenlekken voorkomen en ervoor zorgen dat langlopende bewerkingen de UI niet bijwerken wanneer dat niet langer nodig is.

### Eenvoudig annuleren {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Cancel if not yet executed
if (!result.isDone()) {
  result.cancel();
}
```

### Meerdere updates beheren {#managing-multiple-updates}

Houd bij langlopende bewerkingen met frequente UI-updates alle openstaande resultaten bij:

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

        // Track for potential cancellation
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Cancel all pending UI updates
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### De lifecycle van componenten beheren {#component-lifecycle-management}

Annuleer bij het vernietigen van componenten (bijvoorbeeld tijdens navigatie) alle openstaande updates om geheugenlekken te voorkomen:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel all pending updates to prevent memory leaks
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Ontwerpoverwegingen {#design-considerations}

1. **Vereist doel**: Het doel-`Environment` wordt met `Environment.getCurrent()` uitgelezen op de `Environment`-thread en meegegeven aan het achtergrondwerk. Op een achtergrondthread uitlezen retourneert `null`.

2. **Geheugenlekken voorkomen**: Houd `PendingResult`-objecten altijd bij en annuleer ze in lifecycle-methoden van componenten. Lambdas in de wachtrij houden verwijzingen naar UI-componenten vast, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht hun belang. Er is geen prioriteitssysteem.

4. **Beperkingen van annulering**: Annulering voorkomt alleen dat taken in de wachtrij worden uitgevoerd. Taken die al worden uitgevoerd, worden normaal voltooid.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Hieronder staat een complete, productieklare implementatie die alle best practices voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Use a single thread executor to prevent resource exhaustion
  // For production, consider using a shared application-wide thread pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Track the current task and pending UI updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI components
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Background UI Updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "This demo shows how Environment.runLater() enables safe UI updates from background threads. " +
          "Click 'Start Long Task' to run a 10-second background computation that updates the UI progress. " +
          "The 'Test UI' button proves the UI remains responsive during the background operation.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Result");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Cancel Task");
  private Button testButton = new Button("Test UI - Click Me!");
  private Paragraph footerPara = new Paragraph(
      "Note: The task can be cancelled at any time, demonstrating proper cleanup of both the " +
          "background thread and queued UI updates.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configure fields
    statusField.setReadOnly(true);
    statusField.setValue("Ready to start");
    statusField.setLabel("Status");

    // Configure progress bar
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progress: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Result");

    // Configure buttons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
    });

    // Add components
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel any running task and pending UI updates
    cancelTask();

    // Clear task reference
    currentTask = null;

    // Shutdown the instance executor gracefully
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starting background task...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset cancelled flag and clear previous pending updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Read the target Environment on the UI thread, before the background task starts
    Environment env = Environment.getCurrent();

    // Start background task with explicit executor
    // Note: cancel(true) will interrupt the thread, causing Thread.sleep() to throw
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulate long task with 100 steps
      for (int i = 0; i <= 100; i++) {
        // Check if cancelled
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
            statusField.setValue("Task cancelled!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Task was cancelled", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 10 seconds total
        } catch (InterruptedException e) {
          // Thread was interrupted - exit immediately
          Thread.currentThread().interrupt(); // Restore interrupted status
          return;
        }

        // Do some calculation (deterministic for demo)
        // Produces values between 0 and 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update progress from background thread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Processing... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Final update with result (this code is only reached if the task completed without
      // cancellation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
          statusField.setValue("Task completed!");
          resultField.setValue("Result: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Background task finished!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Set the cancelled flag
      isCancelled = true;

      // Cancel the main task (interrupts the thread)
      currentTask.cancel(true);

      // Cancel all pending UI updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Cancelling task...");
        cancelButton.setEnabled(false);

        showToast("Cancellation requested", Theme.GRAY);
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

### Analyse van de casestudy {#case-study-analysis}

Deze implementatie demonstreert verschillende belangrijke patronen:

#### 1. Threadpoolbeheer {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Gebruikt een **executor met één thread** om uitputting van resources te voorkomen
- Maakt **daemonthreads** aan die het afsluiten van de JVM niet verhinderen

#### 2. Het doel vastleggen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` wordt uitgevoerd op de `Environment`-thread, dus wordt het doel daar uitgelezen en geeft de achtergrondtaak het mee aan elke aanroep van `Environment.runLater()`. Dezelfde code werkt ongewijzigd met een gedeelde threadpool voor de hele applicatie.

#### 3. Openstaande updates bijhouden {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden om het volgende mogelijk te maken:
- Annulering wanneer de gebruiker op annuleren klikt
- Het voorkomen van geheugenlekken in `onDestroy()`
- Correcte opruiming tijdens de lifecycle van de component

#### 4. Coöperatieve annulering {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie. Hierdoor is het volgende mogelijk:
- Onmiddellijk reageren op annulering
- De lus netjes verlaten
- Verdere UI-updates voorkomen

#### 5. Lifecyclebeheer {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Reuses cancellation logic
  currentTask = null;
  executor.shutdown();
}
```
Essentieel om geheugenlekken te voorkomen door:
- Alle openstaande UI-updates te annuleren
- Actieve threads te onderbreken
- De executor af te sluiten

#### 6. De responsiviteit van de UI testen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
Demonstreert dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
