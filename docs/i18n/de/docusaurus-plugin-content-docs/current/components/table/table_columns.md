---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: b3545c590336bb6574bf196fbd417349
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die `Table`-Klasse nutzt `Column`-Klassen, um die Erstellung von Datenspalten innerhalb der Tabelle zu handhaben. Diese Klasse bietet eine breite Palette an Funktionen, die es einem Benutzer ermöglichen, die angezeigten Inhalte innerhalb jeder Spalte umfassend anzupassen. Um eine `Column` zu einer `Table` hinzuzufügen, verwenden Sie eine der `addColumn`-Fabrikmethoden.

## Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein Format zu übersetzen, das für die Anzeige in einer bestimmten Spalte geeignet ist. Die vom Benutzer definierte Funktion nimmt eine Instanz des Zeilendatentyps (T) und gibt den anzuzeigenden Wert für die zugehörige Spalte dieser bestimmten Zeile zurück.

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

In diesem Beispiel wird eine Spalte versuchen, auf Daten aus einem JSON-Objekt zuzugreifen und sie nur anzuzeigen, wenn die Daten nicht null sind.

## Pin-Richtung {#pin-direction}

Das Anheften von Spalten ist eine Funktion, die es Benutzern ermöglicht, eine Spalte an einer bestimmten Seite der Tabelle zu befestigen oder "anzupinnen", um die Sichtbarkeit und Zugänglichkeit zu verbessern. Dies ist nützlich, wenn bestimmte Spalten wie Identifikatoren oder wichtige Informationen sichtbar bleiben sollen, während man horizontal durch eine Tabelle scrollt.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Es gibt drei verfügbare Richtungen für das Anheften einer Spalte:

- `PinDirection.LEFT`: Befestigt die Spalte an der linken Seite.
- `PinDirection.RIGHT`: Befestigt die Spalte an der rechten Seite.
- `PinDirection.AUTO`: Spalte erscheint basierend auf der Einfügefolge.

Das Anheften kann programmgesteuert erfolgen, sodass Benutzer die Anherrichtung basierend auf Benutzerinteraktionen oder Anwendungslogik ändern können.

## Ausrichtung {#alignment}

Die Ausrichtung der Spalte definiert die horizontale Positionierung von Daten innerhalb einer Spalte. Sie beeinflusst, wie Datenwerte angezeigt werden, und gibt den Benutzern eine visuelle Anleitung zur Natur der Informationen. 

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Die Tabellenkomponente unterstützt drei Hauptoptionen für die Ausrichtung:

- `Column.Alignment.LEFT`: Geeignet für textuelle oder beschreibende Daten, bei denen ein linksgerichteter Fluss intuitiv ist. Nützlich, um den Ausgangspunkt des Inhalts hervorzuheben.
- `Column.Alignment.CENTER`: Ideal für numerische oder kategoriale Daten, bei denen eine ausgewogene Präsentation gewünscht ist. Schafft eine visuell zentrierte Anzeige.
- `Column.Alignment.RIGHT`: Wird häufig für numerische Daten verwendet, insbesondere wenn die Größe oder Präzision von Zahlen bedeutend ist. Richte Daten nach rechts aus, um einen natürlichen Lesefluss zu gewährleisten.

Im obigen Beispiel wurde die letzte Spalte für `Kosten` rechtsbündig ausgerichtet, um einen deutlicheren visuellen Unterschied zu schaffen.

## Sichtbarkeit {#visibility}

Es ist möglich, die Sichtbarkeit einer `Column` festzulegen, um zu bestimmen, ob sie in der Tabelle angezeigt wird oder nicht. Dies kann nützlich sein, wenn unter anderem entschieden werden muss, ob sensible Informationen angezeigt werden sollen.

Verwenden Sie die Methode `setHidden()`, wie unten gezeigt, um diese Eigenschaft für eine `Column` festzulegen:

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navigierbar {#navigable}

Das navigierbare Attribut bestimmt, ob Benutzer mit einer Spalte während der Navigation interagieren können. Das Setzen von `setSuppressNavigable()` auf true schränkt die Benutzerinteraktion mit der Spalte ein und bietet eine nur-lesbar Erfahrung.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Etikett {#label}

Das Etikett einer Spalte ist ihr öffentlich sichtbarer Bezeichner, der zur Klarheit und zum Verständnis der angezeigten Daten beiträgt. Verwenden Sie `setLabel`, um das Etikett einer Spalte festzulegen oder zu ändern.

:::tip
Standardmäßig wird das Etikett dasselbe wie die Spalten-ID sein.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## Minimale Breite {#minimum-width}

Die Methode `setMinWidth()` ermöglicht es Ihnen, die minimale Breite einer Spalte zu definieren, um ein konsistentes und ästhetisch ansprechendes Layout zu gewährleisten.

Wenn die minimale Breite nicht angegeben wird, berechnet die Tabelle die minimale Breite basierend auf dem Inhalt der Spalte.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
Der übergebene Wert entspricht der gewünschten minimalen Breite in Pixeln.  
:::
