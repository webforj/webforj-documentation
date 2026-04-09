---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 99281603bebefd43f033e9d0c958c366
---
Sorteren stelt gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is nuttig wanneer gebruikers snel de hoogste of laagste waarden in een bepaalde kolom moeten vinden.

:::tip Beheren en opvragen van gegevens
Voor informatie over het gebruik van het `Repository`-patroon om collecties te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standaard is een kolom niet sorteerbaar, tenzij dit expliciet is ingeschakeld. Om sorteren op een specifieke kolom toe te staan, gebruik de `setSortable(true)`-methode:

```java 
table.getColumn("Leeftijd").setSortable(true);
```

## Meerdere sorteringen {#multi-sorting}

:::warning Meerdere kolomsorteringen zijn standaard uitgeschakeld in webforJ `25.00`
Voor webforj `25.00` ondersteunden tabellen standaard meerdere kolomsorteringen. Vanaf versie `25.00` is dit gedrag veranderd; ontwikkelaars moeten nu expliciet meerdere kolomsorteringen inschakelen.
:::

Als meerdere sorteringen nodig zijn, moet `setMultiSorting(true)` op de tabel worden toegepast. Dit stelt gebruikers in staat om meerdere kolommen in volgorde te sorteren:

```java
table.setMultiSorting(true);
```

Met ingeschakelde meerdere sorteringen zal het klikken op meerdere kolomkoppen ze opeenvolgend sorteren. De sorteerprioriteit wordt visueel aangegeven in de tabel-UI.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

U kunt ook de sorteerprioriteit programmatisch definiëren voor server-side sorteren. Gebruik `setSortOrder()` op de kolommen die u wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sorteer volgorde
naamKolom.setSortOrder(1);
leeftijdKolom.setSortOrder(2);
```

:::info Kolomvolgorde is belangrijk
Tenzij `setSortOrder()` wordt gebruikt, sorteert de tabel standaard op de volgorde waarin kolommen zijn gedeclareerd.
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
- `SortDirection.NONE`: Geen sortering op de kolom toegepast.

Wanneer een kolom sorteren is ingeschakeld, ziet u een set verticale pijlindicatoren aan de bovenkant van de kolom. Deze pijlen stellen de gebruiker in staat om te schakelen tussen de verschillende sorteerrichtingen.

Wanneer oplopende volgorde is geselecteerd, wordt een `^` weergegeven, terwijl aflopende volgorde een `v` weergeeft.


## Client versus server-side sorteren {#client-vs-server-side-sorting}

Het sorteren van gegevens kan grofweg worden onderverdeeld in twee hoofdaanpakken: **Client Sorteren** en **Server Sorteren**.

### Client sorteren {#client-sorting}

Client sorteren houdt in dat gegevens direct binnen de gebruikersinterface van de clientapplicatie worden gerangschikt en weergegeven. Het is het sorteren waarmee gebruikers interactie hebben wanneer ze op kolomkoppen klikken, wat de visuele weergave van gegevens op het scherm beïnvloedt.

De ontwikkelaar heeft geen directe controle over client-side sorteren, maar wordt bepaald door het kolomtype dat in Java is opgegeven. De volgende types worden momenteel ondersteund:

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

In tegenstelling tot client-side sorteren, houdt server sorteren in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze aanpak is bijzonder voordelig wanneer met grote datasets wordt gewerkt die niet praktisch helemaal naar de client kunnen worden overgedragen.

Ontwikkelaars hebben meer controle over de logica van server sorteren. Dit maakt de implementatie van complexe sorteeralgoritmen en optimalisaties mogelijk, wat het geschikt maakt voor scenario's met uitgebreide gegevens. Dit zorgt ervoor dat de client vooraf gesorteerde gegevens ontvangt, waardoor de behoefte aan extensieve client-side verwerking wordt geminimaliseerd.


:::info
Server sorteren is een prestatiegerichte strategie voor het omgaan met datasets die de mogelijkheden voor efficiënte client-side verwerking overschrijden, en is de standaardmethode die door de `Table` wordt gebruikt.
:::

### Kolom eigenschapsnaam {#column-property-name}

Standaard gebruikt de `Table` de ID van een kolom als de eigenschapsnaam bij het opbouwen van sorteercriteria voor een backend-repository. Wanneer de weergave-ID van een kolom niet overeenkomt met de onderliggende data-eigenschap, of wanneer de kolom een berekende waarde weergeeft, gebruik dan `setPropertyName()` om expliciet aan de `Table` aan te geven welke eigenschap moet worden gesorteerd.

```java
// Kolom-ID is "Volledige Naam", maar de backend-eigenschap is "fullName"
table.addColumn("Volledige Naam", Persoon::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

De eigenschapsnaam wordt doorgestuurd naar de `OrderCriteria` wanneer een sorteergebeurtenis wordt geactiveerd, zodat backend-repositories zoals Spring Data JPA of REST-adapters de juiste `ORDER BY`-clausule kunnen opbouwen.

:::warning
Zonder `setPropertyName()` valt de `Table` terug op de kolom-ID. Als dit geen geldige backend-eigenschap is, zal de sortering stilzwijgend falen of onjuist geordende gegevens retourneren.
:::

Geneste eigenschapsroutes worden ook ondersteund met behulp van puntnotatie:

```java
table.addColumn("Stad", Persoon::getCity)
     .setSortable(true)
     .setPropertyName("adres.stad");
```

#### Comparators {#comparators}

De `Column`-component stelt ontwikkelaars in staat om Java `Comparators` voor dynamisch en aangepast sorteren te gebruiken. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om de manier waarop gegevens worden gesorteerd aan te passen, en biedt een hogere controle over het standaard sorteergedrag op basis van natuurlijke volgorde.

Om `Comparator`-sorteren in een `Column` te benutten, kunt u de `setComparator()`-methode gebruiken. Deze methode stelt u in staat om een aangepaste `Comparator`-functie te definiëren die de sorteerl ogica bepaalt.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

In het bovenstaande voorbeeld is er een specifieke aangepaste comparatorfunctie gedefinieerd die twee elementen (a en b) neemt, en de sorteervolgorde definieert op basis van de geparsed gehele waarden van de `Number`-attribuut.

Het gebruik van Comparators voor kolomsorteren is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor het implementeren van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Table` server-side sorteren en sorteert niet-primitieve waarden met behulp van de `toString()`-methode van Object, waardoor ze naar hun stringwaarden worden geconverteerd en vervolgens worden gesorteerd.
:::
