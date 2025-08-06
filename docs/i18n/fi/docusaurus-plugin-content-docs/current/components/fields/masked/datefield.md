---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: e2073fda6d7853bbacc6431c615e8cff
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on teksti syöttökontrolli, joka on suunniteltu rakenteellista päivämääräsyöttöä varten. Se antaa käyttäjille mahdollisuuden syöttää päivämääriä **numeroina** ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski on merkkijono, joka määrittelee odotetun päivämäärämuodon, ohjaten sekä syöttöä että näyttöä.

Tämä komponentti tukee joustavaa jäsentämistä, validointia, lokalisointia ja arvon palauttamista. Se on erityisen hyödyllinen lomakkeissa, kuten rekisteröinneissä, varauksissa ja aikataulutuksissa, joissa tarvitaan johdonmukaisia ja aluekohtaisia päivämäärämuotoja.

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy pelkästään **päivämäärä** arvoihin. Jos tarvitset vastaavaa komponenttia ajan syöttämiseen ja muotoiluun, tutustu sen sijaan [`MaskedTimeField`](./timefield).
:::

## Perusteet {#basics}

`MaskedDateField` voidaan alustaa parametreilla tai ilman. Voit määrittää aloitusarvon, etiketin, paikkamerkin sekä tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita eri päivämäärämuotoja, joita käytetään ympäri maailmaa, ja jotka vaihtelevat päivämäärän, kuukauden ja vuoden järjestyksen mukaan. Yleisimmät kaavat sisältävät:

- **Päivä/Kuukausi/Vuosi** (käytetään koko Euroopassa)
- **Kuukausi/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuukausi/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden muotojen sisällä paikalliset vaihtelut sisältävät erotinmerkin (esim. `-`, `/` tai `.`), ovatko vuodet kaksinumeroisia vai nelinumeroisia sekä täytetäänkö yksinumeroiset kuukaudet tai päivät johtavilla nollilla.

Tämän monimuotoisuuden käsittelemiseksi `MaskedDateField` käyttää muotoindikaattoreita, jotka alkavat jokainen `%`-merkillä, jota seuraa kirjain, joka edustaa tiettyä osaa päivämäärästä. Näitä indikaattoreita käytetään syötteen jäsentämiseen ja päivämäärän näyttämiseen.

### Päivämäärämuodon indikaattorit {#date-format-indicators}

| Muoto | Kuvaus         |
| ------ | -------------- |
| `%Y`   | Vuosi          |
| `%M`   | Kuukausi       |
| `%D`   | Päivä          |

### Muokkaimet {#modifiers}

Muokkaimet antavat enemmän hallintaa päivämäärän osien muotoiluun:

| Muokain | Kuvaus                      |
| ------- | --------------------------- |
| `z`     | Nollatäyttö                 |
| `s`     | Lyhyt tekstiesitys         |
| `l`     | Pitkä tekstiesitys          |
| `p`     | Pakattu numero              |
| `d`     | Desimaalinen (oletusmuoto)  |

Näitä voidaan yhdistää monenlaisten päivämäärämaskien luomiseksi.

## Päivämäärämuodon lokalisointi {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin asettamalla oikean alueen. Tämä varmistaa, että päivämäärät näytetään ja jäsennetään tavalla, joka vastaa käyttäjän odotuksia.

| Alue          | Muoto     | Esimerkki      |
| ------------- | --------- | -------------- |
| Yhdysvallat   | MM/DD/YYYY| `07/04/2023`   |
| Eurooppa      | DD/MM/YYYY| `04/07/2023`   |
| ISO-standardi | YYYY-MM-DD| `2023-07-04`   |

Lokalisoinnin soveltamiseen käytetään `setLocale()`-metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilua että jäsentämistä:

```java
dateField.setLocale(Locale.FRANCE);
```

## Jäsentämislogiikka {#parsing-logic}

`MaskedDateField` jäsentää käyttäjän syötteen määritellyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erottimilla tai ilman, mahdollistaen joustavan syöttämisen samalla varmistaen kelvolliset päivämäärät. Jäsentämiskäyttäytyminen riippuu maskin määrittelemästä muotoilujärjestyksestä (esim. `%Mz/%Dz/%Yz` kuukauden/päivän/vuoden mukaisesti). Tämä muoto määrittää, miten numeeriset sekvenssit tulkitaan.

Esimerkiksi olettaen, että tänään on `15. syyskuuta 2012`, näin erilaisia syötteitä tulkittaisiin:

### Esimerkkijäsentämistilanteet {#example-parsing-scenarios}

| Syöttö                             | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| --------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>     | Yksi numero tulkitaan aina tämän kuukauden päivänä, joten tämä olisi 1. syyskuuta 2012.                                                                                                           | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`12`</div>    | Kaksi numeroa tulkitaan aina tämän kuukauden päivänä, joten tämä olisi 12. syyskuuta 2012.                                                                                                      | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`112`</div>   | Kolme numeroa tulkitaan 1-numeroisena kuukausilukuina ja kahdella numerolla päivänä, joten tämä olisi 12. tammikuuta 2012.                                                                        | Sama kuin YMD                                                                         | Kolme numeroa tulkitaan 1-numeroisena päivänä ja kahden numeron kuukausilukuina, joten tämä olisi 1. joulukuuta 2012.         |
| <div align="center">`1004`</div>  | Neljä numeroa tulkitaan MMDD-muodossa, joten tämä olisi 4. lokakuuta 2012.                                                                                                                      | Sama kuin YMD                                                                         | Neljä numeroa tulkitaan DDMM-muodossa, joten tämä olisi 10. huhtikuuta 2012.                                                |
| <div align="center">`020304`</div> | Kuusi numeroa tulkitaan YYMMDD-muodossa, joten tämä olisi 4. maaliskuuta 2002.                                                                                                                 | Kuusi numeroa tulkitaan MMDDYY-muodossa, joten tämä olisi 3. helmikuuta 2004.       | Kuusi numeroa tulkitaan DDMMYY-muodossa, joten tämä olisi 2. maaliskuuta 2004.                                             |
| <div align="center">`8 numeroa`</div> | Kahdeksan numeroa tulkitaan YYYYMMDD-muodossa. Esimerkiksi `20040612` on 12. kesäkuuta 2004.                                                                                                     | Kahdeksan numeroa tulkitaan MMDDYYYY-muodossa. Esimerkiksi `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan DDMMYYYY-muodossa. Esimerkiksi `06122004` on 6. joulukuuta 2004.                                 |
| <div align="center">`12/6`</div>   | Kaksi numeroa, jotka on erotettu voimassa olevalla erottimella, tulkitaan MM/DD-muodossa, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, lukuun ottamatta kirjaimia ja numeroita, ovat voimassa olevia erottimia. | Sama kuin YMD                                                                         | Kaksi numeroa, jotka on erotettu minkä tahansa erottimen, tulkitaan DD/MM-muodossa, joten tämä olisi 12. kesäkuuta 2012.     |
| <div align="center">`3/4/5`</div>   | Huhtikuun 5. päivä, 2012                                                                                                                                                                          | Maaliskuun 4. päivä, 2005                                                               | Huhtikuun 3. päivä, 2005                                                                                                     |

## Minimivuuden/maximivuuden asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField`-kentässä käyttämällä `setMin()` ja `setMax()` -menetelmiä:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) tyyppisiä arvoja. Syöte, joka on määritellyn alueen ulkopuolella, katsotaan kelpaamattomaksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautustoiminnon, joka palauttaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen kumoamiseksi tai palauttamiseksi oletuspäivämääräksi.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusnäppäin, ellei sitä ohiteta tapahtumakuuntelijalla)

Voit asettaa palautettavan arvon `setRestoreValue()`, joka ottaa [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instanssin.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validointikaaviot {#validation-patterns}

Voit soveltaa asiakaspohjaisia validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmällä:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä malli varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY` muotoa (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa), katsotaan kelvollisiksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on noudatettava JavaScript RegExp -syntaksia, kuten on asiakirjoitettu [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomioita syötteen käsittelystä
Kenttä pyrkii jäsentämään ja muotoilemaan numeerisia päivämääräsyötteitä nykyisen maskin perusteella. Käyttäjät voivat silti manuaalisesti syöttää arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti kelvollinen mutta semanttisesti virheellinen tai jäsentämätön (esim. `99/99/9999`), se voi läpäistä mallin tarkistukset mutta epäonnistua loogisessa validoinnissa.
Käyttäjän syötearvo tulisi aina validoida sovelluksen logiikassa, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että päivämäärä on sekä oikein muotoiltu että mielekäs.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka antaa käyttäjien valita päivämäärän visuaalisesti sen sijaan, että kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkka syöttö on tarpeen.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta käyttämällä `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Valitsimen ikonin näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()`-menetelmää näyttämään tai piilottamaan kalenteri-ikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattinen avautuminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enteriä tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Valinnan pakottaminen valitsimen kautta
Jotta voit varmistaa, että käyttäjät voivat vain valita päivämäärän kalenterivalitsimen avulla (eikä kirjoittamalla itse), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avataan valitsin käyttäjän vuorovaikutuksessa
dateField.setAllowCustomValue(false);    // Estää manuaalisen tekstin syötön
```

Tämä asetus varmistaa, että kaikki päivämääräsyötteet tulevat valitsin käyttöliittymästä, mikä on hyödyllistä, kun haluat tiukkaa muotoilun hallintaa ja poistaa jäsentämisongelmat kirjoitetuista syötteistä.
:::

### Kalenterin manuaalinen avaaminen {#manually-open-the-calendar}

Avaa kalenteri ohjelmallisesti:

```java
picker.open();
```

Tai käytä alias:

```java
picker.show(); // sama kuin open()
```

### Viikkojen näyttäminen kalenterissa {#show-weeks-in-the-calendar}

Valitsin voi vaihtoehtoisesti näyttää viikkonumerot kalenterinäytössä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) lisäämällä pyöräytysohjaimia, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, joka on erityisen hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Avainominaisuudet {#key-features}

- **Vuorovaikutteinen päivämäärän askel**:  
  Käytä nuolinäppäimiä tai pyöräytysnappeja päivämääräarvon lisäämiseksi tai vähentämiseksi.

- **Mukautettava askellus**:  
  Valitse, mitä osaa päivämäärästä muokata käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehdot sisältävät `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset**:  
  Perii tuen vähimmäis- ja enimmäispäivämäärille käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu tulos**:  
  Täysin yhteensopiva `MaskedDateField`:n maskien ja lokalisointiasetusten kanssa.

### Esimerkki: Määritä viikoittainen askel {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä tekee jokaisesta pyöräytysaskeleestä viikon edistämisen tai taaksepäin menemisen. 

## Tyylittely {#styling}

<TableBuilder name="MaskedDateField" />
