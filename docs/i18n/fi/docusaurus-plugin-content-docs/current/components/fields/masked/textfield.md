---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: c50931f8465e3be081ecfee03a3ef559
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

`MaskedTextField`-komponentti tarjoaa konfiguroitavan tekstisyötteen, joka pakottaa muotoilu- ja validointisäännöt. Se soveltuu hyvin sovelluksiin, jotka vaativat strukturoitua syöttöä, kuten talous-, verkkokauppa- ja terveydenhuoltojärjestelmiin.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedTextField` voidaan luoda parametreillä tai ilman. Voit määrittää alkusarjan arvon, etiketin, paikkamerkkitekstin ja kuuntelijan, jos arvo muuttuu.

```java
MaskedTextField field = new MaskedTextField("Tilin ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Maski: ZZZZ-0000 - esimerkiksi: SAVE-2025")
```

## Maskisäännöt {#mask-rules}

`MaskedTextField` muotoilee tekstisyötteen maskin avulla - merkkijono, joka määrittää, mitkä merkit ovat sallittuja kussakin kohdassa. Tämä varmistaa johdonmukaisen ja rakenteellisen syötteen, kuten puhelinnumeroiden, postinumeroiden ja henkilöllisyysnumeroiden muodoissa.

:::tip Maskin soveltaminen ohjelmallisesti
Jos haluat muotoilla merkkijonoja samalla maskisyntaksilla kentän ulkopuolella, esimerkiksi kun renderöit tietoja [`Table`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Tuetut maskimerkit {#supported-mask-characters}

| Merkki    | Kuvaus                                                                                 |
|-----------|---------------------------------------------------------------------------------------|
| `X`       | Mikä tahansa tulostettava merkki                                                     |
| `a`       | Mikä tahansa aakkosellinen merkki (iso tai pieni)                                     |
| `A`       | Mikä tahansa aakkosellinen merkki; pienet kirjaimet muutetaan isoiksi                 |
| `0`       | Mikä tahansa numero (0–9)                                                             |
| `z`       | Mikä tahansa numero tai kirjain (iso tai pieni)                                        |
| `Z`       | Mikä tahansa numero tai kirjain; pienet kirjaimet muutetaan isoiksi                   |

Kaikki muut merkit maskissa käsitellään kirjaimellisesti ja ne on kirjoitettava täsmälleen. 
Esimerkiksi maski kuten `XX@XX` vaatii käyttäjältä `@`-merkin syöttämistä keskelle.

- **Väärät merkit** jätetään huomiotta ilman ilmoitusta.
- **Lyhyt syöte** täytetään väleillä.
- **Pitkä syöte** katkaistaan sopimaan maskiin.

### Esimerkkejä {#examples}

```java
field.setMask("(000) 000-0000");     // Esimerkki: (123) 456-7890
field.setMask("A00 000");            // Esimerkki: A1B 2C3 (Kanadan postinumero)
field.setMask("ZZZZ-0000");          // Esimerkki: ABCD-1234
field.setMask("0000-0000-0000-0000");// Esimerkki: 1234-5678-9012-3456
```

:::tip Täydellinen syöte sallittu
Jos maski sisältää vain `X`, kenttä käyttäytyy kuten tavallinen [`TextField`](../textfield), salliensyötteen mihin tahansa tulostettavaan syötteeseen.
Tämä on hyödyllistä, kun haluat varata kyvyn muotoilla ilman tiukkoja merkkisääntöjä.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validointikaaviot {#validation-patterns}

Vaikka maskit määrittävät syötteen rakenteen, voit yhdistää ne validointikaavioiden kanssa pakottaaksesi tarkempia syöttösääntöjä. Tämä lisää ylimääräisen kerroksen asiakaspuolen validointia käyttöön heti.

Käytä `setPattern()`-metodia soveltaaksesi mukautettua säännöllistä lauseketta:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Pakottaa 10-merkkiseen alfanumeeriseen koodiin
```

Tämä varmistaa, että syöte vastaa ei vain maskia, vaan myös noudattaa määriteltyä rakennetta, kuten pituutta tai sallittuja merkkejä.

Tämä on erityisen hyödyllistä, kun:

- Maski sallii liikaa joustavuutta
- Haluat pakottaa tarkan pituuden tai tietyn muodon (esim. heksadesimaalinen, Base64, UUID)

:::tip Säännöllinen lauseke
Kaavan on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten `RegExp`-tyypin mukaan. Löydät lisää yksityiskohtia [HTML-kaavan attribuutin asiakirjoista](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Arvon palauttaminen {#restoring-the-value}

`MaskedTextField` sisältää palautustoiminnon, joka nolla poistaa kentän arvon ennalta määritettyyn tai alkuperäiseen tilaan. 
Tämä voi olla hyödyllistä käyttäjän muutosten kumoamisessa tai palautettaessa oletusyhteyttä.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti**, kutsumalla `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautusavain ellei sitä ohiteta tapahtumakuuntelijalla)

Voit asettaa palautettavan arvon `setRestoreValue()`-menetelmällä. Jos palautusarvoa ei ole asetettu, kenttä palautuu alkupisteeseen syöttön hetkellä.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

`MaskedTextFieldSpinner` laajentaa [`MaskedTextField`](#basics) lisäämällä spinnereitä, jotka antavat käyttäjille mahdollisuuden kiertää ennaltamääriteltyjen arvojen luetteloa. 
Tämä parantaa käyttäjäkokemusta tilanteissa, joissa syöte tulisi rajoittaa kiinteään joukkoon hyväksyttäviä vaihtoehtoja.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Avainominaisuudet {#key-features}

- **Vaihtoehtoluettelo**  
  Täytä spinnereen voimassa olevat merkkijonovaihtoehdot käyttämällä `setOptions()`:

  ```java
  spinner.setOptions(List.of("Vaihtoehto A", "Vaihtoehto B", "Vaihtoehto C"));
  ```

- **Ohjelmallinen kierto**  
  Käytä `spinUp()` ja `spinDown()` siirtyäksesi vaihtoehtojen läpi:

  ```java
  spinner.spinUp();   // Valitsee seuraavan vaihtoehdon
  spinner.spinDown(); // Valitsee edellisen vaihtoehdon
  ```

- **Indeksin hallinta**  
  Aseta tai hae nykyistä valinnanindeksiä seuraavasti:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskin yhteensopivuus**  
  Perii täysimääräisesti kaikki muotoilut, maskisäännöt ja kaavan validoinnin `MaskedTextField`:stä.

## Tyylittely {#styling}

<TableBuilder name="MaskedTextField" />
