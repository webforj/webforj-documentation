---
title: Card
sidebar_position: 17
sidebar_class_name: new-content
description: >-
  Group related content and actions with the Card component, including slotted
  regions. orientation, elevation, dividers, and click handling.
_i18n_hash: 08b0239bc5bbeb0b14f3b03dda7b8b17
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-card" />
<DocChip chip='since' label='26.02' />
<JavadocLink type="card" location="com/webforj/component/card/Card" top='true'/>

`Card`-komponentti tarjoaa alustan, jolla ryhmitellään liittyvää sisältöä ja toimintoja yhdeksi itemiksi. Se tukee erikoisalueita kuvalle, otsikolle, sisällölle ja alatunnisteelle sekä suuntaa, korkeutta, jakajia ja tiheysasetuksia, jotka säätelevät kortin esitystä.

<!-- INTRO_END -->

## Kortin luominen {#creating-a-card}

Luo `Card` välittämällä sisältö sen konstruktorille, joka asettaa tämän sisällön kortin kehoon. Kehon voi myös täyttää luomisen jälkeen `add()` tai `addToBody()` avulla, jotka tekevät samaa.

```java
Card card = new Card(new Paragraph("Myynti kasvoi kaikilla alueilla."));

// Vastaava
Card card = new Card();
card.addToBody(new Paragraph("Myynti kasvoi kaikilla alueilla."));
```

Tyhjällä `Card`:lla näkyy vain sen kehys, ei mitään muuta.

## Kortin alueet {#card-regions}

Jokainen alue, paitsi keho, täytetään sen omalla slotilla, ja alue, jonka slotissa ei ole sisältöä, ei näy. `Card`, jolla ei ole alatunnistetta, sulkeutuu kehon jälkeen, ja `Card`, jolla on vain keho, on kehystetty sisältölohko.

- `addToFigure()` pitää kortin kuvan, kuten kuvan, videon tai kaavion. Sen sijainti riippuu kortin suunnasta.
- `addToIcon()` asettaa johtavan visualin otsikkoriviin ja hyväksyy minkä tahansa komponentin, mukaan lukien `Icon` tai `Avatar`.
- `addToTitle()` asettaa otsikon otsikkoriviin.
- `addToCaption()` lisää toisen rivin otsikon alle, mikä on hyödyllistä päivämäärälle, kirjoittajalle tai statukselle.
- `addToHeaderActions()` täyttää otsikkorivin lopun, yleensä `Button`- tai valikkotoiminnolla.
- `addToFooter()` sulkee `Card`:n, yleensä toiminnolla tai metatiedoilla.

```java
Card card = new Card(new Paragraph("Myynti kasvoi kaikilla alueilla."));
card.addToFigure(new Img("cover.png", "Raportin kansi"))
    .addToIcon(TablerIcon.create("chart-bar"))
    .addToTitle(new H3("Kuukausiraportti"))
    .addToCaption(new Paragraph("Heinäkuu 2026"))
    .addToHeaderActions(new Button("Jaa"))
    .addToFooter(new Button("Lue lisää"));
````

:::info Otsikko ja saavutettava nimi
`Card` ilmoittaa itsestään alueena, ja otsikosta tulee sen saavutettava nimi. Käytä siellä otsikkoelementtiä, kuten `H3`, jotta ruudunlukijan käyttäjät voivat löytää `Card`:n sivun otsikkorakenteen kautta.
:::

<ComponentDemo
path='/webforj/cardregions'
files={[
  'src/main/java/com/webforj/samples/views/card/CardRegionsView.java',
  'src/main/frontend/css/card/cardRegions.css',
]}
height='700px'
/>

## Suunta {#orientation}

Suunta säätelee, missä kuvasto sijaitsee suhteessa muihin alueisiin, ja se asetetaan `setOrientation()` avulla.

Kortit ovat oletuksena pystyssä, joten ne pinotaan kuvasto ylöspäin otsikon, sisällön ja alatunnisteen ylle. Tämä soveltuu kortteihin, jotka on järjestetty ruudukkoon, jossa jokainen vie kapean sarakkeen. Jos `setOrientation()`-metodille välitetään `Card.Orientation.HORIZONTAL`, kortista tulee vaakasuora, jolloin kuvasto sijoitetaan vierekkäin näiden alueiden kanssa.

```java
card.setOrientation(Card.Orientation.HORIZONTAL);
```

<ComponentDemo
path='/webforj/cardorientation'
files={['src/main/java/com/webforj/samples/views/card/CardOrientationView.java']}
height='500px'
/>

Koska asetus liikuttaa vain kuvastoa eikä mitään muuta, `Card`, jolla ei ole kuvastoa, näyttää samalta kummassakin suunnassa.

## Korkeus ja reuna {#elevation-and-border}

Kaksi asetusta määrittää, kuinka kauas `Card` erottuu sen taustalla olevasta sivusta. `setShadow()` soveltaa arvoa varjopisteasteikosta, joka vaihtelee `NONE`:sta `XSMALL`:iin, `SMALL`:iin, `MEDIUM`:iin, `LARGE`:iin ja `XLARGE`:iin aina `XXLARGE`:iin. `setBorderless()` säätelee, piirtääkö `Card` reunansa. Oletusarvot ovat `Shadow.XSMALL` ja reuna piirretty.

Asetukset ovat riippumattomia, joten mitä tahansa varjoa voidaan yhdistää reunalla tai ilman.

<ComponentDemo
path='/webforj/cardappearance'
files={[
  'src/main/java/com/webforj/samples/views/card/CardAppearanceView.java']}
height='300px'
/>

## Jakajat ja laajus {#dividers-and-expanse}

Vaikka korkeus- ja reunasetukset säätelevät, kuinka `Card` istuu sivua vasten, jakajat ja laajus säätelevät luettavia alueita itse kortissa.

`setDivided(true)` piirtää jakajan otsikon jälkeen ja alatunnisteen eteen, mikä auttaa, kun alueet sisältävät tiheää sisältöä. Jakajat ovat oletusarvoisesti pois päältä. Jakaja alueelle, jossa ei ole sisältöä, ei näy, joten jaoteltu kortti ilman alatunnistetta näyttää vain yhden jakajan, otsikon alla. Jakajat ovat painavampia tasaisilla korteilla, joissa ei ole kehystä, joka tekisi sen työn.

`setExpanse()` säätelee tiheyttä, ohjaamalla pehmustetta, alueiden väliin jäävää tilaa ja otsikon sekä kuvauksen kokoa. `Card` käyttää jaettua `Expanse`-enumeraatiota, joka tarjoaa `NONE`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE` ja `XLARGE`, jossa `MEDIUM` on oletus. Pienemmät laajudet sopivat hallintapaneelin laatikoihin ja sivupalkkeihin, joissa useat kortit jakavat näytön.

Seuraava esimerkki näyttää kaksi `Card`-komponenttia jakajilla. Yksi `Card` käyttää `Expanse.LARGE`, kun taas toinen käyttää `Expanse.SMALL`:

<ComponentDemo
path='/webforj/carddensity'
files={[
  'src/main/java/com/webforj/samples/views/card/CardDensityView.java',
  'src/main/frontend/css/card/cardDensity.css',
]}
height='400px'
/>

## Napsautustapahtumat {#click-events}

`Card`-komponentti toteuttaa `HasElementClickListener`, joten kuuntelija, joka on rekisteröity `onClick()` tai `addClickListener()` avulla, vastaanottaa `ElementClickEvent`:n. Tämä tekee koko pinnasta yhden kohteen.

```java
card.onClick(event -> Router.getCurrent().navigate(new Location("/reports/july")));
```

:::warning Klikkaukset `Card`:n sisällä
Klikkaukset komponentteihin, jotka sijaitsevat `Card`:n sisällä, saavuttavat myös `Card`:n, joten `Card`, jolla on oma kuuntelija, laukaisee sen, kun käyttäjä painaa `Button`:ia otsikkotoiminnoissa tai alatunnisteessa. Lisää kuuntelija `Card`:lle, kun `Card`:lla on yksi selkeä toiminto, ja varaa sisällä olevat painikkeet toiminnoille, joita `Card` itse ei suorita.
:::

## Tyylitys {#styling}

<TableBuilder name="Card" />
