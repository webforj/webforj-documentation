---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: f1572fcb6d1396a20fac32bfa282c091
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

`Icon`-komponentti näyttää ikoneita, jotka skaalautuvat mihin tahansa kokoon laadun menetystä. Voit valita kolmesta sisäänrakennetusta ikonikokoelmasta tai luoda omia mukautettuja. Ikonit palvelevat visuaalisina vihjeinä navigointiin ja toimintoihin, vähentäen tekstimerkintöjen tarvetta käyttöliittymässäsi.

Jokainen `Icon` renderöidään skaalautuvana vektorigrafiikkana (SVG), joka ladataan tarpeen mukaan sisällönjakeluverkosta (CDN) latenssin pitämiseksi alhaisena. Luodaksesi yhden, valitse ikonikokoelma ja ikonille nimen. Jotkut ikonit tarjoavat myös vaihtoehdon ääriviivamuotoisen tai täytetyn version välillä [variations](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Tiesitkö?
Joillakin komponenteilla, kuten `PasswordField` ja `TimeField`, on sisäänrakennettuja ikoneita, jotka auttavat välittämään merkityksen loppukäyttäjille.
:::

## Kokonaisuudet {#pools}

Ikonikokoelma on kokoelma yleisesti käytettyjä ikoneita, jotka mahdollistavat helpon pääsyn ja uudelleenkäytön. Käyttämällä ikoneita ikonikokoelmasta voit varmistaa, että sovelluksesi ikonit ovat tunnistettavia ja jakavat johdonmukaisen tyylin.
Käyttämällä webforJ:ta voit valita kolmesta kokoelmasta tai toteuttaa mukautetun kokoelman.
Jokaisella kokoelmalla on laaja kokoelma avoimen lähdekoodin ikoneita, joita voit käyttää ilmaiseksi.
WebforJ:n käyttö antaa sinulle mahdollisuuden valita kolmesta kokoelmasta ja käyttää niitä ainutlaatuisina luokkina ilman, että sinun tarvitsee ladata ikoneita suoraan.

| Ikonikokoelma                                   | webforJ-luokka                             |
| ------------------------------------------------ | ---------------------------------------- |
| [Tabler](https://tabler-icons.io/)              | `TablerIcon` ja `DwcIcon`.<br/>`DwcIcon` on osa Tabler-ikoneita.|
| [Feather](https://feathericons.com/)            | `FeatherIcon`                            |
| [Font Awesome](https://fontawesome.com/search)  | `FontAwesomeIcon`                       |

:::tip

Jos olet kiinnostunut luomaan oman ikonikokoelman, katso [Luodaan mukautettuja kokoelmia](#creating-custom-pools).

:::

Kun olet valinnut sovellukseesi sisällytettävät kokoelmat, seuraava askel on määrittää haluamasi ikonille nimi.

## Nimitykset {#names}

Sisällyttääksesi ikonin sovellukseesi, tarvitset vain ikonikokoelman ja ikoninnimen. Selaa ikonikokoelman verkkosivustoa etsimällä haluamasi ikonit ja käytä ikoninnimeä `create()`-menetelmän parametrina.
Lisäksi voit luoda ikoneita enumien kautta `FeatherIcon` ja `DwcIcon` -luokille, mikä mahdollistaa niiden ilmestymisen koodin täydentämisessä.

```java
// Luo ikoni String-nimellä
Icon image = TablerIcon.create("image");
// Luo ikoni enumista
Icon image = FeatherIcon.IMAGE.create();
```

## Muunnokset {#variations}

Voit myös personoida ikoneita entisestään hyödyntämällä muunnoksia.
Tietyt ikonit mahdollistavat ääriviiva- tai täyttöversion valinnan, jolloin voit korostaa tiettyä ikonia mieltymystesi mukaan. `FontAwesomeIcon` ja `Tabler`-ikonit tarjoavat muunnoksia.

### `FontAwesomeIcon` muunnokset {#fontawesomeicon-variations}

1. `REGULAR`: Ikonien ääriviivamuunnos. Tämä on oletus.
2. `SOLID`: Ikonien täyttömuunnos.
3. `BRAND`: Muunnos, kun käytät brändien ikoneita.

### `TablerIcon` muunnokset {#tablericon-variations}

1. `OUTLINE`: Ikonien ääriviivamuunnos. Tämä on oletus.
2. `FILLED`: Ikonien täyttömuunnos.

```java
// Täyttömuunnos Font Awesome -ikonista
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Seuraava esittely havainnollistaa, kuinka käyttää erilaisia kokoelmista peräisin olevia ikoneita, soveltaa muunnoksia ja integroida niitä saumattomasti komponentteihin.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Ikonien lisääminen komponentteihin {#adding-icons-to-components}

Integroi ikoneita komponentteihisi käyttämällä slotteja. Slotit tarjoavat joustavia vaihtoehtoja tehdä komponenteista hyödyllisempiä. On hyödyllistä lisätä `Icon` komponenttiin, jotta se selventää käyttäjille tarkoitettua merkitystä.
Komponentit, jotka toteuttavat `HasPrefixAndSuffix`-sen kokoonpanon, voivat sisältää `Icon`-palikan tai muita voimassa olevia komponentteja. Lisätyt komponentit voidaan sijoittaa `prefix`- ja `suffix`-sloteihin, mikä voi parantaa sekä yleistä muotoilua että käyttäjäkokemusta.

Käyttämällä `prefix`- ja `suffix`-slotteja voit määrittää, haluatko ikonisi ennen vai jälkeen tekstin käyttämällä `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä.

Päätös siitä, sijoitetaanko ikoni ennen vai jälkeen tekstin komponentissa, riippuu suurelta osin tarkoituksesta ja muotoiluyhteydestä.

### Ikonin sijoittelu: ennen vs. jälkeen {#icon-placement-before-vs-after}

Komponentin tekstin edessä sijaitsevat ikonit auttavat käyttäjiä ymmärtämään nopeasti komponentin ensisijaisen toiminnan tai tarkoituksen, erityisesti yleisesti tunnistettavien ikonien, kuten tallennusikonin kohdalla.
Ikkunoiden sijoittaminen komponentin tekstin eteen tarjoaa loogisen prosessointijärjestyksen, ohjaten käyttäjiä luonnollisesti tarkoitetun toiminnan läpi, mikä on hyödyllistä painikkeille, joiden ensisijainen tehtävä on välitön toiminta.

Toisaalta, ikonien sijoittaminen komponentin tekstin jälkeen on tehokasta toiminnoille, jotka tarjoavat lisäkontekstia tai vaihtoehtoja, parantaen selkeyttä ja navigointivihjeitä.
Ikonit komponentin tekstin jälkeen ovat ihanteellisia komponenteille, jotka joko tarjoavat lisätietoja tai ohjaavat käyttäjiä suuntaa antavassa käytöksessä.

Loppujen lopuksi johdonmukaisuus on avainasemassa. Kun olet valinnut tyylin, pidä siitä kiinni koko sivustollasi yhtenäisen ja käyttäjäystävällisen suunnittelun saavuttamiseksi.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Mukautettujen kokoelmien luominen {#creating-custom-pools}

Olemassa olevien ikonikokoelmien hyödyntämisen lisäksi voit luoda mukautetun kokoelman, jota voidaan käyttää mukautetuissa logoissa tai avatareissa.
Mukautettu ikonikokoelma voidaan tallentaa keskeiseen hakemistoon tai resurssikansioon (kontekstiin), mikä yksinkertaistaa ikonien hallintaprosessia.
Mukautetun kokoelman avulla sovelluksen luomisesta tulee johdonmukaisempaa ja se vähentää huoltotyötä eri komponenttien ja moduulien välillä.

Mukautetut kokoelmat voidaan luoda kansiosta, joka sisältää SVG-kuvia ja käyttämällä `IconPoolBuilder`-luokkaa. Tämän jälkeen voit valita mukautetun kokoelman nimen ja käyttää sitä SVG-tiedostojen nimien kanssa mukautettujen ikonikomponenttien luomiseksi.

```java
// Luodaan mukautettu kokoelma nimeltä "app-pool", joka sisältää kuvia logosta ja avatarista.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Varmista, että suunnittelet ikonit yhtä leveiksi ja korkeiksi, koska `Icon`-komponentit on suunniteltu vievän neliön muotoista tilaa.
:::

### Mukautetun kokoelman tehdas {#custom-pool-factory}

Voit myös luoda tehdasluokan mukautetulle kokoelmalle webforJ:ssä, aivan kuten `FeatherIcon`. Tämä mahdollistaa ikoniresurssien luomisen ja hallinnan tietyssä kokoelmassa sekä koodin täydentämisen.
Jokainen ikoni voidaan instansioida `create()`-menetelmällä, joka palauttaa `Icon`:n. Tehdasluokan tulisi tarjota kokoelma- ja ikonitunnuksia koskevat metatiedot, jotka on muotoiltu kuvan tiedostonimeen.
Tämä suunnittelu mahdollistaa helpon, standardoidun pääsyn ikoniprosessoreihin mukautetusta kokoelmasta käyttämällä enum-vakioita, mikä tukee skaalautuvuutta ja ylläpidettävyyttä ikoninhallinnassa.

```java
/// Luodaan mukautetun kokoelman tehdas "app-pool"
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return kokoelman nimi ikoneille
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return ikoninnimi
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Seuraava koodinpätkä näyttää kaksi erilaista tapaa käyttää mukautettua kokoelmaa.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Luo ikoni käyttämällä mukautetun kokoelman nimiä ja kuva tiedostoa
Icon customLogo = new Icon("logo", "app-pool");

// Luo ikoni käyttämällä aikaisemman koodinpätkän mukautetun kokoelman tehdasta
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Ikonipainikkeet <DocChip chip="name" label="dwc-icon-button" /> {#icon-buttons}
`Icon`-komponentti ei ole valittava, mutta toimintoja, jotka on parhaiten esitetty yksinkertaisella ikonilla, kuten ilmoitukset tai hälytykset, varten voit käyttää `IconButton`-komponenttia.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sinulle on uusi viesti!", "Ding Dong!")
});
```

## Parhaat käytännöt

- **Saavutettavuus:** Käytä työkaluvihjeitä tai etikettiä ikoneille, jotta sovelluksesi on saavutettavissa näkövammaisille käyttäjille, jotka luottavat näytönlukuohjelmiin.
- **Vältä epäselvyyksiä:** Vältä ikonien käyttöä, jos merkitys ei ole selvä tai laajalti ymmärretty. Jos käyttäjien on arvattava, mitä ikoni edustaa, se kumoaa tarkoituksen.
- **Käytä ikoneita säästeliäästi:** Liian monet ikonit voivat tuntua ylivoimaisilta käyttäjille, joten käytä ikoneita vain, kun ne lisäävät selkeyttä tai vähentävät monimutkaisuutta.

## Muotoilu
Ikoni perii suoran vanhempikomponenttinsa teeman, mutta voit ylikirjoittaa tämän soveltamalla teemaa suoraan `Icon`-komponenttiin.

### Teemat
Icon-komponenteilla on seitsemän erillistä teemaa sisäänrakennettuna nopeaa muotoilua varten ilman CSS:n käyttöä. Nämä teemat ovat esimäärättyjä tyylejä, joita voidaan käyttää ikonien ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ikonien ulkonäköä sovelluksessa.

Vaikka kullekin eri teemalle on monia käyttötarkoituksia, joitakin esimerkkejä käytöstä ovat:

- `DANGER`: Parasta käytettäväksi toiminnoissa, joilla on vakavia seurauksia, kuten täytettyjen tietojen poistaminen tai tilin/tietojen pysyvä poistaminen.
- `DEFAULT`: Sopii sovelluksen toiminnoille, jotka eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen vaihtaminen.
- `PRIMARY`: Sopii pää "call-to-action"-toiminnolle sivulla, kuten rekisteröitymiselle, muutosten tallentamiselle tai siirtymiselle toiselle sivulle.
- `SUCCESS`: Erinomainen visualisoimaan elementin onnistunutta suorittamista sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröintiprosessin valmistumista. Menestysteema voidaan ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
- `WARNING`: Hyödyllinen ilmoittamaan, että käyttäjä on suorittamassa mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta tallentamattomilla muutoksilla. Nämä toiminnot ovat yleensä vähemmän merkittäviä kuin ne, jotka käyttäisivät vaarateemaa.
- `GRAY`: Hyvä hienovaraisille toiminnoille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän täydentäviä sivulle, eivätkä osa päätoiminnallisuutta.
- `INFO`: Hyvä ylimääräisten selventävien tietojen tarjoamiseen käyttäjälle.

<TableBuilder name={['Icon', 'IconButton']} />
