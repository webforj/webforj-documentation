---
title: Splitter
sidebar_position: 115
_i18n_hash: 2f66a9093a3c1f6e339df8fb42048a55
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja koon muokkaamiseen sisältöä sovelluksessasi, kapseloi kaksi koon muokkaus komponenttia: master- ja detaljikomponentit. Jakaja erottaa nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää kunkin komponentin kokoa mieltymystensä mukaisesti.

<!-- INTRO_END -->

## Jakajan luominen {#creating-a-splitter}

Luo `Splitter` -komponentti siirtämällä kaksi komponenttia sen konstruktorille. Ensimmäisestä tulee master-paneeli ja toisesta detalj-paneeli.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Minimi- ja maksimikoko {#min-and-max-size}

`Splitter`-komponentti tarjoaa metodeja, joilla voit asettaa minim- ja maksimikoot sen paneeleille, jolloin voit hallita komponenttien koon muokkauskäyttäytymistä `Splitter`-komponentin sisällä. Kun käyttäjät yrittävät muuttaa paneelien kokoa yli määritettyjen minim- tai maksikokojen, splitter-komponentti valvoo näitä rajoituksia varmistaen, että paneelit pysyvät määritettyjen rajojen sisällä.

### Kokojen asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)` -metodi määrittää minimikoon master-paneelille splitterissä. Samoin `setMasterMaxSize(String masterMaxSize)` -metodi määrittää maksimikoon master-paneelille.

Voit määrittää kokoja käyttäen mitä tahansa kelvollista CSS-yksikköä, kuten alla on esitetty:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Suunta {#orientation}

Voit määrittää suuntauksen `Splitter`-komponentissa, jolloin voit luoda asetteluja, jotka on räätälöity erityisiin design-vaatimuksiin. Määrittämällä suuntauksen komponentti järjestää paneelit vaakasuunnassa tai pystysuunnassa, tarjoten monipuolisuutta asettelusuunnittelussa.

Voit määrittää suuntauksen käyttämällä tuettuja suuntalukkuja Enum, jotta voit määrittää, renderöisikö `Splitter` vaakasuunnassa vai pystysuunnassa:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Suhteellinen asema {#relative-position}

Aseta jakajapalkin alkuperäinen asema `Splitter`-komponentissa käyttämällä `setPositionRelative`. Tämä metodi ottaa numeerisen arvon välillä `0` ja `100`, joka edustaa prosenttiosuutta annetusta tilasta `Splitter`-komponentissa, ja näyttää jakajan annettuna prosenttina kokonaisleveyttä:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Pesiminen {#nesting}

Splitterin pesiminen mahdollistaa monimutkaisten asettelujen luomisen resizable-paneelitasoilla. Se mahdollistaa kehittyneiden käyttäjäliittymien luomisen, joissa on hieno kontrolli sisällön järjestelyssä ja koon muokkauksessa.

Pesiäksesi Splitter-komponentteja, instansioi uusia `Splitter`-instansseja ja lisää niitä lapsiksi olemassa oleville `Splitter`-komponenteille. Tämä hierarkkinen rakenne mahdollistaa monitasoisten asettelujen luomisen joustavilla koon muokkausmahdollisuuksilla. Alla oleva ohjelma havainnollistaa tätä:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Automaattinen tallennus {#auto-save}

`Splitter`-komponentti sisältää Automaattiliitäntäasetuksen, joka tallentaa paneelikoombinaatioiden tilan paikalliseen tallennustilaan, jotta mitat pysyvät johdonmukaisina latausten välillä.

Kun asetat automaattisen tallennuksen konfiguraation, `Splitter`-komponentti tallentaa automaattisesti paneelikoombinaatioiden tilan verkkoselaimen paikalliseen tallennustilaan. Tämä varmistaa, että käyttäjien valitsemat paneelikoot säilyvät sivun latausten tai selaimen istuntojen välillä, vähentäen manuaalisten säätöjen tarvetta.

### Tilojen puhdistaminen {#cleaning-the-state}

Palauttaaksesi `Splitter`-komponentin ohjelmallisesti oletusasetuksiin ja -mittoihin, kutsu `cleanState()` -metodia poistaaksesi kaikki tallennetut tilatiedot, jotka liittyvät `Splitter`-komponenttiin verkkoselaimen paikallisesta tallennustilasta.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

Edellisessä demossa jokainen Splitter-instanssi aktivoi AutoSave-toiminnon kutsumalla `setAutosave` -metodia. Tämä varmistaa, että paneelikoot tallennetaan automaattisesti paikalliseen tallennustilaan. Näin ollen, kun selainta ladataan uudelleen, näiden splitterien koot pysyvät samoina.

"Nollaa tila" -painikkeen napsauttaminen kutsuu `cleanState()`-metodia ja päivittää selaimen ikkunan näyttämään alkuperäiset mitat.

## Tyylit {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalinen käyttäjäkokemus käyttäessäsi `Splitter`-komponenttia, harkitse seuraavia parhaita käytäntöjä:

- **Säädä sisällön mukaan**: Kun päätät paneelien suuntauksesta ja alkuperäistä koosta, ota huomioon sisällön tärkeys. Esimerkiksi asettelussa, jossa on navigointisivupaneeli ja pääsisältöalue, sivupaneelin tulisi yleensä pysyä kapeampana, ja sille tulisi asettaa minimikoko selkeää navigointia varten.

- **Strateginen pesiminen**: Pesiminen splitter-komponenteilla voi luoda monipuolisia asetteluja, mutta se voi myös monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele pesityt asettelusi varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttäjäkokemusta.

- **Muista käyttäjän mieltymykset**: Käytä Automaattiliitäntäasetusta muistaaksesi käyttäjän säätöjä istuntojen välillä, mikä parantaa käyttäjäkokemusta. Tarjoa mahdollisuus, jotta käyttäjät voivat palauttaa oletusasetukset.
