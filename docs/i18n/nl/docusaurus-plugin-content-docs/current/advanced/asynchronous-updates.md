---
sidebar_position: 46
title: Asynchrone Updates
sidebar_class_name: new-content
_i18n_hash: 0db4be3f7e785c967b2e7efa442ca3ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

De `Environment.runLater()` API biedt een mechanisme voor veilig bijwerken van de gebruikersinterface vanuit achtergrondthreads in webforJ-toepassingen. Deze experimentele functie maakt asynchrone operaties mogelijk terwijl de threadveiligheid voor UI-wijzigingen behouden blijft.

:::warning Experimentele API
Deze API is gemarkeerd als experimenteel sinds 25.02 en kan in toekomstige versies veranderen. De API-handtekening, het gedrag en de prestatiekenmerken zijn onderhevig aan wijziging.
:::

## Begrijpen van het threadmodel {#understanding-the-thread-model}

webforJ handhaaft een strikt threadingmodel waarbij alle UI-operaties op de `Environment`-thread moeten plaatsvinden. Deze beperking bestaat omdat:

1. **webforJ API-beperkingen**: De onderliggende webforJ API is gebonden aan de thread die de sessie heeft aangemaakt.
2. **Componentthreadaffiniteit**: UI-componenten behouden status die niet thread-veilig is.
3. **Event-dispatch**: Alle UI-gebeurtenissen worden sequentieel op een enkele thread verwerkt.

Dit single-threaded model voorkomt racecondities en behoudt een consistente staat voor alle UI-componenten, maar creëert uitdagingen bij integratie met asynchrone, langdurige berekeningstaken.

## `RunLater` API {#runlater-api}

De `Environment.runLater()` API biedt twee methoden voor het plannen van UI-updates:

```java title="Environment.java"
// Plan een taak zonder retourwaarde
public static PendingResult<Void> runLater(Runnable task)

// Plan een taak die een waarde retourneert
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot het resultaat of eventuele opgetreden uitzonderingen.

## Threadcontextovererving {#thread-context-inheritance}

Automatische contextovererving is een belangrijke functie van `Environment.runLater()`. Wanneer een thread die in een `Environment` draait kindthreads aanmaakt, erven die kinderen automatisch het vermogen om `runLater()` te gebruiken.

### Hoe overerving werkt {#how-inheritance-works}

Elke thread die vanuit een `Environment`-thread wordt aangemaakt, heeft automatisch toegang tot die `Environment`. Deze overerving gebeurt automatisch, dus je hoeft geen context door te geven of iets te configureren.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Deze thread heeft de Environment-context
        
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

Threads die buiten de `Environment`-context zijn gemaakt, kunnen `runLater()` niet gebruiken en zullen een `IllegalStateException` genereren:

```java
// Statische initialisator - geen Environment-context
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

### Vanaf de UI-thread {#from-the-ui-thread}

Wanneer het vanuit de `Environment`-thread zelf wordt aangeroepen, worden taken **synchronisch en onmiddellijk** uitgevoerd:

```java
button.onClick(e -> {
    System.out.println("Voorafgaand: " + Thread.currentThread().getName());
    
    PendingResult<String> result = Environment.runLater(() -> {
        System.out.println("Binnen: " + Thread.currentThread().getName());
        return "voltooid";
    });
    
    System.out.println("Erna: " + result.isDone());  // true
});
```

Met dit synchrone gedrag worden UI-updates vanuit gebeurtenishandlers onmiddellijk toegepast en komen geen onnodige wachtrijkosten met zich mee.

### Vanuit achtergrondthreads {#from-background-threads}

Wanneer het vanuit een achtergrondthread wordt aangeroepen, worden taken **gequeued voor asynchrone uitvoering**:

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
        // De taak is gequeue en zal asynchroon worden uitgevoerd
    });
}
```

webforJ verwerkt taken die van achtergrondthreads zijn ingediend in **strikte FIFO-volgorde**, waardoor de volgorde van operaties behouden blijft, zelfs wanneer ze gelijktijdig vanaf meerdere threads zijn ingediend. Met deze ordeningsgarantie worden UI-updates in de exacte volgorde toegepast waarin ze zijn ingediend. Dus als thread A taak 1 indient, en daarna thread B taak 2 indient, zal taak 1 altijd vóór taak 2 op de UI-thread worden uitgevoerd. Het verwerken van taken in FIFO-volgorde voorkomt inconsistenties in de UI.

## Taakannulering {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd, ondersteunt annulering, waardoor je kunt voorkomen dat gequeue taken worden uitgevoerd. Door lopende taken te annuleren, kun je geheugenlekken vermijden en voorkomen dat langdurige bewerkingen de UI bijwerken nadat ze niet langer nodig zijn.

### Basisannulering {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Annuleer als het nog niet is uitgevoerd
if (!result.isDone()) {
    result.cancel();
}
```

### Beheren van meerdere updates {#managing-multiple-updates}

Bij het uitvoeren van langdurige bewerkingen met frequente UI-updates, volg alle lopende resultaten:

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

### Componentlevenscyclusbeheer {#component-lifecycle-management}

Wanneer componenten worden vernietigd (bijvoorbeeld tijdens navigatie), annuleer dan alle lopende updates om geheugenlekken te voorkomen:

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

1. **Contextvereiste**: Threads moeten een `Environment`-context hebben geërfd. Externe bibliotheekthreads, systeemtijden en statische initializers kunnen deze API niet gebruiken.

2. **Geheugenlekpreventie**: Houd altijd `PendingResult`-objecten bij en annuleer ze in de componentlevenscyclusmethoden. Gequeue lambdas vangen referenties naar UI-componenten, waardoor garbage collection wordt voorkomen als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht het belang. Er is geen prioriteitssysteem.

4. **Annuleringsbeperkingen**: Annulering voorkomt alleen de uitvoering van gequeue taken. Taken die al worden uitgevoerd, worden normaal afgerond.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Het volgende is een complete, productieklare implementatie die alle beste praktijken voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een enkele threadexecutor om resource-uitputting te voorkomen
  // Voor productie, overweeg een gedeeld applicatie-breed threadpool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Volg de huidige taak en lopende UI-updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-componenten
  private FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Achtergrond UI-updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demo toont hoe Environment.runLater() veilige UI-updates vanuit achtergrondthreads mogelijk maakt. " +
          "Klik op 'Start Long Task' om een achtergrondberekening van 10 seconden uit te voeren die de voortgang van de UI bijwerkt. " +
          "De knop 'Test UI' bewijst dat de UI responsief blijft tijdens de achtergrondbewerking.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Annuleer Taak");
  private Button testButton = new Button("Test UI - Klik op Mij!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd, wat zorgt voor een juiste opruiming van zowel de " +
          "achtergrondthread als de gequeue UI-updates.");
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

    // Annuleer elke lopende taak en lopende UI-updates
    cancelTask();

    // Wis taakreferentie
    currentTask = null;

    // Schakel de instantie-executor op een nette manier uit
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset geannuleerde vlag en wis vorige lopende updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Start achtergrondtaak met expliciete executor
    // Opmerking: cancel(true) zal de thread onderbreken, wat leidt tot Thread.sleep() dat een
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
            showToast("Taak is geannuleerd", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Totaal 10 seconden
        } catch (InterruptedException e) {
          // Thread was onderbroken - verlaat onmiddellijk
          Thread.currentThread().interrupt(); // Herstel onderbroken status
          return;
        }

        // Voer een berekening uit (deterministisch voor demo)
        // Produceert waarden tussen 0 en 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Werk voortgang bij vanuit achtergrondthread
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

      // Annuleer de hoofdtaak (onderbreekt de thread)
      currentTask.cancel(true);

      // Annuleer alle lopende UI-updates
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

### Casestudy-analyse {#case-study-analysis}

Deze implementatie demonstreert verschillende kritieke patronen:

#### 1. Threadpoolbeheer {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Gebruikt een **enkele threadexecutor** om resource-uitputting te voorkomen.
- Creëert **daemonthreads** die de JVM-afsluiting niet zullen blokkeren.

#### 2. Volgen van lopende updates {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden om:
- Annulering mogelijk te maken wanneer de gebruiker op annuleren klikt.
- Geheugenlekpreventie in `onDestroy()`.
- Juiste opruiming tijdens de componentlevenscyclus mogelijk te maken.

#### 3. Coöperatieve annulering {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, waardoor:
- Onmiddellijke reactie op annulering mogelijk is.
- Schoon vertrek uit de lus mogelijk is.
- Voorkoming van verdere UI-updates mogelijk is.

#### 4. Levenscyclusbeheer {#4-lifecycle-management}
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
- Alle lopende UI-updates te annuleren.
- Lopende threads te onderbreken.
- De executor af te sluiten.

#### 5. Testen van UI-responsiviteit {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klik #" + count + " - UI is responsief!", Theme.GRAY);
});
```
Demonstreert dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
