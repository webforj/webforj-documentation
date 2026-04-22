---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: bfaa13bee2b1c6dd1c88c8af989a6532
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

`MaskedTimeField` on tekstikenttä, joka antaa käyttäjille mahdollisuuden syöttää aikoja **numeroina** ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski määrittää odotetun aikamuodon, ohjaten sekä syötettä että näyttöä. Komponentti tukee joustavaa jäsennystä, validointia, lokalisointia ja arvon palauttamista yhdenmukaiselle aikakäsittelylle.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö päivämääräsyötettä?
`MaskedTimeField` on rakennettu **vain-aika** syötteelle. Jos etsit komponenttia, joka käsittelee **päiviä** samankaltaisella maskipohjaisella muotoilulla, tutustu [`MaskedDateField`](./datefield.md).
:::

`MaskedTimeField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkupäivämäärän, etiketin, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedTimeField` käyttää formaattielementtejä määrittääkseen, kuinka aikaa jäsennetään ja näytetään. Jokainen formaattielementti alkaa `%`-merkillä, jota seuraa kirjain, joka edustaa aikakomponenttia.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla tai jäsentää aikoja saman maskisyntaksin avulla kentän ulkopuolella, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) apuluokkaa.
:::

### Aikamuotoindikaattorit {#time-format-indicators}

| Muoto   | Kuvaus                |
|---------|-----------------------|
| `%H`    | Tunti (24-tuntinen)   |
| `%h`    | Tunti (12-tuntinen)   |
| `%m`    | Minuutti              |
| `%s`    | Sekunti               |
| `%p`    | AM/PM                 |

### Muuttujat {#modifiers}

Muuttujat tarkentavat aikakomponenttien näyttöä:

| Muuttuja | Kuvaus                    |
|----------|---------------------------|
| `z`      | Nollalla täyttävä         |
| `s`      | Lyhyt tekstiesitys        |
| `l`      | Pitkä tekstiesitys         |
| `p`      | Pakattu numero             |
| `d`      | Desimaali (oletusmuoto)  |

Nämä mahdollistavat joustavan ja paikallisystävällisen aikamuotoilun.

## Aikamuotoilun lokalisointi {#time-format-localization}

`MaskedTimeField` tukee lokalisointia määrittämällä sopiva lokaali. Tämä varmistaa, että aikasyöte ja -tulo vastaavat alueellisia käytäntöjä.

```java
field.setLocale(Locale.GERMANY);
```

Tämä vaikuttaa siihen, kuinka AM/PM-indikaattorit näytetään, kuinka erotinmerkkejä käsitellään ja kuinka arvot jäsennetään.

## Jäsentämislogiikka {#parsing-logic}

`MaskedTimeField` jäsentää käyttäjän syötteen määritetyn aikamaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet, joissa on tai ei ole erottimia, mahdollistamalla joustavan syötteen samalla kun varmistaa voimassa olevat ajat. Jäsentämis käyttäytyminen riippuu maskissa määritetystä formaattijärjestyksestä (esimerkiksi `%Hz:%mz` tunnin/minuutin osalta). Tämä formaatti määrittää, kuinka numeeriset sekvenssit tulkitaan.

### Esimerkit jäsentämisestä {#example-parsing-scenarios}

| Syöte  | Maski        | Tulkitaan Muotoon |
|--------|--------------|--------------------|
| `900`  | `%Hz:%mz`    | `09:00`            |
| `1345` | `%Hz:%mz`    | `13:45`            |
| `0230` | `%hz:%mz %p` | `02:30 AM`         |
| `1830` | `%hz:%mz %p` | `06:30 PM`         |

## Min/Max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun aikavälin `MaskedTimeField`-kentässä käyttämällä `setMin()` ja `setMax()` metodeja:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Molemmat metodit hyväksyvät [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html) tyypin arvoja. Määrittämättömät arvot otetaan huomioon voimattomina.

## Arvon palauttaminen {#restoring-the-value}

`MaskedTimeField` sisältää palautusominaisuuden, joka nollaa kentän arvon esiasetettuun tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä muutosten kumoamisessa tai oletusaikaan palaamisessa.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletus palautusnäppäin, ellei sitä ole ohitettu tapahtumakuuntelijalla)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validointimallit {#validation-patterns}

Voit soveltaa asiakaspään validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` metodilla:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Tämä malli varmistaa, että vain arvot, jotka vastaavat `HH:mm` muotoa (kaksi numeroa, kaksoispiste, kaksi numeroa), otetaan huomioon voimassa olevina.

:::tip Säännöllisen lausekkeen muoto
Mallin on seurattava JavaScriptin RegExp-syntaksia, kuten on dokumentoitu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää jäsentää ja muotoilla numeeriset aikasyötteet nykyisen maskin perusteella. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai jäsentämätön (esim. `99:99`), se voi läpäistä mallintarkistukset, mutta epäonnistua loogisessa validoinnissa. 
Sinun tulisi aina validoida syötearvo sovelluslogiikassasi, vaikka säännöllinen lausekemalli olisi asetettu, varmistaaksesi, että aika on sekä oikein muotoiltu että merkityksellinen.
:::

## Aikavalitsin {#time-picker}

`MaskedTimeField` sisältää sisäänrakennetun aikavalitsimen, joka antaa käyttäjille mahdollisuuden valita aika visuaalisesti sen sijaan, että kirjoittaisivat sen. Tämä parantaa käyttökokemusta vähemmän teknisille käyttäjille tai kun tarkka syöttö on tarpeen.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit päästä aikavalitsimeen käyttämällä `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Valitsimen kuvakkeen näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` näyttääksesi tai piilottaaksesi kelloikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattinen avautuminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enteriä tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Valinnan pakottaminen valitsimen kautta
Jotta varmistetaan, että käyttäjät voivat valita ajan vain käyttämällä valitsinta (eivätkä manuaalisesti kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
field.getPicker().setAutoOpen(true); // avaa valitsimen käyttäjän vuorovaikutuksessa
field.setAllowCustomValue(false);    // poistaa manuaalisen tekstisyötön käytöstä
```

Tämä asetus takaa, että kaikki aikasyötteet tulevat valitsimen käyttöliittymästä, mikä on hyödyllistä, kun haluat tiukkaa muotoilun valvontaa ja poistaa kirjoitetuista syötteistä aiheutuvat jäsentämisongelmat.
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

### Valitsimen askeleen määrittäminen {#setting-the-picker-step}

Voit määrittää aikavalitsimessa valittavien aikojen välin käyttämällä `setStep()`. Tämä mahdollistaa ajoitusten ohjaamisen—ihanteellinen skenaarioissa, kuten aikatauluttamisessa 15 minuutin välein.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Askeleen rajoitus
Askeleen on jaettava tasan tunti tai koko päivä. Muutoin poikkeus heitetään.
:::

Tämä varmistaa, että alasvetoluettelo sisältää ennakoitavia, tasaisesti jaettuja arvoja, kuten `09:00`, `09:15`, `09:30`, jne.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

`MaskedTimeFieldSpinner` laajentaa [`MaskedTimeField`](#basics) lisäämällä spinnereitä, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää aikaa nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustavan, erityisesti hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Keskeiset ominaisuudet {#key-features}

- **Vuorovaikutteinen aikaskriptiminen:**  
  Käytä nuolinäppäimiä tai kiertopainikkeita aikamuuttujan lisäämiseksi tai vähentämiseksi.

- **Mukautettava kiertoyksikkö:**  
  Valitse, mikä aikaosa muutetaan käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Vaihtoehtoina ovat `HOUR`, `MINUTE`, `SECOND` ja `MILLISECOND`.

- **Min/Max-rajoitukset:**  
  Perii tuen sallituista aikarajoista käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu tulos:**  
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa `MaskedTimeField`-sovelluksesta.

### Esimerkki: Askelhaavan määrittäminen tunnin mukaan {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Tyylit {#styling}

<TableBuilder name="MaskedTimeField" />
