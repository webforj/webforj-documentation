---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0e0bfbd737ce384055397a7ff18b6579
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentti mahdollistaa käyttäjien syöttää ja muokata tekstiä yhdellä rivillä. Voit konfiguroida kentän näyttämään tietyn virtuaalipohja, kuten numero- tai sähköpostipohjan, puhelinpohjan tai URL-pohjan. Komponentti tarjoaa myös sisäänrakennetun validoinnin, joka hylkää arvot, jotka eivät vastaa määriteltyä tyyppiä.

<!-- INTRO_END -->

## Käyttötavat {#usages}

<ParentLink parent="Field" />

`TextField` sopii laajaan valikoimaan tilanteita, joissa tarvitaan tekstin syöttämistä tai muokkaamista. Tässä on joitakin esimerkkejä, milloin käyttää `TextField`-komponenttia:

1. **Lomakekentät**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjän syötteen, kuten nimien, osoitteiden tai kommenttien, keräämiseen. On parasta käyttää `TextField`-komponenttia sovelluksessa, kun syöte on yleensä tarpeeksi lyhyt mahtuakseen yhdelle riville.

2. **Hakutoiminnallisuus**: `TextField` voidaan käyttää hakukentän tarjoamiseen, jolloin käyttäjät voivat syöttää hakukyselyjä.

3. **Tekstin muokkaus**: `TextField` on ihanteellinen sovelluksille, jotka vaativat tekstin muokkausta tai sisällön luomista, kuten asiakirjojen muokkausohjelmille, chat-sovelluksille tai muistiinpanojen ottamiseen.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin käyttämällä `setType()`-metodia. Vastaavasti voit noutaa tyypin `getType()`-metodin avulla, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi, joka automaattisesti poistaa rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin sähköposti-syntaksin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamista tarjoamalla yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi varmistaa sähköpostiosoitteen muodon, se ei takaa, että sähköposti on olemassa.
  :::

- `Type.TEL`: Tämä tyyppi on tarkoitettu puhelinnumeron syöttämiseen. Kenttä näyttää joissain laitteissa puhelin näppäimistön dynaamisten näppäimistöjen avulla.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi, onko käyttäjä syöttänyt URL-osoitteen, joka sisältää skeeman ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamista tarjoamalla yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakumerkkijonojen syöttämiseen. Rivinvaihdot poistetaan automaattisesti syötearvosta.

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

//Nouta nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentässä, jossa ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">` -elementti esittelee arvonsa JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validointiin
Käytä rajoitteita, kuten [pattern](#pattern-matching), [minimipituus](#setminlength) tai [maksimipituus](#setmaxlength), määritelläksesi, milloin arvoa pidetään voimassa.
:::

## Paikkateksti {#placeholder-text}

Voit asettaa paikkatekstin `TextField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkateksti näkyy, kun kenttä on tyhjää, auttaen käyttäjää syöttämään asianmukaista tietoa `TextField`-komponenttiin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa noutaaksesi käyttäjän valittua tekstiä ja saadaksesi tietoa käyttäjän valinnasta. Voit noutaa valitun tekstin `TextField`-komponentista käyttämällä `getSelectedText()`-metodia. Tämä käyttäytyminen yhdistyy yleensä tapahtumaan.

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samoin on mahdollista noutaa `TextField`-komponentin nykyinen valintarange käyttämällä `getSelectionRange()`-metodia. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin aloitus- ja lopetusindeksejä.

:::tip Käyttämällä `getSelectedText()` vs tapahtuman kuormitus
Vaikka voit kutsua `getSelectedText()` manuaalisesti tapahtumakäsittelijän sisällä, on tehokkaampaa käyttää valintatietoja, jotka sisältyvät tapahtuman kuormitukseen—kuten `SelectionChangeEvent`—väljääksesi lisähaun välttämiseksi.
:::

## Mallin vastaavuus {#pattern-matching}

Voit käyttää `setPattern()`-metodia määrittääksesi validointisäännön `TextField`-komponentille käyttämällä säännöllistä lauseketta. Mallin asettaminen lisää rajoitusvalidoinnin, joka vaatii syötearvon vastaavan määriteltyä mallia.

Mallin on oltava voimassa [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selain tulkitsee. `u` (Unicode) -lippu käytetään sisäisesti varmistaakseen Unicode-koodipisteiden tarkan vastaavuuden. Älä pakkaa mallia eteenpäin vinoviivoihin (`/`), koska niitä ei tarvita ja niitä käsitellään kirjaimellisina merkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos mallia ei ole annettu tai syntaksi on virheellinen, validointisääntöä ei oteta huomioon.

:::tip Kontekstiavustus
Kun käytetään monimutkaisia malleja `TextField`:ssä, harkitse yhdistämistä `setLabel()`, `setHelperText()` ja `setTooltipText()` -metodien
kanssa tarjotaksesi lisävihjeitä ja ohjeita.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitusvalidointia käyttäjän syöttämien merkkien määrän perusteella. Tätä voidaan hallita `setMinLength()` ja `setMaxLength()` -metodien avulla. Käytä molempia menetelmiä selkeiden hyväksyttävien syötepituuksien määrittämiseen.

:::info Pituusvaatimukset
Oletusarvoisesti kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, ilmoittaen käyttäjälle, tarvitseeko heidän lyhentää tai pidentää syötettään. Tämä viesti voidaan ohittaa `setInvalidMessage()` -metodilla.
:::

### `setMinLength()` {#setminlength}

Tämä metodi asettaa **minimimäärä UTF-16 koodiyksiköitä**, jotka on syötettävä, jotta kenttä voidaan katsoa voimassa olevaksi. Arvon on oltava kokonaisluku ja sen ei pitäisi ylittää asetettua maksimiarvoa.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syötteessä on vähemmän merkkejä kuin vähimmäisvaatimus, syöte epäonnistuu rajoitusvalidoinnissa. Tämä sääntö on voimassa vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi asettaa **enimmäismäärä UTF-16 koodiyksiköitä** sallittuja tekstikentässä. Arvon on oltava `0` tai suurempi. Jos ei ole asetettu tai asetetaan virheelliseksi arvoksi, ei enimmäisrajaa vahvisteta.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää enemmän kuin 20 merkkiä
```

Kenttä epäonnistuu rajoitusvalidoinnissa, jos syötteen pituus ylittää minimipituuden. Kuten `setMinLength()`-metodille, tämä validointi laukaistaan vain, kun käyttäjä muuttaa arvoa.

## Parhaat käytännöt {#best-practices}

Seuraava osio esittelee joitakin ehdotettuja parhaita käytäntöjä `TextField`-komponentin käytössä.

- **Tarjoa selkeät etiketit ja ohjeet**: Kyseisen `TextField`-komponentin merkitseminen selkeästi indikoi sellaista tietoa, jota käyttäjien tulisi syöttää. Tarjoa lisäohjeita tai paikkatekstejä ohjataksesi käyttäjiä ja asettaaksesi odotukset vaaditulle syötteelle.

- **Oikeinkirjoituksen tarkistus ja automaattinen täyttö**: Kun se on sovellettavissa, harkitse oikeinkirjoituksen tarkistamisen käyttämistä `setSpellCheck()`-metodin avulla ja/tai automaattisen täytön käyttöä `TextField`-komponentissa. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoja nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aiempien syötteiden tai ennalta määriteltyjen vaihtoehtojen perustella.

- **Esteettömyys**: Käytä `TextField`-komponenttia esteettömyys mielessä pitäen, noudattaen esteettömyysstandardeja, kuten oikeaa merkitsemistä, näppäimistön navigointituen ja yhteensopivuutta apuvälineiden kanssa. Varmista, että esteetön käyttäjä voi vuorovaikuttaa `TextField`-komponentin kanssa tehokkaasti.
