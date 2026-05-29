---
title: Google Charts
sidebar_position: 50
_i18n_hash: 31a5912850ae78f116c6738b99910d25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

De `GoogleChart` component integreert de [Google Charts](https://developers.google.com/chart) bibliotheek in webforJ, waardoor je toegang krijgt tot grafiektypes zoals staafdiagram, lijndiagram, cirkeldiagram, geografische kaarten en meer. Grafieken worden geconfigureerd met Java door een type, een dataset en een opties kaart die het uiterlijk en de functionaliteit beheert.

<!-- INTRO_END -->

## Een grafiek maken {#creating-a-chart}

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

Om een grafiek te maken, specificeer je een grafiektype, configureer je de visuele opties en geef je de data op die weergegeven moet worden.

Dit voorbeeld maakt een geografische grafiek die de omzetdata over verschillende landen in kaart brengt, met aangepaste kleuren, legendepositie en sizing van de grafiek:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/resources/static/css/googlecharts/chart.css',
]}
height='300px'
/>

## Grafiektypes {#chart-types}

De `GoogleChart` addon biedt een uitgebreide reeks grafiektypes om aan verschillende vereisten voor datavisualisatie te voldoen. Het selecteren van het juiste grafiektype is essentieel voor het effectief communiceren van het verhaal van de data. Zie de galerij hieronder voor voorbeelden van veelvoorkomende grafieken die in een webforJ-app kunnen worden gebruikt.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/resources/static/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Opties {#options}

De `GoogleChart` addon maakt uitgebreide aanpassing mogelijk via een verscheidenheid aan opties. Deze opties stellen je in staat om de uitstraling en functionaliteit van je grafieken aan te passen aan de behoeften van je app. Opties worden doorgegeven als een `Map<String, Object>` aan de `setOptions()` methode van de grafiek.

Hier is een voorbeeld voor het instellen van de opties van een grafiek:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Maandelijkse Omzet");
options.put("backgroundColor", "#EFEFEF");

// Pas de opties toe op de grafiek
chart.setOptions(options);
```

Voor meer informatie over de beschikbare opties voor specifieke grafieken, zie de [Google Visualization API reference (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Gegevens instellen {#setting-data}

Het visualiseren van gegevens met `GoogleChart` vereist een goede structuur en instelling van de gegevens. Deze gids helpt je bij het voorbereiden van je gegevens en het toepassen ervan op je grafieken.

### Basisgegevensstructuur {#basic-data-setup}

De meest eenvoudige manier om de gegevens te definiëren is door `List<Object>` te gebruiken, waarbij elke rij een lijst van waarden is.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Taak", "Uren per Dag"));
data.add(Arrays.asList("Werk", 11));
data.add(Arrays.asList("Eten", 2));
data.add(Arrays.asList("Verhuizen", 2));
data.add(Arrays.asList("TV Kijken", 2));
data.add(Arrays.asList("Slapen", 7));
chart.setData(data);
```

### Gebruik van kaarten voor complexere structuren {#using-maps-for-more-complex-structures}

Voor complexere datastructuren kun je kaarten gebruiken om rijen voor te stellen en deze vervolgens om te zetten naar het vereiste formaat.

```java
List<Object> data = new ArrayList<>();

// Koprij
data.add(Arrays.asList("Land", "Omzet"));

// Data rijen
Map<String, Object> row1 = Map.of("Land", "Duitsland", "Omzet", 1000);
Map<String, Object> row2 = Map.of("Land", "Verenigde Staten", "Omzet", 1170);
Map<String, Object> row3 = Map.of("Land", "Brazilië", "Omzet", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Zodra de gegevens zijn voorbereid, kunnen ze op de GoogleChart worden toegepast met de `setData` methode.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Gegevens en opties laden vanuit JSON {#loading-data-and-options-from-json}

Je kunt ook gegevens en opties laden vanuit JSON-bestanden met Gson voor een gemakkelijkere beheersing. Deze aanpak helpt om je gegevens en opties georganiseerd en eenvoudig te onderhouden.

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

## Grafische weergaves bijwerken {#updating-chart-visuals}

Het vernieuwen of bijwerken van het uiterlijk van je grafieken als reactie op gegevenswijzigingen, gebruikersinteracties of aanpassingen van visuele opties is eenvoudig met de `redraw()` methode. Deze methode zorgt ervoor dat je grafieken accuraat blijven en visueel overeenkomen met de onderliggende gegevens of wijzigingen in hun instellingen.

Roep `redraw()` aan in scenario's zoals:

- **Na Gegevenswijzigingen**: Zorgt ervoor dat de grafiek eventuele updates van de gegevensbron weerspiegelt.
- **Bij Wijziging van Opties**: Past nieuwe styling of configuratiewijzigingen toe op de grafiek.
- **Voor Responsieve Aanpassingen**: Past de lay-out of grootte van de grafiek aan wanneer de afmetingen van de container veranderen, wat zorgt voor een optimale weergave op apparaten.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/resources/static/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Grafieken exporteren als afbeeldingen {#exporting-charts-as-images}

De `getImageUri()` methode biedt een manier om je Google Charts te exporteren als base64-gecodeerde PNG-afbeeldingen. Deze methode is bijzonder nuttig voor het delen van grafieken buiten de webomgeving, het inbedden ervan in e-mails of documenten, of simpelweg voor archiveringsdoeleinden.

Roep `getImageUri()` aan op je graafinstantie nadat de grafiek volledig is weergegeven. Gewoonlijk wordt deze methode gebruikt binnen een "ready" evenementlistener om ervoor te zorgen dat de grafiek klaar is voor export:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nu kun je de imageUri gebruiken, bijvoorbeeld als de src-attribuut van een img-tag
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

De `GoogleChartSelectedEvent` wordt getriggerd wanneer een gebruiker een datapunt of segment selecteert in een Google Chart component. Dit evenement maakt interactie mogelijk met de geselecteerde grafiekgegevens en biedt details over wat er is geselecteerd. Het evenement kan worden beluisterd met de `addSelectedListener()` methode op de `GoogleChart` instantie.

De `GoogleChartSelectedEvent` is nuttig in toepassingen waar gebruikersinteractie met de grafiek noodzakelijk is. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Voeg de geselecteerde listener aan de grafiek toe
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
De `GoogleChartSelectedEvent` geeft toegang tot de selectiedata, die kan worden opgehaald met de `getSelection()` methode op het grafiekobject. Deze methode retourneert een lijst van objecten, waarbij elk object de volgende eigenschappen bevat:

- **row**: De index van de rij in de datatabel van de grafiek die is geselecteerd.
- **column**: De index van de kolom in de datatabel, die optioneel is en van toepassing is op grafieken die de selectie van individuele cellen toestaan, zoals een tabelgrafiek.
  
Voor grafieken zoals cirkeldiagrammen of staafdiagrammen wordt doorgaans alleen de `row` opgegeven, wat de geselecteerde datapunten aangeeft.

Hier is een voorbeeld van de payload:
```java
[
  {
    "row": 3,  // De geselecteerde rij index in de data
    "column": 2  // (Optioneel) De geselecteerde kolom index
  }
]
```

:::info Meerdere Datapunten Selecteren
Als de gebruiker meerdere datapunten selecteert, retourneert de `getSelection()` methode een array van objecten, elk die een geselecteerd element vertegenwoordigt. De payload kan variëren afhankelijk van het grafiektype en de interactie die de gebruiker uitvoert.
:::
