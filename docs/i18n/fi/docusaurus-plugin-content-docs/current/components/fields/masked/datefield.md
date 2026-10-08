---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: d63b5c4325ef201b54da5b78b4e66f1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on tekstikenttä, joka mahdollistaa käyttäjien syöttää päivämääriä numeroina ja muuntaa syötteen automaattisesti määritellyn maskin mukaan, kun kenttä menettää keskittyksensä. Maski määrittää odotettavan päivämäärämuodon, opastaa sekä syötteessä että näytössä. Komponentti tukee joustavaa jäsennystä, validointia, lokalisointia ja arvon palauttamista johdonmukaisen, aluekohtaisen päivämääräkäytön varmistamiseksi.

<!-- INTRO_END -->

:::tip Etsitkö aikasyötettä?
`MaskedDateField` keskittyy vain **päivämäärä** arvoihin. Jos tarvitset samankaltaista komponenttia aikojen syöttämiseen ja muotoiluun, tutustu sen sijaan [`MaskedTimeField`](/docs/components/fields/masked/timefield) komponenttiin.
:::

`MaskedDateField` voidaan instansioida parametreilla tai ilman. Voit määrittää alkusarjan, tunnisteen, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksille.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita ympäri maailmaa käytettäviä päivämääräformaatteja, jotka vaihtelevat päivämäärän, kuukauden ja vuoden järjestyksen mukaan. Yleisiä malleja ovat:

- **Päivä/Kuukausi/Vuosi** (käytetään suurimmassa osassa Eurooppaa)
- **Kuukausi/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuukausi/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näiden formaatien sisällä paikalliset variantit sisältävät erottimen valinnan (esim. `-`, `/`, tai `.`), ovatko vuodet kaksinumeroisia vai nelinumeroisia, ja ovatko yksinumeroiset kuukaudet tai päivät nollatut.

Käsitelläkseen tätä moninaisuutta, `MaskedDateField` käyttää formaatti-indikaattoreita, jotka alkavat `%`:llä, jota seuraa kirjain, joka edustaa tiettyä päivämäärän osaa. Nämä indikaattorit määrittävät, miten syöte jäsennetään ja miten päivämäärä näytetään.

:::tip Maskien soveltaminen ohjelmallisesti
Jotta voit muotoilla tai jäsentää päivämäriä saman maskin syntaksilla kentän ulkopuolella, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) apuluokkaa.
:::

### Päivämäärämuotoindikaattorit {#date-format-indicators}

| Muoto | Kuvaus      |
| ------ | ----------- |
| `%Y`   | Vuosi       |
| `%M`   | Kuukausi    |
| `%D`   | Päivä       |

### Muuttujat {#modifiers}

Muuttujat antavat enemmän hallintaa siihen, miten päivämäärän osat muotoillaan:

| Muuttuja | Kuvaus                            |
| -------- | --------------------------------- |
| `z`      | Nollaa täyttö                    |
| `s`      | Lyhyt tekstiversio               |
| `l`      | Pitkä tekstiversio                |
| `p`      | Pakattu numero                    |
| `d`      | Desimaalimuoto (oletus)          |

Näitä voidaan yhdistää rakentamaan monenlaisia päivämäärämaskeja.

## Päivämäärän muotoilun lokalisointi {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämääräformaatteihin asettamalla sopiva lokaali. Tämä varmistaa, että päivämäärät näytetään ja jäsennetään tavalla, joka vastaa käyttäjän odotuksia.

| Alue         | Muoto      | Esimerkki     |
| ------------ | ---------- | ------------- |
| Yhdysvallat  | MM/DD/YYYY | `07/04/2023`  |
| Eurooppa     | DD/MM/YYYY | `04/07/2023`  |
| ISO-standardi| YYYY-MM-DD | `2023-07-04`  |

Voit soveltaa lokalisointia käyttämällä `setLocale()` metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilua että jäsentämistä:

```java
dateField.setLocale(Locale.FRANCE);
```

## Jäsentämislogiikka {#parsing-logic}

`MaskedDateField` jäsentää käyttäjän syötteen määritetyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erottimilla tai ilman, mikä mahdollistaa joustavan syöttämisen samalla varmistaen voimassa olevat päivämäärät.
Jäsentämiskäyttäytyminen riippuu maskin määrittämästä järjestyksestä (esim. `%Mz/%Dz/%Yz` kuukaudelle/päivälle/vuodelle). Tämä muoto määrittää, miten numeeriset sekvenssit tulkitaan.

Esimerkiksi, olettaen, että tänään on `15. syyskuuta 2012`, tällöin erilaisia syötteitä tulkitaan seuraavasti:

### Esimerkit jäsentämisestä {#example-parsing-scenarios}

| Syöte                                   | YMD (ISO)                                                                                                                                                                                          | MDY (USA)                                                                            | DMY (EU)                                                                                                                     |
|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------|
| <div align="center">`1`</div>           | Yksi numero tulkitaan aina päiväluvuksi nykyisessä kuussa, joten tämä olisi 1. syyskuuta 2012.                                                                                                  | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`12`</div>          | Kaksi numeroa tulkitaan aina päiväluvuksi nykyisessä kuussa, joten tämä olisi 12. syyskuuta 2012.                                                                                                | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`112`</div>         | Kolme numeroa tulkitaan 1-numeroisena kuun numerona, jota seuraa 2-numeroiseksi päiväksi, joten tämä olisi 12. tammikuuta 2012.                                                                  | Sama kuin YMD                                                                         | Kolme numeroa tulkitaan 1-numeroisena päiväluvuna, jota seuraa 2-numeroiseksi kuun numeroksi, joten tämä olisi 1. joulukuuta 2012. |
| <div align="center">`1004`</div>        | Neljä numeroa tulkitaan MMDD, joten tämä olisi 4. lokakuuta 2012.                                                                                                                              | Sama kuin YMD                                                                         | Neljä numeroa tulkitaan DDMM, joten tämä olisi 10. huhtikuuta 2012.                                                         |
| <div align="center">`020304`</div>      | Kuusi numeroa tulkitaan YYMMDD, joten tämä olisi 4. maaliskuuta 2002.                                                                                                                          | Kuusi numeroa tulkitaan MMDDYY, joten tämä olisi 3. helmikuuta 2004.               | Kuusi numeroa tulkitaan DDMMYY, joten tämä olisi 2. maaliskuuta 2004.                                                         |
| <div align="center">`8 numeroa`</div>   | Kahdeksan numeroa tulkitaan YYYYMMDD. Esimerkiksi, `20040612` on 12. kesäkuuta 2004.                                                                                                           | Kahdeksan numeroa tulkitaan MMDDYYYY. Esimerkiksi, `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan DDMMYYYY. Esimerkiksi, `06122004` on 6. joulukuuta 2004.                                        |
| <div align="center">`12/6`</div>        | Kaksi numeroa, joita erottaa mikä tahansa voimassa oleva erottaja, tulkitaan MM/DD, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, paitsi kirjaimet ja numerot, on hyväksyttyjä erottajia. | Sama kuin YMD                                                                         | Kaksi numeroa, joita erottaa mikä tahansa erottaja, tulkitaan DD/MM, joten tämä olisi 12. kesäkuuta 2012.                               |
| <div align="center">`3/4/5`</div>       | 5. huhtikuuta 2012                                                                                                                                                                                  | 4. maaliskuuta 2005                                                                  | 3. huhtikuuta 2005                                                                                                                 |


## Tekstuaalinen päivämäärän jäsentäminen <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Oletuksena `MaskedDateField` hyväksyy vain numeerista syötettä päivämäärille. Voit kuitenkin mahdollistaa **tekstuaalisen päivämäärän jäsentämisen** salliaksesi käyttäjien syöttää kuukausi- ja päivämäärinimiä syötteissään. Tämä ominaisuus on erityisen hyödyllinen, kun halutaan luoda luonnollisempaa päivämääräsyöttöä.

Ottaaksesi käyttöön tekstuaalisen jäsentämisen, käytä `setTextualDateParsing()` metodia:

```java
dateField.setTextualDateParsing(true);
```

### Kuukauden nimen korvaukset {#month-name-substitution}

Kun tekstuaalinen jäsentäminen on otettu käyttöön, voit käyttää erityisiä muuttujia maskissasi hyväksyäksesi kuukauden nimiä numeeristen arvojen sijaan:

- **`%Ms`** - Hyväksyy lyhyet kuukausinimet (Tammi, Helmi, Maalis, jne.)
- **`%Ml`** - Hyväksyy pitkät kuukausinimet (Tammikuu, Helmikuu, Maaliskuu, jne.)

Kuukauden nimet voivat esiintyä missä tahansa tavassa maskissa, ja kenttä hyväksyy silti numeerista syötettä varajärjestelmänä.

#### Esimerkit {#examples}

| Maski | Syöte          | Tulokset                              |
| ----- | -------------- | ------------------------------------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25`   | **Voimassa** - Jäsentää 1. syyskuuta 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Voimassa** - Jäsentää 1. syyskuuta 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Voimassa** - Jäsentää 1. syyskuuta 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Voimassa** - Numeerinen varajärjestelmä toimii vielä |

:::info
Kaikki 12 kuukautta ovat tuettuja sekä lyhyissä (Tammi, Helmi, Maalis, Huhti, Touko, Kesä, Heinä, Elo, Syys, Loka, Marras, Joulukuu) että pitkissä (Tammikuu, Helmikuu, jne.) muodoissa.
:::
### Päivän nimen koristelut {#day-name-decoration}

Viikonpäivän nimet voidaan sisällyttää syötteeseen parempaa luettavuutta varten, mutta ne ovat **koristeellisia vain** ja ne poistetaan jäsentämisen yhteydessä. Ne eivät vaikuta itse päivämääräarvoon.

- **`%Ds`** - Hyväksyy lyhyet viikonpäivän nimet (Ma, Ti, Ke, jne.)
- **`%Dl`** - Hyväksyy pitkät viikonpäivän nimet (Maanantai, Tiistai, Keskiviikko, jne.)

:::warning Päivän nimet vaativat numeerisen päivän
Kun käytät viikonpäivän nimiä (`%Ds` tai `%Dl`), maskisi **on myös sisällyttävä** `%Dz` tai `%Dd` määrittääkseen todellisen päivän numeron. Ilman numeerista päiväkohteen komponenttia syöte on kelpaamaton.
:::

#### Esimerkit {#examples-1}

| Maski | Syöte                 | Tulokset                              |
| ----- | --------------------- | ------------------------------------- |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25`      | **Voimassa** - Päivän nimi on koristeellista |
| `%Dl %Mz/%Dz/%Yz` | `Maanantai 09/01/25` | **Voimassa** - Päivän nimi on koristeellista |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Ti`    | **Voimassa** - Päivän nimi lopussa    |
| `%Dl/%Mz/%Yz` | `Maanantai/09/25` | **Kelpaamaton** - Puuttuu `%Dz`     |
| `%Mz/%Dl/%Yz` | `09/Maanantai/25` | **Kelpaamaton** - Puuttuu `%Dz`     |

Kaikki 7 viikonpäivää ovat tuettuja sekä lyhyissä (Ma, Ti, Ke, To, Pe, La, Su) että pitkissä (Maanantai, Tiistai, jne.) muodoissa.

### Lisäjäsentämissäännöt {#additional-parsing-rules}

Tekstuaalinen päivämäärän jäsentäminen sisältää useita hyödyllisiä ominaisuuksia:

- **Suurta ja pientä kirjainherkkä:** Syöte kuten `MAANANTAI 09/01/25`, `maanantai 09/01/25` tai `Maanantai 09/01/25` kaikki toimivat samalla tavalla.
- **Paikallistietoinen:** Kuukauden ja päivän nimet on oltava yhteensopivia kentän määritetyn lokaalin kanssa. Esimerkiksi, Ranskan lokaalissa, käytä `septembre` ei `September`. Englanninkielisiä nimiä ei tunnisteta, ellei lokaali ole asetettu englanniksi.
  - Ranskan lokaalilla: `septembre/01/25` tunnistetaan syyskuuksi
  - Saksan lokaalilla: `Montag 09/01/25` tunnistetaan maanantaina

## Min/max-rajojen asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField` käyttämällä `setMin()` ja `setMax()` metodeja:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat metodit hyväksyvät [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) tyypin arvoja. Määritetyn alueen ulkopuolella oleva syöte katsotaan kelpaamattomaksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautustoiminnon, joka palauttaa kentän arvon ennalta määritettyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen palauttamiseen tai oletuspäivämäärään palauttamiseen.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei tapahtumakuuntelijalla ole määritetty muuta)

Voit asettaa palautettavan arvon `setRestoreValue()` metodilla, joka ottaa vastaan [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instanssin.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validointimallit {#validation-patterns}

Voit soveltaa asiakaspään validointisääntöjä käyttäen säännöllisiä lausekkeita `setPattern()` metodilla:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä malli varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY` formaattia (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa), katsotaan kelpaaviksi.

:::tip Säännöllisen lausekkeen muoto
Mallin on seurattava JavaScriptin RegExp-syntaksia, kuten on dokumentoitu [tässä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomiot syöte käsittelyssä
Kenttä yrittää jäsentää ja muotoilla numeerisia päivämääräsyötteitä nykyisen maskin perusteella. Käyttäjät voivat kuitenkin silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöte on synnällisesti oikein mutta merkityksellisesti virheellinen tai jäsentämätön (esim. `99/99/9999`), se voi läpäistä mallintarkastukset mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksesi logiikassa, vaikka säännöllinen lausekemalli olisi asetettu, varmistaaksesi, että päivämäärä on sekä oikein muotoiltu että merkityksellinen.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka mahdollistaa käyttäjien valita päivämäärän visuaalisesti sen sijaan, että syöttäisivät sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkkaa syöttöä tarvitaan.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Pääsy valitsimeen {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta käyttämällä `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Valitsimen kuvakkeen näyttäminen/piilottaminen {#showhide-the-picker-icon}

Käytä `setIconVisible()` näyttääksesi tai piilottaaksesi kalenteri kuvakkeen kentän vieressä:

```java
picker.setIconVisible(true); // näyttää kuvakkeen
```

### Automaattinen avauskäyttäytyminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä vuorovaikuttaa kentän kanssa (esim. napsauttaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Varmista valinta valitsimen kautta
Jotta käyttäjät voivat valita päivämäärän vain käyttämällä kalenterivalitsinta (eivätkä kirjoittamalla itse), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // Avaa valitsimen käyttäjän vuorovaikutuksessa
dateField.setAllowCustomValue(false);    // Estää manuaalisen tekstisyötteen
```

Tämä asetelma takaa, että kaikki päivämääräsyötteet tulevat valitsimen käyttöliittymän kautta, mikä on hyödyllistä, kun haluat tiukan muotoilun hallinnan ja välttää jäsentämisongelmia kirjoitettavasta syötteestä.
:::

### Avaa kalenteri manuaalisesti {#manually-open-the-calendar}

Avaaksesi kalenterin ohjelmallisesti:

```java
picker.open();
```

Tai käytä aliasia:

```java
picker.show(); // sama kuin open()
```

### Näytä viikot kalenterissa {#show-weeks-in-the-calendar}

Valitsija voi valinnaisesti näyttää viikonnumeroita kalenterin näkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa `MaskedDateField` lisäämällä spinnerikontrollit, joiden avulla käyttäjät voivat lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumpaa vuorovaikutustyyliä, joka on erityisen hyödyllinen työpöytätason sovelluksissa.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Interaktiivinen päivämäärän säätäminen:**
  Käytä nuolinäppäimiä tai kierrä-painikkeita päivämääräarvon lisäämiseen tai vähentämiseen.

- **Mukautettava askel-yksikkö:**
  Valitse, mitä osaa päivämäärästä muokata käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehtoja ovat `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**
  Perii tuen minimien ja maksimaalisten sallituille päivämäärille käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu tuloste:**
  Täysin yhteensopiva maskien ja lokalisointiasetusten kanssa `MaskedDateField`-komponentista.

### Esimerkki: Määritä viikoittainen säätö {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä saa aikaan, että jokainen kierräysaskel vie aikarajaa eteen- tai taaksepäin viikon verran.

## Tyylittely {#styling}

<TableBuilder name="MaskedDateField" />
