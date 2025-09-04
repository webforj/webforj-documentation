---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 5ebe30c35548db6d3b603b8a72ed4c2a
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die `Table`-Klasse nutzt `Column`-Klassen, um die Erstellung von Daten Spalten innerhalb dieser zu handhaben. Diese Klasse verfügt über eine Vielzahl von Funktionen, die es dem Benutzer ermöglichen, anzuzeigen, was innerhalb jeder der Spalten angezeigt wird, umfassend anzupassen.
Um eine `Column` zu einer `Table` hinzuzufügen, verwenden Sie eine der `addColumn`-Fabrikmethoden.

## Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein Format zu übersetzen, das für die Anzeige innerhalb einer bestimmten Spalte geeignet ist. Die vom Benutzer definierte Funktion nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese bestimmte Zeile angezeigt werden soll.

Um einen Wertanbieter für eine Spalte festzulegen, verwenden Sie eine der `addColumn`-Fabrikmethoden, die einen Anbieter als Argument akzeptieren:

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

    for (String column : columnsList) {
      table.addColumn(column, (JsonObject person) -> {
        JsonElement element = person.get(column);
        if (!element.isJsonNull()) {
          return element.getAsString();
        }
        return "";
      });
    }
```

In diesem Beispiel wird versucht, auf Daten aus einem JSON-Objekt zuzugreifen; es wird nur gerendert, wenn die Daten nicht null sind.

## Pin-Richtung {#pin-direction}

Das Anheften von Spalten ist ein Feature, das es Benutzern ermöglicht, eine Spalte an einer bestimmten Seite der Tabelle zu befestigen oder "einzupinnen", was die Sichtbarkeit und Zugänglichkeit erhöht. Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder wichtige Informationen, sichtbar bleiben müssen, während horizontal durch eine Tabelle gescrollt wird.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Es gibt drei verfügbare Richtungen zum Anheften einer Spalte:

- `PinDirection.LEFT`: Befestigt die Spalte auf der linken Seite.
- `PinDirection.RIGHT`: Befestigt die Spalte auf der rechten Seite.
- `PinDirection.AUTO`: Spalte erscheint basierend auf der Einfügereihenfolge.

Das Anheften kann programmgesteuert festgelegt werden, sodass Benutzer die Pin-Richtung basierend auf Benutzerinteraktionen oder Anwendungslogik ändern können.

## Ausrichtung {#alignment}

Die Ausrichtung der Spalte definiert die horizontale Positionierung von Daten innerhalb einer Spalte. Sie beeinflusst, wie Datenwerte angezeigt werden, und bietet Benutzern eine visuelle Anleitung zur Natur der Informationen.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Die Table-Komponente unterstützt drei Hauptausrichtungsoptionen:

- `Column.Alignment.LEFT`: Geeignet für Text- oder beschreibende Daten, bei denen eine linksgerechte Anordnung intuitiv ist. Nützlich, wenn der Ausgangspunkt des Inhalts betont werden soll.
- `Column.Alignment.CENTER`: Ideal für numerische oder kategoriale Daten, bei denen eine ausgewogene Darstellung gewünscht wird. Schafft eine visuell zentrierte Anzeige.
- `Column.Alignment.RIGHT`: Häufig für numerische Daten verwendet, insbesondere wenn die Größe oder Präzision von Zahlen von Bedeutung ist. Richtet Daten nach rechts aus für einen natürlichen Lesefluss.

Im obigen Beispiel wurde die letzte Spalte für `Cost` rechtsbündig ausgerichtet, um einen deutlicheren visuellen Unterschied zu bieten.

## Sichtbarkeit {#visibility}

Es ist möglich, die Sichtbarkeit einer `Column` festzulegen, um zu bestimmen, ob sie innerhalb der Tabelle angezeigt wird oder nicht. Dies kann nützlich sein, wenn unter anderem entschieden werden muss, ob sensible Informationen angezeigt werden sollen.

Verwenden Sie die Methode `setHidden()`, wie unten gezeigt, um diese Eigenschaft auf einer `Column` festzulegen:

`table.addColumn("Kreditkarte", Customer::getCreditCardNumber).setHidden(true);`

## Navigierbar {#navigable}

Das Attribut navigierbar bestimmt, ob Benutzer mit einer Spalte während der Navigation interagieren können. Das Setzen von `setSuppressNavigable()` auf true schränkt die Benutzerinteraktion mit der Spalte ein und bietet ein schreibgeschütztes Erlebnis.

```java
table.addColumn("Schreibgeschützte Spalte", Product::getDescription).setSuppressNavigable(true);
```

## Bezeichnung {#label}

Die Bezeichnung einer Spalte ist ihr öffentlich sichtbarer Identifikator, der zur Klarheit und zum Verständnis der angezeigten Daten beiträgt. Verwenden Sie setLabel, um die Bezeichnung, die einer Spalte zugeordnet ist, festzulegen oder zu ändern.

:::tip
Standardmäßig wird die Bezeichnung dieselbe sein wie die Spalten-ID
:::

```java
table.addColumn("Produkt-ID", Product::getProductId).setLabel("ID");
```

## Minimale Breite {#minimum-width}

Die Methode `setMinWidth()` ermöglicht es, die minimale Breite einer Spalte festzulegen, um ein konsistentes und ästhetisch ansprechendes Layout zu gewährleisten.

Wenn die minimale Breite nicht angegeben wird, berechnet die Tabelle die minimale Breite basierend auf dem Spalteninhalt.

```java
table.addColumn("Preis", Product::getPrice).setMinWidth(100.0);
```

:::info
Der übergebene Wert stellt die gewünschte Mindestbreite in Pixel dar.  
:::
