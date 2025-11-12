---
sidebar_position: 46
title: Asynkroniset päivitykset
sidebar_class_name: new-content
_i18n_hash: 0db4be3f7e785c967b2e7efa442ca3ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismin käyttöliittymän turvalliseen päivittämiseen taustasäikeistä webforJ-sovelluksissa. Tämä kokeellinen toiminto mahdollistaa asynkroniset toiminnot samalla säilyttäen säiekohtaisen turvallisuuden käyttöliittymään liittyville muutoksille.

:::warning Kokeellinen API
Tämä API on merkitty kokeelliseksi 25.02 alkaen ja voi muuttua tulevissa julkaisuissa. API:n allekirjoitus, käyttäytyminen ja suorituskykyominaisuudet saattavat muuttua.
:::

## Säiemalli {#understanding-the-thread-model}

webforJ valvoo tiukkaa säiemallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment`-säikeessä. Tämä rajoitus on olemassa seuraavista syistä:

1. **webforJ API -rajoitukset**: Taustalla oleva webforJ API sitoutuu säikeeseen, joka loi istunnon.
2. **Komponenttien säidehtivät**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säiekohtaista.
3. **Tapahtumien käsittely**: Kaikki käyttöliittymätapahtumat käsitellään järjestyksessä yhdessä säikeessä.

Tämä yksisäikeinen malli estää kilpailutilanteet ja ylläpitää johdonmukaista tilaa kaikille käyttöliittymäkomponenteille, mutta luo haasteita asynkronisten, pitkäkestoisten laskentatehtävien integroimiseen.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää käyttöliittymän päivitysten ajoittamiseksi:

```java title="Environment.java"
// Ajoita tehtävä ilman palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Ajoita tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka seuraa tehtävän suorittamista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin, jotka ilmenivät.

## Säiedelegointi {#thread-context-inheritance}

Automaattinen kontekstin siirtäminen on kriittinen ominaisuus `Environment.runLater()`:ssa. Kun `Environment`-säikeessä luodaan lapsisäikeitä, nämä lapset periävät automaattisesti kyvyn käyttää `runLater()`.

### Kuinka perintä toimii {#how-inheritance-works}

Mikä tahansa säie, joka luodaan `Environment`-säikeestä, saa automaattisesti pääsyn siihen `Environment`:iin. Tämä perintä tapahtuu automaattisesti, joten sinun ei tarvitse siirtää mitään kontekstia tai konfiguroida mitään.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Tämä säie on Environment-kontekstissa
        
        // Lastensäikeet perivät kontekstin automaattisesti
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // Voidaan käyttää runLater, koska konteksti periytyi
            Environment.runLater(() -> {
                dataLabel.setText(data);
                loadingSpinner.setVisible(false);
            });
        });
    }
}
```

### Säikeet ilman kontekstia {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()`-metodia ja aiheuttavat `IllegalStateException`-virheen:

```java
// Staattinen inicialisoija - ei Environment-kontekstia
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

// Ulkoisten kirjasto-säikeet - ei Environment-kontekstia
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    });
```

## Suorituskyky {#execution-behavior}

`runLater()`-metodin suorituskyky riippuu siitä, mikä säie kutsuu sitä:

### Käyttöliittymässä {#from-the-ui-thread}

Kun kutsutaan `Environment`-säikeestä, tehtävät suoritetaan **synkronisesti ja välittömästi**:

```java
button.onClick(e -> {
    System.out.println("Ennen: " + Thread.currentThread().getName());
    
    PendingResult<String> result = Environment.runLater(() -> {
        System.out.println("Sisällä: " + Thread.currentThread().getName());
        return "suoritettu";
    });
    
    System.out.println("Jälkeen: " + result.isDone());  // true
});
```

Tämän synkronisen käyttäytymisen avulla käyttöliittymän päivitykset tapahtuvat tapahtumankäsittelijöistä välittömästi ilman tarpeetonta jonotusta.

### Taustasäikeistä {#from-background-threads}

Kun kutsutaan taustasäikeestä, tehtävät ovat **jonotettuina asynkroniseen suoritukseen**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Tämä suoritetaan ForkJoinPool-säikeessä
        System.out.println("Tausta: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Tämä suoritetaan Environment-säikeessä
            System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
            statusLabel.setText("Käsittely valmis");
        });
        
        // result.isDone() olisi false täällä
        // Tehtävä on jonotettu ja suoritetaan asynkronisesti
    });
}
```

webforJ käsittelee taustasäikeistä lähetettyjä tehtäviä **tiukassa FIFO-järjestyksessä**, säilyttäen operaatioiden järjestyksen myös silloin, kun niitä lähetetään samanaikaisesti useilta säikeiltä. Tämän järjestysvakuutuksen ansiosta käyttöliittymän päivitykset tapahtuvat tarkalleen siinä järjestyksessä, jossa ne lähetetään. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymässä. Tehtävien käsittely FIFO-järjestyksessä estää käyttöliittymässä esiintyviä epäjohdonmukaisuuksia.

## Tehtävän peruutus {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, jonka `Environment.runLater()` palauttaa, tukee perumista, mikä mahdollistaa jonotettujen tehtävien suorittamisen estämisen. Perumalla odottavat tehtävät voit välttää muistivuotoja ja estää pitkäkestoisten toimintojen päivittämistä käyttöliittymässä sen jälkeen, kun niitä ei enää tarvita.

### Perusperuutus {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Peru, jos ei vielä suoritettu
if (!result.isDone()) {
    result.cancel();
}
```

### Useiden päivitysten hallinta {#managing-multiple-updates}

Kun suoritat pitkäkestoisia toimintoja, joissa on usein käyttöliittymäpäivityksiä, seuraa kaikkia odottavia tuloksia:

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
                
                // Seuraa mahdollista perumista
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

Kun komponentteja tuhotaan (esimerkiksi navigoinnin aikana), peru kaikki odottavat päivitykset estääksesi muistivuodot:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Peru kaikki odottavat päivitykset estääksesi muistivuodot
        for (PendingResult<?> pending : pendingUpdates) {
            if (!pending.isDone()) {
                pending.cancel();
            }
        }
        pendingUpdates.clear();
    }
}
```

## Suunnittelun huomioitavat asiat {#design-considerations}

1. **Kontekstivaatimus**: Säikeiden on oltava perineet `Environment` konteksti. Ulkoisten kirjastojen säikeet, järjestelmäajastimet ja staattiset inicialisoijat eivät voi käyttää tätä APIa.

2. **Muistivuotojen estäminen**: Seuraa aina ja peru `PendingResult`-objekteja komponentin elinkaarimenetelmien aikana. Jonotetut lamdat tallentavat viittauksia käyttöliittymäkomponentteihin, mikä estää roskakeräyksen, jos niitä ei peruta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä riippumatta tärkeydestä. Prioriteettijärjestelmää ei ole.

4. **Peruutuksen rajoitukset**: Peruminen estää vain jonotettujen tehtävien suorittamisen. Jo suorittavat tehtävät loppuvat normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantokelpoinen toteutus, joka demonstroi kaikkia parhaita käytäntöjä asynkronisille käyttöliittymän päivityksille:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säietä estävää suoritusta resurssien loppumisen estämiseksi
  // Tuotannossa harkitse jaettua sovelluksen laajuista säikeen allasta
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
  private H2 titleLabel = new H2("Taustakuittauksia Demo");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, miten Environment.runLater() mahdollistaa turvallisten käyttöliittymäpäivitysten. " +
          "Napsauta 'Aloita pitkä tehtävä' suorittaaksesi 10 sekunnin taustakäsittely, joka päivittää käyttöliittymän edistystä. " +
          "Testaa 'Käyttöliittymä' -painiketta, jotta näet käyttöliittymän reagoivan taustatoiminnan aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulokset");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peru tehtävä");
  private Button testButton = new Button("Testaa käyttöliittymää - Napsauta minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa taustasäikeen ja " +
          "odottavien käyttöliittymäpäivityksien asianmukaisen puhdistuksen.");
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
      showToast("Klikkaus #" + count + " - Käyttöliittymä on reagoiva!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peru kaikki käynnissä oleva tehtävä ja odottavat käyttöliittymäpäivitykset
    cancelTask();

    // Tyhjennä tehtävän viittaus
    currentTask = null;

    // Sammuta instanssisäikeen asynkronisesti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävä...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruttu lippu ja tyhjennä edelliset odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Aloita taustatehtävä erikseen
    // Huom: cancel(true) keskeyttää säikeen, mikä aiheuttaa Thread.sleep() heittävän
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkä tehtävä 100 askelta
      for (int i = 0; i <= 100; i++) {
        // Tarkista, onko peruttu
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Tehtävä peruttu!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Tehtävä peruttiin", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Yhteensä 10 sekuntia
        } catch (InterruptedException e) {
          // Säie keskeytettiin - poistu välittömästi
          Thread.currentThread().interrupt(); // Palauta keskeytetty tila
          return;
        }

        // Tekee laskelman (deterministinen demon vuoksi)
        // Tuottaa arvoja välillä 0 ja 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistys taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsittely... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Lopullinen päivitys tuloksen kanssa (tätä koodia saavutetaan vain, jos tehtävä valmistui ilman
      // peruutusta)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("Tehtävä suoritettu!");
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
      // Aseta peruttu lippu
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
        statusField.setValue("Peruutetaan tehtävää...");
        cancelButton.setEnabled(false);

        showToast("Peruutuspyyntö tehty", Theme.GRAY);
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

#### 1. Säietä hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Käyttää **yksisäiettä** resurssien loppumisen estämiseksi
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sulkemista

#### 2. Odottavien päivitysten seuraaminen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu seurataan, jotta voidaan mahdollistaa:
- Peruminen, kun käyttäjä napsauttaa peruuta
- Muistivuotojen estäminen `onDestroy()`:ssä
- Oikea puhdistus komponentin elinkaaren aikana

#### 3. Yhteistyöperuutus {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mikä mahdollistaa:
- Välittömän reaktion peruutukseen
- Siistin lopettamisen silmukasta
- Estää lisäkäyttöliittymän päivityksiä 

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
Kriittinen muistivuotojen estämiseksi:
- Peru kaikki odottavat käyttöliittymäpäivitykset
- Keskeytä rinnakkaiset säikeet
- Sammuta säiepooli

#### 5. Käyttöliittymän reagointitestauksen {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klikkaus #" + count + " - Käyttöliittymä on reagoiva!", Theme.GRAY);
});
```
Demonstroi, että käyttöliittymässä on vielä reagointia taustatoimintojen aikana.
