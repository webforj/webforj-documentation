---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: b327e838739be59acec7bfbedc3bb027
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on tekstikenttä, joka sallii käyttäjien syöttää päivämääriä numeroina ja muotoilee syötteen automaattisesti määritetyn maskin mukaan, kun kenttä menettää keskittymisen. Maski määrittää odotetun päivämäärämuodon, ohjaten sekä syötettä että näyttöä. Komponentti tukee joustavaa analysointia, validaatiota, lokalisointia ja arvon palauttamista johdonmukaiselle, aluekohtaiselle päivämäärä käsittelylle.

<!-- INTRO_END -->

:::tip Etsitkö aikasyötettä?
`MaskedDateField` keskittyy yks exclusivisesti **päivämäärä** arvoihin. Jos tarvitset vastaavaa komponenttia aikojen syöttämiseen ja muotoiluun, katso [MaskedTimeField](/docs/components/fields/masked/timefield) sen sijaan.
:::

`MaskedDateField` voidaan instansioida parametreilla tai ilman. Voit määrittää alkuperäisen arvon, etiketin, paikkamerkin ja tapahtumankuuntelijan arvojen muutoksille.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita päivämäärämuotoja, joita käytetään ympäri maailmaa, ja jotka vaihtelevat päivän, kuukauden ja vuoden järjestyksen mukaan. Yleisiä kaavoja ovat:

- **Päivä/Kuu/Vuosi** (käytetään suurimmassa osassa Eurooppaa)
- **Kuu/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuu/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden muotojen sisällä paikalliset variaatiot sisältävät erottimen valinnan (esim., `-`, `/` tai `.`), ovatko vuodet kaksi- vai neljänumeroisia ja ovatko yksinumeroiset kuukaudet tai päivät eteenpäin nollatut.

Monimuotoisuuden hallitsemiseksi `MaskedDateField` käyttää formaatti-indikaattoreita, joista jokainen alkaa `%`-merkillä, jota seuraa kirjain, joka edustaa tiettyä osaa päivämäärästä. Nämä indikaattorit määrittelevät, miten syöte analysoidaan ja miten päivämäärä näytetään.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla tai analysoida päivämääriä saman maskeja syntaksilla kentän ulkopuolella, käytä [MaskDecorator](/docs/advanced/mask-decorator) apuluokkaa.
:::

### Päivämäärämuotoindikaattorit {#date-format-indicators}

| Muoto  | Kuvaus      |
| ------ | ----------- |
| `%Y`   | Vuosi       |
| `%M`   | Kuu         |
| `%D`   | Päivä       |

### Modifikaattorit {#modifiers}

Modifikaattorit mahdollistavat paremman hallinnan siitä, miten päivämäärän osat muotoillaan:

| Modifikaattori | Kuvaus                       |
| --------------- | ---------------------------- |
| `z`            | Nollaa täytön                |
| `s`            | Lyhyt tekstiesitys          |
| `l`            | Pitkä tekstiesitys          |
| `p`            | Pakattu numero               |
| `d`            | Desimaalimuoto (oletusmuoto) |

Näitä voidaan yhdistää laajan valikoiman päivämäärämaskeja luomiseksi.

## Päivämäärämuodon lokalisointi {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin asettamalla sopivan paikallisen asetuksen. Tämä varmistaa, että päivämäärät näytetään ja analysoidaan tavalla, joka vastaa käyttäjän odotuksia.

| Alue          | Muoto       | Esimerkki      |
| --------------| ------------| -------------- |
| Yhdysvallat    | MM/DD/YYYY | `07/04/2023`  |
| Eurooppa      | DD/MM/YYYY | `04/07/2023`  |
| ISO-standardi | YYYY-MM-DD | `2023-07-04`  |

Lokalisoinnin sovittamiseksi käytä `setLocale()` metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilua että analysointia:

```java
dateField.setLocale(Locale.FRANCE);
```

## Analysointilogiikka {#parsing-logic}

`MaskedDateField` analysoi käyttäjän syötteen määritetyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyssä numeeriset syötteet erottimilla tai ilman, mahdollistaen joustavan syötön samalla varmistamalla, että päivämäärät ovat voimassa.
Analysointikäyttäytyminen riippuu maskissa määritetystä formaattijärjestyksestä (esim., `%Mz/%Dz/%Yz` kuukaudelle/päivälle/vuodelle). Tämä muoto määrittää, miten numeerisia sekvenssejä tulkitaan.

Esimerkiksi, olettaen että tänään on `15. syyskuuta 2012`, seuraavat ovat eri syötteiden tulkinnat:

### Esimerkkianalysointitilanteet {#example-parsing-scenarios}

| Syöttö                                  | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ---------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>            | Yksi numero tulkitaan aina nykyisen kuukauden päivänä, joten tämä olisi 1. syyskuuta 2012.                                                                                                     | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`12`</div>           | Kaksi numeroa tulkitaan aina nykyisen kuukauden päivänä, joten tämä olisi 12. syyskuuta 2012.                                                                                                    | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`112`</div>          | Kolme numeroa tulkitaan 1-numeroisena kuukauden numerona, jota seuraa 2-numeroisen päivän numero, joten tämä olisi 12. tammikuuta 2012.                                                          | Sama kuin YMD                                                                         | Kolme numeroa tulkitaan 1-numeroisena päivänä, jota seuraa kahden numeron kuukauden numero, joten tämä olisi 1. joulukuuta 2012. |
| <div align="center">`1004`</div>         | Neljä numeroa tulkitaan MMDD, joten tämä olisi 4. lokakuuta 2012.                                                                                                                                 | Sama kuin YMD                                                                         | Neljä numeroa tulkitaan DDMM, joten tämä olisi 10. huhtikuuta 2012.                                                         |
| <div align="center">`020304`</div>       | Kuusi numeroa tulkitaan YYMMDD, joten tämä olisi 4. maaliskuuta 2002.                                                                                                                           | Kuusi numeroa tulkitaan MMDDYY, joten tämä olisi 3. helmikuuta 2004.            | Kuusi numeroa tulkitaan DDMMYY, joten tämä olisi 2. maaliskuuta 2004.                                                         |
| <div align="center">`8 numeroa`</div>    | Kahdeksan numeroa tulkitaan YYYYMMDD. Esimerkiksi, `20040612` on 12. kesäkuuta 2004.                                                                                                             | Kahdeksan numeroa tulkitaan MMDDYYYY. Esimerkiksi, `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan DDMMYYYY. Esimerkiksi, `06122004` on 6. joulukuuta 2004.                                        |
| <div align="center">`12/6`</div>         | Kaksi numeroa, joita seuraa mikä tahansa voimassa oleva erotin, tulkitaan MM/DD, joten tämä olisi 6. joulukuuta 2012. <br /> Huom: Kaikki merkit lukuun ottamatta kirjaimia ja numeroita katsotaan voimassa oleviksi erottimiksi. | Sama kuin YMD                                                                         | Kaksi numeroa, joita seuraa mikä tahansa erotin, tulkitaan DD/MM, joten tämä olisi 12. kesäkuuta 2012.                               |
| <div align="center">`3/4/5`</div>        | Huhtikuun 5, 2012                                                                                                                                                                               | Maaliskuu 4, 2005                                                                       | Huhtikuun 3, 2005                                                                                                                 |


## Tekstuaalinen päivämäärän analysointi <DocChip chip='since' label='25.10' /> {#textual-date-parsing}

Oletusarvoisesti `MaskedDateField` hyväksyy vain numeerista syötettä päivämäärille. Kuitenkin voit ottaa käyttöön **tekstuaalisen päivämäärän analysoinnin** sallimalla käyttäjien syöttää kuukauden ja päivän nimet syötteissään. Tämä ominaisuus on erityisen hyödyllinen luonnollisempien päivämäärän syöttöjen luomiseksi.

Ota tekstuaalinen analysointi käyttöön käyttämällä `setTextualDateParsing()` metodia:

```java
dateField.setTextualDateParsing(true);
```

### Kuukauden nimen korvaaminen {#month-name-substitution}

Kun tekstuaalinen analysointi on käytössä, voit käyttää erityisiä modifikaattoreita maskissasi hyväksyäksesi kuukauden nimet numeeristen arvojen sijaan:

- **`%Ms`** - Hyväksyy lyhyet kuukauden nimet (Tam, Hel, Mar, jne.)
- **`%Ml`** - Hyväksyy pitkät kuukauden nimet (Tammikuu, Helmikuu, Maaliskuu, jne.)

Kuukausinimet voivat esiintyä missä tahansa asennossa maskissa, ja kenttä hyväksyy silti numeerista syötettä varajärjestelmänä.

#### Esimerkkejä {#examples}

| Maski            | Syöttö            | Tulos                                |
| ---------------- | ----------------- | ------------------------------------- |
| `%Ms/%Dz/%Yz`    | `Sep/01/25`      | **Voimassa** - Analysoitu syyskuuta 1, 2025 |
| `%Ml/%Dz/%Yz`    | `Syyskuu/01/25`  | **Voimassa** - Analysoitu syyskuuta 1, 2025 |
| `%Dz/%Ml/%Yz`    | `01/Syyskuu/25`  | **Voimassa** - Analysoitu syyskuuta 1, 2025 |
| `%Mz/%Dz/%Yz`    | `09/01/25`       | **Voimassa** - Numeerinen varajärjestelmä toimii silti |

:::info
Kaikille 12 kuukaudelle tuetaan sekä lyhyitä (Tam, Hel, Mar, Huht, Tou, Kes, Heinä, Elo, Syys, Lok, Marras, Joulu) että pitkiä (Tammikuu, Helmikuu, jne.) muotoja.
:::
### Päivän nimen koristelu {#day-name-decoration}

Viikonpäivän nimiä voidaan sisällyttää syötteeseen paremman luettavuuden vuoksi, mutta ne ovat **koristeellisia** ja poistetaan analysoinnin aikana. Ne eivät vaikuta varsinaiseen päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyet viikonpäivän nimet (Ma, Ti, Ke, jne.)
- **`%Dl`** - Hyväksyy pitkät viikonpäivän nimet (Maanantai, Tiistai, Keskiviikko, jne.)

:::warning Päivän nimet vaativat numeerisen päivän
Kun käytät viikonpäivän nimiä (`%Ds` tai `%Dl`), maskisi **täytyy myös sisältää** `%Dz` tai `%Dd` määrittääkseen varsinaisen päivämäärän numeron. Ilman numeerista päivämääräkomponenttia syöte on virheitä.
:::

#### Esimerkkejä {#examples-1}

| Maski               | Syöttö                    | Tulos                                |
| ------------------- | ------------------------- | ------------------------------------- |
| `%Ds %Mz/%Dz/%Yz`   | `Ma 09/01/25`            | **Voimassa** - Päivän nimi on koristeena |
| `%Dl %Mz/%Dz/%Yz`   | `Maanantai 09/01/25`     | **Voimassa** - Päivän nimi on koristeena |
| `%Mz/%Dz/%Yz %Ds`   | `09/01/25 Ti`            | **Voimassa** - Päivän nimi lopussa |
| `%Dl/%Mz/%Yz`       | `Maanantai/09/25`        | **Virheellinen** - Puuttuu `%Dz` |
| `%Mz/%Dl/%Yz`       | `09/Maanantai/25`        | **Virheellinen** - Puuttuu `%Dz` |

Kaikkia 7 viikonpäivää tuetaan sekä lyhyinä (Ma, Ti, Ke, To, Pe, La, Su) että pitkinä (Maanantai, Tiistai, jne.) muotoina.

### Lisäanalysointisäännöt {#additional-parsing-rules}

Tekstuaalinen päivämäärän analysointi sisältää useita hyödyllisiä ominaisuuksia:

- **Isokirjaimista riippumaton:** Syöttö kuten `Maanantai 09/01/25`, `maanantai 09/01/25`, tai `Maanantai 09/01/25` toimivat kaikki samalla tavalla.
- **Paikalliset:** Kuukauden ja päivän nimien täytyy vastata kentän määritettyä lokalisointia. Esimerkiksi, ranskalaisessa paikallista, käytetään `septembre` ei `September`. Englantilaisia nimiä ei tunnisteta, ellei lokaalia asetusta ole asetettu englanniksi.
  - Ranskan lokaalissa: `septembre/01/25` tunnistetaan syyskuuna
  - Saksan lokaalissa: `Montag 09/01/25` tunnistetaan maanantaina viikonpäivänä

## Min/Max rajoitusten asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämäärän alueen `MaskedDateField` käyttäen `setMin()` ja `setMax()` metodeja:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat metodit hyväksyvät [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) tyyppisiä arvoja. Syöte, joka on määritettyjen rajojen ulkopuolella, katsotaan virheelliseksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautusominaisuuden, joka nollaa kentän arvon esimäärättyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen kumoamiseksi tai oletuspäivämäärään palauttamiseksi.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusnäppäin, ellei ohjaimella ole ylikirjoitettu)

Voit asettaa palautettavan arvon käyttämällä `setRestoreValue()`, välittämällä [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instanssin.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validointikaaviot {#validation-patterns}

Voit soveltaa asiakaspohjaisia validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` metodilla:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä malli varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY` muotoa (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa), katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on seurattava JavaScript RegExp syntaksia, kuten on asiakirjoitettu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää analysoida ja muotoilla numeerisia päivämääräsyötteitä nykyisen maskin perusteella. Kuitenkin, käyttäjät voivat silti manuaalisesti syöttää arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai ei-analysoitavissa (esim. `99/99/9999`), se voi läpäistä mallin tarkistukset, mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lausekemalli olisi asetettu, varmistaaksesi että päivämäärä on sekä oikein muotoiltu että merkityksellinen.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka sallii käyttäjien valita päivämäärän visuaalisesti sen sijaan, että kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkka syöttö on tarpeen.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta `getPicker()` metodin kautta:

```java
DatePicker picker = dateField.getPicker();
```

### Näytä/pidä valitsimen ikoni piilossa {#showhide-the-picker-icon}

Käytä `setIconVisible()` näyttääksesi tai piilottaaksesi kalenterikuvakkeen kentän vieressä:

```java
picker.setIconVisible(true); // näyttää kuvakkeen
```

### Automaattinen avautumis käyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsautukset, Enter-näppäimen painaminen tai nuolinäppäinten käyttäminen):

```java
picker.setAutoOpen(true);
```

:::tip Pakota valinta valitsimella
Varmistaaksesi, että käyttäjät voivat valita vain päivämäärän kalenteri valitsimen avulla (eikä manuaalisesti kirjoittamalla), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avautuu valitsimella vuorovaikutuksessa
dateField.setAllowCustomValue(false);    // Estää manuaalisen tekstisyötteen
```

Tämä asetus takaa, että kaikki päivämääräsyötteet tulevat valitsimen käyttöliittymistä, mikä on hyödyllistä, kun haluat tiukkaa muotoilun hallintaa ja poistaa kirjoitettujen syötteiden analysoinnista johtuvia ongelmia.
:::

### Avaa kalenteri manuaalisesti {#manually-open-the-calendar}

Avaa kalenteri ohjelmallisesti:

```java
picker.open();
```

Tai käytä alias-asetusta:

```java
picker.show(); // sama kuin open()
```

### Näytä viikot kalenterissa {#show-weeks-in-the-calendar}

Valitsin voi tarvittaessa näyttää viikkonumeroita kalenterinäkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa `MaskedDateField` lisäämällä pyörö- ja nuolivalitsimia, jotka sallivat käyttäjien lisätä tai vähentää päivämäärää käyttäen nuolinäppäimiä tai käyttöliittymän painikkeita. Se tarjoaa opastetun vuorovaikutustyylin, erityisesti hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Avainominaisuudet {#key-features}

- **Interaktiivinen päivämääräaskel:**
  Käytä nuolinäppäimiä tai pyörityspainikkeita päivämääräarvon lisäämiseksi tai vähentämiseksi.

- **Mukautettava askel-yksikkö:**
  Valitse, mikä osa päivämäärästä muutetaan käyttäen `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehtoja ovat `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max rajat:**
  Yhdistelee tuen minimien ja maksimi päivämäärille käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu tuloste:**
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa `MaskedDateField`:stä.

### Esimerkki: Konfiguroi viikoittainen askel {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä tekee jokaisesta pyörimisaskelesta siirtää tai kääntää päivämäärää yhdellä viikolla.

## Tyylittely {#styling}

<TableBuilder name="MaskedDateField" />
