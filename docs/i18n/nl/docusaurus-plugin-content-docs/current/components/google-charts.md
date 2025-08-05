---
title: Google Charts
sidebar_position: 50
_i18n_hash: 84d9b14321275191deb78447cde7c7fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Korte overzicht van de component en wat het is/doet -->

:::info Importeren van Google Charts
Om de `GoogleChart` klasse in je app te gebruiken, gebruik je de volgende XML in je POM-bestand:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

De `GoogleChart` klasse is een uitgebreide oplossing voor het inbedden van rijke, interactieve grafieken binnen webapplicaties. Deze klasse fungeert als een brug naar de [Google Charts](https://developers.google.com/chart) bibliotheek, en biedt een grote verscheidenheid aan grafiektypen die geschikt zijn voor elke data-visualisatie taak.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Grafiektypen {#chart-types}

De `GoogleChart` addon biedt een uitgebreide reeks grafiektypen om aan verschillende data-visualisatievereisten te voldoen. Het kiezen van het juiste grafiektype is essentieel om het verhaal van de data effectief te communiceren. Bekijk de gallery hieronder voor voorbeelden van veelgebruikte grafieken die in een webforJ-app kunnen worden gebruikt.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Opties {#options}

De `GoogleChart` addon maakt uitgebreide aanpassing mogelijk via verschillende opties. Deze opties stellen je in staat om de uitstraling en functionaliteit van je grafieken af te stemmen op de behoeften van je app. Opties worden doorgegeven als een `Map<String, Object>` aan de `setOptions()` methode van de grafiek. 

Hier is een voorbeeld voor het instellen van de opties van een grafiek:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Maandelijkse Omzet");
options.put("backgroundColor", "#EFEFEF");

// Pas de opties toe op de grafiek
chart.setOptions(options);
```

Voor meer informatie over de beschikbare opties voor specifieke grafieken, zie de [Google Visualization API referentie (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Gegevens instellen {#setting-data}

Het visualiseren van gegevens met `GoogleChart` vereist een juiste structuur en instellen van de gegevens. Deze gids zal je helpen bij het voorbereiden van je gegevens en het toepassen ervan op je grafieken.

### Basisgegevenssetup {#basic-data-setup}

De meest eenvoudige manier om de gegevens te definiëren is door gebruik te maken van `List<Object>`, waarbij elke rij een lijst van waarden is.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Taak", "Uren per Dag"));
data.add(Arrays.asList("Werk", 11));
data.add(Arrays.asList("Eten", 2));
data.add(Arrays.asList("Reizen", 2));
data.add(Arrays.asList("TV Kijken", 2));
data.add(Arrays.asList("Slapen", 7));
chart.setData(data);
```

### Gebruik van maps voor complexere structuren {#using-maps-for-more-complex-structures}

Voor complexere datastructuren kun je maps gebruiken om rijen voor te stellen en deze vervolgens om te zetten in het vereiste formaat.

```java
List<Object> data = new ArrayList<>();

// Header rij
data.add(Arrays.asList("Land", "Omzet"));

// Gegevensrijen
Map<String, Object> row1 = Map.of("Land", "Duitsland", "Omzet", 1000);
Map<String, Object> row2 = Map.of("Land", "Verenigde Staten", "Omzet", 1170);
Map<String, Object> row3 = Map.of("Land", "Brazilië", "Omzet", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Zodra de gegevens zijn voorbereid, kunnen ze op de GoogleChart worden toegepast met de setData methode.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Gegevens en opties laden vanuit JSON {#loading-data-and-options-from-json}

Je kunt ook gegevens en opties laden vanuit JSON-bestanden met Gson voor gemakkelijker beheer. Deze aanpak helpt om je gegevens en opties georganiseerd te houden en eenvoudig bij te werken.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Jaar", "Verkoop", "Uitgaven"));
data.add(Arrays.asList("2013", 1000, 400));
data.add(Arrays.asList("2014", 1170, 460));
data.add(Arrays.asList("2015", 660, null)); 
data.add(Arrays.asList("2016", 1030, 540));
chart.setData(data);

Map<String, Object> options = new Gson().fromJson(
    Assets.contentOf("options.json"),
    new TypeToken<Map<String, Object>>() {}.getType()
);
chart.setOptions(options);
```

## Grafische weergaven bijwerken {#updating-chart-visuals}

Het vernieuwen of bijwerken van het uiterlijk van je grafieken als reactie op gegevenswijzigingen, gebruikersinteracties of aanpassingen van visuele opties is eenvoudig met de `redraw()` methode. Deze methode zorgt ervoor dat je grafieken nauwkeurig en visueel uitgelijnd blijven met de onderliggende gegevens of wijzigingen in hun instellingen.

Roep `redraw()` aan in scenario's zoals:

- **Na Gegevenswijzigingen**: Zorgt ervoor dat de grafiek eventuele updates van de gegevensbron weerspiegelt.
- **Bij Wijzigingen van Opties**: Past nieuwe stijlen of configuratiewijzigingen toe op de grafiek.
- **Voor Responsieve Aanpassingen**: Past de lay-out of grootte van de grafiek aan wanneer de afmetingen van de container veranderen, zodat deze optimaal wordt weergegeven op verschillende apparaten.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Grafieken exporteren als afbeeldingen {#exporting-charts-as-images}

De `getImageUri()` methode biedt een manier om je Google Charts te exporteren als base64-gecodeerde PNG-afbeeldingen. Deze methode is bijzonder nuttig voor het delen van grafieken buiten de webomgeving, ze in e-mails of documenten in te voegen, of gewoon voor archiveringsdoeleinden.

Roep `getImageUri()` aan op je grafiekinstantie nadat de grafiek volledig is gerenderd. Gewoonlijk wordt deze methode gebruikt binnen een "ready" eventlistener om ervoor te zorgen dat de grafiek klaar is voor export:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Nu kun je de imageUri gebruiken, bijvoorbeeld als de src-attribuut van een img-tag
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

De `GoogleChartSelectedEvent` wordt geactiveerd telkens wanneer een gebruiker een datapunt of segment in een Google Chart-component selecteert. Dit evenement maakt interactie met de geselecteerde grafiekgegevens mogelijk en biedt details over wat is geselecteerd. Het evenement kan worden beluisterd door de `addSelectedListener()` methode op de `GoogleChart` instantie te gebruiken.

De `GoogleChartSelectedEvent` is nuttig in toepassingen waar gebruikersinteractie met de grafiek nodig is.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Voeg de geselecteerde listener toe aan de grafiek
chart.addSelectedListener(event -> {
    // Verkrijg de selectie
    List<Object> selection = chart.getSelection();
    
    // Verwerk het geselecteerde evenement
    if (!selection.isEmpty()) {
        System.out.println("Geselecteerde Rij: " + selection.get(0));
        // Verdere verwerking op basis van de rij/kolom van selectie
    }
});
```

### Payload {#payload}
De `GoogleChartSelectedEvent` biedt toegang tot de selectiegegevens, die kunnen worden opgehaald met de `getSelection()` methode op het grafiekobject. Deze methode retourneert een lijst van objecten, waarbij elk object de volgende eigenschappen bevat:

- **row**: De index van de rij in de datatabel van de grafiek die is geselecteerd.
- **column**: De index van de kolom in de datatabel, die optioneel is en van toepassing is op grafieken die selectie van individuele cellen, zoals een tabelgrafiek, toestaan.
  
Voor grafieken zoals cirkeldiagrammen of staafgrafieken wordt meestal alleen de `row` gegeven, die het geselecteerde datapunt aangeeft.

Hier is een voorbeeld van payload:
```java
[
  {
    "row": 3,  // De geselecteerde rij-index in de gegevens
    "column": 2  // (Optioneel) De geselecteerde kolom-index
  }
]
```

:::info Meerdere Gegevenspunten Selecteren
Als de gebruiker meerdere gegevenspunten selecteert, retourneert de `getSelection()` methode een array van objecten, elk die een geselecteerd element vertegenwoordigt. De payload kan variëren afhankelijk van het grafiektype en de interactie die de gebruiker uitvoert.
:::
