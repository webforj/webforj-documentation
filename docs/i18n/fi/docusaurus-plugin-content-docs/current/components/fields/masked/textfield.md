---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: b910fd6dedb911a21f3d37b17658c2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField`-komponentti tarjoaa konfiguroitavan tekstikentän, joka pakottaa muotoilusääntöjä ja validoimista. Se soveltuu erinomaisesti sovelluksiin, jotka vaativat eksofomia syötettä, kuten rahoitus-, sähköinen kaupankäynti- ja terveydenhuoltojärjestelmiin.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedTextField`-komponentin voi luoda parametreilla tai ilman. Voit määrittää alkuarvon, tunnisteen, paikkamerkkitekstin ja kuuntelijan tilanteissa, joissa arvo muuttuu.

```java
MaskedTextField field = new MaskedTextField("Tilin ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: SAVE-2025")
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstisyötteen maskeilla - merkkijonoilla, jotka määrittävät, mitkä merkit ovat sallittuja jokaisessa kohdassa. Tämä varmistaa johdonmukaisen ja rakenteellisen syötteen esimerkiksi puhelinnumeroissa, postinumeroissa ja henkilöllisyysnumeroissa.

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                   |
|-----------|------------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettava merkki                                                         |
| `a`       | Mikä tahansa aakkosellinen merkki (iso tai pieni)                                       |
| `A`       | Mikä tahansa aakkosellinen merkki; pienet kirjaimet muutetaan isoiksi                    |
| `0`       | Mikä tahansa numero (0–9)                                                                 |
| `z`       | Mikä tahansa numero tai kirjain (iso tai pieni)                                          |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muutetaan isoiksi                     |

Kaikkia muita merkkejä maskissa käsitellään kirjaimena, ja ne on kirjoitettava tarkasti. Esimerkiksi maski, kuten `XX@XX`, vaatii käyttäjän syöttämään `@` keskelle.

- **Virheelliset merkit** jätetään huomiotta ilman ilmoitusta.
- **Lyhyt syöte** täydennetään välilyönneillä.
- **Pidempi syöte** katkaistaan sopimaan m askiin.

### Esimerkkejä {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (Kanadan postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täydellinen syöttö sallittu
Jos maski sisältää vain `X`, kenttä käyttäytyy kuin tavallinen [`TextField`](../textfield), sallien kaikki tulostettavat syötteet. Tämä on hyödyllistä, kun haluat varata mahdollisuuden muotoiluun ilman tiukkoja merkkisääntöjä.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validointimallit {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne validointimalleihin pakottaaksesi tarkempia syöttösääntöjä. Tämä lisää ylimääräisen kerroksen asiakaspuolen validoimista säännöllisten lausekkeiden avulla.

Käytä `setPattern()`-metodia soveltaaksesi mukautettua säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkisen alfanumeerisen koodin
```

Tämä varmistaa, että syöte ei ainoastaan vastaa maskia, vaan myös täyttää määritellyn rakenteen, kuten pituuden tai sallitut merkit.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liikaa joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. heksadesimaali, Base64, UUID)

:::tip Säännöllisen lausekkeen muoto
Malli on oltava voimassa oleva [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten `RegExp`-tyypin käyttäminen. Lisätietoja löytyy [HTML:n malli-attribuutin dokumentaatiosta](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautusominaisuuden, joka nollaa kentän arvon ennalta määriteltyyn tai alkuperäiseen tilaan. Tämä voi olla hyödyllistä käyttäjän muutosten kumoamiseksi tai oletusarvoiseen syötteeseen palauttamiseksi.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletus palautusnäppäin, ellei ohitetu kielilähettäjällä)

Voit määrittää palautettavan arvon `setRestoreValue()`-metodilla. Jos palautusarvoa ei ole asetettu, kenttä palautuu alkuperäiseen arvoon renderoinnin ajankohtana.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa [`MaskedTextField`](#basics) komponenttia lisäämällä pyöräytyskontrollit, jotka mahdollistavat käyttäjien kiertoa ennalta määrittyneiden arvojen luettelossa. Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syöttö pitäisi rajoittaa kiinteään joukkoon kelvollisia vaihtoehtoja.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Vaihtoehtoluettelon tuki**  
  Täytä pyöräytys luettelolla kelvollisista merkkijonoarvoista käyttämällä `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen pyöriminen**  
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
  Periyttää täysin kaikki muotoilut, maskisäännöt ja mallivaatimukset `MaskedTextField`:iltä.

## Tyylittely {#styling}

<TableBuilder name="MaskedTextField" />
