---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 981d5cd2686c83144433a0135b1222dc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on teksti-input, joka sallii käyttäjien syöttää päivämäärät numeroina ja automaattisesti muotoilla syötteen määritellyn maskin perusteella, kun kenttä menettää fokus. Maski määrittelee odotetun päivämäärämuodon, ohjaten sekä syöttöä että näyttöä. Komponentti tukee joustavaa jäsentämistä, validoimista, lokalisointia ja arvon palauttamista johdonmukaiselle, aluekohtaiselle päivämäärä käsittelylle.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy ainoastaan **päivämäärä** arvoihin. Jos tarvitset samanlaista komponenttia aikojen syöttämiseen ja muotoiluun, katso [`MaskedTimeField`](./timefield).
:::

`MaskedDateField` voidaan luoda parametrista riippumatta. Voit määrittää alkusarjan, etiketin, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita päivämäärämuotoja, jotka vaihtelevat ympäri maailmaa päivä-, kuukausi- ja vuosijärjestyksen mukaan. Yleisimmät kaavat sisältävät:

- **Päivä/Kuukausi/Vuosi** (käytössä suurimmassa osassa Eurooppaa)
- **Kuukausi/Päivä/Vuosi** (käytössä Yhdysvalloissa)
- **Vuosi/Kuukausi/Päivä** (käytössä Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden muotojen sisällä paikalliset vaihtelut sisältävät erotinmerkin valinnan (esim. `-`, `/` tai `.`), ovatko vuodet kahta tai neljää numeroa, ja ovatko yksinumeroiset kuukaudet tai päivät esitäytettyjä nollilla.

Tämän monimuotoisuuden käsittelemiseksi `MaskedDateField` käyttää formaatti-indikaattoreita, jotka alkavat `%`, jota seuraa kirjain, joka edustaa tiettyä osaa päivämäärästä. Nämä indikaattorit määrittelevät, kuinka syöttö jäsennetään ja kuinka päivämäärä näytetään.

### Päivämäärämuotoindikaattorit {#date-format-indicators}

| Muoto | Kuvaus      |
| ----- | ----------- |
| `%Y`  | Vuosi       |
| `%M`  | Kuukausi    |
| `%D`  | Päivä       |

### Modifikaattorit {#modifiers}

Modifikaattorit antavat enemmän hallintaa päivämäärän osien muotoilussa:

| Modifikaattori | Kuvaus                     |
| --------------- | --------------------------- |
| `z`             | Nollatäyte                  |
| `s`             | Lyhyt tekstiesitys         |
| `l`             | Pitkä tekstiesitys         |
| `p`             | Pakattu numero              |
| `d`             | Desimaalimuoto (oletus)    |

Näitä voidaan yhdistää luomaan monenlaisia päivämäärämaskeja.

## Päivämäärän muotoilu lokalisoinnin mukaan {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin asettamalla asianmukaisen lokalisoinnin. Tämä varmistaa, että päivämäärät näytetään ja jäsennetään tavalla, joka vastaa käyttäjän odotuksia.

| Alue            | Muoto      | Esimerkki         |
| --------------- | ---------- | ----------------- |
| Yhdysvallat     | MM/DD/YYYY | `07/04/2023`      |
| Eurooppa        | DD/MM/YYYY | `04/07/2023`      |
| ISO-standardi   | YYYY-MM-DD | `2023-07-04`      |

Lokalisoinnin soveltamiseksi käytä `setLocale()` -metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) -olion ja säätää automaattisesti sekä muotoilun että jäsentämisen:

```java
dateField.setLocale(Locale.FRANCE);
```

## Jäsentämislogiikka {#parsing-logic}

`MaskedDateField` jäsentää käyttäjän syöttöä määritellyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erotinmerkkien kanssa tai ilman, jolloin joustava syöttäminen varmistaa kelvolliset päivämäärät. 

Jäsentämisen käytäntö riippuu maskin määrittämästä järjestyksestä (esim. `%Mz/%Dz/%Yz` kuukausi/päivä/vuosi). Tämä muoto määrää, miten numeeriset sekvenssit tulkitaan.

Esimerkiksi, olettaen että tänään on `15. syyskuuta 2012`, tällöin erilaiset syötteet tulkitaan seuraavasti:

### Esimerkit jäsentämisestä {#example-parsing-scenarios}

| Syöte                              | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ---------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>       | Yksi numero tulkitaan aina päivänumeroksi voimassa olevassa kuukaudessa, joten tämä olisi 1. syyskuuta 2012.                                                                                 | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`12`</div>      | Kaksi numeroa tulkitaan aina päivänumeroksi voimassa olevassa kuukaudessa, joten tämä olisi 12. syyskuuta 2012.                                                                                   | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`112`</div>     | Kolme numeroa tulkitaan yhden numeron kuukausinumeroksi ja kahden numeron päivänumeroksi, joten tämä olisi 12. tammikuuta 2012.                                                                        | Sama kuin YMD                                                                         | Kolme numeroa tulkitaan yhden numeron päivänumeroksi ja kahden numeron kuukausinumeroksi, joten tämä olisi 1. joulukuuta 2012. |
| <div align="center">`1004`</div>    | Neljä numeroa tulkitaan muodossa MMDD, joten tämä olisi 4. lokakuuta 2012.                                                                                                                             | Sama kuin YMD                                                                         | Neljä numeroa tulkitaan muodossa DDMM, joten tämä olisi 10. huhtikuuta 2012.                                                         |
| <div align="center">`020304`</div>  | Kuusi numeroa tulkitaan muodossa YYMMDD, joten tämä olisi 4. maaliskuuta 2002.                                                                                                                              | Kuusi numeroa tulkitaan muodossa MMDDYY, joten tämä olisi 3. helmikuuta 2004.            | Kuusi numeroa tulkitaan muodossa DDMMYY, joten tämä olisi 2. maaliskuuta 2004.                                                         |
| <div align="center">`8 numeroa`</div> | Kahdeksan numeroa tulkitaan muodossa YYYYMMDD. Esimerkiksi `20040612` on 12. kesäkuuta 2004.                                                                                                                | Kahdeksan numeroa tulkitaan muodossa MMDDYYYY. Esimerkiksi `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan muodossa DDMMYYYY. Esimerkiksi `06122004` on 6. joulukuuta 2004.                                        |
| <div align="center">`12/6`</div>    | Kaksi numeroa, jotka on erotettu millä tahansa voimassa olevalla erottimella tulkitaan muotoon MM/DD, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, paitsi kirjaimet ja numerot, on voimassa olevia erotinmerkkejä. | Sama kuin YMD                                                                         | Kaksi numeroa, jotka on erotettu millä tahansa erotinmerkillä tulkitaan muotoon DD/MM, joten tämä olisi 12. kesäkuuta 2012.                               |
| <div align="center">`3/4/5`</div>     | 5. huhtikuuta 2012                                                                                                                                                                                      | 4. maaliskuuta 2005                                                                       | 3. huhtikuuta 2005                                                                                                                 |

## Tekstuaalinen päivämääräjäsentäminen <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Oletuksena `MaskedDateField` hyväksyy vain numeeriset syötteet päivämäärille. Voit kuitenkin ottaa käyttöön **tekstuaalisen päivämääräjäsentämisen** sallimalla käyttäjien syöttää kuukausi- ja päivätietoja syötteeseensä. Tämä toiminto on erityisen hyödyllinen luotaessa luonnollisempaa päivämääräsyöttöä.

Ota tekstuaalinen jäsentäminen käyttöön käyttämällä `setTextualDateParsing()` -metodia:

```java
dateField.setTextualDateParsing(true);
```

### Kuukauden nimen korvaaminen {#month-name-substitution}

Kun tekstuaalinen jäsentäminen on käytössä, voit käyttää erityisiä modifikaattoreita maskissasi hyväksyäksesi kuukausinimiä numeeristen arvojen sijaan:

- **`%Ms`** - Hyväksyy lyhyitä kuukausinimiä (Tammi, Helmi, Maa, jne.)
- **`%Ml`** - Hyväksyy pitkiä kuukausinimiä (Tammikuu, Helmikuu, Maaliskuu, jne.)

Kuukauden nimet voivat esiintyä missä tahansa sijainnissa maskissa, ja kenttä hyväksyy silti numeerisia syötteitä varasyötteenä.

#### Esimerkit

| Maski | Syöte | Tulos |
| ----- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Kelvollinen** - Jäsentää 1. syyskuuta 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Kelvollinen** - Jäsentää 1. syyskuuta 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Kelvollinen** - Jäsentää 1. syyskuuta 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Kelvollinen** - Numero varasyöttö toimii silti |

:::info
Kaikkia 12 kuukautta tuetaan sekä lyhyinä (Tammi, Helmi, Maa, Huhti, Touko, Kesä, Heinä, Elo, Syys, Loka, Marras, Joulu) että pitkinä (Tammikuu, Helmikuu, jne.) muotoina.
:::
### Päivän nimen koristeellinen käyttö {#day-name-decoration}

Viikonpäivän nimiä voidaan sisällyttää syötteeseen paremman luettavuuden vuoksi, mutta ne ovat **koristeellisia vain** ja poistetaan jäsentämisen aikana. Ne eivät vaikuta varsinaiseen päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyitä päivän nimiä (Ma, Ti, Ke, jne.)
- **`%Dl`** - Hyväksyy pitkiä päivän nimiä (Maanantai, Tiistai, Keskiviikko, jne.)

:::warning Päivän nimet tarvitsevat numeerisen päivän
Kun käytät viikonpäivän nimiä (`%Ds` tai `%Dl`), maskisi **täytyy myös sisältää** `%Dz` tai `%Dd` määrittämään varsinainen päivännumero. Ilman numeerista päivän osaa syöte on kelpaamaton.
:::

#### Esimerkit

| Maski | Syöte | Tulos |
| ----- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25` | **Kelvollinen** - Päivän nimi on koristeellinen |
| `%Dl %Mz/%Dz/%Yz` | `Maanantai 09/01/25` | **Kelvollinen** - Päivän nimi on koristeellinen |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Ti` | **Kelvollinen** - Päivän nimi lopussa |
| `%Dl/%Mz/%Yz` | `Maanantai/09/25` | **Kelpaamaton** - Puuttuu `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Maanantai/25` | **Kelpaamaton** - Puuttuu `%Dz` |

Kaikki 7 viikonpäivää tuetaan sekä lyhyinä (Ma, Ti, Ke, To, Pe, La, Su) että pitkinä (Maanantai, Tiistai, jne.) muotoina.

### Lisäjäsentämissäännöt {#additional-parsing-rules}

Tekstuaalinen päivämääräjäsentäminen sisältää useita hyödyllisiä ominaisuuksia:

- **Kokonaissensitiivinen:** Syöte kuten `MAANANTAI 09/01/25`, `maanantai 09/01/25` tai `Maanantai 09/01/25` toimivat kaikki samalla tavalla.
- **Paikkakunnasta riippuvainen:** Kuukausi- ja päivän nimet on sovittava kentän määritettyyn lokalisointiin. Esimerkiksi, ranskalaisella lokalisoinnilla käytä `septembre` etkä `September`. Englanninkielisiä nimiä ei tunnisteta, ellei lokalisointi ole asetettu englanniksi.
  - Ranskalainen lokalisointi: `septembre/01/25` tunnistetaan syyskuuksi
  - Saksalainen lokalisointi: `Montag 09/01/25` tunnistetaan maanantaina nimipäivänä

## Min/Max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField` -komponentissa käyttämällä `setMin()` ja `setMax()` -metodeja:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat menetelmät hyväksyvät [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) -tyyppisiä arvoja. Määriteltyjen rajojen ulkopuoliset syötteet katsotaan kelpaamattomiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautustoiminnon, joka palauttaa kentän arvon esimäärättyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen peruuttamiseksi tai oletuspäivämäärään palauttamiseksi.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusavain, ellei sitä ylikirjoiteta tapahtumakuuntelijan toimesta)

Voit asettaa palautettavan arvon käyttämällä `setRestoreValue()`, antaen `LocalDate` (https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) -instanssin.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Varmennuskuviot {#validation-patterns}

Voit soveltaa asiakaspään validoimisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` -metodilla:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä kuvio varmistaa, että vain arvot, jotka vastaavat muotoa `MM/DD/YYYY` (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa), katsotaan kelvollisiksi.

:::tip Säännöllinen lausekemuoto
Kuvion on noudatettava JavaScript RegExp -syntaksia, kuten on dokumentoitu [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelyyn
Kenttä yrittää jäsentää ja formatoida numeeriset päivämääräsyötteet nykyisen maskin mukaan. Käyttäjät voivat silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti kelvollinen mutta semanttisesti virheellinen tai ei-jäsennettävä (esim. `99/99/9999`), se voi läpäistä kuvio tarkastukset, mutta epäonnistua loogisessa validoimisessa.
Sinun tulisi aina validoida syötearvo sovelluksen logiikassa, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että päivämäärä on sekä oikeassa muodossa että merkityksellinen.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka mahdollistaa käyttäjien valita päivämäärän visuaalisesti, sen sijaan että kirjoitetaan se. Tämä parantaa käytettävyyttä vähemmän teknisten käyttäjien tai silloin, kun tarkka syöttö on tarpeen.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Valitsimen käyttöönotto {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta käyttämällä `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Valitsimen ikonin näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` -metodia näytääksesi tai piilottaaksesi kalenteri-ikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattinen avaustoiminta {#auto-open-behavior}

Voit konfiguroida valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Pakota valinta valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita vain päivämäärän kalenteri-valitsimella (eivätkä kirjoittaa manuaalisesti), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avaa valitsimen käyttäjän vuorovaikutuksen myötä
dateField.setAllowCustomValue(false);    // Estää manuaalisen tekstisyötön
```

Tämä asetus varmistaa, että kaikki päivämääräsyöte tulee valitsimen käyttöliittymän kautta, mikä on hyödyllistä tiukkaa muotoilun hallintaa varten ja eliminoi jäsentämisongelmat kirjoitetuista syötteistä.
:::

### Kalenterin avaaminen manuaalisesti {#manually-open-the-calendar}

Avaa kalenteri ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Viikkojen näyttäminen kalenterissa {#show-weeks-in-the-calendar}

Valitsin voi valinnaisesti näyttää viikkonumerot kalenterinäkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) lisäämällä pyöriväohjaimia, jotka sallivat käyttäjien lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, erityisen hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Avainominaisuudet {#key-features}

- **Interaktiivinen päivämäärän askellus:**  
  Käytä nuolinäppäimiä tai pyörityspainikkeita lisätäksesi tai vähentäksesi päivämääräarvoa.

- **Mukautettava askellus-yksikkö:**  
  Valitse, mikä osa päivämäärästä muutetaan käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehtoja ovat `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**  
  Perii tuen vähimmäis- ja enimmäispäivämäärille käyttämällä `setMin()` ja `setMax()`.

- **Muotoiltu ulostulo:**  
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa, joita `MaskedDateField` tarjoaa.

### Esimerkki: Konfiguroi viikoittainen askellus {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä tekee jokaisesta askelista edistää tai peruuttaa päivämäärää yhdellä viikolla.

## Tyylitys {#styling}

<TableBuilder name="MaskedDateField" />
