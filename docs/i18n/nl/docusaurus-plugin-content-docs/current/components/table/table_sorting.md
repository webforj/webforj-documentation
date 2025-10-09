---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 3c9156ad5da204816bd4ce783003cbf7
---
Sorteren stelt gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is nuttig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom moeten vinden.

:::tip Beheren en opvragen van gegevens
Voor informatie over hoe u het `Repository`-patroon kunt gebruiken om collecties te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standaard is een kolom niet sorteerbaar, tenzij dit expliciet is ingeschakeld. Om sorteren op een specifieke kolom toe te staan, gebruikt u de `setSortable(true)`-methode:

```java 
table.getColumn("Leeftijd").setSortable(true);
```

## Multi-sorting {#multi-sorting}

:::warning Multi-Kolom Sorteren Standaard Uitgeschakeld in webforJ `25.00`
Voor webforj `25.00` ondersteunden tabellen standaard multi-kolom sorteren. Vanaf versie `25.00` is dit gedrag veranderd - ontwikkelaars moeten nu expliciet multi-kolom sorteren inschakelen.
:::

Als multi-sorting nodig is, moet `setMultiSorting(true)` op de tabel worden toegepast. Dit stelt gebruikers in staat om meerdere kolommen achtereenvolgens te sorteren:

```java
table.setMultiSorting(true);
```

Met ingeschakelde multi-sorting, sorteert het klikken op meerdere kolomkoppen ze achtereenvolgens. De sorteerprioriteit wordt visueel aangegeven in de tabel UI.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

U kunt ook de sorteerprioriteit programmatically definiëren voor server-side sorteren. Gebruik `setSortOrder()` op de kolommen die u wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sorteerorde
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Volgorde van Kolommen
Tenzij `setSortOrder()` wordt gebruikt, sorteert de tabel standaard op basis van de volgorde waarin kolommen zijn gedeclareerd.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sorteer richting {#sort-direction}

Er zijn drie beschikbare instellingen voor de richting waarin een kolom gesorteerd kan worden:

- `SortDirection.ASC`: Sorteert de kolom in oplopende volgorde.
- `SortDirection.DESC`: Sorteert de kolom in aflopende volgorde.
- `SortDirection.NONE`: Geen sortering toegepast op de kolom.

Wanneer een kolom sorteren ingeschakeld heeft, ziet u een set verticale pijlindicatoren verschijnen bovenaan de desbetreffende kolom. Deze pijlen stellen de gebruiker in staat om te schakelen tussen de verschillende sorteerrichtingen.

Bij gekozen oplopende volgorde, zal een `^` worden weergegeven, terwijl aflopende volgorde een `v` zal weergeven.


## Client vs. server-side sorteren {#client-vs-server-side-sorting}

Sorteren van gegevens kan breed worden gecategoriseerd in twee hoofdanpakken: **Client Sorteren** en **Server Sorteren**.

### Client sorteren {#client-sorting}

Client sorteren houdt in dat gegevens direct binnen de gebruikersinterface van de clienttoepassing worden gerangschikt en weergegeven. Het is het sorteren waarmee gebruikers interactie hebben wanneer ze op kolomkoppen klikken, wat invloed heeft op de visuele weergave van gegevens op het scherm.

De ontwikkelaar heeft geen directe controle over client-side sorteren, maar dit wordt bepaald door het kolomtype dat in Java is opgegeven. De volgende typen worden momenteel ondersteund:

- TEKST
- GETAL
- BOOLEAN
- DATUM
- DATUMTIJD
- TIJD

:::info
Client sorteren werkt niet wanneer slechts een deel van de gegevens beschikbaar is in de client.
:::

### Server sorteren {#server-sorting}

In tegenstelling tot client-side sorteren, houdt server sorteren in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze aanpak is vooral voordelig bij het omgaan met grote gegevenssets die mogelijk niet praktisch volledig naar de client kunnen worden overgebracht.

Ontwikkelaars hebben meer controle over de logica van server sorteren. Dit maakt het mogelijk om complexe sorteeralgoritmen en optimalisaties te implementeren, wat het geschikt maakt voor scenario's met uitgebreide gegevens. Dit zorgt ervoor dat de client vooraf gesorteerde gegevens ontvangt, waardoor de noodzaak voor uitgebreide client-side verwerking tot een minimum wordt beperkt.


:::info
Server sorteren is een prestatiegerichte strategie voor het omgaan met datasets die de mogelijkheden van efficiënte client-side verwerking overschrijden, en is de standaard methode die door de `Table` wordt gebruikt.
:::

#### Vergelijkers {#comparators}

De `Column`-component stelt ontwikkelaars in staat om Java `Comparators` te gebruiken voor dynamisch en aangepast sorteren. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om aan te passen hoe gegevens worden gesorteerd, waardoor meer controle over het standaard sorteergedrag op basis van natuurlijke ordening mogelijk is.

Om `Comparator`-sorteren in een `Column` te benutten, kunt u de `setComparator()`-methode gebruiken. Deze methode stelt u in staat om een aangepaste `Comparator`-functie te definiëren die de sorteervolgorde dicteert.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

In het bovenstaande voorbeeld is er een aangepaste comparatorfunctie opgegeven die twee elementen (a en b) neemt en de sorteervolgorde definieert op basis van de geparseerde integerwaarden van het `Number`-attribuut.

Het gebruik van vergelijkers voor kolomsorteren is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Table` server-side sorteren, en sorteert niet-primitieve waarden met behulp van de `toString()`-methode van Object, waarbij ze worden omgezet naar hun tekenreekswaarden en vervolgens worden gesorteerd.
:::

