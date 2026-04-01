---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 138207c2dd99dac9837172067950ab2f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentti sallii käyttäjien syöttää ja muokata tekstiä yhdellä rivillä. Voit konfiguroida kentän näyttämään tietyn virtuaalinäppäimistön, kuten numeronäppäimet, sähköpostisyötteen, puhelinnumerosyötteen tai URL-syötteen. Komponentti tarjoaa myös sisäänrakennetun validoinnin, joka hylkää arvot, jotka eivät vastaa määriteltyä tyyppiä.

<!-- INTRO_END -->

## Käytöt {#usages}

<ParentLink parent="Field" />

`TextField` on sopiva laajan valikoiman skenaarioihin, joissa tarvitaan tekstin syöttämistä tai muokkaamista. Tässä on joitakin esimerkkejä, milloin käyttää `TextField`-komponenttia:

1. **Lomaketulot**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjän syötteen tallentamiseen, kuten nimiin, osoitteisiin tai kommentteihin. On parasta käyttää `TextField`-komponenttia sovelluksessa, kun syöte on yleensä tarpeeksi lyhyt mahtuakseen yhdelle riville.

2. **Haku-toiminnallisuus**: `TextField`-komponenttia voidaan käyttää hakukentän tarjoamiseen, jolloin käyttäjät voivat syöttää hakukyselyitä.

3. **Tekstin muokkaus**: `TextField` on ihanteellinen sovelluksille, jotka vaativat tekstin muokkaamista tai sisällön luomista, kuten asiakirjan muokkausohjelmat, keskustelusovellukset tai muistiinpano-sovellukset.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin `setType()`-menetelmällä. Vastaavasti voit noutaa tyypin `getType()`-menetelmällä, joka palauttaa enumerointiarvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi, joka poistaa automaattisesti rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin sähköpostisyntaksin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamista sisältämällä yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi vahvistaa sähköpostiosoitteen muodon, se ei takaa, että sähköposti toimii.
  :::

- `Type.TEL`: Tämä tyyppi on tarkoitettu puhelinnumeroiden syöttämiseen. Kenttä näyttää puhelinäppäimistön joillakin laitteilla dynaamisilla näppäimistöillä.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi, että käyttäjä syöttää URL-osoitteen, joka sisältää protokollan ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamista sisältämällä yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakulausekkeiden syöttämiseksi. Rivinvaihdot poistetaan automaattisesti syötearvosta.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
height='225px'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo edustaa nykyistä käyttäjän syötettä merkkijonona. webforJ:ssa tähän pääsee käsiksi `getValue()`-menetelmällä tai päivitetään ohjelmallisesti `setValue(String)`-menetelmällä.

```java
// Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

// Hae nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-menetelmää käytetään kentällä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on yhdenmukaista sen kanssa, miten HTML `<input type="text">` -elementti paljastaa arvonsa JavaScriptin kautta.

:::tip Yhdistä arvokäsittely validointiin
Käytä rajoitteita, kuten [kuviota](#pattern-matching), [minimipituus](#setminlength) tai [maksimipituus](#setmaxlength), määritelläksesi, milloin arvoa pidetään pätevänä. 
:::

## Paikkateksti {#placeholder-text}

Voit määrittää paikkatekstin `TextField`-komponentille `setPlaceholder()`-menetelmällä. Paikkateksti näytetään, kun kenttä on tyhjö, auttaen ohjaamaan käyttäjää syöttämään asianmukaista syötettä `TextField`-komponenttiin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa noutaaksesi käyttäjän valitseman tekstin ja saadaksesi tietoa käyttäjän valinnasta. Voit noutaa valitun tekstin `TextField`-komponentista `getSelectedText()`-menetelmällä. Tätä käyttäytymistä käytetään yleensä yhdessä tapahtuman kanssa.

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samoin on mahdollista noutaa `TextField`-komponentin nykyinen valintarakenne käyttäen `getSelectionRange()`-menetelmää. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin alkua ja loppua.

:::tip `getSelectedText()` vs tapahtuman lataus
Vaikka voit kutsua `getSelectedText()`-menetelmää manuaalisesti tapahtumankäsittelijän sisällä, on tehokkaampaa käyttää tapahtuman latauksessa annettuja valintatietoja, kuten `SelectionChangeEvent`-tapahtumassa, välttääksemme lisähakuja.
:::

## Kuviojoukon tarkistus {#pattern-matching}

Voit käyttää `setPattern()`-menetelmää määrittelemään validointisääntöjä `TextField`-komponentille säännöllisen lausekkeen avulla. Kuvion määrittäminen lisää rajoitevalidoinnin, joka vaatii syötearvon vastaavan määriteltyä kuviota.

Kuvion on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selaimen tulkitsema. `u` (Unicode) -lippu lisätään sisäisesti, jotta Unicode-koodipisteiden tarkka vastaavuus varmistetaan. Älä kätke kuviota vinoviivoihin (`/`), sillä niitä ei vaadita ja ne käsitellään kirjaimellisina merkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos kuvio ei ole määritetty tai syntaksi on virheellinen, validointisääntöä ei oteta huomioon.

:::tip Kontekstuaalinen apu
Kun käytät monimutkaisempia kuvioita `TextField`-komponentille, harkitse yhdistelmän käyttämistä `setLabel()`, `setHelperText()` ja `setTooltipText()` -menetelmistä
tarjotaksesi lisävihjeitä ja ohjeita.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitevalidointia, joka perustuu käyttäjän syöttämien merkkien määrään. Tätä voidaan hallita `setMinLength()` ja `setMaxLength()` -menetelmien avulla. Käytä molempia menetelmiä määritelläksesi selkeät rajat hyväksyttävälle syötteen pituudelle.

:::info Pituusvaatimukset
Oletusarvoisesti kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, ilmoittaen käyttäjälle, onko heidän lyhennettävä tai pidentävä syötteen. Tätä viestiä voidaan ohittaa `setInvalidMessage()` -menetelmällä.
:::

### `setMinLength()` {#setminlength}

Tämä metodi asettaa **minimimäärän UTF-16-koodiyksiköitä**, jotka on syötettävä, jotta kenttä on voimassa. Arvon on oltava kokonaisluku eikä se saa ylittää määritettyä maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syöte sisältää vähemmän merkkejä kuin minimivaatimus, syöte ei täytä rajoitevalidointia. Tämä sääntö koskee vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi asettaa **maksimimäärän UTF-16-koodiyksiköitä**, joita sallitaan tekstikentässä. Arvon on oltava `0` tai suurempi. Jos ei ole asetettu tai asetettu virheelliseen arvoon, ei maksimia noudateta.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä ei täytä rajoitevalidointia, jos syötteen pituus ylittää minimipituuden. Kuten `setMinLength()`-menetelmässä, tätä validointia laukaistaan vain, kun käyttäjä muuttaa arvoa.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa esitetään joitakin ehdotettuja parhaita käytäntöjä `TextField`-komponentin käytölle.

- **Tarjoa selkeät etiketit ja ohjeet**: Merkitse `TextField` selkeästi, jotta käyttäjät tietävät, minkälaista tietoa heidän tulisi syöttää. Tarjoa lisäohjeita tai paikkatekstejä ohjataksesi käyttäjiä ja asettaaksesi odotuksia vaaditulle syötteelle.

- **Oikoluku ja automaattinen täyttö**: Kun se on mahdollista, harkitse oikoluvun käyttöä `setSpellCheck()`-menetelmällä ja/tai automaattisen täytön käyttöä `TextField`-komponentissa. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoa nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aiempien syötteiden tai ennalta määritettyjen vaihtoehtojen perusteella.

- **Esteettömyys**: Käytä `TextField`-komponenttia esteettömyys mielessä, noudattaen esteettömyysstandardeja, kuten oikeaa merkintää, näppäimistön navigointitukea ja yhteensopivuutta apuvälineiden kanssa. Varmista, että käyttäjät, joilla on esteitä, voivat vuorovaikuttaa `TextField`-komponentin kanssa tehokkaasti.
