---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 112f61dea5c6c0d434267a25ccc61b9e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

`Badge` on kompakti, visuaalisesti erottuva etiketti, jota käytetään tilan, laskentojen tai lyhyiden kontekstin tietojen välittämiseen. Tarvitsetpa sitten ilmoituslaskurin merkitsemistä, tuotteen merkitsemistä "Uudeksi" tai varoituksen huomiota herättämistä, badge tarjoaa kevyen tavan tuoda kyseinen informaatio suoraan käyttöliittymään.

<!-- INTRO_END -->

:::tip Käyttämällä `Badge`
Badge toimii hyvin ilmoituslaskureissa, tilamerkeissä ja lyhyissä metatiedoissa, kuten versiomerkeissä tai julkaisutiloissa. Pidä badge-teksti yhdessä tai kahdessa sanassa, jotta etiketti on helppo havaita yhdellä silmäyksellä.
:::

## Badge luominen {#creating-a-badge}

Yksinkertaisin `Badge` ottaa tekstimerkkijonon. Voit myös siirtää `BadgeTheme` suoraan konstruktorissa määrittääksesi visuaalisen tyylin heti. Ilman argumentteja oleva konstruktori on käytettävissä, kun sinun tarvitsee rakentaa badge dynaamisesti ja konfiguroida se luomisen jälkeen.

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

Voit asettaa tai päivittää badgeen tekstisisältöä milloin tahansa `setLabel()`-metodilla. `setText()`-metodi on synonyymi samalle operaatiossa; käytä kumpaa tahansa, joka tuntuu luonnolliselta asiayhteydessä. Molemmilla on vastaavat getterit, `getLabel()` ja `getText()`, jos sinun tarvitsee lukea nykyinen arvo takaisin.

```java
Badge badge = new Badge();
badge.setLabel("Päivitetty");

// Vastaava
badge.setText("Päivitetty");

// Lue takaisin arvo
String current = badge.getLabel();
```

## Ikonit {#icons}

Joskus visuaalisempi lähestymistapa on hyödyllinen tiedon välittämiseen `Badge`-käytössä. Badget tukevat slotattua ikonisisältöä. Anna ikoni yhdessä tekstin kanssa käyttämällä `Badge(String, Component...)`-konstruktorin, tai anna ikoni yksin luodaksesi vain ikoni-badgen. Yhdistettynä tekstin kanssa ikoni renderöidään etikettin vasemmalle puolelle.

Ikkunat kattavan badge-tyyppien yhdistelmät toimivat erityisen hyvin tiiviissä asetteluissa, joissa lyhyt sana saattaisi tuntua liian täyttyneeltä. Ikonin yhdistäminen tekstin kanssa on hyvä väliintulo, kun pelkkä ikoni yksin saattaisi olla epäselvä. Tilasymboli ymmärretään laajalti yksinään, mutta lyhyen tekstietiketin lisääminen poistaa arvailua ensimmäistä kertaa käyttäjiltä. Voit siirtää useita komponentteja konstruktorille, jos sinun tarvitsee koostaa rikkaampi etuliite, vaikka käytännössä vain yhden ikonin käyttäminen on yleisin malli.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
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

### Nappulat {#buttons}

Liitä `Badge` nappulaan käyttämällä `setBadge()`. Badge näkyy nappulan oikeassa yläkulmassa, päällekkäin nappulan reunan kanssa. Tämä on yleinen kuvio ilmoituslaskureille työkalupalkin toiminnoissa tai ikoninappuloissa. Koska badge on itsenäinen komponentti, se on täysin riippumaton nappulan omasta teemasta ja koosta. Voit parittaa päätason nappulan vaaran badgeen, tai kummitusnappulan onnistumisen badgeen, ja jokainen yhdistelmän puoli tyylittää itsensä ilman ristiriitoja. Laskurin päivittäminen myöhemmin on yhtä yksinkertainen kuin `badge.setLabel()`-kutsuminen uuden arvon kanssa; nappulaan ei tarvitse koskea.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='250px'
/>
<!-- vale on -->

### Välilehti paneeli {#tabbed-pane}

Lisää `Badge` välilehden suffix-elementiksi käyttämällä `setSuffixComponent()`. Tämä on luonnollinen sovitus postilaatikkomaiseen laskentaan tai tilamerkkeihin jokaisella välilehdellä. Tämäntyyppinen kuvio on yleinen sähköpostiohjelmissa tai tehtävienhallintatyökaluissa, joissa on tärkeää ilmoittaa toiminnasta kaikissa osissa yhdellä silmäyksellä. Badge istuu välilehden etiketin viimeisessä reunassa, kaikkien etuliiteelementtien jälkeen, ja on näkyvissä riippumatta siitä, mikä välilehti on aktiivinen. Tämä jatkuvuus on tarkoituksellista: badgejen piilottaminen inaktiivisilla välilehdillä vaikeuttaa tietää, mitkä osat tarvitsevat huomiota ilman siirtymistä kunkin päälle.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='325px'
/>
<!-- vale on -->

## Tyylittely {#styling}

Badget tukevat useita tyylittelyulottuvuuksia: teeman värit merkityksen välittämiseen, laajuusasteen koon hallintaan ja CSS-ominaisuuksia hienosäätöä varten.

### Teemat {#themes}

Kuten monet komponentit webforJ:ssa, `Badge` tulee neljätoista teemalta: seitsemän täytettyä ja seitsemän ääriviivaruokaa.

Täytetyt teemamatot käyttävät kiinteää taustaa ja laskevat automaattisesti tekstivärin, joka täyttää kontrastivaatimukset. Aäriviivamuunnelmat käyttävät sen sijaan värillistä taustaa värillä, tehden niistä hienovaraisemman vaihtoehdon, kun haluat badgeen täydentää ympäröivää sisältöä sen sijaan, että se hallitsisi sitä.

Ota teema käyttöön `setTheme()`-metodin tai konstruktorin kautta.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Mukautettu väri {#custom-color}

Jos sisäänrakennetut teemat eivät vastaa väriäsi, aseta mukautettu siemenväri käyttämällä `--dwc-badge-seed` CSS-ominaisuutta. Tästä yhdestä arvosta badge johdetaan automaattisesti tausta-, teksti- ja reunavärit, jolloin jokainen yhdistelmä pysyy luettavana ilman, että sinun tarvitsee määrittää jokaista erikseen. Tämä tarkoittaa, että voit brändätä badgeä mille tahansa väriä suunnittelujärjestelmässäsi luottamuksen kanssa. Väri, kylläisyys ja vaaleus (HSL) -arvot ovat erityisen käteviä täällä; vain sävyn vaihtaminen riittää tuottamaan täysin erilaisen väriperheen pitäen samalla kontrastin ennallaan.

```java
Badge badge = new Badge("Mukautettu");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Koko {#sizing}

Käytä `setExpanse()`-metodia hallitaksesi badgeen kokoa. Saatavilla on yhdeksän kokoa, jotka vaihtelevat `XXXSMALL`-koosta `XXXLARGE`-kokoon, ja oletus on `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='260px'
/>
<!-- vale on -->

### Osat ja CSS-muuttujat {#parts-and-css-variables}

<TableBuilder name="Badge" />
