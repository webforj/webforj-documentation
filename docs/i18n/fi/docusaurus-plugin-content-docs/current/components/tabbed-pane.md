---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 0b623c02434c6f0d140de0ade3a22c5d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useita sisältöjaksoja voidaan järjestää yhden `TabbedPane` alle, missä kukin jakso on liitetty napsautettavaksi `Tab`. Vain yksi jakso on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, kuvakkeita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käytännöt {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitakin tyypillisiä tilanteita, joissa voit hyödyntää `TabbedPane`-komponenttia sovelluksessasi:

1. **Asiakirjan katselu**: Asiakirjan katseluohjelman toteuttaminen, jossa jokaista välilehteä vastaa eri asiakirja tai tiedosto. Käyttäjät voivat helposti vaihtaa avoimien asiakirjojen välillä tehokkaassa moniajossa.

2. **Tietojen hallinta:**: Käytä `TabbedPane`-komponenttia järjestääksesi tietojen hallintatehtäviä, esimerkiksi:
    >- Eri tietojoukkoja, jotka on tarkoitettu näytettäväksi sovelluksessa
    >- Erilaiset käyttäjäprofiilit voidaan näyttää erillisissä välilehdissä
    >- Eri profiilit käyttäjänhallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osioita. Kukin välilehti voi kapseloida tietyn moduulin toiminnot, mikä mahdollistaa käyttäjien keskittymisen yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävienhallintasovellukset voivat käyttää `TabbedPane`-komponenttia jäsentämään erilaisia projekteja tai tehtäviä. Kukin välilehti voisi viitata tiettyyn projektiin, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee suorittaa erilaisia ohjelmia, `TabbedPane` voisi:
    >- Toimia sivupalkkina, joka mahdollistaa erilaisten sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten mitä esitetään [`AppLayout`](./app-layout.md) -mallissa
    >- Luoda yläpalkin, joka voi palvella samanlaista tarkoitusta tai edustaa alaohjelmia jo valitussa sovelluksessa.
  
## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymän elementtejä, joita voidaan lisätä välilehtipaneeleihin erilaisten sisältönäkymien järjestämiseksi ja niiden välillä vaihtamiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponentteina. Niitä on tarkoitus käyttää yhdessä välilehtipaneelien kanssa. Tämä luokka ei ole `Component`, eikä sitä tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään lisättäessä niitä `TabbedPane` -komponenttiin. Näillä ominaisuuksilla on getterit ja setterit, jotka helpottavat mukauttamista `TabbedPane`-komponentissa.

1. **Key(`Object`)**: Edustaa `Tab`:n ainutkertaista tunnistetta.

2. **Text(`String`)**: Teksti, joka näytetään `Tab`:n otsikkona `TabbedPane`:ssa. Tämä tunnetaan myös otsikkona `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

3. **Tooltip(`String`)**: Vinkkiteksti, joka liittyy `Tab`:iin ja joka näytetään, kun kohdistin leijuu `Tab`:n päällä.

4. **Enabled(`boolean`)**: Edustaa, onko `Tab` tällä hetkellä käytössä vai ei. Voidaan muuttaa `setEnabled(boolean enabled)` -menetelmällä.

5. **Closeable(`boolean`)**: Edustaa, voiko `Tab` olla suljettavissa. Voidaan muuttaa `setCloseable(boolean enabled)` -menetelmällä. Tämä lisää sulkemispainikkeen `Tab`:iin, jota käyttäjä voi napsauttaa, ja laukaisee poistotapahtuman. `TabbedPane`-komponentti määrää, miten poistamisprosessi käsitellään.

6. **Slot(`Component`)**:
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`:n kyvykkyyden parantamiseksi. Voit laittaa kuvakkeita, etikettejä, latauspyöröitä, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuvaajia ja muita hyödyllisiä komponentteja upotettuna `Tab`:iin, jotta käyttäjille selkeytetään aiottua merkitystä.
    Voit lisätä komponentin `prefix`-slotin `Tab`:iin rakennettaessa. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä, jotta voit sijoittaa erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`:n käsittely {#tab-manipulation}

Erilaisia menetelmiä on olemassa, jotta kehittäjät voivat lisätä, sisällyttää, poistaa ja käsitellä `Tab`-elementtien erilaisia ominaisuuksia `TabbedPane`-komponentissa.

### `Tab`:n lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmät ovat saatavilla erilaisissa ylikuormitetuissa muodoissa, jotta kehittäjät voivat joustavasti lisätä uusia välilehtiä `TabbedPane`-komponenttiin. Välilehden lisääminen asettaa sen aikaisempien olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`:n `TabbedPane`:een, jossa määritelty `String` toimii `Tab`:n tekstinä.
2. **`addTab(Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`:een.
3. **`addTab(String text, Component component)`** - Lisää `Tab`, jonka annettu `String` toimii `Tab`:n tekstinä, ja annettu `Component` näytetään `TabbedPane`:n sisältöosiossa.
4. **`addTab(Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää annettavan `Component` `TabbedPane`:n sisältöosiossa.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`:een, luoden jokaiselle erillisen `Tab`:n, jonka teksti asetetaan `Component`:n nimeksi.

:::info
`add(Component... component)` määrittää lähetetyn `Component`:n nimen kutsumalla `component.getName()` lähetetystä argumentista.
:::

### `Tab`:n lisääminen tiettyyn paikkaan {#inserting-a-tab}

Lisäksi uuden `Tab`:n luominen määrättyyn paikkaan on mahdollista. Tähän on useita ylikuormitettuja versioita `insertTab()`-menetelmästä.

1. **`insertTab(int index, String text)`** - Lisää `Tab`:n `TabbedPane`:en määritettyyn indeksiin, jossa määritelty `String` toimii `Tab`:n tekstinä.
2. **`insertTab(int index, Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`:een määritettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`, jonka annettu `String` toimii `Tab`:n tekstinä, ja annettu `Component` näytetään `TabbedPane`:n sisältöosiossa.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää annettavan `Component` `TabbedPane`:n sisältöosiossa.

### `Tab`:n poistaminen {#removing-a-tab}

Yhden `Tab`:n poistamiseksi `TabbedPane`:sta käytä jotakin seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:n `TabbedPane`:sta siirtämällä poistettavan Tab-instanssin.
2. **`removeTab(int index)`** - Poistaa `Tab`:n `TabbedPane`:sta määrittämällä poistettavan `Tab`:n indeksi.

Yksittäisen `Tab`:n poistamiseksi yllä olevien kahden menetelmän lisäksi käytä **`removeAllTabs()`** -menetelmää poistaaksesi kaikki välilehdet `TabbedPane`:sta.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### `Tab`/komponenttiyhteys {#tabcomponent-association}

Vaihtaaksesi näytettävän `Component`-instanssin tietylle `Tab`:lle, kutsu `setComponentFor()`-menetelmää ja siirrä joko `Tab`-instanssi tai kyseisen `Tab`:n indeksi `TabbedPane`:ssä.

:::info
Jos tätä menetelmää käytetään `Tab`:lle, joka on jo liitetty `Component`:iin, aikaisemmin liitetty `Component` tuhotaan.
:::

## Konfigurointi ja asettelu {#configuration-and-layout}

`TabbedPane`-luokassa on kaksi osaa: `Tab`, joka näytetään määritetyssä paikassa, ja näytettävä komponentti. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composing-components) -komponentti, mikä mahdollistaa monimutkaisempien komponenttien esittämisen välilehden sisältöosiossa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien välillä pyyhkäisemällä. Tämä on ihanteellinen mobiilisovelluksille, mutta se voidaan myös määrittää sisäänrakennetun menetelmän avulla tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletusarvoisesti poistettu käytöstä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittaminen {#tab-placement}

`Tabs`-elementtien sisällä `TabbedPane`-komponentissa voidaan sijoittaa eri paikkoihin sovelluskehittäjän mieltymysten mukaan. Tarjotut vaihtoehdot asetetaan käytettävän enum:in avulla, jossa on arvot `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetuksena on `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Kohdistus {#alignment}

Lisäksi kuin muuttamalla `Tab`-elementtien sijoittamista `TabbedPane`-komponentissa, on myös mahdollista määrittää, miten välilehdet kohdistuvat komponenttiin. Oletusarvoisesti `AUTO`-asetus on voimassa, mikä sallii välilehtien sijoituksen päättää niiden kohdentamisen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään saatavilla olevan tilan.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Reuna ja aktiivisuuden indikaattori {#border-and-activity-indicator}

`TabbedPane`-komponentilla on oletusarvoisesti reuna, joka näkyy välilehdissä, asetettuna sen mukaan, mikä `Placement` on asetettu. Tämä reuna auttaa visualisoimaan tilan, jonka erilaiset välilehdet paneelissa vievät.

Kun `Tab`-elementtiä klikataan, aktivoituu oletusarvoisesti aktiivisuuden indikaattori, joka korostaa, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä vaihtoehtoja voidaan mukauttaa kehittäjällä muuttamalla boolean-arvoja asianmukaisilla setterimenetelmillä. Muuttaaksesi, näytetäänkö reuna vai ei, voit käyttää `setBorderless(boolean)` -menetelmää, missä `true` piilottaa reunan ja `false`, oletusarvo, näyttää reunan.

:::info
Tämä reuna ei koske koko `TabbedPane`-komponenttia, ja se palvelee vain erottimena välilehtien ja komponentin sisällön välillä.
:::

Aktiivisen indikaattorin näkyvyyden asettamiseksi voit käyttää `setHideActiveIndicator(boolean)` -menetelmää. Siirtämällä `true` tähän menetelmään piilotetaan aktiivinen indikaattori aktiivisen `Tab`:n alla, kun taas `false`, oletusarvo, pitävät indikaattorin näkyvissä.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivointitilat {#activation-modes}

Hienosäätääksesi `TabbedPane`-komponentin käyttäytymistä näppäimistön navigoinnin aikana, voidaan asetukset `Activation`-tilalle määrittää, miten komponentin tulisi käyttäytyä.

- **`Auto`**: Kun asetetaan automaattisesti, nuolinäppäimillä navigointi näyttäen heti vastaavan välilehden komponentin.

- **`Manual`**: Kun asetettu manuaalisesti, välilehti saa fokuksen, mutta ei näy ennen kuin käyttäjä painaa välilyöntiä tai enter-näppäintä.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Poisto-optiot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavat välilehdet saavat sulkemispainikkeen välilehteen, joka laukaisee sulkemistapahtuman, kun sitä napsautetaan. `TabbedPane` määrää, miten tämä käytös käsitellään.

- **`Manual`**: Oletusarvoisesti poisto on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on itse käsiteltävä tämä tapahtuma haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti `AUTO` voidaan käyttää, mikä laukaisee tapahtuman ja poistaa `Tab`:in komponentista kehittäjälle, jolloin kehittäjän ei tarvitse toteuttaa tätä käytöstä manuaalisesti.

### Segmenttiohjaus <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` voidaan renderöidä segmenttiohjauksena ottamalla käyttöön `segment`-ominaisuus asetuksella `setSegment(true)`. Tässä tilassa välilehdet näytetään liukuvalla pillinä, joka korostaa aktiivista valintaa, tarjoten tiiviin vaihtoehdon tavanomaiselle välilehtiliittymälle.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Tyylit {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane` sisältää sisäänrakennetut `Expanse` ja `Theme` -vaihtoehdot, kuten muut webforJ-komponentit. Näitä voidaan käyttää nopeasti lisäämään tyylittelyä, joka välittää erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsee tyylitellä CSS:llä.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavat käytännöt ovat suositeltavia `TabbedPane`:n hyödyntämisessä sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmitelläksesi liittyvää sisältöä
    >- Jokaisen välilehden tulisi esittää erillinen kategoria tai toiminto sovelluksessa
    >- Ryhmittele samankaltaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitetut välilehdet**: Vältä käyttäjien ylivoimista liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointimallien käyttämistä puhtaamman käyttöliittymän saavuttamiseksi, kun se on tarpeen.

- **Selkeät etiketit**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
    >- Tarjoa selkeät ja tiivistetyt etiketit jokaiselle välilehdelle
    >- Etiketit tulisi kuvastaa sisältöä tai tarkoitusta, mikä helpottaa käyttäjien ymmärtämistä
    >- Hyödynnä kuvakkeita ja erottuvia värejä tarvittaessa

- **Näppäimistön navigointi**: Hyödynnä webforJ:n `TabbedPane`-näppäimistön navigointitukea, jotta vuorovaikutus `TabbedPane`:n kanssa olisi sujuvampaa ja intuitiivisempaa loppukäyttäjälle.

- **Oletusvälilehti**: Jos oletusvälilehti ei ole `TabbedPane`:n alussa, harkitse tämän välilehden asettamista oletukseksi oleelliselle tai usein käytetylle tiedolle.
