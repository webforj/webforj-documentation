---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: fbae9063370715e9f6dc2cb490a27511
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table` klasse gebruikt kolominstellingen om te definiëren en aan te passen hoe gegevens worden weergegeven. Kolommen bepalen welke gegevens worden weergegeven, hoe ze eruitzien en hoe gebruikers ermee kunnen interageren. Deze pagina behandelt kolomidentiteit, presentatie, dimensionering, gebruikersinteracties en gerelateerde gebeurtenissen.

## Kolomidentiteit {#column-identity}

De identiteit van een kolom definieert hoe deze wordt herkend in de `Table`. Dit omvat het label, de waarde die het biedt en of het zichtbaar of navigeerbaar is.

### Label {#label}

Het label van een kolom is de openbare identifier, die helpt om de weergegeven gegevens te verduidelijken.

Gebruik `setLabel()` om het label in te stellen of te wijzigen.

:::tip
Standaard is het label hetzelfde als de kolom-ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Waarde- verstrekkers {#value-providers}

Een waarde-verstrekker is een functie die verantwoordelijk is voor het vertalen van onbewerkte gegevens uit de onderliggende dataset naar een formaat dat geschikt is voor weergave binnen een specifieke kolom. De functie, die je definieert, neemt een instantie van het rijgegevenstype (T) en retourneert de waarde die in de bijbehorende kolom voor die specifieke rij moet worden getoond.

Om een waarde-verstrekker op een kolom in te stellen, gebruik een van de `addColumn()` methoden van de `Table` component.

In het volgende fragment zal een kolom proberen gegevens van een JSON-object te benaderen en deze alleen weergeven als de gegevens niet null zijn.

```java
List<String> columnsList = List.of("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");
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

Het is mogelijk om de zichtbaarheid van een kolom in te stellen, waarmee wordt bepaald of deze binnen de `Table` wordt getoond. Dit kan nuttig zijn bij het bepalen of gevoelige informatie moet worden weergegeven.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navigeerbaar {#navigable}

De navigeerbare eigenschap bepaalt of gebruikers met een kolom kunnen interageren tijdens navigatie. Het instellen van `setSuppressNavigable()` op true beperkt de gebruikersinteractie met de kolom, waardoor een alleen-lezen ervaring wordt gegeven.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Indeling en opmaak {#layout-and-formatting}

Nadat de identiteit van een kolom is vastgesteld, is de volgende stap om te bepalen hoe de inhoud ervan aan gebruikers verschijnt. Indelingsopties zoals uitlijning en vastzetten bepalen waar gegevens zich bevinden en hoe ze zichtbaar blijven terwijl je met een `Table` werkt.

### Uitlijning {#alignment}

Het instellen van de uitlijning van een kolom stelt je in staat om georganiseerde tabellen te creëren, wat gebruikers kan helpen de verschillende secties in de `Table` te identificeren.

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

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waarbij het behouden van een linkerflow intuïtief is. Handig wanneer je het startpunt van de inhoud wilt benadrukken.
- `Column.Alignment.CENTER`: Centreren van kolommen is ideaal voor kortere waarden, zoals een tekencommando, status, of iets anders dat een gebalanceerde presentatie heeft.
- `Column.Alignment.RIGHT`: Overweeg om een rechts uitgelijnde kolom te gebruiken voor numerieke waarden die handig zijn om snel door te scannen, zoals datums, bedragen en percentages.

In het voorgaande voorbeeld is de laatste kolom voor `Cost` rechts uitgelijnd om een duidelijkere visuele onderscheid te bieden.

### Vastzetten {#pinning}

Het vastzetten van kolommen is een functionaliteit waarmee gebruikers een kolom aan een specifieke zijde van de `Table` kunnen vastzetten. Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl je horizontaal door een tabel scrolt.

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

- `PinDirection.LEFT`: Vastzetten van de kolom aan de linkerkant.
- `PinDirection.RIGHT`: Vastzetten van de kolom aan de rechterkant.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoer volgorde.

Vastzetten kan programmatisch worden ingesteld, waardoor je de pinrichting kunt wijzigen op basis van gebruikersinteracties of de logica van de app.

## Kolomdimensionering <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Vaste breedte {#fixed-width}

Stel een exacte breedte in voor een kolom met de `setWidth()` methode, waarbij je de gewenste breedte in pixels specificeert:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

De breedte-eigenschap definieert de gewenste initiële breedte voor de kolom. Hoe deze breedte wordt gebruikt, hangt af van andere eigenschappen en kolomtype:

- **Reguliere kolommen**: Met alleen breedte ingesteld, wordt de kolom weergegeven op de opgegeven breedte, maar kan ze proportioneel krimpen wanneer de container te klein is. De oorspronkelijke breedte fungeert als de gewenste breedte, maar zonder expliciete minimum beperkingen kan de kolom kleiner worden weergegeven dan de ingestelde breedte.
- [**Vastgezette kolommen**](#pinning): Behouden altijd hun exacte breedte en nemen nooit deel aan responsief krimpen.
- [**Flex-kolommen**](#flex-sizing): Het instellen van de breedte is onverenigbaar met flex. Gebruik ofwel breedte (vast) of flex (proportioneel), niet beide.

Als niet gespecificeerd, zal de kolom zijn geschatte breedte gebruiken op basis van de inhoudsanalyse van de eerste paar rijen.

```java
// Huidige breedte ophalen
float currentWidth = column.getWidth();
```

### Minimum breedte {#minimum-width}

De `setMinWidth()` methode stelt je in staat om de minimum breedte van een kolom te definiëren. Als de minimum breedte niet wordt opgegeven, berekent de `Table` de minimum breedte op basis van de kolominhoud.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

De waarde die wordt doorgegeven, vertegenwoordigt de minimum breedte in pixels.

De minimum breedte-eigenschap beheert de kleinste breedte die een kolom kan hebben:

- **Reguliere kolommen**: Met alleen minimum breedte ingesteld, gebruikt de kolom de minimum breedte als zowel gewenste als minimum breedte. Met breedte + minimum breedte kan de kolom van de breedte krimpen naar de minimum breedte, maar niet verder.
- [**Vastgezette kolommen**](#pinning): Als alleen de minimum breedte is ingesteld (geen breedte), wordt dit de vaste breedte.
- [**Flex-kolommen**](#flex-sizing): Voorkomt dat de kolom onder deze breedte krimpt, zelfs wanneer de ruimte in de container beperkt is.

```java
// Huidige minimum breedte ophalen
float minWidth = column.getMinWidth();
```

### Maximum breedte {#maximum-width}

De `setMaxWidth()` methode beperkt hoe breed een kolom kan groeien, waardoor wordt voorkomen dat kolommen met lange inhoud te breed worden en de leesbaarheid negatief beïnvloeden:

```java
table.addColumn("Description", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

De `maxWidth` eigenschap beperkt de groei van de kolom voor alle kolomtypes en zal nooit worden overschreden, ongeacht de inhoud, de grootte van de container of de flex-instellingen.

```java
// Huidige maximum breedte ophalen
float maxWidth = column.getMaxWidth();
```

### Flex-dimensionering {#flex-sizing}

De `setFlex()` methode maakt proportionele kolomdimensionering mogelijk, zodat kolommen de beschikbare ruimte delen nadat vaste breedtekolommen zijn toegewezen:

```java
// Titelkolom krijgt tweemaal de ruimte van de Artiest kolom
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Belangrijke flex-gedragingen:

- **Flex-waarde**: Bepelt de proportie van de beschikbare ruimte. Een kolom met flex=2 krijgt tweemaal de ruimte van een kolom met flex=1.
- **Onverenigbaar met breedte**: Kan niet samen met de breedte-eigenschap worden gebruikt. Wanneer flex groter is dan nul, heeft het effect op de breedte-instelling.
- **Respecteert beperkingen**: Werkt met minimum- en maximumbreedtebeperkingen. Zonder minimumbreedte kunnen flex-kolommen tot 0 krimpen.

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
Breedte- en flex-eigenschappen zijn onderling exclusief. Het instellen van één wist automatisch de andere. Gebruik breedte voor nauwkeurige controle of flex voor responsief gedrag.
:::

### Automatische dimensionering {#automatic-sizing}

Naast handmatige breedte- en flex-instellingen, kunnen kolommen ook automatisch worden gedimensioneerd. Automatische dimensionering laat de `Table` de optimale breedtes bepalen door ofwel de inhoud te analyseren of door de ruimte proportioneel te verdelen.

#### Inhoudsgebaseerde automatische dimensionering {#content-based-auto-sizing}

Automatisch de kolommen dimensioneren op basis van hun inhoud. De `Table` analyseert de gegevens in elke kolom en berekent de optimale breedte om de inhoud weer te geven zonder afkapping.

```java
// Auto-sizing van alle kolommen om inhoud aan te passen
table.setColumnsToAutoSize().thenAccept(c -> {
  // Dimensionering voltooid - kolommen passen nu bij hun inhoud
});

// Auto-sizing van specifieke kolom
table.setColumnToAutoSize("description");
```

#### Proportioneel auto-fit {#proportional-auto-fit}

Verdeel alle kolommen proportioneel over de beschikbare `Table`-breedte. Deze actie stelt elke kolom in op flex=1, zodat ze de totale `Table`-breedte gelijk delen, ongeacht de lengtes van hun inhoud. Kolommen zullen uitbreiden of inkrimpen om de exacte afmetingen van de `Table` in te vullen zonder dat er ruimte overblijft.

```java
// Pas kolommen aan de tabelbreedte aan (equivalent aan het instellen van flex=1 op alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Alle kolommen delen nu de ruimte gelijkmatig
});
```

:::info Asynchrone Operaties
Auto-sizing methoden retourneren `PendingResult<Void>` omdat ze client-side berekeningen vereisen. Gebruik `thenAccept()` om code uit te voeren nadat de dimensionering is voltooid. Als je niet hoeft te wachten op voltooiing, kun je de methoden zonder `thenAccept()` aanroepen.
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

### Kolomvergroting {#column-resizing}

Kolomvergroting geeft gebruikers controle over de hoeveelheid ruimte die elke kolom inneemt door de kolomranden te slepen.

Je kunt het vergrotingsgedrag op individuele kolommen controleren bij het bouwen van je tafel:

```java
// Sta gebruikersvergroting voor deze kolom toe
table.addColumn("Title", Product::getTitle).setResizable(true);

// Uitschakelen van vergroting
table.addColumn("ID", Product::getId).setResizable(false);

// Controleer huidige staat
boolean canResize = column.isResizable();
```

Voor tabellen waar je consistente gedrag wilt over meerdere kolommen, gebruik de bulkconfiguratiemethoden:

```java
// Maak alle bestaande kolommen vergrootbaar
table.setColumnsToResizable(true);

// Blokkeer alle bestaande kolommen van vergroting
table.setColumnsToResizable(false);
```

### Kolom herschikking {#column-reordering}

Kolom herschikking stelt gebruikers in staat om kolommen te slepen en neer te zetten in hun voorkeursoordening, waardoor de lay-out van de `Table` kan worden gepersonaliseerd voor hun workflow.

Configureer de beweging toestemming van kolommen bij het opzetten van je tafel:

```java
// Sta gebruikers toe om deze kolom te verplaatsen
table.addColumn("Title", Product::getTitle).setMovable(true);

// Voorkom kolombeweging (handig voor ID- of actiekolommen)
table.addColumn("ID", Product::getId).setMovable(false);

// Controleer huidige staat
boolean canMove = column.isMovable();
```

Pas de bewegingsinstellingen tegelijkertijd toe op meerdere kolommen:

```java
// Sta herschikking toe voor alle bestaande kolommen
table.setColumnsToMovable(true);

// Uitschakelen van herschikking voor alle bestaande kolommen  
table.setColumnsToMovable(false);
```

:::note Bulkoperaties
De `setColumnsToResizable()` en `setColumnsToMovable()` methoden beïnvloeden alleen bestaande kolommen op het moment van aanroepen. Ze stellen geen standaardwaarden in voor toekomstige kolommen.
:::

### Programmatische kolombeweging {#programmatic-column-movement} 

Naast slepen en neerzetten, kun je ook kolommen programmatisch opnieuw positioneren op basis van index of ID. Houd er rekening mee dat de index alleen is gebaseerd op zichtbare kolommen; verborgen kolommen worden genegeerd bij het berekenen van posities.

```java
// Verplaats kolom naar eerste positie
table.moveColumn("title", 0);

// Verplaats kolom naar laatste positie
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone beweging met callback
table.moveColumn("description", 2).thenAccept(c -> {
  // Kolom succesvol verplaatst
});
```

## Evenementverwerking {#event-handling}

De `Table` component genereert evenementen wanneer gebruikers met kolommen interageren, zodat je kunt reageren op lay-outwijzigingen en gebruikersvoorkeuren opslaan.

Ondersteunde evenementen:

- `TableColumnResizeEvent`: Gewekt wanneer een gebruiker een kolom vergroot door de rand te slepen.
- `TableColumnMoveEvent`: Gewekt wanneer een gebruiker een kolom herschikt door de kop te slepen.

Je kunt luisteraars aan de `Table` koppelen om te reageren wanneer gebruikers de tafel lay-out aanpassen.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Verwerk kolom vergroot gebeurtenis
  // Toegang: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Verwerk kolom verplaats gebeurtenis  
  // Toegang: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
