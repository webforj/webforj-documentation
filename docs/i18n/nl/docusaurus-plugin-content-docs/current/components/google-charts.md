---
title: Google Charts
sidebar_position: 50
_i18n_hash: 7421699c19919de6aab7db8a36123524
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

De `GoogleChart` component integreert de [Google Charts](https://developers.google.com/chart) bibliotheek in webforJ, waardoor je toegang hebt tot grafiektiperen zoals staaf, lijn, taart, geo, en meer. Grafieken worden geconfigureerd met Java met een type, een gegevensset, en een opties map die het uiterlijk en gedrag controleert.

<!-- INTRO_END -->

## Een grafiek maken {#creating-a-chart}

:::info Het importeren van Google Charts
Om de `GoogleChart` klasse in je app te gebruiken, gebruik je de volgende XML in je POM-bestand:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Om een grafiek te maken, specificeer je een grafiektype, configureer je de visuele opties en geef je de gegevens op die weergegeven moeten worden.

Dit voorbeeld maakt een geo-grafiek die omzetgegevens over verschillende landen in kaart brengt, met aangepaste kleuren, legendepositie en afmetingen van het grafiekgebied:

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Grafiektiperen {#chart-types}

De `GoogleChart` addon biedt een uitgebreid scala aan grafiektiperen geschikt voor verschillende gegevensvisualisatiebehoeften. Het kiezen van het juiste grafiektype is essentieel om het verhaal van de gegevens effectief te communiceren. Zie de galerij hieronder voor voorbeelden van veelvoorkomende grafieken die in een webforJ-app kunnen worden gebruikt.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Opties {#options}

De `GoogleChart` addon stelt uitgebreide aanpassing mogelijk via een verscheidenheid aan opties. Deze opties stellen je in staat om het uiterlijk en de functionaliteit van je grafieken af te stemmen op de behoeften van je app. Opties worden doorgegeven als een `Map<String, Object>` aan de `setOptions()` methode van de grafiek. 

Hier is een voorbeeld voor het instellen van de opties van een grafiek:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Maandelijkse Omzet");
options.put("backgroundColor", "#EFEFEF");

// Pas de opties toe op de grafiek
chart.setOptions(options);
```

Voor meer informatie over de opties die beschikbaar zijn voor specifieke grafieken, zie de [Google Visualization API referentie (Grafiekgalerij)](https://developers.google.com/chart/interactive/docs/gallery).

## Gegevens instellen {#setting-data}

Het visualiseren van gegevens met `GoogleChart` vereist een goede structuur en het instellen van de gegevens. Deze gids helpt je bij het voorbereiden van je gegevens en het toepassen ervan op je grafieken.

### Basisgegevensinstelling {#basic-data-setup}

De eenvoudigste manier om de gegevens te definiëren, is door `List<Object>` te gebruiken, waarbij elke rij een lijst van waarden is.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Taak", "Uren per Dag"));
data.add(Arrays.asList("Werk", 11));
data.add(Arrays.asList("Eten", 2));
data.add(Arrays.asList("Pendelen", 2));
data.add(Arrays.asList("TV Kijken", 2));
data.add(Arrays.asList("Slaap", 7));
chart.setData(data);
```

### Gebruik van maps voor complexere structuren {#using-maps-for-more-complex-structures}

Voor complexere gegevensstructuren kun je maps gebruiken om rijen voor te stellen en deze vervolgens om te zetten naar het vereiste formaat.

```java
List<Object> data = new ArrayList<>();

// Hoofdrij
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

Je kunt ook gegevens en opties laden vanuit JSON-bestanden met behulp van Gson voor eenvoudigere beheersing. Deze aanpak helpt om je gegevens en opties georganiseerd en gemakkelijk te updaten.

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

## Grafiekvisuals bijwerken {#updating-chart-visuals}

Het verversen of bijwerken van het uiterlijk van je grafieken in reactie op gegevenswijzigingen, gebruikersinteracties of aanpassingen van visuele opties is eenvoudig met de `redraw()` methode. Deze methode zorgt ervoor dat je grafieken nauwkeurig blijven en visueel in lijn zijn met de onderliggende gegevens of eventuele wijzigingen in hun instellingen.

Roep `redraw()` aan in situaties zoals:

- **Na Gegevenswijzigingen**: Zorgt ervoor dat de grafiek eventuele updates aan de gegevensbron weerspiegelt.
- **Bij Het Wijzigen van Opties**: Past nieuwe styling of configuratiewijzigingen toe op de grafiek.
- **Voor Responsieve Aanpassingen**: Past de lay-out of grootte van de grafiek aan wanneer de afmetingen van de container veranderen, waardoor optimale weergave op verschillende apparaten wordt gegarandeerd.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Grafieken exporteren als afbeeldingen {#exporting-charts-as-images}

De `getImageUri()` methode biedt een manier om je Google Charts als base64-gecodeerde PNG-afbeeldingen te exporteren. Deze methode is bijzonder nuttig voor het delen van grafieken buiten de webomgeving, het inbedden ervan in e-mails of documenten, of simpelweg voor archiveringsdoeleinden.

Roep `getImageUri()` aan op je grafiekinstantie nadat de grafiek volledig is weergegeven. Meestal wordt deze methode gebruikt binnen een "gereed" evenementlistener om ervoor te zorgen dat de grafiek klaar is voor export:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nu kun je de imageUri gebruiken, bijvoorbeeld als de src-attribuut van een img-tag
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

De `GoogleChartSelectedEvent` wordt geactiveerd elke keer dat een gebruiker een datapunten of segment in een Google Chart component selecteert. Dit evenement maakt interactie met de geselecteerde grafiekgegevens mogelijk, met details over wat is geselecteerd. Het evenement kan worden beluisterd door de `addSelectedListener()` methode op de `GoogleChart` instantie te gebruiken.

De `GoogleChartSelectedEvent` is nuttig in applicaties waar gebruikersinteractie met de grafiek noodzakelijk is. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Voeg de geselecteerde listener aan de grafiek toe
chart.addSelectedListener(event -> {
  // Verkrijg de selectie
  List<Object> selection = chart.getSelection();
  
  // Behandel het geselecteerde evenement
  if (!selection.isEmpty()) {
    System.out.println("Geselecteerde Rij: " + selection.get(0));
    // Verdere verwerking op basis van de rij/kolom van de selectie
  }
});
```

### Payload {#payload}
De `GoogleChartSelectedEvent` biedt toegang tot de selectiegegevens, die kunnen worden opgehaald met de `getSelection()` methode op het grafiekobject. Deze methode retourneert een lijst van objecten, waarbij elk object de volgende eigenschappen bevat:

- **rij**: De index van de rij in de gegevens tabel van de grafiek die is geselecteerd.
- **kolom**: De index van de kolom in de gegevens tabel, wat optioneel is en geldt voor grafieken die selectie van individuele cellen toelaten, zoals een tabelgrafiek.
  
Voor grafieken zoals taartgrafieken of staafgrafieken wordt meestal alleen de `rij` opgegeven, die het geselecteerde datapunt aangeeft.

Hier is een voorbeeld van payload:
```java
[
  {
    "row": 3,  // De geselecteerde rij-index in de gegevens
    "column": 2  // (Optioneel) De geselecteerde kolomindex
  }
]
```

:::info Meerdere gegevenspunten selecteren
Als de gebruiker meerdere gegevenspunten selecteert, retourneert de `getSelection()` methode een array van objecten, elk met een geselecteerd element. De payload kan variëren op basis van het grafiektype en de interactie die de gebruiker uitvoert.
:::
