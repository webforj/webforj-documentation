---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 07256952a84572a67b1fe2b66dd5b5a5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstikenttä, jonka avulla käyttäjät voivat syöttää aikoja **numeroin** ja se muotoilee syötteen automaattisesti määritetyn maskin mukaan, kun kenttä menettää fokuksen. Maski määrittää odotetun aikamuodon ja ohjaa sekä syöttöä että näyttöä. Komponentti tukee joustavaa jäsentämistä, validoimista, lokalisointia ja arvon palauttamista johdonmukaiselle aikakäsittelylle.

<!-- INTRO_END -->

:::tip Etsitkö päivämääräsyöttöä?
`MaskedTimeField` on rakennettu **pelkästään aikaa** varten. Jos etsit komponenttia, joka käsittelee **päiviä** samankaltaisella maskipohjaisella muotoilulla, tutustu [`MaskedDateField`](/docs/components/fields/masked/datefield).
:::

`MaskedTimeField` voidaan luoda parametreilla tai ilman. Voit määrittää alkuaikavälin, etiketin, paikkamerkin ja tapahtumakuuntelijan arvomuutoksille.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää muotoilun indikaattoreita määritelläkseen, kuinka aika jäsennetään ja näytetään. Jokainen muotoilun indikaattori alkaa `%`-merkillä, jota seuraa kirjain, joka edustaa aikakomponenttia.

:::tip Maskin soveltaminen ohjelmallisesti
Jos haluat muotoilla tai jäsentää aikoja samalla maskisynneksellä kentän ulkopuolella, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Aikamuotoilun indikaattorit {#time-format-indicators}

| Muoto   | Kuvaus                |
|---------|------------------------|
| `%H`    | Tunti (24-tuntinen)   |
| `%h`    | Tunti (12-tuntinen)   |
| `%m`    | Minuutti              |
| `%s`    | Sekunti               |
| `%p`    | AM/PM                 |

### Muokkaimet {#modifiers}

Muokkaimet tarkentavat aikakomponenttien näyttöä:

| Muokatin | Kuvaus                      |
|----------|------------------------------|
| `z`      | Nollatäyttö                  |
| `s`      | Lyhyt tekstiesitys          |
| `l`      | Pitkä tekstiesitys          |
| `p`      | Pakattu numero               |
| `d`      | Desimaalimuoto (oletusmuoto)|

Nämä mahdollistavat joustavan ja paikallisen aikamuotoilun.

## Aikamuotojen lokalisointi {#time-format-localization}

`MaskedTimeField` tukee lokalisointia määrittämällä sopivan paikallisen asetuksen. Tämä varmistaa, että aika syöttö ja ulosanti vastaavat alueellisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, kuinka AM/PM-indikaattorit näytetään, kuinka erottimet käsitellään ja kuinka arvot jäsennetään.

## Jäsentämislogiikka {#parsing-logic}

`MaskedTimeField` jäsentää käyttäjän syötteen määritettyjen aikamaskien perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erottimilla tai ilman, sallien joustavan syötön samalla varmistaen voimassa olevat ajat. Jäsentämiskäyttäytyminen riippuu maskin määrittelemästä muotojärjestyksestä (esim. `%Hz:%mz` tunnille/minuutille). Tämä muoto määrittää, kuinka numerosekvenssejä tulkitaan.

### Esimerkit jäsentämiskäytännöistä {#example-parsing-scenarios}

| Syöte  | Maski         | Tulkittu muoto |
|--------|---------------|-----------------|
| `900`  | `%Hz:%mz`     | `09:00`         |
| `1345` | `%Hz:%mz`     | `13:45`         |
| `0230` | `%hz:%mz %p`  | `02:30 AM`      |
| `1830` | `%hz:%mz %p`  | `06:30 PM`      |

## Minimi/maximi-rajoitteiden asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-komponentissa käyttäen `setMin()` ja `setMax()` metodeja:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat metodit hyväksyvät [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) tyyppiset arvot. Syötteet, jotka ovat määritellyn alueen ulkopuolella, katsotaan virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautusominaisuuden, joka palauttaa kentän arvon ennalta määritettyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten kumoamiseksi tai palauttamiseksi oletusaikaan.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön avulla**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusnäppäin, ellei tapahtumakuuntelija ohita sitä)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validointimallit {#validation-patterns}

Voit soveltaa asiakaspuolen validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` metodin kanssa:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä malli varmistaa, että vain arvot, jotka vastaavat `HH:mm` muotoa (kaksi numeroa, kaksoispiste, kaksi numeroa), katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on noudatettava JavaScript RegExp -syntaksia, kuten on dokumentoitu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautukset syötteen käsittelystä
Kenttä pyrkii jäsentämään ja muotoilemaan numeerisia aikasyötteitä nykyisen maskin perusteella. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai ei-jäsennettävä (esim. `99:99`), se voi läpäistä mallin tarkistukset, mutta epäonnistua loogisessa validoinnissa. 
Sinun tulisi aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lausekemalli on asetettu, varmistaaksesi, että aika on sekä oikein muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka mahdollistaa käyttäjien valita ajan visuaalisesti sen sijaan, että he kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai silloin, kun tarkka syöttö on tarpeen.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit käyttää aikavalitsinta `getPicker()` metodilla:

```java
TimePicker picker = field.getPicker();
```

### Valitsinikoni näkyviin/piiloon {#showhide-the-picker-icon}

Käytä `setIconVisible()` näyttämään tai piilottamaan kelloikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattisesti avautuminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. klikkaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Valinnan pakottaminen valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita ajan vain valitsimen avulla (eivätkä kirjoita sitä manuaalisesti), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // Avaa valitsimen käyttäjän vuorovaikutuksessa
field.setAllowCustomValue(false);    // Estää manuaalisen tekstisyöttö
```

Tämä asetus takaa, että kaikki aikasyöte tulee valitsimen käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukkaa muotoilun hallintaa ja poistaa jäsentämisongelmat kirjoitetusta syötteestä.
:::

### Valitsimen avaaminen manuaalisesti {#manually-open-the-picker}

Avaa aikavalitsin ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Valitsimen asettamisen askel {#setting-the-picker-step}

Voit määrittää valitsimessa valittavien aikojen välin käyttäen `setStep()`. Tämä sallii sinulle hallita, kuinka valtavaa aikavaihtoehdot ovat - ihanteellinen aikataulutettaessa 15 minuutin välein.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askelrajoitus
Askeleen on jaettava tasaisesti tunti tai kokonainen päivä. Muutoin heitetään poikkeus.
:::

Tämä varmistaa, että alasvetoluettelo sisältää ennustettavia, tasaisesti aikavälein olevia arvoja kuten `09:00`, `09:15`, `09:30` jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa `MaskedTimeField` -komponenttia lisäämällä spinneri-ohjain, joka mahdollistaa käyttäjien kasvattaa tai pienentää aikaa nuolinäppäimillä tai käyttöliittymäpainikkeilla. Se tarjoaa ohjatumman vuorovaikutustavan, mikä on erityisen hyödyllistä työpöytätyyppisissä sovelluksissa.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vuorovaikutteinen aikavälin säätö:**
  Käytä nuolinäppäimiä tai pyöritys-painikkeita kasvataksesi tai pienentääksesi aikavälin arvoa.

- **Mukautettava pyöritysyksikkö:**
  Valitse, mitä aikakomponenttia haluat muokata `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoja ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Minimi/maximi-rajoitukset:**
  Tukee vähimmäis- ja enimmäisaikojen määrittämistä käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu ulostulo:**
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa `MaskedTimeField`-komponentissa.

### Esimerkki: Askel asettaminen tunnin mukaan {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylittely {#styling}

<TableBuilder name="MaskedTimeField" />
