---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 5f6c175ffd4b8d75f3b65c7b77bb13fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField`-komponentti tarjoaa konfiguroitavan tekstinsyötön, joka valvoo muotoilusääntöjä ja validointia. Se soveltuu hyvin sovelluksiin, joissa tarvitaan rakenneosia, kuten talous-, verkkokauppa- ja terveydenhuoltojärjestelmissä.

Tätä komponenttia voidaan alustaa parametreilla tai ilman. Voit määrittää alkuarvon, oletustekstin, paikkamerkin sekä kuuntelijan, jos arvo muuttuu.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Tili ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: SAVE-2025")
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstinsyötön käyttämällä maskia - merkkijonoa, joka määrittää, mitkä merkit ovat sallittuja kussakin paikassa. Tämä varmistaa johdonmukaisen, rakenteisen syötön, kuten puhelinnumerot, postinumerot ja henkilötunnusmuodot.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla merkkijonoja saman maskisynnin mukaan kentän ulkopuolella, esimerkiksi tietojen renderöinnissä [`Table`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                     |
|-----------|-------------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettavissa oleva merkki                                                 |
| `a`       | Mikä tahansa aakkosellinen merkki (isot tai pienet kirjaimet)                          |
| `A`       | Mikä tahansa aakkosellinen merkki; pienet kirjaimet muunnetaan isoiksi                  |
| `0`       | Mikä tahansa numero (0–9)                                                                  |
| `z`       | Mikä tahansa numero tai kirjain (isot tai pienet kirjaimet)                             |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muunnetaan isoiksi                    |

Kaikkia muita merkkejä maskissa käsitellään kirjaimellisina ja ne on kirjoitettava tarkasti.
Esimerkiksi, maski kuten `XX@XX` vaatii käyttäjältä `@` merkin syöttämistä keskelle.

- **Virheelliset merkit** ohitetaan hiljaa.
- **Lyhyet syötteet** täyteen tilaa.
- **Pitkät syötteet** katkaistaan maskin mukaisiksi.

### Esimerkkejä {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (Kanadan postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täysi syöte sallittu
Jos maski sisältää vain `X`, kenttä toimii kuten tavallinen [`TextField`](../textfield), salliien minkä tahansa tulostettavan sisäänsyötön.
Tämä on hyödyllistä, kun haluat säilyttää mahdollisuuden muotoilla ilman tiukkoja merkkisääntöjä.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Vahvistuskuviot {#validation-patterns}

Vaikka maskit määrittelevät syötteen rakenteen, voit yhdistää ne vahvistuskuvioihin tiukempien syöttösääntöjen valvomiseksi. Tämä lisää ylimääräisen kerroksen asiakaspään validoimista käyttäen säännöllisiä lausekkeita.

Käytä `setPattern()`-metodia soveltaaksesi mukautettua säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkin alfanumeerisen koodin
```

Tämä varmistaa, että syöte ei vain vastaa maskia, vaan myös noudattaa määriteltyä rakennetta, kuten pituutta tai sallittuja merkkejä.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liikaa joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. hex, Base64, UUID)

:::tip Säännöllisen lausekkeen muoto
Kuvion on oltava voimassa [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten käytetään `RegExp`-tyypissä. Lisätietoja löytyy [HTML -kuvion attribuutin dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautusominaisuuden, joka palauttaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan.
Tämä voi olla hyödyllistä käyttäjän tekemien muutosten kumoamisessa tai oletustietojen palauttamisessa.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspaluunäppäin, ellei tapahtumakuuntelija ylikirjoita tätä)

Voit määrittää palautettavan arvon `setRestoreValue()`:llä. Jos palautusarvoa ei ole asetettu, kenttä palautuu alkuperäiseen arvoon renderöintihetkellä.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa `MaskedTextField`-komponenttia lisäämällä pyörityskontrolleja, jotka antavat käyttäjien selata ennalta määriteltyjen arvojen luetteloa.
Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syötteen pitäisi rajoittua ennalta määriteltyihin kelvollisiin vaihtoehtoihin.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Tärkeimmät ominaisuudet {#key-features}

- **Vaihtoehtoluettelo tukee**
  Täytä pyörityskontrolli kelvollisten merkkijonojen luettelolla käyttäen `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen pyöritys**
  Käytä `spinUp()` ja `spinDown()` siirtyäksesi vaihtoehtojen läpi:

  ```java
  spinner.spinUp();   // Valitsee seuraavan vaihtoehdon
  spinner.spinDown(); // Valitsee edellisen vaihtoehdon
  ```

- **Indeksinhallinta**
  Aseta tai hae nykyinen valinnan indeksi:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskin yhteensopivuus**
  Perii täysin kaikki muotoilut, maskisäännöt ja kuvion validoinnin `MaskedTextField`-komponentilta.

## Tyylittely {#styling}

<TableBuilder name="MaskedTextField" />
