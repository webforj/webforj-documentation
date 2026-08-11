---
sidebar_position: 55
title: Asynchronous Updates
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 1f53158dabc9d0270dfe80c1df5bb122
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismin käyttöliittymän turvalliselle päivittämiselle taustaprosesseista webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot säilyttäen samalla säie turvallisuuden käyttöliittymän muokkauksille.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Thread-mallin ymmärtäminen {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säiemallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment` säikeessä. Tämä rajoitus johtuu siitä, että:

1. **webforJ API -rajoitukset**: Alus webforJ API sitoutuu säikeeseen, joka loi istunnon
2. **Komponenttien säie affiniteetti**: käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säie turvallinen
3. **Tapahtumien käsittely**: Kaikki käyttöliittymä tapahtumat käsitellään peräkkäin yhdellä säikeellä

Tämä yksisäie malli estää kilpailutilanteita ja ylläpitää johdonmukaista tilaa kaikille käyttöliittymäkomponenteille, mutta aiheuttaa haasteita asynkronisten, pitkäkestoisten laskentatehtävien integroimisessa.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää käyttöliittymän päivitysten ajoittamiseen:

```java title="Environment.java"
// Aikatauluta tehtävä ilman palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Aikatauluta tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -objektin, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin.

## Säieyhteyden perintö {#thread-context-inheritance}

Automaattinen yhteyden perintö on kriittinen ominaisuus `Environment.runLater()`:ssä. Kun `Environment`-säikeessä oleva säie luo lapsisäikeitä, nämä lapset perivät automaattisesti kyvyn käyttää `runLater()`.

### Kuinka perintö toimii {#how-inheritance-works}

Mikä tahansa säie, joka luodaan `Environment`-säikeen sisällä, pääsee automaattisesti siihen `Environment`:iin. Tämä perintö tapahtuu automaattisesti, joten sinun ei tarvitse siirtää mitään kontekstia tai konfiguroida mitään.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // Tässä säikeessä on Environment-konteksti

    // Lapsisäikeet perivät kontekstin automaattisesti
    executor.submit(() -> {
      String data = fetchRemoteData();

      // Voit käyttää runLater, koska konteksti perittiin
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Kontekstittomat säikeet {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()` ja heitetään `IllegalStateException`:

```java
// Staattinen alustaja - ei Environment-kontekstia
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }).start();
}

// Järjestelmätimerisäikeet - ei Environment-kontekstia
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }
}, 1000);

// Ulkoiset kirjastosäikeet - ei Environment-kontekstia
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  });
```

## Suoritus käyttäytyminen {#execution-behavior}

`runLater()`-menetelmän suoritus käyttäytyminen riippuu siitä, mikä säie kutsuu sitä:

### Käyttöliittymäsäikeestä {#from-the-ui-thread}

Kun sitä kutsutaan `Environment`-säikeestä itsestään, tehtävät suoritetaan **synkronisesti ja heti**:

```java
button.onClick(e -> {
  System.out.println("Ennen: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Sisällä: " + Thread.currentThread().getName());
    return "valmis";
  });

  System.out.println("Jälkeen: " + result.isDone());  // true
});
```

Tämän synkronisen käyttäytymisen ansiosta käyttöliittymän päivitykset tapahtuvat heti tapahtumakäsittelijöistä eikä aiheuta tarpeetonta jonotusta.

### Taustasäikeistä {#from-background-threads}

Kun kutsutaan taustasäikeestä, tehtävät **jonotetaan asynkronista suorittamista varten**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Tämä suoritetaan ForkJoinPool -säikeessä
    System.out.println("Tausta: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(() -> {
      // Tämä suoritetaan Environment -säikeessä
      System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
      statusLabel.setText("Käsittely valmis");
    });

    // result.isDone() olisi false täällä
    // Tehtävä on jonotettu ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetettyjä tehtäviä **tiukka FIFO-järjestyksessä**, säilyttäen toimintojen järjestyksen, vaikka niitä lähetettäisiin useista säikeistä samalla. Tällä järjestyksen takuulla käyttöliittymän päivitykset tapahtuvat täsmälleen siinä järjestyksessä, jossa ne on lähetetty. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymässä. Tehtävien käsittely FIFO-järjestyksessä estää epäjohdonmukaisuuksia käyttöliittymässä.

## Tehtävän peruutus {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, jonka `Environment.runLater()` palauttaa, tukee peruutusta, jolloin voit estää jonotettujen tehtävien suorittamisen. Perumalla odottavat tehtävät voit välttää muistivuodot ja estää pitkäkestoisten toimintojen päivittämisen käyttöliittymään, kun niitä ei enää tarvita.

### Perusperuutus {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Peru, jos ei ole vielä suoritettu
if (!result.isDone()) {
  result.cancel();
}
```

### Useiden päivitysten hallinta {#managing-multiple-updates}

Kun suoritat pitkäkestoisia toimintoja, joissa on tiheä käyttöliittymäpäivitys, seuraa kaikkia odottavia tuloksia:

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

        // Seuraa mahdolliselle peruutukselle
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Peru kaikki odottavat käyttöliittymän päivitykset
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

Kun komponentit tuhotaan (esim. navigoinnin aikana), peru kaikki odottavat päivitykset estääkseen muistivuodot:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peru kaikki odottavat päivitykset estääkseen muistivuodot
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

1. **Kontekstivaatimus**: Säikeiden on oltava perineitä `Environment`-konteksti. Ulkoiset kirjastosäikeet, järjestelmätimerit ja staattiset alustajat eivät voi käyttää tätä API:a.

2. **Muistivuodon estäminen**: Seuraa aina ja peru `PendingResult`-objekteja komponenttien elinkaarimenettelyissä. Jonotetut lambdat napsauttavat viittauksia käyttöliittymäkomponentteihin, estäen roskankeruun, ellet peruuta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä merkityksestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruutuksen rajoitukset**: Peruuttaminen estää vain jonotettujen tehtävien suorittamisen. Jo suorittavat tehtävät suoritetaan normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantovalmius toteutus, joka demonstroi kaikkia parhaita käytäntöjä asynkronisille käyttöliittymän päivityksille:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säietehtävää estääksesi resurssien kulumisen
  // Tuotannossa harkitse yhteisen sovellustason säikeen käyttöä
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
  private H2 titleLabel = new H2("Taustakäyttöliittymän päivityksien esittely");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä esittely näyttää, kuinka Environment.runLater() mahdollistaa turvalliset käyttöliittymän päivitykset taustasäikeistä. " +
          "Napsauta 'Aloita pitkä tehtävä' ajaa 10 sekunnin pitkä taustalaskenta, joka päivittää käyttöliittymän edistymistä. " +
          "Napsauta 'Testaa käyttöliittymää' -painiketta osoittamaan, että käyttöliittymä pysyy responsiivisena taustatoiminnan aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulokset");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peru tehtävä");
  private Button testButton = new Button("Testaa käyttöliittymää - Napsauta minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa oikean puhdistuksen sekä taustasäikeestä että jonotetuista käyttöliittymäpäivityksistä.");
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
    resultField.setLabel("Tulokset");

    // Määritä painikkeet
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Napsautus #" + count + " - Käyttöliittymä on responsiivinen!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peru kaikki käynnissä oleva tehtävä ja odottavat käyttöliittymän päivitykset
    cancelTask();

    // Tyhjennä tehtäväviittaus
    currentTask = null;

    // Sulje instanssin täytäntöönpano rauhallisesti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävää...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutuslippu ja tyhjentää aikaisemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Aloita taustatehtävä erillisellä täytäntöönpanolla
    // Huom: cancel(true) keskeyttää säikeen, mikä saa Thread.sleep()-ilmoituksen heittämään
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkä tehtävä 100 vaiheessa
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
          // Säie keskeytettiin - poistuu heti
          Thread.currentThread().interrupt(); // Palauta keskeyttämisen tila
          return;
        }

        // Suorita jokin laskenta (deterministinen esittelyä varten)
        // Tuottaa arvoja väliin 0 ja 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistymistä taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsitellään... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Lopullinen päivitys tuloksen kanssa (tämä koodi saavutetaan vain, jos tehtävä valmistuisi ilman
      // peruutusta)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tehtävä valmis!");
          resultField.setValue("Tulokset: " + String.format("%.2f", finalResult));
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

      // Peru päätehtävä (keskeyttää säikeen)
      currentTask.cancel(true);

      // Peru kaikki odottavat käyttöliittymän päivitykset
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Peruutetaan tehtävää...");
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
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Tapaustutkimuksen analyysi {#case-study-analysis}

Tämä toteutus demonstroi useita kriittisiä malleja:

#### 1. Säietehtävän hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Käyttää **yksittäistä säietehtävää** estääkseen resurssien kulumisen
- Luo **daemon-säikeitä**, jotka eivät estä JVM:ää sulkeutumasta

#### 2. Odottavien päivitysten seuraaminen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu seurataan mahdollistamaan:
- Peruuttaminen, kun käyttäjä napsauttaa peruuta
- Muistivuodon estäminen `onDestroy()`:ssä
- Oikea puhdistus komponentin elinkaaren aikana

#### 3. Yhteistyöperuutus {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mikä mahdollistaa:
- Välittömät reaktiot peruutukseen
- Siisti poistuminen silmukasta
- Estää lisäkäyttöliittymän päivitykset

#### 4. Elinkaaren hallinta {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Uudelleenkäyttää peruutuslogiikan
  currentTask = null;
  executor.shutdown();
}
```
Kriittistä muistivuotojen estämiseksi:
- Peru kaikkia odottavia käyttöliittymän päivityksiä
- Keskeyttää käynnissä olevat säikeet
- Sulje täytäntöönpanot rauhallisesti

#### 5. Käyttöliittymän responsiivisuuden testaus {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Napsautus #" + count + " - Käyttöliittymä on responsiivinen!", Theme.GRAY);
});
```
Demonstroi, että käyttöliittymäsäie pysyy responsiivisena taustatoimien aikana.
