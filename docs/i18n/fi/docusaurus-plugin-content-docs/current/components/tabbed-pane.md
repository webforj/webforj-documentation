---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 544ab11783e8369075f1c02aba2d8dc8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useita sisältöosiota voidaan järjestää yhden `TabbedPane` alle, jossa jokainen osa on sidottu klikattavaan `Tab`. Vain yksi osa on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, kuvakkeita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käyttötapaukset {#usages}

`TabbedPane`-luokka antaa kehittäjille voimakkaan työkalun useiden välilehtien tai osien järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitakin tyypillisiä skenaarioita, joissa saatat käyttää `TabbedPane`-komponenttia sovelluksessasi:

1. **Dokumentin katselu**: Dokumentin katseluohjelman toteuttaminen, jossa jokainen välilehti edustaa eri dokumenttia tai tiedostoa. Käyttäjät voivat helposti siirtyä auki olevien dokumenttien välillä tehokasta moniajoa varten.

2. **Tietojen hallinta**: Käytä `TabbedPane`-komponenttia tietojen hallintatehtävien järjestämiseen, esimerkiksi:
    - Eri tietojoukkojen esittäminen sovelluksessa
    - Eri käyttäjäprofiilien esittäminen erillisissä välilehdissä
    - Eri profiilien esittäminen käyttäjähallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osia. Jokainen välilehti voi kätkeä tiettyjen modulien toiminnallisuudet, mahdollistaen käyttäjien keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintasovellukset voivat käyttää `TabbedPane`-komponenttia eri projekteiden tai tehtävien edustamiseen. Jokainen välilehti voi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Sovelluksen navigointi**: Sovelluksessa, joka tarvitsee ajaa erilaisia ohjelmia, `TabbedPane` voi:
    - Palvella sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien ajamisen yhden sovelluksen sisällä, kuten mikä on osoitettu [`AppLayout`](./app-layout.md) -mallissa
    - Luoda yläpalkin, joka voi palvella samankaltaista tarkoitusta tai edustaa alisovelluksia jo valitun sovelluksen sisällä

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymän elementtejä, joita voidaan lisätä tabulaarisiin paneeleihin erilaisten sisältönäkymien järjestämiseksi ja niiden välillä siirtymiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponentteina. Niitä on tarkoitus käyttää yhdessä tabulaaristen paneelien kanssa. Tämä luokka ei ole `Component` eikä sitä tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään lisättäessä niitä `TabbedPane`-komponenttiin. Näillä ominaisuuksilla on getterit ja setterit, jotka helpottavat mukauttamista `TabbedPane`-komponentissa.

1. **Teksti(`String`)**: Teksti, joka näytetään nimikkeenä `Tab`:lle `TabbedPane`-komponentissa. Tätä kutsutaan myös nimellä otsikko `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

2. **Työkaluvinkki(`String`)**: Työkaluvinkkiteksti, joka liittyy `Tab`:iin ja näytetään, kun kursori leijuu `Tab`:in päällä.

3. **Oletus(`boolean`)**: Edustaa, onko `Tab` käytössä vai ei. Voidaan muuttaa `setEnabled(boolean enabled)` -menetelmällä.

4. **Suljettavissa(`boolean`)**: Edustaa, voidaanko `Tab` sulkea. Voidaan muuttaa `setClosable(boolean closable)` -menetelmällä. Tämä lisää sulje-painikkeen `Tab`:iin, jota voidaan klikata, ja laukaisee poistotapahtuman. `TabbedPane`-komponentti määrää, miten poisto käsitellään.

5. **Slot(`Component`)**:
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`:in kykyjen parantamiseksi. Voit lisätä kuvakkeita, etikettejä, latauspyöriä, tyhjennys/palautusmahdollisuuksia, avatar/profiilikuvia ja muita hyödyllisiä komponentteja `Tab`:in sisään, jotta käyttäjät ymmärtäisivät sen merkityksen paremmin.
    Voit lisätä komponentin `Tab`:in `prefix`-slotille rakentamisen aikana. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä erilaisten komponenttien lisäämiseksi ennen ja jälkeen `Tab`:issa näytettävän vaihtoehdon.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumentit", TablerIcon.create("files")));
        ```

## `Tab`-manipulointi {#tab-manipulation}

Useita menetelmiä on olemassa, joiden avulla kehittäjät voivat lisätä, lisätä, poistaa ja manipuloida erilaisia `Tab`-elementtien ominaisuuksia `TabbedPane`-komponentissa.

### Välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmiä on olemassa eri ylikuormitettuna versioina, jotka antavat kehittäjille joustavuutta uusien välilehtien lisäämiseen `TabbedPane`-komponenttiin. Välilehden lisääminen sijoittaa sen kaikkien aiemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`**: lisää `Tab`:in `TabbedPane`-komponenttiin, jossa annettu `String` on `Tab`:in teksti.
2. **`addTab(Tab tab)`**: lisää annetun `Tab`:in `TabbedPane`-komponenttiin.
3. **`addTab(String text, Component component)`**: lisää `Tab`, jossa annettu `String` on `Tab`:in teksti, ja annettu `Component` näytetään `TabbedPane`-komponentin sisältöosiossa.
4. **`addTab(Tab tab, Component component)`**: lisää annettu `Tab` ja näyttää annetun `Component` `TabbedPane`-komponentin sisältöosiossa.
5. **`add(Component... component)`**: lisää yksi tai useampi `Component`-instanssi `TabbedPane`-komponenttiin, luomalla erillisen `Tab` jokaiselle, ja teksti asetetaan `Component`in nimeksi.

:::info
`add(Component... component)` määrittää annetun `Component`:in nimen kutsumalla `component.getName()` annetusta argumentista.
:::

### Välilehden lisääminen sijaintiin {#inserting-a-tab}

Lisäksi kuin lisätäksesi `Tab`:in olemassa olevien välilehtien loppuun, on myös mahdollista luoda uusi tiettyyn sijaintiin. Tätä varten on olemassa useita ylikuormitettuna versioita `insertTab()`-menetelmästä.

1. **`insertTab(int index, String text)`**: lisää `Tab`:in `TabbedPane`-komponenttiin annettuun indeksiin, jossa annettu `String` on `Tab`:in teksti.
2. **`insertTab(int index, Tab tab)`**: lisää annettu `Tab` `TabbedPane`-komponenttiin määritettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`**: lisää `Tab`, jossa annettu `String` on `Tab`:in teksti, ja annettu `Component` näytetään `TabbedPane`-komponentin sisältöosiossa.
4. **`insertTab(int index, Tab tab, Component component)`**: lisää annettu `Tab` ja näyttää annetun `Component` `TabbedPane`-komponentin sisältöosiossa.

### Välilehden poistaminen {#removing-a-tab}

Poistaaksesi yhden `Tab`-komponentin `TabbedPane`-komponentista, käytä jotakin seuraavista menetelmistä:

1. **`removeTab(Tab tab)`**: poistaa `Tab`:in `TabbedPane`-komponentista välittämällä poistettavan Tab-instanssin.
2. **`removeTab(int index)`**: poistaa `Tab`:in `TabbedPane`-komponentista määrittämällä poistettavan `Tab`:in indeksin.

Yksittäisen `Tab`-poiston lisäksi voit käyttää **`removeAllTabs()`** -menetelmää tyhjentääksesi `TabbedPane`-komponentin kaikista välilehdistä.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisältä.
:::

### Välilehti/Komponentti-assosiaatio {#tabcomponent-association}

Muuttaaksesi, mikä `Component` näytetään tietylle `Tab`:ille, kutsu `setComponentFor()` -menetelmää ja välitä joko `Tab`:in instanssi tai indeksin kyseisen Tabin sijainnista `TabbedPane`-komponentissa.

:::info
Jos tätä menetelmää käytetään `Tab`:ille, joka on jo liitetty `Component`:iin, aikaisemmin liitetty `Component` tuhotaan.
:::

## Kokoonpano ja asettelu {#configuration-and-layout}

`TabbedPane`-luokassa on kaksi osaa: `Tab`, joka näytetään määritettyyn sijaintiin, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](/docs/building-ui/composing-components) -komponentti, mikä mahdollistaa monimutkaisempien komponenttien näyttämisen välilehden sisältöosassa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien läpi pyyhkäisemällä. Tämä on ihanteellinen mobiilisovelluksille, mutta sitä voidaan myös konfiguroida sisäänrakennetun menetelmän avulla tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletuksena pois päältä, mutta ne voidaan aktivoida `setSwipeable(boolean)` ja `setSwipeWithMouse(boolean)` -menetelmillä vastaavasti.

### Välilehtien sijoittaminen {#tab-placement}

`Tabs`-komponentit `TabbedPane`-komponentissa voidaan sijoittaa eri sijainteihin komponentin mukaan sovelluskehittäjän mieltymysten mukaan. Tarjotut vaihtoehdot asetetaan tarjoamalla enum, jonka arvot ovat `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetus on `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Kohdistus {#alignment}

Lisäksi kuin muuttaaksesi `Tab`-elementtien sijoittelua `TabbedPane`-komponentissa, on myös mahdollista määrittää, kuinka välilehdet kohdistuvat komponenttiin. Oletuksena asetuksena on `AUTO`, joka sallii välilehtien sijoittelun määrittää niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat asemaa suhteessa komponenttiin, kun taas `STRETCH` sallii välilehtien täyttää käytettävissä olevan tilan.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Reunus ja aktiviteetti-indikaattori {#border-and-activity-indicator}

`TabbedPane`-komponentissa on oletuksena näkyvissä reuna, joka on sijoitettu sen mukaan, mikä `Placement` on asetettu. Tämä reuna auttaa visualisoimaan tilaa, jonka eri välilehdet paneelissa vievät.

Kun `Tab`:ia napsautetaan, oletuksena aktiviteetti-indikaattori näytetään lähellä kyseistä `Tab`:ia, jotta voidaan korostaa, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä vaihtoehtoja voidaan mukauttaa muuttamalla boolean-arvoja asianmukaisten setter-menettelyjen avulla. Muuttaakseen, näytetäänkö reuna vai ei, voidaan käyttää `setBorderless(boolean)` -menetelmää, jolloin `true` piilottaa reunan ja `false`, oletusarvo, näyttää reunan.

:::info
Tämä reuna ei koske koko `TabbedPane` -komponenttia, vaan palvelee vain rajana välilehtien ja komponentin sisällön välillä.
:::

Aktivoidun indikaattorin näkyvyyden määrittämiseksi voidaan käyttää `setHideActiveIndicator(boolean)` -menetelmää. Jos menetelmälle annetaan parametri `true`, se piilottaa aktiivisen indikaattorin aktiivisen `Tab`:in alla, kun taas `false`, oletusarvo, pitää indikaattorin näkyvissä.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivointitavat {#activation-modes}

Hienojakoisempaan hallintaan siitä, miten `TabbedPane` käyttäytyy näppäimistön navigoinnissa, aktivointitila voidaan asettaa määrittämään, miten komponentin tulisi käyttäytyä.

- **`Auto`**: kun asetetaan automaattiseksi, nuolinäppäimillä navigoiminen välilehtien läpi näyttää heti vastaavan välilehden komponentin.

- **`Manual`**: kun asetetaan manuaaliseksi, välilehti saa fokuksen, mutta sitä ei näytetä ennen kuin käyttäjä painaa väli- tai enter-näppäintä.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavat välilehdet saavat sulkemispainikkeen lisäämistä välilehteen, mikä laukaisee sulkeutumistapahtuman napsautettaessa. `TabbedPane` määrää, miten tämä käytös käsitellään.

- **`Manual`**: oletuksena poisto on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjä päättää, miten tämä tapahtuma käsitellään.

- **`Auto`**: vaihtoehtoisesti voidaan käyttää `AUTO`, joka laukaisee tapahtuman ja myös poistaa `Tab`:in komponentista kehittäjälle, jolloin kehittäjälle ei tarvitse toteuttaa tätä käyttäytymistä manuaalisesti.

### Segmentinhallinta <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` voidaan esittää segmentinhallintana ottamalla käyttöön `segment`-ominaisuus `setSegment(true)` -menetelmällä. Tällä tilalla välilehdet näytetään liukuvalla pilleri-indikaattorilla, joka korostaa aktiivisen valinnan, tarjoten kompaktin vaihtoehdon standardille välilehtiliittymälle.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Tyylit {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane` tulee esivalmisteltuina `Expanse` ja `Theme` vaihtoehtojen kanssa samankaltaisesti kuin muut webforJ komponentit. Näitä voidaan käyttää nopeasti lisäämään tyylittelyä, joka välittää erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsee muotoilla CSS:llä.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavat käytännöt on suositeltu `TabbedPane`-komponentin käytölle sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittämään liittyvää sisältöä:
    - Jokaisen välilehden tulisi edustaa erilaista kategoriaa tai toiminnallisuutta sovelluksessasi.
    - Ryhmäsijoita samanlaisia tai loogisia välilehtiä lähelle toisiaan.

- **Rajalliset välilehdet**: Vältä käyttäjien ylivoimaisuutta liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointikaavioiden käyttöä, kun se on sovellettavissa puhdistettavan käyttöliittymän saavuttamiseksi.

- **Selkeät etiketit**: Merkitse välilehtesi selkeästi intuitiivista käyttöä varten:
    - Anna jokaiselle välilehdelle selkeät ja ytimekkäät etiketit.
    - Etikettien tulisi heijastaa sisältöä tai tarkoitusta, jotta käyttäjät ymmärtävät sen helposti.
    - Käytä kuvakkeita ja erottuvia värejä, kun se on mahdollista.

- **Näppäimistön navigointi**: Käytä webforJ:n `TabbedPane`-näppäimistön navigointituen hyödyntämistä, jotta interaktio `TabbedPane`-komponentin kanssa olisi sujuvampaa ja intuitiivisempaa loppukäyttäjälle.

- **Oletusvälilehti**: Jos oletusvälilehti ei ole asetettu `TabbedPane`-komponentin alkuun, harkitse tämän välilehden asettamista oletukseksi tärkeälle tai usein käytetyn tiedolle.
