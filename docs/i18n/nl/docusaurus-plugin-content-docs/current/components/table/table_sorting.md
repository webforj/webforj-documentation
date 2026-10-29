---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 94345d1313939015f02cf46ecaa5af99
---
Sorteren stelt gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is handig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom moeten vinden.

:::tip Gegevens beheren en opvragen
Voor informatie over hoe het `Repository`-patroon te gebruiken om verzamelingen te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
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

Standaard kan een kolom niet worden gesorteerd, tenzij dit expliciet is ingeschakeld. Om sorteren op een specifieke kolom mogelijk te maken, gebruik je de methode `setSortable(true)`:

```java
table.getColumn("Leeftijd").setSortable(true);
```

## Multi-sorteren {#multi-sorting}

:::warning Multi-koloms sorteren is standaard uitgeschakeld in webforJ `25.00`
Voor webforj `25.00` ondersteunden tabellen standaard multi-koloms sorteren. Begonnen met versie `25.00` is dit gedrag gewijzigd—ontwikkelaars moeten nu expliciet multi-koloms sorteren inschakelen.
:::

Als multi-sorteren nodig is, moet `setMultiSorting(true)` op de tabel worden toegepast. Dit stelt gebruikers in staat om meerdere kolommen opeenvolgend te sorteren:

```java
table.setMultiSorting(true);
```

Met multi-sorteren ingeschakeld, zal het klikken op meerdere kolomkoppen de kolommen opeenvolgend sorteren. De sorteervolgorde wordt visueel aangegeven in de tabel-UI.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Je kunt ook sorteerprioriteit programmatically definiëren voor server-side sorteren. Gebruik `setSortIndex()` op de kolommen die je wilt sorteren, in volgorde van prioriteit:

```java
// Sorteervolgorde van server
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info Kolomvolgorde is belangrijk
Tenzij `setSortIndex()` wordt gebruikt, sorteert de tabel standaard op volgorde waarin de kolommen zijn gedeclareerd.
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

Wanneer een kolom sorteren is ingeschakeld, verschijnt er een set verticale pijlindicatoren aan de bovenkant van de set kolom. Deze pijlen stellen de gebruiker in staat om tussen de verschillende sorteerrichtingen te schakelen.

Wanneer de oplopende volgorde is geselecteerd, wordt een `^` weergegeven, terwijl de aflopende volgorde een `v` zal tonen.

## Client versus server-side sorteren {#client-vs-server-side-sorting}

Het sorteren van gegevens kan in grote lijnen worden gecategoriseerd in twee hoofdbenaderingen: **Client Sorting** en **Server Sorting**.

### Client sorteren {#client-sorting}

Client sorteren houdt in dat gegevens direct binnen de gebruikersinterface van de clienttoepassing worden gerangschikt en weergegeven. Dit is het sorteren waar gebruikers mee interageren wanneer ze op kolomkoppen klikken, wat invloed heeft op de visuele weergave van gegevens op het scherm.

De ontwikkelaar heeft geen directe controle over client-side sorteren, maar dit wordt bepaald door het kolomtype dat in Java is opgegeven. De volgende types worden momenteel ondersteund:

- TEKST
- GETAL
- BOOLEAN
- DATUM
- DATETIME
- TIJD

:::info
Client sorteren werkt niet wanneer slechts een deel van de gegevens beschikbaar is in de client.
:::

### Server sorteren {#server-sorting}

In tegenstelling tot client-side sorteren, houdt server sorteren in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze benadering is bijzonder nuttig bij grote datasets die mogelijk niet praktisch volledig naar de client kunnen worden overgebracht.

Ontwikkelaars hebben meer controle over de logica van server sorteren. Hierdoor kunnen complexe sorteeralgoritmen en optimalisaties worden geïmplementeerd, wat het geschikt maakt voor scenario's met uitgebreide gegevens. Hierdoor ontvangt de client vooraf gesorteerde gegevens, wat de noodzaak voor uitgebreide client-side verwerking minimaliseert.

:::info
Server sorteren is een prestatiegerichte strategie voor het omgaan met datasets die de mogelijkheden van efficiënte client-side verwerking overschrijden, en is de standaardmethode die door de `Table` wordt gebruikt.
:::

### Kolom eigenschapsnaam {#column-property-name}

Standaard gebruikt de `Table` de ID van een kolom als de eigenschapsnaam bij het opbouwen van sorteerkriteria voor een backend-repository. Wanneer de weergave-ID van een kolom niet overeenkomt met de onderliggende gegevensproperty, of wanneer de kolom een berekende waarde weergeeft, gebruik je `setPropertyName()` om expliciet aan te geven welke eigenschap de `Table` moet sorteren.

```java
// Kolom-ID is "Volledige Naam", maar de backend-eigenschap is "fullName"
table.addColumn("Volledige Naam", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

De eigenschapsnaam wordt doorgestuurd naar de `OrderCriteria` wanneer een sorteerevenement plaatsvindt, waardoor backend-repositories zoals Spring Data JPA of REST-adapters de juiste `ORDER BY`-clausule kunnen opbouwen.

:::warning
Zonder `setPropertyName()` valt de `Table` terug op de kolom-ID. Als dit niet overeenkomt met een geldige backend-eigenschap, zal het sorteren stilzwijgend falen of onjuist geordende gegevens retourneren.
:::

Geneste eigenschaps-paden worden ook ondersteund met behulp van puntnotatie:

```java
table.addColumn("Stad", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparators {#comparators}

De `Column`-component stelt ontwikkelaars in staat om Java `Comparators` te gebruiken voor dynamisch en aangepast sorteren. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om aan te passen hoe gegevens worden gesorteerd, waardoor een hogere controle over het standaard sorteergedrag op basis van natuurlijke ordening mogelijk is.

Om `Comparator`-sorteren in een `Column` te benutten, kun je de methode `setComparator()` gebruiken. Deze methode stelt je in staat om een aangepaste `Comparator`-functie te definiëren die de sorteervolgorde bepaalt.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld is er een aangepaste comparatorfunctie gespecificeerd die twee elementen (a en b) neemt en de sorteervolgorde definieert op basis van de geparsed integer-waarden van de `Number`-eigenschap.

Het gebruik van Comparators voor kolomsortering is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Table` server-side sorteren, en sorteert niet-primitieve waarden met behulp van de `toString()`-methode van Object, waarbij ze worden omgezet naar hun stringwaarden en vervolgens worden gesorteerd.
:::
