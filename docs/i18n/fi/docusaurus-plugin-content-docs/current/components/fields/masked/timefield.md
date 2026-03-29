---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 17c5f6ce7fa234dbeb848c4bcab41e60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstikenttä, joka sallii käyttäjien syöttää aikoja **numeroina** ja muotoilee sisäänsyötön automaattisesti määritetyn maskin perusteella, kun kenttä menettää fokuksen. Maski määrittelee odotetun aikamuotoilun, ohjaten sekä syöttöä että näyttöä. Komponentti tukee joustavaa jäsentämistä, validoimista, lokalisointia ja arvojen palauttamista yhtenäisen ajan käsittelyn varmistamiseksi.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö päivämääräsyötettä?
`MaskedTimeField` on rakennettu **vain-aikasyötteelle**. Jos etsit komponenttia, joka käsittelee **päiviä** samankaltaisella maskiperusteisella muotoilulla, katso [`MaskedDateField`](./datefield.md).
:::

`MaskedTimeField` voidaan luoda parametreilla tai ilman. Voit määrittää alkuperäisen arvon, etiketin, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää muotoilun osoittimia määrittääkseen, miten aika jäsennetään ja näytetään. Jokainen muotoilun osoitin alkaa `%`-merkillä, jota seuraa kirjain, joka edustaa aikakomponenttia.

### Aikamuotoilun osoittimet {#time-format-indicators}

| Muoto | Kuvaus               |
|-------|---------------------|
| `%H`  | Tunti (24-tuntinen) |
| `%h`  | Tunti (12-tuntinen) |
| `%m`  | minuutti            |
| `%s`  | sekunti             |
| `%p`  | AM/PM               |

### Modifioijat {#modifiers}

Modifioijat tarkentavat aikakomponenttien näyttöä:

| Modifioija | Kuvaus                           |
|-------------|----------------------------------|
| `z`         | Nollatäyttö                       |
| `s`         | Lyhyt tekstiesitys               |
| `l`         | Pitkä tekstiesitys               |
| `p`         | Pakattu numero                    |
| `d`         | Desimaali (oletusmuoto)          |

Nämä mahdollistavat joustavan ja paikallisten aikamuotoilu.

## Aikamuotoilun lokalisointi {#time-format-localization}

`MaskedTimeField` tukee lokalisointia asettamalla sopivan lokalisoinnin. Tämä varmistaa, että aikasyöttö ja -tuloste vastaavat alueellisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, miten AM/PM-osoitimet näytetään, miten erotinmerkkejä käsitellään ja miten arvot jäsennetään.

## Jäsentämislogiikka {#parsing-logic}

`MaskedTimeField` jäsentää käyttäjän syötteen perusteella määritetyn aikamaskin. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet, joissa on tai ei ole erotinmerkkejä, mikä mahdollistaa joustavan syötön samalla varmistaen kelvolliset ajat. 
Jäsentämiskäyttäytyminen riippuu maskin määrittelemästä muotojärjestyksestä (esim. `%Hz:%mz` tunnin/minuutin jaoks). Tämä muoto määrittää, miten numeeriset sekvenssit tulkitaan.

### Esimerkkijäsentämisskenaariot {#example-parsing-scenarios}

| Syöte  | Maski         | Tulkittu muoto   |
|--------|---------------|------------------|
| `900`  | `%Hz:%mz`     | `09:00`          |
| `1345` | `%Hz:%mz`     | `13:45`          |
| `0230` | `%hz:%mz %p`  | `02:30 AM`       |
| `1830` | `%hz:%mz %p`  | `06:30 PM`       |

## Min/max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-kentässä käyttämällä `setMin()`- ja `setMax()`-menetelmiä:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html)-tyyppisiä arvoja. Määritetyn aikarajan ulkopuoliset syötteet katsotaan kelvottomiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautustoiminnon, joka nollaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten peruuttamiseen tai oletusaikaan paluuseen.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Avaimen kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei tapahtumakuuntelijalla ole muuta määritelty)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validointimallit {#validation-patterns}

Voit soveltaa asiakaspään validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmällä:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä malli varmistaa, että vain `HH:mm`- muodossa (kaksi numeroa, kaksoispiste, kaksi numeroa) olevat arvot katsotaan kelvollisiksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on noudatettava JavaScript RegExp -syntaksia, kuten asiakirjoissa [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syöttökäsittelystä
Kenttä yrittää jäsentää ja muotoilla numeeriset aikasyötteet nykyisen maskin perusteella. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa mutta semanttisesti virheellinen tai jäsentämätön (esim. `99:99`), se voi läpäistä kaavamallit mutta epäonnistua loogisessa validoinnissa. 
Sinun tulisi aina validoida syötearvo sovelluksen logiikassa, vaikka säännöllinen lausekemalli olisi asetettu, varmistaaksesi, että aika on sekä oikein muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka antaa käyttäjien valita ajan visuaalisesti sen sijaan, että kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai silloin, kun tarkka syöttö on tarpeen.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Valitsimen käyttö {#accessing-the-picker}

Voit käyttää aikavalitsinta `getPicker()`-menetelmällä:

```java
TimePicker picker = field.getPicker();
```

### Valitsimen kuvakkeen näyttäminen/pittämättä jättäminen {#showhide-the-picker-icon}

Käytä `setIconVisible()`-menetelmää näyttämään tai piilottamaan kellokuvake kentän vieressä:

```java
picker.setIconVisible(true); // näyttää kuvakkeen
```

### Automaattinen avautuminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Valinnan pakottaminen valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita ajan vain valitsimen avulla (eikä manuaalisesti kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // Avautuu valitsimeen käyttäjän vuorovaikutuksen myötä
field.setAllowCustomValue(false);    // Estää manuaalisen tekstisyötteen
```

Tämä asetustapa takaa, että kaikki aikasyöttö tulee valitsimen käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukan muotoilusäätelyn ja poistaa erityiskirjoitukseen liittyvät jäsentämisongelmat.
:::

### Aikavalitsimen avaaminen manuaalisesti {#manually-open-the-picker}

Avaa aikavalitsin ohjelmallisesti:

```java
picker.open();
```

Tai käytä alias-komentoa:

```java
picker.show(); // sama kuin open()
```

### Valitsimen askeleen määrittäminen {#setting-the-picker-step}

Voit määrittää valitsimen valittavien aikojen välin käyttämällä `setStep()`. Tämä antaa sinulle mahdollisuuden hallita, kuinka hienojakoisia aikavaihtoehdot ovat—ihanteellinen esimerkiksi aikatauluttamiseen 15 minuutin välein.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askelrajoitus
Askeleen on jaettava tasaisesti tunti tai kokonainen päivä. Muutoin poikkeus heitetään.
:::

Tämä varmistaa, että pudotuslistassa on ennakoitavissa olevia, tasaisesti jaettavia arvoja kuten `09:00`, `09:15`, `09:30` jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa [`MaskedTimeField`](#basics) lisäämällä spinnerikontrollit, joiden avulla käyttäjät voivat nostaa tai laskea aikaarvoa nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, joka on erityisen hyödyllinen työpöytäsovelluksissa.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Keskeiset ominaisuudet {#key-features}

- **Interaktiivinen aikaväli:**  
  Käytä nuolinäppäimiä tai pyörimispainikkeita aikaarvon lisäämiseksi tai vähentämiseksi.

- **Mukautettava pyörimisyksikkö:**  
  Valitse, mitä aikakomponenttia muokataan käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoja ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Min/max-rajoitukset:**  
  Tukea vähimmäis- ja enimmäisaikojen asettamiselle käyttämällä `setMin()` ja `setMax()`.

- **Muotoiltu tuloste:**  
  Täysin yhteensopiva `MaskedTimeField`-maskien ja lokalisointiasetusten kanssa.

### Esimerkki: Aikavälin määrittäminen tuntina {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylittely {#styling}

<TableBuilder name="MaskedTimeField" />
