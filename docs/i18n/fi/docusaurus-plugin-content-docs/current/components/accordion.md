---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: b11e2d2ef8854f757454635c984da1d6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` komponentti tarjoaa pystysuunnassa asetellun joukon romahtavia paneeleja. Jokaisella paneelilla on napsautettava otsikko, joka vaihtaa sen sisällön näkyvyyttä. `AccordionPanel` voidaan käyttää itsenäisenä paljastusseksiona tai ryhmitellä `Accordion`-komponentin sisälle, jotta useiden paneelien avautumista ja sulkemista voidaan koordinoida.

<!-- INTRO_END -->

:::tip Milloin käyttää akkordeonia
Akkordeonit toimivat hyvin useimmin kysytyissä kysymyksissä (FAQ), asetussivuilla ja vaiheittaisissa prosesseissa, joissa kaikkien sisältöjen kerralla paljastaminen olisi ylivoimaista. Jos osiot ovat yhtä tärkeitä ja käyttäjät hyötyvät niiden näkemisestä samaan aikaan, harkitse [välilehtiä](/docs/components/tabbedpane) sen sijaan.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` on akkordeon järjestelmän keskeinen rakennuspalikka. Anna konstruktorille etikettimerkkijono asettaaksesi otsikon tekstin, ja lisää sitten lapsikomponentteja täyttämään runkoa. Paneeli toimii itsenäisesti ilman ympäröiviä `Accordion`-ryhmiä, mikä tekee siitä hyödyllisen kevyen paljastuswidgetin, kun tarvitset vain yhden romahtavan osan. Argumenttitonta konstruktoria on myös saatavilla, kun haluat konfiguroida paneelia täysin rakentamisen jälkeen.

```java
// Vain etiketti - lisää runkosisältö erikseen
AccordionPanel panel = new AccordionPanel("Otsikon nimi");
panel.add(new Paragraph("Runko-osa menee tänne."));

// Etiketti ja runkosisältö annettu suoraan konstruktorissa
AccordionPanel panel = new AccordionPanel("Otsikko", new Paragraph("Runko-osa."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Avaa ja sulje {#opening-and-closing}

Ohjaa avattua/suljettua tilaa ohjelmallisesti milloin tahansa. `isOpened()` on hyödyllinen, kun sinun täytyy lukea nykyinen tila ennen kuin päätät, mitä tehdä. Esimerkiksi, voit kääntää paneelia vastakkaiseen tilaan tai ehdollisesti näyttää tai piilottaa muita käyttöliittymän osia.

```java
// Laajenna paneeli
panel.open();

// Romauta paneeli
panel.close();

// Palauttaa true, jos se on nykyisin laajennettu
boolean isOpen = panel.isOpened();
```

Käytä `setLabel()` päivittääksesi otsikon tekstin rakentamisen jälkeen. `setText()` on alias samalle toiminnolle, joten etiketti voidaan pitää synkronoituna dynaamisten tietojen kanssa:

```java
panel.setLabel("Päivitetty etiketti");
```

## Akkordeoni ryhmät {#accordion-groups}

Useiden `AccordionPanel`-instanssien kääntäminen `Accordion`-komponentin sisälle luo koordinoidun ryhmän. Oletusarvoisesti ryhmä käyttää **yksittäismoodia**: yhden paneelin avaaminen romuttaa automaattisesti kaikki muut, pitäen vain yhden osion näkyvissä kerralla. Tämä oletus on tarkoituksellinen, se pitää käyttäjän keskittyneenä yhteen sisältöpalasia ja estää sivua tulemasta visuaalisesti ylivoimaiseksi, kun paneeleilla on merkittävää runkosisältöä.

Paneelit rakennetaan itsenäisesti ja annetaan `Accordion`-ryhmälle, joten voit konfiguroida jokaisen ennen ryhmittelyä. Useita erillisiä `Accordion`-instansseja voi myös olla samalla sivulla - jokainen ryhmä hallitsee omaa tilaansa itsenäisesti, joten yhden ryhmän paneelin avaaminen ei vaikuta toiseen.

```java
AccordionPanel panel1 = new AccordionPanel("Mikä on webforJ?");
AccordionPanel panel2 = new AccordionPanel("Kuinka ryhmitellyt paneelit toimivat?");
AccordionPanel panel3 = new AccordionPanel("Voinko olla useita ryhmiä?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### Moninkertainen tila {#multiple-mode}

Moninkertainen tila sallii minkä tahansa rivin paneeleja pysyä laajennettuna samanaikaisesti. Tämä on hyödyllistä, kun käyttäjät tarvitsevat vertailla useiden osioiden sisältöä kerralla, tai kun jokainen paneeli on tarpeeksi lyhyt siten, että useiden samanaikainen laajentaminen ei luo sotkuista asettelua.

```java
accordion.setMultiple(true);
```

Moninkertaisen tilan ollessa aktiivinen, kaikki paneelit ryhmässä voidaan laajentaa tai romuttaa samanaikaisesti käyttämällä joukkomenetelmiä:

```java
// Laajenna jokainen paneeli ryhmässä
accordion.openAll();

// Romuta jokainen paneeli ryhmässä
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
`openAll()` on vain saatavilla, kun moninkertainen tila on käytössä. Sen kutsuminen yksittäismoodissa ei vaikuta. `closeAll()` toimii molemmissa moodissa.
:::

<!-- vale off -->
## Poissa käytöstä oleva tila {#disabled-state}
<!-- vale on -->

Yksittäisiä paneeleja voidaan laittaa pois käytöstä, jotta käyttäjäinteraktio estetään samalla, kun ne pysyvät näkyvissä. Tämä on kätevää lataustiloissa tai kun tietyt osiot eivät ole ehdollisesti saatavilla, näyttäen paneelirakenteen ilman, että mahdollistetaan ennenaikainen vuorovaikutus. Poissa käytöstä oleva paneeli, joka oli jo avoinna, pysyy laajennettuna, mutta sen otsikkoa ei voi enää napsauttaa romuttaakseen sitä. `Accordion`-ryhmän poistaminen käytöstä soveltaa poistettua tilaa kaikkiin siihen sisältyviin paneeleihin kerralla, joten sinun ei tarvitse käydä paneeleja yksittäin läpi.

```java
// Poista käytöstä yksittäinen paneeli
panel.setEnabled(false);

// Poista käytöstä kaikki paneelit ryhmässä
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

Yli etikettien ja perus avaus/sulkemiskäyttäytymisen, jokainen `AccordionPanel` tukee rikkaampaa mukauttamista sekä sen otsikkosisällön että avautumisen/sulkeutumisen kuvaketta.

### Mukautettu otsikko {#custom-header}

Paneelin otsikko näyttää oletusarvoisesti sen etiketin tavallisena tekstinä. Käytä `addToHeader()` -menetelmää korvataksesi teksti minkä tahansa komponentin tai komponenttien yhdistelmän, mikä tekee siitä helppoa lisätä kuvakkeita, merkkejä, tilan indikaattoreita tai muuta rikkaita merkintöjä paneelin etiketin rinnalle. Tämä on erityisen hyödyllistä hallintapaneeleissa tai asetuspaneeleissa, joissa kunkin osan otsikon tulee antaa lisäkonteksti yhdellä silmäyksellä, kuten kohteiden määrä, varoitusmerkki tai valmistumisen tila, ilman että käyttäjän tarvitsee ensin avata paneelia.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Mukautettu otsikko kuvakkeella"));
panel.addToHeader(headerContent);
```

:::info Etiketin korvaaminen
`addToHeader()` -menetelmällä lisätty sisältö korvasi täysin oletustekstin. `setLabel()` ja `setText()` jatkavat toimimista `addToHeader()` kanssa, mutta koska otsikkotila saa visuaalisen etusijan, etikettitekstiä ei näytetä, ellei sitä sisällytetä nimenomaisesti slotattuun sisältöön.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Mukautettu kuvake {#custom-icon}

Laajene/sulkeutuva indikaattori on oletusarvoisesti chevron, joka on näkyvissä niin auki kuin suljetussa tilassa. `setIcon()` korvasi sen minkä tahansa [`Icon`](/docs/components/icon) komponentilla, joka on hyödyllinen brändättyyn kuvastoon tai kun erilainen visuaalinen metsästys sopii sisältöön paremmin. Antamalla `null` palauttaa oletus chevroadin. `getIcon()` palauttaa nykyiseen asetettuun kuvakkeen tai `null`, jos oletus chevroad on käytössä.

```java
// Korvata oletus chevroad plus-kuvakkeella
panel.setIcon(FeatherIcon.PLUS.create());

// Palauta oletus chevroad
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Sisäkkäiset akkordeonit {#nested-accordions}

Akkordeoneja voidaan upottaa muiden akkordeonipaneelien sisään, mikä on hyödyllistä hierarkkisen sisällön esittämisessä, kuten luokitelluissa asetuksissa tai monitasoisessa navigoinnissa. Lisää sisäinen `Accordion` ulkoiseen `AccordionPanel`:iin kuten mikä tahansa muu lapsikomponentti. Pidä upotukset matalina. Yksi tai kaksi tasoa on yleensä riittävästi. Syvemmät hierarkiat ovat yleensä vaikeampia navigoida ja usein tarkoittavat, että sisältörakenne tarvitsee uudelleenarviointia.

```java
AccordionPanel innerA = new AccordionPanel("Sisäinen Paneeli A");
AccordionPanel innerB = new AccordionPanel("Sisäinen Paneeli B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Ulko Paneeli");
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

`AccordionPanel` laukaisee tapahtumia jokaisessa kytkentäelämänvaiheessa. Kolme tapahtumatyyppiä kattaa erilaiset hetket, joten valitse sen mukaan, milloin logiikkasi tarvitsee suoritettavaksi:

| Tapahtuma | Laukaisee |
|-------|-------|
| `AccordionPanelToggleEvent` | Ennen kuin tila muuttuu |
| `AccordionPanelOpenEvent` | Sen jälkeen, kun paneeli on täysin avattu |
| `AccordionPanelCloseEvent` | Sen jälkeen, kun paneeli on täysin suljettu |

```java
panel.onToggle(e -> {
    // Laukaisee ennen kuin paneeli vaihtaa tilaansa.
    // e.isOpened() heijastaa siirtymään siirrettyä tilaa, ei nykyistä tilaa.
    String direction = e.isOpened() ? "avaaminen" : "sulkeminen";
});

panel.onOpen(e -> {
    // Laukaisee sen jälkeen, kun paneeli on täysin avoinna — hyvä laiskasti latautuville sisällöille.
});

panel.onClose(e -> {
    // Laukaisee sen jälkeen, kun paneeli on täysin suljettu — hyvä siivous- tai yhteenvetopäivityksille.
});
```

## Tyylitys {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
