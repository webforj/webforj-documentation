---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: e50a52f19876f98eec1bd825ca82cd6a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstikenttä, joka on suunniteltu tarkkaa ja jäsenneltyä aikasyöttöä varten. Se antaa käyttäjille mahdollisuuden syöttää aikoja **numeroina** ja muotoilee syötteen automaattisesti määritetyn maskin perusteella, kun kenttä menettää fokuksen. Maski on merkkijono, joka määrittelee odotetun aikamuodon, ohjaten sekä syöttöä että näyttöä.

Tämä komponentti tukee joustavaa jäsentämistä, validoimista, lokalisointia ja arvon palauttamista. Se on erityisen hyödyllinen aikaherkkissä lomakkeissa, kuten aikatauluissa, työaikapapereissa ja varauksissa.

:::tip Etsitkö päivämääräsyöttöä?
`MaskedTimeField` on rakennettu vain **aika** syöttöä varten. Jos etsit komponenttia, joka käsittelee **päivämääriä** vastaavalla maskiperusteisella muotoilulla, tutustu [`MaskedDateField`](./datefield.md).
:::

## Perusteet {#basics}

`MaskedTimeField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkusarvoa, etiketin, paikkaohjaimen ja tapahtumakuuntelijan arvon muutoksille.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää formaattinäyttöjä määrittääkseen, kuinka aikaa jäsennetään ja näytetään. Jokainen formaattinäyttö alkaa prosenttimerkillä `%` ja sitä seuraa kirjain, joka edustaa aikakomponenttia.

### Aikamuotoilunäyttöjä {#time-format-indicators}

| Formaatti | Kuvaus              |
|-----------|---------------------|
| `%H`      | Tunti (24-tuntinen) |
| `%h`      | Tunti (12-tuntinen) |
| `%m`      | Minuutti           |
| `%s`      | Sekunti            |
| `%p`      | AM/PM              |

### Modifierit {#modifiers}

Modifierit hienosäätävät aikakomponenttien näyttämistä:

| Modifier | Kuvaus                          |
|----------|----------------------------------|
| `z`      | Nollatäyttö                     |
| `s`      | Lyhyt tekstimuoto               |
| `l`      | Pitkä tekstimuoto                |
| `p`      | Pakattu numero                   |
| `d`      | Desimaali (oletusmuoto)         |

Nämä mahdollistavat joustavan ja paikallisystävällisen aikamuotoilun.

## Aikamuodon lokalisointi {#time-format-localization}

`MaskedTimeField` tukee lokalisointia määrittämällä sopivan paikallisen asetuksen. Tämä varmistaa, että aikasyöttö ja -tuotto vastaavat alueellisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, kuinka AM/PM-indikaattorit näytetään, miten erotinmerkkejä käsitellään ja kuinka arvot jäsennetään.

## Jäsentämislogiikka {#parsing-logic}

`MaskedTimeField` jäsentää käyttäjän syötteen määritetyn aikamaskin perusteella. Se hyväksyy sekä täydellisiä että lyhennettyjä numeerisia syötteitä erottimilla tai ilman, mahdollistaen joustavan syötön varmistaen samalla, että ajat ovat voimassa.
Jäsentäminen riippuu maskin määrittämästä formaattijärjestyksestä (esim. `%Hz:%mz` tunnille/minuutille). Tämä formaatti määrittää, kuinka numeerisia sekvenssejä tulkitaan.

### Esimerkit jäsentämisestä {#example-parsing-scenarios}

| Syöte  | Maski         | Tulkitaan                       |
|--------|---------------|---------------------------------|
| `900`  | `%Hz:%mz`    | `09:00`                         |
| `1345` | `%Hz:%mz`    | `13:45`                         |
| `0230` | `%hz:%mz %p` | `02:30 AM`                     |
| `1830` | `%hz:%mz %p` | `06:30 PM`                     |

## Min/Max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-komponentissa käyttämällä `setMin()` ja `setMax()` -menetelmiä:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) -tyypin arvoja. Määritellyn alueen ulkopuoliset syötteet katsotaan virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautustoiminnon, joka palauttaa kentän arvon ennaltamääritettyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten kumoamiseksi tai oletusaikaan palaamiseksi.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalausnäppäin, ellei sitä ohitettaisi tapahtumakuuntelijalla)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validointikaaviot {#validation-patterns}

Voit soveltaa asiakaspohjaisia validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` -menetelmällä:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä kaavio varmistaa, että vain `HH:mm` -muotoiset arvot (kaksi numeroa, päällekkäin, kaksi numeroa) katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Kaavan on noudatettava JavaScriptin RegExp-syntaksia, kuten on dokumentoitu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää jäsentää ja muotoilla numeerisia aikasyötteitä nykyisen maskin mukaan. Kuitenkin käyttäjät voivat silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai jäsentämätön (esim. `99:99`), se voi läpäistä kaaviotarkastukset, mutta epäonnistua loogisessa validoinnissa.
Sinun tulee aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että aika on sekä oikein muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka antaa käyttäjille mahdollisuuden valita aikansa visuaalisesti sen sijaan, että kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkkaa syöttöä tarvitaan.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit käyttää aikavalitsinta kutsumalla `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Valitsimen ikonin näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` -menetelmää valitsemaan, näytetäänkö vai piilotetaanko kelloikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattinen avauskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautuvan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enteriä tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Vahvista valinta valitsimella
Varmistaaksesi, että käyttäjät voivat valita ajan vain valitsimen kautta (eivätkä syöttämällä manuaalisesti), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // Avaa valitsimen käyttäjän vuorovaikutuksen aikana
field.setAllowCustomValue(false);    // Estää manuaalisen tekstisyötön
```

Tämä asetelma varmistaa, että kaikki aikasyöttö tulee valitsin-käyttäjäliittymän kautta, mikä on hyödyllistä tiukan formaattikontrollin ja syöttövirheiden poistamisen käsittelyssä.
:::

### Valitsimen avaaminen manuaalisesti {#manually-open-the-picker}

Aukaisemaan aikavalitsin ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Valitsimen välin asettaminen {#setting-the-picker-step}

Voit määrittää valitsimessa valittavien aikavälisten aikojen välin käyttämällä `setStep()`. Tämä mahdollistaa sen, että voit hallita, kuinka suuria aikavalinnan vaihtoehdot ovat—ihanteellinen tilanteisiin, joissa aikataulutetaan 15 minuutin välein.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askeleen rajoite
Askeleen on jaettava täsmällisesti tunti tai koko päivä. Muuten poikkeus heitetään.
:::

Tämä varmistaa, että alasvetoluettelossa on ennakoitavia ja tasaisesti sijoitettuja arvoja kuten `09:00`, `09:15`, `09:30` jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa [`MaskedTimeField`](#basics) -komponenttia lisäämällä kelausohjaimia, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää aikaa käyttämällä nuolinäppäimiä tai käyttöliittymäpainikkeita. Se tarjoaa ohjaavampaa vuorovaikutustyyliä, erityisen hyödyllistä työpöytätyylisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Avaintoiminnot {#key-features}

- **Vuorovaikutteinen aikaskaalautuminen:**  
  Käytä nuolinäppäimiä tai kelanäppäimiä aikarvojen lisäämiseksi tai vähentämiseksi.

- **Mukautettava kiertoyksikkö:**  
  Valitse, mikä aikakomponentti on säädettävissä käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoja ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Min/Max-rajoitukset:**  
  Perii vähimmäis- ja enimmäisaikatuen käyttämällä `setMin()` ja `setMax()`.

- **Muotoiltu tuotto:**  
  Täydellinen yhteensopivuus `MaskedTimeField`-komponentin maskien ja lokalisointiasetusten kanssa.

### Esimerkki: Ajan säätämisen konfigurointi tunnin mukaan {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylittely {#styling}

<TableBuilder name="MaskedTimeField" />
