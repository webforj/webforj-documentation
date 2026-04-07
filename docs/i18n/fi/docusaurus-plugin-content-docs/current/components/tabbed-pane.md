---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: f903eeae452aae41b3eb04c170b9e98e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useita sisältöosuuksia voidaan järjestää yhden `TabbedPane` alaisuuteen, jossa kukin osio on sidottu napsautettaviin `Tab`:eihin. Vain yksi osio on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, kuvakkeita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käytännöt {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitain tyypillisiä skenaarioita, joissa voisit käyttää `TabbedPane`-elementtiä sovelluksessasi:

1. **Dokumentin katselija**: Toteuta dokumentin katselija, jossa jokainen välilehti edustaa eri dokumenttia tai tiedostoa. Käyttäjät voivat helposti vaihtaa avoimien asiakirjojen välillä tehokkaan moniajoon.

2. **Tietojen hallinta**: Käytä `TabbedPane`-elementtiä tietojen hallintatehtävien järjestämiseen, esimerkiksi:
   >- Eri tietojoukkoja voidaan näyttää sovelluksessa
   >- Erilaisia käyttäjäprofiileja voidaan näyttää erillisissä välilehdissä
   >- Eri profiileja käyttäjähallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osioita. Kukin välilehti voi sisällyttää tietyn moduulin toiminnallisuuksia, mikä mahdollistaa käyttäjien keskittymisen yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävien hallintaohjelmat voivat käyttää `TabbedPane`-elementtiä eri projektien tai tehtävien esittämiseen. Kukin välilehti voi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Sovelluksen navigointi**: Sovelluksessa, joka tarvitsee suorittaa erilaisia ohjelmia, `TabbedPane` voisi:
   >- Toimia sivupalkkina, joka mahdollistaa erilaisten sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten [`AppLayout`](./app-layout.md) -mallissa on esitetty
   >- Luoda yläpalkin, joka voi palvella samanlaista tarkoitusta tai edustaa alisovelluksia valitun sovelluksen sisällä.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymäelementtejä, joita voidaan lisätä tabulointipaneeleihin eri sisältönäkymien järjestämiseen ja niiden välillä vaihtamiseen.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponentteina. Ne on tarkoitettu käytettäväksi yhdessä tabulointipaneelien kanssa. Tämä luokka ei ole `Component` ja sitä ei tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään lisättäessä niitä `TabbedPane`:en. Näillä ominaisuuksilla on getterit ja setterit, jotka helpottavat mukauttamista `TabbedPane`:ssa.

1. **Avain(`Object`)**: Edustaa `Tab`:in ainutlaatuista tunnistetta.

2. **Teksti(`String`)**: Teksti, joka näytetään `Tab`:in otsikkona `TabbedPane`:ssa. Tämä tunnetaan myös nimellä otsikko `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

3. **Työkaluvihje(`String`)**: Työkaluvihje, joka liittyy `Tab`:iin, ja joka näytetään, kun kursori leijuu `Tab`:in päällä.

4. **Aktivoitu(`boolean`)**: Edustaa sitä, onko `Tab` tällä hetkellä aktivoitu vai ei. Sitä voidaan muokata `setEnabled(boolean enabled)` -menetelmällä.

5. **Suljettava(`boolean`)**: Edustaa, voidaanko `Tab` sulkea. Sitä voidaan muokata `setCloseable(boolean enabled)` -menetelmällä. Tämä lisää sulkemispainikkeen `Tab`-elementtiin, jota käyttäjä voi napsauttaa, ja laukaisee poistotapahtuman. `TabbedPane`-komponentti määrää, kuinka poisto käsitellään.

6. **Slot(`Component`)**: 
   Slotit tarjoavat joustavia vaihtoehtoja `Tab`-elementin käyttökohteiden parantamiseksi. Voit lisätä kuvakkeita, etikettejä, lataussymboleita, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuvaelementtejä ja muita hyödyllisiä komponentteja `Tab`:in sisään selventämään käyttäjille tarkoitetun merkityksen.
   Voit lisätä komponentin `prefix`-slotille `Tab`:ia rakennettaessa. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä lisätäksesi erilaisia komponentteja näytettävän vaihtoehdon ennen ja jälkeen `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Dokumentit", TablerIcon.create("files")));
        ```

## `Tab`-manipulointi {#tab-manipulation}

Eri menetelmät ovat olemassa kehittäjille lisätä, liittää, poistaa ja manipuloida `Tab`-elementtien erilaisia ominaisuuksia `TabbedPane`:ssa.

### Välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmät ovat saatavilla eri ylikuormitettuina muotoina, jotta kehittäjät voivat lisätä uusia välilehtiä `TabbedPane`:en joustavasti. Välilehden lisääminen sijoittaa sen kaikkien aiemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab` `TabbedPane`:en määritellyllä `String`-ohjeella `Tab`:in tekstinä.
2. **`addTab(Tab tab)`** - Lisää parametrina tarjotun `Tab`:in `TabbedPane`:en.
3. **`addTab(String text, Component component)`** - Lisää `Tab` määritellyllä `String`-ohjeella `Tab`:in tekstinä, ja tarjotun `Component`:in näytettäväksi sisältöosassa `TabbedPane`:ssa.
4. **`addTab(Tab tab, Component component)`** - Lisää tarjotun `Tab`:in ja näyttää tarjotun `Component`:in sisältöosassa `TabbedPane`:ssa.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`:en, luoden erillisen `Tab` jokaiselle, jolloin tekstinä on komponentin nimi.

:::info
`add(Component... component)` määrittää syötetyn `Component`:in nimen kutsumalla `component.getName()` syötetystä argumentista.
:::

### Välilehden liittäminen {#inserting-a-tab}

Lisäksi, että `Tab` voidaan lisätä olemassa olevien välilehtien loppuun, voidaan myös luoda uusi määrätyssä sijainnissa. Tätä varten on useita ylikuormitettuja versioita `insertTab()`-menetelmästä.

1. **`insertTab(int index, String text)`** - Liittää `Tab` tiettyyn indeksiin `TabbedPane`:ssa määritellyllä `String`-ohjeella `Tab`:in tekstinä.
2. **`insertTab(int index, Tab tab)`** - Liittää parametrina tarjotun `Tab`:in `TabbedPane`:en määritettyyn indeksiin.
3. **`insertTab(int index, String text, Component component)`** - Liittää `Tab` määritellyllä `String`-ohjeella `Tab`:in tekstinä, ja tarjotun `Component`:in näyttämiseksi sisältöosassa `TabbedPane`:ssa.
4. **`insertTab(int index, Tab tab, Component component)`** - Liittää tarjotun `Tab`:in ja näyttää tarjotun `Component`:in sisältöosassa `TabbedPane`:ssa.

### Välilehden poistaminen {#removing-a-tab}

Poistaaksesi yhden `Tab`:in `TabbedPane`:sta, käytä yhtä seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:in `TabbedPane`:sta antamalla poistettavan Tab-instanssin.
2. **`removeTab(int index)`** - Poistaa `Tab`:in `TabbedPane`:sta määrittämällä poistettavan `Tab`:in indeksi.

Yksittäisen `Tab`:in poiston lisäksi voit käyttää **`removeAllTabs()`**-menetelmää tyhjentääksesi `TabbedPane`:n kaikista välilehdistä.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Välilehti/Komponentti -yhdistäminen {#tabcomponent-association}

Voit muuttaa `Component`:in, joka näytetään tietylle `Tab`:ille, kutsumalla `setComponentFor()`-menetelmää ja antamalla joko `Tab`:in instanssin tai kyseisen välilehden indeksin `TabbedPane`:ssa.

:::info
Jos tätä menetelmää käytetään `Tab`:illa, joka on jo yhdistetty `Component`:iin, aikaisemmin yhdistetty `Component` tuhotaan.
:::

## Määritys ja asettelu {#configuration-and-layout}

`TabbedPane`-luokalla on kaksi osaa: `Tab`, joka näkyy määrätyssä sijainnissa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composite-components) -komponentti, joka mahdollistaa monimutkaisempien komponenttien näyttämisen välilehden sisältöosassa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien välillä pyyhkäisemällä. Tämä on ihanteellinen mobiilisovellukselle, mutta voidaan myös määrittää sisäänrakennetun menetelmän avulla tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletuksena pois käytöstä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittaminen {#tab-placement}

`Tabs`-elementit `TabbedPane`:ssa voidaan sijoittaa eri paikkoihin komponentin sisällä sovelluskehittäjän mieltymysten mukaan. Tarjotut vaihtoehdot asetetaan antamalla määritetty enum, jossa on arvot `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetus on `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Kohdistus {#alignment}

Lisäksi `Tab`-elementtien sijoittamisen muuttamisen lisäksi `TabbedPane`:ssa on myös mahdollista määrittää, kuinka välilehdet kohdistuvat komponentin sisällä. Oletuksena asetuksena on `AUTO`, mikä mahdollistaa välilehtien sijoituksen määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Reunus ja aktiviteetti-indikaattori {#border-and-activity-indicator}

`TabbedPane`:ssa on oletuksena reunus näkyvissä välilehdille, joka sijoittuu asetetun `Placement`:n mukaan. Tämä reunus auttaa visualisoimaan tilan, jonka erilaiset välilehdet paneelissa vievät.

Kun `Tab`:ia napsautetaan, oletuksena aktiviteetti-indikaattori näkyy lähellä tätä `Tab`:ia korostaen sitä, mikä on tällä hetkellä valittu `Tab`.

Molemmat vaihtoehdot voidaan mukauttaa kehittäjän toimesta muuttamalla boolean-arvoja käyttäen oikeita setter-menetelmiä. Muuttaaksesi sitä, näytetäänkö reunus vai ei, voit käyttää `setBorderless(boolean)` -menetelmää, jossa `true` piilottaa reunuksen ja `false`, oletusarvo, näyttää reunuksen.

:::info
Tämä reunus ei koske `TabbedPane`-komponentin kokonaisuutta, vaan palvelee vain erotusmerkkinä välilehtien ja komponentin sisällön välillä.
:::

Voit säätää aktiivisen indikaattorin näkyvyyttä käyttämällä `setHideActiveIndicator(boolean)` -menetelmää. Antamalla `true` tälle menetelmälle piilotat aktiivisen indikaattorin aktiivisen `Tab`:in alla, kun taas `false`, oletusarvo, pitää indikaattorin näkyvissä.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivointitavat {#activation-modes}

Tarkempaa hallintaa siitä, miten `TabbedPane` käyttäytyy näppäimistön navigoinnissa, varten `Activation`-tila voidaan asettaa määrittämään, kuinka komponentin tulisi toimia.

- **`Auto`**: Kun se on asetettu automaattiseksi, nuolinäppäimillä navigointi välilehtien välillä näyttää heti vastaavan välilehden komponentin.

- **`Manual`**: Kun se on asetettu manuaalisesti, välilehti saa fokuksen, mutta ei näy ennen kuin käyttäjä painaa väli- tai enter-näppäintä.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavissa välilehdissä on sulkemispainike, joka laukaisee sulkemistapahtuman, kun sitä napsautetaan. `TabbedPane` määrää, kuinka tämä käyttäytyminen käsitellään.

- **`Manual`**: Oletuksena poistaminen asetetaan `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on käsiteltävä tämä tapahtuma haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti voidaan käyttää `AUTO`, joka laukaisee tapahtuman ja poistaa myös `Tab`:in komponentista kehittäjän puolesta, jolloin kehittäjä ei tarvitse toteuttaa tätä käyttäytymistä manuaalisesti.

## Tyylitys {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane`-komponentilla on sisäänrakennettuja `Expanse`- ja `Theme`-vaihtoehtoja kuten muissakin webforJ-komponenteissa. Näitä voidaan käyttää nopeasti lisäämään tyylitietonsa, jotka välittävät erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia täytyy tyylitellä CSS:llä.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavat käytännöt ovat suositeltavia `TabbedPane`:n hyödyntämisessä sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittämään aiheeseen liittyvää sisältöä
   >- Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toimintoa sovelluksessasi
   >- Ryhmittele samankaltaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitettu määrä välilehtiä**: Vältä ylikuormittamasta käyttäjiä liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointimallien käyttöä, kun se on soveltuvaa, puhtaan käyttöliittymän saavuttamiseksi

- **Selkeät etiketit**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
   >- Tarjoa selkeitä ja ytimekkäitä etikettilappuja jokaiselle välilehdelle
   >- Etikettilappujen tulisi heijastaa sisältöä tai tarkoitusta, jolloin käyttäjien on helppoa ymmärtää
   >- Hyödynnä kuvakkeita ja erottuvia värejä, kun se on soveltuvaa

- **Näppäimistön navigointi**: Käytä webforJ:n `TabbedPane`-näppäimistön navigointitukea tehdäksesi vuorovaikutuksesta `TabbedPane`:n kanssa sujuvampaa ja intuitiivisempaa loppukäyttäjälle

- **Oletusvälilehti**: Jos oletusvälilehti ei ole sijoitettu `TabbedPane`:n alkuun, harkitse tämän välilehden asettamista oletukseksi olennaiselle tai usein käytetyille tiedoille.
