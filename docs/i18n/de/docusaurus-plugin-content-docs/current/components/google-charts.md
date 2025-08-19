---
title: Google Charts
sidebar_position: 50
_i18n_hash: b477c90cfb24a59329f3047d7ae7d24c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Kurze Übersicht über die Komponente und ihre Funktion -->

:::info Google Charts importieren
Um die Klasse `GoogleChart` in Ihrer Anwendung zu verwenden, fügen Sie die folgende XML in Ihre POM-Datei ein:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

Die Klasse `GoogleChart` ist eine umfassende Lösung zum Einbinden interaktiver Diagramme in Webanwendungen. Diese Klasse fungiert als Brücke zur [Google Charts](https://developers.google.com/chart) Bibliothek und bietet eine Vielzahl von Diagrammtypen, die für jede Datenvisualisierungsaufgabe geeignet sind.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Diagrammtypen {#chart-types}

Das Addon `GoogleChart` bietet eine umfassende Auswahl an Diagrammtypen, die verschiedenen Anforderungen an die Datenvisualisierung gerecht werden. Die Auswahl des geeigneten Diagrammtyps ist entscheidend, um die Geschichte der Daten effektiv zu kommunizieren. Siehe die Galerie unten für Beispiele gängiger Diagramme, die in einer webforJ-Anwendung verwendet werden können.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Optionen {#options}

Das `GoogleChart` Addon ermöglicht umfangreiche Anpassungen durch eine Vielzahl von Optionen. Diese Optionen erlauben es Ihnen, das Aussehen und die Funktionalität Ihrer Diagramme an die Bedürfnisse Ihrer Anwendung anzupassen. Optionen werden als `Map<String, Object>` an die Methode `setOptions()` des Diagramms übergeben.

Hier ist ein Beispiel, um die Optionen eines Diagramms festzulegen:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monatlicher Umsatz");
options.put("backgroundColor", "#EFEFEF");

// Wenden Sie die Optionen auf das Diagramm an
chart.setOptions(options);
```

Für weitere Informationen zu den verfügbaren Optionen für spezifische Diagramme siehe die [Dokumentation zur Google Visualisierungs-API (Diagrammgallerie)](https://developers.google.com/chart/interactive/docs/gallery).

## Daten festlegen {#setting-data}

Um Daten mit `GoogleChart` zu visualisieren, ist es erforderlich, die Daten richtig zu strukturieren und festzulegen. Diese Anleitung führt Sie durch die Vorbereitung Ihrer Daten und deren Anwendung auf Ihre Diagramme.

### Grundlegende Datenstrukturierung {#basic-data-setup}

Die einfachste Möglichkeit, die Daten zu definieren, besteht darin, `List<Object>` zu verwenden, wobei jede Zeile eine Liste von Werten ist.

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

### Verwendung von Maps für komplexere Strukturen {#using-maps-for-more-complex-structures}

Für komplexere Datenstrukturen können Sie Maps verwenden, um Zeilen darzustellen und sie dann in das erforderliche Format zu konvertieren.

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

Sobald die Daten vorbereitet sind, können sie mit der Methode `setData` auf das GoogleChart angewendet werden.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Laden von Daten und Optionen aus JSON {#loading-data-and-options-from-json}

Sie können auch Daten und Optionen aus JSON-Dateien mit Gson laden, um eine einfachere Verwaltung zu ermöglichen. Dieser Ansatz hilft, Ihre Daten und Optionen organisiert und leicht zu aktualisieren zu halten.

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

Das Aktualisieren oder Überarbeiten des Aussehens Ihrer Diagramme als Reaktion auf Datenänderungen, Benutzerinteraktionen oder Anpassungen von visuellen Optionen ist einfach mit der Methode `redraw()`. Diese Methode stellt sicher, dass Ihre Diagramme genau und visuell mit den zugrunde liegenden Daten oder Änderungen an ihren Einstellungen übereinstimmen.

Rufen Sie `redraw()` in Situationen auf wie:

- **Nach Datenänderungen**: Stellt sicher, dass das Diagramm Updates seiner Datenquelle widerspiegelt.
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

Die Methode `getImageUri()` bietet eine Möglichkeit, Ihre Google Charts als base64-kodierte PNG-Bilder zu exportieren. Diese Methode ist besonders nützlich für das Teilen von Diagrammen außerhalb der Webumgebung, das Einbetten in E-Mails oder Dokumente oder einfach für Archivierungszwecke.

Rufen Sie `getImageUri()` auf Ihrer Diagramm-Instanz auf, nachdem das Diagramm vollständig gerendert wurde. Diese Methode wird normalerweise innerhalb eines "ready"-Ereignislisteners verwendet, um sicherzustellen, dass das Diagramm bereit für den Export ist:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Jetzt können Sie imageUri verwenden, zum Beispiel als src-Attribut eines img-Tags
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

Das `GoogleChartSelectedEvent` wird ausgelöst, wann immer ein Benutzer einen Datenpunkt oder ein Segment in einem Google Chart-Diagramm auswählt. Dieses Ereignis ermöglicht die Interaktion mit den ausgewählten Diagrammdaten und bietet Details zu dem, was ausgewählt wurde. Das Ereignis kann gehört werden, indem Sie die Methode `addSelectedListener()` auf der `GoogleChart`-Instanz verwenden.

Das `GoogleChartSelectedEvent` ist in Anwendungen nützlich, in denen die Benutzerinteraktion mit dem Diagramm erforderlich ist.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Fügen Sie den ausgewählten Listener zum Diagramm hinzu
chart.addSelectedListener(event -> {
    // Erhalten Sie die Auswahl
    List<Object> selection = chart.getSelection();
    
    // Verarbeiten Sie das ausgewählte Ereignis
    if (!selection.isEmpty()) {
        System.out.println("Ausgewählte Zeile: " + selection.get(0));
        // Weitere Verarbeitung basierend auf der Zeilen-/Spaltenauswahl
    }
});
```

### Payload {#payload}
Das `GoogleChartSelectedEvent` ermöglicht den Zugriff auf die Auswahldaten, die mit der Methode `getSelection()` auf dem Diagrammobjekt abgerufen werden können. Diese Methode gibt eine Liste von Objekten zurück, wobei jedes Objekt die folgenden Eigenschaften enthält:

- **row**: Der Index der Zeile in der Datentabelle des Diagramms, die ausgewählt wurde.
- **column**: Der Index der Spalte in der Datentabelle, der optional ist und für Diagramme gilt, die die Auswahl einzelner Zellen ermöglichen, wie zum Beispiel ein Tabellen-Diagramm.

Für Diagramme wie Kreisdiagramme oder Balkendiagramme wird typischerweise nur die `row` bereitgestellt, die den ausgewählten Datenpunkt angibt.

Hier ist ein Beispiel für die Payload:
```java
[
  {
    "row": 3,  // Der Index der ausgewählten Zeile in den Daten
    "column": 2  // (Optional) Der Index der ausgewählten Spalte
  }
]
```

:::info Mehrere Datenpunkte auswählen
Wenn der Benutzer mehrere Datenpunkte auswählt, gibt die Methode `getSelection()` ein Array von Objekten zurück, die jeweils ein ausgewähltes Element repräsentieren. Die Payload kann je nach Diagrammtyp und der Interaktion, die der Benutzer ausführt, variieren.
:::
