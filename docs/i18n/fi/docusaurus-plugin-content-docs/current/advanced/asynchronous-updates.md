---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: a426166aa63471b0d9d84e6c4786c6db
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismins turvallisesti päivittää käyttöliittymää taustasäikeistä webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot säilyttäen silmukkaturvallisuuden käyttöliittymän muutoksille.

:::warning Kokeellinen API
Tämä API on merkitty kokeelliseksi 25.02:sta lähtien ja saattaa muuttua tulevissa julkaisuissa. API-allekirjoitus, käyttäytyminen ja suorituskyky ovat muutosten kohteena.
:::

## Thread-mallin ymmärtäminen {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säikeistämismallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment`-säikeessä. Tämä rajoitus on olemassa seuraavista syistä:

1. **webforJ API -rajoitukset**: Taustalla oleva webforJ API sitoo säikeen, joka loi istunnon.
2. **Komponentti-säikeen kiinnittyminen**: Käyttöliittymäkomponentit säilyttävät tilan, joka ei ole silmukkaturvallinen.
3. **Tapahtumien käsittely**: Kaikki käyttöliittymä tapahtumat käsitellään peräkkäin yhdellä säikeellä.

Tämä yksisäikeinen malli estää kilpailuolosuhteet ja ylläpitää yhdenmukaista tilaa kaikille käyttöliittymäkomponenteille, mutta aiheuttaa haasteita asynkronisten, pitkäkestoisten laskentatehtävien integroimisessa.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää käyttöliittymän päivitysten ajoittamiseen:

```java title="Environment.java"
// Aikatauluta tehtävä, jolla ei ole palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Aikatauluta tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin, jotka ovat tapahtuneet.

## Säikeen kontekstin periminen {#thread-context-inheritance}

Automaattinen kontekstin periminen on kriittinen ominaisuus `Environment.runLater()`-menetelmässä. Kun `Environment`-säikeessä toimiva säie luo lapsisäikeitä, nämä lapset perivät automaattisesti mahdollisuuden käyttää `runLater()`-menetelmää.

### Kuinka perintä toimii {#how-inheritance-works}

Mikä tahansa säie, joka luodaan `Environment`-säikeestä, saa automaattisesti käyttöönsä kyseisen `Environment`-kontekstin. Tämä perintä tapahtuu automaattisesti, joten sinun ei tarvitse siirtää mitään kontekstia tai konfiguroida mitään erikseen.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Tämä säie on `Environment`-kontekstissa
        
        // Lapsisäikeet perivät kontekstin automaattisesti
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // Voidaan käyttää runLateria, koska konteksti perittiin
            Environment.runLater(() -> {
                dataLabel.setText(data);
                loadingSpinner.setVisible(false);
            });
        });
    }
}
```

### Kontekstittomat säikeet {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()`-menetelmää, ja ne heittävät `IllegalStateException`-poikkeuksen:

```java
// Staattinen alustaja - ei `Environment`-kontekstia
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    }).start();
}

//järjestelmätaimerisäikeet - ei `Environment`-kontekstia  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    }
}, 1000);

// Ulkopuoliset kirjastosäikeet - ei `Environment`-kontekstia
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    });
```

## Suorituskykykäyttäytyminen {#execution-behavior}

`runLater()`-menetelmän käyttäytyminen riippuu siitä, mikä säie kutsuu sitä:

### Käyttöliittymäsäikeestä {#from-the-ui-thread}

Kun sitä kutsutaan `Environment`-säikeestä itsestään, tehtävät suorituvat **mukautuvasti ja välittömästi**:

```java
button.onClick(e -> {
    System.out.println("Ennen: " + Thread.currentThread().getName());
    
    PendingResult<String> result = Environment.runLater(() -> {
        System.out.println("Sisällä: " + Thread.currentThread().getName());
        return "valmistunut";
    });
    
    System.out.println("Jälkeen: " + result.isDone());  // true
});
```

Tämän mukautuvan käyttäytymisen ansiosta käyttöliittymäpäivitykset tapahtuvat heti ja eivät aiheuta tarpeettomia jonotustilanteita.

### Taustasäikeistä {#from-background-threads}

Kun sitä kutsutaan taustasäikeestä, tehtävät **jonotetaan asynkronista suorittamista varten**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Tämä toimii ForkJoinPool -säikeessä
        System.out.println("Taustat: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Tämä toimii Environment -säikeessä
            System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
            statusLabel.setText("Käsittely valmis");
        });
        
        // result.isDone() olisi false täällä
        // Tehtävä on jonotettu ja suoritetaan asynkronisesti
    });
}
```

webforJ käsittelee taustasäikeistä lähetettyjä tehtäviä **tiukassa FIFO-järjestyksessä**, säilyttäen toimintojen sekvenssin, vaikka ne lähetettäisiin samanaikaisesti useista säikeistä. Tämän järjestämistakuun ansiosta käyttöliittymäpäivitykset sovelletaan tarkalleen siinä järjestyksessä, jossa ne lähetettiin. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymäsäikeessä. FIFO-järjestyksessä käsitteleminen estää epäjohdonmukaisuudet käyttöliittymässä.

## Tehtävän peruuttaminen {#task-cancellation}

`Environment.runLater()`-menetelmästä palautettu <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> tukee peruuttamista, jolloin voit estää jonossa olevien tehtävien suorittamisen. Peruuttamalla odottavat tehtävät voit välttää muistivuotoja ja estää pitkäkestoiset toiminnot päivittämästä käyttöliittymää, kun niitä ei enää tarvita.

### Perusperuuttaminen {#basic-cancellation}

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

Kun suoritetaan pitkäkestoisia operaatioita, joissa on tiheitä käyttöliittymäpäivityksiä, seuraa kaikkia odottavia tuloksia:

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
                
                // Seuraa mahdollisia peruuttamisia
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

Kun komponentit tuhotaan (esim. navigoinnin aikana), peruuta kaikki odottavat päivitykset estäaksesi muistivuodot:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Peruuta kaikki odottavat päivitykset estääksemme muistivuodot
        for (PendingResult<?> pending : pendingUpdates) {
            if (!pending.isDone()) {
                pending.cancel();
            }
        }
        pendingUpdates.clear();
    }
}
```

## Suunnitteluperusteet {#design-considerations}

1. **Kontekstivaatimus**: Säikeiden on oltava perineet `Environment`-konteksti. Ulkopuoliset kirjastosäikeet, järjestelmäajastimet ja staattiset alustajat eivät voi käyttää tätä APIa.

2. **Muistivuotojen ehkäisy**: Aina seuraa ja peruuta `PendingResult`-objekteja komponentin elinkaarimenetelmissä. Jonotetut lambdat tallentavat viittauksia käyttöliittymäkomponentteihin, estäen jätteiden keräyksen, jos niitä ei peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä riippumatta tärkeydestä. Prioriteettijärjestelmää ei ole.

4. **Peruutusrajoitukset**: Peruuttaminen estää vain jonottamattomien tehtävien suorittamisen. Jo käynnissä olevat tehtävät suoritetaan normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantovalmiusimplementaatio, joka esittelee kaikki parhaita käytäntöjä asynkronisten käyttöliittymäpäivitysten osalta:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säiettä estääkseen resurssien ehtymisen
  // Tuotannossa harkitse jaettua sovelluksen laajuista säiettä
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
  private FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Taustakäyttöliittymäpäivitykset -demo");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, kuinka Environment.runLater() mahdollistaa turvalliset käyttöliittymäpäivitykset taustasäikeistä. " +
          "Napsauta 'Aloita pitkä tehtävä' suorittaaksesi 10 sekunnin taustalaskennan, joka päivittää käyttöliittymän edistyksen. " +
          " 'Testi käyttöliittymä' -painike todistaa, että käyttöliittymä pysyy responsiivisena taustatoiminnon aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peruuta tehtävä");
  private Button testButton = new Button("Testi käyttöliittymä - Napsauta minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa, että sekä " +
          "taustasäie että jonotetut käyttöliittymän päivitykset puhdistuvat oikein.");
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

    // Määritä kaavio
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
      showToast("Napsauta #" + count + " - Käyttöliittymä on responsiivinen!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta kaikki käynnissä oleva tehtävä ja odottavat käyttöliittymäpäivitykset
    cancelTask();

    // Tyhjentää tehtäväviittaus
    currentTask = null;

    // Sulje instanssin säie suunnitelmallisesti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävä...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutettu lippu ja tyhjennä aiemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Käynnistä taustatehtävä selkeästi
    // Huom: cancel(true) keskeyttää säikeen, mikä aiheuttaa Thread.sleep():n heittämisen
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
          // Säie keskeytettiin - poistuu heti
          Thread.currentThread().interrupt(); // Palauta keskeytystila
          return;
        }

        // Suorita jonkin laskennan (deterministinen demossa)
        // Tuottaa arvoja, jotka ovat välillä 0 ja 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistys taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsitellään... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Viimeinen päivitys tuloksella (tämä koodi saavutetaan vain, jos tehtävä onnistui ilman
      // peruutuksia)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
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
`}

</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Tapaustutkimuksen analyysi {#case-study-analysis}

Tämä implementaatio esittelee useita kriittisiä malleja:

#### 1. Säieallokoinnin hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Käyttää **yksisäikeistä allokointia** estääkseen resurssien ehtymisen.
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sulkemista.

#### 2. Odottavien päivitysten seuraaminen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu on seurattava mahdollistamaan:
- Peruuttaminen, kun käyttäjä napsauttaa peruuta
- Muistivuotota estämistä `onDestroy()`-menetelmässä
- Oikea puhdistus komponentin elinkaaren aikana

#### 3. Yhteistyöperuutus {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mahdollistaen:
- Välitön reagointi peruuttamiseen
- Siisti poistuminen silmukasta
- Estää edelleen käyttöliittymäpäivitykset

#### 4. Ikäryhmän hallinta {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // Hyödynnä peruuttamislogiikkaa
    currentTask = null;
    executor.shutdown();
}
```
Kriittinen muistivuotojen estämiseksi:
- Peruuta kaikki odottavat käyttöliittymäpäivitykset
- Keskeytä käynnissä olevat säikeet
- Sulje allokointi

#### 5. Käyttöliittymän reagointikokeilu {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Napsauta #" + count + " - Käyttöliittymä on responsiivinen!", Theme.GRAY);
});
```
Todistaa, että käyttöliittymäsäie pysyy responsiivisena taustatoimintojen aikana.
