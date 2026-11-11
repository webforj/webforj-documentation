---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 2f01abb14f66d83dd6edd3cc18ac560e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

`TextField`-komponentin avulla käyttäjät voivat kirjoittaa ja muokata tekstiä yhdellä rivillä. Voit määrittää kentän näyttämään tietyn virtuaalinäppäimistön, kuten numeronäppäimistön tai sähköpostin, puhelinnumeron tai URL-osoitteen syöttämiseen tarkoitetun näppäimistön. Komponentissa on myös sisäänrakennettu validointi, joka hylkää arvot, jotka eivät vastaa määritettyä tyyppiä.

<!-- INTRO_END -->

## Käyttökohteet {#usages}

<ParentLink parent="Field" />

`TextField` sopii monenlaisiin tilanteisiin, joissa tarvitaan tekstin syöttämistä tai muokkaamista. Seuraavassa on esimerkkejä tilanteista, joissa `TextField`-komponenttia kannattaa käyttää:

1. **Lomakekentät**: `TextField`-komponenttia käytetään usein lomakkeissa käyttäjän syötteiden, kuten nimien, osoitteiden tai kommenttien, keräämiseen. `TextField` sopii sovellukseen parhaiten silloin, kun syöte on yleensä riittävän lyhyt mahtuakseen yhdelle riville.

2. **Haku**: `TextField`-komponenttia voidaan käyttää hakukenttänä, johon käyttäjät voivat syöttää hakulausekkeita.

3. **Tekstin muokkaaminen**: `TextField` sopii erinomaisesti sovelluksiin, joissa tarvitaan tekstin muokkaamista tai sisällön luomista, kuten dokumenttien muokkausohjelmiin, chat-sovelluksiin tai muistiinpanosovelluksiin.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin `setType()`-metodilla. Tyypin voi hakea vastaavasti `getType()`-metodilla, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi, joka poistaa rivinvaihdot automaattisesti syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen sähköpostiosoitteiden vakiomuodon mukaisesti. Lisäksi se tarjoaa yhteensopivissa selaimissa ja laitteissa dynaamisen näppäimistön, joka helpottaa kirjoittamista sisältämällä yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Tämä validointi vahvistaa sähköpostiosoitteen muodon, mutta ei takaa, että sähköpostiosoite on olemassa.
  :::

- `Type.TEL`: Tätä tyyppiä käytetään puhelinnumeron syöttämiseen. Joissakin dynaamisilla näppäimistöillä varustetuissa laitteissa kentässä näkyy puhelinnäppäimistö.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se tarkistaa, että käyttäjän syöttämä URL-osoite sisältää skeeman ja verkkotunnuksen, esimerkiksi https://webforj.com. Lisäksi se tarjoaa yhteensopivissa selaimissa ja laitteissa dynaamisen näppäimistön, joka helpottaa kirjoittamista sisältämällä yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yhdellä rivillä käytettävä tekstikenttä hakumerkkijonojen syöttämiseen. Rivinvaihdot poistetaan automaattisesti syötearvosta.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo vastaa käyttäjän nykyistä syötettä merkkijonona. webforJ:ssä arvon voi lukea `getValue()`-metodilla tai päivittää ohjelmallisesti `setValue(String)`-metodilla.

```java
//Aseta alkusisältö
textField.setValue("Alkusisältö");

//Hae nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentässä, jolla ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä vastaa sitä, miten HTML-elementti `<input type="text">` tuo arvonsa saataville JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validointiin
Käytä rajoitteita, kuten [mallia](#pattern-matching), [vähimmäispituutta](#setminlength) tai [enimmäispituutta](#setmaxlength), määrittääksesi, milloin arvo katsotaan kelvolliseksi.
:::

## Paikkamerkkiteksti {#placeholder-text}

Voit asettaa `TextField`-komponentin paikkamerkkitekstin `setPlaceholder()`-metodilla. Paikkamerkkiteksti näkyy, kun kenttä on tyhjä, ja auttaa kehottaen käyttäjää syöttämään sopivan arvon `TextField`-kenttään.

## Valittu teksti {#selected-text}

`TextField`-luokalla voi hakea käyttäjän valitseman tekstin ja saada tietoja käyttäjän valinnasta. Voit hakea `TextField`-komponentista valitun tekstin `getSelectedText()`-metodilla. Tätä käytetään usein yhdessä tapahtuman kanssa.

```java
TextField textField = new TextField("Kirjoita jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Vastaavasti `TextField`-komponentin nykyisen valinta-alueen voi hakea `getSelectionRange()`-metodilla. Se palauttaa `SelectionRange`-olion, joka kuvaa valitun tekstin alku- ja loppuindeksiä.

:::tip Arvon muutokset ja valinta
Käsittele kentän arvon muutoksia `onValueChange()`-metodilla. Sen `ValueChangeEvent<String>` tarjoaa uuden arvon `event.getValue()`-metodin kautta, mutta ei sisällä valinnan tietoja. Voit lukea nykyisen valinnan kutsumalla kentän `getSelectedText()`- tai `getSelectionRange()`-metodia.
:::

## Mallin mukaisuuden tarkistus {#pattern-matching}

Voit määrittää `TextField`-komponentin validointisäännön säännöllisen lausekkeen avulla `setPattern()`-metodilla. Mallin asettaminen lisää rajoitevalidoinnin, joka edellyttää syötearvon vastaavan määritettyä mallia.

Mallin on oltava kelvollinen [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) selaimen tulkitsemana. `u` (Unicode) -lippu lisätään sisäisesti Unicode-koodipisteiden täsmällisen vastaavuuden varmistamiseksi. Älä ympäröi mallia kauttaviivoilla (`/`), sillä niitä ei tarvita ja niitä käsitellään kirjaimellisina merkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos mallia ei anneta tai sen syntaksi on virheellinen, validointisääntö ohitetaan.

:::tip Tilannekohtainen ohjeistus
Kun käytät monimutkaisia malleja `TextField`-komponentissa, harkitse `setLabel()`-, `setHelperText()`- ja `setTooltipText()`-metodien käyttämistä yhdessä lisävihjeiden ja ohjeiden tarjoamiseksi.
:::

## Vähimmäis- ja enimmäispituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitevalidointia käyttäjän syöttämien merkkien määrän perusteella. Tätä voidaan hallita `setMinLength()`- ja `setMaxLength()`-metodeilla. Määritä hyväksyttävän syötteen selkeät pituusrajat käyttämällä molempia metodeja.

:::info Pituusvaatimukset
Kenttä näyttää oletusarvoisesti viestin, kun syötearvo on sallitun alueen ulkopuolella. Viesti kertoo käyttäjälle, pitääkö syötettä lyhentää vai pidentää. Viestin voi korvata `setInvalidMessage()`-metodilla.
:::

### `setMinLength()` {#setminlength}

Tämä metodi määrittää **pienimmän UTF-16-koodiyksiköiden määrän**, joka kenttään on syötettävä, jotta se katsotaan kelvolliseksi. Arvon on oltava kokonaisluku, eikä se saa ylittää määritettyä enimmäispituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syötteessä on vaadittua vähimmäismäärää vähemmän merkkejä, se ei läpäise rajoitevalidointia. Tätä sääntöä sovelletaan vain, kun käyttäjä muuttaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä metodi määrittää tekstikentässä sallittujen **UTF-16-koodiyksiköiden enimmäismäärän**. Arvon on oltava `0` tai suurempi. Jos arvoa ei aseteta tai se on virheellinen, enimmäispituutta ei rajoiteta.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä ei läpäise rajoitevalidointia, jos syötteen pituus ylittää enimmäispituuden. Kuten `setMinLength()`-metodissa, validointi käynnistyy vain, kun käyttäjä muuttaa arvoa.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa kuvataan suositeltuja käytäntöjä `TextField`-komponentin käyttöön.

- **Selkeät tunnisteet ja ohjeet**: Lisää `TextField`-kentälle selkeä tunniste, joka kertoo, millaisia tietoja käyttäjän tulee syöttää. Anna lisäksi ohjetekstiä tai paikkamerkkitekstiä, joka opastaa käyttäjää ja selventää syötteen vaatimuksia.

- **Oikoluku ja automaattinen täydennys**: Kun se on tarkoituksenmukaista, harkitse oikoluvun käyttöönottoa `setSpellCheck()`-metodilla ja/tai automaattisen täydennyksen käyttämistä `TextField`-kentässä. Nämä ominaisuudet voivat nopeuttaa tietojen syöttämistä ja vähentää virheitä ehdottamalla aiempien syötteiden tai ennalta määritettyjen vaihtoehtojen perusteella sopivia arvoja.

- **Esteettömyys**: tarjoa selkeät tunnisteet, näppäimistöllä navigointi ja avustavien teknologioiden tuki, jotta myös vammaiset käyttäjät voivat käyttää kenttää.
