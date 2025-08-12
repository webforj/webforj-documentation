---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: a51ea10e855e94a24cb6e74d8f774abe
---
Sorting stelt gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is nuttig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom moeten vinden.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standaard is een kolom niet sorteerbaar, tenzij expliciet ingeschakeld. Om sorteren op een specifieke kolom mogelijk te maken, gebruik de `setSortable(true)`-methode:

```java 
table.getColumn("Leeftijd").setSortable(true);
```

## Multi-sorting {#multi-sorting}

:::warning Multi-Column Sorting Standaard Uitgeschakeld in webforJ `25.00`
Voor webforj `25.00` ondersteunden tabellen standaard multi-koloms sortering. Vanaf versie `25.00` is dit gedrag veranderd: ontwikkelaars moeten nu expliciet multi-koloms sortering inschakelen.
:::

Als multi-sorting nodig is, moet `setMultiSorting(true)` op de tabel worden toegepast. Dit stelt gebruikers in staat om meerdere kolommen sequentieel te sorteren:

```java
table.setMultiSorting(true);
```

Met ingeschakelde multi-sorting sorteert het klikken op meerdere kolomkoppen ze sequentieel. De sorteervolgorde worden visueel aangeduid in de gebruikersinterface van de tabel.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Je kunt ook de sorteervolgorde programmatically definiëren voor server-side sortering. Gebruik `setSortOrder()` op de kolommen die je wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sorteervolgorde
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Kolomvolgorde is Belangrijk
Tenzij `setSortOrder()` wordt gebruikt, sorteert de tabel standaard op basis van de volgorde waarin kolommen zijn gedeclareerd.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sorteerrichting {#sort-direction}

Er zijn drie beschikbare instellingen voor de richting waarin een kolom kan worden gesorteerd:

- `SortDirection.ASC`: Sorteert de kolom in oplopende volgorde.
- `SortDirection.DESC`: Sorteert de kolom in aflopende volgorde.
- `SortDirection.NONE`: Geen sortering toegepast op de kolom.

Wanneer een kolom sorteren heeft ingeschakeld, zie je een reeks verticale pijlindicatoren verschijnen bovenaan de betreffende kolom. Deze pijlen stellen de gebruiker in staat om tussen de verschillende sorteerrichtingen te toggelen.

Wanneer oplopende volgorde is geselecteerd, wordt een `^` weergegeven, terwijl aflopende volgorde een `v` zal tonen.


## Client vs. server-side sortering {#client-vs-server-side-sorting}

Sorteren van gegevens kan grofweg worden gecategoriseerd in twee hoofdbenaderingen: **Client Sortering** en **Server Sortering**.

### Client sortering {#client-sorting}

Client sortering houdt in dat gegevens direct binnen de gebruikersinterface van de clienttoepassing worden gerangschikt en weergegeven. Het is de sortering waarmee gebruikers interactie hebben wanneer ze op kolomkoppen klikken, wat de visuele weergave van gegevens op het scherm beïnvloedt.

De ontwikkelaar heeft geen directe controle over client-side sortering, maar deze wordt bepaald door het kolomtype dat in Java is opgegeven. De volgende types worden momenteel ondersteund:

- TEKST
- NUMMER
- BOOLEAN
- DATUM
- DATETIME
- TIJD

:::info
Client sortering werkt niet wanneer slechts een deel van de gegevens beschikbaar is in de client.
:::

### Server sortering {#server-sorting}

In tegenstelling tot client-side sortering, houdt server sortering in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze benadering is bijzonder gunstig bij grote datasets die mogelijk onpraktisch zijn om volledig naar de client te verzenden.

Ontwikkelaars hebben meer controle over de logica van server sortering. Dit maakt de implementatie van complexe sorteeralgoritmen en optimalisaties mogelijk, waardoor het geschikt is voor scenario's met uitgebreide gegevens. Dit zorgt ervoor dat de client vooraf gesorteerde gegevens ontvangt, waardoor de noodzaak voor uitgebreide client-side verwerking wordt geminimaliseerd.

:::info
Server sortering is een prestatiegerichte strategie voor het omgaan met datasets die de mogelijkheden van efficiënte client-side verwerking overschrijden, en is de standaardmethode die door de `Table` wordt gebruikt.
:::

#### Comparators {#comparators}

De `Column` component stelt ontwikkelaars in staat om Java `Comparators` te gebruiken voor dynamische en aangepaste sortering. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om aan te passen hoe gegevens worden gesorteerd, waardoor er meer controle is over het standaard sorteergedrag op basis van natuurlijke ordening.

Om `Comparator` sortering in een `Column` te benutten, kun je de `setComparator()`-methode gebruiken. Deze methode stelt je in staat een aangepaste `Comparator`-functie te definiëren die de sorteerlomogram bepaalt.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

In het bovenstaande voorbeeld is er een specifieke aangepaste comparatorfunctie die twee elementen (a en b) neemt, en de sorteervolgorde definieert op basis van de geparseerde gehele waarden van de `Nummer`-attribuut.

Het gebruik van Comparators voor kolomsortering is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Table` server side sortering en sorteert niet-primitive waarden met behulp van de `toString()`-methode van Object, waarbij ze worden geconverteerd naar hun tekenreekswaarden en vervolgens worden gesorteerd.
:::
