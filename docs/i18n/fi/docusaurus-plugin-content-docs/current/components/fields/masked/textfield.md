---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 701dcaccf198fbf507d1cd19c4bd995d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField`-komponentin tavoitteena on tarjota mukautettava ja helposti validoitava tekstinsyöttö. Se soveltuu hyvin sovelluksiin, jotka vaativat muotoiltua syöttöä, kuten rahoitus-, verkkokauppa- ja terveydenhuollon sovelluksiin.

## Perusasiat {#basics}

`MaskedTextField` voidaan instansioida parametreilla tai ilman. Voit määrittää alkusarjan, etiketin, paikkamerkkitekstin ja kuuntelijan, jos arvo muuttuu.

```java
MaskedTextField field = new MaskedTextField("Tilinumero");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: SAVE-2025")
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstisyöttöä maskin avulla - merkkijono, joka määrittää, mitkä merkit ovat sallittuja kussakin asemassa. Tämä varmistaa johdonmukaisen ja strukturoituneen syötön, kuten puhelinnumeroiden, postinumeroiden ja henkilötunnusten muotojen osalta.

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettava merkki                                                         |
| `a`       | Mikä tahansa aakkosnumeerinen merkki (iso tai pieni)                                           |
| `A`       | Mikä tahansa aakkosnumeerinen merkki; pienet kirjaimet muutetaan isoiksi                      |
| `0`       | Mikä tahansa numero (0–9)                                                                     |
| `z`       | Mikä tahansa numero tai kirjain (iso tai pieni)                                               |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muutetaan isoiksi                           |

Kaikki muut merkit maskissa käsitellään kirjaimellisesti ja ne on kirjoitettava tarkasti. 
Esimerkiksi maski kuten `XX@XX` vaatii käyttäjää syöttämään `@` keskelle.

- **Virheelliset merkit** jätetään huomiotta.
- **Lyhyt syöttö** täydennetään välilyönneillä.
- **Pitkä syöttö** katkaistaan sopimaan maskiin.

### Esimerkkejä {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (kanadalainen postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täydellinen syöttö sallittu
Jos maski sisältää vain `X`, kenttä käyttäytyy kuin tavallinen [`TextField`](../text-field.md), mikä sallii minkä tahansa tulostettavan syötteen.
Tämä on hyödyllistä, kun haluat säilyttää mahdollisuuden muotoilla ilman tiukkoja merkkisääntöjä.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validointimallit {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne validointimalleihin pakottaaksesi tarkempia syöttösääntöjä. Tämä lisää ylimääräisen kerroksen asiakaspuolen validointia käyttäen säännöllisiä lausekkeita.

Käytä `setPattern()`-metodia soveltaaksesi mukautettua säännöllistä lausetta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkisen aakkos-numeerisen koodin
```

Tämä varmistaa, että syöte ei ainoastaan vastaa maskia, vaan myös noudattaa määriteltyä rakennetta, kuten pituutta tai sallittuja merkkejä.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liikaa joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. heksadesimaali, Base64, UUID)

:::tip Säännöllisen lausekkeen muoto
Mallin on oltava kelvollinen [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten `RegExp`-tyypissä käytetään. Lisätietoja löytyy [HTML:n malli-attribuutin asiakirjasta](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palauttamisominaisuuden, joka palauttaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan. 
Tämä voi olla hyödyllistä käyttäjän muutosten peruuttamiseen tai oletusyhdisteeseen palaamiseen.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** kutsumalla `restoreValue()`
- **Näppäimistön avulla**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain, ellei se kumota tapahtumakuuntelijalla)

Voit asettaa palautettavan arvon käyttämällä `setRestoreValue()`. Jos palautusarvoa ei ole asetettu, kenttä palautuu alkuperäiseen arvoonsa sen hetkellä, kun se renderöitiin.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa [`MaskedTextField`](#basics) lisäämällä pyörintäohjaimia, jotka mahdollistavat käyttäjien kiertää esiasetettujen arvojen listan. 
Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syötteen tulisi olla rajoitettu kiinteään joukkoon kelvollisia vaihtoehtoja.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Avainominaisuudet {#key-features}

- **Avoin vaihtoehtoluettelo**  
  Täytä pyörintäohjaimet kelvollisten merkkijonojen arvojen listalla käyttämällä `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen pyöritys**  
  Käytä `spinUp()` ja `spinDown()` siirtyäksesi vaihtoehtojen läpi:

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

- **Maskiyhteensopivuus**  
  Perii täysin kaikki muotoilut, maskisäännöt ja mallivalidoinnit `MaskedTextField`-komponentilta.

## Tyylitys {#styling}

<TableBuilder name="MaskedTextField" />
