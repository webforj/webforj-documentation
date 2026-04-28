---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
sidebar_class_name: new-content
_i18n_hash: 790dce3f2bce2da54e03b7407c11204b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useita sisältö-osioita voidaan järjestää yhden `TabbedPane` alle, joista jokainen on sidottu napsautettavaan `Tab`-elementtiin. Vain yksi osa on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, kuvakkeita tai molempia auttaakseen käyttäjiä siirtymään niiden välillä.

<!-- INTRO_END -->

## Käyttötavat {#usages}

`TabbedPane`-luokka antaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitakin tyypillisiä skenaarioita, joissa voit käyttää `TabbedPane`-komponenttia sovelluksessasi:

1. **Dokumentin näyttö**: Dokumentin näyttö, jossa jokainen välilehti edustaa eri asiakirjaa tai tiedostoa. Käyttäjät voivat helposti vaihtaa avointen asiakirjojen välillä tehokasta moniajoa varten.

2. **Tietojen hallinta**: Käytä `TabbedPane`-komponenttia tietojen hallintaan, esimerkiksi:
    >- Eri tietojoukkojen näyttäminen sovelluksessa
    >- Erilaisia käyttäjäprofiileja voidaan näyttää erillisissä välilehdissä
    >- Eri profiilit käyttäjähallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osioita. Jokainen välilehti voi kapseloida tietyn moduulin toiminnot, jolloin käyttäjät voivat keskittyä yhteen sovelluksen osaan kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintasovellukset voivat käyttää `TabbedPane`-komponenttia erilaisten projektien tai tehtävien esittämiseen. Jokainen välilehti voisi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee suorittaa eri ohjelmia, `TabbedPane` voisi:
    >- Palvella sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten [`AppLayout`](./app-layout.md) -mallissa näkyy
    >- Luoda yläpalkin, joka voi palvella samanlaista tarkoitusta tai edustaa alisovelluksia jo valitun sovelluksen sisällä.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymäelementtejä, jotka voidaan lisätä tabulointi-paneeleihin erilaisten sisällönäkymien järjestämiseksi ja vaihtamiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponenteina. Ne on tarkoitettu käytettäväksi yhdessä tabulointi-paneelien kanssa. Tämä luokka ei ole `Component` ja sitä ei tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään niiden lisäämisessä `TabbedPane`-komponenttiin. Näillä ominaisuuksilla on getterit ja setterit, jotka helpottavat muokkausta `TabbedPane`-komponentissa.

1. **Nimi(`Object`)**: Edustaa `Tab`-välilehden ainutlaatuista tunnistetta.

2. **Teksti(`String`)**: Teksti, joka näytetään otsikkona `Tab`-välilehdessä `TabbedPane`-komponentissa. Tätä kutsutaan myös otsikoksi `getTitle()`- ja `setTitle(String title)`-menetelmien kautta.

3. **Työkaluvihje(`String`)**: Työkaluvihjeteksti, joka liittyy `Tab`-välilehteen ja joka näytetään, kun kursori leijuu `Tab`-välilehden yli.

4. **Aktivoitu(`boolean`)**: Edustaa, onko `Tab`-välilehti tällä hetkellä aktiivinen vai ei. Voidaan muokata `setEnabled(boolean enabled)`-menetelmällä.

5. **Suljettava(`boolean`)**: Edustaa, voidaananko `Tab`-välilehti sulkea. Voidaan muokata `setCloseable(boolean enabled)`-menetelmällä. Tämä lisää lähetynapin `Tab`-välilehteen, jota käyttäjä voi napsauttaa, ja käynnistää poisto-tapahtuman. `TabbedPane`-komponentti määrää, miten poistaminen käsitellään.

6. **Slot(`Component`)**: 
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`-komponentin kyvykkyyden parantamiseksi. Voit lisätä kuvakkeita, etikettejä, latauspyöröitä, tyhjennys/nollausmahdollisuuksia, avatar/kuva-profiileja ja muita hyödyllisiä komponentteja `Tab`-välilehteen selkeyttämään käyttäjille tarkoitettua merkitystä. 
    Voit lisätä komponentin `Tab`-välilehden `prefix`-slotille rakentamisen aikana. Vaihtoehtoisesti voit käyttää `setPrefixComponent()`- ja `setSuffixComponent()` -menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon `Tab`-välilehdessä.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumentit", TablerIcon.create("files")));
        ```

## `Tab`-manipulaatio {#tab-manipulation}

Eri menetelmät mahdollistavat kehittäjien lisätä, sijoittaa, poistaa ja muokata erilaisia `Tab`-elementtejä `TabbedPane`-komponentissa.

### Välilehden lisääminen {#adding-a-tab}

`addTab()`- ja `add()`-menetelmät ovat olemassa eri ylikuormitettavissa muodoissa, jotka antavat kehittäjille joustavuutta lisätä uusia välilehtiä `TabbedPane`-komponenttiin. Välilehden lisääminen sijoittaa sen kaikkien aikaisemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`-välilehden `TabbedPane`-komponenttiin, jossa on määritetty `String` välilehden tekstinä.
2. **`addTab(Tab tab)`** - Lisää annettu `Tab`-välilehti `TabbedPane`-komponenttiin.
3. **`addTab(String text, Component component)`** - Lisää `Tab`-välilehden, jossa on annettu `String` välilehden tekstinä, ja annettu `Component`, joka näytetään `TabbedPane`-komponentin sisältöosiossa.
4. **`addTab(Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää tarjotun `Component`-elementin `TabbedPane`-komponentin sisältöosiossa.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`-komponenttiin, luoden erillisen `Tab` jokaiselle, jonka teksti asetetaan komponentin nimeksi.

:::info
`add(Component... component)` määrää kyseisen `Component`-komponentin nimen kutsumalla `component.getName()` -menetelmää annettavasta argumentista.
:::

### Välilehden lisääminen tiettyyn kohtaan {#inserting-a-tab}

Välilehden lisäämisen lisäksi nykyisten välilehtien loppuun on myös mahdollista luoda uusi tiettyyn kohtaan. Tätä varten on olemassa useita ylikuormitettavia versioita `insertTab()`-menetelmästä.

1. **`insertTab(int index, String text)`** - Lisää `Tab`-välilehden `TabbedPane`-komponenttiin määritettyyn indeksiin, jossa on annettu `String` välilehden tekstinä.
2. **`insertTab(int index, Tab tab)`** - Lisää annettu `Tab`-välilehti `TabbedPane`-komponenttiin määritettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`-välilehden, jossa on annettu `String` välilehden tekstinä, ja annettu `Component`, joka näytetään `TabbedPane`-komponentin sisältöosiossa.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää tarjotun `Component`-elementin `TabbedPane`-komponentin sisältöosiossa.

### Välilehden poistaminen {#removing-a-tab}

Poistaaksesi yksittäisen `Tab`-välilehden `TabbedPane`-komponentista, käytä jotakin seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`-välilehden `TabbedPane`-komponentista, kun välilehden instanssi annetaan poistettavaksi.
2. **`removeTab(int index)`** - Poistaa `Tab`-välilehden `TabbedPane`-komponentista määrittämällä poistettavan `Tab`-välilehden indeksi.

Ylläolevien kahden menetelmän lisäksi voidaan käyttää **`removeAllTabs()`**-menetelmää tyhjentämään `TabbedPane`-komponentti kaikista välilehdistä.

:::info
`remove()`- ja `removeAll()`-menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Välilehden/komponentin yhdistäminen {#tabcomponent-association}

Jos haluat vaihtaa näytettävän komponentin tietylle `Tab`-välilehdelle, kutsu `setComponentFor()`-menetelmää ja anna joko `Tab`-instanssi tai sen indeksi `TabbedPane`-komponentissa.

:::info
Jos tätä menetelmää käytetään `Tab`-välilehdelle, joka on jo liitetty `Component`-elementtiin, aiemmin liitetty `Component`-elementti tuhotaan.
:::

## Kokoonpano ja asettelu {#configuration-and-layout}

`TabbedPane`-luokka koostuu kahdesta osasta: `Tab`, joka näytetään tietyssä sijainnissa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composite-components) -komponentti, mikä mahdollistaa monimutkaisempien komponenttien esittämisen välilehden sisältöosassa.

### Pyyhkiminen {#swiping}

`TabbedPane` tukee eri välilehtien selailua pyyhkimällä. Tämä on ihanteellinen mobiilisovellukselle, mutta se voidaan myös konfiguroida sisäänrakennetulla menetelmällä hiiren pyyhkimistä varten. Sekä pyyhkiminen että hiiren pyyhkiminen ovat oletusarvoisesti pois päältä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)`- ja `setSwipableWithMouse(boolean)`-menetelmillä.

### Välilehtien sijoittaminen {#tab-placement}

`Tabs`-välilehdet `TabbedPane`-komponentissa voidaan sijoittaa eri paikkoihin sovelluskehittäjän mieltymysten mukaan. Tarjotut vaihtoehdot asetetaan käytettävän enum-arvon avulla, jossa arvot ovat `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetus on `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Kohdistus {#alignment}

Välilehtien sijoittamisen lisäksi `TabbedPane`-komponentissa on myös mahdollista konfiguroida, miten välilehdet kohdistuvat komponenttiin. Oletuksena käyttö on `AUTO`, joka sallii välilehtien sijoittamisen määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Reuna- ja aktiivisuusindikaattori {#border-and-activity-indicator}

`TabbedPane`-komponentissa on oletusarvoisesti reuna näkyvissä, joka on sijoitettu sen mukaan, mikä `Placement` on asetettu. Tämä reuna auttaa visualisoimaan tilan, jonka eri välilehdet paneelissa vievät.

Kun `Tab`-välilehteä napsautetaan, oletusarvoisesti aktiivisuusindikaattori näkyy sen lähellä auttaakseen korostamaan, mikä on tällä hetkellä valittu `Tab`.

Molempia näitä vaihtoehtoja voidaan räätälöidä kehittäjän toimesta muuttamalla boolean-arvoja asianmukaisilla setterimenetelmillä. Vaihtaaakseen, näkyykö reuna vai ei, voidaan käyttää `setBorderless(boolean)`-menetelmää, jossa `true` piilottaa reunan, ja `false`, oletusarvoinen arvo, näyttää reunan.

:::info
Tämä reuna ei koske koko `TabbedPane` -komponenttia, vaan palvelee vain erottimena välilehtien ja komponentin sisällön välillä.
:::

Voit asettaa aktiivisen indikaattorin näkyvyyden käyttämällä `setHideActiveIndicator(boolean)`-menetelmää. Jos menetelmälle annetaan `true`, se piilottaa aktiivisen indikaattorin aktiivisen `Tab`-välilehden alla, kun taas `false`, oletusarvo, pitää indikaattorin näkyvissä.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivointitavat {#activation-modes}

Saat lisää tarkkuutta siihen, miten `TabbedPane` käyttäytyy navigoidessa näppäimistöllä, aktivointitila voidaan asettaa määrittämään, miten komponentin tulisi käyttäytyä.

- **`Auto`**: Kun asetetaan automaattiseksi, nuolinäppäimillä navigointi tuo heti näkyviin vastaavan välilehden komponentin.

- **`Manual`**: Kun asetetaan manuaaliseksi, välilehti saa fokuksen, mutta sitä ei näytetä ennen kuin käyttäjä painaa välilyöntiä tai enteriä.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-välilehdet voidaan asettaa suljettaviksi. Suljettavissa välilehdissä on sulkemispainike lisättynä välilehteen, mikä laukaisee sulkemistapahtuman, kun sitä napsautetaan. `TabbedPane` määrää, miten tätä käyttäytymistä käsitellään.

- **`Manual`**: Oletusarvoisesti poisto on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on käsiteltävä tämä tapahtuma haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti `AUTO` voidaan käyttää, joka laukaisee tapahtuman ja poistaa myös `Tab`-välilehden komponentista kehittäjän puolesta, poistaen tarpeen kehittäjän toteuttaa tätä käyttäytymistä manuaalisesti.

### Segmenttivalinta <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` voidaan esittää segmenttivalintana ottamalla käyttöön `segment`-ominaisuus asetuksella `setSegment(true)`. Tässä tilassa välilehdet esitetään liukuvalla pillerindikaattorilla, joka korostaa aktiivisen valinnan, tarjoten kompaktin vaihtoehdon perinteiselle välilehtiliittymälle.

<ComponentDemo 
path='/webforj/tabbedpanesegment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java'
height="250px"
/>

## Tyylit {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane`-komponentilla on vakiotilat sekä `Expanse` että `Theme`, jotka ovat samankaltaisia kuin muut webforJ-komponentit. Näitä voidaan käyttää nopeasti lisäämään tyylityksiä, jotka välittävät eri merkityksiä loppukäyttäjälle ilman, että komponenttia tulee tyylittää CSS:llä.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavia käytäntöjä suositellaan `TabbedPane`-komponentin käyttämiseen sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittämään liittyvää sisältöä
    >- Jokainen välilehti tulisi edustaa erillistä kategoriaa tai toimintoa sovelluksessasi
    >- Ryhmittele samanlaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitetut välilehdet**: Vältä käyttäjien ylikuormittamista liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointikuviot käyttökelpoisessa puhtaassa käyttöliittymässä

- **Selkeät merkinnät**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
    >- Tarjoa selkeät ja tiivistetyt merkinnät jokaiselle välilehdelle
    >- Merkinnät tulisi heijastaa sisältöä tai tarkoitusta, tehdäkseen niistä helppoja ymmärtää
    >- Hyödynnä kuvakkeita ja erottuvia värejä missä tarvitaan

- **Näppäimistön navigointi**: Käytä webforJ:n `TabbedPane`-näppäimistön navigointitukea, jotta vuorovaikutus `TabbedPane`-komponentin kanssa olisi saumatonta ja intuitiivista loppukäyttäjälle

- **Oletus-välilehti**: Jos oletus-välilehti ei ole sijoitettu `TabbedPane`-komponentin alkuun, harkitse tämän välilehden asettamista oletus-välilehdiksi tärkeälle tai usein käytetylle tiedolle.
