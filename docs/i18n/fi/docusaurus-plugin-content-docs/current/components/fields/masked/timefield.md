---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 97e5bc068e72cfd770c26fed4ceca434
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstikenttä, joka antaa käyttäjien syöttää aikoja **numeroina** ja muotoilee syötteen automaattisesti määritellyn maskin mukaan, kun kenttä menettää fokuksen. Maski määrittää odotettavan aikamuodon, ohjaten sekä syöttöä että näyttöä. Komponentti tukee joustavaa analysointia, validointia, lokalisointia sekä arvon palauttamista johdonmukaista aikakäsittelyä varten.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö päivämääräsyöttöä?
`MaskedTimeField` on rakennettu **vain ajan** syöttöön. Jos etsit komponenttia, joka käsittelee **päivämääriä** vastaavalla maskipohjaisella muotoilulla, katso [`MaskedDateField`](./datefield.md).
:::

`MaskedTimeField` voidaan instansioida parametreilla tai ilman. Voit määrittää alkuarvon, etiketti, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää formaatti-indikaattoreita määrittämään, miten aikaa analysoidaan ja näytetään. Jokainen formaatti-indikaattori alkaa `%`-merkillä, jota seuraa kirjain, joka edustaa aikaosaa.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla tai analysoida aikoja samalla maskisynteksillä kentän ulkopuolella, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) hyötyluokkaa.
:::

### Aikamuotoindikaattorit {#time-format-indicators}

| Formaatti | Kuvaus              |
|-----------|---------------------|
| `%H`      | Tunti (24-tuntinen) |
| `%h`      | Tunti (12-tuntinen) |
| `%m`      | Minuutti            |
| `%s`      | Sekunti             |
| `%p`      | AM/PM               |

### Muokkaimet {#modifiers}

Muokkaimet tarkentavat aikakomponenttien näyttöä:

| Muokain | Kuvaus                      |
|---------|-----------------------------|
| `z`     | Nollalla täyttö             |
| `s`     | Lyhyt tekstiesitys          |
| `l`     | Pitkä tekstiesitys          |
| `p`     | Pakattu numero              |
| `d`     | Desimaali (oletusmuoto)    |

Nämä mahdollistavat joustavan ja paikallisystävällisen aikamuotoilun.

## Aikamuodon lokalisointi {#time-format-localization}

`MaskedTimeField` tukee lokalisointia asettamalla sopivan paikallisen asetuksen. Tämä varmistaa, että aikasyöttö ja -tulo vastaavat aluetasoisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, miten AM/PM-indikaattorit näytetään, miten erottimia käsitellään ja miten arvot analysoidaan.

## Analysointilogiikka {#parsing-logic}

`MaskedTimeField` analysoi käyttäjän syötteen määritetyn aikamaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet, joissa on tai ei ole erottimia, mahdollistaen joustavan syötön samalla varmistaen voimassa olevat ajat. Analysointikäyttäytyminen riippuu maskin määrittämästä formaattijärjestyksestä (esim. `%Hz:%mz` tunti/minuutti). Tämä formaatti määrää, miten numeerisia sekvenssejä tulkitaan.

### Esimerkit analysointitilanteista {#example-parsing-scenarios}

| Syöte  | Maski        | Tulkitsee Muuna |
|--------|--------------|------------------|
| `900`  | `%Hz:%mz`    | `09:00`          |
| `1345` | `%Hz:%mz`    | `13:45`          |
| `0230` | `%hz:%mz %p` | `02:30 AM`       |
| `1830` | `%hz:%mz %p` | `06:30 PM`       |

## Min/max-rajoitusten asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-komponentissa käyttäen `setMin()`- ja `setMax()`-menetelmiä:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat menetelmät hyväksyvät `java.time.LocalTime`-tyyppisiä arvoja. Määritetyn aikavälin ulkopuoliset syötteet katsotaan virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautusominaisuuden, joka nollaa kentän arvon ennalta määrättyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten peruuttamiseen tai takaisin oletusaikaan.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei tapahtumakuuntelijalla määritellä toisin)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validointikaavat {#validation-patterns}

Voit soveltaa asiakaspuolen validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmällä:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä kaava varmistaa, että vain arvot, jotka vastaavat `HH:mm`-muotoa (kaksi numeroa, kaksoispiste, kaksi numeroa), katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Kaavan on noudatettava JavaScriptin RegExp-syntaksia, kuten on dokumentoitu [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää analysoida ja muotoilla numeerisia aikasyötteitä nykyisen maskin perusteella. Kuitenkin käyttäjät voivat silti syöttää arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti validi mutta semanttisesti virheellinen tai analysoimaton (esim. `99:99`), se voi läpäistä kaavavalvonnan mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksen logiikassasi, vaikka säännöllinen lauseke on asetettu, jotta voit varmistaa, että aika on sekä kunnolla muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka antaa käyttäjien valita ajan visuaalisesti sen sijaan, että he kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai silloin, kun tarkka syöttö on tarpeen.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Aikavalitsimen käyttö {#accessing-the-picker}

Voit käyttää aikavalitsinta `getPicker()`-menetelmällä:

```java
TimePicker picker = field.getPicker();
```

### Valitsimen ikonin näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()`-menetelmää näyttääksesi tai piilottaaksesi kellon ikonin kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikon
```

### Automaattisesti avautuva käyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttamalla, painamalla Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Pakota valinta aikavalitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita ajan vain valitsimen avulla (eikä manuaalisesti kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // Avataan valitsin käyttäjän vuorovaikutuksessa
field.setAllowCustomValue(false);    // Poistaa manuaalisen tekstisyötön
```

Tämä asettelu varmistaa, että kaikki aikasyöte tulee valitsimen käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukan muotoilun hallinnan ja poistaa opiskeluprosesseista kirjoitettuja syötteitä. 
:::

### Aikavalitsimen avaaminen manuaalisesti {#manually-open-the-picker}

Avaa aikavalitsin ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Valitsimen askeleen määrittäminen {#setting-the-picker-step}

Voit määrittää valittavien aikojen välin valitsimessa käyttäen `setStep()`. Tämä mahdollistaa aikavaihtoehtojen kontrolloinnin—ihanteellinen skenaarioissa kuten aikataulutuksissa 15 minuutin välein.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askelrajoitus
Askel täytyy jakaa tasan tunnille tai koko päivälle. Muuten poikkeus heitetään.
:::

Tämä varmistaa, että alasvetoluettelossa on ennustettavia, tasaisesti jaettuja arvoja kuten `09:00`, `09:15`, `09:30` jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa [`MaskedTimeField`](#basics) lisäämällä spinni-ohjaimia, jotka antavat käyttäjien lisätä tai vähentää aikaa nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, mikä on erityisen hyödyllistä työpöytämallisissa sovelluksissa.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vuorovaikutteinen aikaskaalaminen:**  
  Käytä nuolinäppäimiä tai pyörityspainikkeita aikamuutoksen lisäämiseen tai vähentämiseen.

- **Mukautettava pyörimisyksikkö:**  
  Valitse, mitä osaa ajasta haluat muuttaa käyttäen `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoina ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Min./Max. rajat:**  
  Perii tuen minimien ja maksimiarvojen asettamiselle käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu ulostulo:**  
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa `MaskedTimeField`-komponentista.

### Esimerkki: Aikaskaalauksen määrittäminen tunnin mukaan {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylitys {#styling}

<TableBuilder name="MaskedTimeField" />
