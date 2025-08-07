---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 3d719856c08ce148bcd2999878d8c161
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstinsäntöohjain, joka on suunniteltu tarkkaan ja rakenteelliseen aikasyöttöön. Sen avulla käyttäjät voivat syöttää aikoja **numeroina** ja se muotoilee syötteen automaattisesti määritetyn maskin mukaan, kun kenttä menettää fokuksen. Maske on merkkijono, joka määrittää odotetun aikamuodon ohjaten sekä syöttämistä että näyttöä.

Tämä komponentti tukee joustavaa analysointia, validointia, lokalisointia ja arvon palauttamista. Se on erityisen hyödyllinen aikarajoitteisissa lomakkeissa, kuten aikatauluissa, työtunneissa ja varauksissa.

:::tip Etsitkö päivämääräsyöttöä?
`MaskedTimeField` on rakennettu **vain-aika** syöttöön. Jos etsit komponenttia, joka käsittelee **päivämääriä** samankaltaisella maskipohjaisella muotoilulla, tarkista [`MaskedDateField`](./datefield.md).
:::

## Perusteet {#basics}

`MaskedTimeField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkuarvon, etiketti, paikkamerkin ja tapahtumakuuntelijan arvomuutoksille.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää muotoindikaattoreita määrittämään, miten aikaa analysoidaan ja näytetään. Kukin muotoindikaattori alkaa `%`-merkillä, jota seuraa kirjain, joka edustaa aikakomponenttia.

### Aika muotoindikaattorit {#time-format-indicators}

| Muoto | Kuvaus            |
|-------|-------------------|
| `%H`  | Tunti (24-tuntinen) |
| `%h`  | Tunti (12-tuntinen) |
| `%m`  | Minute            |
| `%s`  | Sekunti           |
| `%p`  | AM/PM             |

### Modifikaattorit {#modifiers}

Modifikaattorit hienosäätelevät aikakomponenttien näyttöä:

| Modifikaattori | Kuvaus                      |
|----------------|------------------------------|
| `z`            | Nollatäyttö                  |
| `s`            | Lyhyt tekstiesitys          |
| `l`            | Pitkä tekstiesitys          |
| `p`            | Pakattava numero             |
| `d`            | Desimaali (oletusmuoto)     |

Nämä mahdollistavat joustavan ja paikallisesti ystävällisen aikamuotoilun.

## Aikamuotoilun lokalisointi {#time-format-localization}

`MaskedTimeField` tukee lokalisointia asettamalla sopivan alueen. Tämä varmistaa, että aikasyöttö ja -tulos vastaavat alueellisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, miten AM/PM-indikaattorit näytetään, miten erotinmerkit käsitellään ja miten arvot analysoidaan.

## Analysointilogiikka {#parsing-logic}

`MaskedTimeField` analysoi käyttäjän syötteen määritetyn aikamaskin mukaan. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet eri erottimilla, mahdollistaen joustavan syötön samalla varmistaen kelvolliset ajat. Analysointikäyttäytyminen riippuu maskin määrittämästä muotojärjestyksestä (esimerkiksi `%Hz:%mz` tunnille/minuutille). Tämä muoto määrittää, kuinka numeeriset sekvenssit tulkitaan.

### Esimerkit analysoinnista {#example-parsing-scenarios}

| Syöte  | Mask      | Tulkitut | 
|--------|-----------|----------| 
| `900`  | `%Hz:%mz` | `09:00`  | 
| `1345` | `%Hz:%mz` | `13:45`  | 
| `0230` | `%hz:%mz %p`  | `02:30 AM` | 
| `1830` | `%hz:%mz %p`  | `06:30 PM` | 

## Min/max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-komponentissa käyttämällä `setMin()` ja `setMax()` menetelmiä:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) -tyypin arvoja. Määrittyjen rajojen ulkopuoliset syötteet katsotaan kelvottomiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautusominaisuuden, joka nollaa kentän arvon ennaltamäärättyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten peruutuksessa tai palauttamista oletusaikaan.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusavain, ellei sitä ole ohitettu tapahtumakuuntelijalla)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validointikuvastot {#validation-patterns}

Voit soveltaa asiakaspään validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmää:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä malli varmistaa, että vain `HH:mm`-muotoiset arvot (kaksi numeroa, kaksoispiste, kaksi numeroa) katsotaan kelvollisiksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on noudatettava JavaScript RegExp -syntaksia, kuten on dokumentoitu [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteiden käsittelystä
Kenttä yrittää analysoida ja muotoilla numeerisia aikasyötteitä senhetkisen maskin mukaan. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti kelvollinen mutta semanttisesti virheellinen tai analysoimaton (esim. `99:99`), se voi läpäistä mallintarkistukset mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että aika on sekä oikein muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka antaa käyttäjien valita ajan visuaalisesti sen sijaan, että he kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai silloin, kun tarkkaa syöttöä tarvitaan.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit saada pääsyn aikavalitsimeen käyttämällä `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Valitsimen kuvakkeen näyttäminen/peittäminen {#showhide-the-picker-icon}

Käytä `setIconVisible()`-menetelmää näyttämään tai piilottamaan kelloikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää kuvakkeen
```

### Automaattinen avautumiskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä voi olla vuorovaikutuksessa kentän kanssa (esim. napsauttamalla, painamalla Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Pakota valinta valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita ajan vain valitsimella (eikä manuaalisesti kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // avaa valitsimen käyttäjävuorovaikutuksessa
field.setAllowCustomValue(false);    // estää manuaalisen tekstisyötteen
```

Tämä asetus takaa, että kaikki aikasyöttö tapahtuu valitsimen käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukkaa muotoilun hallintaa ja poistaa kirjoitettujen syötteiden analysoinnista johtuvat ongelmat.
:::

### Avaa valitsin manuaalisesti {#manually-open-the-picker}

Avaaksesi aikavalitsimen ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Valitsimen askeleen asettaminen {#setting-the-picker-step}

Voit määrittää valitsimen valittavien aikojen välin käyttämällä `setStep()`. Tämä mahdollistaa aikavaihtoehtojen hallinnan—ihanteellinen aikataulutuksessa 15 minuutin välein.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askelrajoitus
Askeleen on jaettava tasan tunti tai kokonainen päivä. Muuten virhe ilmestyy.
:::

Tämä varmistaa, että alasvetoluettelo sisältää ennakoitavia, tasaisesti jakautuneita arvoja, kuten `09:00`, `09:15`, `09:30`, jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa [`MaskedTimeField`](#basics) -komponenttia lisäämällä spinner-ohjaimia, joiden avulla käyttäjät voivat lisätä tai vähentää aikaa nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, erityisen hyödyllinen työpöytätyyppisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Keskeiset ominaisuudet {#key-features}

- **Interaktiivinen aikastep**:  
  Käytä nuolinäppäimiä tai pyörityspainikkeita aikavälin lisäämiseen tai vähentämiseen.

- **Mukautettava pyöritysyksikkö**:  
  Valitse mikä osa ajasta muokataan käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoja ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Min/max-rajoitukset**:  
  Perii tuen minimien ja maksimiarvojen asettamiselle `setMin()` ja `setMax()` avulla.

- **Muotoiltu tuloste**:  
  Täydellinen yhteensopivuus `MaskedTimeField`-maskien ja lokalisointiasetusten kanssa.

### Esimerkki: Määritä askeleet tunnin mukaan {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylittely {#styling}

<TableBuilder name="MaskedTimeField" />
