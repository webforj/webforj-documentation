---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 10866226b1025c8c4c0a28499d46de38
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField`-komponentti tarjoaa konfiguroitavan tekstinsyötteen, joka pakottaa muotoilusäännöt ja validaation. Se on hyvin soveltuva sovelluksille, jotka vaativat strukturoitua syötettä, kuten rahoitus-, verkkokauppa- ja terveydenhuoltojärjestelmille.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedTextField` voidaan alustaa parametreilla tai ilman. Voit määrittää alkuperäisen arvon, etiketti, paikkamerkki-tekstin ja kuuntelijan, jos arvo muuttuu.

```java
MaskedTextField field = new MaskedTextField("Tilin ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: SAVE-2025")
```

## Maskeja koskevat säännöt {#mask-rules}

`MaskedTextField` muotoilee tekstinsyötteen käyttäen maskia - merkkijonoa, joka määrittää, mitkä merkit ovat sallittuja kussakin sijainnissa. Tämä varmistaa johdonmukaisen, rakenteellisen syötteen, kuten puhelinnumeroiden, postinumeroiden ja henkilöllisyysnumeroiden muodoissa.

:::tip Maskien soveltaminen ohjelmallisesti
Muotoillaksesi merkkijonoja saman maskisynnin mukaisesti kentän ulkopuolella, esimerkiksi tietojen renderöinnissä [`Table`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                     |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettava merkki                                                            |
| `a`       | Mikä tahansa aakkosnumeerinen merkki (suuret tai pienet kirjaimet)                        |
| `A`       | Mikä tahansa aakkosnumeerinen merkki; pienet kirjaimet muunnetaan suuriksi                 |
| `0`       | Mikä tahansa numero (0–9)                                                                   |
| `z`       | Mikä tahansa numero tai kirjain (suuret tai pienet kirjaimet)                             |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muunnetaan suuriksi                      |

Kaikkia muita merkkejä maskissa kohdellaan kirjaimellisesti, ja ne on kirjoitettava tarkasti.
Esimerkiksi maski kuten `XX@XX` vaatii käyttäjää syöttämään `@` keskelle.

- **Epäpätevät merkit** ohitetaan hiljaa.
- **Lyhyt syöte** täytetään tyhjillä merkeillä.
- **Pitkä syöte** katkaistaan mahtuakseen maskiin.

### Esimerkkejä {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (Kanadan postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täysi syöte sallittu
Jos maski sisältää vain `X`, kenttä käyttäytyy kuin tavanomainen [`TextField`](../textfield), sallien minkä tahansa tulostettavan syötteen.
Tämä on hyödyllistä, kun haluat varata mahdollisuuden muotoilla ilman tiukkoja merkkisääntöjä.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Vahvistuskuviot {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne vahvistuskuvioihin pakottaaksesi tarkempia syöttösääntöjä. Tämä lisää ylimääräisen tason asiakaspuolen validointia käyttämällä säännöllisiä lausekkeita.

Käytä `setPattern()`-metodia soveltaaksesi mukautettua säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkisen aakkosnumeerisen koodin
```

Tämä varmistaa, että syöte ei ainoastaan vastaa maskia, vaan noudattaa myös määriteltyä rakennetta, kuten pituutta tai sallittuja merkkejä.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liikaa joustavuutta
- Halutaan pakottaa tarkka pituus tai tietty muoto (esim. heksadesimaali, Base64, UUID)

:::tip Säännöllisen lausekkeen muoto
Kuvion on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten `RegExp`-tyypissä käytetty. Voit löytää lisää tietoa [HTML-malliattributin dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautustoiminnon, joka nollaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan.
Tämä voi olla hyödyllistä käyttäjän muutosten kumoamisessa tai oletusyhdisteeseen palaamisessa.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusavain, ellei sitä ohiteta tapahtumakuuntelijalla)

Voit asettaa palautettavan arvon käyttämällä `setRestoreValue()`. Jos palautusarvoa ei ole asetettu, kenttä palautuu alkuperäiseen arvoon sen hetkellä, kun se renderöitiin.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa [`MaskedTextField`](#basics) lisäämällä spinnerikontrollit, jotka antavat käyttäjille mahdollisuuden kiertää ennalta määriteltyjen arvojen luetteloa.
Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syötteen tulisi olla rajoitettu kiinteään käyttökelpoisten vaihtoehtojen joukkoon.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Avainominaisuudet {#key-features}

- **Vaihtoehtoluettelo**
  Täytä spinnereitä käyttökelpoisilla merkkijonoarvoilla käyttämällä `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen kierto**
  Käytä `spinUp()` ja `spinDown()` siirtääksesi vaihtoehtoja:

  ```java
  spinner.spinUp();   // Valitsee seuraavan vaihtoehdon
  spinner.spinDown(); // Valitsee edellisen vaihtoehdon
  ```

- **Indeksinhallinta**
  Aseta tai hae nykyinen valintaindeksi:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskiyhteensopivuus**
  Perii täysin kaikki muotoilu-, maskisäännöt ja kuviovahvistuksen `MaskedTextField`-komponentista.

## Tyylit {#styling}

<TableBuilder name="MaskedTextField" />
