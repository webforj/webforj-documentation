---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynkroniset päivitykset
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 96865999eae550e8c20172458695ee01
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

`Environment.runLater()`-rajapinta tarjoaa mekanismin käyttöliittymän turvalliseen päivittämiseen taustasäikeistä webforJ-sovelluksissa. Tämä esikatseluominaisuus mahdollistaa asynkroniset toiminnot säilyttäen samalla käyttöliittymän muutosten säieturvallisuuden.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Säiemallin ymmärtäminen {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säiemallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment`-säikeessä. Tämä rajoitus johtuu seuraavista syistä:

1. **webforJ-rajapinnan rajoitukset**: Taustalla oleva webforJ-rajapinta sitoutuu istunnon luoneeseen säikeeseen
2. **Komponenttien säieaffiniteetti**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säieturvallinen
3. **Tapahtumien käsittely**: Kaikki käyttöliittymätapahtumat käsitellään peräkkäin yhdessä säikeessä

Tämä yksisäikeinen malli estää kilpailutilanteita ja säilyttää kaikkien käyttöliittymäkomponenttien tilan johdonmukaisena, mutta aiheuttaa haasteita asynkronisten ja pitkään kestävien laskentatehtävien integroinnissa.

Jokaisella käyttäjäistunnolla on oma `Environment`-olionsa ja oma `Environment`-säikeensä. Taustasäie ei kuulu mihinkään istuntoon, joten sille on kerrottava, mitä `Environment`-oliota päivitetään.

## `RunLater`-rajapinta {#runlater-api}

`Environment.runLater()`-rajapinnassa on kaksi menetelmää käyttöliittymäpäivitysten ajoittamiseen. Molemmissa kohde-`Environment` annetaan ensimmäisenä argumenttina:

```java title="Environment.java"
// Ajoita tehtävä, joka ei palauta arvoa
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Ajoita tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>-olion, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin.

:::warning[Kohteettomat ylikuormitetut menetelmät]
Ylikuormitetut menetelmät `runLater(Runnable)` ja `runLater(Supplier)` päättelevät kohteen kutsuvan säikeen luoneen säikeen perusteella. Säiepoolissa, jota useat käyttäjät jakavat, jokainen tehtävä suoritetaan sen käyttäjän `Environment`-oliossa, jonka kutsu loi kyseisen poolisäikeen. Nämä ylikuormitetut menetelmät ovat vanhentuneita versiosta 26.10 alkaen, ja ne on määrä poistaa seuraavassa pääversiossa. Anna kohde aina eksplisiittisesti.
:::

## Kohteen välittäminen {#passing-the-target}

`Environment.getCurrent()` palauttaa nykyisen säikeen `Environment`-olion. `Environment`-säikeessä, jossa suoritetaan konstruktorit, tapahtumankäsittelijät ja elinkaarimenetelmät, se palauttaa istunnon `Environment`-olion. Taustasäikeessä se palauttaa arvon `null`.

Lue kohde siinä kohdassa, jossa se on saatavilla, ennen taustatyön käynnistymistä, ja välitä se työlle:

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

Koska kohde välitetään argumenttina, kutsu toimii mistä tahansa säikeestä: näkymän luomasta säikeestä, Springin `@Async`-säikeestä, yhteisestä `ForkJoinPool`-säikeestä, ajastinsäikeestä tai HTTP-asiakasohjelman takaisinkutsusäikeestä.

### Kohteen lukeminen väärässä säikeessä {#reading-the-target-on-the-wrong-thread}

Ainoa vältettävä virhe on `Environment.getCurrent()`-metodin kutsuminen taustatyön sisältä. Siellä se palauttaa arvon `null`, ja `runLater()` heittää `NullPointerException`-poikkeuksen:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Väärin: getCurrent() palauttaa taustasäikeessä arvon null
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Sama koskee staattisia alustajia, `java.util.Timer`-säikeitä ja ulkoisten kirjastojen takaisinkutsuja. Millään niistä ei ole `Environment`-oliota, joten kohteen on tultava kentästä, parametrina tai paikallisesta muuttujasta, jonka arvo on asetettu `Environment`-säikeessä.

## Suorituskäyttäytyminen {#execution-behavior}

`runLater()`-metodin suorituskäyttäytyminen riippuu sitä kutsuvasta säikeestä:

### Käyttöliittymäsäikeestä {#from-the-ui-thread}

Kun metodia kutsutaan kohteen omasta `Environment`-säikeestä, tehtävät suoritetaan **synkronisesti ja välittömästi**:

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Ennen: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Sisällä: " + Thread.currentThread().getName());
    return "completed";
  });

  System.out.println("Jälkeen: " + result.isDone());  // true
});
```

Tämän synkronisen toimintatavan ansiosta tapahtumankäsittelijöiden käyttöliittymäpäivitykset otetaan käyttöön välittömästi ilman tarpeetonta jonotuksen yleiskustannusta.

### Taustasäikeistä {#from-background-threads}

Kun metodia kutsutaan mistä tahansa muusta säikeestä, tehtävät **jonotetaan suoritettaviksi asynkronisesti**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // Tämä suoritetaan ForkJoinPool-säikeessä
    System.out.println("Tausta: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // Tämä suoritetaan Environment-säikeessä
      System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
      statusLabel.setText("Käsittely valmis");
    });

    // result.isDone() palauttaisi tässä arvon false
    // Tehtävä on jonossa ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetetyt tehtävät **tiukassa FIFO-järjestyksessä** ja säilyttää toimintojen järjestyksen, vaikka tehtäviä lähetettäisiin samanaikaisesti useista säikeistä. Tämän järjestystakuun ansiosta käyttöliittymäpäivitykset otetaan käyttöön täsmälleen siinä järjestyksessä, jossa ne lähetettiin. Jos siis säie A lähettää tehtävän 1 ja sen jälkeen säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymäsäikeessä. Tehtävien käsittely FIFO-järjestyksessä estää käyttöliittymän epäjohdonmukaisuuksia.

## Tehtävän peruuttaminen {#task-cancellation}

`Environment.runLater()`-metodin palauttama <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> tukee peruuttamista, jonka avulla voit estää jonossa olevien tehtävien suorittamisen. Odottavien tehtävien peruuttaminen auttaa välttämään muistivuotoja ja estää tarpeettomiksi käyneitä pitkäkestoisia toimintoja päivittämästä käyttöliittymää.

### Peruutuksen perusteet {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Peruuta, jos tehtävää ei ole vielä suoritettu
if (!result.isDone()) {
  result.cancel();
}
```

### Useiden päivitysten hallinta {#managing-multiple-updates}

Kun suoritat pitkäkestoisia toimintoja, joihin liittyy tiheitä käyttöliittymäpäivityksiä, seuraa kaikkia odottavia tuloksia:

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

        // Seurataan mahdollista peruuttamista varten
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Peruuta kaikki odottavat käyttöliittymäpäivitykset
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

Kun komponentit tuhotaan (esimerkiksi siirtymisen yhteydessä), peruuta kaikki odottavat päivitykset muistivuotojen estämiseksi:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta kaikki odottavat päivitykset muistivuotojen estämiseksi
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Suunnittelunäkökohdat {#design-considerations}

1. **Kohdevaatimus**: Kohde-`Environment` luetaan `Environment.getCurrent()`-metodilla `Environment`-säikeessä ja välitetään taustatyölle. Sen lukeminen taustasäikeessä palauttaa arvon `null`.

2. **Muistivuotojen estäminen**: Seuraa `PendingResult`-olioita ja peruuta ne aina komponentin elinkaarimenetelmissä. Jonossa olevat lambdat säilyttävät viittauksia käyttöliittymäkomponentteihin, mikä estää roskienkeruun, jos niitä ei peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä tärkeydestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruutuksen rajoitukset**: Peruuttaminen estää vain jonossa olevien tehtävien suorittamisen. Jo suorituksessa olevat tehtävät suoritetaan normaalisti loppuun.

## Kattava tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on tuotantokäyttöön valmis toteutus, joka havainnollistaa asynkronisten käyttöliittymäpäivitysten parhaita käytäntöjä:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yksisäikeistä suorittajaa resurssien ehtymisen estämiseksi
  // Tuotantokäytössä kannattaa harkita koko sovelluksen yhteistä säiepoolia
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
  private H2 titleLabel = new H2("Taustasäikeen käyttöliittymäpäivitysten esimerkki");
  private Paragraph descriptionPara = new Paragraph(
      "Tässä esimerkissä näytetään, miten Environment.runLater() mahdollistaa turvalliset käyttöliittymäpäivitykset taustasäikeistä. " +
          "Käynnistä 10 sekunnin taustalaskenta, joka päivittää käyttöliittymän edistymistä, napsauttamalla 'Käynnistä pitkä tehtävä'. " +
          "'Testaa käyttöliittymää' -painike osoittaa, että käyttöliittymä pysyy responsiivisena taustatoiminnon aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Käynnistä pitkä tehtävä");
  private Button cancelButton = new Button("Peruuta tehtävä");
  private Button testButton = new Button("Testaa käyttöliittymää – napsauta tästä!");
  private Paragraph footerPara = new Paragraph(
      "Huomautus: Tehtävän voi peruuttaa milloin tahansa. Näin havainnollistetaan taustasäikeen ja jonossa olevien " +
          "käyttöliittymäpäivitysten asianmukaista siivoamista.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Määritä kentät
    statusField.setReadOnly(true);
    statusField.setValue("Valmis aloitettavaksi");
    statusField.setLabel("Tila");

    // Määritä edistymispalkki
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Edistyminen: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Tulos");

    // Määritä painikkeet
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Napsautus #" + count + " – käyttöliittymä toimii!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta käynnissä oleva tehtävä ja odottavat käyttöliittymäpäivitykset
    cancelTask();

    // Tyhjennä tehtävän viittaus
    currentTask = null;

    // Sammuta tämän ilmentymän suorittaja hallitusti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Käynnistetään taustatehtävää...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutuslippu ja tyhjennä aiemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Lue kohde-Environment käyttöliittymäsäikeessä ennen taustatehtävän käynnistymistä
    Environment env = Environment.getCurrent();

    // Käynnistä taustatehtävä määritellyllä suorittajalla
    // Huomautus: cancel(true) keskeyttää säikeen, jolloin Thread.sleep() heittää
    // InterruptedException-poikkeuksen
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkää tehtävää 100 vaiheella
      for (int i = 0; i <= 100; i++) {
        // Tarkista, onko tehtävä peruutettu
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
            statusField.setValue("Tehtävä peruutettu!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Tehtävä peruutettiin", Theme.GRAY);
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

        // Suorita laskutoimitus (esimerkissä deterministinen)
        // Tuottaa arvoja väliltä 0–1
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistyminen taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsitellään... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Lopullinen päivitys tuloksen kanssa (tähän koodiin päädytään vain, jos tehtävä valmistui
      // ilman peruuttamista)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
          statusField.setValue("Tehtävä valmis!");
          resultField.setValue("Tulos: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Taustatehtävä valmis!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Aseta peruutuslippu
      isCancelled = true;

      // Peruuta päätehtävä (keskeyttää säikeen)
      currentTask.cancel(true);

      // Peruuta kaikki odottavat käyttöliittymäpäivitykset
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Peruutetaan tehtävää...");
        cancelButton.setEnabled(false);

        showToast("Peruutuspyyntö lähetetty", Theme.GRAY);
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

### Tapaustutkimuksen analyysi {#case-study-analysis}

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
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sammuttamista

#### 2. Kohteen nimeäminen {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` suoritetaan `Environment`-säikeessä, joten se lukee kohteen siellä ja taustatehtävä välittää sen jokaiselle `Environment.runLater()`-kutsulle. Sama koodi toimii muuttamattomana koko sovelluksen yhteisessä säiepoolissa.

#### 3. Odottavien päivitysten seuraaminen {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu pidetään tallessa, jotta voidaan mahdollistaa:
- Peruuttaminen, kun käyttäjä napsauttaa peruutuspainiketta
- Muistivuotojen estäminen `onDestroy()`-metodissa
- Asianmukainen siivous komponentin elinkaaren aikana

#### 4. Yhteistoiminnallinen peruuttaminen {#4-cooperative-cancellation}
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
  cancelTask();  // Käyttää peruutuslogiikkaa uudelleen
  currentTask = null;
  executor.shutdown();
}
```
Olennaista muistivuotojen estämiseksi:
- Peruuttamalla kaikki odottavat käyttöliittymäpäivitykset
- Keskeyttämällä käynnissä olevat säikeet
- Sammuttamalla suorittajan

#### 6. Käyttöliittymän responsiivisuuden testaaminen {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Napsautus #" + count + " – käyttöliittymä toimii!", Theme.GRAY);
});
```
Osoittaa, että käyttöliittymäsäie pysyy responsiivisena taustatoimintojen aikana.
