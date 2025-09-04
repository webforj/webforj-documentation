---
title: Google Charts
sidebar_position: 50
_i18n_hash: b477c90cfb24a59329f3047d7ae7d24c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Korte overzicht van de component en wat het is/doet -->

:::info Importeren van Google Charts
Om de `GoogleChart`-klasse in uw app te gebruiken, gebruikt u de volgende XML in uw POM-bestand:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

De `GoogleChart`-klasse is een uitgebreide oplossing voor het inbedden van rijke, interactieve grafieken binnen webapplicaties. Deze klasse fungeert als een brug naar de [Google Charts](https://developers.google.com/chart) bibliotheek, die een breed scala aan grafiektypen biedt die geschikt zijn voor elke datavisualisatietaak.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Grafiektypen {#chart-types}

De `GoogleChart`-plugin biedt een uitgebreide reeks grafiektypen om aan verschillende behoeften voor datavisualisatie te voldoen. Het selecteren van het juiste grafiektype is essentieel voor het effectief communiceren van het verhaal van de gegevens. Zie de galerij hieronder voor voorbeelden van veelvoorkomende grafieken die kunnen worden gebruikt in een webforJ-app.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Opties {#options}

De `GoogleChart`-plugin maakt uitgebreide aanpassing mogelijk via verschillende opties. Deze opties stellen u in staat om het uiterlijk en de functionaliteit van uw grafieken aan te passen aan de behoeften van uw app. Opties worden doorgegeven als een `Map<String, Object>` aan de `setOptions()`-methode van de grafiek. 

Hier is een voorbeeld van het instellen van de opties van een grafiek:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Maandelijkse Omzet");
options.put("backgroundColor", "#EFEFEF");

// Pas de opties toe op de grafiek
chart.setOptions(options);
```

Voor meer informatie over de beschikbare opties voor specifieke grafieken, zie de [Google Visualization API-referentie (Grafiekgalerie)](https://developers.google.com/chart/interactive/docs/gallery).

## Gegevens instellen {#setting-data}

Het visualiseren van gegevens met `GoogleChart` vereist een juiste structuur en instelling van de gegevens. Deze gids helpt u bij het voorbereiden van uw gegevens en het toepassen ervan op uw grafieken.

### Basis gegevensinstelling {#basic-data-setup}

De eenvoudigste manier om de gegevens te definiëren is door gebruik te maken van `List<Object>`, waarbij elke rij een lijst van waarden is.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Taak", "Uren per Dag"));
data.add(Arrays.asList("Werk", 11));
data.add(Arrays.asList("Eten", 2));
data.add(Arrays.asList("Vervoer", 2));
data.add(Arrays.asList("TV Kijken", 2));
data.add(Arrays.asList("Slapen", 7));
chart.setData(data);
```

### Gebruik van kaarten voor complexere structuren {#using-maps-for-more-complex-structures}

Voor complexere datastructuren kunt u kaarten gebruiken om rijen voor te stellen en deze vervolgens om te zetten in het vereiste formaat.

```java
List<Object> data = new ArrayList<>();

// Headerrij
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

Zodra de gegevens zijn voorbereid, kunnen ze op de GoogleChart worden toegepast met de setData-methode.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Gegevens en opties laden vanuit JSON {#loading-data-and-options-from-json}

U kunt ook gegevens en opties laden vanuit JSON-bestanden met behulp van Gson voor eenvoudigere beheersbaarheid. Deze benadering helpt om uw gegevens en opties georganiseerd en eenvoudig bij te werken.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Jaar", "Verkopen", "Uitgaven"));
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

## Bijwerken van grafiekvisualisaties {#updating-chart-visuals}

Het vernieuwen of bijwerken van het uiterlijk van uw grafieken als reactie op gegevenswijzigingen, gebruikersinteracties of aanpassingen van visuele opties is eenvoudig met de `redraw()`-methode. Deze methode zorgt ervoor dat uw grafieken accuraat blijven en visueel overeenkomen met de onderliggende gegevens of wijzigingen in hun instellingen.

Roep `redraw()` aan in scenario's zoals:

- **Na Gegevenswijzigingen**: Zorgt ervoor dat de grafiek eventuele updates aan de gegevensbron reflecteert.
- **Bij Wijzigen van Opties**: Past nieuwe styling of configuratiewijzigingen toe op de grafiek.
- **Voor Responsieve Aanpassingen**: Past de lay-out of grootte van de grafiek aan wanneer de afmetingen van de container veranderen, zodat een optimale weergave op verschillende apparaten wordt gegarandeerd.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Grafieken exporteren als afbeeldingen {#exporting-charts-as-images}

De `getImageUri()`-methode biedt een manier om uw Google Charts als base64-gecodeerde PNG-afbeeldingen te exporteren. Deze methode is bijzonder nuttig voor het delen van grafieken buiten de webomgeving, het inbedden ervan in e-mails of documenten, of simpelweg voor archiveringsdoeleinden.

Roep `getImageUri()` aan op uw grafiekinstantie nadat de grafiek volledig is weergegeven. Gewoonlijk wordt deze methode gebruikt binnen een "ready"-evenementlistener om ervoor te zorgen dat de grafiek klaar is voor export:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Nu kunt u de imageUri gebruiken, bijvoorbeeld als de src-attribuut van een img-tag
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

De `GoogleChartSelectedEvent` wordt geactiveerd wanneer een gebruiker een datapunt of segment in een Google Chart-component selecteert. Dit evenement maakt interactie met de geselecteerde grafiekgegevens mogelijk, met details over wat is geselecteerd. Het evenement kan worden beluisterd door de `addSelectedListener()`-methode op de `GoogleChart`-instantie te gebruiken.

De `GoogleChartSelectedEvent` is nuttig in applicaties waar gebruikersinteractie met de grafiek noodzakelijk is. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Voeg de geselecteerde listener toe aan de grafiek
chart.addSelectedListener(event -> {
    // Verkrijg de selectie
    List<Object> selection = chart.getSelection();
    
    // Verwerk het geselecteerde evenement
    if (!selection.isEmpty()) {
        System.out.println("Geselecteerde Rij: " + selection.get(0));
        // Verdere verwerking op basis van de rij/kolom van de selectie
    }
});
```

### Payload {#payload}
De `GoogleChartSelectedEvent` biedt toegang tot de selectiegegevens, die kunnen worden opgehaald met de `getSelection()`-methode op het grafiekobject. Deze methode retourneert een lijst met objecten, waarbij elk object de volgende eigenschappen bevat:

- **row**: De index van de rij in de gegevens tabel van de grafiek die is geselecteerd.
- **column**: De index van de kolom in de gegevens tabel, die optioneel is en geldt voor grafieken die de selectie van individuele cellen toestaan, zoals een tabelgrafiek.
  
Voor grafieken zoals taartgrafieken of staafgrafieken wordt meestal alleen de `row` verstrekt, die het geselecteerde datapunt aangeeft.

Hier is een voorbeeld van de payload:
```java
[
  {
    "row": 3,  // De geselecteerde rij-index in de gegevens
    "column": 2  // (Optioneel) De geselecteerde kolomindex
  }
]
```

:::info Meerdere Datapunten Selecteren
Als de gebruiker meerdere datapunten selecteert, retourneert de `getSelection()`-methode een array van objecten, waarvan er elke een geselecteerd element vertegenwoordigt. De payload kan variëren op basis van het grafiektype en de interactie die de gebruiker uitvoert.
:::
