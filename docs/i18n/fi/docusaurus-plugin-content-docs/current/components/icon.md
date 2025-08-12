---
title: Icon
sidebar_position: 55
_i18n_hash: 2da7d4e8288df67fc46f2a3ba84e12ee
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

webforJ `Icon` -komponentti mahdollistaa ikonien lisäämisen vaivattomasti käyttöliittymään
Ikonit ovat keskeinen osa käyttöliittymän muotoilua, mikä tekee käyttäjien helpommaksi skannata näyttöä toimenpiteitä varten.
Ikonien käyttäminen sovelluksessa luo visuaalisia vihjeitä navigointiin ja toimintoihin, mikä voi vähentää tekstin määrää ja yksinkertaistaa käyttöliittymää. Voit valita kolmesta olemassa olevasta ikonipoolista, ja webforJ antaa sinulle myös mahdollisuuden luoda uusia alusta alkaen.

:::tip Tiesitkö?

Jotkin komponentit, kuten `PasswordField` ja `TimeField`, sisältävät sisäänrakennettuja ikoneita, jotka auttavat välittämään merkityksiä loppukäyttäjille.

:::

## Perusteet {#basics}

Jokainen `Icon` on suunniteltu skaalautuvaksi vektorigrafiikaksi (SVG), mikä tarkoittaa, että se voi helposti skaalautua mihin tahansa kokoon menettämättä tarkkuutta tai laatua.
Lisäksi `Icon`-komponentit ladataan tarpeen mukaan sisällön toimitusverkosta (CDN), mikä auttaa vähentämään viivettä ja parantamaan kokonaistehokkuutta.

Kun luot `Icon`-komponenttia, sinun on määritettävä tietty pooli ja ikonin nimi.
Jotkin ikonit tarjoavat myös vaihtoehdon valita ääriviivainen tai täytetty versio [muunnelmien](#variations) kautta.

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Poolit {#pools}

Ikonipooli on kokoelma yleisimmin käytettyjä ikoneita, mikä mahdollistaa helpon pääsyn ja uudelleenkäytön. Käyttämällä ikoneita ikonipoolista voit varmistaa, että sovelluksesi ikonit ovat tunnistettavia ja jakavat yhdenmukaisen tyylin.
webforJ:n käyttäminen antaa sinulle mahdollisuuden valita kolmesta poolista tai toteuttaa mukautetun poolin.
Jokaisessa poolissa on laaja kokoelma avoimen lähdekoodin ikoneita, joita voit käyttää ilmaiseksi.
webforJ:n käyttö antaa sinulle joustavuutta valita kolme poolia ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata ikoneita suoraan.

| Ikonipooli                                         | webforJ-luokka |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on alikokoelma Tabler-ikoneista.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Jos olet kiinnostunut luomaan oman ikonipoolisi, katso [Mukautettujen poolien luominen](#creating-custom-pools).

:::

Kun olet valinnut poolin tai poolit, jotka haluat sisällyttää sovellukseesi, seuraava vaihe on määrittää sen ikonin nimi, jota haluat käyttää.

### Nimi {#names}

Sisällyttääksesi ikonin sovellukseesi, tarvitset vain ikonipoolin ja ikonin nimen. Selaa ikonipoolin verkkosivustoa etsimään haluamaasi ikonia ja käytä ikonin nimeä `create()`-metodin parametrina.
Lisäksi voit luoda ikoneita enumien kautta `FeatherIcon` ja `DwcIcon` -luokissa, jolloin ne näkyvät koodin täydennyksessä.

```java
// Luo ikoni merkkijonon nimestä
Icon image = TablerIcon.create("image");
// Luo ikoni enumista
Icon image = FeatherIcon.IMAGE.create();
```

### Muunnelmat {#variations}

Voit personoida ikoneita vielä enemmän hyödyntämällä muunnelmia.
Tietyt ikonit antavat sinun valita ääriviivaisen tai täytetyn version, jolloin voit korostaa tiettyä ikonia mieltymyksesi mukaan. `FontAwesomeIcon` ja `Tabler` -ikonit tarjoavat muunnelmia.

#### `FontAwesomeIcon` -muunnelmat {#fontawesomeicon-variations}

1. `REGULAR`: Ikonien ääriviivaversio. Tämä on oletus.
2. `SOLID`: Ikonien täytetty versio.
3. `BRAND`: Versio brändien ikonien käyttäessä.

#### `TablerIcon` -muunnelmat {#tablericon-variations}

1. `OUTLINE`: Ikonien ääriviivaversio. Tämä on oletus.
2. `FILLED`: Ikonien täytetty versio.

```java
// Täytetty muunnelma ikonista Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava esittely havainnollistaa, kuinka käyttää ikoneita eri poolista, soveltaa muunnelmia ja käyttää niitä saumattomasti komponenteissa.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Ikonien lisääminen komponentteihin {#adding-icons-to-components}

Integroi ikonit komponentteihisi käyttäen slotteja. Slotit tarjoavat joustavia vaihtoehtoja tehdä komponenteista hyödyllisempiä. Ikonin lisääminen komponenttiin on hyödyllistä lisätäksesi tarkoitettua merkitystä käyttäjille.
Komponentit, jotka toteuttavat `HasPrefixAndSuffix` -rajapinnan, voivat sisältää `Icon` -komponentin tai muita kelvollisia komponentteja. Lisätyt komponentit voidaan sijoittaa `prefix` ja `suffix` slotteihin, mikä voi parantaa kokonaismuotoilua ja käyttäjäkokemusta.

Käyttämällä `prefix` ja `suffix` slotteja voit päättää, haluatko ikonin ennen vai jälkeen tekstin `setPrefixComponent()` ja `setSuffixComponent()` -metodien avulla.

Päätöksenteko siitä, sijoitetaanko ikoni ennen vai jälkeen tekstin, riippuu suuresti tarkoituksesta ja muotoiluyhteydestä.

### Ikonin sijoittaminen: ennen VS jälkeen {#icon-placement-before-vs-after}

Ikonit, jotka sijoitetaan komponentin tekstin eteen, auttavat käyttäjiä ymmärtämään nopeasti komponentin päätoiminnan tai tarkoituksen, erityisesti yleisesti tunnistettavien ikonien, kuten tallennusikonin, kohdalla.
Ikonit komponentin tekstin edessä tarjoavat loogisen käsittelyjärjestyksen, ohjaten käyttäjät luonnollisesti tarkoitetun toiminnan läpi, mikä on hyödyllistä painikkeille, joiden ensisijainen tehtävä on välitön toiminta.

Toisaalta ikonien sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoille, jotka tarjoavat lisäkontekstia tai vaihtoehtoja, parantaen selkeyttä ja navigointivihjeitä.
Ikonit komponentin tekstin jälkeen ovat ihanteellisia komponenteille, jotka tarjoavat joko lisätietoja tai ohjaavat käyttäjiä suuntaan.

Lopulta johdonmukaisuus on ratkaisevaa. Kun valitset tyylin, pidä kiinni siitä koko sivustolla, jotta saat aikaan yhdenmukaisen ja käyttäjäystävällisen muotoilun.
   
<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Mukautettujen poolien luominen {#creating-custom-pools}

Olemassa olevien ikonikokoelmien käyttämisen lisäksi sinulla on mahdollisuus luoda mukautettu pooli, jota voidaan käyttää mukautetuille logoille tai avatarille.
Mukautettu ikonikokoelma voidaan tallentaa keskitettyyn hakemistoon tai resursseille (konteksti), mikä yksinkertaistaa ikonien hallintaprosessia.
Mukautetun poolin omistaminen tekee sovelluksen luomisesta johdonmukaisempaa ja vähentää ylläpitoa eri komponenteissa ja moduuleissa.

Mukautettuja poolia voidaan luoda hakemistosta, joka sisältää SVG-kuvia, ja käyttämällä `IconPoolBuilder` -luokkaa. Tämän jälkeen voit valita mukautetun poolisi nimen ja käyttää sitä SVG-tiedoston nimien kanssa luodaksesi mukautettuja ikonikomponentteja.

```java
// Luodaan mukautettu pooli nimeltä "app-pool", joka sisältää kuvia logosta ja avatarista.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet ikonit yhtä leveiksi ja korkeiksi, sillä `Icon` -komponentit on suunniteltu täyttämään neliön muotoinen tila.
:::

### Mukautetun poolin tehdas {#custom-pool-factory}

Voit myös luoda tehdasluokan mukautetulle poolille webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa ikonien luomisen ja hallinnan tiettyyn pooliin ja mahdollistaa koodin täydentämisen.
Jokainen ikoni voidaan luoda `create()` -metodin kautta, joka palauttaa `Icon`:n. Tehtaaluokan tulisi tarjota poolikohtaisia metatietoja, kuten poolin nimi ja ikonin tunniste, joka on muotoiltu kuvan tiedostonimen mukaan.
Tämä suunnittelu mahdollistaa helpon, standardoidun pääsyn ikonivarantoihin mukautetusta poolista käyttäen enum-vakiota, mikä tukee skaalausta ja ylläpidettävyyttä ikonien hallinnassa.

```java
// Mukautetun poolin tehdas "app-pool" luominen
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return ikonien poolin nimi
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

Seuraava koodinpätkä näyttää kaksi erilaista tapaa käyttää mukautettua poolia.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Luo ikoni käyttämällä mukautetun poolin ja kuvafailin nimiä
Icon customLogo = new Icon("logo", "app-pool");

// Luo ikoni käyttämällä aikaisemman koodinpätkän mukautettua poolin tehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Ikoni painikkeet {#icon-buttons}
`Icon` -komponentti ei ole valittavissa, mutta toiminnoille, jotka parhaiten esitetään vain ikonilla, kuten ilmoituksille tai hälytyksille, voit käyttää `IconButton`-painiketta.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
  });
```

## Parhaat käytännöt

- **Saavutettavuus:** Käytä työkaluvinkkiä tai tarraa ikoneissa, jotta sovelluksesi on saavutettava näkövammaisille käyttäjille, jotka nojaavat ruudunlukijoihin.
- **Vältä epäselvyyksiä:** Vältä ikonien käyttöä, jos niiden merkitys ei ole selvä tai laajasti ymmärretty. Jos käyttäjien on arvattava, mitä ikoni tarkoittaa, se kumoaa asian tarkoituksen.
- **Käytä ikoneita säästeliäästi:** Liian monet ikonit voivat ylittää käyttäjät, joten käytä ikoneita vain, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Tyylit
Ikoni perii suoran vanhempikomponenttinsa teeman, mutta voit tarpeen mukaan ylittää tämän soveltamalla teemaa suoraan `Icon` -komponenttiin.

### Teemat
Ikoni komponentit sisältävät seitsemän erillistä teemaa nopeaa muotoilua varten ilman CSS:n käyttöä. Nämä teemat ovat ennakkoon määriteltyjä tyylejä, joita voidaan soveltaa ikoneihin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ikonien ulkonäköä sovelluksen sisällä.

Vaikka erilaisille teemoille on monia käyttötarkoituksia, muutama esimerkki käyttötarkoituksista on:

- `DANGER`: Parasta toiminnoille, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
- `DEFAULT`: Sopii sovelluksen toiminnoille, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetusten vaihtaminen.
- `PRIMARY`: Sopii pää "toimi"-painikkeeksi sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiseen sivuun.
- `SUCCESS`: Erinomainen visualisoimaan jonkin elementin onnistunutta suorittamista sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin päättämistä. Onnistumisteemaa voidaan ohjelmallisesti soveltaa, kun onnistunut toiminta on suoritettu.
- `WARNING`: Hyödyllinen viestimään, että käyttäjä on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jossa on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän merkittäviä kuin ne, jotka käyttäisivät Danger-teemaa.
- `GRAY`: Hyvä hienovaraisille toiminnoille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän lisäarvoa sivuille ja eivät ole osa päätoiminnallisuutta.
- `INFO`: Hyvä tarjoamaan lisäselvityksiä käyttäjälle.

<TableBuilder name="Icon" />
