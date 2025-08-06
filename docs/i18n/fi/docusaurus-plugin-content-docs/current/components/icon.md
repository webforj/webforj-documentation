---
title: Icon
sidebar_position: 55
_i18n_hash: ab67367c75477c4366e5e86862dac630
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

webforJ `Icon` -komponentti mahdollistaa ikoneiden helpon lisäämisen käyttöliittymääsi.
Ikonit ovat olennainen osa käyttöliittymän suunnittelun parantamista, mikä helpottaa käyttäjien näyttöruudun skannaamista toiminnallisten elementtien löytämiseksi.
Ikonien käyttäminen sovelluksessasi luo visuaalisia vihjeitä navigointiin ja toimintoihin, mikä voi vähentää tarvittavan tekstin määrää ja yksinkertaistaa käyttöliittymää. Voit valita kolmesta olemassa olevasta ikonipoolista, ja webforJ antaa myös mahdollisuuden luoda uusia alusta alkaen.

:::tip Tiesitkö?

Joillakin komponenteilla, kuten `PasswordField` ja `TimeField`, on sisäänrakennettuja ikoneita, jotka auttavat välittämään merkitystä loppukäyttäjille.

:::

## Perusteet {#basics}

Jokainen `Icon` on suunniteltu suurennettavaksi vektorigrafiikaksi (SVG), mikä tarkoittaa, että se voi helposti skaalautua mihin tahansa kokoon menettämättä terävyyttä tai laatua.
Lisäksi `Icon` -komponentit ladataan kysynnän mukaan sisällönjakeluverkosta (CDN), mikä auttaa vähentämään viivettä ja parantaa kokonaisvaltaista suorituskykyä.

Kun luot `Icon` -komponenttia, sinun on määritettävä tietty pooli ja ikonin nimi.
Jotkut ikonit tarjoavat myös vaihtoehdon valita reunoitetun tai täytetyn version [variatioiden](#variations) kautta.

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Poolit {#pools}

Ikonipooli on kokoelma yleisesti käytettyjä ikoneita, jotka mahdollistavat helpon pääsyn ja käytön uudelleen. Käyttämällä ikoneita ikonipoolista voit varmistaa, että sovelluksesi ikonit ovat tunnistettavissa ja jakavat johdonmukaisen tyylin.
webforJ:n käyttäminen antaa sinun valita kolmesta poolista tai toteuttaa mukautetun poolin.
Jokaisessa poolissa on laaja kokoelma avointa lähdekoodia olevia ikoneita, joita voi käyttää ilmaiseksi.
webforJ:n käyttäminen antaa sinulle joustavuutta valita kolmesta poolista ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata yhtäkään ikonia suoraan.

| Ikonipooli                                         | webforJ-luokka |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on Tabler-ikoneiden osa. |    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Jos olet kiinnostunut luomaan oman ikonipoolin, katso [Mukautettujen poolien luominen](#creating-custom-pools).

:::

Kun olet valinnut poolin tai poolit, jotka haluat sisällyttää sovellukseesi, seuraava vaihe on määrittää ikonin nimi, jota haluat käyttää.

### Nimitykset {#names}

Sisällyttääksesi ikonit sovellukseesi, tarvitset vain ikonipoolin ja ikonin nimen. Selaa ikonipoolin verkkosivustoa etsimään käyttämäsi ikoni ja käytä ikoninnimeä `create()` -menetelmän parametrina.
Lisäksi voit luoda ikoneita enumien kautta `FeatherIcon` ja `DwcIcon` -luokissa, jolloin ne näkyvät koodin täydennyksessä.

```java
// Luo ikoni merkkijonon nimestä
Icon image = TablerIcon.create("image");
// Luo ikoni enumista
Icon image = FeatherIcon.IMAGE.create();
```

### Variatiot {#variations}

Voit personoida ikoneita vielä enemmän käyttäen variatioita.
Tietyt ikonit antavat sinun valita reunoitetun tai täytetyn version, mikä mahdollistaa tietyn ikonin korostamisen mieltymystesi mukaan. `FontAwesomeIcon` ja `Tabler` -ikonit tarjoavat variatioita.

#### `FontAwesomeIcon` -variatiot {#fontawesomeicon-variations}

1. `REGULAR`: Ikonien reunoitettu muunnos. Tämä on oletus.
2. `SOLID`: Ikonien täytetty muunnos.
3. `BRAND`: Muunnos, kun käytät brändien ikoneita.

#### `TablerIcon` -variatiot {#tablericon-variations}

1. `OUTLINE`: Ikonien reunoitettu muunnos. Tämä on oletus.
2. `FILLED`: Ikonien täytetty muunnos.

```java
// Täytetty muunnos ikonista Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava esittely havainnollistaa, miten käyttää ikoneita eri poolista, soveltaa variatioita ja integroida niitä saumattomasti komponentteihin.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Ikonien lisääminen komponentteihin {#adding-icons-to-components}

Integroi ikonit komponentteihisi käyttämällä slotteja. Slotit tarjoavat joustavia vaihtoehtoja komponenttien hyödyllisyyden lisäämiseksi. On hyödyllistä lisätä `Icon` komponenttiin selventämään tarkemmin käyttäjien tarkoitusta.
Komponentit, jotka toteuttavat `HasPrefixAndSuffix` -rajapinnan, voivat sisältää `Icon` -komponentin tai muita kelvollisia komponentteja. Lisätyt komponentit voidaan sijoittaa `prefix` ja `suffix` -slotteihin ja ne voivat parantaa sekä yleistä suunnittelua että käyttäjäkokemusta.

`Prefix` ja `suffix` -slottien avulla voit määrittää, haluatko ikonin ennen vai jälkeen tekstin käyttäen `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä.

Päätös siitä, sijoitetaanko ikoni ennen vai jälkeen komponentin tekstin, riippuu suurelta osin tarkoituksesta ja suunnitteluyhteydestä.

### Ikonin sijoittaminen: ennen VS jälkeen {#icon-placement-before-vs-after}

Ikonit, jotka on sijoitettu komponentin tekstin eteen, auttavat käyttäjiä ymmärtämään nopeasti komponentin ensisijaisen toiminnan tai tarkoituksen, erityisesti yleisesti tunnistettujen ikonien, kuten tallennusikoneen, kohdalla.
Ikonit ennen komponentin tekstiä tarjoavat loogisen prosessointijärjestyksen, ohjaten käyttäjiä luonnollisesti tarkoitetun toiminnan läpi, mikä on hyödyllistä painikkeille, joiden ensisijainen toiminto on välitön toiminta.

Toisaalta, ikonien sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoille, jotka tarjoavat lisäyhteyttä tai -vaihtoehtoja, parantaen selkeyttä ja navigointivihjeitä.
Ikonit komponentin tekstin jälkeen ovat ihanteellisia komponentteihin, jotka tarjoavat lisätietoa tai ohjaavat käyttäjiä suuntaavaan kulkuun.

Lopulta johdonmukaisuus on avainasia. Kun valitset tyylin, ylläpidä sitä koko sivustollasi johdonmukaisen ja käyttääystävällisen suunnittelun varmistamiseksi.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>

## Mukautettujen poolien luominen {#creating-custom-pools}

Olemassa olevien ikonikokoelmien lisäksi sinulla on mahdollisuus luoda mukautettu pooli, jota voidaan käyttää mukautetuille logoille tai avatarille.
Mukautettu ikonipooli voidaan tallentaa keskitettyyn hakemistoon tai resursseihin (kontekstiin), mikä yksinkertaistaa ikoninhallintaprosessia.
Mukautetun poolin omistaminen tekee sovelluksen luomisesta johdonmukaisempaa ja vähentää ylläpitovaatimuksia eri komponenteissa ja moduuleissa.

Mukautetut poolit voidaan luoda hakemistosta, joka sisältää SVG-kuvia, ja käyttämällä `IconPoolBuilder` -luokkaa. Tämän jälkeen voit valita mukautetun poolisi nimen ja käyttää sitä SVG-tiedostojen nimien kanssa mukautettujen ikonikomponenttien luomiseksi.

```java
// Luodaan mukautettu pooli nimeltä "app-pool", joka sisältää kuvat logolle ja avatarille.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet ikonit yhtä leveiksi ja korkeiksi, sillä `Icon` -komponentit on suunniteltu viemään neliön muotoista tilaa.
:::

### Mukautettu poolitehdas {#custom-pool-factory}

Voit myös luoda tehdasluokan mukautetulle poolille webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa ikoniresurssien luomisen ja hallinnan määritellyssä poolissa ja mahdollistaa koodin täydennyksen.
Jokainen ikoni voidaan instansioida `create()` -menetelmän kautta, joka palauttaa `Icon` -komponentin. Tehdasluokan tulisi tarjota poolikohtaista metadataa, kuten poolin nimi ja ikonitunniste, joka on muotoiltu kuvan tiedostonimeksi.
Tämä rakenne mahdollistaa helpon, standardoidun pääsyn ikoniresursseihin mukautetusta poolista käyttäen enum-vakiot, tukien ikoninhallinnan laajennettavuutta ja ylläpidettävyyttä.

```java
/// Luodaan mukautettu poolitehdas app-poolille
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

// Luo ikoni mukautetun poolin ja kuvatiedoston nimillä
Icon customLogo = new Icon("logo", "app-pool");

// Luo ikoni käyttämällä edellisessä koodinpätkässä olevaa mukautetun poolin tehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Ikoni painikkeet {#icon-buttons}
`Icon` -komponentti ei ole valittava, mutta toimintoihin, jotka on parhaiten esitetty pelkästään ikonilla, kuten ilmoitukset tai hälytykset, voit käyttää `IconButton` -komponenttia.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulla on uusi viesti!", "Ding Dong!")
});
```

## Parhaat käytännöt

- **Saavutettavuus:** Käytä työkaluvihjeitä tai etikettiä ikoneissa, jotta sovelluksesi on saavutettavissa näkövammaisten käyttäjien, jotka luottavat ruudunlukuohjelmiin.
- **Vältä epäselvyyksiä:** Vältä ikoneiden käyttöä, jos merkitys ei ole selvä tai laajalti ymmärretty. Jos käyttäjien on arvaettava, mitä ikoni tarkoittaa, se kumoaa tarkoituksen.
- **Käytä ikoneita säästeliäästi:** Liikaa ikoneita voi ylikuormittaa käyttäjiä, joten käytä ikoneita vain, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Tyylittely
`Icon` perii suoraan vanhemman komponentin teeman, mutta voit ohittaa tämän soveltamalla teemaa `Icon` -komponenttiin suoraan.

### Teemat
Ikonikomponentit sisältävät seitsemän erilaista teemaa, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa ikoneihin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ikonien ulkoasua koko sovelluksessa.

Vaikka jokaiselle erilaiselle teemalle on monia käyttötapauksia, muutamia esimerkkikäyttöjä ovat:

- `DANGER`: Paras toimille, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
- `DEFAULT`: Sopii sovelluksessa toteutettaville toiminnoille, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen kytkeminen.
- `PRIMARY`: Sopii pää "toimintakehotteeksi" sivulla, kuten rekisteröityminen, tallentaminen muutoksia tai siirtyminen toiseen sivuun.
- `SUCCESS`: Erinomainen kuvastamaan sovelluksen elementin onnistunutta suorittamista, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin loppuunsaattamista. Menestysteema voidaan ohjelmallisesti soveltaa, kun onnistuva toiminto on suoritettu.
- `WARNING`: Hyödyllinen osoittamaan, että käyttäjä on suorittamassa mahdollisesti riskialtista toimintoa, kuten siirtymistä sivulle, jolla on tallentamattomia muutoksia. Nämä toiminnot ovat useimmiten vähemmän vaikuttavia kuin niiden, jotka käyttäisivät vaarateemaa.
- `GRAY`: Hyvä hienovaraisille toiminnoille, kuten vähäisille asetuksille tai toimille, jotka ovat enemmän lisäyksellisiä sivulle, eivätkä osa päätoimintoja.
- `INFO`: Hyvä käyttäjälle ylimääräisten selventävien tietojen tarjoamiseen.

<TableBuilder name="Icon" />
