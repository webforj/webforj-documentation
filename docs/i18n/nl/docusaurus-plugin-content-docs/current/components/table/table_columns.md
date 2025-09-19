---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 280a70bb65c45d3b200157f3462d7b10
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table` klasse gebruikt kolominstantie om te definiëren en aan te passen hoe gegevens worden weergegeven. Kolommen bepalen welke gegevens worden getoond, hoe ze eruitzien en hoe gebruikers ermee kunnen omgaan. Deze pagina behandelt kolomidentiteit, presentatie, grootte, gebruikersinteracties en gerelateerde gebeurtenissen.

## Kolomidentiteit {#column-identity}

De identiteit van een kolom definieert hoe deze wordt herkend in de `Table`. Dit omvat zijn label, de waarde die hij biedt en of hij zichtbaar of navigeerbaar is.

### Label {#label}

Het label van een kolom is de openbare identificatie, die helpt om de weergegeven gegevens te verduidelijken.  

Gebruik `setLabel()` om het label in te stellen of te wijzigen.

:::tip
Standaard is het label hetzelfde als de kolom-ID.
:::

```java
table.addColumn("Product-ID", Product::getProductId).setLabel("ID");
```

### Waardeproviders {#value-providers}

Een waardeprovider is een functie die verantwoordelijk is voor het vertalen van ruwe gegevens vanuit de onderliggende dataset in een formaat dat geschikt is voor weergave binnen een specifieke kolom. De door jou gedefinieerde functie neemt een instantie van het rijdataset-tetype (T) en retourneert de waarde die in de bijbehorende kolom voor die specifieke rij moet worden getoond.

Om een waardeprovider aan een kolom in te stellen, gebruik je een van de `addColumn()`-methoden van de `Table`-component.

In de volgende snippet probeert een kolom gegevens op te halen uit een JSON-object en deze alleen weer te geven als de gegevens niet null zijn.

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

### Zichtbaarheid {#visibility}

Het is mogelijk om de zichtbaarheid van een kolom in te stellen, wat bepaalt of deze al dan niet wordt weergegeven in de `Table`. Dit kan nuttig zijn bij het bepalen of gevoelige informatie al dan niet moet worden weergegeven. 

```java
table.addColumn("Creditcard", Customer::getCreditCardNumber).setHidden(true);
```

### Navigeerbaar {#navigable}

De navigeerbare eigenschap bepaalt of gebruikers met een kolom kunnen interageren tijdens navigatie. Het instellen van `setSuppressNavigable()` op true beperkt de interactie van gebruikers met de kolom, wat een alleen-lezen ervaring biedt.

```java
table.addColumn("Alleen lezen kolom", Product::getDescription).setSuppressNavigable(true);
```

## Indeling en opmaak {#layout-and-formatting}

Na het vaststellen van de identiteit van een kolom, is de volgende stap om te controleren hoe de inhoud voor gebruikers verschijnt. Indelingsopties zoals uitlijning en vastpinnen bepalen waar gegevens zich bevinden en hoe ze zichtbaar blijven terwijl je met een `Table` werkt.

### Uitlijning {#alignment}

Het instellen van de uitlijning van een kolom stelt je in staat om geordende tabellen te creëren, wat gebruikers kan helpen de verschillende secties in de `Table` te identificeren.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

De `Table`-component ondersteunt drie uitlijningsopties:

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waar het handhaven van een linksgerichte stroom intuïtief is. Handig als je het beginpunt van de inhoud wilt benadrukken.
- `Column.Alignment.CENTER`: Centraal uitgelijnde kolommen zijn ideaal voor kortere waarden, zoals een karakterteken, status of iets anders dat een gebalanceerde presentatie heeft.
- `Column.Alignment.RIGHT`: Overweeg een rechts uitgelijnde kolom te gebruiken voor numerieke waarden die handig zijn om snel doorheen te scannen, zoals datums, bedragen en percentages.

In het bovenstaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd om een duidelijker visueel onderscheid te bieden.

### Vastpinnen {#pinning}

Het vastpinnen van kolommen is een functie waarmee gebruikers een kolom aan een specifieke kant van de `Table` kunnen bevestigen of "vastpinnen". Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl je horizontaal door een tabel scrollt.

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

- `PinDirection.LEFT`: Pakt de kolom aan de linkerkant vast.
- `PinDirection.RIGHT`: Pakt de kolom aan de rechterkant vast.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoegvolgorde.

Vastpinnen kan programmatisch worden ingesteld, waardoor je de pinningsrichting kunt wijzigen op basis van gebruikersinteracties of de logica van de app.

## Kolomgroottes <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Vaste breedte {#fixed-width}

Stel een exacte breedte in voor een kolom met de `setWidth()`-methode, waarbij je de gewenste breedte in pixels opgeeft:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

De breedte-eigenschap definieert de gewenste initiële breedte voor de kolom. Hoe deze breedte wordt gebruikt, hangt af van andere eigenschappen en het kolomtype:

- **Reguliere kolommen**: Met alleen de breedte ingesteld, wordt de kolom weergegeven op de opgegeven breedte, maar kan deze proportioneel krimpen wanneer de container te klein is. De oorspronkelijke breedte dient als gewenste breedte, maar zonder expliciete minimumbeperkingen kan de kolom kleiner worden weergegeven dan de ingestelde breedte.
- [**Vastgepinde kolommen**](#pinning): Handhaven altijd hun exacte breedte en nemen nooit deel aan responsieve krimp.
- [**Flex-kolommen**](#flex-sizing): Het instellen van breedte is incompatibel met flex. Gebruik ofwel breedte (vast) of flex (proportioneel), niet beide.

Als het niet is opgegeven, maakt de kolom gebruik van zijn geschatte breedte op basis van inhoudsanalyse van de eerste paar rijen.

```java
// Huidige breedte ophalen
float currentWidth = column.getWidth();
```

### Minimum breedte {#minimum-width}

De `setMinWidth()`-methode stelt je in staat om de minimale breedte van een kolom te definiëren. Als de minimale breedte niet wordt opgegeven, berekent de `Table` de minimale breedte op basis van de kolominhoud.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100f);
```

De waarde die wordt doorgegeven vertegenwoordigt de minimale breedte in pixels.

De minimum breedte-eigenschap controleert de kleinste breedte die een kolom kan hebben:

- **Reguliere kolommen**: Met alleen de minimum breedte ingesteld, gebruikt de kolom de minimum breedte als zowel gewenste als minimum breedte. Met breedte + minimum breedte, kan de kolom krimpen van de breedte naar de minimum breedte, maar niet verder.
- [**Vastgepinde kolommen**](#pinning): Als alleen de minimum breedte is ingesteld (geen breedte), wordt het de vaste breedte.
- [**Flex-kolommen**](#flex-sizing): Voorkomt dat de kolom krimpt tot onder deze breedte, zelfs wanneer de containerruimte beperkt is.

```java
// Huidige minimum breedte ophalen
float minWidth = column.getMinWidth();
```

### Maximum breedte {#maximum-width}

De `setMaxWidth()`-methode beperkt hoe breed een kolom kan groeien, waardoor wordt voorkomen dat kolommen met lange inhoud te breed worden en de leesbaarheid beïnvloeden:

```java
table.addColumn("Beschrijving", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

De `maxWidth`-eigenschap beperkt de groei van kolommen voor alle kolomtypes en zal nooit worden overschreden, ongeacht de inhoud, containermaat of flex-instellingen.

```java
// Huidige maximum breedte ophalen
float maxWidth = column.getMaxWidth();
```

### Flex-sizing {#flex-sizing}

De `setFlex()`-methode stelt proportionele kolomgroottes in, waardoor kolommen beschikbare ruimte delen nadat vaste breedtekolommen zijn toegewezen:

```java
// Titelskolom krijgt tweemaal de ruimte van de Artiestkolom
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Artiest", Product::getArtist).setFlex(1f);
```

Belangrijke flex-gedragingen:

- **Flex-waarde**: Bepaalt de proportie van de beschikbare ruimte. Een kolom met flex=2 krijgt tweemaal zoveel ruimte als een kolom met flex=1.
- **Incompatibel met breedte**: Kan niet samen met de breedte-eigenschap worden gebruikt. Wanneer flex groter is dan nul, heeft het effect op de breedte-instelling.
- **Respecteert beperkingen**: Werkt met minimum breedte/maximale breedte beperkingen. Zonder minimale breedte kunnen flex-kolommen tot 0 krimpen.

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
Breedte- en flex-eigenschappen zijn wederzijds exclusief. Het instellen van de ene wist automatisch de andere. Gebruik de breedte voor precieze controle of flex voor responsief gedrag.
:::

### Automatische sizing {#automatic-sizing}

Naast handmatige breedte- en flex-instellingen kunnen kolommen ook automatisch worden geconfigureerd. Automatische sizing laat de `Table` de optimale breedtes bepalen door gegevens te analyseren of ruimte proportioneel te verdelen.

#### Op inhoud gebaseerde auto-sizing {#content-based-auto-sizing}

Automatisch kolommen op basis van hun inhoud. De `Table` analyseert de gegevens in elke kolom en berekent de optimale breedte om de inhoud zonder afkapping weer te geven.

```java
// Auto-size alle kolommen om inhoud te passen
table.setColumnsToAutoSize().thenAccept(c -> {
    // Sizing compleet - kolommen passen nu bij hun inhoud
});

// Auto-size specifieke kolom
table.setColumnToAutoSize("beschrijving");
```

#### Proportionele auto-fit {#proportional-auto-fit}

Verdeel alle kolommen proportioneel over de beschikbare `Table`-breedte. Deze bewerking stelt elke kolom in op flex=1, waardoor ze de totale `Table`-breedte gelijk delen, ongeacht hun inhouds lengte. Kolommen zullen uitbreiden of krimpen om exact de dimensies van de `Table` te vullen zonder overblijvende ruimte.

```java
// Pas kolommen aan de tabelbreedte aan (equivalent aan het instellen van flex=1 op alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Alle kolommen delen nu de ruimte gelijk
});
```

:::info Asynchrone Operaties
Auto-sizing methoden retourneren `PendingResult<Void>` omdat ze client-zijde berekeningen vereisen. Gebruik `thenAccept()` om code uit te voeren nadat de sizing is voltooid. Als je niet hoeft te wachten op voltooiing, kun je de methoden aanroepen zonder `thenAccept()`.
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

### Kolomresize {#column-resizing}

Kolomresize geeft gebruikers controle over hoeveel ruimte elke kolom inneemt door de kolomgrenzen te slepen.

Je kunt het resize-gedrag op individuele kolommen controleren wanneer je je tabel bouwt:

```java
// Sta gebruikers toe om deze kolom te resize
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Schakel resizing uit
table.addColumn("ID", Product::getId).setResizable(false);

// Huidige status controleren
boolean canResize = column.isResizable();
```

Voor tabellen waarvoor je een consistente werking wilt over meerdere kolommen, gebruik de bulkconfiguratiemethoden:

```java
// Maak alle bestaande kolommen resizable
table.setColumnsToResizable(true);

// Beperk alle bestaande kolommen van resizing
table.setColumnsToResizable(false);
```

### Kolom herschikken {#column-reordering}

Kolom herschikken stelt gebruikers in staat om kolommen te slepen en in hun voorkeursvolgorde neer te zetten, waardoor ze de lay-out van de `Table` voor hun workflow kunnen personaliseren.

Configureer kolombewegingsautorisaties bij het opzetten van je tabel:

```java
// Sta gebruikers toe deze kolom te verplaatsen
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Voorkom kolomverplaatsing (handig voor ID- of actiekolommen)
table.addColumn("ID", Product::getId).setMovable(false);

// Huidige status controleren
boolean canMove = column.isMovable();
```

Pas bewegingsinstellingen tegelijk toe op meerdere kolommen:

```java
// Sta herschikken toe voor alle bestaande kolommen
table.setColumnsToMovable(true);

// Schakel herschikken uit voor alle bestaande kolommen  
table.setColumnsToMovable(false);
```

:::note Bulkbewerkingen
De `setColumnsToResizable()` en `setColumnsToMovable()` methoden hebben alleen invloed op bestaande kolommen op het moment van aanroep. Ze stellen geen standaardwaarden in voor toekomstige kolommen.
:::

### Programmatic kolomverplaatsing {#programmatic-column-movement} 

Naast sleep-en-neerzetten kun je kolommen ook programmatisch verplaatsen op basis van index of ID. Houd er rekening mee dat de index alleen op zichtbare kolommen is gebaseerd; verborgen kolommen worden genegeerd bij het berekenen van posities.

```java
// Verplaats kolom naar eerste positie
table.moveColumn("titel", 0);

// Verplaats kolom naar laatste positie
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone beweging met callback
table.moveColumn("beschrijving", 2).thenAccept(c -> {
    // Kolom succesvol verplaatst
});
```

## Evenementafhandeling {#event-handling}

De `Table`-component genereert evenementen wanneer gebruikers met kolommen interageren, waardoor je kunt reageren op lay-outwijzigingen en gebruikersvoorkeuren kunt opslaan.

Ondersteunde evenementen:

- `TableColumnResizeEvent`: Afgemaakt wanneer een gebruiker een kolom vergroot of verkleint door de rand te slepen.
- `TableColumnMoveEvent`: Afgemaakt wanneer een gebruiker een kolom herschikt door de kop te slepen.

Je kunt luisteraars aan de `Table`-component bevestigen om te reageren wanneer gebruikers de tabelindeling wijzigen.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Verwerk evenement voor kolomresizing
  // Toegang: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Verwerk evenement voor kolomverplaatsing  
  // Toegang: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
