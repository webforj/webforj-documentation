---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 59dc1d0f2eff7880d818123654e8febf
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

De `Table` class gebruikt kolominstantie om te definiëren en aan te passen hoe data wordt weergegeven. Kolommen bepalen welke data wordt getoond, hoe het eruitziet en hoe gebruikers ermee kunnen interageren. Deze pagina behandelt kolomidentiteit, presentatie, formaten, gebruikersinteracties en gerelateerde evenementen.

## Kolomidentiteit {#column-identity}

De identiteit van een kolom definieert hoe deze wordt herkend in de `Table`. Dit omvat het label, de waarde die het biedt en of het zichtbaar of navigeerbaar is.

### Label {#label}

Het label van een kolom is de publieke identificator, die helpt om weergegeven data te verduidelijken.  

Gebruik `setLabel()` om het label in te stellen of te wijzigen.

:::tip
Standaard zal het label hetzelfde zijn als de kolom-ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Waardeproviders {#value-providers}

Een waardeprovider is een functie die verantwoordelijk is voor het vertalen van ruwe data uit de onderliggende dataset naar een formaat dat geschikt is voor weergave binnen een specifieke kolom. De functie, die je definieert, neemt een instantie van het rijdatatype (T) en retourneert de waarde die in de bijbehorende kolom voor die specifieke rij moet worden getoond.

Om een waardeprovider op een kolom in te stellen, gebruik je een van de `addColumn()` methoden van de `Table` component.

In de volgende snippet zal een kolom proberen gegevens toegankelijk te maken vanuit een JSON-object, en het alleen weergeven als de gegevens niet null zijn.

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

Het is mogelijk om de zichtbaarheid van een kolom in te stellen, waarmee je bepaalt of deze wel of niet zal worden weergegeven binnen de `Table`. Dit kan nuttig zijn bij het bepalen of gevoelige informatie moet worden getoond of niet.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navigeerbaar {#navigable}

De navigeerbare eigenschap bepaalt of gebruikers met een kolom kunnen interageren tijdens de navigatie. Door `setSuppressNavigable()` op true in te stellen, wordt de interactie van de gebruiker met de kolom beperkt, wat een alleen-lezen ervaring biedt.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Lay-out en opmaak {#layout-and-formatting}

Nadat de identiteit van een kolom is vastgesteld, is de volgende stap om te bepalen hoe de inhoud voor gebruikers verschijnt. Lay-outopties zoals uitlijning en vastzetten bepalen waar gegevens zich bevinden en hoe ze zichtbaar blijven terwijl je met een `Table` werkt.

### Uitlijning {#alignment}

Het instellen van de uitlijning van een kolom stelt je in staat om georganiseerde tabellen te maken, wat kan helpen gebruikers de verschillende secties in de `Table` te laten identificeren.

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

- `Column.Alignment.LEFT`: Geschikt voor tekstuele of beschrijvende gegevens waarbij een linkse stroom intuïtief is. Nuttig wanneer de start van de inhoud moet worden benadrukt.
- `Column.Alignment.CENTER`: Centraal uitgelijnde kolommen zijn ideaal voor kortere waarden, zoals een lettertype, status, of iets anders dat een uitgebalanceerde presentatie heeft.
- `Column.Alignment.RIGHT`: Overweeg een rechts uitgelijnde kolom voor numerieke waarden die handig zijn om snel door te bladeren, zoals datums, bedragen en percentages.

In het voorgaande voorbeeld is de laatste kolom voor `Kosten` rechts uitgelijnd voor een duidelijker visueel onderscheid.

### Vastzetten {#pinning}

Kolomvastzetten is een functie waarmee gebruikers een kolom aan een specifieke kant van de `Table` kunnen bevestigen of "vastzetten". Dit is nuttig wanneer bepaalde kolommen, zoals identificatoren of essentiële informatie, zichtbaar moeten blijven terwijl er horizontaal door een tabel wordt gescrold.

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

- `PinDirection.LEFT`: Zet de kolom aan de linkerkant vast.
- `PinDirection.RIGHT`: Zet de kolom aan de rechterkant vast.
- `PinDirection.AUTO`: Kolom verschijnt op basis van de invoerorde.

Vastzetten kan programmatisch worden ingesteld, waardoor je de pinrichting kunt wijzigen op basis van gebruikersinteracties of de logica van de app.

## Kolomformaten <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Vaste breedte {#fixed-width}

Stel een exacte breedte voor een kolom in met de methode `setWidth()`, waarbij de gewenste breedte in pixels wordt gespecificeerd:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

De breedte-eigenschap definieert de gewenste initiële breedte voor de kolom. Hoe deze breedte wordt gebruikt, hangt af van andere eigenschappen en kolomtype:

- **Reguliere kolommen**: Met alleen de breedte ingesteld, wordt de kolom weergegeven op de opgegeven breedte, maar kan deze proportioneel klein worden als de container te klein is. De oorspronkelijke breedte fungeert als de gewenste breedte, maar zonder expliciete minimumbeperkingen kan de kolom kleiner worden weergegeven dan de ingestelde breedte.
- [**Vastgezette kolommen**](#pinning): Handhaven altijd hun exacte breedte en nemen nooit deel aan responsief verkleinen.
- [**Flex kolommen**](#flex-sizing): Breedte instellen is onverenigbaar met flex. Gebruik ofwel breedte (vast) of flex (proportioneel), niet beide.

Als niet gespecificeerd, gebruikt de kolom zijn geschatte breedte op basis van contentanalyse van de eerste paar rijen.

```java
// Huidige breedte ophalen
float currentWidth = column.getWidth();
```

### Minimale breedte {#minimum-width}

Met de methode `setMinWidth()` kun je de minimale breedte van een kolom definiëren. Als de minimale breedte niet wordt opgegeven, berekent de `Table` de minimale breedte op basis van de kolominhoud.

```java
table.addColumn("Prijs", Product::getPrice).setMinWidth(100f);
```

De waarde die wordt doorgegeven, vertegenwoordigt de minimale breedte in pixels.

De minimale breedte-eigenschap controleert de kleinste breedte die een kolom kan hebben:

- **Reguliere kolommen**: Met alleen de minimale breedte ingesteld, gebruikt de kolom de minimale breedte als zowel de gewenste als de minimale breedte. Met breedte + minimale breedte kan de kolom worden verkleind van de breedte tot de minimale breedte, maar niet verder.
- [**Vastgezette kolommen**](#pinning): Als alleen de minimale breedte is ingesteld (geen breedte), wordt het de vaste breedte.
- [**Flex kolommen**](#flex-sizing): Voorkomt dat de kolom onder deze breedte krimpt, zelfs wanneer de containerspace beperkt is.

```java
// Huidige minimale breedte ophalen
float minWidth = column.getMinWidth();
```

### Maximale breedte {#maximum-width}

De methode `setMaxWidth()` beperkt hoe breed een kolom kan groeien, zodat kolommen met lange inhoud niet te breed worden en de leesbaarheid niet aantasten:

```java
table.addColumn("Beschrijving", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

De `maxWidth` eigenschap beperkt de groei van kolommen voor alle kolomtypes en zal nooit worden overschreden, ongeacht de inhoud, de containergrootte of flexinstellingen.

```java
// Huidige maximale breedte ophalen
float maxWidth = column.getMaxWidth();
```

### Flex sizing {#flex-sizing}

De methode `setFlex()` stelt proportionele kolomformaten in, waardoor kolommen beschikbare ruimte delen nadat vaste breedte kolommen zijn toegewezen:

```java
// Titelkolom krijgt tweemaal de ruimte van de Artiestkolom
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Artiest", Product::getArtist).setFlex(1f);
```

Belangrijke flex-gedragingen:

- **Flexwaarde**: Bepaalt de proportie van beschikbare ruimte. Een kolom met flex=2 krijgt tweemaal de ruimte van een kolom met flex=1.
- **Incompatibel met breedte**: Kan niet samen worden gebruikt met de breedte-eigenschap. Wanneer flex groter is dan nul, komt dit boven de breedte-instelling.
- **Respecteert beperkingen**: Werkt met minimum- en maximum breedtebeperkingen. Zonder een minimum breedte kunnen flexkolommen krimpen tot 0.

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
Breedte en flex eigenschappen zijn wederzijds exclusief. Het instellen van de een wist automatisch de andere. Gebruik breedte voor nauwkeurige controle of flex voor responsief gedrag.
:::

### Automatische sizing {#automatic-sizing}

Naast handmatige breedte- en flexinstellingen kunnen kolommen ook automatisch worden geformateerd. Automatische sizing laat de `Table` de optimale breedtes bepalen door ofwel de inhoud te analyseren ofdoor de ruimte proportioneel te verdelen.

#### Inhoudsgebaseerde auto-sizing {#content-based-auto-sizing}

Automatisch kolommen op basis van hun inhoud formateren. De `Table` analyseert de gegevens in elke kolom en berekent de optimale breedte om de inhoud zonder afbreking weer te geven.

```java
// Auto-size alle kolommen om inhoud te passen
table.setColumnsToAutoSize().thenAccept(c -> {
    // Sizing compleet - kolommen passen nu bij hun inhoud
});

// Auto-size specifieke kolom
table.setColumnToAutoSize("description");
```

#### Proportionele auto-fit {#proportional-auto-fit}

Verdeel alle kolommen proportioneel over de beschikbare `Table` breedte. Deze bewerking zet elke kolom op flex=1, zodat ze de totale `Table` breedte gelijk delen, ongeacht hun inhoudlengte. Kolommen zullen uitbreiden of inkrimpen om perfect in de exacte afmetingen van de `Table` te passen, met geen overige ruimte.

```java
// Pas kolommen aan de tafelbreedte aan (equivalent aan het instellen van flex=1 op alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Alle kolommen delen nu de ruimte gelijk
});
```

:::info Asynchrone bewerkingen
Auto-sizing methoden retourneren `PendingResult<Void>` omdat ze client-side berekeningen vereisen. Gebruik `thenAccept()` om code uit te voeren nadat de sizing compleet is. Als je niet hoeft te wachten op voltooiing, kun je de methoden zonder `thenAccept()` aanroepen
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

### Kolomvergroting {#column-resizing}

Kolomvergroting geeft gebruikers controle over hoeveel ruimte elke kolom in beslag neemt door aan de kolomranden te slepen.

Je kunt het resizing gedrag op individuele kolommen controleren bij het bouwen van je tabel:

```java
// Schakel gebruikersvergroting voor deze kolom in
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Verhinderen van vergroting
table.addColumn("ID", Product::getId).setResizable(false);

// Controleer huidige staat
boolean canResize = column.isResizable();
```

Voor tabellen waarin je consistente gedragingen wilt voor meerdere kolommen, gebruik de bulk configuratiemethoden:

```java
// Maak alle bestaande kolommen vergrootbaar
table.setColumnsToResizable(true);

// Vergrendel alle bestaande kolommen tegen vergroting
table.setColumnsToResizable(false);
```

### Kolomherordening {#column-reordering}

Kolomherordening stelt gebruikers in staat om kolommen te slepen en neer te zetten in hun voorkeur volgorde, waardoor de indeling van de `Table` gepersonaliseerd wordt voor hun workflow.

Configureer kolombewegingstoestemmingen bij het instellen van je tabel:

```java
// Sta gebruikers toe om deze kolom te verplaatsen
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Voorkom kolomverplaatsing (nuttig voor ID- of actie kolommen)
table.addColumn("ID", Product::getId).setMovable(false);

// Controleer huidige staat
boolean canMove = column.isMovable();
```

Pas bewegingsinstellingen gelijktijdig toe op meerdere kolommen:

```java
// Sta herordening voor alle bestaande kolommen toe
table.setColumnsToMovable(true);

// Voorkom herordening voor alle bestaande kolommen  
table.setColumnsToMovable(false);
```

:::note Bulk Bewerking
De methoden `setColumnsToResizable()` en `setColumnsToMovable()` hebben alleen invloed op bestaande kolommen op het moment van aanroep. Ze stellen geen standaardwaarden in voor toekomstige kolommen.
:::

### Programmatische kolombeweging {#programmatic-column-movement} 

Naast slepen en neerzetten kun je kolommen ook programmatisch verplaatsen op basis van index of ID. Houd er rekening mee dat de index alleen is gebaseerd op zichtbare kolommen; verborgen kolommen worden genegeerd bij het berekenen van posities.

```java
// Verplaats kolom naar eerste positie
table.moveColumn("titel", 0);

// Verplaats kolom naar laatste positie
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone beweging met callback
table.moveColumn("description", 2).thenAccept(c -> {
    // Kolom succesvol verplaatst
});
```

## Evenementafhandeling {#event-handling}

De `Table` component genereert evenementen wanneer gebruikers interactie hebben met kolommen, zodat je kunt reageren op lay-outwijzigingen en de voorkeuren van gebruikers kunt opslaan.

Ondersteunde evenementen:

- `TableColumnResizeEvent`: Geactiveerd wanneer een gebruiker een kolom vergroot door aan de rand te slepen.
- `TableColumnMoveEvent`: Geactiveerd wanneer een gebruiker een kolom herordeert door de kop te slepen.

Je kunt luisteraars aan de `Table` bevestigen om te reageren wanneer gebruikers de tabelindeling wijzigen.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Behandel kolomvergroting evenement
  // Toegang: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Behandel kolomverplaatsing evenement  
  // Toegang: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
