---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 5ebe30c35548db6d3b603b8a72ed4c2a
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table`-klasse maakt gebruik van `Column`-klassen om de creatie van datacolumns binnenin te beheren. Deze klasse heeft een breed scala aan functionaliteit waarmee een gebruiker kan aanpassen wat er binnen elke van de kolommen wordt weergegeven.  
Om een `Column` aan een `Table` toe te voegen, gebruik een van de `addColumn`-fabriekmethoden.

## Waarde leveranciers {#value-providers}

Een waarde leverancier is een functie die verantwoordelijk is voor het vertalen van ruwe gegevens uit de onderliggende dataset naar een formaat dat geschikt is voor weergave binnen een specifieke kolom. De functie, gedefinieerd door de gebruiker, neemt een instantie van het rijgegevenstype (T) en retourneert de waarde die in de bijbehorende kolom voor die specifieke rij moet worden weergegeven.

Om een waarde leverancier op een kolom in te stellen, gebruik een van de `addColumn`-fabriekmethoden die een leverancier als argument accepteren:

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

In dit voorbeeld zal een kolom proberen gegevens uit een JSON-object te verkrijgen en deze alleen weergeven als de gegevens niet null zijn.

## Pinrichting {#pin-direction}

Kolompinnen is een functie die gebruikers toestaat een kolom aan een specifieke zijde van de tabel vast te zetten of "te pinnen", waardoor de zichtbaarheid en toegankelijkheid wordt verbeterd. Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl er horizontaal door een tabel wordt gescrold.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Er zijn drie beschikbare richtingen voor het pinnen van een kolom:

- `PinDirection.LEFT`: Zet de kolom aan de linkerkant vast.
- `PinDirection.RIGHT`: Zet de kolom aan de rechterkant vast.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoegvolgorde.

Pinnen kan programmatic worden ingesteld, zodat gebruikers de pinrichting kunnen wijzigen op basis van gebruikersinteracties of applicatielogica.

## Uitlijning {#alignment}

Kolomuitleg bepaalt de horizontale positionering van gegevens binnen een kolom. Het beïnvloedt hoe datavelden worden weergegeven en biedt een visuele gids aan gebruikers over de aard van de informatie.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

De Table-component ondersteunt drie primaire uitlijningsopties:

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waarbij een linkse stroom intuïtief is. Nuttig bij het benadrukken van het beginpunt van de inhoud.
- `Column.Alignment.CENTER`: Ideaal voor numerieke of categorische gegevens waar een evenwichtige presentatie gewenst is. Creëert een visueel gecentreerde weergave.
- `Column.Alignment.RIGHT`: Vaak gebruikt voor numerieke gegevens, vooral wanneer de grootorde of precisie van cijfers significant is. Lijnt gegevens naar rechts voor een natuurlijke leesstroom.

In het bovenstaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd om een meer duidelijke visuele onderscheid te bieden.

## Zichtbaarheid {#visibility}

Het is mogelijk om de zichtbaarheid van een `Column` in te stellen, waarbij wordt bepaald of deze al dan niet in de tabel wordt weergegeven. Dit kan nuttig zijn om te bepalen of gevoelige informatie al dan niet moet worden weergegeven.

Gebruik de `setHidden()`-methode, zoals hieronder weergegeven, om deze eigenschap op een `Column` in te stellen:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navigeerbaar {#navigable}

De navigeerbare attributen bepalen of gebruikers interaction kunnen hebben met een kolom tijdens navigatie. Door `setSuppressNavigable()` op true in te stellen, wordt de interactie van de gebruiker met de kolom beperkt, waardoor een alleen-lezen ervaring wordt geboden.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Label {#label}

Het label van een kolom is de openbare identifier, die bijdraagt aan de helderheid en het begrip van weergegeven gegevens. Gebruik setLabel om het label dat aan een kolom is gekoppeld in te stellen of te wijzigen.

:::tip  
Standaard zal het label hetzelfde zijn als de kolom-ID  
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## Minimale breedte {#minimum-width}

De `setMinWidth()`-methode stelt je in staat om de minimale breedte van een kolom te definiëren, waardoor een consistente en esthetisch aantrekkelijke lay-out wordt gegarandeerd.

Als de minimale breedte niet wordt opgegeven, zal de tabel de minimale breedte berekenen op basis van de inhoud van de kolom.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100.0);
```

:::info  
De waarde die wordt doorgegeven, vertegenwoordigt de gewenste minimale breedte in pixels.  
:::
