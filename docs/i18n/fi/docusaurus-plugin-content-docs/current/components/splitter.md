---
title: Splitter
sidebar_position: 115
_i18n_hash: 340bcd9862027e6bfb967c0e6a9b5ec1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja säätämään sisältöä sovelluksessasi, kapseloi kaksi säädettävää komponenttia: pää- ja yksityiskohta-komponentit. Jakaja erottaa nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää jokaisen komponentin kokoa mieltymystensä mukaan.

<!-- INTRO_END -->

## Luodaan jakaja {#creating-a-splitter}

Luo `Splitter` välittämällä kaksi komponenttia sen konstruktorille. Ensimmäisestä tulee pääpaneeli ja toisesta yksityiskohtapaneeli.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Minimi- ja maksimikoko {#min-and-max-size}

`Splitter`-komponentti tarjoaa menetelmiä asetettavaksi paneelien minimikokoja ja maksimikokoja, jolloin voit hallita komponenttien säätö käyttäytymistä `Splitter`-komponentin sisällä. Kun käyttäjät pyrkivät säätämään paneeleja yli määritettyjen minimi- tai maksimikokojen, jakajakomponentti pakottaa nämä rajoitukset, varmistaen, että paneelit pysyvät määritettyjen rajojen sisällä.

### Kokojen asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)`-metodi määrittää minimikoon jakajan pääpaneelille. Samoin `setMasterMaxSize(String masterMaxSize)`-metodi määrittää maksimikoon pääpaneelille.

Voit määrittää kokoja käyttäen mitä tahansa voimassa olevaa CSS-yksikköä, kuten alla on esitetty:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Suunta {#orientation}

Voit konfiguroida suuntaa `Splitter`-komponentissa, jolloin voit luoda asetteluja, jotka on mukautettu erityisiin suunnittelutarpeisiin. Määrittämällä suunnan komponentti asettaa paneelit vaakasuoriksi tai pystysuoriksi, mikä tarjoaa monipuolisuutta asettelusuunnittelussa.

Voit määrittää suunnan käyttämällä tuetut suunnat enumia määrittääksesi, tulisiko `Splitter`-komponentin renderöidä vaakasuoraan vai pystysuoraan:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Suhteellinen sijainti {#relative-position}

Aseta jakajapalkin alkuperäinen sijainti `Splitter`-komponentissa käyttämällä `setPositionRelative`. Tämä metodi hyväksyy numeerisen arvon arvojen `0` ja `100` välillä, joka edustaa prosenttiosuutta annetusta tilasta `Splitter`-komponentissa, ja näyttää jakajan määritellyssä prosenttiosuudessa kokonaisleveyden mukaan:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Kiemurointi {#nesting}

Jakajan kiemurointi mahdollistaa monimutkaisten asettelujen luomisen säädettävien paneelien tasoilla. Se mahdollistaa monimutkaisten käyttöliittymien luomisen, joissa on yksityiskohtainen hallinta sisällön asettelusta ja koosta.

Nostaaksesi jakaja-komponentteja, luo uusia `Splitter`-instansseja ja lisää niitä lapsina olemassa oleviin `Splitter`-komponentteihin. Tämä hierarkkinen rakenne mahdollistaa monitasoisten asettelujen luomisen joustavilla säätömahdollisuuksilla. Seuraava ohjelma havainnollistaa tätä:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Automaatiosäästö {#auto-save}

`Splitter`-komponentti sisältää automaatiosäästö-option, joka tallentaa paneelikoordinaatit paikalliseen tallennustilaan pitääkseen mitat johdonmukaisina uudelleenlatausten välillä.

Kun asetat automaatiosäästökonfiguraation, `Splitter`-komponentti tallentaa automaattisesti paneelikoordinaatit verkkoselaimen paikalliseen tallennustilaan. Tämä varmistaa, että käyttäjien valitsemat paneelikoot säilyvät sivun uudelleenlatausten tai selainistuntojen aikana, mikä vähentää manuaalisten säätöjen tarvetta.

### Tilan puhdistaminen {#cleaning-the-state}

Palauttaaksesi `Splitter`-komponentin oletusasetuksiin ja -mittoihin ohjelmallisesti, kutsu `cleanState()`-metodia poistaaksesi kaikki tallennetut tilatiedot, jotka liittyvät `Splitter`-komponenttiin verkkoselaimen paikallisesta tallennustilasta.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='400px'
/>

Edellisessä esimerkkissä jokainen Splitter-instanssi aktivoi Automaatiosäästö-ominaisuuden kutsumalla `setAutosave`-metodin. Tämä varmistaa, että paneelikoot tallennetaan automaattisesti paikalliseen tallennustilaan. Näin ollen, kun selain ladataan, näiden jakajien koot pysyvät samoina.

"Nollaa tila" -painikkeen napsauttaminen kutsuu `cleanState()`-metodia ja päivittää selaimen ikkunan näyttämään alkuperäiset mitat.

## Tyylittely {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Splitter`-komponentin käytön aikana, ota huomioon seuraavat parhaat käytännöt:

- **Säädä sisällön mukaan**: Kun päätät paneelien suuntauksen ja alkuperäiskoot, ota huomioon sisällön prioriteetti. Esimerkiksi asettelussa, jossa on navigointipaneeli ja pääsisältöalue, navigointipaneelin tulisi yleensä pysyä kapeampana, ja sille tulisi asettaa minimikoko selkeää navigointia varten.

- **Strateginen Kiemurointi**: Kiemurointi jakajissa voi luoda monipuolisia asetteluja, mutta se voi monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele liitännäiset asettelusi varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttäjäkokemusta.

- **Muista käyttäjän mieltymykset**: Käytä Automaatiosäästö-ominaisuutta muistaaksesi käyttäjän säätöjä istuntojen välillä, parantaen käyttäjäkokemusta. Tarjoa vaihtoehto, joka mahdollistaa käyttäjille oletusasetuksiin palauttamisen.
