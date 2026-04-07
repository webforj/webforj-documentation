---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 99f4482faa552334ce209b3f9296f4f5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion`-komponentti tarjoaa pystysuoran nipun taitettavia paneeleja. Jokaisella paneelilla on napsautettava otsikko, joka kytkee sen sisällön näkyvyyden. `AccordionPanel`-paneelia voidaan käyttää itsenäisenä paljastusosiota, tai ryhmittää `Accordion`-komponentin sisälle koordinoimaan laajentamisen ja supistamisen käyttäytymistä useiden paneelien välillä.

<!-- INTRO_END -->

:::tip Milloin käyttää akordeonia
Akordeonit toimivat hyvin usein kysytyissä kysymyksissä, asetussivuilla ja vaiheittaisissa prosesseissa, joissa kaiken sisällön kerralla paljastaminen luo ylivoimaisen asettelun. Jos osiot ovat yhtä tärkeitä ja käyttäjät hyötyvät niiden näkyvistä yhtä aikaa, harkitse sen sijaan [välilehtiä](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` on akordeonijärjestelmän ydinkomponentti. Siirrä merkkijonon otsikko konstruktorille asettaaksesi otsikkotekstin, ja lisää sitten lapsikomponentit täyttämään runko. Paneeli toimii itsenäisesti ilman ympäröivää `Accordion`-ryhmää, joten se on hyödyllinen kevyt paljastusominaisuus, kun tarvitset vain yhden taitettavan osion. Myös argumentiton konstruktori on saatavilla, kun haluat määrittää paneelin kokonaan rakennuksen jälkeen.

```java
// Vain otsikko - lisää runkosisältö erikseen
AccordionPanel panel = new AccordionPanel("Osion otsikko");
panel.add(new Paragraph("Runko sisältö menee tänne."));

// Otsikko ja runkosisältö annetaan suoraan konstruktorissa
AccordionPanel panel = new AccordionPanel("Otsikko", new Paragraph("Runko sisältö."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Avaaminen ja sulkeminen {#opening-and-closing}

Ohjaa avointa/suljettua tilaa ohjelmallisesti milloin tahansa. `isOpened()` on hyödyllinen, kun sinun on luettava nykytila ennen päätöksentekoa. Esimerkiksi saatat kytkeä paneelin vastakkaiseen tilaan tai näyttö- tai piilottaa muita osia käyttöliittymässä ehtojesi mukaan.

```java
// Laajenna paneeli
panel.open();

// Supista paneeli
panel.close();                    

// Palauttaa true, jos on tällä hetkellä laajennettu
boolean isOpen = panel.isOpened();
```

Käytä `setLabel()` päivittääksesi otsikkotekstin rakennuksen jälkeen. `setText()` on alias samalle toiminnolle, joten etiketti voidaan pitää synkronoituna dynaamisten tietojen kanssa:

```java
panel.setLabel("Päivitetty etiketti");
```

## Akordeoniryhmät {#accordion-groups}

Kierittämällä useita `AccordionPanel`-instansseja `Accordion`-komponenttiin luodaan koordinoitu ryhmä. Oletusarvoisesti ryhmä käyttää **yksittäismodea**: avattaessa yksi paneeli, kaikki muut automaattisesti supistuvat, pitäen vain yhden osion näkyvissä kerrallaan. Tämä oletus on tarkoituksellinen, sillä se pitää käyttäjän keskittyneenä yhteen sisältöpalaseseen ja estää sivun näkymästä visuaalisesti ylivoimaiselta, kun paneeleilla on merkittäviä runkosisältöjä.

Paneelit rakennetaan itsenäisesti ja siirretään `Accordion`-komponenttiin, joten voit määrittää jokaisen erikseen ryhmittäessäsi niitä. Useita erillisiä `Accordion`-instansseja voi myös olla samassa sivussa — jokainen ryhmä hallitsee omaa tilaansa itsenäisesti, joten paneelin laajentaminen yhdessä ryhmässä ei vaikuta toiseen.

```java
AccordionPanel panel1 = new AccordionPanel("Mikä on webforJ?");
AccordionPanel panel2 = new AccordionPanel("Miten ryhmitetyt paneelit toimivat?");
AccordionPanel panel3 = new AccordionPanel("Voinko olla useita ryhmiä?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Monimutkainen tila {#multiple-mode}

Monimutkainen tila sallii minkä tahansa määrän paneeleita olla laajennettuina samaan aikaan. Tämä on hyödyllistä, kun käyttäjät tarvitsevat verrata useiden osioiden sisältöä kerralla, tai kun jokainen paneeli on niin lyhyt, että useiden laajentaminen kerralla ei luo sotkuista asettelua.

```java
accordion.setMultiple(true);
```

Kun monimutkainen tila on aktiivinen, kaikki ryhmän paneelit voidaan laajentaa tai supistaa kerralla käyttämällä massamenetelmiä:

```java
// Laajenna kaikki paneelit ryhmässä
accordion.openAll();

// Supista kaikki paneelit ryhmässä
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Yksittäistilan rajoitus
`openAll()` on saatavilla vain, kun monimutkainen tila on käytössä. Sen kutsuminen yksittäistilassa ei vaikuta. `closeAll()` toimii molemmissa tiloissa.
:::

<!-- vale off -->
## Poistettu tila {#disabled-state}
<!-- vale on -->

Yksittäiset paneelit voidaan poistaa käytöstä estämään käyttäjävuorovaikutus samalla, kun ne pysyvät näkyvissä. Tämä on hyödyllistä lataustiloissa tai kun tietyt osiot ovat ehtoisesti käytettävissä, näyttäen paneelirakenteen ilman aikaisempaa vuorovaikutusta. Poistettu paneeli, joka on jo avattu, pysyy laajennettuna, mutta sen otsikkoa ei enää voi napsauttaa sen supistamiseksi. Jos `Accordion`-ryhmää poistetaan käytöstä, estotila koskee kaikkia siihen liittyviä paneeleja kerralla, jolloin sinun ei tarvitse silmukoida paneeleita erikseen.

```java
// Poista käytöstä yksi paneeli
panel.setEnabled(false);

// Poista käytöstä kaikki paneelit ryhmässä
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='600px'
/>
<!-- vale on -->

## Paneelien mukauttaminen {#customizing-panels}

Yli etikettien ja perusavaamisen/sulkemisen käyttäytymisen, jokainen `AccordionPanel` tukee rikkaampaa mukauttamista sekä sen otsikkosisältöön että laajentamissulkuikoniin.

### Mukautettu otsikko {#custom-header}

Paneelin otsikko renderöi sen etiketin tavallisena tekstinä oletusarvoisesti. Käytä `addToHeader()` korvataksesi tämän tekstin minkä tahansa komponentin tai komponenttien yhdistelmällä, jolloin on helppoa sisällyttää kuvakkeita, merkintöjä, tilanäyttäjiä tai muita rikkaita merkintöjä paneelin etiketin viereen. Tämä on erityisen hyödyllistä hallintapaneeleissa tai asetuspaneeleissa, joissa jokaisen osion otsikon on tarkoitus välittää lisäkonteksti nopeasti, kuten kohteen määrä, varoitusmerkki tai valmistumisen tila, ilman, että käyttäjän tarvitsee ensin laajentaa paneelia.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Mukautettu otsikko kuvakkeella"));
panel.addToHeader(headerContent);
```

:::info Etiketin korvaus
`addToHeader()`-menetelmällä lisätty sisältö korvata täydellisesti oletusetikettitekstin. `setLabel()` ja `setText()` jatkavat toimimista yhdessä `addToHeader()`-menetelmän kanssa, mutta koska otsikkoslotilla on visuaalinen etuoikeus, etiketin teksti ei näy, ellei sitä sisällytetä erikseen slottisissältöön.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Mukautettu ikoni {#custom-icon}

Laajentamisen/sulkemisen indikaattori oletusarvoisesti on chevron, joka on näkyvissä sekä avoimissa että suljetuissa tiloissa. `setIcon()` korvataan se millä tahansa [`Icon`](/docs/components/icon) komponentilla, mikä on hyödyllistä brändätyssä ikonografiassa tai kun eri visuaalinen metafora sopii paremmin sisältöön. Siirtäminen `null` palauttaa oletuschevronin. `getIcon()` palauttaa tällä hetkellä asetetun ikon, tai `null`, jos oletuschevron käytössä.

```java
// Korvaa oletuschevron plus-ikonilla
panel.setIcon(FeatherIcon.PLUS.create());

// Palauta oletuschevron
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Kuoalla olevat akordeonit {#nested-accordions}

Akordeoneita voidaan upottaa toisten akordeonipaneelien sisään, mikä on hyödyllistä hierarkkisen sisällön esittämisessä, kuten luokitelluissa asetuksissa tai monitasoisessa navigoinnissa. Lisää sisäinen `Accordion` ulkoiseen `AccordionPanel`-komponenttiin kuten mihin tahansa muuhun lapsikomponenttiin. Säilytä upotukset matalina. Yksi tai kaksi tasoa on yleensä riittävästi. Syvemmät hierarkiat ovat yleensä vaikeampia navigoida ja usein viittaavat siihen, että sisällön rakenne itse tarvitsee uudelleenarviointia.

```java
AccordionPanel innerA = new AccordionPanel("Sisäinen paneeli A");
AccordionPanel innerB = new AccordionPanel("Sisäinen paneeli B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Ulompi paneeli");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Tapahtumat {#events}

`AccordionPanel` lähettää tapahtumia jokaisessa vaiheessa kytkentäelämässä. Kolme tapahtumatyyppiä kattaa erilaisia hetkiä, joten valitse sen mukaan, milloin logiikkasi on suoritettava:

| Tapahtuma | Lähettää |
|-----------|----------|
| `AccordionPanelToggleEvent` | Ennen kuin tila muuttuu |
| `AccordionPanelOpenEvent` | Kun paneeli on täysin avattu |
| `AccordionPanelCloseEvent` | Kun paneeli on täysin suljettu |

```java
panel.onToggle(e -> {
    // Lähettää ennen paneelin tilan muuttumista.
    // e.isOpened() heijastaa tilaa, johon siirrytään, ei nykyistä tilaa.
    String direction = e.isOpened() ? "avataan" : "suljetaan";
});

panel.onOpen(e -> {
    // Lähettää, kun paneeli on täysin avoinna — hyvä laiskalata sisältöä.
});

panel.onClose(e -> {
    // Lähettää, kun paneeli on täysin suljettu — hyvä siivoukselle tai yhteenveto päivityksille.
});
```

## Tyylittely {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
