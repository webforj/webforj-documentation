---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0d350dce3bf30a09bc54f089107b7a25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentti sallii käyttäjien syöttää ja muokata tekstiä yhdessä rivissä. Voit konfiguroida kentän näyttämään tietyn virtuaalinäppäimistön, kuten numeronäppäimistön, sähköpostisyötteen, puhelinnumerosyötteen tai URL-syötteen. Komponentti tarjoaa myös sisäänrakennettua validointia hyljätäksesi arvot, jotka eivät vastaa määriteltyä tyyppiä.

<!-- INTRO_END -->

## Käytännöt {#usages}

<ParentLink parent="Field" />

`TextField` sopii laajaan valikoimaan tilanteita, joissa tarvitaan tekstisyöttöä tai -muokkausta. Tässä on joitakin esimerkkejä siitä, milloin käyttää `TextField`-komponenttia:

1. **Lomakesyötteet**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjän syötteen tallentamiseen, kuten nimien, osoitteiden tai kommenttien keräämiseen. On parasta käyttää `TextField`-komponenttia sovelluksessa, kun syöte on yleensä tarpeeksi lyhyt mahtuakseen yhdelle riville.

2. **Hakulogiikka**: `TextField`-komponenttia voidaan käyttää hakukentän tarjoamiseen, jolloin käyttäjät voivat syöttää hakukyselyitä.

3. **Tekstin muokkaus**: `TextField`-komponentti on ihanteellinen sovelluksille, jotka vaativat tekstin muokkausta tai sisällön luomista, kuten asiakirjan muokkaajat, keskustelusovellukset tai muistiinpanosovellukset.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin käyttämällä `setType()`-metodia. Vastaavasti voit noutaa tyypin käyttämällä `getType()`-metodia, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on oletustyyppi `TextField`-komponentille ja poistaa automaattisesti rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin sähköpostisynnin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka yksinkertaistaa kirjoitusprosessia mukaan lukien yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi varmistaa sähköpostiosoitteen muodon, se ei takaa, että sähköpostiosoite on olemassa.
  :::

- `Type.TEL`: Tätä tyyppiä käytetään puhelinnumeron syöttämiseen. Kenttä näyttää puhelin näppäimistön joillakin laitteilla dynaamisilla näppäimistöillä.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi, että käyttäjä syöttää URL-osoitteen, joka sisältää skeeman ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka yksinkertaistaa kirjoitusprosessia mukaan lukien yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd>, ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakusanojen syöttämiseen. Rivinvaihdot poistetaan automaattisesti syötearvosta.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo edustaa nykyistä käyttäjän syötettä merkkijonona. webforJ:ssä tämä voidaan saavuttaa käyttämällä `getValue()`-metodia tai päivittämällä ohjelmallisesti `setValue(String)`-metodilla.

```java
// Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

// Nouta nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentällä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">` -alkuosa esittelee arvonsa JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validointiin
Käytä rajoitteita, kuten [malli](#pattern-matching), [minimipituus](#setminlength) tai [maksimipituus](#setmaxlength), määrittämään, milloin arvoa pidetään voimassa.
:::

## Paikkamerkkiteksti {#placeholder-text}

Voit asettaa paikkamerkkitekstin `TextField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkamerkkiteksti näkyy, kun kenttä on tyhjää, auttaen käyttäjää syöttämään sopivaa tietoa `TextField`:iin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa saadaksesi käyttäjän valitseman tekstin ja saadaksesi tietoa käyttäjän valinnasta. Voit noutaa valitun tekstin `TextField`:ssä käyttämällä `getSelectedText()`-metodia. Tätä käyttäytymistä käytetään yleisesti tapahtumien yhteydessä.

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Vastaavasti on mahdollista noutaa `TextField`-komponentin nykyinen valintarajaus käyttämällä `getSelectionRange()`-metodia. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin aloitus- ja lopetusindeksejä.

:::tip Arvon muutokset ja valinta
Käytä `onValueChange()`-metodia käsitelläksesi kentän arvon muutoksia. Sen `ValueChangeEvent<String>` antaa uuden arvon `event.getValue()`-kautta, mutta se ei sisällä valintatietoja. Ottaaksesi nykyisen valinnan, kutsu `getSelectedText()` tai `getSelectionRange()` kentästä.
:::

## Mallin sovittaminen {#pattern-matching}

Voit käyttää `setPattern()`-metodia määrittääksesi validointisäännön `TextField`-komponentille käyttämällä säännöllistä lauseketta. Kaavan asettaminen lisää rajoitusvalidoinnin, joka vaatii syötearvon vastaavan määriteltyä mallia.

Malli on oltava voimassa [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selaimen tulkitsemana. `u` (Unicode) -lippu käytetään sisäisesti varmistaaksesi, että Unicode-koodipisteiden tarkka sovittaminen tapahtuu. Älä kääri mallia eteenpäin viivoihin (`/`), koska niitä ei vaadita ja niitä käsitellään kirjaimellisin merkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos mallia ei anneta tai syntaksi on virheellinen, validointisääntöä ei oteta huomioon.

:::tip Kontekstuaalinen apu
Kun käytetään monimutkaisia malleja `TextField`-komponentille, harkitse `setLabel()`, `setHelperText()` ja `setTooltipText()` -metodien yhdistämistä lisävihjeiden ja ohjeiden tarjoamiseksi.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitusvalidointia kirjoitetun merkkimäärän perusteella. Tämä voidaan hallita käyttämällä `setMinLength()` ja `setMaxLength()` -metodeja. Käytä molempia menetelmiä määrittämään selkeä siedettävien syötepituuksien raja.

:::info Pituusvaatimukset
Oletuksena kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, mikä ilmaisee käyttäjälle, onko heidän lyhennettävä tai pidentävä syötteensä. Tämä viesti voidaan ohittaa käyttämällä `setInvalidMessage()`-metodia.
:::

### `setMinLength()` {#setminlength}

Tämä metodi asettaa **vähimmäismäärän UTF-16-koodiyksiköitä**, jotka on syötettävä, jotta kenttä voidaan katsoa voimassa olevaksi. Arvon on oltava kokonaisluku, eikä se saa ylittää määritettyä maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syötteessä on vähemmän merkkejä kuin vähimmäisvaatimus, syöte ei täytä rajoitusvalidointia. Tämä sääntö voimassa, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi asettaa **enimmäismäärän UTF-16-koodiyksiköitä**, joita sallitaan tekstikentässä. Arvon on oltava `0` tai suurempi. Jos ei aseteta tai jos se asetetaan virheelliseen arvoon, enimmäistä ei valvota.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä ei läpäise rajoitusvalidointia, jos syötteen pituus ylittää minimipituuden. Kuten `setMinLength()`-metodin kanssa, tämä validointi aktivoituu vain, kun käyttäjä muuttaa arvoa.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa kuvataan joitakin suosittuja käytäntöjä `TextField`-komponentin käytölle.

- **Tarjoa selkeitä etikettejä ja ohjeita**: Merkitse `TextField` selkeästi osoittamaan, millaista tietoa käyttäjien tulisi syöttää. Tarjoa lisäohjeita tai paikkamerkkiarvoja, jotta käyttäjät voivat ymmärtää tarvittavan syötteen odotukset.

- **Oikeinkirjoituksen tarkistus ja automaattinen täyttö**: Kun se on sovellettavissa, harkitse oikeinkirjoitustarkistuksen hyödyntämistä `setSpellCheck()`-metodilla ja/tai automaattisen täytön käyttöä `TextField`-komponentissa. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoja nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aikaisemmista syötteistä tai ennalta määritellyistä vaihtoehdoista.

- **Saavutettavuus**: Tarjoa selkeitä etikettejä, näppäimistön navigaatiota ja tukea apuvälineille, jotta käyttäjät, joilla on vammoja, voivat käyttää kenttää.
