---
title: Splitter
sidebar_position: 115
_i18n_hash: 7a830c81311c3830e4d1c36bd08903c5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja muuttamaan sisältöä sovelluksessasi, sisältää kaksi muokattavaa komponenttia: isäntä- ja yksityiskohtaiset komponentit. Jakaja erottaa nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää kunkin komponentin kokoa mieltymystensä mukaan.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Minimi- ja maksimikoko {#min-and-max-size}

`Splitter`-komponentti tarjoaa menetelmiä minimoidun ja maksimoidun koon asettamiseen paneeleille, jolloin voit hallita komponenttien kokoa `Splitter`-komponentin sisällä. Kun käyttäjät yrittävät muuttaa paneelien kokoa määritettyjen minimi- tai maksikokojen yli, jakokomponentti valvoo näitä rajoituksia varmistaen, että paneelit pysyvät määritellyissä rajoissa.

### Kokojen asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)`-menetelmä määrittää minimikoon isäntäpaneelille. Vastaavasti `setMasterMaxSize(String masterMaxSize)`-menetelmä määrittää maksimikoon isäntäpaneelille.

Voit määrittää kokoja käyttäen mitä tahansa kelvollisia CSS-yksiköitä, kuten alla on esitetty:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Suunta {#orientation}

Voit määrittää suunnan `Splitter`-komponentissa, mikä sallii luoda asetteluja, jotka on räätälöity erityisiin suunnitteluvaatimuksiin. Määrittämällä suunnan komponentti järjestää paneelit vaakasuoraan tai pystysuoraan, mikä tarjoaa monipuolisuutta asettelusuunnittelussa.

Määrittääksesi suunnan, käytä tuettuja suunnan Enum -arvoja määrittääksesi, tulisiko `Splitter`-komponentin renderöidä vaakasuuntaan tai pystysuuntaan:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Suhteellinen sijainti {#relative-position}

Asettaaksesi jakajapalkin alkuperäisen sijainnin `Splitter`-komponentissa, käytä `setPositionRelative`. Tämä menetelmä ottaa numeerisen arvon, joka vaihtelee välillä `0` ja `100`, joka edustaa prosenttiosuutta annetusta tilasta `Splitter`-komponentissa, ja näyttää jakajan annettuna prosenttina kokonaisleveydestä:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Pesiminen {#nesting}

Splitter-nestaminen mahdollistaa monimutkaisten asettelujen luomisen muokattavilla paneeleilla. Se mahdollistaa kehittyneiden käyttäjäliittymien luomisen, joissa on tarkka hallinta sisällön järjestelysystä ja koosta.

Nestääsi `Splitter`-komponentteja, luo uusia `Splitter`-instansseja ja lisää ne olemassa olevien `Splitter`-komponenttien lapsiksi. Tämä hierarkkinen rakenne mahdollistaa monitasoisten asettelujen luomisen joustavilla koonmuutosmahdollisuuksilla. Alla oleva ohjelma havainnollistaa tätä:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Automaattinen tallennus {#auto-save}

`Splitter`-komponentti sisältää automaattisen tallennusvaihtoehdon, joka tallentaa paneelikoiden tilan paikalliseen tallennustilaan, jotta mitat pysyvät ennallaan sivun uudelleenlatausten aikana.

Kun määrität automaattisen tallennuksen asetuksen, `Splitter`-komponentti tallentaa automaattisesti paneelikoiden tilan verkkoselaimen paikalliseen tallennustilaan. Tämä varmistaa, että käyttäjien valitsemat paneelikoot säilyvät sivun uudelleenlatausten tai selaimen istuntojen välillä, mikä vähentää manuaalisten säätöjen tarvetta.

### Tilan puhdistaminen {#cleaning-the-state}

Palauttaaksesi `Splitter`-komponentin ohjelmallisesti oletusasetuksiin ja mittoihin, kutsu `cleanState()`-menetelmää poistaaksesi kaikki tallennetut tilatiedot, jotka liittyvät `Splitter`-komponenttiin verkkoselaimen paikallisesta tallennustilasta.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

Edellisessä demossa jokainen Splitter-instanssi aktivoi automaattisen tallennusominaisuuden kutsumalla `setAutosave`-menetelmää. Tämä varmistaa, että paneelikoiden mitat tallennetaan automaattisesti paikalliseen tallennustilaan. Näin ollen, kun selain ladataan uudelleen, näiden splitterien mitat pysyvät samoina.

"Nollaa tila" -painiketta napsauttamalla kutsutaan `cleanState()`-menetelmää ja päivitetään selaimen ikkunaa näyttämään alkuperäiset mitat.

## Tyylittely {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Splitter`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Säädä sisällön mukaan**: Kun päätät paneelien suuntaa ja alkuperäisiä kokoja, harkitse sisällön tärkeyttä. Esimerkiksi asettelussa, jossa on navigointipalkki ja pääsisältöalue, navigointipalkin tulisi yleensä pysyä kapeammin ja sille tulisi asettaa minimikoko selkeää navigointia varten.

- **Strateginen pesiminen**: Nestävät splitterit voivat luoda monipuolisia asetteluja, mutta voivat myös monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele pesityt asettelut varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttäjäkokemusta.

- **Muista käyttäjän mieltymykset**: Käytä automaattisen tallennuksen ominaisuutta muistaaksesi käyttäjän säätöjä istuntojen välillä, parantaen käyttäjäkokemusta. Tarjoa vaihtoehto, joka sallii käyttäjien palauttaa oletusasetuksiin.
