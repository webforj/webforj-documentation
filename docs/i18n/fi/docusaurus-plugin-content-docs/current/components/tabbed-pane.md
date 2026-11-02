---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons and customizable tab properties.
_i18n_hash: 2d2e78638206283a7423cea736e3bf2c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useita sisältöosioita voidaan järjestää yhden `TabbedPane` alle, jossa kukin osio liittyy klikattavaan `Tab`:iin. Vain yksi osio on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, kuvakkeita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseksi ja esittämiseksi käyttöliittymässä. Tässä on joitakin tyypillisiä tilanteita, joissa voit käyttää `TabbedPane`-komponenttia sovelluksessasi:

1. **Dokumenttinäkymä**: Dokumenttinäkymän toteuttaminen, jossa jokainen välilehti edustaa eri dokumenttia tai tiedostoa. Käyttäjät voivat helposti vaihtaa avointen dokumenttien välillä tehokkaasti moniajoa varten.

2. **Tietojen hallinta**: Käytä `TabbedPane`-komponenttia organisoidaksesi tietojen hallintatehtäviä, esimerkiksi:
    - Erilaiset tietosarjat, jotka näytetään sovelluksessa
    - Erilaisia käyttäjäprofiileja voidaan näyttää erillisissä välilehdissä
    - Erilaiset profiilit käyttäjähallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osioita. Kukin välilehti voi kapseloida tietyn moduulin toiminnallisuudet, jolloin käyttäjät voivat keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävienhallintaohjelmat voivat käyttää `TabbedPane`-komponenttia edustamaan erilaisia projekteja tai tehtäviä. Jokainen välilehti voisi vastata erityistä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee suorittaa erilaisia ohjelmia, `TabbedPane` voisi:
    - Toimia sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien ajamisen yhdessä sovelluksessa, kuten [`AppLayout`](./app-layout.md) -mallissa
    - Luoda yläpalkin, joka voi palvella samanlaista tarkoitusta, tai edustaa alisovelluksia jo valitussa sovelluksessa

## Välilehdet <DocChip chip='name' label="dwc-tab" /> {#tabs}

Välilehdet ovat käyttöliittymän elementtejä, joita voidaan lisätä välilehtipaneeleihin eri sisältönäkymien järjestämiseksi ja välillä vaihtamiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponentteina. Niitä on tarkoitus käyttää yhdessä välilehtipaneelien kanssa. Tämä luokka ei ole `Component` ja sitä ei tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään lisättäessä niitä `TabbedPane`:iin. Näillä ominaisuuksilla on getters ja setters, jotka helpottavat mukauttamista `TabbedPane`:ssä.

1. **Teksti(`String`)**: Teksti, joka näytetään `Tab`:in otsikkona `TabbedPane`:ssä. Tätä kutsutaan myös otsikoksi `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

2. **Työkaluvihje(`String`)**: Työkaluvihjeen teksti, joka liittyy `Tab`:iin ja näytetään, kun osoitin leijuu `Tab`:in yli.

3. **Aktivointi(`boolean`)**: Edustaa, onko `Tab` käytössä vai ei. Voidaan muuttaa `setEnabled(boolean enabled)` -menetelmällä.

4. **Suljettava(`boolean`)**: Edustaa, voidaanko `Tab` sulkea. Voidaan muuttaa `setClosable(boolean closable)` -menetelmällä. Tämä lisää sulkupainikkeen `Tab`:iin, jota voidaan klikata, ja se laukaisee poistamistapahtuman. `TabbedPane`-komponentti määrää, miten poistaminen käsitellään.

5. **Väliosa(`Component`)**:
   Väliosat tarjoavat joustavia vaihtoehtoja `Tab`:in kyvykkyyden parantamiseksi. Voit lisätä kuvakkeita, etikettejä, lataussyklejä, tyhjentää/aloittaa uudelleen, avatar/profiilikuvaa sekä muita hyödyllisiä komponentteja `Tab`:iin, jotta voit selventää käyttäjille tarkoitettua merkitystä.
   Voit lisätä komponentin `Tab`:in `prefix`-väliosaan konstruktion aikana. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä eri komponenttien lisäämiseksi `Tab`:in näyttöoption eteen ja taakse.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumentit", TablerIcon.create("files")));
        ```

## `Tab`-manipulointi {#tab-manipulation}

Eri menetelmiä on olemassa, jotta kehittäjät voivat lisätä, sijoittaa, poistaa ja manipuloida erilaisia `Tab`-elementtien ominaisuuksia `TabbedPane`:ssä.

### Välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmät ovat olemassa eri ylikuormitettuna versioina, jotta kehittäjillä on joustavuutta uusien välilehtien lisäämisessä `TabbedPane`:iin. Välilehden lisääminen asettaa sen kaikkien aikaisemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`**: lisää `Tab`:in `TabbedPane`:en määritellyllä `String`-tekstillä.
2. **`addTab(Tab tab)`**: lisää parametrina annetun `Tab`:in `TabbedPane`:en.
3. **`addTab(String text, Component component)`**: lisää `Tab`:in, jossa on annettu `String` tekstinä ja tarjottu `Component` näytetään `TabbedPane`:n sisältöosassa.
4. **`addTab(Tab tab, Component component)`**: lisää annettu `Tab` ja näyttää tarjoamasi `Component` `TabbedPane`:n sisältöosassa.
5. **`add(Component... component)`**: lisää yksi tai useampi `Component` -instanssi `TabbedPane`:eseen, luomalla erillinen `Tab` jokaiselle niistä, ja tekstinä käytetään `Component`:in nimeä.

:::info
`add(Component... component)` määrittää siirrettyjen `Component`:ien nimen kutsumalla `component.getName()` siirretyltä argumentilta.
:::

### Välilehden lisääminen osoitteeseen {#inserting-a-tab}

Välilehden lisäämisen lisäksi myös uusien välilehtien luominen määritellyssä sijainnissa on mahdollista. Tähän on useita ylikuormitettuja versioita `insertTab()`.

1. **`insertTab(int index, String text)`**: lisää `Tab`:in `TabbedPane`:en annettuun indeksiin määritellyllä `String`-tekstillä.
2. **`insertTab(int index, Tab tab)`**: lisää parametrina annetun `Tab`:in `TabbedPane`:en annettuun indeksiin.
3. **`insertTab(int index, String text, Component component)`**: lisää `Tab`:in, jossa on annettu `String`-teksti ja tarjottu `Component`, joka näytetään `TabbedPane`:n sisältöosassa.
4. **`insertTab(int index, Tab tab, Component component)`**: lisää annettu `Tab` ja näyttää tarjotun `Component` `TabbedPane`:n sisältöosassa.

### Välilehden poistaminen {#removing-a-tab}

Poistaaksesi yksittäisen `Tab`:in `TabbedPane`:sta, käytä jotakuta seuraavista menetelmistä:

1. **`removeTab(Tab tab)`**: poistaa `Tab`:in `TabbedPane`:sta antamalla poistettavan Tab-instanssin.
2. **`removeTab(int index)`**: poistaa `Tab`:in `TabbedPane`:sta määrittämällä poistettavan `Tab`:in indeksin.

Yksittäisen `Tab`:in poistamisen kahden edellä mainitun menetelmän lisäksi käytä **`removeAllTabs()`**-menetelmää tyhjentääksesi `TabbedPane`:n kaikista välikkeistä.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Välilehden/komponentin yhdistäminen {#tabcomponent-association}

Vaihtaaksesi näytettävän `Component`:in tietylle `Tab`:ille, kutsu `setComponentFor()`-menetelmää ja siirrä jompikumpi `Tab`-instanssi tai kyseisen `Tab`:in indeksi `TabbedPane`:ssä.

:::info
Jos tätä menetelmää käytetään `Tab`:illa, joka on jo yhdistetty `Component`:iin, aikaisemmin yhdistetty `Component` tuhotaan.
:::

## Kokoonpano ja asettelu {#configuration-and-layout}

`TabbedPane`-luokalla on kaksi osaa: `Tab`, joka näytetään tietyssä paikassa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Komposiitti`](/docs/building-ui/composing-components) -komponentti, joka sallii monimutkaisempien komponenttien näyttämisen välilehden sisältöosassa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien välillä pyyhkäisemällä. Tämä on ideaalista mobiilisovelluksessa, mutta se voidaan myös määrittää sisäänrakennetulla menetelmällä tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletuksena poissa käytöstä, mutta ne voidaan ottaa käyttöön `setSwipeable(boolean)` ja `setSwipeWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittaminen {#tab-placement}

`TabbedPane`:n sisällä olevat välilehdet voidaan sijoittaa eri paikkoihin kehittäjien mieltymysten mukaan. Tarjotut vaihtoehdot määritetään annettavan enum-sarjan avulla, jossa on arvot `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetus on `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Kohdistus {#alignment}

Lisäksi kuin `Tab`-elementtien sijoittamisen muuttaminen `TabbedPane`:ssä, on myös mahdollista määrittää, miten välilehdet kohdistuvat komponentissa. Oletuksena asetuksena on `AUTO`, joka sallii välilehtien sijoituksen määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään saatavilla olevan tilan.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Kehys ja aktiivisuustunnistin {#border-and-activity-indicator}

`TabbedPane`:ssa on oletuksena kehys, joka näkyy sen välilehdillä, sijoitettuna riippuen asetetusta `Placement`:ista. Tämä kehys auttaa visualisoimaan tilaa, jonka eri välilehdet paneelissa vievät.

Kun `Tab`:ia klikataan, oletuksena aktiivisuustunnistin näkyy lähellä tätä `Tab`:ia, mikä auttaa korostamaan, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä vaihtoehtoja voidaan mukauttaa muuttamalla boolean-arvoja käyttämällä asianmukaisia setter-menetelmiä. Muuttaaksesi kehykseen näkyvyyttä, voit käyttää `setBorderless(boolean)` -menetelmää, jossa `true` piilottaa kehyksen ja `false`, oletusarvo, näyttää kehyksen.

:::info
Tämä kehys ei koske koko `TabbedPane`-komponenttia, ja se palvelee vain erottimena välilehtien ja komponentin sisällön välillä.
:::

Virheiden näyttämisen säätelemiseksi voidaan käyttää `setHideActiveIndicator(boolean)` -menetelmää. Antamalla `true` tälle menetelmälle piilotetaan aktiivinen tunnistin aktiivisen `Tab`:in alle, kun taas `false`, oletusarvo, pitää tunnistimen näkyvillä.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivointitavat {#activation-modes}

Saadaksesi tarkempaa kontrollia siitä, miten `TabbedPane` käyttäytyy näppäimistön navigoinnissa, aktivointitila voidaan määrätä määrittämään, miten komponentin tulee käyttäytyä.

- **`Auto`**: kun se asetetaan automaattiseksi, nuolinäppäimellä navigoitaessa välilehdet näyttävät heti vastaavan välilehden komponentin.

- **`Manual`**: kun se asetetaan manuaaliseksi, välilehti saa fokuksen, mutta ei näy ennen kuin käyttäjä painaa välilyöntiä tai enteriä.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavissa välikkeissä on sulkupainike, joka lisätään välilehteen ja laukaisee sulkemistapahtuman, kun sitä klikataan. `TabbedPane` määrää, miten tämä käyttäytyminen käsitellään.

- **`Manual`**: oletuksena poistaminen on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on hoidettava tämä tapahtuma haluamallaan tavalla.

- **`Auto`**: vaihtoehtoisesti voidaan käyttää `AUTO`, joka laukaisee tapahtuman ja myös poistaa `Tab`-in komponentista kehittäjälle, mikä poistaa tarpeen implementoida tämä käyttäytyminen manuaalisesti.

### Segmentohjaus <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` voidaan renderöidä segmenttinä ohjauksena ottamalla käyttöön `segment`-ominaisuus `setSegment(true)`. Tässä tilassa välilehdet näytetään liukuvalla pilleritunnistimella, joka korostaa aktiivista valintaa, tarjoten kompaktin vaihtoehdon tavanomaiselle välilehtirajapinnalle.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Tyylittely {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane` tulee sisäänrakennettujen `Expanse`- ja `Theme`-vaihtoehtojen kanssa, samoin kuin muut webforJ-komponentit. Näitä voidaan käyttää nopeasti lisäämään tyylittelyä, joka välittää erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia on tarpeen tyylitellä CSS:llä.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavia käytäntöjä suositellaan `TabbedPane`:n käytölle sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogiseen ryhmittelyyn liittyvälle sisällölle:
    - Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toiminnallisuutta sovelluksessasi.
    - Ryhmittele samankaltaiset tai loogiset välilehdet lähelle toisiaan.

- **Rajoitetut välilehdet**: Vältä käyttäjiä ylikuormittamista liian monilla välilehdillä. Harkitse hierarkisen rakenteen tai muiden navigointimallien käyttöä, kun se on käytännöllistä selkeän käyttöliittymän saamiseksi.

- **Selkeät etiketti**: Merkitse välilehdit selkeästi intuitiiviseen käyttöön:
    - Tarjoa selkeät ja ytimekkäät etiketit jokaiselle välilehdelle.
    - Etiketit tulisi heijastaa sisältöä tai tarkoitusta, jotta käyttäjille olisi helppoa ymmärtää.
    - Käytä kuvakkeita ja erottuvia värejä siellä, missä se on käytännöllistä.

- **Näppäimistön navigointi**: Hyödynnä webforJ:n `TabbedPane`-näppäimistön navigointitukea vuorovaikutuksen tekemiseksi `TabbedPane`:n kanssa sujuvammaksi ja intuitiivisemmaksi loppukäyttäjille.

- **Oletusvälilehti**: Jos oletusvälilehti ei ole sijoitettu `TabbedPane`:n alkuun, harkitse tämän välilehden asettamista oletusarvoksi tärkeälle tai yleisesti käytetylle informaatiolle.
