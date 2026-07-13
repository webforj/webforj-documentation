---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: f577bea532193b97e6fef03a8bcb641b
---
Sorteren stelt gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is nuttig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom moeten vinden.

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe u het `Repository`-patroon kunt gebruiken om verzamelingen te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
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

Standaard is een kolom niet sorteerbaar tenzij dit expliciet is ingeschakeld. Om sorteren op een specifieke kolom toe te staan, gebruikt u de methode `setSortable(true)`:

```java
table.getColumn("Age").setSortable(true);
```

## Multi-sorteren {#multi-sorting}

:::warning Multi-Column Sorteren Standaard Uitgeschakeld in webforJ `25.00`
Voordat webforj `25.00`, ondersteunden tabellen standaard multi-kolom sorteren. Vanaf versie `25.00` is dit gedrag veranderd—ontwikkelaars moeten nu expliciet multi-kolom sorteren inschakelen.
:::

Als multi-sorteren nodig is, moet `setMultiSorting(true)` op de tabel worden toegepast. Dit stelt gebruikers in staat om meerdere kolommen op volgorde te sorteren:

```java
table.setMultiSorting(true);
```

Met multi-sorteren ingeschakeld, zal het klikken op meerdere kolomstoppers ze sequentieel sorteren. De sorteerprioriteit wordt visueel in de tabelgebruikersinterface weergegeven.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

U kunt ook programmatically sorteerprioriteit definiëren voor server-side sorteren. Gebruik `setSortOrder()` op de kolommen die u wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sorteer volgorde
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Kolomvolgorde Is Belangrijk
Tenzij `setSortOrder()` wordt gebruikt, sorteert de tabel standaard op de volgorde waarin kolommen zijn gedeclareerd.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sorteerrichting {#sort-direction}

Er zijn drie beschikbare instellingen voor de richting waarin een kolom kan worden gesorteerd:

- `SortDirection.ASC`: Sorteert de kolom in oplopende volgorde.
- `SortDirection.DESC`: Sorteert de kolom in aflopende volgorde.
- `SortDirection.NONE`: Geen sortering toegepast op de kolom.

Wanneer een kolom sorteren heeft ingeschakeld, ziet u een set verticale pijlindicatoren verschijnen bovenaan de betreffende kolom. Deze pijlen stellen de gebruiker in staat om tussen de verschillende sorteerrichtingen te schakelen.

Wanneer de oplopende volgorde is geselecteerd, wordt er een `^` weergegeven, terwijl de aflopende volgorde een `v` zal weergeven.

## Client vs. server-side sorteren {#client-vs-server-side-sorting}

Sorteren van gegevens kan grofweg worden gecategoriseerd in twee hoofdbenaderingen: **Client Sorteren** en **Server Sorteren**.

### Client sorteren {#client-sorting}

Client sorteren houdt in dat gegevens direct binnen de gebruikersinterface van de clientapplicatie worden gerangschikt en weergegeven. Het is de sortering waarmee gebruikers interactie hebben wanneer ze op kolomkoppen klikken, wat de visuele representatie van gegevens op het scherm beïnvloedt.

De ontwikkelaar heeft geen directe controle over client-side sorteren, maar dit wordt bepaald door het kolomtype dat in Java is opgegeven. De volgende typen worden momenteel ondersteund:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Client sorteren werkt niet wanneer slechts een deel van de gegevens beschikbaar is in de client.
:::

### Server sorteren {#server-sorting}

In tegenstelling tot client-side sorteren, houdt server sorteren in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze benadering is bijzonder voordelig bij het omgaan met grote datasets die mogelijk niet praktisch in hun geheel naar de client kunnen worden overgebracht.

Ontwikkelaars hebben meer controle over de logica van server sorteren. Dit maakt het mogelijk om complexe sorteeralgoritmen en optimalisaties te implementeren, waardoor het geschikt is voor scenario's met uitgebreide data. Dit zorgt ervoor dat de client vooraf gesorteerde gegevens ontvangt, waardoor de behoefte aan uitgebreide verwerking aan de clientzijde wordt geminimaliseerd.

:::info
Server sorteren is een prestatiegerichte strategie voor het omgaan met datasets die de capaciteiten van efficiënte client-side verwerking overschrijden, en is de standaardmethode die door de `Table` wordt gebruikt.
:::

### Kolom eigenschapsnaam {#column-property-name}

Standaard gebruikt de `Table` de ID van een kolom als de eigenschapsnaam bij het opbouwen van sorteerkriteria voor een backend-repository. Wanneer de weergave-ID van een kolom niet overeenkomt met de onderliggende dataprop voorwaarde, of wanneer de kolom een berekende waarde weergeeft, gebruik dan `setPropertyName()` om expliciet aan de `Table` te vertellen op welke eigenschap moet worden gesorteerd.

```java
// Kolom-ID is "Volledige Naam", maar de backend-eigenschap is "fullName"
table.addColumn("Volledige Naam", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

De eigenschapsnaam wordt doorgegeven aan de `OrderCriteria` wanneer een sorteerevenement plaatsvindt, waardoor backend-repositories zoals Spring Data JPA of REST-adapters de juiste `ORDER BY`-clausule kunnen bouwen.

:::warning
Zonder `setPropertyName()` valt de `Table` terug op de kolom-ID. Als deze niet overeenkomt met een geldige backend-eigenschap, zal sorteren stilletjes mislukken of onjuist geordende gegevens retourneren.
:::

Geneste eigenschaps paden worden ook ondersteund met behulp van puntnotatie:

```java
table.addColumn("Stad", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vergelijkers {#comparators}

De `Column`-component staat ontwikkelaars toe om Java `Comparators` te gebruiken voor dynamisch en aangepast sorteren. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om aan te passen hoe gegevens worden gesorteerd, wat hogere controle biedt over het standaard sorteergedrag op basis van natuurlijke ordening.

Om `Comparator`-sorteren in een `Column` te benutten, kunt u de methode `setComparator()` gebruiken. Deze methode stelt u in staat om een aangepaste `Comparator`-functie te definiëren die de sorteervlogica bepaalt.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld wordt een aangepaste vergelijkerfunctie gespecificeerd die twee elementen (a en b) neemt en de sorteervolgorde definieert op basis van de geparseerde gehele waarden van de `Number`-attribuut.

Het gebruik van vergelijkers voor kolomsorteren is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Table` server-side sorteren en sorteert niet-primitieve waarden met behulp van de `toString()`-methode van Object, waarbij ze worden omgezet naar hun stringwaarden en vervolgens worden gesorteerd.
:::
