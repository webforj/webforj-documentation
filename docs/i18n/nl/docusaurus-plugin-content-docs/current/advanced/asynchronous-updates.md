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

De `Environment.runLater()` API biedt een mechanisme voor veilig updaten van de UI vanuit achtergrondthreads in webforJ-applicaties. Deze experimentele functie maakt asynchrone bewerkingen mogelijk terwijl de threadveiligheid voor UI-wijzigingen behouden blijft.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaft een strict threadingmodel waarbij alle UI-bewerkingen moeten plaatsvinden op de `Environment`-thread. Deze beperking bestaat om de volgende redenen:

1. **Beperkingen van de webforJ API**: De onderliggende webforJ API is gebonden aan de thread die de sessie heeft aangemaakt.
2. **Threadaffiniteit van componenten**: UI-componenten behouden een status die niet threadveilig is.
3. **Evenementdispatching**: Alle UI-evenementen worden sequentieel op een enkele thread verwerkt.

Dit single-threaded model voorkomt racecondities en handhaaft een consistente status voor alle UI-componenten, maar creëert uitdagingen bij integratie met asynchrone, langdurige computertaken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het inplannen van UI-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot het resultaat of eventuele opgetreden uitzonderingen.

## Erfelijkheid van threadcontext {#thread-context-inheritance}

Automatische contextovererving is een kritieke functie van `Environment.runLater()`. Wanneer een thread die draait in een `Environment`-thread kindthreads aanmaakt, erven die kinderen automatisch de mogelijkheid om `runLater()` te gebruiken.

### Hoe erfelijkheid werkt {#how-inheritance-works}

Elke thread die wordt aangemaakt vanuit een `Environment`-thread heeft automatisch toegang tot die `Environment`. Deze erfelijkheid gebeurt automatisch, zodat je geen context hoeft door te geven of iets hoeft te configureren.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Deze thread heeft Environment-context

    // Kindthreads erven de context automatisch
    executor.submit(() -> {
      String data = fetchRemoteData();

      // Kan runLater gebruiken omdat de context is geërfd
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Threads zonder context {#threads-without-context}

Threads die buiten de `Environment`-context zijn gemaakt, kunnen `runLater()` niet gebruiken en zullen een `IllegalStateException` veroorzaken:

```java
// Statische initializer - geen Environment-context
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Gooi IllegalStateException
  }).start();
}

// Systeemtimerthreads - geen Environment-context
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Gooi IllegalStateException
  }
}, 1000);

// Externe bibliotheekthreads - geen Environment-context
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Gooi IllegalStateException
  });
```

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van welke thread het aanroept:

### Van de UI-thread {#from-the-ui-thread}

Wanneer aangeroepen vanuit de `Environment`-thread zelf, worden taken **synchronisch en onmiddellijk** uitgevoerd:

```java
button.onClick(e -> {
  System.out.println("Vooraf: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Binnen: " + Thread.currentThread().getName());
    return "voltooid";
  });

  System.out.println("Daarna: " + result.isDone());  // true
});
```

Met dit synchrone gedrag worden UI-updates van gebeurtenishandlers onmiddellijk toegepast en wordt onnodige wachttijd voorkomen.

### Van achtergrondthreads {#from-background-threads}

Wanneer aangeroepen vanuit een achtergrondthread, worden taken **in de wacht gezet voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dit draait op ForkJoinPool-thread
    System.out.println("Achtergrond: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(() -> {
      // Dit draait op Environment-thread
      System.out.println("UI-update: " + Thread.currentThread().getName());
      statusLabel.setText("Verwerking voltooid");
    });

    // result.isDone() zou hier false zijn
    // De taak is in de wacht gezet en zal asynchroon worden uitgevoerd
  });
}
```

webforJ verwerkt taken die zijn ingediend vanuit achtergrondthreads in **strikte FIFO-volgorde**, waardoor de volgorde van bewerkingen behouden blijft, zelfs wanneer ze gelijktijdig vanuit meerdere threads worden ingediend. Met deze volgorde garantie worden UI-updates toegepast in de exacte volgorde waarin ze zijn ingediend. Dus als thread A taak 1 indient en thread B taak 2 indient, zal taak 1 altijd vóór taak 2 op de UI-thread worden uitgevoerd. Het verwerken van taken in FIFO-volgorde voorkomt inconsistenties in de UI.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die wordt geretourneerd door `Environment.runLater()` ondersteunt annulering, zodat je kunt voorkomen dat in de wacht gezette taken worden uitgevoerd. Door lopende taken te annuleren, kun je geheugenlekken vermijden en voorkomen dat langdurige bewerkingen de UI bijwerken nadat ze niet langer nodig zijn.

### Basisannulering {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Annuleer als nog niet uitgevoerd
if (!result.isDone()) {
  result.cancel();
}
```

### Beheren van meerdere updates {#managing-multiple-updates}

Bij het uitvoeren van langdurige bewerkingen met frequente UI-updates, houd alle lopende resultaten bij:

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

        // Houd bij voor mogelijke annulering
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Annuleer alle lopende UI-updates
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Beheer van de levenscyclus van componenten {#component-lifecycle-management}

Wanneer componenten worden vernietigd (bijv. tijdens navigatie), annuleer dan alle lopende updates om geheugenlekken te voorkomen:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuleer alle lopende updates om geheugenlekken te voorkomen
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

1. **Contextvereiste**: Threads moeten een `Environment`-context hebben geërfd. Externe bibliotheekthreads, systeemtimers en statische initialisatoren kunnen deze API niet gebruiken.

2. **Voorkomen van geheugenlekken**: Houd altijd `PendingResult`-objecten bij en annuleer ze in de methoden van de levenscyclus van componenten. In de wacht geplaatste lambdas vangen verwijzingen naar UI-componenten, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht het belang. Er is geen prioriteitssysteem.

4. **Beperkingen van annulering**: Annulering voorkomt alleen de uitvoering van in de wacht gezette taken. Taken die al worden uitgevoerd, worden normaal voltooid.

## Compleet casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Het onderstaande is een complete, productieklare implementatie die alle best practices voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een enkele thread-executor om uitputting van middelen te voorkomen
  // Voor productie, overweeg een gedeeld applicatie-brede threadpool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Houd de huidige taak en in de wacht gezette UI-updates bij
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-componenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demonstratie van Achtegrond UI Updates");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demo toont hoe Environment.runLater() veilige UI-updates vanuit achtergrondthreads mogelijk maakt. " +
          "Klik op 'Start Lange Taak' om een 10 seconden durende achtergrondberekening uit te voeren die de voortgang van de UI bijwerkt. " +
          "De knop 'Test UI' bewijst dat de UI responsief blijft tijdens de achtergrondbewerking.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Start Lange Taak");
  private Button cancelButton = new Button("Annuleer Taak");
  private Button testButton = new Button("Test UI - Klik Op Mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, wat de juiste opruiming van zowel de " +
          "achtergrondthread als de in de wacht gezette UI-updates aantoont.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configureer velden
    statusField.setReadOnly(true);
    statusField.setValue("Klaar om te starten");
    statusField.setLabel("Status");

    // Configureer voortgangsbalk
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Voortgang: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Resultaat");

    // Configureer knoppen
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
    });

    // Voeg componenten toe
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuleer elke lopende taak en in de wacht gezette UI-updates
    cancelTask();

    // Wis taakreferentie
    currentTask = null;

    // Stop de instance executor op een vriendelijke manier
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset geannuleerd vlag en wis vorige in de wacht gezette updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) onderbreekt de thread, waardoor Thread.sleep() een
    // InterruptedException gooit
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuleer lange taak met 100 stappen
      for (int i = 0; i <= 100; i++) {
        // Controleer of geannuleerd
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Taak geannuleerd!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Taak was geannuleerd", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Totaal 10 seconden
        } catch (InterruptedException e) {
          // Thread is onderbroken - verlaat onmiddellijk
          Thread.currentThread().interrupt(); // Herstel de onderbroken status
          return;
        }

        // Voer een berekening uit (deterministisch voor demo)
        // Produceert waarden tussen 0 en 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update voortgang vanuit achtergrondthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verwerking... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Laatste update met resultaat (dit stukje code wordt alleen bereikt als de taak zonder
      // annulering is voltooid)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Taak voltooid!");
          resultField.setValue("Resultaat: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Achtergrondtaak voltooid!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Stel de geannuleerd vlag in
      isCancelled = true;

      // Annuleer de hoofdtaken (onderbreekt de thread)
      currentTask.cancel(true);

      // Annuleer alle in de wacht gezette UI-updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Taak aan het annuleren...");
        cancelButton.setEnabled(false);

        showToast("Annulering aangevraagd", Theme.GRAY);
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

### Analyse van de casestudy {#case-study-analysis}

Deze implementatie demonstreert verschillende kritieke patronen:

#### 1. Beheer van threadpool {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Gebruikt een **enkele thread-executor** om uitputting van middelen te voorkomen
- Creëert **daemondraden** die de JVM-shutdown niet tegenhouden

#### 2. Bijhouden van lopende updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden om te kunnen:
- Annuleren wanneer de gebruiker op annuleren klikt
- Voorkomen van geheugenlekken in `onDestroy()`
- Juiste opruiming tijdens de levenscyclus van componenten

#### 3. Coöperatieve annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, wat mogelijk maakt:
- Directe reactie op annulering
- Schone exit uit de lus
- Voorkomen van verdere UI-updates

#### 4. Beheer van de levenscyclus {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Hergebruikt annuleringlogica
  currentTask = null;
  executor.shutdown();
}
```
Kritiek voor het voorkomen van geheugenlekken door:
- Annuleren van alle in de wacht gezette UI-updates
- Onderbreken van actieve threads
- Stoppen van de executor

#### 5. Testen van UI-responsiviteit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Demonstreert dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
