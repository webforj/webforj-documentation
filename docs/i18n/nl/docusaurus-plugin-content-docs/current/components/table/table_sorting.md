---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 16c35fa416c4ebba3b680deb2d8925ef
---
Sorteerfuncties stellen gebruikers in staat om gegevens in kolommen op volgorde te rangschikken, waardoor informatie gemakkelijker te lezen en te analyseren is. Dit is nuttig wanneer gebruikers snel de hoogste of laagste waarden in een specifieke kolom moeten vinden.

:::tip Gegevens beheren en opvragen
Voor informatie over hoe het `Repository`-patroon kan worden gebruikt om verzamelingen te beheren en op te vragen, zie de [Repository-artikelen](/docs/advanced/repository/overview).
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

Standaard is een kolom niet sorteerbaar tenzij expliciet ingeschakeld. Om sorteren op een specifieke kolom toe te staan, gebruik je de methode `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Multi-sorting {#multi-sorting}

:::warning Multi-Kolom Sorteren Standaard Uitgeschakeld in webforJ `25.00`
Voor webforj `25.00` ondersteunden tabellen standaard multi-kolom sorteren. Met versie `25.00` is dit gedrag veranderd — ontwikkelaars moeten nu expliciet multi-kolom sorteren inschakelen.
:::

Als multi-sorting nodig is, moet `setMultiSorting(true)` op de tabel worden toegepast. Dit stelt gebruikers in staat om meerdere kolommen achtereenvolgens te sorteren:

```java
table.setMultiSorting(true);
```

Met ingeschakelde multi-sorting zal het klikken op meerdere kolomkoppen ze opeenvolgend sorteren. De sorteervolgorde wordt visueel aangegeven in de tabel-UI.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Je kunt ook de sorteervolgorde programmatisch definiëren voor server-side sorteren. Gebruik `setSortOrder()` op de kolommen die je wilt sorteren, in volgorde van prioriteit:

```java
// Server-side sorteervolgorde
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

## Sorteer richting {#sort-direction}

Er zijn drie beschikbare instellingen voor de richting waarin een kolom kan worden gesorteerd:

- `SortDirection.ASC`: Sorteert de kolom in oplopende volgorde.
- `SortDirection.DESC`: Sorteert de kolom in aflopende volgorde.
- `SortDirection.NONE`: Geen sortering toegepast op de kolom.

Wanneer een kolom sorteren is ingeschakeld, zie je een set verticale pijlindicatoren verschijnen aan de bovenkant van de kolom. Deze pijlen stellen de gebruiker in staat om tussen de verschillende sorteerrichtingen te schakelen.

Wanneer de oplopende volgorde is geselecteerd, zal een `^` worden weergegeven, terwijl de aflopende volgorde een `v` zal weergeven.

## Client vs. server-side sorteren {#client-vs-server-side-sorting}

Sorteerfuncties kunnen grofweg worden gecategoriseerd in twee hoofdbenaderingen: **Client Sorteren** en **Server Sorteren**.

### Client sorteren {#client-sorting}

Client sorteren houdt in dat gegevens rechtstreeks binnen de gebruikersinterface van de clienttoepassing worden gerangschikt en weergegeven. Het is de sortering waar gebruikers mee interactie hebben wanneer ze op kolomkoppen klikken, wat de visuele weergave van gegevens op het scherm beïnvloedt.

De ontwikkelaar heeft geen directe controle over client-side sorteren, maar dit wordt bepaald door het kolomtype dat in Java wordt geleverd. De volgende types worden momenteel ondersteund:

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

In tegenstelling tot client-side sorteren houdt server sorteren in dat gegevens op de server worden gerangschikt en georganiseerd voordat ze naar de client worden verzonden. Deze benadering is bijzonder voordelig bij het omgaan met grote datasets die misschien niet praktisch volledig naar de client kunnen worden overgebracht.

Ontwikkelaars hebben meer controle over de logica van server sorteren. Dit maakt de implementatie van complexe sorteeralgoritmen en optimalisaties mogelijk, waardoor het geschikt is voor scenario's met uitgebreide gegevens. Dit zorgt ervoor dat de client vooraf gesorteerde gegevens ontvangt, waardoor de behoefte aan uitgebreide client-side verwerking wordt geminimaliseerd.

:::info
Server sorteren is een prestatiegerichte strategie voor het omgaan met datasets die de mogelijkheden van efficiënte client-side verwerking overschrijden, en is de standaardmethode die door de `Table` wordt gebruikt.
:::

### Kolom eigenschapsnaam {#column-property-name}

Standaard gebruikt de `Table` de ID van een kolom als de eigenschapsnaam bij het opbouwen van sorteercriteria voor een backend-repository. Wanneer de weergegeven ID van een kolom niet overeenkomt met de onderliggende gegevensproperty, of wanneer de kolom een berekende waarde weergeeft, gebruik `setPropertyName()` om expliciet aan de `Table` te vertellen welke eigenschap gesorteerd moet worden.

```java
// Kolom ID is "Volledige Naam", maar de backend-eigenschap is "fullName"
table.addColumn("Volledige Naam", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

De eigenschapsnaam wordt doorgestuurd naar de `OrderCriteria` wanneer een sorteerevenement wordt geactiveerd, waardoor backend-repository's zoals Spring Data JPA of REST-adapters de juiste `ORDER BY`-clausule kunnen opbouwen.

:::warning
Zonder `setPropertyName()` valt de `Table` terug op de kolom-ID. Als dit niet overeenkomt met een geldige backend-eigenschap, kan de sortering stilletjes falen of onjuist geordende gegevens retourneren.
:::

Geneste eigenschapsnamen worden ook ondersteund met behulp van puntnotatie:

```java
table.addColumn("Stad", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vergelijkers {#comparators}

De `Column`-component stelt ontwikkelaars in staat om Java `Comparators` te gebruiken voor dynamische en aangepaste sortering. Een `Comparator` is een mechanisme dat wordt gebruikt om twee objecten van dezelfde klasse te ordenen, zelfs als die klasse door de gebruiker is gedefinieerd. Deze functionaliteit biedt ontwikkelaars de flexibiliteit om te customizing hoe gegevens worden gesorteerd, waardoor meer controle wordt verkregen over de standaard sorteergedrag op basis van natuurlijke volgorde.

Om `Comparator`- sorteren in een `Column` te benutten, kun je de methode `setComparator()` gebruiken. Deze methode stelt je in staat om een aangepaste `Comparator`-functie te definiëren die de sorteervolgorde bepaalt.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

In het bovenstaande voorbeeld is er een aangepaste comparatorfunctie die twee elementen (a en b) specificeert en de sorteervolgorde definieert op basis van de geparseerde gehele waarden van de `Number`-attribuut.

Het gebruik van vergelijkers voor kolomsortering is bijzonder nuttig bij het omgaan met niet-numerieke waarden. Ze zijn ook nuttig voor de implementatie van complexe sorteeralgoritmen.

:::info
Standaard gebruikt de `Table` server-side sorteren, en sorteert niet-primitieve waarden met de `toString()`-methode van Object, waarbij ze worden omgezet in hun stringwaarden en vervolgens worden gesorteerd.
:::
