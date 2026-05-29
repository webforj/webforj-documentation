---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 44d86e725d9228ead98794da8f6210ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

`Environment.runLater()` API tarjoaa mekanismin käyttöliittymän turvalliseen päivittämiseen taustasuorittimilta webforJ-sovelluksissa. Tämä kokeellinen ominaisuus mahdollistaa asynkroniset toiminnot samalla kun säilyttää säikeiden turvallisuuden käyttöliittymän muutoksille.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Ymmärtäminen säikeen mallista {#understanding-the-thread-model}

webforJ noudattaa tiukkaa säikeen mallia, jossa kaikki käyttöliittymän toiminnot on suoritettava `Environment` säikeessä. Tämä rajoitus on olemassa seuraavista syistä:

1. **webforJ API -rajoitukset**: Peruksessa oleva webforJ API sitoo säikeen, joka loi istunnon
2. **Komponentin säikeen affiniteetti**: Käyttöliittymäkomponentit ylläpitävät tilaa, joka ei ole säikeiden turvallinen
3. **Tapahtuman jako**: Kaikki käyttöliittymän tapahtumat käsitellään järjestyksessä yhdellä säikeellä

Tämä yksisäikeinen malli estää kilpailutilanteet ja ylläpitää johdonmukaisen tilan kaikille käyttöliittymäkomponenteille, mutta se luo haasteita asynkronisten, pitkäkestoisten laskentatehtävien integroimisessa.

## `RunLater` API {#runlater-api}

`Environment.runLater()` API tarjoaa kaksi menetelmää käyttöliittymän päivitysten aikatauluttamiseen:

```java title="Environment.java"
// Aikatauluta tehtävä ilman palautusarvoa
public static PendingResult<Void> runLater(Runnable task)

// Aikatauluta tehtävä, joka palauttaa arvon
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Molemmat menetelmät palauttavat <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka seuraa tehtävän suorittamista ja tarjoaa pääsyn tulokseen tai mahdollisiin poikkeuksiin.

## Säikeen kontekstin perintä {#thread-context-inheritance}

Automaattinen kontekstin perintä on kriittinen ominaisuus `Environment.runLater()`:ssa. Kun `Environment`-säikeessä suoritetaan lapsisäikeitä, nämä lapset perivät automaattisesti kyvyn käyttää `runLater()`.

### Miten perintä toimii {#how-inheritance-works}

Kaikilla säikeillä, jotka luodaan `Environment`-säikeen sisällä, on automaattisesti pääsy kyseiseen `Environment`-konseptiin. Tämä perintä tapahtuu automaattisesti, joten sinun ei tarvitse siirtää mitään kontekstia tai konfiguroida mitään.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Tämä säie on Environment-konteksti
    
    // Lapsisäikeet perivät kontekstin automaattisesti
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Voi käyttää runLateria, koska konteksti periytyi
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Säikeet ilman kontekstia {#threads-without-context}

Säikeet, jotka luodaan `Environment`-konteksti ulkopuolella, eivät voi käyttää `runLater()` ja heitetään `IllegalStateException`:

```java
// Statistinen inicialisoija - ei Environment-konteksti
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }).start();
}

// Järjestelmän ajastin säikeet - ei Environment-konteksti  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  }
}, 1000);

// Ulkoisen kirjaston säikeet - ei Environment-konteksti
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Heittää IllegalStateException
  });
```

## Suoritus käyttäytyminen {#execution-behavior}

`runLater()`-menetelmän suoritus käyttäytyminen riippuu siitä, mikä säie kutsuu sitä:

### Käyttöliittymästä {#from-the-ui-thread}

Kun kutsutaan suoraan `Environment` -säikeestä, tehtävät suoritetaan **synkronisesti ja heti**:

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

Tämän synkronisen käyttäytymisen avulla käyttöliittymän päivitykset tapahtuvat heti tapahtumakäsittelijöistä, eivätkä ne vie turhaan jonoa.

### Taustasäikeistä {#from-background-threads}

Kun kutsutaan taustasäikeestä, tehtävät **jonotetaan asynkroniseen suorittamiseen**:

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
    
    // result.isDone() olisi false tässä
    // Tehtävä on jonotettu ja suoritetaan asynkronisesti
  });
}
```

webforJ käsittelee taustasäikeistä lähetettyjä tehtäviä **tiukassa FIFO-järjestyksessä**, säilyttäen operaatioiden sekvenssin, vaikka ne on lähetetty useista säikeistä samanaikaisesti. Tämän järjestyksen takia käyttöliittymän päivitykset suoritetaan täsmälleen siinä järjestyksessä, jossa ne on lähetetty. Jos siis säie A lähettää tehtävän 1 ja sitten säie B lähettää tehtävän 2, tehtävä 1 suoritetaan aina ennen tehtävää 2 käyttöliittymässä. Tehtävien käsittely FIFO-järjestyksessä estää käyttöliittymän epäjärjestyksen.

## Tehtävän peruutus {#task-cancellation}

<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, joka palautetaan `Environment.runLater()` -menetelmästä, tukee peruutusta, jolloin voit estää jonotettujen tehtävien suorittamisen. Peruuttamalla odottavat tehtävät voit välttää muistivuodot ja estää pitkäaikaiset toiminnot päivittämästä käyttöliittymää sen jälkeen, kun niitä ei enää tarvita.

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

Kun suoritat pitkäkestoisia toimintoja, joissa on usein käyttöliittymän päivityksiä, seuraa kaikkia odottavia tuloksia:

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

Kun komponentteja tuhotaan (esim. navigoinnin aikana), peruuta kaikki odottavat päivitykset estääksesi muistivuodot:

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

## Suunnittelu näkökohtia {#design-considerations}

1. **Kontekstivaatimus**: Säikeillä on oltava peritty `Environment` -konteksti. Ulkoiset kirjastosäikeet, järjestelmäajastimet ja staattiset inicialisoijat eivät voi käyttää tätä API:a.

2. **Muistivuotojen estäminen**: Seuraa aina ja peruuta `PendingResult` -objekteja komponentin elinkaarimetodissa. Jonotetut lambdat vangitsevat viittauksia käyttöliittymäkomponentteihin, estäen roskat keräämisen, ellei niitä peruuteta.

3. **FIFO-suoritus**: Kaikki tehtävät suoritetaan tiukassa FIFO-järjestyksessä merkityksestä riippumatta. Prioriteettijärjestelmää ei ole.

4. **Peruutusrajoitukset**: Peruuttaminen estää vain jonotettujen tehtävien suorittamisen. Jo suorittavat tehtävät suoritetaan normaalisti.

## Täydellinen tapaustutkimus: `LongTaskView` {#complete-case-study-longtaskview}

Seuraava on täydellinen, tuotantovalmiin toteutus, joka osoittaa kaikki parhaat käytännöt asynkronisessa käyttöliittymän päivityksessä:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Käytä yhtä säiettä estämään resurssien loppuminen
  // Tuotantokäyttöön harkitse käytettäväksi jaettua sovellustason säiettä
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
  private H2 titleLabel = new H2("Taustakäyttöliittymän päivityksien demo");
  private Paragraph descriptionPara = new Paragraph(
      "Tämä demo näyttää, kuinka Environment.runLater() mahdollistaa turvalliset käyttöliittymän päivitykset taustasäikeiltä. " +
          "Napsauta 'Aloita pitkä tehtävä' suorittaaksesi 10 sekunnin taustalaskennan, joka päivittää käyttöliittymän edistymistä. " +
          "Testi UI -painike todistaa, että käyttöliittymä pysyy responsiivisena taustatoiminnan aikana.");
  private TextField statusField = new TextField("Tila");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Tulos");
  private Button startButton = new Button("Aloita pitkä tehtävä");
  private Button cancelButton = new Button("Peruuta tehtävä");
  private Button testButton = new Button("Testi UI - Napsauta minua!");
  private Paragraph footerPara = new Paragraph(
      "Huom: Tehtävä voidaan peruuttaa milloin tahansa, mikä osoittaa sekä taustasäikeen että jonotettujen käyttöliittymäpäivityksien asianmukaisen puhdistamisen.");
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
      showToast("Napsautus #" + count + " - käyttöliittymä on responsiivinen!", Theme.GRAY);
    });

    // Lisää komponentit
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Peruuta kaikki käynnissä olevat tehtävät ja odottavat käyttöliittymäpäivitykset
    cancelTask();

    // Tyhjennä tehtäväviittaus
    currentTask = null;

    // Sammuta instanssisäie rauhallisesti
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Aloitetaan taustatehtävä...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Nollaa peruutuslippu ja tyhjennä aiemmat odottavat päivitykset
    isCancelled = false;
    pendingUIUpdates.clear();

    // Käynnistä taustatehtävä eksplisiittisellä säikeellä
    // Huom: cancel(true) keskeyttää säikeen, mikä saa Thread.sleep() -pytkimen heittämään
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
            showToast("Tehtävä peruutettu", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // Yhteensä 10 sekuntia
        } catch (InterruptedException e) {
          // Säie keskeytettiin - lopeta heti
          Thread.currentThread().interrupt(); // Palauta keskeytetyn tila
          return;
        }

        // Tee joitain laskelmia (deterministinen demo)
        // Tuottaa lukuja 0 ja 1 välillä
        result += Math.sin(i) * 0.5 + 0.5;

        // Päivitä edistyminen taustasäikeestä
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Käsitellään... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Viimeinen päivitys tuloksella (tätä koodia kutsutaan vain, jos tehtävä suoritettu ilman
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

      // Peruuta kaikki odottavat käyttöliittymäpäivitykset
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Perutaan tehtävää...");
        cancelButton.setEnabled(false);

        showToast("Peruutuspyyntö", Theme.GRAY);
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

#### 1. Säiettä hallinnointi {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Käyttää **yksittäistä säidehtoa** estämään resurssien loppumisen
- Luo **daemon-säikeitä**, jotka eivät estä JVM:n sulkemista

#### 2. Odottavien päivitysten seuraaminen {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Jokainen `Environment.runLater()` -kutsu seurataan mahdollistamaan:
- Peruuttaminen, kun käyttäjä napsauttaa peruutusta
- Muistivuotojen estäminen `onDestroy()`
- Oikea puhdistus komponentin elinkaaren aikana

#### 3. Yhteistyöperuutus {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
Taustasäie tarkistaa tämän lipun jokaisessa iteraatiossa, mahdollistaen:
- Välittömän reagoinnin peruutukseen
- Puhdasta poistumista silmukasta
- Lisäkäyttöliittymäpäivitysten estämistä

#### 4. Elinkaaren hallinta {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Uudelleenkäyttää peruutuslogiikkaa
  currentTask = null;
  executor.shutdown();
}
```
Kriittinen muistivuotojen estämiseksi:
- Peruuta kaikki odottavat käyttöliittymäpäivitykset
- Keskeytä käynnissä olevat säikeet
- Sulje säieterä

#### 5. Käyttöliittymän reagointikyvyn testaaminen {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Napsautus #" + count + " - käyttöliittymä on responsiivinen!", Theme.GRAY);
});
```
Todistaa, että käyttöliittymä säie pysyy responsiivisena taustatoimintojen aikana.
