---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: 433c612e16b0476f720deb896cb73f4a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on tekstikenttä, joka antaa käyttäjille mahdollisuuden syöttää päivämääriä numeroina ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski määrittelee odotetun päivämäärämuodon, ohjaten sekä syöttöä että esitystä. Komponentti tukee joustavaa analysointia, validointia, lokalisointia ja arvon palauttamista johdonmukaista, aluekohtaisesti tarkkaa päivämääräkäsittelyä varten.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy pelkästään **päivämäärä**arvoihin. Jos tarvitset vastaavan komponentin aikojen syöttämiseen ja muotoiluun, tutustu sen sijaan [`MaskedTimeField`](./timefield).
:::

`MaskedDateField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkuperäisen arvon, etiketin, paikkamerkin ja tapahtumakuuntelijan arvon muutoksille.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita eri päivämäärämuotoja eri puolilla maailmaa, jotka vaihtelevat päivän, kuukauden ja vuoden järjestyksen mukaan. Yleisiä kaavoja ovat:

- **Päivä/Kuu/Vuosi** (käytetään lähes koko Euroopassa)
- **Kuu/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuu/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden muotojen sisällä paikalliset variaatiot sisältävät erotinmerkin valinnan (esim. `-`, `/` tai `.`), ovatko vuodet kaksinumeroisia vai nelinumeroisia, ja ovatko yksinumeroiset kuukaudet tai päivät täytettyjä nollilla.

Tämän monimuotoisuuden käsittelemiseksi `MaskedDateField` käyttää formaatti-indikaattoreita, jotka kaikki alkavat prosenttimerkillä `%`, jota seuraa kirjain, joka edustaa tiettyä päivämäärän osaa. Nämä indikaattorit määrittävät, miten syöte analysoidaan ja miten päivämäärä näytetään.

:::tip Maskien käyttäminen ohjelmallisesti
Muotoiltaessa tai analysoitaessa päivämääriä saman maskisynnnin avulla kentän ulkopuolella käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -työkaluluokkaa.
:::

### Päivämäärämuotoindikaattorit {#date-format-indicators}

| Muoto  | Kuvaus     |
| ------ | ---------- |
| `%Y`   | Vuosi      |
| `%M`   | Kuu       |
| `%D`   | Päivä      |

### Muokkaimet {#modifiers}

Muokkaimet antavat enemmän kontrollia siitä, miten päivämäärän komponentit muotoillaan:

| Muokkain | Kuvaus                      |
| -------- | --------------------------- |
| `z`      | Nollatäyttö                 |
| `s`      | Lyhyt tekstiesitys         |
| `l`      | Pitkä tekstiesitys         |
| `p`      | Pakattu numero              |
| `d`      | Kymmenjärjestelmä (oletusmuoto) |

Näitä voidaan yhdistää, jotta voidaan luoda monenlaisia päivämäärämaskuja.

## Päivämäärämuotojen lokalisointi {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin asettamalla asianmukaisen kulttuurin. Tämä varmistaa, että päivämäärät esitetään ja analysoidaan tavalla, joka vastaa käyttäjien odotuksia.

| Alue           | Muoto       | Esimerkki      |
| -------------- | ----------- | -------------- |
| Yhdysvallat    | MM/DD/YYYY  | `07/04/2023`   |
| Eurooppa       | DD/MM/YYYY  | `04/07/2023`   |
| ISO-standardi  | YYYY-MM-DD  | `2023-07-04`   |

Lokalisoinnin soveltamiseksi käytä `setLocale()`-metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilua että analysointia:

```java
dateField.setLocale(Locale.FRANCE);
```

## Analysointilogiikka {#parsing-logic}

`MaskedDateField` analysoi käyttäjän syötteen määritellyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeromuotoiset syötteet erottimien kanssa tai ilman, mikä mahdollistaa joustavan syöttämisen samalla varmistaen, että päivämäärät ovat voimassa.
Analysointikäyttäytyminen riippuu maskissa määritellystä muodosta (esim. `%Mz/%Dz/%Yz` kuukaudesta/päivästä/vuodesta). Tämä muoto määrittelee, miten numerojärjestykset tulkitaan.

Esimerkiksi, olettaen, että tänään on `15. syyskuuta 2012`, näin erilaisia syötteitä analysoitaisiin:

### Esimerkkianalysointiskenaariot {#example-parsing-scenarios}

| Syöte                              | YMD (ISO)                                                                                                                                                                   | MDY (Yhdysvallat)                                                                   | DMY (Eurooppa)                                                                                                              |
| ---------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>       | Yksi numero tulkitaan aina nykyisen kuukauden päivänä, joten tämä olisi 1. syyskuuta 2012.                                                                                  | Sama kuin YMD                                                                       | Sama kuin YMD                                                                                                             |
| <div align="center">`12`</div>      | Kaksi numeroa tulkitaan aina nykyisen kuukauden päivänä, joten tämä olisi 12. syyskuuta 2012.                                                                               | Sama kuin YMD                                                                       | Sama kuin YMD                                                                                                             |
| <div align="center">`112`</div>     | Kolme numeroa tulkitaan 1-numeroisena kuukausina ja 2-numeroisena päivänä, joten tämä olisi 12. tammikuuta 2012.                                                          | Sama kuin YMD                                                                       | Kolme numeroa tulkitaan 1-numeroisena päivänä ja 2-numeroisena kuukautena, joten tämä olisi 1. joulukuuta 2012.          |
| <div align="center">`1004`</div>    | Neljä numeroa tulkitaan MMDD-muotoiseksi, joten tämä olisi 4. lokakuuta 2012.                                                                                             | Sama kuin YMD                                                                       | Neljä numeroa tulkitaan DDMM-muotoiseksi, joten tämä olisi 10. huhtikuuta 2012.                                          |
| <div align="center">`020304`</div>  | Kuusi numeroa tulkitaan YYMMDD-muotoiseksi, joten tämä olisi 4. maaliskuuta 2002.                                                                                         | Kuusi numeroa tulkitaan MMDDYY-muotoiseksi, joten tämä olisi 3. helmikuuta 2004. | Kuusi numeroa tulkitaan DDMMYY-muotoiseksi, joten tämä olisi 2. maaliskuuta 2004.                                        |
| <div align="center">`8 numeroa`</div> | Kahdeksan numeroa tulkitaan YYYYMMDD-muotoiseksi. Esimerkiksi, `20040612` on 12. kesäkuuta 2004.                                                                             | Kahdeksan numeroa tulkitaan MMDDYYYY-muotoiseksi. Esimerkiksi, `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan DDMMYYYY-muotoiseksi. Esimerkiksi, `06122004` on 6. joulukuuta 2004.                          |
| <div align="center">`12/6`</div>     | Kaksi numeroa, jotka on erotettu minkä tahansa voimassa olevan erottimen avulla, tulkitaan MM/DD-muotoiseksi, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, paitsi kirjaimet ja numerot, lasketaan voimassa oleviksi erottimiksi. | Sama kuin YMD                                                                       | Kaksi numeroa, jotka on erotettu minkä tahansa erottimen avulla, tulkitaan DD/MM-muotoiseksi, joten tämä olisi 12. kesäkuuta 2012. |
| <div align="center">`3/4/5`</div>    | Huhtikuun 5, 2012                                                                                                                                                            | Maaliskuun 4, 2005                                                                   | Huhtikuun 3, 2005                                                                                                         |


## Tekstuaalinen päivämäärän analysointi <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Oletusarvoisesti `MaskedDateField` hyväksyy vain numeerista syöttöä päivämäärille. Voit kuitenkin mahdollistaa **tekstuaalisen päivämäärän analysoinnin** salliaksesi käyttäjien syöttää kuukausi- ja päivämääränimet syötteessään. Tämä ominaisuus on erityisen hyödyllinen luonnollisen päivämäärän syöttämisen helpottamiseksi.

Ottaaksesi käyttöön tekstuaalisen analysoinnin, käytä `setTextualDateParsing()` -metodia:

```java
dateField.setTextualDateParsing(true);
```

### Kuukauden nimen korvaminen {#month-name-substitution}

Kun tekstuaalinen analysointi on käytössä, voit käyttää erityisiä muokkaimia maskissasi hyväksyäksesi kuukausinimiä numeeristen arvojen sijaan:

- **`%Ms`** - Hyväksyy lyhyet kuukausinimet (Tam, Hel, Mar jne.)
- **`%Ml`** - Hyväksyy pitkät kuukausinimet (Tammikuu, Helmikuu, Maaliskuu jne.)

Kuukauden nimet voivat esiintyä maskissa missä tahansa asemassa, ja kenttä hyväksyy edelleen numeerisen syötteen varalla.

#### Esimerkit {#examples}

| Maski         | Syöte           | Tulos                               |
| -------------- | --------------- | ----------------------------------- |
| `%Ms/%Dz/%Yz`  | `Sep/01/25`     | **Voimassa** - Analysoidaan 1. syyskuuta 2025 |
| `%Ml/%Dz/%Yz`  | `September/01/25` | **Voimassa** - Analysoidaan 1. syyskuuta 2025 |
| `%Dz/%Ml/%Yz`  | `01/September/25` | **Voimassa** - Analysoidaan 1. syyskuuta 2025 |
| `%Mz/%Dz/%Yz`  | `09/01/25`      | **Voimassa** - Numeerinen varalle on vielä käytössä |

:::info
Kaikki 12 kuukautta tukevat lyhyitä (Tam, Hel, Mar, Huht, Tou, Kes, Heinä, Elo, Syys, Loka, Marras, Joulukuu) ja pitkiä (Tammikuu, Helmikuu, jne.) muotoja.
:::
### Päivän nimen koristelu {#day-name-decoration}

Viikkopäivän nimet voidaan sisällyttää syötteeseen paremman luettavuuden vuoksi, mutta ne ovat **koristeellisia vain** ja poistetaan analysoinnin aikana. Ne eivät vaikuta varsinaiseen päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyet viikonpäivän nimet (Ma, Ti, Ke jne.)
- **`%Dl`** - Hyväksyy pitkät viikonpäivän nimet (Maanantai, Tiistai, Keskiviikko jne.)

:::warning Viikonpäivän nimet vaativat numeerisen päivän
Käytettäessä viikonpäivän nimiä (`%Ds` tai `%Dl`), maskisi **täytyy myös sisältää** `%Dz` tai `%Dd` määrittämään varsinaisen päivän numero. Ilman numeerista päivämääräkomponenttia syöte on virheellinen.
:::

#### Esimerkit {#examples-1}

| Maski               | Syöte                | Tulos                                |
| ------------------- | -------------------- | ------------------------------------ |
| `%Ds %Mz/%Dz/%Yz`   | `Ma 09/01/25`        | **Voimassa** - Päivän nimi on koristeellinen |
| `%Dl %Mz/%Dz/%Yz`   | `Maanantai 09/01/25` | **Voimassa** - Päivän nimi on koristeellinen |
| `%Mz/%Dz/%Yz %Ds`   | `09/01/25 Ti`        | **Voimassa** - Päivän nimi lopussa |
| `%Dl/%Mz/%Yz`       | `Maanantai/09/25`    | **Virheellinen** - Puuttuu `%Dz`   |
| `%Mz/%Dl/%Yz`       | `09/Maanantai/25`    | **Virheellinen** - Puuttuu `%Dz`   |

Kaikki 7 viikonpäivää tukevat lyhyitä (Ma, Ti, Ke, To, Pe, La, Su) ja pitkiä (Maanantai, Tiistai jne.) muotoja.

### Lisäanalysointisäännöt {#additional-parsing-rules}

Tekstuaalinen päivämäärän analysointi sisältää useita hyödyllisiä ominaisuuksia:

- **Ei-herkkä tapauksille:** Syöte kuten `MAANANTAI 09/01/25`, `maanantai 09/01/25`, tai `Maanantai 09/01/25` toimivat kaikki samalla tavalla.
- **Kulttuurintietoinen:** Kuukauden ja päivän nimet on sovitettava kentän määriteltyyn kulttuuriin. Esimerkiksi ranskalaisella kulttuurilla käytetään `septembre` eikä `September`. Englanninkieliset nimet eivät tule tunnistetuksi, ellet kulttuuri ole asetettu englanniksi.
  - Ranskalainen kulttuuri: `septembre/01/25` tunnistetaan syyskuuna
  - Saksalainen kulttuuri: `Montag 09/01/25` tunnistetaan maanantaina viikonpäivänä

## Min/Max-rajoitusten asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField`-komponentissa käyttämällä `setMin()` ja `setMax()` -metodeja:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat metodit hyväksyvät [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) -tyyppisiä arvoja. Syöte, joka on määritellyn alueen ulkopuolella, katsotaan virheelliseksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautusominaisuuden, joka palauttaa kentän arvon esiasetettuun tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen palauttamiseksi tai oletuspäivämäärään palauttamiseksi.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusnäppäin, ellei tapahtumakuuntelijalla toisin määritellä)

Voit asettaa palautettavan arvon käyttämällä `setRestoreValue()`, jossa on [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) -instanssi.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validointikaaviot {#validation-patterns}

Voit soveltaa asiakaspohjaisia validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` -metodin kanssa:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä kaavio varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY` -muotoa (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa) ovat voimassa.

:::tip Säännöllisen lausekkeen muoto
Kaavion on noudatettava JavaScript RegExp -syntaksia, kuten asiakirjoissa on kuvattu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää analysoida ja muotoilla numeerisia päivämääräsyötteitä nykyisen maskin perusteella. Käyttäjät voivat silti kuitenkin syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semantisesti virheellinen tai ei-analysoitavissa (esim. `99/99/9999`), se saattaa läpäistä kaaviotarkistukset, mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syöttöarvo sovelluksesi logiikassa, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että päivämäärä on sekä oikein muotoiltu että merkityksellinen.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka antaa käyttäjille mahdollisuuden valita päivämäärä visuaalisesti sen sijaan, että tyyppisi sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkkaa syöttöä tarvitaan.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Pääsy valitsimelle {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta kutsumalla `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Valitsimen ikonin näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` -metodia näyttääksesi tai piilottaaksesi kalenterikuvakkeen kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikon
```

### Automaattinen avauskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä käyttää kenttää (esim. klikkaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Pakota valitseminen valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita päivämäärän vain kalenterivalitsimella (eikä voi kirjoittaa manuaalisesti), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avaa valitsimen käyttäjän vuorovaikutuksessa
dateField.setAllowCustomValue(false);    // Poistaa manuaalisen tekstisyötön käytöstä
```

Tämä asetus varmistaa, että kaikki päivämääräsyötteet tulevat valitsin käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukkaa muotoilun hallintaa ja poistaa tyypitysyksilöinnistä syntyvät ongelmat.
:::

### Avaa kalenteri manuaalisesti {#manually-open-the-calendar}

Avaa kalenteri ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Näytä viikot kalenterissa {#show-weeks-in-the-calendar}

Valitsin voi valinnaisesti näyttää viikon numerot kalenterinäkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) -komponenttia lisäämällä spinnereitä, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustavan, mikä on erityisen hyödyllistä työpöytäsovelluksissa.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vuorovaikutteinen päivämäärän liianminen:**
  Käytä nuolinäppäimiä tai kiertopainikkeita päivämääräarvon korottamiseksi tai vähentämiseksi.

- **Mukautettava askeleen yksikkö:**
  Valitse, mitä päivämäärän osaa haluat muuttaa käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehtoja ovat `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**
  Perii tuen vähimmäismaksimi päivämäärille `setMin()` ja `setMax()` -metodien avulla.

- **Muotoiltu tuloste:**
  Täydellinen yhteensopivuus `MaskedDateField`-maskien ja lokalisointiasetusten kanssa.

### Esimerkki: Määritä viikoittainen asettelu {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä tekee jokaisesta pyörimiskierrosta päivämäärän eteenpäin tai taaksepäin yhdellä viikolla.

## Tyylittely {#styling}

<TableBuilder name="MaskedDateField" />
