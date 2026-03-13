---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 9ea7ae8b53ce19e2fee19e72929c732e
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

De `Environment.runLater()` API biedt een mechanisme voor het veilig bijwerken van de gebruikersinterface vanuit achtergrondthreads in webforJ-toepassingen. Deze experimentele functie stelt asynchrone bewerkingen in staat, terwijl de threadveiligheid voor gebruikersinterfacewijzigingen behouden blijft.

:::warning Experimentele API
Deze API is gemarkeerd als experimenteel sinds 25.02 en kan in toekomstige releases veranderen. De API-handtekening, gedrag en prestatiekenmerken zijn onderhevig aan wijzigingen.
:::

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaft een strikt threadingmodel waarbij alle gebruikersinterface-operaties op de `Environment`-thread moeten plaatsvinden. Deze beperking bestaat omdat:

1. **webforJ API-beperkingen**: De onderliggende webforJ API is gebonden aan de thread die de sessie heeft aangemaakt.
2. **Threadgelieerdheid van componenten**: Gebruikersinterfacecomponenten behouden een toestand die niet thread-veilig is.
3. **Evenementdispatching**: Alle gebruikersinterface-evenementen worden sequentieel op een enkele thread verwerkt.

Dit single-threaded model voorkomt race conditions en behoudt een consistente toestand voor alle gebruikersinterfacecomponenten, maar creëert uitdagingen bij integratie met asynchrone, langdurige computertaken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het plannen van gebruikersinterface-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot het resultaat of eventuele uitzonderingen die optraden.

## Draadcontextovererving {#thread-context-inheritance}

Automatische contextovererving is een cruciale functie van `Environment.runLater()`. Wanneer een thread die draait in een `Environment` kindthreads aanmaakt, erven die kinderen automatisch de mogelijkheid om `runLater()` te gebruiken.

### Hoe overerving werkt {#how-inheritance-works}

Elke thread die vanuit een `Environment`-thread is gemaakt, heeft automatisch toegang tot die `Environment`. Deze overerving gebeurt automatisch, zodat je geen context hoeft door te geven of iets hoeft te configureren.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Deze thread heeft een Environment-context
        
        // Kindthreads erven automatisch de context
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

Threads die buiten de `Environment`-context zijn gemaakt, kunnen `runLater()` niet gebruiken en zullen een `IllegalStateException` opwerpen:

```java
// Statische initializer - geen Environment-context
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Gooi IllegalStateException
    }).start();
}

// Systeemtimer-threads - geen Environment-context  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Gooi IllegalStateException
    }
}, 1000);

// Externe bibliotheek-threads - geen Environment-context
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Gooi IllegalStateException
    });
```

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van welke thread het aanroept:

### Vanaf de gebruikersinterface-thread {#from-the-ui-thread}

Wanneer het wordt aangeroepen vanuit de `Environment`-thread zelf, worden taken **synchroon en onmiddellijk** uitgevoerd:

```java
button.onClick(e -> {
    System.out.println("Voor: " + Thread.currentThread().getName());
    
    PendingResult<String> result = Environment.runLater(() -> {
        System.out.println("Binnen: " + Thread.currentThread().getName());
        return "gecompleteerd";
    });
    
    System.out.println("Na: " + result.isDone());  // true
});
```

Met dit synchrone gedrag worden gebruikersinterface-updates vanuit gebeurtenishandlers onmiddellijk toegepast en incurreren ze geen onnodige wachttijd.

### Vanaf achtergrondthreads {#from-background-threads}

Wanneer het wordt aangeroepen vanuit een achtergrondthread, worden taken **gepland voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Dit draait op ForkJoinPool-thread
        System.out.println("Achtergrond: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Dit draait op de Environment-thread
            System.out.println("UI-update: " + Thread.currentThread().getName());
            statusLabel.setText("Verwerking voltooid");
        });
        
        // result.isDone() zou hier false zijn
        // De taak is in de wachtrij geplaatst en zal asynchroon worden uitgevoerd
    });
}
```

webforJ verwerkt taken die van achtergrondthreads zijn ingediend in **strikte FIFO-volgorde**, waardoor de volgorde van operaties behouden blijft, zelfs wanneer deze gelijktijdig vanuit meerdere threads zijn ingediend. Met deze volgordegarantie worden gebruikersinterface-updates in de exacte volgorde toegepast waarin ze zijn ingediend. Dus als thread A taak 1 indient, en dan thread B taak 2 indient, zal taak 1 altijd vóór taak 2 op de gebruikersinterface-thread worden uitgevoerd. Het verwerken van taken in FIFO-volgorde voorkomt inconsistenties in de gebruikersinterface.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd, ondersteunt annulering, zodat je kunt voorkomen dat geplande taken worden uitgevoerd. Door uitstaande taken te annuleren, kun je geheugenlekken vermijden en voorkomen dat langdurige bewerkingen de gebruikersinterface bijwerken nadat ze niet meer nodig zijn.

### Basisannulering {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Annuleer als deze nog niet is uitgevoerd
if (!result.isDone()) {
    result.cancel();
}
```

### Beheren van meerdere updates {#managing-multiple-updates}

Bij het uitvoeren van langdurige bewerkingen met frequente gebruikersinterface-updates, houd je alle uitstaande resultaten bij:

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
                
                // Bijhouden voor mogelijke annulering
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

### Beheer van levenscyclus van componenten {#component-lifecycle-management}

Wanneer componenten worden vernietigd (bijvoorbeeld tijdens navigatie), annuleer je alle uitstaande updates om geheugenlekken te voorkomen:

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

1. **Contextvereiste**: Draad moet een `Environment`-context hebben geërfd. Externe bibliotheekthreads, systeemtimers en statische initializers kunnen deze API niet gebruiken.

2. **Geheugenlekpreventie**: Houd altijd `PendingResult`-objecten bij en annuleer deze in de levenscyclusmethoden van componenten. Geplande lambdas vangen verwijzingen naar gebruikersinterfacecomponenten, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht hun belang. Er is geen prioriteitssysteem.

4. **Annuleringslimieten**: Annulering voorkomt alleen de uitvoering van geplande taken. Taken die al worden uitgevoerd, zullen normaal worden voltooid.

## Volledige case study: `LongTaskView` {#complete-case-study-longtaskview}

Het volgende is een volledige, productieklare implementatie die alle best practices voor asynchrone gebruikersinterface-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een enkele thread executor om uitputting van bronnen te voorkomen
  // Voor productie, overweeg een gedeelde applicatie-brede thread pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Houd de huidige taak en uitstaande gebruikersinterface-updates bij
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Gebruikersinterfacecomponenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Achtergrond UI-updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demo toont hoe Environment.runLater() veilige UI-updates vanuit achtergrondthreads mogelijk maakt. " +
          "Klik op 'Begin lange taak' om een achtergrondberekening van 10 seconden uit te voeren die de voortgang van de UI bijwerkt. " +
          "De knop 'Test UI' bewijst dat de UI responsief blijft tijdens de achtergrondbewerking.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Begin lange taak");
  private Button cancelButton = new Button("Annuleer taak");
  private Button testButton = new Button("Test UI - Klik op mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, wat een juiste opruiming van zowel de " +
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

    // Annuleer elke actieve taak en uitstaande gebruikersinterface-updates
    cancelTask();

    // Wis taakverwijzing
    currentTask = null;

    // Sluit de instantie executor netjes af
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset geannuleerd vlag en wis eerdere uitstaande updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) onderbreekt de thread, waardoor Thread.sleep() een
    // InterruptedException kan ondervinden.
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
          Thread.sleep(100); // Totaal van 10 seconden
        } catch (InterruptedException e) {
          // Thread is onderbroken - verlaat onmiddellijk
          Thread.currentThread().interrupt(); // Herstel onderbroken status
          return;
        }

        // Doe enige berekening (deterministisch voor demo)
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

      // Laatste update met resultaat (deze code wordt alleen bereikt als de taak
      // is voltooid zonder annulering)
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
      // Zet de geannuleerde vlag
      isCancelled = true;

      // Annuleer de hoofdtaak (onderbreekt de thread)
      currentTask.cancel(true);

      // Annuleer alle uitstaande gebruikersinterface-updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Taak annuleren...");
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
- Gebruikt een **enkele thread executor** om uitputting van middelen te voorkomen.
- Maakt **daemonthreads** aan die de JVM-shutdown niet verhinderen.

#### 2. Bijhouden van uitstaande updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden om:
- Annulering mogelijk te maken wanneer de gebruiker op annuleren klikt.
- Geheugenlekpreventie in `onDestroy()`.
- Juiste opruiming tijdens de levenscyclus van de component te waarborgen.

#### 3. Coöperatieve annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, waardoor:
- onmiddellijke reactie op annulering mogelijk is.
- schone exit uit de lus.
- verdere gebruikersinterface-updates worden voorkomen.

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
Cruciaal voor het voorkomen van geheugenlekken door:
- Het annuleren van alle uitstaande gebruikersinterface-updates.
- Het onderbreken van actieve threads.
- Het afsluiten van de executor.

#### 5. Testen van gebruikersinterfaceresponsiviteit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Toont aan dat de thread van de gebruikersinterface responsief blijft tijdens achtergrondbewerkingen.
