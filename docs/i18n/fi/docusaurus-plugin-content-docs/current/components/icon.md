---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: 0e51ecab262c62fb63cd767ba8167084
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon`-komponentti näyttää ikoneita, jotka skaalautuvat mihin tahansa kokoon menettämättä laatua. Voit valita kolmen sisäänrakennetun ikonikokoelman välillä tai luoda omia. Ikonit toimivat visuaalisina vihjeinä navigoinnissa ja toimissa, vähentäen tekstilappujen tarvetta käyttöliittymässäsi.

<!-- INTRO_END -->

## Perusteet {#basics}

Jokainen `Icon` on suunniteltu Skaalautuviksi Vektorigrafiikoiksi (SVG), mikä tarkoittaa, että se voi helposti skaalautua mihin tahansa kokoon menettämättä selvyyttä tai laatua. Lisäksi `Icon`-komponentit ladataan tarpeen mukaan sisällönjakeluverkosta (CDN), mikä auttaa vähentämään viivettä ja parantamaan kokonaissuorituskykyä.

Kun luot `Icon`, sinun on tunnistettava tietty kokoelma ja itse ikonin nimi. Jotkut ikonit tarjoavat myös vaihtoehdon korostetun tai täytetyn version välillä [muunnelmien](#variations) kautta.

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Tiesitkö?
Jotkut komponentit, kuten `PasswordField` ja `TimeField`, sisältävät sisäänrakennettuja ikoneita, jotka auttavat välittämään merkitystä loppukäyttäjille.
:::

### Kokoelmat {#pools}

Ikkuna kokoelma on kokoelma yleisesti käytettyjä ikoneita, joka mahdollistaa helpon pääsyn ja käytön uudelleen. Käyttämällä ikoneita ikonikokoelmasta voit varmistaa, että sovelluksesi ikonit ovat tunnistettavia ja jakavat johdonmukaisen tyylin. WebforJ:n käyttö antaa sinun valita kolmen kokoelman väliltä tai toteuttaa mukautetun kokoelman. Jokaisella kokoelmalla on laaja kokoelma avoimen lähdekoodin ikoneita, joita voi käyttää ilmaiseksi. WebforJ:n käyttäminen antaa sinulle joustavuutta valita kolmen kokoelman väliltä ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata yhtään ikoneita suoraan.

| Ikonikerä | webforJ-luokka |
| -------- | ------- |
| [Tabler](https://tabler-icons.io/) | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on osa Tabler-ikonien kokoelmaa. |
| [Feather](https://feathericons.com/) | `FeatherIcon` |
| [Font Awesome](https://fontawesome.com/search) | `FontAwesomeIcon` |

:::tip

Jos olet kiinnostunut oman ikonikeräsi luomisesta, katso [Mukautettujen kokoelmien luominen](#creating-custom-pools).

:::

Kun olet valinnut kokoelman tai kokoelmat, jotka aiot sisällyttää sovellukseesi, seuraava vaihe on määrittää haluamasi ikonin nimi.

### Nimet {#names}

Sisällyttääksesi ikonin sovellukseesi tarvitset kaiken ikonikokoelman ja ikonin nimen. Selaa ikonikokoelman verkkosivustoa haluamasi ikonin löytämiseksi ja käytä ikonin nimeä `create()`-metodin parametrina. Voit myös luoda ikoneita enumien avulla `FeatherIcon` ja `DwcIcon` -luokille, jolloin ne näkyvät koodin täydennyksessä.

```java
// Luo ikoni String-nimen avulla
Icon image = TablerIcon.create("image");
// Luo ikoni enumeroinnin avulla
Icon image = FeatherIcon.IMAGE.create();
```

### Muunnelmat {#variations}

Voit personoida ikoneita entistä enemmän hyödyntämällä muunnelmia. Tietyt ikonit antavat sinun valita korostetun tai täytetyn version, jolloin voit korostaa tiettyä ikonia mieltymystesi mukaan. `FontAwesomeIcon`- ja `Tabler`-ikonit tarjoavat muunnelmia.

#### `FontAwesomeIcon` muunnelmat {#fontawesomeicon-variations}

1. `REGULAR`: Ikoneiden korostettu muunnos. Tämä on oletus.
2. `SOLID`: Ikoneiden täytetty muunnos.
3. `BRAND`: Varianti, jota käytetään brändien ikonien kanssa.

#### `TablerIcon` muunnelmat {#tablericon-variations}

1. `OUTLINE`: Ikoneiden korostettu muunnos. Tämä on oletus.
2. `FILLED`: Ikoneiden täytetty muunnos.

```java
// Täytetty muunnos Font Awesome -ikonista
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava demo havainnollistaa, kuinka käyttää ikoneita eri kokoelmista, soveltaa muunnelmia ja integroida ne sujuvasti komponentteihin.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ikonien lisääminen komponentteihin {#adding-icons-to-components}

Integroi ikonit komponentteihisi käyttämällä slotteja. Slotit tarjoavat joustavia vaihtoehtoja, jotka tekevät komponentteista hyödyllisempiä. On hyödyllistä lisätä `Icon` komponenttiin, jotta käyttäjille voidaan selventää tarkoitetun merkityksen. Komponentit, jotka toteuttavat `HasPrefixAndSuffix` -käyttöliittymän, voivat sisältää `Icon`- tai muita kelvollisia komponentteja. Lisättyjä komponentteja voidaan sijoittaa `prefix`- ja `suffix`-slotteihin, ja ne voivat parantaa sekä yleistä muotoilua että käyttäjäkokemusta.

Käyttämällä `prefix`- ja `suffix`-slotteja voit päättää, haluatko ikonin ennen vai jälkeen tekstin käyttäen `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä.

Päätös siitä, asetetaanko ikoni ennen vai jälkeen komponentin tekstin, riippuu suurelta osin tarkoituksesta ja muotoilukontekstista.

### Ikonin sijoittaminen: ennen VS jälkeen {#icon-placement-before-vs-after}

Ikonit, jotka on sijoitettu ennen komponentin tekstiä, auttavat käyttäjiä nopeasti ymmärtämään komponentin päätoiminnon tai -tarkoituksen, erityisesti yleisesti tunnistetuilla ikoneilla, kuten tallennusikonilla. Ikonit ennen komponentin tekstiä tarjoavat loogisen prosessointijärjestyksen, ohjaten käyttäjiä luonnollisesti tarkoitetun toiminnan läpi, mikä on hyödyllistä painikkeille, joiden ensisijainen tarkoitus on välitön toiminta.

Toisaalta, ikonien sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoille, jotka tarjoavat lisäkontekstia tai vaihtoehtoja, parantaen selkeyttä ja navigointivihjeitä. Ikonit komponentin tekstin jälkeen sopivat hyvin komponentteihin, jotka tarjoavat joko täydentävää tietoa tai ohjaavat käyttäjiä suuntaan.

Lopulta johdonmukaisuus on avainasia. Kun valitset tyylin, pidä se yllä koko sivustossasi yhtenäiseen ja käyttäjäystävälliseen muotoiluun.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Mukautettujen kokoelmien luominen {#creating-custom-pools}

Sen lisäksi, että käytät olemassa olevia ikonikokoelmia, voit luoda mukautetun kokoelman, jota voidaan käyttää mukautetuille logoille tai avatarille. Mukautettu ikonikokoelma voidaan tallentaa keskitettyyn hakemistoon tai resursseihin (konteksti), yksinkertaistaen ikonien hallintaprosessia. Mukautettu kokoelma tekee sovelluksen luomisesta johdonmukaisempaa ja vähentää ylläpitotarvetta eri komponenttien ja moduulien välillä.

Mukautetut kokoelmat voidaan luoda kansiosta, joka sisältää SVG-kuvia, ja käyttämällä `IconPoolBuilder`-luokkaa. Tämän jälkeen voit valita mukautetun kokoelmasi nimen ja käyttää sitä SVG-tiedoston nimien kanssa luodaksesi mukautettuja ikonikomponentteja.

```java
// Luodaan mukautettu kokoelma "app-pool", jossa on kuvat logosta ja avatarista.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet ikonit, joilla on yhtä leveys ja korkeus, koska `Icon`-komponentit on suunniteltu täyttämään neliömäinen tila.
:::

### Mukautetun kokoelman tehdas {#custom-pool-factory}

Voit myös luoda tehtaaluksen mukautetulle kokoelmalle webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa ikoniresurssien luomisen ja hallinnan tietyn kokoelman sisällä ja mahdollistaa koodin täydentämisen. Jokainen ikoni voidaan instansioida `create()`-menetelmän kautta, joka palauttaa `Icon`-komponentin. Tehdasluokan tulisi tarjota kokoelma-kohtaisia metatietoja, kuten kokoelman nimi ja ikonin tunniste, muotoiltu kuvan tiedostonimeksi. Tämä suunnittelu mahdollistaa helpon, standardoidun pääsyn ikoniresursseihin mukautetusta kokoelmasta käyttämällä enum-vakioita, mikä tukee skaalautuvuutta ja ylläpidettävyyttä ikonihallinnassa.

```java
/// Luodaan mukautetun kokoelman tehdas app-pool:lle
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return ikonien kokoelman nimi
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return ikonin nimi
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Seuraava pätkä näyttää kaksi erilaista tapaa käyttää mukautettua kokoelmaa.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Luo ikoni käyttäen mukautetun kokoelman ja kuva tiedoston nimiä
Icon customLogo = new Icon("logo", "app-pool");

// Luo ikoni käyttäen edellisessä pätkässä olevaa mukautetun kokoelman tehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Ikonipainikkeet {#icon-buttons}
`Icon`-komponentti ei ole valittavissa, mutta toimintoja, jotka on parasta esittää vain ikonilla, kuten ilmoitukset tai hälytykset, voit käyttää `IconButton`-komponenttia.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
});
```

## Parhaat käytännöt

- **Esteettömyys:** Käytä työkalun vihjeitä tai etikettiä ikoneissa, jotta sovelluksesi on esteetön näkörajoitteisille käyttäjille, jotka luottavat ruudunlukuohjelmiin.
- **Vältä epäselvyyksiä:** Vältä ikoneiden käyttöä, jos merkitys ei ole selkeä tai laajasti ymmärrettävä. Jos käyttäjien on arvattava, mitä ikoni edustaa, se kumoaa tarkoituksen.
- **Käytä ikoneita säästeliäästi:** Liian monta ikonia voi ylivoimaisesti käyttäjiä, joten käytä ikoneita vain silloin, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Tyylitys
Icon perii suoran vanhempikomponenttinsa teeman, mutta voit ylittää sen soveltamalla teemaa `Icon`-komponenttiin suoraan.

### Teemat
Ikoni komponentit sisältävät seitsemän erillistä teemaa valmiina nopeaa tyylitystä ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa ikoneihin, jotta niiden ulkonäköä ja visuaalista esitystä voidaan muuttaa. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ikonien ulkoasua koko sovelluksessa.

Vaikka jokaiselle erilaiselle teematyyliille on monia käyttötarkoituksia, joitakin esimerkkejä käyttötarkoituksista ovat:

- `DANGER`: Parasta toimille, joilla on vakavia seurauksia, kuten täytetyn tiedon tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
- `DEFAULT`: Sopii hyvin sovelluksen toimintoihin, jotka eivät vaadi erityistä huomiota ja ovat generisiä, kuten asetuksen kytkeminen.
- `PRIMARY`: Sopii pää "toimi" -toiminnolle sivulla, kuten rekisteröitymiselle, muutosten tallentamiselle tai siirtymiselle toiseen sivuun.
- `SUCCESS`: Erinomainen visualisoimaan jonkin sovelluksen elementin onnistunutta loppuunsaattamista, kuten lomakkeen lähettämistä tai rekisteröintiprosessin valmistumista. Onnistumisteemaa voidaan ohjelmallisesti soveltaa sen jälkeen, kun onnistunut toiminta on suoritettu.
- `WARNING`: Kätevä indikoimaan, että käyttäjä on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jossa on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikuttavia kuin ne, jotka käyttäisivät Danger-teemaa.
- `GRAY`: Hyvä pienille toimille, kuten vähäisille asetuksille tai toiminnoille, jotka ovat enemmän sivustoa täydentäviä, eikä muuten ole osa sen päätoimintoja.
- `INFO`: Hyvä lisätiedon tarjoamiseen käyttäjille. 

<TableBuilder name={['Icon', 'IconButton']} />
