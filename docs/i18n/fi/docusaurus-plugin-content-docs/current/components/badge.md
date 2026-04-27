---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 83dfb4c5ec1d554fc78e7e860128fb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

`Badge` on tiivistetty, visuaalisesti erottuva etiketti, jota käytetään tilan, laskentojen tai lyhyiden kontekstuaalisten tietojen välittämiseen. Indikaattorin laskemiseen, kohteen merkitsemiseen "Uutena" tai varoitukseen kiinnittämiseen, tarrat tarjoavat kevyen tavan esittää nämä tiedot suoraan käyttöliittymässä.

<!-- INTRO_END -->

:::tip Käyttämällä `Badge` 
Tarrat toimivat hyvin ilmoituslaskentojen, tilaetikettien ja lyhyen metadatan, kuten versiomerkkien tai julkaisutilojen yhteydessä. Pidä tarran teksti yhdessä tai kahdessa sanassa, jotta etiketti on helposti luettavissa yhdellä silmäyksellä.
:::

## Luominen tarralla {#creating-a-badge}

Yksinkertaisin `Badge` ottaa tekstimerkkijonon. Voit myös siirtää `BadgeTheme`-teeman suoraan konstruktorissa asettaaksesi visuaalisen tyylin heti. Ilman argumentteja oleva konstruktori on käytettävissä, kun tarvitset rakentaa tarran dynaamisesti ja konfiguroida sen luomisen jälkeen.

```java
Badge badge = new Badge("Uusi");

// Teeman kanssa
Badge primary = new Badge("Esitelty", BadgeTheme.SUCCESS);

// Rakennettu dynaamisesti
Badge status = new Badge();
status.setLabel("Odottaa");
status.setTheme(BadgeTheme.WARNING);
```

## Etiketti {#label}

Voit asettaa tai päivittää tarran tekstisisältöä milloin tahansa käyttämällä `setLabel()`. `setText()`-metodi on alias samalle toimenpiteelle; käytä kumpaa tahansa, joka tuntuu luonnollisemmalta asiayhteydessä. Molemmilla on vastaavat getterit, `getLabel()` ja `getText()`, jos sinun tarvitsee lukea nykyinen arvo takaisin.

```java
Badge badge = new Badge();
badge.setLabel("Päivitetty");

// Vastaava
badge.setText("Päivitetty");

// Lue takaisin arvo
String current = badge.getLabel();
```

## Ikonit {#icons}

Joskus visuaalisempi lähestymistapa on hyödyllinen tiedon välittämiseksi `Badge`-elementillä. Tarrat tukevat ikoneita sisältävää sisältöä. Siirrä ikoni yhdessä tekstin kanssa käyttämällä `Badge(String, Component...)`-konstruktoria, tai siirrä ikoni yksin luodaksesi vain ikonista tarran. Kun yhdistät tekstin kanssa, ikoni renderöityy etikettisi vasemmalle puolelle.

Ikoniviimeistellyt tarrat toimivat erityisen hyvin tiheissä asettelussa, joissa lyhyt sana tuntuu sekavalta. Ikonin yhdistäminen tekstin kanssa on hyvä kompromissi, kun pelkkä ikoni voi olla epäselvä. Tilasymboli ymmärretään yleisesti itsessään, mutta lyhyen tekstietiketin lisääminen poistaa arvailuja ensikertalaisten osalta. Voit siirtää useita komponentteja konstruktorille, jos sinun tarvitsee koostaa rikkaampi prefiksi, vaikkakin käytännössä pelkkä ikoni on yleisin malli.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='345px'
/>
<!-- vale on -->

```java
// Ikoni tekstin kanssa
Badge done = new Badge("Valmis", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Vain ikoni
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Käyttö muissa komponenteissa {#usage-in-other-components}

### Painikkeet {#buttons}

Liitä `Badge` `Button`-elementtiin käyttämällä `setBadge()`. Tarra näkyy painikkeen yläoikeassa kulmassa, peittäen painikkeen reunan. Tämä on yleinen malli ilmoituslaskentojen osalta työkalupalkkitoiminnoissa tai kuvakepainikkeissa. Koska tarra on itsenäinen komponentti, se on täysin riippumaton painikkeen omasta teemasta ja koosta. Voit yhdistää pääpainikkeen vaaratarran kanssa tai kummituspainikkeen onnistumistarran kanssa, ja jokainen yhdistelmän puoli tyylittelee itsensä ilman ristiriitoja. Laskennan päivittäminen myöhemmin on yhtä yksinkertaista kuin kutsumalla `badge.setLabel()` uudella arvolla; painiketta ei tarvitse koskea.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='290px'
/>
<!-- vale on -->

### Tabbattu paneeli {#tabbed-pane}

Lisää `Badge` suffiksina `Tab`:lle käyttämällä `setSuffixComponent()`. Tämä on luonnollinen sovitus inbox-tyylisille laskennalle tai tilaindikaattoreille jokaisella välilehdellä. Tämä on sellainen malli, jota näet sähköpostiohjelmissa tai tehtävienhallintaohjelmissa, joissa on tärkeää viestiä toimintaa jokaisessa osiossa yhdellä silmäyksellä. Tarra sijaitsee välilehden etiketin jälkimmäisessä reunassa minkä tahansa prefiksisisällön jälkeen, ja se pysyy näkyvissä riippumatta siitä, mikä välilehti on aktiivinen. Tämä jatkuvuus on tarkoituksellinen: passiivisten välilehtien tarran piilottaminen vaikeuttaisi sen tietämistä, mitkä osiot kaipaavat huomiota ilman siirtymistä jokaiselle niistä.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='360px'
/>
<!-- vale on -->

## Tyylittely {#styling}

Tarrat tukevat useita tyylittelyulottuvuuksia: teemavärejä merkityksen välittämiseksi, laajuuskokoa koon hallitsemiseksi sekä CSS-ominaisuuksia hienosäätöön.

### Teemat {#themes}

Kuten monet komponentit webforJ:ssä, `Badge` tulee neljätoista teemaa: seitsemän täytettyä ja seitsemän ääriviivateemaa.

Täytetyt teemat käyttävät kiinteää taustaa ja laskevat automaattisesti tekstivärin, joka täyttää kontrastivaatimukset. Ääriviivateemat käyttävät sen sijaan sävytettyä taustaa värillisellä reunalla, mikä tekee niistä hienovaraisemman vaihtoehdon, kun haluat, että tarra täydentää ympäröivää sisältöä eikä dominoi sitä.

Sovita teema käyttämällä `setTheme()`-metodia tai konstruktorin kautta.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Mukautettu väri {#custom-color}

Jos sisäänrakennetut teemat eivät vastaa väriasi, aseta mukautettu siemenväri käyttämällä `--dwc-badge-seed` CSS-ominaisuutta. Tästä yhdestä arvosta tarra automaattisesti johtaa taustan, tekstin ja reunavärin, joten jokainen yhdistelmä pysyy luettavana ilman, että sinun täytyy määrittää kutakin erikseen. Tämä tarkoittaa, että voit brändätä tarran mihin tahansa väriin suunnittelujärjestelmässäsi luottavaisesti. Hue, Saturation ja Lightness (HSL) -arvot ovat erityisen käteviä tässä; sävyn vaihtaminen riittää tuottamaan täysin erilaisen väri-perheen säilyttäen samalla kontrastin.

```java
Badge badge = new Badge("Mukautettu");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Koko {#sizing}

Käytä `setExpanse()` hallitaksesi tarran kokoa. Saatavana on yhdeksän kokoa, jotka vaihtelevat `XXXSMALL`-koosta `XXXLARGE`-kokoon, ja oletusarvo on `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='300px'
/>
<!-- vale on -->

### Osat ja CSS-muuttujat {#parts-and-css-variables}

<TableBuilder name="Badge" />
