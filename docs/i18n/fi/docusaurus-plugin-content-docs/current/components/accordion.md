---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 2bf90130b3a767840e2604045504ee91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion`-komponentti tarjoaa pystysuoran joukon laajennettavia paneeleja. Jokaisessa paneelissa on napsautettava otsikko, joka vaihtaa sen sisällön näkyvyyttä. `AccordionPanel`-paneelia voidaan käyttää itsenäisenä paljastusosiona tai ryhmitellä `Accordion`-komponentin sisään, jotta monien paneelien laajentamis- ja sulkemiskäyttäytyminen voidaan koordinoida.

<!-- INTRO_END -->

:::tip Milloin käyttää akkordeonia
Akkordeonit toimivat hyvin usein kysyttävissä kysymyksissä, asetussivuilla ja vaiheittaisissa prosesseissa, joissa kaiken sisällön yhtäaikainen paljastaminen voisi luoda ylivoimaisen asettelun. Jos osiot ovat yhtä tärkeitä ja käyttäjät hyötyvät niiden näkemisestä samanaikaisesti, kannattaa harkita [välilehtiä](/docs/components/tabbedpane) sen sijaan.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` on akkordeon järjestelmän ydinrakennuspalikka. Siirrä merkkijonot otsikkotekstiin konstruktorille ja lisää sitten lapsikomponentteja täyttämään runkoa. Paneeli toimii itsenäisesti ilman ympäröivää `Accordion`-ryhmää, mikä tekee siitä hyödyllisen kevyen paljastus-widgetin, kun tarvitset vain yhden laajennettavan osion. Ilman argumentteja oleva konstruktori on myös saatavilla, kun haluat määrittää paneelin täysin sen rakentamisen jälkeen.

```java
// Vain otsikko - lisää runkosisältö erikseen
AccordionPanel panel = new AccordionPanel("Otsikon nimi");
panel.add(new Paragraph("Runko-osa tulee tänne."));

// Otsikko ja runkosisältö viety suoraan konstruktorissa
AccordionPanel panel = new AccordionPanel("Otsikko", new Paragraph("Runko-osa."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='550px'
/>
<!-- vale on -->

### Avoimen ja suljetun hallinta {#opening-and-closing}

Ohjaa avointa/suljettua tilaa ohjelmallisesti milloin tahansa. `isOpened()` on hyödyllinen, kun sinun on luettava nykyinen tila ennen kuin päätät, mitä tehdä. Voit esimerkiksi vaihtaa paneelia vastakkaiseen tilaan tai ehdollisesti näyttää tai piilottaa muita osia käyttöliittymässä.

```java
// Laajenna paneeli
panel.open();

// Sulje paneeli
panel.close();                    

// Palauttaa true, jos se on nykyisin avattu
boolean isOpen = panel.isOpened();
```

Käytä `setLabel()`-menetelmää päivittääksesi otsikkotekstiä rakentamisen jälkeen. `setText()` on aliaksena samalle toiminnolle, joten etiketti voidaan pitää synkronoituna dynaamisten tietojen kanssa:

```java
panel.setLabel("Päivitetty etiketti");
```

## Akkordeoniryhmät {#accordion-groups}

Useiden `AccordionPanel`-instanssien pakkaaminen `Accordion`-komponentin sisään luo koordinoidun ryhmän. Oletusarvoisesti ryhmä käyttää **yksinkertaista tilaa**: avattaessa yksi paneeli, kaikki muut automaattisesti suljetaan, jolloin vain yksi osio on näkyvissä kerralla. Tämä oletus on tarkoituksellinen, se pitää käyttäjän keskittyneenä yhteen sisältöpalaseseen ja estää sivun näyttämästä visuaalisesti ylivoimaiselta, kun paneeleilla on merkittävää runkosisältöä.

Paneelit rakennetaan itsenäisesti ja annetaan `Accordion`:lle, joten voit määrittää jokaisen ensin ennen niiden ryhmittämistä. Useita erillisiä `Accordion`-instansseja voi myös olla samalla sivulla—jokainen ryhmä hallitsee omaa tilaansa itsenäisesti, joten paneelin laajentaminen yhdessä ryhmässä ei vaikuta toiseen.

```java
AccordionPanel panel1 = new AccordionPanel("Mikä on webforJ?");
AccordionPanel panel2 = new AccordionPanel("Kuinka ryhmitellyt paneelit toimivat?");
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

### Moninkertainen tila {#multiple-mode}

Moninkertainen tila sallii minkä tahansa määrän paneeleja pysyä laajennettuna samanaikaisesti. Tämä on hyödyllistä, kun käyttäjät tarvitsevat useiden osioiden sisällön vertaamista kerralla, tai kun jokainen paneeli on tarpeeksi lyhyt, jotta useiden laajentaminen ei luo sotkuista asettelua.

```java
accordion.setMultiple(true);
```

Moninkertaisen tilan ollessa aktiivinen, kaikki ryhmän paneelit voidaan laajentaa tai sulkea kerralla hyödyntäen massamääriä:

```java
// Laajenna jokainen paneeli ryhmässä
accordion.openAll();

// Sulje jokainen paneeli ryhmässä
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='575px'
/>
<!-- vale on -->

:::info Yksinkertaisen tilan rajoitus
`openAll()` on käytettävissä vain, kun moninkertainen tila on käytössä. Sen kutsuminen yksinkertaisessa tilassa ei vaikuta. `closeAll()` toimii molemmissa tiloissa.
:::

<!-- vale off -->
## Poissaolotila {#disabled-state}
<!-- vale on -->

Yksittäiset paneelit voidaan poistaa käytöstä estämään käyttäjän vuorovaikutusta, vaikka ne säilyvät näkyvinä. Tämä on kätevää lataustilanteissa tai silloin, kun tietyt osiot ovat ehdollisesti käytettävissä, näyttäen paneelin rakenteen ilman ennakoimatonta vuorovaikutusta. Poistettu paneeli, joka oli jo auki, pysyy laajennettuna, mutta sen otsikkoon ei voi enää napsauttaa sen sulkemiseksi. `Accordion`-ryhmän poistaminen käytöstä soveltaa poistettua tilaa kaikkiin sisältämiinsä paneeleihin kerralla, joten sinun ei tarvitse silmukoida paneeleja yksittäin.

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
height='650px'
/>
<!-- vale on -->

## Paneelien mukauttaminen {#customizing-panels}

Etikettien ja perus avaus/sulkemiskäyttäytymisen lisäksi jokainen `AccordionPanel` tukee rikkaampaa mukauttamista sekä otsikkosisällön että laajentamissulkeutumisen ikonille.

### Mukautettu otsikko {#custom-header}

Paneelin otsikko näkyy oletusarvoisesti pelkkänä tekstinä. Käytä `addToHeader()`-menetelmää korvataksesi tämän tekstin millä tahansa komponentilla tai komponenttien yhdistelmällä, mikä tekee siitä suoraviivaista sisällyttää kuvakkeita, merkkejä, tilanarvoja tai muuta rikasta merkintää paneelin etiketin viereen. Tämä on erityisen hyödyllistä kojelaudoissa tai asetuspaneeleissa, joissa jokaisen osion otsikon on välitettävä lisäyhteyttä yhdellä silmäyksellä, kuten kohteen määrä, varoitusmerkki tai valmistumisen tila, ilman että käyttäjän tarvitsee laajentaa paneelia ensin.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Mukautettu otsikko kuvakkeella"));
panel.addToHeader(headerContent);
```

:::info Etiketin vaihto
`addToHeader()`-menetelmän avulla lisätty sisältö korvasi täysin oletusarvoisen etiketin tekstin. `setLabel()` ja `setText()` toimivat edelleen `addToHeader()`-menetelmän ohella, mutta koska otsikkotila on visuaalisesti ensisijainen, etikettitekstiä ei näytetä ellei sisällytä sitä nimenomaan liitettyyn sisältöön.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Mukautettu ikoni {#custom-icon}

Laajentamis-/sulkemisindikaattori oletusarvoisesti näkyy viisarina, joka näkyy sekä avoimissa että suljetuissa tiloissa. `setIcon()`-menetelmällä voit korvata sen millä tahansa [`Icon`](/docs/components/icon) komponentilla, mikä on hyödyllistä brändättyjä kuvastoja varten tai kun toinen visuaalinen metafora sopii sisältöön paremmin. Välittäminen `null` palauttaa oletusarvoisen viisarimallin. `getIcon()` palauttaa tällä hetkellä asetetun ikonin, tai `null`, jos oletusviisarimallia käytetään.

```java
// Korvaa oletusviisari plus-ikonilla
panel.setIcon(FeatherIcon.PLUS.create());

// Palauta oletusviisari
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Upotetut akkordeonit {#nested-accordions}

Akkordeoneja voidaan upottaa muiden akkordeonipaneelien sisään, mikä on hyödyllistä hierarkkisen sisällön esittämisessä, kuten kategorisoidut asetukset tai monitasoinen navigaatio. Lisää sisäinen `Accordion` ulkoiseen `AccordionPanel`-paneeliin kuten mikä muu lapsikomponentti. Pidä upotus matalana. Yksi tai kaksi tasoa on yleensä riittävästi. Syvemmät hierarkiat ovat yleensä vaikeampia navigoida, ja usein tarkoittavat, että sisältörakenne tarvitsee uudelleenarviointia.

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

`AccordionPanel` laukaisee tapahtumia jokaisessa vaihtosykliensä vaiheessa. Kolme tapahtumatyyppiä kattaa eri hetket, joten valitse sen mukaan, milloin logiikkasi tarvitsee toimia:

| Tapahtuma | Laukaisee |
|-------|-------|
| `AccordionPanelToggleEvent` | Ennen tilan muutosta |
| `AccordionPanelOpenEvent` | Kun paneeli on täysin avautunut |
| `AccordionPanelCloseEvent` | Kun paneeli on täysin suljettu |

```java
panel.onToggle(e -> {
    // Laukaisee ennen kuin paneeli muuttaa tilaa.
    // e.isOpened() heijastaa tilaa, johon siirrytään, ei nykyistä tilaa.
    String direction = e.isOpened() ? "avaamassa" : "sulkemassa";
});

panel.onOpen(e -> {
    // Laukaisee, kun paneeli on täysin avattu — hyvä laiskasti ladatulle sisällölle.
});

panel.onClose(e -> {
    // Laukaisee, kun paneeli on täysin suljettu — hyvä puhdistamiseen tai yhteenvetoihin.
});
```

## Tyylit {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
