---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: d582e67d9cfff3b1934f0e3b1a8396ab
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

`TextField`-komponentti sallii käyttäjien syöttää ja muokata tekstiä yhdellä rivillä. Voit konfiguroida kentän näyttämään tietyn virtuaalisen näppäimistön, kuten numero-näppäimistön, sähköpostisyötteen, puhelinnumero-näppäimistön tai URL-syötteen. Komponentti tarjoaa myös sisäänrakennetun validoinnin hylkäämään arvot, jotka eivät noudata määriteltyä tyyppiä.

## Käytöt {#usages}

`TextField` sopii laajaan valikoimaan tilanteita, joissa tarvitaan tekstisisäänsyöttöä tai muokkausta. Tässä on joitakin esimerkkejä siitä, milloin käyttää `TextField`-komponenttia:

1. **Lomakekentät**: `TextField` on yleisesti käytetty lomakkeissa käyttäjän syötteiden, kuten nimien, osoitteiden tai kommenttien, keräämiseen. On parasta käyttää `TextField`-komponenttia, kun syöte on yleisesti riittävän lyhyt mahtuakseen yhdelle riville.

2. **Hakuominaisuus**: `TextField` voi toimia hakupalkkina, joka antaa käyttäjille mahdollisuuden syöttää hakukyselyjä.

3. **Tekstiedito**: `TextField` on ihanteellinen sovelluksille, jotka vaativat tekstieditointia tai sisällön luomista, kuten asiakirjaeditorit, chatteja tai muistiinpanosovellukset.

## Tyyppi {#types}

Voit määrittää `TextField`-komponentin tyypin käyttämällä `setType()`-menetelmää. Vastaavasti voit noutaa tyypin käyttämällä `getType()`-menetelmää, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi ja se poistaa automaattisesti rivinvaihtomerkit syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin sähköpostisyntaksin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoitusprosessia, sisältäen yleisesti käytettyjä näppäimiä kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validointi vahvistaa sähköpostiosoitteen muodon, se ei takaa, että sähköposti olemassa.
  :::

- `Type.TEL`: Tämä tyyppi on tarkoitettu puhelinnumeroiden syöttämiseen. Kenttä näyttää puhelimen näppäimistön joissakin laitteissa, joissa on dynaamiset näppäimistöt.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi, onko käyttäjä syöttänyt URL-osoitteen, joka sisältää skeeman ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka helpottaa kirjoitusprosessia sisältäen yleisesti käytettyjä näppäimiä kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhden rivin tekstikenttä hakusanojen syöttämiseen. Rivinvaihtomerkit poistetaan automaattisesti syötearvosta.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo edustaa nykyistä käyttäjän syötettä merkkijonona. webforJ:ssä tämä voidaan saavuttaa käyttämällä `getValue()`-menetelmää tai päivittämällä ohjelmallisesti `setValue(String)`-menetelmällä.

```java
//Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

//Hae nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-menetelmää käytetään kentällä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">`-elementti altistaa arvonsa JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validoimiseen
Käytä rajoitteita, kuten [kuvio](#pattern-matching), [minimipituus](#setminlength) tai [maksimipituus](#setmaxlength), määrittääksesi, milloin arvo on voimassa. 
:::

## Paikkamerkkiteksti {#placeholder-text}

Voit asettaa paikkamerkkitekstiä `TextField`-komponentille käyttämällä `setPlaceholder()`-menetelmää. Paikkamerkkiteksti näkyy, kun kenttä on tyhjää, auttaen käyttäjää syöttämään oikeaa syötettä `TextField`-komponenttiin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa noutaaksesi käyttäjän valitseman tekstin ja saadaksesi tietoa käyttäjän valinnasta. Voit noutaa valitun tekstin `TextField`-komponentista käyttämällä `getSelectedText()`-menetelmää. Tätä käyttäytymistä käytetään yleisesti yhdessä tapahtuman kanssa.

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samalla tavalla on mahdollista noutaa nykyinen valintaraja `TextField`-komponentista käyttämällä `getSelectionRange()`-menetelmää. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin alku- ja loppuindeksiä.

:::tip Käyttämällä `getSelectedText()` vs tapahtuman kuormitus
Vaikka voit kutsua `getSelectedText()`-menetelmää manuaalisesti tapahtumankäsittelijän sisällä, on tehokkaampaa käyttää tapahtuman kuormituksessa tarjottuja valinta-aineistoja—kuten `SelectionChangeEvent`—lisähaun välttämiseksi.
:::

## Kuvioiden täsmäytys {#pattern-matching}

Voit käyttää `setPattern()`-menetelmää määrittääksesi validointisäännön `TextField`-komponentille säännöllisen lausekkeen avulla. Kuvion asettaminen lisää rajoitevalidoinnin, joka vaatii syötearvon vastaamaan määritettyä kuviota.

Kuvion on oltava voimassa oleva [JavaScript-säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selain tulkitsee sen. `u` (Unicode) lippu otetaan käyttöön sisäisesti varmistaaksesi Unicode-koodipisteiden tarkan täsmäytymisen. Älä kääri kuvioa eteenpäin vinoviivoihin (`/`), koska niitä ei tarvita ja niitä käsitellään kirjaimellisina merkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos mitään kuviota ei ole annettu tai syntaksi on virheellinen, validointisääntöä ei oteta huomioon.

:::tip Kontekstuaalinen apu
Kun käytetään monimutkaisempia kuvioita `TextField`:ssa, harkitse `setLabel()`, `setHelperText()` ja `setTooltipText()` -menetelmien yhdistämistä tarjotaksesi lisäneuvoja ja ohjeita.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee raja-validointia käyttäjän syöttämien merkkien määrän perusteella. Tämä voidaan hallita käyttäen `setMinLength()` ja `setMaxLength()` -menetelmiä. Käytä molempia menetelmiä määrittääksesi selkeän rajaparin hyväksyttävien syötteiden pituuksille.

:::info Pituusvaatimukset
Oletusarvoisesti kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, ilmoittaen käyttäjälle, onko syötettä lyhennettävä tai pidentävä. Tämä viesti voidaan ohittaa `setInvalidMessage()`-menetelmällä.
:::

### `setMinLength()` {#setminlength}

Tämä menetelmä asettaa kentän voimassaolon edellyttämien **minimimäärä UTF-16 koodiyksiköitä**, jotka on syötettävä. Arvon on oltava kokonaisluku, eikä se saa ylittää asetettua maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syöte sisältää vähemmän merkkejä kuin minimivaatimus, syöte epäonnistuu raja-validoinnissa. Tämä sääntö pätee vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä menetelmä asettaa kentässä sallitut **maximimäärä UTF-16 koodiyksiköitä**. Arvon on oltava `0` tai suurempi. Jos ei aseteta tai asetetaan virheelliseen arvoon, mitään maksimiä ei valvota.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä epäonnistuu raja-validoinnissa, jos syötteen pituus ylittää maksimipituuden. Kuten `setMinLength()`:n kanssa, tämä validointi aktivoituu vain silloin, kun arvo muuttuu käyttäjän toimesta.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa esitetään joitakin suositeltuja käytäntöjä `TextField`-komponentin käytölle.

- **Tarjoa selkeät etiketit ja ohjeet**: Merkitse `TextField` selvästi osoittamaan, millaista tietoa käyttäjien tulisi syöttää. Tarjoa lisäohjeita tai paikkamerkkiarvoja opastamaan käyttäjiä ja asettamaan odotuksia vaadittavalle syötteelle.

- **Oikoluku ja automaattitäyttö**: Tarvittaessa harkitse oikoluvun käyttämistä `setSpellCheck()`-menetelmällä ja/tai automaattitäytön käyttöä `TextField`-komponentissa. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoja nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aiemmista syötteistä tai ennalta määritellyistä vaihtoehdoista.

- **Esteettömyys**: Hyödynnä `TextField`-komponenttia esteettömyys mielessä pitäen, noudattaen esteettömyysstandardeja, kuten asianmukaisia merkintöjä, näppäimistön navigointitukea ja yhteensopivuutta apuvälineiden kanssa. Varmista, että käyttäjät, joilla on vammoja, voivat vuorovaikuttaa `TextField`-komponentin kanssa tehokkaasti.
