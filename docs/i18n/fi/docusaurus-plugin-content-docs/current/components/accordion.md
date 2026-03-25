---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 560172f4743427476d9ecaadebd1d61d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion`-komponentti tarjoaa pystysuunnassa pinoutuvan joukon suljettavia paneeleja. Jokaisella paneelilla on napsautettava otsikko, joka vaihtaa sen sisällön näkyvyyden. `AccordionPanel` voidaan käyttää itsenäisenä ilmoitusosiona tai ryhmitellä `Accordion`-sisällä hallitsemaan laajentamis- ja sulkemiskäyttäytymistä useiden paneelien välillä.

<!-- INTRO_END -->

:::tip Milloin käyttää akordiota
Akordit toimivat hyvin usein kysyttyjen kysymysten, asetussivujen ja vaiheittaisissa prosesseissa, joissa kaiken sisällön paljastaminen kerralla luo ylivoimaisen asettelun. Jos osiot ovat yhtä tärkeitä ja käyttäjät hyötyvät niiden näkemisestä samanaikaisesti, harkitse [välilehtiä](/docs/components/tabbedpane) sen sijaan.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` on akordi-järjestelmän ydinrakennuspalikka. Siirrä etiketti merkkijono konstruktorille asettaaksesi otsikkotekstin, ja lisää sitten lapsikomponentteja täyttämään runko. Paneeli toimii itsenäisesti ilman ympäröivää `Accordion`-ryhmää, joten se on hyödyllinen kevyt ilmoituswidget, kun tarvitset vain yhden suljettavan osan. Myös argumentiton konstruktori on saatavilla, kun haluat konfiguroida paneelin täysin rakentamisen jälkeen.

```java
// Vain etiketti - lisää runkot sisältö erikseen
AccordionPanel panel = new AccordionPanel("Osion Otsikko");
panel.add(new Paragraph("Rungon sisältö menee tänne."));

// Otsikko ja runkosisältö annettuna suoraan konstruktorissa
AccordionPanel panel = new AccordionPanel("Otsikko", new Paragraph("Rungon sisältö."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Avoin ja suljettu {#opening-and-closing}

Hallinnoi avointa/suljettua tilaa ohjelmallisesti milloin tahansa. `isOpened()` on hyödyllinen, kun sinun on luettava nykyinen tila ennen päätöksentekoa. Esimerkiksi voit vaihtaa paneelin vastakkaiseen tilaan tai ehdollisesti näyttää tai piilottaa muita UI: n osia.

```java
// Laajenna paneelia
panel.open();

// Sulje paneeli
panel.close();                    

// Palauttaa true, jos se on tällä hetkellä laajennettu
boolean isOpen = panel.isOpened();
```

Käytä `setLabel()` päivittääksesi otsikkotekstin rakentamisen jälkeen. `setText()` on alias samalle toiminnalle, joten etiketti voidaan pitää synkronoituna dynaamisten tietojen kanssa:

```java
panel.setLabel("Päivitetty Etiketti");
```

## Akordi-ryhmät {#accordion-groups}

Useiden `AccordionPanel`-esiintymien pakkaaminen `Accordion`-sisälle luo koordinoidun ryhmän. Oletusarvoisesti ryhmä käyttää **yksittäismoodia**: yhden paneelin avaaminen sulkee automaattisesti kaikki muut, pitäen vain yhden osion näkyvissä kerrallaan. Tämä oletus on tarkoituksellinen, se pitää käyttäjän keskittyneenä yhteen sisältöosaan ja estää sivun tulemasta visuaalisesti ylivoimaiseksi, kun paneeleilla on merkittävä runkosisältö.

Paneelit rakennetaan itsenäisesti ja siirretään `Accordion`:lle, joten voit konfiguroida jokaisen ennen niiden ryhmittelyä. Useita erillisiä `Accordion`-esiintymiä voi myös olla samalla sivulla — jokainen ryhmä hallitsee omaa tilaansa itsenäisesti, joten paneelin laajentaminen yhdessä ryhmässä ei vaikuta toiseen.

```java
AccordionPanel panel1 = new AccordionPanel("Mikä on webforJ?");
AccordionPanel panel2 = new AccordionPanel("Miten ryhmitellyt paneelit toimivat?");
AccordionPanel panel3 = new AccordionPanel("Voinko omistaa useita ryhmiä?");

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

Monimutkainen tila sallii minkä tahansa määrän paneeleja pysyä laajennettuina samanaikaisesti. Tämä on hyödyllistä, kun käyttäjien on verrattava useiden osien sisältöä kerralla, tai kun jokainen paneeli on tarpeeksi lyhyt, ettei useiden avaaminen kerralla luo sotkuista asettelua.

```java
accordion.setMultiple(true);
```

Monimutkaisella tilalla kaikki ryhmän paneelit voidaan laajentaa tai sulkea kerralla käyttämällä massamenetelmiä:

```java
// Laajenna kaikki paneelit ryhmässä
accordion.openAll();

// Sulje kaikki paneelit ryhmässä
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Yksittäismoodin rajoitus
`openAll()` on käytettävissä vain, kun monimutkainen tila on käytössä. Sen kutsuminen yksittäismoodissa ei vaikuta. `closeAll()` toimii molemmissa moodissa.
:::

<!-- vale off -->
## Poistettu tila {#disabled-state}
<!-- vale on -->

Yksittäisiä paneeleja voidaan poistaa käytöstä estämään käyttäjäinteraktiot samalla, kun ne pysyvät näkyvissä. Tämä on kätevää lataustilanteissa tai kun tietyt osastot ovat ehdollisesti saatavilla, näyttäen paneelirakenteen ilman, että sallit ennenaikaista vuorovaikutusta. Poistettu paneeli, joka oli jo avoinna, pysyy laajennettuna, mutta sen otsikkoa ei voi enää napsauttaa sen sulkemiseksi. `Accordion`-ryhmän poistaminen käytöstä soveltaa poistettua tilaa kaikkiin sisältämiinsä paneeleihin yhdellä kertaa, joten sinun ei tarvitse silmukoida paneeleja erikseen.

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

Etikettien ja perus avaus/sulku käyttäytymisen lisäksi, jokainen `AccordionPanel` tukee rikkaampaa mukauttamista sekä otsikkosisällölle että laajentamis/sulkemis kuvakkeelle.

### Mukautettu otsikko {#custom-header}

Paneelin otsikko näyttää sen etiketin tavallisena tekstinä oletuksena. Käytä `addToHeader()` korvataksesi tämän tekstin mihin tahansa komponenttiin tai komponenttien yhdistelmään, tehden helpoksi sisällyttää kuvakkeita, merkkilippuja, tilan indikaattoreita tai muuta rikasta merkintää paneeliotsikon viereen. Tämä on erityisen hyödyllistä kojelaudoissa tai asetuspaneelissa, jossa jokaisen osion otsikon on välitettävä ylimääräistä kontekstiä yhdellä vilkaisulla, kuten kohden määrä, varoitusmerkki tai valmistumisen tila, ilman että käyttäjän tarvitsee laajentaa paneelia ensin.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Mukautettu otsikko kuvakkeella"));
panel.addToHeader(headerContent);
```

:::info Etikettien korvaaminen
Sisältö, joka lisätään `addToHeader()` kautta, korvataan täysin oletusetiketin tekstillä. `setLabel()` ja `setText()` toimivat edelleen yhdessä `addToHeader()` kanssa, mutta koska otsikkotila vie visuaalista etusijaa, etikettitekstiä ei näytetä, ellei sisällytät sitä nimenomaisesti slottiin.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Mukautettu kuvake {#custom-icon}

Laajentamis/sulkemis-indikaattori oletuksena on Chevron, joka on näkyvissä sekä avatuissa että suljetuissa tiloissa. `setIcon()` korvataan se millä tahansa [`Icon`](/docs/components/icon) komponentilla, joka on hyödyllinen brändätyssä ikonografiassa tai kun eri visuaalinen metafora sopii sisältöön paremmin. Antamalla `null` palauttaa oletuschevronin. `getIcon()` palauttaa tällä hetkellä asetetun kuvakkeen tai `null`, jos oletuschevron on käytössä.

```java
// Korvataan oletuschevron plus-ikonilla
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

## Sisäkkäiset akordit {#nested-accordions}

Akordit voidaan sijoittaa muiden akordipaneelien sisään, mikä on hyödyllistä hierarkkisen sisällön, kuten kategorisoitujen asetusten tai monitasoisen navigoinnin edustamiseksi. Lisää sisäinen `Accordion` ulomman `AccordionPanel`:n sisälle kuten mikä tahansa muu lapsikomponentti. Pidä sisäkkäisyys matalana. Yksi tai kaksi tasoa riittää yleensä. Syvä hierarkia on usein vaikeampi navigoida, ja se signaloi usein, että sisällön rakenne itsessään tarvitsee uudelleensuunnittelua.

```java
AccordionPanel innerA = new AccordionPanel("Sisäinen Paneeli A");
AccordionPanel innerB = new AccordionPanel("Sisäinen Paneeli B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Ulkoinen Paneeli");
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

`AccordionPanel` laukaisee tapahtumia jokaisessa vaihteenvaiheessa. Kolme tapahtumatyyppiä kattaa erilaisia hetkiä, joten valitse sen mukaan, milloin logiikkasi tarvitsee suorittaa:

| Tapahtuma | Laukaisee |
|-------|-------|
| `AccordionPanelToggleEvent` | Ennen kuin tila muuttuu |
| `AccordionPanelOpenEvent` | Sen jälkeen, kun paneeli on täysin avattu |
| `AccordionPanelCloseEvent` | Sen jälkeen, kun paneeli on täysin suljettu |

```java
panel.onToggle(e -> {
    // Laukaisee ennen paneelin tilan muutosta.
    // e.isOpened() heijastaa tilaa, johon siirrytään, ei nykyistä tilaa.
    String direction = e.isOpened() ? "avautuu" : "sulkeutuu";
});

panel.onOpen(e -> {
    // Laukaisee sen jälkeen, kun paneeli on täysin auki — hyvä sisällön laiskalataukseen.
});

panel.onClose(e -> {
    // Laukaisee sen jälkeen, kun paneeli on täysin suljettu — hyvä siivoustai yhteenveto-päivityksiin.
});
```

## Tyylittely {#styling}

<TableBuilder name="AccordionPanel" />
