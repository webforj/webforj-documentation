---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 5ebe30c35548db6d3b603b8a72ed4c2a
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`-luokka käyttää `Column`-luokkia tietosarakkeiden luomisen käsittelyyn. Tällä luokalla on laaja valikoima toimintoja, joiden avulla käyttäjä voi perusteellisesti mukauttaa mitä kuhunkin sarakkeeseen näytetään. 
Lisää sarake `Table`-luokkaan käyttämällä yhtä `addColumn`-tehdasmetodista.

## Arvontuottajat {#value-providers}

Arvontuottaja on funktio, joka on vastuussa raakadatasta, joka tulee taustalla olevalta tietosarjalta, muuntamisesta muotoon, joka soveltuu näytettäväksi tietyssä sarakkeessa. Käyttäjän määrittelemä funktio ottaa vastaan rivin tietotyypin instanssin (T) ja palauttaa arvon, joka näytetään kyseisessä sarakkeessa kyseiselle riville.

Aseta arvontuottaja sarakkeeseen käyttämällä yhtä `addColumn`-tehdasmetodista, joka hyväksyy tuottajan argumenttina:

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

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

Tässä esimerkissä sarake yrittää käyttää dataa JSON-objektista, näyttäen sen vain, jos data ei ole null.

## Kiinnityssuunta {#pin-direction}

Sarakkeiden kiinnitys on ominaisuus, joka sallii käyttäjien kiinnittää sarakkeen tietyn puolen taulussa, parantaen näkyvyyttä ja saatavuutta. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnisteet tai olennaiset tiedot, on tarpeen pitää näkyvänä vaakasuorassa vierityksessä taulukossa.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Sarake voidaan kiinnittää kolmeen suuntaan:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake näkyy lisäysjärjestyksen perusteella.

Kiinnitys voidaan asettaa ohjelmallisesti, mikä sallii käyttäjien muuttaa kiinnityssuuntaa käyttäjävuorovaikutuksen tai sovelluslogiikan perusteella.

## Kohdistus {#alignment}

Sarakkeen kohdistus määrittelee datan vaakasuuntaisen sijoittelun sarakkeessa. Se vaikuttaa siihen, miten datan arvot esitetään, tarjoten käyttäjille visuaalista opastusta tiedon luonteesta.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Table Component tukee kolmea pääkohdistusvaihtoehtoa:

- `Column.Alignment.LEFT`: Sopii tekstuaaliseen tai kuvastavaan dataan, jossa vasemmalle suuntautuvan järjestyksen ylläpitäminen on intuitiivista. Hyödyllinen sisällön aloituspisteen korostamisessa.
- `Column.Alignment.CENTER`: Ihanteellinen numero- tai kategorisoidulle datalle, jossa tasapainoinen esitys on toivottavaa. Luodaan visuaalisesti keskitetty näyttö.
- `Column.Alignment.RIGHT`: Yleisimmän käytännön mukaista numeroarvoille, erityisesti kun lukujen suuruus tai tarkkuus on merkittävä. Kohdistaa datan oikealle luonnollisen lukusuunnan varmistamiseksi.

Edellisessä esimerkissä viimeinen sarake `Cost` on oikeakohdistettu, jotta siitä olisi selkeämpi visuaalinen ero.

## Näkyvyys {#visibility}

On mahdollista asettaa `Column`:in näkyvyys, määrittäen näytetäänkö se taulukossa vai ei. Tämä voi olla hyödyllistä, kun muun muassa päätetään, näytetäänkö arkaluontoisia tietoja.

Käytä `setHidden()`-metodia, kuten alla on esitetty, tämän ominaisuuden asettamiseksi sarakkeeseen:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät vuorovaikuttaa sarakkeen kanssa navigoinnin aikana. Asettaminen `setSuppressNavigable()` tottelee rajoittaa käyttäjävuorovaikutusta sarakkeen kanssa, tarjoten vain luku -kokemuksen.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Otsikko {#label}

Sarakkeen otsikko on sen julkinen tunniste, joka edistää näytettävän datan selkeyttä ja ymmärtämistä. Käytä setLabel-älä asettaaksesi tai muuttaaksesi sarakkeeseen liittyvää otsikkoa.

:::tip
Oletuksena otsikko on sama kuin sarakkeen ID
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## Minimileveys {#minimum-width}

`setMinWidth()`-metodi sallii sinun määrittää sarakkeen minimileveyden, varmistaen johdonmukaisen ja esteettisesti miellyttävän asettelun.

Jos minimileveyttä ei anneta, taulukko laskee minimileveyden sarakkeen sisällön perusteella.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
Annettavat arvot edustavat toivottua minimileveyttä pikseleinä.  
:::
