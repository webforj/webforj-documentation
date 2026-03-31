---
title: Google Charts
sidebar_position: 50
_i18n_hash: 7421699c19919de6aab7db8a36123524
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

Die `GoogleChart`-Komponente integriert die [Google Charts](https://developers.google.com/chart)-Bibliothek in webforJ und ermöglicht Ihnen Zugriff auf Diagrammtypen wie Balken, Linien, Kuchen, Geo und mehr. Diagramme werden mit Java unter Verwendung eines Typs, eines Datensatzes und einer Optionskarte konfiguriert, die das Erscheinungsbild und das Verhalten steuert.

<!-- INTRO_END -->

## Erstellen eines Diagramms {#creating-a-chart}

:::info Importieren von Google Charts
Um die `GoogleChart`-Klasse in Ihrer App zu verwenden, verwenden Sie den folgenden XML-Code in Ihrer POM-Datei:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Um ein Diagramm zu erstellen, geben Sie einen Diagrammtyp an, konfigurieren Sie seine visuellen Optionen und stellen Sie die anzuzeigenden Daten zur Verfügung.

Dieses Beispiel erstellt ein Geo-Diagramm, das Einnahmedaten über verschiedene Länder abbildet, mit benutzerdefinierten Farben, Legendensortierung und Dimensionierung des Diagrammbereichs:

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Diagrammtypen {#chart-types}

Das `GoogleChart`-Addon bietet eine umfassende Auswahl an Diagrammtypen, die den unterschiedlichen Anforderungen an die Datenvisualisierung gerecht werden. Die Auswahl des geeigneten Diagrammtyps ist entscheidend, um die Geschichte der Daten effektiv zu kommunizieren. Siehe die Galerie unten für Beispiele gängiger Diagramme, die in einer webforJ-App verwendet werden können.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Optionen {#options}

Das `GoogleChart`-Addon ermöglicht eine umfangreiche Anpassung über eine Vielzahl von Optionen. Diese Optionen ermöglichen es Ihnen, das Aussehen und die Funktionalität Ihrer Diagramme an die Bedürfnisse Ihrer App anzupassen. Optionen werden als `Map<String, Object>` an die Methode `setOptions()` des Diagramms übergeben. 

Hier ist ein Beispiel zum Festlegen der Optionen eines Diagramms:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monatliche Einnahmen");
options.put("backgroundColor", "#EFEFEF");

// Wenden Sie die Optionen auf das Diagramm an
chart.setOptions(options);
```

Für weitere Informationen zu den verfügbaren Optionen für bestimmte Diagramme siehe die [Google Visualization API-Dokumentation (Diagrammgalerie)](https://developers.google.com/chart/interactive/docs/gallery).

## Daten festlegen {#setting-data}

Um Daten mit `GoogleChart` zu visualisieren, müssen die Daten korrekt strukturiert und festgelegt werden. Dieser Leitfaden führt Sie durch die Vorbereitung Ihrer Daten und deren Anwendung auf Ihre Diagramme.

### Grundlegende Datenstrukturierung {#basic-data-setup}

Die einfachste Möglichkeit, die Daten zu definieren, besteht darin, `List<Object>` zu verwenden, wobei jede Zeile eine Liste von Werten ist.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Aufgabe", "Stunden pro Tag"));
data.add(Arrays.asList("Arbeit", 11));
data.add(Arrays.asList("Essen", 2));
data.add(Arrays.asList("Pendeln", 2));
data.add(Arrays.asList("Fernsehen", 2));
data.add(Arrays.asList("Schlafen", 7));
chart.setData(data);
```

### Verwendung von Maps für komplexere Strukturen {#using-maps-for-more-complex-structures}

Für komplexere Datenstrukturen können Sie Maps verwenden, um Zeilen darzustellen und diese dann in das erforderliche Format umzuwandeln.

```java
List<Object> data = new ArrayList<>();

// Kopfzeile
data.add(Arrays.asList("Land", "Einnahmen"));

// Datenzeilen
Map<String, Object> row1 = Map.of("Land", "Deutschland", "Einnahmen", 1000);
Map<String, Object> row2 = Map.of("Land", "Vereinigte Staaten", "Einnahmen", 1170);
Map<String, Object> row3 = Map.of("Land", "Brasilien", "Einnahmen", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Sobald die Daten vorbereitet sind, können sie mit der Methode setData auf das GoogleChart angewendet werden.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Laden von Daten und Optionen aus JSON {#loading-data-and-options-from-json}

Sie können auch Daten und Optionen aus JSON-Dateien mit Gson laden, um die Verwaltung zu erleichtern. Dieser Ansatz hilft, Ihre Daten und Optionen organisiert und einfach zu aktualisieren.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Jahr", "Verkäufe", "Ausgaben"));
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

## Aktualisieren der Diagrammansicht {#updating-chart-visuals}

Das Aktualisieren oder Ändern des Erscheinungsbildes Ihrer Diagramme als Reaktion auf Datenänderungen, Benutzerinteraktionen oder Anpassungen der visuellen Optionen ist mit der Methode `redraw()` ganz einfach. Diese Methode stellt sicher, dass Ihre Diagramme genau und visuell mit den zugrunde liegenden Daten oder Änderungen ihrer Einstellungen übereinstimmen.

Rufen Sie `redraw()` in Szenarien wie diesen auf:

- **Nach Datenänderungen**: Stellt sicher, dass das Diagramm alle Updates an seiner Datenquelle widerspiegelt.
- **Bei Änderung von Optionen**: Wendet neue Stile oder Konfigurationsänderungen auf das Diagramm an.
- **Für responsive Anpassungen**: Passt das Layout oder die Größe des Diagramms an, wenn sich die Abmessungen des Containers ändern, um eine optimale Anzeige auf verschiedenen Geräten zu gewährleisten.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exportieren von Diagrammen als Bilder {#exporting-charts-as-images}

Die Methode `getImageUri()` bietet eine Möglichkeit, Ihre Google Charts als base64-kodierte PNG-Bilder zu exportieren. Diese Methode ist besonders nützlich, um Diagramme außerhalb der Webumgebung zu teilen, sie in E-Mails oder Dokumente einzubetten oder einfach nur zu Archivierungszwecken.

Rufen Sie `getImageUri()` auf Ihrem Diagramminstanz auf, nachdem das Diagramm vollständig gerendert wurde. Typischerweise wird diese Methode innerhalb eines "ready"-Ereignislisteners verwendet, um sicherzustellen, dass das Diagramm für den Export bereit ist:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Jetzt können Sie das imageUri verwenden, zum Beispiel als src-Attribut eines img-Tags
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

Das `GoogleChartSelectedEvent` wird ausgelöst, wenn ein Benutzer einen Datenpunkt oder -segment in einer Google Chart-Komponente auswählt. Dieses Ereignis ermöglicht die Interaktion mit den ausgewählten Diagrammdaten und gibt Details über das, was ausgewählt wurde, bereit. Das Ereignis kann mit der Methode `addSelectedListener()` auf der `GoogleChart`-Instanz abgehört werden.

Das `GoogleChartSelectedEvent` ist in Anwendungen nützlich, in denen eine Benutzerinteraktion mit dem Diagramm erforderlich ist. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Fügen Sie den ausgewählten Listener zum Diagramm hinzu
chart.addSelectedListener(event -> {
  // Auswahl abrufen
  List<Object> selection = chart.getSelection();
  
  // Ereignis der Auswahl verarbeiten
  if (!selection.isEmpty()) {
    System.out.println("Ausgewählte Zeile: " + selection.get(0));
    // Weitere Verarbeitung basierend auf der Auswahl der Zeile/Spalte
  }
});
```

### Payload {#payload}
Das `GoogleChartSelectedEvent` ermöglicht den Zugriff auf die Auswahldaten, die über die Methode `getSelection()` auf dem Diagrammobjekt abgerufen werden können. Diese Methode gibt eine Liste von Objekten zurück, wobei jedes Objekt folgende Eigenschaften enthält:

- **row**: Der Index der Zeile in der Datentabelle des Diagramms, die ausgewählt wurde.
- **column**: Der Index der Spalte in der Datentabelle, der optional ist und für Diagramme gilt, die die Auswahl einzelner Zellen ermöglichen, wie z. B. ein Tabellen-Diagramm.

Bei Diagrammen wie Kuchen- oder Balkendiagrammen wird normalerweise nur die `row` bereitgestellt, die den ausgewählten Datenpunkt angibt.

Hier ist ein Beispiel für die Payload:
```java
[
  {
    "row": 3,  // Der ausgewählte Zeilenindex in den Daten
    "column": 2  // (Optional) Der ausgewählte Spaltenindex
  }
]
```

:::info Auswählen mehrerer Datenpunkte
Wenn der Benutzer mehrere Datenpunkte auswählt, gibt die Methode `getSelection()` ein Array von Objekten zurück, von denen jedes ein ausgewähltes Element darstellt. Die Payload kann je nach Diagrammtyp und der Interaktion, die der Benutzer ausführt, variieren.
:::
