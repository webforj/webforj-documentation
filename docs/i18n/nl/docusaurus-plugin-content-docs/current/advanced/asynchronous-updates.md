---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchrone updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 7fedd93e44d699bba0a959504c7bb40c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

De `Environment.runLater()`-API biedt een mechanisme om de UI in webforJ-applicaties veilig bij te werken vanuit achtergrondthreads. Met deze previewfunctie kun je asynchrone bewerkingen uitvoeren en tegelijkertijd threadveiligheid behouden bij UI-aanpassingen.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Het threadmodel begrijpen {#understanding-the-thread-model}

webforJ hanteert een strikt threadmodel waarbij alle UI-bewerkingen op de `Environment`-thread moeten plaatsvinden. Deze beperking bestaat omdat:

1. **Beperkingen van de webforJ-API**: De onderliggende webforJ-API is gebonden aan de thread die de sessie heeft aangemaakt
2. **Threadaffiniteit van componenten**: UI-componenten behouden een status die niet threadveilig is
3. **Eventdispatching**: Alle UI-events worden achtereenvolgens op één thread verwerkt

Dit model met één thread voorkomt racecondities en zorgt voor een consistente status van alle UI-componenten, maar maakt het lastiger om asynchrone, langdurige rekentaken te integreren.

Elke gebruikerssessie heeft een eigen `Environment` en een eigen `Environment`-thread. Een achtergrondthread hoort bij geen enkele sessie. Daarom moet je aangeven welke `Environment` moet worden bijgewerkt.

## De `RunLater`-API {#runlater-api}

De `Environment.runLater()`-API biedt twee methoden om UI-updates in te plannen. Bij beide is de doel-`Environment` het eerste argument:

```java title="Environment.java"
// Plan een taak in zonder retourwaarde
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Plan een taak in die een waarde retourneert
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Beide methoden retourneren een <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> waarmee de voltooiing van de taak wordt bijgehouden en je toegang krijgt tot het resultaat of eventuele opgetreden uitzonderingen.

:::warning[Overloads zonder doel]
De overloads `runLater(Runnable)` en `runLater(Supplier)` leiden het doel af van de thread die de aanroepende thread heeft aangemaakt. In een threadpool die door meerdere gebruikers wordt gedeeld, wordt elke taak uitgevoerd in de `Environment` van de gebruiker wiens aanroep die poolthread heeft aangemaakt. Deze overloads zijn sinds 26.10 verouderd en worden in de volgende major release verwijderd. Geef altijd het doel mee.
:::

## Het doel meegeven {#passing-the-target}

`Environment.getCurrent()` retourneert de `Environment` van de huidige thread. Op de `Environment`-thread, waarop constructors, eventhandlers en lifecyclemethoden worden uitgevoerd, retourneert deze methode de `Environment` van de sessie. Op een achtergrondthread retourneert de methode `null`.

Lees het doel uit op de plek waar het beschikbaar is, voordat het achtergrondwerk begint, en geef het mee aan dat werk:

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

Omdat het doel als argument wordt meegegeven, werkt de aanroep vanuit elke thread: een thread die door de view is aangemaakt, een Spring-`@Async`-thread, de gedeelde `ForkJoinPool`, een timerthread of een callbackthread van een HTTP-client.

### Het doel op de verkeerde thread uitlezen {#reading-the-target-on-the-wrong-thread}

De enige fout die je moet vermijden, is `Environment.getCurrent()` uitlezen binnen het achtergrondwerk. Daar retourneert de methode `null` en gooit `runLater()` een `NullPointerException`:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Fout: getCurrent() retourneert null op een achtergrondthread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Hetzelfde geldt voor statische initializers, `java.util.Timer`-threads en callbacks van externe bibliotheken. Geen daarvan heeft een `Environment`. Het doel moet daarom afkomstig zijn uit een veld, een parameter of een lokale variabele die op de `Environment`-thread is gevuld en vervolgens is vastgelegd.

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

Door dit synchrone gedrag worden UI-updates vanuit eventhandlers onmiddellijk toegepast en zijn er geen onnodige kosten voor het in de wachtrij plaatsen.

### Vanaf achtergrondthreads {#from-background-threads}

Wanneer de methode wordt aangeroepen vanaf een andere thread, worden taken **in de wachtrij geplaatst voor asynchrone uitvoering**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // Dit wordt uitgevoerd op een ForkJoinPool-thread
    System.out.println("Background: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // Dit wordt uitgevoerd op de Environment-thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Verwerking voltooid");
    });

    // result.isDone() zou hier false zijn
    // De taak staat in de wachtrij en wordt asynchroon uitgevoerd
  });
}
```

webforJ verwerkt taken die vanuit achtergrondthreads worden ingediend in **strikte FIFO-volgorde**. Zo blijft de volgorde van bewerkingen behouden, ook wanneer meerdere threads tegelijk taken indienen. Dankzij deze garantie worden UI-updates precies in de volgorde toegepast waarin ze zijn ingediend. Als thread A dus taak 1 indient en thread B daarna taak 2, wordt taak 1 altijd vóór taak 2 uitgevoerd op de UI-thread. Verwerking in FIFO-volgorde voorkomt inconsistenties in de UI.

## Taken annuleren {#task-cancellation}

De <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> die `Environment.runLater()` retourneert, ondersteunt annulering. Zo kun je voorkomen dat taken in de wachtrij worden uitgevoerd. Door openstaande taken te annuleren, kun je geheugenlekken voorkomen en vermijden dat langdurige bewerkingen de UI bijwerken wanneer dat niet meer nodig is.

### Basisannulering {#basic-cancellation}

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

        // Bijhouden zodat ze eventueel kunnen worden geannuleerd
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

Annuleer alle openstaande updates wanneer componenten worden vernietigd (bijvoorbeeld tijdens navigatie), om geheugenlekken te voorkomen:

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

1. **Vereist doel**: Lees het doel-`Environment` uit met `Environment.getCurrent()` op de `Environment`-thread en geef het mee aan het achtergrondwerk. Als je het op een achtergrondthread uitleest, retourneert de methode `null`.

2. **Geheugenlekken voorkomen**: Houd `PendingResult`-objecten altijd bij en annuleer ze in lifecyclemethoden van componenten. Lambdas in de wachtrij leggen referenties naar UI-componenten vast. Als je ze niet annuleert, kunnen die componenten niet door de garbage collector worden opgeruimd.

3. **FIFO-uitvoering**: Alle taken worden in strikte FIFO-volgorde uitgevoerd, ongeacht hun belang. Er is geen prioriteitssysteem.

4. **Beperkingen van annulering**: Annulering voorkomt alleen dat taken in de wachtrij worden uitgevoerd. Taken die al worden uitgevoerd, worden normaal voltooid.

## Volledige casestudy: `LongTaskView` {#complete-case-study-longtaskview}

Hieronder staat een volledige, productieklare implementatie die alle aanbevolen werkwijzen voor asynchrone UI-updates demonstreert:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Gebruik één thread om uitputting van resources te voorkomen
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
          "Met de knop 'UI testen' kun je zien dat de UI tijdens de achtergrondbewerking responsief blijft.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultaat");
  private Button startButton = new Button("Langdurige taak starten");
  private Button cancelButton = new Button("Taak annuleren");
  private Button testButton = new Button("UI testen - klik hier!");
  private Paragraph footerPara = new Paragraph(
      "Opmerking: De taak kan op elk moment worden geannuleerd. Zo zie je hoe zowel de achtergrondthread als de " +
          "UI-updates in de wachtrij correct worden opgeruimd.");
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

    // Lopende taak en openstaande UI-updates annuleren
    cancelTask();

    // Verwijzing naar de taak wissen
    currentTask = null;

    // De executor van deze instantie netjes afsluiten
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Achtergrondtaak starten...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Annuleringsvlag opnieuw instellen en eerdere openstaande updates wissen
    isCancelled = false;
    pendingUIUpdates.clear();

    // Lees de doel-Environment uit op de UI-thread, voordat de achtergrondtaak begint
    Environment env = Environment.getCurrent();

    // Achtergrondtaak starten met een expliciete executor
    // Opmerking: cancel(true) onderbreekt de thread, waardoor Thread.sleep() een
    // InterruptedException gooit
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Een lange taak met 100 stappen simuleren
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
          // De thread is onderbroken: onmiddellijk afsluiten
          Thread.currentThread().interrupt(); // Onderbroken status herstellen
          return;
        }

        // Een berekening uitvoeren (deterministisch voor de demo)
        // Levert waarden tussen 0 en 1 op
        result += Math.sin(i) * 0.5 + 0.5;

        // Voortgang bijwerken vanuit de achtergrondthread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Bezig met verwerken... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Laatste update met resultaat (deze code wordt alleen bereikt als de taak is voltooid zonder
      // te zijn geannuleerd)
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
```

</ExpandableCode>

<VideoPlayer video="async/long-tasks" width={1080} height={1288} title="Long tasks" />

<!-- vale on -->

### Analyse van de casestudy {#case-study-analysis}

Deze implementatie laat verschillende belangrijke patronen zien:

#### 1. Threadpoolbeheer {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Gebruikt **één thread** om uitputting van resources te voorkomen
- Maakt **daemonthreads** aan die het afsluiten van de JVM niet verhinderen

#### 2. Het doel benoemen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` wordt uitgevoerd op de `Environment`-thread. Daarom leest de methode het doel daar uit en geeft de achtergrondtaak het mee aan elke aanroep van `Environment.runLater()`. Dezelfde code werkt ongewijzigd met een gedeelde threadpool voor de hele applicatie.

#### 3. Openstaande updates bijhouden {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Elke aanroep van `Environment.runLater()` wordt bijgehouden. Zo kun je:
- Annuleren wanneer de gebruiker op annuleren klikt
- Geheugenlekken voorkomen in `onDestroy()`
- Correct opruimen tijdens de lifecycle van een component

#### 4. Coöperatieve annulering {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
De achtergrondthread controleert deze vlag bij elke iteratie, zodat je:
- Onmiddellijk op annulering kunt reageren
- De lus netjes kunt verlaten
- Verdere UI-updates kunt voorkomen

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
- Lopende threads te onderbreken
- De executor af te sluiten

#### 6. De responsiviteit van de UI testen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klik #" + count + " - de UI reageert!", Theme.GRAY);
});
```
Laat zien dat de UI-thread responsief blijft tijdens achtergrondbewerkingen.
