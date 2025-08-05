---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 2c6b6a04c5c33d9e3f1663d15c85a2e9
---
Sorteren stelt gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is nuttig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom willen vinden.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standaard is een kolom niet sorteerbaar tenzij expliciet ingeschakeld. Om sorteren op een specifieke kolom toe te staan, gebruik je de `setSortable(true)` methode:

```java 
table.getColumn("Leeftijd").setSortable(true);
```

## Multi-sorting {#multi-sorting}

:::warning Multi-Kolom Sorteren Standaard Uitgeschakeld in webforJ `25.00`
Voor webforj `25.00` ondersteunden tabellen standaard multi-kolom sorteren. Vanaf versie `25.00` is dit gedrag veranderd—ontwikkelaars moeten nu expliciet multi-kolom sorteren inschakelen.
:::

Als multi-sorting nodig is, moet `setMultiSorting(true)` worden toegepast op de tabel. Dit stelt gebruikers in staat om meerdere kolommen in volgorde te sorteren:

```java
table.setMultiSorting(true);
```

Met multi-sorting ingeschakeld, laat het klikken op meerdere kolomkoppen ze sequentieel sorteren. De sorteervolgorde wordt visueel aangeduid in de tabel-UI.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Je kunt ook de sorteervolgorde programmeermatig definiëren voor server-side sorteren. Gebruik `setSortOrder()` op de kolommen die je wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sorteervolgorde
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Kolom Volgorde Telt
Tenzij `setSortOrder()` wordt gebruikt, sorteert de tabel standaard op basis van de volgorde waarin de kolommen zijn gedeclareerd.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sorteer richting {#sort-direction}

Er zijn drie beschikbare instellingen voor de richting waarin een kolom kan worden gesorteerd:

- `SortDirection.ASC`: Sorteert de kolom in oplopende volgorde.
- `SortDirection.DESC`: Sorteert de kolom in aflopende volgorde.
- `SortDirection.NONE`: Geen sorteren toegepast op de kolom.

Wanneer een kolom sorteren ingeschakeld heeft, zie je een set verticale pijlindicatoren aan de bovenkant van de set kolom verschijnen. Deze pijlen stellen de gebruiker in staat om te schakelen tussen de verschillende sorteer richtingen.

Wanneer oplopende volgorde is geselecteerd, wordt een `^` weergegeven, terwijl aflopende volgorde een `v` zal weergeven.

## Client vs. server-side sorteren {#client-vs-server-side-sorting}

Sorteren van gegevens kan breed worden gecategoriseerd in twee hoofdbenaderingen: **Client Sorteren** en **Server Sorteren**.

### Client sorteren {#client-sorting}

Client sorteren houdt in dat gegevens rechtstreeks binnen de gebruikersinterface van de clientapplicatie worden gerangschikt en weergegeven. Het is de sortering waarmee gebruikers interageren wanneer ze op kolomkoppen klikken, en het beïnvloedt de visuele weergave van gegevens op het scherm.

De ontwikkelaar heeft geen directe controle over client-side sorteren, maar dit wordt bepaald door het type kolom dat in Java wordt aangeboden. De volgende types worden momenteel ondersteund:

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

In tegenstelling tot client-side sorteren, houdt server sorteren in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze benadering is bijzonder nuttig bij het werken met grote datasets die mogelijk niet praktisch volledig naar de client kunnen worden overgedragen.

Ontwikkelaars hebben meer controle over de logica van server sorteren. Dit maakt de implementatie van complexe sorteeralgoritmen en optimalisaties mogelijk, waardoor het geschikt is voor scenario's met uitgebreide gegevens. Hierdoor ontvangt de client vooraf gesorteerde gegevens, waardoor de noodzaak voor uitgebreide client-side verwerking wordt geminimaliseerd.

:::info
Server sorteren is een prestatiegerichte strategie voor het omgaan met datasets die de mogelijkheden van efficiënte client-side verwerking overstijgen, en is de standaardmethode die door de `Tabel` wordt gebruikt.
:::

#### Vergelijkers {#comparators}

De `Column` component stelt ontwikkelaars in staat om Java `Comparators` te gebruiken voor dynamische en aangepaste sortering. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om aan te passen hoe gegevens worden gesorteerd, waardoor ze hogere controle hebben over het standaard sorteergedrag op basis van natuurlijke ordening.

Om `Comparator` sorteren in een `Column` te benutten, kun je de `setComparator()` methode gebruiken. Deze methode stelt je in staat om een aangepaste `Comparator` functie te definiëren die de sorteerlógica dicteert.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

In het bovenstaande voorbeeld specificeert een aangepaste comparatorfunctie die twee elementen (a en b) neemt en de sorteervolgorde definieert op basis van de geparsed integerwaarden van het `Number` attribuut.

Het gebruik van vergelijkers voor kolomsorteren is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Tabel` server-side sorteren, en sorteert niet-primitieve waarden met behulp van de `toString()` methode van Object, waarbij ze worden omgezet naar hun stringwaarden en vervolgens worden gesorteerd.
:::
