---
title: TabbedPane
slug: tabbedpane
sidebar_position: 125
description: >-
  Organize content into switchable Tab sections with the TabbedPane component,
  supporting icons, keys, and customizable tab properties.
_i18n_hash: 563f9251b928e2e9426d69d4b5192880
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-tabbed-pane" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/tabbedpane/TabbedPane" top='true'/>

Useamman sisällön osion voi organisoida yhden `TabbedPane` alle, missä jokainen osio liittyy klikkattavaan `Tab`:iin. Vain yksi osio on näkyvissä kerrallaan, ja välilehdet voivat näyttää tekstiä, kuvakkeita tai molempia auttaakseen käyttäjiä navigoimaan niiden välillä.

<!-- INTRO_END -->

## Käyttöesimerkit {#usages}

`TabbedPane`-luokka tarjoaa kehittäjille tehokkaan työkalun useiden välilehtien tai osioiden järjestämiseen ja esittämiseen käyttöliittymässä. Tässä on joitain tyypillisiä skenaarioita, joissa saatat hyödyntää `TabbedPane`:a sovelluksessasi:

1. **Asiakirjan katselu**: Asiakirjakatselun toteuttaminen, jossa jokainen välilehti edustaa eri asiakirjaa tai tiedostoa. Käyttäjät voivat helposti siirtyä avoimien asiakirjojen välillä tehokasta moniajokykyä varten.

2. **Datan hallinta:** Hyödynnä `TabbedPane`-elementtiä järjestääksesi datan hallintatehtäviä, kuten:
    >- Eri datasetit, jotka näytetään sovelluksessa
    >- Erilaisia käyttäjäprofiileja voidaan näyttää erillisissä välilehdissä
    >- Eri profiilat käyttäjähallintajärjestelmässä

3. **Moduulivalinta**: `TabbedPane` voi edustaa eri moduuleja tai osioita. Jokainen välilehti voi kapseloida tietyn moduulin toiminnallisuudet, jolloin käyttäjät voivat keskittyä yhteen sovelluksen osa-alueeseen kerrallaan.

4. **Tehtävien hallinta**: Tehtävienhallintasovellukset voivat käyttää `TabbedPane`-elementtiä edustamaan erilaisia projekteja tai tehtäviä. Jokainen välilehti voisi vastata tietyille projektille, jolloin käyttäjät voivat hallita ja seurata tehtäviä erikseen.

5. **Sovelluksen navigointi**: Sovelluksessa, joka tarvitsee ajaa erilaisia ohjelmia, `TabbedPane` voisi:
    >- Toimia sivupalkkina, joka mahdollistaa eri sovellusten tai ohjelmien käynnistämisen yhdessä sovelluksessa, kuten mitä esitetään [`AppLayout`](./app-layout.md) mallissa
    >- Luoda yläpalkin, joka voi palvella samankaltaista tarkoitusta tai edustaa ali-sovelluksia jo valitussa sovelluksessa.

## Välilehdet {#tabs}

Välilehdet ovat käyttöliittymäelementtejä, jotka voidaan lisätä välilehtipaneeleihin erilaisten sisältönäyttöjen järjestämiseksi ja vaihtamiseksi.

:::important
Välilehtiä ei ole tarkoitettu käytettäväksi itsenäisinä komponentteina. Niitä tulee käyttää yhdessä välilehtipaneelien kanssa. Tämä luokka ei ole `Component` eikä sitä tule käyttää sellaisena.
:::

### Ominaisuudet {#properties}

Välilehdet koostuvat seuraavista ominaisuuksista, joita käytetään niiden lisäämiseen `TabbedPane`-elementtiin. Näillä ominaisuuksilla on hakijat ja asettajat, jotka helpottavat mukauttamista `TabbedPane`-elementissä.

1. **Avain(`Object`)**: Edustaa välilehden ainutlaatuista tunnistetta.

2. **Teksti(`String`)**: Teksti, joka näkyy välilehden otsikkona `TabbedPane`-elementissä. Tätä kutsutaan myös nimellä otsikko `getTitle()` ja `setTitle(String title)` menetelmien kautta.

3. **Työkaluvihje(`String`)**: Työkaluvihjeteksti, joka liittyy välilehteen ja näkyy, kun kursori liikkuu välilehden päälle.

4. **Otetaan käyttöön(`boolean`)**: Edustaa, onko välilehti tällä hetkellä käytössä vai ei. Voidaan muuttaa `setEnabled(boolean enabled)` menetelmällä.

5. **Suljettava(`boolean`)**: Edustaa, voiko välilehden sulkea. Voidaan muuttaa `setCloseable(boolean enabled)` menetelmällä. Tämä lisää sulkupainikkeen välilehteen, jota käyttäjä voi klikata, ja laukaisee poistotapahtuman. `TabbedPane`-komponentti määrää, kuinka poistaminen käsitellään.

6. **Slot(`Component`)**: 
    Slotit tarjoavat joustavia vaihtoehtoja `Tab`:in kyvykkyyden parantamiseksi. Voit lisätä kuvakkeita, etikettejä, lataussiruja, tyhjennys/nollausmahdollisuuksia, avatar/profiilikuvaa ja muita hyödyllisiä komponentteja sisäkkäin `Tab`:issa käyttäjien tarkoitetun merkityksen selvittämiseksi.
    Voit lisätä komponentin `prefix`-paikkaan `Tab`:in rakentamisen yhteydessä. Vaihtoehtoisesti voit käyttää `setPrefixComponent()` ja `setSuffixComponent()` menetelmiä lisätäksesi erilaisia komponentteja ennen ja jälkeen näkyvän vaihtoehdon `Tab`:issa.

        ```java
        TabbedPane pane = new TabbedPane();
        pane.addTab(new Tab("Documents", TablerIcon.create("files")));
        ```

## `Tab` manipulointi {#tab-manipulation}

Eri menetelmiä on olemassa, jotta kehittäjät voivat lisätä, lisätä, poistaa ja manipuloida eri ominaisuuksia `Tab`-elementeistä `TabbedPane`-elementissä.

### `Tab`:n lisääminen {#adding-a-tab}

`addTab()` ja `add()` menetelmät ovat olemassa eri ylikuormitettuna kapasiteettina antaakseen kehittäjille joustavuutta lisätä uusia välilehtiä `TabbedPane`:en. Välilehden lisääminen sijoittaa sen aiemmin olemassa olevien välilehtien jälkeen.

1. **`addTab(String text)`** - Lisää `Tab`:in `TabbedPane`:en määritellyllä `String`:lla, joka on puolestaan välilehden teksti.
2. **`addTab(Tab tab)`** - Lisää annettu `Tab` parametrina `TabbedPane`:en.
3. **`addTab(String text, Component component)`** - Lisää `Tab`:in, jossa annettu `String` on välilehden teksti, ja annettu `Component` näytetään `TabbedPane`-elementin sisällössä.
4. **`addTab(Tab tab, Component component)`** - Lisää annettu `Tab`, ja esittää annetun `Component`-elementin `TabbedPane`-elementin sisällössä.
5. **`add(Component... component)`** - Lisää yksi tai useampi `Component`-instanssi `TabbedPane`:en, luoden erillisen `Tab`:in jokaiselle, ja tekstin asettaminen `Component`-nimen mukaan.

:::info
`add(Component... component)` määrittää siirretyn `Component`-nimen kutsumalla `component.getName()` siirretylle argumentille.
:::

### `Tab`:in lisääminen tiettyyn kohtaan {#inserting-a-tab}

Välilehden lisäämisen lisäksi olemassa olevien välilehtien päähän on myös mahdollista luoda uusi tiettyyn kohtaan. Tätä varten on saatavilla useita ylikuormitettuja versioita `insertTab()`-menetelmälle.

1. **`insertTab(int index, String text)`** - Lisää `Tab`:in `TabbedPane`:en annetussa indeksissä määritellyllä `String`:lla.
2. **`insertTab(int index, Tab tab)`** - Lisää annettu `Tab` parametrina `TabbedPane`:en määritellyssä indeksissä.
3. **`insertTab(int index, String text, Component component)`** - Lisää `Tab`:in, jossa annettu `String` on välilehden teksti, ja annettu `Component` näytetään `TabbedPane`-elementin sisällössä.
4. **`insertTab(int index, Tab tab, Component component)`** - Lisää annettu `Tab` ja esittää annetun `Component`-elementin `TabbedPane`-elementin sisällössä.

### `Tab`:in poistaminen {#removing-a-tab}

Poistaaksesi yhden `Tab`:in `TabbedPane`:sta, käytä jotakin seuraavista menetelmistä:

1. **`removeTab(Tab tab)`** - Poistaa `Tab`:in `TabbedPane`:sta välilehti-instanin välityksellä.
2. **`removeTab(int index)`** - Poistaa `Tab`:in `TabbedPane`:sta määrittelemällä poistettavan välilehden indeksin.

Yksittäisen `Tab`:in poistamiseksi edellä mainittujen kahden menetelmän lisäksi käytä **`removeAllTabs()`**-menetelmää tyhjentääksesi `TabbedPane`:n kaikista välilehdistä.

:::info
`remove()` ja `removeAll()` menetelmät eivät poista välilehtiä komponentin sisällä.
:::

### Tab/Component-yhdistämisen hallinta {#tabcomponent-association}

Muuta näytettävä `Component` tietylle `Tab`:ille kutsumalla `setComponentFor()`-menetelmää ja antaen joko `Tab`-instanssin tai kyseisen Tabin indeksin `TabbedPane`:ssa.

:::info
Jos tätä menetelmää käytetään `Tab`:illa, joka on jo yhdistetty `Component`:iin, aiemmin yhdistetty `Component` tuhotaan.
:::

## Asetus ja asettelu {#configuration-and-layout}

`TabbedPane`-luokalla on kaksi osaa: `Tab`, joka näytetään määritellyssä sijainnissa, ja näytettävä komponentti. Tämä voi olla yksi komponentti tai [`Composite`](/docs/building-ui/composing-components) komponentti, joka mahdollistaa monimutkaisempien komponenttien näyttämisen välilehden sisältöosiossa.

### Pyyhkäisy {#swiping}

`TabbedPane` tukee navigointia eri välilehtien välillä pyyhkäisemällä. Tämä on ihanteellinen mobiilisovelluksessa, mutta se voidaan myös konfiguroida sisäänrakennetun menetelmän kautta tukemaan hiiren pyyhkäisyä. Sekä pyyhkäisy että hiiren pyyhkäisy ovat poissa käytöstä oletuksena, mutta ne voidaan aktivoida `setSwipable(boolean)` ja `setSwipableWithMouse(boolean)` menetelmillä, vastaavasti.

### Välilehtien sijoittelu {#tab-placement}

`Tabs`-elementit `TabbedPane`:ssa voidaan sijoittaa eri sijainteihin komponentin sisällä sovelluskehittäjän mieltymyksen mukaan. Tarjotut vaihtoehdot asetetaan annetun enum-arvon avulla, joka sisältää arvot `TOP`, `BOTTOM`, `LEFT`, `RIGHT` tai `HIDDEN`. Oletusasetuksena on `TOP`.

<ComponentDemo
path='/webforj/tabbedpaneplacement'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPanePlacementView.java']}
height='400px'
/>

### Kohdistus {#alignment}

Väliavien muuttamisen lisäksi on myös mahdollista konfiguroida se, kuinka välilehdet kohdistuvat komponentin sisällä `TabbedPane`:ssa. Oletuksena `AUTO`-asetus on käytössä, mikä antaa välilehtien sijoituksen määrätä niiden kohdistuksen.

Muut vaihtoehdot ovat `START`, `END`, `CENTER` ja `STRETCH`. Ensimmäiset kolme kuvaavat sijaintia suhteessa komponenttiin, kun taas `STRETCH` saa välilehdet täyttämään käytettävissä olevan tilan.

<ComponentDemo
path='/webforj/tabbedpanealignment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneAlignmentView.java']}
height='250px'
/>

### Raja ja aktiviteetti-indikaattori {#border-and-activity-indicator}

`TabbedPane`:lla on oletuksena rajaus, joka näytetään sen sisällä oleville välilehdille, sijoitettuna sen mukaan, mikä `Placement` on asetettu. Tämä raja auttaa visualisoimaan tilan, jonka eri välilehdet paneelissa vievät.

Kun `Tab`:ia klikataan, oletuksena aktiviteetti-indikaattori näkyy kyseisen `Tab`:in lähellä, mikä auttaa korostamaan, mikä on tällä hetkellä käytetty `Tab`.

Molempia näitä vaihtoehtoja voidaan muokata kehittäjän toimesta muuttamalla totuusarvoja käyttäen asianmukaisia asettamismenetelmiä. Jos haluat muuttaa, näytetäänkö raja vai ei, voit käyttää `setBorderless(boolean)` -menetelmää, jolloin `true` piilottaa rajan ja `false`, oletusasetuksena, näyttää rajauksen.

:::info
Tämä raja ei vaikuta koko `TabbedPane`-komponenttiin, vaan toimii vain erottimena välilehden ja komponentin sisällön välillä.
:::

Aktiviteetti-indikaattorin näkyvyyden asettamiseksi voit käyttää `setHideActiveIndicator(boolean)` -menetelmää. Täsmällisesti, jos kutsut `true` tätä menetelmää, se piilottaa aktiivisen indikaattorin aktiivisen `Tab`:in alla, kun taas `false`, oletusasetuksena, pitää indikaattorin näkyvissä.

<ComponentDemo
path='/webforj/tabbedpaneborder'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneBorderView.java']}
height='300px'
/>

### Aktivointitilat {#activation-modes}

Kehittäjät voivat saada enemmän tarkkuutta siihen, kuinka `TabbedPane` käyttäytyy, kun sitä navigoidaan näppäimistöllä, aktivointitila voidaan asettaa määrittämään, kuinka komponentin pitäisi käyttäytyä.

- **`Auto`**: Kun asetetaan automaattiseksi, välilehteä navigoitaessa nuoli-näppäimillä vastaava välilehti komponentti näkyy heti.

- **`Manual`**: Kun asetetaan manuaaliseksi, välilehti saa fokuksen, mutta ei näy ennen kuin käyttäjä painaa välilyöntiä tai enteriä.

<ComponentDemo
path='/webforj/tabbedpaneactivation'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneActivationView.java']}
height='250px'
/>

### Poisto-ominaisuudet {#removal-options}

Yksittäiset `Tab` elementit voidaan asettaa suljettaviksi. Suljettavat välilehdet saavat sulkupainikkeen lisättäväksi välilehdelle, joka laukaisee sulkeutumis tapahtuman, kun sitä klikataan. `TabbedPane` määrää, kuinka tämä toiminta käsitellään.

- **`Manual`**: Oletuksena poisto on asetettu `MANUAL`-tilaan, mikä tarkoittaa, että tapahtuma laukaistaan, mutta kehittäjän on käsiteltävä tämä tapahtuma haluamallaan tavalla.

- **`Auto`**: Vaihtoehtoisesti `AUTO` voidaan käyttää, mikä laukaisee tapahtuman ja poistaa `Tab`:in komponentista kehittäjälle, poistaen tarpeen kehittäjän itse toteuttaa tätä toimintoa.

### Segmenttivalinta <DocChip chip='since' label='26.00' /> {#segment-control}

`TabbedPane` voidaan renderoida segmenttivalinnaksi aktivoimalla `segment`-ominaisuus `setSegment(true)`. Tässä tilassa välilehdet näytetään liukuvalla pillindikaattorilla, joka korostaa aktiivista valintaa, tarjoten kompaktin vaihtoehdon tavanomaiselle välilehti käyttöliittymälle.

<ComponentDemo
path='/webforj/tabbedpanesegment'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneSegmentView.java']}
height='250px'
/>

## Tyylit {#styling}

### Laajuus ja teema {#expanse-and-theme}

`TabbedPane` sisältää sisäänrakennetut `Expanse` ja `Theme` vaihtoehdot, jotka ovat samanlaisia kuin muut webforJ komponentit. Näitä voidaan käyttää nopeasti tyylittämään, joka välittää eri merkityksiä loppukäyttäjälle ilman tarvetta tyylittää komponenttia CSS:llä.

<ComponentDemo
path='/webforj/tabbedpaneexpansetheme'
files={['src/main/java/com/webforj/samples/views/tabbedpane/TabbedPaneExpanseThemeView.java']}
height='250px'
/>

<TableBuilder name={['Tab', 'TabbedPane']} />

## Parhaat käytännöt {#best-practices}

Seuraavia käytäntöjä suositellaan käytettäväksi `TabbedPane` -elementissä sovelluksissa:

- **Looginen ryhmittely**: Käytä välilehtiä loogisesti ryhmittämään liittyvää sisältöä
    >- Jokaisen välilehden tulisi edustaa erillistä kategoriaa tai toiminnallisuutta sovelluksessasi
    >- Ryhmittele samankaltaisia tai loogisia välilehtiä lähelle toisiaan

- **Rajoitettu määrä välilehtiä**: Vältä käyttäjien ylivoimaamista liian monilla välilehdillä. Ota huomioon hierarkkinen rakenne tai muut navigointimallit, joissa soveltuu siisti käyttöliittymä

- **Selkeät etiketit**: Merkitse välilehdet selkeästi intuitiivista käyttöä varten
    >- Tarjoa selkeät ja tiiviit etiketit jokaiselle välilehdelle
    >- Etiketit tulisi kuvastaa sisältöä tai tarkoitusta, jotta käyttäjien on helppo ymmärtää
    >- Hyödynnä kuvakkeita ja erottuvia värejä, kun soveltuu

- **Näppäimistön navigointi**: Hyödynnä webforJ:n `TabbedPane` näppäimistön navigointitukea varmistaaksesi, että vuorovaikutus `TabbedPane`:n kanssa on sujuvampaa ja intuitiivisempaa loppukäyttäjälle

- **Oletusvälilehti**: Jos oletusvälilehti ei ole asetettu `TabbedPane`:n alkuun, harkitse tämän välilehden asettamista oletukseksi välttämättömän tai usein käytetyn tiedon esittämiseksi.
