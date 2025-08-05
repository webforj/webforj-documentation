---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: b3545c590336bb6574bf196fbd417349
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table`-klasse maakt gebruik van `Column`-klassen voor het creëren van datacolommen erin. Deze klasse heeft een breed scala aan functionaliteit waarmee een gebruiker grondig kan aanpassen wat er binnen elke kolom wordt weergegeven. 
Om een `Column` aan een `Table` toe te voegen, gebruik een van de `addColumn` fabrieksmethoden.

## Waardeproviders {#value-providers}

Een waardeprovider is een functie die verantwoordelijk is voor het omzetten van ruwe gegevens uit de onderliggende dataset in een formaat dat geschikt is voor weergave binnen een specifieke kolom. De functie, gedefinieerd door de gebruiker, neemt een instantie van het rijgegevens type (T) en retourneert de waarde die moet worden weergegeven in de bijbehorende kolom voor die specifieke rij.

Om een waardeprovider op een kolom in te stellen, gebruik een van de `addColumn` fabrieksmethoden die een provider als argument accepteren:

```java
    List<String> columnsList = Arrays.asList("atleet", "leeftijd", "land", "jaar", "sport", "goud", "zilver", "brons", "totaal");

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

In dit voorbeeld zal een kolom proberen gegevens te benaderen vanuit een JSON-object, het zal deze alleen weergeven als de gegevens niet null zijn.

## Pinrichting {#pin-direction}

Kolom vastzetten is een functie die gebruikers in staat stelt om een kolom aan een specifieke zijde van de tabel te bevestigen of "vast te zetten", waardoor de zichtbaarheid en toegankelijkheid wordt verbeterd. Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven tijdens het horizontaal scrollen door een tabel.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Er zijn drie beschikbare richtingen voor het vastzetten van een kolom:

- `PinDirection.LEFT`: Verstevigt de kolom aan de linkerkant.
- `PinDirection.RIGHT`: Verstevigt de kolom aan de rechterkant.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoer volgorde.

Vastzetten kan programmaatisch worden ingesteld, waardoor gebruikers de pinrichting kunnen wijzigen op basis van gebruikersinteracties of applicatielogica.

## Uitlijning {#alignment}

Kolomuitlijning definieert de horizontale positionering van gegevens binnen een kolom. Het beïnvloedt hoe datavalue worden weergegeven, door een visuele gids te bieden aan gebruikers over de aard van de informatie.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

De Tabelcomponent ondersteunt drie primaire uitlijningsmogelijkheden:

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waarbij het handhaven van een naar links gerichte stroom intuïtief is. Nuttig wanneer de nadruk ligt op het beginpunt van de inhoud.
- `Column.Alignment.CENTER`: Ideaal voor numerieke of categorische gegevens waarbij een evenwichtige presentatie gewenst is. Creëert een visueel gecentreerde weergave.
- `Column.Alignment.RIGHT`: Veelvuldig gebruikt voor numerieke gegevens, vooral wanneer de grootte of precisie van cijfers significant is. Lijnt gegevens naar rechts voor een natuurlijke leesstroom.

In het bovenstaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd om een ​​duidelijkere visuele onderscheiding te bieden.

## Zichtbaarheid {#visibility}

Het is mogelijk om de zichtbaarheid van een `Column` in te stellen, waarmee wordt bepaald of deze al dan niet binnen de tabel wordt getoond. Dit kan nuttig zijn bij het bepalen of gevoelige informatie moet worden weergegeven.

Gebruik de `setHidden()`-methode, zoals hieronder weergegeven, om deze eigenschap in te stellen op een `Column`:

`table.addColumn("Creditcard", Customer::getCreditCardNumber).setHidden(true);`

## Navigeerbaar {#navigable}

De navigeerbare eigenschap bepaalt of gebruikers kunnen interageren met een kolom tijdens navigatie. Het instellen van `setSuppressNavigable()` op true beperkt de gebruikersinteractie met de kolom, waardoor een alleen-lezen ervaring wordt geboden.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Label {#label}

Het label van een kolom is de openbaar zichtbare identificator, die bijdraagt aan de duidelijkheid en het begrip van weergegeven gegevens. Gebruik setLabel om het label dat aan een kolom is gekoppeld in te stellen of te wijzigen.

:::tip
Standaard zal het label hetzelfde zijn als de kolom-ID
:::

```java
table.addColumn("Product-ID", Product::getProductId).setLabel("ID");
```

## Minimale breedte {#minimum-width}

De `setMinWidth()`-methode stelt je in staat om de minimale breedte van een kolom te definiëren, waardoor een consistente en esthetisch aantrekkelijke lay-out wordt gegarandeerd.

Als de minimale breedte niet wordt opgegeven, zal de tabel de minimale breedte berekenen op basis van de kolominhoud.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100.0);
```

:::info
De waarde die wordt doorgegeven vertegenwoordigt de gewenste minimale breedte in pixels.  
:::
