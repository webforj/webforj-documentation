---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: a733a52b4d9ffb87eae039e9729b9cb9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

De `GoogleChart` component integreert de [Google Charts](https://developers.google.com/chart) bibliotheek in webforJ, zodat je toegang hebt tot grafiektype zoals staaf-, lijn-, taart-, geografisch en meer. Grafieken worden geconfigureerd met Java met een type, een dataset en een opties map die het uiterlijk en gedrag beheert.

<!-- INTRO_END -->

## Een grafiek maken {#creating-a-chart}

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

Om een grafiek te maken, specificeer je een grafiektype, configureer je de visuele opties en geef je de gegevens op die moeten worden weergegeven.

Dit voorbeeld maakt een geografische grafiek die omzetgegevens in verschillende landen weergeeft, met aangepaste kleuren, legendepositionering en grootte van het grafiekgebied:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Grafiektype {#chart-types}

De `GoogleChart` addon biedt een uitgebreide reeks grafiek types om aan verschillende datavisualisatiebehoeften te voldoen. Het kiezen van het juiste grafiektype is essentieel voor het effectief communiceren van het verhaal van de gegevens. Zie de galerij hieronder voor voorbeelden van veelvoorkomende grafieken die in een webforJ-app kunnen worden gebruikt.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Opties {#options}

De `GoogleChart` addon maakt uitgebreide aanpassing mogelijk via een verscheidenheid aan opties. Deze opties stellen je in staat om het uiterlijk en de functionaliteit van je grafieken aan te passen aan de behoeften van je app. Opties worden doorgegeven als een `Map<String, Object>` naar de `setOptions()` methode van de grafiek.

Hier is een voorbeeld voor het instellen van de opties van een grafiek:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Maandelijkse Omzet");
options.put("backgroundColor", "#EFEFEF");

// Pas de opties toe op de grafiek
chart.setOptions(options);
```

Voor meer informatie over de beschikbare opties voor specifieke grafieken, zie de [Google Visualization API referentie (Grafiek Galerij)](https://developers.google.com/chart/interactive/docs/gallery).

## Gegevens instellen {#setting-data}

Het visualiseren van gegevens met `GoogleChart` vereist een goede structuur en instelling van de gegevens. Deze gids leidt je door het voorbereiden van je gegevens en het toepassen ervan op je grafieken.

### Basisgegevens setup {#basic-data-setup}

De meest eenvoudige manier om de gegevens te definiëren, is door gebruik te maken van `List<Object>`, waarbij elke rij een lijst van waarden is.

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

Voor complexere datastructuren kun je kaarten gebruiken om rijen te vertegenwoordigen en deze vervolgens om te zetten naar het vereiste formaat.

```java
List<Object> data = new ArrayList<>();

// Header rij
data.add(Arrays.asList("Land", "Omzet"));

// Gegevens rijen
Map<String, Object> row1 = Map.of("Land", "Duitsland", "Omzet", 1000);
Map<String, Object> row2 = Map.of("Land", "Verenigde Staten", "Omzet", 1170);
Map<String, Object> row3 = Map.of("Land", "Brazilië", "Omzet", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Zodra de gegevens zijn voorbereid, kunnen ze worden toegepast op de GoogleChart met de setData methode.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Gegevens en opties laden vanuit JSON {#loading-data-and-options-from-json}

Je kunt ook gegevens en opties laden vanuit JSON-bestanden met behulp van Gson voor eenvoudigere beheersing. Deze aanpak helpt je om je gegevens en opties georganiseerd en eenvoudig te updaten.

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

## Grafiek visuals bijwerken {#updating-chart-visuals}

Het vernieuwen of bijwerken van het uiterlijk van je grafieken als reactie op gegevenswijzigingen, gebruikersinteracties of wijzigingen in visuele opties is eenvoudig met de `redraw()` methode. Deze methode zorgt ervoor dat je grafieken nauwkeurig en visueel in lijn blijven met de onderliggende gegevens of wijzigingen in hun instellingen.

Roep `redraw()` aan in scenario's zoals:

- **Na Gegevens Wijzigingen**: Zorg ervoor dat de grafiek eventuele updates aan de gegevensbron weergeeft.
- **Bij Wijzigingen van Opties**: Pas nieuwe styling of configuratiewijzigingen toe op de grafiek.
- **Voor Responsieve Aanpassingen**: Pas de lay-out of afmetingen van de grafiek aan wanneer de afmetingen van de container veranderen, zodat een optimale weergave op apparaten wordt gegarandeerd.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Grafieken exporteren als afbeeldingen {#exporting-charts-as-images}

De `getImageUri()` methode biedt een manier om je Google Charts te exporteren als base64-gecodeerde PNG-afbeeldingen. Deze methode is vooral nuttig voor het delen van grafieken buiten de webomgeving, het embedden ervan in e-mails of documenten, of gewoon voor archiveringsdoeleinden.

Roep `getImageUri()` aan op je grafiekinstantie nadat de grafiek volledig is gerenderd. Gewoonlijk wordt deze methode gebruikt binnen een "klaar" evenementenluisteraar om ervoor te zorgen dat de grafiek klaar is voor export:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nu kun je de imageUri gebruiken, bijvoorbeeld als de src-attribuut van een img-tag
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

De `GoogleChartSelectedEvent` wordt geactiveerd telkens wanneer een gebruiker een datapunten of segment in een Google Chart-component selecteert. Dit evenement stelt interactie met de geselecteerde grafiekgegevens mogelijk, en biedt details over wat is geselecteerd. Het evenement kan worden beluisterd door de `addSelectedListener()` methode op de `GoogleChart` instantie te gebruiken.

De `GoogleChartSelectedEvent` is nuttig in applicaties waar gebruikersinteractie met de grafiek noodzakelijk is.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Voeg de geselecteerde luisteraar toe aan de grafiek
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
De `GoogleChartSelectedEvent` biedt toegang tot de selectiegegevens, die kunnen worden opgehaald met de `getSelection()` methode op het grafiekobject. Deze methode retourneert een lijst van objecten, waarbij elk object de volgende eigenschappen bevat:

- **row**: De index van de rij in de gegevens tabel van de grafiek die is geselecteerd.
- **column**: De index van de kolom in de gegevens tabel, die optioneel is en van toepassing is op grafieken die selectie van individuele cellen toestaan, zoals een tabelgrafiek.

Voor grafieken zoals taartgrafieken of staafgrafieken wordt meestal alleen de `row` verstrekt, wat het geselecteerde datapunt aangeeft.

Hier is een voorbeeld van payload:
```java
[
  {
    "row": 3,  // De geselecteerde rij-index in de gegevens
    "column": 2  // (Optioneel) De geselecteerde kolom-index
  }
]
```

:::info Meerdere Datapunten Selecteren
Als de gebruiker meerdere datapunten selecteert, retourneert de `getSelection()` methode een array van objecten, waarbij elk een geselecteerd element vertegenwoordigt. De payload kan variëren op basis van het grafiektype en de interactie die de gebruiker uitvoert.
:::
