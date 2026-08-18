---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Asynchronous Updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: ee0d9acbd0ac4b9b04510636531eb49d
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismin käyttöliittymän turvalliseen päivittämiseen taustakelloista webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot samalla, kun se ylläpitää säikeen turvallisuutta käyttöliittymän muutoksille.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Ymmärtäminen säie mallista {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säiemallia, jossa kaikki käyttöliittymän toiminnot on suoritettava `Environment`-säikeessä. Tämä rajoitus on olemassa, koska:

1. **webforJ API:n rajoitukset**: Taustalla oleva webforJ API sitoo säikeen, joka loi istunnon
2. **Komponentin säie affiniteetti**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säie turvallinen
3. **Tapahtuman käsittely**: Kaikki käyttöliittymän tapahtumat käsitellään peräkkäin yhdessä säikeessä

Tämä yksisäikeinen malli estää kilpailutilanteita ja ylläpitää johdonmukaista tilaa kaikille käyttöliittymäkomponenteille, mutta luo haasteita, kun integroidaan asynkronisia, pitkäkestoisia laskentatehtäviä.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää käyttöliittymän päivitysten ajoittamiseen:

```java title="Environment.java"
// Ajoita tehtävä ilman palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Ajoita tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin.

## Säie kontekstin perintä {#thread-context-inheritance}

Automaattinen kontekstin perintä on kriittinen ominaisuus `Environment.runLater()`:ssa. Kun `Environment`:ssä toimiva säie luo lapsisäikeitä, nämä lapset perivät automaattisesti kyvyn käyttää `runLater()`.

### Kuinka perintä toimii {#how-inheritance-works}

Mikä tahansa säie, joka luodaan `Environment`-säieestä, saa automaattisesti pääsyn kyseiseen `Environment`:iin. Tämä perintä tapahtuu automaattisesti, joten sinun ei tarvitse siirtää mitään kontekstia tai konfiguroida mitään.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Tässä säikeessä on Environment-konteksti

    // Lastensäikeet perivät kontekstin automaattisesti
    executor.submit(() -> {
      String data = fetchRemoteData();

      // Voit käyttää runLateria, koska konteksti perittiin
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Säikeet ilman kontekstia {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()` ja heittävät `IllegalStateException`:

```java
// Staattinen alustaja - ei Environment-kontekstia
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }).start();
}

// Järjestelmän ajastinsäikeet - ei Environment-kontekstia
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }
}, 1000);

// Ulkoisen kirjaston säikeet - ei Environment-kontekstia
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  });
```

## Suoritus käyttäytyminen {#execution-behavior}

`runLater()` suoritus käyttäytyminen riippuu siitä, mikä säie sen kutsuu:

### Käyttäjäliittymäsäikeestä {#from-the-ui-thread}

Kun se kutsutaan `Environment`-säikeestä, tehtävät suoritetaan **synkronisesti ja heti**:

```java
button.onClick(e -> {
  System.out.println("Ennen: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Sisällä: " + Thread.currentThread().getName());
    return "valmis";
  });

  System.out.println("Jälkeen: " + result.isDone());  // tosi
});
```

Tämän synkronisen käyttäytymisen myötä käyttöliittymän päivitykset tapahtumankäsittelijöistä soveltuvat heti eivätkä aiheuta tarpeettomia jonottamisoverheadia.

### Taustasäikeistä {#from-background-threads}

Kun se kutsutaan taustasäikeestä, tehtävät ovat **jonossa asynkronista suorittamista varten**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Tämä toimii ForkJoinPoolin säikeessä
    System.out.println("Tausta: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(() -> {
      // Tämä toimii Environment-säikeessä
      System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
      statusLabel.setText("Käsittely valmis");
    });

    // result.isDone() olisi tässä epätosi
    // Tehtävä on jonossa ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetettyjä tehtäviä **tiukassa FIFO-järjestyksessä**, säilyttäen toimintojen sekvenssin jopa useista säikeistä samanaikaisesti lähetettyinä. Tämän järjestyksen takuumalla käyttöliittymän päivitykset sovelletaan tarkalleen siinä järjestyksessä, jossa ne lähetettiin. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymässä. Tehtävien käsittely FIFO-järjestyksessä estää käyttöliittymässä esiintyviä epäjohdonmukaisuuksia.

## Tehtävän peruutus {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, jonka `Environment.runLater()` palauttaa, tukee peruutusta, jolloin voit estää jonossa olevien tehtävien suorittamisen. Peruuttamalla odottamat tehtävät voit välttää muistivuotoja ja estää pitkäkestoisten operaatioiden päivittämisen käyttöliittymään niiden ollessa enää tarpeettomia.

### Perusperuutus {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Peruuta, jos ei ole vielä suoritettu
if (!result.isDone()) {
  result.cancel();
}
```

### Useiden päivitysten hallinta {#managing-multiple-updates}

Kun suoritat pitkäkestoisia operaatioita useilla tavoin käyttöliittymän päivityksillä, seuraa kaikkia odottavia tuloksia:

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

        // Seuraa mahdollista peruutusta
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Peruuta kaikki odottavat käyttöliittymän päivitykset
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

Kun komponentit tuhotaan (esim. navigoinnin aikana), peruuta kaikki odottavat päivitykset estääksesi muistivuodot:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta kaikki odottavat päivitykset estääksesi muistivuotoja
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Suunnittelun huomioita {#design-considerations}

1. **Kontekstivaatimus**: Säikeiden on oltava perineet `Environment`-konteksti. Ulkoisten kirjastojen säikeet, järjestelmäajastimet ja staattiset alustajat eivät voi käyttää tätä API:a.

2. **Muistivuotojen estäminen**: Seuraa aina ja peruuta `PendingResult`-objekteja komponentin elinkaarimenetelmissä. Jonossa olevat lambda-lausunnot kaappaavat viittauksia käyttöliittymäkomponentteihin, estäen roskankeruun, jos niitä ei peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä merkityksestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruutuksen rajoitukset**: Peruuttaminen estää vain jonossa olevien tehtävien suorittamisen. Jo käynnissä olevat tehtävät päättyvät normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantovalmiiksi toteutus, joka osoittaa kaikki parhaita käytäntöjä asynkronisille käyttöliittymän päivityksille:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säiettä estääksesi resurssien loppumisen
  // Tuotantoon harkitse käytettäväksi yhteistä sovellustason säieallasta
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Seuraa nykyistä tehtävää ja odottavia käyttöliittymän päivityksiä
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Käyttöliittymäkomponentit
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Taustalla olevat käyttöliittymäpäivitykset");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, kuinka Environment.runLater() mahdollistaa turvalliset käyttöliittymän päivitykset taustasäikeistä. " +
          "Napsauta 'Aloita pitkä tehtävä' suorittaaksesi 10 sekunnin taustalaskennan, joka päivittää käyttöliittymän edistymistä. " +
          "Testi käyttöliittymän painike todistaa, että käyttöliittymä pysyy reagoivana taustatoimintojen aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peruuta tehtävä");
  private Button testButton = new Button("Testi UI - Napsauta minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa asianmukaisen puhdistuksen sekä " +
          "taustasäikeelle että jonossa oleville käyttöliittymän päivityksille.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Määritä kentät
    statusField.setReadOnly(true);
    statusField.setValue("Valmis aloittamaan");
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
      showToast("Napsautus #" + count + " - käyttöliittymä on reagoiva!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta mahdollinen käynnissä oleva tehtävä ja odottavat käyttöliittymän päivitykset
    cancelTask();

    // Tyhjennä tehtäväreferenssi
    currentTask = null;

    // Sammuta instanssieditori kauniisti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävä...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutuslippu ja tyhjennä aikaisemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Aloita taustatehtävä erillisellä suorittajalla
    // Huom: cancel(true) keskeyttää säikeen, mikä aiheuttaa Thread.sleep():n heittävän
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkää tehtävää 100 vaiheessa
      for (int i = 0; i <= 100; i++) {
        // Tarkista, onko peruutettu
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
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
          Thread.sleep(100); // 10 sekuntia yhteensä
        } catch (InterruptedException e) {
          // Säie keskeytettiin - poistu välittömästi
          Thread.currentThread().interrupt(); // Palauta keskeytysstatus
          return;
        }

        // Suorita laskentaa (deterministinen demo)
        // Tuottaa arvoja välillä 0 ja 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistyminen taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsittely... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Viimeinen päivitys tuloksella (tätä koodia saavutetaan vain, jos tehtävä onnistui ilman
      // peruutusta)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tehtävä suoritettu!");
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

      // Peruuta kaikki odottavat käyttöliittymän päivitykset
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Peruuttaminen...");
        cancelButton.setEnabled(false);

        showToast("Peruutus pyydetty", Theme.GRAY);
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

Tämä toteutus osoittaa useita kriittisiä malleja:

#### 1. Säietehtäviin liittyvien hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Käyttää **yksisäietehtävää** resurssien loppumisen estämiseksi
- Luo **daemon säikeitä**, jotka eivät estä JVM:n sammuttamista

#### 2. Odottavien päivitysten seuranta {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu seurataan mahdollistamaan:
- Peruuttaminen, kun käyttäjä napsauttaa peruutusta
- Muistivuotojen estäminen `onDestroy()`:ssa
- Asianmukainen puhdistus komponentin elinkaaren aikana

#### 3. Yhteistyöperuutus {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mahdollistaen:
- Välitön vastaus peruutukseen
- Puhdas poistuminen silmukasta
- Lisäkäyttöliittymäpäivitysten estäminen

#### 4. Elinkaaren hallinta {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Uudelleenkäyttää peruuttamislogiikan
  currentTask = null;
  executor.shutdown();
}
```
Kriittinen muistivuotojen estämiseksi:
- Peruuttamalla kaikki odottavat käyttöliittymän päivitykset
- Keskeyttämällä käynnissä olevat säikeet
- Samalla suorittajalla

#### 5. Käyttöliittymän reagointikyvyn testaaminen {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Napsautus #" + count + " - käyttöliittymä on reagoiva!", Theme.GRAY);
});
```
Osoittaa, että käyttöliittymäsäie pysyy reagoivana taustatoimintojen aikana.
