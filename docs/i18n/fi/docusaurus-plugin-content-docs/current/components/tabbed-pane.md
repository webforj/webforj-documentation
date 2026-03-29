---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: d1522c6bd692420a02df494fa0509a23
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useita sisältöosioita voidaan järjestää yhden `TabbedPane`:n alle, jossa jokainen osio liittyy klikattavaan `Tab`:iin. Vain yksi osio on näkyvissä kerrallaan, ja tabit voivat näyttää tekstiä, ikoneita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käytännöt {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on muutamia tyypillisiä skenaarioita, joissa voit hyödyntää `TabbedPane`:ä sovelluksessasi:

1. **Dokumentin katseluohjelma**: Dokumentin katseluohjelman toteuttaminen, jossa jokainen tab edustaa eri dokumenttia tai tiedostoa. Käyttäjät voivat helposti vaihtaa avoimien dokumenttien välillä tehokkaan moniajon vuoksi.

2. **Tietojen hallinta**: Hyödynnä `TabbedPane`:ä järjestääksesi tietojen hallintatehtäviä, esimerkiksi:
    >- Eri tietojoukon näyttäminen sovelluksessa
    >- Eri käyttäjäprofiilien näyttäminen erillisissä tabissa
    >- Eri profiilit käyttäjähallintajärjestelmässä

3. **Moduulivalinta**: `TabbedPane` voi edustaa eri moduuleja tai osioita. Jokainen tab voi kapseloida tietyn moduulin toiminnallisuudet, jolloin käyttäjät voivat keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintasovellukset voivat käyttää `TabbedPane`:ä esittääkseen eri projekteja tai tehtäviä. Jokainen tab voi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee käyttää erilaisia ohjelmia, `TabbedPane` voisi:
    >- Toimia sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten mitä esitetään [`AppLayout`](./app-layout.md) -mallissa
    >- Luoda pääpalkin, joka voi palvella samanlaista tarkoitusta tai edustaa alisovelluksia jo valitussa sovelluksessa.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymän elementtejä, joita voidaan lisätä tabbed paneihin järjestämään ja vaihtamaan eri sisältönäkymiä.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponenteina. Ne on tarkoitettu käytettäväksi yhdessä tabbed panejen kanssa. Tämä luokka ei ole `Component` eikä sitä tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään niiden lisäämisessä `TabbedPane`:en. Näillä ominaisuuksilla on getterit ja setterit, jotka helpottavat mukautuksia `TabbedPane`:ssä.

1. **Key(`Object`)**: Edustaa `Tab`:in ainutlaatuista tunnistetta.

2. **Text(`String`)**: Teksti, joka näytetään `Tab`:in otsikkona `TabbedPane`:ssä. Tätä kutsutaan myös otsikoksi `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

3. **Tooltip(`String`)**: Vinkkiteksti, joka liittyy `Tab`:iin ja näytetään, kun kursori leijuu `Tab`:in päällä.

4. **Enabled(`boolean`)**: Edustaa, onko `Tab` tällä hetkellä käytössä vai ei. Voidaan muokata `setEnabled(boolean enabled)` -menetelmällä.

5. **Closeable(`boolean`)**: Edustaa, voidaanko `Tab` sulkea. Voidaan muokata `setCloseable(boolean enabled)` -menetelmällä. Tämä lisää sulkemispainikkeen `Tab`:iin, jota käyttäjä voi klikata, ja laukaisee poisto-tapahtuman. `TabbedPane`-komponentti määrää, miten poistaminen käsitellään.

6. **Slot(`Component`)**: 
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`:in kyvykkyyden parantamiseksi. Voit lisätä ikoneita, etikettejä, lataussymboleita, tyhjennys/nollausmahdollisuuksia, avatar/profiiliviestejä ja muita hyödyllisiä komponentteja `Tab`:in sisään, jotta käyttäjille voidaan selkeyttää tarkoitetut merkitykset.
    Voit lisätä komponentin `prefix`-slotille `Tab`:ia rakennettaessa. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä eri komponenttien lisäämiseksi ennen ja jälkeen näytettävän vaihtoehdon `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumentit", TablerIcon.create("tiedostot")));
        ```

## `Tab`-manipulointi {#tab-manipulation}

Eri menetelmiä on olemassa, jotka mahdollistavat kehittäjille `Tab`:ien lisäämisen, lisäämisen, poistamisen ja eri ominaisuuksien manipuloimisen `TabbedPane`:ssa.

### `Tab`-lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmät ovat saatavilla eri ylikuormitetuissa muodoissa, mikä antaa kehittäjille joustavuutta uusien välilehtien lisäämisessä `TabbedPane`:en. `Tab`:in lisääminen sijoittaa sen kaikkien aikaisemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`:in `TabbedPane`:en määritellyllä `String`:llä `Tab`:in tekstinä.
2. **`addTab(Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`:en.
3. **`addTab(String text, Component component)`** - Adds a `Tab` with the given `String` as the text of the `Tab`, and the provided `Component` displayed in the content section of the `TabbedPane`.
4. **`addTab(Tab tab, Component component)`** - Lisää annetun `Tab`:in ja näyttää annetun `Component`:in sisällön osiossa `TabbedPane`:ssä.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`:en, luoden erillisen `Tab` jokaiselle, ja tekstinä käytetään `Component`:in nimeä.

:::info
`add(Component... component)` määrittää siirretyn `Component`:n nimen kutsumalla `component.getName()` siirretyn argumentin päällä.
:::

### `Tab`-lisääminen {#inserting-a-tab}

Lisäksi uuden `Tab`:in luominen voidaan tehdä määritellyssä sijainnissa. Tähän tarkoitukseen on saatavilla useita ylikuormitetun version `insertTab()` -menetelmistä.

1. **`insertTab(int index, String text)`** - Lisää `Tab`:in `TabbedPane`:en annetussa indeksissä määritellyllä `String`:llä `Tab`:in tekstinä.
2. **`insertTab(int index, Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`:en tiettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`:in annetulla `String`:llä `Tab`:in tekstinä ja annettu `Component` näytetään sisällön osiossa `TabbedPane`:ssä.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annetun `Tab`:in ja näyttää annetun `Component`:in sisällön osiossa `TabbedPane`:ssä.

### `Tab`-poistaminen {#removing-a-tab}

Yksittäisen `Tab`:in poistamiseksi `TabbedPane`:sta, käytä yhtä seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:in `TabbedPane`:sta välittämällä poistettavan Tab-instanssin.
2. **`removeTab(int index)`** - Poistaa `Tab`:in `TabbedPane`:sta määrittämällä poistettavan `Tab`:in indeksi.

Yksittäisten `Tab`:ien poistamiselle kahden yllä olevan menetelmän lisäksi käytetään **`removeAllTabs()`** -menetelmää tyhjentämään `TabbedPane` kaikista välilehdistä.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Tab/Component-yhteys {#tabcomponent-association}

Muuttamalla `Component`:ia, joka näytetään tietylle `Tab`:ille, kutsu `setComponentFor()` -menetelmää ja välitä joko `Tab`:in instanssi tai kyseisen `Tab`:in indeksi `TabbedPane`:ssä.

:::info
Jos tätä menetelmää käytetään `Tab`:in yhteydessä, joka on jo liitetty `Component`:iin, aikaisemmin liitetty `Component` tuhotaan.
:::

## Konfigurointi ja asettelu {#configuration-and-layout}

`TabbedPane`-luokalla on kaksi osaa: `Tab`, joka näkyy määritellyssä sijainnissa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composite-components) -komponentti, joka mahdollistaa monimutkaisempien komponenttien näyttämisen `tab`:in sisältöosiossa.

### Swipe-ominaisuus {#swiping}

`TabbedPane` tukee navigointia eri välilehtien välillä pyyhkäisemällä. Tämä ominaisuus on ihanteellinen mobiilisovelluksille, mutta se voidaan myös konfiguroida sisäänrakennetun menetelmän avulla hiiren pyyhkäisyn tukemiseksi. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletuksena pois päältä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittelu {#tab-placement}

`Tabs` `TabbedPane`:ssä voidaan sijoittaa eri paikkoihin komponentin sisällä sovelluksen kehittäjän mieltymysten mukaan. Tarjotut vaihtoehdot asetetaan käytettävissä olevan enumeroinnin kautta, jonka arvot ovat `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetuksena on `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Kohdistus {#alignment}

Lisäksi `Tab`:ien sijoittamisen muuttamiseen `TabbedPane`:ssä, on myös mahdollista konfiguroida, miten välilehdet kohdistuvat komponentissa. Oletuksena asetus `AUTO` on käytössä, joka mahdollistaa välilehtien sijoittelun määrittää niiden kohdistuksen.

Muita vaihtoehtoja ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Reunus ja aktiviteetti-indikaattori {#border-and-activity-indicator}

`TabbedPane`:lla on oletuksena reunus näytettäväksi sen välilehdille, sijoitettuna sen mukaan, mikä `Placement` on asetettu. Tämä reunus auttaa visualisoimaan tilan, jonka erilaiset välilehdet paneelissa vievät.

Kun `Tab`ia klikataan, oletuksena aktiviteetti-indikaattori näytetään kyseisen `Tab`:in vieressä, mikä auttaa korostamaan, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä asetuksia voivat kehittäjät mukauttaa muuttamalla boolean-arvoja käyttämällä asiaankuuluvia setter-menetelmiä. Muuttaaksesi näkyykö reunus vai ei, voit käyttää `setBorderless(boolean)` -menetelmää, jossa `true` piilottaa reunuksen ja `false`, oletusarvo, näyttää reunuksen.

:::info
Tämä reunus ei koske koko `TabbedPane`-komponenttia, ja sillä on vain tarkoitus toimia erottimena välilehtien ja komponentin sisällön välillä.
:::

Aseta aktiivisen indikaattorin näkyvyyttä varten voidaan käyttää `setHideActiveIndicator(boolean)` -menetelmää. Tämän menetelmän syöttäminen `true` piilottaa aktiivisen indikaattorin aktiivisen `Tab`:in alta, kun taas `false`, oletusarvo, pitää indikaattorin näkyvissä.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivointitilat {#activation-modes}

Saadaksesi tarkempaa hallintaa siitä, miten `TabbedPane` käyttäytyy, kun sitä navigoidaan näppäimistöllä, aktivointitila voidaan asettaa määrittämään, miten komponentin tulisi käyttäytyä.

- **`Auto`**: Kun tämä asetetaan automaattiseksi, nuolinäppäimillä navigointi näyttää välittömästi vastaavan tab-komponentin.

- **`Manual`**: Kun tämä asetetaan manuaaliseksi, tab saa fokuksen, mutta sitä ei näytetä, ennen kuin käyttäjä painaa väli- tai enter-näppäintä.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Poisto-oikeudet {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavat välilehdet saavat sulkupainikkeen välilehteen, mikä laukaisee sulkemistapahtuman, kun sitä klikataan. `TabbedPane` määrää, miten tämä käyttäytyminen käsitellään.

- **`Manual`**: Oletuksena poisto on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on huolehdittava tästä tapahtumasta haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti voidaan käyttää `AUTO`, joka laukaisee tapahtuman ja poistaa myös `Tab`:in komponentista kehittäjälle, eikä kehittäjän tarvitse toteuttaa tätä käyttäytymistä manuaalisesti.

## Tyylitus {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane` sisältää sisäänrakennetut `Expanse` ja `Theme` -vaihtoehdot, jotka ovat samanlaisia kuin muiden webforJ-komponenttien. Näitä voidaan käyttää nopeasti lisäämään tyylittelua, joka välittää erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsee tyylitellä CSS:llä.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Parhaat käytännöt {#best-practices}

Seuraavat käytännöt suositellaan `TabbedPane`:n käyttämiseksi sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittelemään liittyvää sisältöä
    >- Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toiminnallisuutta sovelluksessasi
    >- Ryhmittele samankaltaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitetut välilehdet**: Vältä käyttäjien ylivoimista liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointimallien käyttöä puhtaan käyttöliittymän saavuttamiseksi

- **Selkeät etikettit**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
    >- Tarjoa selkeät ja tiiviit etiketit jokaiselle välilehdelle
    >- Etiketit tulisi heijastaa sisältöä tai tarkoitusta, mikä tekee siitä helppoa käyttäjille ymmärtää
    >- Hyödynnä ikoneita ja erottuvia värejä tarpeen mukaan

- **Näppäimistön navigointi** Käytä webforJ:n `TabbedPane` -näppäimistön navigointitukea, jotta käyttäjien vuorovaikutus `TabbedPane`:n kanssa olisi saumatonta ja intuitiivista

- **Oletusvälilehti**: Jos oletusvälilehti ei ole sijoitettu `TabbedPane`:n alkuun, harkitse tämän välilehden asettamista oletukseksi olennaista tai usein käytettyä tietoa varten.
