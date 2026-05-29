---
title: Badge
sidebar_position: 8
_i18n_hash: 1f599f2c8a833e09f2d945ed0ead5447
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

`Badge` on tiivis, visuaalisesti erottuva etiketti, jota käytetään tilan, laskentojen tai lyhyiden kontekstuaalisten tietojen esittämiseen. Olitpa sitten käyttämässä sitä ilmoitusmäärien merkitsemiseen, tuotteen merkitsemiseen "Uusi" tai varoituksen huomioimiseen, badge antaa kevyen tavan tuoda nämä tiedot suoraan käyttöliittymään.

<!-- INTRO_END -->

:::tip Käyttäminen `Badge`
Badget toimivat hyvin ilmoituslaskentojen, tilamerkkien ja lyhyiden metatietojen, kuten versiotunnisteiden tai julkaisutilojen, yhteydessä. Pidä badge-teksti yhdessä tai kahdessa sanassa, jotta etiketti luetaan yhdellä vilkaisulla.
:::

## Badge luominen {#creating-a-badge}

Yksinkertaisin `Badge` ottaa tekstimerkkijonon. Voit myös välittää `BadgeTheme`-tietueen suoraan konstruktoriin asettaaksesi visuaalisen tyylin heti. Ilman argumentteja oleva konstruktori on käytettävissä, kun sinun tarvitsee luoda badge dynaamisesti ja konfiguroida se luomisen jälkeen.

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

Voit asettaa tai päivittää badge-etiketin tekstisisältöä milloin tahansa käyttämällä `setLabel()`. `setText()`-metodi on alias samalle toiminnolle; käytä mikä tahansa, joka tuntuu sopivammalta asiayhteydessä. Molemmilla on vastaavat getterit, `getLabel()` ja `getText()`, jos sinun tarvitsee lukea nykyinen arvo takaisin.

```java
Badge badge = new Badge();
badge.setLabel("Päivitetty");

// Vastaava
badge.setText("Päivitetty");

// Lue arvo takaisin
String current = badge.getLabel();
```

## Ikonit {#icons}

Joskus visuaalisempi lähestymistapa on hyödyllinen tiedon välittämisessä `Badge`-merkillä. Badget tukevat slotattua ikonisisältöä. Välitä ikoni tekstin ohella käyttäen `Badge(String, Component...)`-konstruktoria tai välitä vain ikoni luodaksesi pelkästään ikonisen badge. Kun teksti yhdistetään ikoniin, ikoni renderöidään etiketin vasemmalle puolelle.

Pelkästään ikoniset badget toimivat erityisen hyvin tiheissä asetteluissa, joissa lyhyt sana olisi sotkuinen. Ikonin yhdistäminen tekstin kanssa on hyvä kompromissi, kun pelkkä ikoni saattaa olla epäselvä. Statustunnus ymmärretään laajalti itsenäisesti, mutta lyhyen tekstietiketin lisääminen poistaa arvaamisen ensimmäiseltä käyttäjältä. Voit välittää useita komponentteja konstruktorille, jos sinun tarvitsee koostaa rikkaampaa etuliitettä, mutta käytännössä yksi ikoni on yleisin kaava.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java']}
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

Liitä `Badge` painikkeeseen käyttäen `setBadge()`. Badge näkyy painikkeen oikeassa yläkulmassa, peittäen painikkeen reunan. Tämä on yleinen malli ilmoitusmääriä varten työkalupalkin toimille tai kuvakepainikkeille. Koska badge on itsenäinen komponentti, se on täysin riippumaton painikkeen omasta teemasta ja koosta. Voit yhdistää ensisijaisen painikkeen vaarallisella badgeen tai aavepainikkeen onnistuneella badgeen, ja jokainen puoli tyylistää itsensä ilman ristiriitoja. Lukumäärän päivittäminen myöhemmin on yhtä yksinkertaista kuin kutsumalla `badge.setLabel()` uudella arvolla; painiketta ei tarvitse koskea.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java']}
height='290px'
/>
<!-- vale on -->

### Välilehtipaneeli {#tabbed-pane}

Lisää `Badge` päätteeksi `Tab`:iin käyttäen `setSuffixComponent()`. Tämä on luontainen sovitus sisäänkirjautumistyyppisille laskentamäärille tai tilamerkkien esittämiseen jokaisessa välilehdessä. Tätä mallia näkee sähköpostiohjelmissa tai tehtävienhallitsijoissa, joissa on tärkeää viestiä toimintaa jokaisessa osassa yhdellä vilkaisulla. Badge sijaitsee välilehden etiketin peräpuolella, minkä tahansa ennen tulevan sisällön jälkeen, ja pysyy näkyvissä riippumatta siitä, mikä välilehti on aktiivinen. Tämä pysyvyys on tarkoituksellinen: badge piilottaminen inaktiivisilla välilehdillä vaikeuttaisi siihen liittyvien osien tiedostamista ilman, että jokaiselle vaihteelle vaihdetaan.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane'
files={['src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java']}
height='360px'
/>
<!-- vale on -->

## Tyylit {#styling}

Badget tukevat useita tyylidimensioita: teeman värejä merkityksen viestittämiseen, laajuusasteikkoa koon hallintaan ja CSS-ominaisuuksia tarkkaan muokkaamiseen.

### Teemat {#themes}

Kuten monissa webforJ-komponenteissa, `Badge` on saatavilla neljätoista teemaa: seitsemän täytettyä ja seitsemän ääriviivaihtoehtoa.

Täytetyt teemat käyttävät kiinteää taustaa ja laskevat automaattisesti tekstivärin, joka täyttää kontrastivaatimukset. Ääriviivavalinnat sen sijaan käyttävät värillistä taustaa värillisellä reunalla, mikä tekee niistä hienovaraisemman vaihtoehdon, kun haluat, että badge täydentää ympäröivää sisältöä sen sijaan, että se hallitsisi sitä.

Käytä teemaa `setTheme()`-menetelmällä tai konstruktorin kautta.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java']}
height='260px'
/>
<!-- vale on -->

### Mukautettu väri {#custom-color}

Jos sisäänrakennetut teemat eivät vastaa palettiasi, aseta mukautettu siemenväri käyttämällä CSS-ominaisuutta `--dwc-badge-seed`. Tästä yhdestä arvosta badge johdonmukaisesti johdetaan taustan, tekstin ja reunavärin, joten jokainen yhdistelmä pysyy luettavana ilman, että sinun tarvitsee määrittää jokaista erikseen. Tämä tarkoittaa, että voit brändätä badge minkä tahansa värin suunnittelujärjestelmässäsi luottamuksella. Hue, Saturaatio ja Valoisuus (HSL) arvot ovat erityisen käteviä tässä; sävyn vaihtaminen yksin riittää tuottamaan täysin erilaisen väriperheen samalla kun kontrasti säilyy.

```java
Badge badge = new Badge("Mukautettu");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Kokoaminen {#sizing}

Käytä `setExpanse()` hallitaksesi badge-kokoa. Yhdeksän kokoa on saatavilla, vaihdellen `XXXSMALL`-kokoisesta `XXXLARGE`-kokoon, ja oletus on `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java']}
height='300px'
/>
<!-- vale on -->

### Osat ja CSS-muuttujat {#parts-and-css-variables}

<TableBuilder name="Badge" />
