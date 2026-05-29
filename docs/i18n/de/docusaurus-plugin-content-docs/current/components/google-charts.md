---
title: Google Charts
sidebar_position: 50
_i18n_hash: 31a5912850ae78f116c6738b99910d25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

Die `GoogleChart`-Komponente integriert die [Google Charts](https://developers.google.com/chart)-Bibliothek in webforJ und ermöglicht den Zugriff auf Diagrammtypen wie Balken, Linien, Kuchen, Geo und mehr. Diagramme werden mit Java konfiguriert, indem ein Typ, ein Datensatz und eine Optionskarte bereitgestellt werden, die das Erscheinungsbild und das Verhalten steuern.

<!-- INTRO_END -->

## Erstellung eines Diagramms {#creating-a-chart}

:::info Importieren von Google Charts
Um die `GoogleChart`-Klasse in Ihrer Anwendung zu verwenden, fügen Sie die folgende XML in Ihre POM-Datei ein:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Um ein Diagramm zu erstellen, geben Sie einen Diagrammtyp an, konfigurieren Sie die visuellen Optionen und stellen Sie die anzuzeigenden Daten bereit.

Dieses Beispiel erstellt ein Geo-Diagramm, das Umsatzdaten über verschiedene Länder mit benutzerdefinierten Farben, einer Legendenpositionierung und einer Größenanpassung des Diagrammbereichs abbildet:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/resources/static/css/googlecharts/chart.css',
]}
height='300px'
/>

## Diagrammtypen {#chart-types}

Das `GoogleChart`-Addon bietet eine umfassende Auswahl an Diagrammtypen, die den verschiedenen Anforderungen an die Datenvisualisierung gerecht werden. Die Auswahl des geeigneten Diagrammtyps ist entscheidend, um die Geschichte der Daten effektiv zu kommunizieren. Siehe die Galerie unten für Beispiele gängiger Diagramme, die in einer webforJ-Anwendung verwendet werden können.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/resources/static/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Optionen {#options}

Das `GoogleChart`-Addon ermöglicht eine umfassende Anpassung durch eine Vielzahl von Optionen. Diese Optionen ermöglichen es Ihnen, das Aussehen und die Funktionalität Ihrer Diagramme an die Bedürfnisse Ihrer Anwendung anzupassen. Optionen werden als `Map<String, Object>` an die Methode `setOptions()` des Diagramms übergeben. 

Hier ist ein Beispiel zum Festlegen der Optionen eines Diagramms:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monatlicher Umsatz");
options.put("backgroundColor", "#EFEFEF");

// Wenden Sie die Optionen auf das Diagramm an
chart.setOptions(options);
```

Für weitere Informationen zu den für spezifische Diagramme verfügbaren Optionen siehe die [Google Visualization API-Dokumentation (Diagrammgalerie)](https://developers.google.com/chart/interactive/docs/gallery).

## Festlegen von Daten {#setting-data}

Die Visualisierung von Daten mit `GoogleChart` erfordert eine ordnungsgemäße Strukturierung und Festlegung der Daten. Diese Anleitung führt Sie durch die Vorbereitung Ihrer Daten und deren Anwendung auf Ihre Diagramme.

### Grundlegende Datenkonfiguration {#basic-data-setup}

Der geradlinigste Weg, die Daten zu definieren, besteht darin, `List<Object>` zu verwenden, wobei jede Zeile eine Liste von Werten ist.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Aufgabe", "Stunden pro Tag"));
data.add(Arrays.asList("Arbeiten", 11));
data.add(Arrays.asList("Essen", 2));
data.add(Arrays.asList("Pendeln", 2));
data.add(Arrays.asList("Fernsehen", 2));
data.add(Arrays.asList("Schlafen", 7));
chart.setData(data);
```

### Verwendung von Karten für komplexere Strukturen {#using-maps-for-more-complex-structures}

Für komplexere Datenstrukturen können Sie Karten verwenden, um Zeilen darzustellen, und diese dann in das erforderliche Format konvertieren.

```java
List<Object> data = new ArrayList<>();

// Kopfzeile
data.add(Arrays.asList("Land", "Umsatz"));

// Datenzeilen
Map<String, Object> row1 = Map.of("Land", "Deutschland", "Umsatz", 1000);
Map<String, Object> row2 = Map.of("Land", "Vereinigte Staaten", "Umsatz", 1170);
Map<String, Object> row3 = Map.of("Land", "Brasilien", "Umsatz", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Sobald die Daten vorbereitet sind, können sie mit der Methode setData auf das GoogleChart angewendet werden.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Laden von Daten und Optionen aus JSON {#loading-data-and-options-from-json}

Sie können auch Daten und Optionen aus JSON-Dateien mit Gson laden, um eine einfachere Verwaltung zu ermöglichen. Dieser Ansatz trägt dazu bei, Ihre Daten und Optionen organisiert und einfach aktualisierbar zu halten.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Jahr", "Umsatz", "Ausgaben"));
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

## Aktualisieren der Diagrammvisualisierungen {#updating-chart-visuals}

Das Aktualisieren oder Neuzeichnen des Aussehens Ihrer Diagramme als Reaktion auf Datenänderungen, Benutzereingaben oder Anpassungen der visuellen Optionen ist einfach mit der Methode `redraw()`. Diese Methode stellt sicher, dass Ihre Diagramme genau und visuell im Einklang mit den zugrunde liegenden Daten oder Änderungen an ihren Einstellungen bleiben.

Rufen Sie `redraw()` in Situationen auf wie:

- **Nach Datenänderungen**: Stellt sicher, dass das Diagramm alle Aktualisierungen seiner Datenquelle widerspiegelt.
- **Bei Änderungen der Optionen**: Wendet neue Stile oder Konfigurationsänderungen auf das Diagramm an.
- **Für responsive Anpassungen**: Passt das Layout oder die Größe des Diagramms an, wenn sich die Abmessungen des Containers ändern, um eine optimale Anzeige auf verschiedenen Geräten sicherzustellen.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/resources/static/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exportieren von Diagrammen als Bilder {#exporting-charts-as-images}

Die Methode `getImageUri()` bietet eine Möglichkeit, Ihre Google Charts als base64-codierte PNG-Bilder zu exportieren. Diese Methode ist besonders nützlich, um Diagramme außerhalb der Webumgebung zu teilen, sie in E-Mails oder Dokumente einzubetten oder einfach für Archivierungszwecke zu verwenden.

Rufen Sie `getImageUri()` auf Ihrer Diagramminstanz auf, nachdem das Diagramm vollständig gerendert wurde. Typischerweise wird diese Methode innerhalb eines "bereit"-Ereignislisteners verwendet, um sicherzustellen, dass das Diagramm bereit zum Export ist:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Jetzt können Sie imageUri verwenden, zum Beispiel als src-Attribut eines img-Tags
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

Das `GoogleChartSelectedEvent` wird ausgelöst, wenn ein Benutzer einen Datenpunkt oder ein Segment in einer Google Chart-Komponente auswählt. Dieses Ereignis ermöglicht die Interaktion mit den ausgewählten Diagrammdaten und bietet Details zu dem, was ausgewählt wurde. Das Ereignis kann durch die Verwendung der Methode `addSelectedListener()` auf der Instanz von `GoogleChart` abgehört werden.

Das `GoogleChartSelectedEvent` ist nützlich in Anwendungen, in denen eine Benutzereingabe mit dem Diagramm erforderlich ist. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Fügen Sie den ausgewählten Listener zum Diagramm hinzu
chart.addSelectedListener(event -> {
  // Holen Sie sich die Auswahl
  List<Object> selection = chart.getSelection();
  
  // Verarbeiten Sie das ausgewählte Ereignis
  if (!selection.isEmpty()) {
    System.out.println("Ausgewählte Zeile: " + selection.get(0));
    // Weitere Verarbeitung basierend auf der Zeilen-/Spaltenauswahl
  }
});
```

### Payload {#payload}
Das `GoogleChartSelectedEvent` bietet Zugriff auf die Selektionsdaten, die mit der Methode `getSelection()` des Diagrammobjekts abgerufen werden können. Diese Methode gibt eine Liste von Objekten zurück, wobei jedes Objekt die folgenden Eigenschaften enthält:

- **row**: Der Index der Zeile in der Datentabelle des Diagramms, die ausgewählt wurde.
- **column**: Der Index der Spalte in der Datentabelle, der optional ist und für Diagramme gilt, die die Auswahl einzelner Zellen zulassen, z. B. bei einem Tabellendiagramm.
  
Bei Diagrammen wie Tortendiagrammen oder Balkendiagrammen wird normalerweise nur die `row` bereitgestellt, die den ausgewählten Datenpunkt angibt.

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
Wenn der Benutzer mehrere Datenpunkte auswählt, gibt die Methode `getSelection()` ein Array von Objekten zurück, die jeweils ein ausgewähltes Element darstellen. Die Payload kann je nach Diagrammtyp und Interaktion des Benutzers variieren.
:::
