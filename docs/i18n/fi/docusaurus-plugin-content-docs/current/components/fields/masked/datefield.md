---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 6c75156564c20c2d451ebe7046213c37
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on teksti-ilmaisin, joka sallii käyttäjien syöttää päivämääriä numeroina ja muotoilee syötteen automaattisesti määritellyn maskin mukaan, kun kenttä menettää fokuksen. Maski määrittelee odotetun päivämäärämuodon, joka ohjaa sekä syötettä että näyttöä. Komponentti tukee joustavaa jäsennystä, validoimista, lokalisointia ja arvon palauttamista johdonmukaiselle, aluekohtaiselle päivämäärän käsittelylle.

<!-- INTRO_END -->

## Perusteet {#basics}

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy pelkästään **päivämäärä**arvoihin. Jos tarvitset vastaavaa komponenttia aikojen syöttämiseen ja muotoiluun, tutustu [`MaskedTimeField`](./timefield) -komponenttiin.
:::

`MaskedDateField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkuarvon, etiketin, paikkamerkin ja tapahtumakuuntelijan arvon muutoksille.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee monia päivämäärämuotoja, joita käytetään ympäri maailmaa, ja jotka vaihtelevat päivän, kuukauden ja vuosien järjestyksen mukaan. Yleisiä kaavoja ovat:

- **Päivä/Kuukausi/Vuosi** (käytetään suurimmassa osassa Eurooppaa)
- **Kuukausi/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuukausi/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden muotojen sisällä paikalliset variantit sisältävät erotinmerkin valinnan (esim. `-`, `/` tai `.`), ovatko vuodet kaksinumeroisia vai nelinumeroisia ja täytetäänkö yksinumeroiset kuukaudet tai päivät nollilla eteen.

Jotta tämä moninaisuus voitaisiin käsitellä, `MaskedDateField` käyttää muotoilunäyttöjä, jotka alkavat jokainen `%`-merkillä, jota seuraa kirjain, joka edustaa tiettyä osaa päivämäärästä. Nämä näyttöindikaattorit määrittävät, kuinka syöte jäsennetään ja kuinka päivämäärä näytetään.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla tai jäsennellä päivämäärät samalla syntaksilla ulkopuolella kenttää, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Päivämäärämuotoilunäytöt {#date-format-indicators}

| Muoto | Kuvaus |
| ------ | ----------- |
| `%Y`   | Vuosi        |
| `%M`   | Kuukausi       |
| `%D`   | Päivä         |

### Modifikaattorit {#modifiers}

Modifikaattorit mahdollistavat enemmän hallintaa siitä, kuinka päivämäärän osat on muotoiltu:

| Modifikaattori | Kuvaus               |
| --------------- | ------------------------- |
| `z`            | Nollata täytettävä                 |
| `s`            | Lyhyt tekstimuoto |
| `l`            | Pitkä tekstimuoto  |
| `p`            | Koottu numero             |
| `d`            | Kymmenes (oletusmuoto)  |

Näitä voidaan yhdistää, jotta saadaan aikaan laaja valikoima päivämäärämaskeja.

## Päivämäärämuotojen lokalisointi {#date-format-localization}

`MaskedDateField` sopeutuu alueellisiin päivämäärämuotoihin oikean lokaalin asettamalla. Tämä takaa, että päivämäärät näytetään ja jäsennetään tavalla, joka vastaa käyttäjän odotuksia.

| Alue         | Muoto     | Esimerkki      |
| ------------ | ---------- | ------------ |
| Yhdysvallat  | MM/DD/YYYY | `07/04/2023` |
| Eurooppa     | DD/MM/YYYY | `04/07/2023` |
| ISO-standardi | YYYY-MM-DD | `2023-07-04` |

Lokalisoinnin soveltamiseksi käytä `setLocale()`-metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilun että jäsennyksen:

```java
dateField.setLocale(Locale.FRANCE);
```

## Jäsentämislogiikka {#parsing-logic}

`MaskedDateField` jäsentää käyttäjän syötteen määritellyn päivämäärämaskin mukaan. Se hyväksyy sekä täydelliset että lyhennetyet numeeriset syötteet, olipa niissä erottimia tai ei, mahdollistaen joustavan syötön samalla varmistamalla, että päivämäärät ovat voimassa.
Jäsentämiskäyttäytyminen riippuu maskin määrittelemästä järjestyksestä (esim. `%Mz/%Dz/%Yz` kuukaudelle/päivälle/vuodelle). Tämä formaatti määrittelee, kuinka numeerisia sekvenssejä tulkitaan.

Esimerkiksi, olettaen että tänään on `15. syyskuuta 2012`, näin erilaisia syötteitä tulkitaan:

### Esimerkit jäsentämisskenaarioista {#example-parsing-scenarios}

| Syöte                             | YMD (ISO)                                                                                                                                                                    | MDY (US)                                                                                     | DMY (EU)                                                                                     |
| --------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>   | Yksi numero tulkitaan aina päivänumeroksi kuluvassa kuukaudessa, joten tämä olisi 1. syyskuuta 2012.                                                       | Sama kuin YMD                                                                                | Sama kuin YMD                                                                                |
| <div align="center">`12`</div>  | Kaksi numeroa tulkitaan aina päivänumeroksi kuluvassa kuukaudessa, joten tämä olisi 12. syyskuuta 2012.                                                      | Sama kuin YMD                                                                                | Sama kuin YMD                                                                                |
| <div align="center">`112`</div> | Kolme numeroa tulkitaan yhdestä numerosta kuukauden numerona ja kahta numerosta päivän numerona, joten tämä olisi 12. tammikuuta 2012.                    | Sama kuin YMD                                                                                | Kolme numeroa tulkitaan yhdestä numerosta päivän numerona ja kahta numerosta kuukauden numerona, joten tämä olisi 1. joulukuuta 2012. |
| <div align="center">`1004`</div> | Neljä numeroa tulkitaan muodossa MMDD, joten tämä olisi 4. lokakuuta 2012.                                                                                        | Sama kuin YMD                                                                                | Neljä numeroa tulkitaan muodossa DDMM, joten tämä olisi 10. huhtikuuta 2012.                |
| <div align="center">`020304`</div> | Kuusi numeroa tulkitaan muodossa YYMMDD, joten tämä olisi 4. maaliskuuta 2002.                                                                                       | Kuusi numeroa tulkitaan muodossa MMDDYY, joten tämä olisi 3. helmikuuta 2004.               | Kuusi numeroa tulkitaan muodossa DDMMYY, joten tämä olisi 2. maaliskuuta 2004.               |
| <div align="center">`8 digits`</div> | Kahdeksan numeroa tulkitaan muodossa YYYYMMDD. Esimerkiksi, `20040612` on 12. kesäkuuta 2004.                                                                     | Kahdeksan numeroa tulkitaan muodossa MMDDYYYY. Esimerkiksi, `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan muodossa DDMMYYYY. Esimerkiksi, `06122004` on 6. joulukuuta 2004. |
| <div align="center">`12/6`</div> | Kaksi numeroa, jotka on erotettu millä tahansa voimassa olevalla erottimella, tulkitaan muodossa MM/DD, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, paitsi kirjaimet ja numerot, katsotaan voimassa oleviksi erottimiksi. | Sama kuin YMD                                                                                | Kaksi numeroa, jotka on erotettu millä tahansa erottimella, tulkitaan muodossa DD/MM, joten tämä olisi 12. kesäkuuta 2012.                |
| <div align="center">`3/4/5`</div> | 5. huhtikuuta 2012                                                                                                                                                     | 4. maaliskuuta 2005                                                                          | 3. huhtikuuta 2005                                                                           |

## Tekstuaalinen päivämäärän jäsentäminen <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Oletuksena `MaskedDateField` hyväksyy vain numeerista syötettä päivämäärille. Voit kuitenkin aktivoida **tekstuaalisen päivämäärän jäsentämisen** sallimaan käyttäjien syöttää kuukausi- ja päivämäärän nimiä syötteeseensä. Tämä ominaisuus on erityisen hyödyllinen luonnollisemman päivämäärän syöttämisen mahdollistamiseksi.

Aktivoidaksesi tekstuaalisen jäsentämisen, käytä `setTextualDateParsing()` -metodia:

```java
dateField.setTextualDateParsing(true);
```

### Kuukauden nimen korvaaminen {#month-name-substitution}

Kun tekstuaalinen jäsentäminen on käytössä, voit käyttää erityisiä modifikaattoreita maskissasi hyväksyäksesi kuukausinimiä numeeristen arvojen sijaan:

- **`%Ms`** - Hyväksyy lyhyet kuukausinimet (tammi, helmi, maalis, jne.)
- **`%Ml`** - Hyväksyy pitkät kuukausinimet (tammikuu, helmikuu, maaliskuu, jne.)

Kuukauden nimet voivat esiintyä missä tahansa maskin osassa, ja kenttä hyväksyy silti numeerista syötettä varana.

#### Esimerkit

| Maski | Syöte | Tulos |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Kelvollinen** - Jäsentää syyskuun 1. päiväksi, 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Kelvollinen** - Jäsentää syyskuun 1. päiväksi, 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Kelvollinen** - Jäsentää syyskuun 1. päiväksi, 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Kelvollinen** - Numeerinen varatuki toimii edelleen |

:::info
Kaikki 12 kuukautta tuetaan sekä lyhyissä (tammi, helmi, maalis, huhti, touko, kesä, heinä, elo, syys, loka, marras, joulu) että pitkissä (tammikuu, helmikuu, jne.) muodoissa.
:::
### Päivän nimen koristelu {#day-name-decoration}

Viikonpäivän nimiä voidaan sisällyttää syötteeseen paremman luettavuuden vuoksi, mutta ne ovat **koristeellisia vain** ja poistetaan jäsentämisen yhteydessä. Ne eivät vaikuta itse päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyet viikonpäivän nimet (ma, ti, ke, jne.)
- **`%Dl`** - Hyväksyy pitkät viikonpäivän nimet (maanantai, tiistai, keskiviikko, jne.)

:::warning Viikonpäivän nimet vaativat numeerisen päivän
Kun käytetään viikonpäivän nimiä (`%Ds` tai `%Dl`), maskin **täytyy myös sisältää** `%Dz` tai `%Dd` määrittämään todellinen päivännumero. Ilman numeerista päivän osaa syöte on virheellinen.
:::

#### Esimerkit

| Maski | Syöte | Tulos |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mon 09/01/25` | **Kelvollinen** - Päivän nimi on koristeellinen |
| `%Dl %Mz/%Dz/%Yz` | `Monday 09/01/25` | **Kelvollinen** - Päivän nimi on koristeellinen |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Tue` | **Kelvollinen** - Päivän nimi lopussa |
| `%Dl/%Mz/%Yz` | `Monday/09/25` | **Virheellinen** - Puuttuu `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Monday/25` | **Virheellinen** - Puuttuu `%Dz` |

Kaikki 7 viikonpäivää tuetaan sekä lyhyissä (ma, ti, ke, to, pe, la, su) että pitkissä (maanantai, tiistai, jne.) muodoissa.

### Lisäjäsentämissäännöt {#additional-parsing-rules}

Tekstuaalinen päivämäärän jäsentäminen sisältää useita hyödyllisiä ominaisuuksia:

- **Isokirjaimettomuus:** Syöte kuten `MONDAY 09/01/25`, `monday 09/01/25` tai `Monday 09/01/25` toimii kaikki samalla tavalla.
- **Paikallisesti tietoinen:** Kuukausi- ja päivämäärän nimien on vastattava kentän määritettyä lokaalia. Esimerkiksi ranskankielisellä lokaalilla käytä `septembre`, ei `Septemberia`. Englanninkielisiä nimiä ei tunnisteta, ellei lokaalia ole asetettu englanniksi.
  - Ranskankielinen lokaali: `septembre/01/25` tunnistetaan syyskuuna
  - Saksankielinen lokaali: `Montag 09/01/25` tunnistetaan maanantaina

## Min/max-rajoitusten asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallittua päivämääräaluetta `MaskedDateField`-kentässä käyttämällä `setMin()` ja `setMax()` -menetelmiä:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat metodit hyväksyvät arvoja tyypiltä [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Syötteet, jotka ovat määritetyn alueen ulkopuolella, katsotaan virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautusominaisuuden, joka palauttaa kentän arvon ennaltamääritettyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen peruuttamiseksi tai oletuspäivämäärään palauttamiseksi.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusnäppäin, ellei se ole ylikirjoitettu tapahtumakuuntelijalla)

Voit asettaa palautettavan arvon `setRestoreValue()`, jolloin käytetään [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) -instanssia.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validointikaavat {#validation-patterns}

Voit soveltaa asiakaspään validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()` -menetelmällä:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä kaava varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY` -muotoa (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa), katsotaan kelvollisiksi.

:::tip Säännöllisen lausekkeen muoto
Kaavan täytyy noudattaa JavaScript RegExp -syntaksia, kuten on asiakirjoinvissa [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelystä
Kenttä yrittää jäsentää ja muotoilla numeerisia päivämääräsyötteitä nykyisen maskin mukaan. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on syntaktisesti voimassa mutta semanttisesti virheellinen tai jäsentämätön (esim. `99/99/9999`), se voi päästä kaavavalidoituksen läpi, mutta epäloogiseen validointiin.
Sinun tulisi aina validoida syötearvo sovelluslogiikassasi, vaikka säännöllinen lausekekaava olisi asetettu, jotta voit varmistaa, että päivämäärä on sekä oikein muotoiltu että merkityksellinen.
::::

## Päivämäärävalitsija {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsijan, joka antaa käyttäjien valita päivämäärän visuaalisesti sen sijaan, että he kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkka syöttö on tarpeen.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Valitsijan käyttö {#accessing-the-picker}

Voit käyttää päivämäärävalitsijaa käyttämällä `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Valitsijan kuvakkeen näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` -menetelmää näyttämään tai piilottamaan kalenteri-ikoni kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattinen avauskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsijan avaamaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. klikkaa, painaa Enteriä tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Vahvista valinta valitsimen kautta
Varmistaaksesi, että käyttäjät voivat vain valita päivämäärän kalenterivalitsimen avulla (eivätkä kirjoita manuaalisesti), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avataan valitsin käyttäjän vuorovaikutuksessa
dateField.setAllowCustomValue(false);    // Poistaa manuaalisen tekstin syöttämisen käytöstä
```

Tämä asetus varmistaa, että kaikki päivämääräsyötteet tulevat valitsin UI:sta, mikä on hyödyllistä, kun haluat tiukkaa muotoilua ja poistaa jäsentämisongelmia kirjoitetuista syötteistä.
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

Valitsija voi optioalisti näyttää viikon numerot kalenterinäkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) lisäämällä spinner-ohjausosas, jotka antavat käyttäjien lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustavan, erityisesti hyödyllinen työpöytätyylisissä sovelluksissa.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Keskeiset ominaisuudet {#key-features}

- **Interaktiivinen päivämäärän askel:**  
  Käytä nuolinäppäimiä tai kääntöpainikkeita lisätäksesi tai vähentääksesi päivämääräarvoa.

- **Mukautettava askeleen yksikkö:**  
  Valitse, mikä osa päivämäärästä muokataan käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehdot sisältävät `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**  
  Tukee vähimmäis- ja enimmäispäivämäärien asettamista käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu tulos:**  
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa `MaskedDateField` -komponentista.

### Esimerkki: Määritä viikoittainen asteittaisuus {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä saa jokaisen askelten muutoksen edistämään tai peruutamaan päivämäärää yhdellä viikolla.

## Tyylittely {#styling}

<TableBuilder name="MaskedDateField" />
