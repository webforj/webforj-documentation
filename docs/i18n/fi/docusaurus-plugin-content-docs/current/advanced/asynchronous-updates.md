---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynkroniset päivitykset
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 7fedd93e44d699bba0a959504c7bb40c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

`Environment.runLater()`-rajapinta tarjoaa webforJ-sovelluksissa mekanismin käyttöliittymän turvalliseen päivittämiseen taustasäikeistä. Tämä esikatseluominaisuus mahdollistaa asynkroniset toiminnot ja pitää samalla käyttöliittymän muutokset säieturvallisina.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Säiemallin ymmärtäminen {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säiemallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment`-säikeessä. Tämä rajoitus johtuu seuraavista syistä:

1. **webforJ-rajapinnan rajoitukset**: Taustalla oleva webforJ-rajapinta sidotaan istunnon luoneeseen säikeeseen.
2. **Komponenttien säiekohtaisuus**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säieturvallinen.
3. **Tapahtumien käsittely**: Kaikki käyttöliittymätapahtumat käsitellään peräkkäin yhdessä säikeessä.

Tämä yksisäikeinen malli estää kilpailutilanteet ja pitää kaikkien käyttöliittymäkomponenttien tilan yhdenmukaisena, mutta aiheuttaa haasteita integroitavaksi asynkronisiin, pitkään suoritettaviin laskentatehtäviin.

Jokaisella käyttäjäistunnolla on oma `Environment` ja oma `Environment`-säikeensä. Taustasäie ei kuulu mihinkään istuntoon, joten sille on kerrottava, mitä `Environment`-ympäristöä sen tulee päivittää.

## `RunLater`-rajapinta {#runlater-api}

`Environment.runLater()`-rajapinta tarjoaa kaksi tapaa käyttöliittymäpäivitysten ajoittamiseen. Molemmissa kohde-`Environment` annetaan ensimmäisenä argumenttina:

```java title="Environment.java"
// Ajoita tehtävä, jolla ei ole paluuarvoa
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Ajoita tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Molemmat metodit palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>-olion, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin.

:::warning[Kuormitetut metodit ilman kohdetta]
Ylikuormitetut metodit `runLater(Runnable)` ja `runLater(Supplier)` päättelevät kohteen kutsuvan säikeen luoneesta säikeestä. Usean käyttäjän kesken jaetussa säiepoolissa jokainen tehtävä suoritetaan sen käyttäjän `Environment`-ympäristössä, jonka kutsu loi poolisäikeen. Nämä ylikuormitukset on merkitty vanhentuneiksi versiosta 26.10 alkaen, ja ne on määrä poistaa seuraavassa pääversiossa. Anna kohde aina eksplisiittisesti.
:::

## Kohteen välittäminen {#passing-the-target}

`Environment.getCurrent()` palauttaa nykyisen säikeen `Environment`-ympäristön. `Environment`-säikeessä, jossa suoritetaan konstruktorit, tapahtumankäsittelijät ja elinkaarimetodit, se palauttaa istunnon `Environment`-ympäristön. Taustasäikeessä se palauttaa arvon `null`.

Lue kohde siellä, missä se on saatavilla, ennen taustatyön käynnistymistä, ja välitä se työlle:

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Konstruktori suoritetaan Environment-säikeessä
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

Koska kohde välitetään argumenttina, kutsu toimii mistä tahansa säikeestä: näkymän luomasta säikeestä, Springin `@Async`-säikeestä, yhteisestä `ForkJoinPool`-poolista, ajastinsäikeestä tai HTTP-asiakasohjelman takaisinkutsusäikeestä.

### Kohteen lukeminen väärässä säikeessä {#reading-the-target-on-the-wrong-thread}

Ainoa vältettävä virhe on `Environment.getCurrent()`-metodin lukeminen taustatyön sisällä. Siellä se palauttaa arvon `null`, ja `runLater()` heittää `NullPointerException`-poikkeuksen:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Väärin: getCurrent() palauttaa null-arvon taustasäikeessä
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Sama pätee staattisiin alustajiin, `java.util.Timer`-säikeisiin ja ulkoisten kirjastojen takaisinkutsuihin. Mikään niistä ei ole `Environment`-ympäristössä, joten kohteen on tultava kentästä, parametrina tai talteen otetusta paikallisesta muuttujasta, jonka arvo asetettiin `Environment`-säikeessä.

## Suorituskäyttäytyminen {#execution-behavior}

`runLater()`-metodin suorituskäyttäytyminen riippuu sitä kutsuvasta säikeestä:

### Käyttöliittymäsäikeestä {#from-the-ui-thread}

Kun metodia kutsutaan kohteen omasta `Environment`-säikeestä, tehtävät suoritetaan **synkronisesti ja välittömästi**:

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

Tämän synkronisen toiminnan ansiosta tapahtumankäsittelijöiden käyttöliittymäpäivitykset otetaan käyttöön välittömästi ilman tarpeetonta jonotuksen aiheuttamaa kuormitusta.

### Taustasäikeistä {#from-background-threads}

Kun metodia kutsutaan mistä tahansa muusta säikeestä, tehtävät **jonotetaan asynkronista suoritusta varten**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // Tämä suoritetaan ForkJoinPool-säikeessä
    System.out.println("Background: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // Tämä suoritetaan Environment-säikeessä
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Processing complete");
    });

    // result.isDone() palauttaisi tässä false
    // Tehtävä jonotetaan ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetetyt tehtävät **tiukassa FIFO-järjestyksessä** ja säilyttää toimintojen järjestyksen myös silloin, kun useat säikeet lähettävät tehtäviä samanaikaisesti. Tämän järjestystakuun ansiosta käyttöliittymäpäivitykset otetaan käyttöön täsmälleen siinä järjestyksessä, jossa ne lähetettiin. Jos siis säie A lähettää tehtävän 1 ja sen jälkeen säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymäsäikeessä. Tehtävien käsittely FIFO-järjestyksessä estää käyttöliittymän epäjohdonmukaisuudet.

## Tehtävien peruutus {#task-cancellation}

`Environment.runLater()`-metodin palauttama <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> tukee peruutusta, jonka avulla voit estää jonossa olevien tehtävien suorittamisen. Odottavien tehtävien peruminen auttaa välttämään muistivuotoja ja estää pitkään suoritettavia toimintoja päivittämästä käyttöliittymää sen jälkeen, kun päivityksiä ei enää tarvita.

### Peruutuksen perusteet {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Peru, jos suoritusta ei ole vielä aloitettu
if (!result.isDone()) {
  result.cancel();
}
```

### Useiden päivitysten hallinta {#managing-multiple-updates}

Kun suoritat pitkään kestäviä toimintoja ja päivität käyttöliittymää usein, seuraa kaikkia odottavia tuloksia:

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

        // Seurataan mahdollista peruutusta varten
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Peru kaikki odottavat käyttöliittymäpäivitykset
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Komponentin elinkaaren hallinta {#component-lifecycle-management}

Kun komponentit tuhotaan (esimerkiksi siirtymisen aikana), peru kaikki odottavat päivitykset muistivuotojen estämiseksi:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peru kaikki odottavat päivitykset muistivuotojen estämiseksi
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Suunnittelussa huomioitavaa {#design-considerations}

1. **Kohdevaatimus**: Kohde-`Environment` luetaan `Environment`-säikeessä `Environment.getCurrent()`-metodilla ja välitetään taustatyölle. Sen lukeminen taustasäikeessä palauttaa arvon `null`.

2. **Muistivuotojen estäminen**: Seuraa `PendingResult`-olioita ja peru ne aina komponentin elinkaarimetodeissa. Jonotetut lambda-lausekkeet säilyttävät viitteitä käyttöliittymäkomponentteihin, mikä estää niiden roskienkeruun, ellei niitä peruta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä niiden tärkeydestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruutuksen rajoitukset**: Peruutus estää vain jonotettujen tehtävien suorittamisen. Jo suoritettavat tehtävät valmistuvat normaalisti.

## Kattava esimerkki: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on kattava, tuotantokäyttöön valmis toteutus, joka havainnollistaa asynkronisten käyttöliittymäpäivitysten parhaita käytäntöjä:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yksisäikeistä suorittajaa resurssien ehtymisen estämiseksi
  // Tuotantokäytössä kannattaa harkita sovelluksenlaajuisesti jaettua säiepoolia
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Seuraa nykyistä tehtävää ja odottavia käyttöliittymäpäivityksiä
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Käyttöliittymäkomponentit
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

    // Määritä kentät
    statusField.setReadOnly(true);
    statusField.setValue("Ready to start");
    statusField.setLabel("Status");

    // Määritä edistymispalkki
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

    // Määritä painikkeet
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peru käynnissä oleva tehtävä ja odottavat käyttöliittymäpäivitykset
    cancelTask();

    // Tyhjennä tehtäväviite
    currentTask = null;

    // Sammuta instanssin suorittaja hallitusti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starting background task...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutuslippu ja tyhjennä aiemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Lue kohde-Environment käyttöliittymäsäikeessä ennen taustatehtävän käynnistymistä
    Environment env = Environment.getCurrent();

    // Käynnistä taustatehtävä eksplisiittisellä suorittajalla
    // Huomautus: cancel(true) keskeyttää säikeen, jolloin Thread.sleep() heittää
    // InterruptedException-poikkeuksen
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkää tehtävää 100 vaiheella
      for (int i = 0; i <= 100; i++) {
        // Tarkista, onko tehtävä peruttu
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
          Thread.sleep(100); // Yhteensä 10 sekuntia
        } catch (InterruptedException e) {
          // Säie keskeytettiin – lopeta heti
          Thread.currentThread().interrupt(); // Palauta keskeytystila
          return;
        }

        // Suorita laskutoimitus (esittelyä varten deterministinen)
        // Tuottaa arvoja väliltä 0–1
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistyminen taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Processing... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Viimeinen päivitys tuloksella (tähän koodiin päädytään vain, jos tehtävä valmistui ilman
      // peruutusta)
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
      // Aseta peruutuslippu
      isCancelled = true;

      // Peru päätehtävä (keskeyttää säikeen)
      currentTask.cancel(true);

      // Peru kaikki odottavat käyttöliittymäpäivitykset
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

<VideoPlayer video="async/long-tasks" width={1080} height={1288} title="Long tasks" />

<!-- vale on -->

### Esimerkin analyysi {#case-study-analysis}

Tämä toteutus havainnollistaa useita keskeisiä toimintamalleja:

#### 1. Säiepoolin hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Käyttää **yksisäikeistä suorittajaa** resurssien ehtymisen estämiseksi
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sammutusta

#### 2. Kohteen nimeäminen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` suoritetaan `Environment`-säikeessä, joten se lukee kohteen siellä ja taustatehtävä välittää sen jokaiselle `Environment.runLater()`-kutsulle. Sama koodi toimii sellaisenaan myös sovelluksenlaajuisessa jaetussa säiepoolissa.

#### 3. Odottavien päivitysten seuraaminen {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokaista `Environment.runLater()`-kutsua seurataan, jotta voidaan mahdollistaa:
- Peruutus käyttäjän painaessa peruutuspainiketta
- Muistivuotojen estäminen `onDestroy()`-metodissa
- Asianmukainen siivous komponentin elinkaaren aikana

#### 4. Yhteistoiminnallinen peruutus {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisella iteraatiolla, mikä mahdollistaa:
- Välittömän reagoinnin peruutukseen
- Siistin poistumisen silmukasta
- Uusien käyttöliittymäpäivitysten estämisen

#### 5. Elinkaaren hallinta {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Käyttää uudelleen peruutuslogiikkaa
  currentTask = null;
  executor.shutdown();
}
```
Tämä on tärkeää muistivuotojen estämiseksi seuraavin tavoin:
- Perutaan kaikki odottavat käyttöliittymäpäivitykset
- Keskeytetään käynnissä olevat säikeet
- Sammutetaan suorittaja

#### 6. Käyttöliittymän reagointikyvyn testaaminen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
Osoittaa, että käyttöliittymäsäie pysyy reagoivana taustatoimintojen aikana.
