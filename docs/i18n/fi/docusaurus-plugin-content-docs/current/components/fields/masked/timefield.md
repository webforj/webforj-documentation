---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 2631f01d383c134ba92d8ad03f5a57d3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstikenttä, joka sallii käyttäjien syöttää aikoja **numeroina** ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski määrittelee odotetun aikamuodon, ohjaten sekä syöttöä että näyttöä. Komponentti tukee joustavaa jäsentämistä, validointia, lokalisaatiota ja arvon palauttamista johdonmukaiselle aikakäsittelylle.

<!-- INTRO_END -->

## Perustiedot {#basics}

:::tip Etsitkö päivämääräsyöttöä?
`MaskedTimeField` on rakennettu vain **aikoja** varten. Jos etsit komponenttia, joka käsittelee **päiviä** samanlaisen maskiperusteisen muotoilun avulla, tutustu [`MaskedDateField`](./datefield.md).
:::

`MaskedTimeField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkupäivämäärän, etiketin, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää muotoilun indikaattoreita määrittämään, miten aikaa jäsennetään ja näytetään. Jokainen muotoilun indikaattori alkaa prosenttimerkistä `%`, jonka jälkeen tulee kirjain, joka edustaa aikakomponenttia.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla tai jäsentää aikoja samalla maskisyntaksilla kentän ulkopuolella, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Aikamuotoilun indikaattorit {#time-format-indicators}

| Muoto | Kuvaus             |
|-------|--------------------|
| `%H`  | Tunti (24-tuntinen)|
| `%h`  | Tunti (12-tuntinen)|
| `%m`  | Minuutti           |
| `%s`  | Sekunti            |
| `%p`  | AM/PM              |

### Muokkaimet {#modifiers}

Muokkaimet tarkentavat aikakomponenttien näyttöä:

| Muokkain | Kuvaus                   |
|----------|--------------------------|
| `z`      | Nollasijoitus            |
| `s`      | Lyhyt tekstiesitys       |
| `l`      | Pitkä tekstiesitys       |
| `p`      | Pakattu number           |
| `d`      | Desimaaliluku (oletusmuoto) |

Nämä mahdollistavat joustavan ja paikallisesti mukautuvan aikamuotoilun.

## Aikamuotojen lokalisaatio {#time-format-localization}

`MaskedTimeField` tukee lokalisaatiota oikean alueen määrittämisellä. Tämä varmistaa, että aikasyöte ja -tulos vastaavat alueellisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, miten AM/PM -indikaattorit näytetään, miten erottimet käsitellään ja miten arvot jäsennetään.

## Jäsentämislogiikka {#parsing-logic}

`MaskedTimeField` jäsentää käyttäjän syöttämän tiedon määritellyn aikamaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erottimilla tai ilman, mikä mahdollistaa joustavan syötteen ilman, että voimassa olevat ajat vaarantuvat.
Jäsentäminen riippuu maskissa määritellystä muotojärjestyksestä (esim. `%Hz:%mz` tunnin/minuutin osalta). Tämä muoto määrää, miten numeeriset sekvenssit tulkitaan.

### Esimerkit jäsentämistapauksista {#example-parsing-scenarios}

| Syöte | Mask | Tulkinta |
|-------|------|----------|
| `900` | `%Hz:%mz` | `09:00` |
| `1345` | `%Hz:%mz` | `13:45` |
| `0230` | `%hz:%mz %p` | `02:30 AM` |
| `1830` | `%hz:%mz %p` | `06:30 PM` |

## Min/max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-kentässä käyttämällä `setMin()` ja `setMax()` -menetelmiä:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) -tyypin arvot. Määritelmien ulkopuolisia syötteitä pidetään voimassa olevina.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautusominaisuuden, joka palauttaa kentän arvon ennalta määrättyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten kumoamisen tai oletusaikaan palaamisen kannalta.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei tapahtumakuuntelija ylikirjoita sitä)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validointimallit {#validation-patterns}

Voit soveltaa asiakaspuskurointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmällä:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä malli varmistaa, että vain arvot, jotka vastaavat `HH:mm` -muotoa (kaksi numeroa, kaksoispiste, kaksi numeroa), katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on seurattava JavaScriptin RegExp-syntaksia, kuten on dokumentoitu [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää jäsentää ja muotoilla numeerisia aikasyötteitä nykyisen maskin perusteella. Kuitenkin, käyttäjät voivat silti manuaalisesti syöttää arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai jäsentämättömä (esim. `99:99`), se saattaa läpäistä mallintarkastukset mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että aika on sekä oikein muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka sallii käyttäjien valita ajan muuttamalla sitä visuaalisesti sen sijaan, että he kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai silloin, kun tarkka syöte on tarpeen.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Valitsimen käyttö {#accessing-the-picker}

Voit käyttää aikavalitsinta käyttämällä `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Valitsimen kuvakkeen näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` -menetelmää näyttämään tai piilottamaan kelloikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikon
```

### Automaattinen avautumiskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Valitsimen kautta valinnan pakottaminen
Varmistaaksesi, että käyttäjät voivat valita ajan vain valitsimen kautta (eivätkä manuaalisesti kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // Avautuu valitsimella vuorovaikuttaessa
field.setAllowCustomValue(false);    // Poistaa manuaalisen tekstisyötteen mahdollisuuden
```

Tämä asetus takaa, että kaikki aikasyöte tulee valitsimen käyttöliittymästä, mikä on hyödyllistä, kun haluat tiukkaa muotoilua ja poistaa jäsentämiseen liittyvät ongelmat kirjoitetuista syötteistä.
:::

### Avaa valitsin manuaalisesti {#manually-open-the-picker}

Avaa aikavalitsin ohjelmallisesti:

```java
picker.open();
```

Tai käytä alias-nimeä:

```java
picker.show(); // sama kuin open()
```

### Valitsimen askeleen asettaminen {#setting-the-picker-step}

Voit määrittää valitsimen valittavien aikojen välin käyttäen `setStep()`. Tämä mahdollistaa sen, että voit hallita, kuinka tarkasti aikavaihtoehdot asetetaan – ihanteellinen aikataulutustilanteissa, joissa käytetään 15 minuutin välejä.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askeleen rajoitus
Askeleen on jaettava tasan tunti tai koko päivä. Muussa tapauksessa poikkeus heitetään.
:::

Tämä varmistaa, että avattavassa luettelossa on ennakoitavia, tasaisesti jakautuvia arvoja kuten `09:00`, `09:15`, `09:30` jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa [`MaskedTimeField`](#basics) käyttöliittymään lisäämällä pyörimiskontrollit, jotka sallivat käyttäjien lisätä tai vähentää aikaa nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, erityisesti työpöytätyyppisissä sovelluksissa.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Interaktiivinen aikavaihdon säätö:**
  Käytä nuolinäppäimiä tai pyöritys-näppäimiä aikarvojen lisäämiseksi tai vähentämiseksi.

- **Mukautettava pyörimisvyöhyke:**
  Valitse, mitä aikakomponenttia muokata käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoja ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Min/Max-rajoitukset:**
  Perii tuen minimille ja maksimeille aikarajoille käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu tuloste:**
  Täysin yhteensopiva maskien ja lokalisaatioasetusten kanssa `MaskedTimeField`:stä.

### Esimerkki: Aika-askeletuksen määrittäminen tuntien mukaan {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylittely {#styling}

<TableBuilder name="MaskedTimeField" />
