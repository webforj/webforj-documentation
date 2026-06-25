---
sidebar_position: 1
title: Accordion
_i18n_hash: 207c70347cc18d88661a8a9279988417
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion`-komponentti tarjoaa pystysuoraan pinottujen, suljettavien paneelien setin. Jokaisella paneelilla on klikkaamalla pystyttävä otsikko, joka vaihtaa sen sisällön näkyvyyttä. `AccordionPanel` voidaan käyttää itsenäisenä paljastusosiona tai ryhmitellä `Accordion`in sisälle laajentamaan ja supistamaan käyttäytymistä useiden paneelien kesken.

<!-- INTRO_END -->

:::tip Milloin käyttää akordiota
Akordit toimivat hyvin usein kysytyissä kysymyksissä, asetussivuilla ja vaiheittaisissa prosesseissa, joissa kaiken sisällön paljastaminen kerralla voisi luoda ylivoimaisen asettelun. Jos osiot ovat yhtä tärkeitä ja käyttäjät hyötyvät niiden näkemisestä samanaikaisesti, harkitse sen sijaan [välilehtiä](/docs/components/tabbedpane).
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` on akordijärjestelmän keskeinen rakennuspalikka. Anna konstruktorille merkkijono otsikoksi, ja lisää sitten lapsikomponentteja täyttämään runko. Paneeli toimii itsenäisesti ilman ympäröivää `Accordion`-ryhmää, mikä tekee siitä hyödyllisen kevyen paljastustyökalun, kun tarvitset vain yhden suljettavan osion. Argumentittomaa konstruktoria on myös saatavilla, kun haluat määrittää paneelin täysin rakentamisen jälkeen.

```java
// Vain otsikko - lisää runkosisältö erikseen
AccordionPanel panel = new AccordionPanel("Otsikko");
panel.add(new Paragraph("Runkosisältö tulee tähän."));

// Otsikko ja runkosisältö siirretty suoraan konstruktorinaan
AccordionPanel panel = new AccordionPanel("Otsikko", new Paragraph("Runkosisältö."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Avoimen ja suljetun hallitseminen {#opening-and-closing}

Voit hallita avointa/suljettua tilaa ohjelmallisesti koska tahansa. `isOpened()` on hyödyllinen, kun sinun on luettava nykyinen tila ennen kuin päätät, mitä tehdä. Esimerkiksi voit vaihtaa paneelin vastakkaiseen tilaan tai ehdollisesti näyttää tai piilottaa muita käyttöliittymän osia.

```java
// Laajenna paneeli
panel.open();

// Supista paneeli
panel.close();                    

// Palauttaa true, jos se on tällä hetkellä laajennettu
boolean isOpen = panel.isOpened();
```

Käytä `setLabel()`-menetelmää päivittääksesi otsikkotekstin rakentamisen jälkeen. `setText()` on samaan operaatioon liittyvä alias, joten etiketti voidaan pitää synkronoituna dynaamisen datan kanssa:

```java
panel.setLabel("Päivitetty etiketti");
```

## Akordiryhmät {#accordion-groups}

Useiden `AccordionPanel`-instanssien pakkaaminen `Accordion`in sisälle luo koordinoidun ryhmän. Oletusarvoisesti ryhmä käyttää **yksittäistilaa**: avattaessa yhtä paneelia kaikki muut automaattisesti supistuvat, pitäen vain yhden osion näkyvänä kerrallaan. Tämä oletus on tahallinen, se pitää käyttäjän keskittyneenä yhteen sisältöön ja estää sivua tulemasta visuaalisesti ylivoimaiseksi, kun paneeleilla on merkittävää runkosisältöä.

Paneelit rakennetaan itsenäisesti ja lähetetään `Accordion`-komponentille, joten voit määrittää jokaisen ennen ryhmittämistä. Useita erillisiä `Accordion`-instansseja voi myös olla sivulla — jokainen ryhmä hallitsee omaa tilaansa itsenäisesti, joten paneelin laajentaminen yhdessä ryhmässä ei vaikuta toiseen.

```java
AccordionPanel panel1 = new AccordionPanel("Mikä on webforJ?");
AccordionPanel panel2 = new AccordionPanel("Miten ryhmitellyt paneelit toimivat?");
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

### Monitila {#multiple-mode}

Monitila sallii minkä tahansa määrän paneeleita pysyä avoinna samanaikaisesti. Tämä on hyödyllistä, kun käyttäjät tarvitsevat useiden osioiden sisällön vertailua kerralla, tai kun jokainen paneeli on riittävän lyhyt, niin että useiden yhtä aikaa avaaminen ei luo hälyistä asettelua.

```java
accordion.setMultiple(true);
```

Monitilan aktivoituessa kaikki paneelit ryhmässä voidaan laajentaa tai supistaa kerralla käyttämällä massamalleja:

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

:::info Yksittäistilan rajoitus
`openAll()` on saatavilla vain, kun monitila on aktivoitu. Sen kutsuminen yksittäistilassa ei vaikuta mihinkään. `closeAll()` toimii molemmissa tiloissa.
:::

<!-- vale off -->
## Poissaoleva tila {#disabled-state}
<!-- vale on -->

Yksittäisiä paneeleita voidaan estää käyttäjävuorovaikutukselta, vaikka ne ovat edelleen näkyvissä. Tämä on kätevää lataustiloissa tai kun tietyt osiot ovat ehdollisesti käytettävissä, mikä näyttää paneelirakenteen ilman aikaisempaa vuorovaikutusta. Poissaoleva paneeli, joka oli jo auki, pysyy laajennettuna, mutta sen otsikkoa ei voi enää napsauttaa sen supistamiseksi. Jos `Accordion`-ryhmää estetään, se soveltaa poissaolevaa tilaa kaikkiin sisältyviin paneeleihin kerralla, jotta sinun ei tarvitse silmukoida paneeleita yksittäin.

```java
// Estä yksittäinen paneeli
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

Yli otsikoiden ja yksinkertaisen avaus/sulkemiskäyttäytymisen, jokainen `AccordionPanel` tukee rikkaampaa mukauttamista sekä sen otsikkosisältöön että laajentamiseen/supistamiseen liittyvään ikoniin.

### Mukautettu otsikko {#custom-header}

Paneelin otsikko renderöi sen etiketin oletusarvoisesti tavallisena tekstinä. Käytä `addToHeader()`-menetelmää vaihtaaksesi kyseisen tekstin mihin tahansa komponenttiin tai komponenttien yhdistelmään, mikä tekee helpoksi käyttää ikooneja, merkkejä, tilan indikaattoreita tai muita rikkaita merkintöjä paneeli-etiketin rinnalla. Tämä on erityisen hyödyllistä hallinta- tai asetuspaneeleissa, joissa jokaisen osion otsakon tarvitsee välittää ylimääräistä kontekstia yhdellä silmäyksellä, kuten tavaramäärä, varoitusmerkki tai valmistumistila, ilman, että käyttäjän tarvitsee laajentaa paneelia ensin.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Mukautettu otsikko ikonilla"));
panel.addToHeader(headerContent);
```

:::info Etiketin vaihto
`addToHeader()`-menetelmällä lisätty sisältö korvasi täysin oletuslabelin tekstin. `setLabel()` ja `setText()` toimivat edelleen yhdessä `addToHeader()`-menetelmän kanssa, mutta koska otsikkotila vie visuaalista etua, etikettitekstiä ei näytetä, ellei se sisällytetä nimenomaan slottiin liitettyyn sisältöön.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Mukautettu ikoni {#custom-icon}

Laajentamis/supistamis-indikaattori on oletusarvoisesti nuoli, joka näkyy sekä avatussa että suljetussa tilassa. `setIcon()`-menetelmä korvää sen millä tahansa [`Icon`](/docs/components/icon) komponentilla, mikä on hyödyllistä brändätyille ikkunoille tai kun eri visuaalinen metafora sopii sisältöön paremmin. Antamalla `null` palauttaa oletusarvoisen nuolen. `getIcon()` palauttaa nykyisen asetetun ikonin tai `null`, jos oletusnuoli on käytössä.

```java
// Korvata oletusnuoli plus-ikonilla
panel.setIcon(FeatherIcon.PLUS.create());

// Palauta oletusnuoli
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Sisäkkäiset akordit {#nested-accordions}

Akordit voidaan upottaa muiden akordionipaneelien sisälle, mikä on hyödyllistä hierarkkisen sisällön esittämisessä, kuten kategorisoiduissa asetuksissa tai monitasoisessa navigoinnissa. Lisää sisäinen `Accordion` ulkoiseen `AccordionPanel`-komponenttiin kuten mihin tahansa muuhun lapsikomponenttiin. Pidä upottaminen matalana. Yksi tai kaksi tasoa on yleensä riittävää. Syvemmät hierarkiat on usein vaikeampia navigoida, ja ne usein viittaavat siihen, että sisällön rakenne itsessään tarvitsee uudelleenajattelua.

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
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Tapahtumat {#events}

`AccordionPanel` laukaisee tapahtumia jokaisessa vaihdon elinkaaren vaiheessa. Kolme tapahtumatyypkiä kattaa eri hetkiä, joten valitse sen perusteella, milloin logiikkasi tarvitsee käynnistyä:

| Tapahtuma | Laukaisee |
|-----------|-----------|
| `AccordionPanelToggleEvent` | Ennen kuin tila muuttuu |
| `AccordionPanelOpenEvent` | Paneelin jälkeen, kun se on täysin avattu |
| `AccordionPanelCloseEvent` | Paneelin jälkeen, kun se on täysin suljettu |

```java
panel.onToggle(e -> {
    // Laukaisee paneelin tila muuttuessa.
    // e.isOpened() heijastaa siirtyvää tilaa, ei nykyistä tilaa.
    String direction = e.isOpened() ? "avaaminen" : "sulkeminen";
});

panel.onOpen(e -> {
    // Laukaisee, kun paneeli on täysin avattu — hyvä laiska-asetusten lataamiseen.
});

panel.onClose(e -> {
    // Laukaisee, kun paneeli on täysin suljettu — hyvä puhdistamiseen tai yhteenvetopäivityksiin.
});
```

## Tyylittely {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
