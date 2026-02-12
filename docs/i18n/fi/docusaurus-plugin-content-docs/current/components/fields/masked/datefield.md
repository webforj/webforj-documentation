---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 93973075b9f8f9bcc3eddf18e8b01017
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on tekstinsyöttöohjain, joka on suunniteltu rakenteellista päivämäärän syöttöä varten. Se sallii käyttäjien syöttää päivämääriä **numeroina** ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski on merkkijono, joka määrittää odotetun päivämäärämuodon, ohjaten sekä syötettä että näyttöä.

Tämä komponentti tukee joustavaa analysointia, vahvistamista, lokalisointia ja arvon palauttamista. Se on erityisen hyödyllinen lomakkeissa, kuten rekisteröinnissä, varauksissa ja aikataulutuksessa, joissa johdonmukaisia ja aluekohtaisia päivämäärämuotoja tarvitaan.

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy pelkästään **päivämäärä** arvoihin. Jos tarvitset samanlaista komponenttia aikojen syöttämiseen ja muotoiluun, tutustu sen sijaan [`MaskedTimeField`](./timefield).
:::

## Perusteet {#basics}

`MaskedDateField` voidaan instanssia joko parametrien kanssa tai ilman. Voit määrittää alkusarjan, etiketti, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita eri päivämäärämuotoja ympäri maailmaa, jotka vaihtelevat päivämäärän, kuukauden ja vuoden järjestyksen mukaan. Yleisiä kaavoja ovat:

- **Päivä/Kuukausi/Vuosi** (käytetään suurimmassa osassa Eurooppaa)
- **Kuukausi/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuukausi/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden muotojen sisällä paikalliset vaihtelut sisältävät erottimen valinnan (esim. `-`, `/` tai `.`), ovatko vuodet kahden vai neljän numeron pituisia, ja ovatko yksinumeroiset kuukaudet tai päivät täytetty etulukuilla.

Käsitelläkseen tätä moninaisuutta `MaskedDateField` käyttää muotoilun indikaattoreita, joista jokainen alkaa `%`, jota seuraa kirjain, joka edustaa tiettyä osaa päivämäärästä. Nämä indikaattorit määrittävät, kuinka syöte analysoidaan ja miten päivämäärä näytetään.

### Päivämäärämuotoilun indikaattorit {#date-format-indicators}

| Muoto | Kuvaus      |
| ----- | ----------- |
| `%Y`  | Vuosi       |
| `%M`  | Kuukausi    |
| `%D`  | Päivä       |

### Muokkaimet {#modifiers}

Muokkaimet antavat enemmän kontrollia päivämäärän osien muotoiluun:

| Muokkain | Kuvaus                     |
| -------- | --------------------------- |
| `z`      | Nollatäyttö                 |
| `s`      | Lyhyt tekstiesitys          |
| `l`      | Pitkä tekstiesitys          |
| `p`      | Pakattu numero              |
| `d`      | Desimaali (oletusmuoto)    |

Nämä voidaan yhdistää luomaan monenlaisia päivämäärämaskeja.

## Päivämäärämuotojen lokalisointi {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin asettamalla oikean paikallisuuden. Tämä varmistaa, että päivämäärät näkyvät ja analysoidaan tavalla, joka vastaa käyttäjän odotuksia.

| Alue          | Muoto       | Esimerkki      |
| ------------- | ----------- | -------------- |
| Yhdysvallat   | MM/DD/YYYY  | `07/04/2023`   |
| Eurooppa      | DD/MM/YYYY  | `04/07/2023`   |
| ISO-standardi | YYYY-MM-DD  | `2023-07-04`   |

Jotta voisit käyttää lokalisointia, käytä `setLocale()`-metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilun että analyysin:

```java
dateField.setLocale(Locale.FRANCE);
```

## Analysointilogiikka {#parsing-logic}

`MaskedDateField` analysoi käyttäjän syötteen määritetyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet, joilla on tai ei ole erotinta, mahdollistamalla joustavan syötteen samalla varmistaen voimassa olevat päivämäärät. Analysointikäyttäytyminen riippuu maskin määrittelemästä muotojärjestyksestä (esim. `%Mz/%Dz/%Yz` kuukaudelle/päivälle/vuodelle). Tämä muoto määrittää, miten numeeriset sekvenssit tulkitaan.

Esimerkiksi, olettaen, että tänään on `15. syyskuuta 2012`, näin erilaiset syötteet tulkitaan:

### Esimerkit analysointitapauksista {#example-parsing-scenarios}

| Syöte                                 | YMD (ISO)                                                                                                                                | MDY (Yhdysvallat)                                                                | DMY (Eurooppa)                                                                                                                |
| -------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Yksi numero tulkitaan aina päivän numerona nykyisessä kuussa, joten tämä olisi 1. syyskuuta 2012.                                        | Sama kuin YMD                                                                    | Sama kuin YMD                                                                                                                 |
| <div align="center">`12`</div>        | Kaksi numeroa tulkitaan aina päivän numerona nykyisessä kuussa, joten tämä olisi 12. syyskuuta 2012.                                     | Sama kuin YMD                                                                    | Sama kuin YMD                                                                                                                 |
| <div align="center">`112`</div>       | Kolme numeroa tulkitaan 1-numeroisena kuukausinumerona, jota seuraa 2-numeroisen päivän numero, joten tämä olisi 12. tammikuuta 2012. | Sama kuin YMD                                                                    | Kolme numeroa tulkitaan 1-numeroisena päivän numerona, jota seuraa 2-numeroisen kuukausinumeron, joten tämä olisi 1. joulukuuta 2012. |
| <div align="center">`1004`</div>      | Neljä numeroa tulkitaan MMDD-muotoisena, joten tämä olisi 4. lokakuuta 2012.                                                          | Sama kuin YMD                                                                    | Neljä numeroa tulkitaan DDMM-muotoisena, joten tämä olisi 10. huhtikuuta 2012.                                              |
| <div align="center">`020304`</div>    | Kuusi numeroa tulkitaan YYMMDD-muotoisena, joten tämä olisi 4. maaliskuuta 2002.                                                     | Kuusi numeroa tulkitaan MMDDYY-muotoisena, joten tämä olisi 3. helmikuuta 2004.    | Kuusi numeroa tulkitaan DDMMYY-muotoisena, joten tämä olisi 2. maaliskuuta 2004.                                             |
| <div align="center">`8 numeroa`</div> | Kahdeksan numeroa tulkitaan YYYYMMDD-muotoisena. Esimerkiksi `20040612` on 12. kesäkuuta 2004.                                        | Kahdeksan numeroa tulkitaan MMDDYYYY-muotoisena. Esimerkiksi `06122004` on 12. kesäkuuta 2004.      | Kahdeksan numeroa tulkitaan DDMMYYYY-muotoisena. Esimerkiksi `06122004` on 6. joulukuuta 2004.                                |
| <div align="center">`12/6`</div>      | Kaksi numeroa, jotka on erotettu millä tahansa voimassa olevalla erotinmerkillä, tulkitaan MM/DD-muotoiseksi, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, paitsi kirjaimet ja numerot, katsotaan voimassa oleviksi erottimiksi. | Sama kuin YMD                                                                    | Kaksi numeroa, jotka on erotettu millä tahansa erottimella, tulkitaan DD/MM-muotoiseksi, joten tämä olisi 12. kesäkuuta 2012.      |
| <div align="center">`3/4/5`</div>      | 5. huhtikuuta 2012                                                                                                                                                                           | 4. maaliskuuta 2005                                                               | 3. huhtikuuta 2005                                                                                                            |


## Tekstuaalinen päivämäärän analysointi <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Oletusarvoisesti `MaskedDateField` hyväksyy vain numeerista syötettä päivämäärille. Voit kuitenkin ottaa käyttöön **tekstuaalisen päivämäärän analysoinnin** salliaksesi käyttäjien syöttää kuukausi- ja päivännimiä syötteessä. Tämä ominaisuus on erityisen hyödyllinen luotaessa luonnollisempaa päivämäärän syöttöä.

Ottaaksesi tekstuaalisen analysoinnin käyttöön, käytä `setTextualDateParsing()`-metodia:

```java
dateField.setTextualDateParsing(true);
```

### Kuukausinimen korvauksen {#month-name-substitution}

Kun tekstuaalinen analysointi on käytössä, voit käyttää erityisiä muokkaimia maskissasi hyväksyäksesi kuukausinimiä numeeristen arvojen sijaan:

- **`%Ms`** - Hyväksyy lyhyet kuukausinimet (Tam, Hel, Maa jne.)
- **`%Ml`** - Hyväksyy pitkät kuukausinimet (Tammikuu, Helmikuu, Maaliskuu jne.)

Kuukausinimet voivat esiintyä missä tahansa paikassa maskissa, ja kenttä hyväksyy silti numeerista syötettä varmuuden vuoksi.

#### Esimerkit

| Maski             | Syöte              | Tulos                             |
| ------------------| ------------------ | ---------------------------------- |
| `%Ms/%Dz/%Yz`     | `Sep/01/25`        | **Voimassa** - Analysoidaan syyskuun 1, 2025 |
| `%Ml/%Dz/%Yz`     | `September/01/25`  | **Voimassa** - Analysoidaan syyskuun 1, 2025 |
| `%Dz/%Ml/%Yz`     | `01/September/25`  | **Voimassa** - Analysoidaan syyskuun 1, 2025 |
| `%Mz/%Dz/%Yz`     | `09/01/25`         | **Voimassa** - Numeroidun varmuuden käyttö toimii edelleen |

:::info
Kaikki 12 kuukautta ovat tuettuja sekä lyhyissä (Tam, Hel, Maa, Huhti, Touko, Kesä, Heinä, Elo, Syys, Loka, Marras, Joulu) että pitkissä (Tammikuu, Helmikuu jne.) muodoissa.
:::
### Päivänimen koristelu {#day-name-decoration}

Viikonpäivän nimet voidaan sisällyttää syötteeseen paremman luettavuuden vuoksi, mutta ne ovat **koristeellisia vain** ja poistetaan analysoinnin aikana. Ne eivät vaikuta todelliseen päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyet päivän nimet (Ma, Ti, Ke jne.)
- **`%Dl`** - Hyväksyy pitkät päivän nimet (Maanantai, Tiistai, Keskiviikko jne.)

:::warning Päivän nimet vaativat numeerisen päivän
Kun käytetään viikonpäivän nimiä (`%Ds` tai `%Dl`), maskissa **on myös oltava** `%Dz` tai `%Dd`, jotta voidaan määrittää todellinen päivän numero. Ilman numeerista päivän osaa syöte on voimassa oleva.
:::

#### Esimerkit

| Maski                | Syöte             | Tulos                             |
| ---------------------| ------------------| ---------------------------------- |
| `%Ds %Mz/%Dz/%Yz`    | `Ma 09/01/25`     | **Voimassa** - Päivänimi on koristeellinen |
| `%Dl %Mz/%Dz/%Yz`    | `Maanantai 09/01/25` | **Voimassa** - Päivänimi on koristeellinen |
| `%Mz/%Dz/%Yz %Ds`    | `09/01/25 Ti`     | **Voimassa** - Päivänimi lopussa |
| `%Dl/%Mz/%Yz`       | `Maanantai/09/25` | **Voimaton** - Puuttuu `%Dz` |
| `%Mz/%Dl/%Yz`       | `09/Maanantai/25` | **Voimaton** - Puuttuu `%Dz` |

Kaikki 7 viikonpäivää ovat tuettuja sekä lyhyissä (Ma, Ti, Ke, To, Pe, La, Su) että pitkissä (Maanantai, Tiistai jne.) muodoissa.

### Lisäanalysointisäännöt {#additional-parsing-rules}

Tekstuaalinen päivämäärän analysointi sisältää useita hyödyllisiä ominaisuuksia:

- **Kokonaistajuisesti:** Syötteet kuten `MAANANTAI 09/01/25`, `maanantai 09/01/25`, tai `Maanantai 09/01/25` toimivat kaikki samalla tavalla.
- **Paikallisesta huomiota:** Kuukauden ja päivän nimet on sovitettava kentän määritettyyn paikallisuuteen. Esimerkiksi, ranskalaisessa paikallisuudessa käytetään `septembre` ei `September`. Englanninkielisiä nimiä ei tunnisteta, ellei paikallisuus ole asetettu englanniksi.
  - Ranskalainen paikallisuus: `septembre/01/25` tunnistetaan syyskuuna
  - Saksan paikallisuus: `Montag 09/01/25` tunnistetaan maanantaina

## Min/max-rajoitusten asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField`-komponentissa käyttämällä `setMin()` ja `setMax()`-metodeja:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) tyyppisiä arvoja. Syöte, joka on määritellyn alueen ulkopuolella, katsotaan voimattomaksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautustoiminnon, joka nollaa kentän arvon ennalta määritettyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen palauttamisessa tai palauttamisessa oletuspäivämäärään.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei sitä ole ylivoimaisessa tapahtumakuuntelijassa)

Voit asettaa palautettavan arvon `setRestoreValue()`, joka antaa [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instanssin.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Vahvistuskaavat {#validation-patterns}

Voit soveltaa asiakaspuolen vahvistussääntöjä säännöllisiä lausekkeita käyttäen `setPattern()`-metodin avulla:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä kaava varmistaa, että vain arvot, jotka vastaavat muodollista `MM/DD/YYYY` (kaksi numeroa, viiva, kaksi numeroa, viiva, neljä numeroa) katsotaan voimassa oleviksi.

:::tip Säännöllisen lausekkeen muoto
Kaavan on noudatettava JavaScript RegExp -syntaksia, kuten on dokumentoitu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää analysoida ja muotoilla numeerisia päivämääräsyötteitä nykyisen maskin perusteella. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa, mutta semanttisesti virheellinen tai analysoimaton (esim. `99/99/9999`), se voi läpäistä kaavion tarkastukset, mutta epäonnistua loogisessa vahvistamisessa.
Sinun tulisi aina validoida syötearvo sovelluksen logiikassasi, vaikka säännöllinen lausekekaava olisi asetettu, varmistamaan, että päivämäärä on sekä oikein muotoiltu että merkityksellinen.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, jonka avulla käyttäjät voivat valita päivämäärän visuaalisesti sen sijaan, että kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkka syöttö on tarpeen.

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

Käytä `setIconVisible()` näyttääksesi tai piilottaaksesi kalenterikuvakkeen kentän vieressä:

```java
picker.setIconVisible(true); // näyttää kuvakkeen
```

### Automaattinen avautuminen {#auto-open-behavior}

Voit määrittää valitsimen avautuvaksi automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Valinnan pakottaminen valitsimen kautta
Varmista, että käyttäjät voivat valita päivämäärän vain kalenterivalitsimen avulla (eivätkä kirjoita sitä manuaalisesti), yhdistämällä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // avaa valitsimen käyttäjän vuorovaikutuksessa
dateField.setAllowCustomValue(false);    // estää manuaalisen tekstisyötteen
```

Tämä asetus takaa, että kaikki päivämääräsyöte tulee valitsimen käyttöliittymästä, mikä on hyödyllistä, kun halutaan tiukkaa muotoilua ja poistaa kirjoitetun syötteen analysoinnista aiheutuvat ongelmat.
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

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) lisäämällä pyöräytysohjaimia, jotka sallivat käyttäjien suurentaa tai pienentää päivämäärää nuolinäppäimillä tai käyttöpainikkeilla. Se tarjoaa opastetumman vuorovaikutustyylin, joka on erityisen hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Keskeiset ominaisuudet {#key-features}

- **Vuorovaikutteinen päivämäärän säätö:**  
  Käytä nuolinäppimiä tai kääntöpainikkeita lisätäksesi tai vähentääksesi päivämääräarvoa.

- **Mukautettava säätöyksikkö:**  
  Valitse, mitä osaa päivämäärästä haluat muokata käyttämällä `setSpinField()`:

```java
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
```

Vaihtoehdot sisältävät `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**  
  Perii tuen minimien ja maksimiarvojen asettamiseen `setMin()` ja `setMax()` avulla.

- **Muotoiltu ulostulo:**  
  Täydellinen yhteensopivuus maskien ja lokalisointiasetusten kanssa `MaskedDateField`:istä.

### Esimerkki: Määritä viikoittainen säätö {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä saa jokaisen yliastutun vaiheen eteenpäin tai taaksepäin päivämäärää yhdellä viikolla.

## Tyylittely {#styling}

<TableBuilder name="MaskedDateField" />
