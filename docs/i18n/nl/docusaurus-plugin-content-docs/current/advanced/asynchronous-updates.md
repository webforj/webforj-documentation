---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: ee0d9acbd0ac4b9b04510636531eb49d
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

De `Environment.runLater()` API biedt een mechanisme voor veilig bijwerken van de gebruikersinterface vanuit achtergrondthreads in webforJ-applicaties. Deze experimentele functie maakt asynchrone operaties mogelijk terwijl de threadveiligheid voor de gebruikersinterface-wijzigingen behouden blijft.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaf een strikt threadingmodel waarbij alle gebruikersinterface-operaties moeten plaatsvinden op de `Environment`-thread. Deze restrictie bestaat omdat:

1. **webforJ API-beperkingen**: De onderliggende webforJ API is gebonden aan de thread die de sessie heeft aangemaakt
2. **Component thread-affiniteit**: Gebruikersinterfacecomponenten behouden een staat die niet thread-veilig is
3. **Evenementdispatching**: Alle gebruikersinterface-evenementen worden sequieel op een enkele thread verwerkt

Dit single-threaded model voorkomt racecondities en behoudt een consistente staat voor alle gebruikersinterfacecomponenten, maar creëert uitdagingen bij de integratie met asynchrone, langlopende computationele taken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het plannen van gebruikersinterface-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot de resultaten of eventuele uitzonderingen die zijn opgetreden.

## Threadcontextovereenkomst {#thread-context-inheritance}

Automatische contextovereenkomst is een cruciale functie van `Environment.runLater()`. Wanneer een thread die draait in een `Environment` childthreads aanmaakt, erven die kinderen automatisch de mogelijkheid om `runLater()` te gebruiken.

### Hoe overeenstemming werkt {#how-inheritance-works}

Elke thread die wordt gemaakt vanuit een `Environment`-thread heeft automatisch toegang tot die `Environment`. Deze overeenkomst gebeurt automatisch, dus je hoeft geen context door te geven of iets te configureren.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Deze thread heeft Environment-context

    // Childthreads erven de context automatisch
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

### Van de gebruikersinterface-thread {#from-the-ui-thread}

Wanneer aangeroepen vanuit de `Environment`-thread zelf, worden taken **sychronisch en onmiddellijk** uitgevoerd:

```java
button.onClick(e -> {
  System.out.println("Voor: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Binnen: " + Thread.currentThread().getName());
    return "voltooid";
  });

  System.out.println("Na: " + result.isDone());  // true
});
```

Met dit synchrone gedrag worden gebruikersinterface-updates vanuit gebeurtenishandlers onmiddellijk toegepast en wordt er geen onnodige wachtende overhead gegenereerd.

### Van achtergrondthreads {#from-background-threads}

Wanneer aangeroepen vanuit een achtergrondthread, worden taken **gepland voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dit draait op ForkJoinPool-thread
    System.out.println("Achtergrond: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(() -> {
      // Dit draait op de Environment-thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Verwerking compleet");
    });

    // result.isDone() zou hier false zijn
    // De taak is gepland en zal asynchroon worden uitgevoerd
  });
}
```

webforJ verwerkt taken die zijn ingediend vanuit achtergrondthreads in **strikte FIFO-volgorde**, waardoor de volgorde van operaties wordt behouden, zelfs wanneer ze gelijktijdig vanuit meerdere threads zijn ingediend. Met deze ordergarantie worden gebruikersinterface-updates toegepast in de exacte volgorde waarin ze zijn ingediend. Dus als thread A taak 1 indient, en vervolgens thread B taak 2 indient, zal taak 1 altijd vóór taak 2 worden uitgevoerd op de gebruikersinterface-thread. Het verwerken van taken in FIFO-volgorde voorkomt inconsistenties in de gebruikersinterface.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd ondersteunt annulering, waardoor je kunt voorkomen dat geplande taken worden uitgevoerd. Door uitstaande taken te annuleren, kun je geheugenlekken vermijden en voorkomen dat langlopende operaties de gebruikersinterface bijwerken nadat ze niet langer nodig zijn.

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

Bij het uitvoeren van langlopende operaties met frequente gebruikersinterface-updates, volg je alle uitstaande resultaten:

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

        // Volg voor mogelijke annulering
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Annuleer alle uitstaande gebruikersinterface-updates
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

Wanneer componenten worden vernietigd (bijv. tijdens navigatie), annuleer je alle uitstaande updates om geheugenlekken te voorkomen:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Annuleer alle uitstaande updates om geheugenlekken te voorkomen
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

1. **Contextvereiste**: Threads moeten een `Environment`-context hebben geërfd. Draad bibliotheekthreads, systeem timers, en statische initializers kunnen deze API niet gebruiken.

2. **Voorkomen van geheugenlekken**: Volg altijd `PendingResult`-objecten en annuleer deze in componentlevenscyclusmethoden. Geplande lambdas leggen referenties vast naar gebruikersinterfacecomponenten, waardoor garbage collection wordt voorkomen als ze niet geannuleerd worden.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht het belang. Er is geen prioriteitssysteem.

4. **Beperkingen van annuleringen**: Annulering voorkomt alleen de uitvoering van geplande taken. Taken die al worden uitgevoerd, worden normaal beëindigd.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Het volgende is een complete, productieklare implementatie die alle best practices voor asynchrone gebruikersinterface-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een enkele thread-executor om uitputting van middelen te voorkomen
  // Voor de productie, overweeg het gebruik van een gedeeld applicatie-brede thread pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Volg de huidige taak en uitstaande gebruikersinterface-updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Gebruikersinterfacecomponenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demonstratie van achtergrondgebruikersinterface-updates");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demonstratie toont aan hoe Environment.runLater() veilige gebruikersinterface-updates mogelijk maakt vanuit achtergrondthreads. " +
          "Klik op 'Start Langlopende Taak' om een 10-seconden achtergrondbewerking uit te voeren die de voortgang van de gebruikersinterface bijwerkt. " +
          "De knop 'Test UI' bewijst dat de gebruikersinterface responsief blijft tijdens de achtergrondoperatie.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Start Langlopende Taak");
  private Button cancelButton = new Button("Annuleer Taak");
  private Button testButton = new Button("Test UI - Klik op Mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, waardoor een goede opruiming van zowel de " +
          "achtergrondthread als de geplande gebruikersinterface-updates wordt gedemonstreerd.");
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

    // Annuleer elke draaiende taak en uitstaande gebruikersinterface-updates
    cancelTask();

    // Leeg taakverwijzing
    currentTask = null;

    // Sluit de instance-executor op een nette manier af
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset geannuleerde vlag en leegg recente uitstaande updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) zal de thread onderbreken, waardoor Thread.sleep() een
    // InterruptedException veroorzaakt
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
            showToast("Taak werd geannuleerd", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Totaal 10 seconden
        } catch (InterruptedException e) {
          // Thread is onderbroken - kom onmiddellijk terug
          Thread.currentThread().interrupt(); // Herstel onderbroken status
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

      // Laatste update met resultaat (deze code wordt alleen bereikt als de taak zonder
      // annulering wordt voltooid)
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
      // Stel de geannuleerde vlag in
      isCancelled = true;

      // Annuleer de hoofdtaken (onderbreekt de thread)
      currentTask.cancel(true);

      // Annuleer alle uitstaande gebruikersinterface-updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Taak annuleert...");
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
```

</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Analyse van de casestudy {#case-study-analysis}

Deze implementatie demonstreert verschillende kritieke patronen:

#### 1. Beheer van thread pools {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Gebruik een **enkele thread-executor** om uitputting van middelen te voorkomen
- Maak **daemon threads** die de JVM-afsluiting niet zullen voorkomen

#### 2. Volgen van uitstaande updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt gevolgd om:
- Annulering mogelijk te maken wanneer de gebruiker op annuleren klikt
- Voorkomen van geheugenlekken in `onDestroy()`
- Zorg voor een goede opruiming tijdens de levenscyclus van componenten

#### 3. Coöperatieve annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, waardoor mogelijk wordt:
- Onmiddellijke reactie op annulering
- Schone exit uit de lus
- Voorkomen van verdere gebruikersinterface-updates

#### 4. Beheer van de levenscyclus {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Hergebruikt annuleringslogica
  currentTask = null;
  executor.shutdown();
}
```
Kritisch voor het voorkomen van geheugenlekken door:
- Annuleren van alle uitstaande gebruikersinterface-updates
- Onderbreken van draaiende threads
- Het afsluiten van de executor

#### 5. Testen van de responsiviteit van de gebruikersinterface {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Demonstreert dat de gebruikersinterface-thread responsief blijft tijdens achtergrondoperaties.
