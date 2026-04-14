---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: cbdf51a80355d73a6c7f5ec85cfa198a
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

De `Environment.runLater()` API biedt een mechanisme voor het veilig bijwerken van de gebruikersinterface vanuit achtergrondthreads in webforJ-toepassingen. Deze experimentele functie maakt asynchrone bewerkingen mogelijk terwijl de thread-veiligheid voor UI-wijzigingen behouden blijft.

<ExperimentalWarning />

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaft een strikt threadmodel waarbij alle UI-bewerkingen moeten plaatsvinden op de `Environment` thread. Deze beperking bestaat omdat:

1. **webforJ API-beperkingen**: De onderliggende webforJ API bindt aan de thread die de sessie heeft aangemaakt
2. **Thread affiniteit van componenten**: UI-componenten behouden staat die niet thread-veilig is
3. **Evenementverwerking**: Alle UI-events worden sequentieel op één thread verwerkt

Dit single-threaded model voorkomt racecondities en behoudt een consistente staat voor alle UI-componenten, maar creëert uitdagingen bij integratie met asynchrone, langdurige computertaken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het plannen van UI-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot het resultaat of eventuele uitzonderingen die zich hebben voorgedaan.

## Thread contextoverdracht {#thread-context-inheritance}

Automatische contextoverdracht is een cruciale functie van `Environment.runLater()`. Wanneer een thread die in een `Environment` draait kindthreads aanmaakt, erven die kindthreads automatisch de mogelijkheid om `runLater()` te gebruiken.

### Hoe overdracht werkt {#how-inheritance-works}

Elke thread die vanuit een `Environment` thread wordt gemaakt, heeft automatisch toegang tot die `Environment`. Deze overdracht gebeurt automatisch, zodat u geen context hoeft door te geven of iets hoeft te configureren.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Deze thread heeft Environment-context
    
    // Kindthreads erven de context automatisch
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Kan runLater gebruiken omdat de context is geerfd
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Threads zonder context {#threads-without-context}

Threads die buiten de `Environment` context zijn aangemaakt kunnen `runLater()` niet gebruiken en zullen een `IllegalStateException` genereren:

```java
// Statische initialisator - geen Environment-context
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Throw IllegalStateException
  }).start();
}

// Systeemtimerthreads - geen Environment-context  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Throw IllegalStateException
  }
}, 1000);

// Externe bibliotheekthreads - geen Environment-context
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Throw IllegalStateException
  });
```

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van welke thread het aanroept:

### Van de UI-thread {#from-the-ui-thread}

Wanneer het wordt aangeroepen vanuit de `Environment` thread zelf, worden taken **synchronisch en onmiddellijk** uitgevoerd:

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

Met dit synchronische gedrag worden UI-updates vanuit gebeurtenishandlers onmiddellijk toegepast en brengt het geen onnodige wachtrijoverhead met zich mee.

### Van achtergrondthreads {#from-background-threads}

Wanneer aangeroepen vanuit een achtergrondthread, worden taken **gepland voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Dit draait op ForkJoinPool thread
    System.out.println("Achtergrond: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Dit draait op Environment thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Verwerking voltooid");
    });
    
    // result.isDone() zou hier false zijn
    // De taak is gepland en zal asynchroon worden uitgevoerd
  });
}
```

webforJ verwerkt taken die vanuit achtergrondthreads zijn ingediend in **strikte FIFO-volgorde**, waarbij de volgorde van bewerkingen wordt behouden, zelfs wanneer deze gelijktijdig vanuit meerdere threads worden ingediend. Met deze volgordegarantie worden UI-updates in de exacte volgorde toegepast waarin ze zijn ingediend. Als thread A taak 1 indient, en vervolgens thread B taak 2 indient, dan zal taak 1 altijd vóór taak 2 worden uitgevoerd op de UI-thread. Het verwerken van taken in FIFO-volgorde voorkomt inconsistenties in de UI.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd ondersteunt annulering, zodat u kunt voorkomen dat geplande taken worden uitgevoerd. Door lopende taken te annuleren, kunt u geheugenlekken vermijden en voorkomen dat langdurige bewerkingen de UI bijwerken nadat ze niet meer nodig zijn.

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

### Beheersen van meerdere updates {#managing-multiple-updates}

Bij het uitvoeren van langdurige operaties met frequente UI-updates, houd alle lopende resultaten bij:

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
        
        // Houd bij ter voorkoming van annulering
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

Wanneer componenten worden vernietigd (bijvoorbeeld tijdens navigatie), annuleer alle lopende updates om geheugenlekken te voorkomen:

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

1. **Contextvereiste**: Threads moeten een `Environment` context hebben geërfd. Externe bibliotheekthreads, systeemtimers en statische initialisatoren kunnen deze API niet gebruiken.

2. **Voorkoming van geheugenlekken**: Houd altijd `PendingResult` objecten bij en annuleer deze in methoden voor de levenscyclus van componenten. Geplande lambdas vangen referenties naar UI-componenten, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden uitgevoerd in strikte FIFO-volgorde, ongeacht het belang. Er is geen prioriteitssysteem.

4. **Annuleringsbeperkingen**: Annulering voorkomt alleen de uitvoering van geplande taken. Taken die al worden uitgevoerd, zullen normaal worden voltooid.

## Volledige case study: `LongTaskView` {#complete-case-study-longtaskview}

De volgende is een complete, productieklare implementatie die alle beste praktijken voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een enkele thread-executor om uitputting van middelen te voorkomen
  // Voor productie, overweeg een gedeelde threadpool voor de hele applicatie
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Houd de huidige taak en lopende UI-updates bij
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-componenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demonstratie van achtergrond UI-updates");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demonstratie toont aan hoe Environment.runLater() veilige UI-updates vanuit achtergrondthreads mogelijk maakt. " +
          "Klik op 'Start Lange Taak' om een achtergrondcomputatie van 10 seconden uit te voeren die de UI voortgang bijwerkt. " +
          "De knop 'Test UI' toont aan dat de UI responsief blijft tijdens de achtergrondbewerking.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Start Lange Taak");
  private Button cancelButton = new Button("Annuleer Taak");
  private Button testButton = new Button("Test UI - Klik op Mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, wat een goede opruiming van zowel de " +
          "achtergrondthread als de geplande UI-updates aantoont.");
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

    // Annuleer elke lopende taak en geplande UI-updates
    cancelTask();

    // Maak taalkreferentie leeg
    currentTask = null;

    // Schakel de instantie executor veilig uit
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset de geannuleerde vlag en maak eerdere lopende updates leeg
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start de achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) zal de thread onderbreken, wat ervoor zorgt dat Thread.sleep() een
    // InterruptedException genereert
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
          // Thread was onderbroken - verlaat meteen
          Thread.currentThread().interrupt(); // Herstel onderbroken status
          return;
        }

        // Doe een berekening (deterministisch voor demonstratie)
        // Produceert waarden tussen 0 en 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update voortgang vanuit de achtergrondthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Verwerking... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Laatste update met resultaat (deze code wordt alleen bereikt als de taak zonder
      // annulering voltooid is)
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

      // Annuleer alle lopende UI-updates
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

### Case study-analyse {#case-study-analysis}

Deze implementatie demonstreert verschillende kritische patronen:

#### 1. Threadpoolbeheer {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Gebruikt een **enkele thread-executor** om uitputting van middelen te voorkomen
- Creeërt **daemonthreads** die de JVM-afsluiting niet kunnen voorkomen

#### 2. Volgen van lopende updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke `Environment.runLater()` aanroep wordt bijgehouden om:
- Annulering mogelijk te maken wanneer de gebruiker op annuleren klikt
- Voorkomen van geheugenlekken in `onDestroy()`
- Juiste opruiming tijdens de levenscyclus van de component

#### 3. Coöperatieve annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, waardoor mogelijk is:
- Een onmiddellijke reactie op annulering
- Een schone uitgang uit de lus
- Voorkomen van verdere UI-updates

#### 4. Levenscyclusbeheer {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Hergebruikt annulering logica
  currentTask = null;
  executor.shutdown();
}
```
Cruciaal voor het voorkomen van geheugenlekken door:
- Het annuleren van alle lopende UI-updates
- Het onderbreken van actieve threads
- Het veilig afsluiten van de executor

#### 5. Testen van UI-responsiviteit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Toont aan dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
