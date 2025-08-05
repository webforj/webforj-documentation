---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: a426166aa63471b0d9d84e6c4786c6db
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

De `Environment.runLater()` API biedt een mechanisme voor het veilig bijwerken van de UI vanuit achtergrondthreads in webforJ-toepassingen. Deze experimentele functie maakt asynchrone bewerkingen mogelijk terwijl threadveiligheid voor UI-wijzigingen behouden blijft.

:::warning Experimentele API
Deze API is gemarkeerd als experimenteel sinds 25.02 en kan in toekomstige releases veranderen. De API-handtekening, het gedrag en de prestatiekenmerken kunnen worden gewijzigd.
:::

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaft een strikt threadingmodel waarbij alle UI-operaties op de `Environment`-thread moeten plaatsvinden. Deze beperking bestaat omdat:

1. **webforJ API-beperkingen**: De onderliggende webforJ API bindt zich aan de thread die de sessie heeft gemaakt.
2. **Thread-affiniteit van componenten**: UI-componenten behouden een status die niet threadveilig is.
3. **Evenementdispatch**: Alle UI-gebeurtenissen worden sequentieel op een enkele thread verwerkt.

Dit eendrachtige model voorkomt race-omstandigheden en behoudt een consistente status voor alle UI-componenten, maar creëert uitdagingen bij integratie met asynchrone, langdurige rekentaken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het inplannen van UI-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot het resultaat of eventuele uitzonderingen die zijn opgetreden.

## Erfelijkheid van thread-context {#thread-context-inheritance}

Automatische context-erfelijkheid is een cruciale eigenschap van `Environment.runLater()`. Wanneer een thread die draait in een `Environment` kindthreads aanmaakt, erven die kinderen automatisch de mogelijkheid om `runLater()` te gebruiken.

### Hoe erfelijkheid werkt {#how-inheritance-works}

Elke thread die vanuit een `Environment`-thread wordt gemaakt, heeft automatisch toegang tot dat `Environment`. Deze erfelijkheid gebeurt automatisch, zodat je geen context hoeft door te geven of iets hoeft te configureren.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Deze thread heeft Environment-context
        
        // Kindthreads erven de context automatisch
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // Kan runLater gebruiken omdat context is geërfd
            Environment.runLater(() -> {
                dataLabel.setText(data);
                loadingSpinner.setVisible(false);
            });
        });
    }
}
```

### Threads zonder context {#threads-without-context}

Threads die buiten de `Environment`-context zijn gecreëerd, kunnen `runLater()` niet gebruiken en zullen een `IllegalStateException` genereren:

```java
// Statische initializer - geen Environment-context
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Werpt IllegalStateException
    }).start();
}

// Systeem-timerthreads - geen Environment-context  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Werpt IllegalStateException
    }
}, 1000);

// Externe bibliotheekthreads - geen Environment-context
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Werpt IllegalStateException
    });
```

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van welke thread het aanroept:

### Vanuit de UI-thread {#from-the-ui-thread}

Wanneer het vanuit de `Environment`-thread zelf wordt aangeroepen, worden taken **synchronisch en onmiddellijk** uitgevoerd:

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

Met dit synchronische gedrag worden UI-updates vanuit gebeurtenishandlers onmiddellijk toegepast en worden er geen onnodige wachtrijen opgebouwd.

### Vanuit achtergrondthreads {#from-background-threads}

Wanneer het vanuit een achtergrondthread wordt aangeroepen, worden taken **in de wachtrij geplaatst voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Dit draait op ForkJoinPool-thread
        System.out.println("Achtergrond: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Dit draait op Environment-thread
            System.out.println("UI Update: " + Thread.currentThread().getName());
            statusLabel.setText("Verwerking voltooid");
        });
        
        // result.isDone() zou hier false zijn
        // De taak is in de wachtrij geplaatst en zal asynchroon worden uitgevoerd
    });
}
```

webforJ verwerkt taken die vanuit achtergrondthreads zijn ingediend in **strikte FIFO-volgorde**, waardoor de volgorde van operaties behouden blijft, zelfs wanneer ze gelijktijdig vanuit meerdere threads worden ingediend. Met deze volgorde-garantie worden UI-updates toegepast in de exacte volgorde waarin ze zijn ingediend. Dus als thread A taak 1 indient, en vervolgens thread B taak 2 indient, zal taak 1 altijd vóór taak 2 op de UI-thread worden uitgevoerd. Taken in FIFO-volgorde verwerken voorkomt inconsistenties in de UI.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd, ondersteunt annulering, waardoor je kunt voorkomen dat in de wachtrij geplaatste taken worden uitgevoerd. Door lopende taken te annuleren, kun je geheugenlekken voorkomen en langdurige bewerkingen vermijden die de UI bijwerken nadat ze niet langer nodig zijn.

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

Bij het uitvoeren van langdurige bewerkingen met frequente UI-updates, volg je alle wachtende resultaten:

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
                
                // Volg voor potentiële annulering
                pendingUpdates.add(update);
                
                Thread.sleep(100);
            }
        });
    }
    
    public void cancelTask() {
        isCancelled = true;
        
        // Annuleer alle wachtende UI-updates
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

Wanneer componenten worden vernietigd (bijvoorbeeld tijdens navigatie), annuleer dan alle wachtende updates om geheugenlekken te voorkomen:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Annuleer alle wachtende updates om geheugenlekken te voorkomen
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

1. **Contextvereiste**: Threads moeten een `Environment`-context hebben geërfd. Externe bibliotheekthreads, systeemtimers en statische initializers kunnen deze API niet gebruiken.

2. **Voorkomen van geheugenlekken**: Houd altijd `PendingResult`-objecten bij en annuleer ze in de methoden voor de levenscyclus van componenten. In de wachtrij geplaatste lambdas vangen referenties naar UI-componenten, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht het belang. Er is geen prioriteitssysteem.

4. **Annuleringsbeperkingen**: Annulering voorkomt alleen de uitvoering van in de wachtrij geplaatste taken. Taken die al worden uitgevoerd, zullen normaal worden voltooid.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Het volgende is een complete, productieklare implementatie die alle best practices voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")

public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een executor met één thread om uitputting van hulpbronnen te voorkomen
  // Voor productie, overweeg het gebruik van een gedeeld threadpool op applicatieniveau
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Houd de huidige taak en wachtende UI-updates bij
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-componenten
  private FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Achtergrond UI-updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demo laat zien hoe Environment.runLater() veilige UI-updates vanuit achtergrondthreads mogelijk maakt. " +
          "Klik op 'Start Long Task' om een 10-seconden achtergrondrekenopdracht uit te voeren die de voortgang van de UI bijwerkt. " +
          "De knop 'Test UI' bewijst dat de UI responsief blijft tijdens de achtergrondbewerking.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Annuleer Taak");
  private Button testButton = new Button("Test UI - Klik op Mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, wat een goede opruiming van zowel de " +
          "achtergrondthread als de in de wachtrij geplaatste UI-updates aantoont.");
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

    // Annuleer elke lopende taak en wachtende UI-updates
    cancelTask();

    // Maak taakomschrijving schoon
    currentTask = null;

    // Shutdown de instantie-executor gracieus
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset de annulering vlag en maak vorige wachtende updates schoon
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) zal de thread onderbreken, waardoor Thread.sleep() een
    // InterruptedException zal werpen
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuleer lange taak met 100 stappen
      for (int i = 0; i <= 100; i++) {
        // Controleer op annulering
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Taak geannuleerd!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Taak is geannuleerd", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Totaal 10 seconden
        } catch (InterruptedException e) {
          // Thread was onderbroken - onmiddellijk beëindigen
          Thread.currentThread().interrupt(); // Herstel onderbroken status
          return;
        }

        // Doe een berekening (bepaald voor demo)
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

      // Laatste update met resultaat (deze code wordt alleen bereikt als de taak is voltooid zonder
      // annulering)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Taak voltooid!");
          resultField.setValue("Resultaat: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Achtergrondtaak is voltooid!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Stel de geannuleerde vlag in
      isCancelled = true;

      // Annuleer de hoofdtaak (onderbreekt de thread)
      currentTask.cancel(true);

      // Annuleer alle wachtende UI-updates
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

#### 1. Beheer van threadpools {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Gebruikt een **executor met één thread** om uitputting van hulpbronnen te voorkomen
- Creëert **daemonthreads** die de JVM-afsluiting niet zullen verhinderen

#### 2. Bijhouden van wachtende updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden om:
- Annulering mogelijk te maken wanneer de gebruiker op annuleren klikt
- Voorkomen van geheugenlekken in `onDestroy()`
- Juiste opruiming tijdens de levenscyclus van de component

#### 3. Samenwerkende annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, wat mogelijk maakt:
- Onmiddellijke reactie op annulering
- Schone uitgang uit de lus
- Voorkoming van verdere UI-updates

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
- Het annuleren van alle wachtende UI-updates
- Het onderbreken van lopende threads
- Het afsluiten van de executor

#### 5. Testen van UI-responsiviteit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Toont aan dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
