---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: 6537c2d24bfc99dd866f27277ed4f197
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion`-komponentti tarjoaa pystysuoran kokoelman taitettavia paneeleja. Jokaisella paneelilla on napsautettava otsikko, joka vaihtaa sen sisällön näkyvyyttä. `AccordionPanel`-paneelia voidaan käyttää itsenäisenä paljastusosiona tai ryhmitellä osaksi `Accordion`-komponenttia, mikä koordinoi laajentamis- ja supistamiskäyttäytymistä useiden paneelien kesken.

<!-- INTRO_END -->

:::tip Milloin käyttää akneliitä
Aknelit toimivat hyvin usein kysytyissä kysymyksissä, asetussivuilla ja vaiheittaisissa prosesseissa, joissa kaikkien sisältöjen paljastaminen kerralla luo ylivoimaisen käyttöliittymän. Jos osiot ovat yhtä tärkeitä ja käyttäjät hyötyvät niiden näkemisestä samanaikaisesti, harkitse [välilehtiä](/docs/components/tabbedpane) sen sijaan.
:::

## `AccordionPanel` <DocChip chip='name' label="dwc-accordion-panel" /> {#accordion-panel}

`AccordionPanel` on akneli-järjestelmän ydinrakennusosa. Anna konstruktorille merkkijono otsikkoa varten, ja lisää sitten lapsikomponentteja täyttämään runko. Paneeli toimii itsenäisesti ilman ympäröivää `Accordion`-ryhmää, mikä tekee siitä hyödyllisen kevyen paljastuswidgetin, kun tarvitset vain yhden taitettavan osion. Parametriton konstruktori on myös käytettävissä, jos haluat määrittää paneelin kokonaan rakentamisen jälkeen.

```java
// Ainoastaan otsikko - lisää runko erikseen
AccordionPanel panel = new AccordionPanel("Otsikon nimi");
panel.add(new Paragraph("Runko sisältö menee tänne."));

// Otsikko ja runko sisältö annettuna suoraan konstruktorissa
AccordionPanel panel = new AccordionPanel("Otsikko", new Paragraph("Runko sisältö."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Avaminen ja sulkeminen {#opening-and-closing}

Ohjaa auki/suljettu-tilaa ohjelmallisesti milloin tahansa. `isOpened()` on hyödyllinen, kun sinun täytyy lukea nykyinen tila ennen päätöstä toimintatavasta. Esimerkiksi saatat vaihdella paneelia vastakkaiseen tilaan tai ehdollisesti näyttää tai piilottaa muita käyttöliittymän osia.

```java
// Laajenna paneeli
panel.open();

// Supista paneeli
panel.close();

// Palauttaa true, jos paneeli on tällä hetkellä laajennettu
boolean isOpen = panel.isOpened();
```

Käytä `setLabel()`-metodia päivittääksesi otsikkoteksti rakentamisen jälkeen. `setText()` on alias samalle toiminnalle, joten etiketti voidaan pitää synkronoituna dynaamisten tietojen kanssa:

```java
panel.setLabel("Päivitetty etiketti");
```

## Akneliryhmät {#accordion-groups}

Useiden `AccordionPanel`-instanssien pakkaaminen `Accordion`-komponentin sisään luo koordinoidun ryhmän. Oletuksena ryhmä käyttää **yksittäismoodia**: avatessaan yhden paneelin kaikki muut supistuvat automaattisesti, pitäen vain yhden osion näkyvissä kerrallaan. Tämä oletus on tarkoituksellinen, se pitää käyttäjän keskittyneenä yhteen sisältöön ja estää sivun muuttumisen visuaalisesti ylivoimaiseksi, kun paneeleilla on huomattavaa runko-osaa.

Paneelit rakennetaan itsenäisesti ja siirretään `Accordion`-komponenttiin, joten voit määrittää jokaisen ennen ryhmittämistä. Useita erillisiä `Accordion`-instansseja voi myös olla samassa sivussa — jokainen ryhmä hallitsee omaa tilaansa itsenäisesti, joten paneelin laajentaminen yhdessä ryhmässä ei vaikuta toiseen.

```java
AccordionPanel panel1 = new AccordionPanel("Mikä on webforJ?");
AccordionPanel panel2 = new AccordionPanel("Miten ryhmitellyt paneelit toimivat?");
AccordionPanel panel3 = new AccordionPanel("Voinko saada useita ryhmiä?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### Monimutkainen tila {#multiple-mode}

Monimutkainen tila mahdollistaa minkä tahansa määrän paneeleja pysymään laajennettuna samanaikaisesti. Tämä on hyödyllistä, kun käyttäjien on verrattava useiden osioiden sisältöjä kerralla, tai kun jokainen paneeli on niin lyhyt, että useiden laajentaminen kerralla ei luo sekavaa käyttöliittymää.

```java
accordion.setMultiple(true);
```

Monimutkaisen tilan ollessa aktiivinen, kaikki ryhmän paneelit voidaan laajentaa tai supistaa kerralla käyttämällä joukkometodeja:

```java
// Laajenna jokainen paneeli ryhmässä
accordion.openAll();

// Supista jokainen paneeli ryhmässä
accordion.closeAll();
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Yksittäismoodin rajoitus
`openAll()` on saatavilla vain, kun monimutkainen tila on käytössä. Sen kutsuminen yksittäismoodissa ei vaikuta. `closeAll()` toimii molemmissa moodissa.
:::

<!-- vale off -->
## Poissaoleva tila {#disabled-state}
<!-- vale on -->

Yksittäisiä paneeleja voidaan estää estämään käyttäjävuorovaikutus, samalla kun ne pysyvät näkyvinä. Tämä on kätevää lataustilanteissa tai kun tietyt osiot eivät ole ehdollisesti saatavilla, näyttäen paneelin rakenteen ilman aikaisia vuorovaikutuksia. Estetty paneeli, joka oli jo avattu, pysyy laajennettuna, mutta sen otsikkoa ei voi enää napsauttaa sulkeakseen sen. `Accordion`-ryhmän estäminen kohdistaa estetyn tilan kaikkiin sisältyviin paneeleihin kerralla, joten sinun ei tarvitse silmukoida paneeleja erikseen.

```java
// Estä yksi paneeli
panel.setEnabled(false);

// Estä kaikki paneelit ryhmässä
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## Paneelien mukauttaminen {#customizing-panels}

Yksinkertaisten otsikoiden ja avaus/sulkemiskäyttäytymisen lisäksi jokainen `AccordionPanel` tukee rikkaampaa mukauttamista sekä sen otsikkosisällölle että laajentamis/supistamiskeholle.

### Mukautettu otsikko {#custom-header}

Paneelin otsikko renderöi sen etiketin tavallisena tekstinä oletuksena. Käytä `addToHeader()`-metodia korvataksesi tämän tekstin yhdellä tai useammalla komponentilla, mikä tekee ikoni, vyöhyke-ilmoittimet tai muut rikkaat merkintäelementit mukaan lukien paneelin etiketin helposti mahdolliseksi. Tämä on erityisen hyödyllistä kojelaudoilla tai asetuspaneeleissa, joissa kunkin osion otsikon tulee välittää ylimääräistä kontekstiä yhdellä silmäyksellä, kuten kohdemäärä, varoitusmerkki tai suoritusaste, ilman että käyttäjän tarvitsee ensin laajentaa paneelia.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Mukautettu otsikko ikonilla"));
panel.addToHeader(headerContent);
```

:::info Etiketin korvaaminen
`addToHeader()`-metodin kautta lisätty sisältö korvasi täysin oletusarvoisen etikettit tekstin. `setLabel()` ja `setText()` toimivat edelleen yhdessä `addToHeader()`-metodin kanssa, mutta koska otsikkokohta vie visuaalista etusijaa, etikettittekstiä ei näytetä, ellei sitä sisällytetä selkeästi slotattuun sisältöön.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Mukautettu ikoni {#custom-icon}

Laajentamis/sulkemisindikointi oletusarvoisesti näkyy viuhkamaisena kuvana, joka näkyy sekä avoimissa että suljetuissa tiloissa. `setIcon()`-metodia käytetään korvaamaan se millä tahansa [`Icon`](/docs/components/icon) komponentilla, mikä on hyödyllistä brändätyissä ikonisivuissa tai silloin, kun toinen visuaalinen metafora sopii sisältöön paremmin. Nullin välittäminen palauttaa oletusarvoisen viuhkan. `getIcon()` palauttaa tällä hetkellä asetetun ikonin tai null, jos oletusviuhkaa käytetään.

```java
// Korvaa oletusviuhka plus-ikonilla
panel.setIcon(FeatherIcon.PLUS.create());

// Palauta oletusviuhka
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Sisäkkäiset aknelit {#nested-accordions}

Aknelit voidaan sijoittaa muiden akneli-paneelien sisään, mikä on hyödyllistä hierarkkisen sisällön, kuten luokiteltujen asetusten tai monitasoisten navigaatioiden, esittämisessä. Lisää sisempi `Accordion` ulompaan `AccordionPanel`-paneeliin kuten mikä tahansa muu lapsikomponentti. Pidä sisäkkäisyys matalana. Yksi tai kaksi tasoa on yleensä riittävästi. Syvemmät hierarkiat ovat vaikeampia navigoida ja usein osoittavat, että sisällön rakenne tarvitsee pohdintaa.

```java
AccordionPanel innerA = new AccordionPanel("Sisäinen Paneeli A");
AccordionPanel innerB = new AccordionPanel("Sisäinen Paneeli B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Ulkopuolinen Paneeli");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Tapahtumat {#events}

`AccordionPanel` laukaisee tapahtumia joka vaiheessa vaihtojen elinkaaren aikana. Kolme tapahtumatyyppiä kattaa erilaisia hetkiä, joten valitse sen mukaan, milloin logiikkasi tarvitsee suorittaa:

| Tapahtuma | Laukaisee |
|-----------|-----------|
| `AccordionPanelToggleEvent` | Ennen kuin tila muuttuu |
| `AccordionPanelOpenEvent` | Paneelin avauduttua täysin |
| `AccordionPanelCloseEvent` | Paneelin sulkeuduttua täysin |

```java
panel.onToggle(e -> {
    // Laukaisee ennen paneelin tilan muuttumista.
    // e.isOpened() heijastaa siirtymätilaa, ei nykyistä tilaa.
    String direction = e.isOpened() ? "avaamassa" : "sulkemassa";
});

panel.onOpen(e -> {
    // Laukaisee paneelin ollessa täysin avattu — hyvä laiskasti lataavalle sisällölle.
});

panel.onClose(e -> {
    // Laukaisee paneelin ollessa täysin suljettu — hyvä siivoukselle tai yhteenveto päivityksille.
});
```

## Tyylittely {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
