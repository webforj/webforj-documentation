---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: 71ebfc077bb8042752cbfea71a971e47
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

`TextField`-komponentti mahdollistaa käyttäjien syöttää ja muokata tekstiä yhdessä rivissä. Voit määrittää kentän näyttämään erityisen virtuaalinäppäimistön, kuten numeronäppäimistön, sähköpostisyötteen, puhelinnumerosyötteen tai URL-syötteen. Komponentti tarjoaa myös sisäänrakennettua validoimista hylätäkseen arvot, jotka eivät noudata määriteltyä tyyppiä.

## Käyttötapaukset {#usages}

`TextField` sopii laajaan valikoimaan tilanteita, joissa tekstiä syötetään tai muokataan. Tässä on joitakin esimerkkejä, milloin käyttää `TextField`-komponenttia:

1. **Lomakesyötteet**: `TextField`-komponenttia käytetään yleisesti lomakkeissa käyttäjän syötteen, kuten nimien, osoitteiden tai kommenttien tallentamiseen. `TextField`-komponentti on paras valinta sovelluksessa, kun syöte on yleensä tarpeeksi lyhyt mahtuakseen yhdelle riville.

2. **Hakutoiminnallisuus**: `TextField`-komponenttia voidaan käyttää hakukenttänä, joka mahdollistaa käyttäjien syöttää hakukyselyitä.

3. **Tekstin muokkaus**: `TextField` on ihanteellinen sovelluksille, jotka vaativat tekstin muokkaamista tai sisällön luomista, kuten asiakirjankäsittelysovellukset, keskusteluohjelmat tai muistiinpanosovellukset.

## Tyypit {#types}

Voit määrittää `TextField`-komponentin tyypin käyttämällä `setType()`-metodia. Vastaavasti voit noutaa tyypin käyttämällä `getType()`-metodia, joka palauttaa enum-arvon.

- `Type.TEXT`: Tämä on `TextField`-komponentin oletustyyppi, joka automaattisesti poistaa rivinvaihdot syötearvosta.

- `Type.EMAIL`: Tämä tyyppi on tarkoitettu sähköpostiosoitteiden syöttämiseen. Se validoi syötteen standardin sähköpostisynnin mukaan. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka yksinkertaistaa kirjoitusprosessia sisältäen yleisesti käytettyjä näppäimiä, kuten <kbd>.com</kbd> ja <kbd>@</kbd>.

  :::note
  Vaikka tämä validoiminen vahvistaa sähköpostiosoitteen muotoa, se ei takaa, että sähköpostiosoite on olemassa.
  :::

- `Type.TEL`: Tämä tyyppi on tarkoitettu puhelinnumeron syöttämiseen. Kenttä näyttää puhelimen näppäimistön tietyissä laitteissa dynaamisten näppäimistöjen kanssa.

- `Type.URL`: Tämä tyyppi on tarkoitettu URL-osoitteiden syöttämiseen. Se validoi, että käyttäjä syöttää URL-osoitteen, joka sisältää kaavan ja verkkotunnuksen, esimerkiksi: https://webforj.com. Lisäksi se tarjoaa yhteensopiville selaimille ja laitteille dynaamisen näppäimistön, joka yksinkertaistaa kirjoitusprosessia sisältäen yleisesti käytettyjä näppäimiä, kuten <kbd>:</kbd>, <kbd>/</kbd> ja <kbd>.com</kbd>.

- `Type.SEARCH`: Yksirivinen tekstikenttä hakusanojen syöttämiseen. Rivinvaihdot poistetaan automaattisesti syötearvosta.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Kentän arvo {#field-value}

`TextField`-komponentin arvo edustaa nykyistä käyttäjän syötettä merkkijonona. webforJ:ssä tähän voidaan päästä käsiksi käyttämällä `getValue()`-metodia tai päivittämällä ohjelmallisesti `setValue(String)`-metodilla.

```java
//Aseta alkuperäinen sisältö
textField.setValue("Alkuperäinen sisältö");

//Nouta nykyinen arvo
String text = textField.getValue();
```

Jos `getValue()`-metodia käytetään kentässä, jossa ei ole nykyistä arvoa, se palauttaa tyhjän merkkijonon (`""`).

Tämä käyttäytyminen on johdonmukaista sen kanssa, miten HTML `<input type="text">` -elementti paljastaa arvonsa JavaScriptin kautta.

:::tip Yhdistä arvon käsittely validoimiseen
Käytä rajoituksia, kuten [kuvio](#pattern-matching), [minimipituus](#setminlength) tai [maksimipituus](#setmaxlength), määrittääksesi, milloin arvoa pidetään voimassa. 
:::

## Paikkamerkkiteksti {#placeholder-text}

Voit asettaa paikkamerkkitekstiä `TextField`-komponentille käyttämällä `setPlaceholder()`-metodia. Paikkamerkkiteksti näkyy, kun kenttä on tyhjillään, auttaen käyttäjää syöttämään asianmukaista tietoa `TextField`-komponenttiin.

## Valittu teksti {#selected-text}

On mahdollista vuorovaikuttaa `TextField`-luokan kanssa saadaksesi käyttäjän valitseman tekstin ja saadaksesi tietoa käyttäjän valinnasta. Valittu teksti `TextField`-komponentissa voidaan noutaa `getSelectedText()`-metodin avulla. Tätä käyttäytymistä käytetään tyypillisesti yhdessä tapahtuman kanssa. 

```java
TextField textField = new TextField("Syötä jotain...");
Button getSelectionBtn = new Button("Hae valittu teksti");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Valittu teksti: " + selected);
});
```

Samoin on mahdollista noutaa `TextField`-komponentin nykyinen valintavalikoima `getSelectionRange()`-metodin avulla. Tämä palauttaa `SelectionRange`-objektin, joka edustaa valitun tekstin alkamis- ja päättymisindeksejä.

:::tip Käytä `getSelectedText()` vs tapahtuman payload
Vaikka voit kutsua `getSelectedText()`-metodia manuaalisesti tapahtuman käsittelijässä, on tehokkaampaa käyttää tapahtuman payloadissa annettua valintatietoa—kuten `SelectionChangeEvent`—lisähaun välttämiseksi.
:::

## Kuvion vastaavuus {#pattern-matching}

Voit käyttää `setPattern()`-metodia määrittääksesi validoimisääntö `TextField`-komponentille käyttäen säännöllistä lausetetta. Kuvion asettaminen lisää rajoitevaihtoehtoa, joka vaatii syötearvon vastaamaan määriteltyä kuvioita.

Kuvion on oltava voimassa oleva [JavaScriptin säännöllinen lauseke](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), kuten selain tulkitsee. `u` (Unicode) -lippu sovelletaan sisäisesti taataksesi Unicode-kohtien tarkat vastaavuudet. Älä kääri kuvioita eteen- ja taaksepäin vinoviivoihin (`/`), koska niitä ei tarvita ja niitä käsitellään kirjainmerkkeinä.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // esim. ABC12
```

Jos kuvioita ei anneta tai syntaksi on virheellinen, validoimisääntöä ei noudateta.

:::tip Kontekstuaalinen apu
Kun käytät monimutkaisia kuvioita `TextField`-komponentissa, harkitse yhdistelmän käyttöä `setLabel()`, `setHelperText()` ja `setTooltipText()` menetelmistä
tarjoamaan lisävihjeitä ja ohjeita.
:::

## Minimipituus ja maksimipituus {#minimum-and-maximum-length}

`TextField`-komponentti tukee rajoitevalidoimista käyttäjän syöttämien merkkien määrän osalta. Tämä voidaan hallita käyttämällä `setMinLength()` ja `setMaxLength()` -metodeja. Käytä molempia menetelmiä määrittämään selkeä hyväksyttävien syötteiden pituusraja.

:::info Pituusvaatimukset
Oletusarvoisesti kenttä näyttää viestin, kun syötearvo on alueen ulkopuolella, ilmoittaen käyttäjälle, tarvitseeko heidän lyhentää tai pidentää syötteensä. Tämä viesti voidaan korvata `setInvalidMessage()` -metodilla.
:::

### `setMinLength()` {#setminlength}

Tämä menetelmä asettaa **vähimmäismäärän UTF-16-koodiyksiköitä**, jotka on syötettävä, jotta kenttä katsotaan päteväksi. Arvon on oltava kokonaisluku, eikä sen tulisi ylittää määriteltyä maksimipituutta.

```java
textField.setMinLength(5); // Käyttäjän on syötettävä vähintään 5 merkkiä
```

Jos syöte sisältää vähemmän merkkejä kuin vähimmäisvaatimus, syöte epäonnistuu rajoitevalidoinnissa. Tämä sääntö pätee vain, kun käyttäjä vaihtaa kentän arvoa.

### `setMaxLength()` {#setmaxlength}

Tämä menetelmä asettaa **maksimimäärän UTF-16-koodiyksiköitä**, joita saa olla tekstikentässä. Arvon on oltava `0` tai suurempi. Jos sitä ei aseteta tai se asetetaan virheelliseen arvoon, maksimirajaa ei valvoa.

```java
textField.setMaxLength(20); // Käyttäjä ei voi syöttää yli 20 merkkiä
```

Kenttä epäonnistuu rajoitevalidoinnissa, jos syötteen pituus ylittää minimipituuden. Kuten `setMinLength()`-menetelmällä, tämä validoiminen laukaistaan vain silloin, kun käyttäjä vaihtaa arvoa.

## Parhaat käytännöt {#best-practices}

Seuraavassa osiossa esitellään joitakin ehdotettuja parhaita käytäntöjä `TextField`-komponentin käytölle.

- **Tarjoa Selkeät Etiketit ja Ohjeet**: Merkitse `TextField` selkeästi osoittaaksesi, millaista tietoa käyttäjien tulisi syöttää. Anna lisäohjeita tai paikkamerkkiarvoja ohjaamaan käyttäjiä ja asettamaan odotuksia vaaditulle syötteelle.

- **Oikoluku ja Automaattinen Täyttö**: Soveltuvin osin harkitse oikoluvun käyttäminen `setSpellCheck()`-metodilla ja/tai automaattisen täytön tarjoaminen `TextField`-komponentissa. Nämä ominaisuudet voivat auttaa käyttäjiä syöttämään tietoja nopeammin ja vähentämään virheitä tarjoamalla ehdotettuja arvoja aiempien syötteiden tai ennalta määritettyjen vaihtoehtojen perusteella.

- **Saavutettavuus**: Käytä `TextField`-komponenttia saavutettavuus mielessä pitäen, noudattaen saavutettavuusstandardeja, kuten oikeaa merkitsemistä, näppäimistön navigointituen tarjoamista ja yhteensopivuutta avustavien teknologioiden kanssa. Varmista, että vammaiset käyttäjät voivat vuorovaikuttaa `TextField`-komponentin kanssa tehokkaasti.
