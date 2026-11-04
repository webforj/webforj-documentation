---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 9ee372191dc17b2510f020bc908f0060
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField` -komponentti tarjoaa konfiguroitavan tekstisyötteen, joka valvoo muotoilusääntöjä ja validointia. Se sopii hyvin sovelluksiin, joissa tarvitaan rakenteellista syötettä, kuten rahoitus-, verkkokauppa- ja terveydenhuoltojärjestelmissä.

Tätä komponenttia voidaan instansioida parametreilla tai ilman niitä. Voit määrittää alkupisteen, etiketin, paikkatekstin ja kuuntelijan, jos arvo muuttuu.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Tili-ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: TALLA-2025");
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstisyötteen käyttämällä maskia - merkkijonoa, joka määrittelee, mitä merkkejä saa olla kullakin sijainnilla. Tämä varmistaa johdonmukaisen, rakenteellisen syötteen sellaisille asioille kuin puhelinnumerot, postituskoodit ja henkilötunnusmuodot.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla merkkijonoja saman maskisynnin avulla kentän ulkopuolella, esimerkiksi kun renderöit tietoja [`Table`](/docs/components/table/overview) -komponentissa, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                     |
|-----------|--------------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettava merkki                                                          |
| `a`       | Mikä tahansa aakkosellinen merkki (suuraakkoset tai pienaakkoset)                         |
| `A`       | Mikä tahansa aakkosellinen merkki; pienet kirjaimet muutetaan suuriksi                   |
| `0`       | Mikä tahansa numero (0–9)                                                                  |
| `z`       | Mikä tahansa numero tai kirjain (suuraakkoset tai pienaakkoset)                           |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muutetaan suuriksi                      |

Kaikki muut merkit maskissa käsitellään kirjaimina ja ne on kirjoitettava tarkasti.
Esimerkiksi maski kuten `XX@XX` vaatii käyttäjältä merkin `@` syöttämistä keskelle.

- **Virheelliset merkit** ohitetaan hiljaa.
- **Lyhyt syöte** täytetään välikohtia.
- **Pitkä syöte** lyhennetään mahtuakseen maskiin.

### Esimerkkejä {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A0A 0A0");            // Esimerkki: A1B 2C3 (Kanadan postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täydellinen syöte sallittu
Jos maskissa on vain `X`, kenttä toimii kuten tavallinen [`TextField`](../textfield), salliessaan minkä tahansa tulostettavan syötteen.
Tämä on hyödyllistä, kun haluat varata mahdollisuuden muotoilla ilman tiukkoja merkkisääntöjä.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validointimallit {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne validointimalleihin pakottaaksesi tarkempia syöttösääntöjä. Tämä lisää ylimääräisen asiakaspinnan validoinnin käyttämällä säännöllisiä lausekkeita.

Käytä `setPattern()`-metodia soveltaaksesi tiettyä säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkisen alfanumeerisen koodin
```

Tämä varmistaa, että syöte ei ainoastaan vastaa maskia, vaan myös noudattaa määriteltyä rakennetta, kuten pituus tai sallitut merkit.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liian paljon joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. heksadesimaali, Base64, UUID)

:::tip Säännöllisen lausekkeen muoto
Malli on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten käytetään `RegExp`-tyypissä. Lisätietoja löydät [HTML:n malliattribuutin dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautustoiminnon, joka palauttaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan.
Tämä voi olla hyödyllistä käyttäjän muutosten kumoamiseksi tai oletussyötteen palauttamiseksi.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusnäppäin, ellei sitä ole ylikirjoitettu tapahtumakuuntelijalla)

Voit asettaa palautettavan arvon `setRestoreValue()`-metodilla. Jos palautusarvoa ei ole asetettu, sekä `restoreValue()` että <kbd>ESC</kbd> asettavat kentän arvoksi `null`.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa `MaskedTextField`-komponenttia lisäämällä pyörintäohjaimia, jotka mahdollistavat käyttäjien siirtyä esiväksi määriteltyjen arvojen luettelon läpi. Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syötteen tulisi rajoittua kiinteään joukkoon voimassa olevia vaihtoehtoja.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vaihtoehtoluettelo**
  Täytä pyörimistaja kelvollisten merkkijonovaihtoehtojen luettelolla käyttäen `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen pyöriminen**
  Käytä `spinUp()` ja `spinDown()` liikuttaaksesi vaihtoehtoja:

  ```java
  spinner.spinUp();   // Valitsee seuraavan vaihtoehdon
  spinner.spinDown(); // Valitsee edellisen vaihtoehdon
  ```

- **Indeksin hallinta**
  Aseta tai hae nykyisen valinnan indeksi:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskin yhteensopivuus**
  Perii täysin kaikki muotoilut, maskisäännöt ja mallivalidoinnin `MaskedTextField`-komponentista.

## Tyylittely {#styling}

<TableBuilder name="MaskedTextField" />
