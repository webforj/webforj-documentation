---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 5bd41c7d02fb7ae0c934db0a4e2ffb60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on tekstikenttä, joka sallii käyttäjien syöttävän päivämääriä numeroina ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski määrittää odotetun päivämäärämuodon, ohjaten sekä syötettä että näyttöä. Komponentti tukee joustavaa analysointia, validointia, lokalisaatiota ja arvon palauttamista johdonmukaiselle, aluekohtaiselle päivämääränkäsittelylle.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy ainoastaan **päivämäärä**-arvoihin. Jos tarvitset samankaltaista komponenttia ajan syöttämiseen ja muotoiluun, tutustu [`MaskedTimeField`](./timefield) -komponenttiin.
:::

`MaskedDateField` voidaan instanssia muodostaa parametreilla tai ilman. Voit määrittää alkusarjan, etiketin, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita maailmanlaajuisesti käytettyjä päivämäärämuotoja, jotka vaihtelevat päivän, kuukauden ja vuoden järjestyksessä. Yleisiä kaavoja ovat:

- **Päivä/Kuu/Vuosi** (yleinen useimmissa Euroopan maissa)
- **Kuu/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuu/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden formaatien sisällä paikalliset variaatiot sisältävät erottimen valinnan (esim. `-`, `/` tai `.`), ovatko vuodet kahden tai neljän numeron mittaisia, ja täytetäänkö yksinumeroiset kuukaudet tai päivät johtavilla nollilla.

Tämän monimuotoisuuden käsittelemiseksi `MaskedDateField` käyttää formaatti-indikaattoreita, jotka alkavat `%`:llä, jota seuraa kirjain, joka edustaa tiettyä päivämäärän osaa. Nämä indikaattorit määrittävät, miten syöte analysoidaan ja miten päivämäärä näytetään.

:::tip Maskien käyttö ohjelmallisesti
Voit muotoilla tai analysoida päivämääriä saman maskisynnin avulla kentän ulkopuolella käyttämällä [`MaskDecorator`](/docs/advanced/mask-decorator) -työkaluluokkaa.
:::

### Päivämäärämuotoindikaattorit {#date-format-indicators}

| Muoto | Kuvaus    |
| ------ | --------- |
| `%Y`   | Vuosi     |
| `%M`   | Kuukausi  |
| `%D`   | Päivä     |

### Modifikaattorit {#modifiers}

Modifikaattorit antavat parempaa hallintaa siitä, miten päivämäärän osat muotoillaan:

| Modifikaattori | Kuvaus                       |
| --------------- | ----------------------------- |
| `z`             | Nollatäyttö                   |
| `s`             | Lyhyt tekstiesitys           |
| `l`             | Pitkä tekstiesitys           |
| `p`             | Pakattu luku                 |
| `d`             | Desimaalimuoto (oletus)      |

Näitä voidaan yhdistää luomaan laaja valikoima päivämäärämaskeja.

## Päivämäärämuodon lokalisaatio {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin määrittämällä sopivan kieliasetuksen. Tämä varmistaa, että päivämäärät näytetään ja analysoidaan tavalla, joka vastaa käyttäjän odotuksia.

| Alue           | Muoto      | Esimerkki       |
| ---------------| -----------| ----------------|
| Yhdysvallat    | MM/DD/YYYY | `07/04/2023`    |
| Eurooppa       | DD/MM/YYYY | `04/07/2023`    |
| ISO-standardi  | YYYY-MM-DD | `2023-07-04`    |

Kansainvälistämiseksi käytetään `setLocale()`-menetelmää. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) arvon ja säätää automaattisesti sekä muotoilua että analysointia:

```java
dateField.setLocale(Locale.FRANCE);
```

## Analysointilogiikka {#parsing-logic}

`MaskedDateField` analysoi käyttäjän syötteen määritetyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erottimien kanssa tai ilman, mahdollistamalla joustavan syöttämisen samalla varmistaen voimassa olevat päivämäärät. Analysointikäyttäytyminen riippuu maskin määrittelemästä muodon järjestyksestä (esim. `%Mz/%Dz/%Yz` kuukaudelle/päivälle/vuodelle). Tämä muoto määrittää, miten numeeriset sekvenssit tulkitaan.

Esimerkiksi olettaen, että tänään on `15. syyskuuta 2012`, tämä on, miten erilaiset syötteet tulkitaan:

### Esimerkkianalysointitapaukset {#example-parsing-scenarios}

| Syöte                             | YMD (ISO)                                                                                                                                                                                 | MDY (US)                                                        | DMY (EU)                                                                                                                    |
| --------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -----------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>    | Yksi numero tulkitaan aina nykyisen kuukauden päiväksi, joten tämä olisi 1. syyskuuta 2012.                                                                                          | Sama kuin YMD                                                    | Sama kuin YMD                                                                                                           |
| <div align="center">`12`</div>   | Kaksi numeroa tulkitaan aina nykyisen kuukauden päiväksi, joten tämä olisi 12. syyskuuta 2012.                                                                                         | Sama kuin YMD                                                    | Sama kuin YMD                                                                                                           |
| <div align="center">`112`</div>  | Kolme numeroa tulkitaan 1-numeron kuukaudeksi ja 2-numeron päiväksi, joten tämä olisi 12. tammikuuta 2012.                                                                             | Sama kuin YMD                                                    | Kolme numeroa tulkitaan 1-numeron päiväksi ja 2-numeron kuukaudeksi, joten tämä olisi 1. joulukuuta 2012.              |
| <div align="center">`1004`</div> | Neljä numeroa tulkitaan MMDD-muotoiseksi, joten tämä olisi 4. lokakuuta 2012.                                                                                                           | Sama kuin YMD                                                    | Neljä numeroa tulkitaan DDMM-muotoiseksi, joten tämä olisi 10. huhtikuuta 2012.                                        |
| <div align="center">`020304`</div> | Kuusi numeroa tulkitaan YYMMDD-muotoiseksi, joten tämä olisi 4. maaliskuuta 2002.                                                                                                      | Kuusi numeroa tulkitaan MMDDYY-muotoiseksi, joten tämä olisi 3. helmikuuta 2004.                                      | Kuusi numeroa tulkitaan DDMMYY-muotoiseksi, joten tämä olisi 2. maaliskuuta 2004.                                       |
| <div align="center">`8 numeroa`</div> | Kahdeksan numeroa tulkitaan YYYYMMDD-muotoiseksi. Esimerkiksi `20040612` on 12. kesäkuuta 2004.                                                                                         | Kahdeksan numeroa tulkitaan MMDDYYYY-muotoiseksi. Esimerkiksi `06122004` on 12. kesäkuuta 2004.                        | Kahdeksan numeroa tulkitaan DDMMYYYY-muotoiseksi. Esimerkiksi `06122004` on 6. joulukuuta 2004.                          |
| <div align="center">`12/6`</div>  | Kaksi numeroa, jotka on eroteltu millä tahansa voimassa olevalla erottimella, tulkitaan MM/DD-muotoiseksi, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, lukuun ottamatta kirjaimia ja numeroita, ovat voimassa olevia erottimia. | Sama kuin YMD                                                    | Kaksi numeroa, jotka on eroteltu millä tahansa erottimella, tulkitaan DD/MM-muotoiseksi, joten tämä olisi 12. kesäkuuta 2012. |
| <div align="center">`3/4/5`</div>  | 5. huhtikuuta 2012                                                                                                                                                                       | 4. maaliskuuta 2005                                             | 3. huhtikuuta 2005                                                                                                       |


## Tekstuaalinen päivämäärän analysointi <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Oletuksena `MaskedDateField` hyväksyy vain numeerista syötettä päivämäärille. Voit kuitenkin sallia **tekstuaalisen päivämäärän analysoinnin** mahdollistamalla käyttäjien syöttää kuukausien ja päivien nimiä syötteessään. Tämä ominaisuus on erityisen hyödyllinen, kun halutaan luoda luonnollisempaa päivämäärän syöttöä.

Aktivoidaksesi tekstuaalisen analysoinnin, käytä `setTextualDateParsing()` -menetelmää:

```java
dateField.setTextualDateParsing(true);
```

### Kuukauden nimen korvaaminen {#month-name-substitution}

Kun tekstuaalinen analysointi on aktivoitu, voit käyttää erityisiä modifikaattoreita maskissasi hyväksyäksesi kuukausien nimet numeeristen arvojen sijasta:

- **`%Ms`** - Hyväksyy lyhyet kuukausien nimet (tammi, helmi, maalis jne.)
- **`%Ml`** - Hyväksyy pitkät kuukausien nimet (tammikuu, helmikuu, maaliskuu jne.)

Kuukauden nimet voivat esiintyä missä tahansa paikassa maskissa, ja kenttä hyväksyy silti numeerisen syötteen varoiksi.

#### Esimerkit

| Maski          | Syöte                | Tulos                                     |
| -------------- | -------------------- | ----------------------------------------- |
| `%Ms/%Dz/%Yz`  | `Sep/01/25`          | **Voimassa** - Analysoitu 1. syyskuuta 2025 |
| `%Ml/%Dz/%Yz`  | `September/01/25`    | **Voimassa** - Analysoitu 1. syyskuuta 2025 |
| `%Dz/%Ml/%Yz`  | `01/September/25`    | **Voimassa** - Analysoitu 1. syyskuuta 2025 |
| `%Mz/%Dz/%Yz`  | `09/01/25`          | **Voimassa** - Numeerinen varajärjestelmä toimii edelleen |

:::info
Kaikki 12 kuukautta ovat tuettuja sekä lyhyessä (tammi, helmi, maalis, huhti, touko, kesä, heinä, elo, syys, loka, marras, joulu) että pitkässä (tammikuu, helmikuu jne.) muodossa.
:::
### Päivän nimen koristelu {#day-name-decoration}

Viikonpäivän nimiä voidaan sisällyttää syötteeseen paremman luettavuuden vuoksi, mutta ne ovat **koristeellisia ainoastaan** ja ne poistetaan analysoinnin yhteydessä. Ne eivät vaikuta varsinaiseen päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyet päivän nimet (ma, ti, ke jne.)
- **`%Dl`** - Hyväksyy pitkät päivän nimet (maanantai, tiistai, keskiviikko jne.)

:::warning Päivän nimet vaativat numeerisen päivän
Kun käytetään viikonpäivän nimiä (`%Ds` tai `%Dl`), maskin **täytyy myös sisältää** `%Dz` tai `%Dd`, jotta varsinaista päivämäärän numeroa voidaan määrittää. Ilman numeerista päivätietoa syöte on virheellinen.
:::

#### Esimerkit

| Maski                | Syöte                     | Tulos                       |
| ---------------------| ------------------------- | --------------------------- |
| `%Ds %Mz/%Dz/%Yz`   | `Ma 09/01/25`            | **Voimassa** - Päivän nimi on koristeellinen |
| `%Dl %Mz/%Dz/%Yz`   | `Maanantai 09/01/25`     | **Voimassa** - Päivän nimi on koristeellinen |
| `%Mz/%Dz/%Yz %Ds`   | `09/01/25 Ti`            | **Voimassa** - Päivän nimi lopussa |
| `%Dl/%Mz/%Yz`       | `Maanantai/09/25`        | **Virheellinen** - Puuttuu `%Dz` |
| `%Mz/%Dl/%Yz`       | `09/Maanantai/25`        | **Virheellinen** - Puuttuu `%Dz` |

Kaikki 7 viikonpäivää ovat tuettuja sekä lyhyessä (ma, ti, ke, to, pe, la, su) että pitkässä (maanantai, tiistai jne.) muodossa.

### Lisäanalysointisäännöt {#additional-parsing-rules}

Tekstuaalinen päivämäärän analysointi sisältää useita hyödyllisiä ominaisuuksia:

- **Kirjainkoolla ei ole väliä:** Syötteet kuten `Maanantai 09/01/25`, `maanantai 09/01/25` tai `Maanantai 09/01/25` toimivat kaikki samalla tavalla.
- **Aluekohtaisesti tietoinen:** Kuukauden ja päivän nimien on vastattava kentän määritettyä paikallista asetusta. Esimerkiksi ranskassa käytä `septembre` etkä `September`. Englanninkielisiä nimiä ei tunnisteta, ellei kieliasetusta ole asetettu englanniksi.
  - Ranskalainen alue: `septembre/01/25` tunnistetaan syyskuuksi
  - Saksalainen alue: `Montag 09/01/25` tunnistetaan maanantaina olevan viikonpäivän nimeksi

## Min/max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField`-komponentissa käyttämällä `setMin()` ja `setMax()` -menetelmiä:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat menetelmät hyväksyvät arvot tyyppiä [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Määritettyjen rajojen ulkopuolinen syöte katsotaan virheelliseksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautusominaisuuden, joka nollaa kentän arvon ennalta määritettyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen palauttamiseen tai oletuspäivämäärän palauttamiseen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** kutsumalla `restoreValue()`
- **Näppäimistön kautta** painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusnäppäin, ellei tapahtumakuuntelijalla ole ohitettua)

Voit asettaa palautettavan arvon `setRestoreValue()`-menetelmällä, jossa siirrytään [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instanssin kanssa.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validointikaavat {#validation-patterns}

Voit soveltaa asiakaspohjaisia validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmällä:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä kaava varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY`-muotoa (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa), katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Kaavan on noudatettava JavaScript RegExp -syntaksia, kuten on dokumentoitu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää analysoida ja muotoilla numeerisia päivämääräsyötteitä tämänhetkisen maskin perusteella. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät muodollisesti vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai analysoimaton (esim. `99/99/9999`), se voi läpäistä kaavion tarkastukset mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lausekke kaavaa olisi asetettu, varmistaaksesi, että päivämäärä on sekä oikein muotoiltu että merkityksellinen.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka antaa käyttäjille mahdollisuuden valita päivämäärän visuaalisesti sen sijaan, että he kirjoittavat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai silloin, kun tarkkaa syöttöä tarvitaan.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta `getPicker()`-menetelmällä:

```java
DatePicker picker = dateField.getPicker();
```

### Näytä/piilota valitsimen kuvake {#showhide-the-picker-icon}

Käytä `setIconVisible()`-menetelmää näyttääksesi tai piilottaaksesi kalenterikuvakkeen kentän vieressä:

```java
picker.setIconVisible(true); // näyttää kuvakkeen
```

### Automaattinen avauskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. klikkaamalla, painamalla Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Pakota valinta valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita päivämäärän vain kalenterivalitsimen kautta (eivätkä kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avaa valitsimen käyttäjävuorovaikutuksen yhteydessä
dateField.setAllowCustomValue(false);    // Poistaa manuaalisen tekstisyötteen käytöstä
```

Tämä asetus takaa, että kaikki päivämääräsyöttö tapahtuu valitsimen käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukkaa muotoilun hallintaa ja poistaa kirjoitettujen syötteiden analysointiongelmat.
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

Valitsin voi valinnaisesti näyttää viikkonumerot kalenterinäkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) -komponenttia lisäämällä spinnereitä, jotka antavat käyttäjille mahdollisuuden lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, joka on erityisen hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vuorovaikutteinen päivämäärän säätäminen:**  
  Käytä nuolinäppäimiä tai kääntöpainikkeita päivämääräarvon lisäämiseksi tai vähentämiseksi.

- **Mukautettava käyttöyksikkö:**  
  Valitse, mikä osa päivämäärästä muokata käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehtoja ovat `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**  
  Perii tuen vähimmäis- ja enimmäispäivämäärille käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu ulostulo:**  
  Täydellinen yhteensopivuus maskien ja `MaskedDateField`-asetusten lokalisaation kanssa.

### Esimerkki: Määritä viikottainen säätö {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä tekee jokaisesta käännöksestä tai taaksepäin kulkevista päivämääristä yhden viikon eteen tai taakse.

## Tyylit {#styling}

<TableBuilder name="MaskedDateField" />
