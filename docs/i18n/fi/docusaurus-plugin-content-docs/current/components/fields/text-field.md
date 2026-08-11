---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 18d2a614ed2e9c53513a92017b3622e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentti mahdollistaa käyttäjien syöttää ja muokata tekstiä yhdellä rivillä. Voit määrittää kentän näyttämään tietyn virtuaalinäppäimistön, kuten numeronäppäimistön, sähköpostisyötteen, puhelinnumerosyötteen tai URL-syötteen. Komponentti tarjoaa myös sisäänrakennetun validoinnin hylkäämään arvot, jotka eivät vastaa määritettyä tyyppiä.

<!-- INTRO_END -->

## Käyttötapaukset {#usages}

<ParentLink parent="Field" />

`TextField` soveltuu laajaan valikoimaan tilanteita, joissa tarvitaan tekstin syöttämistä tai muokkaamista. Tässä on joitakin esimerkkejä tilanteista, jolloin `TextField`-komponenttia kannattaa käyttää:

1. **Lomakesyötteet**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjän syötteen, kuten nimien, osoitteiden tai kommenttien, tallentamiseen. `TextField` on paras käyttää sovelluksessa, kun syöte on yleensä lyhyt ja mahtuu yhdelle riville.

2. **Hakuominaisuus**: `TextField`-komponenttia voidaan käyttää hakulaatikon tarjoamiseen, jolloin käyttäjät voivat syöttää hakukyselyitä.

3. **Tekstin muokkaus**: `TextField` on ihanteellinen sovelluksille, jotka vaativat tekstin muokkaamista tai sisällön luomista, kuten asiakirjojen muokkaajia, chat-sovelluksia tai muistiinpanosovelluksia.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin käyttämällä `setType()`-metodia. Vastaavasti voit palauttaa tyypin käyttämällä `getType()`-metodia, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi, joka automaattisesti poistaa rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin mukaisen sähköpostikentän syntaksin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoitusprosessia sisällyttämällä yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi vahvistaa sähköpostiosoitteen muotoa, se ei takaa, että sähköpostiosoite on olemassa.
  :::

- `Type.TEL`: Tämä tyyppi on tarkoitettu puhelinnumeron syöttämiseen. Kenttä näyttää puhelinnäppäimistön joillakin laitteilla, joissa on dynaamiset näppäimistöt.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi, syöttääkö käyttäjä URL-osoitett, joka sisältää skeeman ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoitusprosessia sisällyttämällä yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakulausekkeiden syöttämiseksi. Rivinvaihdot poistetaan automaattisesti syötearvosta.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo edustaa nykyistä käyttäjän syötettä merkkijonona. webforJ:ssä tämä voidaan saavuttaa käyttämällä `getValue()`-metodia tai päivittämällä ohjelmallisesti `setValue(String)`-metodilla.

```java
//Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

//Hae nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentällä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">`-elementti tuo esiin arvonsa JavaScriptin kautta.

:::tip Yhdistä arvojen käsittely validointiin
Sovella rajoituksia, kuten [kaavaa](#pattern-matching), [minimipituutta](#setminlength) tai [maksimipituutta](#setmaxlength) määrittääksesi, milloin arvoa pidetään kelvollisena.
:::

## Paikkamerkkiteksti {#placeholder-text}

Voit asettaa paikkamerkkitekstiä `TextField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkamerkkiteksti näkyy, kun kenttä on tyhj, auttaen käyttäjää syöttämään asianmukaista syötettä `TextField`-komponenttiin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa saadakseen käyttäjän valitun tekstin sekä tietoa käyttäjän valinnasta. Voit hakea valitun tekstin `TextField`-komponentista käyttämällä `getSelectedText()`-metodia. Tätä käyttäytymistä käytetään yleisesti yhdessä tapahtuman kanssa.

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samoin on mahdollista hakea nykyinen valintaraja `TextField`-komponentista käyttämällä `getSelectionRange()`-metodia. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin aloitus- ja lopetusindeksejä.

:::tip `getSelectedText()` vs tapahtumakuormitus
Vaikka voit kutsua `getSelectedText()`-metodia manuaalisesti tapahtumankäsittelijässä, on tehokkaampaa käyttää tapahtuman kuormassa annettuja valintatietoja—kuten `SelectionChangeEvent`—lisäämällä ylimääräisiä hakuehtoja.
:::

## Kaavan sovittaminen {#pattern-matching}

Voit käyttää `setPattern()`-metodia määrittääksesi validointisäännön `TextField`-komponentille käyttäen säännöllistä lauseketta. Kaavan asettaminen lisää rajoituksen validointia, joka vaatii syötearvon vastaavan määritettyä kaavaa.

Kaavan on oltava kelvollinen [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selain tulkitsee sen. `u` (Unicode) lippua käytetään sisäisesti varmistaakseen Unicode-koodipisteiden tarkkuuden. Älä kääri kaavaa eteenpäin vinoviivoihin (`/`), sillä niitä ei tarvita, ja niitä käsitellään kirjaimellisina merkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos kaavaa ei ole annettu tai syntaksi on virheellinen, validointisääntöä ei noudateta.

:::tip Kontekstuaalinen apu
Kun käytät monimutkaisia kaavoja `TextField`-komponentille, harkitse yhdistämistä `setLabel()`, `setHelperText()` ja `setTooltipText()` -metodien kanssa
antamaan lisävihjeitä ja ohjeita.
:::

## Minimipituus ja maksimi pituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitusvalidointia käyttäjän syöttämän merkkimäärän perusteella. Tätä voidaan hallita käyttämällä `setMinLength()` ja `setMaxLength()` -metodeja. Käytä molempia metodeja määrittämään selkeä raja hyväksyttävien syötteiden pituuksille.

:::info Pituusvaatimukset
Oletuksena kenttä näyttää viestin, kun syötearvo on voimassa, ilmoittamalla käyttäjälle, onko heidän tarpeen lyhentää tai pidentää syötettään. Tämä viesti voidaan korvata `setInvalidMessage()`-metodilla.
:::

### `setMinLength()` {#setminlength}

Tämä metodi asettaa **minimimäärän UTF-16-koodiyksiköitä**, jotka on syötettävä, jotta kenttä voidaan katsoa kelvolliseksi. Arvon on oltava kokonaisluku, eikä sen pitäisi ylittää määritettyä maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syötteessä on vähemmän merkkejä kuin minimivaatimus, syöte epäonnistuu rajoitusvalidoinnissa. Tämä sääntö koskee vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi asettaa **maksimimäärän UTF-16-koodiyksiköitä**, jotka on sallittu tekstikentässä. Arvon on oltava `0` tai suurempi. Jos ei aseteta tai asetetaan virheelliseen arvoon, maksimi ei ole voimassa.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä epäonnistuu rajoitusvalidoinnissa, jos syötteen pituus ylittää minimipituuden. Kuten `setMinLength()`-metodilla, tämä validointi laukaistaan vain, kun käyttäjän muuttama arvo on muuttunut.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa esitetään joitakin ehdotettuja parhaita käytäntöjä `TextField`-komponentin hyödyntämiseksi.

- **Tarjoa selkeät etiketti ja ohjeet**: Merkitse `TextField`-komponentti selkeästi, jotta käyttäjät ymmärtävät, minkä tyyppistä tietoa heidän tulisi syöttää. Tarjoa lisäohjeita tai paikkamerkkivälejä auttaaksesi käyttäjiä ja asettaaksesi odotuksia vaaditulle syötteelle.

- **Oikeinkirjoitus ja automaattitäyttö**: Kun mahdollista, harkitse oikaisun käyttämistä `setSpellCheck()`-metodin kanssa ja/tai automaattitäytön käyttöä `TextField`-komponentissa. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoa nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aiempien syötteiden tai ennalta määrättyjen vaihtoehtojen perusteella.

- **Esteettömyys**: Hyödynnä `TextField`-komponenttia esteettömyys huomioiden, noudattaen esteettömyyden standardeja, kuten asianmukaista merkintää, näppäimistön navigointitukea ja yhteensopivuutta apuvälineiden kanssa. Varmista, että käyttäjät, joilla on esteitä, voivat vuorovaikuttaa `TextField`-komponentin kanssa tehokkaasti.
