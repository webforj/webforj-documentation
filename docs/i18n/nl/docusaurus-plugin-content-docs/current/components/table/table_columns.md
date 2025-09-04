---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 6be8663364f0b321c603eb8b870cc104
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table` klasse gebruikt kolominstanties om te definiëren en te personaliseren hoe gegevens worden weergegeven. Kolommen bepalen welke gegevens worden getoond, hoe het eruit ziet en hoe gebruikers ermee kunnen interageren. Deze pagina behandelt kolomidentiteit, presentatie, grootte, gebruikersinteracties en gerelateerde evenementen.

## Kolomidentiteit {#column-identity}

De identiteit van een kolom definieert hoe deze wordt herkend in de `Table`. Dit omvat het label, de waarde die het biedt en of het zichtbaar of navigeerbaar is.

### Label {#label}

Het label van een kolom is de publieke identificator, die helpt om de weergegeven gegevens te verduidelijken.

Gebruik `setLabel()` om het label in te stellen of te wijzigen.

:::tip
Standaard zal het label hetzelfde zijn als de kolom-ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Waarde aanbieders {#value-providers}

Een waarde aanbieder is een functie die verantwoordelijk is voor het vertalen van ruwe gegevens uit de onderliggende dataset naar een formaat dat geschikt is voor weergave binnen een specifieke kolom. De functie, die jij definieert, neemt een instantie van het rijgegevens type (T) en retourneert de waarde die in de bijbehorende kolom voor die specifieke rij moet worden weergegeven.

Om een waarde aanbieder op een kolom in te stellen, gebruik je een van de `addColumn()` methoden van de `Table` component.

In de volgende snippet, zal een kolom proberen gegevens op te halen uit een JSON-object, en het alleen weergeven als de gegevens niet null zijn.

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

Het is mogelijk om de zichtbaarheid van een kolom in te stellen, waarmee wordt bepaald of deze wordt weergegeven in de `Table`. Dit kan nuttig zijn wanneer, onder andere, moet worden bepaald of gevoelige informatie moet worden weergegeven.

```java
table.addColumn("Creditcard", Customer::getCreditCardNumber).setHidden(true);
```

### Navigeerbaar {#navigable}

De navigeerbare eigenschap bepaalt of gebruikers met een kolom kunnen interageren tijdens de navigatie. Het instellen van `setSuppressNavigable()` op true beperkt de interactie van de gebruiker met de kolom en biedt een alleen-lezen ervaring.

```java
table.addColumn("Alleen-lezen Kolom", Product::getDescription).setSuppressNavigable(true);
```

## Indeling en formattering {#layout-and-formatting}

Nadat de identiteit van een kolom is vastgesteld, is de volgende stap om te regelen hoe de inhoud ervan aan gebruikers wordt weergegeven. Indelingsopties zoals uitlijning en vastzetten bepalen waar gegevens zich bevinden en hoe ze zichtbaar blijven terwijl je met een `Table` werkt.

### Uitlijning {#alignment}

Het instellen van de uitlijning van een kolom stelt je in staat om georganiseerde tabellen te maken, wat gebruikers kan helpen de verschillende secties in de `Table` te identificeren.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

De `Table` component ondersteunt drie uitlijningsopties:

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waar het behouden van een linker stroom intuïtief is. Nuttig wanneer de nadruk ligt op het beginpunt van de inhoud.
- `Column.Alignment.CENTER`: Centraal uitgelijnde kolommen zijn ideaal voor kortere waarden, zoals een tekenreeks, status of iets anders met een gebalanceerde presentatie.
- `Column.Alignment.RIGHT`: Overweeg een rechts uitgelijnde kolom te gebruiken voor numerieke waarden die snel kunnen worden doorzocht, zoals datums, bedragen en percentages.

In het voorgaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd om een meer voor de hand liggend visueel onderscheid te bieden.

### Vastzetten {#pinning}

Het vastzetten van kolommen is een functie die gebruikers in staat stelt een kolom aan een specifieke zijde van de `Table` te bevestigen of te "pinnen". Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl er horizontaal door een tabel wordt geschrolled.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Er zijn drie beschikbare richtingen voor het vastzetten van een kolom:

- `PinDirection.LEFT`: Zet de kolom vast aan de linkerkant.
- `PinDirection.RIGHT`: Zet de kolom vast aan de rechterkant.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoer volgorde.

Vastzetten kan programmatisch worden ingesteld, zodat je de pinningsrichting kunt wijzigen op basis van gebruikersinteracties of de logica van de app.

## Kolomgrootte <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Vaste breedte {#fixed-width}

Stel een exacte breedte voor een kolom in met de `setWidth()` methode, waarbij je de gewenste breedte in pixels opgeeft:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

De breedte-eigenschap definieert de gewenste initiële breedte voor de kolom. Hoe deze breedte wordt gebruikt, hangt af van andere eigenschappen en kolomtypen:

- **Reguliere kolommen**: Met alleen de breedte ingesteld, wordt de kolom weergegeven op de opgegeven breedte, maar kan deze proportioneel krimpen wanneer de container te klein is. De oorspronkelijke breedte dient als de gewenste breedte, maar zonder expliciete minimumbeperkingen kan de kolom kleiner renderen dan de ingestelde breedte.
- [**Vastgezette kolommen**](#pinning): behouden altijd hun exacte breedte en nemen nooit deel aan responsief krimpen.
- [**Flex kolommen**](#flex-sizing): Het instellen van de breedte is incompatibel met flex. Gebruik ofwel breedte (vast) of flex (proportioneel), niet beide.

Als het niet gespecificeerd is, gebruikt de kolom zijn geschatte breedte op basis van de inhoudsanalyse van de eerste paar rijen.

```java
// Verkrijg huidige breedte
float currentWidth = column.getWidth();
```

### Minimale breedte {#minimum-width}

De `setMinWidth()` methode stelt je in staat om de minimale breedte van een kolom te definiëren. Als de minimale breedte niet is opgegeven, berekent de `Table` de minimale breedte op basis van de kolominhoud.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100f);
```

De waarde die wordt doorgegeven, vertegenwoordigt de minimale breedte in pixels.

De minimale breedte-eigenschap bepaalt de kleinste breedte die een kolom kan hebben:

- **Reguliere kolommen**: Met alleen de minimale breedte ingesteld, gebruikt de kolom de minimale breedte als zowel de gewenste als de minimale breedte. Met breedte + minimale breedte kan de kolom van de breedte krimpen tot de minimale breedte, maar niet verder.
- [**Vastgezette kolommen**](#pinning): Als alleen de minimale breedte is ingesteld (geen breedte), wordt het de vaste breedte.
- [**Flex kolommen**](#flex-sizing): voorkomt dat de kolom onder deze breedte krimpt, zelfs wanneer de container ruimte beperkt is.

```java
// Verkrijg huidige minimale breedte
float minWidth = column.getMinWidth();
```

### Maximale breedte {#maximum-width}

De `setMaxWidth()` methode beperkt hoe breed een kolom kan worden, waardoor wordt voorkomen dat kolommen met lange inhoud te breed worden en de leesbaarheid beïnvloeden:

```java
table.addColumn("Beschrijving", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

De `maxWidth` eigenschap beperkt de groei van de kolom voor alle kolomtypen en zal nooit worden overschreden, ongeacht de inhoud, container grootte of flexinstellingen.

```java
// Verkrijg huidige maximale breedte
float maxWidth = column.getMaxWidth();
```

### Flex grootte {#flex-sizing}

De `setFlex()` methode maakt proportionele kolomgrootte mogelijk, waardoor kolommen beschikbare ruimte delen nadat vaste breedte kolommen zijn toegewezen:

```java
// Titel kolom krijgt tweemaal de ruimte van de Kunstenaar kolom
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Kunstenaar", Product::getArtist).setFlex(1f);
```

Belangrijke flex-gedragingen:

- **Flex waarde**: Bepaalt de proportie van beschikbare ruimte. Een kolom met flex=2 krijgt tweemaal de ruimte van een kolom met flex=1.
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
Breedte en flex-eigenschappen zijn wederzijds exclusief. Het instellen van de een verwijderd automatisch de andere. Gebruik breedte voor nauwkeurige controle of flex voor responsief gedrag.
:::

### Automatische sizing {#automatic-sizing}

Naast handmatige breedte- en flexinstellingen kunnen kolommen ook automatisch worden geschaald. Automatische sizing laat de `Table` optimale breedtes bepalen, hetzij door inhoud te analyseren of door ruimte proportioneel te verdelen.

#### Inhouds-gebaseerde automatische sizing {#content-based-auto-sizing}

Automatisch kolommen schalen op basis van hun inhoud. De `Table` analyseert de gegevens in elke kolom en berekent de optimale breedte om de inhoud zonder afbreken weer te geven.

```java
// Auto-size alle kolommen om inhoud te passen
table.setColumnsToAutoSize().thenAccept(c -> {
    // Sizing compleet - kolommen passen nu bij hun inhoud
});

// Auto-size specifieke kolom
table.setColumnToAutoSize("beschrijving");
```

#### Proportionele auto-fit {#proportional-auto-fit}

Verdeel alle kolommen proportioneel over de beschikbare `Table` breedte. Deze operatie stelt elke kolom in op flex=1, zodat ze de totale `Table` breedte gelijk delen, ongeacht hun inhouds lengte. Kolommen zullen uitbreiden of krimpen om de exacte `Table` afmetingen in te vullen zonder resterende ruimte.

```java
// Laat kolommen passen bij de tabel breedte (equivalent aan het instellen van flex=1 op alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Alle kolommen delen nu de ruimte gelijkmatig
});
```

:::info Async Operaties
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

Kolom resizing geeft gebruikers controle over hoeveel ruimte elke kolom inneemt door de kolomranden te slepen.

Je kunt het resizing gedrag op individuele kolommen controleren bij het opbouwen van je tabel:

```java
// Sta gebruikers resizing toe voor deze kolom
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Disabel resizing
table.addColumn("ID", Product::getId).setResizable(false);

// Controleer huidige staat
boolean canResize = column.isResizable();
```

Voor tabellen waar je consistente gedrag wilt over meerdere kolommen, gebruik je de bulkconfiguratiemethoden:

```java
// Maak alle bestaande kolommen resizebaar
table.setColumnsToResizable(true);

// Vergrendel alle bestaande kolommen tegen resizing
table.setColumnsToResizable(false);
```

### Kolom herschikken {#column-reordering}

Kolom herschikken stelt gebruikers in staat om kolommen te slepen en neer te zetten in hun voorkeursvolgorde, waardoor de `Table` lay-out kan worden gepersonaliseerd voor hun workflow.

Configureer kolombeweging machtigingen bij het instellen van je tabel:

```java
// Sta gebruikers toe deze kolom te verplaatsen
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Voorkom kolom verplaatsing (nuttig voor ID- of actiekolommen)
table.addColumn("ID", Product::getId).setMovable(false);

// Controleer huidige staat
boolean canMove = column.isMovable();
```

Pas de bewegingsinstellingen gelijktijdig toe op meerdere kolommen:

```java
// Sta herschikken toe voor alle bestaande kolommen
table.setColumnsToMovable(true);

// Schakel herschikken uit voor alle bestaande kolommen  
table.setColumnsToMovable(false);
```

:::note Bulk Operaties
De `setColumnsToResizable()` en `setColumnsToMovable()` methoden hebben alleen invloed op bestaande kolommen op het moment van aanroep. Ze stellen geen standaard in voor toekomstige kolommen.
:::

### Programmatiseerde kolomverplaatsing {#programmatic-column-movement} 

Naast slepen en neerzetten, kun je kolommen ook programmatiseerd verplaatsen op index of ID. Houd er rekening mee dat de index alleen is gebaseerd op zichtbare kolommen; verborgen kolommen worden genegeerd bij het berekenen van posities.

```java
// Verplaats kolom naar de eerste positie
table.moveColumn("titel", 0);

// Verplaats kolom naar de laatste positie
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Async verplaatsing met callback
table.moveColumn("beschrijving", 2).thenAccept(c -> {
    // Kolom succesvol verplaatst
});
```

## Evenementafhandeling {#event-handling}

De `Table` component genereert evenementen wanneer gebruikers met kolommen interageren, waardoor je kunt reageren op lay-outwijzigingen en gebruikersvoorkeuren opslaan.

Ondersteunde evenementen:

- `TableColumnResizeEvent`: Aangestoken wanneer een gebruiker een kolom resized door de rand te slepen.
- `TableColumnMoveEvent`: Aangestoken wanneer een gebruiker een kolom herschikt door de kop te slepen.

Je kunt luisteraars aan de `Table` koppelen om te reageren wanneer gebruikers de tabelindeling wijzigen.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Behandel kolom resize-evenement
  // Toegang: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Behandel kolom verplaats evenement  
  // Toegang: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
