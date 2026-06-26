---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 9a348db865b5ea1688eb09c4f6f75a57
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Usean useita sisältöosioita voi järjestää yhden `TabbedPane`:n alle, jossa kukin osa on sidottu napsautettavaan `Tab`:iin. Vain yksi osa on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, ikoneita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käytöt {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittelyyn käyttöliittymässä. Tässä on joitakin tyypillisiä skenaarioita, joissa voit hyödyntää `TabbedPane`-elementtiä sovelluksessasi:

1. **Dokumentin katselu**: Dokumentin katseluohjelman toteuttaminen, jossa jokainen välilehti edustaa eri asiakirjaa tai tiedostoa. Käyttäjät voivat helposti vaihtaa avointen asiakirjojen välillä tehokasta monitehtävyyttä varten.

2. **Tietojen hallinta:** Hyödynnä `TabbedPane`:a tietojen hallintatehtävien järjestämiseen, esimerkiksi:
    >- Eri tietojoukko, joka näytetään sovelluksessa
    >- Eri käyttäjäprofiileja voidaan näyttää erillisissä välilehdissä
    >- Eri profiileja käyttäjien hallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa eri moduuleja tai osioita. Jokainen välilehti voi kapseloida tietyn moduulin toiminnot, jolloin käyttäjät voivat keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintaohjelmat voivat käyttää `TabbedPane`:a erilaisten projektien tai tehtävien esittämiseen. Jokainen välilehti voisi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee suorittaa useita ohjelmia, `TabbedPane` voisi:
    >- Toimia sivupalkkina, joka sallii eri sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten mitä esitetään [`AppLayout`](./app-layout.md) -mallissa
    >- Luoda yläosan, joka voi palvella samanlaista tarkoitusta tai edustaa alisovelluksia valitussa sovelluksessa.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymäelementtejä, joita voidaan lisätä tabbed paneihin erilaisten sisältönäkymien järjestämiseksi ja vaihtamiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponenteina. Niitä on tarkoitus käyttää yhdessä tabbed panejen kanssa. Tämä luokka ei ole `Component` ja sitä ei tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään niiden lisäämiseen `TabbedPane`:hen. Näillä ominaisuuksilla on getters ja setters mukauttamista varten `TabbedPane`:ssa.

1. **Key(`Object`)**: Edustaa `Tab`:in ainutlaatuista tunnistetta.

2. **Text(`String`)**: Teksti, joka näytetään `Tab`-otsikkona `TabbedPane`:ssa. Tätä kutsutaan myös otsikoksi `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

3. **Tooltip(`String`)**: Vihjeen teksti, joka liittyy `Tab`:iin, ja joka näytetään, kun kursori leijuu `Tab`:in päällä.

4. **Enabled(`boolean`)**: Edustaa, onko `Tab` tällä hetkellä käytössä vai ei. Voidaan muuttaa `setEnabled(boolean enabled)` -menetelmällä.

5. **Closeable(`boolean`)**: Edustaa, voidaanko `Tab` sulkea. Voidaan muuttaa `setCloseable(boolean enabled)` -menetelmällä. Tämä lisää sulkemispainikkeen `Tab`:lle, jota käyttäjä voi napsauttaa, ja laukaisee poisto- tapahtuman. `TabbedPane`-komponentti määrää, miten poisto käsitellään.

6. **Slot(`Component`)**: 
    Slottit tarjoavat joustavia vaihtoehtoja `Tab`:in kyvykkyyden parantamiseksi. Voit liittää ikoneita, etikettejä, lataussiruja, tyhjennys/resetointikeytyksiä, avatar/profiilikuvaa ja muita hyödyllisiä komponentteja `Tab`:in sisälle käyttäjille tarkoitetun aikomuksen selventämiseksi.
    Voit lisätä komponentin `Tab`:n `prefix`-slotille rakentaessasi. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä eri komponenttien liittämiseen ennen ja jälkeen näytettävän vaihtoehdon `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumentit", TablerIcon.create("files")));
        ```

## `Tab` manipulointi {#tab-manipulation}

Eri menetelmät ovat olemassa, jotta kehittäjät voivat lisätä, lisätä, poistaa ja manipuloida erilaisia `Tab`-elementtien ominaisuuksia `TabbedPane`:ssä.

### Välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmät ovat erilaisissa ylikuormitetuissa muodoissa, jotta kehittäjät voivat lisätä uusia välilehtiä `TabbedPane`:hen. Välilehden lisääminen sijoittaa sen kaikkiin aikaisemmin olemassa oleviin välilehtiin.

1. **`addTab(String text)`** - Lisää `Tab`:n `TabbedPane`:hen annetulla `String`-arvolla, joka on `Tab`:n teksti.
2. **`addTab(Tab tab)`** - Lisää annettu `Tab`-parametri `TabbedPane`:hen.
3. **`addTab(String text, Component component)`** - Lisää `Tab`:n annetulla `String`-arvolla, joka on `Tab`:n teksti, ja annetulla `Component`:illa, joka näytetään `TabbedPane`:n sisältöosiossa.
4. **`addTab(Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää annetun `Component`:in `TabbedPane`:n sisältöosiossa.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`:hen, luoden erillisen `Tab`:n jokaiselle, jonka teksti on asetettu `Component`:in nimeksi.

:::info
`add(Component... component)` määrittää annettavan `Component`:in nimen kutsumalla `component.getName()` annettavan argumentin kohdalla.
:::

### Välilehden lisääminen {#inserting-a-tab}

Lisäksi olemassa olevien välilehtien lopussa halutun lisäyksen luominen on mahdollista. Tähän on useita ylikuormitetuja versioita `insertTab()`-menetelmistä. 

1. **`insertTab(int index, String text)`** - Lisää `Tab`:n `TabbedPane`:hen annetulla indeksillä, jonka teksti on määritetty `String`-arvolla.
2. **`insertTab(int index, Tab tab)`** - Lisää annettu `Tab`-parametri `TabbedPane`:hen annetulla indeksillä.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`:n annetulla indeksillä, jonka teksti on määritetty `String`-arvolla ja annettu `Component`, joka näkyy `TabbedPane`:n sisältöosassa.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää annetun `Component`:in `TabbedPane`:n sisältöosassa.

### Välilehden poistaminen {#removing-a-tab}

Yhden `Tab`-elementin poistamiseksi `TabbedPane`:sta käytä jotakin seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:n `TabbedPane`:sta antamalla poistettavan Tab-instanssin.
2. **`removeTab(int index)`** - Poistaa `Tab`:n `TabbedPane`:sta määrittämällä `Tab`:n poistettava indeksi.

Yksittäisen `Tab`:n poistamiseksi käytä myös **`removeAllTabs()`** -menetelmää poistaaksesi kaikki välilehdet `TabbedPane`:sta.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentista.
:::

### Tab/Component-yhteys {#tabcomponent-association}

Muutoksen tekemiseksi näytettävälle `Component`:ille tietylle `Tab`:lle, soita `setComponentFor()`-menetelmälle ja siirrä joko `Tab`-instanssi tai kyseisen `Tab`:n indeksi `TabbedPane`:ssä.

:::info
Jos tätä menetelmää käytetään `Tab`:lle, joka on jo liitetty `Component`:iin, aikaisemmin liitetty `Component` hävitetään.
:::

## Konfigurointi ja asettelu {#configuration-and-layout}

`TabbedPane`-luokassa on kaksi osaa: `Tab`, joka näytetään tietyssä paikassa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](/docs/building-ui/composing-components) komponentti, jonka avulla voidaan esittää monimutkaisempia komponentteja välilehden sisältöosiossa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia erilaisten välilehtien välillä pyyhkäisemällä. Tämä on ihanteellinen mobiilisovelluksessa, mutta sen voi myös konfiguroida sisäänrakennetun menetelmän avulla tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletusarvoisesti pois päältä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehden sijoittaminen {#tab-placement}

`Tabs`-välilehdet voidaan sijoittaa eri paikkoihin komponentin sisällä sovelluskehittäjien mieltymysten mukaan. Tarjolla olevat vaihtoehdot asetetaan annettavan enum-sarjan avulla, jolla on arvot `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetus on `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Kohdistus {#alignment}

Välilehtien sijoittamisen muuttamisen lisäksi `TabbedPane`:ssa on mahdollista myös konfiguroida, kuinka välilehdet kohdistuvat komponenttiin. Oletusarvoisesti asetuksena on `AUTO`, joka sallii välilehtien sijoittelun määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Reuna- ja aktiivisuusindikaattori {#border-and-activity-indicator}

`TabbedPane`:ssa on oletusarvoisesti näkyvissä reunus, joka näkyy siinä oleville välilehdille, ja tämä riippuu asetetusta `Placement`-arvosta. Tämä reuna auttaa visualisoimaan tilan, joka erilaisilla välilehdillä paneelissa on.

Kun `Tab`:ia napsautetaan, oletusarvoisesti aktiivisuusinikaattori näkyy sen lähellä, mikä auttaa korostamaan, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä vaihtoehtoja voidaan mukauttaa kehittäjän toimesta muuttamalla boolean-arvoja käyttäen asianmukaisia setter-metodeja. Muuttaaksesi, onko reuna näkyvissä vai ei, voidaan käyttää `setBorderless(boolean)` -menetelmää, jossa `true` piilottaa reunan ja `false`, oletusarvo, näyttää reunan.

:::info
Tämä reuna ei kosketa koko `TabbedPane`-komponenttia, vaan toimii yksinkertaisesti erottimena välilehtien ja komponentin sisällön välillä.
:::

Aktiivisuuden indikaattorin näkyvyyden asettamiseksi voidaan käyttää `setHideActiveIndicator(boolean)` -menetelmää. Passoimalla `true` tälle menetelmälle piilotetaan aktiivinen indikaattori aktiivisen `Tab`:in alla, kun taas `false`, oletusarvo, pitää indikaattorin näkyvissä.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivointitilat {#activation-modes}

Tarkempaa hallintaa varten siitä, miten `TabbedPane` käyttäytyy, kun sitä navigoidaan näppäimistöllä, aktivointitila voidaan asettaa määrittämään, miten komponentin tulee käyttäytyä.

- **`Auto`**: Kun se on asetettu automaattiseksi, välilehtien navigointi nuolinäppäimillä näyttää heti vastaavan välilehden komponentin.

- **`Manual`**: Kun se on asetettu manuaaliseksi, välilehti saa fokuksen, mutta ei näy ennen kuin käyttäjä painaa välilyöntiä tai Enter-näppäintä.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavilla välilehdillä on sulkemispainike välilehdessä, joka laukaisee sulkemistapahtuman, kun sitä napsautetaan. `TabbedPane` määrää, miten tätä käyttäytymistä käsitellään.

- **`Manual`**: Oletusarvoisesti poisto on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on hoidettava tämä tapahtuma haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti voit käyttää `AUTO`:a, joka laukaisee tapahtuman ja poistaa myös `Tab`:n komponentista kehittäjän puolesta, jolloin kehittäjän ei tarvitse toteuttaa tätä käyttäytymistä manuaalisesti.

### Segmenttien hallinta <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` voidaan esittää segmenttihallintana ottamalla käyttöön `segment`-ominaisuus `setSegment(true)` -menetelmällä. Tässä tilassa välilehdet näytetään liukuvan pillerindikaattorin kanssa, joka korostaa aktiivista valintaa, tarjoten kompaktin vaihtoehdon tavanomaiselle tabbed käyttöliittymälle.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Tyylit {#styling}

### Laajennus ja teema {#expanse-and-theme}

`TabbedPane` tulee sisäänrakennettujen `Expanse` ja `Theme` -vaihtoehtojen kanssa, jotka ovat samankaltaisia kuin muut webforJ-komponentit. Näitä voidaan käyttää nopeasti lisäämään tyyliä, joka välittää erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsee tyylitellä CSS:llä.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavia käytäntöjä suositellaan käytettäessä `TabbedPane`:a sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittämään liittyvää sisältöä
    >- Jokaisen välilehden tulisi edustaa erilaista kategoriaa tai toimintoa sovelluksessa
    >- Ryhmittele samankaltaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitettu määrä välilehtiä**: Vältä käyttäjien ylikuormittamista liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointimallien käyttöä soveltuvissa kohteissa siistin käyttöliittymän saavuttamiseksi.

- **Selkeät etiketit**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
    >- Tarjoa selkeitä ja ytimekkäitä etikettejä jokaiselle välilehdelle
    >- Etiketit tulisi kuvastaa sisältöä tai tarkoitusta, jolloin käyttäjät ymmärtävät sen helposti
    >- Hyödynnä ikoneita ja erottuvia värejä silloin, kun se on soveltuvaa

- **Näppäimistön navigointi**: Käytä webforJ:n `TabbedPane`-näppäimistön navigointitukea, jotta vuorovaikutus `TabbedPane`:n kanssa on sujuvampaa ja intuitiivisempaa loppukäyttäjälle.

- **Oletusvälilehti**: Jos oletusvälilehti ei ole sijoitettuna `TabbedPane`:n alkuun, harkitse tämän välilehden asettamista oletukseksi tärkeälle tai usein käytetylle tiedolle.
