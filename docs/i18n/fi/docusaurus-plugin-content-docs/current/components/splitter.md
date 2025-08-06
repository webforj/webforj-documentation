---
title: Splitter
sidebar_position: 115
_i18n_hash: 0f8ea00bed7b930d5b7a8efe6bcd5446
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

`Splitter`-komponentti, joka on suunniteltu jakamaan ja koon muuttamaan sisältöä sovelluksessasi, sisältää kaksi säädettävää komponenttia: pääkomponentin ja yksityiskohtakomponentit. Erottaja erottelee nämä komponentit, jolloin käyttäjät voivat dynaamisesti säätää jokaisen komponentin kokoa mieltymystensä mukaan.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Minimi ja maksimi koko {#min-and-max-size}

`Splitter`-komponentti tarjoaa menetelmiä asettaa paneelien minimi- ja maksimikoot, jolloin voit hallita komponenttien koon muuttamiskäyttäytymistä `Splitter`in sisällä. Kun käyttäjät yrittävät muuttaa paneelien kokoa määritettyjen minimi- tai maksimikokojen yli, splitter-komponentti pakottaa nämä rajoitukset, varmistaen, että paneelit pysyvät määritettyjen rajojen sisällä.

### Kokojen asettaminen {#setting-sizes}

`setMasterMinSize(String masterMinSize)`-menetelmä määrittää pääpaneelin vähimmäiskoon splitterissä. Vastaavasti `setMasterMaxSize(String masterMaxSize)`-menetelmä määrittää pääpaneelin enimmäiskoon.

Voit määrittää kokoja käyttämällä mitä tahansa voimassa olevaa CSS-yksikköä, kuten alla on esitetty:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Suunta {#orientation}

Voit määrittää suunnan `Splitter`-komponentissa, jolloin voit luoda asetteluja, jotka on räätälöity erityisiin suunnittelutarpeisiin. Määrittämällä suunnan komponentti järjestää paneelit vaakasuoraan tai pystysuoraan, tarjoten monipuolisuutta asettelusuunnittelussa.

Voit määrittää suunnan käyttämällä tuettuja suunnat Enum, joka määrittää, tulisiko `Splitter`in renderöidä vaakasuorana vai pystysuorana:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Suhteellinen sijainti {#relative-position}

Aseta jakotangon alkuperäinen sijainti `Splitter`-komponentissa käyttämällä `setPositionRelative`. Tämä menetelmä ottaa numeerisen arvon 0–100, joka edustaa prosenttiosuutta määritetystä tilasta `Splitter`issä, ja näyttää jakajan määritettynä prosenttiosuutena kokonaisleveyttä:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Pesiminen {#nesting}

Splitterin pesiminen mahdollistaa monimutkaisten asettelujen luomisen säädettävillä paneeleilla. Se mahdollistaa monimutkaisten käyttöliittymien luomisen, joissa on yksityiskohtainen hallinta sisällön järjestämisestä ja koon muuttamisesta.

Pesimään `Splitter`-komponentteja kannattaa instanssia uusia `Splitter`-instansseja ja lisätä ne olemassa oleviin `Splitter`-komponentteihin lapsina. Tämä hierarkkinen rakenne mahdollistaa monitasoisten asetusten luomisen joustavilla koon muuttamismahdollisuuksilla. Seuraava ohjelma esittää tämän:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Automaaalinen tallennus {#auto-save}

`Splitter`-komponentti sisältää Automaaalisen tallennustilan vaihtoehdon, joka tallentaa paneelikoiden tilan paikalliseen tallennukseen säilyttääkseen mitat johdonmukaisina latausten välillä.

Kun asetat automaattisen tallennuksen kokoonpanon, `Splitter`-komponentti tallentaa automaattisesti paneelikoiden tilan verkkoselaimen paikalliseen tallennukseen. Tämä varmistaa, että paneelikoiden koko, jonka käyttäjät valitsevat, säilyy sivun latausten tai selainistuntojen yli, vähentäen manuaalisten säätöjen tarvetta.

### Tilojen puhdistaminen {#cleaning-the-state}

Voit ohjelmallisesti palauttaa `Splitter`-komponentin takaisin oletusasetuksiin ja mittoihin kutsumalla `cleanState()`-menetelmää, joka poistaa kaikki tallennettu tila-aineisto liittyen `Splitter`-komponenttiin verkkoselaimen paikallisesta tallennuksesta.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

Edellisessä demonissa jokainen Splitter-instanssi aktivoi Automaaalisen tallennusominaisuuden kutsumalla `setAutosave`-menetelmää. Tämä varmistaa, että paneelikoiden koot tallennetaan automaattisesti paikalliseen tallennukseen. Näin ollen, kun selain ladataan uudelleen, näiden splitterien koot pysyvät samoina.

"Nollaa tila" -painiketta napsauttamalla kutsutaan `cleanState()`-menetelmää ja päivitetään selainikkuna näyttämään alkuperäiset mitat.

## Tyylitys {#styling}

<TableBuilder name="Splitter" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Splitter`-komponenttia käytettäessä, harkitse seuraavia parhaita käytäntöjä:

- **Säädä sisällön mukaan**: Määrittäessäsi paneelien suuntaa ja alkukokoja, ota huomioon sisällön prioriteetti. Esimerkiksi asettelussa, jossa on navigointipalkki ja pääsisältöalue, navigointipalkin tulisi yleensä olla kapeampi ja asetettu minimi koko, jotta navigointi olisi selkeää.

- **Strateginen pesiminen**: Pesiminen splitter-komponenttien avulla voi luoda monipuolisia asetteluja, mutta voi monimutkaistaa käyttöliittymää ja vaikuttaa suorituskykyyn. Suunnittele pesityt asettelut varmistaaksesi, että ne ovat intuitiivisia ja parantavat käyttäjäkokemusta.

- **Muista käyttäjän mieltymykset**: Käytä Automaaalista tallennusta muistamaan käyttäjän säätöjä istuntojen välillä, parantaen käyttäjäkokemusta. Tarjoa vaihtoehto, joka sallii käyttäjien palauttavan oletusasetuksiin.
