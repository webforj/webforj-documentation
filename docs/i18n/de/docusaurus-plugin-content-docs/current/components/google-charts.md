---
title: Google Charts
sidebar_position: 50
_i18n_hash: 84d9b14321275191deb78447cde7c7fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Kurzüberblick über die Komponente und ihre Funktionen -->

:::info Google Charts importieren
Um die Klasse `GoogleChart` in Ihrer App zu verwenden, fügen Sie die folgende XML in Ihre POM-Datei ein:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

Die Klasse `GoogleChart` ist eine umfassende Lösung zum Einbetten von reichhaltigen, interaktiven Diagrammen in Webanwendungen. Diese Klasse fungiert als Brücke zur [Google Charts](https://developers.google.com/chart) Bibliothek und bietet eine Vielzahl von Diagrammtypen, die für jede Datenvisualisierungsaufgabe geeignet sind.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Diagrammtypen {#chart-types}

Das Addon `GoogleChart` bietet eine umfassende Palette von Diagrammtypen, die den verschiedenen Anforderungen an die Datenvisualisierung gerecht werden. Die Auswahl des richtigen Diagrammtyps ist entscheidend, um die Geschichte der Daten effektiv zu kommunizieren. Siehe die Galerie unten für Beispiele gängiger Diagramme, die in einer webforJ-App verwendet werden können.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Optionen {#options}

Das Addon `GoogleChart` ermöglicht umfassende Anpassungen durch eine Vielzahl von Optionen. Diese Optionen ermöglichen es, das Aussehen und die Funktionalität Ihrer Diagramme an die Anforderungen Ihrer App anzupassen. Optionen werden als `Map<String, Object>` an die Methode `setOptions()` des Diagramms übergeben. 

Hier ist ein Beispiel zur Festlegung der Optionen eines Diagramms:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monatlicher Umsatz");
options.put("backgroundColor", "#EFEFEF");

// Wenden Sie die Optionen auf das Diagramm an
chart.setOptions(options);
```

Für weitere Informationen zu den verfügbaren Optionen für spezifische Diagramme siehe die [Google Visualization API Referenz (Diagrammgalerie)](https://developers.google.com/chart/interactive/docs/gallery).

## Daten festlegen {#setting-data}

Die Visualisierung von Daten mit `GoogleChart` erfordert eine ordnungsgemäße Strukturierung und Festlegung der Daten. Dieser Leitfaden zeigt Ihnen, wie Sie Ihre Daten vorbereiten und auf Ihre Diagramme anwenden.

### Grundlegende Datenkonfiguration {#basic-data-setup}

Der einfachste Weg, die Daten zu definieren, besteht darin, `List<Object>` zu verwenden, wobei jede Zeile eine Liste von Werten ist.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Aufgabe", "Stunden pro Tag"));
data.add(Arrays.asList("Arbeit", 11));
data.add(Arrays.asList("Essen", 2));
data.add(Arrays.asList("Pendeln", 2));
data.add(Arrays.asList("Fernsehen", 2));
data.add(Arrays.asList("Schlaf", 7));
chart.setData(data);
```

### Verwendung von Maps für komplexere Strukturen {#using-maps-for-more-complex-structures}

Für komplexere Datenstrukturen können Sie Maps verwenden, um Zeilen darzustellen und diese dann in das erforderliche Format zu konvertieren.

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

Sobald die Daten vorbereitet sind, können sie mit der Methode `setData` auf das GoogleChart angewendet werden.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Laden von Daten und Optionen aus JSON {#loading-data-and-options-from-json}

Sie können auch Daten und Optionen aus JSON-Dateien mit Gson laden, um die Verwaltung zu erleichtern. Dieser Ansatz hilft, Ihre Daten und Optionen organisiert und einfach aktualisierbar zu halten.

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

Das Aktualisieren oder Erneuern des Erscheinungsbilds Ihrer Diagramme als Reaktion auf Datenänderungen, Benutzerinteraktionen oder Anpassungen von visuellen Optionen ist mit der Methode `redraw()` einfach. Diese Methode stellt sicher, dass Ihre Diagramme genau und optisch mit den zugrunde liegenden Daten oder Änderungen an ihren Einstellungen übereinstimmen.

Rufen Sie `redraw()` in Szenarien wie den folgenden auf:

- **Nach Datenmodifikationen**: Stellt sicher, dass das Diagramm alle Aktualisierungen seiner Datenquelle widerspiegelt.
- **Bei Änderung der Optionen**: Wendet neue Stil- oder Konfigurationsänderungen auf das Diagramm an.
- **Für responsive Anpassungen**: Passt das Layout oder die Größe des Diagramms an, wenn sich die Abmessungen des Containers ändern, um eine optimale Anzeige auf verschiedenen Geräten zu gewährleisten.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Diagramme als Bilder exportieren {#exporting-charts-as-images}

Die Methode `getImageUri()` bietet eine Möglichkeit, Ihre Google Charts als base64-kodierte PNG-Bilder zu exportieren. Diese Methode ist besonders nützlich, um Diagramme außerhalb der Webumgebung zu teilen, sie in E-Mails oder Dokumenten einzubetten oder einfach zur Archivierung zu verwenden.

Rufen Sie `getImageUri()` an Ihrer Diagramminstanz auf, nachdem das Diagramm vollständig gerendert wurde. In der Regel wird diese Methode innerhalb eines "ready"-Ereignis-Listeners verwendet, um sicherzustellen, dass das Diagramm bereit für den Export ist:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Jetzt können Sie imageUri verwenden, zum Beispiel als src-Attribut eines img-Tags
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

Das `GoogleChartSelectedEvent` wird ausgelöst, wenn ein Benutzer einen Datenpunkt oder Abschnitt in einer Google Chart-Komponente auswählt. Dieses Ereignis ermöglicht die Interaktion mit den ausgewählten Diagrammdaten und bietet Details zu dem, was ausgewählt wurde. Das Ereignis kann mit der Methode `addSelectedListener()` auf der `GoogleChart`-Instanz abgehört werden.

Das `GoogleChartSelectedEvent` ist in Anwendungen nützlich, in denen eine Benutzerinteraktion mit dem Diagramm erforderlich ist.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Fügen Sie den ausgewählten Listener zum Diagramm hinzu
chart.addSelectedListener(event -> {
    // Holen Sie die Auswahl
    List<Object> selection = chart.getSelection();
    
    // Verarbeiten Sie das ausgewählte Ereignis
    if (!selection.isEmpty()) {
        System.out.println("Ausgewählte Zeile: " + selection.get(0));
        // Weitere Verarbeitung basierend auf der Zeilen-/Spaltenauswahl
    }
});
```

### Payload {#payload}
Das `GoogleChartSelectedEvent` bietet Zugriff auf die Auswahldaten, die mit der Methode `getSelection()` auf dem Diagrammobjekt abgerufen werden können. Diese Methode gibt eine Liste von Objekten zurück, wobei jedes Objekt die folgenden Eigenschaften enthält:

- **row**: Der Index der Zeile in der Datentabelle des Diagramms, die ausgewählt wurde.
- **column**: Der Index der Spalte in der Datentabelle, der optional ist und auf Diagramme zutrifft, die die Auswahl einzelner Zellen ermöglichen, wie z.B. ein Tabellen-Diagramm.
  
Für Diagramme wie Kreisdiagramme oder Balkendiagramme wird normalerweise nur die `row` bereitgestellt, die den ausgewählten Datenpunkt angibt.

Hier ein Beispiel für die Payload:
```java
[
  {
    "row": 3,  // Der ausgewählte Zeilenindex in den Daten
    "column": 2  // (Optional) Der ausgewählte Spaltenindex
  }
]
```

:::info Mehrere Datenpunkte auswählen
Wenn der Benutzer mehrere Datenpunkte auswählt, gibt die Methode `getSelection()` ein Array von Objekten zurück, die jeweils ein ausgewähltes Element repräsentieren. Die Payload kann je nach Diagrammtyp und der Interaktion des Benutzers variieren.
:::
