---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: cbdf51a80355d73a6c7f5ec85cfa198a
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` -API tarjoaa mekanismin käyttöliittymän turvalliseen päivittämiseen taustasäikeistä webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot samalla pitäen käyttöliittymän muokkausten säikeiturvallisina.

<ExperimentalWarning />

## Ymmärtäminen säikeen mallista {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säikeiden mallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment`-säikeessä. Tämä rajoite johtuu siitä, että:

1. **webforJ API -rajoitukset**: Taustalla oleva webforJ API sidotaan säikeeseen, joka loi istunnon.
2. **Komponenttien säikesuhteet**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säikeen turvallista.
3. **Tapahtumien käsittely**: Kaikki käyttöliittymä tapahtumat käsitellään peräkkäin yhdellä säikeellä.

Tämä yksisäikeinen malli estää kilpailutilanteet ja ylläpitää johdonmukaista tilaa kaikille käyttöliittymäkomponenteille, mutta luo haasteita integroitaessa asynkronisia, pitkiä laskentatehtäviä.

## `RunLater` -API {#runlater-api}

`Environment.runLater()` -API tarjoaa kaksi menetelmää käyttöliittymän päivitysten aikatauluttamiseen:

```java title="Environment.java"
// Aikatauluta tehtävä ilman palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Aikatauluta tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -objektin, joka seuraa tehtävän valmistumista ja antaa pääsyn tulokseen tai mahdollisiin poikkeuksiin, jotka tapahtuivat.

## Säikeen kontekstiin periytyminen {#thread-context-inheritance}

Automaattinen kontekstitietoisuus on kriittinen ominaisuus `Environment.runLater()` -metodissa. Kun `Environment` -säikeessä toimiva säie luo lapsisäikeitä, nämä lapset perivät automaattisesti kyvyn käyttää `runLater()` -metodia.

### Kuinka periytyminen toimii {#how-inheritance-works}

Mikä tahansa säie, joka on luotu `Environment` -säikeessä, pääsee automaattisesti siihen `Environment`-kontekstiin. Tämä periytyminen tapahtuu automaattisesti, joten konteksin siirtämistä tai mitään konfigurointia ei tarvita.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Tämä säie saa Environment-kontekstin
    
    // Lapsisäikeet perivät kontekstin automaattisesti
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Voi käyttää runLateria, koska konteksti on peritty
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Säikeet ilman kontekstia {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()` -metodia, ja ne heittävät `IllegalStateException` -poikkeuksen:

```java
// Staattinen alustaja - ei Environment-kontekstia
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }).start();
}

// Järjestelmäajurit - ei Environment-kontekstia  
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

`runLater()` -metodin suoritus käyttäytyminen riippuu siitä, mikä säie sitä kutsuu:

### Käyttöliittymästä {#from-the-ui-thread}

Kun kutsutaan `Environment` -säikeeltä itseltään, tehtävät suoritetaan **synkronisesti ja heti**:

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

Tämän synkronisen käyttäytymisen avulla käyttöliittymän päivitykset tapahtuvat heti tapahtumakäsittelijöistä, eikä aiheuta turhaa jonottamista.

### Taustasäikeistä {#from-background-threads}

Kun kutsutaan taustasäikeestä, tehtävät **jonotetaan asynkronista suorittamista varten**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Tämä suoritetaan ForkJoinPool säikeessä
    System.out.println("Tausta: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Tämä suoritetaan Environment-säikeessä
      System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
      statusLabel.setText("Käsittely valmis");
    });
    
    // result.isDone() olisi false täällä
    // Tehtävä on jonossa ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetetyt tehtävät **tiukassa FIFO-järjestyksessä**, säilyttäen operaatioiden järjestyksen, vaikka ne olisi lähetetty useilta säikeiltä samanaikaisesti. Tämän järjestyksen takuurajaamisella käyttöliittymän päivitykset toteutuvat täsmälleen siinä järjestyksessä, jossa ne ovat lähetetty. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymässä. Tehtävien käsittely FIFO-järjestyksessä estää epäjohdonmukaisuudet käyttöliittymässä.

## Tehtävän peruuttaminen {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -objekti, joka palautetaan `Environment.runLater()` -metodista, tukee peruuttamista, jolloin voit estää jonossa olevien tehtävien suorittamisen. Peruuttamalla odottavat tehtävät, voit välttää muistivuotoja ja estää pitkäkestoisia operaatioita päivittämästä käyttöliittymää, kun niitä ei enää tarvita.

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

Pitkäkestoisia toimintoja, joissa on tiheitä käyttöliittymäpäivityksiä, suorittaessasi seuraa kaikkia odottavia tuloksia:

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
        
        // Seuraa mahdollista peruuttamista
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

Kun komponentteja tuhotaan (esim. navigoinnin aikana), peruuta kaikki odottavat päivitykset estääksesi muistivuotoja:

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

## Suunnittelupohdinnat {#design-considerations}

1. **Kontekstivaatimus**: Säikeiden on oltava perineet `Environment`-konteksti. Ulkoiset kirjastosäikeet, järjestelmäajurit ja staattiset alustajat eivät voi käyttää tätä APIa.

2. **Muistivuotojen estäminen**: Seuraa aina `PendingResult` -objekteja komponentin elinkaarimenetelmissä. Jonotetut lambdat kaappaavat viittauksia käyttöliittymäkomponentteihin, estäen roskakeräyksen, jos niitä ei peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä merkityksestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruuttamisen rajoitukset**: Peruuttaminen estää vain jonossa olevien tehtävien suorittamisen. Jo suorittavat tehtävät valmistuvat normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantovalmiiksi toteutus, joka osoittaa kaikki parhaat käytännöt asynkronisille käyttöliittymäpäivityksille:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säiettä estämään resurssin uupumista
  // Tuotannossa harkitse jakaa sovelluksen laajuinen säiettä
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
  private H2 titleLabel = new H2("Taustakäyttöliittymän päivitykset");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, kuinka Environment.runLater() mahdollistaa turvalliset käyttöliittymäpäivitykset taustasäikeistä. " +
          "Klikkaa 'Aloita pitkä tehtävä' suorittaa kymmenen sekunnin taustalaskentatehtävää, joka päivittää käyttöliittymän edistymistä. " +
          "‘Testi käyttöliittymä’ -painike todistaa käyttöliittymän pysyvän reagointikykyisen taustatehtävän aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peruuta tehtävä");
  private Button testButton = new Button("Testi käyttöliittymä - Klikkaa minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävän voi peruuttaa milloin tahansa, mikä osoittaa oikein siivouksen sekä " +
          "taustasäikeelle että jonotetuille käyttöliittymäpäivityksille.");
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
      showToast("Klikkaus #" + count + " - käyttöliittymä on reagoiva!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta mahdollinen käynnissä oleva tehtävä ja odottavat käyttöliittymäpäivitykset
    cancelTask();

    // Tyhjennä tehtäväviittaus
    currentTask = null;

    // Sammuta instanssin suorittaja rauhallisesti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävä...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutettu lippu ja tyhjennä aikaisemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Käynnistä taustatehtävä erikseen suorittajalta
    // Huom: cancel(true) keskeyttää säikeen, mistä Thread.sleep() heittää
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkään tehtävää 100 vaihetta
      for (int i = 0; i <= 100; i++) {
        // Tarkista, onko peruutettu
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Tehtävä peruutettu!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Tehtävää on peruutettu", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Yhteensä 10 sekuntia
        } catch (InterruptedException e) {
          // Säie keskeytettiin - poistu heti
          Thread.currentThread().interrupt(); // Palauta keskeytysohje
          return;
        }

        // Tee laskentaa (deterministinen demolle)
        // Tuottaa arvoja, jotka vaihtelevat 0 ja 1 välillä
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistystä taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsitellään... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Viimeinen päivitys tuloksella (tämä koodi saavutetaan vain, jos tehtävä päättyi ilman
      // peruuttamista)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tehtävä valmis!");
          resultField.setValue("Tulos: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Taustatehtävä on valmis!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Aseta peruutettu lippu
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

        showToast("Peruutus pyydettiin", Theme.GRAY);
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

Tämä toteutus osoittaa useita kriittisiä malleja:

#### 1. Säiettä hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Käyttää **yhden säikeen suorittajaa** estämään resurssien uupumista
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sammutusta

#### 2. Odottavien päivitysten seuraaminen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()` -kutsu seurataan, mikä mahdollistaa:
- Peruuttamisen, kun käyttäjä napsauttaa peruuta
- Muistivuotojen estämisen `onDestroy()`:ssa
- Oikean puhdistuksen komponentin elinkaaren aikana

#### 3. Yhteistyöperuuttaminen {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mikä mahdollistaa:
- Välittömän reagoinnin peruuttamiseen
- Siistin poistumisen silmukasta
- Lisäämällä käyttöliittymän päivityksiä

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
- Peruuta kaikki odottavat käyttöliittymäpäivitykset
- Keskeytä käynnissä olevat säikeet
- Sammuta suorittaja

#### 5. Käyttöliittymän reagointikyvyn testaus {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klikkaus #" + count + " - käyttöliittymä on reagoiva!", Theme.GRAY);
});
```
Todistaa, että käyttöliittymän säie pysyy reagoivana taustatoimintojen aikana.
