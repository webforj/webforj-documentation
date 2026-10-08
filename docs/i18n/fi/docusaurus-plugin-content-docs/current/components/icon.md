---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: c526ee2878756d5dd13fa2972dfef56e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon` komponentti näyttää kuvakkeita, jotka voidaan skaalata mihin tahansa kokoon menettämättä laatua. Voit valita kolmesta sisäänrakennetusta kuvakekokoelmasta tai luoda omia. Kuvakkeet toimivat visuaalisina vihjeinä navigointia ja toimintoja varten, vähentäen tekstimäisten nimilappujen tarvetta käyttöliittymässäsi.

Jokainen `Icon` renderöidään skaalautuvana vektorikuvana (SVG), joka ladataan tarpeen mukaan sisällönjakeluverkosta (CDN) latenssin pitäminen alhaisena. Luodaksesi yhden, valitse kuvakekokoelma ja kuvakkeen nimi. Jotkut kuvakkeet tarjoavat myös valinnan ääriviivakuvakkeen tai täytetyn version välillä [variations](#variations) kautta.

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Tiesitkö?
Jotkin komponentit, kuten `PasswordField` ja `TimeField`, sisältävät sisäänrakennettuja kuvakkeita auttaakseen välittämään merkitystä loppukäyttäjille.
:::

## Pools {#pools}

Kuvakekokoelma on kokoelma yleisesti käytettyjä kuvakkeita, joka mahdollistaa helpon pääsyn ja uudelleenkäytön. Käytettäessä kuvakkeita kuvakekokoelmasta voit varmistaa, että sovelluksesi kuvakkeet ovat tunnistettavia ja jakavat yhdenmukaisen tyylin. 
WebforJ:n käyttäminen antaa sinun valita kolmesta kokoelmasta tai toteuttaa mukautetun kokoelman. 
Jokaisella kokoelmalla on laaja kokoelma avoimen lähdekoodin kuvakkeita, joita on ilmainen käyttää. 
WebforJ:n käyttö antaa sinulle joustavuutta valita kolmesta kokoelmasta ja käyttää niitä ainutlaatuisina luokkina ilman, että tarvitsee ladata mitään kuvakkeista suoraan.

| Kuvakekokoelma                                         | WebforJ-luokka |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on alikokoelma Tablerin kuvakkeista.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Jos olet kiinnostunut luomaan oman kuvakekokoelman, katso [Creating custom pools](#creating-custom-pools).

:::

Kun olet valinnut kokoelman tai kokoelmat, jotka haluat sisällyttää sovellukseesi, seuraava vaihe on määrittää käytettävän kuvakkeen nimi.

## Names {#names}

Sisällyttääksesi kuvakkeen sovellukseesi, tarvitset vain kuvakekokoelman ja kuvakkeen nimen. Selaa kuvakekokoelman verkkosivustoa sen kuvakkeen löytämiseksi, jota haluat käyttää, ja käytä kuvakkeen nimeä `create()`-menetelmän parametreina. 
Lisäksi voit luoda kuvakkeita enumien kautta `FeatherIcon` ja `DwcIcon` -luokille, mikä mahdollistaa niiden ilmestymisen koodin täydentämiseen.

```java
// Luo kuvake merkkijonon nimestä
Icon image = TablerIcon.create("image");
// Luo kuvake enumeroinnista
Icon image = FeatherIcon.IMAGE.create();
```

## Variations {#variations}

Voit räätälöidä kuvakkeita entistä enemmän hyödyntämällä variaatioita. 
Tietyt kuvakkeet antavat sinulle mahdollisuuden valita ääriviivakuvakkeen tai täytetyn version, mikä mahdollistaa tietyn kuvakkeen korostamisen mieltymystesi mukaan. `FontAwesomeIcon` ja `Tabler` kuvakkeet tarjoavat variaatioita.

### `FontAwesomeIcon` variaatiot {#fontawesomeicon-variations}

1. `REGULAR`: Kuvakkeiden ääriviivavariaatio. Tämä on oletus.
2. `SOLID`: Kuvakkeiden täytetty variaatio.
3. `BRAND`: Variaatio, jota käytetään, kun käytät brändien kuvakkeita.

### `TablerIcon` variaatiot {#tablericon-variations}

1. `OUTLINE`: Kuvakkeiden ääriviivavariaatio. Tämä on oletus.
2. `FILLED`: Kuvakkeiden täytetty variaatio.

```java
// Täytetty variaatio kuvakkeesta Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava demo havainnollistaa, kuinka käyttää kuvakkeita eri kokoelmista, soveltaa variaatioita ja integrointia komponentteihin.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Adding icons to components {#adding-icons-to-components}

Integroi kuvakkeita komponentteihisi käyttämällä slotteja. Slotit tarjoavat joustavia vaihtoehtoja komponenttien hyödyllisyyden parantamiseksi. On hyödyllistä lisätä `Icon` komponenttiin selventääkseen sen merkitystä käyttäjille. 
Komponentit, jotka toteuttavat `HasPrefixAndSuffix` -rajapinnan, voivat sisältää `Icon` tai muita kelvollisia komponentteja. Lisätyt komponentit voidaan sijoittaa `prefix` ja `suffix` slotteihin, ja ne voivat parantaa sekä kokonaisdesignia että käyttäjäkokemusta.

Käyttäen `prefix` ja `suffix` slotteja, voit määrittää, haluatko kuvakkeen ennen vai jälkeen tekstin `setPrefixComponent()` ja `setSuffixComponent()` -menetelmien avulla.

Päätettäessä, sijoitetaanko kuvake ennen vai jälkeen komponentin tekstin, riippuu suuresti tarkoituksesta ja suunnittelukontekstista.

### Kuvakkeen sijoittaminen: ennen VS jälkeen {#icon-placement-before-vs-after}

Komponentin tekstin edellä sijaitsevat kuvakkeet auttavat käyttäjiä ymmärtämään komponentin ensisijaisen toiminnan tai tarkoituksen nopeasti, erityisesti yleisesti tunnistettavien kuvakkeiden, kuten tallenna-ikon, kohdalla. 
Kuvakkeet ennen komponentin tekstiä tarjoavat loogisen käsittelyjärjestyksen, ohjaten käyttäjiä luonnollisesti haluttuun toimintaan, mikä on hyödyllistä painikkeilla, joiden ensisijainen tehtävä on välitön toiminta.

Toisaalta, kuvakkeiden sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoille, jotka tarjoavat lisäkontekstia tai vaihtoehtoja, parantaen selkeyttä ja vihjeitä navigointiin. 
Kuvakkeet komponentin tekstin jälkeen ovat ihanteellisia komponentteihin, jotka tarjoavat joko täydentävää tietoa tai ohjaavat käyttäjiä suuntaan.

Lopulta johdonmukaisuus on avain. Kun olet valinnut tyylin, ylläpidä sitä koko sivustollasi johdonmukaisen ja käyttäjäystävällisen suunnittelun varmistamiseksi.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Creating custom pools {#creating-custom-pools}

Nykyisten kuvakekokoelmien hyödyntämisen lisäksi voit luoda mukautetun kokoelman, jota voidaan käyttää mukautetuissa logoissa tai avatarissa. 
Mukautettu kuvakekokoelma voidaan tallentaa keskitettyyn hakemistoon tai resurssikansioon (konteksti), mikä yksinkertaistaa kuvakkeiden hallintaprosessia. 
Mukautetun kokoelman omistaminen tekee sovelluksen luomisesta johdonmukaisempaa ja vähentää ylläpitoa eri komponenttien ja moduulien välillä.

Mukautetut kokoelmat voidaan luoda hakemistosta, joka sisältää SVG-kuvia, ja käyttämällä `IconPoolBuilder` -luokkaa. Tämän jälkeen voit valita mukautetun kokoelmasi nimen ja käyttää sitä SVG-tiedostojen nimien kanssa mukautettujen kuvakekomponenttien luomiseksi.

```java
// Luodaan mukautettu kokoelma nimeltä "app-pool", jossa on kuvia logosta ja avatarista.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet kuvakkeet, joissa on yhtä suuri leveys ja korkeus, sillä `Icon` komponentit on suunniteltu vievän neliömäistä tilaa.
:::

### Custom pool factory {#custom-pool-factory}

Voit myös luoda tehtaaluokan mukautetulle kokoelmalle webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa kuvakevarojen luomisen ja hallinnan määritellyssä kokoelmassa sekä koodin täydentämisen. 
Jokainen kuvake voidaan instansioida `create()`-menetelmällä, joka palauttaa `Icon`. Tehtaaluokan tulisi tarjota kokoelmaan liittyvää metadataa, kuten kokoelman nimi ja kuvakkeen tunniste, muotoiltuna kuvan tiedostonimeksi. 
Tämä suunnittelu mahdollistaa helpon, standardoidun pääsyn kuvakevaroihin mukautetusta kokoelmasta käyttämällä enum-konstantteja, tukien skaalautuvuutta ja ylläpidettävyyttä kuvakkeiden hallinnassa.

```java
/// Luodaan mukautettu kokoelmatehdas app-poolille
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

// Luo kuvake käyttämällä mukautetun kokoelman ja kuva tiedostonimi
Icon customLogo = new Icon("logo", "app-pool");

// Luo kuvake käyttämällä mukautetun kokoelmatehtaan aiemmasta pätkästä
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon buttons {#icon-buttons}
`Icon` komponentti ei ole valittavissa, mutta toiminnoille, jotka parhaiten esitetään pelkästään kuvakkeella, kuten ilmoitukset tai hälytykset, voit käyttää `IconButton` -komponenttia.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
  });
```

## Best practices

- **Saavutettavuus:** Käytä työkaluvihjettä tai etikettiä kuvakkeilla, jotta sovelluksesi on saavutettavissa näkövammaisille käyttäjille, jotka riippuvat näytönlukuohjelmista.
- **Vältä epäselvyyksiä:** Vältä kuvakkeiden käyttöä, jos merkitys ei ole selkeä tai yleisesti ymmärretty. Jos käyttäjien on arvattava, mitä kuvake edustaa, se kumoaa tarkoituksen.
- **Käytä kuvakkeita säästeliäästi:** Liian monet kuvakkeet voivat ylittää käyttäjiä, joten käytä kuvakkeita vain, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Styling
Kuvake perii suoran vanhempikomponenttinsa teeman, mutta voit ohittaa tämän soveltamalla teemaa `Icon` suoraan.

### Themes
Kuvakekomponenteilla on seitsemän erilaista teemaa sisäänrakennettuna nopeaa muotoilua varten ilman CSS:n käyttöä. Nämä teemat ovat ennakkoon määriteltyjä tyylejä, joita voidaan soveltaa kuvakkeisiin muuttaakseen niiden ulkonäköä ja visuaalista esitystä. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa kuvakkeiden ulkoasua sovelluksen sisällä.

Vaikka jokaisella eri teemalla on monia käyttötapauksia, joitakin esimerkki käyttöjä ovat:

- `DANGER`: Paras toiminnoille, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
- `DEFAULT`: Sopii toimille koko sovelluksessa, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen vaihtaminen.
- `PRIMARY`: Sopii pää "call-to-action" sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
- `SUCCESS`: Erinomainen visuaalisena merkkinä jonkin elementin onnistuneesta suorittamisesta sovelluksessa, kuten lomakkeen lähettämisestä tai rekisteröitymisprosessin suorittamisesta. Onnistumisteemaa voidaan ohjelmallisesti soveltaa sen jälkeen, kun onnistunut toiminta on suoritettu.
- `WARNING`: Hyödyllinen ilmoittamaan, että käyttäjä on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jolla on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikuttavia kuin ne, joille käytettäisiin Danger-teemaa.
- `GRAY`: Hyvä hienovaraisille toiminnoille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän sivuversioita sivusta, eivätkä osa päätoiminnallisuutta.
- `INFO`: Hyvä tarjoamaan käyttäjälle lisäinformaatioita selventämiseksi.

<TableBuilder name={['Icon', 'IconButton']} />
