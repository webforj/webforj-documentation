---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 2e67673ef0ac49904be50764ef47ecb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

`TabbedPane`-luokka tarjoaa kompaktin ja järjestelmällisen tavan esittää sisältöä, joka on jaettu useisiin osioihin, joista jokainen on liitetty `Tab`-välilehteen. Käyttäjät voivat vaihtaa näiden osioiden välillä napsauttamalla vastaavia välilehtiä, jotka ovat usein merkitty tekstillä ja/tai kuvakkeilla. Tämä luokka yksinkertaistaa monipuolisten käyttöliittymien luomista, joissa erilainen sisältö tai lomakkeet on saatava käyttöön, mutta ei samanaikaisesti näkyville.

## Käyttötavat {#usages}

`TabbedPane`-luokka antaa kehittäjille tehokkaan työkalun organisoida ja esittää useita välilehtiä tai osioita käyttöliittymässä. Tässä on joitakin tyypillisiä skenaarioita, joissa saatat hyödyntää `TabbedPane`-luokkaa sovelluksessasi:

1. **Dokumentin katseluohjelma**: Toteuta dokumentin katseluohjelma, jossa jokainen välilehti edustaa erilaista asiakirjaa tai tiedostoa. Käyttäjät voivat helposti vaihtaa avoimien asiakirjojen välillä tehokkaasti multitaskauksen aikana.

2. **Tietojen hallinta**: Hyödynnä `TabbedPane`-luokkaa organisoidaksesi tietojen hallintatehtäviä, esimerkiksi:
    >- Eri tietoaineistot, jotka näkyvät sovelluksessa
    >- Erilaiset käyttäjäprofiilit voidaan näyttää erillisissä välilehdissä
    >- Eri profiilit käyttäjähallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osioita. Jokainen välilehti voi kapseloida tietyn moduulin toiminnot, jolloin käyttäjät voivat keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintohakemisto voi käyttää `TabbedPane`-luokkaa esittämään erilaisia projekteja tai tehtäviä. Jokainen välilehti voisi vastata erityistä projektia, mikä mahdollistaa käyttäjien hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee suorittaa erilaisia ohjelmia, `TabbedPane` voisi:
    >- Palvella sivupalkkina, joka mahdollistaa erilaisten sovellusten tai ohjelmien suorittamisen yhdellä sovelluksella, kuten mitä esitetään [`AppLayout`](./app-layout.md) -mallissa
    >- Luoda yläpalkin, joka voi palvella samankaltaista tarkoitusta tai esittää alisovelluksia jo valitun sovelluksen sisällä.
  
## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymän elementtejä, jotka voidaan lisätä välilehtipaneeleihin järjestämään ja vaihtamaan erilaisten sisältönäkymien välillä.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponenteina. Niitä on tarkoitus käyttää yhdessä välilehtipaneelien kanssa. Tämä luokka ei ole `Component` eikä sitä tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään lisättäessä niitä `TabbedPane`-luokkaan. Näillä ominaisuuksilla on noutajat ja asettajat, jotta mukauttaminen olisi helpompaa `TabbedPane`-luokassa.

1. **Key(`Object`)**: Edustaa `Tab`:in ainutlaatuista tunnistetta.

2. **Text(`String`)**: Teksti, joka näytetään otsikkona `Tab`-välilehdelle `TabbedPane`-luokassa. Tätä kutsutaan myös otsikoksi `getTitle()` ja `setTitle(String title)`-menetelmien avulla.

3. **Tooltip(`String`)**: Työkaluvihje, joka liittyy `Tab`:iin, ja joka näytetään, kun kursori leijuu `Tab`:in päällä.

4. **Enabled(`boolean`)**: Edustaa, onko `Tab` tällä hetkellä käytössä vai ei. Sitä voidaan muuttaa `setEnabled(boolean enabled)`-menetelmällä.

5. **Closeable(`boolean`)**: Edustaa, voiko `Tab` olla suljettavissa. Sitä voidaan muuttaa `setCloseable(boolean enabled)`-menetelmällä. Tämä lisää sulkemispainikkeen `Tab`:iin, jota käyttäjä voi napsauttaa, ja laukaisee poistamistapahtuman. `TabbedPane`-komponentti määrää, miten poisto käsitellään.

6. **Slot(`Component`)**: 
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`-komponentin toimivuuden parantamiseksi. Voit lisätä kuvakkeita, tunnisteita, lataussymboleita, tyhjentämis- tai nollausominaisuuksia, avatar/profiilikuvia ja muita hyödyllisiä komponentteja `Tab`:iin käyttäjille tarkoitetun merkityksen selkeyttämiseksi.
    Voit lisätä komponentin `Tab`:in `prefix`-slottiin rakennusvaiheessa. Voit myös käyttää `setPrefixComponent()` ja `setSuffixComponent()`-menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-manipulointi {#tab-manipulation}

Eri menetelmiä on olemassa, jotta kehittäjät voivat lisätä, lisätä, poistaa ja manipuloida erilaisia `Tab`-elementtien ominaisuuksia `TabbedPane`-luokassa.

### Välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()`-menetelmät ovat saatavilla erilaisissa ylikuormitettavissa muodoissa, jotta kehittäjillä olisi joustavuutta uusien välilehtien lisäämisessä `TabbedPane`-luokkaan. Välilehden lisääminen sijoittaa sen kaikkien aikaisemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`:in `TabbedPane`-luokkaan, jossa spesifioitu `String` toimii `Tab`:in tekstinä.
2. **`addTab(Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`-luokkaan.
3. **`addTab(String text, Component component)`** - Lisää `Tab`:in, jossa annettu `String` on `Tab`:in teksti, ja tarjottu `Component` näytetään `TabbedPane`-luokan sisältöosiossa.
4. **`addTab(Tab tab, Component component)`** - Lisää tarjottu `Tab` ja näyttää tarjotun `Component`:in `TabbedPane`-luokan sisältöosiossa.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssia `TabbedPane`-luokkaan, luoden erillinen `Tab` jokaiselle niistä, jolloin tekstiksi asetetaan `Component`:in nimi.

:::info
`add(Component... component)` määrittää siirretyn `Component`:in nimen kutsumalla `component.getName()` siirretylle argumentille.
:::

### Välilehden lisääminen varauspaikkaan {#inserting-a-tab}

Lisäksi kuin `Tab`-välilehden lisääminen nykyisten välilehtien loppuun, on myös mahdollista luoda uusi välilehti määriteltyyn sijaintiin. Tämän tekemiseksi useita ylikuormitettavia versioita `insertTab()`-menetelmästä on saatavilla.

1. **`insertTab(int index, String text)`** - Lisää `Tab`-välilehden `TabbedPane`-luokkaan antamalla indeksin ja spesifioitu `String` teksti `Tab`:issa.
2. **`insertTab(int index, Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`-luokkaan määritettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`-välilehden, jossa annettu `String` on `Tab`:in teksti, ja tarjottu `Component` näkyy `TabbedPane`-luokan sisältöosiossa.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää tarjottu `Tab` ja näyttää tarjotun `Component`:in `TabbedPane`-luokan sisältöosiossa.

### Välilehden poistaminen {#removing-a-tab}

Poistaaksesi yhden `Tab`-välin `TabbedPane`-luokasta, käytä yhtä seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:in `TabbedPane`-luokasta siirtämällä poistettavan `Tab`-instanssin.
2. **`removeTab(int index)`** - Poistaa `Tab`-välilehden `TabbedPane`-luokasta määrittämällä poistettavan `Tab`:n indeksi.

Yksittäisen `Tab`:n poistamisen kahden ylemmän menetelmän lisäksi käytä **`removeAllTabs()`** -menetelmää tyhjentääksesi `TabbedPane`-luokan kaikista välilehdistä.

:::info
`remove()` ja `removeAll()`-menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Välilehti/komponentti-assosiaatio {#tabcomponent-association}

Vaihtaaksesi `Component`:in, joka näytetään tiettyä `Tab`:ia varten, kutsu `setComponentFor()`-menetelmää ja siirrä joko `Tab`-instanssi tai kyseisen `Tab`:in indeksi `TabbedPane`-luokassa.

:::info
Jos tätä menetelmää käytetään `Tab`:ssa, joka on jo yhdistetty `Component`:iin, aikaisemmin yhdistetty `Component` tuhotaan.
:::

## Konfigurointi ja asettelu {#configuration-and-layout}

`TabbedPane`-luokalla on kaksi osaa: `Tab`, joka näkyy määritetyssä sijainnissa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composite-components) -komponentti, mikä mahdollistaa monimutkaisempien komponenttien esittämisen välilehden sisältöosiossa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien läpi pyyhkäisemällä. Tämä on ihanteellinen mobiilisovellukselle, mutta se voidaan konfiguroida myös sisäänrakennetun menetelmän avulla tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletuksena poissa käytöstä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittaminen {#tab-placement}

`Tabs` `TabbedPane`-luokassa voidaan sijoittaa eri paikkoihin komponentin sisällä sovelluskehittäjän mieltymyksen mukaan. Tarjotut vaihtoehdot asetetaan käytettävissä olevan enum-tyyppisen koodin avulla, jolla on arvot `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetus on `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Kohdistus {#alignment}

Lisäksi kuin `Tab`-elementtien sijoittamisen muuttaminen `TabbedPane`-luokassa, on myös mahdollista konfiguroida, miten välilehdet kohdistuvat komponentin sisällä. Oletuksena asetuksena on `AUTO`, mikä sallii välilehtien sijoituksen määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat asemaa suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Raita ja aktiivisuustunnistin {#border-and-activity-indicator}

`TabbedPane`-luokassa on oletuksena raita välilehille, joka sijaitsee asetettavasta `Placement`-arvosta riippuen. Tämä raita auttaa visualisoimaan tilaa, jonka erilaiset välilehdet paneelissa ottavat. 

Kun `Tab`-välilehtiä napsautetaan, oletuksena aktiivisuustunnistin näytetään lähellä tuota `Tab`:ia korostaakseen, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä vaihtoehtoja voidaan mukauttaa kehittäjän avulla muuttamalla boolean-arvoja asianmukaisilla asettajamenetelmillä. Muuttaaksesi, näytetäänkö raitaa vai ei, voit käyttää `setBorderless(boolean)`-menetelmää, jolloin `true` piilottaa raidan ja `false`, oletusarvo, näyttää raidan.

:::info
Tämä raita ei koske koko `TabbedPane`-komponenttia, ja palvelee vain erottimena välilehtien ja komponentin sisällön välillä.
:::

Aktiivisen tunnistimen näkyvyyden asettamiseen voidaan käyttää `setHideActiveIndicator(boolean)`-menetelmää. Antamalla `true` tälle menetelmälle piilotat aktiivisen tunnistimen aktiivisen `Tab`:in alla, kun taas `false`, oletusarvo, pitää tunnistimen näkyvissä.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivointitilat {#activation-modes}

Jotta voit hallita tarkemmin, miten `TabbedPane` käyttäytyy näppäimistöllä navigoitaessa, aktivointitila voidaan asettaa määrittämään, miten komponentin tulisi käyttäytyä.

- **`Auto`**: Kun se on asetettu automaattiseksi, välilehtien navigointi nuolinäppäimillä näyttää vastaavan välilehti-komponentin välittömästi.

- **`Manual`**: Kun se on asetettu manuaaliseksi, välilehti saa kohdistuksen, mutta ei näy, ennen kuin käyttäjä painaa välilyöntiä tai enteriä.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettavaksi. Suljettavat välilehdet saavat sulkemispainikkeen, joka laukaisee sulku-tapahtuman napsautettaessa. `TabbedPane` määrää, miten tämä käyttäytyminen käsitellään.

- **`Manual`**: Oletuksena poisto on asetettu `MANUAL`:ksi, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on käsiteltävä se haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti voidaan käyttää `AUTO`, joka laukaisee tapahtuman, ja poistaa myös `Tab`:n komponentista kehittäjälle, joten kehittäjän ei tarvitse toteuttaa tätä käyttäytymistä manuaalisesti. 

## Tyylittely {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane`-luokka sisältää sisäänrakennetut `Expanse`- ja `Theme`-vaihtoehdot, jotka ovat samanlaisia muiden webforJ komponenttien kanssa. Näitä voidaan käyttää nopeasti lisäämään tyylit, jotka välittävät erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsee tyylittää CSS:llä.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Parhaat käytännöt {#best-practices}

Seuraavia käytäntöjä suositellaan käytettäessä `TabbedPane`-luokkaa sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittelemään liittyvää sisältöä
    >- Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toimintaa sovelluksessasi
    >- Ryhmittele samankaltaisia tai loogisia välilehtiä lähelle toisiaan

- **Rajoitetut välilehdet**: Vältä käyttäjien ylivoimaista tilannetta käyttämällä liian monia välilehtiä. Harkitse hierarkkisen rakenteen tai muiden navigointimallien käyttöä tarvittaessa siistin käyttöliittymän saavuttamiseksi.

- **Selkeät tunnisteet**: Merkitse välilehdet selkeästi intuitiiviseen käyttöön
    >- Anna selkeät ja tiiviit tunnisteet jokaiselle välilehdelle
    >- Tunnisteiden tulisi heijastaa sisältöä tai tarkoitusta, jolloin käyttäjät ymmärtävät sen helposti
    >- Hyödynnä kuvakkeita ja erottuvia värejä, kun se on tarpeellista

- **Näppäimistön navigointi**: Käytä webforJ:n `TabbedPane`-näppäimistön navigointitukea tehdäksesi vuorovaikutuksesta `TabbedPane`-luokan kanssa sujuvampaa ja intuitiivisempaa loppukäyttäjälle.

- **Oletusvälilehti**: Jos oletusvälilehti ei sijaitse ensimmäisenä `TabbedPane`-luokassa, harkitse tämän välilehden asettamista oletukseksi tärkeää tai yleisesti käytettävää tietoa varten.
