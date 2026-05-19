---
title: Icon
sidebar_position: 55
_i18n_hash: ae46080226d89087113b901c748f0942
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon`-komponentti näyttää ikoneita, jotka skaalaavat mihin tahansa kokoon ilman laadun hävikkiä. Voit valita kolmesta sisäänrakennetusta ikonipoolista tai luoda omia. Ikonit palvelevat visuaalisina vihjeinä navigoinnille ja toiminnoille, vähentäen tekstilabelien tarvetta käyttöliittymässäsi.

<!-- INTRO_END -->

## Perusteet {#basics}

Jokainen `Icon` on suunniteltu skaalaavaksi vektorikuvaksi (SVG), mikä tarkoittaa, että se voi helposti skaalaantua mihin tahansa kokoon menettämättä kirkkautta tai laatua. Lisäksi `Icon`-komponentit ladataan tarpeen mukaan sisältötoimitusverkon (CDN) kautta, mikä auttaa vähentämään viivettä ja parantamaan yleistä suorituskykyä.

Kun luot `Icon`-komponenttia, sinun on tunnistettava tietty pooli ja ikonin nimi. Jotkut ikonit tarjoavat myös valinnan viivan tai täytetyn version välillä [variations](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Tiesitkö?
Jotkut komponentit, kuten `PasswordField` ja `TimeField`, sisältävät sisäänrakennettuja ikoneita, jotka auttavat välittämään merkitystä loppukäyttäjille.
:::

### Poolit {#pools}

Ikonipooli on kokoelma yleisesti käytettyjä ikoneita, joka mahdollistaa helpon pääsyn ja uudelleenkäytön. Käyttämällä ikoneita ikonipoolista voit varmistaa, että sovelluksesi ikonit ovat tunnistettavia ja jakavat johdonmukaisen tyylin. webforJ:n käyttö mahdollistaa kolmonen poolin valinnan tai mukautetun poolin toteuttamisen. Jokaisella poolilla on laaja kokoelma avoimen lähdekoodin ikoneita, jotka ovat ilmaisia käytettäväksi. webforJ:n käyttö antaa sinulle joustavuutta valita kolme poolia ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata ikoneita suoraan.

| Ikonipooli                                        | webforJ Luokka |
| --------                                         | ------- |
| [Tabler](https://tabler-icons.io/)               | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on osa Tabler-ikoneista.|    
| [Feather](https://feathericons.com/)             | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)   | `FontAwesomeIcon`   |

:::tip

Jos olet kiinnostunut oman ikonipoolin luomisesta, katso [Oman poolin luominen](#creating-custom-pools).

:::

Kun olet valinnut poolin tai poolit, joita haluat käyttää sovelluksessasi, seuraava vaihe on määrittää sen ikonipoolin nimi, jota haluat käyttää.

### Nimikkeet {#names}

Sisällyttääksesi ikonin sovellukseesi, tarvitset vain ikonipoolin ja ikonin nimen. Selaa ikonipoolin verkkosivustoa etsimään käyttämääsi ikonia ja käytä ikonin nimeä `create()`-metodin parametrina. Voit myös luoda ikoneita enumien kautta `FeatherIcon`- ja `DwcIcon`-luokissa, mikä mahdollistaa niiden näkymisen koodin täydentämisessä.

```java
// Luo ikoni merkkijonon nimestä
Icon image = TablerIcon.create("image");
// Luo ikoni enumista
Icon image = FeatherIcon.IMAGE.create();
```

### Vaihtoehdot {#variations}

Voit personoida ikoneita vielä enemmän hyödyntämällä vaihtoehtoja. Tietyt ikonit mahdollistavat valinnan viivan tai täytetyn version välillä, mikä antaa sinulle mahdollisuuden korostaa tiettyä ikonia mieltymystesi mukaan. `FontAwesomeIcon`- ja `Tabler`-ikonit tarjoavat vaihtoehtoja.

#### `FontAwesomeIcon` vaihtoehdot {#fontawesomeicon-variations}

1. `REGULAR`: Ikonien viivaversio. Tämä on oletus.
2. `SOLID`: Ikonien täytetty versio.
3. `BRAND`: Versio, kun käytät brändien ikoneita.

#### `TablerIcon` vaihtoehdot {#tablericon-variations}

1. `OUTLINE`: Ikonien viivaversio. Tämä on oletus.
2. `FILLED`: Ikonien täytetty versio.

```java
// Täytetty versio ikonista Font Awesamista
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava esittely näyttää, miten käytetään ikoneita eri pooleista, sovelletaan vaihtoehtoja ja integroidaan ne saumattomasti komponentteihin.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ikonien lisääminen komponentteihin {#adding-icons-to-components}

Integroi ikoneita komponentteihisi käyttämällä slotteja. Slotit tarjoavat joustavia vaihtoehtoja, jotka tekevät komponenteista hyödyllisempia. Ikonin lisääminen komponenttiin voi selkeyttää aiottua merkitystä käyttäjille. `HasPrefixAndSuffix`-rajapintaa toteuttavat komponentit voivat sisältää `Icon`- tai muita voimassa olevia komponentteja. Lisäkomponentit voidaan sijoittaa `prefix`- ja `suffix`-sloteihin, ja ne voivat parantaa sekä yleistä muotoilua että käyttäjäkokemusta.

Käyttämällä `prefix`- ja `suffix`-slotteja voit päättää, haluatko ikonin ennen vai jälkeen tekstin `setPrefixComponent()`- ja `setSuffixComponent()`-metodien avulla.

Päätös siitä, sijoitetaanko ikoni ennen vai jälkeen komponentin tekstin, riippuu suurelta osin tarkoituksesta ja suunnittelukontekstista.

### Ikonin sijoittaminen: ennen VS jälkeen {#icon-placement-before-vs-after}

Ikonit, jotka sijoitetaan ennen komponentin tekstiä, auttavat käyttäjiä ymmärtämään nopeasti komponentin ensisijaisen toiminnon tai tarkoituksen, erityisesti yleisesti tunnistettavien ikonien, kuten tallenna-ikonin, osalta. Ikonit ennen komponentin tekstiä tarjoavat loogisen prosessointijärjestyksen, ohjaten käyttäjiä luonnollisesti aiottuun toimintaan, mikä on hyödyllistä painikkeissa, joiden ensisijainen tehtävä on välitön toiminta.

Toisaalta, sijoittamalla ikonit komponentin tekstin jälkeen, voit parantaa kontekstia tai vaihtoehtoja antavien toimintojen selkeyttä. Ikonit komponentin tekstin jälkeen ovat ihanteellisia komponenteille, jotka tarjoavat lisätietoa tai ohjaavat käyttäjiä suuntaamalla.

Loppujen lopuksi johdonmukaisuus on avainasemassa. Kun olet valinnut tyylin, ylläpidä sitä koko sivustollasi yhtenäisen ja käyttäjäystävällisen muotoilun saavuttamiseksi.
   
<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Oman poolin luominen {#creating-custom-pools}

Lisäksi olemassa olevien ikonikokoelmien hyödyntämisen lisäksi voit luoda mukautetun poolin, jota voidaan käyttää mukautetuille logoille tai avatarille. Mukautettu ikonipooli voidaan tallentaa keskitettyyn hakemistoon tai resurssikansioon (konteksti), mikä yksinkertaistaa ikonien hallintaprosessia. Oman poolin olemassaolo tekee sovelluksen luomisesta johdonmukaisempaa ja vähentää ylläpitoa eri komponenttien ja moduulien välillä.

Mukautetut poolit voidaan luoda kansiosta, joka sisältää SVG-kuvia, ja käyttämällä `IconPoolBuilder`-luokkaa. Tämän jälkeen voit valita mukautetun poolisi nimen ja käyttää sitä SVG-tiedostojen nimien kanssa luodaksesi mukautettuja ikonikomponentteja.

```java
// Luodaan mukautettu pooli nimeltä "app-pool", jossa on kuvia logosta ja avatarista.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet ikonit yhtä leveiksi ja korkeiksi, sillä `Icon`-komponentit on suunniteltu täyttämään neliön muotoista tilaa.
:::

### Mukautetun poolin tehdas {#custom-pool-factory}

Voit myös luoda tehdasluokan mukautetulle poolille webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa ikoniresurssien luomisen ja hallinnan määritellyssä poolissa ja mahdollistaa koodin täydentämisen. Jokainen ikoni voidaan instansioida `create()`-metodin kautta, joka palauttaa `Icon`. Tehdasluokan tulisi tarjota poolikohtaista metatietoa, kuten poolin nimi ja ikonitunniste, muotoiltuna kuvan tiedostonimeen. Tämä malli mahdollistaa helpon, vakiomuotoisen pääsyn ikoniresursseihin mukautetusta poolista käyttämällä enumvakioita, tukien ikonien hallinnan skaalautuvuutta ja ylläpidettävyyttä.

```java
/// Luodaan mukautetun poolin tehdas app-poolille
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return ikoneiden poolin nimi
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

Seuraava pätkä näyttää kaksi erilaista tapaa käyttää mukautettua poolia.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Luo ikoni käyttäen mukautetun poolin ja kuvatiedoston nimiä
Icon customLogo = new Icon("logo", "app-pool");

// Luo ikoni käyttäen aikaisemman pätkän mukautetun poolin tehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Ikonipainikkeet {#icon-buttons}
`Icon`-komponentti ei ole valittavissa, mutta toimintoja, jotka on parasta esittää pelkästään ikonilla, kuten ilmoitukset tai hälytykset, varten voit käyttää `IconButton`ia.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
});
```

## Parhaat käytännöt

- **Esteettömyys:** Käytä työkaluvihjeitä tai etikettejä ikoneilla tehdäksesi sovelluksestasi esteettömän näkövammaisille käyttäjille, jotka luottavat ruudunlukijoihin.
- **Vältä epäselvyyksiä:** Vältä ikoneiden käyttöä, jos merkitys ei ole selkeä tai laajasti ymmärretty. Jos käyttäjien on arvattava, mitä ikoni tarkoittaa, se kumoaa tarkoituksen.
- **Käytä ikoneita kohtuudella:** Liian monet ikonit voivat ylikuormittaa käyttäjiä, joten käytä ikoneita vain, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Muotoilu
Ikoni perii suoraan vanhemman komponenttinsa teeman, mutta voit ohittaa tämän soveltamalla teemaa `Icon`-komponenttiin suoraan.

### Teemat
Ikonikomponentit sisältävät seitsemän erityistä teemaa sisäänrakennettuina nopeaa muotoilua varten ilman CSS:n käyttöä. Nämä teemat ovat valmiiksi määritettyjä tyylejä, joita voidaan soveltaa ikoneihin ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ikonien ulkonäköä sovelluksessa.

Vaikka jokaisella erilaisella teemalla on monia käyttötarkoituksia, joitakin esimerkkejä käytöistä ovat:

- `DANGER`: Paras vakavien seurausten kanssa, kuten täytettävien tietojen poistaminen tai tilin/tietojen peruuttamaton poistaminen.
- `DEFAULT`: Sopiva yleisiin toimiin sovelluksessa, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen vaihtaminen.
- `PRIMARY`: Sopii pääasialliseksi "call-to-action" -komponentiksi sivulla, kuten rekisteröitymiseen, muutosten tallentamiseen tai siirtymiseen toiselle sivulle.
- `SUCCESS`: Erinomainen visualisoimaan sovelluksessa tapahtuvan elementin onnistunutta loppuunsaattamista, kuten lomakkeen tai rekisteröinnin viimeistelyn. Onnistuminen-teema voidaan ohjelmallisesti käyttää onnistuneen toiminnan valmistuttua.
- `WARNING`: Hyödyllinen ilmoittamaan, että käyttäjä on tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä sivulle, jolla on tallentamattomia muutoksia. Nämä toimet ovat yleensä vähemmän vaikuttavia kuin ne, jotka käyttäisivät Vaaraa-teemaa.
- `GRAY`: Hyvä hienovaraisiin toimiin, kuten pieniin asetuksiin tai toimiin, jotka ovat enemmän lisälaatuja sivulle, eivätkä ole osa päätoimintoja.
- `INFO`: Hyvä tarjoamaan käyttäjälle lisäselventävää tietoa.

<TableBuilder name={['Icon', 'IconButton']} />
