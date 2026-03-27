---
title: Google Charts
sidebar_position: 50
_i18n_hash: 3fe2f0cf8eb09dad5a6e8fb8f6cfe3cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

Die `GoogleChart`-Komponente integriert die [Google Charts](https://developers.google.com/chart)-Bibliothek in webforJ und gibt Ihnen Zugriff auf Diagrammtypen wie Balken-, Linien-, Kreis-, Geo- und andere Diagramme. Diagramme werden mit Java unter Verwendung eines Typs, eines Datensatzes und einer Optionskarte konfiguriert, die das Erscheinungsbild und das Verhalten steuert.

<!-- INTRO_END -->

## Erstellung eines Diagramms {#creating-a-chart}

:::info Import von Google Charts
Um die `GoogleChart`-Klasse in Ihrer Anwendung zu verwenden, fügen Sie die folgende XML in Ihre POM-Datei ein:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

Um ein Diagramm zu erstellen, geben Sie einen Diagrammtyp an, konfigurieren Sie dessen visuelle Optionen und stellen Sie die anzuzeigenden Daten bereit.

Dieses Beispiel erstellt ein Geo-Diagramm, das Umsatzdaten über verschiedene Länder abbildet, mit benutzerdefinierten Farben, Legendensortierung und Größenanpassung des Diagrammgebiets:

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Diagrammtypen {#chart-types}

Das `GoogleChart`-Addon bietet eine umfassende Auswahl an Diagrammtypen, die verschiedene Anforderungen an die Datenvisualisierung erfüllen. Die Auswahl des geeigneten Diagrammtyps ist entscheidend, um die Geschichte der Daten effektiv zu kommunizieren. Siehe die Galerie unten für Beispiele gängiger Diagramme, die in einer webforJ-Anwendung verwendet werden können.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Optionen {#options}

Das `GoogleChart`-Addon ermöglicht umfangreiche Anpassungen durch eine Vielzahl von Optionen. Diese Optionen erlauben es Ihnen, das Aussehen und die Funktionalität Ihrer Diagramme an die Bedürfnisse Ihrer Anwendung anzupassen. Optionen werden als `Map<String, Object>` an die Methode `setOptions()` des Diagramms übergeben.

Hier ein Beispiel für das Festlegen der Optionen eines Diagramms:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monatlicher Umsatz");
options.put("backgroundColor", "#EFEFEF");

// Wenden Sie die Optionen auf das Diagramm an
chart.setOptions(options);
```

Für weitere Informationen zu den verfügbaren Optionen für spezifische Diagramme siehe die [Dokumentation der Google Visualisierung API (Diagrammgalerie)](https://developers.google.com/chart/interactive/docs/gallery).

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

Für komplexere Datenstrukturen können Sie Maps verwenden, um Zeilen darzustellen und sie dann in das erforderliche Format zu konvertieren.

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

Sobald die Daten vorbereitet sind, können sie mit der Methode setData an GoogleChart angewendet werden.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Laden von Daten und Optionen aus JSON {#loading-data-and-options-from-json}

Sie können auch Daten und Optionen aus JSON-Dateien mit Gson zum einfacheren Management laden. Dieser Ansatz hilft, Ihre Daten und Optionen organisiert und leicht aktualisierbar zu halten.

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

## Aktualisierung des Diagramm-Designs {#updating-chart-visuals}

Das Aktualisieren oder Erneuern des Erscheinungsbilds Ihrer Diagramme als Reaktion auf Datenänderungen, Benutzerinteraktionen oder Anpassungen der visuellen Optionen ist einfach mit der Methode `redraw()`. Diese Methode stellt sicher, dass Ihre Diagramme genau und visuell im Einklang mit den zugrunde liegenden Daten oder Änderungen an ihren Einstellungen bleiben.

Rufen Sie `redraw()` in Szenarien wie diesen auf:

- **Nach Datenänderungen**: Stellt sicher, dass das Diagramm alle Aktualisierungen der Datenquelle widerspiegelt.
- **Beim Ändern von Optionen**: Wendet neue Stile oder Konfigurationsänderungen auf das Diagramm an.
- **Für responsive Anpassungen**: Passt das Layout oder die Größe des Diagramms an, wenn sich die Abmessungen des Containers ändern, um eine optimale Anzeige auf verschiedenen Geräten zu gewährleisten.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exportieren von Diagrammen als Bilder {#exporting-charts-as-images}

Die Methode `getImageUri()` bietet eine Möglichkeit, Ihre Google Charts als base64-kodierte PNG-Bilder zu exportieren. Diese Methode ist besonders nützlich, um Diagramme außerhalb der Webumgebung zu teilen, sie in E-Mails oder Dokumenten einzubetten oder einfach für Archivierungszwecke.

Rufen Sie `getImageUri()` auf Ihrer Diagramminstanz auf, nachdem das Diagramm vollständig gerendert wurde. Typischerweise wird diese Methode innerhalb eines "bereit"-Ereignislisteners verwendet, um sicherzustellen, dass das Diagramm bereit zum Export ist:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Jetzt können Sie das imageUri verwenden, zum Beispiel als src-Attribut eines img-Tags
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

Das `GoogleChartSelectedEvent` wird ausgelöst, wenn ein Benutzer einen Datenpunkt oder ein Segment in einer Google Chart-Komponente auswählt. Dieses Ereignis ermöglicht die Interaktion mit den ausgewählten Diagrammdaten und bietet Details darüber, was ausgewählt wurde. Das Ereignis kann durch die Verwendung der Methode `addSelectedListener()` auf der `GoogleChart`-Instanz abgehört werden.

Das `GoogleChartSelectedEvent` ist nützlich in Anwendungen, in denen die Benutzerinteraktion mit dem Diagramm notwendig ist.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Fügen Sie den ausgewählten Listener zum Diagramm hinzu
chart.addSelectedListener(event -> {
    // Holen Sie die Auswahl ab
    List<Object> selection = chart.getSelection();
    
    // Behandeln Sie das ausgewählte Ereignis
    if (!selection.isEmpty()) {
        System.out.println("Ausgewählte Zeile: " + selection.get(0));
        // Weitere Verarbeitung basierend auf der Zeilen-/Spaltenauswahl
    }
});
```

### Nutzlast {#payload}
Das `GoogleChartSelectedEvent` bietet Zugriff auf die Auswahldaten, die mit der Methode `getSelection()` auf dem Diagramm-Objekt abgerufen werden können. Diese Methode gibt eine Liste von Objekten zurück, wobei jedes Objekt die folgenden Eigenschaften enthält:

- **row**: Der Index der Zeile in der Datentabelle des Diagramms, die ausgewählt wurde.
- **column**: Der Index der Spalte in der Datentabelle, die optional ist und auf Diagramme angewendet wird, die die Auswahl einzelner Zellen ermöglichen, wie z. B. ein Tabellen-Diagramm.
  
Für Diagramme wie Kreis- oder Balkendiagramme wird normalerweise nur die `row` bereitgestellt, die den ausgewählten Datenpunkt angibt.

Hier ein Beispiel für die Nutzlast:
```java
[
  {
    "row": 3,  // Der ausgewählte Zeilenindex in den Daten
    "column": 2  // (Optional) Der ausgewählte Spaltenindex
  }
]
```

:::info Auswahl mehrerer Datenpunkte
Wenn der Benutzer mehrere Datenpunkte auswählt, gibt die Methode `getSelection()` ein Array von Objekten zurück, von denen jedes ein ausgewähltes Element darstellt. Die Nutzlast kann je nach Diagrammtyp und der Interaktion, die der Benutzer ausführt, variieren.
:::
