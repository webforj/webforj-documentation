---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: c700d01058105b5b752ecfa560224fb5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja koon muuttamaan sisältöä sovelluksessasi, kapseloi kaksi säädettävää komponenttia: master- ja detail-komponentit. Jakaja erottaa nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää kunkin komponentin kokoa mieltymystensä mukaan.

<!-- INTRO_END -->

## Luo jakaja {#creating-a-splitter}

Luo `Splitter` välittämällä kaksi komponenttia sen konstruktorille. Ensimmäisestä tulee master-paneeli ja toisesta detail-paneeli.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Minimi- ja maksimikoko {#min-and-max-size}

`Splitter`-komponentti tarjoaa metodeja paneelien minimien ja maksimi kokojen asettamiseen, mikä antaa sinun hallita komponenttien koon muuttamis käyttäytymistä `Splitter`-komponentissa. Kun käyttäjät yrittävät muuttaa paneeleita yli määritettyjen minimi- tai maksimikokojen, jakajakomponentti noudattaa näitä rajoituksia varmistaen, että paneelit pysyvät määritettyjen rajojen sisällä.

### Kokojen asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)`-metodi määrittelee minimikoon master-paneelille splitterissä. Vastaavasti `setMasterMaxSize(String masterMaxSize)`-metodi määrittelee maksimi koon master-paneelille.

Voit määrittää kokoja käyttäen mitä tahansa kelvollista CSS-yksikköä, kuten alla on esitetty:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Suunta {#orientation}

Voit määrittää suuntaa `Splitter`-komponentissa, jolloin voit luoda asetteluja, jotka on räätälöity erityisiin suunnittelutarpeisiin. Määrittämällä suunnan komponentti järjestää paneelit vaaka- tai pystysuunnassa, tarjoten monipuolisuutta asettelun suunnittelussa.

Määrittääksesi suunta, käytä tuettuja orientaatioita Enum:ia määrittämään, pitäisikö `Splitter`-komponentin renderöidä vaakasuoraan tai pystysuoraan:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Suhteellinen sijainti {#relative-position}

Aseta jakajapalkin alkuperäinen sijainti `Splitter`-komponentissa käyttämällä `setPositionRelative`-metodia. Tämä metodi ottaa numeron arvona välillä `0`–`100`, joka edustaa prosenttiosuutta annetusta tilasta `Splitterissä`, ja näyttää jakajan annetussa prosentissa kokonaisleveyttä:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Pesiminen {#nesting}

Jakajien pesiminen mahdollistaa monimutkaisten asettelujen luomisen säädettävillä paneeleilla. Se mahdollistaa monimutkaisten käyttöliittymien luomisen, joissa on yksityiskohtainen hallinta sisällön järjestelyyn ja koon muuttamiseen.

Pesimällä `Splitter`-komponentteja, voit instansioida uusia `Splitter`-instansseja ja lisätä ne lapsina olemassa oleville `Splitter`-komponenteille. Tämä hierarkkinen rakenne mahdollistaa monitason asettelujen luomisen joustavilla koon muutostaidoilla. Seuraava ohjelma havainnollistaa tätä:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Automaattinen tallennus {#auto-save}

`Splitter`-komponentti sisältää Automaattisen tallennuksen vaihtoehdon, joka tallentaa paneelien koon tilan paikalliseen tallennukseen, jotta mitat pysyvät samana latausten välillä.

Kun asetat automaattisen tallennuksen kokoonpanon, `Splitter`-komponentti tallentaa automaattisesti paneelien koon tilan verkkoselaimen paikalliseen tallennukseen. Tämä varmistaa, että paneelien käyttäjien valitsemat koot säilyvät sivun latausten tai selaimen istuntojen välillä, vähentäen manuaalisten säätöjen tarvetta.

### Tilan puhdistaminen {#cleaning-the-state}

Palauttaaksesi `Splitter`-komponentin ohjelmallisesti oletusasetuksiin ja -mittoihin, kutsu `cleanState()`-metodia poistaaksesi kaikki tallennetut tila tiedot, jotka liittyvät `Splitter`-komponenttiin verkkoselaimen paikallisesta tallennuksesta.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='400px'
/>

Edellisessä demonstraatiossa jokainen Splitter-instanssi aktivoi Automaattisen tallennuksen ominaisuuden kutsumalla `setAutosave`-metodia. Tämä varmistaa, että paneelien koot tallennetaan automaattisesti paikalliseen tallennukseen. Näin ollen, kun lataa selain, näiden jakajien koot pysyvät samoina.

"Nollaa tila" -painiketta napsauttamalla kutsutaan `cleanState()`-metodia ja päivitetään selaimen ikkuna näyttämään alkuperäiset mitat.

## Tyylittely {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalinen käyttäjäkokemus `Splitter`-komponentin käytön aikana, ota huomioon seuraavat parhaat käytännöt:

- **Säädä sisällön mukaan**: Kun päätät paneelien suuntaa ja aloitusmittoja, ota huomioon sisällön prioriteetti. Esimerkiksi asettelussa, jossa on navigointipaneeli ja pääsisältöalue, navigointipaneelin pitäisi tyypillisesti pysyä kapeampana, ja sille tulisi määrittää minimikoko selkeän navigoinnin varmistamiseksi.

- **Strateginen pesiminen**: Nestetyt jakajat voivat luoda monipuolisia asetteluja, mutta voivat myös monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele pesityt asettelusi varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttäjäkokemusta.

- **Muista käyttäjän mieltymykset**: Käytä automaattisen tallennuksen ominaisuutta muistaaksesi käyttäjien säätöjä istuntojen välillä, parantaen käyttäjäkokemusta. Tarjoa vaihtoehto, jonka avulla käyttäjät voivat palauttaa oletusasetuksiin.
