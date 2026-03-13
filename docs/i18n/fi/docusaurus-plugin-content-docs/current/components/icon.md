---
title: Icon
sidebar_position: 55
_i18n_hash: 8350df59fb9ce335776bc0556861cda5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon`-komponentti näyttää ikoneita, jotka skaalautuvat mihin tahansa kokoon laadun heikkenemättä. Voit valita kolmestatoista sisäänrakennetusta ikonipoolista tai luoda omia. Ikonit toimivat visuaalisina vihjeinä navigaatioon ja toimintoihin, mikä vähentää tekstin tarvetta käyttöliittymässäsi.

<!-- INTRO_END -->

## Perusteet {#basics}

Jokainen `Icon` on suunniteltu skaalautuvaksi vektorigrafiikaksi (SVG), mikä tarkoittaa, että se voi helposti skaalautua mihin tahansa kokoon ilman, että selkeys tai laatu kärsii. Lisäksi `Icon`-komponentit ladataan tarpeen mukaan sisällönjakeluverkosta (CDN), mikä auttaa vähentämään viivettä ja parantamaan yleistä suorituskykyä.

Kun luot `Icon`-komponentin, sinun on määriteltävä tietty pooli ja itse ikonin nimi. Jotkut ikonit tarjoavat myös vaihtoehdon käyttää ääriviivoja tai täytettyä versiota [vaihtoehtojen](#variations) kautta.

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Tiesitkö?
Joissakin komponenteissa, kuten `PasswordField` ja `TimeField`, on sisäänrakennettuja ikoneita, jotka auttavat välittämään merkitystä loppukäyttäjille.
:::

### Poolit {#pools}

Ikonipooli on kokoelma yleisesti käytettyjä ikoneita, joka mahdollistaa helpon pääsyn ja käytön uudelleen. Käyttämällä ikoneita ikonipoolista voit varmistaa, että sovelluksesi ikonit ovat helposti tunnistettavia ja niillä on yhtenäinen tyyli. webforJ:n avulla voit valita kolmesta poolista tai toteuttaa mukautetun poolin. Jokaisella poolilla on laaja kokoelma avoimen lähdekoodin ikooneja, joita voi käyttää ilmaiseksi. webforJ:n käyttäminen antaa sinulle mahdollisuuden valita kolmesta poolista ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata ikoneita suoraan.

| Ikonipooli                                        | webforJ-luokka |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)               | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on osa Tabler-ikoneista.|    
| [Feather](https://feathericons.com/)             | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)   | `FontAwesomeIcon`   |

:::tip

Jos olet kiinnostunut luomaan oman ikonipoolin, katso [Mukautettujen poolien luominen](#creating-custom-pools).

:::

Kun olet valinnut sisältävät poolit sovelluksessasi, seuraava askel on määritellä ikonin nimi, jota haluat käyttää.

### Nimet {#names}

Inkludoi ikoni sovellukseesi, tarvitset vain ikonipoolin ja ikonin nimen. Selaa ikonipoolin verkkosivustoa etsimällä ikonia, jota haluat käyttää, ja käytä ikoninnimeä `create()`-metodin parametrina. Lisäksi voit luoda ikoneita kautta `FeatherIcon` ja `DwcIcon` -luokkien enumien, jolloin ne näkyvät koodin täydennyksessä.

```java
// Luo ikoni merkkijonon nimestä
Icon image = TablerIcon.create("image");
// Luo ikoni enumista
Icon image = FeatherIcon.IMAGE.create();
```

### Vaihtoehdot {#variations}

Voit personoida ikoneita vielä enemmän hyödyntämällä vaihtoehtoja. Tietyt ikonit antavat sinun valita ääriviiva- tai täytettyversion, mikä mahdollistaa tietyn ikoniversion korostamisen mieltymystesi mukaan. `FontAwesomeIcon`- ja `Tabler`-ikonit tarjoavat vaihtoehtoja.

#### `FontAwesomeIcon` vaihtoehdot {#fontawesomeicon-variations}

1. `REGULAR`: Ikonien ääriviivaversio. Tämä on oletus.
2. `SOLID`: Ikonien täytetty versio.
3. `BRAND`: Versio, jota käytetään brändi-ikoneiden yhteydessä.

#### `TablerIcon` vaihtoehdot {#tablericon-variations}

1. `OUTLINE`: Ikonien ääriviivaversio. Tämä on oletus.
2. `FILLED`: Ikonien täytetty versio.

```java
// Täytetty versio ikoni Font Awesome -katalogista
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava esimerkki havainnollistaa, miten käyttää ikoneita eri poolista, soveltaa vaihtoehtoja ja yhdistää ne saumattomasti komponentteihin.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Ikonien lisääminen komponentteihin {#adding-icons-to-components}

Integrate ikoneita komponentteihisi käyttämällä slotteja. Slottit tarjoavat joustavia vaihtoehtoja komponenttien hyödyllisyyden lisäämiseksi. On hyödyllistä lisätä `Icon`-komponentti, jotta käyttäjille selviää tarkka merkitys. Komponentit, jotka toteuttavat `HasPrefixAndSuffix`-rajapinnan, voivat sisältää `Icon`- tai muita voimassa olevia komponentteja. Lisätyt komponentit voidaan sijoittaa `prefix`- ja `suffix`-slotteihin ja ne voivat parantaa sekä kokonaisdesignia että käyttäjäkokemusta.

Käyttämällä `prefix`- ja `suffix`-slotteja voit päättää, haluatko, että ikoni tulee ennen vai jälkeen tekstin `setPrefixComponent()` ja `setSuffixComponent()` -menetelmien avulla.

Päätöksenteko siitä, sijoitetaanko ikoni ennen vai jälkeen komponentin tekstin, riippuu suuresti tarkoituksesta ja suunnittelukontekstista.

### Ikonin sijoittaminen: ennen VS jälkeen {#icon-placement-before-vs-after}

Ikonit, jotka sijoitetaan komponentin tekstin eteen, auttavat käyttäjiä ymmärtämään komponentin ensisijaisen toiminnan tai tarkoituksen nopeasti, erityisesti universaalisti tunnistettavien ikonien, kuten tallennusikonin, osalta. Ikonit ennen komponentin tekstiä tarjoavat loogisen käsittelyjärjestyksen, ohjaten käyttäjiä luonnollisesti haluttuun toimintaan, mikä on hyödyllistä painikkeille, joiden ensisijainen toiminto on välitön toiminta.

Toisaalta, ikonien sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoille, jotka tarjoavat lisäkontekstia tai vaihtoehtoja, lisäten selkeyttä ja vihjeitä navigointiin. Ikonit komponentin tekstin jälkeen ovat ihanteellisia komponenteille, jotka tarjoavat joko lisätietoja tai ohjaavat käyttäjiä suuntaan.

Lopulta johdonmukaisuus on avain. Kun valitset tyylin, pidä se yllä koko sivustollasi yhtenäisen ja käyttäjäystävällisen suunnittelun varmistamiseksi.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Mukautettujen poolien luominen {#creating-custom-pools}

Ajanvieton lisäksi voit luoda mukautetun poolin, jota voidaan käyttää mukautetuille logoille tai avatar-ikkunoille. Mukautettu ikonipooli voidaan tallentaa keskitettyyn hakemistoon tai resursseihin (kontekstiin), mikä helpottaa ikonien hallintaa. Mukautetun poolin omistaminen tekee sovellusten kehittämisestä johdonmukaisempaa ja vähentää ylläpitoa eri komponenttien ja moduulien välillä.

Mukautetut poolit voidaan luoda kansiosta, joka sisältää SVG-kuvia ja käyttämällä `IconPoolBuilder` -luokkaa. Tämän jälkeen voit valita mukautetun poolisi nimen ja käyttää sitä SVG-tiedostojen nimien kanssa luodaksesi mukautettuja ikonikomponentteja.

```java
// Luodaan mukautettu pooli nimeltä "app-pool", joka sisältää kuvamannekeja logolle ja avatarille.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet ikonit yhtä leveiksi ja korkeiksi, sillä `Icon`-komponentit on suunniteltu täyttämään neliömäinen tila.
:::

### Mukautettu poolitehdas {#custom-pool-factory}

Voit myös luoda tehtaaluokan mukautettua poolia varten webforJ:ssä samalla tavalla kuin `FeatherIcon`. Tämä mahdollistaa ikonivarojen luomisen ja hallinnan tietyssä poolissa ja mahdollistaa koodin täydennyksen. Jokainen ikoni voidaan instansioida `create()`-menetelmän kautta, joka palauttaa `Icon`-komponentin. Tehtaaluokan tulisi tarjota poolikohtaisia metatietoja, kuten poolin nimi ja ikonin tunnus, muotoiltuna kuvan tiedostonimeksi. Tämä suunnittelu mahdollistaa helpon ja standardoidun pääsyn mukautetun poolin ikoniin enum-vakioiden avulla, tukien ikoni-hallinnan skaalautuvuutta ja ylläpidettävyyttä.

```java
/// Luodaan mukautettu poolitehdas "app-pool"
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return ikoni poolin nimi
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

Seuraava koodi näyttää kaksi erilaista tapaa käyttää mukautettua poolia.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Luo ikoni käyttäen mukautetun poolin nimiä ja kuvannota
Icon customLogo = new Icon("logo", "app-pool");

// Luo ikoni käyttämällä aikaisemmassa fragmentissa määritettyä mukautettua poolitehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Ikonipainikkeet {#icon-buttons}
`Icon`-komponentti ei ole valittavissa, mutta toiminnoille, joita on parasta edustaa vain ikonilla, kuten ilmoitukset tai varoitukset, voit käyttää `IconButton`-komponenttia.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
  });
```

## Parhaat käytännöt

- **Esteettömyys:** Käytä työkaluvihjettä tai etikettiä ikoneissa, jotta sovelluksesi on esteetön näkövammaisille käyttäjille, jotka tukeutuvat näytönlukuohjelmiin.
- **Vältä epäselvyyttä:** Vältä ikoneiden käyttöä, jos merkitys ei ole selvä tai laajasti ymmärretty. Jos käyttäjien on arvattava, mitä ikoni edustaa, se kumoaa tarkoituksen.
- **Käytä ikoneita säästeliäästi:** Liian monta ikonia voi ylikuormittaa käyttäjiä, joten käytä ikoneita vain, kun ne lisäävät selvyyttä tai vähentävät monimutkaisuutta.

## Tyylittely
Ikoni periä suoran vanhemman komponenttinsa teeman, mutta voit ylittää tämän soveltamalla teemaa suoraan `Icon`-komponenttiin.

### Teemat
Ikonikomponentit tulevat seitsemällä erillisellä teemalla valmiina nopeaan tyylittelyyn ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa ikoneihin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja yhdenmukaisen tavan mukauttaa ikonien ulkoasua koko sovelluksessa.

Vaikka kaikilla eri teemoilla on monia käyttötarkoituksia, joitakin esimerkkejä käyttötapauksista ovat:

- `DANGER`: Paras toimille, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
- `DEFAULT`: Sopii sovelluksessa käytettäville toiminnoille, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen säätäminen.
- `PRIMARY`: Sopii pää "toimenpiteeseen" sivulla, kuten rekisteröitymiseen, muutosten tallentamiseen tai siirtymiseen toiselle sivulle.
- `SUCCESS`: Erinomainen visualisoimaan ohjelmaelementin onnistumista, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin loppuun saattamista. Menestys-teema voidaan ohjelmallisesti soveltaa kerran onnistuneen toiminnan jälkeen.
- `WARNING`: Hyödyllinen viestin osoittamiseksi siihen, että käyttäjä on aikomassa suorittaa mahdollisesti riskialttiita toimintoja, kuten navigoiminen pois sivulta, jolla ei ole tallennettuja muutoksia. Nämä toiminnot ovat usein vähemmän vaikuttavia kuin ne, joissa käytetään Danger-teemaa.
- `GRAY`: Hyvä hienovaraisille toiminnoille, kuten vähäisille asetuksille tai toiminnoille, jotka ovat enemmän täydentäviä sivulle eivätkä kuulu päätoimintoihin.
- `INFO`: Hyvä antamaan lisäselvityksiä käyttäjälle.

<TableBuilder name="Icon" />
