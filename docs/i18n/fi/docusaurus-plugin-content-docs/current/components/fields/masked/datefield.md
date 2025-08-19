---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: f76242de3a742ad3a930e1581f688592
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

`MaskedDateField` on tekstiinputtiohjaus, joka on suunniteltu jäsenneltyyn päivämäärien syöttämiseen. Se sallii käyttäjien syöttää päivämääriä **numeroina** ja automaattisesti muotoilee syötteen määritellyn maskin perusteella, kun kenttä menettää fokuksen. Maski on merkkijono, joka määrittää odotetun päivämäärämuodon, ohjaten sekä syötettä että näyttöä.

Tämä komponentti tukee joustavaa jäsentämistä, validoimista, lokalisointia ja arvon palauttamista. Se on erityisen hyödyllinen lomakkeissa, kuten rekisteröinneissä, varauksissa ja aikataulutuksessa, joissa vaaditaan johdonmukaisia ja aluekohtaisia päivämäärämuotoja.

:::tip Etsitkö aikasyöttöä?
`MaskedDateField` keskittyy pelkästään **päivämäärä** arvoihin. Jos tarvitset vastaavaa komponenttia ajan syöttämiseen ja muotoiluun, katso [`MaskedTimeField`](./timefield).
:::

## Perusteet {#basics}

`MaskedDateField` voidaan instansioida joko parametreilla tai ilman. Voit määrittää alkusarjan, etiketin, paikkamerkin ja tapahtumakuuntelijan arvojen muutoksiin.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskisäännöt {#mask-rules}

`MaskedDateField` tukee useita päivämäärämuotoja, joita käytetään eri puolilla maailmaa, jotka vaihtelevat päivän, kuukauden ja vuoden järjestyksen mukaan. Yleisiä kuvioita ovat:

- **Päivä/Kuu/Vuosi** (käytetään useimmissa Euroopan maissa)
- **Kuu/Päivä/Vuosi** (käytetään Yhdysvalloissa)
- **Vuosi/Kuu/Päivä** (käytetään Kiinassa, Japanissa ja Koreassa; myös ISO-standardi: `YYYY-MM-DD`)

Näissä muodoissa paikalliset variaatiot sisältävät erottimen valinnan (esim. `-`, `/` tai `.`), onko vuosia kaksi vai neljä numeroa, ja ovatko yksinumeroiset kuukaudet tai päivät täytetty etunollilla.

Käsitelläkseen tätä monimuotoisuutta `MaskedDateField` käyttää formaatti-indikaattoreita, jotka alkavat aina `%`:llä ja joita seuraa kirjain, joka edustaa jokaisen osan päivämäärästä. Nämä indikaattorit määrittävät, miten syöte jäsennetään ja miten päivämäärä näytetään.

### Päivämääräformaatti-indikaattorit {#date-format-indicators}

| Muoto | Kuvaus     |
| ----- | ---------- |
| `%Y`  | Vuosi      |
| `%M`  | Kuukausi   |
| `%D`  | Päivä      |

### Muokkaimet {#modifiers}

Muokkaimet mahdollistavat enemmän hallintaa sen suhteen, miten päivämäärän osia muotoillaan:

| Muokkain | Kuvaus                       |
| -------- | ----------------------------- |
| `z`      | Nollatäyttö                   |
| `s`      | Lyhyt tekstiesitys           |
| `l`      | Pitkä tekstiesitys           |
| `p`      | Pakattu numero                |
| `d`      | Desimaalimuoto (oletusmuoto) |

Nämä voidaan yhdistää rakentamaan laaja valikoima päivämäärämaskeja.

## Päivämäärämuodon lokalisointi {#date-format-localization}

`MaskedDateField` mukautuu alueellisiin päivämäärämuotoihin asettamalla asianmukaisen kielitunnuksen. Tämä varmistaa, että päivämäärät näytetään ja jäsennetään tavalla, joka vastaa käyttäjän odotuksia.

| Alue          | Muoto     | Esimerkki        |
| ------------- | --------- | ---------------- |
| Yhdysvallat   | MM/DD/YYYY| `07/04/2023`     |
| Eurooppa      | DD/MM/YYYY| `04/07/2023`     |
| ISO-standardi | YYYY-MM-DD| `2023-07-04`     |

Käytä lokalisointia varten `setLocale()` -metodia. Se hyväksyy [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) ja säätää automaattisesti sekä muotoilua että jäsentämistä:

```java
dateField.setLocale(Locale.FRANCE);
```

## Jäsentämislogiikka {#parsing-logic}

`MaskedDateField` jäsentää käyttäjän syötteen määritellyn päivämäärämaskin perusteella. Se hyväksyy sekä täydelliset että lyhennetyt numeeriset syötteet erottimilla tai ilman, mikä mahdollistaa joustavan syötteen, samalla kun varmistetaan, että päivämäärät ovat voimassa.
Jäsentämiskäyttäytyminen riippuu maskissa määritellystä muototilasta (esim. `%Mz/%Dz/%Yz` kuukaudelle/päivälle/vuodelle). Tämä muoto määräytyy sen mukaan, miten numeeriset merkkijonot tulkitaan.

Esimerkiksi, olettaen, että tänään on `15. syyskuuta 2012`, näin erilaiset syötteet tulkitaan:

### Esimerkit jäsentämisestä {#example-parsing-scenarios}

| Syöttö                              | YMD (ISO)                                                                                                                                                                                          | MDY (US)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Yksi numero tulkitaan aina kuukauden nykyisen päivän numerona, joten tämä olisi 1. syyskuuta 2012.                                                                                 | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`12`</div>       | Kaksi numeroa tulkitaan aina kuukauden nykyisen päivän numerona, joten tämä olisi 12. syyskuuta 2012.                                                                                   | Sama kuin YMD                                                                         | Sama kuin YMD                                                                                                                  |
| <div align="center">`112`</div>      | Kolme numeroa tulkitaan 1-numeroisena kuukauden numerona, jota seuraa 2-numeroisesti päivänumero, joten tämä olisi 12. tammikuuta 2012.                                                                        | Sama kuin YMD                                                                         | Kolme numeroa tulkitaan 1-numeroisena päivän numerona, jota seuraa 2-numeroisen kuukauden numero, joten tämä olisi 1. joulukuuta 2012. |
| <div align="center">`1004`</div>     | Neljä numeroa tulkitaan MMDD-muodossa, joten tämä olisi 4. lokakuuta 2012.                                                                                                                             | Sama kuin YMD                                                                         | Neljä numeroa tulkitaan DDMM-muodossa, joten tämä olisi 10. huhtikuuta 2012.                                                         |
| <div align="center">`020304`</div>   | Kuusi numeroa tulkitaan YYMMDD-muodossa, joten tämä olisi 4. maaliskuuta 2002.                                                                                                                              | Kuusi numeroa tulkitaan MMDDYY-muodossa, joten tämä olisi 3. helmikuuta 2004.            | Kuusi numeroa tulkitaan DDMMYY-muodossa, joten tämä olisi 2. maaliskuuta 2004.                                                         |
| <div align="center">`8 numeroa`</div> | Kahdeksan numeroa tulkitaan YYYYMMDD-muodossa. Esimerkiksi `20040612` on 12. kesäkuuta 2004.                                                                                                                | Kahdeksan numeroa tulkitaan MMDDYYYY-muodossa. Esimerkiksi `06122004` on 12. kesäkuuta 2004. | Kahdeksan numeroa tulkitaan DDMMYYYY-muodossa. Esimerkiksi `06122004` on 6. joulukuuta 2004.                                        |
| <div align="center">`12/6`</div>     | Kaksi numeroa, jotka on erotettu mistä tahansa voimassa olevasta erottimesta, tulkitaan muodossa MM/DD, joten tämä olisi 6. joulukuuta 2012. <br />Huom: Kaikki merkit, paitsi kirjaimet ja numerot, katsotaan voimassa oleviksi erottimiksi. | Sama kuin YMD                                                                         | Kaksi numeroa, jotka on erotettu mistä tahansa erottimesta, tulkitaan muodossa DD/MM, joten tämä olisi 12. kesäkuuta 2012.                               |
| <div align="center">`3/4/5`</div>    | 5. huhtikuuta 2012                                                                                                                                                                                      | 4. maaliskuuta 2005                                                                       | 3. huhtikuuta 2005                                                                                                                 |

## Min/max-rajoitusten asettaminen {#setting-minmax-constraints}

Voit rajoittaa sallitun päivämääräalueen `MaskedDateField`-komponentissa käyttämällä `setMin()` ja `setMax()` -menetelmiä:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Molemmat metodit hyväksyvät arvoja tyyppiä [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Syöttö, joka on määritellyn alueen ulkopuolella, katsotaan virheelliseksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedDateField` sisältää palautusominaisuuden, joka palauttaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan. Tämä on hyödyllistä käyttäjän syötteen palauttamiseksi tai palauttamiseksi oletuspäivämäärään.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näyttönäppäimistön avulla**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusavain, ellei tapahtumakuuntelijalla ole toisin määritelty)

Voit asettaa palautettavan arvon `setRestoreValue()`-metodilla, välittämällä [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instanssin.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validointikuviot {#validation-patterns}

Voit soveltaa asiakassivujen validointisääntöjä käyttämällä säännöllisiä lausekkeita `setPattern()`-menetelmällä:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Tämä kuvio varmistaa, että vain arvot, jotka vastaavat `MM/DD/YYYY` muotoa (kaksi numeroa, vinoviiva, kaksi numeroa, vinoviiva, neljä numeroa) katsotaan voimassa oleviksi.

:::tip Säännöllinen lauseke -muoto
Kuvion on noudatettava JavaScriptin RegExp-synteesiä, kuten on asiakirjoitettu [täällä](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Huomautuksia syötteen käsittelyyn
Kenttä yrittää jäsentää ja muotoilla numeeriset päivämääräsyötteet nykyisen maskin perusteella. Kuitenkin, käyttäjät voivat silti syöttää manuaalisesti arvoja, jotka eivät vastaa odotettua muotoa. Jos syöttö on syntaktisesti voimassa, mutta semanttisesti virheellinen tai jäsentämätön (esim. `99/99/9999`), se voi ohittaa kuvion tarkat, mutta epäonnistua loogisessa validoinnissa.
Sinun tulisi aina validoida syötearvo sovelluksen logiikassasi, vaikka säännöllinen lauseke olisi asetettu, varmistaaksesi, että päivämäärä on sekä oikein muotoiltu että mielekäs.
::::

## Päivämäärävalitsin {#date-picker}

`MaskedDateField` sisältää sisäänrakennetun kalenterivalitsimen, joka sallii käyttäjien valita päivämäärän visuaalisesti sen sijaan, että he kirjoittaisivat sen. Tämä parantaa käytettävyyttä vähemmän teknisille käyttäjille tai kun tarkka syöttö on tarpeen.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Pipetön valitsimen käyttö {#accessing-the-picker}

Voit käyttää päivämäärävalitsinta `getPicker()`-menetelmällä:

```java
DatePicker picker = dateField.getPicker();
```

### Näytä/piilota valitsimen ikoni {#showhide-the-picker-icon}

Käytä `setIconVisible()`-menetelmää näyttääksesi tai piilottaaksesi kalenteri-ikonin kentän vieressä:

```java
picker.setIconVisible(true); // näyttää ikonin
```

### Automaattinen avautuminen {#auto-open-behavior}

Voit määrittää valitsimen avautumaan automaattisesti, kun käyttäjä toimii kentän kanssa (esim. napsauttaa, painaa Enter tai nuolinäppäimiä):

```java
picker.setAutoOpen(true);
```

:::tip Vaatimus vain valitsimen kautta
Varmistaaksesi, että käyttäjät voivat valita päivämäärän vain kalenterivalitsimen avulla (eivätkä syöttäen sitä manuaalisesti), yhdistä seuraavat kaksi asetusta:

```java
dateField.getPicker().setAutoOpen(true); // avaa valitsimen käyttäjän toiminnan yhteydessä
dateField.setAllowCustomValue(false);    // estää manuaalisen tekstisyötön
```

Tämä asetus takaa, että kaikki päivämääräsyötteet tulevat valitsimen käyttöliittymästä, mikä on hyödyllistä, kun haluat tiukan muotoilun hallinnan ja estää jäsentämisongelmia kirjoitettavasta syötteestä.
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

Valitsija voi valinnaisesti näyttää viikkonumerot kalenterinäkymässä:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

`MaskedDateFieldSpinner` laajentaa [`MaskedDateField`](#basics) lisäämällä pyörityskontrolleja, jotka sallivat käyttäjien lisätä tai vähentää päivämäärää nuolinäppäimillä tai käyttöliittymän painikkeilla. Se tarjoaa ohjatumman vuorovaikutustyylin, mikä on erityisen hyödyllistä työpöytäsovelluksissa.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Keskeiset ominaisuudet {#key-features}

- **Interaktiivinen päivämäärän säätäminen:**  
  Käytä nuolinäppäimiä tai kääntöpainikkeita päivämääräarvon lisäämiseksi tai vähentämiseksi.

- **Mukautettava askel-yksikkö:**  
  Valitse, mikä osa päivämäärästä muutetaan käyttämällä `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Vaihtoehdot sisältävät `DAY`, `WEEK`, `MONTH` ja `YEAR`.

- **Min/Max-rajoitukset:**  
  Perii tuen minimien ja maksimiarvojen asettamiselle käyttäen `setMin()` ja `setMax()`.

- **Muotoiltu lähtö:**  
  Täysin yhteensopiva `MaskedDateField` maskien ja lokalisointiasetusten kanssa.

### Esimerkki: Määritä viikkosäätö {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Tämä tekee jokaisesta käännöstä eteen- tai taaksepäin yhden viikon verran päivämäärää.

## Tyylit {#styling}

<TableBuilder name="MaskedDateField" />
