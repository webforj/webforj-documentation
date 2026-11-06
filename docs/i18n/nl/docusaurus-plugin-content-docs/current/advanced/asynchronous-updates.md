---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 8dc68aced15dfd90481cd9ea98e4b2f2
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

De `Environment.runLater()`-API biedt een mechanisme om de UI veilig bij te werken vanuit achtergrondthreads in webforJ-applicaties. Deze experimentele functie maakt asynchrone bewerkingen mogelijk, terwijl de threadveiligheid van UI-aanpassingen behouden blijft.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Het threadmodel begrijpen {#understanding-the-thread-model}

webforJ hanteert een strikt threadmodel waarbij alle UI-bewerkingen op de `Environment`-thread moeten plaatsvinden. Deze beperking bestaat omdat:

1. **Beperkingen van de webforJ-API**: De onderliggende webforJ-API is gekoppeld aan de thread die de sessie heeft aangemaakt
2. **Threadaffiniteit van componenten**: UI-componenten behouden een status die niet threadveilig is
3. **Eventdispatching**: Alle UI-gebeurtenissen worden sequentieel verwerkt op één thread

Dit single-threadmodel voorkomt racecondities en zorgt voor een consistente status van alle UI-componenten, maar veroorzaakt uitdagingen bij de integratie met asynchrone, langdurige rekentaken.

Elke gebruikerssessie heeft een eigen `Environment` en een eigen `Environment`-thread. Een achtergrondthread hoort bij geen enkele sessie en moet daarom worden verteld welke `Environment` moet worden bijgewerkt.

## De `RunLater`-API {#runlater-api}

De `Environment.runLater()`-API biedt twee methoden om UI-updates in te plannen. Bij beide is de doel-`Environment` het eerste argument:

```java title="Environment.java"
// Plan een taak zonder retourwaarde in
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Plan een taak die een waarde retourneert in
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die de voltooiing van de taak bijhoudt en toegang biedt tot het resultaat of eventuele opgetreden uitzonderingen.

:::warning[Overloads zonder doel]
De overloads `runLater(Runnable)` en `runLater(Supplier)` leiden het doel af van de thread die de aanroepende thread heeft aangemaakt. In een threadpool die door meerdere gebruikers wordt gedeeld, wordt elke taak uitgevoerd in de `Environment` van de gebruiker wiens aanroep die poolthread heeft aangemaakt. Deze overloads zijn sinds 26.10 verouderd en worden in de volgende hoofdrelease verwijderd. Geef het doel altijd expliciet mee.
:::

## Het doel meegeven {#passing-the-target}

`Environment.getCurrent()` retourneert de `Environment` van de huidige thread. Op de `Environment`-thread, waarop constructors, eventhandlers en lifecyclemethoden worden uitgevoerd, retourneert deze methode de `Environment` van de sessie. Op een achtergrondthread retourneert de methode `null`.

Lees het doel waar het beschikbaar is, voordat het achtergrondwerk begint, en geef het mee aan dat werk:

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // De constructor wordt uitgevoerd op de Environment-thread
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

Doordat het doel als argument wordt meegegeven, werkt de aanroep vanuit elke thread: een thread die door de view is aangemaakt, een Spring-`@Async`-thread, de gedeelde `ForkJoinPool`, een timerthread of een callbackthread van een HTTP-client.

### Het doel op de verkeerde thread lezen {#reading-the-target-on-the-wrong-thread}

De enige fout die je moet vermijden, is `Environment.getCurrent()` aanroepen binnen het achtergrondwerk. Daar retourneert de methode `null` en veroorzaakt `runLater()` een `NullPointerException`:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Fout: getCurrent() retourneert null op een achtergrondthread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Hetzelfde geldt voor statische initialisatoren, `java.util.Timer`-threads en callbacks van externe bibliotheken. Geen daarvan heeft een `Environment`, dus moet het doel afkomstig zijn uit een veld, een parameter of een vastgelegde lokale variabele die op de `Environment`-thread is gevuld.

## Uitvoeringsgedrag {#execution-behavior}

Het uitvoeringsgedrag van `runLater()` hangt af van de thread die de methode aanroept:

### Vanaf de UI-thread {#from-the-ui-thread}

Wanneer de methode wordt aangeroepen vanaf de eigen `Environment`-thread van het doel, worden taken **synchroon en onmiddellijk** uitgevoerd:

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Voor: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Binnen: " + Thread.currentThread().getName());
    return "voltooid";
  });

  System.out.println("Na: " + result.isDone());  // true
});
```

Dankzij dit synchrone gedrag worden UI-updates vanuit eventhandlers onmiddellijk toegepast, zonder onnodige wachtrijoverhead.

### Vanaf achtergrondthreads {#from-background-threads}

Wanneer de methode wordt aangeroepen vanaf een andere thread, worden taken **in de wachtrij geplaatst voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // Dit wordt uitgevoerd op een ForkJoinPool-thread
    System.out.println("Achtergrond: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // Dit wordt uitgevoerd op de Environment-thread
      System.out.println("UI-update: " + Thread.currentThread().getName());
      statusLabel.setText("Verwerking voltooid");
    });

    // result.isDone() zou hier false zijn
    // De taak staat in de wachtrij en wordt asynchroon uitgevoerd
  });
}
```

webforJ verwerkt taken die vanuit achtergrondthreads worden ingediend in **strikte FIFO-volgorde**, zodat de volgorde van bewerkingen behouden blijft, ook wanneer ze gelijktijdig vanuit meerdere threads worden ingediend. Dankzij deze garantie worden UI-updates precies in de volgorde toegepast waarin ze zijn ingediend. Als thread A dus taak 1 indient en thread B daarna taak 2, wordt taak 1 altijd vóór taak 2 uitgevoerd op de UI-thread. FIFO-verwerking voorkomt inconsistenties in de UI.

## Taken annuleren {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die door `Environment.runLater()` wordt geretourneerd, ondersteunt annulering. Zo kun je voorkomen dat taken in de wachtrij worden uitgevoerd. Door taken die nog in de wachtrij staan te annuleren, kun je geheugenlekken voorkomen en voorkomen dat langdurige bewerkingen de UI bijwerken wanneer dat niet langer nodig is.

### Eenvoudige annulering {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Annuleer de taak als deze nog niet is uitgevoerd
if (!result.isDone()) {
  result.cancel();
}
```

### Meerdere updates beheren {#managing-multiple-updates}

Houd bij langdurige bewerkingen met frequente UI-updates alle openstaande resultaten bij:

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

        // Bijhouden voor eventuele annulering
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Alle openstaande UI-updates annuleren
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

Wanneer componenten worden vernietigd (bijvoorbeeld tijdens navigatie), annuleer dan alle openstaande updates om geheugenlekken te voorkomen:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Alle openstaande updates annuleren om geheugenlekken te voorkomen
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

1. **Doel vereist**: De doel-`Environment` wordt met `Environment.getCurrent()` gelezen op de `Environment`-thread en meegegeven aan het achtergrondwerk. Als je deze methode op een achtergrondthread aanroept, retourneert ze `null`.

2. **Geheugenlekken voorkomen**: Houd `PendingResult`-objecten altijd bij en annuleer ze in lifecyclemethoden van componenten. Lambdas in de wachtrij leggen verwijzingen naar UI-componenten vast, waardoor deze niet door de garbagecollector kunnen worden opgeruimd als ze niet worden geannuleerd.

3. **FIFO-uitvoering**: Alle taken worden uitgevoerd in strikte FIFO-volgorde, ongeacht hun belang. Er is geen prioriteitssysteem.

4. **Beperkingen van annulering**: Annulering voorkomt alleen de uitvoering van taken die nog in de wachtrij staan. Taken die al worden uitgevoerd, worden normaal voltooid.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

De volgende code is een volledige, productieklare implementatie die alle best practices voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik een executor met één thread om uitputting van resources te voorkomen
  // Overweeg voor productie een gedeelde threadpool voor de hele applicatie
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Houd de huidige taak en openstaande UI-updates bij
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-componenten
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demo van UI-updates op de achtergrond");
  private Paragraph descriptionPara = new Paragraph(
      "Deze demo laat zien hoe Environment.runLater() veilige UI-updates vanuit achtergrondthreads mogelijk maakt. " +
          "Klik op 'Langdurige taak starten' om een berekening van 10 seconden op de achtergrond uit te voeren die de voortgang in de UI bijwerkt. " +
          "De knop 'UI testen' laat zien dat de UI responsief blijft tijdens de bewerking op de achtergrond.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Langdurige taak starten");
  private Button cancelButton = new Button("Taak annuleren");
  private Button testButton = new Button("UI testen - klik hier!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd. Zo wordt getoond hoe zowel de " +
          "achtergrondthread als updates in de wachtrij correct worden opgeruimd.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Velden configureren
    statusField.setReadOnly(true);
    statusField.setValue("Klaar om te starten");
    statusField.setLabel("Status");

    // Voortgangsbalk configureren
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

    // Knoppen configureren
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Klik #" + count + " - de UI reageert!", Theme.GRAY);
    });

    // Componenten toevoegen
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Eventuele actieve taak en openstaande UI-updates annuleren
    cancelTask();

    // Verwijzing naar de taak wissen
    currentTask = null;

    // De executor van deze instantie op een nette manier afsluiten
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // De annuleringsvlag resetten en eerdere openstaande updates wissen
    isCancelled = false;
    pendingUIUpdates.clear();

    // De doel-Environment op de UI-thread lezen, voordat de achtergrondtaak start
    Environment env = Environment.getCurrent();

    // Achtergrondtaak starten met een expliciete executor
    // Opmerking: cancel(true) onderbreekt de thread, waardoor Thread.sleep() een
    // InterruptedException genereert
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Langdurige taak simuleren met 100 stappen
      for (int i = 0; i <= 100; i++) {
        // Controleren of de taak is geannuleerd
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
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
          Thread.sleep(100); // In totaal 10 seconden
        } catch (InterruptedException e) {
          // De thread is onderbroken: onmiddellijk stoppen
          Thread.currentThread().interrupt(); // De interruptstatus herstellen
          return;
        }

        // Een berekening uitvoeren (deterministisch voor de demo)
        // Levert waarden tussen 0 en 1 op
        result += Math.sin(i) * 0.5 + 0.5;

        // De voortgang bijwerken vanuit de achtergrondthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Bezig met verwerken... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Laatste update met resultaat (deze code wordt alleen bereikt als de taak zonder
      // annulering is voltooid)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
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
      // De annuleringsvlag instellen
      isCancelled = true;

      // De hoofdtaak annuleren (onderbreekt de thread)
      currentTask.cancel(true);

      // Alle openstaande UI-updates annuleren
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

#### 2. Het doel bepalen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` wordt uitgevoerd op de `Environment`-thread. Daarom leest de methode daar het doel en geeft de achtergrondtaak dit mee aan elke aanroep van `Environment.runLater()`. Dezelfde code werkt ongewijzigd met een gedeelde threadpool voor de hele applicatie.

#### 3. Openstaande updates bijhouden {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden, zodat het volgende mogelijk is:
- Annulering wanneer de gebruiker op Annuleren klikt
- Voorkomen van geheugenlekken in `onDestroy()`
- Correct opruimen tijdens de lifecycle van componenten

#### 4. Coöperatieve annulering {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, wat het volgende mogelijk maakt:
- Direct reageren op annulering
- De lus netjes verlaten
- Verdere UI-updates voorkomen

#### 5. Lifecyclebeheer {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Hergebruikt de annuleringslogica
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
  showToast("Klik #" + count + " - de UI reageert!", Theme.GRAY);
});
```
Toont aan dat de UI-thread responsief blijft tijdens bewerkingen op de achtergrond.
