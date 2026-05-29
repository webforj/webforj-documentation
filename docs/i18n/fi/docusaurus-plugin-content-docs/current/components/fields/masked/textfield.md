---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 8ef566720a30ba07ae47b5a957804c52
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField` -komponentti tarjoaa konfiguroitavan tekstinsyötteen, joka pakottaa muotoilun säännöt ja validoinnin. Se on erityisen sopiva sovelluksille, jotka vaativat strukturoitua syötettä, kuten rahoitus-, verkkokauppa- ja terveydenhuoltojärjestelmille.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedTextField` voidaan luoda parametreilla tai ilman. Voit määrittää alkuarvon, etikettin, paikkamerkkitekstin ja kuuntelijan, jos arvo muuttuu.

```java
MaskedTextField field = new MaskedTextField("Tili-ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: SAVE-2025")
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstisyötteen käyttäen maskia - merkkijonoa, joka määrittää, mitä merkkejä sallitaan kullakin paikalla. Tämä varmistaa johdonmukaisen, rakenteellisen syötteen esimerkiksi puhelinnumeroille, postinumeroille ja henkilöllisyysnumeroille.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla merkkijonoja samalla maskisyntaksilla kentän ulkopuolella, esimerkiksi kun renderöit tietoja [`Taulukossa`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -hyötyluokkaa.
:::

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki   | Kuvaus                                                                                     |
|----------|---------------------------------------------------------------------------------------------|
| `X`      | Mikä tahansa tulostettava merkki                                                           |
| `a`      | Mikä tahansa aakkosnumeerinen merkki (isot tai pienet)                                     |
| `A`      | Mikä tahansa aakkosnumeerinen merkki; pienet kirjaimet muutetaan isoiksi                   |
| `0`      | Mikä tahansa numero (0–9)                                                                   |
| `z`      | Mikä tahansa numero tai kirjain (isot tai pienet)                                          |
| `Z`      | Mikä tahansa numero tai kirjain; pienet kirjaimet muutetaan isoiksi                        |

Kaikkia muita merkkejä maskissa käsitellään kirjaimellisesti ja ne on kirjoitettava tarkasti. 
Esimerkiksi maski kuten `XX@XX` vaatii käyttäjää syöttämään `@` keskelle.

- **Virheellisiä merkkejä** ei huomata.
- **Lyhyt syöte** täydennetään välilyönneillä.
- **Pitkä syöte** katkaistaan maskin mukaiseksi.

### Esimerkit {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (Kanadan postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täysi syöte sallittu
Jos maski sisältää vain `X`, kenttä käyttäytyy kuin tavallinen [`Tekstikenttä`](../textfield), sallien minkä tahansa tulostettavan syötteen.
Tämä on hyödyllistä, kun haluat säilyttää mahdollisuuden muotoiluun soveltamatta tiukkoja merkkisääntöjä.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validointikuviot {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne validointikuviot, jotta voit pakottaa tarkempia syotesääntöjä. Tämä lisää ylimääräisen kerroksen asiakaspuolen validointia, käyttäen säännöllisiä lausekkeita.

Käytä `setPattern()` -metodia soveltamaan mukautettua säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkisen aakkosnumeerisen koodin
```

Tämä varmistaa, että syöte ei vain vastaa maskia vaan myös noudattaa määriteltyä rakennetta, kuten pituutta tai sallittuja merkkejä.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liikaa joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. hex, Base64, UUID)

:::tip Säännöllinen lausekemuoto
Mallin on oltava kelvollinen [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten `RegExp` -tyypissä käytetään. Voit löytää lisätietoja [HTML-malliattribuutin asiakirjoista](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautustoiminnon, joka nollaa kentän arvon esisäädettyyn tai alkuperäiseen tilaan. 
Tätä voidaan käyttää käyttäjän muutosten kumoamiseen tai palauttamiseen oletusyhteyksiin.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusnäppäin, ellei tapahtumakuuntelija ylikirjoita)

Voit asettaa palautettavan arvon `setRestoreValue()` -kumppanilla. Jos palautusarvoa ei ole asetettu, kenttä palaa alkuperäiseen arvoonsa, kun se renderoitiin.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa [`MaskedTextField`](#basics) lisäämällä spin-napit, jotka sallivat käyttäjien kiertää ennalta määriteltyä arvoluetteloa. 
Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syötteen tulisi rajoittua kiinteään sarjaan voimassa olevista vaihtoehdoista.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Valintaluettelo Tuki**  
  Täytä spin vaihtoehdoilla voimassa olevien merkkijonojen avulla käyttäen `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen spin**  
  Käytä `spinUp()` ja `spinDown()` siirtyäksesi vaihtoehtojen läpi:

  ```java
  spinner.spinUp();   // Valitsee seuraavan vaihtoehdon
  spinner.spinDown(); // Valitsee edellisen vaihtoehdon
  ```

- **Indeksin hallinta**  
  Aseta tai hae nykyinen valintaan liittyvä indeksi:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskin yhteensopivuus**  
  Perii täysin kaikki muotoilu-, maskisäännöt ja kuvion validoinnin `MaskedTextField`:stä.

## Tyylittely {#styling}

<TableBuilder name="MaskedTextField" />
