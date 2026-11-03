---
sidebar_position: 15
title: Sorteren
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 7a910f5bdcaa8a9e053bcda6706808c2
---
Sorteren laat gebruikers gegevens in kolommen ordenen, waardoor informatie makkelijker te lezen en te analyseren is. Dit is handig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom willen vinden.

:::tip Gegevens beheren en opvragen
Zie de [artikelen over Repository](/docs/advanced/repository/overview) voor informatie over het gebruik van het patroon `Repository` voor het beheren en opvragen van verzamelingen.
:::

<ComponentDemo
path='/webforj/tablesorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Kolommen kunnen standaard niet worden gesorteerd, tenzij dit expliciet is ingeschakeld. Gebruik de methode `setSortable(true)` om sorteren voor een specifieke kolom toe te staan:

```java
table.getColumn("Age").setSortable(true);
```

## Meerdere kolommen sorteren {#multi-sorting}

:::warning Sorteren op meerdere kolommen standaard uitgeschakeld in webforJ `25.00`
Vóór webforj `25.00` ondersteunden tabellen standaard sorteren op meerdere kolommen. Vanaf versie `25.00` is dit gedrag gewijzigd: ontwikkelaars moeten sorteren op meerdere kolommen nu expliciet inschakelen.
:::

Als sorteren op meerdere kolommen nodig is, moet `setMultiSorting(true)` op de tabel worden toegepast. Hiermee kunnen gebruikers meerdere kolommen achtereenvolgens sorteren:

```java
table.setMultiSorting(true);
```

Wanneer sorteren op meerdere kolommen is ingeschakeld, worden kolommen achtereenvolgens gesorteerd als er op meerdere kolomkoppen wordt geklikt. De sorteervolgorde wordt visueel aangegeven in de tabelinterface.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Je kunt de sorteervolgorde ook programmatisch instellen voor sorteren op de server. Gebruik `setSortIndex()` op de kolommen die je wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sort order
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info De kolomvolgorde is van belang
Als `setSortIndex()` niet wordt gebruikt, sorteert de tabel standaard op basis van de volgorde waarin de kolommen zijn gedeclareerd.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortOrderView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sorteerrichting {#sort-direction}

Er zijn drie beschikbare instellingen voor de richting waarin een kolom kan worden gesorteerd:

- `SortDirection.ASC`: Sorteert de kolom in oplopende volgorde.
- `SortDirection.DESC`: Sorteert de kolom in aflopende volgorde.
- `SortDirection.NONE`: De kolom wordt niet gesorteerd.

Wanneer sorteren voor een kolom is ingeschakeld, worden bovenaan de betreffende kolom verticale pijlen weergegeven. Met deze pijlen kan de gebruiker schakelen tussen de verschillende sorteerrichtingen.

Bij selectie van de oplopende volgorde wordt `^` weergegeven; bij de aflopende volgorde wordt `v` weergegeven.


## Sorteren aan client- versus serverzijde {#client-vs-server-side-sorting}

Gegevens sorteren kan grofweg worden onderverdeeld in twee hoofdmethoden: **sorteren aan de clientzijde** en **sorteren aan de serverzijde**.

### Sorteren aan clientzijde {#client-sorting}

Bij sorteren aan de clientzijde worden gegevens rechtstreeks in de gebruikersinterface van de clienttoepassing geordend en weergegeven. Dit is de sortering waarmee gebruikers werken wanneer ze op kolomkoppen klikken en zo bepalen hoe de gegevens op het scherm worden weergegeven.

De ontwikkelaar heeft geen directe controle over sorteren aan de clientzijde. De sortering wordt bepaald door het kolomtype dat in Java is opgegeven. De volgende typen worden momenteel ondersteund:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Sorteren aan de clientzijde werkt niet wanneer slechts een deel van de gegevens beschikbaar is op de client.
:::

### Sorteren aan serverzijde {#server-sorting}

In tegenstelling tot sorteren aan de clientzijde worden gegevens bij sorteren aan de serverzijde op de server geordend voordat ze naar de client worden verzonden. Deze aanpak is bijzonder nuttig bij grote gegevensverzamelingen die niet praktisch in hun geheel naar de client kunnen worden overgebracht.

Ontwikkelaars hebben meer controle over de logica voor sorteren aan de serverzijde. Hierdoor kunnen complexe sorteeralgoritmen en optimalisaties worden geïmplementeerd, wat deze methode geschikt maakt voor situaties met grote hoeveelheden gegevens. Zo ontvangt de client vooraf gesorteerde gegevens en is er minder uitgebreide verwerking aan de clientzijde nodig.


:::info
Sorteren aan de serverzijde is een prestatiegerichte strategie voor gegevensverzamelingen die de mogelijkheden van efficiënte verwerking aan de clientzijde te boven gaan. Dit is de standaardmethode die door `Table` wordt gebruikt.
:::

### Naam van de kolomeigenschap {#column-property-name}

Standaard gebruikt `Table` de ID van een kolom als eigenschapsnaam bij het opstellen van sorteercriteria voor een backendrepository. Als de weergave-ID van een kolom niet overeenkomt met de onderliggende gegevenseigenschap, of als de kolom een berekende waarde weergeeft, gebruik dan `setPropertyName()` om expliciet aan `Table` door te geven op welke eigenschap moet worden gesorteerd.

```java
// Column ID is "Full Name", but the backend property is "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

De eigenschapsnaam wordt doorgegeven aan `OrderCriteria` wanneer een sorteergebeurtenis plaatsvindt. Zo kunnen backendrepositories zoals Spring Data JPA of REST-adapters de juiste `ORDER BY`-clausule opstellen.

:::warning
Zonder `setPropertyName()` valt `Table` terug op de kolom-ID. Als deze niet overeenkomt met een geldige backend-eigenschap, mislukt het sorteren stilzwijgend of worden de gegevens in de verkeerde volgorde geretourneerd.
:::

Geneste eigenschapspaden worden ook ondersteund met puntnotatie:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparators {#comparators}

Met de component `Column` kunnen ontwikkelaars Java-`Comparators` gebruiken voor dynamisch en aangepast sorteren. Een `Comparator` is een mechanisme om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Zo kunnen ontwikkelaars flexibel bepalen hoe gegevens worden gesorteerd en meer controle uitoefenen over het standaard sorteergedrag op basis van natuurlijke volgorde.

Gebruik de methode `setComparator()` om sorteren met `Comparator` in een `Column` toe te passen. Met deze methode kun je een aangepaste `Comparator`-functie definiëren die de sorteervolgorde bepaalt.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld wordt een aangepaste comparatorfunctie opgegeven die twee elementen (a en b) ontvangt en de sorteervolgorde bepaalt op basis van de geparseerde gehele getallen van het kenmerk `Number`.

Comparators gebruiken om kolommen te sorteren is vooral nuttig bij niet-numerieke waarden. Ze zijn ook handig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard sorteert `Table` aan de serverzijde. Niet-primitieve waarden worden gesorteerd met de methode `toString()` van Object, die de waarden omzet naar tekenreeksen en ze vervolgens sorteert.
:::
