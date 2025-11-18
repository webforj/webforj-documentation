---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
_i18n_hash: ebf6bff550fd69aeb6ab8e4dfefd2323
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

`TabbedPane`-luokka tarjoaa tiiviin ja järjestelmällisen tavan näyttää sisältöä, joka on jaettu useisiin osiin, joista jokainen liittyy `Tab`:iin. Käyttäjät voivat vaihtaa näiden osien välillä napsauttamalla vastaavia välilehtiä, jotka useimmiten on merkitty tekstillä ja/tai kuvakkeilla. Tämä luokka yksinkertaistaa monimutkaisten käyttöliittymien luomista, joissa erilaiset sisällöt tai lomakkeet tarvitsevat olla saavutettavissa, mutta eivät samanaikaisesti näkyvissä.

## Käyttötarkoitukset {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille voimakkaan työkalun useiden välilehtien tai osien järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitain tyypillisiä tilanteita, joissa saatat käyttää `TabbedPane`:ia sovelluksessasi:

1. **Dokumentin katselu**: Dokumentin katselun toteuttaminen, jossa jokainen välilehti edustaa eri dokumenttia tai tiedostoa. Käyttäjät voivat helposti vaihtaa avoimien dokumenttien välillä tehokkaan moniajoa varten.

2. **Tietojen hallinta:** Hyödynnä `TabbedPane`:ia tietojen hallintatehtävien järjestämiseen, esimerkiksi:
    >- Eri tietojoukot, jotka on esitettävä sovelluksessa
    >- Eri käyttäjäprofiilit, jotka voidaan näyttää erillisissä välilehdissä
    >- Eri profiilit käyttäjähallintajärjestelmässä

3. **Moduulin valinta**: `TabbedPane` voi edustaa erilaisia moduuleja tai osia. Jokainen välilehti voi kapseloida tietyn moduulin toiminnallisuudet, mahdollistaen käyttäjien keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävänhallintasovellukset voivat käyttää `TabbedPane`:ia erilaisten projektien tai tehtävien esittämiseen. Jokainen välilehti voi vastata tiettyä projektia, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Ohjelman navigointi**: Sovelluksessa, joka tarvitsee suorittaa erilaisia ohjelmia, `TabbedPane` voisi:
    >- Toimia sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien suorittamisen yhdessä sovelluksessa, kuten näyttää [`AppLayout`](./app-layout.md) -mallissa
    >- Luoda yläpalkin, joka voi palvella samanlaista tarkoitusta tai edustaa alisovelluksia jo valitun sovelluksen sisällä.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymäelementtejä, jotka voidaan lisätä välilehtipaneeleihin erilaisten sisältönäkymien järjestämiseksi ja niiden välillä vaihtamiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponentteina. Niitä on tarkoitus käyttää yhdessä välilehtipaneelien kanssa. Tämä luokka ei ole `Component` ja sitä ei pitäisi käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään niiden lisäämiseksi `TabbedPane`:iin. Näillä ominaisuuksilla on getterit ja setterit, jotta mukauttaminen `TabbedPane`:issa olisi helpompaa.

1. **Avain(`Object`)**: Edustaa `Tab`:in yksilöllistä tunnistetta.

2. **Teksti(`String`)**: Teksti, joka näytetään `Tab`:in otsikkona `TabbedPane`:issa. Tätä kutsutaan myös otsikoksi `getTitle()` ja `setTitle(String title)` -menetelmien kautta.

3. **Vihje(`String`)**: Vihjeteksti, joka liittyy `Tab`:iin ja joka näytetään, kun kursori leijuu `Tab`:in päällä.

4. **Otuva(`boolean`)**: Edustaa, onko `Tab` tällä hetkellä käytössä vai ei. Voidaan muokata `setEnabled(boolean enabled)` -menetelmällä.

5. **Suljettava(`boolean`)**: Edustaa, voidaanko `Tab` sulkea. Voidaan muokata `setCloseable(boolean enabled)` -menetelmällä. Tämä lisää sulkemispainikkeen `Tab`:iin, jolle käyttäjä voi klikata, ja laukaisee poistumistapahtuman. `TabbedPane`-komponentti määrää, kuinka poisto käsitellään.

6. **Slot(`Component`)**: 
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`:n kykyjen parantamiseksi. Voit käyttää kuvakkeita, etikettejä, lataussymboleita, tyhjennys/nollausmahdollisuutta, avatar/profiilikuvia sekä muita hyödyllisiä komponentteja, jotka on upotettu `Tab`:iin, jotta käyttäjille voitaisiin selkeyttää aikomuksia.
    Voit lisätä komponentin `Tab`:in `prefix`-slotille rakentamisen yhteydessä. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` -menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen näytettävän vaihtoehdon `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab`-manipulointi {#tab-manipulation}

Eri menetelmiä on olemassa, jotka mahdollistavat kehittäjille `Tab`-elementtien lisäämisen, sijoittamisen, poistamisen ja erilaisten ominaisuuksien manipuloinnin `TabbedPane`:ssa.

### Välilehden lisääminen {#adding-a-tab}

`addTab()` ja `add()` -menetelmiä on useissa ylikuormituksissa, jotta kehittäjillä olisi joustavuutta lisätä uusia välilehtiä `TabbedPane`:iin. Välilehden lisääminen sijoittaa sen kaikkien aikaisemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`:in `TabbedPane`:iin annetulla `String`-tekstillä.
2. **`addTab(Tab tab)`** - Lisää annettu `Tab`-parametri `TabbedPane`:iin.
3. **`addTab(String text, Component component)`** - Lisää `Tab`, jonka annettu `String` on `Tab`:in teksti, ja annettu `Component` näytetään `TabbedPane`:n sisältöosassa.
4. **`addTab(Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää annetun `Component`:in `TabbedPane`:n sisältöosassa.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`:iin, luoden erillisen `Tab`:in jokaiselle, ja asetetaan tekstiksi `Component`:in nimi.

:::info
`add(Component... component)` määrittää siirretyn `Component`:in nimen kutsumalla `component.getName()` siirretylle argumentille.
:::

### Välilehden lisääminen tiettyyn kohtaan {#inserting-a-tab}

Lisäksi uuden välilehden luominen on mahdollista tietyssä sijainnissa olemassa olevien välilehtien lopussa. Tämän tekemiseen on useita ylikuormitusversioita `insertTab()`-menetelmästä. 

1. **`insertTab(int index, String text)`** - Lisää `Tab`:in `TabbedPane`:iin annetussa indeksissä, käyttäen annettua `String`-tekstiä `Tab`:in tekstinä.
2. **`insertTab(int index, Tab tab)`** - Lisää parametrina annettu `Tab` `TabbedPane`:tiin määritetyssä indeksissä.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`, jonka annettu `String` on `Tab`:in teksti, ja annettu `Component` näytetään `TabbedPane`:n sisältöosassa.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annettu `Tab` ja näyttää annetun `Component`:in `TabbedPane`:n sisältöosassa.

### Välilehden poistaminen {#removing-a-tab}

Poistaaksesi yhden `Tab`:in `TabbedPane`:sta, käytä jotakin seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:in `TabbedPane`:sta, kun annetaan poistettava Tab-instanssi.
2. **`removeTab(int index)`** - Poistaa `Tab`:in `TabbedPane`:sta määrittämällä poistettavan `Tab`:in indeksin.

Molempien edellä mainittujen menetelmien lisäksi voit käyttää **`removeAllTabs()`**-menetelmää poistaaksesi `TabbedPane`:sta kaikki välilehdet.

:::info
`remove()` ja `removeAll()` -menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Välilehden/Komponentin yhdistäminen {#tabcomponent-association}

Voit muuttaa näytettävän `Component`:in `Tab`:ille kutsumalla `setComponentFor()`-menetelmää ja välittämällä joko `Tab`:in instanssin tai kyseisen Tabin indeksin `TabbedPane`:ssä.

:::info
Jos tätä menetelmää käytetään `Tab`:lle, joka on jo yhdistetty `Component`:iin, aikaisemmin yhdistetty `Component` tuhoutuu.
:::

## Konfigurointi ja asettelu {#configuration-and-layout}

`TabbedPane`-luokalla on kaksi koostavaa osaa: `Tab`, joka näytetään määritetyssä paikassa, ja komponentti, joka näytetään. Tämä voi olla yksi komponentti tai [`Composite`](../building-ui/composite-components) -komponentti, joka mahdollistaa monimutkaisempien komponenttien näyttämisen välilehden sisältöosassa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigoimista eri välilehtien läpi pyyhkäisemällä. Tämä on ihanteellinen mobiilisovelluksessa, mutta sitä voidaan myös konfiguroida sisäänrakennetun menetelmän avulla hiiren pyyhkäisyä varten. Sekä pyyhkäisy että hiiren pyyhkäisy ovat oletusarvoisesti pois päältä, mutta ne voidaan ottaa käyttöön `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` -menetelmillä.

### Välilehtien sijoittelu {#tab-placement}

`TabbedPane`:n sisällä olevat `Tabs` voidaan sijoittaa eri paikoille komponentin sisällä sovelluksen kehittäjän mieltymysten mukaan. Annetut vaihtoehdot asetetaan käytettävissä olevan enum-erottimen avulla, jonka arvot ovat `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetuksena on `TOP`.

<ComponentDemo 
path='/webforj/tabbedpaneplacement?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java'
height="400px"
/>

### Kohdistus {#alignment}

Lisäksi, että `Tab`-elementtien sijoittamista voidaan muuttaa `TabbedPane`:ssa, on myös mahdollista konfiguroida, kuinka välilehdet kohdistuvat komponenttiin. Oletusarvoisesti asetus `AUTO` on käytössä, joka antaa välilehtien sijoituksen määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Kolme ensimmäistä kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo 
path='/webforj/tabbedpanealignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java'
height="250px"
/>

### Reunus ja aktiivisuustunnistin {#border-and-activity-indicator}

`TabbedPane`:ssa näytetään oletusarvoisesti reunus, joka on sijoitettu sen mukaan, mitä `Placement`-asetusta on käytetty. Tämä reunus auttaa visualisoimaan tilan, jonka erilaiset välilehdet paneelissa vievät.

Kun `Tab`:ia klikataan, oletusarvoisesti aktivointitunnistin näytetään lähellä sitä `Tab`:ia, jotta se merkitään nykyiseksi valituksi `Tab`:iksi.

Molempia näitä vaihtoehtoja voidaan kehittäjän muokata muuttamalla boolean-arvoja asianmukaisilla asetusmenetelmillä. Reunuksen näkyvyyden muuttamiseksi voidaan käyttää `setBorderless(boolean)` -menetelmää, jossa `true` piilottaa reunuksen ja `false`, oletusarvo, näyttää reunuksen.

:::info
Tämä reunus ei koske koko `TabbedPane`-komponenttia, ja se toimii vain erottimena välilehtien ja komponentin sisällön välillä.
:::

Aktiivisen tunnistimen näkyvyyden asettamiseksi voidaan käyttää `setHideActiveIndicator(boolean)` -menetelmää. Tämä menetelmä, jonka parametri `true` piilottaa aktiivisen tunnistimen aktiivisen `Tab`:in alla, kun taas `false`, oletusarvo, pitää tunnistimen näkyvissä.

<ComponentDemo 
path='/webforj/tabbedpaneborder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java'
height="300px"
/>

### Aktivointitavat {#activation-modes}

Antamaan tarkempaa hallintaa siitä, kuinka `TabbedPane` käyttäytyy, kun sitä navigoidaan näppäimistöllä, `Activation`-tilaa voidaan asettaa määrittelemään, kuinka komponentin tulisi käyttäytyä.

- **`Auto`**: Kun se on asetettu automaattiseksi, välilehtien navigointi nuolinäppäimillä näyttää välittömästi vastaavan välilehden komponentin.

- **`Manual`**: Kun se on asetettu manuaaliseksi, välilehti saa kohdistuksen, mutta ei näy ennen kuin käyttäjä painaa väli- tai enter-näppäintä.

<ComponentDemo 
path='/webforj/tabbedpaneactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java'
height="250px"
/>

### Poistamisvaihtoehdot {#removal-options}

Yksittäiset `Tab`-elementit voidaan asettaa suljettaviksi. Suljettavilla välilehdillä on sulkemispainike lisättynä, joka laukaisee sulkeutumis tapahtuman napsautettaessa. `TabbedPane` määrää, kuinka tämä käyttäytyminen käsitellään.

- **`Manual`**: Oletusarvoisesti poistaminen on asetettu `MANUAL`, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on käsiteltävä tätä tapahtumaa haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti `AUTO` voidaan käyttää, mikä laukaisee tapahtuman ja poistaa myös `Tab`:in komponentista kehittäjän puolesta, jolloin kehittäjän ei tarvitse toteuttaa tätä käyttäytymistä manuaalisesti.

## Tyylittely {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane` tulee sisäänrakennettujen `Expanse`- ja `Theme`-vaihtoehtojen kanssa, jotka ovat samanlaisia kuin muissa webforJ-komponenteissa. Näitä voidaan käyttää nopeasti tyylittelemään siten, että se viestii erilaisia merkityksiä loppukäyttäjälle ilman, että komponenttia tarvitsisi tyylittää CSS:llä.

<ComponentDemo 
path='/webforj/tabbedpaneexpansetheme?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java'
height="250px"
/>

<TableBuilder name="TabbedPane" />

## Parhaat käytännöt {#best-practices}

Seuraavia käytäntöjä suositellaan `TabbedPane`:n käyttämiseen sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogiseen liittyvän sisällön ryhmittelyyn
    >- Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toimintoa sovelluksessasi
    >- Ryhmittele samanlaiset tai loogiset välilehdet lähelle toisiaan

- **Rajoitettu määrä välilehtiä**: Vältä käyttäjien ylivoimaamista liian monilla välilehdillä. Harkitse hierarkkisen rakenteen tai muiden navigointimallien käyttöä tarvittaessa siistin käyttöliittymän saavuttamiseksi.

- **Selkeät etiketit**: Ilmoita välilehtesi selkeästi intuitiivista käyttöä varten
    >- Anna selkeät ja ytimekkäät etiketit jokaiselle välilehdelle
    >- Etiketit tulisi heijastaa sisältöä tai tarkoitusta, jolloin käyttäjien on helppo ymmärtää
    >- Käytä kuvakkeita ja erottuvia värejä mahdollisuuksien mukaan

- **Näppäimistön navigointi**: Hyödynnä webforJ:n `TabbedPane`-näppäimistön navigointitukea tehdäksesi vuorovaikutuksesta `TabbedPane`:n kanssa sujuvampaa ja intuitiivisempaa loppukäyttäjälle.

- **Oletusvälilehti**: Jos oletusvälilehti ei ole sijoitettu `TabbedPane`:n alkupäähän, harkitse tämän välilehden asettamista oletukseksi tärkeää tai usein käytettävää tietoa varten.
