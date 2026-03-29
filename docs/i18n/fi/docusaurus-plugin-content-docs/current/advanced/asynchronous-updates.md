---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: ead192e1c1a415742cb0446e2d5c314c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismin käyttöliittymän turvalliselle päivittämiselle taustateistä webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot samalla säilyttäen säieystävällisyyden käyttöliittymän muutoksille.

:::warning Kokeellinen API
Tätä API:a on merkitty kokeelliseksi 25.02:sta alkaen, ja se voi muuttua tulevissa julkaisuissa. API:n allekirjoitus, käyttäytyminen ja suorituskykyominaisuudet voivat muuttua.
:::

## Thread-mallin ymmärtäminen {#understanding-the-thread-model}

webforJ valvoo tiukkaa säiemallia, jossa kaikki käyttöliittymätoiminnot on suoritettava `Environment`-säikeessä. Tämä rajoitus johtuu seuraavista syistä:

1. **webforJ API -rajoitukset**: Perustuva webforJ API sitoutuu säikeeseen, joka luo istunnon.
2. **Komponentin säiekuuluvuus**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säieystävällistä.
3. **Tapahtuman käsittely**: Kaikki käyttöliittymästapahtumat käsitellään peräkkäin yhdessä säikeessä.

Tämä yksisäikeinen malli estää kilpailevat tilanteet ja ylläpitää vakaata tilaa kaikille käyttöliittymäkomponenteille, mutta se luo haasteita asynkronisten, pitkään kestäneiden laskentatehtävien integroinnissa.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää käyttöliittymän päivitysten aikatauluttamiseksi:

```java title="Environment.java"
// Aikatauluta tehtävä ilman palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Aikatauluta tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -objektin, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin.

## Säikeen kontekstin periytyminen {#thread-context-inheritance}

Automaattinen kontekstin periytyminen on kriittinen ominaisuus `Environment.runLater()`-metodissa. Kun `Environment`-säikeessä juoksee lapsisäikeitä, nämä lapset perivät automaattisesti kyvyn käyttää `runLater()`-metodia.

### Kuinka periytyminen toimii {#how-inheritance-works}

Mikä tahansa säie, joka luodaan `Environment`-säikeen sisällä, saa automaattisesti pääsyn kyseiseen `Environment`-konstantiin. Tämä periytyminen tapahtuu automaattisesti, joten sinun ei tarvitse siirtää kontekstia tai määrittää mitään.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Tämä säie omaa Environment -kontekstin
    
    // Lapsisäikeet perivät kontekstin automaattisesti
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Voi käyttää runLater koska konteksti on peritty
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Kontekstittomat säikeet {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()`-metodia, ja ne aiheuttavat `IllegalStateException`-poikkeuksen:

```java
// Staattinen alustaja - ei Environment -kontekstia
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }).start();
}

// Järjestelmän ajastinsäikeet - ei Environment -kontekstia  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }
}, 1000);

// Ulkoisen kirjaston säikeet - ei Environment -kontekstia
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  });
```

## Suoritus käyttäytyminen {#execution-behavior}

`runLater()`-metodin suorittaminen riippuu siitä, mikä säie kutsuu sitä:

### Käyttöliittymäsäikeestä {#from-the-ui-thread}

Kun kutsutaan `Environment`-säikeestä itsestään, tehtävät suoritetaan **synkronisesti ja heti**:

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

Tällä synkronisella käyttäytymisellä käyttöliittymäpäivitykset tapahtuvat heti tapahtumankäsittelijöistä ilman tarpeetonta jonottamista.

### Taustasäikeistä {#from-background-threads}

Kun kutsutaan taustasäikeestä, tehtävät **jonotetaan asynkronista suorittamista varten**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Tämä suoritetaan ForkJoinPool-säikeessä
    System.out.println("Taustalla: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Tämä suoritetaan Environment-säikeessä
      System.out.println("Käyttöliittymän päivitys: " + Thread.currentThread().getName());
      statusLabel.setText("Käsittely valmis");
    });
    
    // result.isDone() olisi tässä epätosi
    // Tehtävä on jonotettu ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetetyt tehtävät **tiukassa FIFO-järjestyksessä**, säilyttäen toimintojen järjestyksen, vaikka niitä lähetettäisiin samanaikaisesti useista säikeistä. Tämän järjestyksen takia käyttöliittymäpäivitykset sovelletaan täsmälleen siinä järjestyksessä, jossa ne on lähetetty. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymässä. Tehtävien käsittely FIFO-järjestyksessä estää epäjohdonmukaisuuksia käyttöliittymässä.

## Tehtävän peruuttaminen {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> -objekti, joka palautuu `Environment.runLater()`-metodista, tukee peruuttamista, mikä mahdollistaa jonotettujen tehtävien suorittamisen estämisen. Peruuttamalla odottavat tehtävät voit välttää muistivuotoja ja estää pitkään kestäviä operaatioita päivittämästä käyttöliittymää, kun niitä ei enää tarvita.

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

### useiden päivitysten hallinta {#managing-multiple-updates}

Kun suoritat pitkään kestäviä operaatioita, joilla on tiheitä käyttöliittymän päivityksiä, seuraa kaikkia odottavia tuloksia:

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
        
        // Seuraa mahdollisia peruutuksia
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

Kun komponentteja tuhotaan (esim. navigoinnin aikana), peruuta kaikki odottavat päivitykset estääksesi muistivuodot:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Peruuta kaikki odottavat päivitykset estääkseen muistivuodot
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Suunnitteluperiaatteet {#design-considerations}

1. **Kontekstivaatimus**: Säikeillä on oltava peritty `Environment`-konteksti. Ulkoisten kirjastojen säikeet, järjestelmäajastimet ja staattiset alustajat eivät voi käyttää tätä API:a.

2. **Muistivuotojen estämiseksi**: Seuraa aina `PendingResult`-objekteja komponentin elinkaaren menetelmissä. Jonotetut lambdat tallentavat viittauksia käyttöliittymäkomponentteihin, estäen roskakeräyksen, jos niitä ei peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä riippumatta tärkeydestä. Prioriteettijärjestelmää ei ole.

4. **Peruuttamisen rajoitukset**: Peruuttaminen estää vain jonon tehtävien suorittamisen. Jo suoritettavat tehtävät päättyvät normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantovalmiin toteutuksen esimerkki, joka demonstroi kaikkia parhaita käytäntöjä asynkronisissa käyttöliittymäpäivityksissä:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säieexecutor, jotta estät resurssien loppumisen
  // Tuotannossa harkitse käytettäväksi yhteistä sovellustasoista säiettä
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
  private H2 titleLabel = new H2("Taustapäivitykset käyttöliittymässä");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, miten Environment.runLater() mahdollistaa turvalliset käyttöliittymäpäivitykset taustasäikeistä. " +
          "Klikkaa 'Aloita pitkä tehtävä' suorittaaksesi 10 sekunnin taustalaskentatehtävän, joka päivittää käyttöliittymän edistymisen. " +
          "Testi käyttöliittymä -painike todistaa, että käyttöliittymä pysyy reagoivana taustatoiminnan aikana.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button(" Peruuta tehtävä");
  private Button testButton = new Button("Testi käyttöliittymä - Klikkaa minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom.: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa asianmukaisen siivouksen sekä " +
          "taustasäikeelle että jonotetuille käyttöliittymän päivityksille.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Määritä kentät
    statusField.setReadOnly(true);
    statusField.setValue("Valmis aloittamaan");
    statusField.setLabel("Status");

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

    // Peruuta kaikki käynnissä oleva tehtävä ja odottavat käyttöliittymän päivitykset
    cancelTask();

    // Tyhjennä tehtäväviittaus
    currentTask = null;

    // Samalla rauhoita instanssiexecutor
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävää...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutetun lippu ja tyhjennä edelliset odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Aloita taustatehtävä eksplicitisti
    // Huom.: cancel(true) keskeyttää säikeen, mikä saa Thread.sleep():n heittämään
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
          // Säie keskeytettiin - poistuu välittömästi
          Thread.currentThread().interrupt(); // Palauta keskeytysstatus
          return;
        }

        // Suorita joitakin laskelmia (deterministinen demolle)
        // Tuottaa arvoja 0:n ja 1:n välillä
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistyminen taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsitellään... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Lopullinen päivitys tuloksen kanssa (tämä koodi saavutetaan vain, jos tehtävä suoritettiin
      // ilman peruuttamista)
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

      // Peruuta kaikki odottavat käyttöliittymän päivitykset
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Peruuntumista ollaan käsittelemässä...");
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

Tämä toteutus esittelee useita kriittisiä kaavoja:

#### 1. Säieexecutorin hallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Käyttää **yksittäistä säieexecutor**-käyttäjää resurssien loppumisen estämiseksi
- Luodaan **daemon-säikeitä**, jotka eivät estä JVM:ää sulkeutumasta

#### 2. Odottavien päivitysten seuranta {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu seurataan mahdollistamaan:
- Peruuttaminen, kun käyttäjä napsauttaa peruuta
- Muistivuotojen estäminen `onDestroy()`-metodissa
- Oikea siivous komponentin elinkaaren aikana

#### 3. Yhteistyöperuutus {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämä lippu jokaisessa iteraatiossa, mahdollistaen:
- Välitön vastaus peruutukseen
- Siisti poistuminen silmukasta
- Lisäkäyttöliittymän päivitysten estäminen

#### 4. Elinkaaren hallinta {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Käytetään peruuttamislogiikkaa
  currentTask = null;
  executor.shutdown();
}
```
Kriittinen muistivuotojen estämiseksi:
- Peruuta kaikki odottavat käyttöliittymän päivitykset
- Keskeytä käynnissä olevat säikeet
- Sulje executor

#### 5. Käyttöliittymän reagoivuuden testaus {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Klikkaus #" + count + " - käyttöliittymä on reagoiva!", Theme.GRAY);
});
```
Osoittaa, että käyttöliittymä säie pysyy reagoivana taustatoimintojen aikana.
