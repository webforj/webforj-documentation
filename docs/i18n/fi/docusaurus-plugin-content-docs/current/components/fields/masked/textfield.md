---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 31f236456f3f30e15115a03275be9132
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField`-komponentin tarkoituksena on tarjota säädettävä ja helposti validoitava tekstisyöttö. Se on hyvin soveltuva sovelluksiin, jotka vaativat muotoiltua syötettä, kuten rahoitus-, verkkokauppa- ja terveydenhuoltosovelluksiin.

## Perusteet {#basics}

`MaskedTextField` voidaan instansioida parametreilla tai ilman. Voit määrittää alkuarvon, labelin, paikkamerkkitekstin ja kuuntelijan, jos arvo muuttuu.

```java
MaskedTextField field = new MaskedTextField("Tilin ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esim: SAVE-2025")
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstisyötteen maskin avulla - merkkijono, joka määrittelee, mitkä merkit ovat sallittuja kussakin paikassa. Tämä varmistaa yhdenmukaisen, rakenteellisen syötteen, kuten puhelinnumerot, postinumerot ja henkilötunnukset.

### Hyväksytyt maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                 |
|-----------|----------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettava merkki                                                      |
| `a`       | Mikä tahansa aakkosellinen merkki (iso tai pieni)                                      |
| `A`       | Mikä tahansa aakkosellinen merkki; pienet kirjaimet muuttuvat isoiksi                 |
| `0`       | Mikä tahansa numero (0–9)                                                              |
| `z`       | Mikä tahansa numero tai kirjain (iso tai pieni)                                        |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muuttuvat isoiksi                    |

Kaikki muut maskin merkinnät käsitellään kirjaimellisesti ja ne on kirjoitettava tarkasti. 
Esimerkiksi maski kuten `XX@XX` vaatii käyttäjältä `@`-merkin syöttämistä keskelle.

- **Hyväksymättömät merkit** saatetaan hiljaa ohittaa.
- **Lyhyt syöte** täytetään välilyönneillä.
- **Pitkä syöte** katkaistaan mahtumaan maskiin.

### Esimerkit {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (kanadalainen postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täysi syöte sallittu
Jos maski sisältää vain `X`, kenttä käyttäytyy kuin tavallinen [`TextField`](../text-field.md), sallien minkä tahansa tulostettavan syötteen. 
Tämä on hyödyllistä, kun haluat säilyttää kyvyn muotoilla ilman tiukkoja merkintosääntöjä.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validointikaaviot {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne validointikaavioihin pakottaaksesi tarkempia syöttösääntöjä. Tämä lisää ylimääräisen tason asiakaspuoleista validointia käyttäen säännöllisiä lausekkeita.

Käytä `setPattern()`-metodia soveltaaksesi mukautettua säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkiseen alfanumeeriseen koodiin
```

Tämä varmistaa, että syöte ei vain vastaa maskia, vaan myös noudattaa määriteltyä rakennetta, kuten pituutta tai sallittuja merkkejä.

Tämä on erityisen hyödyllistä kun:

- Maski sallii liikaa joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. hex, Base64, UUID)

:::tip Säännöllinen lausekkeen muoto
Kaavan on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), jota `RegExp`-tyyppi käyttää. Lisätietoja löytyy [HTML-kaavamäärite-dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautusominaisuuden, joka palauttaa kentän arvon ennalta määritettyyn tai alkuperäiseen tilaan. 
Tämä voi olla hyödyllistä käyttäjän muutosten kumoamisessa tai oletussyötteen palauttamisessa.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuksen palautusnäppäin, ellei tapahtumakuuntelijalla ole määritetty muuta)

Voit asettaa palautettavan arvon `setRestoreValue()`-metodilla. Jos palautusarvoa ei ole asetettu, kenttä palaa alkuperäiseen arvoon siinä vaiheessa, kun se renderöitiin.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa [`MaskedTextField`](#basics) lisäämällä spinnereitä, jotka antavat käyttäjien selata ennalta määriteltyjä arvoja. 
Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syötteen tulisi rajoittua kiinteään validien vaihtoehtojen joukkoon.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vaihtoehtoluettelo**  
  Täytä spinneri kelvollisten merkkijarjestyksien listalla käyttäen `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen kierto**  
  Käytä `spinUp()` ja `spinDown()` siirtyäksesi vaihtoehtojen välillä:

  ```java
  spinner.spinUp();   // Valitsee seuraavan vaihtoehdon
  spinner.spinDown(); // Valitsee edellisen vaihtoehdon
  ```

- **Indeksin hallinta**  
  Aseta tai hae nykyinen valintaindeksi:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskin yhteensopivuus**  
  Perii täysin kaikki muotoilut, maskisäännöt ja kaavan validoinnit `MaskedTextField`-komponentilta.

## Tyylitys {#styling}

<TableBuilder name="MaskedTextField" />
