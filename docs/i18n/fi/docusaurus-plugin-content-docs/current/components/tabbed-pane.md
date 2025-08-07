---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: 4f2421ca62abb88a3edd750e7887d2db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

`TabbedPane`-luokka tarjoaa kompaktin ja järjestelmällisen tavan esittää sisältöä, joka on jaettu useisiin osioihin, joista jokainen on liitetty `Tab`-välilehteen. Käyttäjät voivat vaihtaa näiden osioiden välillä napsauttamalla vastaavia välilehtiä, jotka on usein merkitty tekstillä ja/tai kuvakkeilla. Tämä luokka yksinkertaistaa monivaiheisten käyttöliittymien luomista, joissa eri sisältö tai lomakkeet on oltava käytettävissä, mutta eivät samanaikaisesti näkyvissä.

## Käytöt {#usages}

`TabbedPane`-luokka antaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitain tyypillisiä skenaarioita, joissa voit hyödyntää `TabbedPane`-komponenttia sovelluksessasi:

1. **Asiakirjan tarkastaja**: Asiakirjan tarkastaja, jossa jokainen välilehti edustaa eri asiakirjaa tai tiedostoa. Käyttäjät voivat helposti vaihtaa avoimien asiakirjojen välillä tehokkaan moniajoon.

2. **Tietojen hallinta**: Hyödynnä `TabbedPane`-komponenttia organisoimaan tietojen hallintatehtäviä, kuten:
    > - Eri tietosarjoja, joita esitetään sovelluksessa
    > - Erilaisia käyttäjäprofiileja, jotka voidaan näyttää erillisissä välilehdissä
    > - Eri profiileja käyttäjien hallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa eri moduuleja tai osioita. Jokaista välilehteä voidaan käyttää tietyn moduulin toiminnallisuuden kapseloimiseen, mahdollistaen käyttäjien keskittymisen yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintaohjelmat voivat käyttää `TabbedPane`-komponenttia edustamaan erilaisia projekteja tai tehtäviä. Jokainen välilehti voi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, jossa on tarpeen suorittaa erilaisia ohjelmia, `TabbedPane` voisi:
    > - Toimia sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten `AppLayout`-mallissa
    > - Luoda yläosan, joka voi palvella samanlaista tarkoitusta tai edustaa alisovelluksia jo valitussa sovelluksessa.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymän elementtejä, jotka voidaan lisätä välilehtipaneeleihin organisointia ja eri sisältönäkymien välillä vaihtamista varten.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponenteina. Niitä on tarkoitus käyttää yhdessä välilehtipaneelien kanssa. Tämä luokka ei ole `Component` eikä sitä tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään niiden lisäämiseen `TabbedPane`-komponenttiin. Näillä ominaisuuksilla on gettereitä ja settereitä, jotka helpottavat mukauttamista `TabbedPane`-komponentissa.

1. **Key(`Object`)**: Edustaa `Tab`-välilehden ainutlaatuista tunnistetta.

2. **Text(`String`)**: Teksti, joka näytetään välilehden otsikkona `TabbedPane`-komponentissa. Tätä kutsutaan myös otsikoksi `getTitle()`- ja `setTitle(String title)`-menetelmien kautta.

3. **Tooltip(`String`)**: Välilehden kanssa liitetty työkaluvihje, joka näytetään, kun kursori leijuu välilehden päällä.

4. **Enabled(`boolean`)**: Edustaa, onko `Tab`-välilehti tällä hetkellä käytössä vai ei. Voidaan muuttaa `setEnabled(boolean enabled)`-menetelmällä.

5. **Closeable(`boolean`)**: Edustaa, voidaanko `Tab`-välilehti sulkea. Voidaan muuttaa `setCloseable(boolean enabled)`-menetelmällä. Tämä lisää sulku-painikkeen `Tab`-välilehdelle, jota käyttäjä voi napsauttaa, ja tämä laukaisee poistotapahtuman. `TabbedPane`-komponentti määrittää, miten poistamista käsitellään.

6. **Slot(`Component`)**: 
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`-välilehden kyvykkyyden parantamiseen. Voit sisällyttää kuvakkeita, etikettejä, latauspyöröjä, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuvia ja muita hyödyllisiä komponentteja `Tab`-välilehden sisään, jotta voit selkeyttää käyttäjille tarkoitetun merkityksen.
    Voit lisätä komponentin `prefix`-slotille `Tab`-välilehdelle rakentamisen yhteydessä. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon `Tab`-välilehdessä.

```java
TabbedPane pane = new TabbedPane();
pane.addTab(new Tab("Documents", TablerIcon.create("files")));
```

## `Tab`-manipulaatio {#tab-manipulation}

On olemassa erilaisia menetelmiä, jotka mahdollistavat kehittäjien lisätä, sijoittaa, poistaa ja manipuloida erilaisia `Tab`-elementtien ominaisuuksia `TabbedPane`-komponentissa.

### `Tab`-välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmät ovat saatavilla eri ylikuormitettuina muotoina, jotta kehittäjille tarjotaan joustavuutta uusien välilehtien lisäämiseen `TabbedPane`-komponenttiin. Uuden `Tab`-välilehden lisääminen sijoittaa sen kaikkien aikaisempien olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`-välilehden `TabbedPane`-komponenttiin määritellyn `String`-arvon avulla, joka toimii välilehden tekstinä.
2. **`addTab(Tab tab)`** - Lisää parametrina annetun `Tab`-välilehden `TabbedPane`-komponenttiin.
3. **`addTab(String text, Component component)`** - Lisää `Tab`-välilehden, jossa on annettu `String`-arvo välilehden tekstinä ja annettu `Component`, joka näkyy `TabbedPane`-komponentin sisällössä.
4. **`addTab(Tab tab, Component component)`** - Lisää annetun `Tab`-välilehden ja näyttää annetun `Component`-komponentin `TabbedPane`-komponentin sisällössä.
5. **`add(Component... component)`** - Lisää yksi tai useampia `Component`-instansseja `TabbedPane`-komponenttiin, luoden erillisen `Tab`-välilehden jokaiselle, jonka tekstiksi asetetaan `Component`in nimi.

:::info
`add(Component... component)` määrittää annettavan `Component`in nimen kutsumalla `component.getName()` annettavan argumentin kohdalla.
:::

### `Tab`-välilehden insertointi {#inserting-a-tab}

Lisäksi, että voit lisätä `Tab`-välilehden olemassa olevien välilehtien loppuun, voit myös luoda uuden tietyssä sijainnissa. Tätä varten on käytettävissä useita ylikuormituksen versioita `insertTab()`-menetelmälle.

1. **`insertTab(int index, String text)`** - Lisää `Tab`-välilehden `TabbedPane`-komponenttiin annettuun indeksiin, jossa on määritelty `String`-arvo välilehden tekstinä.
2. **`insertTab(int index, Tab tab)`** - Lisää parametrina annettu `Tab`-välilehti `TabbedPane`-komponenttiin määritettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`-välilehden, jossa on annettu `String`-arvo välilehden tekstinä, ja annettu `Component`, joka näkyy `TabbedPane`-komponentin sisällössä.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annettu `Tab`-välilehti ja näyttää annetun `Component`-komponentin `TabbedPane`-komponentin sisällössä.

### `Tab`-välilehden poistaminen {#removing-a-tab}

Poistaaksesi yhden `Tab`-välilehden `TabbedPane`-komponentista, käytä yhtä seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`-välilehden `TabbedPane`-komponentista, kun välilehtikuva annetaan.
2. **`removeTab(int index)`** - Poistaa `Tab`-välilehden `TabbedPane`-komponentista määrittämällä poistettavan välilehden indeksi.

Yhdessä edellä mainittujen kahden menetelmän kanssa yksittäisen `Tab`-välilehden poistamiseksi käytä **`removeAllTabs()`** -menetelmää poistaaksesi kaikki välilehdet `TabbedPane`-komponentista.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Tab/Component-yhdistäminen {#tabcomponent-association}

Vaihtaaksesi `Component`in, joka näytetään tietylle `Tab`-välilehdelle, kutsu `setComponentFor()`-menetelmää ja syötä joko `Tab`-välilehden instanssi tai kyseisen välilehden indeksi `TabbedPane`-komponentissa.

:::info
Jos tätä menetelmää käytetään `Tab`-välilehdelle, joka on jo liitetty `Component`-komponenttiin, aiemmin liitetty `Component` tuhotaan.
:::

## Konfiguraatio ja asettelu {#configuration-and-layout}

`TabbedPane`-luokka koostuu kahdesta osasta: `Tab`, joka näytetään määritetyssä sijainnissa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composite-components) -komponentti, joka mahdollistaa monimutkaisempien komponenttien esittämisen välilehden sisältöosassa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien välillä pyyhkäisemällä. Tämä on ihanteellista mobiilisovellukseen, mutta sitä voidaan myös konfiguroida sisäänrakennetun menetelmän avulla tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletuksena pois käytöstä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittaminen {#tab-placement}

`Tabs`-välilehdet `TabbedPane`-komponentissa voidaan sijoittaa erilaisiin paikkoihin kehittäjän mieltymysten mukaan. Tarjotut vaihtoehdot määritetään käytettävissä olevalla enumilla, jossa arvot ovat `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetuksena on `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Kohdistus {#alignment}

Lisäksi, että voit muuttaa `Tab`-välilehtien sijoitusmahdollisuuksia `TabbedPane`-komponentissa, on myös mahdollista konfiguroida, miten välilehdet kohdistuvat komponentissa. Oletusarvoisesti asetus on `AUTO`, mikä sallii välilehtien paikan määrätä niiden kohdistamisen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Reuna ja aktiivisuusindikaattori {#border-and-activity-indicator}

`TabbedPane`-komponentilla on oletuksena reuna, joka näytetään välilehdille sen sisällä, ja se on asetettu riippuen siitä, mikä `Placement` on määritetty. Tämä reuna auttaa visualisoimaan tilaa, jota eri välilehdet paneelissa käyttävät.

Kun `Tab`-välilehtiä napsautetaan, oletuksena aktiivisuusindikaattori näytetään lähellä tätä `Tab`-välilehteä, jotta voidaan korostaa, mikä on nykyinen valittu `Tab`.

Molempia vaihtoehtoja voidaan mukauttaa kehittäjän toimesta muuttamalla boolean-arvoja käyttämällä asianmukaisia setter-menetelmiä. Voit vaihtaa, näytetäänkö reuna vai ei, käyttämällä `setBorderless(boolean)`-menetelmää, jossa `true` piilottaa reunan ja `false`, oletusarvo, näyttää reunan.

:::info
Tämä reuna ei koske koko `TabbedPane`-komponenttia, vaan toimii vain erottimena välilehtien ja komponentin sisällön välillä.
:::

Asettaaksesi aktiivisen indikaattorin näkyvyyden voit käyttää menetelmää `setHideActiveIndicator(boolean)`. Tämän menetelmän kutsuminen `true`:lla piilottaa aktiivisen indikaattorin aktiivisen `Tab`-välilehden alla, kun taas `false`, oletusarvo, pitää indikaattorin näkyvissä.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivointitavat {#activation-modes}

Ihan loppumattoman tarkkaa hallintaa siitä, miten `TabbedPane` käyttäytyy näppäimistöllä navigoitaessa, voidaan säätää `Activation`-tilalla, joka voi määrittää, miten komponentin tulee käyttäytyä.

- **`Auto`**: Asetettaessa automaattisesti, navigointi välilehtien välillä nuolinäppäimillä näyttää heti vastaavan välilehden komponentin.

- **`Manual`**: Asetettaessa manuaalisesti, välilehti saa keskittymisen, mutta sitä ei näytetä, ennen kuin käyttäjä painaa välilyöntiä tai enteriä.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-välilehdet voidaan asettaa suljettaviksi. Suljettavat välilehdet saavat sulku-painikkeen lisäämisen, joka laukaisee sulkemistapahtuman napsautettaessa. `TabbedPane` määrittää, miten tämä käytös käsitellään.

- **`Manual`**: Oletuksena poisto on asetettu `MANUAL`:ksi, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjä voi itse käsitellä tapahtuman haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti voidaan käyttää `AUTO`:a, joka laukaisee tapahtuman ja poistaa myös `Tab`-välilehden komponentista kehittäjälle, jolloin kehittäjän ei tarvitse toteuttaa tätä käytöstä manuaalisesti.

## Tyylit {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane`-komponentilla on sisäänrakennetut `Expanse`- ja `Theme`-vaihtoehdot, jotka ovat samankaltaisia kuin muissa webforJ-komponenteissa. Näitä voidaan käyttää nopeasti lisättäessä tyylittelyä, joka viestii erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsee tyylitellä CSS:llä.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Parhaat käytännöt {#best-practices}

Seuraavat käytännöt ovat suositeltavia käytettäväksi `TabbedPane`-komponentin kanssa sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittelemään liittyvää sisältöä
    > - Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toiminnallisuutta sovelluksessasi
    > - Ryhmittele samanlaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitettu määrä välilehtiä**: Vältä käyttäjiä ylikuormittamista liian monilla välilehdillä. Harkitse hierarkisen rakenteen tai muiden navigaatiomallien käyttöä siellä, missä se on mahdollista, siistin käyttöliittymän saavuttamiseksi.

- **Selkeät etiketit**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
    > - Anna kullekin välilehdelle selkeät ja yksiselitteiset etiketit
    > - Etikettien tulisi heijastaa sisältöä tai tarkoitusta, jotta käyttäjille on helppo ymmärtää
    > - Hyödynnä kuvakkeita ja erottuvia värejä, missä sovellettavissa

- **Näppäimistön navigointi**: Käytä webforJ:n `TabbedPane`-näppäimistön navigointitukea käyttöliittymää helpottamaan ja intuitiivisempaa loppukäyttäjälle.

- **Oletusvälilehti**: Jos oletusvälilehti ei ole sijoitettu `TabbedPane`-komponentin alkuun, harkitse tämän välilehden asettamista oletukseksi keskeiselle tai yleisesti käytettävälle tiedolle.
