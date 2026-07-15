---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: 0683e5c7589bbf3fa42b8ea137c4f809
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja muokkaamaan sisältöä sovelluksessasi, kapseloi kaksi uudelleensäädettävää komponenttia: pää- ja yksityiskohtaiset komponentit. Jakaja erottaa nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää kunkin komponentin kokoa mieltymystensä mukaan.

<!-- INTRO_END -->

## Luodaan jakaja {#creating-a-splitter}

Luo `Splitter` välittämällä kaksi komponenttia sen konstruktoriin. Ensimmäinen tulee pääpaneeliksi ja toinen yksityiskohtaiseksi paneeliksi.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Minimi- ja maksimikoko {#min-and-max-size}

`Splitter`-komponentti tarjoaa menetelmiä asettaa minimaalinen ja maksimaalinen koko sen paneeleille, jolloin voit hallita komponenttien koon muutokseen liittyvää käyttäytymistä `Splitter`-komponentin sisällä. Kun käyttäjät yrittävät muuttaa paneelien kokoa yli määritettyjen minimi- tai maksimikokojen, jakajakomponentti noudattaa näitä rajoituksia varmistaen, että paneelit pysyvät määritettyjen rajojen sisällä.

### Koossa asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)`-menetelmä määrittelee minimikoon jakajan pääpaneelille. Samoin `setMasterMaxSize(String masterMaxSize)`-menetelmä määrittelee maksimikoon pääpaneelille.

Voit määrittää kokoja käyttämällä mitä tahansa voimassa olevaa CSS-yksikköä, kuten seuraavassa esitetään:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Suunta {#orientation}

Voit määrittää suuntaa `Splitter`-komponentissa, jolloin voit luoda asetteluja, jotka on räätälöity erityisiin suunnittelutarpeisiin. Määrittämällä suunnan komponentti järjestää paneelit vaakasuoraan tai pystysuoraan, mikä tarjoaa monipuolisuutta asettelusuunnittelussa.

Voit määrittää suunnan käyttämällä tuettuja suuntanaineita, jotta voit määrittää, tulisiko `Splitter`-komponentin renderöidä vaakasuoraan tai pystysuoraan:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Suhteellinen sijainti {#relative-position}

Aseta jakajan alkuperäinen sijainti `Splitter`-komponentissa käyttämällä `setPositionRelative`. Tämä menetelmä ottaa numeron arvon `0` ja `100` välillä, joka edustaa prosenttiosuutta annetusta tilasta `Splitter`:issä, ja näyttää jakajan annetun prosenttiosuuden koko leveydestä:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Sisäkkäisyys {#nesting}

Splitter-sisäkkäisyys mahdollistaa monimutkaisten asettelujen luomisen, joissa on tasoja uudelleensäädettäviä paneeleita. Se mahdollistaa monimutkaisten käyttöliittymien luomisen, joilla on tarkka hallinta sisällön järjestelyyn ja koon muuttamiseen.

Sisäkkäisten Splitter-komponenttien luomiseksi luo uusia `Splitter`-instansseja ja lisää ne olemassa olevien `Splitter`-komponenttien lapsiksi. Tämä hierarkkinen rakenne mahdollistaa monitasoisten asettelujen luomisen joustavilla koon muutoksilla. Alla oleva ohjelma havainnollistaa tätä:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Automaattinen tallennus {#auto-save}

`Splitter`-komponentti sisältää automaattisen tallennuksen vaihtoehdon, joka tallentaa paneelikokojen tilan paikalliseen tallennukseen, jotta mitat pysyvät yhdenmukaisina uudelleenlatausten välillä.

Kun asetat automaattisen tallennuksen asetuksen, `Splitter`-komponentti tallentaa automaattisesti paneelikokojen tilan verkkoselaimen paikalliseen tallennukseen. Tämä varmistaa, että käyttäjien valitsemat paneelikoot säilyvät sivun latausten tai selainistuntojen aikana, vähentäen manuaalisten säätöjen tarvetta.

### Tilanteen puhdistaminen {#cleaning-the-state}

Voit ohjelmallisesti palauttaa `Splitter`-komponentin oletusasetuksiin ja -mittoihin kutsumalla `cleanState()`-menetelmää, joka poistaa kaikki tallennetut tila- tiedot, jotka liittyvät `Splitter`-komponenttiin verkkoselaimen paikallisesta tallennuksesta.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='400px'
/>

Edellisessä demonstraatiossa jokainen Splitter-instanssi aktivoi automaattisen tallennusominaisuuden kutsumalla `setAutosave`-menetelmää. Tämä varmistaa, että paneelikoot tallennetaan automaattisesti paikalliseen tallennukseen. Näin ollen kun selain ladataan uudelleen, näiden jakajien koot pysyvät samoina.

"Nollaa tila" -painikkeen klikkaaminen kutsuu `cleanState()`-menetelmää ja päivittää selainikkunan näyttämään alkuperäiset mitat.

## Tyylitys {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `Splitter`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Säädä sisällön mukaan**: Kun päätät paneelien suuntaa ja alkuperäisiä kokoja, ota huomioon sisällön prioriteetti. Esimerkiksi asettelussa, jossa on navigointipaneeli ja pääsisältöalue, navigointipaneelin tulisi yleensä pysyä kapeammassa koossa selkeän navigoinnin vuoksi.

- **Strateginen sisäkkäisyys**: Sisäkkäisten jakajien luominen voi luoda monipuolisia asetteluja, mutta se voi myös monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele sisäkkäiset asettelusi varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttäjäkokemusta.

- **Muista käyttäjän mieltymykset**: Käytä automaattisen tallennuksen ominaisuutta muistamaan käyttäjän säädöt istuntojen välillä, mikä parantaa käyttäjäkokemusta. Tarjoa mahdollisuus, jolla käyttäjät voivat palata oletusasetuksiin.
