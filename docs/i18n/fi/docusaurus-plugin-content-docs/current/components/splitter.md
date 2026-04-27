---
title: Splitter
sidebar_position: 115
_i18n_hash: 9eb7b2aa3890f16f8fe8a2d4c303b227
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja koon muuttamaan sisältöä sovelluksessasi, kapseloi kaksi kokoa muunneltavaa komponenttia: pää- ja yksityiskohtakomponentit. Jakaja erottaa nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää kummankin komponentin kokoa omien mieltymystensä mukaan.

<!-- INTRO_END -->

## Luodaan jakaja {#creating-a-splitter}

Luo `Splitter` siirtämällä kaksi komponenttia sen konstruktorille. Ensimmäinen muuttuu pääpaneeliksi ja toinen yksityiskohtapaneeliksi.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Minimi- ja maksimi koko {#min-and-max-size}

`Splitter`-komponentti tarjoaa menetelmiä asetettavaksi minimiksi ja maksimi kokoiksi sen paneeleille, jolloin voit hallita komponenttien koon muutoskäyttäytymistä `Splitter`-komponentin sisällä. Kun käyttäjät yrittävät muuttaa paneeleita yli määrättyjen minimien tai maksimi kokojen, jakajakomponentti pakottaa nämä rajoitukset, varmistaen että paneelit pysyvät määritellyissä rajoissa.

### Kokojen asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)`-menetelmä määrittelee minimikoon jakajan pääpaneelille. Vastaavasti `setMasterMaxSize(String masterMaxSize)`-menetelmä määrittelee maksimikoon pääpaneelille.

Voit määrittää koot käyttäen mitä tahansa kelvollisia CSS-yksiköitä, kuten on esitetty alla:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Suunta {#orientation}

Voit konfiguroida suunnan `Splitter`-komponentissa, jolloin voit luoda asetteluja, jotka on räätälöity erityisiin suunnittelutarpeisiin. Määrittämällä suunnan komponentti järjestää paneelit vaaka- tai pystysuoraan, mikä tarjoaa joustavuutta asettelun suunnittelussa.

Konfiguroidaksesi suuntaa, käytä tuettuja suuntia Enumia määrittämään, pitäisikö `Splitter`-komponentin renderöidä vaakasuoraan vai pystysuoraan:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Suhteellinen sijainti {#relative-position}

Aseta jakajan alun perin sijainti `Splitter`-komponentissa käyttämällä `setPositionRelative`. Tämä menetelmä ottaa numeerisen arvon, joka vaihtelee välillä `0`–`100`, mikä edustaa prosenttiosuutta annettavasta tilasta `Splitter`-komponentissa, ja näyttää jakajan annettuna prosenttiosuutena kokonaisleveyksistä:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Pesiminen {#nesting}

Jakajien pesiminen mahdollistaa monimutkaisten asetteluiden luomisen muunneltavien paneelien tasoilla. Se mahdollistaa monimutkaisten käyttöliittymien luomisen, joissa on yhteensopivat säätömahdollisuudet sisällön järjestelylle ja koon muuttamiselle.

Jotta voit pesiä Splitter-komponentteja, luo uusia `Splitter`-instansseja ja lisää ne lapsina olemassa oleviin `Splitter`-komponentteihin. Tämä hierarkkinen rakenne mahdollistaa monitasoisten asetteluiden luomisen joustavilla koon muutosmahdollisuuksilla. Alla oleva ohjelma havainnollistaa tätä:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Automaattinen tallennus {#auto-save}

`Splitter`-komponentti sisältää automaattisen tallennuksen vaihtoehdon, joka tallentaa paneelikohtaiset koot paikalliseen tallennustilaan, jotta mitat pysyvät johdonmukaisina latausten välillä.

Kun asetat automaattisen tallennuksen konfiguraation, `Splitter`-komponentti tallentaa automaattisesti paneelikohtaiset koot verkkoselaimen paikalliseen tallennustilaan. Tämä varmistaa, että paneelit valitsemat koot säilyvät sivun latauksen tai selaimen istuntojen välillä, vähentäen manuaalisten säädösten tarvetta.

### Tilanteen puhdistaminen {#cleaning-the-state}

Voit ohjelmallisesti palauttaa `Splitter`-komponentin oletusasetuksiin ja mittoihin kutsumalla `cleanState()`-menetelmää, joka poistaa kaiken tallennetun tiladatapadon `Splitter`-komponentin osalta verkkoselaimen paikallisesta tallennustilasta.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='400px'
/>

Edellisessä demossa jokainen Splitter-instanssi aktivoi automaattisen tallennuksen käyttämällä `setAutosave`-menetelmää. Tämä varmistaa, että paneelikohtaiset koot tallennetaan automaattisesti paikalliseen tallennustilaan. Näin ollen, kun selain ladataan uudelleen, näiden jakajien koot pysyvät samoina.

"Tyhjennä tila" -painikkeen napsauttaminen kutsuu `cleanState()`-menetelmää ja päivitää selainikkunan näyttämään alkuperäiset mitat.

## Tyylit {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttökokemuksen varmistamiseksi `Splitter`-komponentin käytön aikana, harkitse seuraavia parhaita käytäntöjä:

- **Säädä sisällön perusteella**: Kun päätät paneelien suuntaa ja aloituskokoja, ota huomioon sisällön tärkeys. Esimerkiksi asettelussa, jossa on navigointipalkki ja pääsisältöalue, navigointipalkin tulisi yleensä pysyä kapeampana, ja sille tulisi asettaa minimikoko selkeän navigoinnin varmistamiseksi.

- **Strateginen pesiminen**: Jakajien pesiminen voi luoda monipuolisia asetteluja, mutta se voi myös monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele pesityt asettelut varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttökokemusta.

- **Muista käyttäjän mieltymykset**: Käytä automaattisen tallennuksen toimintoa muistaaksesi käyttäjän säädöt istuntojen välillä, mikä parantaa käyttökokemusta. Tarjoa mahdollisuus, joka sallii käyttäjille palauttaa oletusasetukset.
