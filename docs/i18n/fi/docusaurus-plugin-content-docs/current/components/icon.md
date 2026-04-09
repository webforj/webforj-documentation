---
title: Icon
sidebar_position: 55
_i18n_hash: 5c32d2def53818005b15e22290fb3d52
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon`-komponentti näyttää kuvakkeita, jotka skaalautuvat mihin tahansa kokoon ilman laadun menettämistä. Voit valita kolmesta sisäänrakennetusta kuvakekokoelmasta tai luoda omia. Kuvakkeet toimivat visuaalisina vihjeinä navigoinnille ja toimille, vähentäen tekstilappusten tarvetta käyttöliittymässäsi.

<!-- INTRO_END -->

## Perusteet {#basics}

Jokainen `Icon` on suunniteltu skaalautuvaksi vektorigrafiikaksi (SVG), mikä tarkoittaa, että se voi helposti skaalautua mihin tahansa kokoon ilman selkeyden tai laadun heikkenemistä. Lisäksi `Icon`-komponentit ladattavat tarvittaessa sisällönjakeluverkosta (CDN), mikä auttaa vähentämään latenssia ja parantamaan yleistä suorituskykyä.

Kun luot `Icon`-komponenttia, sinun on määritettävä tietty kokoelma ja kuvakkeen nimi. Jotkut kuvakkeet tarjoavat myös mahdollisuuden valita ääriviivattoman tai täytetyn version [muunnosten](#variations) kautta.

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Tiesitkö?
Joillakin komponenteilla, kuten `PasswordField` ja `TimeField`, on sisäänrakennettuja kuvakkeita, jotka auttavat välittämään merkitystä loppukäyttäjille.
:::

### Kokoelmat {#pools}

Kuvakekokoelma on kokoelma yleisesti käytettyjä kuvakkeita, joka mahdollistaa helpon pääsyn ja uudelleenkäytön. Käyttämällä kuvakkeita kokoelmasta voit varmistaa, että sovelluksesi kuvakkeet ovat tunnistettavia ja jakavat yhdenmukaisen tyylin. 
webforJ:n avulla voit valita kolmesta kokoelmasta tai toteuttaa mukautetun kokoelman. Jokaisessa kokoelmassa on laaja valikoima avoimen lähdekoodin kuvakkeita, joita voit käyttää vapaasti. 
webforJ:n käyttö antaa sinulle joustavuutta valita kolmesta kokoelmasta ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata kuvakkeita suoraan.

| Kuvakekokoelma                               | webforJ-luokka |
| -------------------------------------------- | --------------- |
| [Tabler](https://tabler-icons.io/)          | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on osa Tabler-kokoelman kuvakkeista.|    
| [Feather](https://feathericons.com/)        | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)| `FontAwesomeIcon`   |

:::tip

Jos olet kiinnostunut oman kuvakekokoelman luomisesta, katso [Oman kokoelman luominen](#creating-custom-pools).

:::

Kun olet valinnut sisällytettävät kokoelmat sovellukseesi, seuraava vaihe on määrittää haluamasi kuvakkeen nimi.

### Nimet {#names}

Sisällyttääksesi kuvakkeen sovellukseesi tarvitset vain kuvakekokoelman ja kuvakkeen nimen. Selaa kuvakekokoelman verkkosivustoa etsimällä käytettävää kuvaketta, ja käytä kuvakkeen nimeä `create()`-metodin parametritiettönä. Lisäksi voit luoda kuvakkeita enumien kautta `FeatherIcon` ja `DwcIcon` -luokille, jolloin ne ilmestyvät koodin täydennykseen.

```java
// Luo kuvake merkkijonon nimestä
Icon image = TablerIcon.create("image");
// Luo kuvake enumista
Icon image = FeatherIcon.IMAGE.create();
```

### Muunnokset {#variations}

Voit henkilökohtaistaa kuvakkeita vielä enemmän hyödyntämällä muunnoksia. Tietyt kuvakkeet antavat mahdollisuuden valita ääriviivaton tai täytetty versio, jolloin voit korostaa tiettyä kuvaketta mieltymystesi mukaan. `FontAwesomeIcon` ja `Tabler`-kuvakkeet tarjoavat muunnoksia.

#### `FontAwesomeIcon` muunnokset {#fontawesomeicon-variations}

1. `REGULAR`: Kuvakkeiden ääriviivallinen muunnos. Tämä on oletus.
2. `SOLID`: Kuvakkeiden täytetty muunnos.
3. `BRAND`: Muunnos, kun käytät brändien kuvakkeita.

#### `TablerIcon` muunnokset {#tablericon-variations}

1. `OUTLINE`: Kuvakkeiden ääriviivallinen muunnos. Tämä on oletus.
2. `FILLED`: Kuvakkeiden täytetty muunnos.

```java
// Täytetty muunnos Font Awesome -kuvakkeesta
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava demo havainnollistaa, kuinka käyttää kuvakkeita eri kokoelmista, soveltaa muunnoksia ja integroida niitä saumattomasti komponentteihin.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Kuvakkeiden lisääminen komponentteihin {#adding-icons-to-components}

Integroi kuvakkeita komponentteihisi käyttäen slotteja. Slotit tarjoavat joustavia vaihtoehtoja tehdä komponenteista hyödyllisempiä. On hyödyllistä lisätä `Icon` komponenttiin, jotta käyttäjille voidaan täsmentää tarkoitettua merkitystä. Komponentit, jotka toteuttavat `HasPrefixAndSuffix`-rajapinnan, voivat sisältää `Icon`- tai muita voimassa olevia komponentteja. Lisätyt komponentit voidaan asettaa `prefix`- ja `suffix`-slotteihin, ja ne voivat parantaa sekä yleistä suunnittelua että käyttäjäkokemusta.

Käyttämällä `prefix`- ja `suffix`-slotteja voit päättää, haluatko ikonisi tekstin edelle tai taakse käyttämällä `setPrefixComponent()` ja `setSuffixComponent()` -metodeja.

Päätös siitä, sijoitatko kuvakkeen ennen vai jälkeen tekstin komponentissa, riippuu suuresti tarkoituksesta ja suunnittelukontekstista.

### Kuvakkeiden sijoittelu: ennen VS jälkeen {#icon-placement-before-vs-after}

Kuvakkeet, jotka sijaitsevat komponentin tekstin edessä, auttavat käyttäjiä nopeasti ymmärtämään komponentin ensisijaisen toiminnan tai tarkoituksen, erityisesti yleisesti tunnistettavien kuvakkeiden, kuten tallennuskuvakkeen, osalta. Kuvakkeet ennen komponentin tekstiä tarjoavat loogisen käsittelyjärjestyksen, ohjaten käyttäjiä luonnollisesti tarkoitettuihin toimintoihin, mikä on hyödyllistä painikkeille, joiden ensisijainen tehtävä on välitön toiminta.

Toisaalta kuvakkeiden sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoissa, jotka tarjoavat lisäkontekstia tai vaihtoehtoja, parantaen selkeyttä ja vihjeitä navigoinnille. Kuvakkeet komponentin tekstin jälkeen ovat ihanteellisia komponenteille, jotka tarjoavat joko lisätietoa tai ohjaavat käyttäjiä suuntaa antavalla tavalla.

Lopulta johdonmukaisuus on avainasemassa. Kun valitset tyylin, pidä se yhtenäisenä koko sivustollasi, jotta saat aikaan yhtenäisen ja käyttäjäystävällisen suunnittelun.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Oman kokoelman luominen {#creating-custom-pools}

Olemassa olevien kuvakekokoelmien ohella voit myös luoda mukautetun kokoelman, jota voidaan käyttää mukautetuissa logoissa tai avatarissa. Mukautettu kuvakekokoelma voidaan tallentaa keskitettyyn hakemistoon tai resursseihin (konteksti), mikä helpottaa kuvakkeiden hallintaprosessia. Mukautetun kokoelman käyttö tekee sovellusten luomisesta johdonmukaisempaa ja vähentää ylläpitoa eri komponenttien ja moduulien välillä.

Mukautettuja kokoelmia voidaan luoda kansiosta, joka sisältää SVG-kuvia, ja käyttämällä `IconPoolBuilder`-luokkaa. Tämän jälkeen voit valita mukautetun kokoelmasi nimen ja käyttää sitä SVG-tiedostojen nimien kanssa luodaksesi mukautettuja kuvakekomponentteja.

```java
// Luodaan mukautettu kokoelma nimeltään "app-pool", joka sisältää kuvia logosta ja avatarista.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet kuvakkeet yhtä leveäksi ja korkeaksi, sillä `Icon`-komponentit on suunniteltu viemään neliömäinen tila.
:::

### Mukautetun kokoelman tehdas {#custom-pool-factory}

Voit myös luoda tehdasluokan mukautetulle kokoelmalle webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa kuvakeresurssien luomisen ja hallinnoimisen tietyssä kokoelmassa ja mahdollistaa koodin täydennyksen. Jokainen kuvake voidaan instansioida `create()`-metodin kautta, joka palauttaa `Icon`-objektin. tehdasluokan tulisi tarjota kokoelma-spesifistä metatietoa, kuten kokoelman nimi ja kuvakkeen tunniste, muotoiltuna kuvan tiedostonimelle. Tämä malli mahdollistaa helpon, standardoidun pääsyn kuvakeassets -resursseihin mukautetun kokoelman avulla käyttämällä enum-konstanteita, jotka tukevat skaalautuvuutta ja ylläpidettävyyttä kuvakkeiden hallinnassa.

```java
// Luodaan mukautetun kokoelman tehdas "app-pool"
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return kokoelman nimi kuvakkeille
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return kuvakkeen nimi
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Seuraava koodinpätkä näyttää kaksi eri tapaa käyttää mukautettua kokoelmaa.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Luo kuvake mukautetun kokoelman ja kuvatiedoston nimien kautta
Icon customLogo = new Icon("logo", "app-pool");

// Luo kuvake käyttäen edellisen pätkän mukautetun kokoelman tehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Kuvakepainikkeet {#icon-buttons}
`Icon`-komponenttia ei voida valita, mutta toiminnoille, jotka on parasta esittää vain kuvakkeilla, kuten ilmoitukset tai hälytykset, voit käyttää `IconButton`-komponenttia.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
});
```

## Parhaat käytännöt

- **Saavutettavuus:** Käytä työkaluvihjettä tai nimeä kuvakkeissa, jotta sovelluksesi olisi saavutettavissa näkövammaisille käyttäjille, jotka luottavat ruudunlukijoihin.
- **Vältä epäselvyyksiä:** Vältä kuvakkeiden käyttöä, jos niiden merkitys ei ole selvä tai yleisesti tunnettu. Jos käyttäjien on arvattava, mitä kuvake tarkoittaa, se kumoaa tarkoituksen.
- **Käytä kuvakkeita säästeliäästi:** Liian monet kuvakkeet voivat ylikuormittaa käyttäjiä, joten käytä kuvakkeita vain silloin, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Tyylittely
`Icon`-komponentti periä suoraan vanhemman komponenttinsa teeman, mutta voit ohittaa tämän soveltamalla teemaa `Icon`-komponenttiin suoraan.

### Teemat
Kuvakekomponentit sisältävät seitsemän erilaista teemaa, jotka on valmiina nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennaltamääriteltyjä tyylejä, jotka voidaan soveltaa kuvakkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja yhdenmukaisen tavan mukauttaa kuvakkeiden ulkoasua sovelluksessa.

Vaikka käyttötilanteita on monia eri teemoille, joitakin esimerkkejä ovat:

- `DANGER`: Paras toimille, joilla on vakavia seurauksia, kuten täytettyjen tietojen poistaminen tai tilin/tietojen lopullinen poistaminen. 
- `DEFAULT`: Sopii toimille sovelluksessa, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen vaihtaminen.
- `PRIMARY`: Sopii päätoiminnalle sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle. 
- `SUCCESS`: Erinomainen kuvastamaan onnistuneen toiminnan esittämistä sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisen loppuunsaattamista. Onnistuneita teemoja voidaan ohjelmallisesti soveltaa onnistuneen toiminnan jälkeen.
- `WARNING`: Hyödyllinen merkkaamaan, että käyttäjä on suorittamassa potentiaalisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jolla on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän vaikutuksellisia kuin ne, missä käytetään Danger-teemaa.
- `GRAY`: Hyvälemat pienille toimille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän täydentäviä sivulle eikä osa päätoimintoa.
- `INFO`: Hyvä tarjoamaan käyttäjälle lisäselvitystä.

<TableBuilder name={['Icon', 'IconButton']} />
