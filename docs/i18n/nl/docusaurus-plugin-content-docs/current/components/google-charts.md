---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: 8e1916e526ef45c55ca75c6cd32a4e7f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

De `GoogleChart` component integreert de [Google Charts](https://developers.google.com/chart) bibliotheek in webforJ, waardoor je toegang krijgt tot diagramtypes zoals staafdiagrammen, lijndiagrammen, cirkeldiagrammen, geo-diagrammen en meer. Diagrammen worden geconfigureerd met Java met een type, een dataset en een opties kaart die het uiterlijk en gedrag beheert.

<!-- INTRO_END -->

## Een diagram maken {#creating-a-chart}

:::info Google Charts importeren
Om de `GoogleChart` klasse in je app te gebruiken, gebruik je de volgende XML in je POM-bestand:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Om een diagram te maken, geef je een diagramtype op, configureer je de visuele opties en lever je de gegevens om weer te geven.

Dit voorbeeld maakt een geo-diagram dat de omzetgegevens door verschillende landen in kaart brengt, met aangepaste kleuren, legenda-plaatsing en afmetingen van het diagramgebied:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Diagramtypes {#chart-types}

De `GoogleChart` addon biedt een uitgebreide reeks diagramtypes om aan verschillende behoeften voor gegevensvisualisatie te voldoen. Het kiezen van het juiste diagramtype is essentieel om het verhaal van de gegevens effectief over te brengen. Zie de galerij hieronder voor voorbeelden van veelvoorkomende diagrammen die in een webforJ-app kunnen worden gebruikt.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Opties {#options}

De `GoogleChart` addon stelt uitgebreide aanpassingen mogelijk via een verscheidenheid aan opties. Deze opties stellen je in staat om de uitstraling en functionaliteit van je diagrammen aan te passen aan de behoeften van je app. Opties worden doorgegeven als een `Map<String, Object>` aan de `setOptions()` methode van het diagram.

Hier is een voorbeeld van het instellen van de opties van een diagram:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Maandelijkse Omzet");
options.put("backgroundColor", "#EFEFEF");

// Pas de opties toe op het diagram
chart.setOptions(options);
```

Voor meer informatie over de beschikbare opties voor specifieke diagrammen, zie de [Google Visualisatie API referentie (Diagramgalerij)](https://developers.google.com/chart/interactive/docs/gallery).

## Gegevens instellen {#setting-data}

Het visualiseren van gegevens met `GoogleChart` vereist een juiste structuur en instelling van de gegevens. Deze gids leidt je door het voorbereiden van je gegevens en het toepassen ervan op je diagrammen.

### Basisgegevensopstelling {#basic-data-setup}

De eenvoudigste manier om de gegevens te definiëren is door gebruik te maken van `List<Object>`, waarbij elke rij een lijst van waarden is.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Taak", "Uren per Dag"));
data.add(Arrays.asList("Werken", 11));
data.add(Arrays.asList("Eten", 2));
data.add(Arrays.asList("Pendelen", 2));
data.add(Arrays.asList("Tv-kijken", 2));
data.add(Arrays.asList("Slapen", 7));
chart.setData(data);
```

### Kaarten gebruiken voor complexere structuren {#using-maps-for-more-complex-structures}

Voor complexere datastructuren kun je kaarten gebruiken om rijen weer te geven en deze vervolgens om te zetten in het vereiste formaat.

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

Zodra de gegevens zijn voorbereid, kunnen deze worden toegepast op de GoogleChart met behulp van de methoden setData.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Gegevens en opties laden vanuit JSON {#loading-data-and-options-from-json}

Je kunt ook gegevens en opties laden vanuit JSON-bestanden met behulp van Gson voor eenvoudigere beheer. Deze aanpak helpt je gegevens en opties georganiseerd en eenvoudig te updaten te houden.

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

## Het visueel bijwerken van diagrammen {#updating-chart-visuals}

De `GoogleChart` wordt automatisch bijgewerkt wanneer de gegevens, opties, type of selectie verandert. Je hoeft `redraw()` niet aan te roepen na het gebruik van `setData()`, `setOptions()`, `setType()` of `setSelection()`.

De component luistert ook naar gebeurtenis voor het wijzigen van de browservenster en wordt automatisch opnieuw getekend om in de nieuwe afmetingen te passen.

Roep `redraw()` aan voor wijzigingen in de containergrootte die geen vensterveranderingsgebeurtenis activeren. Voorbeelden zijn het uitbreiden van een paneel en het aanpassen van een zijbalk. Roep het aan nadat de nieuwe afmetingen van de container zijn toegepast.

De volgende demo werkt de gegevens van het diagram bij met `setData()`. Het diagram wordt automatisch opnieuw getekend wanneer de nieuwe gegevens zijn toegepast.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Diagrammen exporteren als afbeeldingen {#exporting-charts-as-images}

De `getImageUri()` methode biedt een manier om je Google Charts als base64-gecodeerde PNG-afbeeldingen te exporteren. Deze methode is vooral handig voor het delen van diagrammen buiten de webomgeving, het opnemen ervan in e-mails of documenten, of gewoon voor archiveringsdoeleinden.

Roep `getImageUri()` aan op je diagraminstantie nadat het diagram volledig is weergegeven. Gewoonlijk wordt deze methode gebruikt binnen een "ready" gebeurtenisluisteraar om ervoor te zorgen dat het diagram klaar is voor export:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nu kun je de imageUri gebruiken, bijvoorbeeld als de src-attribuut van een img-tag
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

De `GoogleChartSelectedEvent` wordt geactiveerd wanneer een gebruiker een datapunt of segment in een Google Chart component selecteert. Deze gebeurtenis maakt interactie met de geselecteerde diagramgegevens mogelijk en biedt details over wat er is geselecteerd. De gebeurtenis kan worden beluisterd door de `addSelectedListener()` methode op de `GoogleChart` instantie te gebruiken.

De `GoogleChartSelectedEvent` is nuttig in toepassingen waar gebruikersinteractie met het diagram noodzakelijk is.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Voeg de geselecteerde luisteraar toe aan het diagram
chart.addSelectedListener(event -> {
  // Verkrijg de selectie
  List<Object> selection = chart.getSelection();

  // Verwerk de geselecteerde gebeurtenis
  if (!selection.isEmpty()) {
    System.out.println("Geselecteerde Rij: " + selection.get(0));
    // Verdere verwerking op basis van de rij/kolom van selectie
  }
});
```

### Payload {#payload}
De `GoogleChartSelectedEvent` biedt toegang tot de selectiegegevens, die kunnen worden opgehaald met de `getSelection()` methode op het diagramobject. Deze methode retourneert een lijst van objecten, waarbij elk object de volgende eigenschappen bevat:

- **row**: De index van de rij in de gegevens tabel van het diagram die is geselecteerd.
- **column**: De index van de kolom in de gegevens tabel, wat optioneel is en van toepassing is op diagrammen waarbij de selectie van individuele cellen is toegestaan, zoals een tabeldiagram.

Voor diagrammen zoals cirkeldiagrammen of staafdiagrammen wordt meestal alleen de `row` verstrekt, wat het geselecteerde datapunt aangeeft.

Hier is een voorbeeld van payload:
```java
[
  {
    "row": 3,  // De geselecteerde rij-index in de gegevens
    "column": 2  // (Optioneel) De geselecteerde kolomindex
  }
]
```

:::info Meerdere Datapunten Selecteren
Als de gebruiker meerdere datapunten selecteert, retourneert de `getSelection()` methode een array van objecten, waarbij elk een geselecteerd element vertegenwoordigt. De payload kan variëren afhankelijk van het diagramtype en de interactie die de gebruiker uitvoert.
:::
