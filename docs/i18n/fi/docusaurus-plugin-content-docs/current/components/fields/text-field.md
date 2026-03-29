---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: e972f03e1d4deb1802bc4f56104e61b3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentti sallii käyttäjien syöttää ja muokata tekstiä yhdessä rivissä. Voit konfiguroida kentän näyttämään tietyn virtuaalinäppäimistön, kuten numeronäppäimistön, sähköpostin syötön, puhelinnumeron syötön tai URL-osoitteen syötön. Komponentti tarjoaa myös sisäänrakennetun validoinnin kyseisen tyypin standardeihin sopimattomien arvojen hylkäämiseksi.

<!-- INTRO_END -->

## Käytännöt {#usages}

<ParentLink parent="Field" />

`TextField` on soveltuva monenlaisiin tilanteisiin, joissa tarvitaan tekstisyöttöä tai muokkausta. Tässä on joitakin esimerkkejä, milloin käyttää `TextField`:iä:

1. **Lomakesyötteet**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjän syötteen tallentamiseen, kuten nimiin, osoitteisiin tai kommentteihin. On parasta käyttää `TextField`:iä sovelluksessa, kun syöte on yleensä niin lyhyt, että se mahtuu yhdelle riville.

2. **Haku-ominaisuus**: `TextField` voidaan käyttää hakukenttänä, joka sallii käyttäjien syöttää hakukyselyitä.

3. **Tekstin muokkaus**: `TextField` on ihanteellinen sovelluksille, jotka vaativat tekstin muokkaamista tai sisällön luomista, kuten asiakirjamuokkausohjelmat, chat-sovellukset tai muistiinpanosovellukset.

## Tyypit {#types}

Voit määrittää `TextField`:in tyypin käyttämällä `setType()`-metodia. Vastaavasti voit noutaa tyypin käyttämällä `getType()`-metodia, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`:in oletustyyppi ja se poistaa automaattisesti rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on sähköpostiosoitteiden syöttämistä varten. Se validoi syötteen standardin sähköpostisyntaksin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamisprosessia sisältämällä yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi vahvistaa sähköpostiosoitteen muodon, se ei takaa, että sähköposti on olemassa.
  :::

- `Type.TEL`: Tämä tyyppi on puhelinnumeron syöttämistä varten. Kenttä näyttää joissakin laitteissa puhelin näppäimistön dynaamisilla näppäimistöillä.

- `Type.URL`: Tämä tyyppi on URL-osoitteiden syöttämistä varten. Se validoi, että käyttäjä syöttää URL-osoitteen, joka sisältää kaavan ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoittamisprosessia sisältämällä yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakusanojen syöttämistä varten. Rivinvaihdot poistetaan automaattisesti syötearvosta.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Kentän arvo {#field-value}

`TextField`:in arvo edustaa nykyistä käyttäjän syötteitä merkkijonona. webforJ:ssä tämä voidaan saavuttaa käyttämällä `getValue()`-metodia tai päivittämällä ohjelmallisesti `setValue(String)`-menetelmällä.

```java
//Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

//Hae nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentällä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">` -elementti paljastaa arvonsa JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validointiin
Käytä rajoitteita, kuten [kuvio](#pattern-matching), [minimi pituus](#setminlength), tai [maksimi pituus](#setmaxlength) määrittämään, milloin arvo katsotaan voimassa olevaksi. 
:::

## Paikkamerkki teksti {#placeholder-text}

Voit asettaa paikkamerkki tekstin `TextField`:lle käyttämällä `setPlaceholder()`-metodia. Paikkamerkki teksti näkyy, kun kenttä on tyhjällekään, auttaen ohjaamaan käyttäjää syöttämään asianmukaista tietoa `TextField`:lle.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa käyttäjän valitun tekstin hakemiseksi ja saada tietoa käyttäjän valinnasta. Voit noutaa valitun tekstin `TextField`:issä käyttämällä `getSelectedText()`-metodia. Tätä käyttäytymistä käytetään yleisesti yhdessä tapahtuman kanssa. 

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae Valittu Teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samoin on mahdollista noutaa nykyinen valintaraja `TextField`:stä käyttämällä `getSelectionRange()`-metodia. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin aloitus- ja lopetusindeksejä.

:::tip Käyttäen `getSelectedText()` vs tapahtuman kuorma
Vaikka voit kutsua `getSelectedText()` manuaalisesti tapahtumakäsittelijän sisällä, on tehokkaampaa käyttää valintatietoja, jotka toimitetaan tapahtuman kuormassa—kuten `SelectionChangeEvent`—lisätäksesi välttämättömiä hakuja.
:::

## Kuvion tarkistus {#pattern-matching}

Voit käyttää `setPattern()`-metodia määrittääksesi validointisäännön `TextField`:lle käyttäen säännöllistä lauseketta. Kuvion asettaminen lisää rajoitteen, joka vaatii syötearvon vastaamaan määritettyä kuvioa.

Kuvion on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selain tulkitsee. `u` (Unicode) lippu sovelletaan sisäisesti varmistaaksesi Unicode-koodipisteiden tarkan vastineen. Älä kääri kuvioita eteenpäin vinoviivoihin (`/`), sillä niitä ei tarvita ja niitä käsitellään kirjaimellisina merkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos mitään kuviota ei ole annettu tai syntaksi on virheellinen, validointisääntöä ei oteta huomioon.

:::tip Kontekstuaalinen apu
Kun käytät monimutkaisempia kuvioita `TextField`:ssä, harkitse yhdistelmän käyttämistä `setLabel()`, `setHelperText()` ja `setTooltipText()` -menetelmistä lisävihjeiden ja ohjeiden tarjoamiseksi.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitteen validaatiota käyttäjän syöttämien merkkien lukumäärän perusteella. Tähän voidaan vaikuttaa käyttämällä `setMinLength()` ja `setMaxLength()` -menetelmiä. Käytä molempia menetelmiä määrittääksesi selkeät rajat hyväksyttävien syötteiden pituudelle.

:::info Pituusvaatimukset
Oletusarvoisesti kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, ilmoittaen käyttäjälle, tarvitseeko heidän lyhentää tai pidentää syötettään. Tämä viesti voidaan ylittää `setInvalidMessage()`-menetelmällä.
:::

### `setMinLength()` {#setminlength}

Tämä metodi asettaa **vähimmäismäärän UTF-16-koodiyksiköitä**, jotka on syötettävä, jotta kenttä voidaan katsoa voimassa olevaksi. Arvon on oltava kokonaisluku, eikä se saa ylittää määritettyä maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syöte sisältää vähemmän merkkejä kuin minimivaatimus, syöte epäonnistuu rajoitteen validoinnissa. Tämä sääntö soveltuu vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi asettaa **enimmäismäärän UTF-16-koodiyksiköitä**, jotka ovat sallittuja tekstikentässä. Arvon on oltava `0` tai suurempi. Jos sitä ei aseteta tai se asetetaan virheelliseksi arvoksi, maksimaalia ei valvota.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä epäonnistuu rajoitteen validoinnissa, jos syötteen pituus ylittää minimipituuden. Kuten `setMinLength()`:n kanssa, tämä validointi aktivoituu vain, kun arvoa muutetaan käyttäjän toimesta.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa esitetään joitakin ehdotettuja parhaita käytäntöjä `TextField`:in käytölle.

- **Tarjoa Selkeät Etiketit ja Ohjeet**: Erita `TextField`:iä selkeästi ilmoittaaksesi, minkä tyyppistä tietoa käyttäjien tulisi syöttää. Tarjoa lisäohjeita tai paikkamerkkiarvoja ohjaamaan käyttäjiä ja asettamaan odotuksia vaaditulle syötteelle.

- **Oikoluku ja Automaattinen Täydennys**: Jos mahdollista, harkitse oikoluvun käyttämistä `setSpellCheck()`-menetelmällä ja/tai automaattisen täydennyksen käyttöä `TextField`:issä. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoa nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aiempien syötteiden tai ennalta määrättyjen vaihtoehtojen perusteella.

- **Esteettömyys**: Käytä `TextField`-komponenttia esteettömyys mielessä, noudattaen esteettömyysstandardeja, kuten oikeaa nimeämistä, näppäimistön navigaatio-tukea ja yhteensopivuutta apuvälineiden kanssa. Varmista, että vammaiset käyttäjät voivat vuorovaikuttaa `TextField`:n kanssa tehokkaasti.
