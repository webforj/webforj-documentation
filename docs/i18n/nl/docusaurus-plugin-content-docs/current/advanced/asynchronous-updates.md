---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 44d86e725d9228ead98794da8f6210ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

De `Environment.runLater()` API biedt een mechanisme om de UI veilig bij te werken vanuit achtergrondthreads in webforJ-toepassingen. Deze experimentele functie maakt asynchrone bewerkingen mogelijk terwijl de threadveiligheid voor UI-wijzigingen wordt behouden.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaft een strikt threadingmodel waarbij alle UI-bewerkingen op de `Environment`-thread moeten plaatsvinden. Deze beperking bestaat omdat:

1. **beperkingen van de webforJ API**: De onderliggende webforJ API is gebonden aan de thread die de sessie heeft aangemaakt.
2. **Threadaffiniteit van componenten**: UI-componenten behouden status die niet threadveilig is.
3. **Evenementdispatching**: Alle UI-evenementen worden sequentieel op een enkele thread verwerkt.

Dit single-threaded model voorkomt race-omstandigheden en behoudt een consistente staat voor alle UI-componenten, maar creëert uitdagingen bij integratie met asynchrone, langdurige computertaken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het inplannen van UI-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de taakvoltooiing bijhoudt en toegang biedt tot het resultaat of eventuele uitzonderingen die zijn opgetreden.

## Erfelijkheid van threadcontext {#thread-context-inheritance}

Automatische contextoverdracht is een cruciale functie van `Environment.runLater()`. Wanneer een thread die draait in een `Environment`-thread child-threads aanmaakt, erven die kinderen automatisch de mogelijkheid om `runLater()` te gebruiken.

### Hoe erfelijkheid werkt {#how-inheritance-works}

Elke thread die vanuit een `Environment`-thread is aangemaakt, heeft automatisch toegang tot die `Environment`. Deze erfelijkheid gebeurt automatisch, zodat je geen context hoeft door te geven of iets hoeft te configureren.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Deze thread heeft de Environment-context
    
    // Child-threads erven automatisch de context
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

Threads die buiten de `Environment`-context zijn aangemaakt, kunnen `runLater()` niet gebruiken en zullen een `IllegalStateException` opwerpen:

```java
// Staticale initializer - geen Environment-context
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Werpt IllegalStateException
  }).start();
}

// Systeemtimer-threads - geen Environment-context  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Werpt IllegalStateException
  }
}, 1000);

// Draad van externe bibliotheken - geen Environment-context
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Werpt IllegalStateException
  });
```

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van welke thread het aanroept:

### Vanuit de UI-thread {#from-the-ui-thread}

Wanneer aangeroepen vanuit de `Environment`-thread zelf, worden taken **synchronisch en onmiddellijk** uitgevoerd:

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

Met dit synchrone gedrag worden UI-updates vanuit gebeurtenishandlers onmiddellijk toegepast en zijn er geen onnodige wachttijden.

### Vanuit achtergrondthreads {#from-background-threads}

Wanneer aangeroepen vanuit een achtergrondthread, worden taken **geënt voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dit draait op een ForkJoinPool-thread
    System.out.println("Achtergrond: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Dit draait op de Environment-thread
      System.out.println("UI-update: " + Thread.currentThread().getName());
      statusLabel.setText("Verwerking voltooid");
    });
    
    // result.isDone() zou hier false zijn
    // De taak is geënt en zal asynchroon worden uitgevoerd
  });
}
```

webforJ verwerkt taken die vanuit achtergrondthreads zijn ingediend in **strikte FIFO-volgorde**, waarbij de volgorde van bewerkingen wordt behouden, zelfs wanneer ze tegelijkertijd vanuit meerdere threads zijn ingediend. Met deze volgorde garantie worden UI-updates toegepast in de exacte volgorde waarin ze zijn ingediend. Dus als thread A taak 1 indient, en daarna thread B taak 2 indient, wordt taak 1 altijd uitgevoerd vóór taak 2 op de UI-thread. Het verwerken van taken in FIFO-volgorde voorkomt inconsistenties in de UI.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd, ondersteunt annulering, waardoor je kunt voorkomen dat ingediende taken worden uitgevoerd. Door hangende taken te annuleren, kun je geheugenlekken vermijden en voorkomen dat langdurige bewerkingen de UI bijwerken nadat ze niet meer nodig zijn.

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

Bij het uitvoeren van langdurige taken met frequente UI-updates, volg alle hangende resultaten:

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
    
    // Annuleer alle hangende UI-updates
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

Wanneer componenten worden vernietigd (bijv. tijdens navigatie), annuleer alle hangende updates om geheugenlekken te voorkomen:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Annuleer alle hangende updates om geheugenlekken te voorkomen
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

1. **Contextvereiste**: Threads moeten een `Environment`-context hebben geërfd. Threads van externe bibliotheken, systeemtimers en statische initializers kunnen deze API niet gebruiken.

2. **Voorkomen van geheugenlekken**: Houd altijd `PendingResult`-objecten bij en annuleer ze in componentlevenscyclusmethoden. Geplande lambdas vangen verwijzingen naar UI-componenten, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht het belang. Er is geen prioriteitssysteem.

4. **Annuleringsbeperkingen**: Annulering voorkomt alleen de uitvoering van ingediende taken. Taken die al uitvoeren, zullen normaal worden voltooid.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Het volgende is een complete, productieklare implementatie die alle beste praktijken voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een enkele thread-executor om uitputting van hulpbronnen te voorkomen
  // Voor productie, overweeg het gebruik van een gedeeld applicatiebrede threadpool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Volg de huidige taak en hangende UI-updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-componenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demonstratie van achtergrond UI-updates");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demonstratie toont aan hoe Environment.runLater() veilige UI-updates mogelijk maakt vanuit achtergrondthreads. " +
          "Klik op 'Start Long Task' om een achtergrondberekening van 10 seconden te starten die de voortgang van de UI bijwerkt. " +
          "De knop 'Test UI' bewijst dat de UI responsief blijft tijdens de achtergrondbewerking.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Annuleer Taak");
  private Button testButton = new Button("Test UI - Klik op Mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, wat een goede opruiming van zowel de " +
          "achtergrondthread als de ingediende UI-updates aantoont.");
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
    progressBar.setText("Vooruitgang: {{x}}%");
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

    // Annuleer eventuele lopende taken en hangende UI-updates
    cancelTask();

    // Wis taakreferentie
    currentTask = null;

    // Sluit de instantie-executor op een nette manier af
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak wordt gestart...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset het annuleringsvlag en wis eerdere hangende updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) zal de thread onderbreken, waardoor Thread.sleep() een
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
          // Thread werd onderbroken - verlaat onmiddellijk
          Thread.currentThread().interrupt(); // Herstel onderbroken status
          return;
        }

        // Voer wat berekening uit (deterministisch voor demo)
        // Produceert waarden tussen 0 en 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update voortgang vanuit achtergrondthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verwerken... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Eindupdate met resultaat (deze code wordt alleen bereikt als de taak is voltooid zonder
      // annulering)
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

      // Annuleer de hoofdtaska (onderbreekt de thread)
      currentTask.cancel(true);

      // Annuleer alle hangende UI-updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Taak wordt geannuleerd...");
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

#### 1. Beheer van threadpools {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Gebruik een **enkele thread-executor** om uitputting van hulpbronnen te voorkomen.
- Creëert **daemonthreads** die de JVM-shutdown niet zullen voorkomen.

#### 2. Bijhouden van hangende updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke `Environment.runLater()`-aanroep wordt gevolgd om:
- Annulering mogelijk te maken wanneer de gebruiker op annuleren klikt.
- Voorkomen van geheugenlekken in `onDestroy()`.
- Goede opruiming tijdens de levenscyclus van de component.

#### 3. Coöperatieve annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, waardoor:
- Onmiddellijke reactie op annulering mogelijk is.
- Schone exit uit de lus.
- Voorkoming van verdere UI-updates.

#### 4. Beheer van de levenscyclus {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Hergebruik van annuleringslogica.
  currentTask = null;
  executor.shutdown();
}
```
Kritisch om geheugenlekken te voorkomen door:
- Annuleren van alle hangende UI-updates.
- Onderbreken van actieve threads.
- Afsluiten van de executor.

#### 5. Testen van UI-responsiviteit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Toont aan dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
