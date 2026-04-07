---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 19fe294c57ad6b7d105039c25aedab11
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table` klasse gebruikt kolominstellingen om te definiëren en aan te passen hoe gegevens worden weergegeven. Kolommen bepalen welke gegevens worden getoond, hoe ze eruitzien, en hoe gebruikers ermee kunnen interageren. Deze pagina behandelt kolomidentiteit, presentatie, sizing, gebruikersinteracties en gerelateerde evenementen.

## Kolomidentiteit {#column-identity}

De identiteit van een kolom definieert hoe deze wordt herkend in de `Table`. Dit omvat het label, de waarde die het biedt en of het zichtbaar of navigeerbaar is.

### Label {#label}

Het label van een kolom is zijn publiek toegankelijke identifier, wat helpt om weergegeven gegevens te verduidelijken.  

Gebruik `setLabel()` om het label in te stellen of te wijzigen.

:::tip
Standaard is het label hetzelfde als de kolom-ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Waarde providers {#value-providers}

Een waarde provider is een functie die verantwoordelijk is voor het vertalen van ruwe gegevens uit de onderliggende dataset naar een geschikt formaat voor weergave binnen een specifieke kolom. De functie, die je definieert, neemt een instantie van het rijdatatype (T) en retourneert de waarde die in de bijbehorende kolom voor die specifieke rij moet worden weergegeven.

Om een waarde provider aan een kolom in te stellen, gebruik je een van de `addColumn()` methoden van de `Table` component.

In de volgende snippet zal een kolom proberen gegevens uit een JSON-object te halen, deze alleen weer te geven als de gegevens niet null zijn.

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

Het is mogelijk om de zichtbaarheid van een kolom in te stellen, wat bepaalt of deze wel of niet wordt getoond binnen de `Table`. Dit kan nuttig zijn, onder andere, bij het bepalen of gevoelige informatie moet worden weergegeven.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navigeerbaar {#navigable}

De navigeerbare eigenschap bepaalt of gebruikers kunnen interageren met een kolom tijdens navigatie. Het instellen van `setSuppressNavigable()` op true beperkt de interactie van gebruikers met de kolom, wat een alleen-lezen ervaring biedt.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Indeling en opmaak {#layout-and-formatting}

Nadat de identiteit van een kolom is vastgesteld, is de volgende stap om te controleren hoe de inhoud aan gebruikers verschijnt. Indelingsopties zoals uitlijning en vastpinnen bepalen waar gegevens zich bevinden en hoe deze zichtbaar blijven terwijl je met een `Table` werkt.

### Uitlijning {#alignment}

Het instellen van de uitlijning van een kolom laat je georganiseerde tabellen maken, wat kan helpen gebruikers te laten identificeren welke secties er in de `Table` zijn.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

De `Table` Component ondersteunt drie uitlijningsopties:

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waarbij een linker richting intuïtief is. Handig bij het benadrukken van het startpunt van de inhoud.
- `Column.Alignment.CENTER`: Centraal uitgelijnde kolommen zijn ideaal voor kortere waarden, zoals een tekenreeks, status, of iets anders met een evenwichtige presentatie.
- `Column.Alignment.RIGHT`: Overweeg een rechts uitgelijnde kolom voor numerieke waarden die snel doorzocht moeten worden, zoals data, bedragen en percentages.

In het voorgaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd om een duidelijker visueel onderscheid te bieden.

### Vastpinnen {#pinning}

Het vastpinnen van kolommen is een functie die gebruikers toestaat een kolom aan een specifieke kant van de `Table` te bevestigen of "vast te pinnen". Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl je horizontaal door een tabel scrollt.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Er zijn drie beschikbare richtingen voor het vastpinnen van een kolom:

- `PinDirection.LEFT`: Pinnen de kolom aan de linkerkant.
- `PinDirection.RIGHT`: Pinnen de kolom aan de rechterkant.
- `PinDirection.AUTO`: De kolom verschijnt op basis van de volgorde van invoegen.

Vastpinnen kan programmatologisch worden ingesteld, zodat je de pinnarichting kunt wijzigen op basis van gebruikersinteracties of volgens de logica van de app.

## Kolom sizing <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Vaste breedte {#fixed-width}

Stel een exacte breedte in voor een kolom met de methode `setWidth()`, waarbij je de gewenste breedte in pixels opgeeft:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

De breedte-eigenschap definieert de gewenste initiële breedte voor de kolom. Hoe deze breedte wordt gebruikt, hangt af van andere eigenschappen en kolomtypes:

- **Reguliere kolommen**: Met alleen de breedte ingesteld, wordt de kolom weergegeven op de opgegeven breedte, maar kan proportioneel krimpen wanneer de container te klein is. De originele breedte dient als de gewenste breedte, maar zonder expliciete minimum beperkingen kan de kolom kleiner weergeven dan de ingestelde breedte.
- [**Vastgepinde kolommen**](#pinning): Behouden altijd hun exacte breedte, nemen nooit deel aan responsieve krimp.
- [**Flex kolommen**](#flex-sizing): Het instellen van de breedte is incompatibel met flex. Gebruik ofwel breedte (vast) of flex (proportioneel), niet beide.

Als niet gespecificeerd, gebruikt de kolom zijn geschatte breedte op basis van inhoudsanalyse van de eerste paar rijen.

```java
// Verkrijg huidige breedte
float currentWidth = column.getWidth();
```

### Minimale breedte {#minimum-width}

De methode `setMinWidth()` stelt je in staat om de minimale breedte van een kolom te definiëren. Als de minimale breedte niet is opgegeven, berekent de `Table` de minimale breedte op basis van de kolominhoud.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100f);
```

De waarde die wordt doorgegeven, vertegenwoordigt de minimale breedte in pixels.

De minimale breedte-eigenschap controleert de kleinste breedte die een kolom kan hebben:

- **Reguliere kolommen**: Met alleen minimale breedte ingesteld, gebruikt de kolom de minimale breedte als zowel de gewenste als de minimale breedte. Met breedte + minimale breedte kan de kolom krimpen van de breedte naar de minimale breedte, maar niet verder.
- [**Vastgepinde kolommen**](#pinning): Als alleen de minimale breedte is ingesteld (geen breedte), wordt deze de vaste breedte.
- [**Flex kolommen**](#flex-sizing): Voorkomt dat de kolom onder deze breedte krimpt, zelfs wanneer de ruimte in de container beperkt is.

```java
// Verkrijg huidige minimale breedte
float minWidth = column.getMinWidth();
```

### Maximale breedte {#maximum-width}

De methode `setMaxWidth()` beperkt hoe breed een kolom kan groeien, waardoor voorkomt dat kolommen met lange inhoud te breed worden en de leesbaarheid beïnvloeden:

```java
table.addColumn("Beschrijving", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

De `maxWidth` eigenschap beperkt de groei van kolommen voor alle kolomtypes en zal nooit worden overschreden, ongeacht de inhoud, de grootte van de container of flex-instellingen.

```java
// Verkrijg huidige maximale breedte
float maxWidth = column.getMaxWidth();
```

### Flex sizing {#flex-sizing}

De methode `setFlex()` maakt proportionele kolom sizing mogelijk, waardoor kolommen beschikbare ruimte delen nadat vaste breedte kolommen zijn toegewezen:

```java
// Titelkolom krijgt tweemaal de ruimte van de Artiest kolom
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Artiest", Product::getArtist).setFlex(1f);
```

Belangrijke flex gedragingen:

- **Flexwaarde**: Bepaalt het aandeel van de beschikbare ruimte. Een kolom met flex=2 krijgt tweemaal de ruimte van een kolom met flex=1.
- **Incompatibel met breedte**: Kan niet samen met de breedte-eigenschap worden gebruikt. Wanneer flex groter is dan nul, heeft het effect op de breedte-instelling.
- **Respecteert beperkingen**: Werkt met minimum breedte/maximale breedte beperkingen. Zonder minimum breedte kunnen flex kolommen tot 0 krimpen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Breedte vs Flex
Breedte- en flex-eigenschappen zijn wederzijds exclusief. Het instellen van de een wist automatisch de ander. Gebruik breedte voor precieze controle of flex voor responsief gedrag.
:::

### Automatische sizing {#automatic-sizing}

Naast handmatige breedte- en flexinstellingen, kunnen kolommen ook automatisch worden gepositioneerd. Automatische sizing laat de `Table` optimale breedtes bepalen, hetzij door de inhoud te analyseren of door ruimte proportioneel te verdelen.

#### Inhoudsgebaseerde automatische sizing {#content-based-auto-sizing}

Automatisch kolommen positioneren op basis van hun inhoud. De `Table` analyseert de gegevens in elke kolom en berekent de optimale breedte om de inhoud zonder afbreking weer te geven.

```java
// Auto-size alle kolommen om inhoud te passen
table.setColumnsToAutoSize().thenAccept(c -> {
  // sizing compleet - kolommen passen nu bij hun inhoud
});

// Auto-size specifieke kolom
table.setColumnToAutoSize("description");
```

#### Proportionele auto-fit {#proportional-auto-fit}

Verdeel alle kolommen proportioneel over de beschikbare breedte van de `Table`. Deze operatie stelt elke kolom in op flex=1, waardoor ze de totale `Table` breedte gelijk delen, ongeacht de lengte van hun inhoud. Kolommen zullen uitbreiden of krimpen om de exacte dimensies van de `Table` in te vullen zonder overgebleven ruimte.

```java
// Pas kolommen aan de tabelbreedte aan (gelijk aan het instellen van flex=1 op alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Alle kolommen delen nu op gelijke wijze ruimte
});
```

:::info Asynchrone Operaties
Auto-sizing methoden retourneren `PendingResult<Void>` omdat ze client-side berekeningen vereisen. Gebruik `thenAccept()` om code uit te voeren nadat de sizing is voltooid. Als je niet hoeft te wachten op voltooiing, kun je de methoden zonder `thenAccept()` aanroepen.
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## Gebruikersinteracties <DocChip chip='since' label='25.03' /> {#user-interactions}

### Kolom resizing {#column-resizing}

Kolom resizing geeft gebruikers controle over hoeveel ruimte elke kolom in beslag neemt door aan de randen van de kolom te slepen.

Je kunt de resizinggedraging op individuele kolommen regelen bij het bouwen van je tabel:

```java
// Sta gebruikers toe deze kolom te resizing
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Uitschakelen van resizing
table.addColumn("ID", Product::getId).setResizable(false);

// Controleer huidige staat
boolean canResize = column.isResizable();
```

Voor tabellen waarbij je een consistente gedraging over meerdere kolommen wilt, gebruik je de bulkconfiguratie methoden:

```java
// Maak alle bestaande kolommen resizebaar
table.setColumnsToResizable(true);

// Vergrendel alle bestaande kolommen tegen resizing
table.setColumnsToResizable(false);
```

### Kolom herschikken {#column-reordering}

Kolom herschikken stelt gebruikers in staat om kolommen te slepen en neer te zetten in hun voorkeursgeschiedenis, waardoor ze de lay-out van de `Table` kunnen personaliseren voor hun workflow.

Configureer kolombewegingstoestemmingen bij het opzetten van je tabel:

```java
// Sta gebruikers toe deze kolom te verplaatsen
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Voorkom kolombeweging (handig voor ID of actie kolommen)
table.addColumn("ID", Product::getId).setMovable(false);

// Controleer huidige staat
boolean canMove = column.isMovable();
```

Pas bewegingsinstellingen tegelijk toe op meerdere kolommen:

```java
// Sta herschikken toe voor alle bestaande kolommen
table.setColumnsToMovable(true);

// Voorkom herschikken voor alle bestaande kolommen  
table.setColumnsToMovable(false);
```

:::note Bulk Operaties
De methoden `setColumnsToResizable()` en `setColumnsToMovable()` beïnvloeden alleen bestaande kolommen op het moment van aanroep. Ze stellen geen standaardwaarden in voor toekomstige kolommen.
:::

### Programmatic kolombeweging {#programmatic-column-movement} 

Naast slepen en neerzetten, kun je kolommen ook programmatig verplaatsen op basis van index of ID. Houd er rekening mee dat de index alleen is gebaseerd op zichtbare kolommen; verborgen kolommen worden genegeerd bij het berekenen van posities.

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

De `Table` component geeft evenementen af wanneer gebruikers interageren met kolommen, waardoor je kunt reageren op layoutwijzigingen en gebruikersvoorkeuren opslaan.

Ondersteunde evenementen:

- `TableColumnResizeEvent`: Afgevuurd wanneer een gebruiker een kolom resize door de rand te slepen.
- `TableColumnMoveEvent`: Afgevuurd wanneer een gebruiker een kolom herschikt door de kop te slepen.

Je kunt luisteraars aan de `Table` koppelen om te reageren wanneer gebruikers de tabelindeling wijzigen.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Verwerk kolom resize evenement
  // Toegang: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Verwerk kolom verplaats evenement  
  // Toegang: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
