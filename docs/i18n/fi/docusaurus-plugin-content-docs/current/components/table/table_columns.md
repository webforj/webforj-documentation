---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: b3545c590336bb6574bf196fbd417349
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`-luokka hyödyntää `Column`-luokkia luodakseen tietosarakkeita sen sisälle. Tällä luokalla on laaja valikoima toiminnallisuuksia, jotka mahdollistavat käyttäjälle täydellisen mukauttamisen sille, mitä kussakin sarakkeessa näytetään.
Lisätäksesi `Column`:n `Table`:n, käytä yhtä `addColumn`-tehtävämenetelmistä.

## Arvotoimittajat {#value-providers}

Arvotoimittaja on funktio, joka vastaa raakadatan kääntämisestä taustalla olevaan tietojoukkoon sellaiseen muotoon, joka on sopiva näytettäväksi tietyssä sarakkeessa. Käyttäjän määrittämä funktio ottaa vastaan rivitietotyypin (T) ja palauttaa arvon, joka näytetään liittyvässä sarakkeessa kyseiselle riville.

Asettaaksesi arvotoimittajan sarakkeelle, käytä jotain `addColumn`-tehtävämenetelmistä, jotka ottavat vastaan toimittajan argumenttina:

```java
    List<String> columnsList = Arrays.asList("urheilija", "ikä", "maa", "vuosi", "laji", "kulta", "hopea", "pronssi", "yhteensä");

    for (String column : columnsList) {
      table.addColumn(column, (JsonObject person) -> {
        JsonElement element = person.get(column);
        if (!element.isJsonNull()) {
          return element.getAsString();
        }
        return "";
      });
    }
```

Tässä esimerkissä sarake yrittää päästä käsiksi tietoihin JSON-objektista, renderöiden sen vain, jos tiedot eivät ole null.

## Pinnaussuunnat {#pin-direction}

Sarakkeiden pinnaaminen on ominaisuus, joka mahdollistaa käyttäjien kiinnittää tai "pinnata" sarakkeen tietyn puolelle taulukkoa, parantaen näkyvyyttä ja saavutettavuutta. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnistetiedot tai olennaiset tiedot, on tarpeen pitää näkyvissä, kun vieritetään vaakasuunnassa taulukossa.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Sarake voidaan kiinnittää kolmeen suuntaan:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake näkyy lisäysjärjestyksen mukaan.

Pinnaamista voidaan asettaa ohjelmallisesti, jolloin käyttäjät voivat muuttaa pinnaussuunnan käyttäjätapahtumien tai sovelluksen logiikan mukaan.

## Kohdistus {#alignment}

Sarakkeen kohdistus määrittelee datan vaakasuoran sijoittamisen sarakkeessa. Se vaikuttaa siihen, miten datan arvot näytetään, tarjoten visuaalisen ohjeen käyttäjille tiedon luonteesta.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Table-komponentti tukee kolmea pääkohdistusvaihtoehtoa:

- `Column.Alignment.LEFT`: Sopii tekstuaaliseen tai kuvailevaan dataan, jossa vasemmalle suuntautuva virtaus on intuitiivinen. Hyödyllinen, kun halutaan korostaa sisällön aloituspistettä.
- `Column.Alignment.CENTER`: Ihanteellinen numeeriseen tai kategoriseen dataan, jossa tasapainoinen esitys on toivottavaa. Luo visuaalisesti keskitety näyttö.
- `Column.Alignment.RIGHT`: Yleisesti käytetään numeeriselle datalle, erityisesti kun lukujen suuruus tai tarkkuus on merkittävä. Kohdistaa datan oikealle luonnollisen lukemisen virtauksen saavuttamiseksi.

Edellä olevassa esimerkissä viimeinen sarake `Kustannus` on ollut oikealle kohdistettu luodakseen selvemmän visuaalisen eron.

## Näkyvyys {#visibility}

On mahdollista asettaa `Column`:n näkyvyys, päättäen, näytetäänkö se taulukossa vai ei. Tämä voi olla hyödyllistä, kun muun muassa päätetään, näytetäänkö arkaluonteisia tietoja.

Käytä `setHidden()`-metodia, kuten alla on esitetty, asettaaksesi tämän ominaisuuden sarakkeelle:

`table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);`

## Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät vuorovaikuttaa sarakkeen kanssa navigoinnin aikana. Asettaessaan `setSuppressNavigable()` arvoksi true rajoittaa käyttäjien vuorovaikutusta sarakkeen kanssa, tarjoten vain lukemiskokemuksen.

```java
table.addColumn("Vain luku -sarakke", Product::getDescription).setSuppressNavigable(true);
```

## Otsikko {#label}

Sarakkeen otsikko on sen julkisesti näkyvä tunniste, joka lisää selkeyttä ja ymmärrystä näytettävään dataan. Käytä setLabel asettaaksesi tai muuttaaksesi sarakkeeseen liittyvää otsikkoa.

:::tip
Oletusarvoisesti otsikko on sama kuin sarakkeen tunnus
:::

```java
table.addColumn("Tuotteen ID", Product::getProductId).setLabel("ID");
```

## Minimileveys {#minimum-width}

`setMinWidth()`-metodi mahdollistaa sarakkeen minimaalisen leveyden määrittämisen, varmistaen johdonmukaisen ja esteettisesti miellyttävän ulkoasun.

Jos minimileveyttä ei anneta, taulukko laskee minimileveyden sarakkeen sisällön perusteella.

```java
table.addColumn("Hinta", Product::getPrice).setMinWidth(100.0);
```

:::info
Annettu arvo edustaa toivottua minimileveyttä pikseleinä.  
:::
