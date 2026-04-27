---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 51fe8b136580a1fca9e5a918389f33bf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentti mahdollistaa käyttäjien syöttää ja muokata tekstiä yhdellä rivillä. Voit määrittää kentän näyttämään tietyn virtuaalisen näppäimistön, kuten numeronäppäimistön, sähköpostin syötteen, puhelinnumeron syötteen tai URL-osoitteen syötteen. Komponentti tarjoaa myös sisäänrakennetun validoinnin hylätä arvoja, jotka eivät vastaa määritettyä tyyppiä.

<!-- INTRO_END -->

## Käyttötavat {#usages}

<ParentLink parent="Field" />

`TextField` sopii laajaan valikoimaan skenaarioita, joissa tarvitaan tekstin syöttämistä tai muokkaamista. Tässä on joitakin esimerkkejä siitä, milloin `TextField`-komponenttia kannattaa käyttää:

1. **Lomakekentät**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjätietojen, kuten nimien, osoitteiden tai kommenttien, keräämiseksi. `TextField`-komponenttia on parasta käyttää sovelluksessa, kun syöttö on yleensä sen verran lyhyt, että se mahtuu yhteen riviin.

2. **Hakuominaisuus**: `TextField`-komponenttia voidaan käyttää hakukentän tarjoamiseen, jolloin käyttäjät voivat syöttää hakukyselyitä.

3. **Tekstin muokkaus**: `TextField`-komponentti on ihanteellinen sovelluksille, jotka vaativat tekstin muokkaamista tai sisällön luomista, kuten asiakirjamuokkaajat, chat-sovellukset tai muistiinpanosovellukset.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin käyttämällä `setType()`-metodia. Samoin voit palauttaa tyypin `getType()`-metodilla, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi, joka poistaa automaattisesti rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin sähköpostisyntaxin mukaisesti. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamisprosessia lisäämällä yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi vahvistaa sähköpostiosoitteen muodon, se ei takaa, että sähköpostiosoite on olemassa.
  :::

- `Type.TEL`: Tämä tyyppi on tarkoitettu puhelinnumeron syöttämiseen. Kenttä näyttää joissakin laitteissa puhelin näppäimistön dynaamiset näppäimistöt.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi sen, että käyttäjä syöttää URL-osoitteen, joka sisältää skeeman ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamisprosessia lisäämällä yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakujonojen syöttämiseen. Rivinvaihdot poistuvat automaattisesti syötearvosta.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
height='250px'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo edustaa nykyistä käyttäjän syötettä merkkijonona. webforJ:ssä tähän voidaan päästä käsiksi `getValue()`-metodin avulla tai päivittää ohjelmallisesti `setValue(String)`-metodilla.

```java
// Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

// Hae nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentällä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">` -elementti paljastaa arvonsa JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validointiin
Sovella rajoitteita, kuten [kaavaa](#pattern-matching), [minimiä pituutta](#setminlength) tai [maksimipituutta](#setmaxlength), määrittääksesi, milloin arvoa pidetään voimassa.
:::

## Paikkatieto {#placeholder-text}

Voit asettaa `TextField`-komponentille paikkatietoa käyttämällä `setPlaceholder()`-metodia. Paikkatieto näytetään, kun kenttä on tyhjennetty, auttaen käyttäjää syöttämään sopivaa tietoa `TextField`-komponenttiin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa saadakseen käyttäjän valitun tekstin ja saadakseen tietoa käyttäjän valinnasta. Voit saada valitun tekstin `TextField`-komponentista käyttämällä `getSelectedText()`-metodia. Tätä käyttäytymistä käytetään yleensä yhdessä tapahtuman kanssa.

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samoin on mahdollista saada `TextField`-komponentin nykyinen valintarajaus `getSelectionRange()`-metodin avulla. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin aloitus- ja lopetusindeksejä.

:::tip `getSelectedText()` vs tapahtuman kuormitus
Vaikka voit kutsua `getSelectedText()`-metodia manuaalisesti tapahtumankäsittelijän sisällä, on tehokkaampaa käyttää tapahtuman kuormituksessa annettuja valintatietoja, kuten `SelectionChangeEvent`-tapahtumassa, välttääksesi lisähaun.
:::

## Kaavion vastaavuus {#pattern-matching}

Voit käyttää `setPattern()`-metodia määrittääksesi validointisäännön `TextField`-komponentille käyttämällä säännöllistä lausetta. Kaavan asettaminen lisää rajoitevalidoinnin, joka edellyttää syötearvon vastaavan erityistä kaavaa.

Kaavan on oltava voimassa oleva [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selain tulkitsee sen. `u` (Unicode) -lippu käytetään sisäisesti varmistamaan Unicode-koodipisteiden tarkka vastaavuus. Älä kääri kaavaa eteenpäin vinoviivoihin (`/`), koska ne eivät ole tarpeellisia ja käsitellään kirjaimellisina merkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos kaavaa ei anneta tai syntaksi on virheellinen, validointisääntöä ei oteta huomioon.

:::tip Kontekstuaalinen apu
Käytettäessä monimutkaisempia kaavoja `TextField`-komponentissa, harkitse `setLabel()`, `setHelperText()` ja `setTooltipText()` -metodien yhdistämistä lisävihjeiden ja ohjeiden antamiseksi.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitevalidointia käyttäjän syöttämien merkkien määrän perusteella. Tämä voidaan hallita `setMinLength()` ja `setMaxLength()` -metodien avulla. Käytä molempia metodeja määrittääksesi selkeät rajat hyväksyttävien syötteiden pituuksille.

:::info Pituusvaatimukset
Oletuksena kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, ilmoittaen käyttäjälle, että heidän on lyhennettävä tai pidennettävä syötteensä. Tämä viesti voidaan ylittää `setInvalidMessage()`-metodilla.
:::

### `setMinLength()` {#setminlength}

Tämä metodi asettaa **vähimmäismäärän UTF-16-koodiyksiköitä**, jotka on syötettävä, jotta kenttä voidaan katsoa voimassa olevaksi. Arvon on oltava kokonaisluku, eikä se saa ylittää määritettyä maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syöte sisältää vähemmän merkkejä kuin vähimmäisvaatimus, syöte epäonnistuu rajoitevalidoinnissa. Tämä sääntö koskee vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi asettaa **enimmäismäärän UTF-16-koodiyksiköitä**, jotka on sallittu tekstikentässä. Arvon on oltava `0` tai suurempi. Jos sitä ei aseteta tai määritetään virheellistä arvoa, maksimia ei pakoteta.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä epäonnistuu rajoitevalidoinnissa, jos syötteen pituus ylittää vähimmäispituuden. Kuten `setMinLength()`-metodin kanssa, tämä validointi aktivoituu vain, kun käyttäjä muuttaa arvoa.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa luetellaan joitakin ehdotettuja parhaita käytäntöjä `TextField`-komponentin käytölle.

- **Tarjoa selvä etiketointi ja ohjeet**: Merkitse `TextField`-komponentti selkeästi ilmoittaaksesi, minkä tyyppistä tietoa käyttäjien tulisi syöttää. Tarjoa lisäohjeita tai paikkatietoja ohjataksesi käyttäjiä ja asettaaksesi odotuksia vaaditulle syötteelle.

- **Oikoluku ja automaattinen täydentäminen**: Kun se on sovellettavissa, harkitse oikoluvun käyttämistä `setSpellCheck()`-metodilla ja/tai automaattisen täydentämisen käyttöä `TextField`-komponentissa. Nämä ominaisuudet auttavat käyttäjiä syöttämään tietoja nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aikaisempien syöttöjen tai ennaltamääriteltyjen vaihtoehtojen perusteella.

- **Saavutettavuus**: Käytä `TextField`-komponenttia saavutettavuus mielessä pitäen, noudattaen saavutettavuusstandardeja, kuten asianmukaista etiketöintiä, näppäimistön navigointituen tarjoamista ja yhteensopivuutta apuvälineiden kanssa. Varmista, että käyttäjät, joilla on vammoja, voivat vuorovaikuttaa `TextField`-komponentin kanssa tehokkaasti.
