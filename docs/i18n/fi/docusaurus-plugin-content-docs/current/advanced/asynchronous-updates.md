---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 9ea7ae8b53ce19e2fee19e72929c732e
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismin, jonka avulla UI:ta voidaan turvallisesti päivittää taustatehtävistä webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot säilyttäen samalla säikeiden turvallisuuden UI-muutoksille.

:::warning Kokeellinen API
Tätä APIa pidetään kokeellisena 25.02 versiosta alkaen, ja se saattaa muuttua tulevissa versioissa. API:n allekirjoitus, toiminta ja suorituskykyominaisuudet voivat muuttua.
:::

## Säie-mallin ymmärtäminen {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säiemallia, jossa kaikki UI-toiminnot on suoritettava `Environment`-säikeellä. Tämä rajoitus johtuu siitä, että:

1. **webforJ API-rajoitukset**: Perimmäinen webforJ API sitoutuu säikeeseen, joka loi istunnon
2. **Komponenttien säietuki**: UI-komponentit ylläpitävät tilaa, joka ei ole säieturvallinen
3. **Tapahtumien käsittely**: Kaikki UI-tapahtumat käsitellään peräkkäin yhdellä säikeellä

Tämä yksisäikeinen malli estää kilpailuolosuhteet ja ylläpitää johdonmukaista tilaa kaikille UI-komponenteille, mutta aiheuttaa haasteita asynkronisten, pitkiä laskentatehtäviä integroitaessa.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää UI-päivitysten aikatauluttamiseksi:

```java title="Environment.java"
// Aikatauluta tehtävä ilman paluuarvoa
public static PendingResult<Void> runLater(Runnable task)

// Aikatauluta tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>:n, joka seuraa tehtävän valmistumista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin, jotka tapahtuivat.

## Säiekonteksti-inhenesis {#thread-context-inheritance}

Automaattinen konteksti-inhenesis on kriittinen ominaisuus `Environment.runLater()`:ssa. Kun `Environment`-säikeessä luodaan lapsisäikeitä, ne perivät automaattisesti kyvyn käyttää `runLater()`:a.

### Kuinka periminen toimii {#how-inheritance-works}

Mikään säie, joka on luotu `Environment`-säikeen sisällä, ei tarvitse passata kontekstia tai konfiguroida mitään.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Tämä säie sisältää Environment-kontekstin
        
        // Lapsisäikeet perivät kontekstin automaattisesti
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // Voi käyttää runLater, koska konteksti periytyi
            Environment.runLater(() -> {
                dataLabel.setText(data);
                loadingSpinner.setVisible(false);
            });
        });
    }
}
```

### Säikeet ilman kontekstiä {#threads-without-context}

Säikeet, jotka on luotu `Environment`-kontekstin ulkopuolella, eivät voi käyttää `runLater()`:a ja heittävät `IllegalStateException`:n:

```java
// Statinen alustus - ei Environment-kontekstia
static {
    new Thread(() -> {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    }).start();
}

// Järjestelmäajastimen säikeet - ei Environment-kontekstia  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    public void run() {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    }
}, 1000);

// Ulkoisten kirjastojen säikeet - ei Environment-kontekstia
httpClient.sendAsync(request, responseHandler)
    .thenAccept(response -> {
        Environment.runLater(() -> {});  // Heittää IllegalStateException
    });
```

## Suoritus käyttäytyminen {#execution-behavior}

`runLater()`-menetelmän suorituskyky riippuu siitä, mikä säie kutsuu sen:

### UI-säikeestä {#from-the-ui-thread}

Kun sitä kutsutaan suoraan `Environment`-säikeestä, tehtävät suoritetaan **synkronisesti ja välittömästi**:

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

Tämän synkronisen käytöksen vuoksi UI-päivitykset tapahtuvat välittömästi tapahtumakäsittelijöistä eivätkä aiheuta ylimääräistä jonoa.

### Taustasäikeistä {#from-background-threads}

Kun sitä kutsutaan taustasäikeestä, tehtävät **jonotetaan asynkroniseen suorittamiseen**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Tämä suoritetaan ForkJoinPool säikeellä
        System.out.println("Tausta: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Tämä suoritetaan Environment-säikeellä
            System.out.println("UI-päivitys: " + Thread.currentThread().getName());
            statusLabel.setText("Käsittely valmis");
        });
        
        // result.isDone() olisi false täällä
        // Tehtävä on jonotettu ja suoritetaan asynkronisesti
    });
}
```

webforJ käsittelee taustasäikeistä lähetetyt tehtävät **tiukassa FIFO-järjestyksessä**, säilyttäen toimintojen järjestyksen, vaikka ne lähetetään samanaikaisesti useista säikeistä. Tämän järjestysvakuuden avulla UI-päivitykset suoritetaan tarkalleen siinä järjestyksessä, jossa ne on lähetetty. Joten jos säie A lähettää tehtävän 1, ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 UI-säikeellä. Tehtävien käsittely FIFO-järjestyksessä estää johdonmukaisuuden puutteen UI:ssa.

## Tehtävän peruutus {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, jonka `Environment.runLater()` palauttaa, tukee peruutusta, jolloin voit estää jonotettujen tehtävien suorittamisen. Peruuttamalla odottavat tehtävät voit välttää muistivuotoja ja estää pitkät toiminnot päivittämästä UI:ta, kun niitä ei enää tarvita.

### Perusperuutus {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Peruuta, jos ei vielä suoritettu
if (!result.isDone()) {
    result.cancel();
}
```

### Useiden päivityksien hallinta {#managing-multiple-updates}

Kun suoritetaan pitkiä laskentatehtäviä, joissa on usein UI-päivityksiä, seuraa kaikkia odottavia tuloksia:

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
        
        // Peruuta kaikki odottavat UI-päivitykset
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

Kun komponentit tuhoutuvat (esim. navigoinnin aikana), peruuta kaikki odottavat päivitykset estääksesi muistivuodot:

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

## Suunnitteluohjeet {#design-considerations}

1. **Konteksti vaatimukset**: Säikeillä on oltava peritty `Environment`-konteksti. Ulkoisten kirjastojen säikeet, järjestelmäajastimet ja staattiset alustukset eivät voi käyttää tätä API:a.

2. **Muistivuotojen estäminen**: Seuraa aina ja peruuta `PendingResult`-objektit komponenttien elinkaarimenettelyissä. Jonotetut lambdat vangitsevat viittauksia UI-komponentteihin, estäen roskakeräyksen, jos niitä ei peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä merkityksestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruutusrajoitukset**: Peruuttaminen estää vain jonotettujen tehtävien suorituksen. Jo suorittavat tehtävät valmistuvat normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraavassa on täydellinen, tuotantovalmiin implementoinnin esittely, joka näyttää kaikki parhaat käytännöt asynkronisille UI-päivityksille:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säieexecutor, estääksesi resurssien ehtymisen
  // Tuotannossa harkitse jaetun sovelluslaajuisen säikeen käyttöä
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Seuraa nykyistä tehtävää ja odottavia UI-päivityksiä
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI-komponentit
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Taustalla tapahtuvat UI-päivitykset");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, miten Environment.runLater() mahdollistaa turvalliset UI-päivitykset taustasäikeistä. " +
          "Klikkaa 'Aloita pitkä tehtävä' suorittaaksesi 10 sekunnin taustalaskennan, joka päivittää UI:n edistymistä. " +
          "Testi UI -painike todistaa, että UI pysyy reagoivana taustatoiminnan aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peruuta tehtävä");
  private Button testButton = new Button("Testi UI - Klikkaa minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa sekä taustasäikeen että jonotettujen UI-päivitysten asianmukaisen puhdistuksen.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Konfiguroi kentät
    statusField.setReadOnly(true);
    statusField.setValue("Valmis aloitettavaksi");
    statusField.setLabel("Tila");

    // Konfiguroi edistymispalkki
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

    // Konfiguroi painikkeet
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Klikkaus #" + count + " - UI on reagoiva!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta kaikki käynnissä oleva tehtävä ja odottavat UI-päivitykset
    cancelTask();

    // Tyhjennä tehtäväviittaus
    currentTask = null;

    // Sulje instanssiexecutor sujuvasti
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

    // Käynnistä taustatehtävä määritettyä executor:ia käyttäen
    // Huom: cancel(true) keskeyttää säikeen, mikä aiheuttaa Thread.sleep():n heittää
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simuloi pitkää tehtävää 100 vaiheella
      for (int i = 0; i <= 100; i++) {
        // Tarkista onko peruutettu
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("Tehtävä peruutettu!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Tehtävä peruutettu", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Yhteensä 10 sekuntia
        } catch (InterruptedException e) {
          // Säie on keskeytetty - poistutaan välittömästi
          Thread.currentThread().interrupt(); // Palauta keskeytysohjelmointi
          return;
        }

        // Suorita laskentaa (deterministinen esitys)
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

      // Viimeinen päivitys tuloksella (tämä koodi saavutetaan vain, jos tehtävä onnistui ilman
      // peruutusta)
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
      // Aseta peruutuslippu
      isCancelled = true;

      // Peruuta päätehtävä (keskeyttää säikeen)
      currentTask.cancel(true);

      // Peruuta kaikki odottavat UI-päivitykset
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Peruuttamista...");
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

Tämä toteutus osoittaa useita kriittisiä malleja:

#### 1. Säiepoolinhallinta {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Käyttää **yksittäistä säieexecutor** estääkseen resurssien ehtymisen
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sulkemista

#### 2. Odottavien päivitysten seuraaminen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()`-kutsu seurataan, jotta mahdollistetaan:
- Peruuttaminen, kun käyttäjä napsauttaa peruuta
- Muistivuotojen estäminen `onDestroy()`:ssä
- Oikea puhdistus komponentin elinkaaren aikana

#### 3. Yhteistyöpohjainen peruuttaminen {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mikä mahdollistaa:
- Välittömän reagoinnin peruuttamiseen
- Siistin poistumisen silmukasta
- UI-päivitysten estämisen

#### 4. Elinkaaren hallinta {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // Uudelleenkäyttää peruuttamisen logiikkaa
    currentTask = null;
    executor.shutdown();
}
```
Kriittinen muistivuotojen estämiseksi:
- Peruuttamalla kaikki odottavat UI-päivitykset
- Keskeyttämällä käynnissä olevat säikeet
- Sulkemalla executor

#### 5. UI:n reagointikyvyn testaaminen {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Klikkaus #" + count + " - UI on reagoiva!", Theme.GRAY);
});
```
Demonstroi, että UI-säie pysyy reagoivana taustatoimintojen aikana.
