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

Die `GoogleChart`-Komponente integriert die [Google Charts](https://developers.google.com/chart)-Bibliothek in webforJ und gibt Ihnen Zugriff auf Diagrammtypen wie Balken, Linien, Tortendiagramme, geografische Diagramme und mehr. Diagramme werden mit Java konfiguriert, indem ein Typ, ein Datensatz und eine Optionen-Karte angegeben werden, die das Erscheinungsbild und das Verhalten steuert.

<!-- INTRO_END -->

## Erstellen eines Diagramms {#creating-a-chart}

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

Um ein Diagramm zu erstellen, spezifizieren Sie einen Diagrammtyp, konfigurieren dessen visuelle Optionen und geben die anzuzeigenden Daten an.

Dieses Beispiel erstellt ein geografisches Diagramm, das Umsatzdaten aus verschiedenen Ländern mit benutzerdefinierten Farben, Positionierung der Legende und Größenanpassung des Diagrammbereichs darstellt:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Diagrammtypen {#chart-types}

Das `GoogleChart`-Addon bietet eine umfassende Auswahl an Diagrammtypen, die verschiedenen Anforderungen an die Datenvisualisierung gerecht werden. Die Auswahl des geeigneten Diagrammtyps ist entscheidend, um die Geschichte der Daten effektiv zu kommunizieren. Siehe die Galerie unten für Beispiele gängiger Diagramme, die in einer webforJ-App verwendet werden können.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Optionen {#options}

Das `GoogleChart`-Addon ermöglicht umfassende Anpassungen durch eine Vielzahl von Optionen. Diese Optionen erlauben es Ihnen, das Aussehen und die Funktionalität Ihrer Diagramme an die Bedürfnisse Ihrer App anzupassen. Optionen werden als `Map<String, Object>` an die Methode `setOptions()` des Diagramms übergeben.

Hier ist ein Beispiel zur Einstellung der Optionen eines Diagramms:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monatlicher Umsatz");
options.put("backgroundColor", "#EFEFEF");

// Wenden Sie die Optionen auf das Diagramm an
chart.setOptions(options);
```

Für weitere Informationen zu den verfügbaren Optionen für spezifische Diagramme siehe die [Google Visualization API-Referenz (Diagrammgallerie)](https://developers.google.com/chart/interactive/docs/gallery).

## Daten festlegen {#setting-data}

Die Visualisierung von Daten mit `GoogleChart` erfordert eine ordnungsgemäße Strukturierung und Festlegung der Daten. Dieser Leitfaden führt Sie durch die Vorbereitung Ihrer Daten und deren Anwendung auf Ihre Diagramme.

### Grundlegende Datenkonfiguration {#basic-data-setup}

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

Sobald die Daten vorbereitet sind, können sie mit der Methode setData auf das GoogleChart angewendet werden.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Laden von Daten und Optionen aus JSON {#loading-data-and-options-from-json}

Sie können auch Daten und Optionen aus JSON-Dateien mit Gson laden, um eine einfachere Verwaltung zu ermöglichen. Dieser Ansatz hilft, Ihre Daten und Optionen organisiert und einfach zu aktualisieren zu halten.

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

## Aktualisierung der Diagrammdarstellung {#updating-chart-visuals}

Das `GoogleChart` aktualisiert sich automatisch, wenn sich die Daten, Optionen, der Typ oder die Auswahl ändern. Sie müssen `redraw()` nach der Verwendung von `setData()`, `setOptions()`, `setType()` oder `setSelection()` nicht aufrufen.

Die Komponente hört auch auf Ereignisse zur Größenänderung des Browserfensters und zeichnet automatisch neu, um sich an die neuen Maße anzupassen.

Rufen Sie `redraw()` bei Änderungen der Containergröße auf, die kein Fenstergröße-Änderungsereignis auslösen. Beispiele hierfür sind das Erweitern eines Panels und das Ändern der Größe einer Seitenleiste. Rufen Sie es auf, nachdem die neuen Maße des Containers angewendet wurden.

Die folgende Demo aktualisiert die Daten des Diagramms mit `setData()`. Das Diagramm wird automatisch neu gezeichnet, wenn die neuen Daten angewendet werden.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exportieren von Diagrammen als Bilder {#exporting-charts-as-images}

Die Methode `getImageUri()` ermöglicht es, Ihre Google Charts als base64-kodierte PNG-Bilder zu exportieren. Diese Methode ist besonders nützlich, um Diagramme außerhalb der Webumgebung zu teilen, sie in E-Mails oder Dokumente einzubetten oder einfach für Archivierungszwecke zu verwenden.

Rufen Sie `getImageUri()` auf Ihrer Diagramminstanz auf, nachdem das Diagramm vollständig gerendert wurde. Typischerweise wird diese Methode innerhalb eines "ready"-Ereignislisteners verwendet, um sicherzustellen, dass das Diagramm bereit zum Export ist:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Jetzt können Sie die imageUri verwenden, z. B. als src-Attribut eines img-Tags
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

Das `GoogleChartSelectedEvent` wird ausgelöst, wann immer ein Benutzer einen Datenpunkt oder ein Segment in einer Google Chart-Komponente auswählt. Dieses Ereignis ermöglicht die Interaktion mit den ausgewählten Diagrammdaten und bietet Details darüber, was ausgewählt wurde. Das Ereignis kann abgehört werden, indem die Methode `addSelectedListener()` auf der `GoogleChart`-Instanz verwendet wird.

Das `GoogleChartSelectedEvent` ist nützlich in Anwendungen, in denen die Interaktion des Benutzers mit dem Diagramm erforderlich ist.

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

### Nutzdaten {#payload}
Das `GoogleChartSelectedEvent` bietet Zugriff auf die Auswahldaten, die mit der Methode `getSelection()` auf dem Diagrammobjekt abgerufen werden können. Diese Methode gibt eine Liste von Objekten zurück, wobei jedes Objekt die folgenden Eigenschaften enthält:

- **row**: Der Index der Zeile in der Datentabelle des Diagramms, die ausgewählt wurde.
- **column**: Der Index der Spalte in der Datentabelle, der optional ist und für Diagramme gilt, die die Auswahl einzelner Zellen erlauben, wie z. B. ein Tabellendiagramm.

Für Diagramme wie Tortendiagramme oder Balkendiagramme wird typischerweise nur die `row` bereitgestellt, die den ausgewählten Datenpunkt angibt.

Hier ist ein Beispiel für die Nutzdaten:
```java
[
  {
    "row": 3,  // Der ausgewählte Zeilenindex in den Daten
    "column": 2  // (Optional) Der ausgewählte Spaltenindex
  }
]
```

:::info Mehrere Datenpunkte auswählen
Wenn der Benutzer mehrere Datenpunkte wählt, gibt die Methode `getSelection()` ein Array von Objekten zurück, die jeweils ein ausgewähltes Element darstellen. Die Nutzdaten können je nach Diagrammtyp und der Interaktion, die der Benutzer ausführt, variieren.
:::
