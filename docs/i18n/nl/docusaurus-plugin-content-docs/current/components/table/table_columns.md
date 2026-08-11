---
sidebar_position: 5
title: Columns
slug: columns
description: >-
  Define Table columns with labels, value providers, visibility, navigability,
  sizing, and user-interaction events.
_i18n_hash: 5ca9d9959c258db42780e52dad8c463d
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table` klasse gebruikt kolominstanties om te definiëren en te personaliseren hoe gegevens worden weergegeven. Kolommen regelen welke gegevens worden getoond, hoe ze eruitzien en hoe gebruikers ermee kunnen interageren. Deze pagina behandelt kolomidentiteit, presentatie, grootte, gebruikersinteracties en gerelateerde gebeurtenissen.

## Kolomidentiteit {#column-identity}

De identiteit van een kolom definieert hoe deze wordt herkend in de `Table`. Dit omvat het label, de waarde die het biedt, en of het zichtbaar of navigeerbaar is.

### Label {#label}

Het label van een kolom is de publiek zichtbare identificator, die helpt om de weergegeven gegevens te verduidelijken.

Gebruik `setLabel()` om het label in te stellen of te wijzigen.

:::tip
Standaard zal het label hetzelfde zijn als de kolom-ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Waardeproviders {#value-providers}

Een waardeprovider is een functie die verantwoordelijk is voor het vertalen van rauwe gegevens uit de onderliggende dataset naar een formaat dat geschikt is voor weergave binnen een specifieke kolom. De functie, die jij definieert, neemt een instantie van het rijgegevens type (T) en retourneert de waarde die moet worden getoond in de bijbehorende kolom voor die specifieke rij.

Om een waardeprovider op een kolom in te stellen, gebruik je een van de `addColumn()`-methoden van de `Table`-component.

In de volgende code zal een kolom proberen gegevens te benaderen uit een JSON-object, en deze alleen weergeven als de gegevens niet null zijn.

```java
List<String> columnsList = List.of("atleet", "leeftijd", "land", "jaar", "sport", "goud", "zilver", "brons", "totaal");
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

### Zichtbaarheid {#visibility}

Het is mogelijk om de zichtbaarheid van een kolom in te stellen, wat bepaalt of deze al dan niet wordt weergegeven binnen de `Table`. Dit kan nuttig zijn bij het bepalen of gevoelige informatie dient te worden weergegeven.

```java
table.addColumn("Creditcard", Customer::getCreditCardNumber).setHidden(true);
```

### Navigeerbaar {#navigable}

Het navigeerbare attribuut bepaalt of gebruikers met een kolom kunnen interageren tijdens de navigatie. Het instellen van `setSuppressNavigable()` op true beperkt de interactie van de gebruiker met de kolom, waardoor een alleen-lezen ervaring wordt geboden.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Lay-out en opmaak {#layout-and-formatting}

Nadat de identiteit van een kolom is vastgesteld, is de volgende stap om te regelen hoe de inhoud eruitziet voor gebruikers. Lay-outopties zoals uitlijning en vastzetten bepalen waar gegevens zich bevinden en hoe ze zichtbaar blijven terwijl je met een `Table` werkt.

### Uitlijning {#alignment}

Het instellen van de uitlijning van een kolom stelt je in staat om georganiseerde tabellen te maken, wat gebruikers kan helpen de verschillende secties in de `Table` te identificeren.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnalignment'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

De `Table` Component ondersteunt drie uitlijningsopties:

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waarbij een linkse flow intuïtief is. Nuttig wanneer je het beginpunt van de inhoud wilt benadrukken.
- `Column.Alignment.CENTER`: Centrisch uitgelijnde kolommen zijn ideaal voor kortere waarden, zoals een karakter sleutel, status, of iets anders dat een gebalanceerde presentatie heeft.
- `Column.Alignment.RIGHT`: Overweeg het gebruik van een rechts-uitgelijnde kolom voor numerieke waarden die handig zijn om snel doorheen te scannen, zoals data, bedragen en percentages.

In het bovenstaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd om een duidelijkere visuele differentiatie te bieden.

### Vastzetten {#pinning}

Het vastzetten van kolommen is een functie waarmee gebruikers een kolom aan een specifieke zijde van de `Table` kunnen bevestigen of "vastzetten". Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl je horizontaal door een tabel scrolt.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnpinning'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Er zijn drie beschikbare richtingen voor het vastzetten van een kolom:

- `PinDirection.LEFT`: Zet de kolom aan de linkerkant vast.
- `PinDirection.RIGHT`: Zet de kolom aan de rechterkant vast.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoegorde.

Vastzetten kan programmatisch worden ingesteld, zodat je de vastzetrichting kunt wijzigen op basis van gebruikersinteracties of door de logica van de app.

## Kolomgrootte <DocChip chip='since' label='25.03' /> {#column-sizing}

### Vaste breedte {#fixed-width}

Stel een exacte breedte in voor een kolom met behulp van de `setWidth()`-methode, waarbij je de gewenste breedte in pixels opgeeft:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

De breedte-eigenschap definieert de gewenste initiële breedte voor de kolom. Hoe deze breedte wordt gebruikt, hangt af van andere eigenschappen en kolomtype:

- **Reguliere kolommen**: Alleen met lengte ingesteld, wordt de kolom weergegeven op de opgegeven breedte maar kan deze proportioneel krimpen wanneer de container te klein is. De oorspronkelijke breedte dient als de gewenste breedte, maar zonder expliciete minimumbeperkingen kan de kolom kleiner worden weergegeven dan de ingestelde breedte.
- [**Vastgezette kolommen**](#pinning): Handhaven altijd hun exacte breedte, deelnemen nooit aan responsief krimpen.
- [**Flex-kolommen**](#flex-sizing): Het instellen van een breedte is niet compatibel met flex. Gebruik ofwel breedte (vast) of flex (proportioneel), niet beide.

Als niet gespecificeerd, gebruikt de kolom de geschatte breedte op basis van de inhoudsanalyse van de eerste paar rijen.

```java
// Huidige breedte verkrijgen
float currentWidth = column.getWidth();
```

### Minimale breedte {#minimum-width}

De `setMinWidth()`-methode stelt je in staat om de minimale breedte van een kolom te definiëren. Als de minimale breedte niet wordt opgegeven, berekent de `Table` de minimale breedte op basis van de kolominhoud.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100f);
```

De waarde die wordt doorgegeven vertegenwoordigt de minimale breedte in pixels.

De minimale breedte-eigenschap controleert de kleinste breedte die een kolom kan hebben:

- **Reguliere kolommen**: Alleen met de minimale breedte ingesteld, gebruikt de kolom de minimale breedte als zowel gewenste als minimale breedte. Met breedte + minimale breedte kan de kolom krimpen van de breedte naar de minimale breedte maar niet verder.
- [**Vastgezette kolommen**](#pinning): Als alleen de minimale breedte is ingesteld (geen breedte), wordt het de vaste breedte.
- [**Flex-kolommen**](#flex-sizing): Voorkomt dat de kolom hieronder krimpt, zelfs wanneer de ruimte in de container beperkt is.

```java
// Huidige minimale breedte verkrijgen
float minWidth = column.getMinWidth();
```

### Maximale breedte {#maximum-width}

De `setMaxWidth()`-methode beperkt hoe breed een kolom kan worden, zodat kolommen met lange inhoud niet te breed worden en de leesbaarheid beïnvloeden:

```java
table.addColumn("Beschrijving", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

De `maxWidth`-eigenschap beperkt de groei van kolommen voor alle kolomtypen en zal nooit worden overschreden ongeacht de inhoud, containeromvang of flex-instellingen.

```java
// Huidige maximale breedte verkrijgen
float maxWidth = column.getMaxWidth();
```

### Flex-grootte {#flex-sizing}

De `setFlex()`-methode maakt proportionele kolomgrootte mogelijk, waardoor kolommen beschikbare ruimte delen nadat kolommen met een vaste breedte zijn toegewezen:

```java
// Titelkolom krijgt twee keer de ruimte van de Artiste kolom
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Artiest", Product::getArtist).setFlex(1f);
```

Belangrijke flex-gedragingen:

- **Flex-waarde**: Bepaalt de proportie van beschikbare ruimte. Een kolom met flex=2 krijgt twee keer de ruimte van een kolom met flex=1.
- **Incompatibel met breedte**: Kan niet samen met de breedte-eigenschap worden gebruikt. Wanneer flex groter is dan nul, heeft het effect boven de breedte-instelling.
- **Respecteert beperkingen**: Werkt samen met minimale breedte/maximale breedte beperkingen. Zonder minimale breedte kunnen flex-kolommen krimpen tot 0.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnflexsizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

:::info Breedte vs Flex
Breedte- en flex-eigenschappen zijn wederzijds exclusief. Het instellen van de een wist automatisch de ander. Gebruik breedte voor nauwkeurige controle of flex voor responsief gedrag.
:::

### Automatische grootte {#automatic-sizing}

Naast handmatige breedte- en flex-instellingen kunnen kolommen ook automatisch worden geschaald. Automatische sizing laat de `Table` de optimale breedtes bepalen door ofwel de inhoud te analyseren of door de ruimte proportioneel te verdelen.

#### Inhoud-gebaseerde automatische sizing {#content-based-auto-sizing}

Automatisch de grootte van kolommen op basis van hun inhoud. De `Table` analyseert de gegevens in elke kolom en berekent de optimale breedte om de inhoud zonder afsnijding weer te geven.

```java
// Auto-size alle kolommen om inhoud te passen
table.setColumnsToAutoSize().thenAccept(c -> {
  // Sizing compleet - kolommen passen nu bij hun inhoud
});

// Auto-size specifieke kolom
table.setColumnToAutoSize("beschrijving");
```

#### Proportioneel auto-fit {#proportional-auto-fit}

Verdeel alle kolommen proportioneel over de beschikbare `Table`-breedte. Deze bewerking stelt elke kolom in op flex=1, waardoor ze de totale `Table`-breedte gelijk delen, ongeacht de lengte van hun inhoud. Kolommen zullen uitzetten of krimpen om de exacte `Table`-afmetingen in te vullen zonder overblijvende ruimte.

```java
// Pas kolommen aan de breedte van de tabel (equivalent aan het instellen van flex=1 op alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Alle kolommen delen nu de ruimte gelijkmatig
});
```

:::info Asynchrone Operaties
Auto-sizing methoden retourneren `PendingResult<Void>` omdat ze client-side berekeningen vereisen. Gebruik `thenAccept()` om code uit te voeren nadat de sizing is voltooid. Als je niet hoeft te wachten op voltooiing, kun je de methoden zonder `thenAccept()` aanroepen.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnautosizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

## Gebruikersinteracties <DocChip chip='since' label='25.03' /> {#user-interactions}

### Kolomresize {#column-resizing}

Kolomresize geeft gebruikers controle over hoeveel ruimte elke kolom in beslag neemt door de kolomgrenzen te slepen.

Je kunt het resize-gedrag op individuele kolommen regelen bij het bouwen van je tabel:

```java
// Sta gebruikers toe deze kolom te resizen
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Uitschakelen van resizing
table.addColumn("ID", Product::getId).setResizable(false);

// Huidige staat controleren
boolean canResize = column.isResizable();
```

Voor tabellen waarbij je consistente gedrag wilt hebben bij meerdere kolommen, gebruik de bulkconfiguratiemethoden:

```java
// Maak alle bestaande kolommen resizable
table.setColumnsToResizable(true);

// Vergrendel alle bestaande kolommen van resizing
table.setColumnsToResizable(false);
```

### Kolomherordening {#column-reordering}

Kolomherordening stelt gebruikers in staat om kolommen te slepen en neer te zetten in hun voorkeursvolgorde, waardoor ze de lay-out van de `Table` kunnen personaliseren voor hun workflow.

Configureer de bewegingstoestemmingen wanneer je je tabel instelt:

```java
// Sta gebruikers toe deze kolom te verplaatsen
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Voorkom kolombeweging (nuttig voor ID- of actiekolommen)
table.addColumn("ID", Product::getId).setMovable(false);

// Huidige staat controleren
boolean canMove = column.isMovable();
```

Pas de bewegingsinstellingen gelijktijdig toe op meerdere kolommen:

```java
// Sta herordening toe voor alle bestaande kolommen
table.setColumnsToMovable(true);

// Voorkom herordening voor alle bestaande kolommen
table.setColumnsToMovable(false);
```

:::note Bulk Operaties
De `setColumnsToResizable()` en `setColumnsToMovable()`-methoden beïnvloeden alleen bestaande kolommen op het moment van aanroepen. Ze stellen geen standaardwaarden in voor toekomstige kolommen.
:::

### Programmatische kolomverplaatsing {#programmatic-column-movement}

Naast slepen en neerzetten, kun je ook kolommen programmatisch verplaatsen op basis van index of ID. Houd er rekening mee dat de index alleen is gebaseerd op zichtbare kolommen; verborgen kolommen worden genegeerd bij het berekenen van posities.

```java
// Verplaats kolom naar de eerste positie
table.moveColumn("titel", 0);

// Verplaats kolom naar de laatste positie
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone verplaatsing met callback
table.moveColumn("beschrijving", 2).thenAccept(c -> {
  // Kolom met succes verplaatst
});
```

## Gebeurtenisafhandeling {#event-handling}

De `Table` component genereert evenementen wanneer gebruikers met kolommen interageren, waardoor je kunt reageren op lay-outwijzigingen en voorkeuren van gebruikers opslaan.

Ondersteunde evenementen:

- `TableColumnResizeEvent`: Vuur wanneer een gebruiker een kolom resize door de rand ervan te slepen.
- `TableColumnMoveEvent`: Vuur wanneer een gebruiker een kolom herschikt door de kop ervan te slepen.

Je kunt luisteraars aan de `Table` bevestigen om te reageren wanneer gebruikers de tabelindeling wijzigen.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Behandel kolom resize evenement
  // Toegang: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Behandel kolom verplaatsing evenement
  // Toegang: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
